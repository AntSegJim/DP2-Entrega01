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

<display:table pagesize="5" name="requests" id="row"
requestURI="procession/brotherhood/list.do" >

<display:column property="ticker" titleKey="procession.ticker" />
<display:column property="momemt" titleKey="procession.moment" />
<display:column property="description" titleKey="procession.description" />
<display:column property="title" titleKey="procession.title" />
<display:column property="drafMode" titleKey="procession.draftMode" />
<display:column property="position" titleKey="procession.position" />

<display:column>
	<a href="procession/brotherhood/show.do?processionId=${row.id}"><spring:message code="procession.show" /></a>
</display:column>
<display:column>
	<a href="procession/brotherhood/edit.do?processionId=${row.id}"><spring:message code="procession.edit" /></a>
</display:column>
<display:column>
	<a href="procession/brotherhood/delete.do?processionId=${row.id}"><spring:message code="procession.delete" /></a>
</display:column>

</display:table>

<input type="button" name="create" value="<spring:message code="request.create" />"
			onclick="javascript: relativeRedir('procession/brotherhood/create.do');" />

</security:authorize>