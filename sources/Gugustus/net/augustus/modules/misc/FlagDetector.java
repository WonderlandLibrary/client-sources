package net.augustus.modules.misc;

import net.augustus.events.*;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.modules.misc.flagdetector.FlagDetectorPlayerData;
import net.augustus.utils.ChatUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.MovingObjectPosition;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class FlagDetector extends Module {

	public int movementFlags = 0;
	public int combatFlags = 0;

	public FlagDetector() {
		super("FlagDetector", Color.black, Categorys.MISC);
	}

	public CopyOnWriteArrayList<FlagDetectorPlayerData> data = new CopyOnWriteArrayList<>();
	
	@EventTarget
	public void onEventRender2D(EventRender2D event) {
		if(this.isToggled()) {
			String combatFlagString = "Combat Flags: " + combatFlags;
			String movementFlagString = "Movement Flags: " + movementFlags;
			mc.fontRendererObj.drawString(combatFlagString, event.getScaledX() / 2 - mc.fontRendererObj.getStringWidth(combatFlagString) / 2, 100, 0xff00ff00);
			mc.fontRendererObj.drawString(movementFlagString, event.getScaledX() / 2 - mc.fontRendererObj.getStringWidth(movementFlagString) / 2, 105 + mc.fontRendererObj.FONT_HEIGHT,0xff00ff00);
		}
	}

	@EventTarget
	public void onPreMotion(EventPreMotion e) {
	}

	@EventTarget
	public void onWorldChange(EventWorld e) {
		ChatUtil.sendChat("Reset Flag Counts (Movement: " + movementFlags + ", Combat: " +  combatFlags + ")");
		movementFlags = 0;
		combatFlags = 0;
	}

	@EventTarget
	public void onPacketReceive(EventReadPacket e) {
		if (e.getPacket() instanceof S08PacketPlayerPosLook) {
			movementFlags++;
			ChatUtil.sendChat("Detected Movement Mitigation (" + movementFlags + ")");
		}
	}

	@EventTarget
	public void onEventAttackEntity(EventAttackEntity e) {
		if(this.isToggled()) {
			EntityPlayer player = mc.thePlayer;
			ItemStack heldItem = player.getHeldItem();
			float totalDamage = 0;

			if (heldItem != null && heldItem.getItem() instanceof ItemSword) {
				ItemSword sword = (ItemSword) heldItem.getItem();
			    float baseDamage = sword.getDamageVsEntity();
	
			    // Calculate enchantment modifiers
			    int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, heldItem);
			    int smiteLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, heldItem);
			    int baneOfArthropodsLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.baneOfArthropods.effectId, heldItem);
	
			    float enchantmentModifier = 0.0f;
			    if (sharpnessLevel > 0) {
			        enchantmentModifier += sharpnessLevel * 1.0f; // Each level of Sharpness adds 1.0 damage
			    }
			    if (smiteLevel > 0) {
			        enchantmentModifier += smiteLevel * 2.5f; // Each level of Smite adds 2.5 damage (to undead)
			    }
			    if (baneOfArthropodsLevel > 0) {
			        enchantmentModifier += baneOfArthropodsLevel * 2.5f; // Each level of Bane of Arthropods adds 2.5 damage (to arthropods)
			    }
	
			    totalDamage = baseDamage + enchantmentModifier;
			}
			
			EntityLivingBase target = null;
			if(mm.killAura.isToggled() && mm.killAura.target != null)
				target = mm.killAura.target;
			else if(mc.pointedEntity != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
				target = (EntityLivingBase) mc.pointedEntity;
			
			if(target instanceof EntityPlayer) {
				float damageTaken = 0;
				if(target.hurtTime >= 10) {
					damageTaken = target.lastHealth - target.getHealth();
					if(damageTaken < totalDamage) {
						combatFlags++;
						ChatUtil.sendChat("Detected Combat Mitigation (" + combatFlags + ")");
					}
				}
			}
				
		}
	}

}
