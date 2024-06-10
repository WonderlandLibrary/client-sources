// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.clickgui;

import org.lwjgl.opengl.GL11;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.clickgui.element.elements.ElementModuleButton;
import me.kaktuswasser.client.module.Module;

import java.util.Iterator;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen
{
    protected final List<Panel> panels;
    
    public List<Panel> getPanels() {
        return this.panels;
    }
    
    public ClickGUI() {
        this.panels = new CopyOnWriteArrayList<Panel>();
        this.width = 100;
        this.height = 18;
        this.panels.add(new Panel(Module.Category.PLAYER, 120, 20, this.width, this.height, false, false) {
            @Override
            public void setupItems() {
                for (final Module module : Client.getModuleManager().getModules()) {
                    if (module.getCategory() == Module.Category.PLAYER) {
                        this.getElements().add(new ElementModuleButton(module, Module.Category.PLAYER));
                    }
                }
            }
        });
        this.panels.add(new Panel(Module.Category.MOVEMENT, 120, 60, this.width, this.height, false, false) {
            @Override
            public void setupItems() {
                for (final Module module : Client.getModuleManager().getModules()) {
                    if (module.getCategory() == Module.Category.MOVEMENT) {
                        this.getElements().add(new ElementModuleButton(module, Module.Category.MOVEMENT));
                    }
                }
            }
        });
        this.panels.add(new Panel(Module.Category.COMBAT, 120, 100, this.width, this.height, false, false) {
            @Override
            public void setupItems() {
                for (final Module module : Client.getModuleManager().getModules()) {
                    if (module.getCategory() == Module.Category.COMBAT) {
                        this.getElements().add(new ElementModuleButton(module, Module.Category.COMBAT));
                    }
                }
            }
        });
        this.panels.add(new Panel(Module.Category.RENDER, 120, 140, this.width, this.height, false, false) {
            @Override
            public void setupItems() {
                for (final Module module : Client.getModuleManager().getModules()) {
                    if (module.getCategory() == Module.Category.RENDER) {
                        this.getElements().add(new ElementModuleButton(module, Module.Category.RENDER));
                    }
                }
            }
        });
        this.panels.add(new Panel(Module.Category.WORLD, 120, 180, this.width, this.height, false, false) {
            @Override
            public void setupItems() {
                for (final Module module : Client.getModuleManager().getModules()) {
                    if (module.getCategory() == Module.Category.WORLD) {
                        this.getElements().add(new ElementModuleButton(module, Module.Category.WORLD));
                    }
                }
            }
        });
        this.panels.add(new Panel(Module.Category.EXPLOITS, 120, 220, this.width, this.height, false, false) {
            @Override
            public void setupItems() {
                for (final Module module : Client.getModuleManager().getModules()) {
                    if (module.getCategory() == Module.Category.EXPLOITS) {
                        this.getElements().add(new ElementModuleButton(module, Module.Category.EXPLOITS));
                    }
                }
            }
        });
        this.panels.add(new Panel(Module.Category.MISC, 120, 260, this.width, this.height, false, false) {
            @Override
            public void setupItems() {
                for (final Module module : Client.getModuleManager().getModules()) {
                    if (module.getCategory() == Module.Category.MISC) {
                        this.getElements().add(new ElementModuleButton(module, Module.Category.MISC));
                    }
                }
            }
        });
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float button) {
        GL11.glPushAttrib(1048575);
        for (final Panel panel : this.panels) {
            panel.drawScreen(mouseX, mouseY, button);
        }
        GL11.glPopAttrib();
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Panel panel : this.panels) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Panel panel : this.panels) {
            panel.mouseReleased(mouseX, mouseY, state);
        }
    }
    
    @Override
    public void onGuiClosed() {
        if (Client.getFileManager().getFileByName("guiconfiguration") != null) {
            Client.getFileManager().getFileByName("guiconfiguration").saveFile();
        }
    }
    
    public Panel getPanelByTitle(final String panelTitle) {
        for (final Panel panel : this.panels) {
            if (panel.getTitle().equalsIgnoreCase(panelTitle)) {
                return panel;
            }
        }
        return null;
    }
}
