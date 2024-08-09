package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AccessibilityScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector4i;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.ui.clickgui.Window;
import wtf.shiyeno.ui.midnight.Style;
import wtf.shiyeno.ui.midnight.StyleManager;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.UserProfile;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.*;
import wtf.shiyeno.util.render.animation.AnimationMath;
import wtf.shiyeno.util.render.shader.ShaderUtils;

import javax.annotation.Nullable;

import static wtf.shiyeno.util.IMinecraft.mc;

public class MainMenuScreen extends Screen {
    private static final Logger field_238656_b_ = LogManager.getLogger();
    public static final RenderSkyboxCube PANORAMA_RESOURCES = new RenderSkyboxCube(new ResourceLocation("textures/gui/title/background/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY_TEXTURES = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private static final ResourceLocation ACCESSIBILITY_TEXTURES = new ResourceLocation("textures/gui/accessibility.png");
    private final boolean showTitleWronglySpelled;
    private Button buttonResetDemo;
    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation MINECRAFT_TITLE_EDITION = new ResourceLocation("textures/gui/title/edition.png");
    private int widthCopyright;
    private int widthCopyrightRest;
    private Screen realmsNotification;
    private boolean hasCheckedForRealmsNotification;

    @Nullable
    private String splashText;
    private final RenderSkybox panorama = new RenderSkybox(PANORAMA_RESOURCES);
    private final boolean showFadeInAnimation;
    private long firstRenderTime;

    public MainMenuScreen() {
        this(false);
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        if (ClientUtil.legitMode) {
            if (this.splashText == null) {
                this.splashText = this.minecraft.getSplashes().getSplashText();
            }

            this.widthCopyright = this.font.getStringWidth("Copyright Mojang AB. Do not distribute!");
            this.widthCopyrightRest = this.width - this.widthCopyright - 2;
            int i = 24;
            int j = this.height / 4 + 48;
            net.minecraft.client.gui.widget.button.Button button = null;


            this.addSingleplayerMultiplayerButtons(j, 24);

            if (Reflector.ModListScreen_Constructor.exists()) {
                button = ReflectorForge.makeButtonMods(this, j, 24);
                this.addButton(button);
            }

            this.addButton(new ImageButton(this.width / 2 - 124, j + 72 + 12, 20, 20, 0, 106, 20, net.minecraft.client.gui.widget.button.Button.WIDGETS_LOCATION, 256, 256, (p_lambda$init$0_1_) ->
            {
                this.minecraft.displayGuiScreen(new LanguageScreen(this, this.minecraft.gameSettings, this.minecraft.getLanguageManager()));
            }, new TranslationTextComponent("narrator.button.language")));
            this.addButton(new net.minecraft.client.gui.widget.button.Button(this.width / 2 - 100, j + 72 + 12, 98, 20, new TranslationTextComponent("menu.options"), (p_lambda$init$1_1_) ->
            {
                this.minecraft.displayGuiScreen(new OptionsScreen(this, this.minecraft.gameSettings));
            }));
            this.addButton(new net.minecraft.client.gui.widget.button.Button(this.width / 2 + 2, j + 72 + 12, 98, 20, new TranslationTextComponent("menu.quit"), (p_lambda$init$2_1_) ->
            {
                this.minecraft.shutdown();
            }));
            this.addButton(new ImageButton(this.width / 2 + 104, j + 72 + 12, 20, 20, 0, 0, 20, ACCESSIBILITY_TEXTURES, 32, 64, (p_lambda$init$3_1_) ->
            {
                this.minecraft.displayGuiScreen(new AccessibilityScreen(this, this.minecraft.gameSettings));
            }, new TranslationTextComponent("narrator.button.accessibility")));
            this.minecraft.setConnectedToRealms(false);

            if (this.minecraft.gameSettings.realmsNotifications && !this.hasCheckedForRealmsNotification) {
                RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
                this.realmsNotification = realmsbridgescreen.func_239555_b_(this);
                this.hasCheckedForRealmsNotification = true;
            }

            if (this.areRealmsNotificationsEnabled()) {
                this.realmsNotification.init(this.minecraft, this.width, this.height);
            }

            return;
        }

        int buttonWidth = 220;
        int buttonHeight = 22;
        int gap = 5;
        int off = (int) (buttonHeight + gap);

        this.addButton(new Button((int) (mc.getMainWindow().scaledWidth() / 2f - buttonWidth / 2f), mc.getMainWindow().scaledHeight() / 2  - 15, (buttonWidth - gap) / 2, buttonHeight, new StringTextComponent("Одиночная игра"), p_onPress_1_ -> {
            mc.displayGuiScreen(new WorldSelectionScreen(this));
        }));
        this.addButton(new Button((int) (mc.getMainWindow().scaledWidth() / 2f + gap / 2f), mc.getMainWindow().scaledHeight() / 2 - 15, (buttonWidth - gap) / 2, buttonHeight, new StringTextComponent("Сетевая игра"), p_onPress_1_ -> {
            mc.displayGuiScreen(new MultiplayerScreen(this));
        }));
        this.addButton(new Button((int) (mc.getMainWindow().scaledWidth() / 2 - buttonWidth / 2f), mc.getMainWindow().scaledHeight() / 2  - 15 + off, buttonWidth, buttonHeight, new StringTextComponent("Менеджер аккаунтов"), p_onPress_1_ -> {
            mc.displayGuiScreen(Managment.ALT);
        }));
        this.addButton(new Button((int) (mc.getMainWindow().scaledWidth() / 2 - buttonWidth / 2f), mc.getMainWindow().scaledHeight() / 2  - 15 + off * 2, buttonWidth, buttonHeight, new StringTextComponent("Настройки"), p_onPress_1_ -> {
            mc.displayGuiScreen(new OptionsScreen(this, this.minecraft.gameSettings));
        }));

        int miscButtonsWidth = 27 * 3 + gap * 2;

        this.addButton(new Button((int) (mc.getMainWindow().scaledWidth() / 2f - miscButtonsWidth / 2f), mc.getMainWindow().scaledHeight() / 2  - 15 + off * 4, 27, 27, new StringTextComponent("B"), p_onPress_1_ -> {
            try {
                Util.getOSType().openURI(new URI("https://t.me/shiyenoclient"));
            }
            catch(URISyntaxException e) {
            }
        },  true));
        this.addButton(new Button((int) (mc.getMainWindow().scaledWidth() / 2 - miscButtonsWidth / 2f + 27 + gap), mc.getMainWindow().scaledHeight() / 2  - 15 + off * 4, 27, 27, new StringTextComponent("C"), p_onPress_1_ -> {
            try {
                Util.getOSType().openURI(new URI("https://discord.gg/RA5qn2yvgs"));
            }
            catch(URISyntaxException e) {
            }
        },  true));
        this.addButton(new Button((int) (mc.getMainWindow().scaledWidth() / 2 - miscButtonsWidth / 2f + 27 * 2 + gap * 2), mc.getMainWindow().scaledHeight() / 2  - 15 + off * 4, 27, 27, new StringTextComponent("A"), p_onPress_1_ -> {
            mc.shutdownMinecraftApplet();
        },  true));
    }

    private boolean areRealmsNotificationsEnabled() {
        return this.minecraft.gameSettings.realmsNotifications && this.realmsNotification != null;
    }

    private void addSingleplayerMultiplayerButtons(int yIn, int rowHeightIn) {
        this.addButton(new net.minecraft.client.gui.widget.button.Button(this.width / 2 - 100, yIn, 200, 20, new TranslationTextComponent("menu.singleplayer"), (p_lambda$addSingleplayerMultiplayerButtons$4_1_) ->
        {
            this.minecraft.displayGuiScreen(new WorldSelectionScreen(this));
        }));
        boolean flag = this.minecraft.isMultiplayerEnabled();
        net.minecraft.client.gui.widget.button.Button.ITooltip button$itooltip = flag ? net.minecraft.client.gui.widget.button.Button.field_238486_s_ : (p_lambda$addSingleplayerMultiplayerButtons$5_1_, p_lambda$addSingleplayerMultiplayerButtons$5_2_, p_lambda$addSingleplayerMultiplayerButtons$5_3_, p_lambda$addSingleplayerMultiplayerButtons$5_4_) ->
        {
            if (!p_lambda$addSingleplayerMultiplayerButtons$5_1_.active) {
                this.renderTooltip(p_lambda$addSingleplayerMultiplayerButtons$5_2_, this.minecraft.fontRenderer.trimStringToWidth(new TranslationTextComponent("title.multiplayer.disabled"), Math.max(this.width / 2 - 43, 170)), p_lambda$addSingleplayerMultiplayerButtons$5_3_, p_lambda$addSingleplayerMultiplayerButtons$5_4_);
            }
        };
        (this.addButton(new net.minecraft.client.gui.widget.button.Button(this.width / 2 - 100, yIn + rowHeightIn * 1, 200, 20, new TranslationTextComponent("menu.multiplayer"), (p_lambda$addSingleplayerMultiplayerButtons$6_1_) ->
        {
            Screen screen = (Screen) (this.minecraft.gameSettings.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this));
            this.minecraft.displayGuiScreen(screen);
        }, button$itooltip))).active = flag;
        (this.addButton(new net.minecraft.client.gui.widget.button.Button(this.width / 2 - 100, yIn + rowHeightIn * 2, 200, 20, new TranslationTextComponent("menu.online"), (p_lambda$addSingleplayerMultiplayerButtons$7_1_) ->
        {
            this.switchToRealms();
        }, button$itooltip))).active = flag;

        if (Reflector.ModListScreen_Constructor.exists() && this.buttons.size() > 0) {
            Widget widget = this.buttons.get(this.buttons.size() - 1);
            widget.x = this.width / 2 + 2;
            widget.setWidth(98);
        }
    }

    private void switchToRealms() {
        RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
        realmsbridgescreen.func_231394_a_(this);
    }

    public MainMenuScreen(boolean fadeIn) {
        super(new TranslationTextComponent("narrator.screen.title"));
        this.showFadeInAnimation = fadeIn;
        this.showTitleWronglySpelled = (double) (new Random()).nextFloat() < 1.0E-4D;
    }

    public void tick() {
        if (this.areRealmsNotificationsEnabled()) {
            this.realmsNotification.tick();
        }
    }

    public static CompletableFuture<Void> loadAsync(TextureManager texMngr, Executor backgroundExecutor) {
        return CompletableFuture.allOf(texMngr.loadAsync(MINECRAFT_TITLE_TEXTURES, backgroundExecutor), texMngr.loadAsync(MINECRAFT_TITLE_EDITION, backgroundExecutor), texMngr.loadAsync(PANORAMA_OVERLAY_TEXTURES, backgroundExecutor), PANORAMA_RESOURCES.loadAsync(texMngr, backgroundExecutor));
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (ClientUtil.legitMode) {
            if (this.firstRenderTime == 0L && this.showFadeInAnimation) {
                this.firstRenderTime = Util.milliTime();
            }

            float f = this.showFadeInAnimation ? (float) (Util.milliTime() - this.firstRenderTime) / 1000.0F : 1.0F;
            GlStateManager.disableDepthTest();
            fill(matrixStack, 0, 0, this.width, this.height, -1);
            this.panorama.render(partialTicks, MathHelper.clamp(f, 0.0F, 1.0F));
            int i = 274;
            int j = this.width / 2 - 137;
            int k = 30;
            this.minecraft.getTextureManager().bindTexture(PANORAMA_OVERLAY_TEXTURES);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.showFadeInAnimation ? (float) MathHelper.ceil(MathHelper.clamp(f, 0.0F, 1.0F)) : 1.0F);
            blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
            float f1 = this.showFadeInAnimation ? MathHelper.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
            int l = MathHelper.ceil(f1 * 255.0F) << 24;

            if ((l & -67108864) != 0) {
                this.minecraft.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, f1);

                if (this.showTitleWronglySpelled) {
                    this.blitBlackOutline(j, 30, (p_lambda$render$10_2_, p_lambda$render$10_3_) ->
                    {
                        this.blit(matrixStack, p_lambda$render$10_2_ + 0, p_lambda$render$10_3_, 0, 0, 99, 44);
                        this.blit(matrixStack, p_lambda$render$10_2_ + 99, p_lambda$render$10_3_, 129, 0, 27, 44);
                        this.blit(matrixStack, p_lambda$render$10_2_ + 99 + 26, p_lambda$render$10_3_, 126, 0, 3, 44);
                        this.blit(matrixStack, p_lambda$render$10_2_ + 99 + 26 + 3, p_lambda$render$10_3_, 99, 0, 26, 44);
                        this.blit(matrixStack, p_lambda$render$10_2_ + 155, p_lambda$render$10_3_, 0, 45, 155, 44);
                    });
                } else {
                    this.blitBlackOutline(j, 30, (p_lambda$render$11_2_, p_lambda$render$11_3_) ->
                    {
                        this.blit(matrixStack, p_lambda$render$11_2_ + 0, p_lambda$render$11_3_, 0, 0, 155, 44);
                        this.blit(matrixStack, p_lambda$render$11_2_ + 155, p_lambda$render$11_3_, 0, 45, 155, 44);
                    });
                }

                this.minecraft.getTextureManager().bindTexture(MINECRAFT_TITLE_EDITION);
                blit(matrixStack, j + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);

                if (Reflector.ForgeHooksClient_renderMainMenu.exists()) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_renderMainMenu, this, matrixStack, this.font, this.width, this.height, l);
                }

