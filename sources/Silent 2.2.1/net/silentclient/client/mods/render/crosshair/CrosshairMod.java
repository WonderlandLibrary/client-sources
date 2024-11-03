package net.silentclient.client.mods.render.crosshair;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.RenderEvent;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.settings.RenderMod;
import net.silentclient.client.utils.DisplayUtil;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.Sounds;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class CrosshairMod extends Mod {

    private int componentWidth = 0;
    private final CLayoutManager layoutManager;

    boolean[][] cDefault = new boolean[][]{
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, true, true, false, true, false, true, true, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false}
    };

	public CrosshairMod() {
		super("Crosshair", ModCategory.MODS, "silentclient/icons/mods/crosshair.png");
        layoutManager = new CLayoutManager();
    }
	
	@Override
	public void setup() {
        this.addBooleanSetting("Preset Crosshair", this, false);

        ArrayList<String> presets = new ArrayList<String>();
        presets.add("1");
        presets.add("2");
        presets.add("3");
        presets.add("4");
        presets.add("5");
        presets.add("6");
        presets.add("7");
        presets.add("8");
        presets.add("9");
        presets.add("10");
        presets.add("11");
        presets.add("12");
        presets.add("13");
        presets.add("14");
        presets.add("15");
        presets.add("16");
        presets.add("17");
        presets.add("18");
        presets.add("19");
        presets.add("20");
        presets.add("21");
        presets.add("22");
        presets.add("23");
        presets.add("24");
        presets.add("25");
        presets.add("26");
        presets.add("27");
        presets.add("28");
        presets.add("29");
        presets.add("30");
        presets.add("31");
        presets.add("32");
        presets.add("33");
        presets.add("34");
        presets.add("35");
        presets.add("36");

        this.addModeSetting("Preset ID", this, "1", presets);


		ArrayList<String> options = new ArrayList<String>();
		
		options.add("Cross");
		options.add("Circle");
		options.add("Arrow");
		options.add("Triangle");
		options.add("Square");
		options.add("None");

        this.addBooleanSetting("Use custom shape", this, false);
        this.addCellSetting("Shape", this, cDefault);
		this.addModeSetting("Type", this, "Cross", options);
		this.addColorSetting("Crosshair Color", this, new Color(255, 255, 255));
		this.addColorSetting("Player Color", this, new Color(176,46,38));
		this.addColorSetting("Hostile Color", this, new Color(0,0,0));
		this.addColorSetting("Passive Color", this, new Color(0,0,0));
		this.addColorSetting("Outline Color", this, new Color(0,0,0));
		this.addColorSetting("Dot Color", this, new Color(0,0,0));
		this.addSliderSetting("Width", this, 3, 1, 40, true);
		this.addSliderSetting("Height", this, 3, 1, 40, true);
		this.addSliderSetting("Gap", this, 2, 1, 40, true);
		this.addSliderSetting("Thickness", this, 1, 1, 10, true);
		this.addSliderSetting("Scale", this, 1, 0.5, 2, false);
		this.addBooleanSetting("Highlight Hostile Mobs", this, false);
		this.addBooleanSetting("Highlight Passive Mobs", this, false);
		this.addBooleanSetting("Highlight Players", this, false);
		this.addBooleanSetting("Crosshair Outline", this, false);
		this.addBooleanSetting("Crosshair Dot", this, false);
        this.addBooleanSetting("Vanilla Blendering", this, true);
	}

    private boolean[][] getCells() {
        return Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Shape").getCells();
    }

    @Override
    public int customComponentHeight() {
        boolean preset = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean();
        int height = 30;

        if(preset) {
            height += 30;
            int crossIndex = 1;
            for(@SuppressWarnings("unused") String presetID : Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getOptions()) {
                crossIndex++;
                if(crossIndex / 6 == 1) {
                    crossIndex = 1;
                    height += 30;
                }
            }
        }

        return height;
    }

    @Override
	public int customComponentLiteHeight() {
        boolean preset = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean();
        int height = 30;

        if(preset) {
            height += 30;
            int crossIndex = 1;
            for(@SuppressWarnings("unused") String presetID : Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getOptions()) {
                crossIndex++;
                if(crossIndex / 11 == 1) {
                    crossIndex = 1;
                    height += 30;
                }
            }
        }

        return height;
	}

    @Override
    public int customComponentLiteWidth() {
        return componentWidth;
    }

    @Override
    public int customComponentWidth() {
        return customComponentLiteWidth();
    }

    @Override
    public MouseCursorHandler.CursorType renderCustomComponent(int x, int y, int width, int height, int mouseX, int mouseY) {
        MouseCursorHandler.CursorType cursorType = null;
        CustomFontRenderer font = new CustomFontRenderer();
        font.setRenderMode(CustomFontRenderer.RenderMode.CUSTOM);
        GlStateManager.pushMatrix();
        int widthComponent = 144;
        int buttonWidth = (widthComponent) / 2;
        boolean preset = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean();
        RenderUtils.drawRect(x, y, buttonWidth, 20, preset ? new Color(0, 0, 0).getRGB() : -1);
        font.drawString("Custom", x + ((buttonWidth / 2) - (font.getStringWidth("Custom", 14, SilentFontRenderer.FontType.TITLE) / 2)), y + 3, preset ? -1 : new Color(0, 0, 0).getRGB(), 14);
        RenderUtils.drawRect(x + buttonWidth, y, buttonWidth, 20, preset ? -1 : new Color(0, 0, 0).getRGB());
        font.drawString("Preset", x + buttonWidth + ((buttonWidth / 2) - (font.getStringWidth("Preset", 14, SilentFontRenderer.FontType.TITLE) / 2)), y + 3, preset ? new Color(0, 0, 0).getRGB() : -1, 14);
        componentWidth = width;
        if(MouseUtils.isInside(mouseX, mouseY, x, y, buttonWidth, 20) || MouseUtils.isInside(mouseX, mouseY, x + buttonWidth, y, buttonWidth, 20)) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
        }
        if(preset) {
            int crossIndex = 1;
            int spacing = 10;
            int crossY = y + 25;
            String selected = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getValString();
            RenderUtil.drawRoundedRect(x + spacing, crossY,  22, 22, 5, new Color(0, 0, 0).getRGB());
            RenderUtil.drawRoundedOutline(x + spacing, crossY,  22, 22, 5, 3, -1);

            RenderUtil.waitDrawImage(new ResourceLocation("silentclient/mods/crosshair/crosshair" + selected + ".png"), x + spacing + ((22 / 2) - (15 / 2)), crossY - 1 + ((22 / 2) - (15 / 2)), 15, 15, false);
            spacing += 25;
            crossIndex += 1;
            for(String presetID : Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getOptions()) {
                if(selected.equals(presetID)) {
                    continue;
                }
                RenderUtil.drawRoundedRect(x + spacing, crossY,  22, 22, 5, new Color(0, 0, 0).getRGB());

                if(MouseUtils.isInside(mouseX, mouseY, x + spacing, crossY, 22, 22)) {
                    RenderUtil.drawRoundedOutline(x + spacing, crossY,  22, 22, 5, 3, new Color(255, 255, 255, 127).getRGB());
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }

                RenderUtil.waitDrawImage(new ResourceLocation("silentclient/mods/crosshair/crosshair" + presetID + ".png"), x + spacing + ((22 / 2) - (15 / 2)), crossY - 1 + ((22 / 2) - (15 / 2)), 15, 15, false);
                spacing += 25;
                crossIndex += 1;
                if(crossIndex / 6 == 1) {
                    crossIndex = 1;
                    spacing = 10;
                    crossY += 30;
                }
            }
        }
        GlStateManager.popMatrix();
        return cursorType;
    }

    @Override
    public void customComponentClick(int x, int y, int mouseX, int mouseY, int mouseButton, GuiScreen screen) {
        int widthComponent = 144;
        int buttonWidth = (widthComponent) / 2;
        if(MouseUtils.isInside(mouseX, mouseY, x, y, buttonWidth, 20)) {
            Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").setValBoolean(false);
            Sounds.playButtonSound();
        }
        if(MouseUtils.isInside(mouseX, mouseY, x + buttonWidth, y, buttonWidth, 20)) {
            Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").setValBoolean(true);
            Sounds.playButtonSound();
        }
        boolean preset = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean();
        if(preset) {
            int crossIndex = 1;
            int spacing = 10;
            int crossY = y + 35;
            spacing += 25;
            crossIndex += 1;
            String selected = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getValString();
            for(String presetID : Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getOptions()) {
                if(presetID.equals(selected)) {
                    continue;
                }
                if(MouseUtils.isInside(mouseX, mouseY, x + spacing, crossY - 1, 22, 22)) {
                    Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").setValString(presetID);
                }
                spacing += 25;
                crossIndex += 1;
                if(crossIndex / 6 == 1) {
                    crossIndex = 1;
                    spacing = 10;
                    crossY += 30;
                }
            }
        }
    }

    @Override
    public MouseCursorHandler.CursorType renderCustomLiteComponent(int x, int y, int width, int height, int mouseX, int mouseY) {
        MouseCursorHandler.CursorType cursorType = null;
        CustomFontRenderer font = new CustomFontRenderer();
        font.setRenderMode(CustomFontRenderer.RenderMode.CUSTOM);
        GlStateManager.pushMatrix();
        int widthComponent = 190 * 2;
        int buttonWidth = (widthComponent - 108) / 2;
        boolean preset = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean();
        RenderUtils.drawRect(x, y, buttonWidth, 20, preset ? new Color(0, 0, 0).getRGB() : -1);
        font.drawString("Custom", x + ((buttonWidth / 2) - (font.getStringWidth("Custom", 14, SilentFontRenderer.FontType.TITLE) / 2)), y + 3, preset ? -1 : new Color(0, 0, 0).getRGB(), 14);
        RenderUtils.drawRect(x + buttonWidth, y, buttonWidth, 20, preset ? -1 : new Color(0, 0, 0).getRGB());
        font.drawString("Preset", x + buttonWidth + ((buttonWidth / 2) - (font.getStringWidth("Preset", 14, SilentFontRenderer.FontType.TITLE) / 2)), y + 3, preset ? new Color(0, 0, 0).getRGB() : -1, 14);
        componentWidth = width;
        if(MouseUtils.isInside(mouseX, mouseY, x, y, buttonWidth, 20) || MouseUtils.isInside(mouseX, mouseY, x + buttonWidth, y, buttonWidth, 20)) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
        }
        if(preset) {
            int crossIndex = 1;
            int spacing = 0;
            int crossY = y + 30;
            String selected = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getValString();
            RenderUtil.drawRoundedRect(x + spacing, crossY - 1,  22, 22, 5, new Color(0, 0, 0).getRGB());
            RenderUtil.drawRoundedOutline(x + spacing, crossY - 1,  22, 22, 5, 3, -1);
            RenderUtil.waitDrawImage(new ResourceLocation("silentclient/mods/crosshair/crosshair" + selected + ".png"), x + spacing + ((22 / 2) - (15 / 2)), crossY - 1 + ((22 / 2) - (15 / 2)), 15, 15, false);
            spacing += 25;
            crossIndex += 1;
            for(String presetID : Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getOptions()) {
                if(selected.equals(presetID)) {
                    continue;
                }
                RenderUtil.drawRoundedRect(x + spacing, crossY - 1,  22, 22, 5, new Color(0, 0, 0).getRGB());

                if(MouseUtils.isInside(mouseX, mouseY, x + spacing, crossY - 1, 22, 22)) {
                    RenderUtil.drawRoundedOutline(x + spacing, crossY - 1,  22, 22, 5, 3, new Color(255, 255, 255, 127).getRGB());
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }

                RenderUtil.waitDrawImage(new ResourceLocation("silentclient/mods/crosshair/crosshair" + presetID + ".png"), x + spacing + ((22 / 2) - (15 / 2)), crossY - 1 + ((22 / 2) - (15 / 2)), 15, 15, false);
                spacing += 25;
                crossIndex += 1;
                if(crossIndex / 11 == 1) {
                    crossIndex = 1;
                    spacing = 0;
                    crossY += 30;
                }
            }
        }
        GlStateManager.popMatrix();

        return cursorType;
    }

    @Override
    public void customLiteComponentClick(int x, int y, int mouseX, int mouseY, int mouseButton, GuiScreen screen) {
        int widthComponent = 190 * 2;
        int buttonWidth = (widthComponent - 108) / 2;
        if(MouseUtils.isInside(mouseX, mouseY, x, y, buttonWidth, 20)) {
            Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").setValBoolean(false);
            Sounds.playButtonSound();
        }
        if(MouseUtils.isInside(mouseX, mouseY, x + buttonWidth, y, buttonWidth, 20)) {
            Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").setValBoolean(true);
            Sounds.playButtonSound();
        }
        boolean preset = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean();
        if(preset) {
            int crossIndex = 1;
            int spacing = 0;
            int crossY = y + 30;
            spacing += 25;
            crossIndex += 1;
            String selected = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getValString();
            for(String presetID : Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getOptions()) {
                if(presetID.equals(selected)) {
                    continue;
                }
                if(MouseUtils.isInside(mouseX, mouseY, x + spacing, crossY - 1, 22, 22)) {
                    Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").setValString(presetID);
                }
                spacing += 25;
                crossIndex += 1;
                if(crossIndex / 11 == 1) {
                    crossIndex = 1;
                    spacing = 0;
                    crossY += 30;
                }
            }
        }
    }

    @EventTarget
	public void displayCrosshair(RenderEvent event)
    {
		if(!Client.getInstance().getSettingsManager().getSettingByClass(RenderMod.class, "Crosshair in F5").getValBoolean() && !(this.mc.gameSettings.thirdPersonView < 1)) {
			return;
		}
        if(Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean()) {
            return;
        }
        if(Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Use custom Shape").getValBoolean()) {
            renderCustomShape();
        } else {
            renderCrosshair();
        }
    }

    public void renderCustomShape() {
        ScaledResolution res = new ScaledResolution(mc);

        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if (getCells()[row][col] && isToggled()) {
                    RenderUtils.drawRect(
                            (float) res.getScaledWidth() / 2 - 5 + col,
                            (float) res.getScaledHeight() / 2 - 5 + row,
                            1, 1, Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Color").getValColor().getRGB()
                    );
                }
            }
        }
    }

    public void renderCrosshair() {
        String type =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Type").getValString();
        Color crosshairColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Color").getValColor();
        Color playerColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Player Color").getValColor();
        Color hostileColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Hostile Color").getValColor();
        Color passiveColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Passive Color").getValColor();
        Color dotColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Dot Color").getValColor();
        int gap =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Gap").getValInt();
        float scale =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Scale").getValFloat();
        boolean hostile =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Highlight Hostile Mobs").getValBoolean();
        boolean passive =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Highlight Passive Mobs").getValBoolean();
        boolean players =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Highlight Players").getValBoolean();
        boolean dot =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Dot").getValBoolean();

        int[] aint = DisplayUtil.getScreenSize();
        int i = aint[0] / 2;
        int j = aint[1] / 2;
        GlStateManager.pushMatrix();

        {
            if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 1)
            {
                double[] adouble = DisplayUtil.getScreenSizeDouble();
                GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
                GL11.glMatrixMode(GL11.GL_PROJECTION);
                GL11.glLoadIdentity();
                GL11.glOrtho(0.0D, adouble[0], adouble[1], 0.0D, 1000.0D, 3000.0D);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glLoadIdentity();
                GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
            }

            int k = gap;

            Color color = crosshairColor;

            if (Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
            {
                if (Minecraft.getMinecraft().objectMouseOver.entityHit instanceof EntityPlayer && players)
                {
                    color = playerColor;
                }
                else if (Minecraft.getMinecraft().objectMouseOver.entityHit instanceof EntityLiving)
                {
                    if (hostile && hostileEntity(Minecraft.getMinecraft().objectMouseOver.entityHit))
                    {
                        color = hostileColor;
                    }
                    else if (passive)
                    {
                        color = passiveColor;
                    }
                }
            }

            if (Client.getInstance().getScaleFactor() < 2)
            {
                k *= 2;
            }
            GlStateManager.pushMatrix();
            GlUtils.startScale(i, j, scale);

            switch (type.toUpperCase())
            {
                case "CIRCLE":
                    displayCircleCrosshair(i, j, k, color);
                    break;

                case "SQUARE":
                    displaySquareCrosshair(i, j, k, color);
                    break;

                case "ARROW":
                    displayArrowCrosshair(i, j, k, color);
                    break;

                case "CROSS":
                    displayCrossCrosshair(i, j, k, color);
                    break;

                case "TRIANGLE":
                    displayTriangle(i, j, k, color);
            }

            if (dot)
            {
                DisplayUtil.displayFilledRectangle(i, j, i + 1, j + 1, dotColor);
            }

            GlUtils.stopScale();
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();

        }
    }

    private void displayTriangle(int drawX, int drawY, int renderGap, Color color)
    {
		Color outlineColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Outline Color").getValColor();
		int width =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Width").getValInt();
		int height =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Height").getValInt();
		boolean outline =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Outline").getValBoolean();
        DisplayUtil.drawLines(new float[] {(float)drawX, (float)(drawY - renderGap) - (float)height / 2.0F, (float)(drawX - renderGap) - (float)width / 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F, (float)(drawX - renderGap) - (float)width / 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F, (float)(drawX + renderGap) + (float)width / 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F, (float)(drawX + renderGap) + (float)width / 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F, (float)drawX, (float)(drawY - renderGap) - (float)height / 2.0F}, 2.0F, color, true);

        if (outline)
        {
            DisplayUtil.drawLines(new float[] {(float)drawX, (float)(drawY - renderGap) - (float)height / 2.0F - 2.0F, (float)(drawX - renderGap) - (float)width / 2.0F - 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F + 1.0F, (float)(drawX - renderGap) - (float)width / 2.0F - 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F + 1.0F, (float)(drawX + renderGap) + (float)width / 2.0F + 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F + 1.0F, (float)(drawX + renderGap) + (float)width / 2.0F + 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F + 1.0F, (float)drawX, (float)(drawY - renderGap) - (float)height / 2.0F - 2.0F}, 2.0F, outlineColor, true);
            DisplayUtil.drawLines(new float[] {(float)drawX, (float)(drawY - renderGap) - (float)height / 2.0F + 2.0F, (float)(drawX - renderGap) - (float)width / 2.0F + 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F - 1.0F, (float)(drawX - renderGap) - (float)width / 2.0F + 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F - 1.0F, (float)(drawX + renderGap) + (float)width / 2.0F - 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F - 1.0F, (float)(drawX + renderGap) + (float)width / 2.0F - 2.0F, (float)(drawY + renderGap) + (float)height / 2.0F - 1.0F, (float)drawX, (float)(drawY - renderGap) - (float)height / 2.0F + 2.0F}, 2.0F, outlineColor, true);
        }
    }

    private void displayCrossCrosshair(int screenWidth, int screenHeight, int renderGap, Color color)
    {
		Color outlineColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Outline Color").getValColor();
		int width =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Width").getValInt();
		int height =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Height").getValInt();
		int thickness =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Thickness").getValInt();
		boolean outline =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Outline").getValBoolean();
        int i = thickness / 2;

        if (outline)
        {
            DisplayUtil.displayFilledRectangle(screenWidth - i - 1, screenHeight - renderGap + 1, screenWidth - i, screenHeight - renderGap - height + 1, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth + i + 1, screenHeight - renderGap + 1, screenWidth + i + 2, screenHeight - renderGap - height + 1, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth - i - 1, screenHeight - renderGap + 2, screenWidth + i + 2, screenHeight - renderGap + 1, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth - i - 1, screenHeight - renderGap - height, screenWidth + i + 2, screenHeight - renderGap - height + 1, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth - i - 1, screenHeight + renderGap, screenWidth - i, screenHeight + renderGap + height + 1, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth + i + 1, screenHeight + renderGap, screenWidth + i + 2, screenHeight + renderGap + height + 1, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth - i - 1, screenHeight + renderGap - 1, screenWidth + i + 2, screenHeight + renderGap, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth - i - 1, screenHeight + renderGap + height, screenWidth + i + 2, screenHeight + renderGap + height + 1, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth + renderGap, screenHeight - i - 1, screenWidth + renderGap + width, screenHeight - i, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth + renderGap, screenHeight + i + 1, screenWidth + renderGap + width, screenHeight + i + 2, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth + renderGap - 1, screenHeight - i - 1, screenWidth + renderGap, screenHeight + i + 2, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth + renderGap + width, screenHeight - i - 1, screenWidth + renderGap + width + 1, screenHeight + i + 2, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth - renderGap + 1, screenHeight - i - 1, screenWidth - renderGap - width, screenHeight - i, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth - renderGap + 1, screenHeight + i + 1, screenWidth - renderGap - width, screenHeight + i + 2, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth - renderGap + 2, screenHeight - i - 1, screenWidth - renderGap + 1, screenHeight + i + 2, outlineColor);
            DisplayUtil.displayFilledRectangle(screenWidth - renderGap - width, screenHeight - i - 1, screenWidth - renderGap - width + 1, screenHeight + i + 2, outlineColor);
        }

        DisplayUtil.displayFilledRectangle(screenWidth - i, screenHeight - renderGap + 1, screenWidth + i + 1, screenHeight - renderGap - height + 1, color);
        DisplayUtil.displayFilledRectangle(screenWidth - i, screenHeight + renderGap, screenWidth + i + 1, screenHeight + renderGap + height, color);
        DisplayUtil.displayFilledRectangle(screenWidth - renderGap + 1, screenHeight - thickness / 2, screenWidth - renderGap - width + 1, screenHeight + i + 1, color);
        DisplayUtil.displayFilledRectangle(screenWidth + renderGap, screenHeight - thickness / 2, screenWidth + renderGap + width, screenHeight + i + 1, color);
        GL11.glLineWidth(2.0F);
    }

    private void displayCircleCrosshair(int drawX, int drawY, int renderGap, Color color)
    {
		Color outlineColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Outline Color").getValColor();
		int thickness =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Thickness").getValInt();
		boolean outline =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Outline").getValBoolean();
        int i = outline ? thickness + 4 : thickness;

        if (i > 10)
        {
            i = 10;
        }

        DisplayUtil.drawCircle((float)drawX, (float)drawY, (float)renderGap, (float)i, color, true);

        if (outline)
        {
            DisplayUtil.drawCircle((float)drawX, (float)drawY, (float)renderGap + (float)i / 2.0F - 1.5F, 2.0F, outlineColor, true);
            DisplayUtil.drawCircle((float)drawX, (float)drawY, (float)renderGap - (float)i / 2.0F + 1.5F, 2.0F, outlineColor, true);
        }
    }

    private void displaySquareCrosshair(int drawX, int drawY, int renderGap, Color color)
    {
    	Color outlineColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Outline Color").getValColor();
		int thickness =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Thickness").getValInt();
		boolean outline =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Outline").getValBoolean();
        DisplayUtil.displayFilledRectangle(drawX - renderGap - thickness, drawY - renderGap - thickness, drawX + renderGap + thickness, drawY - renderGap, color);
        DisplayUtil.displayFilledRectangle(drawX - renderGap - thickness, drawY + renderGap, drawX + renderGap + thickness, drawY + renderGap + thickness, color);
        DisplayUtil.displayFilledRectangle(drawX - renderGap - thickness, drawY - renderGap, drawX - renderGap, drawY + renderGap, color);
        DisplayUtil.displayFilledRectangle(drawX + renderGap, drawY - renderGap, drawX + renderGap + thickness, drawY + renderGap, color);
        GL11.glLineWidth(2.0F);

        if (outline)
        {
            DisplayUtil.displayRectangle(drawX - renderGap, drawY - renderGap, drawX + renderGap, drawY + renderGap, outlineColor);
            DisplayUtil.displayRectangle(drawX - renderGap - thickness, drawY - renderGap - thickness, drawX + renderGap + thickness, drawY + renderGap + thickness, outlineColor);
        }
    }

    private void displayArrowCrosshair(int screenWidth, int screenHeight, int renderGap, Color color)
    {
    	Color outlineColor =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Outline Color").getValColor();
		int width =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Width").getValInt();
		int height =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Height").getValInt();
		int gap =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Gap").getValInt();
		int thickness =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Thickness").getValInt();
		boolean outline =  Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Outline").getValBoolean();
        double d0 = (double)renderGap / ((double)gap + 1.0D * ((double)gap + 5.0D) * 2.0D);
        int i = (int)((double)width + d0 * (double)(width + 5) * 2.0D);
        int j = (int)((double)height + d0 * (double)(height + 5) * 2.0D);
        GL11.glPushMatrix();

        if (outline)
        {
            GL11.glLineWidth((float)(thickness + 3));
            DisplayUtil.displayLine(screenWidth - i - 1, screenHeight + j + 1, screenWidth, screenHeight, outlineColor);
            DisplayUtil.displayLine(screenWidth, screenHeight, screenWidth + i + 1, screenHeight + j + 1, outlineColor);
        }

        GL11.glLineWidth((float)(thickness + 1));
        DisplayUtil.displayLine(screenWidth - i, screenHeight + j, screenWidth, screenHeight, color);
        DisplayUtil.displayLine(screenWidth, screenHeight, screenWidth + i, screenHeight + j, color);
        GL11.glLineWidth(2.0F);
        GL11.glPopMatrix();
    }

    public static boolean hostileEntity(Entity e)
    {
        return !(e instanceof EntityBat) && !(e instanceof EntityChicken) && !(e instanceof EntityCow) && !(e instanceof EntityHorse) && !(e instanceof EntityOcelot) && !(e instanceof EntityPig) && !(e instanceof EntitySheep) && !(e instanceof EntitySquid) && !(e instanceof EntityVillager) && !(e instanceof EntityWolf);
    }
}
