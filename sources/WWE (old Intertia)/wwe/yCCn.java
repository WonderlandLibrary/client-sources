package wwe;

import net.minecraft.client.gui.*;
import wwe.utils.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import java.util.*;
import wwe.modules.movement.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.io.*;

public class yCCn extends GuiScreen
{
    public GuiScreen BbyI;
    public File aDPv;
    public int vKBT;
    public ajLR duNU;
    public boolean XZpr;
    public ajLR.wwe/ABpb nnWu;
    public GuiTextField wsUw;
    public GuiTextField cMST;
    public GuiTextField gJOx;
    
    public yCCn(final GuiScreen bbyI, final File adPv) {
        super();
        this.BbyI = bbyI;
        this.aDPv = adPv;
    }
    
    public void func_73866_w_() {
        this.addButton(new GuiButton(0, this.width / 2 - 17 - 39, this.height / 2 + this.height / 4 - 25, 34, 20, "Save"));
        this.addButton(new GuiButton(1, this.width / 2 - 17, this.height / 2 + this.height / 4 - 25, 34, 20, "Back"));
        this.addButton(new GuiButton(2, this.width / 2 - 17 + 39, this.height / 2 + this.height / 4 - 25, 34, 20, "Play"));
        this.addButton(new GuiButton(3, this.width / 2 - 42, this.height / 2 + this.height / 4 - 50, 84, 20, "Place lyric here"));
        final int n = this.width / 2 - this.width / 4;
        final int n2 = this.height / 2 - this.height / 4;
        final int n3 = this.width / 2 + this.width / 4;
        final int n4 = this.height / 2 + this.height / 4;
        (this.cMST = new GuiTextField(0, Wrapper.fontRenderer, this.width / 2 - this.width / 4 + 5, this.height / 2 + this.height / 4 - 50, 20, 20)).setText("20");
        this.addButton(new GuiButton(4, this.width / 2 - this.width / 4 + 5, this.height / 2 + this.height / 4 - 25, 20, 20, "<-"));
        (this.gJOx = new GuiTextField(1, Wrapper.fontRenderer, this.width / 2 + this.width / 4 - 25, this.height / 2 + this.height / 4 - 50, 20, 20)).setText("20");
        this.addButton(new GuiButton(5, this.width / 2 + this.width / 4 - 25, this.height / 2 + this.height / 4 - 25, 20, 20, "->"));
        this.vKBT = 0;
        this.duNU = Wrapper.ypJK().FpyV(this.aDPv.getName());
        this.nnWu = null;
        this.wsUw = null;
        super.initGui();
    }
    
