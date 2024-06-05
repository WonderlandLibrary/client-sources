package de.tired.shaderloader;

import de.tired.interfaces.IHook;
import de.tired.shaderloader.list.BackGroundShader;
import de.tired.shaderloader.list.BlurShader;
import net.minecraft.client.gui.ScaledResolution;

public class ShaderRenderer implements IHook {


    public ShaderRenderer() {
    }

    public static void renderBG() {
        final ScaledResolution sr = new ScaledResolution(MC);
        ShaderManager.shaderBy(BackGroundShader.class).doRender();
    }

    public static void startBlur() {
        ShaderManager.shaderBy(BlurShader.class).startBlur();

    }

    public static void stopBlur() {
        ShaderManager.shaderBy(BlurShader.class).stopBlur();
    }

    public static void stopBlur(int radius) {
        ShaderManager.shaderBy(BlurShader.class).stopBlur();
    }


}
