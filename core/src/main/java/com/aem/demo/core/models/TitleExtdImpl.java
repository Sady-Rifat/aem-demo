package com.aem.demo.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Title;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import javax.annotation.PostConstruct;

@Slf4j
@Model(adaptables = SlingHttpServletRequest.class, adapters = {TitleExtd.class, ComponentExporter.class},
        resourceType = TitleExtdImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class TitleExtdImpl implements TitleExtd {
    public static final String RESOURCE_TYPE = "aem-demo/components/title";

    @Delegate(types = com.adobe.cq.wcm.core.components.models.Title.class)
    @Self
    private com.adobe.cq.wcm.core.components.models.Title delegate;

    @PostConstruct
    protected void init() {
        log.info("Start Extended Title Model");
    }
}
