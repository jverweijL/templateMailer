<%@ include file="/init.jsp" %>

<portlet:renderURL var="formURL">
	<portlet:param name="mvcRenderCommandName" value="/form" />
</portlet:renderURL>

<h3><liferay-ui:message key="templatemailer.select.header"/></h3>

<liferay-ui:search-container var="searchContainer" emptyResultsMessage="Oops. There Are No Users To Display, Please add Users">
	<liferay-ui:search-container-results results="<%= (List<AssetEntry>) request.getAttribute("assetentries") %>" />
	<liferay-ui:search-container-row modelVar="article" className="com.liferay.asset.kernel.model.AssetEntry">
		<liferay-ui:search-container-column-text name="Template" value="<%= HtmlUtil.escape(article.getTitleCurrentValue()) %>"/>
		<liferay-ui:search-container-column-text align="right">
			<aui:button cssClass="group-selector-button" value="choose" onclick="submitForm(${article.getClassPK()})" />
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>


<aui:form action="<%= formURL %>" method="post" name="fm">
	<aui:input name="classPK" type="hidden" />
</aui:form>

<aui:script>
	function submitForm(classPK) {

		var form = document.<portlet:namespace />fm;
		document.getElementById("<portlet:namespace />classPK").setAttribute('value',classPK);
		form.submit();
	}

</aui:script>

