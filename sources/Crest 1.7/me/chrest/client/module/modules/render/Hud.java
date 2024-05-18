// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.render;

import me.chrest.event.events.TickEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import me.chrest.event.EventTarget;
import java.util.Iterator;
import me.chrest.utils.RenderingUtils;
import net.minecraft.client.gui.ScaledResolution;
import me.chrest.client.module.ModuleManager;
import org.lwjgl.opengl.GL11;
import me.chrest.utils.ClientUtils;
import me.chrest.client.gui.ui.TabGui;
import me.chrest.utils.Rainbow;
import me.chrest.event.events.Render2DEvent;
import me.chrest.client.option.Option;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod(enabled = true, shown = false, displayName = "Hud")
public class Hud extends Module
{
    private static final int MIN_HEX = -23614;
    private static final int MAX_HEX = -3394561;
    private static final int MAX_FADE = 20;
    private static int currentColor;
    private static int fadeState;
    private static boolean goingUp;
    Minecraft mc;
    @Option.Op(name = "Armor Gosterme")
    private boolean armorStatus;
    @Option.Op(name = "HUD")
    private boolean infoHud;
    @Option.Op(name = "TabGu\u0131")
    private boolean tabGUI;
    @Option.Op(name = "Zaman")
    private boolean time;
    @Option.Op(name = "Sh\u0131tTabGu\u0131")
    private boolean virtue;
    
    public Hud() {
        this.mc = Minecraft.getMinecraft();
        this.time = true;
        this.tabGUI = true;
    }
    
    @EventTarget
    private void onRender2D(final Render2DEvent event) {
        final int index = 0;
        TabGui.mainhue = Rainbow.rainbow(index * 200000000L, 1.0f).getRGB();
        ClientUtils.mc();
        final String extra = (this.tabGUI && this.virtue) ? (" FPS: " + Minecraft.debugFPS) : "";
        GL11.glPushMatrix();
        GL11.glScalef(1.25f, 1.25f, 1.25f);
        ClientUtils.clientFont().drawStringWithShadow("C §f" + extra, 3.0, 2.0, Hud.currentColor);
        GL11.glPopMatrix();
        ClientUtils.clientFont().drawStringWithShadow("   §frest        (§1R§2e§3l§4e§5a§6s§7e§f)  §4 §f" + extra, 3.0, 4.0, 0);
        ClientUtils.clientFont().drawStringWithShadow("           v1.7 §f" + extra, 3.0, 4.0, Hud.currentColor);
        int y = 13;
        ClientUtils.clientFont().drawStringWithShadow("XYZ: " + Math.round(ClientUtils.x()) + " " + Math.round(ClientUtils.y()) + " " + Math.round(ClientUtils.z()), 1.0, y + 320, 13882323);
        for (final Module mod : ModuleManager.getModulesForRender()) {
            int index2 = 0;
            if (mod.drawDisplayName(event.getWidth() - ClientUtils.clientFont().getStringWidth(String.format("%s" + ((mod.getSuffix().length() > 0) ? " §7 §f %s" : ""), mod.getDisplayName(), mod.getSuffix())) - 2, y)) {
                y += 10;
                ++index2;
            }
        }
        final ScaledResolution scaledRes = new ScaledResolution(ClientUtils.mc(), ClientUtils.mc().displayWidth, ClientUtils.mc().displayHeight);
        if (this.infoHud && this.tabGUI && this.armorStatus) {
            RenderingUtils.drawStuffStatus(scaledRes);
        }
    }
    
    private String getTime() {
        if (this.time) {
            String time = new SimpleDateFormat("hh:mm a").format(new Date());
            if (time.startsWith("0")) {
                time = time.replaceFirst("0", "");
            }
            return time;
        }
        return "";
    }
    
    private void updateFade() {
        if (Hud.fadeState >= 20 || Hud.fadeState <= 0) {
            Hud.goingUp = !Hud.goingUp;
        }
        if (Hud.goingUp) {
            ++Hud.fadeState;
        }
        else {
            --Hud.fadeState;
        }
        final double ratio = Hud.fadeState / 20.0;
        Hud.currentColor = this.getFadeHex(0, 16777215, ratio);
    }
    
    private int getFadeHex(final int hex1, final int hex2, final double ratio) {
        int r = hex1 >> 16;
        int g = hex1 >> 8 & 0xFF;
        int b = hex1 & 0xFF;
        r += (int)(((hex2 >> 16) - r) * ratio);
        g += (int)(((hex2 >> 8 & 0xFF) - g) * ratio);
        b += (int)(((hex2 & 0xFF) - b) * ratio);
        return r << 16 | g << 8 | b;
    }
    
    @EventTarget
    private void onTick(final TickEvent event) {
        this.updateFade();
    }
}