                if (this.splashText != null) {
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef((float) (this.width / 2 + 90), 70.0F, 0.0F);
                    RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
                    float f2 = 1.8F - MathHelper.abs(MathHelper.sin((float) (Util.milliTime() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
                    f2 = f2 * 100.0F / (float) (this.font.getStringWidth(this.splashText) + 32);
                    RenderSystem.scalef(f2, f2, f2);
                    drawCenteredString(matrixStack, this.font, this.splashText, 0, -8, 16776960 | l);
                    RenderSystem.popMatrix();
                }

                String s = "Minecraft " + SharedConstants.getVersion().getName();

                if (this.minecraft.isDemo()) {
                    s = s + " Demo";
                } else {
                    s = s + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
                }

                if (this.minecraft.isModdedClient()) {
                    s = s + I18n.format("menu.modded");
                }

                if (Reflector.BrandingControl.exists()) {
                    if (Reflector.BrandingControl_forEachLine.exists()) {
                        BiConsumer<Integer, String> biconsumer = (p_lambda$render$12_3_, p_lambda$render$12_4_) ->
                        {
                            drawString(matrixStack, this.font, p_lambda$render$12_4_, 2, this.height - (10 + p_lambda$render$12_3_ * (9 + 1)), 16777215 | l);
                        };
                        Reflector.call(Reflector.BrandingControl_forEachLine, true, true, biconsumer);
                    }

                    if (Reflector.BrandingControl_forEachAboveCopyrightLine.exists()) {
                        BiConsumer<Integer, String> biconsumer1 = (p_lambda$render$13_3_, p_lambda$render$13_4_) ->
                        {
                            drawString(matrixStack, this.font, p_lambda$render$13_4_, this.width - this.font.getStringWidth(p_lambda$render$13_4_), this.height - (10 + (p_lambda$render$13_3_ + 1) * (9 + 1)), 16777215 | l);
                        };
                        Reflector.call(Reflector.BrandingControl_forEachAboveCopyrightLine, biconsumer1);
                    }
                } else {
                    drawString(matrixStack, this.font, s, 2, this.height - 10, 16777215 | l);
                }

                drawString(matrixStack, this.font, "Copyright Mojang AB. Do not distribute!", this.widthCopyrightRest, this.height - 10, 16777215 | l);

                if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.height - 10 && mouseY < this.height) {
                    fill(matrixStack, this.widthCopyrightRest, this.height - 1, this.widthCopyrightRest + this.widthCopyright, this.height, 16777215 | l);
                }

                for (Widget widget : this.buttons) {
                    widget.setAlpha(f1);
                }
                if (this.areRealmsNotificationsEnabled() && f1 >= 1.0F) {
                    this.realmsNotification.render(matrixStack, mouseX, mouseY, partialTicks);
                }
                super.render(matrixStack, mouseX, mouseY, partialTicks);
            }
            return;
        }

