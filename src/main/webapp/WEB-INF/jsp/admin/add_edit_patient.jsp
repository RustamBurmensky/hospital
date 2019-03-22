<%@ page import="com.epam.burmensky.hospital.model.enumeration.Language" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:choose>
    <c:when test="${patientBean.id == 0}">
        <fmt:message key="add_edit_patient_jsp.title.add" var="title"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="add_edit_patient_jsp.title.edit" var="title"/>
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

        <form id="add_edit_patient_form" action="controller" method="post">

            <%--===========================================================================
            Hidden field. In the query it will act as command=addUserPost.
            The purpose of this to define the command name, which have to be executed
            after you submit current form.
            ===========================================================================--%>
            <input type="hidden" name="command" value="addEditPatientPost"/>

            <c:if test="${patientBean.id != 0}">
                <input type="hidden" name="id" value="${patientBean.id}">
            </c:if>

            <c:forEach var="record" items="${patientBean.patientDetails}">

                <div class="form-group">
                    <label class="col-form-label" for="firstName_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_patient_jsp.label.first_name">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <input id="firstName_${Language.getLanguage(record.langId).getLangName()}"
                           name="firstName_${Language.getLanguage(record.langId).getLangName()}"
                           class="form-control" value="${record.firstName}" required/>
                </div>

                <div class="form-group">
                    <label for="secondName_${Language.getLanguage(record.langId).getLangName()}}">
                        <fmt:message key="add_edit_patient_jsp.label.second_name">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <input id="secondName_${Language.getLanguage(record.langId).getLangName()}"
                           name="secondName_${Language.getLanguage(record.langId).getLangName()}"
                           class="form-control" value="${record.secondName}" required/>
                </div>

                <div class="form-group">
                    <label for="patronymic_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_patient_jsp.label.patronymic">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <input id="patronymic_${Language.getLanguage(record.langId).getLangName()}"
                           name="patronymic_${Language.getLanguage(record.langId).getLangName()}"
                           class="form-control" value="${record.patronymic}" required/>
                </div>

                <div class="form-group">
                    <label for="address_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_patient_jsp.label.address">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <input id="address_${Language.getLanguage(record.langId).getLangName()}"
                           name="address_${Language.getLanguage(record.langId).getLangName()}"
                           class="form-control" value="${record.address}" required/>
                </div>

                <div class="form-group">
                    <label for="occupation_${Language.getLanguage(record.langId).getLangName()}">
                        <fmt:message key="add_edit_patient_jsp.label.occupation">
                            <fmt:param value="${Language.getLanguage(record.langId).getLocalizedName(userLang.locale)}"/>
                        </fmt:message>
                    </label>
                    <input id="occupation_${Language.getLanguage(record.langId).getLangName()}"
                           name="occupation_${Language.getLanguage(record.langId).getLangName()}"
                           class="form-control" value="${record.occupation}" required/>
                </div>

            </c:forEach>

            <div class="form-group">
                <label for="birthday"><fmt:message key="add_edit_patient_jsp.label.birthday"/></label>
                <fmt:formatDate value="${patientBean.birthday}" pattern="yyyy-MM-dd" var="bd"/>
                <input id="birthday" type="date" name="birthday" class="form-control" value="${bd}" required/>
            </div>

            <div class="form-group">
                <label for="weight"><fmt:message key="add_edit_patient_jsp.label.weight"/></label>
                <input id="weight" type="number" name="weight" class="form-control" value="${patientBean.weight}" min="0" max="500" required/>
            </div>

            <div class="form-group">
                <label for="height"><fmt:message key="add_edit_patient_jsp.label.height"/></label>
                <input id="height" type="number" name="height" class="form-control" value="${patientBean.height}" min="0" max="350" required/>
            </div>

            <div class="form-group">
                <label for="admissionDate"><fmt:message key="add_edit_patient_jsp.label.admission_date"/></label>
                <fmt:formatDate value="${patientBean.admissionDate}" pattern="yyyy-MM-dd" var="ad"/>
                <input id="admissionDate" type="date" name="admissionDate" class="form-control" value="${ad}" required/>
            </div>

            <fieldset class="form-group">
                <div class="row">
                    <legend class="col-form-label col-sm-2 pt-0"><fmt:message key="add_edit_patient_jsp.label.treatment"/></legend>
                    <div class="col-sm-10">
                        <div class="form-check">
                            <c:choose>
                                <c:when test="${patientBean.inpatient}">
                                    <input class="form-check-input" type="radio" name="inpatient" id="inpatient" value="true" checked>
                                </c:when>
                                <c:otherwise>
                                    <input class="form-check-input" type="radio" name="inpatient" id="inpatient" value="true">
                                </c:otherwise>
                            </c:choose>
                            <label class="form-check-label" for="inpatient">
                                <fmt:message key="add_edit_patient_jsp.label.inpatient"/>
                            </label>
                        </div>
                        <div class="form-check">
                            <c:choose>
                                <c:when test="${not patientBean.inpatient}">
                                    <input class="form-check-input" type="radio" name="inpatient" id="outpatient" value="false" checked>
                                </c:when>
                                <c:otherwise>
                                    <input class="form-check-input" type="radio" name="inpatient" id="outpatient" value="false">
                                </c:otherwise>
                            </c:choose>
                            <label class="form-check-label" for="outpatient">
                                <fmt:message key="add_edit_patient_jsp.label.outpatient"/>
                            </label>
                        </div>
                    </div>
                </div>
            </fieldset>

            <button type="submit" class="btn btn-primary"><fmt:message key="add_edit_patient_jsp.button.save"/></button>

        </form>

        <%-- CONTENT --%>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>