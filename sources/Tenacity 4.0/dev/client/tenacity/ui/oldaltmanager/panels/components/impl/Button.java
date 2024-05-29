package dev.client.tenacity.ui.oldaltmanager.panels.components.impl;

import dev.client.tenacity.ui.oldaltmanager.backend.Alt;
import dev.client.tenacity.ui.oldaltmanager.backend.AltManagerUtils;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.ui.oldaltmanager.panels.components.Component;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Button extends Component {
    private final String text;
    private final boolean center;
    public float width, height;
    public int textColor = -1;
    public Animation alternateAnimation;
    private Animation hoverAnimation;

    public Button(String text, float width, float height, float largerRectWidth) {
        this.text = text;
        this.width = width;
        this.height = height;
        center = true;
    }

    public Button(String text, float width, float height) {
        this.text = text;
        this.width = width;
        this.height = height;
        center = false;
    }

    @Override
    public void initGui() {
        hoverAnimation = new DecelerateAnimation(200, 1);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, Animation initAnimation) {
        Animation moveAnimation = initAnimation;
        if (alternateAnimation != null) moveAnimation = alternateAnimation;
        float xVal = (float) ((x - (width + 50)) + ((width + 50) * moveAnimation.getOutput()));
        boolean hover = HoveringUtil.isHovering(xVal, y, width, height, mouseX, mouseY);
        hoverAnimation.setDirection(hover ? Direction.FORWARDS : Direction.BACKWARDS);
        Gui.drawRect2(xVal, y, width, height, interpolateRectColor(hoverAnimation));
        float textX = xVal + width / 2f;
        if (text.equals("method")) {
            float iconWidth = 47 / 2f;
            float iconHeight = 46 / 2f;
            xVal += 0.25f;
            if (Alt.currentLoginMethod.equals(Alt.AltType.MOJANG)) {
                GL11.glEnable(GL11.GL_BLEND);
                mc.getTextureManager().bindTexture(new ResourceLocation("Tenacity/mojang.png"));
                GlStateManager.color(1, 1, 1, 1);
                Gui.drawModalRectWithCustomSizedTexture(xVal + width / 2f - iconWidth / 2f, y + height / 2f - iconHeight / 2f, 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);
            } else {
                float xRect = xVal + width / 2f - iconWidth / 2f;
                float yRect = y + height / 2f - iconHeight / 2f + 0.25f;
                Gui.drawRect2(xRect, yRect, 10, 10, new Color(243, 83, 37).getRGB());
                Gui.drawRect2(xRect + 12, yRect, 10, 10, new Color(129, 188, 6).getRGB());
                Gui.drawRect2(xRect, yRect + 12, 10, 10, new Color(5, 166, 240).getRGB());
                Gui.drawRect2(xRect + 12, yRect + 12, 10, 10, new Color(255, 186, 8).getRGB());
            }
        } else {
            FontUtil.tenacityBoldFont26.drawCenteredString(text, textX, y + ((height / 2f) - (FontUtil.tenacityBoldFont26.getHeight() / 2f)), textColor);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, AltManagerUtils altManagerUtils) {
        hovering = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
    }

}