        float widthPerc = (float) mc.getMainWindow().scaledWidth() / 960;
        float heigthPerc = (float) mc.getMainWindow().scaledHeight() / 505;
        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();

        mc.gameRenderer.setupOverlayRendering(2);

        float windowWidth = mc.getMainWindow().scaledWidth();
        float windowHeight = mc.getMainWindow().scaledHeight();

        ShaderUtils.MAIN_MENU.begin();
        ShaderUtils.MAIN_MENU.setUniform("time", mc.timer.getPartialTicks(1) / 20.0F);
        ShaderUtils.MAIN_MENU.setUniform("resolution", windowWidth, windowHeight);
        ShaderUtils.MAIN_MENU.setUniform("mouse", mouseX, mouseY);
        ShaderUtils.MAIN_MENU.drawQuads(0,0, mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight());
        ShaderUtils.MAIN_MENU.end();

        int miscButtonsWidth = 27 * 3 + 5 * 4;
        RenderUtil.Render2D.drawRoundedRect(mc.getMainWindow().scaledWidth() / 2f - miscButtonsWidth / 2f, mc.getMainWindow().scaledHeight() / 2f  - 15 + 27 * 4, miscButtonsWidth, 27, 6,
                RenderUtil.reAlphaInt(Window.light, 100));

