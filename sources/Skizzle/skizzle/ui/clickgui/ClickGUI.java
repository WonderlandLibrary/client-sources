/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package skizzle.ui.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventKey;
import skizzle.events.listeners.EventMouseClick;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.newFont.FontUtil;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.settings.BooleanSetting;
import skizzle.settings.DescriptionSetting;
import skizzle.settings.KeybindSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.settings.Setting;
import skizzle.ui.elements.Textbox;
import skizzle.util.AnimationHelper;
import skizzle.util.Colors;
import skizzle.util.ColourUtil;
import skizzle.util.RenderUtil;

public class ClickGUI
extends Module {
    public int animX;
    public AnimationHelper anim;
    public int lastMsButton = 0;
    public boolean isBinding = false;
    public int animY;
    public BooleanSetting snapSlider = new BooleanSetting(Qprot0.0("\uf673\u71c5\ucd35\ua7f4\ud9d7\u73c9\u8c23\u9a04\u5706\u72d8\uf9c5"), false);
    public static ArrayList<Module> searches;
    public int mouseX;
    public BooleanSetting bar;
    public BooleanSetting drag;
    public ModeSetting animations;
    public ModeSetting searchMode;
    public boolean isMovingCat = false;
    public int startMoveX;
    public static HashMap<String, ArrayList<Module>> search;
    public int mouseY;
    public int lastMouseY;
    public NumberSetting maxModule;
    public Textbox searchBox;
    public ModeSetting modeSetting;
    public int mouseHold = -1;
    public int mouseScroll = 0;
    public int startMoveY;
    public String searchFilter = "";
    public ModeSetting sidebars;
    public int lastMouseX;
    public BooleanSetting keepMouse = new BooleanSetting(Qprot0.0("\uf66b\u71ce\ucd31\ua7f4\ud9d7\u73d9\u8c3a\u9a1f\u5711\u72d2\uf9c5"), true);

    @Override
    public void onDisable() {
        ClickGUI Nigga;
        Nigga.animX = 0;
        Nigga.animY = 0;
        Nigga.mc.setIngameFocus();
        for (Module.Category Nigga2 : Module.Category.values()) {
            if (!Nigga2.clickGuiExpand) continue;
            Nigga2.outAnim.stage = 0.0;
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onEvent(Event Nigga) {
        Iterator<String> iterator;
        Object Nigga2;
        ClickGUI Nigga7;
        Nigga7.mc.setIngameNotInFocus();
        Nigga7.lastMouseX = Nigga7.mouseX;
        Nigga7.lastMouseY = Nigga7.mouseY;
        if (Nigga instanceof EventKey) {
            EventKey Nigga6 = (EventKey)Nigga;
            Keyboard.getKeyName((int)Nigga6.getCode()).toLowerCase();
            if (Nigga6.getCode() == 1) {
                Nigga6.setCancelled(true);
                Nigga7.toggle();
            }
            if (Nigga7.isBinding) {
                for (Module module : Client.modules) {
                    for (Setting setting : module.settings) {
                        if (!(setting instanceof KeybindSetting)) continue;
                        KeybindSetting Nigga10 = (KeybindSetting)setting;
                        int Nigga5 = Nigga6.getCode();
                        if (Nigga5 != 28 && Nigga5 != 200 && Nigga5 != 208 && Nigga5 != 205 && Nigga5 != 203 && Nigga5 != 1 && Nigga5 != 14 && Nigga10.cgSelected && Nigga7.isBinding) {
                            Nigga10.setKeyCode(Nigga6.getCode());
                            Nigga10.cgSelected = false;
                            Nigga7.isBinding = false;
                        }
                        if ((Nigga5 == 1 || Nigga5 == 14) && Nigga10.cgSelected && Nigga7.isBinding) {
                            Nigga10.setKeyCode(0);
                            Nigga10.cgSelected = false;
                            Nigga7.isBinding = false;
                        }
                        Nigga6.setCancelled(true);
                    }
                }
            } else {
                Nigga7.searchBox.onKeyPress(Nigga6.getCode());
                Nigga6.setCancelled(true);
                String string = Nigga7.searchBox.storedText;
                searches.clear();
                if (Nigga7.searchMode.getMode().equals(Qprot0.0("\uf64e\u71cc\ucd26\ue26c\u6203"))) {
                    void var5_19;
                    ArrayList<String> arrayList = new ArrayList<String>();
                    boolean bl = false;
                    while (++var5_19 < string.length() - 1) {
                        String Nigga13 = "";
                        int Nigga14 = var5_19 + 2;
                        if (Nigga14 > string.length()) {
                            Nigga14 = string.length();
                        }
                        Nigga13 = string.substring((int)var5_19, Nigga14).toLowerCase();
                        arrayList.add(Nigga13);
                    }
                    for (String string2 : arrayList) {
                        if (search.get(string2) == null) continue;
                        for (Module Nigga16 : search.get(string2)) {
                            if (searches.contains(Nigga16)) continue;
                            searches.add(Nigga16);
                        }
                    }
                } else if (Nigga7.searchMode.getMode().equals(Qprot0.0("\uf643\u71c4\ucd3a\ue279\u620f\u73f3\u8c21\u9a1e"))) {
                    for (Module module : Client.modules) {
                        if (!module.name.toLowerCase().contains(string)) continue;
                        searches.add(module);
                    }
                }
            }
        }
        if (Nigga instanceof EventRenderGUI) {
            void var5_24;
            Nigga7.searchBox.draw(Nigga7.mouseX, Nigga7.mouseY);
            GL11.glColor4f((float)Float.intBitsToFloat(1.08691136E9f ^ 0x7F48F3BF), (float)Float.intBitsToFloat(1.08788378E9f ^ 0x7F57C9E8), (float)Float.intBitsToFloat(1.08216179E9f ^ 0x7F007AA0), (float)Float.intBitsToFloat(1.0968567E9f ^ 0x7EE0B459));
            Nigga2 = FontUtil.cleankindalarge;
            boolean bl = true;
            Module.Category[] arrcategory = Module.Category.values();
            int n = arrcategory.length;
            boolean bl2 = false;
            while (++var5_24 < n) {
                double Nigga3;
                void var3_14;
                Module.Category category = arrcategory[var5_24];
                if (category.cgY == -100) {
                    category.cgY = var3_14 * 21;
                }
                int Nigga4 = 1;
                int Nigga42 = 0;
                if (Nigga7.hovering(category.cgX, category.cgY, 80 + category.cgX, 20 + category.cgY) && !category.isMoving && !Nigga7.isMovingCat && Nigga7.mouseHold == 0) {
                    category.isMoving = true;
                    Nigga7.isMovingCat = true;
                    Nigga7.startMoveX = category.cgX - Nigga7.mouseX;
                    Nigga7.startMoveY = category.cgY - Nigga7.mouseY;
                }
                if (Nigga7.mouseHold == -1 && category.isMoving) {
                    Client.fileManager.updateClickGUISettings();
                    category.isMoving = false;
                    Nigga7.isMovingCat = false;
                }
                if (category.isMoving) {
                    int Nigga20 = Nigga7.mouseX + Nigga7.startMoveX;
                    int Nigga32 = Nigga7.mouseY + Nigga7.startMoveY;
                    if (Nigga32 >= 0 && Nigga32 <= Nigga7.mc.displayHeight / 2 - 20) {
                        category.cgY = Nigga32;
                    }
                    if (Nigga20 >= 0 && Nigga20 <= Nigga7.mc.displayWidth / 2 - 80) {
                        category.cgX = Nigga20;
                    }
                }
                double Nigga21 = category.outAnim.getDelay();
                boolean Nigga22 = category.outAnim.hasTimeElapsed((long)-83959743 ^ 0xFFFFFFFFFAFEE040L, true);
                if (Nigga7.drag.isEnabled() && Nigga22) {
                    double Nigga18;
                    category.rotation = -(category.lastX - category.cgX);
                    double Nigga19 = 2.0;
                    if (category.rotation - category.lastRotation > Nigga19) {
                        category.rotation = category.lastRotation + Nigga19;
                    }
                    if (category.rotation - category.lastRotation < -Nigga19) {
                        category.rotation = category.lastRotation - Nigga19;
                    }
                    if (category.rotation > (Nigga18 = 35.0)) {
                        category.rotation = Nigga18;
                    }
                    if (category.rotation < -Nigga18) {
                        category.rotation = -Nigga18;
                    }
                    category.lastRotation = category.rotation;
                    category.lastX = category.cgX;
                    category.lastY = category.cgY;
                } else {
                    category.rotation = 0.0;
                }
                GL11.glEnable((int)2848);
                GL11.glTranslated((double)((double)category.cgX + 42.5), (double)category.cgY, (double)0.0);
                GL11.glRotated((double)category.rotation, (double)0.0, (double)0.0, (double)1.0);
                GL11.glTranslated((double)(-((double)category.cgX + 42.5)), (double)(-category.cgY), (double)0.0);
                double Nigga5 = 0.0;
                if (category.clickGuiExpand) {
                    double Nigga222;
                    double Nigga23;
                    double Nigga24;
                    Nigga21 /= 5.0;
                    if ((Nigga21 += 1.0) < 1.0) {
                        Nigga21 = 1.0;
                    }
                    Nigga3 = 2.0;
                    for (Module Nigga25 : Client.modules) {
                        if (!Nigga25.category.equals((Object)category)) continue;
                        if (Nigga25.cgExpanded) {
                            for (Setting Nigga26 : Nigga25.settings) {
                                Nigga3 += 1.0;
                                if (!(Nigga26 instanceof ModeSetting) || !Nigga7.modeSetting.getMode().equals(Qprot0.0("\uf66c\u71c2\ucd27\ue279"))) continue;
                                ModeSetting Nigga27 = (ModeSetting)Nigga26;
                                if (!Nigga27.expanded) continue;
                                iterator = Nigga27.modes.iterator();
                                while (iterator.hasNext()) {
                                    iterator.next();
                                    Nigga3 += 0.0;
                                }
                            }
                        }
                        Nigga3 += 1.0;
                    }
                    if (("" + category.outAnim.stage).equals(Qprot0.0("\uf66e\u71ca\ucd1a"))) {
                        category.outAnim.stage = 0.0;
                    }
                    if ((Nigga24 = Nigga3 * 17.0 * 2.0) > Nigga7.maxModule.getValue() * 2.0) {
                        Nigga24 = Nigga7.maxModule.getValue() * 2.0;
                    }
                    Nigga21 = Client.delay;
                    if (Nigga22) {
                        if (!category.goingIn) {
                            if (!Nigga7.animations.getMode().equals(Qprot0.0("\uf66e\u71c4\ucd3a\ue268"))) {
                                if (category.outAnim.stage > 10.0) {
                                    if (Nigga7.animations.getMode().equals(Qprot0.0("\uf662\u71c2\ucd38\ue264\u6200\u73ff\u8c2e\u9a1f"))) {
                                        category.outAnim.stage -= Nigga21 + Math.pow(Nigga24 - category.outAnim.stage, 1.2) / 200.0;
                                    }
                                    if (Nigga7.animations.getMode().equals(Qprot0.0("\uf662\u71c2\ucd38\ue264\u6200\u73ff\u8c2e\u9a1f\u12d9"))) {
                                        category.outAnim.stage -= Client.delay / 5.0 + Math.pow(category.outAnim.stage / 1.5, 1.2) / 200.0;
                                    }
                                    if (Nigga7.animations.getMode().equals(Qprot0.0("\uf66c\u71c2\ucd3a\ue268\u620f\u73e8"))) {
                                        category.outAnim.stage -= Client.delay / 2.5;
                                    }
                                }
                                if (category.outAnim.stage < 10.0) {
                                    category.outAnim.stage = 10.0;
                                    category.clickGuiExpand = false;
                                }
                            } else {
                                category.clickGuiExpand = false;
                            }
                        } else if (!Nigga7.animations.getMode().equals(Qprot0.0("\uf66e\u71c4\ucd3a\ue268"))) {
                            if (category.outAnim.direction.equals((Object)AnimationHelper.Directions.IN) && category.outAnim.stage < Nigga24 - 1.0) {
                                if (Nigga7.animations.getMode().equals(Qprot0.0("\uf662\u71c2\ucd38\ue264\u6200\u73ff\u8c2e\u9a1f"))) {
                                    category.outAnim.stage += Nigga21 + Math.pow(Nigga24 - category.outAnim.stage, 1.4) / 250.0;
                                }
                                if (Nigga7.animations.getMode().equals(Qprot0.0("\uf662\u71c2\ucd38\ue264\u6200\u73ff\u8c2e\u9a1f\u12d9"))) {
                                    category.outAnim.stage += Nigga21 / 5.0 + Math.pow(category.outAnim.stage / 1.5, 1.4) / 200.0;
                                }
                                if (Nigga7.animations.getMode().equals(Qprot0.0("\uf66c\u71c2\ucd3a\ue268\u620f\u73e8"))) {
                                    category.outAnim.stage += Nigga21 / 2.5;
                                }
                            }
                        } else {
                            category.outAnim.stage = Nigga24;
                        }
                        if (category.outAnim.stage > Nigga24) {
                            category.outAnim.stage = Nigga24;
                        }
                        if (category.outAnim.stage < 0.0) {
                            category.outAnim.stage = 0.0;
                        }
                    }
                    if ((Nigga5 = Nigga3 * 17.0 + (double)Nigga42 + (double)category.scroll - 14.0) > Nigga7.maxModule.getValue()) {
                        Nigga5 = Nigga7.maxModule.getValue();
                    }
                    if ((Nigga23 = (double)((int)(category.outAnim.stage / 2.0))) > Nigga5) {
                        Nigga23 = Nigga5;
                    }
                    if ((Nigga222 = (double)category.cgY + Nigga23 + 7.0) < (double)(20 + category.cgY)) {
                        Nigga222 = 20 + category.cgY;
                    }
                    if (Nigga7.sidebars.getMode().equals(Qprot0.0("\uf662\u71c4\ucd20\ue265")) || Nigga7.sidebars.getMode().equals(Qprot0.0("\uf66c\u71ce\ucd32\ue279"))) {
                        Gui.drawRect(category.cgX - 2, category.cgY + 10, category.cgX, Nigga222 - 5.0, -14671840);
                    }
                    if (Nigga7.sidebars.getMode().equals(Qprot0.0("\uf662\u71c4\ucd20\ue265")) || Nigga7.sidebars.getMode().equals(Qprot0.0("\uf672\u71c2\ucd33\ue265\u621a"))) {
                        Gui.drawRect(category.cgX + 87, category.cgY + 10, category.cgX + 85, Nigga222 - 5.0, -14671840);
                    }
                    if (Nigga7.bar.isEnabled()) {
                        Gui.drawRect(category.cgX, Nigga23 + (double)category.cgY, category.cgX + 85, 6.0 + Nigga23 + (double)category.cgY, -14671840);
                        RenderUtil.drawRoundedRect(category.cgX - 2, Nigga23 + (double)category.cgY, category.cgX + 85 + 2, 7.0 + Nigga23 + (double)category.cgY, 5.0, -14671840);
                    } else {
                        Gui.drawRect(category.cgX, Nigga23 + (double)category.cgY, category.cgX + 85, 2.0 + Nigga23 + (double)category.cgY, -14671840);
                    }
                    if (Nigga7.hovering(category.cgX - 2, (int)(Nigga23 + (double)category.cgY), category.cgX + 85 + 2, (int)(7.0 + Nigga23 + (double)category.cgY))) {
                        Gui.drawRect(category.cgX + 35, Nigga23 + (double)category.cgY + 2.0, category.cgX + 51, 4.0 + Nigga23 + (double)category.cgY, -11513776);
                    }
                    GL11.glPushMatrix();
                    RenderUtil.initMask();
                    Gui.drawRect(0.0, category.cgY + 10, category.cgX + 90, (double)category.cgY + Nigga23, 255);
                    RenderUtil.useMask();
                    for (Module Nigga28 : Client.modules) {
                        if (!Nigga28.category.equals((Object)category)) continue;
                        boolean Nigga29 = Nigga7.hovering(category.cgX - FontUtil.cleanmedium.getStringWidth(Nigga28.name) / 2 + 35, Nigga4 * 17 + 3 + Nigga42 + category.cgY + category.scroll, category.cgX - FontUtil.cleanmedium.getStringWidth(Nigga28.name) / 2 + 40 + FontUtil.cleanmedium.getStringWidth(Nigga28.name), Nigga4 * 17 + 15 + Nigga42 + category.cgY + category.scroll);
                        Gui.drawRect(category.cgX, Nigga4 * 17 + category.scroll + category.cgY + Nigga42 + 3, 85 + category.cgX, Nigga4 * 17 + 17 + category.scroll + category.cgY + Nigga42 + 3, -298831824);
                        int Nigga30 = -7303024;
                        if (Nigga28.toggled) {
                            Nigga30 = -1;
                        }
                        if (Nigga29) {
                            Color Nigga31 = new Color(Client.RGBColor);
                            int Nigga32 = 180;
                            if (Nigga28.toggled) {
                                Nigga32 = 255;
                            }
                            Nigga30 = Colors.getColor(Nigga31.getRed(), Nigga31.getBlue(), Nigga31.getGreen(), Nigga32);
                        }
                        if (Nigga7.searchBox.selected && Nigga7.searchBox.storedText.length() > 0) {
                            Nigga30 = searches.contains(Nigga28) ? -1 : -11513776;
                        }
                        FontUtil.cleanmediumish.drawCenteredString(Nigga28.name, category.cgX + 42, Nigga4 * 17 + 5 + Nigga42 + category.cgY + category.scroll, Nigga30);
                        int Nigga33 = 0;
                        if (Nigga28.cgExpanded) {
                            for (Setting Nigga34 : Nigga28.settings) {
                                int Nigga37;
                                int Nigga38 = Client.RGBColor;
                                if (!ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\uf664\u71c2\ucd27\ue26c\u620c\u73f6\u8c2a\u9a09"))) {
                                    Nigga38 = ColourUtil.getRainbow((float)ModuleManager.hudModule.rgbSpeed.getValue(), (float)ModuleManager.hudModule.saturation.getValue() / Float.intBitsToFloat(1.00764742E9f ^ 0x7F707ACE), Float.intBitsToFloat(1.08845914E9f ^ 0x7F609193), (long)((double)(Nigga33 * 30) * ModuleManager.hudModule.rainbowOffset.getValue()));
                                }
                                if (Nigga34.disabled) {
                                    Nigga38 = ColourUtil.getRainbow((float)ModuleManager.hudModule.rgbSpeed.getValue(), (float)ModuleManager.hudModule.saturation.getValue() / Float.intBitsToFloat(1.0350096E9f ^ 0x7ECFFE37), Float.intBitsToFloat(1.07604851E9f ^ 0x7F1001AE), (long)((double)(Nigga33 * 30) * ModuleManager.hudModule.rainbowOffset.getValue()));
                                }
                                boolean Nigga39 = Nigga7.hovering(category.cgX, Nigga33 * 17 + 22 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, category.cgX + 85, Nigga33 * 17 + 37 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll);
                                int Nigga40 = Nigga33 * 17 + 20 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll;
                                if (Nigga40 < category.cgY) {
                                    Nigga40 = category.cgY;
                                }
                                if ((Nigga37 = Nigga33 * 17 + 37 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll) < category.cgY) {
                                    Nigga37 = category.cgY;
                                }
                                Gui.drawRect(category.cgX, Nigga40, category.cgX + 85, Nigga37, -580886432);
                                if (Nigga34 instanceof BooleanSetting) {
                                    FontUtil.cleantiny.drawString(Nigga34.name, category.cgX + 16, Nigga33 * 17 + 24 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, Nigga39 ? Nigga38 : -1);
                                    Gui.drawRect(category.cgX + 2, Nigga33 * 17 + 22 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, category.cgX + 13, Nigga33 * 17 + 27 + Nigga4 * 17 + Nigga42 + category.cgY + 7 + category.scroll, -297779136);
                                    if (((BooleanSetting)Nigga34).isEnabled()) {
                                        Gui.drawRect(category.cgX + 4, Nigga33 * 17 + 24 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, category.cgX + 11, Nigga33 * 17 + 23 + Nigga4 * 17 + Nigga42 + category.cgY + 9 + category.scroll, Nigga38);
                                        Nigga7.mc.getTextureManager().bindTexture(new ResourceLocation(Qprot0.0("\uf673\u71c0\ucd3d\ue277\u6214\u73f6\u8c2a\u9a42\u1288\uc94c\uf9d2\uaf0f\u74dd\u37fb\u163a\u6a44\u42fb\u4024\u52c7\u7d5f\u0f88\u01cd")));
                                        int Nigga6 = 8;
                                        int Nigga8 = 8;
                                        GL11.glEnable((int)3042);
                                        GL11.glEnable((int)2848);
                                        GuiScreen.drawModalRectWithCustomSizedTexture(category.cgX + 4, Nigga33 * 17 + 24 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, Float.intBitsToFloat(2.13336013E9f ^ 0x7F287DF9), Float.intBitsToFloat(2.12705754E9f ^ 0x7EC8527B), Nigga6, Nigga8, Nigga6, Nigga8);
                                    }
                                }
                                if (Nigga34 instanceof ModeSetting) {
                                    ModeSetting Nigga41 = (ModeSetting)Nigga34;
                                    if (Nigga7.modeSetting.getMode().equals(Qprot0.0("\uf673\u71c2\ucd3a\ue26a\u6202\u73ff"))) {
                                        FontUtil.cleantiny.drawString(String.valueOf(Nigga34.name) + Qprot0.0("\uf61a\u718b") + ((ModeSetting)Nigga34).getMode(), category.cgX + 2, Nigga33 * 17 + 24 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, Nigga39 ? Nigga38 : -1);
                                    } else if (Nigga7.modeSetting.getMode().equals(Qprot0.0("\uf66c\u71c2\ucd27\ue279"))) {
                                        FontUtil.cleantiny.drawString(Nigga34.name, category.cgX + 2, Nigga33 * 17 + 24 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, Nigga39 ? Nigga38 : -1);
                                        if (Nigga41.expanded) {
                                            for (String Nigga422 : Nigga41.modes) {
                                                int Nigga43 = Nigga33 * 17 + 27 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll + 10;
                                                Gui.drawRect(category.cgX, Nigga43, category.cgX + 85, Nigga43 + 10, -579833744);
                                                boolean Nigga44 = Nigga7.hovering(category.cgX, Nigga43, category.cgX + 85, Nigga43 + 9);
                                                int Nigga45 = -4473925;
                                                if (Nigga41.getMode().equals(Nigga422)) {
                                                    Nigga45 = -1;
                                                }
                                                FontUtil.cleantiny.drawString(Nigga422, category.cgX + 8, Nigga43, Nigga44 ? Nigga38 : Nigga45);
                                                Nigga42 += 10;
                                            }
                                        }
                                    }
                                }
                                if (Nigga34 instanceof NumberSetting) {
                                    int Nigga36 = category.cgX;
                                    int Nigga35 = category.cgX + 85;
                                    FontUtil.cleantiny.drawStringWithShadow(String.valueOf(Nigga34.name) + Qprot0.0("\uf61a\u718b") + ((NumberSetting)Nigga34).getValue(), category.cgX + 2, Nigga33 * 17 + 22 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, Nigga39 ? Nigga38 : -1);
                                    NumberSetting Nigga46 = (NumberSetting)Nigga34;
                                    double Nigga47 = (Nigga46.sliderValue - Nigga46.getMinimum()) / (Nigga46.getMaximum() - Nigga46.getMinimum());
                                    Nigga47 *= 85.0;
                                    if (Nigga33 * 17 + 22 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll > category.cgY - 5) {
                                        Gui.drawRect(Nigga36, Nigga33 * 17 + 33 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, category.cgX + 85, Nigga33 * 17 + 13 + Nigga4 * 17 + Nigga42 + category.cgY + 22 + category.scroll, -297779136);
                                        Gui.drawRect(Nigga36, Nigga33 * 17 + 33 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, (int)((double)category.cgX + Nigga47), Nigga33 * 17 + 13 + Nigga4 * 17 + Nigga42 + category.cgY + 22 + category.scroll, Nigga38);
                                    }
                                    if (Nigga7.mouseHold == 0 && Nigga7.hovering(Nigga36, Nigga33 * 17 + 26 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, 85 + category.cgX, Nigga33 * 17 + 13 + Nigga4 * 17 + Nigga42 + category.cgY + 24 + category.scroll) && !Nigga34.disabled) {
                                        float Nigga48 = (float)(Nigga36 - Nigga7.mouseX) / (float)(Nigga36 - Nigga35);
                                        double Nigga49 = (Nigga46.getMaximum() - Nigga46.getMinimum()) * (double)Nigga48 + Nigga46.getMinimum();
                                        double Nigga50 = (double)((float)((Nigga46.getMaximum() - Nigga46.getMinimum()) * (double)Nigga48)) + Nigga46.getMinimum();
                                        double Nigga51 = 1.0 / Nigga46.increment;
                                        Nigga46.sliderValue = Nigga50 = (double)Math.round(Math.max(Nigga46.getMinimum(), Math.min(Nigga46.getMaximum(), Nigga50)) * Nigga51) / Nigga51;
                                        if (Nigga7.snapSlider.isEnabled()) {
                                            Nigga46.setValue(Nigga50);
                                        } else {
                                            Nigga50 = Math.round(Nigga49 * 100.0);
                                            Nigga46.value = Nigga50 /= 100.0;
                                            Nigga46.sliderValue = Nigga49;
                                        }
                                    }
                                }
                                if (Nigga34 instanceof KeybindSetting) {
                                    if (((KeybindSetting)Nigga34).cgSelected && Nigga33 * 17 + 22 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll > category.cgY) {
                                        Gui.drawRect(category.cgX, Nigga33 * 17 + 20 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, 85 + category.cgX, Nigga33 * 17 + 37 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, Nigga38);
                                    }
                                    if (Nigga33 * 17 + 22 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll > category.cgY) {
                                        FontUtil.cleantiny.drawString(String.valueOf(Nigga34.name) + Qprot0.0("\uf61a\u718b") + Keyboard.getKeyName((int)((KeybindSetting)Nigga34).getKeyCode()), category.cgX + 2, Nigga33 * 17 + 24 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, Nigga39 ? (((KeybindSetting)Nigga34).cgSelected ? -1 : Nigga38) : -1);
                                    }
                                }
                                if (Nigga34 instanceof DescriptionSetting && Nigga33 * 17 + 22 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll > category.cgY) {
                                    FontUtil.cleantiny.drawString(((DescriptionSetting)Nigga34).desc, category.cgX + 8, Nigga33 * 17 + 24 + Nigga4 * 17 + Nigga42 + category.cgY + category.scroll, -1);
                                }
                                ++Nigga33;
                            }
                            Iterator<Setting> iterator2 = Nigga28.settings.iterator();
                            while (iterator2.hasNext()) {
                                iterator2.next();
                                Nigga42 += 17;
                            }
                        }
                        Gui.drawRect(category.cgX, category.cgY + 10, 85 + category.cgX, 20 + category.cgY, -14671840);
                        ++Nigga4;
                    }
                    Gui.drawStaticGradientRectVert(category.cgX, (int)Nigga23 + category.cgY - 4, category.cgX + 85, (int)Nigga23 + category.cgY, 0x202020, -14671840);
                    GlStateManager.disableAlpha();
                    RenderUtil.disableMask();
                    GL11.glPopMatrix();
                }
                RenderUtil.drawRoundedRect(category.cgX - 2, category.cgY, 85 + category.cgX + 2, 20 + category.cgY, 15.0, -14671840);
                Nigga3 = (int)(category.outAnim.stage / 2.0);
                if (Nigga3 > Nigga5) {
                    Nigga3 = Nigga5;
                }
                int Nigga52 = Client.RGBColor;
                if (!ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\uf664\u71c2\ucd27\ue26c\u620c\u73f6\u8c2a\u9a09"))) {
                    Nigga52 = ColourUtil.getRainbow((float)ModuleManager.hudModule.rgbSpeed.getValue(), Float.intBitsToFloat(1.08010394E9f ^ 0x7F2DDF20), Float.intBitsToFloat(1.09074867E9f ^ 0x7E8380FD), (long)((double)(var3_14 * 30) * ModuleManager.hudModule.rainbowOffset.getValue()));
                }
                ((MinecraftFontRenderer)Nigga2).drawString(category.name, 42 - ((MinecraftFontRenderer)Nigga2).getStringWidth(category.name) / 2 + category.cgX, 2 + category.cgY, Nigga52);
                ++var3_14;
                GL11.glTranslated((double)((double)category.cgX + 42.5), (double)category.cgY, (double)0.0);
                GL11.glRotated((double)(-category.rotation), (double)0.0, (double)0.0, (double)1.0);
                GL11.glTranslated((double)((double)(-category.cgX) - 42.5), (double)(-category.cgY), (double)0.0);
            }
        }
        if (Nigga instanceof EventMouseClick) {
            int Nigga9;
            int Nigga10;
            int Nigga11;
            Nigga2 = (EventMouseClick)Nigga;
            boolean bl = true;
            Nigga7.searchBox.onMouseClick(((EventMouseClick)Nigga2).mouseButton, ((EventMouseClick)Nigga2).state, Nigga7.mouseX, Nigga7.mouseY);
            if (((EventMouseClick)Nigga2).mouseButton != -1) {
                if (((EventMouseClick)Nigga2).state == 1 && Nigga7.mouseHold == -1) {
                    Nigga7.lastMsButton = ((EventMouseClick)Nigga2).mouseButton;
                    Nigga7.mouseHold = ((EventMouseClick)Nigga2).mouseButton;
                } else if (((EventMouseClick)Nigga2).state == 0 && Nigga7.mouseHold != -1) {
                    Nigga7.lastMsButton = ((EventMouseClick)Nigga2).mouseButton;
                    Nigga7.mouseHold = -1;
                }
            }
            int n = Mouse.getEventDWheel();
            if (Mouse.getDWheel() != 0) {
                for (Module.Category category : Module.Category.values()) {
                    if (Nigga7.hovering(category.cgX, category.cgY, 80 + category.cgX, 20 + category.cgY) && ((EventMouseClick)Nigga2).mouseButton == 1) {
                        boolean bl3 = category.goingIn = !category.goingIn;
                        if (category.goingIn) {
                            category.clickGuiExpand = true;
                        }
                    }
                    Nigga11 = 0;
                    if (!category.clickGuiExpand || !category.goingIn) continue;
                    int Nigga56 = 0;
                    for (Module Nigga57 : Client.modules) {
                        if (!Nigga57.category.equals((Object)category)) continue;
                        if (Nigga57.cgExpanded) {
                            for (Setting Nigga58 : Nigga57.settings) {
                                ++Nigga56;
                                if (!(Nigga58 instanceof ModeSetting)) continue;
                                ModeSetting Nigga59 = (ModeSetting)Nigga58;
                                if (!Nigga7.modeSetting.getMode().equals(Qprot0.0("\uf66c\u71c2\ucd27\ue279")) || !Nigga59.expanded) continue;
                                Iterator<String> iterator3 = Nigga59.modes.iterator();
                                while (iterator3.hasNext()) {
                                    iterator3.next();
                                    Nigga11 += 10;
                                }
                            }
                        }
                        ++Nigga56;
                    }
                    Nigga10 = (Nigga56 + 2) * 17 + Nigga11 + category.scroll - 14;
                    if ((double)Nigga10 > Nigga7.maxModule.getValue()) {
                        Nigga10 = (int)Nigga7.maxModule.getValue();
                    }
                    if ((Nigga9 = (int)(category.outAnim.stage / 2.0)) > Nigga10) {
                        Nigga9 = Nigga10;
                    }
                    if (Nigga7.hovering(category.cgX, category.cgY + 15, 80 + category.cgX, 7 + Nigga9 + category.cgY)) {
                        int Nigga60 = category.scroll;
                        if (n < 0) {
                            category.scroll += n / 4;
                            if ((double)Nigga9 < Nigga7.maxModule.getValue() - 1.0) {
                                category.scroll = Nigga60;
                            }
                        } else if (n > 0) {
                            category.scroll += n / 4;
                        }
                        if (category.scroll > 0) {
                            category.scroll = 0;
                        }
                    }
                    if ((double)(Nigga10 = (Nigga56 + 2) * 17 + Nigga11 + category.scroll - 14) > Nigga7.maxModule.getValue()) {
                        Nigga10 = (int)Nigga7.maxModule.getValue();
                    }
                    if ((Nigga9 = (int)(category.outAnim.stage / 2.0)) <= Nigga10) continue;
                    Nigga9 = Nigga10;
                }
            }
            if (((EventMouseClick)Nigga2).state == 1) {
                for (Module.Category category : Module.Category.values()) {
                    void var3_16;
                    if (Nigga7.hovering(category.cgX, category.cgY, 80 + category.cgX, 20 + category.cgY) && ((EventMouseClick)Nigga2).mouseButton == 1) {
                        boolean bl4 = category.goingIn = !category.goingIn;
                        if (category.goingIn) {
                            category.clickGuiExpand = true;
                        }
                    }
                    Nigga11 = 0;
                    if (category.clickGuiExpand && category.goingIn) {
                        int Nigga62 = 0;
                        for (Module Nigga63 : Client.modules) {
                            if (!Nigga63.category.equals((Object)category)) continue;
                            if (Nigga63.cgExpanded) {
                                Iterator<Setting> Nigga60 = Nigga63.settings.iterator();
                                while (Nigga60.hasNext()) {
                                    Nigga60.next();
                                    ++Nigga62;
                                }
                            }
                            ++Nigga62;
                        }
                        Nigga10 = (Nigga62 + 2) * 17 + Nigga11 + category.scroll - 14;
                        if ((double)Nigga10 > Nigga7.maxModule.getValue()) {
                            Nigga10 = (int)Nigga7.maxModule.getValue();
                        }
                        if ((Nigga9 = (int)(category.outAnim.stage / 2.0)) > Nigga10) {
                            Nigga9 = Nigga10;
                        }
                        int Nigga64 = 1;
                        for (Module Nigga65 : Client.modules) {
                            if (!Nigga65.category.equals((Object)category) || Nigga7.mouseY > Nigga10 + category.cgY) continue;
                            if (Nigga7.hovering(category.cgX - FontUtil.cleanmedium.getStringWidth(Nigga65.name) / 2 + 35, Nigga64 * 17 + 3 + Nigga11 + category.cgY + category.scroll, category.cgX - FontUtil.cleanmedium.getStringWidth(Nigga65.name) / 2 + 40 + FontUtil.cleanmedium.getStringWidth(Nigga65.name), Nigga64 * 17 + 15 + Nigga11 + category.cgY + category.scroll) && Nigga64 * 17 - 5 + Nigga11 + category.cgY + category.scroll > category.cgY) {
                                if (((EventMouseClick)Nigga2).mouseButton == 0) {
                                    Nigga65.toggle();
                                }
                                if (((EventMouseClick)Nigga2).mouseButton == 1) {
                                    boolean bl5 = Nigga65.cgExpanded = !Nigga65.cgExpanded;
                                    if (Nigga65.cgExpanded) {
                                        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\uf647\u71de\ucd3d\ue223\u620c\u73ef\u8c3b\u9a19\u1284\uc94a\uf999\uaf1c\u74c4\u37c1\u1624\u6a56")), Float.intBitsToFloat(1.06381254E9f ^ 0x7F687DD0)));
                                    }
                                    if (!Nigga65.cgExpanded) {
                                        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\uf647\u71de\ucd3d\ue223\u620c\u73ef\u8c3b\u9a19\u1284\uc94a\uf999\uaf1c\u74c4\u37c1\u1624\u6a56")), Float.intBitsToFloat(1.08308378E9f ^ 0x7F7DBF1E)));
                                    }
                                }
                            }
                            if (Nigga65.cgExpanded) {
                                int Nigga66 = 0;
                                for (Setting Nigga67 : Nigga65.settings) {
                                    boolean Nigga68 = Nigga7.hovering(category.cgX, Nigga66 * 17 + 20 + Nigga64 * 17 + Nigga11 + category.cgY + category.scroll, category.cgX + 85, Nigga66 * 17 + 37 + Nigga64 * 17 + Nigga11 + category.cgY + category.scroll);
                                    if (Nigga66 * 17 + 20 + Nigga64 * 17 + Nigga11 + category.cgY + category.scroll > category.cgY && Nigga67 instanceof ModeSetting) {
                                        ModeSetting Nigga69 = (ModeSetting)Nigga67;
                                        if (Nigga7.modeSetting.getMode().equals(Qprot0.0("\uf66c\u71c2\ucd27\ue279"))) {
                                            if (((EventMouseClick)Nigga2).mouseButton == 1 && Nigga68) {
                                                boolean bl6 = Nigga69.expanded = !Nigga69.expanded;
                                            }
                                            if (Nigga69.expanded) {
                                                int Nigga70 = 0;
                                                iterator = Nigga69.modes.iterator();
                                                while (iterator.hasNext()) {
                                                    int Nigga71;
                                                    boolean Nigga72;
                                                    iterator.next();
                                                    if (((EventMouseClick)Nigga2).mouseButton == 0 && (Nigga72 = Nigga7.hovering(category.cgX, Nigga71 = Nigga66 * 17 + 27 + Nigga64 * 17 + Nigga11 + category.cgY + category.scroll + 10 + Nigga70 * 10, category.cgX + 85, Nigga71 + 9))) {
                                                        Nigga69.index = Nigga70;
                                                        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\uf647\u71de\ucd3d\ue223\u620c\u73ef\u8c3b\u9a19\u1284\uc94a\uf999\uaf1c\u74c4\u37c1\u1624\u6a56")), Float.intBitsToFloat(1.05361024E9f ^ 0x7ECCD0F9)));
                                                    }
                                                    ++Nigga70;
                                                }
                                                iterator = Nigga69.modes.iterator();
                                                while (iterator.hasNext()) {
                                                    iterator.next();
                                                    Nigga11 += 10;
                                                }
                                            }
                                        } else if (Nigga7.modeSetting.getMode().equals(Qprot0.0("\uf673\u71c2\ucd3a\ue26a\u6202\u73ff")) && Nigga68) {
                                            Nigga69.cycle(((EventMouseClick)Nigga2).mouseButton == 1);
                                        }
                                    }
                                    if (Nigga68 && Nigga66 * 17 + 20 + Nigga64 * 17 + Nigga11 + category.cgY + category.scroll > category.cgY) {
                                        if (Nigga67 instanceof BooleanSetting) {
                                            ((BooleanSetting)Nigga67).toggle();
                                            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\uf647\u71de\ucd3d\ue223\u620c\u73ef\u8c3b\u9a19\u1284\uc94a\uf999\uaf1c\u74c4\u37c1\u1624\u6a56")), Float.intBitsToFloat(1.04871392E9f ^ 0x7E821AAD)));
                                        }
                                        if (Nigga67 instanceof KeybindSetting && !Nigga7.isBinding) {
                                            if (((EventMouseClick)Nigga2).mouseButton == 0) {
                                                ((KeybindSetting)Nigga67).cgSelected = true;
                                                Nigga7.isBinding = true;
                                            }
                                            if (((EventMouseClick)Nigga2).mouseButton == 1) {
                                                ((KeybindSetting)Nigga67).code = 0;
                                            }
                                        }
                                    }
                                    ++Nigga66;
                                }
                                Iterator<Setting> iterator4 = Nigga65.settings.iterator();
                                while (iterator4.hasNext()) {
                                    iterator4.next();
                                    Nigga11 += 17;
                                }
                            }
                            ++Nigga64;
                        }
                    }
                    ++var3_16;
                }
            }
        }
    }

    public boolean hovering(int Nigga, int Nigga2, int Nigga3, int Nigga4) {
        ClickGUI Nigga5;
        return Nigga5.mouseX >= Nigga && Nigga5.mouseY >= Nigga2 && Nigga5.mouseX <= Nigga3 && Nigga5.mouseY <= Nigga4;
    }

    public ClickGUI() {
        super(Qprot0.0("\uf663\u71c7\ucd3d\ua7e7\ud99c\u73dd\u8c1a\u9a24"), 54, Module.Category.RENDER);
        ClickGUI Nigga;
        Nigga.modeSetting = new ModeSetting(Qprot0.0("\uf66d\u71c4\ucd30\ua7e1\ud9d7\u73c9\u8c2a\u9a19\u5716\u72d4\uf9d9\uaf0b"), Qprot0.0("\uf66c\u71c2\ucd27\ua7f0"), Qprot0.0("\uf66c\u71c2\ucd27\ua7f0"), Qprot0.0("\uf673\u71c2\ucd3a\ua7e3\ud99b\u73ff"));
        Nigga.sidebars = new ModeSetting(Qprot0.0("\uf662\u71ca\ucd26\ua7f7"), Qprot0.0("\uf662\u71c4\ucd20\ua7ec"), Qprot0.0("\uf66c\u71ce\ucd32\ua7f0"), Qprot0.0("\uf672\u71c2\ucd33\ua7ec\ud983"), Qprot0.0("\uf662\u71c4\ucd20\ua7ec"), Qprot0.0("\uf66e\u71c4\ucd3a\ua7e1"));
        Nigga.bar = new BooleanSetting(Qprot0.0("\uf662\u71c4\ucd20\ua7f0\ud998\u73f7\u8c6f\u9a2f\u5703\u72cf"), true);
        Nigga.drag = new BooleanSetting(Qprot0.0("\uf664\u71d9\ucd35\ua7e3\ud9d7\u73ca\u8c27\u9a14\u5711\u72d4\uf9d4\uaf1f"), true);
        Nigga.animations = new ModeSetting(Qprot0.0("\uf661\u71c5\ucd3d\ua7e9\ud996\u73ee\u8c26\u9a02\u570c\u72ce"), Qprot0.0("\uf662\u71c2\ucd38\ua7ed\ud999\u73ff\u8c2e\u9a1f"), Qprot0.0("\uf662\u71c2\ucd38\ua7ed\ud999\u73ff\u8c2e\u9a1f"), Qprot0.0("\uf662\u71c2\ucd38\ua7ed\ud999\u73ff\u8c2e\u9a1f\u5750"), Qprot0.0("\uf66c\u71c2\ucd3a\ua7e1\ud996\u73e8"), Qprot0.0("\uf66e\u71c4\ucd3a\ua7e1"));
        Nigga.searchMode = new ModeSetting(Qprot0.0("\uf673\u71ce\ucd35\ua7f6\ud994\u73f2"), Qprot0.0("\uf64e\u71cc\ucd26\ua7e5\ud99a"), Qprot0.0("\uf64e\u71cc\ucd26\ua7e5\ud99a"), Qprot0.0("\uf643\u71c4\ucd3a\ua7f0\ud996\u73f3\u8c21\u9a1e"));
        Nigga.maxModule = new NumberSetting(Qprot0.0("\uf670\u71ca\ucd3a\ua7e1\ud99b\u73ba\u8c07\u9a08\u570b\u72da\uf9df\uaf18"), 270.0, 121.0, 1000.0, 1.0);
        Nigga.anim = new AnimationHelper();
        Nigga.addSettings(Nigga.snapSlider, Nigga.modeSetting, Nigga.sidebars, Nigga.drag, Nigga.animations, Nigga.bar, Nigga.maxModule, Nigga.searchMode);
        Nigga.toggled = false;
    }

    public static void setupSearch() {
        HashMap Nigga = new HashMap();
        for (Module Nigga2 : Client.modules) {
            String Nigga3 = Nigga2.name;
            for (int Nigga4 = 0; Nigga4 < Nigga3.length() - 1; ++Nigga4) {
                String Nigga5 = "";
                int Nigga6 = Nigga4 + 2;
                if (Nigga6 > Nigga3.length()) {
                    Nigga6 = Nigga3.length();
                }
                if (Nigga.get(Nigga5 = Nigga3.substring(Nigga4, Nigga6).toLowerCase()) != null && ((ArrayList)Nigga.get(Nigga5)).size() > 0) {
                    ((ArrayList)Nigga.get(Nigga5)).add(Nigga2);
                    continue;
                }
                ArrayList<Module> Nigga7 = new ArrayList<Module>();
                Nigga7.add(Nigga2);
                Nigga.put(Nigga5, Nigga7);
            }
        }
        search = Nigga;
    }

    static {
        search = new HashMap();
        searches = new ArrayList();
    }

    @Override
    public void onEnable() {
        ClickGUI Nigga;
        Nigga.searchBox = new Textbox(Qprot0.0("\uf653\u71ce\ucd35\u59c6\udfc4\u73f2"), 0, 2, 2, 90, 20);
        if (Nigga.keepMouse.isEnabled()) {
            Mouse.setCursorPosition((int)Nigga.lastMouseX, (int)Nigga.lastMouseY);
        }
    }

    public class fakeGuiScreen
    extends GuiScreen {
        public ClickGUI this$0;

        public static {
            throw throwable;
        }

        public fakeGuiScreen(ClickGUI clickGUI) {
            fakeGuiScreen Nigga;
            Nigga.this$0 = clickGUI;
            Nigga.allowUserInput = true;
        }
    }
}

