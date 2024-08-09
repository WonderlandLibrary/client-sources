/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.client.Vec2i;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.KawaseBlur;
import mpp.venusfr.utils.render.RenderUtil;
import mpp.venusfr.utils.render.Stencil;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.utils.shader.ShaderUtil;
import mpp.venusfr.venusfr;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;

public class MainScreen
extends Screen
implements IMinecraft {
    float padding = 5.0f;
    float heightButton = 15.0f + 2.0f * this.padding;
    private final ResourceLocation backmenu = new ResourceLocation("venusfr/images/backmenu.png");
    private final ResourceLocation logo = new ResourceLocation("venusfr/images/logo.png");
    private final ResourceLocation single = new ResourceLocation("venusfr/images/mainmenu/single.png");
    private final ResourceLocation multy = new ResourceLocation("venusfr/images/mainmenu/multy.png");
    private final ResourceLocation settings = new ResourceLocation("venusfr/images/mainmenu/settings.png");
    private final ResourceLocation quit = new ResourceLocation("venusfr/images/mainmenu/quit.png");
    private final List<Button> buttons = new ArrayList<Button>();
    private static final CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList();
    private final StopWatch stopWatch = new StopWatch();
    static boolean start = false;
    public static final ResourceLocation button = new ResourceLocation("venusfr/images/button.png");

    public MainScreen() {
        super(ITextComponent.getTextComponentOrEmpty(""));
    }

    @Override
    public void init(Minecraft minecraft, int n, int n2) {
        super.init(minecraft, n, n2);
        float f = 175.0f;
        for (Particle particle : particles) {
            particle.y = ThreadLocalRandom.current().nextInt(-5, n2);
        }
        float f2 = 40.0f;
        float f3 = (float)Math.round((float)ClientUtil.calc(n2) / 2.0f + 1.0f) + 90.0f;
        this.buttons.clear();
        this.buttons.add(new Button(this, f2, f3, 90.0f, this.heightButton, "Single", this::lambda$init$0));
        this.buttons.add(new Button(this, f2, f3 += this.heightButton + 5.0f, 90.0f, this.heightButton, "Servers", this::lambda$init$1));
        this.buttons.add(new Button(this, f2, f3 += this.heightButton + 5.0f, 90.0f, this.heightButton, "Settings", this::lambda$init$2));
        this.buttons.add(new Button(this, f2, f3 += this.heightButton + 5.0f, 90.0f, this.heightButton, "Exit", mc::shutdownMinecraftApplet));
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        venusfr.getInstance().getAltWidget().updateScroll((int)d, (int)d2, (float)d3);
        return super.mouseScrolled(d, d2, d3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        boolean bl;
        super.render(matrixStack, n, n2, f);
        if (this.stopWatch.isReached(100L)) {
            particles.add(new Particle(this));
            this.stopWatch.reset();
        }
        MainWindow mainWindow = mc.getMainWindow();
        int n3 = ClientUtil.calc(mainWindow.getScaledWidth());
        int n4 = ClientUtil.calc(mainWindow.getScaledHeight());
        int n5 = 960;
        int n6 = 540;
        int n7 = (n3 - n5) / 2;
        int n8 = (n4 - n6) / 2 + 50;
        boolean bl2 = bl = mainWindow.getWidth() < 900 && mainWindow.getHeight() < 900;
        if (bl) {
            n8 += 50;
        }
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RenderUtil.shaderMainMenu.init();
        RenderUtil.shaderMainMenu.setUniformf("time", (float)(System.currentTimeMillis() - venusfr.initTime) / 1000.0f);
        RenderUtil.shaderMainMenu.setUniformf("resolution", mc.getMainWindow().getWidth(), mc.getMainWindow().getHeight());
        ShaderUtil.drawQuads();
        RenderUtil.shaderMainMenu.unload();
        GlStateManager.disableBlend();
        MainScreen.mc.gameRenderer.setupOverlayRendering(2);
        DisplayUtils.drawImage(this.logo, (float)n7, (float)n8, (float)n5, (float)n6, -1);
        KawaseBlur.blur.updateBlur(2.0f, 10);
        this.drawButtons(matrixStack, n, n2, f);
        venusfr.getInstance().getAltWidget().render(matrixStack, n, n2);
        MainScreen.mc.gameRenderer.setupOverlayRendering();
    }

    @Override
    public boolean charTyped(char c, int n) {
        venusfr.getInstance().getAltWidget().onChar(c);
        return super.charTyped(c, n);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        venusfr.getInstance().getAltWidget().onKey(n);
        return true;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        Vec2i vec2i = ClientUtil.getMouse((int)d, (int)d2);
        this.buttons.forEach(arg_0 -> MainScreen.lambda$mouseClicked$3(vec2i, n, arg_0));
        venusfr.getInstance().getAltWidget().click(vec2i.getX(), vec2i.getY(), n);
        return super.mouseClicked(d, d2, n);
    }

    private void drawButtons(MatrixStack matrixStack, int n, int n2, float f) {
        this.buttons.forEach(arg_0 -> MainScreen.lambda$drawButtons$4(matrixStack, n, n2, f, arg_0));
    }

    private static void lambda$drawButtons$4(MatrixStack matrixStack, int n, int n2, float f, Button button) {
        button.render(matrixStack, n, n2, f);
    }

    private static void lambda$mouseClicked$3(Vec2i vec2i, int n, Button button) {
        button.click(vec2i.getX(), vec2i.getY(), n);
    }

    private void lambda$init$2() {
        mc.displayGuiScreen(new OptionsScreen(this, MainScreen.mc.gameSettings));
    }

    private void lambda$init$1() {
        mc.displayGuiScreen(new MultiplayerScreen(this));
    }

    private void lambda$init$0() {
        mc.displayGuiScreen(new WorldSelectionScreen(this));
    }

    private class Particle {
        private final float x;
        private float y;
        private float size;
        final MainScreen this$0;

        public Particle(MainScreen mainScreen) {
            this.this$0 = mainScreen;
            this.x = ThreadLocalRandom.current().nextInt(0, IMinecraft.mc.getMainWindow().getScaledWidth());
            this.y = 0.0f;
            this.size = 0.0f;
        }

        public void update() {
            this.y += 1.0f;
        }

        public void render(MatrixStack matrixStack) {
            this.size += 0.1f;
            GlStateManager.pushMatrix();
            GlStateManager.translated((double)this.x + Math.sin((float)System.nanoTime() / 1.0E9f) * 5.0, this.y, 0.0);
            GlStateManager.rotatef(this.size * 20.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translated(-((double)this.x + Math.sin((float)System.nanoTime() / 1.0E9f) * 5.0), -this.y, 0.0);
            float f = 1.0f - MathHelper.clamp(this.y / (float)IMinecraft.mc.getMainWindow().getScaledHeight(), 0.0f, 1.0f);
            this.y += 1.0f;
            Fonts.damage.drawText(matrixStack, "A", (float)((double)this.x + Math.sin((float)System.nanoTime() / 1.0E9f) * 5.0), this.y, -1, MathHelper.clamp(this.size * f, 0.0f, 9.0f));
            GlStateManager.popMatrix();
            if (this.y >= (float)IMinecraft.mc.getMainWindow().getScaledHeight()) {
                particles.remove(this);
            }
        }
    }

    private class Button {
        private final float x;
        private final float y;
        private final float width;
        private final float height;
        private String text;
        private Runnable action;
        final MainScreen this$0;

        public void render(MatrixStack matrixStack, int n, int n2, float f) {
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 15.0f, 1);
            DisplayUtils.drawShadow(this.x, this.y, this.width, this.height, 10, ColorUtils.rgba(0, 0, 10, 128));
            DisplayUtils.drawRoundedRect(this.x, this.y, this.width, this.height, new Vector4f(4.0f, 4.0f, 4.0f, 4.0f), ColorUtils.rgba(0, 0, 10, 128));
            Stencil.readStencilBuffer(1);
            Stencil.uninitStencilBuffer();
            int n3 = ColorUtils.rgb(200, 200, 200);
            if (MathUtil.isHovered(n, n2, this.x, this.y, this.width, this.height)) {
                n3 = ColorUtils.rgb(255, 255, 255);
            }
            Fonts.montserrat.drawCenteredText(matrixStack, this.text, this.x + this.width / 2.0f, this.y + this.height / 2.0f - 8.25f + 3.0f, n3, 15.0f);
            if (this.text == "Single") {
                DisplayUtils.drawImage(this.this$0.single, this.x + this.width, this.y, this.this$0.heightButton, this.this$0.heightButton, ColorUtils.rgb(255, 255, 255));
            } else if (this.text == "Servers") {
                DisplayUtils.drawImage(this.this$0.multy, this.x + this.width, this.y, this.this$0.heightButton, this.this$0.heightButton, ColorUtils.rgb(255, 255, 255));
            } else if (this.text == "Settings") {
                DisplayUtils.drawImage(this.this$0.settings, this.x + this.width, this.y, this.this$0.heightButton, this.this$0.heightButton, ColorUtils.rgb(255, 255, 255));
            } else if (this.text == "Exit") {
                DisplayUtils.drawImage(this.this$0.quit, this.x + this.width, this.y, this.this$0.heightButton, this.this$0.heightButton, ColorUtils.rgb(255, 255, 255));
            }
        }

        public void click(int n, int n2, int n3) {
            if (MathUtil.isHovered(n, n2, this.x, this.y, this.width, this.height)) {
                this.action.run();
            }
        }

        public Button(MainScreen mainScreen, float f, float f2, float f3, float f4, String string, Runnable runnable) {
            this.this$0 = mainScreen;
            this.x = f;
            this.y = f2;
            this.width = f3;
            this.height = f4;
            this.text = string;
            this.action = runnable;
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }

        public float getWidth() {
            return this.width;
        }

        public float getHeight() {
            return this.height;
        }
    }
}

