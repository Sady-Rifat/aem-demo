package com.aem.demo.core.servlets;

import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;

@Slf4j
@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Alive txt",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/public/aem-demo/alive",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=txt"
        })
public class AliveServletDemo extends SlingAllMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        throw new RuntimeException("Hello Runtime");
    }
}
