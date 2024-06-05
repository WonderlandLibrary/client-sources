package me.dinozoid.strife.ui.menu;

import me.dinozoid.strife.shader.Shader;
import me.dinozoid.strife.shader.implementations.MenuShader;
import me.dinozoid.strife.ui.element.StrifeTextField;
import me.dinozoid.strife.util.render.RenderUtil;
import me.dinozoid.strife.util.ui.ZoomUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class StrifeMainMenu extends GuiScreen {


    private ResourceLocation strifeLogo, singleplayerButton, multiplayerButton, altsButton, settingsButton, quitButton;
    private ZoomUtil strifeLogoZoom, singleplayerZoom, multiplayerZoom, altsZoom, settingsZoom, quitZoom;

    private Shader shader;

    private float zoomValue = 0.298f;

    public StrifeMainMenu() {
        this(0);
    }

    private StrifeAccountManager strifeAccountManager;

    public StrifeMainMenu(int pass) {
        strifeLogo = new ResourceLocation("strife/gui/Strife-128x.png");
        singleplayerButton = new ResourceLocation("strife/gui/mainmenu/singleplayer.png");
        multiplayerButton = new ResourceLocation("strife/gui/mainmenu/multiplayer.png");
        altsButton = new ResourceLocation("strife/gui/mainmenu/alts.png");
        settingsButton = new ResourceLocation("strife/gui/mainmenu/settings.png");
        quitButton = new ResourceLocation("strife/gui/mainmenu/quit.png");
        shader = new MenuShader(pass);
    }

    @Override
    public void initGui() {
        ScaledResolution sc = new ScaledResolution(mc);
        strifeLogoZoom = new ZoomUtil(sc.getScaledWidth() / 2 - 32, sc.getScaledHeight() / 2 - 75, 64, 64, 12, zoomValue, 6);
        singleplayerZoom = new ZoomUtil(sc.getScaledWidth() / 2 - 12 - 64, sc.getScaledHeight() / 2, 24, 24, 12, zoomValue, 6);
        multiplayerZoom = new ZoomUtil(sc.getScaledWidth() / 2 - 12 - 32, sc.getScaledHeight() / 2, 24, 24, 12, zoomValue, 6);
        altsZoom = new ZoomUtil(sc.getScaledWidth() / 2 - 12, sc.getScaledHeight() / 2, 24, 24, 12, zoomValue, 6);
        settingsZoom = new ZoomUtil(sc.getScaledWidth() / 2 - 12 + 32, sc.getScaledHeight() / 2, 24, 24, 12, zoomValue, 6);
        quitZoom = new ZoomUtil(sc.getScaledWidth() / 2 - 12 + 64, sc.getScaledHeight() / 2, 24, 24, 12, zoomValue, 6);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sc = new ScaledResolution(mc);
        shader.render(sc.getScaledWidth(), sc.getScaledHeight());
        strifeLogoZoom.update(mouseX, mouseY);
        singleplayerZoom.update(mouseX, mouseY);
        multiplayerZoom.update(mouseX, mouseY);
        altsZoom.update(mouseX, mouseY);
        settingsZoom.update(mouseX, mouseY);
        quitZoom.update(mouseX, mouseY);
        RenderUtil.drawImage(strifeLogo, strifeLogoZoom.getX(), strifeLogoZoom.getY(), strifeLogoZoom.getWidth(), strifeLogoZoom.getHeight(), 255);
        RenderUtil.drawImage(singleplayerButton, singleplayerZoom.getX(), singleplayerZoom.getY(), singleplayerZoom.getWidth(), singleplayerZoom.getHeight(), 255);
        RenderUtil.drawImage(multiplayerButton, multiplayerZoom.getX(), multiplayerZoom.getY(), multiplayerZoom.getWidth(), multiplayerZoom.getHeight(), 255);
        RenderUtil.drawImage(altsButton, altsZoom.getX(), altsZoom.getY(), altsZoom.getWidth(), altsZoom.getHeight(), 255);
        RenderUtil.drawImage(settingsButton, settingsZoom.getX(), settingsZoom.getY(), settingsZoom.getWidth(), settingsZoom.getHeight(), 255);
        RenderUtil.drawImage(quitButton, quitZoom.getX(), quitZoom.getY(), quitZoom.getWidth(), quitZoom.getHeight(), 255);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (RenderUtil.isHovered(singleplayerZoom.getX(), singleplayerZoom.getY(), singleplayerZoom.getWidth(), singleplayerZoom.getHeight(), mouseX, mouseY)) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (RenderUtil.isHovered(multiplayerZoom.getX(), multiplayerZoom.getY(), multiplayerZoom.getWidth(), multiplayerZoom.getHeight(), mouseX, mouseY)) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (RenderUtil.isHovered(altsZoom.getX(), altsZoom.getY(), altsZoom.getWidth(), altsZoom.getHeight(), mouseX, mouseY)) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            if (strifeAccountManager == null) strifeAccountManager = new StrifeAccountManager(this, shader.pass());
            mc.displayGuiScreen(strifeAccountManager);
        }
        if (RenderUtil.isHovered(settingsZoom.getX(), settingsZoom.getY(), settingsZoom.getWidth(), settingsZoom.getHeight(), mouseX, mouseY)) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }
        if (RenderUtil.isHovered(quitZoom.getX(), quitZoom.getY(), quitZoom.getWidth(), quitZoom.getHeight(), mouseX, mouseY)) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            mc.shutdown();
        }
    }

    public void pass(int pass) {
        shader.pass(pass);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
