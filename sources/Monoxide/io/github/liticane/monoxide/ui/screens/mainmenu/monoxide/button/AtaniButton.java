package io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.button;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import io.github.liticane.monoxide.module.impl.hud.PostProcessingModule;
import io.github.liticane.monoxide.util.render.RenderUtil;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import io.github.liticane.monoxide.util.render.shader.render.ingame.RenderableShaders;
import io.github.liticane.monoxide.util.render.shader.shaders.RoundedShader;

import java.awt.*;

public class AtaniButton extends GuiButton
{

    private DecelerateAnimation decelerateAnimation = new DecelerateAnimation(200, 1, Direction.BACKWARDS);

    public AtaniButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public AtaniButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible)
        {
            if(RenderUtil.isHovered(mouseX, mouseY, xPosition, yPosition, width, height))
                this.decelerateAnimation.setDirection(Direction.FORWARDS);
            else
                this.decelerateAnimation.setDirection(Direction.BACKWARDS);
            RenderableShaders.render(true, true, () -> {
                RoundedShader.drawRound(this.xPosition, this.yPosition, this.width, this.height, 5, new Color(0, 0, 0, 205 + (int) (this.decelerateAnimation.getOutput() * 50)));
            });
            if(!PostProcessingModule.getInstance().isEnabled() || !PostProcessingModule.getInstance().bloom.getValue())
                RoundedShader.drawRound(this.xPosition, this.yPosition, this.width, this.height, 5, new Color(0, 0, 0, 50));
            FontRenderer fontRenderer = FontStorage.getInstance().findFont("SF UI", 19);
            fontRenderer.drawTotalCenteredString(displayString, this.xPosition + this.width / 2, this.yPosition + this.height / 2, -1);
        }
    }

}
