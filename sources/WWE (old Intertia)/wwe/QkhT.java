package wwe;

import wwe.utils.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import java.io.*;
import org.lwjgl.input.*;
import net.minecraft.util.math.*;
import wwe.modules.world.*;

public class QkhT extends GuiScreen
{
    public ArrayList<Deoo> EqPG;
    public Deoo wtTS;
    public int VgRj;
    public GuiTextField rbTn;
    public GuiScreen Mnia;
    
    public QkhT(final GuiScreen mnia) {
        super();
        this.EqPG = new ArrayList<Deoo>();
        this.Mnia = mnia;
    }
    
    public void func_73866_w_() {
        this.EqPG.clear();
        int n = 40;
        final int n2 = 200;
        final Iterator<File> iterator = Wrapper.ypJK().lUoc().iterator();
        while (iterator.hasNext()) {
            this.EqPG.add(new Deoo(this, iterator.next(), this.width / 2 - n2 / 2, n, n2, 10));
            n += 12;
            if (QkhT.\u2003\u200d\u200d\u200c\u2000\u2004\u200a\u200c) {
                throw null;
            }
        }
        this.addButton(new GuiButton(0, this.width / 2 - 25, this.height - 25, 50, 20, "Back"));
        this.addButton(new GuiButton(1, this.width / 2 - 80, this.height - 25, 50, 20, "Edit"));
        this.addButton(new GuiButton(2, this.width / 2 + 30, this.height - 25, 50, 20, "Use"));
        (this.rbTn = new GuiTextField(0, Wrapper.fontRenderer, this.width / 2 - 100, 10, 200, 20)).setFocused(true);
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        if (this.EqPG.size() < (this.height - 70) / 12) {
            this.VgRj = 0;
            if (QkhT.\u2003\u200d\u200d\u200c\u2000\u2004\u200a\u200c) {
                throw null;
            }
        }
        else if (this.VgRj > 0) {
            this.VgRj = 0;
            if (QkhT.\u2003\u200d\u200d\u200c\u2000\u2004\u200a\u200c) {
                throw null;
            }
        }
        else if (Math.abs(this.VgRj) > this.EqPG.size() * 12 - (this.height - 70)) {
            this.VgRj = -(this.EqPG.size() * 12 - (this.height - 70));
        }
        GL11.glPushMatrix();
        gnNa.zfJJ(0.0, 40.0, this.width, new ScaledResolution(Wrapper.mc).getScaledHeight_double() - 30.0);
        GL11.glEnable(3089);
        int i = 0;
        while (i < this.EqPG.size()) {
            this.EqPG.get(i).mmCA(n, n2, n3);
            ++i;
            if (QkhT.\u2003\u200d\u200d\u200c\u2000\u2004\u200a\u200c) {
                throw null;
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        this.rbTn.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73876_c() {
        this.rbTn.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        int i = 0;
        while (i < this.EqPG.size()) {
            final Deoo wtTS = this.EqPG.get(i);
            if (wtTS.VUFK) {
                if (this.wtTS == wtTS) {
                    this.wtTS = null;
                    if (QkhT.\u2003\u200d\u200d\u200c\u2000\u2004\u200a\u200c) {
                        throw null;
                    }
                }
                else {
                    this.wtTS = wtTS;
                }
                return;
            }
            ++i;
            if (QkhT.\u2003\u200d\u200d\u200c\u2000\u2004\u200a\u200c) {
                throw null;
            }
        }
        this.wtTS = null;
        this.rbTn.mouseClicked(n, n2, n3);
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    public void func_146274_d() throws IOException {
        final int dWheel = Mouse.getDWheel();
        if (dWheel > 0 || dWheel < 0) {
            this.VgRj -= MathHelper.clamp(dWheel, 3, -3);
        }
        super.handleMouseInput();
    }
    
    public void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            Wrapper.mc.displayGuiScreen(this.Mnia);
        }
        if (guiButton.id == 1 && this.wtTS != null) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new yCCn(this, this.wtTS.avoi));
        }
        if (guiButton.id == 2 && this.wtTS != null) {
            NoteBot.DbuO = Wrapper.ypJK().FpyV(this.wtTS.avoi.getName());
        }
    }
    
    public void func_73869_a(final char c, final int n) throws IOException {
        if (n == 1) {
            Wrapper.mc.displayGuiScreen(this.Mnia);
        }
        if (this.rbTn.textboxKeyTyped(c, n)) {
            this.EqPG.clear();
            this.VgRj = 0;
            int n2 = 40;
            final int n3 = 200;
            for (final File file : Wrapper.ypJK().lUoc()) {
                if (this.rbTn.getText().isEmpty() || file.getName().substring(0, file.getName().lastIndexOf(".")).toLowerCase().contains(this.rbTn.getText().toLowerCase())) {
                    this.EqPG.add(new Deoo(this, file, this.width / 2 - n3 / 2, n2, n3, 10));
                    n2 += 12;
                }
                if (QkhT.\u2003\u200d\u200d\u200c\u2000\u2004\u200a\u200c) {
                    throw null;
                }
            }
        }
    }
}
