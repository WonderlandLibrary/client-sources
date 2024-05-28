package arsenic.gui.click;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class CustomGuiScreen extends GuiScreen {
    public float scale;
    public float curWidth = 0;
    public float curHeight = 0;

    public CustomGuiScreen() {
        this.scale = 2;
    }



    public void doInit() {}

    public void drawScr(int mouseX, int mouseY, float partialTicks) {}

    public void mouseClick(int mouseX, int mouseY, int mouseButton) {}

    public void mouseRelease(int mouseX, int mouseY, int state) {}

    @Override
    public final void initGui() {
        this.doInit();
        int sf = new ScaledResolution(mc).getScaleFactor();
        height = (int) ((height * sf) / scale);
        width = (int) ((width * sf) / scale);
        super.initGui();
    }

    @Override
    public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
        rescale(this.scale);
        curWidth = mc.displayWidth / scale;
        curHeight = mc.displayHeight / scale;

        this.drawScr(this.getRealMouseX(), this.getRealMouseY(), partialTicks);

        rescaleMC();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected final void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.mouseClick(this.getRealMouseX(), this.getRealMouseY(), mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected final void mouseReleased(int mouseX, int mouseY, int state) {
        this.mouseRelease(this.getRealMouseX(), this.getRealMouseY(), state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    public int getRealMouseX() { return (int) ((Mouse.getX() * (mc.displayWidth / scale)) / mc.displayWidth); }

    public int getRealMouseY() {
        float scaleHeight = (mc.displayHeight / scale);
        return (int) (scaleHeight - (Mouse.getY() * scaleHeight) / mc.displayHeight);
    }

    public void rescale(double factor) {
        rescale(mc.displayWidth / factor, mc.displayHeight / factor);
    }

    public void rescaleMC() {
        ScaledResolution resolution = new ScaledResolution(mc);
        rescale(mc.displayWidth / resolution.getScaleFactor(), mc.displayHeight / resolution.getScaleFactor());
    }

    public void rescale(double width, double height) {
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, width, height, 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }
}
