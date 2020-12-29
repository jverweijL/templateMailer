<%@ include file="/init.jsp" %>

<portlet:actionURL var="sendmailURL" name="/sendmail"></portlet:actionURL>

<h3><liferay-ui:message key="templatemailer.preview.header"/></h3>
<aui:form action="<%= sendmailURL %>" method="post" name="fm">
    <aui:input name="to" type="hidden" value="${to}"/>
    <aui:input name="subject" type="hidden" value="${subject}" />
    <aui:input name="body" type="hidden" value="${body}"/>
    <aui:fieldset-group markupView="lexicon">
        <aui:fieldset label="">
            <h3>To</h3>
            <div>${to}</div>
            <h3>Subject</h3>
            <div>${subject}</div>
            <h3>Body</h3>
            <div>${body}</div>
        </aui:fieldset>
    </aui:fieldset-group>

    <aui:button-row>
        <aui:button cssClass="btn-lg" type="submit" value="Send"/>
    </aui:button-row>
</aui:form>