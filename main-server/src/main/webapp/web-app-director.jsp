<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: wstar
  Date: 05.09.2020
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset=UTF-8>
    <link rel="icon" href="/favicon.ico">
    <title>Loading...</title>
    <c:set var="path" value="${pageContext.request.requestURI}"/>
    <c:choose>
        <c:when test="${fn:startsWith(path, '/m/')}">
            <script src="/maker/maker.nocache.js"></script>
        </c:when>
        <c:otherwise>
            <script src="/app/app.nocache.js"></script>
        </c:otherwise>
    </c:choose>
</head>
<body>
<noscript>
    <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled
        in order for this application to display correctly.
    </div>
</noscript>
</body>
</html>

