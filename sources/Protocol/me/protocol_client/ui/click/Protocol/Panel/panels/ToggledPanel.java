package me.protocol_client.ui.click.Protocol.Panel.panels;

import java.util.ArrayList;

import me.protocol_client.Protocol;
import me.protocol_client.module.Module;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ModStuff;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ThemeStuff;
import me.protocol_client.ui.click.Protocol.Panel.Panel;
import me.protocol_client.ui.click.Protocol.theme.ClickTheme;

public class ToggledPanel extends Panel {
    public ToggledPanel(float x, float y) {
        super("Enabled Mods", new ArrayList<>(), x, y, 20, 10);

        float elementY = 4;
        for (Module mod : Protocol.getModules()) {
        	if(mod.isToggled()){
        		if(!getElements().contains(new ModStuff(mod, x + 2, elementY + 2, Protocol.getGuiClick().getTheme().getElementWidth(), Protocol.getGuiClick().getTheme().getElementHeight()))){
            getElements().add(new ModStuff(mod, x + 2, elementY + 2, Protocol.getGuiClick().getTheme().getElementWidth(), Protocol.getGuiClick().getTheme().getElementHeight()));
        		}
            elementY += Protocol.getGuiClick().getTheme().getElementHeight() + 1;
        }else{
        	if(getElements().contains(new ModStuff(mod, x + 2, elementY + 2, Protocol.getGuiClick().getTheme().getElementWidth(), Protocol.getGuiClick().getTheme().getElementHeight()))){
        	getElements().remove(new ModStuff(mod, x + 2, elementY + 2, Protocol.getGuiClick().getTheme().getElementWidth(), Protocol.getGuiClick().getTheme().getElementHeight()));
        	}
        }
        }
    }
}
