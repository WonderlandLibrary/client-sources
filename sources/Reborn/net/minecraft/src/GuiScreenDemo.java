package net.minecraft.src;

import java.net.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;

public class GuiScreenDemo extends GuiScreen
{
    @Override
    public void initGui() {
        this.buttonList.clear();
        final byte var1 = -16;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 116, this.height / 2 + 62 + var1, 114, 20, StatCollector.translateToLocal("demo.help.buy")));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height / 2 + 62 + var1, 114, 20, StatCollector.translateToLocal("demo.help.later")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        switch (par1GuiButton.id) {
            case 1: {
                par1GuiButton.enabled = false;
                try {
                    final Class var2 = Class.forName("java.awt.Desktop");
                    final Object var3 = var2.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
                    var2.getMethod("browse", URI.class).invoke(var3, new URI("http://www.minecraft.net/store?source=demo"));
                }
                catch (Throwable var4) {
                    var4.printStackTrace();
                }
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            }
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
    }
    
    @Override
    public void drawDefaultBackground() {
        super.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/demo_bg.png");
        final int var1 = (this.width - 248) / 2;
        final int var2 = (this.height - 166) / 2;
        this.drawTexturedModalRect(var1, var2, 0, 0, 248, 166);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        final int var4 = (this.width - 248) / 2 + 10;
        int var5 = (this.height - 166) / 2 + 8;
        this.fontRenderer.drawString(StatCollector.translateToLocal("demo.help.title"), var4, var5, 2039583);
        var5 += 12;
        final GameSettings var6 = this.mc.gameSettings;
        String var7 = StatCollector.translateToLocal("demo.help.movementShort");
        var7 = String.format(var7, Keyboard.getKeyName(var6.keyBindForward.keyCode), Keyboard.getKeyName(var6.keyBindLeft.keyCode), Keyboard.getKeyName(var6.keyBindBack.keyCode), Keyboard.getKeyName(var6.keyBindRight.keyCode));
        this.fontRenderer.drawString(var7, var4, var5, 5197647);
        var7 = StatCollector.translateToLocal("demo.help.movementMouse");
        this.fontRenderer.drawString(var7, var4, var5 + 12, 5197647);
        var7 = StatCollector.translateToLocal("demo.help.jump");
        var7 = String.format(var7, Keyboard.getKeyName(var6.keyBindJump.keyCode));
        this.fontRenderer.drawString(var7, var4, var5 + 24, 5197647);
        var7 = StatCollector.translateToLocal("demo.help.inventory");
        var7 = String.format(var7, Keyboard.getKeyName(var6.keyBindInventory.keyCode));
        this.fontRenderer.drawString(var7, var4, var5 + 36, 5197647);
        this.fontRenderer.drawSplitString(StatCollector.translateToLocal("demo.help.fullWrapped"), var4, var5 + 68, 218, 2039583);
        super.drawScreen(par1, par2, par3);
    }
}
