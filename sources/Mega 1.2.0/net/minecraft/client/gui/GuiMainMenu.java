package net.minecraft.client.gui;

import club.mega.Mega;
import club.mega.util.AnimationUtil;
import club.mega.util.ColorUtil;
import club.mega.util.RenderUtil;
import club.mega.util.RoundedButton;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.CustomPanorama;
import net.optifine.CustomPanoramaProperties;
import net.optifine.reflect.Reflector;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private final Object threadLock = new Object();
    private String openGLWarning1;
    private String openGLWarning2;
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private boolean field_183502_L;
    private GuiScreen field_183503_M;
    private double current;

    public GuiMainMenu()
    {
        this.openGLWarning2 = field_96138_a;
        this.field_183502_L = false;
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
        {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    private boolean func_183501_a()
    {
        return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.field_183503_M != null;
    }

    public void updateScreen()
    {
        if (this.func_183501_a())
        {
            this.field_183503_M.updateScreen();
        }
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public void initGui()
    {
        int j = this.height / 4 + 48;

        this.addSingleplayerMultiplayerButtons(j, 24);
        this.buttonList.add(new RoundedButton(0, this.width / 2 - 100, j + 72, 98, 20, ColorUtil.getMainColor(),ColorUtil.getMainColor(200), 3, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new RoundedButton(4, this.width / 2 + 2, j + 72, 98, 20, ColorUtil.getMainColor(),ColorUtil.getMainColor(200), 3, I18n.format("menu.quit", new Object[0])));

        synchronized (this.threadLock)
        {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = (int) ((this.buttonList.get(0)).yPosition - 24);
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }

        this.mc.setConnectedToRealms(false);

        if (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.field_183502_L)
        {
            RealmsBridge realmsbridge = new RealmsBridge();
            this.field_183503_M = realmsbridge.getNotificationScreen(this);
            this.field_183502_L = true;
        }

        if (this.func_183501_a())
        {
            this.field_183503_M.setGuiSize(this.width, this.height);
            this.field_183503_M.initGui();
        }
        current = 0.5;
    }

    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
    {
        this.buttonList.add(new RoundedButton(1, this.width / 2 - 100, p_73969_1_, ColorUtil.getMainColor(),ColorUtil.getMainColor(200),3, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new RoundedButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, ColorUtil.getMainColor(),ColorUtil.getMainColor(200), 3, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new RoundedButton(14, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, ColorUtil.getMainColor(),ColorUtil.getMainColor(200), 3,"AltManager"));
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 5)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 14)
        {
            this.mc.displayGuiScreen(Mega.INSTANCE.getAltManager());
        }

        if (button.id == 4)
        {
            this.mc.shutdown();
        }

        if (button.id == 6 && Reflector.GuiModList_Constructor.exists())
        {
            this.mc.displayGuiScreen((GuiScreen)Reflector.newInstance(Reflector.GuiModList_Constructor, new Object[] {this}));
        }

        if (button.id == 11)
        {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (button.id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null)
            {
                GuiYesNo guiyesno = GuiSelectWorld.makeDeleteWorldYesNo(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }

    public void confirmClicked(boolean result, int id)
    {
        if (result && id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (id == 13)
        {
            if (result)
            {
                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(this.openGLWarningLink)});
                }
                catch (Throwable throwable)
                {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        int y = this.height / 4 + 38;
        RenderUtil.drawRect(0, 0, width, height, new Color(40,40,40));
        current = AnimationUtil.animate(current, 1, 0.02);
        GL11.glPushMatrix();
        GL11.glScaled(current, current, 1);
        RenderUtil.drawRoundedRect(width / 2D - 110, y, 220, 115, 5, new Color(1, 1, 1, 140));
        super.drawScreen(mouseX, mouseY, partialTicks);

        Mega.INSTANCE.getFontManager().getFont("Roboto bold 80").drawCenteredString(Mega.INSTANCE.getName(), width / 2D, height / 4.5, ColorUtil.getMainColor());
        Mega.INSTANCE.getFontManager().getFont("Arial 22").drawStringWithShadow("v" + Mega.INSTANCE.getVersion(), (float) width / 2 + 40, (float) height / 4.5F, new Color(170,170,170).getRGB());

        Mega.INSTANCE.getFontManager().getFont("Roboto bold 18").drawString(Mega.INSTANCE.getName(), 2, height - 10, ColorUtil.getMainColor());
        Mega.INSTANCE.getFontManager().getFont("Arial 10").drawString("v" + Mega.INSTANCE.getVersion(), Mega.INSTANCE.getFontManager().getFont("Roboto bold 18").getWidth(Mega.INSTANCE.getName()) - 4, height - 14, new Color(255,255,255, 180).getRGB());
        Mega.INSTANCE.getFontManager().getFont("Arial 18").drawString("by " + Mega.INSTANCE.getDev(), 2 + Mega.INSTANCE.getFontManager().getFont("Roboto bold 18").getWidth(Mega.INSTANCE.getName()) + 8, height - 10, new Color(255,255,255, 180).getRGB());
        Mega.INSTANCE.getChangelog().render(mouseX, mouseY,13);
        GL11.glPopMatrix();

    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        Mega.INSTANCE.getChangelog().mouseClicked(mouseX, mouseY, mouseButton);
        synchronized (this.threadLock)
        {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w)
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }

        if (this.func_183501_a())
        {
            this.field_183503_M.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        Mega.INSTANCE.getChangelog().mouseReleased(state);
    }

    public void onGuiClosed()
    {
        if (this.field_183503_M != null)
        {
            this.field_183503_M.onGuiClosed();
        }
    }
}
