package templatemailer.portlet.configuration.definition;

import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import templatemailer.portlet.configuration.TemplateMailerPortletConfiguration;

public class TemplateMailerPortletConfigurationBeanDeclaration implements ConfigurationBeanDeclaration {
    @Override
    public Class<?> getConfigurationBeanClass()
    {
        return TemplateMailerPortletConfiguration.class;
    }
}
