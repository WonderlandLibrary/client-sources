package tk.rektsky.Guis;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class RektMainMenu extends GuiScreen
{
    private static final FontRenderer font;
    private static final Minecraft mc;
    private static final ResourceLocation ICON;
    private static final ResourceLocation BACKGROUND;
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.pushMatrix();
        this.drawDefaultBackground();
        System.out.println("FUCK");
        this.fontRendererObj.drawString("Cyka blyat", 0, 0, 0);
        RektMainMenu.mc.getTextureManager().bindTexture(new ResourceLocation("textures\\gui\\presets\\luck.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, 100, 100, 100.0f, 100.0f);
        GlStateManager.popMatrix();
    }
    
    @Override
    public void initGui() {
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
    }
    
    static {
        font = Minecraft.getMinecraft().fontRendererObj;
        mc = Minecraft.getMinecraft();
        ICON = new ResourceLocation("rektsky/icons/icon.png");
        BACKGROUND = new ResourceLocation("rektsky/images/background.png");
    }
}
