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

<p><spring:message code="profile.action.1" /></p>
<p><spring:message code="profile.action.2"/></p>

<security:authorize access="isAuthenticated()">

<img src="${actor.photo}">  <br/>
<br/>
<b><spring:message code="profile.action.2.name" /> </b> ${actor.name} <br/>
<b><spring:message code="profile.action.2.middleName" /></b> ${actor.middleName} <br/>
<b><spring:message code="profile.action.2.surname" /></b> ${actor.surname} <br/>
<b><spring:message code="profile.action.2.email" /></b> ${actor.email} <br/>
<b><spring:message code="profile.action.2.phone" /></b> ${actor.phone} <br/>
<b><spring:message code="profile.action.2.address" /></b> ${actor.address} <br/>

<security:authorize access="hasRole('BROTHERHOOD')">
<b><spring:message code="profile.brotherhood.title" /></b> ${actor.title} <br/>
<b><spring:message code="profile.brotherhood.establishmentDate" /></b> ${actor.establishmentDate} <br/>

<display:table name="${actor.pictures}" id="row">
<display:column property="url" 
titleKey="profile.brotherhood.pictures" />
</display:table>


<!--<display:table name="pictures" id="row">
		<display:column property="url" titleKey="profile.brotherhood.pictures" />
			
			</display:table>-->

</security:authorize>

<br/>
<input type="button" name="cancel" value="<spring:message code="administrator.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />


</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<form action="profile/edit-administrator.do">
    <input type="submit" value="<spring:message code="profile.edit.profile" />" />
</form>
</security:authorize>


<security:authorize access="hasRole('BROTHERHOOD')">
<form action="profile/edit-brotherhood.do">
    <input type="submit" value="<spring:message code="profile.edit.profile" />" />
</form>
</security:authorize>


