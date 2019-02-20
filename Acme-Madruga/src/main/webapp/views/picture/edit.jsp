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
<form:form action="picture/brotherhood/edit.do" modelAttribute="picture">

	<form:hidden path="id" />
	<form:hidden path="version" />

 
 	<form:label path="url">
		<spring:message code="picture.url" />:
	</form:label>
	<form:input path="url" />
	<form:errors cssClass="error" path="url" />
	<br />
		
	<input type="submit" name="save" value="<spring:message code="picture.save" />" />
	<jstl:if test="${float.id ne 0 }">
		<input type="submit" name="delete" value="<spring:message code="picture.delete" />"/>
	</jstl:if>
	
	<input type="button" name="cancel" value="<spring:message code="picture.cancel" />"
		onclick="javascript: relativeRedir('picture/brotherhood/picturesBrotherhood.do');" />
	<br />
</form:form>
</security:authorize>
</body>
</html>