        super.render(matrixStack, mouseX, mouseY, partialTicks);
        float calculatedWidth = 548f / 474f * 50.5f * widthPerc;

        RenderUtil.Render2D.drawImage(
                new ResourceLocation("shiyeno/images/shiyeno.png"),
                (mc.getMainWindow().getScaledWidth() / 2f) - (calculatedWidth / 2),
                mc.getMainWindow().getScaledHeight() / 2f - 110,
                calculatedWidth,
                50.5f * heigthPerc,
                -1
        );

        BloomHelper.draw(15, 2, false);
        mc.gameRenderer.setupOverlayRendering();
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void onClose() {
        if (this.realmsNotification != null) {
            this.realmsNotification.onClose();
        }
    }

    public static class Button extends AbstractButton {

        public static final net.minecraft.client.gui.widget.button.Button.ITooltip field_238486_s_ = (button, matrixStack, mouseX, mouseY) ->
        {
        };
        protected final net.minecraft.client.gui.widget.button.Button.IPressable onPress;
        protected final net.minecraft.client.gui.widget.button.Button.ITooltip onTooltip;
        private boolean useIcon;

        public Button(int x, int y, int width, int height, ITextComponent title, net.minecraft.client.gui.widget.button.Button.IPressable pressedAction) {
            this(x, y, width, height, title, pressedAction, field_238486_s_, false);
        }

