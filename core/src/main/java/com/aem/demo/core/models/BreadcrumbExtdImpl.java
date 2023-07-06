package com.aem.demo.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import com.adobe.cq.wcm.core.components.models.NavigationItem;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import java.util.Collection;

@Model(adaptables = SlingHttpServletRequest.class, adapters = {BreadcrumbExtd.class, ComponentExporter.class},
        resourceType = BreadcrumbExtdImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class BreadcrumbExtdImpl implements BreadcrumbExtd {
    public static final String RESOURCE_TYPE = "bs-commons/components/breadcrumb/v1/breadcrumb";
    private final String breadcrumbMarkup = "{\n" +
            "      \"@context\": \"https://schema.org\",\n" +
            "      \"@type\": \"BreadcrumbList\",\n" +
            "      \"itemListElement\": []\n" +
            "    }";
    @Self
    private Breadcrumb coreBreadcrumb;

    @Override
    public String getSchemaJsonLD() {
        return generateSchemaLD();
    }

    @Override
    public Collection<NavigationItem> getItems() {
        return this.coreBreadcrumb.getItems();
    }

    @Override
    public String getId() {
        return this.coreBreadcrumb.getId();
    }

    @Override
    public ComponentData getData() {
        return this.coreBreadcrumb.getData();
    }

    protected String generateSchemaLD() {
        Gson gson = new Gson();
        JsonObject breadcrumb = gson.fromJson(breadcrumbMarkup, JsonObject.class);
        JsonArray breadcrumbItemListElement = new JsonArray();

        int position = 1;
        for (NavigationItem navigationItem : coreBreadcrumb.getItems()) {
            JsonObject breadcrumbItem = new JsonObject();
            breadcrumbItem.addProperty("@type", "ListItem");
            breadcrumbItem.addProperty("position", position++);
            breadcrumbItem.addProperty("name", navigationItem.getTitle());
            breadcrumbItem.addProperty("item", navigationItem.getLink().getMappedURL());
            breadcrumbItemListElement.add(breadcrumbItem);
        }
        breadcrumb.add("itemListElement", breadcrumbItemListElement);
        return gson.toJson(breadcrumb);
    }
}
