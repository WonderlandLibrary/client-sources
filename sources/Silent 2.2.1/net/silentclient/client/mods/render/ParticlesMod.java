package net.silentclient.client.mods.render;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumParticleTypes;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EntityAttackEvent;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class ParticlesMod extends Mod {
	public ParticlesMod() {
		super("Particles", ModCategory.MODS, "silentclient/icons/mods/particles.png");
	}
	
	@Override
	public void setup() {
		this.addBooleanSetting("Show Sharpness", this, true);
		this.addBooleanSetting("Always Sharpness Particles", this, false);
		this.addSliderSetting("Sharpness Multiplier", this, 1, 1, 20, true);
		this.addBooleanSetting("Show Criticals", this, true);
		this.addSliderSetting("Criticals Multiplier", this, 1, 1, 20, true);
		this.addBooleanSetting("Only Players Particles", this, false);
		this.addBooleanSetting("Cancel Impossible", this, false);
		this.addBooleanSetting("New Particles For Impossibles", this, false);
	}
	
	@EventTarget
	public void onAttackEntity(EntityAttackEvent event) {
		int critMultiplier = Client.getInstance().getSettingsManager().getSettingByName(this, "Criticals Multiplier").getValInt();
		int sharpnessMultiplier = Client.getInstance().getSettingsManager().getSettingByName(this, "Sharpness Multiplier").getValInt();
		boolean showSharpness = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Sharpness").getValBoolean();
		boolean allwaysShowSharpness = Client.getInstance().getSettingsManager().getSettingByName(this, "Always Sharpness Particles").getValBoolean();
		boolean showCriticals = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Criticals").getValBoolean();
		boolean onlyPlayers = Client.getInstance().getSettingsManager().getSettingByName(this, "Only Players Particles").getValBoolean();
		boolean cancel_imp = Client.getInstance().getSettingsManager().getSettingByName(this, "Cancel Impossible").getValBoolean();
		boolean impNewParticle = Client.getInstance().getSettingsManager().getSettingByName(this, "New Particles For Impossibles").getValBoolean();

		Entity target = event.getVictim();
		Entity player = event.getPlayer();

		if (!onlyPlayers || target instanceof EntityPlayer) {
			if (player.getDistanceToEntity(target) <= 3.0F || mc.thePlayer.capabilities.isCreativeMode || !cancel_imp) {
				boolean crit = mc.thePlayer.fallDistance > 0.0F && !mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isPotionActive(Potion.blindness) && mc.thePlayer.ridingEntity == null;
				boolean sharpness = ((EntityPlayerSP) player).getHeldItem() != null && ((EntityPlayerSP) player).getHeldItem().isItemEnchanted() && ((EntityPlayerSP) player).getHeldItem().getEnchantmentTagList().toString().contains("id:16s");
				int i;

				if (showSharpness && (allwaysShowSharpness || sharpness)) {
					for (i = 0; i < (sharpness ? sharpnessMultiplier - 1 : sharpnessMultiplier); ++i) {
						if (player.getDistanceToEntity(target) > 3.0F && !mc.thePlayer.capabilities.isCreativeMode && impNewParticle) {
							mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.VILLAGER_ANGRY);
						} else {
							mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT_MAGIC);
						}
					}
				}

				if (crit && showCriticals) {
					for (i = 0; i < critMultiplier - 1; ++i) {
						if (player.getDistanceToEntity(target) > 3.0F && !mc.thePlayer.capabilities.isCreativeMode && impNewParticle) {
							mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CLOUD);
						} else {
							mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT);
						}
					}
				}

			}
		}
	}
}
