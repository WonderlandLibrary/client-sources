package net.silentclient.client.utils.shader;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.mods.render.NewMotionBlurMod;
import net.silentclient.client.mods.util.IMixinShaderGroup;

public class MotionBlurUtils {
	public static final MotionBlurUtils instance = new MotionBlurUtils();

    private static final ResourceLocation location = new ResourceLocation("shaders/post/sc_motionblur2.json");
    private static final Logger logger = LogManager.getLogger();

    private Minecraft mc = Minecraft.getMinecraft();
    private ShaderGroup shader;
    private float shaderBlur;
    private int cachedWidth = 0;
    private int cachedHeight = 0;
    
    public float getBlurFactor() {
        return (float) Client.getInstance().getSettingsManager().getSettingByClass(NewMotionBlurMod.class, "Amount").getValDouble();
    }

    public ShaderGroup getShader() {

        if (shader == null || cachedWidth != mc.displayWidth || cachedHeight != mc.displayHeight) {
        	shader = null;
            shaderBlur = Float.NaN;

            try {
                shader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), location);
                shader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
                cachedWidth = mc.displayWidth;
                cachedHeight = mc.displayHeight;
            } catch (JsonSyntaxException | IOException error) {
            	logger.error("Could not load motion blur shader", error);
                return null;
            }
        }
        
        if (shaderBlur != getBlurFactor()) {
            ((IMixinShaderGroup)shader).getListShaders().forEach((shader) -> {
                ShaderUniform blendFactorUniform = shader.getShaderManager().getShaderUniform("BlurFactor");

                if (blendFactorUniform != null) {
                    blendFactorUniform.set(getBlurFactor());
                }
            });

            shaderBlur = getBlurFactor();
        }

        return shader;
    }

}
