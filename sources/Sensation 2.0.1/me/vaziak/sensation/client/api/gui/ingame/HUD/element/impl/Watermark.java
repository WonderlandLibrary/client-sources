package me.vaziak.sensation.client.api.gui.ingame.HUD.element.impl;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.event.EventSystem;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Quadrant;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.ColorProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.visual.Overlay;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.FontRenderer;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

/**
 * @author antja03
 */
public class Watermark extends Element {
    public BooleanProperty   enabled    = new BooleanProperty("Enabled", "Show Watermark",null, true);
    public StringsProperty   mode    = new StringsProperty("Mode", "Changes watermark theme.",() -> enabled.getValue(), false, true, new String[]{"Image", "Exhibition", "Sensation", "Astolfo", "New"});
    public BooleanProperty   useCustomFont    = new BooleanProperty("Custom Font", "Use verdana as the font.",() -> enabled.getValue(), true);
    public ColorProperty backgroundColor = new ColorProperty("Color", "",
            () -> enabled.getValue(), 1f, 0f, 1f, 255);


    private FontRenderer fontRenderer;

    public Watermark() {
        super("Watermark", Quadrant.TOP_LEFT, 2, 2);
        registerValue(enabled);
        registerValue(mode);
        registerValue(useCustomFont);
        registerValue(backgroundColor);
		EventSystem.hook(this);
    }
    
    @Override
    public void drawElement(boolean editor) {
        this.editX = positionX;
        this.editY = positionY;
        this.width = mode.getValue().get("Image") ? 32 : Fonts.verdanaN.getStringWidth("Sensation") + 5;
        this.height = mode.getValue().get("Image") ? 32 : Fonts.verdanaN.getHeight() + 5;

        Overlay hud = (Overlay) Sensation.instance.cheatManager.getCheatRegistry().get("Overlay");
        ScaledResolution scalres = new ScaledResolution(Minecraft.getMinecraft());
        if (Minecraft.getMinecraft().getCurrentServerData() != null) {
        	if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("hypixel")) {
        		if (Minecraft.getMinecraft().getCurrentServerData().pingToServer >=  80 && !Sensation.instance.developerMode) {
        	           Fonts.arial.drawCenteredStringWithShadow(EnumChatFormatting.RED + "WARNING: HIGH PING IMPACTS CLIENT PERFOMANCE" , scalres.getScaledWidth() / 2, scalres.getScaledHeight() / 2 + 150, ColorCreator.createRainbowFromOffset(3200, 1, 10));
        	           
        		}
        	}
        }
        if (!hud.getState())
            return;
        if (enabled.getValue()) {
            if (mode.getValue().get("New")) {
                Minecraft mc = Minecraft.getMinecraft();

                int ms = 0;


                Fonts.f20.drawStringWithShadow("S" + EnumChatFormatting.WHITE + "ensation", positionX, positionY, backgroundColor.getValue().getRGB());

                if (mc.getCurrentServerData() == null) {
                    return;
                }
                ms = (int) Minecraft.getMinecraft().getCurrentServerData().pingToServer;
                Fonts.f20.drawStringWithShadow(EnumChatFormatting.GRAY + "MS: " + EnumChatFormatting.WHITE + ms + EnumChatFormatting.GRAY + "ms", positionX, positionY + 10, -1);
                //Fonts.arial.drawStringWithShadow("Purchase Sensation at https://azuma.club", 2, new ScaledResolution(mc).getScaledHeight() + 10, -1);
            } else if (mode.getValue().get("Sensation")) {
                String pattern = "HH:mm";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String date = simpleDateFormat.format(new Date());
                if (useCustomFont.getValue()) {
                    Fonts.arial.drawStringWithShadow(EnumChatFormatting.RED + "S" + EnumChatFormatting.GRAY + "ensation b" + Sensation.instance.version, positionX, positionY, new Color(255, 71, 71).getRGB());
                } else {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(EnumChatFormatting.RED + "S" + EnumChatFormatting.GRAY + "ensation b" + Sensation.instance.version, (float) positionX, (float) positionY, new Color(255, 71, 71).getRGB());
                }
            } else if (mode.getValue().get("Image")) {
                GlStateManager.color(1f, 1f, 1f, 1f);
                Draw.drawImg(new ResourceLocation("client/gui/logo/64x64.png"), positionX - 1, positionY - 1, 32, 32);
            } else if (mode.getValue().get("Exhibition")) {
                String pattern = "HH:mm";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String date = simpleDateFormat.format(new Date());
                if (useCustomFont.getValue()) {
                    Fonts.arial.drawStringWithShadow("S" + EnumChatFormatting.WHITE + "ensation " + EnumChatFormatting.GRAY + "[" + EnumChatFormatting.WHITE + date + EnumChatFormatting.GRAY + "]", positionX, positionY, ColorCreator.createRainbowFromOffset(3200, 1, 10));
                } else {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("S" + EnumChatFormatting.WHITE + "ensation " + EnumChatFormatting.GRAY + "[" + EnumChatFormatting.WHITE + date + EnumChatFormatting.GRAY + "]", (float) positionX, (float) positionY, ColorCreator.createRainbowFromOffset(3200, 1, 10));
                }
            } else if (mode.getValue().get("Asstolfo")) {
            	   Fonts.bf20.drawStringWithShadow("Ass" + EnumChatFormatting.GRAY + "tolfo", positionX, positionY, new Color(60,179,113).getRGB());
            }
        }
    }
}
