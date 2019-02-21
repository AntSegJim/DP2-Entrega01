<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="isAuthenticated()">

<display:table pagesize="5" name="notifications" id="row"
requestURI="notification/actor/list.do" >

<display:column>
	<a href="notification/actor/show.do?notificationId=${row.id}"><spring:message code="notification.moreDetails" /></a>
</display:column>

<display:column property="subject" titleKey="notification.subject" />


</display:table>

</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<a href="notification/actor/create.do"><spring:message code="notification.create" /></a>
</security:authorize>