package cn.itcast.ssm.orginalDao;

import cn.itcast.ssm.po.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoImplTest {

    private ApplicationContext applicationContext;

    //在setUp这个方法的得到spring容器
    @Before
    public void setUp() throws Exception{
        applicationContext=new ClassPathXmlApplicationContext("classpath:config/spring/applicationContext.xml");
    }

    @Test
    public void testFindUserById() throws Exception {
        UserDao userDao=(UserDao) applicationContext.getBean("userDao");
        User user=userDao.findUserById(1);
        System.out.println(user);
    }

}
