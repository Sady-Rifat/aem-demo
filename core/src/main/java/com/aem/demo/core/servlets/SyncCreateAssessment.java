package com.aem.demo.core.servlets;

import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceSettings;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=CA Config Filter Servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/public/aem-demo/google-captcha"
        })
public class SyncCreateAssessment extends SlingAllMethodsServlet {
    RecaptchaEnterpriseServiceSettings.Builder recaptchaEnterpriseServiceSettingsBuilder = RecaptchaEnterpriseServiceSettings.newBuilder();

}
