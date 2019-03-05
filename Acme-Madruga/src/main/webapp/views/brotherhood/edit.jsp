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

<form:form action="brotherhood/edit.do" modelAttribute="brotherhood">
<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="administrator.error" /> </p>
</jstl:if>
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="pictures" />
	
	<fieldset>
	<legend><spring:message code="administrator.personalDatas" /></legend>
	
	<acme:textbox code="administrator.name" path="name"/>
	<br />
	
	<acme:textbox code="administrator.middleName" path="middleName"/>
	<br />
	
	<acme:textbox code="administrator.surname" path="surname"/>
	<br />
		
	<acme:textbox code="administrator.photo" path="photo"/>	
	<br />
	
	<acme:textbox code="administrator.email" path="email"/>	
	<br />
	
	<acme:textbox code="administrator.phone" path="phone"/>	
	<br />
	
	<acme:textbox code="administrator.adress" path="address"/>	
	<br />
	<p><spring:message code="administrator.information" /></p>
	</fieldset>
	<br />
	
	<fieldset>
	 <legend><spring:message code="brotherhood.Data" /></legend>
	<acme:textbox code="brotherhood.title" path="title"/>	
	<acme:textbox code="brotherhood.establishmentDate" path="establishmentDate"/>	
	<br />
	</fieldset>
	
	<fieldset>
	 <legend><spring:message code="administrator.userAccount" /></legend>
	<acme:textbox code="administrator.username" path="userAccount.username"/>	
	<acme:password code="administrator.password" path="userAccount.password"/>
	</fieldset>
	<br />
	
	<acme:submit name="save" code="administrator.save"/>
	<acme:cancel url="welcome/index.do" code="administrator.cancel"/>
	
</form:form>