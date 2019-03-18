<%@ page import="com.epam.burmensky.hospital.model.enumeration.RecordType" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib uri="com.epam.burmensky.hospital.web.tag.pagination" prefix="p" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<html>

<fmt:message key="list_card_records_jsp.title" var="title">
    <fmt:param value="${patient.secondName} ${patient.firstName}"/>
</fmt:message>
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

    <h1>${title}</h1>

    <%--===========================================================================
    This is the CONTENT, containing the main part of the page.
    ===========================================================================--%>

    <div class="row">
        <div class="col-sm-12">

        <%-- CONTENT --%>

        <c:choose>
            <c:when test="${fn:length(recordBeanList) == 0}">
                <fmt:message key="list_card_records_jsp.table.empty"/>
            </c:when>
            <c:otherwise>

                <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th><fmt:message key="list_card_records_jsp.table.header.physician_name"/></th>
                            <th><fmt:message key="list_card_records_jsp.table.header.specialization"/></th>
                            <th><fmt:message key="list_card_records_jsp.table.header.record_type"/></th>
                            <th><fmt:message key="list_card_records_jsp.table.header.date"/></th>
                            <th><fmt:message key="list_card_records_jsp.table.header.record_text"/></th>
                            <th colspan="2"><fmt:message key="list_card_records_jsp.table.header.operations"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bean" items="${recordBeanList}">
                            <tr>
                                <td>
                                    ${bean.physicianSecondName} ${bean.physicianFirstName} ${bean.physicianPatronymic}
                                </td>
                                <td>${bean.specializationName}</td>
                                <td>${RecordType.getRecordType(bean.recordTypeId).getLocalizedName(userLang.locale)}</td>
                                <td><h:dateFormatter date="${bean.date}" locale="${userLang.locale}"/></td>
                                <td>${bean.text}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${bean.userId eq user.id or userRole eq Role.ADMIN}">
                                            <a class="btn btn-outline-primary" href="controller?command=addEditCardRecordGet&id=${bean.id}">
                                                <i class="material-icons">edit</i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="btn btn-outline-dark disabled" href="#">
                                                <i class="material-icons">edit</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${bean.userId eq user.id or userRole eq Role.ADMIN}">
                                            <form action="controller" method="post">
                                                <input type="hidden" name="id" value="${bean.id}">
                                                <input type="hidden" name="command" value="deleteCardRecord"/>
                                                <button type="submit" class="btn btn-outline-danger"><i class="material-icons">delete</i></button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="btn btn-outline-dark disabled" href="#">
                                                <i class="material-icons">delete</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </div>

                <p:printPagination currentPage="${currentPage}"
                                   pageNumber="${pageNumber}"
                                   url="controller?command=listCardRecord&patientId=${patient.id}"/>

            </c:otherwise>
        </c:choose>

        <%-- CONTENT --%>
        </div>
    </div>

    <a class="btn btn-outline-primary" href="controller?command=addEditCardRecordGet&patientId=${patient.id}"><fmt:message key="list_card_records_jsp.button.add"/></a>

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</div>
</body>
</html>