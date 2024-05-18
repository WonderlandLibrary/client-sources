package net.minecraft.client.triton.impl.modules.combat.aura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.client.triton.impl.modules.combat.Aura;
import net.minecraft.client.triton.impl.modules.movement.NoSlowdown;
import net.minecraft.client.triton.impl.modules.movement.Speed;
import net.minecraft.client.triton.impl.modules.movement.speed.Vhop;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RotationUtils;
import net.minecraft.client.triton.utils.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Switch extends AuraMode {
	private boolean setupTick;
	private boolean switchingTargets;
	private List<EntityLivingBase> targets;
	private int index;
	private Timer timer;

	public Switch(final String name, final boolean value, final Module module) {
		super(name, value, module);
		this.targets = new ArrayList<EntityLivingBase>();
		this.timer = new Timer();
	}

	@Override
	public boolean onUpdate(final UpdateEvent event) {
		if (super.onUpdate(event)) {
			switch (event.getState()) {
			case PRE: {
				final Aura auraModule = (Aura) this.getModule();
				final NoSlowdown noSlowdownModule = (NoSlowdown) new NoSlowdown().getInstance();
				this.targets = this.getTargets();
				if (this.index >= this.targets.size()) {
					this.index = 0;
				}
				if (this.targets.size() > 0) {
					final EntityLivingBase target = this.targets.get(this.index);
					if (target != null) {
						if (auraModule.autoblock && ClientUtils.player().getCurrentEquippedItem() != null
								&& ClientUtils.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
							ClientUtils.playerController().sendUseItem(ClientUtils.player(), ClientUtils.world(),
									ClientUtils.player().getCurrentEquippedItem());
							if (!noSlowdownModule.isEnabled() && auraModule.noslowdown) {
								ClientUtils.packet(
										new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
												BlockPos.ORIGIN, EnumFacing.DOWN));
							}
						}
						final float[] rotations = RotationUtils.getRotations(target);
						event.setYaw(rotations[0]);
						event.setPitch(rotations[1]);
					}
					if (this.setupTick) {
						if (this.targets.size() > 0 && auraModule.criticals && ClientUtils.player().isCollidedVertically
								&& this.bhopCheck()) {
							event.setY(event.getY() + 0.07);
							event.setGround(false);
						}
						if (this.timer.delay(auraModule.speed)) {
							this.incrementIndex();
							this.switchingTargets = true;
							this.timer.reset();
						}
					} else {
						if (this.targets.size() > 0 && auraModule.criticals && ClientUtils.player().isCollidedVertically
								&& this.bhopCheck()) {
							event.setGround(false);
							event.setAlwaysSend(true);
						}
						if (ClientUtils.player().fallDistance > 0.0f && ClientUtils.player().fallDistance < 0.66) {
							event.setGround(true);
						}
					}
				}
				this.setupTick = !this.setupTick;
				break;
			}
			case POST: {
				final Aura auraModule = (Aura) this.getModule();
				if (!this.setupTick || this.targets.size() <= 0 || this.targets.get(this.index) == null
						|| this.targets.size() <= 0) {
					break;
				}
				final EntityLivingBase target = this.targets.get(this.index);
				if (target != null) {
					if (ClientUtils.player().isBlocking()) {
						ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
								BlockPos.ORIGIN, EnumFacing.DOWN));
					}
					auraModule.attack(target);
					if (ClientUtils.player().isBlocking()) {
						ClientUtils.packet(
								new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
					}
				}
				break;
			}
			}
		}
		return true;
	}

	private boolean bhopCheck() {
		if (new Speed().getInstance().isEnabled() && ((Speed) new Speed().getInstance()).bhop.getValue()) {
			if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
				return false;
			}
			Vhop.stage = -4;
		}
		return true;
	}

	private void swap(final int slot, final int hotbarNum) {
		ClientUtils.mc().playerController.windowClick(ClientUtils.mc().thePlayer.inventoryContainer.windowId, slot,
				hotbarNum, 2, ClientUtils.mc().thePlayer);
	}

	private void incrementIndex() {
		++this.index;
		if (this.index >= this.targets.size()) {
			this.index = 0;
		}
	}

	private List<EntityLivingBase> getTargets() {
		final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
		for (final Entity entity : ClientUtils.loadedEntityList()) {
			if (((Aura) this.getModule()).isEntityValid(entity)) {
				targets.add((EntityLivingBase) entity);
			}
		}
		targets.sort(new Comparator<EntityLivingBase>() {
			@Override
			public int compare(final EntityLivingBase target1, final EntityLivingBase target2) {
				return Math.round(target2.getHealth() - target1.getHealth());
			}
		});
		return targets;
	}
}
