<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib uri="com.epam.burmensky.hospital.web.tag.pagination" prefix="p" %>

<html>

<fmt:message key="list_specializations_jsp.title" var="title"/>
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
            <c:when test="${fn:length(specializationBeanList) == 0}">
                <fmt:message key="list_specializations_jsp.table.empty"/>
            </c:when>
            <c:otherwise>

                <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th><fmt:message key="list_specializations_jsp.table.header.name"/></th>
                            <th colspan="2"><fmt:message key="list_specializations_jsp.table.header.operations"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bean" items="${specializationBeanList}">

                            <tr>
                                <td>${bean.name}</td>
                                <td>
                                    <a class="btn btn-outline-primary" href="controller?command=addEditSpecializationGet&id=${bean.id}">
                                        <i class="material-icons">edit</i>
                                    </a>
                                </td>
                                <td>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="id" value="${bean.id}">
                                        <input type="hidden" name="command" value="deleteSpecialization"/>
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
                                   url="controller?command=listSpecializations"/>

            </c:otherwise>
        </c:choose>

        <%-- CONTENT --%>
        </div>
    </div>

    <a class="btn btn-outline-primary" href="controller?command=addEditSpecializationGet"><fmt:message key="list_specializations_jsp.button.add"/></a>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>