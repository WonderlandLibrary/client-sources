// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.UI;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.Badlion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.badlion.Wrapper;
import net.minecraft.client.network.badlion.Events.EventRender2D;
import net.minecraft.client.network.badlion.Mod.Mod;
import net.minecraft.client.network.badlion.Utils.RenderUtils;
import net.minecraft.client.network.badlion.Utils.TimeMeme;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.ttf.NahrFont.FontType;

public class UIRenderer extends GuiIngame
{
    public int colors;
    public int delay;
    public int c2;
    public static float hue;
    public TimeMeme timer;
    int rainbow;
    public static Minecraft mc;
    
    static {
        UIRenderer.mc = Minecraft.getMinecraft();
    }
    
    public UIRenderer(final Minecraft mcIn) {
        super(mcIn);
        this.delay = 50;
        this.c2 = 0;
        this.rainbow = Badlion.getRainbow();
        this.timer = new TimeMeme();
    }
    
    @Override
    public void func_175180_a(final float var1337) {
        super.func_175180_a(var1337);
        this.renderEuphoriaUI();
    }
    
    private void renderEuphoriaUI() {
        if (!Badlion.getWinter().legit.isEnabled()) {
            GL11.glPushMatrix();
            final String Cherry = "DaddyChe55y".replace("5", "r");
            Wrapper.fr.drawStringWithShadow("§7" + Cherry + "  " + "§f§8[§f0.3§8] §4http://youtube.com/Daddy!", 2.0f, 2.0f, -2105377);
            Wrapper.fr.drawStringWithShadow("\u00a77" + (int)UIRenderer.mc.thePlayer.posX + "\u00a7f: X", Minecraft.displayWidth / 2 - (Wrapper.fr.getStringWidth(String.valueOf((int)UIRenderer.mc.thePlayer.posX) + " :X§7") + 1), Minecraft.displayHeight / 2 - 29, -1);
            Wrapper.fr.drawStringWithShadow("\u00a77" + (int)UIRenderer.mc.thePlayer.posY + "\u00a7f: Y", Minecraft.displayWidth / 2 - (Wrapper.fr.getStringWidth(String.valueOf((int)UIRenderer.mc.thePlayer.posY) + " :Y§7") + 1), Minecraft.displayHeight / 2 - 19, -1);
            Wrapper.fr.drawStringWithShadow("\u00a77" + (int)UIRenderer.mc.thePlayer.posZ + "\u00a7f: Z", Minecraft.displayWidth / 2 - (Wrapper.fr.getStringWidth(String.valueOf((int)UIRenderer.mc.thePlayer.posZ) + " :Z§7") + 1), Minecraft.displayHeight / 2 - 9, -1);
            int yCount = 2;
            final int gCount = 2;
            final Minecraft minecraft = Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            final int displayWidth = Minecraft.displayWidth;
            Minecraft.getMinecraft();
            final ScaledResolution res = new ScaledResolution(minecraft, displayWidth, Minecraft.displayHeight);
            final EventRender2D event = new EventRender2D();
            EventManager.call(event);
            final ArrayList<Mod> sortex = new ArrayList<Mod>();
            for (final Mod mod : Badlion.getWinter().theMods.getMods()) {
                sortex.add(mod);
            }
            Collections.sort(sortex, new Comparator<Mod>() {
                @Override
                public int compare(final Mod mod1, final Mod mod2) {
                    final String s1 = String.valueOf(mod1.getRenderName());
                    final String s2 = String.valueOf(mod2.getRenderName());
                    final int cmp = Minecraft.getMinecraft().fontRendererObj.getStringWidth(s2) - Minecraft.getMinecraft().fontRendererObj.getStringWidth(s1);
                    return (cmp != 0) ? cmp : s2.compareTo(s1);
                }
            });
            for (final Mod mod2 : sortex) {
                if (mod2.isEnabled() && mod2.getName() != "Hud" && mod2.getName() != "Commands") {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(mod2.getRenderName(), ScaledResolution.getScaledWidth() - Minecraft.getMinecraft().fontRendererObj.getStringWidth(mod2.getRenderName()) - 2, yCount, RenderUtils.transparency(getModColor(mod2), 1.0f));
                    yCount += 11;
                }
            }
            final int yBottom = ScaledResolution.getScaledHeight() - 15;
            final int[] array = { Minecraft.getMinecraft().thePlayer.getPosition().getX(), Minecraft.getMinecraft().thePlayer.getPosition().getY(), Minecraft.getMinecraft().thePlayer.getPosition().getZ() };
        }
        else if (Badlion.getWinter().legit.isEnabled()) {
            Display.setTitle("Minecraft 1.8");
        }
        final Color color = new Color(224, 247, 255, 255);
        final int c = color.getRGB();
        Badlion.getWinter().getGuiMgr().update();
        Badlion.getWinter().getKeybindGuiMgr().update();
        UIRenderer.hue += 0.0f;
        if (UIRenderer.hue > 255.0f) {
            UIRenderer.hue = 0.0f;
        }
        GL11.glPopMatrix();
        if (Badlion.getWinter().legit.isEnabled()) {
            GL11.glPushMatrix();
        }
    }
    
    public static int getModColor(final Mod module) {
        switch (module.getCategory()) {
            case COMBAT: {
                return -65536;
            }
            case RENDER: {
                return -16711681;
            }
            case OTHER: {
                return -256;
            }
            case MOVEMENT: {
                return -16711936;
            }
            case AUTO: {
                return -12164996;
            }
            default: {
                return -1;
            }
        }
    }
}
