package cn.itcast.ssm.controller;

import cn.itcast.ssm.controller.validation.ValidGroup1;
import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//商品的controller
@Controller
//为了对url进行分类管理，可以在这里定义根路径，最终访问url是根路径+子路径
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    //商品分类
    //itemTypes表示将方法返回值放在request中的key
    @ModelAttribute("itemTypes")
    public Map<String, String> getItemTypes() {
        Map<String, String> itemTypes = new HashMap<String, String>();
        itemTypes.put("101", "数码");
        itemTypes.put("102", "母婴");
        return itemTypes;
    }

    //商品列表
    @RequestMapping("/queryItems")
    public ModelAndView queryItems(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception {
        //调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribute，在jsp页面中通过itemsList取数据
        modelAndView.addObject("itemsList", itemsList);
        modelAndView.setViewName("items/itemsList");
        return modelAndView;
    }

    //商品信息修改页面展示
    @RequestMapping(value = "/editItems", method = {RequestMethod.POST, RequestMethod.GET})
    //@RequestParam里面绑定request传入参数名称和形参进行绑定
    //通过required属性制定参数是否必须要传入
    //通过defaultValue可以设置默认值，如果id参数没有传入,将默认值和形参进行绑定
    public String editItems(Model model, HttpServletRequest request, @RequestParam(value = "id", required = true) Integer a) throws Exception {
        ItemsCustom itemsCustom = itemsService.findItemsById(a);

        //判断商品是否为空,根据id没有查询到商品，抛出异常，提示用户商品信息不存在
//        if(itemsCustom==null){
//            throw new CustomException("修改信息不存在");
//        }

        model.addAttribute("items", itemsCustom);
        return "items/editItems";
    }

    //商品信息修改提交
    //在需要校验的pojo前边添加@Validated，在需要校验的pojo后边添加BindingResult bindingResult接受校验出错信息
    //注意：@Validated和BindingResult bindingResult是配对出现，并且形参顺序是固定的（一前一后）
    //@ModelAttribute可以制定pojo回显到request中的key
    @RequestMapping("/editItemsSubmit")
    public String editItemsSubmit(Model model, HttpServletRequest request, HttpServletResponse response, Integer id,
                                  @ModelAttribute("items")
                                  @Validated(value = {ValidGroup1.class}) ItemsCustom itemsCustom, BindingResult bindingResult,
                                  MultipartFile items_pic//接受商品图片
    ) throws Exception {
        //获取校验错误信息
        if (bindingResult.hasErrors()) {
            //输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                //打印错误信息
                System.out.println(objectError.getDefaultMessage());
            }
            model.addAttribute("allErrors", allErrors);

            //或者  可以直接使用model将提交pojo回显到页面
            //model.addAttribute("items",itemsCustom);

            //出错重新到商品修改页面
            return "items/editItems";
        }

        //上传图片
        if (items_pic != null) {
            //存储图片的物理路径
            String pic_path = "D:\\upload\\temp\\";
            //原始图片名称
            String orginalFilename = items_pic.getOriginalFilename();
            if (orginalFilename != "" && orginalFilename.length() > 0) {
                //新的图片名称
                String newFilename = UUID.randomUUID() + orginalFilename.substring(orginalFilename.lastIndexOf("."));
                //新图片
                File newFile = new File((pic_path + newFilename));
                //将内容中的数据写入磁盘
                items_pic.transferTo(newFile);
                //将新的图片名称写到itemsCustom中
                itemsCustom.setPic(newFilename);
            }
        }

        itemsService.updateItems(id, itemsCustom);

        //重定向到商品查询列表 request域不共享
        return "redirect:queryItems.action";

        //页面转发 request域共享
//        return "forward:queryItems.action";

//        request.getRequestDispatcher("queryItems.action").forward(request,response);

//        response.sendRedirect("queryItems.action");

//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write("json串");
    }

    ////////////////////数据绑定-数组
    //批量删除商品信息
    @RequestMapping("/deleteItems")
    public String deleteItems(Integer[] items_id) throws Exception {

        return "success";
    }

    ////////////////////数据绑定-List集合
    //批量修改商品页面，将商品信息查询出来，在页面中可以编辑商品信息
    @RequestMapping("/editItemsQuery")
    public ModelAndView editItemsQuery(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception {
        //调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribute，在jsp页面中通过itemsList取数据
        modelAndView.addObject("itemsList", itemsList);
        modelAndView.setViewName("items/editItemsQuery");
        return modelAndView;
    }

    //批量修改商品提交
    //通过ItemsQueryVo接受批量提交的商品信息，将商品信息存储到itemsQueryVo中
    @RequestMapping("/editItemsAllSubmit")
    public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception {

        return "success";
    }

    ////////////////////数据绑定-Map
    //同上

    //查询商品信息，输出json
    @RequestMapping("/itemsView/{id}")
    //@RequestMapping("/itemsView/{id}/{type}")
    public @ResponseBody ItemsCustom itemsView(@PathVariable Integer id) throws Exception{

        //调用service查询商品信息
        ItemsCustom itemsCustom=itemsService.findItemsById(id);
        return itemsCustom;
    }


}
