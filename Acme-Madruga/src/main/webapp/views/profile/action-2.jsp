<%--
 * action-2.jsp
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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<p><spring:message code="profile.action.3" /></p>

<security:authorize access="isAuthenticated()">
<form:form  modelAttribute="actor" action="${action}">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="profile.error" /> </p>
</jstl:if>

<form:label path="name"><spring:message code="profile.action.3.changePersonalData" />:</form:label><br /><br />
	<form:hidden path="id"/>
	<form:hidden path="version" />
	
	
	<security:authorize access="hasRole('BROTHERHOOD')">
	<form:hidden path="pictures" />
	</security:authorize>
	
	<acme:textbox code="profile.action.3.name" path="name"/>
	<br />
	<acme:textbox code="profile.action.3.middleName" path="middleName"/>
	<br />
	<acme:textbox code="profile.action.3.surname" path="surname"/>
	<br />
	<acme:textbox code="profile.action.3.email" path="email"/>	
	<br />
	<acme:textbox code="profile.action.3.photo" path="photo"/>
	<br />
	<acme:textbox code="profile.action.3.phone" path="phone"/>
	<br />
	<acme:textbox code="profile.action.3.address" path="address"/>
	<br />
	
	<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:textbox code="profile.brotherhood.title" path="title"/>
	<br />
	<acme:textbox code="profile.brotherhood.establishmentDate" path="establishmentDate"/>
	<br />
	</security:authorize>
	
	<br />
	<acme:submit name="save" code="profile.action.3.save"/>
	<acme:cancel url="profile/personal-datas.do" code="administrator.cancel"/>
</form:form>

</security:authorize>

