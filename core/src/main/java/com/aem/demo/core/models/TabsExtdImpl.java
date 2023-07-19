package com.aem.demo.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.commons.link.Link;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.adobe.cq.wcm.core.components.models.Tabs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, adapters = {TabsExtd.class, ComponentExporter.class},
        resourceType = TabsExtdImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class TabsExtdImpl implements TabsExtd {
    public static final String RESOURCE_TYPE = "aem-demo/components/tabs";

    @Self
    @Delegate
    private Tabs coreTabs;

    @Override
    public List<CustomListItem> getExtItems() {
        List<CustomListItem> customListItems = new ArrayList<>();
        for (ListItem item : coreTabs.getItems()) {
            CustomListItemImpl extItem = new CustomListItemImpl();
            extItem.setId(item.getTitle().toLowerCase().replace(" ", "-"));
            extItem.setLink(item.getLink());
            extItem.setURL(item.getURL());
            extItem.setTitle(item.getTitle());
            extItem.setName(item.getName());
            extItem.setDescription(item.getDescription());
            extItem.setPath(item.getPath());
            extItem.setTeaserResource(item.getTeaserResource());
            customListItems.add(extItem);
        }
        return customListItems;
    }

    interface CustomListItem extends ListItem {

    }
    @Getter
    @Setter
    private class CustomListItemImpl implements CustomListItem {
        private String id;
        private Link link;
        private String URL;
        private String Title;
        private String description;
        private Calendar lastModified;
        private String path;
        private String name;
        private Resource teaserResource;
    }
}