        public Button(int x, int y, int width, int height, ITextComponent title, net.minecraft.client.gui.widget.button.Button.IPressable pressedAction, boolean useIcon) {
            this(x, y, width, height, title, pressedAction, field_238486_s_, useIcon);
        }

        public Button(int x, int y, int width, int height, ITextComponent title, net.minecraft.client.gui.widget.button.Button.IPressable pressedAction,  net.minecraft.client.gui.widget.button.Button.ITooltip onTooltip)
        {
            super(x, y, width, height, title);
            this.onPress = pressedAction;
            this.onTooltip = onTooltip;
        }

        public Button(int x, int y, int width, int height, ITextComponent title, net.minecraft.client.gui.widget.button.Button.IPressable pressedAction, net.minecraft.client.gui.widget.button.Button.ITooltip onTooltip, boolean useIcon) {
            super(x, y, width, height, title);
            this.onPress = pressedAction;
            this.onTooltip = onTooltip;
            this.useIcon = useIcon;
        }

        public float animation;

        public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            animation = AnimationMath.lerp(animation, isHovered() ? 1f : 0, 10);

            float maxDistance = (float) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

            if (!useIcon) {
                Style s1 = new Style("a", ColorUtil.getColorStyle(0), Window.light);
                Style s2 = new Style("a", ColorUtil.getColorStyle(90), Window.light);
                Style s3 = new Style("a", ColorUtil.getColorStyle(180), Window.light);
                Style s4 = new Style("a", ColorUtil.getColorStyle(270), Window.light);

                RenderUtil.Render2D.drawRoundedRect(x-2f, y-2f, width+4, height+4, 6f, RenderUtil.reAlphaInt(ColorUtil.rgba(1d, 1d,1d, 1d), (int)(255 * animation)));

                RenderUtil.Render2D.drawGradientRound(x-0.5f, y-0.5f, width+1, height+1, 5.5f,
                        ColorUtil.interpolateColor(ColorUtil.rgba(1d, 1d,1d, 1d), ColorUtil.gradient(2, 270, s4.colors), animation),
                        ColorUtil.interpolateColor(ColorUtil.rgba(1d, 1d,1d, 1d), ColorUtil.gradient(2, 0, s1.colors), animation),
                        ColorUtil.interpolateColor(ColorUtil.rgba(1d, 1d,1d, 1d), ColorUtil.gradient(2, 180, s3.colors), animation),
                        ColorUtil.interpolateColor(ColorUtil.rgba(1d, 1d,1d, 1d), ColorUtil.gradient(2, 90, s2.colors), animation));

                RenderUtil.Render2D.drawGradientRound(x, y, width, height, 5f,
                        ColorUtil.interpolateColor(Window.light, ColorUtil.getColorStyle(270), animation * 0.1f),
                        ColorUtil.interpolateColor(Window.light, ColorUtil.getColorStyle(0), animation * 0.1f),
                        ColorUtil.interpolateColor(Window.light, ColorUtil.getColorStyle(180), animation * 0.1f),
                        ColorUtil.interpolateColor(Window.light, ColorUtil.getColorStyle(90), animation * 0.1f));

            }

