package me.satisfactory.base.gui;

import org.lwjgl.opengl.*;
import me.satisfactory.base.*;
import net.minecraft.client.renderer.*;
import java.text.*;
import java.awt.*;
import java.util.*;
import net.minecraft.client.gui.*;

public class NewMainMenu extends GuiScreen implements GuiYesNoCallback
{
    public static int animation;
    FontRenderer fontRenderer;
    FontRenderer fontRenderer2;
    FontRenderer fontRenderer3;
    FontRenderer fontRenderer4;
    private int k;
    private int anim1;
    private int anim2;
    private int anim3;
    private int anim4;
    private int anim5;
    
    public NewMainMenu() {
        this.fontRenderer = new UnicodeFontRenderer(new Font("Arial", 0, 55));
        this.fontRenderer2 = new UnicodeFontRenderer(new Font("Arial", 0, 95));
        this.fontRenderer3 = new UnicodeFontRenderer(new Font("Arial", 0, 18));
        this.fontRenderer4 = new UnicodeFontRenderer(new Font("Arial", 0, 30));
        this.anim1 = 0;
        this.anim2 = 0;
        this.anim3 = 0;
        this.anim4 = 0;
        this.anim5 = 0;
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GL11.glPushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        NewMainMenu.mc.getTextureManager().bindTexture(Base.INSTANCE.getMainMenu());
        this.drawTexturedModalRect(0, 0, 0, 0, NewMainMenu.width, NewMainMenu.height);
        final ScaledResolution sr = new ScaledResolution(NewMainMenu.mc, NewMainMenu.mc.displayWidth, NewMainMenu.mc.displayHeight);
        Gui.drawScaledCustomSizeModalRect(0.0, 0.0, 0.0f, 0.0f, NewMainMenu.width, NewMainMenu.height, NewMainMenu.width, NewMainMenu.height, (float)NewMainMenu.width, (float)NewMainMenu.height);
        final int var3 = NewMainMenu.height / 4 + 48;
        RenderHelper.drawBorderedRect((float)NewMainMenu.width, (float)(NewMainMenu.height / 6), (float)(-NewMainMenu.width), var3 - 999 + 144 - 4 - 0.1f, 1.5f, -1728053248, Integer.MIN_VALUE);
        this.drawSession(mouseX, mouseY);
        final SimpleDateFormat dff = new SimpleDateFormat("HH:mm:ss");
        final Date todayy = Calendar.getInstance().getTime();
        final String renderTime = dff.format(todayy);
        RenderHelper.drawBorderedRect((float)(NewMainMenu.width / 2 + 177), (float)NewMainMenu.height, (float)(NewMainMenu.width / 2 - 165), (float)(NewMainMenu.height - 60), 7.0f, Color.black.getRGB(), 0);
        RenderHelper.drawBorderedRect((float)(NewMainMenu.width / 2 - 100), (float)(NewMainMenu.height / 2 - 50 - 10), (float)(NewMainMenu.width / 2 + 100), (float)(NewMainMenu.height / 2 + 50 + 0), 1.5f, -1728053248, Integer.MIN_VALUE);
        this.fontRenderer4.drawString("Bypass Status:", 10, NewMainMenu.height / 17, -1);
        this.fontRenderer3.drawString("Remix client, Made by Aidan and Mees", NewMainMenu.width / 2 - 76, NewMainMenu.height / 2 + 35, -1);
        this.fontRenderer.drawString(renderTime, NewMainMenu.width / 2 - this.fontRenderer.getStringWidth(renderTime) / 2, NewMainMenu.height / 2 + 6 - 10, -1);
        this.fontRenderer2.drawString("Remix", NewMainMenu.width / 2 - 30 - this.fontRenderer.getStringWidth("Remix") / 2, NewMainMenu.height / 2 - 45 - 10, -1);
        RenderHelper.drawHLine((float)(NewMainMenu.width / 2 - 60), (float)(NewMainMenu.width / 2 + 60), NewMainMenu.height / 2 - 5.5f, -1);
        RenderHelper.drawHLine((float)NewMainMenu.width, (float)(-NewMainMenu.width), (float)(NewMainMenu.height / 6), Color.BLACK.getRGB());
        RenderHelper.drawHLine((float)(NewMainMenu.width / 2 + 176), (float)(NewMainMenu.width / 2 - 167), NewMainMenu.height - 61.8f, Color.BLACK.getRGB());
        if ((NewMainMenu.animation += 11) > 720) {
            NewMainMenu.animation = 0;
        }
        this.animateAnimations(mouseX, mouseY);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.drawIcon((float)(NewMainMenu.width / 2 - 140), (float)(NewMainMenu.height - 47 - this.anim1), 40, 40, Base.INSTANCE.getSinglePlayer());
        RenderHelper.drawIcon((float)(NewMainMenu.width / 2 - 80), (float)(NewMainMenu.height - 47 - this.anim2), 40, 40, Base.INSTANCE.getMultiPlayer());
        RenderHelper.drawIcon((float)(NewMainMenu.width / 2 - 20), (float)(NewMainMenu.height - 47 - this.anim3), 40, 40, Base.INSTANCE.getAlt());
        RenderHelper.drawIcon((float)(NewMainMenu.width / 2 + 40), (float)(NewMainMenu.height - 47 - this.anim4), 40, 40, Base.INSTANCE.getSettings());
        RenderHelper.drawIcon((float)(NewMainMenu.width / 2 + 105), (float)(NewMainMenu.height - 47 - this.anim5), 40, 40, Base.INSTANCE.getExit());
        GlStateManager.enableLighting();
        GlStateManager.enableBlend();
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private void drawSession(final int mouseX, final int mouseY) {
        this.fontRenderer3.drawString("Watchdog:", 120, NewMainMenu.height / 14, -1);
        this.fontRenderer3.drawString("Gwen:", 195, NewMainMenu.height / 14, -1);
        this.fontRenderer3.drawString("NCP:", 255, NewMainMenu.height / 14, -1);
        this.fontRenderer3.drawString("Guardian:", 315, NewMainMenu.height / 14, -1);
        this.fontRenderer3.drawString("AAC:", 395, NewMainMenu.height / 14, -1);
        RenderHelper.drawFullCircle(170, NewMainMenu.height / 14 + 5.5, 2.0, -NewMainMenu.animation / 2, 99.0f, 360, -13710223);
        RenderHelper.drawFullCircle(230, NewMainMenu.height / 14 + 5.5, 2.0, -NewMainMenu.animation / 3, 10.0f, 360, -13710223);
        RenderHelper.drawFullCircle(287, NewMainMenu.height / 14 + 5.5, 2.0, NewMainMenu.animation / 2, 10.0f, 360, -13710223);
        RenderHelper.drawFullCircle(365, NewMainMenu.height / 14 + 5.5, 2.0, NewMainMenu.animation / 3, 10.0f, 360, -13710223);
        RenderHelper.drawFullCircle(428, NewMainMenu.height / 14 + 5.5, 2.0, NewMainMenu.animation / 3, 10.0f, 360, -13710223);
    }
    
    public void animateAnimations(final int mouseX, final int mouseY) {
        final boolean isOverExit = mouseX > NewMainMenu.width / 2 + 105 && mouseX < NewMainMenu.width / 2 + 145 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        final boolean isOverSingleplayer = mouseX > NewMainMenu.width / 2 - 140 && mouseX < NewMainMenu.width / 2 - 100 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        final boolean isOverMultiplayer = mouseX > NewMainMenu.width / 2 - 80 && mouseX < NewMainMenu.width / 2 - 40 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        final boolean isOverAltManager = mouseX > NewMainMenu.width / 2 - 20 && mouseX < NewMainMenu.width / 2 + 20 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        final boolean isOverSettings = mouseX > NewMainMenu.width / 2 + 40 && mouseX < NewMainMenu.width / 2 + 80 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        if (isOverSingleplayer) {
            if (this.anim1 < 3) {
                ++this.anim1;
            }
        }
        else if (this.anim1 > 0) {
            --this.anim1;
        }
        if (isOverMultiplayer) {
            if (this.anim2 < 3) {
                ++this.anim2;
            }
        }
        else if (this.anim2 > 0) {
            --this.anim2;
        }
        if (isOverAltManager) {
            if (this.anim3 < 3) {
                ++this.anim3;
            }
        }
        else if (this.anim3 > 0) {
            --this.anim3;
        }
        if (isOverSettings) {
            if (this.anim4 < 3) {
                ++this.anim4;
            }
        }
        else if (this.anim4 > 0) {
            --this.anim4;
        }
        if (isOverExit) {
            if (this.anim5 < 3) {
                ++this.anim5;
            }
        }
        else if (this.anim5 > 0) {
            --this.anim5;
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean isOverExit = mouseX > NewMainMenu.width / 2 + 105 && mouseX < NewMainMenu.width / 2 + 145 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        final boolean isOverSingleplayer = mouseX > NewMainMenu.width / 2 - 140 && mouseX < NewMainMenu.width / 2 - 100 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        final boolean isOverMultiplayer = mouseX > NewMainMenu.width / 2 - 80 && mouseX < NewMainMenu.width / 2 - 40 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        final boolean isOverAltManager = mouseX > NewMainMenu.width / 2 - 20 && mouseX < NewMainMenu.width / 2 + 20 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        final boolean isOverSettings = mouseX > NewMainMenu.width / 2 + 40 && mouseX < NewMainMenu.width / 2 + 80 && mouseY > NewMainMenu.height - 47 && mouseY < NewMainMenu.height - 7;
        if (mouseButton == 0 && isOverSingleplayer) {
            NewMainMenu.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (mouseButton == 0 && isOverMultiplayer) {
            NewMainMenu.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (mouseButton == 0 && isOverAltManager) {
            NewMainMenu.mc.displayGuiScreen(new GuiMultiplayer(this));
            NewMainMenu.mc.displayGuiScreen(new GuiAltLogin(this));
        }
        if (mouseButton == 0 && isOverSettings) {
            NewMainMenu.mc.displayGuiScreen(new GuiOptions(this, NewMainMenu.mc.gameSettings));
        }
        if (mouseButton == 0 && isOverExit) {
            NewMainMenu.mc.shutdown();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    static {
        NewMainMenu.animation = 0;
    }
}
