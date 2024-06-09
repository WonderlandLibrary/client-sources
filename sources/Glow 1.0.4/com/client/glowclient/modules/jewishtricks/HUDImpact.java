package com.client.glowclient.modules.jewishtricks;

import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.*;

public class HUDImpact extends ModuleContainer
{
    public static ea b;
    
    @SubscribeEvent
    public void M(final RenderGameOverlayEvent$Text renderGameOverlayEvent$Text) {
        final ca ca = new ca();
        final Minecraft client = FMLClientHandler.instance().getClient();
        final ScaledResolution scaledResolution = new ScaledResolution(client);
        final int n = 1;
        final int n2 = 1;
        String.format("%.2f", client.player.rotationPitch);
        String.format("%.2f", client.player.rotationYaw);
        final String format = String.format("%.1f", client.player.posY);
        final String format2 = String.format("%.1f", client.player.posX);
        final String format3 = String.format("%.1f", client.player.posZ);
        String.format("%.3f", client.player.posX / 8.0);
        String.format("%.3f", client.player.posZ / 8.0);
        String.format("%.3f", client.player.posX * 8.0);
        String.format("%.3f", client.player.posZ * 8.0);
        final double n3 = n;
        final double n4 = n2;
        final String s = "lmpact §f4.2";
        final int m = ga.M(70, 160, 175, 255);
        this.M(s, 4.0, 5.0, m, ca, scaledResolution);
        final StringBuilder append = new StringBuilder().append("FPS §f");
        final Minecraft minecraft = client;
        this.M(append.append(Minecraft.getDebugFPS()).toString(), 4.0, 16.0, m, ca, scaledResolution);
        if (minecraft.getConnection().getPlayerInfo(client.player.getUniqueID()) != null) {
            this.M(new StringBuilder().insert(0, "Ping §f").append(client.getConnection().getPlayerInfo(client.player.getUniqueID()).getResponseTime()).toString(), 4.0, 27.0, m, ca, scaledResolution);
        }
        final String s2 = "+wQ\u00ea\u0017";
        final String s3 = "vW\u000f\u00caI";
        final String s4 = ")wQ\u00ea\u0017";
        this.M(new StringBuilder().insert(0, "TPS §f").append(this.e()).toString(), 4.0, 38.0, m, ca, scaledResolution);
        this.M(SA.M(s4), format2, n - 2, n2 - 14, m, ca, scaledResolution);
        this.M(Ta.M(s3), format, n - 2, n2 - 3, m, ca, scaledResolution);
        this.M(SA.M(s2), format3, n - 2, n2 + 8, m, ca, scaledResolution);
        final ScaledResolution scaledResolution2 = scaledResolution;
        int n5 = scaledResolution2.getScaledWidth() / 2 + 129;
        final int n6 = scaledResolution2.getScaledHeight() - 55;
        n5 -= 103;
        final int n7 = n6;
        int n8 = 0;
        int n9;
        int i = n9 = 0;
        while (i < 4) {
            final ItemStack j = UB.M(n9);
            final ca k = ca.K().M(com.client.glowclient.ca::B).M(com.client.glowclient.ca::e);
            final ItemStack itemStack = j;
            final double n10 = n5 - 16 + 11 * n8;
            ++n8;
            final ca l = k.M(itemStack, n10, n7);
            final ItemStack itemStack2 = j;
            final double n11 = n5 - 27 + 11 * n8;
            ++n8;
            final double n12 = n7;
            ++n9;
            l.D(itemStack2, n11, n12).M(com.client.glowclient.ca::M);
            i = n9;
        }
        final int n13 = 5;
        final int n14 = 10;
        final String s5 = "Middle Click Friend";
        final int n15 = n13;
        final String s6 = s5;
        final double n16 = scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s5, 1.0) - 4.0;
        final double n17 = n13;
        final int n18 = 255;
        final int n19 = 150;
        this.M(s6, n16, n17, ga.M(n18, n19, n19, 200), ca, scaledResolution);
        final int n20 = n15 + n14;
        final String s7 = "Levitation Control";
        final double n21 = scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s7, 1.0) - 4.0;
        final double n22 = n20;
        final int n23 = 150;
        final int n24 = 175;
        this.M(s7, n21, n22, ga.M(n24, n23, n24, 200), ca, scaledResolution);
        final int n25 = n20 + n14;
        final String s8 = "Auto Reconnect";
        final double n26 = scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s8, 1.0) - 4.0;
        final double n27 = n25;
        final int n28 = 225;
        final int n29 = 150;
        this.M(s8, n26, n27, ga.M(n28, n29, n29, 200), ca, scaledResolution);
        final int n30 = n25 + n14;
        final String s9 = "Velocity §70% 0%";
        this.M(s9, scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s9, 1.0) - 4.0, n30, ga.M(225, 175, 150, 200), ca, scaledResolution);
        final int n31 = n30 + n14;
        final String s10 = "CameraClip";
        final double n32 = scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s10, 1.0) - 4.0;
        final double n33 = n31;
        final int n34 = 150;
        final int n35 = 225;
        this.M(s10, n32, n33, ga.M(n34, n35, n35, 200), ca, scaledResolution);
        final int n36 = n31 + n14;
        final String s11 = "Auto Totem";
        this.M(s11, scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s11, 1.0) - 4.0, n36, ga.M(150, 225, 175, 200), ca, scaledResolution);
        final int n37 = n36 + n14;
        final String s12 = "Nametags";
        this.M(s12, scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s12, 1.0) - 4.0, n37, ga.M(175, 210, 235, 200), ca, scaledResolution);
        final int n38 = n37 + n14;
        final String s13 = "Anti Blind";
        final double n39 = scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s13, 1.0) - 4.0;
        final double n40 = n38;
        final int n41 = 150;
        final int n42 = 200;
        this.M(s13, n39, n40, ga.M(n42, n42, n41, n42), ca, scaledResolution);
        final int n43 = n38 + n14;
        final String s14 = "Auto Fish";
        final double n44 = scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s14, 1.0) - 4.0;
        final double n45 = n43;
        final int n46 = 150;
        final int n47 = 175;
        this.M(s14, n44, n45, ga.M(n47, n46, n47, 200), ca, scaledResolution);
        final int n48 = n43 + n14;
        final String s15 = "Respawn";
        this.M(s15, scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s15, 1.0) - 4.0, n48, ga.M(225, 175, 150, 200), ca, scaledResolution);
        final int n49 = n48 + n14;
        final String s16 = "Tracers";
        final double n50 = scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s16, 1.0) - 4.0;
        final double n51 = n49;
        final int n52 = 150;
        final int n53 = 200;
        this.M(s16, n50, n51, ga.M(n53, n53, n52, n53), ca, scaledResolution);
        final int n54 = n49 + n14;
        final String s17 = "Riding";
        this.M(s17, scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s17, 1.0) - 4.0, n54, ga.M(225, 175, 150, 200), ca, scaledResolution);
        final int n55 = n54 + n14;
        final String s18 = "Light";
        final double n56 = scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s18, 1.0) - 4.0;
        final double n57 = n55;
        final int n58 = 255;
        final int n59 = 150;
        this.M(s18, n56, n57, ga.M(n58, n59, n59, 200), ca, scaledResolution);
    }
    
    public HUDImpact() {
        super(Category.JEWISH TRICKS, "HUDImpact", false, -1, "Shows all HUD features");
    }
    
    static {
        HUDImpact.b = g.M;
    }
    
    public void M(final String s, final double n, final double n2, final int n3, final ca ca, final ScaledResolution scaledResolution) {
        if (HUDImpact.b != null) {
            ca.K().M(HUDImpact.b).D(n3).M(s, n + 1.0, n2 + 1.0, true).D(n3).M(s, n, n2);
            return;
        }
        ca.K().M(HUDImpact.b).D(n3).M(s, n, n2, true);
    }
    
    public void M(final String s, final String s2, final double n, final double n2, final int n3, final ca ca, final ScaledResolution scaledResolution) {
        if (HUDImpact.b != null) {
            ca.K().M(HUDImpact.b).D(n3).M(s, n + scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s, 1.0) - Ia.M(HUDImpact.b, s2, 1.0) - 2.0 + 1.0 + 1.0, n2 + scaledResolution.getScaledHeight() - Ia.M(HUDImpact.b) - 13.0 + 1.0, true).D(n3).M(s, n + scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s, 1.0) - Ia.M(HUDImpact.b, s2, 1.0) - 2.0 + 1.0, n2 + scaledResolution.getScaledHeight() - Ia.M(HUDImpact.b) - 13.0).D(ga.G).M(s2, n + scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s2, 1.0) - 2.0 + 1.0, n2 + scaledResolution.getScaledHeight() - Ia.M(HUDImpact.b) - 13.0 + 1.0, true).D(ga.G).M(s2, n + scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s2, 1.0) - 2.0, n2 + scaledResolution.getScaledHeight() - Ia.M(HUDImpact.b) - 13.0);
            return;
        }
        ca.K().M(HUDImpact.b).D(n3).M(s, n + scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s, 1.0) - Ia.M(HUDImpact.b, s2, 1.0) - 2.0 + 1.0, n2 + scaledResolution.getScaledHeight() - Ia.M(HUDImpact.b) - 13.0, true).D(ga.G).M(s2, n + scaledResolution.getScaledWidth() - Ia.M(HUDImpact.b, s2, 1.0) - 2.0, n2 + scaledResolution.getScaledHeight() - Ia.M(HUDImpact.b) - 13.0, true);
    }
    
    private String e() {
        final StringBuilder sb = new StringBuilder("");
        final te m = me.M();
        final int n = 100;
        final int n2;
        if ((n2 = m.M() / n) * n < m.M()) {
            sb.append(String.format("%.2f", m.M().M()));
            if (n2 > 0) {
                sb.append(", ");
            }
        }
        if (n2 > 0) {
            int n3;
            int i = n3 = n2;
            while (i > 0) {
                sb.append(String.format("%.2f", m.M(n3 * n).M()));
                if (n3 - 1 != 0) {
                    sb.append(", ");
                }
                i = --n3;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void D() {
        ModuleManager.M("HUD").k();
    }
}
