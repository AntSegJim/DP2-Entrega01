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

<security:authorize access="hasRole('MEMBER')">
<jstl:if test="${exception eq 'e'}">
	<b style="color:red;"><spring:message code="request.cant.delete"/></b><br>
</jstl:if>
<display:table pagesize="5" name="requests" id="row"
requestURI="request/member/list.do" >

<display:column titleKey="enrolment.status">

<jstl:if test="${row.status eq 1 }">
	<spring:message code="enrolment.status.pending" />
</jstl:if>

<jstl:if test="${row.status eq 0 }">
	<spring:message code="enrolment.status.accepted" />
</jstl:if>

<jstl:if test="${row.status eq 2 }">
	<spring:message code="enrolment.status.cancel" />
</jstl:if>
</display:column>

<display:column property="columna" titleKey="request.columna" />
<display:column property="row" titleKey="request.row" />
<display:column property="description" titleKey="request.description" />
<display:column property="procession.title" titleKey="request.procession" />

<display:column>
	<jstl:if test="${row.status eq 1 }">
		<a href="request/member/delete.do?requestId=${row.id}"><spring:message code="request.delete" /></a>
	</jstl:if>
	<jstl:if test="${row.status eq 0 or row.status eq 2 }">
	-
	</jstl:if>
</display:column>

</display:table>

<input type="button" name="create" value="<spring:message code="request.create" />"
			onclick="javascript: relativeRedir('request/member/create.do');" />

</security:authorize>