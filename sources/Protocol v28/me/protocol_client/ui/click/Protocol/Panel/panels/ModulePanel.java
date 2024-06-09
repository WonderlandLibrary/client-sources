package me.protocol_client.ui.click.Protocol.Panel.panels;

import java.util.ArrayList;

import me.protocol_client.module.Category;
import me.protocol_client.ui.click.Protocol.Panel.Panel;

public class ModulePanel extends Panel {
    public ModulePanel(Category type, float x, float y) {
        super(type.name(), new ArrayList<>(), x, y, 20, 10);
        addModuleElements(type);
    }
}
