package net.minecraft.src;

import java.nio.charset.*;
import java.io.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import java.awt.image.*;
import me.enrythebest.reborn.cracked.gui.screens.*;
import java.net.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import me.enrythebest.reborn.cracked.mods.*;
import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.util.*;
import me.enrythebest.reborn.cracked.*;
import java.util.*;

public class GuiMainMenu extends GuiScreen
{
    private static final Random rand;
    private float updateCounter;
    private String splashText;
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private int viewportTexture;
    private boolean field_96141_q;
    private static boolean field_96140_r;
    private static boolean field_96139_s;
    private final Object field_104025_t;
    private String field_92025_p;
    private String field_104024_v;
    private static final String[] titlePanoramaPaths;
    private static final String[] titlePanoramaPathsReborn;
    public static final String field_96138_a;
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    
    static {
        rand = new Random();
        GuiMainMenu.field_96140_r = false;
        GuiMainMenu.field_96139_s = false;
        titlePanoramaPaths = new String[] { "/title/bg/panorama0.png", "/title/bg/panorama1.png", "/title/bg/panorama2.png", "/title/bg/panorama3.png", "/title/bg/panorama4.png", "/title/bg/panorama5.png" };
        titlePanoramaPathsReborn = new String[] { "/panorama0.png", "/panorama1.png", "/panorama2.png", "/panorama3.png", "/panorama4.png", "/panorama5.png" };
        field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    }
    
    public GuiMainMenu() {
        this.updateCounter = 0.0f;
        this.splashText = "missingno";
        this.panoramaTimer = 0;
        this.field_96141_q = true;
        this.field_104025_t = new Object();
        this.drawOutlinedNormalRect(0, 28, 320, 500, Integer.MIN_VALUE, -1);
        BufferedReader var1 = null;
        try {
            final ArrayList var2 = new ArrayList();
            var1 = new BufferedReader(new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/title/splashes.txt"), Charset.forName("UTF-8")));
            String var3;
            while ((var3 = var1.readLine()) != null) {
                var3 = var3.trim();
                if (var3.length() > 0) {
                    var2.add(var3);
                }
            }
            Morbid.getManager();
            do {
                this.splashText = var2.get(GuiMainMenu.rand.nextInt(var2.size()));
            } while (this.splashText.hashCode() == 125780783);
        }
        catch (IOException ex) {}
        finally {
            if (var1 != null) {
                try {
                    var1.close();
                }
                catch (IOException ex2) {}
            }
        }
        if (var1 != null) {
            try {
                var1.close();
            }
            catch (IOException ex3) {}
        }
        this.updateCounter = GuiMainMenu.rand.nextFloat();
        this.field_92025_p = "";
        final String var4 = System.getProperty("os_architecture");
        String var3 = System.getProperty("java_version");
        Morbid.getManager();
        if (ModManager.getMod("vanilla").isEnabled()) {
            if ("ppc".equalsIgnoreCase(var4)) {
                this.field_92025_p = EnumChatFormatting.BOLD + "Notice!" + EnumChatFormatting.RESET + " PowerPC compatibility will be dropped in Minecraft 1.6";
                this.field_104024_v = "http://tinyurl.com/javappc";
            }
            else if (var3 != null && var3.startsWith("1.5")) {
                this.field_92025_p = EnumChatFormatting.BOLD + "Notice!" + EnumChatFormatting.RESET + " Java 1.5 compatibility will be dropped in Minecraft 1.6";
                this.field_104024_v = "http://tinyurl.com/javappc";
            }
            if (this.field_92025_p.length() == 0) {
                new Thread(new RunnableTitleScreen(this), "1.6 Update Check Thread").start();
            }
        }
    }
    
    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
    }
    
