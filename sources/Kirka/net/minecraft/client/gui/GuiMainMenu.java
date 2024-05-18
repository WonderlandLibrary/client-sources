/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.gui;

import java.awt.Font;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import me.thekirkayt.client.gui.account.AccountScreen;
import me.thekirkayt.utils.RenderUtils;
import me.thekirkayt.utils.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class GuiMainMenu
extends GuiScreen
implements GuiYesNoCallback {
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random field_175374_h = new Random();
    private int x;
    private float updateCounter;
    private String splashText;
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private DynamicTexture viewportTexture;
    private boolean field_175375_v = true;
    private final Object field_104025_t = new Object();
    private String field_92025_p;
    private String field_146972_A = field_96138_a;
    private String field_104024_v;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    public static final String field_96138_a = "Please click " + (Object)((Object)EnumChatFormatting.UNDERLINE) + "here" + (Object)((Object)EnumChatFormatting.RESET) + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation field_110351_G;
    private GuiButton field_175372_K;
    private static final String __OBFID = "CL_00001154";
    private final UnicodeFontRenderer hugeText;
    private final UnicodeFontRenderer guiText;

    public GuiMainMenu() {
        Object var1 = null;
        this.updateCounter = field_175374_h.nextFloat();
        this.field_92025_p = "";
        this.hugeText = new UnicodeFontRenderer(new Font("Card Shark", 0, 180));
        this.guiText = new UnicodeFontRenderer(new Font("Verdana", 0, 22));
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.field_92025_p = I18n.format("title.oldgl1", new Object[0]);
            this.field_146972_A = I18n.format("title.oldgl2", new Object[0]);
            this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
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
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar var1 = Calendar.getInstance();
        var1.setTime(new Date());
        boolean var2 = true;
        int var3 = height / 4 + 48;
        if (this.mc.isDemo()) {
            this.addDemoButtons(var3, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(var3, 24);
        }
        this.buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, width / 2 + 2, var3 + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new GuiButtonLanguage(5, width / 2 - 124, var3 + 72 + 12));
        Object var4 = this.field_104025_t;
        Object object = this.field_104025_t;
        synchronized (object) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
            int var5 = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (width - var5) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get((int)0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + var5;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }

    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
        this.buttonList.add(new GuiButton(1, width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new GuiButton(69, width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, "Account Manager"));
    }

    private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
        this.buttonList.add(new GuiButton(11, width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonResetDemo = new GuiButton(12, width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0]));
        this.buttonList.add(this.buttonResetDemo);
        ISaveFormat var3 = this.mc.getSaveLoader();
        WorldInfo var4 = var3.getWorldInfo("Demo_World");
        if (var4 == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        WorldInfo var3;
        ISaveFormat var2;
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (button.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 14 && this.field_175372_K.visible) {
            this.switchToRealms();
        }
        if (button.id == 4) {
            this.mc.shutdown();
        }
        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }
        if (button.id == 12 && (var3 = (var2 = this.mc.getSaveLoader()).getWorldInfo("Demo_World")) != null) {
            GuiYesNo var4 = GuiSelectWorld.func_152129_a(this, var3.getWorldName(), 12);
            this.mc.displayGuiScreen(var4);
        }
        if (button.id == 69) {
            this.mc.displayGuiScreen(new AccountScreen());
        }
    }

    private void switchToRealms() {
        RealmsBridge var1 = new RealmsBridge();
        var1.switchToRealms(this);
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if (result && id == 12) {
            ISaveFormat var6 = this.mc.getSaveLoader();
            var6.flushCache();
            var6.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id == 13) {
            if (result) {
                try {
                    Class<?> var3 = Class.forName("java.awt.Desktop");
                    Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    var3.getMethod("browse", URI.class).invoke(var4, new URI(this.field_104024_v));
                }
                catch (Throwable var5) {
                    logger.error("Couldn't open link", var5);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableAlpha();
        GlStateManager.enableAlpha();
        Tessellator var4 = Tessellator.getInstance();
        WorldRenderer var5 = var4.getWorldRenderer();
        int var6 = 274;
        int var7 = width / 2 - var6 / 2;
        int var8 = 30;
        ScaledResolution scaledRes = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glPopMatrix();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/kirka b11.png"));
        Gui.drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
        var5.func_178991_c(-1);
        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2 + 90, 70.0f, 0.0f);
        float var9 = 1.8f - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0f * 3.1415927f * 2.0f) * 0.1f);
        GlStateManager.scale(var9, var9, var9);
        GlStateManager.popMatrix();
        RenderUtils.getScaledRes();
        RenderUtils.getScaledRes();
        RenderUtils.getScaledRes();
        RenderUtils.drawRect(0.0f, ScaledResolution.getScaledHeight() - 25, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), -1610612736);
        String var10 = "\u00a79\u00a7lK\u00a7f\u00a7lirka \u00a79\u00a7lb11";
        String s = "S";
        String onus = "onus";
        if (this.mc.isDemo()) {
            var10 = String.valueOf(var10) + " Demo";
        }
        RenderUtils.getScaledRes();
        int h = ScaledResolution.getScaledHeight();
        RenderUtils.getScaledRes();
        int w = ScaledResolution.getScaledWidth();
        GuiMainMenu.drawString(this.fontRendererObj, var10, width - this.fontRendererObj.getStringWidth(var10) - 2, height - 20, -1);
        String var11 = "\u00a79\u00a7lM\u00a7\u0430\u00a7lade \u00a79\u00a7lb\u00a7\u0430\u00a7ly \u00a79\u00a7lT\u00a7f\u00a7lhe\u00a79\u00a7lK\u00a7f\u00a7lirka\u00a79\u00a7lY\u00a7f\u00a7lT ";
        GuiMainMenu.drawString(this.fontRendererObj, var11, width - this.fontRendererObj.getStringWidth(var11) - 2, height - 10, -1);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        GlStateManager.popMatrix();
        this.hugeText.drawStringWithShadow("", w / 2 - this.hugeText.getStringWidth("Kirka b11") / 4, 80, -1);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.2f, 1.2f, 1.2f);
        GlStateManager.popMatrix();
        this.guiText.drawStringWithShadow("", 2, h - 14, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Object var4 = this.field_104025_t;
        Object object = this.field_104025_t;
        synchronized (object) {
            if (this.field_92025_p.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
                var5.disableSecurityWarning();
                this.mc.displayGuiScreen(var5);
            }
        }
    }
}

