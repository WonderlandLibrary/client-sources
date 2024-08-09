/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AccessibilityScreen;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.LanguageScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WinGameScreen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.WorldSummary;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.raphimc.mcauth.step.java.StepMCProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenuScreen
extends Screen {
    private static final Logger field_238656_b_ = LogManager.getLogger();
    public static final RenderSkyboxCube PANORAMA_RESOURCES = new RenderSkyboxCube(new ResourceLocation("textures/gui/title/background/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY_TEXTURES = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private static final ResourceLocation ACCESSIBILITY_TEXTURES = new ResourceLocation("textures/gui/accessibility.png");
    private final boolean showTitleWronglySpelled;
    @Nullable
    private String splashText;
    private Button buttonResetDemo;
    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation MINECRAFT_TITLE_EDITION = new ResourceLocation("textures/gui/title/edition.png");
    private boolean hasCheckedForRealmsNotification;
    private Screen realmsNotification;
    private int widthCopyright;
    private int widthCopyrightRest;
    private final RenderSkybox panorama = new RenderSkybox(PANORAMA_RESOURCES);
    private final boolean showFadeInAnimation;
    private long firstRenderTime;
    private Screen modUpdateNotification;
    boolean start = false;
    String authCode = "";
    boolean authed;
    StepMCProfile.MCProfile mcProfile = null;

    public MainMenuScreen() {
        this(false);
    }

    public MainMenuScreen(boolean bl) {
        super(new TranslationTextComponent("narrator.screen.title"));
        this.showFadeInAnimation = bl;
        this.showTitleWronglySpelled = (double)new Random().nextFloat() < 1.0E-4;
    }

    private boolean areRealmsNotificationsEnabled() {
        return this.minecraft.gameSettings.realmsNotifications && this.realmsNotification != null;
    }

    @Override
    public void tick() {
        if (this.areRealmsNotificationsEnabled()) {
            this.realmsNotification.tick();
        }
    }

    public static CompletableFuture<Void> loadAsync(TextureManager textureManager, Executor executor) {
        return CompletableFuture.allOf(textureManager.loadAsync(MINECRAFT_TITLE_TEXTURES, executor), textureManager.loadAsync(MINECRAFT_TITLE_EDITION, executor), textureManager.loadAsync(PANORAMA_OVERLAY_TEXTURES, executor), PANORAMA_RESOURCES.loadAsync(textureManager, executor));
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    protected void init() {
        if (this.splashText == null) {
            this.splashText = this.minecraft.getSplashes().getSplashText();
        }
        this.widthCopyright = this.font.getStringWidth("Copyright Mojang AB. Do not distribute!");
        this.widthCopyrightRest = this.width - this.widthCopyright - 2;
        int n = 24;
        int n2 = this.height / 4 + 48;
        Button button = null;
        if (this.minecraft.isDemo()) {
            this.addDemoButtons(n2, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(n2, 24);
            if (Reflector.ModListScreen_Constructor.exists()) {
                button = ReflectorForge.makeButtonMods(this, n2, 24);
                this.addButton(button);
            }
        }
        this.addButton(new ImageButton(this.width / 2 - 124, n2 + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, this::lambda$init$0, new TranslationTextComponent("narrator.button.language")));
        this.addButton(new Button(this.width / 2 - 100, n2 + 72 + 12, 98, 20, new TranslationTextComponent("menu.options"), this::lambda$init$1));
        this.addButton(new Button(this.width / 2 + 2, n2 + 72 + 12, 98, 20, new TranslationTextComponent("menu.quit"), this::lambda$init$2));
        this.addButton(new ImageButton(this.width / 2 + 104, n2 + 72 + 12, 20, 20, 0, 0, 20, ACCESSIBILITY_TEXTURES, 32, 64, this::lambda$init$3, new TranslationTextComponent("narrator.button.accessibility")));
        this.minecraft.setConnectedToRealms(true);
        if (this.minecraft.gameSettings.realmsNotifications && !this.hasCheckedForRealmsNotification) {
            RealmsBridgeScreen realmsBridgeScreen = new RealmsBridgeScreen();
            this.realmsNotification = realmsBridgeScreen.func_239555_b_(this);
            this.hasCheckedForRealmsNotification = true;
        }
        if (this.areRealmsNotificationsEnabled()) {
            this.realmsNotification.init(this.minecraft, this.width, this.height);
        }
        if (Reflector.NotificationModUpdateScreen_init.exists()) {
            this.modUpdateNotification = (Screen)Reflector.call(Reflector.NotificationModUpdateScreen_init, this, button);
        }
    }

    private void addSingleplayerMultiplayerButtons(int n, int n2) {
        this.addButton(new Button(this.width / 2 - 100, n, 200, 20, new TranslationTextComponent("menu.singleplayer"), this::lambda$addSingleplayerMultiplayerButtons$4));
        boolean bl = this.minecraft.isMultiplayerEnabled();
        Button.ITooltip iTooltip = bl ? Button.field_238486_s_ : this::lambda$addSingleplayerMultiplayerButtons$5;
        this.addButton(new Button((int)(this.width / 2 - 100), (int)(n + n2 * 1), (int)200, (int)20, (ITextComponent)new TranslationTextComponent((String)"menu.multiplayer"), (Button.IPressable)(Button.IPressable)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/widget/button/Button;)V, lambda$addSingleplayerMultiplayerButtons$6(net.minecraft.client.gui.widget.button.Button ), (Lnet/minecraft/client/gui/widget/button/Button;)V)((MainMenuScreen)this), (Button.ITooltip)iTooltip)).active = bl;
        this.addButton(new Button((int)(this.width / 2 - 100), (int)(n + n2 * 2), (int)200, (int)20, (ITextComponent)new TranslationTextComponent((String)"menu.online"), (Button.IPressable)(Button.IPressable)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/widget/button/Button;)V, lambda$addSingleplayerMultiplayerButtons$7(net.minecraft.client.gui.widget.button.Button ), (Lnet/minecraft/client/gui/widget/button/Button;)V)((MainMenuScreen)this), (Button.ITooltip)iTooltip)).active = bl;
        if (Reflector.ModListScreen_Constructor.exists() && this.buttons.size() > 0) {
            Widget widget = (Widget)this.buttons.get(this.buttons.size() - 1);
            widget.x = this.width / 2 + 2;
            widget.setWidth(98);
        }
    }

    private void addDemoButtons(int n, int n2) {
        boolean bl = this.func_243319_k();
        this.addButton(new Button(this.width / 2 - 100, n, 200, 20, new TranslationTextComponent("menu.playdemo"), arg_0 -> this.lambda$addDemoButtons$8(bl, arg_0)));
        this.buttonResetDemo = this.addButton(new Button(this.width / 2 - 100, n + n2 * 1, 200, 20, new TranslationTextComponent("menu.resetdemo"), this::lambda$addDemoButtons$9));
        this.buttonResetDemo.active = bl;
    }

    private boolean func_243319_k() {
        boolean bl;
        block8: {
            SaveFormat.LevelSave levelSave = this.minecraft.getSaveLoader().getLevelSave("Demo_World");
            try {
                boolean bl2 = bl = levelSave.readWorldSummary() != null;
                if (levelSave == null) break block8;
            } catch (Throwable throwable) {
                try {
                    if (levelSave != null) {
                        try {
                            levelSave.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                } catch (IOException iOException) {
                    SystemToast.func_238535_a_(this.minecraft, "Demo_World");
                    field_238656_b_.warn("Failed to read demo world data", (Throwable)iOException);
                    return true;
                }
            }
            levelSave.close();
        }
        return bl;
    }

    private void switchToRealms() {
        RealmsBridgeScreen realmsBridgeScreen = new RealmsBridgeScreen();
        realmsBridgeScreen.func_231394_a_(this);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.firstRenderTime == 0L && this.showFadeInAnimation) {
            this.firstRenderTime = Util.milliTime();
        }
        float f2 = this.showFadeInAnimation ? (float)(Util.milliTime() - this.firstRenderTime) / 1000.0f : 1.0f;
        GlStateManager.disableDepthTest();
        MainMenuScreen.fill(matrixStack, 0, 0, this.width, this.height, -1);
        this.panorama.render(f, MathHelper.clamp(f2, 0.0f, 1.0f));
        int n3 = 274;
        int n4 = this.width / 2 - 137;
        int n5 = 30;
        this.minecraft.getTextureManager().bindTexture(PANORAMA_OVERLAY_TEXTURES);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, this.showFadeInAnimation ? (float)MathHelper.ceil(MathHelper.clamp(f2, 0.0f, 1.0f)) : 1.0f);
        MainMenuScreen.blit(matrixStack, 0, 0, this.width, this.height, 0.0f, 0.0f, 16, 128, 16, 128);
        float f3 = this.showFadeInAnimation ? MathHelper.clamp(f2 - 1.0f, 0.0f, 1.0f) : 1.0f;
        int n6 = MathHelper.ceil(f3 * 255.0f) << 24;
        if ((n6 & 0xFC000000) != 0) {
            this.minecraft.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, f3);
            if (this.showTitleWronglySpelled) {
                this.blitBlackOutline(n4, 30, (arg_0, arg_1) -> this.lambda$render$10(matrixStack, arg_0, arg_1));
            } else {
                this.blitBlackOutline(n4, 30, (arg_0, arg_1) -> this.lambda$render$11(matrixStack, arg_0, arg_1));
            }
            this.minecraft.getTextureManager().bindTexture(MINECRAFT_TITLE_EDITION);
            MainMenuScreen.blit(matrixStack, n4 + 88, 67, 0.0f, 0.0f, 98, 14, 128, 16);
            if (Reflector.ForgeHooksClient_renderMainMenu.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_renderMainMenu, this, matrixStack, this.font, this.width, this.height, n6);
            }
            if (this.splashText != null) {
                RenderSystem.pushMatrix();
                RenderSystem.translatef(this.width / 2 + 90, 70.0f, 0.0f);
                RenderSystem.rotatef(-20.0f, 0.0f, 0.0f, 1.0f);
                float f4 = 1.8f - MathHelper.abs(MathHelper.sin((float)(Util.milliTime() % 1000L) / 1000.0f * ((float)Math.PI * 2)) * 0.1f);
                f4 = f4 * 100.0f / (float)(this.font.getStringWidth(this.splashText) + 32);
                RenderSystem.scalef(f4, f4, f4);
                MainMenuScreen.drawCenteredString(matrixStack, this.font, this.splashText, 0, -8, 0xFFFF00 | n6);
                RenderSystem.popMatrix();
            }
            String string = "Minecraft " + SharedConstants.getVersion().getName();
            string = this.minecraft.isDemo() ? string + " Demo" : string + (String)("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
            if (this.minecraft.isModdedClient()) {
                string = string + I18n.format("menu.modded", new Object[0]);
            }
            if (Reflector.BrandingControl.exists()) {
                Object object;
                if (Reflector.BrandingControl_forEachLine.exists()) {
                    object = (arg_0, arg_1) -> this.lambda$render$12(matrixStack, n6, arg_0, arg_1);
                    Reflector.call(Reflector.BrandingControl_forEachLine, true, true, object);
                }
                if (Reflector.BrandingControl_forEachAboveCopyrightLine.exists()) {
                    object = (arg_0, arg_1) -> this.lambda$render$13(matrixStack, n6, arg_0, arg_1);
                    Reflector.call(Reflector.BrandingControl_forEachAboveCopyrightLine, object);
                }
            } else {
                MainMenuScreen.drawString(matrixStack, this.font, string, 2, this.height - 10, 0xFFFFFF | n6);
            }
            MainMenuScreen.drawString(matrixStack, this.font, "Copyright Mojang AB. Do not distribute!", this.widthCopyrightRest, this.height - 10, 0xFFFFFF | n6);
            if (n > this.widthCopyrightRest && n < this.widthCopyrightRest + this.widthCopyright && n2 > this.height - 10 && n2 < this.height) {
                MainMenuScreen.fill(matrixStack, this.widthCopyrightRest, this.height - 1, this.widthCopyrightRest + this.widthCopyright, this.height, 0xFFFFFF | n6);
            }
            for (Widget widget : this.buttons) {
                widget.setAlpha(f3);
            }
            super.render(matrixStack, n, n2, f);
            if (this.areRealmsNotificationsEnabled() && f3 >= 1.0f) {
                this.realmsNotification.render(matrixStack, n, n2, f);
            }
        }
        if (this.modUpdateNotification != null) {
            this.modUpdateNotification.render(matrixStack, n, n2, f);
        }
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (super.mouseClicked(d, d2, n)) {
            return false;
        }
        if (this.areRealmsNotificationsEnabled() && this.realmsNotification.mouseClicked(d, d2, n)) {
            return false;
        }
        if (d > (double)this.widthCopyrightRest && d < (double)(this.widthCopyrightRest + this.widthCopyright) && d2 > (double)(this.height - 10) && d2 < (double)this.height) {
            this.minecraft.displayGuiScreen(new WinGameScreen(false, Runnables.doNothing()));
        }
        return true;
    }

    @Override
    public void onClose() {
        if (this.realmsNotification != null) {
            this.realmsNotification.onClose();
        }
    }

    private void deleteDemoWorld(boolean bl) {
        if (bl) {
            try (SaveFormat.LevelSave levelSave = this.minecraft.getSaveLoader().getLevelSave("Demo_World");){
                levelSave.deleteSave();
            } catch (IOException iOException) {
                SystemToast.func_238538_b_(this.minecraft, "Demo_World");
                field_238656_b_.warn("Failed to delete demo world", (Throwable)iOException);
            }
        }
        this.minecraft.displayGuiScreen(this);
    }

    private void lambda$render$13(MatrixStack matrixStack, int n, Integer n2, String string) {
        MainMenuScreen.drawString(matrixStack, this.font, string, this.width - this.font.getStringWidth(string), this.height - (10 + (n2 + 1) * 10), 0xFFFFFF | n);
    }

    private void lambda$render$12(MatrixStack matrixStack, int n, Integer n2, String string) {
        MainMenuScreen.drawString(matrixStack, this.font, string, 2, this.height - (10 + n2 * 10), 0xFFFFFF | n);
    }

    private void lambda$render$11(MatrixStack matrixStack, Integer n, Integer n2) {
        this.blit(matrixStack, n + 0, n2, 0, 0, 155, 44);
        this.blit(matrixStack, n + 155, n2, 0, 45, 155, 44);
    }

    private void lambda$render$10(MatrixStack matrixStack, Integer n, Integer n2) {
        this.blit(matrixStack, n + 0, n2, 0, 0, 99, 44);
        this.blit(matrixStack, n + 99, n2, 129, 0, 27, 44);
        this.blit(matrixStack, n + 99 + 26, n2, 126, 0, 3, 44);
        this.blit(matrixStack, n + 99 + 26 + 3, n2, 99, 0, 26, 44);
        this.blit(matrixStack, n + 155, n2, 0, 45, 155, 44);
    }

    private void lambda$addDemoButtons$9(Button button) {
        SaveFormat saveFormat = this.minecraft.getSaveLoader();
        try (SaveFormat.LevelSave levelSave = saveFormat.getLevelSave("Demo_World");){
            WorldSummary worldSummary = levelSave.readWorldSummary();
            if (worldSummary != null) {
                this.minecraft.displayGuiScreen(new ConfirmScreen(this::deleteDemoWorld, new TranslationTextComponent("selectWorld.deleteQuestion"), new TranslationTextComponent("selectWorld.deleteWarning", worldSummary.getDisplayName()), new TranslationTextComponent("selectWorld.deleteButton"), DialogTexts.GUI_CANCEL));
            }
        } catch (IOException iOException) {
            SystemToast.func_238535_a_(this.minecraft, "Demo_World");
            field_238656_b_.warn("Failed to access demo world", (Throwable)iOException);
        }
    }

    private void lambda$addDemoButtons$8(boolean bl, Button button) {
        if (bl) {
            this.minecraft.loadWorld("Demo_World");
        } else {
            DynamicRegistries.Impl impl = DynamicRegistries.func_239770_b_();
            this.minecraft.createWorld("Demo_World", MinecraftServer.DEMO_WORLD_SETTINGS, impl, DimensionGeneratorSettings.func_242752_a(impl));
        }
    }

    private void lambda$addSingleplayerMultiplayerButtons$7(Button button) {
        this.switchToRealms();
    }

    private void lambda$addSingleplayerMultiplayerButtons$6(Button button) {
        Screen screen = this.minecraft.gameSettings.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this);
        this.minecraft.displayGuiScreen(screen);
    }

    private void lambda$addSingleplayerMultiplayerButtons$5(Button button, MatrixStack matrixStack, int n, int n2) {
        if (!button.active) {
            this.renderTooltip(matrixStack, this.minecraft.fontRenderer.trimStringToWidth(new TranslationTextComponent("title.multiplayer.disabled"), Math.max(this.width / 2 - 43, 170)), n, n2);
        }
    }

    private void lambda$addSingleplayerMultiplayerButtons$4(Button button) {
        this.minecraft.displayGuiScreen(new WorldSelectionScreen(this));
    }

    private void lambda$init$3(Button button) {
        this.minecraft.displayGuiScreen(new AccessibilityScreen(this, this.minecraft.gameSettings));
    }

    private void lambda$init$2(Button button) {
        this.minecraft.shutdown();
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(new OptionsScreen(this, this.minecraft.gameSettings));
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(new LanguageScreen((Screen)this, this.minecraft.gameSettings, this.minecraft.getLanguageManager()));
    }
}

