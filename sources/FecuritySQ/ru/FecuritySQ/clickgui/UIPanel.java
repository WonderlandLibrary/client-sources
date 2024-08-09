package ru.FecuritySQ.clickgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.clickgui.elements.Element;
import ru.FecuritySQ.clickgui.elements.ElementModule;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.module.дисплей.ClientOverlay;
import ru.FecuritySQ.utils.MathUtil;
import ru.FecuritySQ.utils.RandomString;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIPanel {

    public Module.Category type;

    public float x, y, width, height, tempHeight;
    public boolean visible = true, open = true;
    protected MatrixStack stack = new MatrixStack();

    public List<ElementModule> elements = new ArrayList<>();

    public UIPanel(float width, float height, Module.Category type, float x, float y) {
        this.width = width;
        this.height = height;
        this.type = type;
        this.x = x;
        this.y = y;
        loadElements();
    }

    public void draw(int mouseX, int mouseY) {
        updateElements();
        this.tempHeight = (float) MathUtil.animate(getOpenHeight(), this.tempHeight, 0.05f);
        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.scissorRect(x - 2, y - 2, x + width + 2, y + height + tempHeight + 2);
        RenderUtil.drawRound(x, y, width, height + this.tempHeight, 4, new Color(10, 10, 10, 255).getRGB());
        RandomString.alphanum.equalsIgnoreCase(getDisplay());
        Fonts.GREYCLIFF.drawCenteredString(stack, getDisplay(), x + width / 2, y + this.height / 2 - Fonts.GREYCLIFF.getFontHeight() / 2 + 4.5f, Color.LIGHT_GRAY.getRGB());
        RenderUtil.drawImage(stack, new ResourceLocation("FecuritySQ/panel/" + type.getIndex() + ".png"), x + 8, y + 5, 12, 12);
        for (ElementModule e : elements) e.draw(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MathUtil.collided(x, y, width, height, mouseX, mouseY) && mouseButton == 1) open = !open;
        for (ElementModule e : elements) if(open) e.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (ElementModule e : elements) if(open) e.mouseReleased(mouseX, mouseY, state);
    }

    public void keyTyped(int keyCode) {
        for (ElementModule e : elements) if(open) e.keypressed(keyCode);
    }

    private void loadElements(){
        for (Module m : FecuritySQ.get().getModuleList()) {
            if(m.getCategory() != type || m == null) continue;;
            elements.add(new ElementModule(m, this));
        }
    }
    private void updateElements() {
        float offset = 0;
        if(open) {
            for (ElementModule e : elements) {
                e.x = this.x;
                e.y = this.y + this.height + offset;
                e.width = this.width;
                if (e.extended) for (Element element : e.elements) if(open) offset += element.getHeight();
                offset += e.getHeight();
            }
        }
    }

    public String getDisplay(){
        String title = type.name();
        title = title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase();
        return title;
    }

    public float getOpenHeight(){
        float guiOffset = 0;
        if (open) {
            for (ElementModule e : elements) {
                if (e.extended) {
                    for (Element element : e.elements) {
                        guiOffset += element.getHeight();
                    }
                }
                guiOffset += e.getHeight();
            }
        }
        return guiOffset;
    }
}