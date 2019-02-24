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

<security:authorize access="hasRole('MEMBER')">
<spring:message code="request.brotherhood"/>
<select id="select-prueba" name="brotherhoodId">
	<option value="0">---</option>
	<option value="2">1</option>
    <jstl:forEach var="item" items="${brotherhoods}">		
		<option value="${item.id}">{item.title}</option>
    </jstl:forEach>
</select>

<form:form action=" request/member/edit.do" modelAttribute="request">

<acme:textbox code="request.status" path="status"/>
<acme:textbox code="request.description" path="description"/>
<acme:textbox code="request.columna" path="columna"/>
<acme:textbox code="request.row" path="row"/>
<acme:select id="select-aRellenar" items="${processions}" itemLabel="ticker" code="request.procession" path="procession"/>
<br/>
<input type="submit" name="save" 
	value="<spring:message code="position.save" />" />
	

<input type="button" name="cancel" value="<spring:message code="request.cancel" />"
			onclick="javascript: relativeRedir('request/member/list.do');" />
</form:form>

<script type="text/javascript">
	$(document).ready(function(){
		$('select').change(function(){
			var brotherhoodId = $('#select-prueba option:selected').attr('value');
			$.ajax({
				type:'GET',
				url:'procession/member/list.do?brotherhoodId='+brotherhoodId,
				success: function(res) {
					document.getElementById("rellenarme").innerHTML = res;
			        console.log(res);
			        alert(res);
			    }
			});
		});
	});
</script>
<div id="rellenarme"></div>
</security:authorize>

