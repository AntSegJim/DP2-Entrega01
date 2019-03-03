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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<security:authorize access="hasRole('ADMIN')">

<fieldset>
<legend><spring:message code="administrator.member.brotherhood" /></legend>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${memberBrotherhoodAvg}"></jstl:out> <br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${memberBrotherhoodMin}"></jstl:out><br/>
<b><spring:message code="administrator.avg" /></b>: <jstl:out value="${memberBrotherhoodMax}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "2" value ="${memberBrotherhoodDesv}"></fmt:formatNumber>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.Procession" /></legend>
<jstl:if test="${fn:length(procession) ne 0}">
<jstl:forEach var="item" items="${procession}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

</security:authorize>
