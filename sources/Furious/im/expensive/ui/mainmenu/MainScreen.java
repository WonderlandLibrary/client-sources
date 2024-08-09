package im.expensive.ui.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.Furious;
import im.expensive.functions.impl.render.HUD;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.client.Vec2i;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.KawaseBlur;
import im.expensive.utils.render.Stencil;
import im.expensive.utils.render.font.Fonts;
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
import org.lwjgl.system.CallbackI;
import ru.hogoshi.util.Easings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class MainScreen extends Screen implements IMinecraft {
    public MainScreen() {
        super(ITextComponent.getTextComponentOrEmpty(""));

    }

    private final ResourceLocation backmenu = new ResourceLocation("expensive/images/backmenu.png");
    private final List<Button> buttons = new ArrayList<>();

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        float widthButton = 350 / 2f;


        for (Particle particle : particles) {
            particle.y = ThreadLocalRandom.current().nextInt(-5, height);
        }
        float x = ClientUtil.calc(width) / 2f - widthButton / 2f;
        float y = Math.round(ClientUtil.calc(height) / 2f + 1);
        buttons.clear();

        buttons.add(new Button(x +30, y+9f, widthButton - 75, 68 - 45, "Singleplayer", () -> {
            mc.displayGuiScreen(new WorldSelectionScreen(this));
        }));
        y += 68 / 2f + 5;
        buttons.add(new Button(x +30, y -6.5f, widthButton - 75, 68 - 45, "Multiplayer", () -> {
            mc.displayGuiScreen(new MultiplayerScreen(this));
        }));
        y += 68 / 2f + 5;
        buttons.add(new Button(x + 30, y -22, widthButton - 75, 68 - 45, "Options", () -> {
            mc.displayGuiScreen(new OptionsScreen(this, mc.gameSettings));
        }));
    }

    private static final CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList<>();

    private final StopWatch stopWatch = new StopWatch();
    static boolean start = false;

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        Furious.getInstance().getAltWidget().updateScroll((int) mouseX, (int) mouseY, (float) delta);
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

        int logoWidth = 1920 / 2;
        int logoHeight = 1080 / 2;

        // Расчет координат для рисования логотипа по центру
        int xLogo = (windowWidth - logoWidth) / 2;
        int yLogo = (windowHeight - logoHeight) / 2 + 50;
        boolean small = mainWindow.getWidth() < 900 && mainWindow.getHeight() < 900;
        if (small) {
            yLogo += 50;
        }
        // Рисование логотипа по центру
        DisplayUtils.drawImage(backmenu, 0, 0, width, height, -1);
        mc.gameRenderer.setupOverlayRendering(2);
        //  DisplayUtils.drawRoundedRect(xLogo +370, yLogo +175,width -750, height -400, 11, ColorUtils.rgba(0, 0, 0, 180));
        KawaseBlur.blur.updateBlur(3, 4);
        Fonts.sfbold.drawCenteredText(matrixStack, "Furious", xLogo + 475, yLogo +210, HUD.getColor(0,1), 15F);
        Fonts.sfui.drawCenteredText(matrixStack, "made by: knowqe", xLogo + 472.5f, yLogo +305, -1, 7.5F);
        drawButtons(matrixStack, mouseX, mouseY, partialTicks);

        Furious.getInstance().getAltWidget().render(matrixStack, mouseX, mouseY);
        mc.gameRenderer.setupOverlayRendering();

    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        Furious.getInstance().getAltWidget().onChar(codePoint);
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        Furious.getInstance().getAltWidget().onKey(keyCode);
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vec2i fixed = ClientUtil.getMouse((int) mouseX, (int) mouseY);
        buttons.forEach(b -> b.click(fixed.getX(), fixed.getY(), button));
        Furious.getInstance().getAltWidget().click(fixed.getX(), fixed.getY(), button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void drawButtons(MatrixStack stack, int mX, int mY, float pt) {

        buttons.forEach(b -> b.render(stack, mX, mY, pt));
    }

    public static final ResourceLocation button = new ResourceLocation("expensive/images/button.png");

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

        public void render(MatrixStack stack, int mouseX, int mouseY, float pt) {
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(x, y + 2, width, height, 5, -1);
            Stencil.readStencilBuffer(1);
//            KawaseBlur.blur.BLURRED.draw();
            Stencil.uninitStencilBuffer();
            final ru.hogoshi.Animation animation = new ru.hogoshi.Animation();

            //1
            int color = ColorUtils.rgb(255, 255, 255);
            if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
                color = HUD.getColor(0);
            }
            //2
            int buttoncolor = ColorUtils.rgba(21, 24, 40, 255);
            if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
                buttoncolor = HUD.getColor(1, 15);
            }
            DisplayUtils.drawRoundedRect(x, y, width, height, 5, buttoncolor);
            Fonts.montserrat.drawCenteredText(stack, text, x + width / 2f, y + height / 2f - 5.5f + 2, -1, 10f);


        }

        public void click(int mouseX, int mouseY, int button) {
            if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
                action.run();
            }
        }

    }

}
