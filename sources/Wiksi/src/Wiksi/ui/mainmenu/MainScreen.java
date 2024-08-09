/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package src.Wiksi.ui.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.Wiksi;
import src.Wiksi.functions.impl.render.HUD;
import src.Wiksi.utils.client.ClientUtil;
import src.Wiksi.utils.client.IMinecraft;
import src.Wiksi.utils.client.Vec2i;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.math.StopWatch;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.KawaseBlur;
import src.Wiksi.utils.render.Stencil;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.shader.ShaderUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
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
import src.Wiksi.utils.text.GradientUtil;

import static src.Wiksi.utils.render.DisplayUtils.drawQuads;

public class MainScreen
        extends Screen
        implements IMinecraft {
    public static ShaderUtil mainmenu = new ShaderUtil("MainMenu");
    private boolean showWelcomeText = true;
    private long welcomeTextStartTime;
    private boolean firstRender = true;
    private final ResourceLocation sun = new ResourceLocation("Wiksi/images/sun.png");
    private final ResourceLocation logo = new ResourceLocation("Wiksi/images/logo.png");
    private final ResourceLocation backmenu = new ResourceLocation("Wiksi/images/backmenu.png");
    private String altName = "";
    private boolean typing;
    private final List<Button> buttons = new ArrayList<Button>();
    private static final CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList();
    public final StopWatch timer = new StopWatch();
    public float o = 0;
    static boolean start = false;
    public static final ResourceLocation button = new ResourceLocation("Wiksi/images/button.png");

    public MainScreen() {
        super(ITextComponent.getTextComponentOrEmpty(""));
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        float widthButton = 150.0f;
        if (this.firstRender) {
            this.showWelcomeText = true;
            this.welcomeTextStartTime = System.currentTimeMillis();
            this.firstRender = false;
        }
        float x = (float)ClientUtil.calc(width) / 2.0f - widthButton / 2.0f;
        float y = Math.round((float)ClientUtil.calc(height) / 2.0f + 1.0f);
        this.buttons.clear();
        this.buttons.add(new Button(x - 27.0f, y - 10.0f, widthButton - 107.0f, 41.0f, "", () -> mc.displayGuiScreen(new WorldSelectionScreen(this))));
        this.buttons.add(new Button(x + 20.0f, (y += 39.0f) - 49.0f, widthButton - 107.0f, 41.0f, "", () -> mc.displayGuiScreen(new MultiplayerScreen(this))));
        this.buttons.add(new Button(x + 68.0f, (y += 39.0f) - 88.0f, widthButton - 107.0f, 41.0f, "", () -> mc.displayGuiScreen(new OptionsScreen(this, MainScreen.mc.gameSettings))));
        this.buttons.add(new Button(x + 115.0f, (y += 39.0f) - 127.0f, widthButton - 107.0f, 41.0f, "", mc::shutdownMinecraftApplet));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        Wiksi.getInstance().getAltWidget().updateScroll((int) mouseX, (int) mouseY, (float) delta);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        boolean small;
        MainWindow mainWindow = mc.getMainWindow();
        int windowWidth = ClientUtil.calc(mainWindow.getScaledWidth());
        int windowHeight = ClientUtil.calc(mainWindow.getScaledHeight());

        DisplayUtils.drawImage(this.backmenu, 0.0F, 0.0F, (float)this.width, (float)this.height, -1);
        mc.gameRenderer.setupOverlayRendering(2);
        KawaseBlur.blur.updateBlur(1.0F, 1);
        this.drawButtons(stack, mouseX, mouseY, partialTicks);
        Wiksi.getInstance().getAltWidget().render(stack, mouseX, mouseY);
        mc.gameRenderer.setupOverlayRendering();

        int logoWidth = 100;
        int logoHeight = 100;
        int xLogo = (windowWidth - logoWidth) / 2;
        int yLogo = (windowHeight - logoHeight) / 2;
        boolean bl = small = mainWindow.getWidth() < 900 && mainWindow.getHeight() < 900;
        if (small) {
            yLogo += 0;
        }
        DisplayUtils.drawRoundedRect((float)(xLogo - 50), (float)(yLogo + 45), (float)(logoWidth - 59), (float)(logoHeight - 60), new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), new Vector4i(ColorUtils.getColor(0), ColorUtils.getColor(90), ColorUtils.getColor(180), ColorUtils.getColor(270)));
        ResourceLocation logo = new ResourceLocation("Wiksi/images/single.png");
        DisplayUtils.drawImage(logo, (float)(xLogo - 45), (float)(yLogo + 50), 32.0f, 32.0f, -1);
        DisplayUtils.drawRoundedRect((float)(xLogo - 4), (float)(yLogo + 45), (float)(logoWidth - 59), (float)(logoHeight - 60), new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), new Vector4i(ColorUtils.getColor(0), ColorUtils.getColor(90), ColorUtils.getColor(180), ColorUtils.getColor(270)));
        ResourceLocation logo1 = new ResourceLocation("Wiksi/images/multi.png");
        DisplayUtils.drawImage(logo1, (float)xLogo, (float)(yLogo + 50), 32.0f, 32.0f, -1);
        DisplayUtils.drawRoundedRect((float)(xLogo + 44), (float)(yLogo + 45), (float)(logoWidth - 59), (float)(logoHeight - 60), new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), new Vector4i(ColorUtils.getColor(0), ColorUtils.getColor(90), ColorUtils.getColor(180), ColorUtils.getColor(270)));
        ResourceLocation logo2 = new ResourceLocation("Wiksi/images/setting.png");
        DisplayUtils.drawImage(logo2, (float)(xLogo + 48), (float)(yLogo + 50), 32.0f, 32.0f, -1);
        DisplayUtils.drawRoundedRect((float)(xLogo + 90), (float)(yLogo + 45), (float)(logoWidth - 59), (float)(logoHeight - 60), new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), new Vector4i(ColorUtils.getColor(0), ColorUtils.getColor(90), ColorUtils.getColor(180), ColorUtils.getColor(270)));
        ResourceLocation logo3 = new ResourceLocation("Wiksi/images/exit.png");
        DisplayUtils.drawImage(logo3, (float)(xLogo + 100), (float)(yLogo + 50), 32.0f, 32.0f, -1);
        DisplayUtils.drawRoundedRect(360.0F, 260.0F, 240.0f, 70.0f, 10.0f, ColorUtils.rgba(0, 0, 0, 1900));
        Fonts.sfbold.drawText(stack, GradientUtil.gradient("Wiksi client"), 365.0F, 130.0F, 30.0F, HUD.getColor(0));
        Fonts.sfbold.drawText(stack, GradientUtil.gradient("Recode 1.16.5"), 413.0F, 170.0F, 15.0F, HUD.getColor(0));
        Fonts.sfbold.drawText(stack, "Сделано с любовью", 430.0F, 485.0F, ColorUtils.rgba(255, 255, 255, 255), 8.0f);
        Fonts.sfMedium.drawText(stack, "разработчиком d3f4ult", 425.0F, 495.0F, ColorUtils.rgba(255, 255, 255, 255), 8.0f);
        MainScreen.mc.gameRenderer.setupOverlayRendering(2);
        KawaseBlur.blur.updateBlur(3.0f, 4);
        this.drawButtons(stack, mouseX, mouseY, partialTicks);
        MainScreen.mc.gameRenderer.setupOverlayRendering(2);

        Wiksi.getInstance().getAltWidget().render(stack, mouseX, mouseY);
        mc.gameRenderer.setupOverlayRendering();

}

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        Wiksi.getInstance().getAltWidget().onChar(codePoint);
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        Wiksi.getInstance().getAltWidget().onKey(keyCode);
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vec2i fixed = ClientUtil.getMouse((int) mouseX, (int) mouseY);
        buttons.forEach(b -> b.click(fixed.getX(), fixed.getY(), button));
        Wiksi.getInstance().getAltWidget().click(fixed.getX(), fixed.getY(), button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void drawButtons(MatrixStack stack, int mX, int mY, float pt) {
        this.buttons.forEach(b -> b.render(stack, mX, mY, pt));
    }

    private class Button {
        private final float x;
        private final float y;
        private final float width;
        private final float height;
        private String text;
        private Runnable action;

        public void render(MatrixStack stack, int mouseX, int mouseY, float pt) {
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(this.x, this.y + 2.0f, this.width, this.height, 5.0f, ColorUtils.rgba(0, 0, 0, 10));
            DisplayUtils.drawRoundedRect(this.x, this.y, this.width, this.height, new Vector4f(4.0f, 4.0f, 4.0f, 4.0f), ColorUtils.rgba(10, 10, 10, 250));
            Stencil.readStencilBuffer(1);
            Stencil.uninitStencilBuffer();
            int color = ColorUtils.rgba(0, 0, 0, 255);
            if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height)) {
                color = ColorUtils.rgba(255, 255, 255, 240);
            }
            Fonts.sfMedium.drawGradientText(stack, this.text, this.x + this.width / 2.0f - 33.0f, this.y + this.height / 2.0f - 7.5f + 4.0f, color, color, 12, 0.1f);
        }

        public void click(int mouseX, int mouseY, int button) {
            if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height)) {
                this.action.run();
            }
        }

        public Button(float x, float y, float width, float height, String text, Runnable action) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.text = text;
            this.action = action;
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

    private class Particle {
        private final float x = ThreadLocalRandom.current().nextInt(0, IMinecraft.mc.getMainWindow().getScaledWidth());
        private float y = 0.0f;
        private float size = 0.0f;

        public void update() {
            this.y += 1.0f;
        }

        public void render(MatrixStack stack) {
            this.size += 0.1f;
            GlStateManager.pushMatrix();
            GlStateManager.translated((double)this.x + Math.sin((float)System.nanoTime() / 1.0E9f) * 5.0, this.y, 0.0);
            GlStateManager.rotatef(this.size * 20.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translated(-((double)this.x + Math.sin((float)System.nanoTime() / 1.0E9f) * 5.0), -this.y, 0.0);
            float multi = 1.0f - MathHelper.clamp(this.y / (float)IMinecraft.mc.getMainWindow().getScaledHeight(), 0.0f, 1.0f);
            this.y += 1.0f;
            Fonts.damage.drawText(stack, "A", (float)((double)this.x + Math.sin((float)System.nanoTime() / 1.0E9f) * 5.0), this.y, -1, MathHelper.clamp(this.size * multi, 0.0f, 9.0f));
            GlStateManager.popMatrix();
            if (this.y >= (float)IMinecraft.mc.getMainWindow().getScaledHeight()) {
                particles.remove(this);
            }
        }
    }
}

