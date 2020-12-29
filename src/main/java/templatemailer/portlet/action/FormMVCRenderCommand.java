package templatemailer.portlet.action;

import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import templatemailer.constants.TemplateMailerPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + TemplateMailerPortletKeys.TEMPLATEMAILER,
                "mvc.command.name=/form"
        },
        service = MVCRenderCommand.class
)
public class FormMVCRenderCommand implements MVCRenderCommand {

    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {

        LiferayPortletRequest portletRequest = PortalUtil.getLiferayPortletRequest(renderRequest);
        Long  classPK = Long.valueOf(portletRequest.getParameter("classPK"));
        try {
            JournalArticle article = _JournalArticleLocalService.getLatestArticle(classPK);
            Fields fields = _journalConverter.getDDMFields(article.getDDMStructure(), article.getContent());
            String body = (String) fields.get("Body").getValue();

            // time for some regex ninja.
            Pattern pattern = Pattern.compile("\\$\\{.*?\\}");
            Matcher matcher = pattern.matcher(body);
            ArrayList fieldnames = new ArrayList();
            while (matcher.find()) {
                String field = matcher.group().replaceAll("[\\$,\\{,\\}]","");
                if (!fieldnames.contains(field))
                    fieldnames.add(field);
            }
            renderRequest.setAttribute("fieldnames",fieldnames);
            renderRequest.setAttribute("classPK",classPK);
        } catch (PortalException e) {
            e.printStackTrace();
        }

        return "/form.jsp";
    }

    @Reference
    private JournalArticleLocalService _JournalArticleLocalService;
    @Reference
    private JournalConverter _journalConverter;
}
