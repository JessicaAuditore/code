﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <TITLE>添加联系人</TITLE>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
    <LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
          rel=stylesheet>


    <META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
<c:if test="${sessionScope.user.realname==null}">
    <c:redirect url="${pageContext.request.contextPath }/login.jsp"></c:redirect>
</c:if>

<c:if test="${sessionScope.user.level!='管理员'}">
    <c:redirect url="${pageContext.request.contextPath }/jsp/noRight.jsp"></c:redirect>
</c:if>

<FORM id=form1 name=form1
      action="${pageContext.request.contextPath }/linkman_updateLinkMan.action"
      method=post>
    <input type="hidden" name="lkmId" value="${linkman.lkmId }"/>

    <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
        <TBODY>
        <TR>
            <TD width=15><IMG src="${pageContext.request.contextPath }/images/new_019.jpg"
                              border=0></TD>
            <TD width="100%" background="${pageContext.request.contextPath }/images/new_020.jpg"
                height=20></TD>
            <TD width=15><IMG src="${pageContext.request.contextPath }/images/new_021.jpg"
                              border=0></TD>
        </TR>
        </TBODY>
    </TABLE>
    <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
        <TBODY>
        <TR>
            <TD width=15 background=${pageContext.request.contextPath }/images/new_022.jpg><IMG
                    src="${pageContext.request.contextPath }/images/new_022.jpg" border=0></TD>
            <TD vAlign=top width="100%" bgColor=#ffffff>
                <TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
                    <TR>
                        <TD class=manageHead>当前位置：联系人管理 &gt; 修改联系人</TD>
                    </TR>
                    <TR>
                        <TD height=2></TD>
                    </TR>
                </TABLE>
                <TABLE cellSpacing=0 cellPadding=5 border=0>
                    <TR>
                        <td>联系人名称：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="lkmName" value="${linkman.lkmName}">
                        </td>
                        <td>联系人性别：</td>
                        <td>
                            <input type="radio" value="男" name="lkmGender"
                                   <c:if test="${linkman.lkmGender=='男' }">checked</c:if>>男

                            <input type="radio" value="女" name="lkmGender"
                                   <c:if test="${linkman.lkmGender=='女' }">checked</c:if>>女
                        </td>
                    </TR>
                    <TR>
                        <td>联系人电话 ：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="lkmPhone" value="${linkman.lkmPhone}">
                        </td>
                        <td>联系人手机 ：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="lkmMobile" value="${linkman.lkmMobile}">
                        </td>
                    </TR>
                    <tr>
                        <td>所属客户：</td>
                        <td colspan="3">
                            <select name="customer.cid">
                                <c:forEach var="customer" items="${listCustomer}">
                                    <c:if test="${customer.custName==linkman.customer.custName}">
                                        <option value="${customer.cid}" selected>${customer.custName}</option>
                                    </c:if>
                                    <c:if test="${customer.custName!=linkman.customer.custName}">
                                        <option value="${customer.cid}">${customer.custName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan=2>
                            <INPUT class=button id=sButton2 type=submit
                                   value="保存 " name=sButton2>
                        </td>
                    </tr>
                </TABLE>


            </TD>
            <TD width=15 background="${pageContext.request.contextPath }/images/new_023.jpg">
                <IMG src="${pageContext.request.contextPath }/images/new_023.jpg" border=0></TD>
        </TR>
        </TBODY>
    </TABLE>
    <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
        <TBODY>
        <TR>
            <TD width=15><IMG src="${pageContext.request.contextPath }/images/new_024.jpg"
                              border=0></TD>
            <TD align=middle width="100%"
                background="${pageContext.request.contextPath }/images/new_025.jpg" height=15></TD>
            <TD width=15><IMG src="${pageContext.request.contextPath }/images/new_026.jpg"
                              border=0></TD>
        </TR>
        </TBODY>
    </TABLE>
</FORM>
</BODY>
</HTML>
