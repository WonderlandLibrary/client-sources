package me.vaziak.sensation.client.api.gui.ingame.clickui.tab;

import java.awt.*;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.gui.ingame.clickui.Interface;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.base.BaseTextEntry;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.simple.SimpleTextButton;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.configuration.ContainerConfigurations;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.configuration.TextEntryConfigurations;
import me.vaziak.sensation.client.api.gui.ingame.clickui.configuration.Configuration;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;

/**
 * @author antja03
 */
public class TabConfiguration extends Tab {

    private ContainerConfigurations configContainer;
    private BaseTextEntry configNameEntry;

    private Configuration selectedConfiguration;

    public TabConfiguration(Interface theInterface) {
        super(theInterface);
        this.components.add(configContainer = new ContainerConfigurations(theInterface, 25, 0, 175, 150));

        this.components.add(configNameEntry = new TextEntryConfigurations(theInterface, Fonts.f18, 207, 5, 110, 10));

        this.components.add(new SimpleTextButton(theInterface, new Color(40, 40, 40), new Color(20, 20, 20), "Create", 12, new Color(200, 200, 200), 220, 20, 85, 10, button -> {
            if (!configNameEntry.getContent().isEmpty()) {
                if (Sensation.instance.configurationManager.searchByIdentifier(configNameEntry.getContent()) == null) {
                    Configuration configuration = new Configuration(configNameEntry.getContent());
                    configuration.save();
                    Sensation.instance.configurationManager.add(configuration);
                      configContainer.refresh();
                }
            }
        }));

        this.components.add(new SimpleTextButton(theInterface, new Color(40, 40, 40), new Color(20, 20, 20), "Overwrite", 12, new Color(200, 200, 200), 220, 50, 85, 10, button -> {
            if (configContainer.getSelectedConfig() != null) {
                configContainer.getSelectedConfig().save();
             }
        }));

        this.components.add(new SimpleTextButton(theInterface, new Color(40, 40, 40), new Color(20, 20, 20), "Load", 12, new Color(200, 200, 200), 220, 65, 85, 10, button -> {
            if (configContainer.getSelectedConfig() != null) {
                configContainer.getSelectedConfig().load();
             }
        }));

        this.components.add(new SimpleTextButton(theInterface, new Color(40, 40, 40), new Color(20, 20, 20), "Make default", 12, new Color(200, 200, 200), 220, 80, 85, 10, button -> {
            if (configContainer.getSelectedConfig() != null) {
                Sensation.instance.configurationManager.getConfigurationList().forEach(config -> config.setDefault(false));
                configContainer.getSelectedConfig().setDefault(true);
                Sensation.instance.configurationManager.saveConfigurationFiles();
            }
        }));

        this.components.add(new SimpleTextButton(theInterface, new Color(40, 40, 40), new Color(20, 20, 20), "Delete", 12, new Color(200, 200, 200), 220, 95, 85, 10, button -> {
            if (configContainer.getSelectedConfig() != null) {
                Sensation.instance.configurationManager.delete(configContainer.getSelectedConfig());
                 configContainer.refresh();
            }
        }));

        this.components.add(new SimpleTextButton(theInterface, new Color(40, 40, 40), new Color(20, 20, 20), "Refresh", 12, new Color(200, 200, 200), 220, 110, 85, 10, button -> {
            Sensation.instance.configurationManager.loadConfigurationFiles(true);
            configContainer.refresh();
        }));
    }

    @Override
    public void onTick() {
        getInterface().setWidth(300);
        super.onTick();
    }

}
