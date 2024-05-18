package wwe;

import net.minecraft.client.gui.*;
import wwe.utils.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import java.io.*;
import net.minecraft.util.*;
import java.util.*;

public class jCxW extends GuiScreen
{
    public sbfc FyML;
    
    public jCxW() {
        super();
    }
    
    public void func_73866_w_() {
        (this.FyML = new sbfc()).BBwj();
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.FyML.gCgh();
        if (this.FyML.QOXG(3000.0)) {
            this.Qlum();
        }
        final int n4 = this.width / 2 - this.width / 4;
        final int n5 = this.height / 2 - this.height / 4;
        final int n6 = this.width / 2 + this.width / 4;
        final int n7 = this.height / 2 + this.height / 4;
        drawRect(n4, n5, n6, n7, -1);
        drawRect(n4 + 2, n5 + 2, this.width / 2 - 2, n7 - 2, -16777216);
        Wrapper.fontRenderer.drawString("Select Song", this.width / 2 - this.width / 4 / 2 - Wrapper.fontRenderer.getStringWidth("Select Song") / 2, this.height / 2 - Wrapper.fontRenderer.FONT_HEIGHT / 2, -1);
        drawRect(n6 - 2, n5 + 2, this.width / 2 + 2, n7 - 2, -16777216);
        Wrapper.fontRenderer.drawString("Download Songs", this.width / 2 + this.width / 4 / 2 - Wrapper.fontRenderer.getStringWidth("Download Songs") / 2, this.height / 2 - Wrapper.fontRenderer.FONT_HEIGHT / 2, -1);
        drawRect(this.width / 2 - 16, n5 + 2, this.width / 2 + 16, n5 + 2 + 32, -1);
        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        this.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Blocks.NOTEBLOCK), (this.width / 2 - 16) / 2, (n5 + 3) / 2);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        final int n4 = this.height / 2 - this.height / 4;
        if (n >= this.width / 2 - 16 && n2 >= n4 + 3 && n < this.width / 2 + 16 && n2 < n4 + 3 + 31) {
            this.Qlum();
            if (jCxW.\u2007\u2001\u200b\u200d\u200a\u200a\u2008\u200d\u2004) {
                throw null;
            }
        }
        else if (n >= this.width / 2 - this.width / 4 + 2 && n < this.width / 2 - 2 && n2 >= this.height / 2 - this.height / 4 + 2 && n2 < this.height / 2 + this.height / 4 - 2) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new QkhT(this));
            Wrapper.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            if (jCxW.\u2007\u2001\u200b\u200d\u200a\u200a\u2008\u200d\u2004) {
                throw null;
            }
        }
        else if (n >= this.width / 2 + 2 && n < this.width / 2 + this.width / 4 - 2 && n2 >= this.height / 2 - this.height / 4 + 2 && n2 < this.height / 2 + this.height / 4 - 2) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new faUw(this));
            Wrapper.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    public void Qlum() {
        Wrapper.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)yYGD.VVcl(Blocks.NOTEBLOCK).get(new Random().nextInt(yYGD.VVcl(Blocks.NOTEBLOCK).size())), (float)Math.pow(2.0, (new Random().nextInt(9) - 12) / 12.0)));
        this.FyML.BBwj();
    }
}
