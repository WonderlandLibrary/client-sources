package net.silentclient.client.mods.render;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.mixin.accessors.SimpleReloadableResourceManagerAccessor;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.utils.NotificationUtils;
import net.silentclient.client.utils.resource.SaturationResourceManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

public class ColorSaturationMod extends Mod {
	private final Map<String, FallbackResourceManager> domainResourceManagers;
    private Field cachedFastRender;
    
    public ShaderGroup shader;
    private int cachedWidth = 0;
    private int cachedHeight = 0;
	
	public ColorSaturationMod() {
		super("Color Saturation", ModCategory.MODS, "silentclient/icons/mods/colorsaturation.png");
		this.domainResourceManagers = ((SimpleReloadableResourceManagerAccessor) this.mc.getResourceManager()).getDomainResourceManagers();
		try {
            this.cachedFastRender = GameSettings.class.getDeclaredField("ofFastRender");
        } catch (Exception exception) {

        }
	}
	
	public boolean isFastRenderEnabled() {
		try {
			return this.cachedFastRender.getBoolean(this.mc.gameSettings);
		} catch (Exception exception) {
			return false;
		}
    }
	
	@Override
	public void onChangeSettingValue(Setting setting) {
		if(isEnabled()) {
			update();
		}
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Amount", this, 1, 0, 5, false);
	}
	
	@EventTarget
	public void tick(ClientTickEvent event) {
		if (this.domainResourceManagers != null && !this.domainResourceManagers.containsKey("colorsaturation")) {
            this.domainResourceManagers.put("colorsaturation", new SaturationResourceManager(Client.getInstance().getiMetadataSerializer()));
        }
    }
	
	public void update() {
		if (this.domainResourceManagers != null && this.domainResourceManagers.containsKey("colorsaturation")) {
            this.domainResourceManagers.remove("colorsaturation");
        }
		if (this.domainResourceManagers != null && !this.domainResourceManagers.containsKey("colorsaturation")) {
            this.domainResourceManagers.put("colorsaturation", new SaturationResourceManager(Client.getInstance().getiMetadataSerializer()));
        }
		shader = null;
	}
	
	public ShaderGroup getShader() {
		if(isFastRenderEnabled() || mc.thePlayer == null || !isEnabled() || isForceDisabled()) {
			return null;
		}
		if(shader != null && cachedWidth == mc.displayWidth && cachedHeight == mc.displayHeight) {
			return shader;
		}
		shader = null;
		if (this.domainResourceManagers != null && this.domainResourceManagers.containsKey("colorsaturation")) {
            this.domainResourceManagers.remove("colorsaturation");
        }
		if (this.domainResourceManagers != null && !this.domainResourceManagers.containsKey("colorsaturation")) {
            this.domainResourceManagers.put("colorsaturation", new SaturationResourceManager(Client.getInstance().getiMetadataSerializer()));
        }
		try {
			this.shader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(),
					new ResourceLocation("colorsaturation", "colorsaturation"));
			shader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
			cachedWidth = mc.displayWidth;
            cachedHeight = mc.displayHeight;
			return shader;
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isForceDisabled() {
		return isFastRenderEnabled();
	}
}
