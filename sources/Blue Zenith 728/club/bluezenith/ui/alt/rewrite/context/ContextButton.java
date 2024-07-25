package club.bluezenith.ui.alt.rewrite.context;

import club.bluezenith.ui.alt.rewrite.AccountElement;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.client.TriConsumer;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class ContextButton {
    private static final FontRenderer font = FontUtil.createFont("Product Sans Regular", 35);

    private final String name;

    private final AccountElement accountElement;
    private final ContextMenu parent;

    private float alpha, hoverAlpha;
    private final float posX, posY, width, height;

    private TriConsumer<Integer, Integer, Integer> clickConsumer;

    public ContextButton(String name, ContextMenu parent, AccountElement accountElement, float posX, float posY, float width, float height) {
        this.name = name;
        this.parent = parent;
        this.accountElement = accountElement;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public ContextButton onClick(TriConsumer<Integer, Integer, Integer> clickConsumer) {
        this.clickConsumer = clickConsumer;
        return this;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void draw(int mouseX, int mouseY) {
        if(isMouseOver(mouseX, mouseY))
            hoverAlpha = RenderUtil.animate(1F, hoverAlpha, 0.15F);
        else hoverAlpha = RenderUtil.animate(0F, hoverAlpha, 0.15F);

        RenderUtil.rect(posX, posY, posX + width, posY + height, new Color(83/255F, 53/255F, 215/255F,
                parent.shouldDisappear() ? Math.min(hoverAlpha, parent.getAlpha()) : hoverAlpha));

        font.drawString(name, 2 + posX, 1 + posY, new Color(1, 1, 1, alpha).getRGB());
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, posX, posY, posX + width, posY + height);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        clickConsumer.accept(mouseX, mouseY, mouseButton);
    }
}
