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

<display:table pagesize="5" name="requests" id="row"
requestURI="request/member/list.do" >

<display:column property="status" titleKey="request.status" />
<display:column property="columna" titleKey="request.columna" />
<display:column property="row" titleKey="request.row" />
<display:column property="description" titleKey="request.description" />
<display:column property="member.name" titleKey="request.member" />
<display:column property="procession.ticker" titleKey="request.procession" />

<display:column>
	<a href="request/member/delete.do?requestId=${row.id}"><spring:message code="request.delete" /></a>
</display:column>

</display:table>

</security:authorize>