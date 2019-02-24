<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


</head>
<body>

<security:authorize access="hasRole('BROTHERHOOD')">
<form:form action="float/brotherhood/edit.do" modelAttribute="paso">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="pictures" />
	<form:hidden path="procession" />

 
 	<form:label path="title">
		<spring:message code="float.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="description">
		<spring:message code="float.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="brotherhood">
	<spring:message code="float.brotherhood" />:
	</form:label>
	<form:select id="brotherhood" path="brotherhood">
		<form:options items="${brotherhoods}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="brotherhood" />
	<br />
		
	<input type="submit" name="save" value="<spring:message code="float.save" />" />
	<jstl:if test="${paso.id ne 0 }">
		<input type="submit" name="delete" value="<spring:message code="float.delete" />"/>
	</jstl:if>
	
	<input type="button" name="cancel" value="<spring:message code="float.cancel" />"
		onclick="javascript: relativeRedir('float/brotherhood/list.do');" />
	<br />
</form:form>
</security:authorize>
</body>
</html>