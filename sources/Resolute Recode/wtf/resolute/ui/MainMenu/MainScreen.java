package wtf.resolute.ui.MainMenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.utiled.client.ClientUtil;
import wtf.resolute.utiled.client.IMinecraft;
import wtf.resolute.utiled.client.Vec2i;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.math.StopWatch;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.ShaderUtils;
import wtf.resolute.utiled.render.Stencil;
import wtf.resolute.utiled.render.font.Fonts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class MainScreen extends Screen implements IMinecraft {
    public MainScreen() {
        super(ITextComponent.getTextComponentOrEmpty(""));

    }
    public final ResourceLocation mainmusic = new ResourceLocation("resolute/sounds/GuiMusic.wav");
    private final ResourceLocation backmenu = new ResourceLocation("resolute/images/backmenu.png");
    private final ResourceLocation logo = new ResourceLocation("resolute/images/logo.png");
    private final List<Button> buttons = new ArrayList<>();

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        float widthButton = 83;
        float width2 = 0;

        for (Particle particle : particles) {
            particle.y = ThreadLocalRandom.current().nextInt(-5, height);
        }
        float x = (mc.getMainWindow().scaledWidth() / 2F) - (width2 / 2F);
        float y = Math.round(ClientUtil.calc(height) / 2f + 1);
        buttons.clear();
        buttons.add(new Button(width2 + 16, 120, widthButton, 25, "SinglePlayer", () -> {
            mc.displayGuiScreen(new WorldSelectionScreen(this));
        }));
        y += 68 / 2f + 5;
        buttons.add(new Button(width2 + 16, 150, widthButton, 25, "MultiPlayer", () -> {
            mc.displayGuiScreen(new MultiplayerScreen(this));
        }));
        y += 68 / 2f + 5;
        buttons.add(new Button(width2 + 16, 180, widthButton, 25, "Setting", () -> {
            mc.displayGuiScreen(new OptionsScreen(this, mc.gameSettings));
        }));
        y += 68 / 2f + 5;
        buttons.add(new Button(width2 + 16, 210, widthButton, 25, "Exit", mc::shutdownMinecraftApplet));
    }

    private static final CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList<>();

    private final StopWatch stopWatch = new StopWatch();
    static boolean start = false;

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        ResoluteInfo.getInstance().getAltWidget().updateScroll((int) mouseX, (int) mouseY, (float) delta);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (stopWatch.isReached(100)) {
            particles.add(new Particle());
            stopWatch.reset();
        }
        MainWindow mainWindow = mc.getMainWindow();
        int windowWidth = ClientUtil.calc(mainWindow.getScaledWidth());
        int windowHeight = ClientUtil.calc(mainWindow.getScaledHeight());
        boolean small = mainWindow.getWidth() < 900 && mainWindow.getHeight() < 900;
        ShaderUtils.MAIN_MENU.begin();
        ShaderUtils.MAIN_MENU.setUniform("time", mc.timer.getPartialTicks(1) / 20.0F);
        ShaderUtils.MAIN_MENU.setUniform("resolution", mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight());
        ShaderUtils.MAIN_MENU.drawQuads(0,0,0, mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight());
        ShaderUtils.MAIN_MENU.end();
        mc.gameRenderer.setupOverlayRendering(2);
        //DisplayUtils.drawImage(logo, xLogo, yLogo, logoWidth, logoHeight, -1);
        int widths = mc.getMainWindow().scaledWidth();
        int heits = mc.getMainWindow().scaledHeight();
        float width2 = 0;
        DisplayUtils.drawRoundedRect(0, 0, 115, mc.getMainWindow().scaledHeight(),0,DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 100));
        Fonts.sfbold.drawCenteredText(matrixStack, "RESOLUTE", 58,
                35, ColorUtils.rgb(161, 164, 177),
                13, 0.1f);
        int xLogo = (115 - 70) / 2;
        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        DisplayUtils.drawImage(logo,xLogo,40,70,70,ColorUtils.rgb(161, 164, 177));
        drawButtons(matrixStack, mouseX, mouseY, partialTicks);
        ResoluteInfo.getInstance().getAltWidget().render(matrixStack, mouseX, mouseY);
        mc.gameRenderer.setupOverlayRendering();
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        ResoluteInfo.getInstance().getAltWidget().onChar(codePoint);
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        ResoluteInfo.getInstance().getAltWidget().onKey(keyCode);
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vec2i fixed = ClientUtil.getMouse((int) mouseX, (int) mouseY);
        buttons.forEach(b -> b.click(fixed.getX(), fixed.getY(), button));
        ResoluteInfo.getInstance().getAltWidget().click(fixed.getX(), fixed.getY(), button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void drawButtons(MatrixStack stack, int mX, int mY, float pt) {

        buttons.forEach(b -> b.render(stack, mX, mY, pt));
    }
    private class Particle {

        private final float x;
        private float y;
        private float size;

        public Particle() {
            x = ThreadLocalRandom.current().nextInt(0, mc.getMainWindow().getScaledWidth());
            y = 0;
            size = 0;
        }

        public void update() {
            y += 1f;
        }

        public void render(MatrixStack stack) {
            //update();
            size += 0.1f;
            GlStateManager.pushMatrix();
            GlStateManager.translated((x + Math.sin((System.nanoTime() / 1000000000f)) * 5), y, 0);
            GlStateManager.rotatef(size * 20, 0, 0, 1);
            GlStateManager.translated(-(x + Math.sin((System.nanoTime() / 1000000000f)) * 5), -y, 0);
            float multi = 1 - MathHelper.clamp((y / mc.getMainWindow().getScaledHeight()), 0, 1);
            y += 1;
            Fonts.damage.drawText(stack, "A", (float) (x + Math.sin((System.nanoTime() / 1000000000f)) * 5), y, -1, MathHelper.clamp(size * multi, 0, 9));
            GlStateManager.popMatrix();
            if (y >= mc.getMainWindow().getScaledHeight()) {
                particles.remove(this);
            }
        }

    }

    @AllArgsConstructor
    private class Button {
        @Getter
        private final float x, y, width, height;
        private String text;
        private Runnable action;
        private final ResourceLocation singleplayer = new ResourceLocation("resolute/images/singleplayer.png");
        public void render(MatrixStack stack, int mouseX, int mouseY, float pt) {
            float widthButton = 80;
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(x, y + 2, width, height, 5, -1);
            Stencil.readStencilBuffer(1);
            Stencil.uninitStencilBuffer();
            int colors = new Color(0, 0, 0).getRGB();
            if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
                colors = ColorUtils.rgb(161, 164, 177);
            }
            int firstColor = ColorUtils.getColorStyle(0);
            int secondColor = ColorUtils.getColorStyle(100);
            DisplayUtils.drawShadow(x, y + 2, width, height, 8, colors,colors);
            DisplayUtils.drawRoundedRect(x, y + 2, width, height,3, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
            int color = ColorUtils.rgb(161, 164, 177);
            if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
                color = ColorUtils.rgb(255, 255, 255);
            }
            Fonts.sfMedium.drawCenteredText(stack, text, x + width / 2f, y + height / 2f - 5.5f + 2, color, 9f);


        }

        public void click(int mouseX, int mouseY, int button) {
            if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
                action.run();
            }
        }

    }

}
