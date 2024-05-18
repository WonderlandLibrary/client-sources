package net.minecraft.client.triton.impl.modules.combat.aura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.client.triton.impl.modules.combat.Aura;
import net.minecraft.client.triton.impl.modules.combat.AutoPot;
import net.minecraft.client.triton.impl.modules.movement.NoSlowdown;
import net.minecraft.client.triton.impl.modules.movement.Speed;
import net.minecraft.client.triton.management.enemies.EnemyManager;
import net.minecraft.client.triton.management.event.EventManager;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RotationUtils;
import net.minecraft.client.triton.utils.Timer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Tick extends AuraMode {
	private EntityLivingBase target;
	public static EntityLivingBase focusTarget;
	private boolean reBlock;
	private boolean unblocked;
	private Timer timer;
	private Timer swapTargetTimer;
	private Timer testTimer;
	private UpdateEvent prevEvent;
	private static /* synthetic */ int[] $SWITCH_TABLE$me$aristhena$event$Event$State;

	public Tick(final String name, final boolean value, final Module module) {
		super(name, value, module);
		this.timer = new Timer();
		this.swapTargetTimer = new Timer();
		this.testTimer = new Timer();
		EventManager.register(this);
	}

	@Override
	public boolean enable() {
		if (super.enable() && ClientUtils.player() != null) {
			this.prevEvent = null;
			this.target = this.getTarget(true);
			this.reBlock = false;
		}
		return super.enable();
	}

	@Override
	public boolean onUpdate(final UpdateEvent event) {
		if (super.onUpdate(event)) {
			if (event.getState() == event.getState().PRE) {
				final Aura auraModule = (Aura) this.getModule();
				this.lowerTicks();
				if (this.shouldBlock(auraModule)) {
					ClientUtils.playerController().sendUseItem(ClientUtils.player(), ClientUtils.world(),
							ClientUtils.player().getCurrentEquippedItem());
					if (!new NoSlowdown().getInstance().isEnabled() && auraModule.noslowdown) {
						ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
								BlockPos.ORIGIN, EnumFacing.DOWN));
					}
				}
				if (this.target != null) {
					final float[] rotations = RotationUtils.getRotations(this.target);
					event.setYaw(rotations[0]);
					event.setPitch(rotations[1]);
					this.prevEvent = event;
				}
				if (this.swapTargetTimer.delay(auraModule.dura ? 440 : 240) && (this.getTarget(false) != null
						|| this.isInvalid(this.target) || this.target.auraTicks <= 9)) {
					this.target = this.getTarget(false);
					this.swapTargetTimer.reset();
				}
			}
			if (event.getState() == event.getState().POST) {
				final Aura auraModule = (Aura) this.getModule();
				if (this.reBlock) {
					ClientUtils.playerController().sendUseItem(ClientUtils.player(), ClientUtils.world(),
							ClientUtils.player().getCurrentEquippedItem());
					this.reBlock = false;
				}
				if (auraModule.dura && !new Speed().getInstance().isEnabled()
						&& this.prevEvent.getYaw() == RotationUtils.getRotations(this.target)[0]) {
					if (this.target.auraTicks <= 10) {
						if (ClientUtils.player().isBlocking()) {
							final C07PacketPlayerDigging dedicatedToTickDotJava = new C07PacketPlayerDigging(
									C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
							ClientUtils.packet(dedicatedToTickDotJava);
							this.reBlock = true;
						}
						this.target.auraTicks = 20;
						if (auraModule.dura) {
							this.testTimer.reset();
							this.swap(9, ClientUtils.player().inventory.currentItem);
							this.attack(this.target, false);
							auraModule.attack(this.target, false);
							this.attack(this.target, true);
							this.swap(9, ClientUtils.player().inventory.currentItem);
							this.attack(this.target, false);
							this.attack(this.target, true);
						} else {
							this.attack(this.target, false);
							auraModule.attack(this.target, false);
							this.attack(this.target, true);
						}
						AutoPot.potNextCompat = true;
						this.swapTargetTimer.reset();
					}
				} else {
					if (!new Speed().getInstance().isEnabled() && auraModule.criticals) {
						this.crit();
					}
					if (this.prevEvent != null && this.target.auraTicks <= 10
							&& this.prevEvent.getYaw() == RotationUtils.getRotations(this.target)[0]) {
						if (ClientUtils.player().isBlocking()) {
							final C07PacketPlayerDigging dedicatedToTickDotJava = new C07PacketPlayerDigging(
									C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
							ClientUtils.packet(dedicatedToTickDotJava);
							this.reBlock = true;
						}
						this.target.auraTicks = 20;
						auraModule.attack(this.target);
						auraModule.attack(this.target);
						auraModule.attack(this.target);
						AutoPot.potNextCompat = true;
						this.swapTargetTimer.reset();
					}
				}
			}
		}
		return true;
	}

	private boolean shouldBlock(final Aura auraModule) {
		if (auraModule.autoblock && ClientUtils.player().getCurrentEquippedItem() != null
				&& ClientUtils.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
			final double oldRange = auraModule.range;
			final int nearbyEntitiesBlock = 0;
			for (final Entity entity : ClientUtils.loadedEntityList()) {
				if (auraModule.isEntityValid(entity)) {
					auraModule.range = oldRange;
					return true;
				}
			}
		}
		return false;
	}

	private boolean isInvalid(final EntityLivingBase entity) {
		return entity == null || !((Aura) new Aura().getInstance()).isEntityValid(entity);
	}

	private void attack(final EntityLivingBase ent, final boolean crit) {
		final Aura auraModule = (Aura) this.getModule();
		auraModule.swingItem();
		if (crit) {
			this.crit();
		} else {
			ClientUtils.packet(new C03PacketPlayer(true));
		}
		final float sharpLevel = EnchantmentHelper.func_152377_a(ClientUtils.player().getHeldItem(),
				ent.getCreatureAttribute());
		final boolean vanillaCrit = ClientUtils.player().fallDistance > 0.0f && !ClientUtils.player().onGround
				&& !ClientUtils.player().isOnLadder() && !ClientUtils.player().isInWater()
				&& !ClientUtils.player().isPotionActive(Potion.blindness) && ClientUtils.player().ridingEntity == null;
		ClientUtils.packet(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.ATTACK));
		if (crit || vanillaCrit) {
			ClientUtils.player().onCriticalHit(ent);
		}
		if (sharpLevel > 0.0f) {
			ClientUtils.player().onEnchantmentCritical(ent);
		}
	}

	private void crit() {
		if (ClientUtils.player().isCollidedVertically) {
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.0624,
					ClientUtils.z(), true));
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y(),
					ClientUtils.z(), false));
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.11E-4,
					ClientUtils.z(), false));
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y(),
					ClientUtils.z(), false));
		}
	}

	protected void swap(final int slot, final int hotbarNum) {
		ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, slot, hotbarNum, 2,
				ClientUtils.player());
	}

	private EntityLivingBase getTarget(final boolean startup) {
		final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
		final Aura auraModule = (Aura) this.getModule();
		if (startup) {
			for (int i = 0; i < 10; ++i) {
				this.lowerTicks();
			}
		}
		for (final Entity ent : ClientUtils.loadedEntityList()) {
			if (ent instanceof EntityLivingBase) {
				final EntityLivingBase entity = (EntityLivingBase) ent;
				if (auraModule.enemyPriority && EnemyManager.isEnemy(entity.getName())
						&& auraModule.isEntityValid(entity)) {
					return entity;
				}
				if ((entity.auraTicks > 12 && (entity.auraTicks > 13)) || !auraModule.isEntityValid(entity)) {
					continue;
				}
				targets.add(entity);
			}
		}
		Collections.sort(targets, new Comparator<EntityLivingBase>() {
			@Override
			public int compare(final EntityLivingBase ent1, final EntityLivingBase ent2) {
				return ent1.auraTicks - ent2.auraTicks;
			}
		});
		if (targets.isEmpty()) {
			return null;
		}
		final EntityLivingBase ent2 = targets.get(0);
		if (this.target != ent2) {
			Math.min(ent2.auraTicks, 12);
		}
		return ent2;
	}

	private void lowerTicks() {
		for (final Entity ent : ClientUtils.loadedEntityList()) {
			if (ent instanceof EntityLivingBase) {
				final EntityLivingBase entityLivingBase;
				final EntityLivingBase entity = entityLivingBase = (EntityLivingBase) ent;
				--entityLivingBase.auraTicks;
			}
		}
	}
}
