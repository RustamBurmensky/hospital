<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib uri="com.epam.burmensky.hospital.web.tag.pagination" prefix="p" %>

<html>

<fmt:message key="list_appointments_jsp.title" var="title">
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

        <form id="add_appointment_form" action="controller" method="post">

            <%--===========================================================================
            Hidden field. In the query it will act as command=addAppointmentPost.
            The purpose of this to define the command name, which have to be executed
            after you submit current form.
            ===========================================================================--%>
            <input type="hidden" name="command" value="addAppointmentPost"/>
            <input type="hidden" name="patientId" value="${patient.id}">

            <div class="form-group">
                <label for="userId"><fmt:message key="list_appointments_jsp.label.physician"/></label>
                <select id="userId" name="userId" class="custom-select">
                    <c:forEach var="userEntry" items="${usersToAppoint}">
                        <option value="${userEntry.id}">${userEntry.secondName} ${userEntry.firstName} ${userEntry.patronymic}
                        (${userEntry.specializationName})</option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn btn-primary"><fmt:message key="list_appointments_jsp.button.save"/></button>

        </form>

    <div class="row">
        <div class="col-sm-12">

        <%-- CONTENT --%>

        <c:choose>
            <c:when test="${fn:length(appointmentBeanList) == 0}">
                <fmt:message key="list_appointments_jsp.table.empty"/>
            </c:when>
            <c:otherwise>

                <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th><fmt:message key="list_appointments_jsp.table.header.second_name"/></th>
                            <th><fmt:message key="list_appointments_jsp.table.header.first_name"/></th>
                            <th><fmt:message key="list_appointments_jsp.table.header.patronymic"/></th>
                            <th><fmt:message key="list_appointments_jsp.table.header.role"/></th>
                            <th><fmt:message key="list_appointments_jsp.table.header.specialization"/></th>
                            <th><fmt:message key="list_appointments_jsp.table.header.operations"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bean" items="${appointmentBeanList}">
                            <tr>
                                <td>${bean.secondName}</td>
                                <td>${bean.firstName}</td>
                                <td>${bean.patronymic}</td>
                                <td>${Role.getRole(bean.roleId).getLocalizedName(userLang.locale)}</td>
                                <td>${bean.specializationName}</td>
                                <td>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="command" value="deleteAppointment"/>
                                        <input type="hidden" name="userId" value="${bean.id}"/>
                                        <input type="hidden" name="patientId" value="${patient.id}">
                                        <button type="submit" class="btn btn-outline-danger"><i class="material-icons">delete</i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </div>
                
                <p:printPagination currentPage="${currentPage}"
                                   pageNumber="${pageNumber}"
                                   url="controller?command=listAppointments&patientId=${patient.id}"/>

            </c:otherwise>
        </c:choose>

        <%-- CONTENT --%>
        </div>
    </div>

    <a class="btn btn-outline-primary" href="controller?command=addEditAppointmentGet&patientId=${patient.id}"><fmt:message key="list_appointments_jsp.button.add"/></a>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>