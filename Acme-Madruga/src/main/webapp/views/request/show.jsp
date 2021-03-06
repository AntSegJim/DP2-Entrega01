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

<security:authorize access="hasRole('BROTHERHOOD')">

<spring:message code="request.row"/>:<jstl:out value=" ${request.row}"></jstl:out><br>
<spring:message code="request.columna"/>: <jstl:out value="${request.columna}"></jstl:out><br>
<spring:message code="request.description"/>: <jstl:out value="${request.description}"></jstl:out><br>
<spring:message code="request.member.name"/>: <jstl:out value="${request.member.name}"></jstl:out><br>
<spring:message code="request.procession"/>: <jstl:out value="${request.procession.title}"></jstl:out><br>
<spring:message code="request.status"/>: 
<jstl:if test="${request.status eq 1 }">
<spring:message code="request.pending"/>
</jstl:if>
<jstl:if test="${request.status eq 0 }">
<spring:message code="request.accepted"/>
</jstl:if>
<jstl:if test="${request.status eq 2 }">
<spring:message code="request.rejected"/>
</jstl:if>
<br>
<input type="button" name="create" value="<spring:message code="procession.back" />"
			onclick="javascript: relativeRedir('procession/brotherhood/list.do');" />

</security:authorize>

<security:authorize access="hasRole('MEMBER')">

<spring:message code="request.row"/>:<jstl:out value=" ${request.row}"></jstl:out><br>
<spring:message code="request.columna"/>: <jstl:out value="${request.columna}"></jstl:out><br>
<spring:message code="request.description"/>: <jstl:out value="${request.description}"></jstl:out><br>
<spring:message code="request.member.name"/>: <jstl:out value="${request.member.name}"></jstl:out><br>
<spring:message code="request.procession"/>: <jstl:out value="${request.procession.title}"></jstl:out><br>
<spring:message code="request.status"/>: 
<jstl:if test="${request.status eq 1 }">
<spring:message code="request.pending"/>
</jstl:if>
<jstl:if test="${request.status eq 0 }">
<spring:message code="request.accepted"/>
</jstl:if>
<jstl:if test="${request.status eq 2 }">
<spring:message code="request.rejected"/>
</jstl:if>
<br>
<input type="button" name="create" value="<spring:message code="procession.back" />"
			onclick="javascript: relativeRedir('request/member/list.do');" />

</security:authorize>