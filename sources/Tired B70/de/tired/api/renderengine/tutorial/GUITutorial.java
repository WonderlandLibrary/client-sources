package de.tired.api.renderengine.tutorial;

import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.interfaces.FHook;
import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.ShaderRenderer;
import de.tired.shaderloader.list.BackGroundShader;
import net.minecraft.client.gui.GuiScreen;

public class GUITutorial extends GuiScreen {

    public TutorialState tutorialState;

    public GUITutorial() {
        tutorialState = TutorialState.STATE1;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        ShaderManager.shaderBy(BackGroundShader.class).doRender();

        final double widthA = 140;

        ShaderRenderer.startBlur();
        RenderProcessor.drawRoundedRectangle(width / 2f - widthA, height / 4f + 25, width / 2f + widthA, height / 4f + 175, 12, Integer.MAX_VALUE);
        ShaderRenderer.stopBlur();

        if (tutorialState == TutorialState.STATE1) {
            FHook.big2.drawStringWithShadow("Welcome to Tired!", width / 2f + widthA - widthA * 1.40, height / 4f + 37, -1);
        }


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();
    }


}
