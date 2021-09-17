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
import java.util.Enumeration;
import java.util.Set;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + TemplateMailerPortletKeys.TEMPLATEMAILER,
                "mvc.command.name=/preview"
        },
        service = MVCRenderCommand.class
)
public class PreviewMVCRenderCommand implements MVCRenderCommand {

    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {

        //LiferayPortletRequest portletRequest = PortalUtil.getLiferayPortletRequest(renderRequest);
        Long  classPK = Long.valueOf(renderRequest.getRenderParameters().getValue("classPK"));
        try {
            JournalArticle article = _JournalArticleLocalService.getLatestArticle(classPK);
            Fields fields = _journalConverter.getDDMFields(article.getDDMStructure(), article.getContent());
            String body = (String) fields.get("Body").getValue();
            String subject = (String) fields.get("Subject").getValue();

            Set<String> params = renderRequest.getRenderParameters().getNames();
            for (String param:params) {
                body = body.replaceAll("\\$\\{" + param + "\\}", renderRequest.getRenderParameters().getValue(param));
                subject = subject.replaceAll("\\$\\{" + param + "\\}", renderRequest.getRenderParameters().getValue(param));
            }

            renderRequest.setAttribute("to",renderRequest.getRenderParameters().getValue("to"));
            renderRequest.setAttribute("subject",subject);
            renderRequest.setAttribute("body",body);

        } catch (PortalException e) {
            e.printStackTrace();
        }

        return "/preview.jsp";
    }

    @Reference
    private JournalArticleLocalService _JournalArticleLocalService;
    @Reference
    private JournalConverter _journalConverter;
}