            var font = useIcon ? Fonts.mainMenuIcons[31] : Fonts.msRegular[17];

            if (useIcon) {
                font.drawCenteredString(matrixStack,
                        this.getMessage().getString(),
                        x + width / 2f + 1,
                        y + height / 2f - font.getFontHeight() / 2f + 3,
                        RenderUtil.reAlphaInt(ColorUtil.interpolateColor(-1, ColorUtil.getColorStyle(0), animation), (int) (255)));
            } else {
                BloomHelper.registerRenderCall(() -> {
                    font.drawCenteredString(matrixStack,
                            ClientUtil.gradient(this.getMessage().getString(),
                                    RenderUtil.reAlphaInt(
                                            ColorUtil.interpolateColor(Window.light, ColorUtil.getColorStyle(0), animation), (int)(255 * animation)),
                                    RenderUtil.reAlphaInt(
                                            ColorUtil.interpolateColor(Window.light, ColorUtil.getColorStyle(90), animation), (int)(255 * animation))),
                            x + width / 2f,
                            y + height / 2f - font.getFontHeight() / 2f + 2,
                            -1);
                });

                font.drawCenteredString(matrixStack,
                        ClientUtil.gradient(this.getMessage().getString(),
                                RenderUtil.reAlphaInt(
                                        ColorUtil.interpolateColor(ColorUtil.rgba(132, 135, 147,255), ColorUtil.getColorStyle(0), animation), (int)(255 * animation)),
                                RenderUtil.reAlphaInt(
                                        ColorUtil.interpolateColor(ColorUtil.rgba(132, 135, 147,255), ColorUtil.getColorStyle(90), animation), (int)(255 * animation))),
                        x + width / 2f,
                        y + height / 2f - font.getFontHeight() / 2f + 2,
                        -1);
            }
        }

        public void onPress() {
            this.onPress.onPress(this);
        }
    }
}