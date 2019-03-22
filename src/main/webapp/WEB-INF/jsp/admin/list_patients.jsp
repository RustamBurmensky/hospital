<%@ page import="com.epam.burmensky.hospital.model.enumeration.PatientSortingMode" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib uri="com.epam.burmensky.hospital.web.tag.pagination" prefix="p" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<html>

<fmt:message key="list_patients_jsp.title" var="title"/>
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
            <c:when test="${fn:length(patientBeanList) == 0}">
                <fmt:message key="list_patients_jsp.table.empty"/>
            </c:when>
            <c:otherwise>

                <form action="controller" method="get">

                    <input type="hidden" name="command" value="listPatients">

                    <div class="form-group">
                        <label for="sortingMode"><fmt:message key="list_patients_jsp.label.sort"/></label>
                        <select id="sortingMode" name="sortingMode" class="custom-select">
                            <c:forEach var="sortMode" items="${PatientSortingMode.values()}">
                                <c:choose>
                                    <c:when test="${sortMode.sortModeId eq sortingMode.sortModeId}">
                                        <option selected="selected" value="${sortMode.sortModeId}">${sortMode.getLocalizedName(userLang.locale)}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${sortMode.sortModeId}">${sortMode.getLocalizedName(userLang.locale)}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary"><fmt:message key="list_patients_jsp.button.sort"/></button>

                </form>

                <c:choose>
                    <c:when test="${userRole == Role.ADMIN}">
                        <c:set var="colCount" value="5"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="colCount" value="1"/>
                    </c:otherwise>
                </c:choose>
                <c:set var="s" value="s"/>

                <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th><fmt:message key="list_patients_jsp.table.header.second_name"/></th>
                            <th><fmt:message key="list_patients_jsp.table.header.first_name"/></th>
                            <th><fmt:message key="list_patients_jsp.table.header.patronymic"/></th>
                            <th><fmt:message key="list_patients_jsp.table.header.birthday"/></th>
                            <th colspan="${colCount}">
                                <fmt:message key="list_patients_jsp.table.header.operations"/>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bean" items="${patientBeanList}">
                            <tr>
                                <td>${bean.secondName}</td>
                                <td>${bean.firstName}</td>
                                <td>${bean.patronymic}</td>
                                <td><h:dateFormatter date="${bean.birthday}" locale="${userLang.locale}"/></td>
                                <td>
                                    <a class="btn btn-outline-success" href="controller?command=listCardRecords&patientId=${bean.id}">
                                        <i class="material-icons">assignment</i>
                                    </a>
                                </td>
                                <c:if test="${userRole == Role.ADMIN}">
                                <td>
                                    <a class="btn btn-outline-success" href="controller?command=listAppointments&patientId=${bean.id}">
                                        <i class="material-icons">people</i>
                                    </a>
                                </td>
                                <td>
                                    <a class="btn btn-outline-success" href="controller?command=showDischarge&patientId=${bean.id}">
                                        <i class="material-icons">assignment_turned_in</i>
                                    </a>
                                </td>
                                <td>
                                    <a class="btn btn-outline-primary" href="controller?command=addEditPatientGet&id=${bean.id}">
                                        <i class="material-icons">edit</i>
                                    </a>
                                </td>
                                <td>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="id" value="${bean.id}">
                                        <input type="hidden" name="command" value="deletePatient"/>
                                        <button type="submit" class="btn btn-outline-danger"><i class="material-icons">delete</i></button>
                                    </form>
                                </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </div>
                
                <p:printPagination currentPage="${currentPage}"
                                   pageNumber="${pageNumber}"
                                   url="controller?command=listPatients"
                                   sortingMode="${sortingMode.sortModeId}"/>

            </c:otherwise>
        </c:choose>

        <%-- CONTENT --%>
        </div>
    </div>

    <a class="btn btn-outline-primary" href="controller?command=addEditPatientGet"><fmt:message key="list_patients_jsp.button.add"/></a>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>