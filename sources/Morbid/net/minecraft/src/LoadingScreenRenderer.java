package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class LoadingScreenRenderer implements IProgressUpdate
{
    private String field_73727_a;
    private Minecraft mc;
    private String currentlyDisplayedText;
    private long field_73723_d;
    private boolean field_73724_e;
    
    public LoadingScreenRenderer(final Minecraft par1Minecraft) {
        this.field_73727_a = "";
        this.currentlyDisplayedText = "";
        this.field_73723_d = Minecraft.getSystemTime();
        this.field_73724_e = false;
        this.mc = par1Minecraft;
    }
    
    @Override
    public void resetProgressAndMessage(final String par1Str) {
        this.field_73724_e = false;
        this.func_73722_d(par1Str);
    }
    
    @Override
    public void displayProgressMessage(final String par1Str) {
        this.field_73724_e = true;
        this.func_73722_d(par1Str);
    }
    
    public void func_73722_d(final String par1Str) {
        this.currentlyDisplayedText = par1Str;
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            final ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, var2.getScaledWidth_double(), var2.getScaledHeight_double(), 0.0, 100.0, 300.0);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -200.0f);
        }
    }
    
    @Override
    public void resetProgresAndWorkingMessage(final String par1Str) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            this.field_73723_d = 0L;
            this.field_73727_a = par1Str;
            this.setLoadingProgress(-1);
            this.field_73723_d = 0L;
        }
    }
    
    @Override
    public void setLoadingProgress(final int par1) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            final long var2 = Minecraft.getSystemTime();
            if (var2 - this.field_73723_d >= 100L) {
                this.field_73723_d = var2;
                final ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                final int var4 = ScaledResolution.getScaledWidth();
                final int var5 = ScaledResolution.getScaledHeight();
                GL11.glClear(256);
                GL11.glMatrixMode(5889);
                GL11.glLoadIdentity();
                GL11.glOrtho(0.0, var3.getScaledWidth_double(), var3.getScaledHeight_double(), 0.0, 100.0, 300.0);
                GL11.glMatrixMode(5888);
                GL11.glLoadIdentity();
                GL11.glTranslatef(0.0f, 0.0f, -200.0f);
                GL11.glClear(16640);
                final Tessellator var6 = Tessellator.instance;
                this.mc.renderEngine.bindTexture("/gui/background.png");
                final float var7 = 32.0f;
                var6.startDrawingQuads();
                var6.setColorOpaque_I(4210752);
                var6.addVertexWithUV(0.0, var5, 0.0, 0.0, var5 / var7);
                var6.addVertexWithUV(var4, var5, 0.0, var4 / var7, var5 / var7);
                var6.addVertexWithUV(var4, 0.0, 0.0, var4 / var7, 0.0);
                var6.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                var6.draw();
                if (par1 >= 0) {
                    final byte var8 = 100;
                    final byte var9 = 2;
                    final int var10 = var4 / 2 - var8 / 2;
                    final int var11 = var5 / 2 + 16;
                    GL11.glDisable(3553);
                    var6.startDrawingQuads();
                    var6.setColorOpaque_I(8421504);
                    var6.addVertex(var10, var11, 0.0);
                    var6.addVertex(var10, var11 + var9, 0.0);
                    var6.addVertex(var10 + var8, var11 + var9, 0.0);
                    var6.addVertex(var10 + var8, var11, 0.0);
                    var6.setColorOpaque_I(8454016);
                    var6.addVertex(var10, var11, 0.0);
                    var6.addVertex(var10, var11 + var9, 0.0);
                    var6.addVertex(var10 + par1, var11 + var9, 0.0);
                    var6.addVertex(var10 + par1, var11, 0.0);
                    var6.draw();
                    GL11.glEnable(3553);
                }
                this.mc.fontRenderer.drawStringWithShadow(this.currentlyDisplayedText, (var4 - this.mc.fontRenderer.getStringWidth(this.currentlyDisplayedText)) / 2, var5 / 2 - 4 - 16, 16777215);
                this.mc.fontRenderer.drawStringWithShadow(this.field_73727_a, (var4 - this.mc.fontRenderer.getStringWidth(this.field_73727_a)) / 2, var5 / 2 - 4 + 8, 16777215);
                Display.update();
                try {
                    Thread.yield();
                }
                catch (Exception ex) {}
            }
        }
    }
    
    @Override
    public void onNoMoreProgress() {
    }
}
