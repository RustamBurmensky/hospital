<%@ page import="com.epam.burmensky.hospital.model.enumeration.UserSortingMode" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib uri="com.epam.burmensky.hospital.web.tag.pagination" prefix="p" %>

<html>

<fmt:message key="list_users_jsp.title" var="title"/>
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
            <c:when test="${fn:length(userBeanList) == 0}">
                <fmt:message key="list_users_jsp.table.empty"/>
            </c:when>
            <c:otherwise>

                <form action="controller" method="get">

                    <input type="hidden" name="command" value="listUsers">

                    <div class="form-group">
                        <label for="sortingMode"><fmt:message key="list_users_jsp.label.sort"/></label>
                        <select id="sortingMode" name="sortingMode" class="custom-select">
                            <c:forEach var="sortMode" items="${UserSortingMode.values()}">
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

                    <button type="submit" class="btn btn-primary"><fmt:message key="list_users_jsp.button.sort"/></button>

                </form>

                <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th><fmt:message key="list_users_jsp.table.header.second_name"/></th>
                            <th><fmt:message key="list_users_jsp.table.header.first_name"/></th>
                            <th><fmt:message key="list_users_jsp.table.header.patronymic"/></th>
                            <th><fmt:message key="list_users_jsp.table.header.role"/></th>
                            <th><fmt:message key="list_users_jsp.table.header.specialization"/></th>
                            <th><fmt:message key="list_users_jsp.table.header.patients_number"/></th>
                            <th colspan="2"><fmt:message key="list_users_jsp.table.header.operations"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bean" items="${userBeanList}">
                            <tr>
                                <td>${bean.secondName}</td>
                                <td>${bean.firstName}</td>
                                <td>${bean.patronymic}</td>
                                <td>${Role.getRole(bean.roleId).getLocalizedName(userLang.locale)}</td>
                                <td>${bean.specializationName}</td>
                                <td>${bean.patientsNumber}</td>
                                <td>
                                    <a class="btn btn-outline-primary" href="controller?command=addEditUserGet&id=${bean.id}">
                                        <i class="material-icons">edit</i>
                                    </a>
                                </td>
                                <td>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="id" value="${bean.id}">
                                        <input type="hidden" name="command" value="deleteUser"/>
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
                                   url="controller?command=listUsers"
                                   sortingMode="${sortingMode.sortModeId}"/>

            </c:otherwise>
        </c:choose>

        <%-- CONTENT --%>
        </div>
    </div>

    <a class="btn btn-outline-primary" href="controller?command=addEditUserGet"><fmt:message key="list_users_jsp.button.add"/></a>

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</div>
</body>
</html>