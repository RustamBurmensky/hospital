<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ attribute name="locale" required="true" type="java.util.Locale" rtexprvalue="true" %>
<%@ attribute name="date" required="true" type="java.util.Date" rtexprvalue="true" %>
<c:choose>
    <c:when test="${locale.language == 'en'}">
        <fmt:formatDate value="${date}" pattern="dd/MM/yyyy"/>
    </c:when>
    <c:otherwise>
        <fmt:formatDate value="${date}" pattern="dd.MM.yyyy"/>
    </c:otherwise>
</c:choose>
