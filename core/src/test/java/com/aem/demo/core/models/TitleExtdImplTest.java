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
class TitleExtdImplTest {
    private final AemContext context = AppAemContext.newAemContext();
    private TitleExtd titleExtd;

//    @BeforeEach
//    void init() {
//        Resource pageResource = context.load().json("/com/aem/demo/core/models/page.json", "/content/uk");
//        Page page = pageResource.getChild("en/product").adaptTo(Page.class);
//
//        Map<String, Object> jsonObject = TestUtils.getJsonObject("/com/aem/demo/core/models/title.json");
//        context.currentResource(context.create().resource(page, "extendedTitle", jsonObject));
//        titleExtd = context.request().adaptTo(TitleExtd.class);
//    }
//
//    @Test
//    void idTest() {
//        assertNotNull(titleExtd.getId());
//    }

}
