package de.tired.api.guis.clickgui.impl.extensions;

import de.tired.api.guis.clickgui.impl.clickable.Clickable;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.render.Scissoring;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.list.GradientShader;
import de.tired.tired.Tired;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class Panel {

    private final ModuleCategory moduleCategory;

    private int x, y, dragX, dragY;

    private boolean dragging, extended;

    public float animation = 0;

    public int mouseX, mouseY;

    private final ArrayList<ModuleRendering> moduleRenderingArrayList;

    public ArrayList<Module> modules = new ArrayList<>();

    public Panel(int x, int y, ModuleCategory moduleCategory) {

        this.x = x;
        this.y = y;
        this.moduleCategory = moduleCategory;
        this.moduleRenderingArrayList = new ArrayList<>();

        for (Module module : Tired.INSTANCE.moduleManager.getModuleList()) {
            if (module.getModuleCategory() != this.moduleCategory)
                continue;

            moduleRenderingArrayList.add(new ModuleRendering(module));

            modules.add(module);

        }

        moduleRenderingArrayList.sort((m1, m2) -> FontManager.IBMPlexSans.getStringWidth(m2.module.clickGUIText) - FontManager.IBMPlexSans.getStringWidth(m1.module.clickGUIText));


    }

    public void drawPanel(int mouseX, int mouseY, boolean rectangle) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        if (dragging && ModuleRendering.allowInteraction) {
            this.x = mouseX + dragX;
            this.y = mouseY + dragY;
        }

        final String categoryText = moduleCategory.displayName;



        if (animation != 3) {
            int yAdditional2 = (int) animation - 20;
            for (ModuleRendering render : moduleRenderingArrayList) {
                render.renderLayer1(mouseX, mouseY, x, y + 20 + yAdditional2, true);
                yAdditional2 += animation;
            }
            int yAdditiona3 = (int) animation - 20;
            ShaderManager.shaderBy(GradientShader.class).renderShader();
            for (ModuleRendering render : moduleRenderingArrayList) {
                render.renderEnableLayer(mouseX, mouseY, x, y + 20 + yAdditiona3);
                yAdditiona3 += animation;
            }
            ShaderManager.shaderBy(GradientShader.class).stopRender();
            int yAdditional = (int) animation - 20;
            for (ModuleRendering render : moduleRenderingArrayList) {
                render.renderTextLayer(mouseX, mouseY, x, y + 20 + yAdditional);
                yAdditional += animation;
            }
        }


        Gui.drawRect(x, y + 2, x + Clickable.getWidth(), y + Clickable.getHeight(), new Color(22, 23, 25).getRGB());

        double state = extended ? Math.round(Clickable.height) : 3;

        animation = (float) AnimationUtil.getAnimationState(animation, state,144);

        ShaderManager.shaderBy(GradientShader.class).renderShader();

        FontManager.IBMPlexSans.drawString(categoryText, Clickable.calculateMiddle(categoryText, FontManager.IBMPlexSans, x, Clickable.getWidth()), y + 7, -1);
        ShaderManager.shaderBy(GradientShader.class).stopRender();




    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean mouseOver = (Clickable.isOver(x, y, (int) Clickable.getWidth(), (int) Clickable.getHeight(), mouseX, mouseY));

        if (animation != 3) {
            this.moduleRenderingArrayList.forEach(moduleButton -> moduleButton.mouseClicked(mouseButton));
        }
        if (mouseOver && ModuleRendering.allowInteraction) {
            if (mouseButton == 0) {
                this.dragX = x - mouseX;
                this.dragY = y - mouseY;
                this.dragging = true;
            } else if (mouseButton == 1) {
                this.extended = !extended;
            }

        }
    }

    public void mouseReleased() {
        this.dragging = false;
    }


}
