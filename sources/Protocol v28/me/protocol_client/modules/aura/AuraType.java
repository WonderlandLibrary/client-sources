package me.protocol_client.modules.aura;

import java.util.ArrayList;
import java.util.List;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.modules.Jesus;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import events.EventPacketSent;
import events.EventPostMotionUpdates;
import events.EventPreMotionUpdates;

public class AuraType {
	public ArrayList<AuraType>		types		= new ArrayList<AuraType>();
	public List<EntityLivingBase>	littleshits	= new ArrayList();
	public EntityLivingBase			meme;
	public EntityLivingBase			lastTarget;
	public String					name;
	public AuraType					current;
	public static float				yaw;
	public static float				pitch;
	public int						tTicks;
	public int						critTicks;
	public int						switchTicks;

	public void addType(AuraType type) {
		this.types.add(type);
	}

	public AuraType getTypeByName(String name) {
		for (AuraType t : this.types) {
			if (t.getName().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuraType getCurrent() {
		return this.current;
	}

	public void setCurrent(AuraType current) {
		this.current = current;
	}

	public void onUpdate(EventPreMotionUpdates event) {
	}

	public void onPacketOut(EventPacketSent event) {
	}

	public void afterUpdate(EventPostMotionUpdates event) {
	}

	public boolean isHealing() {
		return Protocol.autopot.shouldHeal() && Protocol.autopot.getTotalPots() > 0 && Protocol.autopot.isToggled();
	}

	public void crit() {
		if (Wrapper.getPlayer().onGround && !Jesus.isOnLiquid()) {
			critTicks++;
			if (critTicks >= 2) {
				Wrapper.mc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc().thePlayer.posX, Wrapper.mc().thePlayer.posY + 0.05F, Wrapper.mc().thePlayer.posZ, false));
				Wrapper.mc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc().thePlayer.posX, Wrapper.mc().thePlayer.posY, Wrapper.mc().thePlayer.posZ, false));
				Wrapper.mc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc().thePlayer.posX, Wrapper.mc().thePlayer.posY + 0.012511F, Wrapper.mc().thePlayer.posZ, false));
				Wrapper.mc().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc().thePlayer.posX, Wrapper.mc().thePlayer.posY, Wrapper.mc().thePlayer.posZ, false));
				critTicks = 0;
			}
		}
	}

	public void face(EntityLivingBase ent, EventPreMotionUpdates event) {
		faceEntity(ent, Integer.MAX_VALUE, Integer.MAX_VALUE, event);
		Wrapper.getPlayer().rotationPitch += 9.0E-4f;
	}
	public void face(EntityLivingBase ent) {
		faceEntity(ent, Integer.MAX_VALUE, Integer.MAX_VALUE, null);
		Wrapper.getPlayer().rotationPitch += 9.0E-4f;
	}

	/**
     * Ripped straight from EntityLivingBase: Changes pitch and yaw so that the entity calling the function is facing the entity provided as an argument.
     */
    public void faceEntity(Entity p_70625_1_, float p_70625_2_, float p_70625_3_, EventPreMotionUpdates event)
    {
        double var4 = p_70625_1_.posX - Wrapper.getPlayer().posX;
        double var8 = p_70625_1_.posZ - Wrapper.getPlayer().posZ;
        double var6;

        if (p_70625_1_ instanceof EntityLivingBase)
        {
            EntityLivingBase var14 = (EntityLivingBase)p_70625_1_;
            var6 = var14.posY + (double)var14.getEyeHeight() - (Wrapper.getPlayer().posY + (double)Wrapper.getPlayer().getEyeHeight());
        }
        else
        {
            var6 = (p_70625_1_.getEntityBoundingBox().minY + p_70625_1_.getEntityBoundingBox().maxY) / 2.0D - (Wrapper.getPlayer().posY + (double)Wrapper.getPlayer().getEyeHeight());
        }

        double var141 = (double)MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float)(-(Math.atan2(var6, var141) * 180.0D / Math.PI));
        pitch = this.updateRotation(Wrapper.getPlayer().rotationPitch, var13, p_70625_3_);
        yaw = this.updateRotation(Wrapper.getPlayer().rotationYaw, var12, p_70625_2_);
        if(event != null){
        event.pitch = pitch;
        event.yaw = yaw;
        }
        if(Protocol.auraModule.lockview.getValue()){
        	Wrapper.getPlayer().rotationPitch = this.updateRotation(Wrapper.getPlayer().rotationPitch, var13, p_70625_3_);
        	Wrapper.getPlayer().rotationYaw = this.updateRotation(Wrapper.getPlayer().rotationYaw, var12, p_70625_2_);
        }
    }

    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_)
    {
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);

        if (var4 > p_70663_3_)
        {
            var4 = p_70663_3_;
        }

        if (var4 < -p_70663_3_)
        {
            var4 = -p_70663_3_;
        }

        return p_70663_1_ + var4;
    }

	public boolean shouldShowCrits(boolean force) {
		return force || (!Wrapper.getPlayer().isOnLadder() && Wrapper.getPlayer().fallDistance > 0 && !Wrapper.getPlayer().onGround && !Jesus.isInLiquid());
	}

	public boolean shouldEnchantCrit(EntityLivingBase ent) {
		float sharp = EnchantmentHelper.func_152377_a(Wrapper.getPlayer().getHeldItem(), ent.getCreatureAttribute());
		return sharp > 0;
	}

	public boolean isInAttackRange(EntityLivingBase ent) {
		if (ent.getDistanceToEntity(Wrapper.getPlayer()) <= Protocol.auraModule.range.getValue()) {
			return true;
		}
		return false;
	}

	public static float changeRotation(final float p_70663_1_, final float p_70663_2_, final float p_70663_3_) {
		float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
		if (var4 > p_70663_3_) {
			var4 = p_70663_3_;
		}
		if (var4 < -p_70663_3_) {
			var4 = -p_70663_3_;
		}
		return p_70663_1_ + var4;
	}

	public boolean isAttackable(EntityLivingBase ent) {
		if (Protocol.auraModule.player.getValue()) {
			if (ent instanceof EntityPlayer && !((EntityPlayer)ent).capabilities.isCreativeMode && !ent.isDead && ent.auraTimer == 0 && ent != Wrapper.getPlayer() && ent.ticksExisted > Protocol.auraModule.existed.getValue() && ((EntityPlayer) ent).getHealth() > 0 && !ent.isDead && !Protocol.getFriendManager().isFriend(ent.getName()) && !ent.getName().startsWith("Body #")) {
				if (!Protocol.auraModule.wallcheck.getValue()) {
					if (!Protocol.auraModule.invisible.getValue()) {
						if (!ent.isInvisible()) {
							return true;
						}
						return false;
					}
					if (ent.canEntityBeSeen(Wrapper.getPlayer())) {
						return true;
					}
					return false;
				}
				if (!Protocol.auraModule.invisible.getValue()) {
					if (!ent.isInvisible()) {
						return true;
					}
					return false;
				}
				return true;
			}
		}
		if (Protocol.auraModule.mob.getValue()) {
			if (ent instanceof EntityLivingBase && !(ent instanceof EntityPlayer)) {
				if (ent.getHealth() > 0 && !ent.isDead && ent.auraTimer == 0 && ent.ticksExisted > Protocol.auraModule.existed.getValue() && !ent.isDead && !Protocol.getFriendManager().isFriend(ent.getName()) && !ent.getName().startsWith("Body #")) {
					if (!Protocol.auraModule.wallcheck.getValue()) {
						if (!Protocol.auraModule.invisible.getValue()) {
							if (!ent.isInvisible()) {
								return true;
							}
							return false;
						}
						if (ent.canEntityBeSeen(Wrapper.getPlayer())) {
							return true;
						}
						return false;
					}
					if (!Protocol.auraModule.invisible.getValue()) {
						if (!ent.isInvisible()) {
							return true;
						}
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
}
