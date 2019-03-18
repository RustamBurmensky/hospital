<%@ page import="com.epam.burmensky.hospital.model.enumeration.Language" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:choose>
    <c:when test="${specializationBean.id == 0}">
        <fmt:message key="add_edit_specialization_jsp.title.add" var="title"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="add_edit_specialization_jsp.title.edit" var="title"/>
    </c:otherwise>
</c:choose>
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

    <h1>${title}</h1>

    <div class="row">

        <%-- CONTENT --%>

        <form id="add_edit_specialization_form" action="controller" method="post">

            <%--===========================================================================
            Hidden field. In the query it will act as command=addUserPost.
            The purpose of this to define the command name, which have to be executed
            after you submit current form.
            ===========================================================================--%>
            <input type="hidden" name="command" value="addEditSpecializationPost"/>

            <c:if test="${specializationBean.id != 0}">
                <input type="hidden" name="id" value="${specializationBean.id}">
            </c:if>

            <c:forEach var="record" items="${specializationBean.specializationDetails}">
                <div class="form-group">
                    <label for="name_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_specialization_jsp.label.name">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <input id="name_${Language.getLanguage(record.langId).getLangName()}"
                           type="text"
                           name="name_${Language.getLanguage(record.langId).getLangName()}"
                           class="form-control" value="${record.name}" required/>
                </div>
            </c:forEach>

            <button type="submit" class="btn btn-primary"><fmt:message key="add_edit_specialization_jsp.button.save"/></button>

        </form>

        <%-- CONTENT --%>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</div>
</body>
</html>