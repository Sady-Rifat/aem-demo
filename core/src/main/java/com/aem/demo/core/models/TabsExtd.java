package com.aem.demo.core.models;

import com.adobe.cq.wcm.core.components.models.Tabs;

import java.util.List;

public interface TabsExtd extends Tabs {
    List<TabsExtdImpl.CustomListItem> getExtItems();
}
