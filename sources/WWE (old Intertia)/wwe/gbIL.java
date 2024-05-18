package wwe;

import wwe.utils.*;
import org.lwjgl.opengl.*;
import java.io.*;
import net.minecraft.client.gui.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.util.math.*;

public class gbIL extends GuiScreen
{
    public GuiScreen MRWv;
    public File ucGA;
    public File iekf;
    public static File yUUB;
    public ArrayList<KkoX> ocWe;
    public KkoX Ezsz;
    public boolean WpCa;
    public ArrayList<String> dDfB;
    public String iHDa;
    public WeBh fbVi;
    public wwe/HwMm WmnF;
    
    public gbIL(final GuiScreen mrWv, final File ucGA, final boolean wpCa, final wwe/HwMm wmnF, final String... array) {
        super();
        this.ocWe = new ArrayList<KkoX>();
        this.dDfB = new ArrayList<String>();
        this.MRWv = mrWv;
        this.ucGA = ucGA;
        this.WpCa = wpCa;
        this.WmnF = wmnF;
        String string = "";
        for (final String s : array) {
            this.dDfB.add(s);
            string = string + s + ", ";
        }
        this.iHDa = "Pick a file with one of the following extensions: " + string.substring(0, string.lastIndexOf(","));
        if (gbIL.yUUB == null) {
            gbIL.yUUB = this.ucGA;
        }
    }
    
    public void func_73866_w_() {
        this.fbVi = new WeBh(0, bozE.TCnN, this.width / 2 + 135, 40, 50, 10);
        this.ocWe.clear();
        if (this.iekf == null) {
            File iekf;
            if (this.WpCa) {
                iekf = this.ucGA;
                if (gbIL.\u2005\u200d\u2007) {
                    throw null;
                }
            }
            else {
                iekf = gbIL.yUUB;
            }
            this.iekf = iekf;
        }
        if (this.iekf != null) {
            gbIL.yUUB = this.iekf;
        }
        int n = 50;
        this.ocWe.add(new KkoX(this, new ScaledResolution(Wrapper.mc).getScaledWidth() / 2 - 125, n, "..."));
        n += 10;
        final Iterator<File> iterator = Qaqa(this.iekf).iterator();
        while (iterator.hasNext()) {
            this.ocWe.add(new KkoX(this, new ScaledResolution(Wrapper.mc).getScaledWidth() / 2 - 125, n, iterator.next()));
            n += 10;
            if (gbIL.\u2005\u200d\u2007) {
                throw null;
            }
        }
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        Gui.drawRect(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -1);
        final int n4 = 1 * scaledResolution.getScaleFactor();
        GL11.glPushMatrix();
        GL11.glScaled(1.0 / scaledResolution.getScaleFactor(), 1.0 / scaledResolution.getScaleFactor(), 1.0);
        gnNa.AvjN((scaledResolution.getScaledWidth() / 2 - 125) * n4, 40 * n4, (scaledResolution.getScaledWidth() / 2 + 125) * n4, 50 * n4, 1, -4934476);
        GL11.glPopMatrix();
        this.fbVi.drawTextBox();
        bozE.TCnN.drawString(this.iekf.getAbsolutePath(), scaledResolution.getScaledWidth() / 2 - 125 + 3, 41, -16777216);
        this.ocWe.get(0).Fgbt(n, n2, n3);
        GL11.glPushMatrix();
        gnNa.zfJJ(0.0, 60.0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight() - 20);
        GL11.glEnable(3089);
        int i = 1;
        while (i < this.ocWe.size()) {
            this.ocWe.get(i).Fgbt(n, n2, n3);
            ++i;
            if (gbIL.\u2005\u200d\u2007) {
                throw null;
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        if (this.iHDa != null) {
            bozE.TCnN.drawString(this.iHDa, this.width / 2 - bozE.TCnN.getStringWidth(this.iHDa) / 2, 15, -16777216);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
    }
    
    public void func_73876_c() {
        this.fbVi.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        this.fbVi.mouseClicked(n, n2, n3);
        this.iHDa = null;
        int i = 0;
        while (i < this.ocWe.size()) {
            if (this.ocWe.get(i).IxcR(n, n2, n3)) {
                if (gbIL.\u2005\u200d\u2007) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
                if (gbIL.\u2005\u200d\u2007) {
                    throw null;
                }
                continue;
            }
        }
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_73869_a(final char c, final int n) throws IOException {
        if (this.fbVi.textboxKeyTyped(c, n)) {
            this.ocWe.clear();
            int n2 = 50;
            this.ocWe.add(new KkoX(this, new ScaledResolution(Wrapper.mc).getScaledWidth() / 2 - 125, n2, "..."));
            n2 += 10;
            for (final File file : Qaqa(this.iekf)) {
                if (this.fbVi.getText().isEmpty() || file.getName().toLowerCase().contains(this.fbVi.getText().toLowerCase()) || file.getName().toLowerCase().replaceAll(" ", "").contains(this.fbVi.getText().toLowerCase())) {
                    this.ocWe.add(new KkoX(this, new ScaledResolution(Wrapper.mc).getScaledWidth() / 2 - 125, n2, file));
                    n2 += 10;
                }
                if (gbIL.\u2005\u200d\u2007) {
                    throw null;
                }
            }
        }
        if (n == 1) {
            this.mc.displayGuiScreen(this.MRWv);
            return;
        }
        super.keyTyped(c, n);
    }
    
    public void func_146284_a(final GuiButton guiButton) throws IOException {
        super.actionPerformed(guiButton);
    }
    
    public static ArrayList<File> Qaqa(final File file) {
        final ArrayList<File> list = new ArrayList<File>();
        final ArrayList<File> list2 = new ArrayList<File>();
        if (file == null || file.listFiles() == null) {
            return list;
        }
        final File[] array = file.listFiles();
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final File file2 = array[i];
            if (file2.isDirectory()) {
                list.add(file2);
                if (gbIL.\u2005\u200d\u2007) {
                    throw null;
                }
            }
            else if (file2.isFile()) {
                list2.add(file2);
            }
            ++i;
            if (gbIL.\u2005\u200d\u2007) {
                throw null;
            }
        }
        list.addAll(list2);
        return list;
    }
    
    public boolean YMUh(final File file) {
        return this.dDfB.contains(file.getName().toLowerCase().substring(file.getName().lastIndexOf(".") + 1));
    }
    
    public void func_146274_d() throws IOException {
        final int dWheel = Mouse.getDWheel();
        if (dWheel > 0 || dWheel < 0) {
            final int clamp = MathHelper.clamp(dWheel, 1, -1);
            int i = 0;
            while (i < this.ocWe.size()) {
                final KkoX kkoX = this.ocWe.get(i);
                if (kkoX.mxzj != null) {
                    if (gbIL.\u2005\u200d\u2007) {
                        throw null;
                    }
                }
                else {
                    final KkoX kkoX2 = kkoX;
                    kkoX2.VEXW -= clamp * 10;
                }
                ++i;
                if (gbIL.\u2005\u200d\u2007) {
                    throw null;
                }
            }
        }
        super.handleMouseInput();
    }
    
    public interface wwe/HwMm
    {
        void gyPG(final File p0);
    }
}
