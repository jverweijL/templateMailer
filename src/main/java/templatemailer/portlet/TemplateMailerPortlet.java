package templatemailer.portlet;

import com.liferay.asset.util.AssetHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import templatemailer.constants.TemplateMailerPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author jverweij
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=TemplateMailer",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + TemplateMailerPortletKeys.TEMPLATEMAILER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class TemplateMailerPortlet extends MVCPortlet {


	@Override
	public void render(RenderRequest renderRequest,
					   RenderResponse renderResponse) throws PortletException, IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		SearchContext searchContext = SearchContextFactory.getInstance(
				new long[0], new String[0], new HashMap<>(), themeDisplay.getCompanyId(),
				null, null, null, themeDisplay.getScopeGroupId(), null,
				themeDisplay.getUser().getUserId());
		searchContext.setStart(-1);
		searchContext.setEnd(-1);
		SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine(SearchEngineHelperUtil.getDefaultSearchEngineId());
		IndexSearcher searcher = searchEngine.getIndexSearcher();
		Hits hits = null;
		try {
			hits = searcher.search(searchContext,getSearchQuery());
		} catch (SearchException e) {
			e.printStackTrace();
		}


		renderRequest.setAttribute("assetentries", _assetHelper.getAssetEntries(hits));

		super.render(renderRequest, renderResponse);
	}

	private Query getSearchQuery() {
		BooleanQuery searchQuery = new BooleanQueryImpl();
		searchQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, JournalArticle.class.getName());
		searchQuery.addRequiredTerm("head", Boolean.TRUE);
		//TODO configurable folderId
		searchQuery.addRequiredTerm(Field.FOLDER_ID, 39455);

		return searchQuery;
	}

	@Reference
	private JournalArticleLocalService _JournalArticleLocalService;

	@Reference
	private AssetHelper _assetHelper;
}