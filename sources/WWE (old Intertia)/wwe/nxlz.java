package wwe;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class nxlz extends GuiSlot
{
    private fIdW Inrt;
    private int bsOT;
    
    public nxlz(final Minecraft minecraft, final fIdW inrt) {
        super(minecraft, inrt.width, inrt.height, 32, inrt.height - 59, 25);
        this.Inrt = inrt;
        this.bsOT = -1;
    }
    
    protected int func_148127_b() {
        return wGez.VkYf.size();
    }
    
    protected void func_148144_a(final int bsOT, final boolean b, final int n, final int n2) {
        this.bsOT = bsOT;
    }
    
    protected boolean func_148131_a(final int n) {
        boolean b;
        if (this.bsOT == n) {
            b = true;
            if (nxlz.\u200b) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    protected int zFMV() {
        return this.bsOT;
    }
    
    protected void func_148123_a() {
        this.Inrt.drawDefaultBackground();
    }
    
    protected void func_192637_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final float n7) {
        final aRkr aRkr = wGez.VkYf.get(n);
        this.Inrt.drawString(this.Inrt.qLsH(), aRkr.dcDV(), n2, n3 + 1, 16777215);
        if (aRkr.RZeI()) {
            this.Inrt.drawString(this.Inrt.qLsH(), "Premium", n2, n3 + 12, 65280);
            if (nxlz.\u200b) {
                throw null;
            }
        }
        else {
            this.Inrt.drawString(this.Inrt.qLsH(), "§mPremium", n2, n3 + 12, 10027008);
        }
    }
    
    public void AkNw(final float n, final float n2, final int n3, final int n4, final int n5, final int n6, final float n7) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)(n + 0.0f), (double)(n2 + n6), (double)n7);
        buffer.pos((double)(n + n5), (double)(n2 + n6), (double)n7);
        buffer.pos((double)(n + n5), (double)(n2 + 0.0f), (double)n7);
        buffer.pos((double)(n + 0.0f), (double)(n2 + 0.0f), (double)n7);
        instance.draw();
    }
}
