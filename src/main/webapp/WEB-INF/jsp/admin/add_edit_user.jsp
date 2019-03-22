<%@ page import="com.epam.burmensky.hospital.model.enumeration.Language" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:choose>
    <c:when test="${userBean.id == 0}">
        <fmt:message key="add_edit_user_jsp.title.add" var="title"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="add_edit_user_jsp.title.edit" var="title"/>
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

        <%-- CONTENT --%>

        <form id="add_edit_user_form" action="controller" method="post">

            <%--===========================================================================
            Hidden field. In the query it will act as command=addUserPost.
            The purpose of this to define the command name, which have to be executed
            after you submit current form.
            ===========================================================================--%>
            <input type="hidden" name="command" value="addEditUserPost"/>

            <c:if test="${userBean.id != 0}">
                <input type="hidden" name="id" value="${userBean.id}">
            </c:if>

            <c:forEach var="record" items="${userBean.userDetails}">

                <div class="form-group">
                    <label for="firstName_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_user_jsp.label.first_name">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <input id="firstName_${Language.getLanguage(record.langId).getLangName()}"
                           name="firstName_${Language.getLanguage(record.langId).getLangName()}"
                           class="form-control" value="${record.firstName}" required/>
                </div>

                <div class="form-group">
                    <label for="secondName_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_user_jsp.label.second_name">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <input id="secondName_${Language.getLanguage(record.langId).getLangName()}"
                           name="secondName_${Language.getLanguage(record.langId).getLangName()}"
                           class="form-control" value="${record.secondName}" required/>
                </div>

                <div class="form-group">
                    <label for="patronymic_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_user_jsp.label.patronymic">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <input id="patronymic_${Language.getLanguage(record.langId).getLangName()}"
                           name="patronymic_${Language.getLanguage(record.langId).getLangName()}"
                           class="form-control" value="${record.patronymic}" required/>
                </div>

            </c:forEach>

            <div class="form-group">
                <label for="roleId"><fmt:message key="add_edit_user_jsp.label.role"/></label>
                <select id="roleId" name="roleId" class="custom-select">
                    <c:forEach var="role" items="${Role.values()}">
                        <c:choose>
                            <c:when test="${role.roleId eq userBean.roleId}">
                                <option selected="selected" value="${role.roleId}">${Role.getRole(role.roleId).getLocalizedName(userLang.locale)}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${role.roleId}">${Role.getRole(role.roleId).getLocalizedName(userLang.locale)}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="specializationId"><fmt:message key="add_edit_user_jsp.label.specialization"/></label>
                <select id="specializationId" name="specializationId" class="custom-select">
                    <c:forEach var="spec" items="${specializationBeans}">
                        <c:choose>
                            <c:when test="${spec.id == userBean.specializationId}">
                                <option selected="selected" value="${spec.id}">${spec.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${spec.id}">${spec.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="langId"><fmt:message key="add_edit_user_jsp.label.language"/></label>
                <select id="langId" name="langId" class="custom-select">
                    <c:forEach var="lang" items="${Language.values()}">
                        <c:choose>
                            <c:when test="${lang.langId == userBean.langId}">
                                <option selected="selected" value="${lang.langId}">${lang.getLocalizedName(userLang.locale)}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${lang.langId}">${lang.getLocalizedName(userLang.locale)}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="birthday"><fmt:message key="add_edit_user_jsp.label.birthday"/></label>
                <fmt:formatDate value="${userBean.birthday}" pattern="yyyy-MM-dd" var="bd"/>
                <input id="birthday" type="date" name="birthday" class="form-control" value="${bd}" required/>
            </div>

            <div class="form-group">
                <label for="login"><fmt:message key="add_edit_user_jsp.label.login"/></label>
                <input id="login" type="text" name="login" class="form-control" value="${userBean.login}" required/>
            </div>

            <div class="form-group">
                <label for="password"><fmt:message key="add_edit_user_jsp.label.password"/></label>
                <input id="password" type="password" name="password" class="form-control" value="${userBean.password}" required/>
            </div>

            <button type="submit" class="btn btn-primary"><fmt:message key="add_edit_user_jsp.button.save"/></button>

        </form>

        <%-- CONTENT --%>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>