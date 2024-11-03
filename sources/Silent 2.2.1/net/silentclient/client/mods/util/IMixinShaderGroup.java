package net.silentclient.client.mods.util;

import net.minecraft.client.shader.Shader;

import java.util.List;

public interface IMixinShaderGroup {
	List<Shader> getListShaders();
	Object getMainFramebuffer();
}
