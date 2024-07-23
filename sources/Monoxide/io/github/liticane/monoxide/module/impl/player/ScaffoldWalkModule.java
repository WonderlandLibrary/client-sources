package io.github.liticane.monoxide.module.impl.player;

import java.util.function.Supplier;

import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.PlayerJumpEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import org.lwjglx.input.Keyboard;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.game.RunTickEvent;
import io.github.liticane.monoxide.listener.event.minecraft.input.ClickingEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.SafeWalkEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.SilentMoveEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.rotation.RotationEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.math.random.RandomUtil;
import io.github.liticane.monoxide.util.math.time.TimeHelper;
import io.github.liticane.monoxide.util.player.PlayerHandler;
import io.github.liticane.monoxide.util.player.PlayerUtil;
import io.github.liticane.monoxide.util.player.rotation.RotationUtil;
import io.github.liticane.monoxide.util.world.block.BlockUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@ModuleData(name = "ScaffoldWalk", description = "Bridging automatically", category = ModuleCategory.PLAYER)
public class ScaffoldWalkModule extends Module {

    private final ModeValue rotations = new ModeValue("Rotations", this, new String[]{"Simple", "Advanced", "Godbridge"});
    public NumberValue<Float> minYaw = new NumberValue<>("Minimum Yaw", this, 40f, 0f, 180f, 0);
    public NumberValue<Float> maxYaw = new NumberValue<>("Maximum Yaw", this, 40f, 0f, 180f, 0);
    public NumberValue<Float> minPitch = new NumberValue<>("Minimum Pitch", this, 40f, 0f, 180f, 0);
    public NumberValue<Float> maxPitch = new NumberValue<>("Maximum Pitch", this, 40f, 0f, 180f, 0);
    private final BooleanValue swinging = new BooleanValue("Swing Client-Side", this, true);
    private final NumberValue<Float> timerSpeed = new NumberValue<>("Timer", this, 1f, 0.1f, 5.0f, 3);
    public NumberValue<Float> minStartYaw = new NumberValue<>("Minimum Start Yaw", this, 4f, 0f, 180f, 0);
    public NumberValue<Float> maxStartYaw = new NumberValue<>("Maximum Start Yaw", this, 5f, 0f, 180f, 0);
    public NumberValue<Float> minStartPitch = new NumberValue<>("Minimum Start Pitch", this, 4f, 0f, 180f, 0);
    public NumberValue<Float> maxStartPitch = new NumberValue<>("Maximum Start Pitch", this, 5f, 0f, 180f, 0);
    private final BooleanValue sprint = new BooleanValue("Sprint", this, false);
    private final BooleanValue switchItems = new BooleanValue("Switch Items", this, true);
    private final BooleanValue reverseMovement = new BooleanValue("Reverse Movement", this, false);
    private final BooleanValue addStrafe = new BooleanValue("Add Strafe", this, false);
    private final NumberValue<Long> delay = new NumberValue<>("Delay", this, 0L, 0L, 1000L, 0);
    private final BooleanValue sneak = new BooleanValue("Sneak", this, false);
    private final BooleanValue safeWalk = new BooleanValue("SafeWalk", this, false);
    private final ModeValue sneakMode = new ModeValue("Sneak Mode", this, new String[]{"Edge", "Constant"}, new Supplier[]{() -> sneak.getValue()});
    private final BooleanValue tower = new BooleanValue("Tower", this, false);
    private final ModeValue towerMode = new ModeValue("Tower Mode", this, new String[]{"Vanilla", "Polar LowJump", "Verus", "NCP", "Intave", "MMC", "Watchdog"}, new Supplier[]{() -> tower.getValue()});
    private final NumberValue<Long> unSneakDelay = new NumberValue<Long>("Unsneak delay", this, 0L, 0L, 1000L, 0, new Supplier[]{() -> sneak.getValue() && sneakMode.is("Edge")});
    private final BooleanValue autoJump = new BooleanValue("Auto Jump", this, false);
    private final BooleanValue dragClick = new BooleanValue("Drag Click", this, false);
    private final TimeHelper timeHelper = new TimeHelper(), unsneakTimeHelper = new TimeHelper(), startingTimeHelper = new TimeHelper();
    private double[] lastPosition = new double[3];
    private int lastItem = -1;
    private BlockPos blockPos;
    private boolean starting;
    private int places;
    private float lastYaw, lastPitch;

