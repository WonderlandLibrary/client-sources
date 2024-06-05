package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.net.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.settings.*;
import org.apache.logging.log4j.*;

public class GuiScreenDemo extends GuiScreen
{
    private static final Logger logger;
    private static final ResourceLocation field_146348_f;
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        final byte var1 = -16;
        this.buttonList.add(new GuiButton(1, GuiScreenDemo.width / 2 - 116, GuiScreenDemo.height / 2 + 62 + var1, 114, 20, I18n.format("demo.help.buy", new Object[0])));
        this.buttonList.add(new GuiButton(2, GuiScreenDemo.width / 2 + 2, GuiScreenDemo.height / 2 + 62 + var1, 114, 20, I18n.format("demo.help.later", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 1: {
                button.enabled = false;
                try {
                    final Class var2 = Class.forName("java.awt.Desktop");
                    final Object var3 = var2.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
                    var2.getMethod("browse", URI.class).invoke(var3, new URI("http://www.minecraft.net/store?source=demo"));
                }
                catch (Throwable var4) {
                    GuiScreenDemo.logger.error("Couldn't open link", var4);
                }
                break;
            }
            case 2: {
                GuiScreenDemo.mc.displayGuiScreen(null);
                GuiScreenDemo.mc.setIngameFocus();
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
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiScreenDemo.mc.getTextureManager().bindTexture(GuiScreenDemo.field_146348_f);
        final int var1 = (GuiScreenDemo.width - 248) / 2;
        final int var2 = (GuiScreenDemo.height - 166) / 2;
        this.drawTexturedModalRect(var1, var2, 0, 0, 248, 166);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        final int var4 = (GuiScreenDemo.width - 248) / 2 + 10;
        int var5 = (GuiScreenDemo.height - 166) / 2 + 8;
        this.fontRendererObj.drawString(I18n.format("demo.help.title", new Object[0]), var4, var5, 2039583);
        var5 += 12;
        final GameSettings var6 = GuiScreenDemo.mc.gameSettings;
        this.fontRendererObj.drawString(I18n.format("demo.help.movementShort", GameSettings.getKeyDisplayString(var6.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindRight.getKeyCode())), var4, var5, 5197647);
        this.fontRendererObj.drawString(I18n.format("demo.help.movementMouse", new Object[0]), var4, var5 + 12, 5197647);
        this.fontRendererObj.drawString(I18n.format("demo.help.jump", GameSettings.getKeyDisplayString(var6.keyBindJump.getKeyCode())), var4, var5 + 24, 5197647);
        this.fontRendererObj.drawString(I18n.format("demo.help.inventory", GameSettings.getKeyDisplayString(var6.keyBindInventory.getKeyCode())), var4, var5 + 36, 5197647);
        this.fontRendererObj.drawSplitString(I18n.format("demo.help.fullWrapped", new Object[0]), var4, var5 + 68, 218, 2039583);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        logger = LogManager.getLogger();
        field_146348_f = new ResourceLocation("textures/gui/demo_background.png");
    }
}
