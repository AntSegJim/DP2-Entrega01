<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="member/edit.do" modelAttribute="memberRegistrationForm">
<jstl:if test="${not empty exception}">
		<b style="color:red"> <spring:message code="member.error" /> </b>
</jstl:if>
	<form:hidden path="id"/>
	<form:hidden path="version" />
	
	<fieldset>
	<legend><spring:message code="administrator.personalDatas" /></legend>
	
	<acme:textbox code="administrator.name" path="name"/>
	<acme:textbox code="administrator.middleName" path="middleName"/>
	<acme:textbox code="administrator.surname" path="surname"/>
	<acme:textbox code="administrator.photo" path="photo"/>
	<acme:textbox code="administrator.email" path="email"/>
	<acme:textbox code="administrator.phone" path="phone"/>
	<acme:textbox code="administrator.adress" path="address"/>
	
	<p><spring:message code="administrator.information" /></p>
	</fieldset>
	<br />
	
	<fieldset>
	 <legend><spring:message code="administrator.userAccount" /></legend>
	<acme:textbox code="administrator.username" path="userAccount.username"/>
	<acme:password code="administrator.password" path="userAccount.password"/>
	<acme:password code="administrator.confirmation.password" path="password2"/>
	</fieldset>
	<br />
	<acme:checkbox code="Terminos.Condiciones" path="check" />
	<a  target="_blank" href="https://localhost:8443/Sample-Project/termsAndConditions/show.do"><spring:message code="Terminos.Condiciones" /></a> 
	
	<input type="submit" name="save" 
	value="<spring:message code="administrator.save" />" />

<acme:cancel url="welcome/index.do" code="administrator.cancel"/>
	
</form:form>

<script>
$( document ).ready(function() {
	document.getElementById("checkbox").value='false';
	document.getElementById("checkbox").checked=false;
});
$( '#checkbox' ).on( 'click', function() {
    if( $(this).is(':checked') ){
        document.getElementById("checkbox").value='true';
    } else {
    	document.getElementById("checkbox").value='false';
    }
});
</script>