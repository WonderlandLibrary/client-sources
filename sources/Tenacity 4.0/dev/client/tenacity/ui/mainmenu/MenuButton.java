package dev.client.tenacity.ui.mainmenu;

import dev.client.tenacity.ui.Screen;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.EaseInOutQuad;
import dev.utils.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MenuButton implements Screen {

    public final String location, text;
    private Animation hoverAnimation;
    public float x, y, buttonWH;
    public Runnable clickAction;

    public MenuButton(String location, String text) {
        this.location = location;
        this.text = text;
    }


    @Override
    public void initGui() {
        hoverAnimation = new EaseInOutQuad(200, 1);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        boolean hovered = HoveringUtil.isHovering(x, y, buttonWH, buttonWH, mouseX, mouseY);
        hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderUtil.setAlphaLimit(0);
        mc.getTextureManager().bindTexture(new ResourceLocation(location));
        Gui.drawModalRectWithCustomSizedTexture(x, (float) (y - (15 * hoverAnimation.getOutput())), 0, 0, buttonWH, buttonWH, buttonWH, buttonWH);


        RenderUtil.setAlphaLimit(0);
        RenderUtil.resetColor();
        int i = mc.fontRendererObj.drawString(text, x + buttonWH / 2f, (float) ((y + buttonWH - 20) + (8 * hoverAnimation.getOutput())), ColorUtil.applyOpacity(-1, (float) hoverAnimation.getOutput()));

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        boolean hovered = HoveringUtil.isHovering(x, y, buttonWH, buttonWH, mouseX, mouseY);
        if(hovered) {
            clickAction.run();
        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}
