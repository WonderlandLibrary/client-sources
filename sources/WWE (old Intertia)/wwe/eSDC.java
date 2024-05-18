package wwe;

import java.util.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.text.*;
import net.minecraft.client.gui.*;
import wwe.utils.*;
import java.io.*;
import org.lwjgl.input.*;
import javax.imageio.*;

public class eSDC extends GuiScreen
{
    public static ArrayList<Lnec> ybni;
    public GuiScreen yGWF;
    int pGNH;
    sbfc EUEd;
    
    public eSDC(final GuiScreen ygwf) {
        super();
        this.pGNH = 0;
        this.EUEd = new sbfc();
        this.yGWF = ygwf;
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawBackground(1);
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.mc.getTextureManager().bindTexture((ResourceLocation)qdlI.JTTe.get(qdlI.kbXN));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), (float)scaledResolution.getScaledWidth(), (float)scaledResolution.getScaledHeight());
        GL11.glPushMatrix();
        GlStateManager.scale(5.0f, 5.0f, 0.0f);
        this.drawCenteredString(this.mc.fontRenderer, TextFormatting.DARK_GREEN + "WWE" + TextFormatting.DARK_AQUA + "Credits", this.width / 10, 10, -1);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int i = 0;
        while (i < eSDC.ybni.size()) {
            eSDC.ybni.get(i).xvET(n, n2, n3);
            ++i;
            if (eSDC.\u200f\u2002\u2009\u2006\u200b\u2003\u2001\u200d) {
                throw null;
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73866_w_() {
        this.pGNH = 0;
        this.buttonList.add(new GuiButton(21, this.width / 2 - 100, this.height - 25, "Back"));
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        int i = 0;
        while (i < eSDC.ybni.size()) {
            final Lnec lnec = eSDC.ybni.get(i);
            lnec.XMpd = lnec.Bcna + scaledResolution.getScaledWidth() / 2;
            lnec.ZwCC = lnec.uvzZ + scaledResolution.getScaledHeight() / 2;
            ++i;
            if (eSDC.\u200f\u2002\u2009\u2006\u200b\u2003\u2001\u200d) {
                throw null;
            }
        }
        this.updateScreen();
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 21) {
            Wrapper.mc.displayGuiScreen(this.yGWF);
        }
    }
    
    public void func_73869_a(final char c, final int n) {
        if (n == 1) {
            Wrapper.mc.displayGuiScreen(this.yGWF);
        }
    }
    
    public void func_146274_d() throws IOException {
        final int dWheel = Mouse.getDWheel();
        if (dWheel != 0) {
            int i = 0;
            while (i < eSDC.ybni.size()) {
                final Lnec lnec = eSDC.ybni.get(i);
                if (dWheel > 0) {
                    final Lnec lnec2 = lnec;
                    lnec2.ZwCC += 50;
                    if (eSDC.\u200f\u2002\u2009\u2006\u200b\u2003\u2001\u200d) {
                        throw null;
                    }
                }
                else if (dWheel < 0) {
                    final Lnec lnec3 = lnec;
                    lnec3.ZwCC -= 50;
                }
                ++i;
                if (eSDC.\u200f\u2002\u2009\u2006\u200b\u2003\u2001\u200d) {
                    throw null;
                }
            }
        }
        super.handleMouseInput();
    }
    
    static {
        eSDC.ybni = new ArrayList<Lnec>();
        ResourceLocation dkaH = null;
        try {
            dkaH = FIwW.dkaH(Lnec.tCKN(ImageIO.read(eSDC.class.getResourceAsStream("/assets/minecraft/textures/wwe/unknown.png")), 500), "wweunknown");
            if (eSDC.\u200f\u2002\u2009\u2006\u200b\u2003\u2001\u200d) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        eSDC.ybni.add(new Lnec("Founder/Dev", "IKnowImEZ aka THEREALWWEFAN231", "dfcc87eb40744fc692505f505a905956"));
        eSDC.ybni.add(new Lnec("Custom Font & Alt Manager", "xDolf Devs", "05178670fffb450ab8a4095ee51df4d4"));
        eSDC.ybni.add(new Lnec("Discord", "ForceAWP", dkaH));
        eSDC.ybni.add(new Lnec("Discord", "RonIsBest", "71e0c133513c4b648918f405bae9d2d6"));
        eSDC.ybni.add(new Lnec("Alot Of Help", "DevOnDuty", "310abccb3bf446e8ac632cb304636116"));
        eSDC.ybni.add(new Lnec("Info On Color Signs", "TheTroll2001", "57705e35e0cb428daf583ce346c330a1"));
        eSDC.ybni.add(new Lnec("Entity Speed", "Future Client", "futurelogo.png", true));
        eSDC.ybni.add(new Lnec("Speed Mode Tomato", "Tomato Client", "tomato.png", true));
        eSDC.ybni.add(new Lnec("Flight Mode Hypixel", "Idk Who Did", dkaH));
        eSDC.ybni.add(new Lnec("Trajectories, Auto Tool, Fancy Chat+ Classic, Auto farm(getFacingFromBlock method)", "Wurst Client Dev", "wurstlogo.png", false));
        eSDC.ybni.add(new Lnec("Elytra+ Mode 2h2e", "2h2e Client Dev", "2h2elogo.png", false));
        eSDC.ybni.add(new Lnec("Reflection Fields Forge Mod", "Pepsi Mod Devs", "pepsimodlogo.png", false));
        eSDC.ybni.add(new Lnec("Website Help", "Paralusion", "Paralusion.png", true));
        eSDC.ybni.add(new Lnec("Website Help", "TechX", "b57eaad15fb847d2bb015d4bfd667ed1"));
        eSDC.ybni.add(new Lnec("CrystalAura Auto Place", "Kami Client Dev", "kami.png", true));
        eSDC.ybni.add(new Lnec("Jesus Bouncy", "Cinnamon Client Devs", "cinnamon.png", true));
        eSDC.ybni.add(new Lnec("Baritone", "leijurv", "leijurv.png", true));
        eSDC.ybni.add(new Lnec(".randombook", "Earthcomputer", "earthcomputer.png", true));
        eSDC.ybni.add(new Lnec("Packet Fly Fast Base", "Riga", dkaH));
        eSDC.ybni.add(new Lnec("Bow Aim Bot Rotations", "Uzi Client Devs", dkaH));
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final int n;
        int xMpd = n = scaledResolution.getScaledWidth() / 2 - 340;
        int zwCC = scaledResolution.getScaledHeight() / 2 - 200;
        int i = 0;
        while (i < eSDC.ybni.size()) {
            final Lnec lnec = eSDC.ybni.get(i);
            if (i % 4 == 0) {
                zwCC += 120;
                xMpd = n;
            }
            xMpd += 120;
            lnec.XMpd = xMpd;
            lnec.ZwCC = zwCC;
            lnec.Bcna = xMpd - scaledResolution.getScaledWidth() / 2;
            lnec.uvzZ = zwCC - scaledResolution.getScaledHeight() / 2;
            ++i;
            if (eSDC.\u200f\u2002\u2009\u2006\u200b\u2003\u2001\u200d) {
                throw null;
            }
        }
    }
}
