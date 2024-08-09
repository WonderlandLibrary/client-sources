package ru.FecuritySQ.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;

public class AltButton extends Button {
    public AltButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction) {
        super(x, y, width, height, title, pressedAction);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawGradientRound(this.x, this.y, this.width, this.height, 5, new Color(0, 60, 178, 200).getRGB(), new Color(19, 3, 232, 200).getRGB(), new Color(3, 8, 255, 200).getRGB(), new Color(0, 123, 255, 200).getRGB());
        Fonts.MCR14.drawCenteredString(matrixStack,this.getMessage().getString(), this.x + this.width / 2, this.y + (this.height - 1) / 2, -1);
    }
}
