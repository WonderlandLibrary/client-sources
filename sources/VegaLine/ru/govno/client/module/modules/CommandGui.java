/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class CommandGui
extends Module {
    static AnimationUtils alphaPC = new AnimationUtils(0.0f, 0.0f, 0.125f);
    public static CommandGui get;
    static int mouseX;
    static int mouseY;
    static int mouseButton;
    static boolean clicked;
    static String toAdd;
    static String toSend;
    static String selectedName;
    static AnimationUtils scrollNames;
    static CommandType selectedType;

    public CommandGui() {
        super("CommandGui", 0, Module.Category.MISC);
        this.settings.add(new Settings("WX", 0.01f, 1.0f, 0.0f, this, () -> false));
        this.settings.add(new Settings("WY", 0.3f, 1.0f, 0.0f, this, () -> false));
        get = this;
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        float f = CommandGui.alphaPC.to = this.actived && CommandGui.mc.currentScreen instanceof GuiChat ? 1.0f : 0.0f;
        if (CommandGui.canDrawWindow()) {
            CommandGui.drawWindow();
            CommandGui.updateAdds();
            CommandGui.updateName();
            float h = 70.0f;
            float size = CommandGui.tabList(true).size() * 10;
            if (size > 70.0f) {
                CommandGui.scrollNames.to = MathUtils.clamp(CommandGui.scrollNames.to, 0.0f, size - h);
                scrollNames.setAnim(MathUtils.clamp(CommandGui.scrollNames.anim, 0.0f, size - h));
            } else {
                CommandGui.scrollNames.to = 0.0f;
            }
        }
    }

    public static float getAlphaPC() {
        return alphaPC.getAnim();
    }

    public static boolean canDrawWindow() {
        return (double)CommandGui.getAlphaPC() > 0.03;
    }

    public static float[] getWindowCoord() {
        float x = 0.0f;
        float y = 0.0f;
        if (get != null) {
            ScaledResolution sr = new ScaledResolution(mc);
            x = get.currentFloatValue("WX") * (float)sr.getScaledWidth();
            y = get.currentFloatValue("WY") * (float)sr.getScaledHeight();
        }
        return new float[]{x, y};
    }

    public static float getWindowWidth() {
        return 152.0f;
    }

    public static float getWindowHeight() {
        return 100.0f;
    }

    static List<String> tabList(boolean onlyName) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
        for (NetworkPlayerInfo player : Minecraft.player.connection.getPlayerInfoMap()) {
            if (player.getGameProfile() == null) continue;
            String text = onlyName ? player.getGameProfile().getName() : (player.getDisplayName() == null ? player.getGameProfile().getName() : player.getDisplayName().getUnformattedText());
            text = text.replace("  ", " ").replace("\u00a7l", "").toString().replace("[]", "").replace("\u00a7k", "").replace("\u00a7m", "").replace("\u00a7n", "").replace("\u00a7o", "");
            text = text.replace("\uff21", "A");
            text = text.replace("\uff22", "B");
            text = text.replace("\uff23", "C");
            text = text.replace("\uff24", "D");
            text = text.replace("\uff25", "E");
            text = text.replace("\uff26", "F");
            text = text.replace("\uff27", "G");
            text = text.replace("\uff28", "H");
            text = text.replace("\uff29", "I");
            text = text.replace("\uff2a", "J");
            text = text.replace("\uff2b", "K");
            text = text.replace("\uff2c", "L");
            text = text.replace("\uff2d", "M");
            text = text.replace("\uff2e", "N");
            text = text.replace("\uff2f", "O");
            text = text.replace("\uff30", "P");
            text = text.replace("\uff31", "Q");
            text = text.replace("\uff32", "R");
            text = text.replace("\uff33", "S");
            text = text.replace("\uff34", "T");
            text = text.replace("\uff35", "U");
            text = text.replace("\uff36", "V");
            text = text.replace("\uff37", "W");
            text = text.replace("\uff38", "X");
            text = text.replace("\uff39", "Y");
            text = text.replace("\uff3a", "Z");
            text = text.replace("\u25b7", ">");
            text = text.replace("\u25c1", "<");
            list.add(text);
        }
        return list;
    }

    static void updateName() {
        boolean hasName = false;
        for (String name : CommandGui.tabList(true)) {
            if ((selectedName == null || !name.contains(selectedName)) && !name.equalsIgnoreCase(selectedName)) continue;
            hasName = true;
        }
        if (!hasName) {
            selectedName = null;
        }
    }

    static void updateAdds() {
        if (toAdd != null) {
            toSend = toSend + toAdd;
            toAdd = null;
        }
        if (toSend != null) {
            if (toSend.contains("null")) {
                toSend = toSend.replace("null", "");
            }
        } else if (selectedType != null) {
            selectedType = null;
        }
    }

    public static void updateMousePos(int mX, int mY) {
        mouseX = mX;
        mouseY = mY;
    }

    public static void callClick(int mX, int mY, int mB) {
        mouseX = mX;
        mouseY = mY;
        mouseButton = mB;
        if (CommandGui.isHoveredToPanel(false) && !CommandGui.isHoveredToPanel(true)) {
            clicked = true;
        }
        CommandGui.onClickMouse();
    }

    public static boolean isHoveredToPanel(boolean onlyMove) {
        return CommandGui.canDrawWindow() && RenderUtils.isHovered(mouseX, mouseY, CommandGui.getWindowCoord()[0], CommandGui.getWindowCoord()[1], CommandGui.getWindowWidth(), onlyMove ? 15.0f : CommandGui.getWindowHeight());
    }

    public static void callWhell(boolean plus) {
        if (!CommandGui.isHoveredToPanel(false)) {
            return;
        }
        float h = 70.0f;
        float size = CommandGui.tabList(true).size() * 10;
        CommandGui.scrollNames.to = size > h ? CommandGui.scrollNames.to + (plus ? 10.0f : -10.0f) : 0.0f;
    }

    static void onClickMouse() {
        if (clicked && mouseButton == 0) {
            float x = CommandGui.getWindowCoord()[0];
            float y = CommandGui.getWindowCoord()[1];
            float w = CommandGui.getWindowWidth();
            float h = CommandGui.getWindowHeight();
            y += 15.0f;
            if ((float)mouseX >= x + w - 33.0f && (float)mouseY >= y + h - 27.0f && (float)mouseX <= x + w - 4.0f && (float)mouseY <= y + h - 17.0f && toSend != null && !toSend.isEmpty()) {
                Minecraft.player.sendChatMessage(toSend);
                return;
            }
            CommandType type2 = null;
            float yType = y;
            float xType = x + 4.0f;
            float yNames = y - scrollNames.getAnim();
            float xNames = x + 41.0f;
            if ((float)mouseX >= xType && (float)mouseX <= xType + 35.0f) {
                for (CommandType command : CommandType.values()) {
                    if ((float)mouseY >= yType && (float)mouseY <= yType + 10.0f) {
                        type2 = command;
                    }
                    yType += 10.0f;
                }
            }
            String netName = null;
            if ((float)mouseX >= xNames && (float)mouseX <= xNames + 105.0f && selectedType != null) {
                for (String name : CommandGui.tabList(true)) {
                    if ((float)mouseY >= yNames && (float)mouseY <= yNames + 10.0f && yNames < y + h - 30.0f && yNames > y - 10.0f) {
                        netName = name;
                    }
                    yNames += 10.0f;
                }
            }
            if (netName == null && type2 != null) {
                selectedType = type2;
            }
            selectedName = netName;
            toSend = (selectedType != null ? CommandGui.selectedType.name : "") + (selectedName != null ? selectedName : "");
            clicked = false;
        }
    }

    public static void drawWindow() {
        if (CommandGui.canDrawWindow()) {
            GL11.glPushMatrix();
            int bgCol = ColorUtils.getColor(0, 0, 0, 160.0f * CommandGui.getAlphaPC());
            float x = CommandGui.getWindowCoord()[0];
            float y = CommandGui.getWindowCoord()[1];
            float w = CommandGui.getWindowWidth();
            float h = CommandGui.getWindowHeight();
            RenderUtils.customScaledObject2DPro(x, y, w, h, 1.0f, Math.min(CommandGui.getAlphaPC() * 1.1f, 1.0f));
            RenderUtils.drawClientHudRect(x, y, x + w, y + h + 2.0f, CommandGui.getAlphaPC());
            if (CommandGui.getAlphaPC() * 255.0f >= 33.0f) {
                Fonts.comfortaaRegular_15.drawString("CommandGui", x + w / 2.0f - (float)(Fonts.comfortaaRegular_15.getStringWidth("CommandGui") / 2), y + 7.0f, ColorUtils.getColor(255, 255, 255, 255.0f * CommandGui.getAlphaPC()));
            }
            StencilUtil.initStencilToWrite();
            RenderUtils.drawRect(x + 4.0f, y + 15.0f, x + w - 4.0f, y + h - 14.0f, -1);
            StencilUtil.readStencilBuffer(1);
            float yType = y += 15.0f;
            float xType = x + 4.0f;
            float yNames = y - scrollNames.getAnim();
            float xNames = x + 41.0f;
            for (CommandType command : CommandType.values()) {
                boolean select = selectedType == command;
                RenderUtils.drawAlphedRect(xType, yType, xType + 35.0f, yType + 10.0f, ColorUtils.getColor(255, 55 + (select ? 0 : 200), 55 + (select ? 0 : 200), (float)(55 + (select ? 60 : 0)) * CommandGui.getAlphaPC()));
                if (CommandGui.getAlphaPC() * 255.0f >= 33.0f) {
                    Fonts.comfortaaRegular_15.drawStringWithShadow(command.name, xType + 2.0f, yType + 3.0f, ColorUtils.getColor(255, 255, 255, 255.0f * CommandGui.getAlphaPC()));
                }
                yType += 10.0f;
            }
            if (selectedType != null) {
                boolean select;
                for (String name : CommandGui.tabList(true)) {
                    if (yNames < y + h - 30.0f && yNames > y - 10.0f) {
                        select = selectedName != null && name.contains(selectedName);
                        boolean moused = (float)mouseY >= yNames && (float)mouseY <= yNames + 10.0f && (float)mouseX >= xNames && (float)mouseX <= xNames + 105.0f;
                        RenderUtils.drawAlphedRect(xNames, yNames, xNames + 105.0f, yNames + 10.0f, ColorUtils.getColor(255, 55 + (select ? 0 : 200), 55 + (select ? 0 : 200), (float)(55 + (select || moused ? (moused ? (select ? 80 : 50) : 60) : 0)) * CommandGui.getAlphaPC()));
                    }
                    yNames += 10.0f;
                }
                yNames = y - scrollNames.getAnim();
                for (String name : CommandGui.tabList(false)) {
                    if (yNames < y + h - 30.0f && yNames > y - 10.0f) {
                        boolean bl = select = selectedName != null && name.contains(selectedName);
                        if (CommandGui.getAlphaPC() * 255.0f >= 33.0f) {
                            Fonts.comfortaaRegular_15.drawStringWithShadow(name, xNames + 2.0f, yNames + 3.0f, ColorUtils.getColor(255, 255, 255, 255.0f * CommandGui.getAlphaPC()));
                        }
                    }
                    yNames += 10.0f;
                }
            }
            StencilUtil.uninitStencilBuffer();
            if (yNames > y + 10.0f) {
                float hdc = 10.181818f;
                float scrollPC = scrollNames.getAnim() / hdc / (float)CommandGui.tabList(true).size() / hdc * 7.0f;
                float scrollPutHC = 10.0f * ((float)CommandGui.tabList(true).size() / hdc);
                float scrollY1 = 0.5f + scrollPC * 69.0f;
                float scrollY2 = scrollY1 + 10.0f;
                RenderUtils.drawRect(x + w - 4.0f, y + scrollY1, x + w - 3.0f, y + scrollY2, ColorUtils.getColor(255, 255, 255, 255.0f * CommandGui.getAlphaPC()));
                RenderUtils.drawLightContureRect(x + w - 4.0f, y + 0.5f, x + w - 3.0f, y + 69.5f, ColorUtils.getColor(255, 255, 255, 110.0f * CommandGui.getAlphaPC()));
            }
            StencilUtil.initStencilToWrite();
            RenderUtils.drawAlphedRect(x + 4.0f, y + h - 15.0f - 12.0f, x + w - 4.0f - 30.0f, y + h - 15.0f - 2.0f, -1);
            StencilUtil.readStencilBuffer(1);
            RenderUtils.drawAlphedRect(x + 4.0f, y + h - 15.0f - 12.0f, x + w - 4.0f, y + h - 15.0f - 2.0f, ColorUtils.getColor(255, 255, 255, 55.0f * CommandGui.getAlphaPC()));
            if (toSend != null && CommandGui.getAlphaPC() * 255.0f >= 33.0f) {
                Fonts.comfortaaRegular_15.drawStringWithShadow(toSend, x + 6.0f, y + h - 15.0f - 9.0f, ColorUtils.getColor(255, 255, 255, 255.0f * CommandGui.getAlphaPC()));
            }
            if (toSend != null && !toSend.isEmpty()) {
                StencilUtil.uninitStencilBuffer();
            }
            if (toSend == null || toSend.isEmpty()) {
                StencilUtil.readStencilBuffer(0);
            }
            RenderUtils.drawAlphedRect(x + w - 33.0f, y + h - 27.0f, x + w - 4.0f, y + h - 17.0f, ColorUtils.getColor(155, 255, 155, 135.0f * (toSend != null && !toSend.isEmpty() ? 1.0f : 0.3f) * CommandGui.getAlphaPC()));
            float f = toSend != null && !toSend.isEmpty() ? 1.0f : 0.3f;
            if (255.0f * f * CommandGui.getAlphaPC() >= 26.0f && CommandGui.getAlphaPC() * 255.0f >= 33.0f) {
                Fonts.comfortaaRegular_15.drawStringWithShadow("Send", x + w - 28.5f, y + h - 15.0f - 9.0f, ColorUtils.getColor(55, 255, 55, 255.0f * (toSend != null && !toSend.isEmpty() ? 1.0f : 0.3f) * CommandGui.getAlphaPC()));
            }
            if (toSend == null || toSend.isEmpty()) {
                StencilUtil.uninitStencilBuffer();
            }
        }
        RenderUtils.resetBlender();
        GlStateManager.enableBlend();
        GL11.glPopMatrix();
    }

    static {
        clicked = false;
        toSend = null;
        selectedName = null;
        scrollNames = new AnimationUtils(0.0f, 0.0f, 0.16f);
        selectedType = null;
    }

    public static enum CommandType {
        DUEL("/duel "),
        TPA("/tpa ");

        String name;

        private CommandType(String name) {
            this.name = name;
        }
    }
}

