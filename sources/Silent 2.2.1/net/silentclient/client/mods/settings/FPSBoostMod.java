package net.silentclient.client.mods.settings;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.init.Blocks;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventManager;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EntityJoinLevelEvent;
import net.silentclient.client.mixin.accessors.EntityLookHelperAccessor;
import net.silentclient.client.mixin.ducks.EntityAITasksExt;
import net.silentclient.client.mixin.ducks.EntityLivingExt;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.mods.other.ai.FastTrig;
import net.silentclient.client.mods.other.ai.FixedEntityLookHelper;
import net.silentclient.client.utils.HUDCaching;
import net.silentclient.client.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FPSBoostMod extends Mod {
	public static final Set<Block> flowersBlocks = Sets.newHashSet(
			Blocks.double_plant,
			Blocks.red_flower,
			Blocks.yellow_flower
	);

	public static final Set<Block> tallGrass = Sets.newHashSet(Blocks.tallgrass);

	public static final Set<Block> fencesBlocks = Sets.newHashSet(Blocks.nether_brick_fence, Blocks.oak_fence, Blocks.acacia_fence, Blocks.birch_fence, Blocks.dark_oak_fence, Blocks.jungle_fence, Blocks.spruce_fence);
	public static final Set<Block> fencesGatesBlocks = Sets.newHashSet(Blocks.oak_fence_gate, Blocks.acacia_fence_gate, Blocks.birch_fence_gate, Blocks.dark_oak_fence_gate, Blocks.jungle_fence_gate, Blocks.spruce_fence_gate);

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public FPSBoostMod() {
		super("FPS Boost", ModCategory.SETTINGS, "silentclient/icons/settings/fpsboost.png");
	}
	
	@Override
	public void setup() {
		ArrayList<String> lazyLoading = new ArrayList<String>();

		lazyLoading.add("Off");
		lazyLoading.add("Quality"); // 1
		lazyLoading.add("Balance"); // 2
		lazyLoading.add("Performance"); // 4

		ArrayList<String> entityCulling = new ArrayList<String>();

		entityCulling.add("Off");
		entityCulling.add("Quality"); // 10
		entityCulling.add("Balance"); // 25
		entityCulling.add("Performance"); // 50

		this.addBooleanSetting("FPS Boost", this, true); // ready
		this.addBooleanSetting("Advanced FPS Boost", this, false); // ready
		this.addBooleanSetting("Low Graphics Mode", this, false); // ready
		this.addBooleanSetting("Hud Optimization", this, false); // ready
		this.addBooleanSetting("Optimized Entity Movement", this, true);
		this.addBooleanSetting("Limit Unfocused FPS", this, true);
		this.addSliderSetting("Unfocused FPS Limit", this, 60, 10, 200, true);
		this.addModeSetting("Lazy Chunk Loading", this, "Balance", lazyLoading); // ready
		this.addModeSetting("Occlusion Culling", this, "Balance", entityCulling); // ready
		this.addBooleanSetting("Hide Tall Grass", this, false); // ready
		this.addBooleanSetting("Hide Flowers", this, false); // ready
		this.addBooleanSetting("Hide Fences", this, false); // ready
		this.addBooleanSetting("Hide Fence Gates", this, false); // ready
		this.addBooleanSetting("Hide Armor Stands", this, false); // ready
		this.addBooleanSetting("Hide Skulls", this, false); // ready
		this.addBooleanSetting("Hide Item Frames", this, false); // ready
		this.addBooleanSetting("Hide Maps In Item Frames", this, false); // ready
		this.addBooleanSetting("Hide Stuck Arrows", this, false); // ready
		this.addBooleanSetting("Hide Ground Arrows", this, false); // ready
		this.addBooleanSetting("Hide Lava Particles", this, false); // ready
		this.addBooleanSetting("Hide Mob in Spawner", this, false); // ready
		this.addBooleanSetting("Hide Spawner Particles", this, false); // ready
		this.addBooleanSetting("Hide World Border", this, false); // ready
		this.addSliderSetting("Player Render Distance", this, 64, 1, 64, true); // ready
		this.addSliderSetting("Passive Entity Render Distance", this, 64, 1, 64, true); // ready
		this.addSliderSetting("Hostile Entity Render Distance", this, 64, 1, 64, true); // ready
		this.addSliderSetting("Misc. Entity Render Distance", this, 64, 1, 64, true); // ready
		this.addBooleanSetting("Check glError",this, false); // ready
		this.addBooleanSetting("Do memory debug",this, true); // ready
		EventManager.register(new HUDCaching());
		FastTrig.init();
	}

	@EventTarget
	public void onJoinWorldEvent(EntityJoinLevelEvent event) {
		if(!Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Optimized Entity Movement").getValBoolean()) {
			return;
		}

		Entity entity = event.getEntity();
		if (entity instanceof EntityLiving) {
			EntityLiving living = (EntityLiving)entity;
			Iterator<EntityAITasks.EntityAITaskEntry> it = ((List<EntityAITasks.EntityAITaskEntry>) ((EntityAITasksExt) ((EntityLivingExt)((Object)living)).client$getTasks()).client$getTaskEntries()).iterator();
			while (it.hasNext()) {
				EntityAITasks.EntityAITaskEntry obj = it.next();
				if (!(obj instanceof EntityAITasks.EntityAITaskEntry)) continue;
				EntityAITasks.EntityAITaskEntry task = obj;
				if (task.action instanceof EntityAIWatchClosest) {
					it.remove();
					continue;
				}
				if (!(task.action instanceof EntityAILookIdle)) continue;
				it.remove();
			}
			if (living.getLookHelper() == null || living.getLookHelper().getClass() == EntityLookHelper.class) {
				EntityLookHelper oldHelper = living.getLookHelper();
				((EntityLivingExt)((Object)living)).client$setLookHelper(new FixedEntityLookHelper(living));
				((EntityLookHelperAccessor)((Object)living.getLookHelper())).setPosX(((EntityLookHelperAccessor)((Object)oldHelper)).getPosX());
				((EntityLookHelperAccessor)((Object)living.getLookHelper())).setPosY(((EntityLookHelperAccessor)((Object)oldHelper)).getPosY());
				((EntityLookHelperAccessor)((Object)living.getLookHelper())).setPosZ(((EntityLookHelperAccessor)((Object)oldHelper)).getPosZ());
				((EntityLookHelperAccessor)((Object)living.getLookHelper())).setLooking(((EntityLookHelperAccessor)((Object)oldHelper)).isLooking());
				((EntityLookHelperAccessor)((Object)living.getLookHelper())).setDeltaLookPitch(((EntityLookHelperAccessor)((Object)oldHelper)).getDeltaLookPitch());
				((EntityLookHelperAccessor)((Object)living.getLookHelper())).setDeltaLookYaw(((EntityLookHelperAccessor)((Object)oldHelper)).getDeltaLookYaw());
			}
		}
	}
	
	public static boolean basicEnabled() {
		return Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "FPS Boost").getValBoolean();
	}

	public static boolean advancedEnabled() {
		return Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Advanced FPS Boost").getValBoolean();
	}
	
	public static boolean hudOptimizationEnabled() {
		return Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hud Optimization").getValBoolean();
	}
	
	@Override
	public void onChangeSettingValue(Setting setting) {
		if(setting.getName().equals("Hud Optimization")) {
			if(setting.getValBoolean()) {
				if (!OpenGlHelper.isFramebufferEnabled() && mc.thePlayer != null) {
	                NotificationUtils.showNotification("Error", "Disable Fast Render/Anti-aliasing");
	                setting.setValBoolean(false);
	            }
			}
			return;
		}
		
		if(setting.getName().equals("Optimized Entity Movement") || setting.getName().equals("Low Graphics Mode") || !setting.isCheck() || setting.getName().equals("Check glError") || setting.getName().equals("Do memory debug") || setting.getName().equals("Limit Unfocused FPS")) {
			return;
		}
		
		mc.renderGlobal.loadRenderers();
	}
}
