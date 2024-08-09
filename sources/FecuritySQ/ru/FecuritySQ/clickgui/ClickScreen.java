package ru.FecuritySQ.clickgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.shader.GaussianBlur;
import ru.FecuritySQ.shader.GrayShader;
import ru.FecuritySQ.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClickScreen extends Screen {

    protected Minecraft mc = Minecraft.getInstance();
    public CopyOnWriteArrayList<UIPanel> panels = new CopyOnWriteArrayList<>();

    public float scroll = 0;
    public float scrollAnimation = 0;


    public ClickScreen() {
        super(new StringTextComponent(""));
    }

    public void loadPanels() {
        int x = 10;
        for (Module.Category type : Module.Category.values()) {
           if(type == Module.Category.Центр); else
            panels.add(new UIPanel(100,22, type, 20 + x, 20));
            x += 115;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        GrayShader.renderGray();
        GaussianBlur.renderBlur(10);
        Fonts.dreamspace32.drawCenteredStringWithShadow(matrixStack, "Created by Tef при поддержке: haslov, Vkarik, Zаюша, Shaori", 450, 350, -1);
        panelsPosition();
        GlStateManager.pushMatrix();
        panels.stream().filter(p -> p.visible).forEach(panel -> panel.draw(mouseX, mouseY));
        GlStateManager.popMatrix();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0) {
            this.scroll -= 12f;
        }
        else if (delta < 0) {
            this.scroll += 12f;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        panels.stream().filter(p -> p.visible).forEach(panel -> panel.mouseClicked((int) mouseX, (int) mouseY, button));
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        panels.stream().filter(p -> p.visible).forEach(panel -> panel.mouseReleased((int) mouseX, (int) mouseY, button));
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        panels.stream().filter(p -> p.visible).forEach(panel -> panel.keyTyped(keyCode));
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void panelsPosition(){
        int scaleX = mc.getMainWindow().getScaledWidth() / 2 + 2;
        int offset = 0;

        this.scrollAnimation = MathUtil.animation(this.scrollAnimation, this.scroll, 2.5f);

        for(UIPanel p : panels){
            if(!p.visible) continue;
            p.x = (scaleX + offset);
            p.y = 100 + scrollAnimation;
            offset+= p.width + 4;
        }
        for(UIPanel p : panels) {
            if(!p.visible) continue;
            p.x = p.x - offset / 2;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
