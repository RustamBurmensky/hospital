<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib uri="com.epam.burmensky.hospital.web.tag.pagination" prefix="p" %>

<html>

<fmt:message key="show_discharge_jsp.title" var="title">
    <fmt:param value="${patient.secondName}"/>
    <fmt:param value="${patient.firstName}"/>
    <fmt:param value="${patient.patronymic}"/>
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
            <c:when test="${empty dischargeBean}">
                <fmt:message key="show_discharge_jsp.table.empty"/>
            </c:when>
            <c:otherwise>

                <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th><fmt:message key="show_discharge_jsp.table.header.date"/></th>
                            <th><fmt:message key="show_discharge_jsp.table.header.diagnosis"/></th>
                            <th colspan="2"><fmt:message key="show_discharge_jsp.table.header.operations"/></th>
                        </tr>
                    </thead>
                    <tbody>
                            <fmt:formatDate value="${dischargeBean.date}" pattern="dd.MM.yyyy" var="dischargeDate"/>
                            <tr>
                                <td>${dischargeDate}</td>
                                <td>${dischargeBean.diagnosis}</td>
                                <td>
                                    <a class="btn btn-outline-primary" href="controller?command=addEditDischargeGet&id=${dischargeBean.id}">
                                        <i class="material-icons">edit</i>
                                    </a>
                                </td>
                                <td>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="patientId" value="${dischargeBean.patientId}">
                                        <input type="hidden" name="command" value="deleteDischarge"/>
                                        <button type="submit" class="btn btn-outline-danger"><i class="material-icons">delete</i></button>
                                    </form>
                                </td>
                            </tr>
                    </tbody>
                </table>
                </div>

            </c:otherwise>
        </c:choose>

        <%-- CONTENT --%>
        </div>
    </div>

        <c:if test="${empty dischargeBean}">
            <a class="btn btn-outline-primary"
               href="controller?command=addEditDischargeGet&patientId=${patient.id}">
                <fmt:message key="show_discharge_jsp.button.discharge"/>
            </a>
        </c:if>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>