    // watchdog tower
    private boolean spoofGround = false;
    private int towerTick = 0;

    @Listen
    public final void onTick(RunTickEvent runTickEvent) {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;

        mc.thePlayer.setSprinting(sprint.isEnabled());

        mc.thePlayer.speedInAir = 0.02F;
        mc.timer.timerSpeed = timerSpeed.getValue();

        if (mc.thePlayer.motionX == 0.0 && mc.thePlayer.motionZ == 0.0 && mc.thePlayer.onGround) {
            starting = true;
            startingTimeHelper.reset();
        }

        if (startingTimeHelper.hasReached(200)) {
            starting = false;
        }
    }

    @Listen
    public final void onRotation(RotationEvent rotationEvent) {
        this.blockPos = BlockUtil.getAimBlockPos();

        if (this.blockPos != null) {
            float[] rotations = this.getRotations();
            float yawSpeed;
            float pitchSpeed;
            if (starting) {
                yawSpeed = (float) RandomUtil.randomBetween(minStartYaw.getValue(), maxStartYaw.getValue());
                pitchSpeed = (float) RandomUtil.randomBetween(minStartPitch.getValue(), maxStartPitch.getValue());
            } else {
                yawSpeed = (float) RandomUtil.randomBetween(minYaw.getValue(), maxYaw.getValue());
                pitchSpeed = (float) RandomUtil.randomBetween(minPitch.getValue(), maxPitch.getValue());
            }
            final float deltaYaw = (((rotations[0] - PlayerHandler.yaw) + 540) % 360) - 180;
            final float deltaPitch = rotations[1] - PlayerHandler.pitch;
            final float yawDistance = MathHelper.clamp_float(deltaYaw, -yawSpeed, yawSpeed);
            final float pitchDistance = MathHelper.clamp_float(deltaPitch, -pitchSpeed, pitchSpeed);
            final float targetYaw = PlayerHandler.yaw + yawDistance, targetPitch = PlayerHandler.pitch + pitchDistance;
            rotations = RotationUtil.applyMouseFix(targetYaw, targetPitch);
            rotationEvent.setYaw(rotations[0]);
            rotationEvent.setPitch(rotations[1]);
        }
    }

