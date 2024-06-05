package net.minecraft.src;

import net.minecraft.client.*;
import java.util.*;
import java.awt.*;
import java.awt.datatransfer.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class GuiScreen extends Gui
{
    public static final boolean isMacOs;
    protected Minecraft mc;
    public int width;
    public int height;
    protected List buttonList;
    public boolean allowUserInput;
    protected FontRenderer fontRenderer;
    public GuiParticle guiParticles;
    private GuiButton selectedButton;
    private int eventButton;
    private long field_85043_c;
    private int field_92018_d;
    
    static {
        isMacOs = (Minecraft.getOs() == EnumOS.MACOS);
    }
    
    public GuiScreen() {
        this.buttonList = new ArrayList();
        this.allowUserInput = false;
        this.selectedButton = null;
        this.eventButton = 0;
        this.field_85043_c = 0L;
        this.field_92018_d = 0;
    }
    
    public void drawScreen(final int par1, final int par2, final float par3) {
        for (int var4 = 0; var4 < this.buttonList.size(); ++var4) {
            final GuiButton var5 = this.buttonList.get(var4);
            var5.drawButton(this.mc, par1, par2);
        }
    }
    
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 1) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }
    
    public static String getClipboardString() {
        try {
            final Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)var0.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (Exception ex) {}
        return "";
    }
    
    public static void setClipboardString(final String par0Str) {
        try {
            final StringSelection var1 = new StringSelection(par0Str);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, null);
        }
        catch (Exception ex) {}
    }
    
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        if (par3 == 0) {
            for (int var4 = 0; var4 < this.buttonList.size(); ++var4) {
                final GuiButton var5 = this.buttonList.get(var4);
                if (var5.mousePressed(this.mc, par1, par2)) {
                    this.selectedButton = var5;
                    this.mc.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
                    this.actionPerformed(var5);
                }
            }
        }
    }
    
    protected void mouseMovedOrUp(final int par1, final int par2, final int par3) {
        if (this.selectedButton != null && par3 == 0) {
            this.selectedButton.mouseReleased(par1, par2);
            this.selectedButton = null;
        }
    }
    
    protected void func_85041_a(final int par1, final int par2, final int par3, final long par4) {
    }
    
    protected void actionPerformed(final GuiButton par1GuiButton) {
    }
    
    public void setWorldAndResolution(final Minecraft par1Minecraft, final int par2, final int par3) {
        this.guiParticles = new GuiParticle(par1Minecraft);
        this.mc = par1Minecraft;
        this.fontRenderer = par1Minecraft.fontRenderer;
        this.width = par2;
        this.height = par3;
        this.buttonList.clear();
        this.initGui();
    }
    
    public void initGui() {
    }
    
    public void handleInput() {
        while (Mouse.next()) {
            this.handleMouseInput();
        }
        while (Keyboard.next()) {
            this.handleKeyboardInput();
        }
    }
    
    public void handleMouseInput() {
        final int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
        final int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen && this.field_92018_d++ > 0) {
                return;
            }
            this.eventButton = Mouse.getEventButton();
            this.field_85043_c = Minecraft.getSystemTime();
            this.mouseClicked(var1, var2, this.eventButton);
        }
        else if (Mouse.getEventButton() != -1) {
            if (this.mc.gameSettings.touchscreen && --this.field_92018_d > 0) {
                return;
            }
            this.eventButton = -1;
            this.mouseMovedOrUp(var1, var2, Mouse.getEventButton());
        }
        else if (this.eventButton != -1 && this.field_85043_c > 0L) {
            final long var3 = Minecraft.getSystemTime() - this.field_85043_c;
            this.func_85041_a(var1, var2, this.eventButton, var3);
        }
    }
    
    public void handleKeyboardInput() {
        if (Keyboard.getEventKeyState()) {
            int var1 = Keyboard.getEventKey();
            final char var2 = Keyboard.getEventCharacter();
            if (var1 == 87) {
                this.mc.toggleFullscreen();
                return;
            }
            if (GuiScreen.isMacOs && var1 == 28 && var2 == '\0') {
                var1 = 29;
            }
            this.keyTyped(var2, var1);
        }
    }
    
    public void updateScreen() {
    }
    
    public void onGuiClosed() {
    }
    
    public void drawDefaultBackground() {
        this.drawWorldBackground(0);
    }
    
    public void drawWorldBackground(final int par1) {
        if (Minecraft.theWorld != null) {
            this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
        }
        else {
            this.drawBackground(par1);
        }
    }
    
    public void drawBackground(final int par1) {
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        final Tessellator var2 = Tessellator.instance;
        this.mc.renderEngine.bindTexture("/gui/background.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var3 = 32.0f;
        var2.startDrawingQuads();
        var2.setColorOpaque_I(4210752);
        var2.addVertexWithUV(0.0, this.height, 0.0, 0.0, this.height / var3 + par1);
        var2.addVertexWithUV(this.width, this.height, 0.0, this.width / var3, this.height / var3 + par1);
        var2.addVertexWithUV(this.width, 0.0, 0.0, this.width / var3, par1);
        var2.addVertexWithUV(0.0, 0.0, 0.0, 0.0, par1);
        var2.draw();
    }
    
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    public void confirmClicked(final boolean par1, final int par2) {
    }
    
    public static boolean isCtrlKeyDown() {
        final boolean var0 = Keyboard.isKeyDown(28) && Keyboard.getEventCharacter() == '\0';
        return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157) || (GuiScreen.isMacOs && (var0 || Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220)));
    }
    
    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
}
