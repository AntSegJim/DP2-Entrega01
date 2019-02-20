<%--
 * action-2.jsp
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
<security:authorize access="hasRole('BROTHERHOOD')">

<display:table pagesize="5" name="pictures" id="row"
requestURI="picture/brotherhood/picturesBrotherhood.do" >
<display:column>
	<a href="picture/brotherhood/edit.do?pictureId=${row.id}" ><spring:message code="picture.edit" /></a>
</display:column>
<display:column property="picture" titleKey="picture.picture"> <img src="${row.picture}" width="130px" height="80px">  </display:column>
</display:table>
<form action="picture/brotherhood/edit.do">
    <input type="submit" value="<spring:message code="picture.create" />" />
</form>
</security:authorize>





