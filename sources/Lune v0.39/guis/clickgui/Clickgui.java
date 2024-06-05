package me.superskidder.lune.guis.clickgui;

import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.superskidder.lune.modules.cloud.IRC;
import me.superskidder.lune.utils.render.TranslateUtil;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.render.ClickGui;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.guis.utils.InputBox;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.render.RenderUtils;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.NewMode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class Clickgui extends GuiScreen {
    public static Mod currentModul;
    public static ArrayList<Mod> modArrayList = new ArrayList<>();
    public static ModCategory currentcategory = ModCategory.Combat;

    /**
     * Window
     */
    public static int windowWeight = 400, windowHeight = 220;
    public static int windowX = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 2 - windowWeight / 2,
            windowY = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() / 2 - windowHeight / 2;

    private int startValue = 0;
    private int alpha2;
    private static int role;
    private static int role2;
    private InputBox searchbox;

    /**
     * Color
     */
    public static int color_main = new Color(38, 43, 51).getRGB();
    public static int color_middle = new Color(47, 52, 63).getRGB();
    public static int color_left = new Color(38, 43, 51).getRGB();
    public static int color_select = new Color(77, 82, 93).getRGB();
    public static int color_unselect = new Color(57, 62, 73).getRGB();

    public static int light_main = new Color(255, 255, 255).getRGB();
    public static int light_middle = new Color(232, 232, 232).getRGB();
    public static int light_left = new Color(107, 127, 255).getRGB();
    public static int light_select = new Color(105, 125, 250).getRGB();
    public static int light_unselect = new Color(225, 225, 225).getRGB();

    public static int dark_main = new Color(38, 43, 51).getRGB();
    public static int dark_middle = new Color(47, 52, 63).getRGB();
    public static int dark_left = new Color(38, 43, 51).getRGB();
    public static int dark_select = new Color(77, 82, 93).getRGB();
    public static int dark_unselect = new Color(57, 62, 73).getRGB();

    public static int rgb = ClickGui.mod.getValue() == ClickGui.Mods.Light ? new Color(50, 50, 50).getRGB()
            : new Color(250, 250, 250).getRGB();

    public PluginMarketGui pluginMarketGui;
    public CloudConfigGui cloudConfigGui;
    public TranslateUtil translate;

    public static void setLight() {
        color_main = light_main;
        color_left = light_left;
        color_middle = light_middle;
        color_select = light_select;
        color_unselect = light_unselect;
        rgb = new Color(50, 50, 50).getRGB();
    }

    public static void setDark() {
        color_main = dark_main;
        color_left = dark_left;
        color_middle = dark_middle;
        color_select = dark_select;
        color_unselect = dark_unselect;
        rgb = new Color(250, 250, 250).getRGB();
    }
    // Color/

    @Override
    public void initGui() {
        searchbox = new InputBox(1, mc.fontRendererObj, windowX + 5, windowY + 25, 65, 12);
        IRCgui.inputbox = new InputBox(1, Minecraft.getMinecraft().fontRendererObj, windowX + 5, windowY + 25, 160, 15);
        pluginMarketGui = new PluginMarketGui();
        cloudConfigGui = new CloudConfigGui();
        this.translate = new TranslateUtil(0, 0);
    }

    boolean drag = false;
    int dragX = 0;
    int dragY = 0;

    static int currentY = windowY + 45;

    static int currentY2 = 45;

    int alpha = 0;
    float sizeanim = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        translate.interpolate(this.width, this.height, 6);

        int var4 = this.windowX;
        int var5 = this.windowY;
        double xmod = this.width - (translate.getX());
        double ymod = this.height - (translate.getY());
        GlStateManager.translate(0.5 * xmod, 0, 0);
        boolean flag = (ClickGui.mod.getValue() == ClickGui.Mods.Light);


        if (flag) {
            Clickgui.setLight();
        } else {
            Clickgui.setDark();
        }

        GL11.glEnable(GL_MULTISAMPLE);

        // Drag the window
        if (isHovered(windowX, windowY, windowX + windowWeight, windowY + 15, mouseX, mouseY)
                && Mouse.isButtonDown(0)) {
            if (dragX == 0 && dragY == 0) {
                dragX = mouseX - windowX;
                dragY = mouseY - windowY;
            } else {
                windowX = mouseX - dragX;
                windowY = mouseY - dragY;
            }
            drag = true;
        } else if (dragX != 0 || dragY != 0) {
            dragX = 0;
            dragY = 0;
        }

        // Change the size of the window
        if (isHovered(windowX + windowWeight - 10, windowY + windowHeight - 10, windowX + windowWeight + 10,
                windowY + windowHeight + 10, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            if (windowWeight > 399) {
                windowWeight = mouseX - windowX;
            } else {
                windowWeight = 400;
            }

            if (windowHeight > 299) {
                windowHeight = mouseY - windowY;
            } else {
                windowHeight = 300;
            }
        }

        if (windowWeight < 400) {
            windowWeight += 5;
        }
        if (windowHeight < 300) {
            windowHeight += 5;
        }
        if (!Mouse.isButtonDown(0)) {
            drag = false;
        }

        // Main Window
        RenderUtils.drawRoundedRect(windowX, windowY, windowX + windowWeight, windowY + windowHeight, color_main,
                color_main);
        // Middle
        RenderUtils.drawRoundedRect(windowX + 75, windowY, windowX + 200, windowY + windowHeight, color_middle,
                color_middle);
        // Left
        RenderUtils.drawRoundedRect(windowX, windowY, windowX + 75, windowY + windowHeight, color_left, color_left);

        FontLoaders.F30.drawString("Lune", windowX + 15, windowY + 6, new Color(255, 255, 255).getRGB());
        FontLoaders.F16.drawString(Lune.CLIENT_Ver, windowX + 50, windowY + 16, new Color(255, 255, 255, 150).getRGB());

        // Render search box
        searchbox.xPosition = windowX + 5;
        searchbox.yPosition = windowY + 26;

        if (isHovered(searchbox.xPosition, searchbox.yPosition, 65 + searchbox.xPosition, searchbox.yPosition + 12,
                mouseX, mouseY)) {
            RenderUtils.drawRoundRect(searchbox.xPosition - 0.5F, searchbox.yPosition - 0.5F,
                    65 + searchbox.xPosition + 0.5F, searchbox.yPosition + 12 + 0.5F,
                    new Color(255, 255, 255).getRGB());
        }
        searchbox.drawTextBox();
        searchbox.setMaxStringLength(50);

        if (searchbox.getText().isEmpty()) {
            FontLoaders.F16.drawString("Search...", windowX + 8, windowY + 28, new Color(155, 155, 155).getRGB());
        }

        int dWheel = Mouse.getDWheel();
        // Role the Modules
        if (isHovered(windowX + 80, windowY, windowX + 200, windowY + windowHeight, mouseX, mouseY)) {
            if (dWheel < 0 && role2 + windowY + 45
                    + ModuleManager.getModsByCategory(currentcategory).size() * 30 > windowY + windowHeight) {
                role2 -= 8;
            }
            if (dWheel > 0 && role2 + windowY + 45 < windowY + 35) {
                role2 += 8;
            }
        }

        if (role < role2) {
            role++;
        } else if (role > role2) {
            role--;
        }

        // Role the Values of the Module
        if (isHovered(windowX + 200, windowY + 45, windowX + windowWeight - 10, windowY + windowHeight - 10, mouseX,
                mouseY)) {
            if (dWheel < 0
                    && windowY + startValue + currentModul.getValues().size() * 15 + 70 > windowY + windowHeight - 10) {
                startValue -= 8;
            }
            if (dWheel > 0 && startValue + windowY + 70 < windowY + 70) {
                startValue += 8;
            }
        }

        // Render every category
        int cateStartY = windowY + 45;
        for (ModCategory mc : ModCategory.values()) {
            if (mc == currentcategory) {
                RenderUtils.drawImage(windowX + 10, cateStartY + 5, 8, 8,
                        new ResourceLocation("client/" + mc.toString() + ".png"), new Color(255, 255, 255));
                // Render the line under the category
                RenderUtils.drawRect(windowX, windowY + currentY, windowX + 2f, windowY + currentY + 20, color_middle);
            } else {
                RenderUtils.drawImage(windowX + 10, cateStartY + 5, 8, 8,
                        new ResourceLocation("client/" + mc.toString() + ".png"), new Color(220, 220, 220));
            }
            FontLoaders.F18.drawString(mc.name(), windowX + 25, cateStartY + 5, -1);
            cateStartY += 35;
        }
        GL11.glClear(0);

        if (currentY < currentY2) {
            currentY += (currentY2 - currentY) / 10;
        } else if (currentY > currentY2) {
            currentY -= (currentY - currentY2) / 10;
        }

        if (currentY < currentY2) {
            currentY += 1;
        } else if (currentY > currentY2) {
            currentY -= 1;
        }

        // Modules

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(windowX, (sr.getScaledHeight() - (windowY + windowHeight)) * 2, windowWeight * 3,
                windowHeight * 2 - 1);
        int startModY = windowY + 15;
        int realY = startModY + role2;

        for (Mod m : ModuleManager.modList) {

            if (m.getType() == currentcategory) {
                // Filter the text of search box
                if (searchbox.getText().isEmpty()) {
                    if (m.getState()) {
                        if (isHovered(windowX + 80, realY, windowX + 195, realY + 20, mouseX, mouseY)) {
                            RenderUtils.drawRect(windowX + 80, realY, windowX + 195, realY + 20,
                                    new Color(127, 147, 255).getRGB());
                        } else {
                            RenderUtils.drawRect(windowX + 80, realY, windowX + 195, realY + 20,
                                    new Color(107, 127, 255).getRGB());
                        }
                        FontLoaders.F16.drawString(m.getName(), windowX + 90, realY + 8,
                                new Color(255, 255, 255).getRGB());
                    } else {
                        if (isHovered(windowX + 80, realY, windowX + 195, realY + 20, mouseX, mouseY)) {
                            RenderUtils.drawRect((float) (windowX + 79.5), realY, windowX + 195.5F, realY + 20.5F,
                                    new Color(107, 127, 255).getRGB());
                        }
                        RenderUtils.drawRect(windowX + 80, realY, windowX + 195, realY + 20, color_main);
                        FontLoaders.F16.drawString(m.getName(), windowX + 90, realY + 8,
                                new Color(180, 180, 180).getRGB());

                    }
                    realY += 20;
                } else if (m.getName().toLowerCase().contains(searchbox.getText().toLowerCase())) {
                    if (m.getState()) {
                        RenderUtils.drawRoundRect(windowX + 80, realY, windowX + 195, realY + 20,
                                new Color(107, 127, 255).getRGB());
                        FontLoaders.F16.drawString(m.getName(), windowX + 90, realY + 8,
                                new Color(255, 255, 255).getRGB());
                    } else {
                        if (isHovered(windowX + 80, realY, windowX + 195, realY + 20, mouseX, mouseY)) {
                            RenderUtils.drawRoundRect((float) (windowX + 79.5), realY - 0.5F, windowX + 195.5F,
                                    realY + 20.5F, new Color(107, 127, 255).getRGB());
                        }
                        RenderUtils.drawRoundRect(windowX + 80, realY, windowX + 195, realY + 20, color_main);
                        FontLoaders.F16.drawString(m.getName(), windowX + 90, realY + 8,
                                new Color(180, 180, 180).getRGB());

                    }
                    realY += 20;
                }
            }
        }
        RenderUtils.drawRoundRect(windowX + 80, windowY, windowX + 200, windowY + windowHeight,
                new Color(new Color(color_middle).getRed(), new Color(color_middle).getGreen(),
                        new Color(color_middle).getBlue(), alpha).getRGB());

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        // Render the content of the current category
        if (currentModul != null) {
            if (currentModul.getType() == ModCategory.Cloud) {
                switch (currentModul.getName()) {
                    case "IRC":
                        IRCgui.drawIRC(windowX, windowY, windowWeight, windowHeight);
                        break;
                    case "Plugin Market":
                        pluginMarketGui.drawPluginMarket(windowX, windowY, windowWeight, windowHeight);
                        break;
                    case "Cloud Config":
                        cloudConfigGui.drawCloudConfig(windowX, windowY, windowWeight, windowHeight);
                        break;
                    default:
                        System.out.println(currentModul.getName());
                        break;
                }
                FontLoaders.F30.drawString(currentModul.getName(), windowX + 210, windowY + 6, rgb);
            }

            int valueStartY = windowY + 25;
            int valueRealY = valueStartY + startValue;
            if (currentModul.getType() != ModCategory.Cloud) {
                FontLoaders.F14.drawString(currentModul.getName(), windowX + 210, windowY + 6, rgb);
                if (currentModul.getValues().size() == 0) {
                    FontLoaders.F16.drawString("Empty settings", windowX + 210, windowY + 20,
                            new Color(107, 127, 255).getRGB());
                }
            }
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(windowX, (sr.getScaledHeight() - (windowY + windowHeight)) * 2 + 20, windowWeight * 3,
                    windowHeight * 2 - 55);

            // Render values
            for (Value v : currentModul.getValues()) {
                if (v instanceof Mode) {
                    FontLoaders.F14.drawString(v.name, windowX + 210, valueRealY, new Color(204, 204, 204).getRGB());
                    if (isHovered(windowX + windowWeight - 80, valueRealY - 5, windowX + windowWeight - 15,
                            valueRealY + 5, mouseX, mouseY)) {
                        RenderUtils.drawRect((float) (windowX + windowWeight - 80.5), (float) (valueRealY - 4.5),
                                (float) (windowX + windowWeight - 14.5), (float) (valueRealY + 7.5), color_select);
                        RenderUtils.drawRect(windowX + windowWeight - 80, valueRealY - 4, windowX + windowWeight - 15,
                                valueRealY + 7f, color_unselect);
                        FontLoaders.F14.drawCenteredString(((Mode) v).getModeAsString(),
                                windowX + windowWeight - 80 + 65 / 2, valueRealY, new Color(183, 183, 183).getRGB());
                    } else {
                        RenderUtils.drawRect(windowX + windowWeight - 80, valueRealY - 4, windowX + windowWeight - 15,
                                valueRealY + 7, color_unselect);
                        FontLoaders.F14.drawCenteredString(((Mode) v).getModeAsString(),
                                windowX + windowWeight - 80 + 65 / 2, valueRealY, new Color(183, 183, 183).getRGB());
                    }
                } else if (v instanceof Bool) {
                    FontLoaders.F14.drawString(v.name, windowX + 210, valueRealY - 2,
                            new Color(204, 204, 204).getRGB());
                    if ((boolean) v.getValue()) {
                        if (isHovered(windowX + windowWeight - 36, valueRealY - 5, windowX + windowWeight - 15,
                                valueRealY + 5, mouseX, mouseY)) {
                            RenderUtils.drawRect(windowX + windowWeight - 36, valueRealY - 5,
                                    windowX + windowWeight - 15, valueRealY + 5, new Color(127, 147, 255).getRGB());
                        } else {
                            RenderUtils.drawRect(windowX + windowWeight - 36, valueRealY - 5,
                                    windowX + windowWeight - 15, valueRealY + 5, new Color(107, 127, 255).getRGB());
                        }

                        RenderUtils.drawCircle(windowX + windowWeight - 20, valueRealY, 3,
                                new Color(255, 255, 255).getRGB());
                    } else {
                        if (isHovered(windowX + windowWeight - 36, valueRealY - 5, windowX + windowWeight - 15,
                                valueRealY + 5, mouseX, mouseY)) {
                            RenderUtils.drawRect((float) (windowX + windowWeight - 36.5), (float) (valueRealY - 5.5),
                                    (float) (windowX + windowWeight - 14.5), (float) (valueRealY + 5.5), color_select);
                        }
                        RenderUtils.drawRect(windowX + windowWeight - 36, valueRealY - 5, windowX + windowWeight - 15,
                                valueRealY + 5, color_unselect);
                        RenderUtils.drawCircle(windowX + windowWeight - 30, valueRealY, 3,
                                new Color(112, 112, 112).getRGB());
                    }
                } else if (v instanceof Num) {
                    FontLoaders.F14.drawString(v.getName(), windowX + 210, valueRealY - 2,
                            new Color(204, 204, 204).getRGB());
                    FontLoaders.F14.drawString(v.getValue().toString(),
                            windowX + windowWeight - 15 - 67 + FontLoaders.F14.getStringWidth(v.getValue().toString())
                                    - FontLoaders.F14.getStringWidth(v.getValue().toString()),
                            valueRealY - 5, new Color(204, 204, 204).getRGB());
                    if (isHovered(windowX + windowWeight - 15 - 67, valueRealY, windowX + windowWeight - 15,
                            valueRealY + 3, mouseX, mouseY)) {
                        RenderUtils.drawRect(windowX + windowWeight - 15 - 67.5F, valueRealY - 0.5F,
                                windowX + windowWeight - 14.5F, valueRealY + 2.5F, new Color(50, 90, 255).getRGB());
                    }
                    RenderUtils.drawRect(windowX + windowWeight - 15 - 67, valueRealY, windowX + windowWeight - 15,
                            valueRealY + 2, color_unselect);
                    float render = (float) (67.0F
                            * (((Number) v.getValue()).floatValue() - ((Num) v).getMin().floatValue())
                            / (((Num) v).getMax().floatValue() - ((Num) v).getMin().floatValue()));

                    RenderUtils.drawRect(windowX + windowWeight - 15 - 67, valueRealY,
                            windowX + windowWeight - 15 - 67 + render, valueRealY + 2,
                            new Color(107, 127, 255).getRGB());

                    if (isHovered(windowX + windowWeight - 15 - 67, valueRealY, windowX + windowWeight - 15,
                            valueRealY + 4, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                        float render2 = ((Num) v).getMin().floatValue();
                        double max = ((Num) v).getMax().doubleValue();
                        double inc = 0.1;
                        double valAbs = (double) mouseX - ((double) (windowX + windowWeight - 15 - 67));
                        double perc = valAbs / 67.0D;
                        perc = Math.min(Math.max(0.0D, perc), 1.0D);
                        double valRel = (max - render2) * perc;
                        double val = render2 + valRel;
                        val = (double) Math.round(val * (1.0D / inc)) / (1.0D / inc);
                        ((Num) v).setValue(Double.valueOf(val));
                    }
                }
                valueRealY += 15;
            }

            // Render Script Modes
            for (NewMode v : currentModul.getNewModes()) {
                FontLoaders.F14.drawString(v.name, windowX + 210, valueRealY, new Color(204, 204, 204).getRGB());
                if (isHovered(windowX + windowWeight - 60, valueRealY - 5, windowX + windowWeight - 15, valueRealY + 5,
                        mouseX, mouseY)) {
                    RenderUtils.drawRoundRect(windowX + windowWeight - 60, valueRealY - 5, windowX + windowWeight - 15,
                            valueRealY + 5, color_select);
                    FontLoaders.F14.drawString(v.getModeAsString(),
                            windowX + windowWeight - 60 + 22 - FontLoaders.F14.getStringWidth(v.getModeAsString()) / 2,
                            valueRealY - 2, new Color(183, 183, 183).getRGB());
                } else {
                    RenderUtils.drawRoundRect(windowX + windowWeight - 60, valueRealY - 5, windowX + windowWeight - 15,
                            valueRealY + 5, color_unselect);
                    FontLoaders.F14.drawString(v.getModeAsString(),
                            windowX + windowWeight - 60 + 22 - FontLoaders.F14.getStringWidth(v.getModeAsString()) / 2,
                            valueRealY - 2, new Color(183, 183, 183).getRGB());
                }
            }

            RenderUtils.drawRoundRect(windowX + 200, windowY, windowX + windowWeight, windowY + windowHeight - 10,
                    new Color(new Color(color_main).getRed(), new Color(color_main).getGreen(),
                            new Color(color_main).getBlue(), alpha2).getRGB());
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        if (alpha > 0) {
            alpha -= alpha / 10;
        }
        if (alpha > 0) {
            alpha -= 1;
        }

        if (alpha2 > 0) {
            alpha2 -= alpha2 / 40;
        }
        if (alpha2 > 0) {
            alpha2 -= 0.5;
        }
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (character == '\t' && (this.searchbox.isFocused())) {
            this.searchbox.setFocused(!this.searchbox.isFocused());
        }
        this.searchbox.textboxKeyTyped(character, key);

        if (IRCgui.inputbox != null) {
            if (character == '\t' && (IRCgui.inputbox.isFocused())) {
                this.searchbox.setFocused(!IRCgui.inputbox.isFocused());
            }
        }

        if (IRCgui.inputbox.isFocused() && (character == '\t' || key == Keyboard.KEY_RETURN)) {
            System.out.println("IRC SEND MESSAGE:" + IRCgui.inputbox.getText());
            IRC.sendMessage(("MSG@" + Minecraft.getMinecraft().thePlayer.getName() + "@" + ChatFormatting.BLUE
                    + Lune.CLIENT_NAME + ChatFormatting.WHITE + "@" + IRCgui.inputbox.getText()).replaceAll("&", "§"));
            IRCgui.inputbox.setText("");
        }

        if (IRCgui.inputbox != null) {
            IRCgui.inputbox.textboxKeyTyped(character, key);
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);

        if (!isHovered(windowX, windowY, windowX + windowWeight, windowY + windowHeight, mouseX, mouseY)) {
            return;
        }

        searchbox.mouseClicked(mouseX, mouseY, mouseButton);
        if (IRCgui.inputbox != null) {
            IRCgui.inputbox.mouseClicked(mouseX, mouseY, mouseButton);
        }

        int cateStartY = windowY + 45;
        for (ModCategory mc : ModCategory.values()) {
            if (isHovered(windowX, cateStartY, windowX + 75, cateStartY + 35, mouseX, mouseY)) {
                currentcategory = mc;
                currentY2 = cateStartY - windowY;
                startValue = 0;
                role = 0;
                role2 = 0;
                alpha = 255;
            }
            cateStartY += 35;
        }

        int startModY = windowY + 15;
        int realModY = startModY + role2;
        for (Mod m : ModuleManager.modList) {
            if (m.getName().toLowerCase().contains(searchbox.getText().toLowerCase())) {
                if (m.getType() == currentcategory) {
                    if (isHovered(windowX + 80, realModY, windowX + 195, realModY + 20, mouseX, mouseY)
                            && mouseButton == 0) {
                        m.setStage(!m.getState());
                    }
                    if (isHovered(windowX + 80, realModY, windowX + 195, realModY + 20, mouseX, mouseY)
                            && mouseButton == 1) {
                        currentModul = m;
                        alpha2 = 255;
                    }
                    realModY += 20;
                }
            }
        }

        if (currentModul != null) {
            int valueStartY = windowY + 25;
            int valueRealY = valueStartY + startValue;
            RenderUtils.drawRoundRect(windowX + 170, windowY + 45, windowX + windowWeight - 10,
                    windowY + windowHeight - 10, -1);
            FontLoaders.F16.drawString(currentModul.getName(), windowX + 175, windowY + 50,
                    new Color(50, 50, 50).getRGB());
            FontLoaders.F14.drawString(currentModul.getName(), windowX + 175, windowY + 58,
                    new Color(191, 191, 191).getRGB());
            if (currentModul.getValues().size() == 0) {
                FontLoaders.F16.drawString("Empty settings", windowX + 180, windowY + 20, new Color(0, 0, 0).getRGB());
            }

            for (Value v : currentModul.getValues()) {
                if (v instanceof Mode) {
                    if (isHovered((float) (windowX + windowWeight - 80), valueRealY - 5, windowX + windowWeight - 15,
                            valueRealY + 5, mouseX, mouseY)) {
                        if (Arrays.binarySearch(((Mode<?>) v).getModes(), (v.getValue()))
                                + 1 < ((Mode<?>) v).getModes().length) {
                            v.setValue(((Mode<?>) v)
                                    .getModes()[Arrays.binarySearch(((Mode<?>) v).getModes(), (v.getValue())) + 1]);
                        } else {
                            v.setValue(((Mode<?>) v).getModes()[0]);
                        }
                    }
                } else if (v instanceof Bool) {
                    if (isHovered(windowX + windowWeight - 36, valueRealY - 5, windowX + windowWeight - 15,
                            valueRealY + 5, mouseX, mouseY)) {
                        v.setValue(!(boolean) v.getValue());
                    }
                }
                valueRealY += 15;
            }

            for (NewMode newMode : currentModul.getNewModes()) {
                if (isHovered(windowX + windowWeight - 60, valueRealY - 5, windowX + windowWeight - 15, valueRealY + 5,
                        mouseX, mouseY)) {
                    if (Arrays.binarySearch(newMode.getModes(), (newMode.getValue()))
                            + 1 < (newMode).getModes().length) {
                        newMode.setValue((newMode)
                                .getModes()[Arrays.binarySearch((newMode).getModes(), (newMode.getValue())) + 1]);
                    } else {
                        newMode.setValue((newMode).getModes()[0]);
                    }
                }
            }
        }
        this.pluginMarketGui.mouseClicked(mouseX, mouseY, mouseButton);
        this.cloudConfigGui.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

}
