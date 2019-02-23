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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="enrolment/member/edit.do" modelAttribute="enrolment">

<form:hidden path="id"/>
<form:hidden path="version"/>

<form:hidden path="moment"/>
<form:hidden path="endMoment"/>
<form:hidden path="status"/>
<form:hidden path="member"/>



<acme:select items="${positions }" itemLabel="name" code="enrolment.position" path="position"/>
<acme:select items="${brotherhoods }" itemLabel="title" code="enrolment.brotherhood" path="brotherhood"/>

<br/>
<input type="submit" name="save" 
	value="<spring:message code="enrolment.save" />" />
</form:form>