package ru.FecuritySQ.clickgui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.clickgui.elements.ElementPanel;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.utils.MathUtil;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HUB extends UIPanel {

    public float openHeightPanel;
    public List<ElementPanel> panels = new ArrayList<>();
    public HUB(float width, float height, float x, float y) {
        super(width, height, Module.Category.Центр, x, y);
        init();
    }

    void init(){
        for(UIPanel p : FecuritySQ.get().getClickGui().panels) {
            if (p.type != Module.Category.Центр) panels.add(new ElementPanel(p, p.width, 16));
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        RenderUtil.scissorRect(x - 2, y - 2, x + width + 2, y + height + openHeightPanel + 2);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.drawRound(x, y, width, height + openHeightPanel, 4, new Color(10, 10, 10, 220).getRGB());
        Fonts.GREYCLIFF.drawCenteredString(stack, type.name(), x + width / 2, y + this.height / 2+1 - Fonts.GREYCLIFF.getFontHeight() / 2, Color.LIGHT_GRAY.getRGB());
        RenderUtil.drawImage(stack, new ResourceLocation("FecuritySQ/panel/" + this.type.getIndex() + ".png"), x + 8, y + 5, 12, 12);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for(ElementPanel p : panels) if(open)  p.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
