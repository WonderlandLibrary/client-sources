package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.Render2DEvent;
import dev.lvstrng.argon.event.listeners.Render2DListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;// so did we just disappear with all the methods or what
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class AutoDoubleHand extends Module implements Render2DListener {
    private final BooleanSetting checkShield;
    private final BooleanSetting onPopSwitch;
    private final BooleanSetting lowHealthSwitch;
    private final IntSetting healthThreshold;
    private final BooleanSetting checkGround;
//    private final BooleanSetting checkNearbyPlayers;
    private final IntSetting playerDistance;
    private final BooleanSetting predictCrystals;
    private final BooleanSetting checkAim;
    private final BooleanSetting checkHeldItems;
    private final IntSetting heightTrigger;
    private boolean isTotemActive;
    private boolean isHealthLow;

    public AutoDoubleHand() {
        super("Auto Double Hand", "Automatically switches to your totem when you're about to pop", 0, Category.COMBAT);
        this.checkShield = new BooleanSetting("Check Shield", false).setDescription("Checks if you're blocking with a shield");
        this.onPopSwitch = new BooleanSetting("On Pop", false).setDescription("Switches to a totem if you pop");
        this.lowHealthSwitch = new BooleanSetting("On Health", false).setDescription("Switches to totem if low on health");
        this.healthThreshold = new IntSetting("Health", 1.0, 20.0, 2.0, 1.0).setDescription("Health to trigger at");
        this.checkGround = new BooleanSetting("On Ground", true).setDescription("Whether crystal damage is checked on ground or not");
//        this.checkNearbyPlayers = new BooleanSetting("Check Players", true).setDescription("Checks for nearby players");
        this.playerDistance = new IntSetting("Distance", 1.0, 10.0, 5.0, 0.1).setDescription("Player distance");
        this.predictCrystals = new BooleanSetting("Predict Crystals", false);
        this.checkAim = new BooleanSetting("Check Aim", false).setDescription("Checks if the opponent is aiming at obsidian");
        this.checkHeldItems = new BooleanSetting("Check Items", false).setDescription("Checks if the opponent is holding crystals");
        this.heightTrigger = new IntSetting("Activates Above", 0.0, 4.0, 0.2, 0.1).setDescription("Height to trigger at");
        this.addSettings(new Setting[]{
                this.checkShield,
                this.onPopSwitch,
                this.lowHealthSwitch,
                this.healthThreshold,
                this.checkGround,
//                this.checkNearbyPlayers,
                this.playerDistance,
                this.predictCrystals,
                this.checkAim,
                this.checkHeldItems,
                this.heightTrigger
        });
        this.isTotemActive = false;
        this.isHealthLow = false;
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(Render2DListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(Render2DListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender2D(final Render2DEvent event) {
        if (this.mc.player == null) {
            return;
        }

        final double playerDistanceSquared = this.playerDistance.getValue() * this.playerDistance.getValue();
        final PlayerInventory inventory = this.mc.player.getInventory();

        if (this.checkShield.getValue() && this.mc.player.isBlocking()) {
            return;
        }

        if (inventory.offHand.get(0).getItem() != Items.TOTEM_OF_UNDYING && this.onPopSwitch.getValue() && !this.isHealthLow) {
            this.isHealthLow = true;
            InventoryUtil.method309(Items.TOTEM_OF_UNDYING);
        }

        if (inventory.offHand.get(0).getItem() == Items.TOTEM_OF_UNDYING) {
            this.isHealthLow = false;
        }

        if (this.mc.player.getHealth() <= this.healthThreshold.getValue() && this.lowHealthSwitch.getValue() && !this.isTotemActive) {
            this.isTotemActive = true;
            InventoryUtil.method309(Items.TOTEM_OF_UNDYING);
        }

        if (this.mc.player.getHealth() > this.healthThreshold.getValue()) {
            this.isTotemActive = false;
        }

        if (this.mc.player.getHealth() > 19.0f) {
            return;
        }

        if (!this.checkGround.getValue() && this.mc.player.isOnGround()) {
            return;
        }

//        if (this.checkNearbyPlayers.getValue() && this.mc.world.getPlayers().parallelStream().filter(this::isPlayerNearby).noneMatch(this::isPlayerInRange)) {
//            return;
//        }

        final double heightTriggerValue = this.heightTrigger.getValue();
        for (int heightCheck = (int) Math.floor(heightTriggerValue), i = 1; i <= heightCheck; ++i) {
            if (!this.mc.world.getBlockState(this.mc.player.getBlockPos().add(0, -i, 0)).isAir()) {
                return;
            }
        }

        final Vec3d playerPosition = this.mc.player.getPos();
        if (!this.mc.world.getBlockState(new BlockPos(new BlockPos((int) playerPosition.x, (int) playerPosition.y - (int) heightTriggerValue, (int) playerPosition.z))).isAir()) {
            return;
        }

        final List<Vec3d> crystalPositions = this.getCrystalPositions();
        final ArrayList<Vec3d> nearbyCrystals = new ArrayList<>();
        crystalPositions.addAll(getCrystalPositions());

        if (this.predictCrystals.getValue()) {
            Stream<BlockPos> crystalStream = BlockUtil.method260(this.mc.player.getBlockPos().add(-6, -8, -6), this.mc.player.getBlockPos().add(6, 2, 6))
                    .filter(this::isObsidianBlock).filter(CrystalUtil::method392);

            if (this.checkAim.getValue()) {
                if (this.checkHeldItems.getValue()) {
                    crystalStream = crystalStream.filter(this::isAimingAtObsidian);
                } else {
                    crystalStream = crystalStream.filter(this::isAimingAtObsidianWithoutItems);
                }
            }
            nearbyCrystals.addAll(crystalStream.map(BlockPos::toCenterPos).toList());
        }

        for (Vec3d crystalPos : nearbyCrystals) {
            if (DamageUtil.method263((PlayerEntity) this.mc.player, crystalPos, true, null, false) >= this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) {
                InventoryUtil.method309(Items.TOTEM_OF_UNDYING);
                break;
            }
        }
    }

    private List<Vec3d> getCrystalPositions() {
        final Vec3d playerPosition = this.mc.player.getPos();
        return this.mc.world.getEntitiesByClass(EndCrystalEntity.class, new Box(playerPosition.add(-6.0, -6.0, -6.0), playerPosition.add(6.0, 6.0, 6.0)), p -> true).stream().map(Entity::getPos).toList();
    }

    private boolean isObsidianBlock(final BlockPos blockPos) {
        return mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN;
    }

    private boolean isAimingAtObsidian(final BlockPos blockPos) {
        return this.mc.world.getPlayers().parallelStream().filter(p -> p != mc.player).filter(p -> p.isHolding(Items.END_CRYSTAL)).anyMatch(p -> isAimingAtBlock(blockPos, p));
    }

    private boolean isAimingAtObsidianWithoutItems(final BlockPos blockPos) {
        return this.mc.world.getPlayers().parallelStream().filter(p -> p != mc.player).anyMatch(p -> isAimingAtBlock(blockPos, p));
    }

    private boolean isAimingAtBlock(final BlockPos blockPos, final AbstractClientPlayerEntity playerEntity) {
        final Vec3d position = playerEntity.getEyePos();
        final BlockHitResult hitResults = this.mc.world.raycast(new RaycastContext(position, position.add(RotationUtil.method484((PlayerEntity) playerEntity).multiply(4.5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, playerEntity));
        final BlockHitResult hitResult = hitResults;
        return hitResult != null && hitResult.getBlockPos().equals(blockPos);
    }
}
