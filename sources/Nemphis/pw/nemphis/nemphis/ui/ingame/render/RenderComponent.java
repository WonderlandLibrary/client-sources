/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.ui.ingame.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import pw.vertexcode.util.lwjgl.LWJGLUtil;

public class RenderComponent {
    public ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    public FontRenderer fr;
    public Minecraft mc;
    public LWJGLUtil lwjglUtil;

    public RenderComponent() {
        this.fr = Minecraft.getMinecraft().fontRendererObj;
        this.mc = Minecraft.getMinecraft();
        this.lwjglUtil = new LWJGLUtil();
    }

    public void render() {
    }
}

