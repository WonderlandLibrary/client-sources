package me.vaziak.sensation.client.api.gui.ingame.HUD;

import me.vaziak.sensation.client.api.gui.ingame.HUD.element.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.EventRender2D;
import me.vaziak.sensation.client.api.event.events.KeyPressEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import me.vaziak.sensation.utils.math.MathUtils;

/**
 * @author antja03
 */
public class Hud {

    public final HudEditorGui hudEditorGui = new HudEditorGui();

    private final HashMap<String, Element> elementRegistry = new HashMap<>();
    
    private double lastPosX = Double.NaN;
    private double lastPosZ = Double.NaN;
    private double maxBPS = 0;
    private ArrayList<Double> distances = new ArrayList<Double>();

	private boolean near;

    public Hud() {
        registerElement(new Watermark());
        registerElement(new TargetHUD());
        registerElement(new ToggledList());
        registerElement(new TabGui());
        registerElement(new ItemHud());
        registerElement(new AdditionalInfo());
        Sensation.eventBus.register(this);
    }

    private void registerElement(Element element) {
        elementRegistry.put(element.getIdentifier(), element);
    }

    private double getDistTraveled() {
        double total = 0;
        for (double d : distances) {
            total += d;
        }
        return total;
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent e) {
        if (!Double.isNaN(lastPosX) && !Double.isNaN(lastPosZ)) {
            double differenceX = Math.abs(lastPosX - Minecraft.getMinecraft().thePlayer.posX);
            double differenceZ = Math.abs(lastPosZ - Minecraft.getMinecraft().thePlayer.posZ);
            double distance = Math.sqrt(differenceX * differenceX + differenceZ * differenceZ) * 2;

            distances.add(distance);
            if (distances.size() > 20)
                distances.remove(0);
        }

        lastPosX = Minecraft.getMinecraft().thePlayer.posX;
        lastPosZ = Minecraft.getMinecraft().thePlayer.posZ;
    }

    @Collect
    public void onRenderOverlay(EventRender2D event) {
        if (Minecraft.getMinecraft().currentScreen instanceof HudEditorGui)
            return;
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        Minecraft mc = Minecraft.getMinecraft();
        this.elementRegistry.values().forEach(element -> element.drawElement(false)); 
    	
        int x = (int) mc.thePlayer.posX;
        int y = (int) mc.thePlayer.posY;
        int z = (int) mc.thePlayer.posZ;
        if (MathUtils.round(getDistTraveled(), 2) > maxBPS) {
        	maxBPS = MathUtils.round(getDistTraveled(), 2);
        }  
     //   Fonts.f18.drawString(maxBPS + " Max blocks/sec", 2, resolution.getScaledHeight() - 30, new Color(255,255,255).getRGB());
        //if (mc.getCurrentServerData() != null) {
        //    Fonts.f16.drawString("Ping: " + mc.getCurrentServerData().pingToServer , 2, resolution.getScaledHeight() - 10, new Color(255,255,255).getRGB());
            
        //}
         //TODO: Turn this into an actual HUD feature, instead of just you know some hardcoded shit
        /*
        //inerts*       
        Draw.drawRectangle(1, resolution.getScaledHeight() / 2 - 205, Fonts.f16.getStringWidth("Blocks P/s: ") + 16, resolution.getScaledHeight() / 2 - 165, new Color(0, 0, 0, 90).getRGB());
        //right
        Draw.drawRectangle(Fonts.f16.getStringWidth("Blocks P/s: ") + 16.1, resolution.getScaledHeight() / 2 - 205, Fonts.f16.getStringWidth("Blocks P/s: ") + 16.8, resolution.getScaledHeight() / 2 - 165, new Color(0, 0, 0, 129).getRGB());
        //left
        Draw.drawRectangle(1, resolution.getScaledHeight() / 2 - 205, 1.8, resolution.getScaledHeight() / 2 - 165, new Color(0, 0, 0).getRGB());
        //bottom
        Draw.drawRectangle(1.0, resolution.getScaledHeight() / 2 - 164, Fonts.f16.getStringWidth("Blocks P/s: ") + 16, resolution.getScaledHeight() / 2 - 165, new Color(0, 0, 0, 129).getRGB());
        //text
        Fonts.f18.drawString(MathUtils.round(getDistTraveled(), 2) + " bp /s", 2, resolution.getScaledHeight() / 2 - 203, new Color(255,255,255).getRGB());
        
        Fonts.f16.drawStringWithShadow("X: " + MathUtils.round(Minecraft.getMinecraft().thePlayer.posX, 1), 3.5, resolution.getScaledHeight() / 2 - 192, 0xffffffff);
        Fonts.f16.drawStringWithShadow("Y: " + MathUtils.round(Minecraft.getMinecraft().thePlayer.posY, 1), 3.5, resolution.getScaledHeight() / 2 - 182, 0xffffffff);
        Fonts.f16.drawStringWithShadow("Z: " + MathUtils.round(Minecraft.getMinecraft().thePlayer.posZ, 1), 3.5, resolution.getScaledHeight() / 2 - 172, 0xffffffff);
        */
    //    Fonts.f16.drawStringWithShadow("Timer: " + mc.timer.timerSpeed, 3.5, resolution.getScaledHeight() / 2 - 100, 0xffffffff);
            
    }

    @Collect
    public void onKeyPress(KeyPressEvent event) {
        if (Minecraft.getMinecraft().currentScreen instanceof HudEditorGui)
            return;

        this.elementRegistry.values().forEach(module -> module.onKeyPress(event.getKeyCode()));
    }

    public HashMap<String, Element> getElementRegistry() {
        return elementRegistry;
    }
}
