<%@ page import="com.epam.burmensky.hospital.model.enumeration.Language" %>
<%@ page import="com.epam.burmensky.hospital.model.enumeration.RecordType" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:choose>
    <c:when test="${recordBean.id == 0}">
        <fmt:message key="add_edit_card_record_jsp.title.add" var="title"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="add_edit_card_record_jsp.title.edit" var="title"/>
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

        <form id="add_edit_card_record_form" action="controller" method="post">

            <%--===========================================================================
            Hidden field. In the query it will act as command=addUserPost.
            The purpose of this to define the command name, which have to be executed
            after you submit current form.
            ===========================================================================--%>
            <input type="hidden" name="command" value="addEditCardRecordPost"/>

            <c:if test="${recordBean.id != 0}">
                <input type="hidden" name="id" value="${recordBean.id}">
            </c:if>

            <input type="hidden" name="patientId" value="${recordBean.patientId}">
            <input type="hidden" name="userId" value="${recordBean.userId}">

            <div class="form-group">
                <label for="recordTypeId"><fmt:message key="add_edit_card_record_jsp.label.record_type"/></label>
                <select id="recordTypeId" name="recordTypeId" class="custom-select">
                    <c:forEach var="type" items="${recordTypes}">
                        <c:choose>
                            <c:when test="${type.recordTypeId eq recordBean.recordTypeId}">
                                <option selected="selected" value="${type.recordTypeId}">${RecordType.getRecordType(type.recordTypeId).getLocalizedName(userLang.locale)}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${type.recordTypeId}">${RecordType.getRecordType(type.recordTypeId).getLocalizedName(userLang.locale)}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="date"><fmt:message key="add_edit_card_record_jsp.label.date"/></label>
                <fmt:formatDate value="${recordBean.date}" pattern="yyyy-MM-dd" var="recordDate"/>
                <input id="date" type="date" name="date" class="form-control" value="${recordDate}" required/>
            </div>

            <c:forEach var="record" items="${recordBean.healthCardRecordDetails}">

                <div class="form-group">
                    <label for="text_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_card_record_jsp.label.text">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <textarea id="text_${Language.getLanguage(record.langId).getLangName()}"
                          name="text_${Language.getLanguage(record.langId).getLangName()}"
                          class="form-control" required>${record.text}</textarea>
                </div>

            </c:forEach>

            <button type="submit" class="btn btn-primary"><fmt:message key="add_edit_card_record_jsp.button.save"/></button>

        </form>

        <%-- CONTENT --%>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</div>
</body>
</html>