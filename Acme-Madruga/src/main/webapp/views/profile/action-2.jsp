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



<p><spring:message code="profile.action.3" /></p>

<security:authorize access="isAuthenticated()">
<form:form  modelAttribute="actor" action="${action}">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="profile.error" /> </p>
</jstl:if>

<form:label path="name"><spring:message code="profile.action.3.changePersonalData" />:</form:label><br /><br />
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="userAccount" />

	
	<form:label path="name"><spring:message code="profile.action.3.name" />:</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name" />	
	<br />
	
	<form:label path="middleName"><spring:message code="profile.action.3.middleName" />:</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />
	
	<form:label path="surname"><spring:message code="profile.action.3.surname" />:</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	<form:label path="email"><spring:message code="profile.action.3.email" />:</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email" />	
	<br />
	
	<form:label path="photo"><spring:message code="profile.action.3.photo" />:</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<form:label path="phone"><spring:message code="profile.action.3.phone" />:</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="address"><spring:message code="profile.action.3.address" />:</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	<security:authorize access="hasRole('BROTHERHOOD')">
<form:label path="title"><spring:message code="profile.brotherhood.title" />:</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="establishmentDate"><spring:message code="profile.brotherhood.establishmentDate" />:</form:label>
	<form:input path="establishmentDate" />
	<form:errors cssClass="error" path="establishmentDate" />
	<br />
	
	<!--<form:label path="pictures"><spring:message code="profile.brotherhood.pictures" />:</form:label>
	<form:input path="pictures" />
	<form:errors cssClass="error" path="pictures" />-->
	
		<form:label path="pictures">
		<spring:message code="profile.brotherhood.pictures" />:
	</form:label>
	<form:select id="pictures" path="pictures" >
		<form:option value="0" label="----" />		
		<form:options items="${pictures}" itemValue="id" itemLabel="url"/>
	
	</form:select>
	<br />
	

	</security:authorize>
	<br />
	<input type="submit" name="save" value="<spring:message code="profile.action.3.save" />" />
	<input type="button" name="cancel" value="<spring:message code="administrator.cancel" />"
			onclick="javascript: relativeRedir('profile/personal-datas.do');" />

</form:form>

</security:authorize>

