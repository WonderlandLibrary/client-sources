/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.imfr0zen.guiapi.Frame;
import me.imfr0zen.guiapi.GuiFrame;
import me.imfr0zen.guiapi.components.Button;
import me.imfr0zen.guiapi.components.GuiComponent;
import me.imfr0zen.guiapi.components.GuiSlider;
import me.imfr0zen.guiapi.components.GuiToggleButton;
import me.imfr0zen.guiapi.listeners.ClickListener;
import me.imfr0zen.guiapi.listeners.ExtendListener;
import me.imfr0zen.guiapi.listeners.ValueListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.module.ModuleManager;
import pw.vertexcode.nemphis.modules.Killaura;
import pw.vertexcode.nemphis.value.Value;
import pw.vertexcode.util.module.types.ToggleableModule;

public class ClickGui
extends GuiScreen {
    public static final FontRenderer FONTRENDERER = Minecraft.getMinecraft().fontRendererObj;
    public static int currentId = 0;
    private ArrayList<Frame> frames = new ArrayList();

    public ClickGui() {
        currentId = 0;
        this.addCategorys();
    }

    public void addCategorys() {
        int i = 0;
        while (i < Category.values().length) {
            GuiFrame frame = new GuiFrame(Category.values()[i].name(), i == 0 ? i : i * 172, 2);
            int j = 0;
            while (j < Nemphis.instance.modulemanager.getMods().size()) {
                final ToggleableModule module = Nemphis.instance.modulemanager.getMods().get(j);
                Button button = new Button(module.getName());
                button.addClickListener(new ClickListener(module, button));
                button.addExtendListener(new ExtendListener(){

                    @Override
                    public void addComponents() {
                        if (module instanceof Killaura) {
                            GuiSlider s1 = new GuiSlider("Reach", 2.0f, 10.0f, ((Float)module.getValue("reach").getValue()).floatValue());
                            GuiSlider s2 = new GuiSlider("Speed", 2.0f, 10.0f, ((Float)module.getValue("speed").getValue()).floatValue());
                            s1.addValueListener(new ValueListener(module){
                                private final /* synthetic */ ToggleableModule val$module;

                                @Override
                                public void valueUpdated(float value) {
                                    this.val$module.getValue("reach").setValue(Float.valueOf(value));
                                }

                                @Override
                                public void valueChanged(float value) {
                                    this.val$module.getValue("reach").setValue(Float.valueOf(value));
                                }
                            });
                            s2.addValueListener(new ValueListener(module){
                                private final /* synthetic */ ToggleableModule val$module;

                                @Override
                                public void valueUpdated(float value) {
                                    this.val$module.getValue("speed").setValue(Float.valueOf(value));
                                }

                                @Override
                                public void valueChanged(float value) {
                                    this.val$module.getValue("speed").setValue(Float.valueOf(value));
                                }
                            });
                            GuiToggleButton b1 = new GuiToggleButton("Autoblock");
                            GuiToggleButton b2 = new GuiToggleButton("Players");
                            GuiToggleButton b3 = new GuiToggleButton("Animals");
                            GuiToggleButton b4 = new GuiToggleButton("Mobs");
                            GuiToggleButton b5 = new GuiToggleButton("Teams");
                            GuiToggleButton b6 = new GuiToggleButton("AACBots");
                            GuiToggleButton b7 = new GuiToggleButton("GACBots");
                            GuiToggleButton b8 = new GuiToggleButton("SpartanBots");
                            b1.addClickListener(new ActionListener(module, b1){
                                private final /* synthetic */ ToggleableModule val$module;
                                private final /* synthetic */ GuiToggleButton val$b1;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    this.val$module.getValue("autoblock").setValue(this.val$b1.isToggled());
                                }
                            });
                            b2.addClickListener(new ActionListener(module, b2){
                                private final /* synthetic */ ToggleableModule val$module;
                                private final /* synthetic */ GuiToggleButton val$b2;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    this.val$module.getValue("players").setValue(this.val$b2.isToggled());
                                }
                            });
                            b3.addClickListener(new ActionListener(module, b3){
                                private final /* synthetic */ ToggleableModule val$module;
                                private final /* synthetic */ GuiToggleButton val$b3;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    this.val$module.getValue("animals").setValue(this.val$b3.isToggled());
                                }
                            });
                            b4.addClickListener(new ActionListener(module, b4){
                                private final /* synthetic */ ToggleableModule val$module;
                                private final /* synthetic */ GuiToggleButton val$b4;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    this.val$module.getValue("mobs").setValue(this.val$b4.isToggled());
                                }
                            });
                            b5.addClickListener(new ActionListener(module, b5){
                                private final /* synthetic */ ToggleableModule val$module;
                                private final /* synthetic */ GuiToggleButton val$b5;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    this.val$module.getValue("teams").setValue(this.val$b5.isToggled());
                                }
                            });
                            b6.addClickListener(new ActionListener(module, b6){
                                private final /* synthetic */ ToggleableModule val$module;
                                private final /* synthetic */ GuiToggleButton val$b6;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    this.val$module.getValue("aacBots").setValue(this.val$b6.isToggled());
                                }
                            });
                            b7.addClickListener(new ActionListener(module, b7){
                                private final /* synthetic */ ToggleableModule val$module;
                                private final /* synthetic */ GuiToggleButton val$b7;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    this.val$module.getValue("gommeBots").setValue(this.val$b7.isToggled());
                                }
                            });
                            b8.addClickListener(new ActionListener(module, b8){
                                private final /* synthetic */ ToggleableModule val$module;
                                private final /* synthetic */ GuiToggleButton val$b8;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    this.val$module.getValue("spartanBots").setValue(this.val$b8.isToggled());
                                }
                            });
                            b1.setToggled((Boolean)module.getValue("autoblock").getValue());
                            b2.setToggled((Boolean)module.getValue("players").getValue());
                            b3.setToggled((Boolean)module.getValue("animals").getValue());
                            b4.setToggled((Boolean)module.getValue("mobs").getValue());
                            b5.setToggled((Boolean)module.getValue("teams").getValue());
                            b6.setToggled((Boolean)module.getValue("aacBots").getValue());
                            b7.setToggled((Boolean)module.getValue("gommeBots").getValue());
                            b8.setToggled((Boolean)module.getValue("spartanBots").getValue());
                            this.add(s1);
                            this.add(s2);
                            this.add(b1);
                            this.add(b2);
                            this.add(b3);
                            this.add(b4);
                            this.add(b5);
                            this.add(b6);
                            this.add(b7);
                            this.add(b8);
                        }
                    }

                });
                if (module.getCategory() == Category.COMBAT && Category.values()[i] == Category.COMBAT) {
                    frame.addButton(button);
                }
                if (module.getCategory() == Category.EXPLOITS && Category.values()[i] == Category.EXPLOITS) {
                    frame.addButton(button);
                }
                if (module.getCategory() == Category.WORLD && Category.values()[i] == Category.WORLD) {
                    frame.addButton(button);
                }
                if (module.getCategory() == Category.PLAYER && Category.values()[i] == Category.PLAYER) {
                    frame.addButton(button);
                }
                if (module.getCategory() == Category.MOVEMENT && Category.values()[i] == Category.MOVEMENT) {
                    frame.addButton(button);
                }
                if (module.getCategory() == Category.RENDER && Category.values()[i] == Category.RENDER) {
                    frame.addButton(button);
                }
                ++j;
            }
            this.addFrame(frame);
            ++i;
        }
    }

    protected void addFrame(Frame frame) {
        if (!this.frames.contains(frame)) {
            this.frames.add(frame);
        }
    }

    @Override
    public void initGui() {
        for (Frame frame : this.frames) {
            frame.init();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Frame frame : this.frames) {
            frame.render(mouseX, mouseY);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame frame : this.frames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (Frame frame : this.frames) {
            frame.keyTyped(keyCode, typedChar);
        }
    }

}

