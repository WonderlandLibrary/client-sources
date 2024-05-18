package wwe;

import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import wwe.utils.*;
import net.minecraft.util.math.*;
import java.io.*;
import net.minecraft.item.*;
import wwe.modules.world.*;

public class bSlU extends GuiScreen
{
    public static GuiTextField mSnY;
    public static GuiTextField WmQa;
    public static GuiTextField ySMj;
    public static GuiTextField KVEk;
    public static Xssq Qina;
    
    public bSlU(final Xssq qina) {
        super();
        bSlU.Qina = qina;
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        bSlU.mSnY = new GuiTextField(0, this.fontRenderer, this.width / 2 - 51, this.height / 2 - this.height / 4 + this.height / 8, 103, 20);
        bSlU.WmQa = new GuiTextField(2, this.fontRenderer, this.width / 2 - this.width / 4 + 2, this.height / 2 + this.height / 4 - this.height / 5, 40, 20);
        bSlU.ySMj = new GuiTextField(3, this.fontRenderer, this.width / 2 - this.width / 4 + 52, this.height / 2 + this.height / 4 - this.height / 5, 40, 20);
        bSlU.KVEk = new GuiTextField(4, this.fontRenderer, this.width / 2 - this.width / 4 + 102, this.height / 2 + this.height / 4 - this.height / 5, 40, 20);
        this.buttonList.add(new GuiButton(20, this.width / 2 + this.width / 6, this.height / 2 + this.height / 4 - this.height / 5, 48, 20, "Remove"));
        this.buttonList.add(new GuiButton(21, this.width / 2 + this.width / 6, this.height / 2 + this.height / 4 - this.height / 5 - this.height / 12, 48, 20, this.iiXK()));
        this.buttonList.add(new dDAG(0, this.width / 2 - 112, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -16777216));
        this.buttonList.add(new dDAG(9, this.width / 2 - 96, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -7650029));
        this.buttonList.add(new dDAG(1, this.width / 2 - 80, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -16777046));
        this.buttonList.add(new dDAG(2, this.width / 2 - 64, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -16733696));
        this.buttonList.add(new dDAG(3, this.width / 2 - 48, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -16733526));
        this.buttonList.add(new dDAG(4, this.width / 2 - 32, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -5636096));
        this.buttonList.add(new dDAG(5, this.width / 2 - 16, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -5635926));
        this.buttonList.add(new dDAG(6, this.width / 2, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -22016));
        this.buttonList.add(new dDAG(7, this.width / 2 + 16, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -5592406));
        this.buttonList.add(new dDAG(8, this.width / 2 + 32, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -11184811));
        this.buttonList.add(new dDAG(10, this.width / 2 + 48, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -11141291));
        this.buttonList.add(new dDAG(11, this.width / 2 + 64, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -11141121));
        this.buttonList.add(new dDAG(12, this.width / 2 + 80, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -43521));
        this.buttonList.add(new dDAG(13, this.width / 2 + 96, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -171));
        this.buttonList.add(new dDAG(14, this.width / 2 + 112, this.height / 2 + this.height / 4 - this.height / 15, 16, 16, "", -1));
        bSlU.mSnY.setMaxStringLength(50);
        bSlU.WmQa.setMaxStringLength(8);
        bSlU.ySMj.setMaxStringLength(8);
        bSlU.KVEk.setMaxStringLength(8);
        bSlU.mSnY.setText(bSlU.Qina.CkDS);
        bSlU.WmQa.setText("" + bSlU.Qina.Nehz.getX());
        bSlU.ySMj.setText("" + bSlU.Qina.Nehz.getY());
        bSlU.KVEk.setText("" + bSlU.Qina.Nehz.getZ());
        this.updateScreen();
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        gnNa.wQYt((float)(this.width / 2 - this.width / 4), (float)(this.height / 2 - this.height / 4), (float)(this.width / 2 + this.width / 4), (float)(this.height / 2 + this.height / 4), -1);
        bSlU.mSnY.drawTextBox();
        bSlU.WmQa.drawTextBox();
        bSlU.ySMj.drawTextBox();
        bSlU.KVEk.drawTextBox();
        Wrapper.fontRenderer.drawString("Way Point Editor", this.width / 2 - Wrapper.fontRenderer.getStringWidth("Way Point Editor") / 2, this.height / 2 - this.height / 4 + this.height / 36, -16777216);
        Wrapper.fontRenderer.drawString("Way Point Name : " + bSlU.Qina.CkDS, this.width / 2 - Wrapper.fontRenderer.getStringWidth("Way Point Name : " + bSlU.Qina.CkDS) / 2, this.height / 2 - this.height / 4 + this.height / 12, -16777216);
        Wrapper.fontRenderer.drawString("Color", this.width / 2 - this.width / 4 + this.width / 12, this.height / 2 + this.height / 4 - this.height / 10, -16777216);
        Wrapper.fontRenderer.drawString("X", this.width / 2 - this.width / 4 + 2 + 18, this.height / 2 + this.height / 4 - this.height / 5 - 10, -16777216);
        Wrapper.fontRenderer.drawString("Y", this.width / 2 - this.width / 4 + 2 + 50 + 18, this.height / 2 + this.height / 4 - this.height / 5 - 10, -16777216);
        Wrapper.fontRenderer.drawString("Z", this.width / 2 - this.width / 4 + 2 + 100 + 18, this.height / 2 + this.height / 4 - this.height / 5 - 10, -16777216);
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
        if (!bSlU.mSnY.getText().isEmpty()) {
            bSlU.Qina.CkDS = bSlU.mSnY.getText();
        }
        if (!bSlU.WmQa.getText().isEmpty() && !bSlU.ySMj.getText().isEmpty() && !bSlU.KVEk.getText().isEmpty()) {
            bSlU.Qina.Nehz = new BlockPos(Double.parseDouble(bSlU.WmQa.getText()), Double.parseDouble(bSlU.ySMj.getText()), Double.parseDouble(bSlU.KVEk.getText()));
        }
        Wrapper.ypJK().VLak();
    }
    
