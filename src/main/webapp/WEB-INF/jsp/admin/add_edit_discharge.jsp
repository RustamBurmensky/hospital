<%@ page import="com.epam.burmensky.hospital.model.enumeration.Language" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:choose>
    <c:when test="${dischargeBean.id == 0}">
        <fmt:message key="add_edit_discharge_jsp.title.add" var="title"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="add_edit_discharge_jsp.title.edit" var="title"/>
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

        <form id="add_edit_discharge_form" action="controller" method="post">

            <%--===========================================================================
            Hidden field. In the query it will act as command=addUserPost.
            The purpose of this to define the command name, which have to be executed
            after you submit current form.
            ===========================================================================--%>
            <input type="hidden" name="command" value="addEditDischargePost"/>

            <c:if test="${dischargeBean.id != 0}">
                <input type="hidden" name="id" value="${dischargeBean.id}">
            </c:if>

            <input type="hidden" name="patientId" value="${dischargeBean.patientId}">

            <div class="form-group">
                <label for="date"><fmt:message key="add_edit_discharge_jsp.label.date"/></label>
                <fmt:formatDate value="${dischargeBean.date}" pattern="yyyy-MM-dd" var="dischargeDate"/>
                <input id="date" type="date" name="date" class="form-control" value="${dischargeDate}" required/>
            </div>

            <c:forEach var="record" items="${dischargeBean.dischargeDetails}">

                <div class="form-group">
                    <label for="diagnosis_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_discharge_jsp.label.diagnosis">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <textarea id="diagnosis_${Language.getLanguage(record.langId).getLangName()}"
                          name="diagnosis_${Language.getLanguage(record.langId).getLangName()}"
                          class="form-control" required>${record.diagnosis}</textarea>
                </div>

            </c:forEach>

            <button type="submit" class="btn btn-primary"><fmt:message key="add_edit_discharge_jsp.button.save"/></button>

        </form>

        <%-- CONTENT --%>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</div>
</body>
</html>