    public void func_73876_c() {
        if (this.XZpr) {
            for (final ajLR.wwe/WkII wwe/WkII : this.duNU.FgWb) {
                if (wwe/WkII.cCcA == this.vKBT) {
                    Wrapper.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)yYGD.VVcl(Blocks.NOTEBLOCK).get(wwe/WkII.eEsY), (float)Math.pow(2.0, (wwe/WkII.fljR - 12) / 12.0)));
                }
                if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                    throw null;
                }
            }
            for (final ajLR.wwe/ABpb wwe/ABpb : this.duNU.nOsS) {
                if (wwe/ABpb.RZpY == this.vKBT) {
                    HAxG.TuzD(wwe/ABpb.MSAn);
                }
                if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                    throw null;
                }
            }
            ++this.vKBT;
        }
        if (this.wsUw != null) {
            this.wsUw.updateCursorCounter();
        }
        if (this.cMST != null) {
            this.cMST.updateCursorCounter();
        }
        if (this.gJOx != null) {
            this.gJOx.updateCursorCounter();
        }
        super.updateScreen();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        final int n4 = this.width / 2 - this.width / 4;
        final int n5 = this.height / 2 - this.height / 4;
        final int n6 = this.width / 2 + this.width / 4;
        final int n7 = this.height / 2 + this.height / 4;
        drawRect(n4, n5, n6, n7, -1);
        int cCcA = 0;
        for (final ajLR.wwe/WkII wwe/WkII : this.duNU.FgWb) {
            if (wwe/WkII.cCcA > cCcA) {
                cCcA = wwe/WkII.cCcA;
            }
            if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                throw null;
            }
        }
        drawRect(n4 + 2, n5 + 20, n6 - 2, n5 + 30, -16777216);
        final int abs = Math.abs(n4 + 2 - (n6 - 2));
        final Iterator<ajLR.wwe/ABpb> iterator2 = this.duNU.nOsS.iterator();
        while (iterator2.hasNext()) {
            final int n8 = abs * iterator2.next().RZpY / (cCcA - 1);
            drawRect(n4 + 2 + n8, n5 + 20, n4 + 2 + n8 + 2, n5 + 30, -16711936);
            if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                throw null;
            }
        }
        int n9 = 0;
        if (this.vKBT > cCcA + 1) {
            this.vKBT = cCcA + 1;
        }
        if (this.vKBT > 0) {
            n9 = abs * this.vKBT / (cCcA - 1);
        }
        final int n10 = cCcA / 20;
        final int n11 = n10 / 60;
        final String string = "Current tick " + this.vKBT + ", " + this.aDPv.getName().substring(0, this.aDPv.getName().lastIndexOf(".")) + ", " + n11 + new String(Speed.rhii((n10 - n11 * 60) * 1.6666666666666667 / 100.0, 1) + "").substring(1) + " minutes";
        Wrapper.fontRenderer.drawString(string, this.width / 2 - Wrapper.fontRenderer.getStringWidth(string) / 2, n5 + 5, -65536);
        drawRect(n4 + 2 + n9, n5 + 20, n4 + 2 + n9 + 2, n5 + 30, -65536);
        if (this.wsUw == null) {
            Wrapper.fontRenderer.drawString("Materials:", this.width / 2 - Wrapper.fontRenderer.getStringWidth("Materials:") / 2, n5 + Math.abs(n5 - n7) / 2 - 20, -16777216);
            int n12 = this.width / 2 - this.duNU.YDyR.size() * 15 / 2;
            for (final Integer n13 : this.duNU.YDyR) {
                if (n13 == 0) {
                    Wrapper.fontRenderer.drawString("Air", n12 + 2, n5 + Math.abs(n5 - n7) / 2 - 6, -16777216);
                    n12 += 15;
                }
                if (n13 == 1) {
                    RenderHelper.enableStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Blocks.STONE), n12, n5 + Math.abs(n5 - n7) / 2 - 10);
                    RenderHelper.disableStandardItemLighting();
                    n12 += 15;
                }
                if (n13 == 2) {
                    RenderHelper.enableStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack((Block)Blocks.SAND), n12, n5 + Math.abs(n5 - n7) / 2 - 10);
                    RenderHelper.disableStandardItemLighting();
                    n12 += 15;
                }
                if (n13 == 3) {
                    RenderHelper.enableStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Blocks.GLASS), n12, n5 + Math.abs(n5 - n7) / 2 - 10);
                    RenderHelper.disableStandardItemLighting();
                    n12 += 15;
                }
                if (n13 == 4) {
                    RenderHelper.enableStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Blocks.PLANKS), n12, n5 + Math.abs(n5 - n7) / 2 - 10);
                    RenderHelper.disableStandardItemLighting();
                    n12 += 15;
                }
                if (n13 == 5) {
                    RenderHelper.enableStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Blocks.CLAY), n12, n5 + Math.abs(n5 - n7) / 2 - 10);
                    RenderHelper.disableStandardItemLighting();
                    n12 += 15;
                }
                if (n13 == 6) {
                    RenderHelper.enableStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Blocks.GOLD_BLOCK), n12, n5 + Math.abs(n5 - n7) / 2 - 10);
                    RenderHelper.disableStandardItemLighting();
                    n12 += 15;
                }
                if (n13 == 7) {
                    RenderHelper.enableStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Blocks.WOOL), n12, n5 + Math.abs(n5 - n7) / 2 - 10);
                    RenderHelper.disableStandardItemLighting();
                    n12 += 15;
                }
                if (n13 == 8) {
                    RenderHelper.enableStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Blocks.PACKED_ICE), n12, n5 + Math.abs(n5 - n7) / 2 - 10);
                    RenderHelper.disableStandardItemLighting();
                    n12 += 15;
                }
                if (n13 == 9) {
                    RenderHelper.enableStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Blocks.BONE_BLOCK), n12, n5 + Math.abs(n5 - n7) / 2 - 10);
                    RenderHelper.disableStandardItemLighting();
                    n12 += 15;
                }
                if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                    throw null;
                }
            }
        }
        if (this.wsUw != null) {
            Wrapper.fontRenderer.drawString("Lyric", this.width / 2 - Wrapper.fontRenderer.getStringWidth("Lyric") / 2, n5 + 40, -65536);
            this.wsUw.drawTextBox();
        }
        if (this.cMST != null) {
            Wrapper.fontRenderer.drawString("Ticks", n4 + 5, n7 - 60, -16777216);
            this.cMST.drawTextBox();
        }
        if (this.gJOx != null) {
            Wrapper.fontRenderer.drawString("Ticks", n6 - 25, n7 - 60, -16777216);
            this.gJOx.drawTextBox();
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        final int n4 = this.width / 2 - this.width / 4;
        final int n5 = this.height / 2 - this.height / 4;
        final int n6 = this.width / 2 + this.width / 4;
        final int abs = Math.abs(n4 + 2 - (n6 - 2));
        int cCcA = 0;
        for (final ajLR.wwe/WkII wwe/WkII : this.duNU.FgWb) {
            if (wwe/WkII.cCcA > cCcA) {
                cCcA = wwe/WkII.cCcA;
            }
            if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                throw null;
            }
        }
        for (final ajLR.wwe/ABpb nnWu : this.duNU.nOsS) {
            final int n7 = abs * nnWu.RZpY / (cCcA - 1);
            if (n >= n4 + 2 + n7 && n < n4 + 4 + n7 && n2 >= n5 + 20 && n2 < n5 + 30 && nnWu != null) {
                if (nnWu == this.nnWu) {
                    this.actionPerformed((GuiButton)this.buttonList.get(7));
                }
                this.nnWu = nnWu;
                (this.wsUw = new GuiTextField(0, Wrapper.fontRenderer, this.width / 2 - 125, n5 + 50, 250, 20)).setText(this.nnWu.MSAn);
                this.addButton(new GuiButton(6, this.width / 2 - Wrapper.fontRenderer.getStringWidth("Delete") / 2, n5 + 73, 35, 20, "Delete"));
                this.addButton(new GuiButton(7, this.width / 2 - Wrapper.fontRenderer.getStringWidth("Hide Lyric Box") / 2, n5 + 94, 75, 20, "Hide Lyric Box"));
                return;
            }
            if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                throw null;
            }
        }
        if (n >= n4 + 2 && n < n6 - 2 && n2 >= n5 + 20 && n2 < n5 + 30) {
            this.duNU.nOsS.add(new ajLR.wwe/ABpb((n - n4 - 2) * cCcA / abs, ""));
        }
        if (this.wsUw != null) {
            this.wsUw.mouseClicked(n, n2, n3);
        }
        if (this.cMST != null) {
            this.cMST.mouseClicked(n, n2, n3);
        }
        if (this.gJOx != null) {
            this.gJOx.mouseClicked(n, n2, n3);
        }
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            int i = 0;
            while (i < this.duNU.nOsS.size()) {
                if (this.duNU.nOsS.get(i).MSAn.equals("")) {
                    if (this.duNU.nOsS != null && this.duNU.nOsS.get(i) == this.nnWu) {
                        this.actionPerformed((GuiButton)this.buttonList.get(6));
                        --i;
                        if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.duNU.nOsS.remove(i);
                        --i;
                    }
                }
                ++i;
                if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                    throw null;
                }
            }
            Wrapper.ypJK().HLif(this.aDPv.getName().substring(0, this.aDPv.getName().lastIndexOf(".")), this.duNU, true);
        }
        if (guiButton.id == 1) {
            Wrapper.mc.displayGuiScreen(this.BbyI);
        }
        if (guiButton.id == 2) {
            boolean xZpr;
            if (!this.XZpr) {
                xZpr = true;
                if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                    throw null;
                }
            }
            else {
                xZpr = false;
            }
            this.XZpr = xZpr;
            String displayString;
            if (this.XZpr) {
                displayString = "Pause";
                if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                    throw null;
                }
            }
            else {
                displayString = "Play";
            }
            guiButton.displayString = displayString;
        }
        if (guiButton.id == 3) {
            if (this.nnWu != null) {
                this.actionPerformed((GuiButton)this.buttonList.get(7));
            }
            final int n = this.height / 2 - this.height / 4;
            final ajLR.wwe/ABpb nnWu = new ajLR.wwe/ABpb(this.vKBT, "");
            this.duNU.nOsS.add(nnWu);
            this.nnWu = nnWu;
            (this.wsUw = new GuiTextField(0, Wrapper.fontRenderer, this.width / 2 - 125, n + 50, 250, 20)).setText(this.nnWu.MSAn);
            this.addButton(new GuiButton(6, this.width / 2 - Wrapper.fontRenderer.getStringWidth("Delete") / 2, n + 73, 35, 20, "Delete"));
            this.addButton(new GuiButton(7, this.width / 2 - Wrapper.fontRenderer.getStringWidth("Hide Lyric Box") / 2, n + 94, 75, 20, "Hide Lyric Box"));
        }
        if (guiButton.id == 4) {
            final int vkbt = this.vKBT;
            int int1;
            if (this.cMST == null || this.cMST.getText().isEmpty() || this.cMST.getText().equals("")) {
                int1 = 0;
                if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                    throw null;
                }
            }
            else {
                int1 = Integer.parseInt(this.cMST.getText());
            }
            this.vKBT = vkbt - int1;
        }
        if (guiButton.id == 5) {
            final int vkbt2 = this.vKBT;
            int int2;
            if (this.gJOx == null || this.gJOx.getText().isEmpty() || this.gJOx.getText().equals("")) {
                int2 = 0;
                if (yCCn.\u200e\u2006\u200d\u200b\u2002\u2007) {
                    throw null;
                }
            }
            else {
                int2 = Integer.parseInt(this.gJOx.getText());
            }
            this.vKBT = vkbt2 + int2;
        }
        if (guiButton.id == 6) {
            this.duNU.nOsS.remove(this.nnWu);
            this.nnWu = null;
            this.wsUw = null;
            this.buttonList.remove(7);
            this.buttonList.remove(guiButton);
        }
        if (guiButton.id == 7) {
            if (this.nnWu.MSAn.equals("")) {
                this.duNU.nOsS.remove(this.nnWu);
            }
            this.nnWu = null;
            this.wsUw = null;
            this.buttonList.remove(6);
            this.buttonList.remove(guiButton);
        }
    }
    
    public void func_73869_a(final char c, final int n) throws IOException {
        if (n == 1) {
            Wrapper.mc.displayGuiScreen(this.BbyI);
        }
        if (this.wsUw != null && this.wsUw.textboxKeyTyped(c, n)) {
            this.nnWu.MSAn = this.wsUw.getText();
        }
        if (this.cMST != null && (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || n == 14 || n == 203 || n == 205 || n == 211)) {
            this.cMST.textboxKeyTyped(c, n);
        }
        if (this.gJOx != null && (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || n == 14 || n == 203 || n == 205 || n == 211)) {
            this.gJOx.textboxKeyTyped(c, n);
        }
    }
}
