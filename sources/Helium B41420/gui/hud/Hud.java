package rip.helium.gui.hud;

//import javafx.scene.control.Toggle;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import me.hippo.systems.lwjeb.annotation.*;

import java.awt.Color;
import java.text.*;
import java.util.*;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.*;
import rip.helium.*;
import rip.helium.event.minecraft.*;
import rip.helium.gui.hud.element.*;
import rip.helium.gui.hud.element.impl.*;
import rip.helium.utils.*;
import rip.helium.utils.font.*;
import rip.helium.utils.font.FontRenderer;

public class Hud
{
    private rip.helium.cheat.impl.visual.Hud hud;
    public final ArrayList<Element> elements;
    protected Minecraft mc;
    private double lastPosX;
    private double lastPosZ;
    private ArrayList<Double> distances;
    FontRenderer logo;
    ScaledResolution resolution;
    String c = "Null";
    
    public Hud() {
        this.elements = new ArrayList<Element>();
        this.mc = Minecraft.getMinecraft();
        this.lastPosX = Double.NaN;
        this.lastPosZ = Double.NaN;
        this.distances = new ArrayList<Double>();
        this.logo = new FontRenderer(Fonts.fontFromTTF(new ResourceLocation("client/ElliotSans-Bold.ttf"), 20.0f, 0), true, true);
        this.resolution = new ScaledResolution(Minecraft.getMinecraft());
        this.elements.add(new ToggledList());
        Helium.eventBus.register(this);
    }
    
    public double getDistTraveled() {
        double total = 0.0;
        for (final double d : this.distances) {
            total += d;
        }
        return total;
    }
    
    @Collect
    public void onPlayerUpdate(final PlayerUpdateEvent e) {
        if (!Double.isNaN(this.lastPosX) && !Double.isNaN(this.lastPosZ)) {
            final double differenceX = Math.abs(this.lastPosX - Minecraft.getMinecraft().thePlayer.posX);
            final double differenceZ = Math.abs(this.lastPosZ - Minecraft.getMinecraft().thePlayer.posZ);
            final double distance = Math.sqrt(differenceX * differenceX + differenceZ * differenceZ) * 2.0;
            this.distances.add(distance);
            if (this.distances.size() > 20) {
                this.distances.remove(0);
            }
        }
        this.lastPosX = Minecraft.getMinecraft().thePlayer.posX;
        this.lastPosZ = Minecraft.getMinecraft().thePlayer.posZ;
    }
     
    
    
    @Collect
    public void render(final RenderOverlayEvent event) {
        if (this.hud == null) {
            this.hud = (rip.helium.cheat.impl.visual.Hud) Helium.instance.cheatManager.getCheatRegistry().get("Hud");
        }
        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        this.elements.forEach(element -> element.drawElement(false));
        if (this.hud == null) {
            this.hud = (rip.helium.cheat.impl.visual.Hud) Helium.instance.cheatManager.getCheatRegistry().get("Hud");
        }
        if (!Helium.instance.cheatManager.isCheatEnabled("Hud")) {
            return;
        }
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    	DateFormat dateFormat = new SimpleDateFormat("hh:mm");
    	String dateString = dateFormat.format(new Date()).toString();
        int y = (int) ToggledList.positionY - 2;
        if (rip.helium.cheat.impl.visual.Hud.hud_tnt.getValue()) {
            for (final Entity obj : mc.theWorld.loadedEntityList) {
                if (obj instanceof EntityTNTPrimed) {
                    if (mc.thePlayer.getDistanceToEntity(obj) < 8) {
                        Fonts.f16.drawStringWithShadow("found tnt " + Math.round(mc.thePlayer.getDistanceToEntity(obj)) + " meters away from you ", 448, 35, new Color(255, 0, 0).getRGB());
                    }
                }
            }
        }
        if (rip.helium.cheat.impl.visual.Hud.prop_theme.getValue().get("Helium")) {
            if (rip.helium.cheat.impl.visual.Hud.prop_colormode.getValue().get("Rainbow")) {
                Fonts.f20.drawStringWithShadow("H§7elium", 8, 8, ColorCreator.createRainbowFromOffset(3200, y * -15));
            } else {
                Fonts.f20.drawStringWithShadow("Helium", 1, 5, rip.helium.cheat.impl.visual.Hud.prop_color.getValue().getRGB());
            }
        } else if (rip.helium.cheat.impl.visual.Hud.prop_theme.getValue().get("Virtue")) {
            mc.fontRendererObj.drawStringWithShadow("§fVirtue", 2,2, 1);
        }
       // Fonts.f20.drawStringWithShadow("V§fiper Destruction Edition", 2, 14, ColorCreator.createRainbowFromOffset(3200, y * -15));
        //Fonts.f18.drawStringWithShadow("§fCheat created by Kansio#6759", 2, 13, 1);
        //mc.fontRendererObj.drawStringWithShadow("§fBuild - 911", 1, 13, 1);
        if (this.hud.prop_logo.getValue()) {
            if (this.hud.prop_logomode.getValue().get("Helium")) {
                final String pattern = "HH:mm";
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                final String date = simpleDateFormat.format(new Date());
            	DateFormat dateFormatads = new SimpleDateFormat("hh:mm");
            	String dateStrinasdg = dateFormat.format(new Date()).toString();
                
                //Gui.drawRect(150, 25, 2, 2, new Color(30, 30, 30).getRGB());
                //Gui.drawRect(150, 20, 2, 2, ColorCreator.createRainbowFromOffset(3200, 1));
                float x = 3;
                float z = 3;
                //Draw.drawBorderedRectangle(40, 70, 40, 70, 5,new Color(30, 30, 30).getRGB(), ColorCreator.createRainbowFromOffset(3200, 1), true);
                
                //mc.fontRendererObj.drawStringWithShadow("§fDeveloper Build §7- §fBuild 2" + "", 3, 30, 2);
                //Gui.drawRect(150, 3, 2, 2, ColorCreator.createRainbowFromOffset(3200, 1));
                //Fonts.verdanaN.drawStringWithShadow("§f§l" + c + " - " + FadeAway.client_build + " Developer Build", 4.0, 9.7, 16777215);
                
                //Fonts.verdana2.drawStringWithShadow(EnumChatFormatting.GRAY + "adeAway (§2C§4h§2r§4i§2s§4t§2m§4a§2s §4E§2d§4i§2t§4i§2o§4n§7)", 11.5, 4.15, 16777215);
            }
            //else if (this.hud.prop_logomode.getValue().get("Image")) {
               // Draw.drawImg(new ResourceLocation("client/gui/logo/128x128.png"), -2.0, -2.0, 128.0, 128.0);
            }
          //  else if (this.hud.prop_logomode.getValue().get("Image2")) {
              //  Draw.drawImg(new ResourceLocation("client/gui/logo/512x512.png"), -2.0, -2.0, 128.0, 128.0);
            }
    
    @Collect
    public void onKeyPressed(final KeyPressEvent event) {
        this.elements.forEach(module -> module.onKeyPress(event.getKeyCode()));
    }
}
