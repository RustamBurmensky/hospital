<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%--===========================================================================
Here we use a table layout.
Class page corresponds to the '.page' element in included CSS document.
===========================================================================--%>

    <%--===========================================================================
    This is the HEADER, containing a top menu.
    header.jspf contains all necessary functionality for it.
    Just included it in this JSP document.
    ===========================================================================--%>

    <%-- HEADER --%>
    <%@ include file="/WEB-INF/jspf/header.jspf"%>
    <%-- HEADER --%>


    <div class="container">

    <%--===========================================================================
    This is the CONTENT, containing the main part of the page.
    ===========================================================================--%>

    <div class="row d-flex justify-content-center">

        <%-- if get this page using forward --%>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                Error message: ${errorMessage}
            </div>
        </c:if>

        <%-- CONTENT --%>

        <%--===========================================================================
        Defines the web form.
        ===========================================================================--%>
        <form id="login_form" action="controller" method="post">

            <%--===========================================================================
            Hidden field. In the query it will act as command=login.
            The purpose of this to define the command name, which have to be executed
            after you submit current form.
            ===========================================================================--%>
            <input type="hidden" name="command" value="login"/>

            <div class="form-group">
                <label for="login"><fmt:message key="login_jsp.label.login"/></label>
                <input id="login" name="login" class="form-control"/>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="login_jsp.label.password"/></label>
                <input id="password" type="password" name="password" class="form-control"/>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="login_jsp.button.login"/></button>
        </form>

        <%-- CONTENT --%>

    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</div>
</body>
</html>