package me.protocol_client.modules.aura.types;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.modules.aura.AuraType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import events.EventPacketSent;
import events.EventPostMotionUpdates;
import events.EventPreMotionUpdates;

public class MultiAura extends AuraType {

	@Override
	public void onUpdate(EventPreMotionUpdates event) {
		if (Protocol.auraModule.block.getValue()) {
			for (Object o : Wrapper.getWorld().loadedEntityList) {
				if (o instanceof EntityLivingBase) {
					EntityLivingBase jew = (EntityLivingBase) o;
					if (isAttackable(jew) && Wrapper.getPlayer().getDistanceToEntity(jew) <= Protocol.auraModule.blockrange.getValue()) {
						Wrapper.getPlayer().getHeldItem().useItemRightClick(Wrapper.getWorld(), Wrapper.getPlayer());
					}
				}
			}
		}
		gasTheJew();
	}

	public void gasTheJew() {
		for (Object o : Wrapper.getWorld().loadedEntityList) {
			if (o instanceof EntityLivingBase) {
				EntityLivingBase jew = (EntityLivingBase) o;
				if (isAttackable(jew) && isInAttackRange(jew)) {
					tTicks++;
					if (tTicks >= (20 / Protocol.auraModule.delay.getValue())) {
						if (Wrapper.getPlayer().isBlocking()) {
							Wrapper.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
						}
						if (Protocol.auraModule.crits.getValue()) {
							this.crit();
						}
						Wrapper.getPlayer().swingItem();
						Wrapper.sendPacket(new C02PacketUseEntity(jew, C02PacketUseEntity.Action.ATTACK));
						Wrapper.getPlayer().swingItem();
						// if (shouldEnchantCrit(jew)) {
						// Wrapper.getPlayer().onEnchantmentCritical(jew);
						// }
						// if
						// (shouldShowCrits(Protocol.auraModule.crits.getValue()))
						// {
						// Wrapper.getPlayer().onCriticalHit(jew);
						// }
						tTicks = 0;
					}
				}
			}
		}
	}

	public void gasThe(EntityLivingBase jew) {
	}

	@Override
	public void afterUpdate(EventPostMotionUpdates event) {
	}

	@Override
	public void onPacketOut(EventPacketSent event) {
	}
}
