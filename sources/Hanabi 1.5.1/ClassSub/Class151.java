package ClassSub;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import cn.Hanabi.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.hmlProject.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import java.util.*;
import cn.Hanabi.utils.fontmanager.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.io.*;

public class Class151 extends GuiScreen
{
    public ArrayList<Class123> slots;
    public ScaledResolution sr;
    public Class205 timer;
    public boolean coolDown;
    
    
    public Class151() {
        this.slots = new ArrayList<Class123>();
        this.timer = new Class205();
        this.coolDown = true;
    }
    
    public void initGui() {
        this.sr = new ScaledResolution(Minecraft.getMinecraft());
        this.coolDown = true;
        this.timer.reset();
        this.slots.clear();
        this.slots.add(new Class123("单人游戏", HanabiFonts.ICON_MAINMENU_SINGLE, (GuiScreen)new GuiSelectWorld((GuiScreen)this)));
        this.slots.add(new Class123("多人游戏", HanabiFonts.ICON_MAINMENU_MULTI, (GuiScreen)new GuiMultiplayer((GuiScreen)this)));
        this.slots.add(new Class123("黑卡管理", HanabiFonts.ICON_MAINMENU_ALTMANAGER, new Class7()));
        this.slots.add(new Class123("游戏设置", HanabiFonts.ICON_MAINMENU_SETTINGS, (GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings)));
        this.slots.add(new Class123("�?出游�?", HanabiFonts.ICON_MAINMENU_QUITGAME, null));
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        this.sr = new ScaledResolution(Minecraft.getMinecraft());
        if (this.coolDown && this.timer.isDelayComplete(500L)) {
            this.coolDown = false;
            this.timer.reset();
        }
        Class246.drawImage(new ResourceLocation("Client/mainmenu/mainmenu.png"), 0, 0, this.sr.getScaledWidth(), this.sr.getScaledHeight());
        int n4 = this.sr.getScaledWidth() / 2 - 150;
        final int n5 = this.sr.getScaledHeight() / 2;
        for (final Class123 class123 : this.slots) {
            class123.draw(n, n2, n4, n5);
            if (!this.coolDown && !Hanabi.INSTANCE.loadFont) {
                class123.onClick(n, n2, n4, n5);
            }
            n4 += 70;
        }
        final String string = "欢迎回来, " + Mod.fuck;
        Hanabi.INSTANCE.fontManager.wqy18.drawString(string, this.sr.getScaledWidth() - 55 - Hanabi.INSTANCE.fontManager.wqy18.getStringWidth(string), 20.0f, -1);
        final int size = HMLManager.hooks.size();
        Hanabi.INSTANCE.fontManager.wqy18.drawString("已加�?" + size + "个HML Module.", 3, 2, Color.GREEN.getRGB());
        if (size > 0) {
            Hanabi.INSTANCE.fontManager.wqy18.drawString("警告！HML Project尚不完善，可能导致不可预料的错误�?", 3, 13, Color.RED.getRGB());
        }
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.0f);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(new ResourceLocation("Client/mainmenu/avatar.png"));
        this.drawScaledTexturedModalRect(this.sr.getScaledWidth() - 46, 9.0, 0.0, 0.0, 32.0, 32.0, 8.1f);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        Class246.drawArc(this.sr.getScaledWidth() - 30, 25.0f, 16.0, Class15.WHITE.c, 0, 360.0, 2);
        GL11.glPopMatrix();
        Hanabi.INSTANCE.fontManager.comfortaa17.drawString("HML Project API - Build 0", 2.0f, this.sr.getScaledHeight() - 20, -1);
        if (Hanabi.INSTANCE.loadFont) {
            final UnicodeFontRenderer wqy18 = Hanabi.INSTANCE.fontManager.wqy18;
            final StringBuilder append = new StringBuilder().append("正在加载剩余字体,请稍�?... (");
            final FontManager fontManager = Hanabi.INSTANCE.fontManager;
            wqy18.drawString(append.append(FontManager.fontName).append(")").toString(), 2.0f, this.sr.getScaledHeight() - 10, -1);
        }
        else {
            Hanabi.INSTANCE.fontManager.comfortaa17.drawString("Hanabi build 1.5.1 - by Margele", 2.0f, this.sr.getScaledHeight() - 10, -1);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void drawScaledTexturedModalRect(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final float n7) {
        final float n8 = 0.00390625f * n7;
        final float n9 = 0.00390625f * n7;
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        getWorldRenderer.pos(n + 0.0, n2 + n6, (double)this.zLevel).tex((double)((float)(n3 + 0.0) * n8), (double)((float)(n4 + n6) * n9)).endVertex();
        getWorldRenderer.pos(n + n5, n2 + n6, (double)this.zLevel).tex((double)((float)(n3 + n5) * n8), (double)((float)(n4 + n6) * n9)).endVertex();
        getWorldRenderer.pos(n + n5, n2 + 0.0, (double)this.zLevel).tex((double)((float)(n3 + n5) * n8), (double)((float)(n4 + 0.0) * n9)).endVertex();
        getWorldRenderer.pos(n + 0.0, n2 + 0.0, (double)this.zLevel).tex((double)((float)(n3 + 0.0) * n8), (double)((float)(n4 + 0.0) * n9)).endVertex();
        getInstance.draw();
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    public void updateScreen() {
        super.updateScreen();
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}
