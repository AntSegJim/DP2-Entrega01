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

<display:table pagesize="5" name="enrolments" id="row"
requestURI="enrolment/member/list.do" >

<display:column property="moment" titleKey="enrolment.moment" format="{0,date,dd-MM-yyyy HH:mm}">
</display:column>

<display:column titleKey="enrolment.brotherhood">
<jstl:out value="${row.brotherhood.title}"></jstl:out>
</display:column>

<display:column titleKey="enrolment.position">
<jstl:out value="${row.position.name}"></jstl:out>
</display:column>

</display:table>

<input type="button" name="create" value="<spring:message code="enrolment.create" />"
			onclick="javascript: relativeRedir('enrolment/member/create.do');" />
