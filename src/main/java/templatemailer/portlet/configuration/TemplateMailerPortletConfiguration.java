package templatemailer.portlet.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = "sample",
        scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
        id = "templatemailer.portlet.configuration.TemplateMailerPortletConfiguration"
)
public interface TemplateMailerPortletConfiguration {
    @Meta.AD(deflt = "0", required = false)
    long folderid();
}