    @Override
    public void initGui() {
        this.viewportTexture = this.mc.renderEngine.allocateAndSetupTexture(new BufferedImage(256, 256, 2));
        final Calendar var1 = Calendar.getInstance();
        var1.setTime(new Date());
        if (var1.get(2) + 1 == 11 && var1.get(5) == 9) {
            this.splashText = "Happy birthday, ez!";
        }
        else if (var1.get(2) + 1 == 6 && var1.get(5) == 1) {
            this.splashText = "Happy birthday, Notch!";
        }
        else if (var1.get(2) + 1 == 12 && var1.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        }
        else if (var1.get(2) + 1 == 1 && var1.get(5) == 1) {
            this.splashText = "Happy new year!";
        }
        else if (var1.get(2) + 1 == 10 && var1.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }
        final StringTranslate var2 = StringTranslate.getInstance();
        final int var3 = this.height / 4 + 48;
        if (this.mc.isDemo()) {
            this.addDemoButtons(var3, 24, var2);
        }
        else {
            this.addSingleplayerMultiplayerButtons(var3, 24, var2);
        }
        this.func_96137_a(var2, var3, 24);
        if (this.mc.hideQuitButton) {
            this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72, var2.translateKey("menu.options")));
        }
        else {
            Morbid.getManager();
            if (ModManager.getMod("vanilla").isEnabled()) {
                this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, 98, 20, var2.translateKey("menu.options")));
                this.buttonList.add(new GuiButton(4, this.width / 2 + 2, var3 + 72 + 12, 98, 20, var2.translateKey("menu.quit")));
                this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, var3 + 72 + 12));
            }
            Morbid.getManager();
            if (!ModManager.getMod("vanilla").isEnabled()) {
                this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72, 98, 20, var2.translateKey("menu.options")));
                this.buttonList.add(new GuiButton(4, this.width / 2 + 2, var3 + 72, 98, 20, var2.translateKey("menu.quit")));
                this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, var3 + 72 + 12));
            }
        }
        final Object var4 = this.field_104025_t;
        final Object var5 = this.field_104025_t;
        synchronized (this.field_104025_t) {
            this.field_92023_s = this.fontRenderer.getStringWidth(this.field_92025_p);
            Morbid.getManager();
            if (ModManager.getMod("vanilla").isEnabled()) {
                this.field_92024_r = this.fontRenderer.getStringWidth(GuiMainMenu.field_96138_a);
            }
            final int var6 = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - var6) / 2;
            this.field_92021_u = this.buttonList.get(0).yPosition - 24;
            this.field_92020_v = this.field_92022_t + var6;
            this.field_92019_w = this.field_92021_u + 24;
        }
        // monitorexit(this.field_104025_t)
    }
    
    private void func_96137_a(final StringTranslate par1StringTranslate, final int par2, final int par3) {
        if (this.field_96141_q) {
            if (!GuiMainMenu.field_96140_r) {
                GuiMainMenu.field_96140_r = true;
                new ThreadTitleScreen(this, par1StringTranslate, par2, par3).start();
            }
            else if (GuiMainMenu.field_96139_s) {
                this.func_98060_b(par1StringTranslate, par2, par3);
            }
        }
    }
    
    private void func_98060_b(final StringTranslate par1StringTranslate, final int par2, final int par3) {
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, par2 + par3 * 2, par1StringTranslate.translateKey("menu.online")));
    }
    
    private void addSingleplayerMultiplayerButtons(final int par1, final int par2, final StringTranslate par3StringTranslate) {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, par1, par3StringTranslate.translateKey("SinglePlayer")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, par1 + par2 * 1, par3StringTranslate.translateKey("MultiPlayer")));
        Morbid.getManager();
        if (!ModManager.getMod("vanilla").isEnabled()) {
            this.buttonList.add(new GuiButton(101, this.width / 2 - 100, par1 + par2 * 4, par3StringTranslate.translateKey("Alt Manager")));
            this.buttonList.add(new GuiButton(100, this.width / 2 - 100, par1 + par2 * 2, par3StringTranslate.translateKey("Login")));
        }
    }
    
    private void addDemoButtons(final int par1, final int par2, final StringTranslate par3StringTranslate) {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, par1, par3StringTranslate.translateKey("menu.playdemo")));
        this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, par1 + par2 * 1, par3StringTranslate.translateKey("menu.resetdemo")));
        final ISaveFormat var4 = this.mc.getSaveLoader();
        final WorldInfo var5 = var4.getWorldInfo("Demo_World");
        if (var5 == null) {
            this.buttonResetDemo.enabled = false;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (par1GuiButton.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings));
        }
        if (par1GuiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (par1GuiButton.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (par1GuiButton.id == 3) {
            this.mc.displayGuiScreen(new GuiScreenOnlineServers(this));
        }
        if (par1GuiButton.id == 100) {
            this.mc.displayGuiScreen(new AltLogin(this));
        }
        if (par1GuiButton.id == 101) {
            this.mc.displayGuiScreen(new AltManager(this));
        }
        if (par1GuiButton.id == 4) {
            this.mc.shutdown();
        }
        if (par1GuiButton.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }
        if (par1GuiButton.id == 12) {
            final ISaveFormat var2 = this.mc.getSaveLoader();
            final WorldInfo var3 = var2.getWorldInfo("Demo_World");
            if (var3 != null) {
                final GuiYesNo var4 = GuiSelectWorld.getDeleteWorldScreen(this, var3.getWorldName(), 12);
                this.mc.displayGuiScreen(var4);
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean par1, final int par2) {
        if (par1 && par2 == 12) {
            final ISaveFormat var6 = this.mc.getSaveLoader();
            var6.flushCache();
            var6.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (par2 == 13) {
            if (par1) {
                try {
                    final Class var7 = Class.forName("java.awt.Desktop");
                    final Object var8 = var7.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
                    var7.getMethod("browse", URI.class).invoke(var8, new URI(this.field_104024_v));
                }
                catch (Throwable var9) {
                    var9.printStackTrace();
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }
    
    private void drawPanorama(final int par1, final int par2, final float par3) {
        final Tessellator var4 = Tessellator.instance;
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GLU.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        final byte var5 = 8;
        for (int var6 = 0; var6 < var5 * var5; ++var6) {
            GL11.glPushMatrix();
            final float var7 = (var6 % var5 / var5 - 0.5f) / 64.0f;
            final float var8 = (var6 / var5 / var5 - 0.5f) / 64.0f;
            final float var9 = 0.0f;
            GL11.glTranslatef(var7, var8, var9);
            GL11.glRotatef(MathHelper.sin((this.panoramaTimer + par3) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-(this.panoramaTimer + par3) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int var10 = 0; var10 < 6; ++var10) {
                GL11.glPushMatrix();
                if (var10 == 1) {
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 2) {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 3) {
                    GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 4) {
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var10 == 5) {
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                Morbid.getManager();
                if (ModManager.findMod(Vanilla.class).isEnabled()) {
                    this.mc.renderEngine.bindTexture(GuiMainMenu.titlePanoramaPaths[var10]);
                }
                else {
                    this.mc.renderEngine.bindTexture(GuiMainMenu.titlePanoramaPathsReborn[var10]);
                }
                var4.startDrawingQuads();
                var4.setColorRGBA_I(16777215, 255 / (var6 + 1));
                final float var11 = 0.0f;
                var4.addVertexWithUV(-1.0, -1.0, 1.0, 0.0f + var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, -1.0, 1.0, 1.0f - var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, 1.0, 1.0, 1.0f - var11, 1.0f - var11);
                var4.addVertexWithUV(-1.0, 1.0, 1.0, 0.0f + var11, 1.0f - var11);
                var4.draw();
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }
        var4.setTranslation(0.0, 0.0, 0.0);
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        GL11.glEnable(3008);
        GL11.glEnable(2929);
    }
    
    private void rotateAndBlurSkybox(final float par1) {
        GL11.glBindTexture(3553, this.viewportTexture);
        this.mc.renderEngine.resetBoundTexture();
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColorMask(true, true, true, false);
        final Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        final byte var3 = 3;
        for (int var4 = 0; var4 < var3; ++var4) {
            var2.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f / (var4 + 1));
            final int var5 = this.width;
            final int var6 = this.height;
            final float var7 = (var4 - var3 / 2) / 256.0f;
            var2.addVertexWithUV(var5, var6, this.zLevel, 0.0f + var7, 0.0);
            var2.addVertexWithUV(var5, 0.0, this.zLevel, 1.0f + var7, 0.0);
            var2.addVertexWithUV(0.0, 0.0, this.zLevel, 1.0f + var7, 1.0);
            var2.addVertexWithUV(0.0, var6, this.zLevel, 0.0f + var7, 1.0);
        }
        var2.draw();
        GL11.glColorMask(true, true, true, true);
        this.mc.renderEngine.resetBoundTexture();
    }
    
    private void renderSkybox(final int par1, final int par2, final float par3) {
        GL11.glViewport(0, 0, 256, 256);
        this.drawPanorama(par1, par2, par3);
        GL11.glDisable(3553);
        GL11.glEnable(3553);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        final Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        final float var5 = (this.width > this.height) ? (120.0f / this.width) : (120.0f / this.height);
        final float var6 = this.height * var5 / 256.0f;
        final float var7 = this.width * var5 / 256.0f;
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        final int var8 = this.width;
        final int var9 = this.height;
        var4.addVertexWithUV(0.0, var9, this.zLevel, 0.5f - var6, 0.5f + var7);
        var4.addVertexWithUV(var8, var9, this.zLevel, 0.5f - var6, 0.5f - var7);
        var4.addVertexWithUV(var8, 0.0, this.zLevel, 0.5f + var6, 0.5f - var7);
        var4.addVertexWithUV(0.0, 0.0, this.zLevel, 0.5f + var6, 0.5f + var7);
        var4.draw();
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.renderSkybox(par1, par2, par3);
        final Tessellator var4 = Tessellator.instance;
        final short var5 = 274;
        final int var6 = this.width / 2 - var5 / 2;
        final byte var7 = 30;
        this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        for (int var8 = 0; var8 < 6; ++var8) {
            Morbid.getManager();
            if (ModManager.getMod("vanilla").isEnabled()) {
                this.mc.renderEngine.bindTexture("titlePanoramaPaths");
            }
            else {
                this.renderBackground(this.width, this.height);
            }
        }
        Morbid.getManager();
        if (ModManager.getMod("vanilla").isEnabled()) {
            this.mc.renderEngine.bindTexture("/title/mclogo.png");
        }
        else {
            this.mc.renderEngine.bindTexture("/reborn/mclogo.png");
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.updateCounter < 1.0E-4) {
            this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 99, 44);
            this.drawTexturedModalRect(var6 + 99, var7 + 0, 129, 0, 27, 44);
            this.drawTexturedModalRect(var6 + 99 + 26, var7 + 0, 126, 0, 3, 44);
            this.drawTexturedModalRect(var6 + 99 + 26 + 3, var7 + 0, 99, 0, 26, 44);
            this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
        }
        else {
            Morbid.getManager();
            if (!ModManager.findMod(Vanilla.class).isEnabled()) {
                this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 175, 43);
                this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 43, 155, 43);
            }
            else {
                this.drawTexturedModalRect(var6 + 32, var7 + 0, 0, 0, 175, 43);
                this.drawTexturedModalRect(var6 + 191, var7 + 0, 0, 43, 155, 43);
            }
        }
        var4.setColorOpaque_I(16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2 + 90, 70.0f, 0.0f);
        GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        float var9 = 1.8f - MathHelper.abs(MathHelper.sin(Minecraft.getSystemTime() % 1000L / 1000.0f * 3.1415927f * 2.0f) * 0.1f);
        Morbid.getManager();
        var9 = var9 * 100.0f / (this.fontRenderer.getStringWidth(this.splashText) + 32);
        GL11.glScalef(var9, var9, var9);
        this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, 16776960);
        GL11.glPopMatrix();
        Morbid.getManager();
        if (!ModManager.getMod("vanilla").isEnabled()) {
            String var10 = MorbidHelper.getMinecraftVer();
            if (this.mc.isDemo()) {
                var10 = String.valueOf(var10) + " Demo";
            }
            this.drawString(this.fontRenderer, var10, 2, this.height - 20, 16777215);
            this.drawString(this.fontRenderer, "§4Reborn §fv0.6", 2, this.height - 30, 16729088);
            String var11 = "§fCoded by EnryTheBest_";
            this.drawString(this.fontRenderer, var11, 2, this.height - 10, 16729088);
            var11 = "§fCurrent Account: " + MorbidWrapper.getUsername();
            this.drawString(this.fontRenderer, var11, 2, 2, 16729088);
            final String var12 = "Copyright Mojang AB. Do not distribute!";
            this.drawString(this.fontRenderer, var12, this.width - this.fontRenderer.getStringWidth(var12) - 2, this.height - 10, 16777215);
        }
        else {
            String var10 = "Minecraft 1.5.2";
            if (this.mc.isDemo()) {
                var10 = String.valueOf(var10) + " Demo";
            }
            this.drawString(this.fontRenderer, var10, 2, this.height - 10, 16777215);
            final String var11 = "Copyright Mojang AB. Do not distribute!";
            this.drawString(this.fontRenderer, var11, this.width - this.fontRenderer.getStringWidth(var11) - 2, this.height - 10, 16777215);
        }
        if (this.field_92025_p != null && this.field_92025_p.length() > 0) {
            Gui.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRenderer, this.field_92025_p, this.field_92022_t, this.field_92021_u, 16777215);
            this.drawString(this.fontRenderer, GuiMainMenu.field_96138_a, (this.width - this.field_92024_r) / 2, this.buttonList.get(0).yPosition - 12, 16777215);
        }
        super.drawScreen(par1, par2, par3);
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        final Object var4 = this.field_104025_t;
        final Object var5 = this.field_104025_t;
        synchronized (this.field_104025_t) {
            if (this.field_92025_p.length() > 0 && par1 >= this.field_92022_t && par1 <= this.field_92020_v && par2 >= this.field_92021_u && par2 <= this.field_92019_w) {
                final GuiConfirmOpenLink var6 = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
                var6.func_92026_h();
                this.mc.displayGuiScreen(var6);
            }
        }
        // monitorexit(this.field_104025_t)
    }
    
    static Object func_104004_a(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.field_104025_t;
    }
    
    static String func_104005_a(final GuiMainMenu par0GuiMainMenu, final String par1Str) {
        return par0GuiMainMenu.field_92025_p = par1Str;
    }
    
    static String func_104013_b(final GuiMainMenu par0GuiMainMenu, final String par1Str) {
        return par0GuiMainMenu.field_104024_v = par1Str;
    }
    
    static int func_104006_a(final GuiMainMenu par0GuiMainMenu, final int par1) {
        return par0GuiMainMenu.field_92023_s = par1;
    }
    
    static String func_104023_b(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.field_92025_p;
    }
    
    static FontRenderer func_104022_c(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.fontRenderer;
    }
    
    static int func_104014_b(final GuiMainMenu par0GuiMainMenu, final int par1) {
        return par0GuiMainMenu.field_92024_r = par1;
    }
    
    static FontRenderer func_104007_d(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.fontRenderer;
    }
    
    static int func_104016_e(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.field_92023_s;
    }
    
    static int func_104015_f(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.field_92024_r;
    }
    
    static int func_104008_c(final GuiMainMenu par0GuiMainMenu, final int par1) {
        return par0GuiMainMenu.field_92022_t = par1;
    }
    
    static int func_104009_d(final GuiMainMenu par0GuiMainMenu, final int par1) {
        return par0GuiMainMenu.field_92021_u = par1;
    }
    
    static List func_104019_g(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.buttonList;
    }
    
    static int func_104011_e(final GuiMainMenu par0GuiMainMenu, final int par1) {
        return par0GuiMainMenu.field_92020_v = par1;
    }
    
    static int func_104018_h(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.field_92022_t;
    }
    
    static int func_104012_f(final GuiMainMenu par0GuiMainMenu, final int par1) {
        return par0GuiMainMenu.field_92019_w = par1;
    }
    
    static int func_104020_i(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.field_92021_u;
    }
    
    static Minecraft func_98058_a(final GuiMainMenu par0GuiMainMenu) {
        return par0GuiMainMenu.mc;
    }
    
    static void func_98061_a(final GuiMainMenu par0GuiMainMenu, final StringTranslate par1StringTranslate, final int par2, final int par3) {
        par0GuiMainMenu.func_98060_b(par1StringTranslate, par2, par3);
    }
    
    static boolean func_98059_a(final boolean par0) {
        return GuiMainMenu.field_96139_s = par0;
    }
    
    private void renderBackground(final int par1, final int par2) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3008);
        this.mc.renderEngine.bindTexture("/reborn/background.png");
        final Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0, par2, -90.0, 0.0, 1.0);
        var3.addVertexWithUV(par1, par2, -90.0, 1.0, 1.0);
        var3.addVertexWithUV(par1, 0.0, -90.0, 1.0, 0.0);
        var3.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void drawOutlinedNormalRect(final int i, final int j, final int k, final int l, final int m, final int n) {
    }
}