    public void func_73876_c() {
        bSlU.mSnY.updateCursorCounter();
        bSlU.WmQa.updateCursorCounter();
        bSlU.ySMj.updateCursorCounter();
        bSlU.KVEk.updateCursorCounter();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        bSlU.mSnY.mouseClicked(n, n2, n3);
        bSlU.WmQa.mouseClicked(n, n2, n3);
        bSlU.ySMj.mouseClicked(n, n2, n3);
        bSlU.KVEk.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
    }
    
    protected void func_73869_a(final char c, final int n) {
        bSlU.mSnY.textboxKeyTyped(c, n);
        if (n == 1) {
            Wrapper.mc.displayGuiScreen((GuiScreen)null);
        }
        if (n != 11 && n != 2 && n != 3 && n != 4 && n != 5 && n != 6 && n != 7 && n != 8 && n != 9 && n != 10 && n != 14 && n != 211 && n != 203 && n != 205 && n != 200 && n != 208) {
            return;
        }
        bSlU.WmQa.textboxKeyTyped(c, n);
        bSlU.ySMj.textboxKeyTyped(c, n);
        bSlU.KVEk.textboxKeyTyped(c, n);
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            bSlU.Qina.rnCG = EnumDyeColor.BLACK;
        }
        if (guiButton.id == 1) {
            bSlU.Qina.rnCG = EnumDyeColor.BLUE;
        }
        if (guiButton.id == 2) {
            bSlU.Qina.rnCG = EnumDyeColor.GREEN;
        }
        if (guiButton.id == 3) {
            bSlU.Qina.rnCG = EnumDyeColor.CYAN;
        }
        if (guiButton.id == 4) {
            bSlU.Qina.rnCG = EnumDyeColor.RED;
        }
        if (guiButton.id == 5) {
            bSlU.Qina.rnCG = EnumDyeColor.PURPLE;
        }
        if (guiButton.id == 6) {
            bSlU.Qina.rnCG = EnumDyeColor.ORANGE;
        }
        if (guiButton.id == 7) {
            bSlU.Qina.rnCG = EnumDyeColor.SILVER;
        }
        if (guiButton.id == 8) {
            bSlU.Qina.rnCG = EnumDyeColor.GRAY;
        }
        if (guiButton.id == 9) {
            bSlU.Qina.rnCG = EnumDyeColor.BROWN;
        }
        if (guiButton.id == 10) {
            bSlU.Qina.rnCG = EnumDyeColor.LIME;
        }
        if (guiButton.id == 11) {
            bSlU.Qina.rnCG = EnumDyeColor.LIGHT_BLUE;
        }
        if (guiButton.id == 12) {
            bSlU.Qina.rnCG = EnumDyeColor.PINK;
        }
        if (guiButton.id == 13) {
            bSlU.Qina.rnCG = EnumDyeColor.YELLOW;
        }
        if (guiButton.id == 14) {
            bSlU.Qina.rnCG = EnumDyeColor.WHITE;
        }
        if (guiButton.id == 20) {
            WayPoints.ccNC(bSlU.Qina);
            Wrapper.mc.displayGuiScreen((GuiScreen)null);
        }
        if (guiButton.id == 21) {
            if (bSlU.Qina.ADFS && !bSlU.Qina.kKMJ && !bSlU.Qina.MUAZ) {
                bSlU.Qina.ADFS = false;
                bSlU.Qina.kKMJ = true;
                bSlU.Qina.MUAZ = false;
                if (bSlU.\u200b\u200b\u2000\u2007\u2006) {
                    throw null;
                }
            }
            else if (!bSlU.Qina.ADFS && bSlU.Qina.kKMJ && !bSlU.Qina.MUAZ) {
                bSlU.Qina.ADFS = false;
                bSlU.Qina.kKMJ = false;
                bSlU.Qina.MUAZ = true;
                if (bSlU.\u200b\u200b\u2000\u2007\u2006) {
                    throw null;
                }
            }
            else if (!bSlU.Qina.ADFS && !bSlU.Qina.kKMJ && bSlU.Qina.MUAZ) {
                bSlU.Qina.ADFS = true;
                bSlU.Qina.kKMJ = false;
                bSlU.Qina.MUAZ = false;
            }
            this.buttonList.clear();
            this.initGui();
        }
    }
    
    public String iiXK() {
        if (bSlU.Qina.ADFS && !bSlU.Qina.kKMJ && !bSlU.Qina.MUAZ) {
            return "Beacon";
        }
        if (!bSlU.Qina.ADFS && bSlU.Qina.kKMJ && !bSlU.Qina.MUAZ) {
            return "Box ESP";
        }
        if (!bSlU.Qina.ADFS && !bSlU.Qina.kKMJ && bSlU.Qina.MUAZ) {
            return "Tracer";
        }
        return "";
    }
}
