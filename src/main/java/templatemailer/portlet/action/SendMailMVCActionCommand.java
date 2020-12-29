package templatemailer.portlet.action;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.petra.content.ContentUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import templatemailer.constants.TemplateMailerPortletKeys;

import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + TemplateMailerPortletKeys.TEMPLATEMAILER,
                "mvc.command.name=/sendmail"
        },
        service = MVCActionCommand.class
)
public class SendMailMVCActionCommand implements MVCActionCommand {

    @Override
    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
        //LiferayPortletRequest portletRequest = PortalUtil.getLiferayPortletRequest(actionRequest);

        // from
        InternetAddress from = new InternetAddress();
        from.setAddress("admin@liferay.com");

        // to
        InternetAddress to = new InternetAddress();
        to.setAddress(actionRequest.getActionParameters().getValue("to"));

        MailMessage mailMessage = new MailMessage(from,to,actionRequest.getActionParameters().getValue("subject"),actionRequest.getActionParameters().getValue("body").toString(),Boolean.TRUE);
        _mailService.sendEmail(mailMessage);

        System.out.println("Mail send");

        return true;
    }

    @Reference
    private MailService _mailService;
}
