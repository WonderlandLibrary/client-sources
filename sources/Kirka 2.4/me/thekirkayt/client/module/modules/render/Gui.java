/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import me.thekirkayt.client.gui.click.ClickGui;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@Module.Mod(displayName="Click Gui", keybind=54, shown=false)
public class Gui
extends Module {
    private boolean darkTheme;
    @Option.Op(name="Rainbow")
    public static boolean rainbow = true;
    @Option.Op(name="Blur")
    public static boolean blur = false;

    @Override
    public void enable() {
        ClientUtils.mc().displayGuiScreen(ClickGui.getInstance());
        if (blur && OpenGlHelper.shadersSupported && ClientUtils.mc().func_175606_aa() instanceof EntityPlayer) {
            if (ClientUtils.mc().entityRenderer.theShaderGroup != null) {
                ClientUtils.mc().entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            ClientUtils.mc().entityRenderer.shaderIndex = 18;
            if (ClientUtils.mc().entityRenderer.shaderIndex != EntityRenderer.shaderCount) {
                ClientUtils.mc().entityRenderer.func_175069_a(EntityRenderer.shaderResourceLocations[18]);
            } else {
                ClientUtils.mc().entityRenderer.theShaderGroup = null;
            }
        }
    }

    public boolean isDarkTheme() {
        return this.darkTheme;
    }
}

