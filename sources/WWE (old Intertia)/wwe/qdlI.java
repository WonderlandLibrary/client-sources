package wwe;

import net.minecraft.util.*;
import wwe.utils.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import net.minecraft.realms.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;

public class qdlI extends GuiScreen
{
    public static String[] jBhh;
    public String Katg;
    public static boolean WFhU;
    public static String gGMK;
    public static int kbXN;
    public static ArrayList<ResourceLocation> JTTe;
    public static int GHWo;
    public boolean veXh;
    public long ovZE;
    public static boolean mSnI;
    public static boolean bppf;
    public sbfc qSYb;
    public boolean tPWM;
    
    public qdlI() {
        super();
        this.Katg = "KEK";
        this.qSYb = new sbfc();
        this.veXh = !qdlI.mSnI;
        qdlI.mSnI = true;
    }
    
    public void func_73866_w_() {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final int n = scaledResolution.getScaledWidth() / 5 + 15;
        this.buttonList.add(new Gpxt(1, 15, scaledResolution.getScaledHeight() / 2 - scaledResolution.getScaledHeight() / 4, scaledResolution.getScaledWidth() / 5, scaledResolution.getScaledHeight() / 2, "Single Player"));
        this.buttonList.add(new Gpxt(2, scaledResolution.getScaledWidth() / 5 + 30, scaledResolution.getScaledHeight() / 2 - scaledResolution.getScaledHeight() / 4, scaledResolution.getScaledWidth() / 5, scaledResolution.getScaledHeight() / 2, "Multi Player"));
        this.buttonList.add(new Gpxt(3, scaledResolution.getScaledWidth() / 5 + 30 + n, scaledResolution.getScaledHeight() / 2 - scaledResolution.getScaledHeight() / 4, scaledResolution.getScaledWidth() / 5, scaledResolution.getScaledHeight() / 2, "Realms"));
        this.buttonList.add(new Gpxt(4, scaledResolution.getScaledWidth() / 5 + 30 + n + n, scaledResolution.getScaledHeight() / 2 - scaledResolution.getScaledHeight() / 4, scaledResolution.getScaledWidth() / 5, scaledResolution.getScaledHeight() / 2, "WWE Options"));
        this.buttonList.add(new Gpxt(5, scaledResolution.getScaledWidth() / 5 + 30 + n + n + n, scaledResolution.getScaledHeight() / 2 - scaledResolution.getScaledHeight() / 4, scaledResolution.getScaledWidth() / 5, scaledResolution.getScaledHeight() / 2, "Options"));
        this.Katg = qdlI.jBhh[new Random().nextInt(qdlI.jBhh.length)];
        this.buttonList.add(new GuiButton(21, 50, this.height - this.height / 8 - 10, 20, 20, "MC"));
        this.buttonList.add(new GuiButton(22, this.width - 50, this.height - this.height / 8 - 10, 20, 20, ">"));
        this.qSYb.BBwj();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        if (this.ovZE == (0x8BD54C46AD28B622L ^ 0x8BD54C46AD28B622L) && this.veXh) {
            this.ovZE = System.nanoTime() / (0xCF90732968FACDE7L ^ 0xCF90732968F58FA7L);
        }
        float n4;
        if (this.veXh) {
            n4 = (System.nanoTime() / (0x7A8B0A87ADB2E38EL ^ 0x7A8B0A87ADBDA1CEL) - this.ovZE) / 1500.0f;
            if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                throw null;
            }
        }
        else {
            n4 = 1.0f;
        }
        final float n5 = n4;
        this.drawDefaultBackground();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        drawRect(0, 0, this.width, this.height, -1);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        final float n6 = 1.0f;
        final float n7 = 1.0f;
        final float n8 = 1.0f;
        float n9;
        if (this.veXh) {
            n9 = (float)MathHelper.ceil(MathHelper.clamp(n5, 0.0f, 1.0f));
            if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                throw null;
            }
        }
        else {
            n9 = 1.0f;
        }
        GlStateManager.color(n6, n7, n8, n9);
        final float n10 = n5;
        final int n11 = MathHelper.ceil(n10 * 255.0f) << 24;
        GlStateManager.color(1.0f, 1.0f, 1.0f, n10);
        if (qdlI.bppf) {
            this.qSYb.gCgh();
        }
        if (qdlI.bppf && this.qSYb.QOXG(5000.0)) {
            if (!this.tPWM) {
                int kbXN;
                if (qdlI.kbXN >= qdlI.JTTe.size() - 1) {
                    kbXN = 0;
                    if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                        throw null;
                    }
                }
                else {
                    kbXN = qdlI.kbXN + 1;
                }
                qdlI.kbXN = kbXN;
                Wrapper.ypJK().OsgT();
                this.tPWM = true;
            }
            this.mc.getTextureManager().bindTexture((ResourceLocation)qdlI.JTTe.get(qdlI.kbXN));
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), (float)scaledResolution.getScaledWidth(), (float)scaledResolution.getScaledHeight());
            final int n12 = ((int)this.qSYb.KBDH() - 5000) * this.width / 1000;
            if (n12 <= this.width) {
                final TextureManager textureManager = this.mc.getTextureManager();
                final ArrayList<ResourceLocation> jtTe = qdlI.JTTe;
                int n13;
                if (qdlI.kbXN == 0) {
                    n13 = qdlI.JTTe.size() - 1;
                    if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                        throw null;
                    }
                }
                else {
                    n13 = qdlI.kbXN - 1;
                }
                textureManager.bindTexture((ResourceLocation)jtTe.get(n13));
                Gui.drawModalRectWithCustomSizedTexture(0 - n12, 0, 0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), (float)scaledResolution.getScaledWidth(), (float)scaledResolution.getScaledHeight());
            }
            if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                throw null;
            }
        }
        else {
            this.mc.getTextureManager().bindTexture((ResourceLocation)qdlI.JTTe.get(qdlI.kbXN));
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), (float)scaledResolution.getScaledWidth(), (float)scaledResolution.getScaledHeight());
        }
        if (qdlI.bppf && this.qSYb.QOXG(10000.0)) {
            this.qSYb.BBwj();
            this.tPWM = false;
        }
        int n14 = (int)(n10 * 255.0f);
        if (n14 > 255) {
            n14 = 255;
        }
        int n15;
        if (n14 < 1) {
            n15 = 5;
            if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                throw null;
            }
        }
        else {
            n15 = n14;
        }
        final int fobH = n15;
        bozE.xISY.drawString("WWE Mod", 15, scaledResolution.getScaledHeight() / 8 - bozE.xISY.FONT_HEIGHT / 2, new Color(255, 255, 255, fobH).getRGB());
        bozE.xISY.drawString("Welcome " + Wrapper.mc.getSession().getUsername(), scaledResolution.getScaledWidth() - bozE.xISY.getStringWidth("Welcome " + Wrapper.mc.getSession().getUsername()) - 2, scaledResolution.getScaledHeight() - 13, new Color(255, 255, 255, fobH).getRGB());
        bozE.SPev.cECW(this.Katg, scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() - scaledResolution.getScaledHeight() / 8 - bozE.SPev.FONT_HEIGHT / 2, new Color(JIfG.JzFV(0xAA0CF39BC5D68775L ^ 0xAA0CF39BC5D68774L, 1.0f).getRed(), JIfG.JzFV(0x4BD9E94F4D91E6F3L ^ 0x4BD9E94F4D91E6F2L, 1.0f).getGreen(), JIfG.JzFV(0x876BDB3F65BBB5E7L ^ 0x876BDB3F65BBB5E6L, 1.0f).getBlue(), fobH).getRGB());
        bozE.SPev.cECW(qdlI.gGMK, scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 2 - scaledResolution.getScaledHeight() / 4 - scaledResolution.getScaledHeight() / 13, new Color(255, 255, 255, fobH).getRGB());
        if (qdlI.WFhU) {
            bozE.SPev.cECW("Update available at wweclient.com or wweclient.ml", scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() - scaledResolution.getScaledHeight() / 8 - bozE.SPev.FONT_HEIGHT / 2 + scaledResolution.getScaledHeight() / 13, new Color(255, 255, 255, fobH).getRGB());
        }
        if (n > 15 && n < 15 + bozE.xISY.getStringWidth("WWE Mod") && n2 > scaledResolution.getScaledHeight() / 8 - 4 && n2 < scaledResolution.getScaledHeight() / 8 + 8) {
            drawRect(15, scaledResolution.getScaledHeight() / 8 + 7, 15 + bozE.xISY.getStringWidth("WWE Mod"), scaledResolution.getScaledHeight() / 8 + 8, new Color(255, 255, 255, fobH).getRGB());
        }
        if (qdlI.WFhU && n > scaledResolution.getScaledWidth() / 2 - bozE.SPev.getStringWidth("Update available at wweclient.ml or wweclient.com") / 2 && n < scaledResolution.getScaledWidth() / 2 + bozE.SPev.getStringWidth("Update available at wweclient.ml or wweclient.com") / 2 && n2 > scaledResolution.getScaledHeight() - scaledResolution.getScaledHeight() / 8 - 4 + scaledResolution.getScaledHeight() / 13 && n2 < scaledResolution.getScaledHeight() - scaledResolution.getScaledHeight() / 8 - 4 + scaledResolution.getScaledHeight() / 13 + 11) {
            drawRect(scaledResolution.getScaledWidth() / 2 - bozE.SPev.getStringWidth("Update available at wweclient.ml or wweclient.com") / 2, scaledResolution.getScaledHeight() - scaledResolution.getScaledHeight() / 8 - 4 + scaledResolution.getScaledHeight() / 13 + 12, scaledResolution.getScaledWidth() / 2 + bozE.SPev.getStringWidth("Update available at wweclient.ml or wweclient.com") / 2, scaledResolution.getScaledHeight() - scaledResolution.getScaledHeight() / 8 - 4 + scaledResolution.getScaledHeight() / 13 + 11, new Color(255, 255, 255, fobH).getRGB());
        }
        int n16 = 0;
        if (n > this.width - 40 && n < this.width && n2 > 0 && n2 < 40) {
            n16 = 100;
        }
        if (n > this.width - 20 && n < this.width && n2 > 0 && n2 < 20) {
            n16 = 150;
        }
        drawRect(this.width, 0, this.width - 20, 20, new Color(255, 0, 0, n16).getRGB());
        for (final GuiButton guiButton : this.buttonList) {
            if (!(guiButton instanceof Gpxt)) {
                if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                    throw null;
                }
                continue;
            }
            else {
                ((Gpxt)guiButton).fobH = fobH;
                if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                    throw null;
                }
                continue;
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        if (n > this.width - 20 && n < this.width && n2 > 0 && n2 < 20) {
            if (qdlI.kbXN + 1 >= qdlI.JTTe.size()) {
                qdlI.kbXN = 0;
                if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                    throw null;
                }
            }
            else {
                ++qdlI.kbXN;
            }
            Wrapper.ypJK().OsgT();
            this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
        if (n > 15 && n < 15 + bozE.xISY.getStringWidth("WWE Mod") && n2 > this.height / 8 - 4 && n2 < this.height / 8 + 8) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("https://wweclient.com"));
                    if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                        throw null;
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
        if (qdlI.WFhU && n > this.width / 2 - bozE.SPev.getStringWidth("Update available at wweclient.com or wweclient.com") / 2 && n < this.width / 2 + bozE.SPev.getStringWidth("Update available at wweclient.com or wweclient.com") / 2 && n2 > this.height - this.height / 8 - 4 + this.height / 13 && n2 < this.height - this.height / 8 - 4 + this.height / 13 + 11) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("https://wweclient.com"));
                    if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                        throw null;
                    }
                }
                catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }
            this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
        super.mouseClicked(n, n2, n3);
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen((GuiScreen)new GuiWorldSelection((GuiScreen)this));
        }
        if (guiButton.id == 2) {
            this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)this));
        }
        if (guiButton.id == 3) {
            new RealmsBridge().switchToRealms((GuiScreen)this);
        }
        if (guiButton.id == 4) {
            this.mc.displayGuiScreen((GuiScreen)new ssGO());
        }
        if (guiButton.id == 5) {
            this.mc.displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings));
        }
        if (guiButton.id == 21) {
            this.mc.displayGuiScreen((GuiScreen)new GuiMainMenu());
            ssGO.jMLq = false;
            Wrapper.ypJK().OsgT();
        }
        if (guiButton.id == 22) {
            this.mc.displayGuiScreen((GuiScreen)new xpXF());
        }
    }
    
    public void func_73869_a(final char c, final int n) throws IOException {
        if (n == 1) {
            this.Katg = qdlI.jBhh[new Random().nextInt(qdlI.jBhh.length)];
            return;
        }
        int kbXN = -1;
        int i = 0;
        while (i < qdlI.JTTe.size()) {
            if (n == Keyboard.getKeyIndex(i + 1 + "")) {
                kbXN = i;
                if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
                if (qdlI.\u2004\u2004\u200d\u200d\u200b\u2006\u2002) {
                    throw null;
                }
                continue;
            }
        }
        if (kbXN != -1) {
            qdlI.kbXN = kbXN;
            Wrapper.ypJK().OsgT();
        }
        super.keyTyped(c, n);
    }
    
    static {
        qdlI.kbXN = 0;
        qdlI.JTTe = new ArrayList<ResourceLocation>();
        qdlI.GHWo = 0;
        qdlI.bppf = true;
        qdlI.JTTe.add(new ResourceLocation("textures/wwe/back/1.png"));
        qdlI.JTTe.add(new ResourceLocation("textures/wwe/back/2.png"));
        qdlI.JTTe.add(new ResourceLocation("textures/wwe/back/3.png"));
        qdlI.JTTe.add(new ResourceLocation("textures/wwe/back/4.png"));
        qdlI.JTTe.add(new ResourceLocation("textures/wwe/back/5.png"));
        qdlI.JTTe.add(new ResourceLocation("textures/wwe/back/6.png"));
        qdlI.JTTe.add(new ResourceLocation("textures/wwe/back/7.png"));
        qdlI.GHWo = qdlI.JTTe.size();
    }
}
