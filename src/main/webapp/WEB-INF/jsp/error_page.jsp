<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<fmt:message key="error_page_jsp.title" var="title"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

    <%-- HEADER --%>
    <%@ include file="/WEB-INF/jspf/header.jspf"%>
    <%-- HEADER --%>

    <div class="container">
        <%-- CONTENT --%>

        <div class="alert alert-danger d-flex justify-content-center" role="alert">
            <h2><fmt:message key="error_page_jsp.header"/></h2>
        </div>

        <%-- this way we get the error information (error 404)--%>
        <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
        <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>

        <%-- this way we get the exception --%>
        <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

        <c:if test="${not empty code}">
            <h1 class="display-3">Error code: ${code}</h1>
        </c:if>

        <c:if test="${not empty message}">
            <h3>${message}</h3>
        </c:if>

        <%-- if get this page using forward --%>
        <c:if test="${not empty errorMessage and empty exception and empty code}">
            <h3>Error message: ${errorMessage}</h3>
        </c:if>

        <%-- this way we print exception stack trace --%>
        <c:if test="${not empty exception}">
            <hr/>
            <h3>Stack trace:</h3>
            <c:forEach var="stackTraceElement" items="${exception.stackTrace}">
                ${stackTraceElement}
            </c:forEach>
        </c:if>

        <%-- CONTENT --%>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>