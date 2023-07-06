package com.aem.demo.core.models;

import com.aem.demo.core.testcontext.AppAemContext;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class BreadcrumbExtdImplTest {
    private final AemContext context = AppAemContext.newAemContext();
    private BreadcrumbExtd breadcrumbExtd;

    @BeforeEach
    void init() {
        Resource pageResource = context.load().json("/com/aem/demo/core/models/page.json", "/content/uk");
        Page page = pageResource.getChild("en/product").adaptTo(Page.class);

        Map<String, Object> jsonObject = TestUtils.getJsonObject("/com/aem/demo/core/models/breadcrumb.json");
        context.currentResource(context.create().resource(page, "extendedBreadcrumb", jsonObject));
        breadcrumbExtd = context.request().adaptTo(BreadcrumbExtd.class);
    }

    @Test
    void idTest() {
        assertNotNull(breadcrumbExtd.getId());
    }

    @Test
    void schemaJsonLDTest() {
        assertNotNull(breadcrumbExtd.getSchemaJsonLD());
    }

    @Test
    void componentDataTest() {
        TestUtils.enableDataLayer(context, true);
        assertNotNull(breadcrumbExtd.getData());
    }

    @Test
    void navItemTest() {
        assertNotNull(breadcrumbExtd.getItems());
    }
}