    private static final List<Block> invalidBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest,
            Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.enchanting_table, Blocks.carpet,
            Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice,
            Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch,
            Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore,
            Blocks.iron_ore, Blocks.lapis_ore, Blocks.sand, Blocks.lit_redstone_ore, Blocks.quartz_ore,
            Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
            Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
            Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table, Blocks.red_flower, Blocks.double_plant,
            Blocks.yellow_flower, Blocks.bed, Blocks.ladder, Blocks.waterlily, Blocks.double_stone_slab, Blocks.stone_slab,
            Blocks.double_wooden_slab, Blocks.wooden_slab, Blocks.heavy_weighted_pressure_plate,
            Blocks.light_weighted_pressure_plate, Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.stone_slab2,
            Blocks.double_stone_slab2, Blocks.tripwire, Blocks.tripwire_hook, Blocks.tallgrass, Blocks.dispenser,
            Blocks.command_block, Blocks.web, Blocks.soul_sand);

    @Listen
    public void onJump(PlayerJumpEvent event) {
        if (!tower.isEnabled() || !mc.gameSettings.keyBindJump.pressed)
            return;

        switch (towerMode.getValue()) {
            case "Polar LowJump":
                event.setHeight(0.39);
                break;
            case "Intave":
                event.setHeight(0.37);
                break;
        }
    }

    @Listen
    public final void onUpdate(UpdateEvent updateEvent) {
        getPlayer().setSprinting(false);

        BlockPos under = new BlockPos(mc.thePlayer).down();

        if (mc.thePlayer.onGround && mc.theWorld.isAirBlock(under) && places >= 8 && autoJump.isEnabled()) {
            mc.thePlayer.jump();
            places = -1;
        }

        if (switchItems.getValue()) {
            if ((mc.thePlayer.getHeldItem() != null && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) || mc.thePlayer.getHeldItem() == null) {
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

                    if (stack != null && stack.stackSize != 0 && stack.getItem() instanceof ItemBlock && !invalidBlocks.contains(((ItemBlock) stack.getItem()).getBlock())) {
                        if (lastItem == -1)
                            lastItem = mc.thePlayer.inventory.currentItem;
                        mc.thePlayer.inventory.currentItem = i;
                    }
                }
            }
        }

        if (reverseMovement.getValue()) {
            getGameSettings().keyBindBack.pressed = isKeyDown(getGameSettings().keyBindForward.getKeyCode());
            getGameSettings().keyBindForward.pressed = false;
        }

        if (tower.getValue() && mc.gameSettings.keyBindJump.pressed && mc.thePlayer.fallDistance < 1.5) {
            switch (towerMode.getValue()) {
                case "Vanilla":
                    mc.thePlayer.motionY = 0.42;
                    break;
                case "Verus":
                    if (mc.thePlayer.ticksExisted % 2 == 0)
                        mc.thePlayer.motionY = 0.42;
                    break;
                case "MMC":
                    mc.thePlayer.setSprinting(false);
                    mc.thePlayer.motionY = 0.42;
                    break;
                case "NCP":
                    if (mc.thePlayer.offGroundTicks > 3) {
                        mc.thePlayer.motionY = -0.0784000015258789;
                    }
                    break;
                case "Watchdog": {
                    towerMove();
                }
            }
        }

        if (tower.getValue() && sneak.getValue() && mc.gameSettings.keyBindJump.pressed) {
            mc.thePlayer.setSneaking(false);
            return;
        }

        if (sneak.getValue() && sneakMode.is("Edge")) {
            if (unSneakDelay.getValue() == 0 || unsneakTimeHelper.hasReached(unSneakDelay.getValue())) {
                getGameSettings().keyBindSneak.pressed = false;
            }

            for (int y = -1; y < 0; y++) {
                final Vec3 pos = getPlayer().getPositionVector().addVector(0, y, 0);
                final BlockPos blockPos = new BlockPos(pos);
                if (getWorld().isAirBlock(blockPos)) {
                    getGameSettings().keyBindSneak.pressed = true;
                    unsneakTimeHelper.reset();
                }
            }
        } else if (sneak.getValue() && sneakMode.is("Constant")) {
            getGameSettings().keyBindSneak.pressed = true;
        }
    }

    private void towerMove() {
        if (!this.isMoving()) {
            return;
        }
        if (mc.thePlayer.onGround) {
            if (this.towerTick == 0 || this.towerTick == 5) {
                float f = mc.thePlayer.rotationYaw * (float) (Math.PI / 180);
                mc.thePlayer.motionX -= MathHelper.sin(f) * 0.2f * 75 / 100.0;
                mc.thePlayer.motionY = 0.42;
                mc.thePlayer.motionZ += MathHelper.cos(f) * 0.2f * 75 / 100.0;
                this.towerTick = 1;
            }
        } else if (mc.thePlayer.motionY > -0.0784000015258789) {
            int n = (int) Math.round(mc.thePlayer.posY % 1.0 * 100.0);
            switch (n) {
                case 42:
                    mc.thePlayer.motionY = 0.33;
                    break;
                case 75:
                    mc.thePlayer.motionY = 1.0 - mc.thePlayer.posY % 1.0;
                    this.spoofGround = true;
                    break;
                case 0:
                    mc.thePlayer.motionY = -0.0784000015258789;
                    break;
            }
        }
    }

    @Listen
    public void onSafeWalkEvent(SafeWalkEvent event) {
        if (mc.thePlayer == null && mc.theWorld == null)
            return;

        if (safeWalk.isEnabled())
            event.setSafe(true);
    }

    @Listen
    public void onSilent(SilentMoveEvent silentMoveEvent) {
        if (addStrafe.getValue()) {
            final BlockPos b = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(b).getBlock().getMaterial() == Material.air && mc.currentScreen == null && !Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCodeDefault()) && mc.thePlayer.movementInput.moveForward != 0.0f) {
                if (mc.thePlayer.getHorizontalFacing(PlayerHandler.yaw) == EnumFacing.EAST) {
                    if (b.getZ() + 0.5 > mc.thePlayer.posZ) {
                        mc.thePlayer.movementInput.moveStrafe = 1.0f;
                    } else {
                        mc.thePlayer.movementInput.moveStrafe = -1.0f;
                    }
                } else if (mc.thePlayer.getHorizontalFacing(PlayerHandler.yaw) == EnumFacing.WEST) {
                    if (b.getZ() + 0.5 < mc.thePlayer.posZ) {
                        mc.thePlayer.movementInput.moveStrafe = 1.0f;
                    } else {
                        mc.thePlayer.movementInput.moveStrafe = -1.0f;
                    }
                } else if (mc.thePlayer.getHorizontalFacing(PlayerHandler.yaw) == EnumFacing.SOUTH) {
                    if (b.getX() + 0.5 < mc.thePlayer.posX) {
                        mc.thePlayer.movementInput.moveStrafe = 1.0f;
                    } else {
                        mc.thePlayer.movementInput.moveStrafe = -1.0f;
                    }
                } else if (b.getX() + 0.5 > mc.thePlayer.posX) {
                    mc.thePlayer.movementInput.moveStrafe = 1.0f;
                } else {
                    mc.thePlayer.movementInput.moveStrafe = -1.0f;
                }
            }
        }
    }

    @Listen
    public void onPacket(PacketEvent packetEvent) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            if (tower.isEnabled() && towerMode.is("Watchdog")) {
                if (packetEvent.getPacket() instanceof C03PacketPlayer && spoofGround) {
                    ((C03PacketPlayer) packetEvent.getPacket()).setOnGround(true);
                }
            }
        }
    }

    @Listen
    public void onClicking(ClickingEvent clickingEvent) {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;

        MovingObjectPosition objectOver = mc.objectMouseOver;
        BlockPos blockpos = mc.objectMouseOver.getBlockPos();
        ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();

        if (objectOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK || mc.theWorld.getBlockState(blockpos).getBlock().getMaterial() == Material.air) {
            return;
        }

        if (itemstack != null && !(itemstack.getItem() instanceof ItemBlock)) {
            return;
        }

        if (dragClick.isEnabled() && Math.random() > 0.25)
            sendPacket(new C08PacketPlayerBlockPlacement(itemstack));

        if (this.timeHelper.hasReached(this.delay.getValue())) {
            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemstack, blockpos, objectOver.sideHit, objectOver.hitVec)) {
                if (this.swinging.getValue())
                    mc.thePlayer.swingItem();
                else
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());

                ++places;
            }

            if (itemstack != null && itemstack.stackSize == 0) {
                mc.thePlayer.inventory.mainInventory[mc.thePlayer.inventory.currentItem] = null;
            }

            mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);
            timeHelper.reset();
        }
    }

    @Override
    public String getSuffix() {
        return this.rotations.getValue() + " " + delay.getValue() + "-MS";
    }

    @Override
    public void onEnable() {
        timeHelper.reset();
        startingTimeHelper.reset();
        starting = true;
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        mc.thePlayer.speedInAir = 0.02F;
        mc.timer.timerSpeed = 1;

        if (this.lastItem != -1) {
            mc.thePlayer.inventory.currentItem = this.lastItem;
            this.lastItem = -1;
        }

        getGameSettings().keyBindSneak.pressed = isKeyDown(getGameSettings().keyBindSneak.getKeyCode());
        getGameSettings().keyBindBack.pressed = isKeyDown(getGameSettings().keyBindBack.getKeyCode());
        getGameSettings().keyBindForward.pressed = isKeyDown(getGameSettings().keyBindForward.getKeyCode());

        places = 0;
    }

    private float[] getRotations() {
        BlockPos position = new BlockPos(
                mc.thePlayer.posX,
                mc.thePlayer.posY - 0.5,
                mc.thePlayer.posZ
        );

        switch (this.rotations.getValue()) {
            case "Simple": {
                for (float possibleYaw = mc.thePlayer.rotationYaw - 180; possibleYaw <= mc.thePlayer.rotationYaw + 180; possibleYaw += 45) {
                    for (float possiblePitch = 90; possiblePitch > 70; possiblePitch -= possiblePitch > (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 60 : 80) ? 1 : 10) {
                        final MovingObjectPosition hitBlock = mc.thePlayer.customRayTrace(5, 1.0f, possibleYaw, possiblePitch);

                        if (hitBlock.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                                && BlockUtil.canPlaceAt(hitBlock.getBlockPos())
                                && hitBlock.getBlockPos().equalsBlockPos(this.blockPos)
                                && hitBlock.getBlockPos().getY() <= position.getY()
                        ) {
                            return new float[]{possibleYaw, possiblePitch};
                        }
                    }
                }

                return RotationUtil.getRotation(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
            }
            case "Advanced": {
                float[] rotations = {
                        PlayerHandler.yaw, PlayerHandler.pitch
                };

                if (this.starting) {
                    rotations[0] = mc.thePlayer.rotationYaw - 180.0F;
                    rotations[1] = 80.345F;
                } else {
                    float yaw = mc.thePlayer.rotationYaw - 180.0F;
                    double x = mc.thePlayer.posX;
                    double z = mc.thePlayer.posZ;

                    rotations[0] = yaw;

                    final double add1 = 1.288;
                    final double add2 = 0.288;

                    if (!PlayerUtil.canBuildForward()) {
                        x += mc.thePlayer.posX - this.lastPosition[0];
                        z += mc.thePlayer.posZ - this.lastPosition[2];
                    }

                    this.lastPosition = new double[]{
                            mc.thePlayer.posX,
                            mc.thePlayer.posY,
                            mc.thePlayer.posZ
                    };

                    final double maxX = this.blockPos.getX() + add1;
                    final double minX = this.blockPos.getX() - add2;
                    final double maxZ = this.blockPos.getZ() + add1;
                    final double minZ = this.blockPos.getZ() - add2;

                    if (x > maxX || x < minX || z > maxZ || z < minZ && !mc.thePlayer.onGround) {
                        final List<MovingObjectPosition> hitBlockList = new ArrayList<>();
                        final List<Float> pitchList = new ArrayList<>();

                        for (float pitch = Math.max(PlayerHandler.pitch - 20.0f, -90.0f); pitch < Math.min(PlayerHandler.pitch + 20.0f, 90.0f); pitch += 0.05f) {
                            final float[] rotation = RotationUtil.applyMouseFix(yaw, pitch);
                            final MovingObjectPosition hitBlock = mc.thePlayer.customRayTrace(5, 1.0f, yaw, rotation[1]);

                            if (hitBlock.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                                    && BlockUtil.canPlaceAt(hitBlock.getBlockPos())
                                    && !hitBlockList.contains(hitBlock)
                                    && hitBlock.getBlockPos().equalsBlockPos(this.blockPos)
                                    && hitBlock.sideHit != EnumFacing.DOWN
                                    && hitBlock.sideHit != EnumFacing.UP
                                    && hitBlock.getBlockPos().getY() <= position.getY()) {
                                hitBlockList.add(hitBlock);
                                pitchList.add(rotation[1]);
                            }
                        }

                        hitBlockList.sort(Comparator.comparingDouble(m -> mc.thePlayer.getDistanceSq(m.getBlockPos().add(0.5, 0.5, 0.5))));
                        MovingObjectPosition nearestBlock = null;

                        if (!hitBlockList.isEmpty()) {
                            nearestBlock = hitBlockList.get(0);
                        }

                        if (nearestBlock != null) {
                            rotations[0] = yaw;
                            pitchList.sort(Comparator.comparingDouble(RotationUtil::getDistanceToLastPitch));

                            if (!pitchList.isEmpty()) {
                                rotations[1] = pitchList.get(0);
                            }

                            return rotations;
                        }
                    } else {
                        rotations[1] = PlayerHandler.pitch;
                    }
                }

                return rotations;
            }
            case "Godbridge": {
                float[] allYaw = {
                        45,
                        -45,
                        -135,
                        135
                };

                for (float possibleYaw : allYaw) {
                    for (float possiblePitch = 75.7F; possiblePitch > 30 && possiblePitch < 90.0F; possiblePitch += 0.05F) {
                        final MovingObjectPosition hitBlock = mc.thePlayer.customRayTrace(2, 1.0f, possibleYaw, 75.7F);

                        if (hitBlock.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                                && BlockUtil.canPlaceAt(hitBlock.getBlockPos())
                                && hitBlock.getBlockPos().equalsBlockPos(this.blockPos)
                                && hitBlock.sideHit != EnumFacing.DOWN
                                && hitBlock.sideHit != EnumFacing.UP
                                && hitBlock.getBlockPos().getY() <= position.getY()
                        ) {
                            lastYaw = possibleYaw;
                            lastPitch = possiblePitch;

                            return new float[]{possibleYaw, possiblePitch};
                        }
                    }
                }

                return new float[]{lastYaw, lastPitch};
            }
        }

        return new float[]{0.0F, 0.0F};
    }

}