<%@ include file="/init.jsp" %>

<portlet:renderURL var="mailpreviewURL">
    <portlet:param name="mvcRenderCommandName" value="/preview"/>
</portlet:renderURL>

<h3><liferay-ui:message key="templatemailer.form.header"/></h3>
<aui:form action="<%= mailpreviewURL %>" method="post" name="fm">
    <aui:fieldset-group markupView="lexicon">
        <aui:fieldset label="">
            <aui:input name="classPK" type="hidden" value="${classPK}"/>
            <aui:input label="To" name="to" type="email" required="true"/>
            <c:forEach items="<%= (ArrayList<String>) request.getAttribute("fieldnames") %>" var="fieldname">
                <aui:input label="${fieldname}" name="${fieldname}" type="text"/>
            </c:forEach>
        </aui:fieldset>
    </aui:fieldset-group>

    <aui:button-row>
        <aui:button cssClass="btn-lg" type="submit" value="Preview"/>
    </aui:button-row>
</aui:form>