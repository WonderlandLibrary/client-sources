package wwe;

import wwe.utils.*;
import java.awt.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class oBqx extends GuiScreen
{
    public RCMD tIMq;
    public GuiTextField kEZV;
    public GuiTextField ZxjL;
    public GuiTextField namX;
    public fwEt mERx;
    public fwEt NOMW;
    public fwEt uLZF;
    public int BTrV;
    
    public oBqx(final RCMD tiMq) {
        super();
        this.tIMq = tiMq;
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.kEZV.drawTextBox();
        this.ZxjL.drawTextBox();
        this.namX.drawTextBox();
        this.mERx.QYaQ(n, n2, n3);
        this.NOMW.QYaQ(n, n2, n3);
        this.uLZF.QYaQ(n, n2, n3);
        Wrapper.fontRenderer.drawString("Red", this.width / 2 - 100 + 35, this.height / 2 - 88 - 10, -1);
        Wrapper.fontRenderer.drawString("Green", this.width / 2 - 100 + 85, this.height / 2 - 88 - 10, -1);
        Wrapper.fontRenderer.drawString("Blue", this.width / 2 - 100 + 135, this.height / 2 - 88 - 10, -1);
        final Color uuoB = this.uuoB();
        final int rgb = new Color(uuoB.getRed(), uuoB.getGreen(), uuoB.getBlue()).getRGB();
        XybC.eTim("Example", 50.0f, 40.0f, uuoB.getRGB());
        gnNa.axgH(50, 50, 100, 100, rgb, -16777216);
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.kEZV = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100 + 35, this.height / 2 - 88, 30, 20);
        this.ZxjL = new GuiTextField(1, this.fontRenderer, this.width / 2 - 100 + 85, this.height / 2 - 88, 30, 20);
        this.namX = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100 + 135, this.height / 2 - 88, 30, 20);
        this.kEZV.setMaxStringLength(3);
        this.ZxjL.setMaxStringLength(3);
        this.namX.setMaxStringLength(3);
        Wrapper.ypJK().JIqk();
        this.kEZV.setText(this.tIMq.zlwQ + "");
        this.ZxjL.setText(this.tIMq.elYg + "");
        this.namX.setText(this.tIMq.Kbjh + "");
        this.buttonList.add(new GuiButton(20, this.width / 2 - 100, this.height - 75, 98, 20, "Save"));
        this.buttonList.add(new GuiButton(22, this.width / 2 + 2, this.height - 75, 98, 20, "Default"));
        this.buttonList.add(new GuiButton(21, this.width / 2 - 100, this.height - 45, "Back"));
        this.mERx = new fwEt(this, 0, this.width / 2 - 127, this.height / 2 - 28);
        this.NOMW = new fwEt(this, 1, this.width / 2 - 127, this.height / 2 - 28 + 30);
        this.uLZF = new fwEt(this, 2, this.width / 2 - 127, this.height / 2 - 28 + 60);
        this.updateScreen();
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void func_73876_c() {
        this.kEZV.updateCursorCounter();
        this.ZxjL.updateCursorCounter();
        this.namX.updateCursorCounter();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        this.kEZV.mouseClicked(n, n2, n3);
        this.ZxjL.mouseClicked(n, n2, n3);
        this.namX.mouseClicked(n, n2, n3);
        this.mERx.rhRb(n, n2, n3);
        this.NOMW.rhRb(n, n2, n3);
        this.uLZF.rhRb(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
        this.mERx.dEUV(n, n2, n3);
        this.NOMW.dEUV(n, n2, n3);
        this.uLZF.dEUV(n, n2, n3);
    }
    
    protected void func_73869_a(final char c, final int n) {
        if (n != 11 && n != 2 && n != 3 && n != 4 && n != 5 && n != 6 && n != 7 && n != 8 && n != 9 && n != 10 && n != 14 && n != 211 && n != 203 && n != 205 && n != 200 && n != 208) {
            return;
        }
        this.kEZV.textboxKeyTyped(c, n);
        this.ZxjL.textboxKeyTyped(c, n);
        this.namX.textboxKeyTyped(c, n);
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 20) {
            this.tIMq.zlwQ = this.uuoB().getRed();
            this.tIMq.elYg = this.uuoB().getGreen();
            this.tIMq.Kbjh = this.uuoB().getBlue();
            Wrapper.ypJK().XRKn();
        }
        if (guiButton.id == 21) {
            Wrapper.mc.displayGuiScreen(this.tIMq.VhuH.kebz);
        }
        if (guiButton.id == 22) {
            this.kEZV.setText(this.tIMq.RzMj + "");
            this.ZxjL.setText(this.tIMq.IzwG + "");
            this.namX.setText(this.tIMq.YzNk + "");
            Wrapper.ypJK().XRKn();
        }
    }
    
    public Color uuoB() {
        if (this.kEZV.getText().isEmpty() || this.ZxjL.getText().isEmpty() || this.namX.getText().isEmpty()) {
            return new Color(this.tIMq.RzMj, this.tIMq.IzwG, this.tIMq.YzNk);
        }
        final Integer value = Integer.valueOf(this.kEZV.getText());
        final Integer value2 = Integer.valueOf(this.ZxjL.getText());
        final Integer value3 = Integer.valueOf(this.namX.getText());
        if (value > 255 || value2 > 255 || value3 > 255) {
            return new Color(1, 1, 1);
        }
        Color color;
        if (new Color(value, value2, value3) == null) {
            color = new Color(this.tIMq.RzMj, this.tIMq.IzwG, this.tIMq.RzMj);
            if (oBqx.\u200b\u2005\u200e) {
                throw null;
            }
        }
        else {
            color = new Color(value, value2, value3);
        }
        return color;
    }
    
    private int CHXF(final float n, final float n2, final float n3) {
        return (int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB(1.0f - n / 360.0f, n2, n3)), 16);
    }
}
