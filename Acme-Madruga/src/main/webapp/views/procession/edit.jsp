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

<security:authorize access="hasRole('BROTHERHOOD')">
<spring:message code="brotherhood.procession"/>

<spring:message code="rocession.rows"/><input id="filas" type = "text" name = "filas">
<spring:message code="rocession.columns"/><input id="columnas" type = "text" name = "columnas">

<form:form action="procession/brotherhood/edit.do" modelAttribute="request">
<form:hidden path="id"/>
<form:hidden path="version"/>
<acme:textbox code="procession.ticker" path="ticker"/>
<acme:textbox code="procession.description" path="description"/>
<acme:textbox code="procession.title" path="title"/>
<acme:textbox code="procession.moment" path="moment"/>
<acme:textbox code="procession.draftMode" path="draftMode"/>
<form:hidden id="get-position" path="position"/>
<br/>
<input type="submit" name="save" 
	value="<spring:message code="position.save" />" />
</form:form>

<input type="button" name="cancel" value="<spring:message code="request.cancel" />"
			onclick="javascript: relativeRedir('procession/brotherhood/list.do');" />
			
<script type="text/javascript">
var filas = document.getElementById("filas");
var columnas = document.getElementById("columnas");
var matrix = [];
for(var i=0; i<filas; i++) {
    matrix[i] = [];
    for(var j=0; j<columnas; j++) {
        matrix[i][j] = 0;
    }
}
document.getElementById("get-position").value = matrix;
</script>

</security:authorize>

