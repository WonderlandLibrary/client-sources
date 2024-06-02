/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.mojang.authlib.GameProfile;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventEntityRender;
import cafe.kagu.kagu.eventBus.impl.EventRender3D;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.utils.Shader;
import cafe.kagu.kagu.utils.SpoofUtils;
import cafe.kagu.kagu.utils.Shader.ShaderType;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author lavaflowglow
 *
 */
public class ModViewModels extends Module {
	
	public ModViewModels() {
		super("ViewModel", Category.VISUAL);
		setSettings(overrideF3, desyncModels, desyncViewModelRed, desyncViewModelGreen, desyncViewModelBlue,
				desyncViewModelAlpha);
		
		// Load the shaders
		try {
			desyncModelShader = new Shader(ShaderType.FRAGMENT, FileManager.readStringFromFile(FileManager.COLOR_SHADER));
			desyncModelShader.create();
			desyncModelShader.link();
			desyncModelShader.createUniform("rgba");
		} catch (Exception e) {
			logger.error("Failed to load the color shader, this may cause issues", e);
		}
		
	}
	
	private BooleanSetting overrideF3 = new BooleanSetting("Override F3", true);
	private BooleanSetting desyncModels = new BooleanSetting("Desync Model", false);
	
	// Desync model colors
	private IntegerSetting desyncViewModelRed = (IntegerSetting) new IntegerSetting("Desync Red", 255, 0, 255, 1).setDependency(desyncModels::isEnabled);
	private IntegerSetting desyncViewModelGreen = (IntegerSetting) new IntegerSetting("Desync Green", 255, 0, 255, 1).setDependency(desyncModels::isEnabled);
	private IntegerSetting desyncViewModelBlue = (IntegerSetting) new IntegerSetting("Desync Blue", 255, 0, 255, 1).setDependency(desyncModels::isEnabled);
	private IntegerSetting desyncViewModelAlpha = (IntegerSetting) new IntegerSetting("Desync Alpha", 70, 0, 255, 1).setDependency(desyncModels::isEnabled);
	
	private boolean renderingDesync = false;
	private Shader desyncModelShader;
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * @return the renderingDesync
	 */
	public boolean isRenderingDesync() {
		return renderingDesync;
	}
	
	@EventHandler
	private Handler<EventEntityRender> onRenderEntity = e -> {
		
		if (e.isPre() || e.getEntity() != mc.thePlayer)
			return;
		
		if (desyncModels.isEnabled() && !renderingDesync) {
			
			if (mc.gameSettings.thirdPersonView == 0 || (!SpoofUtils.isSpoofYaw() && !SpoofUtils.isSpoofPitch())) {
				return;
			}
			
			GlStateManager.pushMatrix();
			GlStateManager.pushAttrib();
			GlStateManager.enableBlend();
			GlStateManager.enableAlpha();
			GL11.glEnable(GL11.GL_BLEND);
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			GlStateManager.disableDepth();
			if (Kagu.getModuleManager().getModule(ModEsp.class).isEnabled() && Kagu.getModuleManager().getModule(ModEsp.class).getChams().isEnabled()) {
				GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
				GL11.glPolygonOffset(1.0f, -1099998.0f);
			}
			RenderPlayer renderDesync = new RenderPlayer(mc.getRenderManager());
			renderingDesync = true;
			
			desyncModelShader.bind();
			GL20.glUniform4f(desyncModelShader.getUniform("rgba"), desyncViewModelRed.getValue() / 255f, desyncViewModelGreen.getValue() / 255f, desyncViewModelBlue.getValue() / 255f, desyncViewModelAlpha.getValue() / 255f);
			renderDesync.doRender(mc.thePlayer, 0, 0, 0, 0, e.getPartialTicks());
			desyncModelShader.unbind();
			
			renderingDesync = false;
			{
				GlStateManager.disableBlend();
				GL11.glDisable(GL11.GL_BLEND);
				GlStateManager.disableAlpha();
				GlStateManager.enableDepth();
			}
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
	};
	
	/**
	 * @return the overrideF3
	 */
	public BooleanSetting getOverrideF3() {
		return overrideF3;
	}
	
	@Override
	public double getArraylistAnimation() {
		return 0;
	}
	
	/**
	 * @return the desyncModelShader
	 */
	public Shader getDesyncModelShader() {
		return desyncModelShader;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isDisabled() {
		return false;
	}
	
	@Override
	public void toggle() {
		
	}
	
	@Override
	public void enable() {
		
	}
	
	@Override
	public void disable() {
		
	}
	
}
