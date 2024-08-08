package lol.point.returnclient.module.impl.player;

import lol.point.returnclient.events.impl.packet.EventPacket;
import lol.point.returnclient.events.impl.player.EventMotion;
import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.util.minecraft.*;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;

@ModuleInfo(
        name = "Scaffold",
        description = "Makes you place blocks",
        category = Category.PLAYER
)
public class Scaffold extends Module {

    private final StringSetting rotations = new StringSetting("Rotations", new String[]{"None", "Hypixel", "GodBridge", "Sneak", "Vulcan"});
    private final StringSetting swap = new StringSetting("Swap", new String[]{"Switch", "None"});
    private final StringSetting tower = new StringSetting("Tower", new String[]{"None", "Hypixel", "Vanilla", "Vulcan", "Verus", "NCP"});
    private final StringSetting sprint = new StringSetting("Sprint", new String[]{"None", "Vanilla", "Hypixel", "Bypass", "Hypixel NonSprint"});

    private BlockData blockData;
    private int serverSlot, prevSlot;
    private float[] targetRotations;
    private boolean noGround;

    private int blocks;
    public boolean stopped;

    private boolean shouldJump = true;
    private double startY;

    private boolean hypixelSprint;
    private int blocksPlaced, airTicks, groundTicks;

    private int tickCounter;
    private int ticks;

    private float angle;

    public Scaffold() {
        addSettings(rotations, swap, tower, sprint);
    }

    public String getSuffix() {
        return rotations.value;
    }

    public void onEnable() {
        if (mc.thePlayer == null) {
            return;
        }

        startY = mc.thePlayer.posY - 1;

        mc.thePlayer.jump();

        tickCounter = 0;
        angle = mc.thePlayer.rotationYaw;

        if (!mc.thePlayer.onGround) {
            ticks = 100;
        }
        if (isOnHypixel()) {
            if (this.blocks <= 0) {
                this.stopped = false;
                this.blocks = 0;
                mc.gameSettings.keyBindSprint.pressed = true;
                mc.thePlayer.setSprinting(true);
            } else {
                this.stopped = true;
            }
        }
        prevSlot = mc.thePlayer.inventory.currentItem;
    }

    public void onDisable() {
        if (mc.thePlayer == null) return;

        switch (swap.value) {
            case "Switch" -> {
                if (mc.thePlayer.inventory.currentItem != prevSlot) {
                    mc.thePlayer.inventory.currentItem = prevSlot;
                }
            }
        }
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        final int blockSlot = ItemUtil.getBlockSlot();

        if (blockSlot == -1) return;

        switch (swap.value) {
            case "Switch" -> {
                if (mc.thePlayer.inventory.currentItem != blockSlot) {
                    mc.thePlayer.inventory.currentItem = blockSlot;
                }
            }
        }

        blockData = getBlockData(new BlockPos(mc.thePlayer.posX, sprint.is("KeepY") ? startY + 1 : mc.thePlayer.posY - 1, mc.thePlayer.posZ));

        if (blockData != null && serverSlot == blockSlot) {
            final ItemStack placementStack = mc.thePlayer.getHeldItem();

            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, placementStack, blockData.getBlockPos(), blockData.getEnumFacing(), getHitVector())) {
                mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
            }

            for (int i = 0; i < 9; i++) {
                final ItemStack stack = mc.thePlayer.inventory.mainInventory[i];
                if (stack != null && stack.stackSize == 0) {
                    mc.thePlayer.inventory.mainInventory[i] = null;
                }
            }
        }

        blocksPlaced++;

        if (sprint.is("KeepY")) {
            if (MoveUtil.isMoving() && blocksPlaced > 2 && !mc.gameSettings.keyBindJump.isKeyDown()) {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }

                System.out.println((startY - 3) + " " + (startY - 2) + " " + (startY - 1) + " " + startY + " " + (startY + 1) + (startY + 2) + " " + (startY + 3));

                if (mc.thePlayer.motionY + mc.thePlayer.posY < startY + 2 && mc.thePlayer.motionY < -0.15) {
                    System.out.println("pos Y checked");

                    BlockData blockData = getBlockData(new BlockPos(mc.thePlayer.posX, startY + 3, mc.thePlayer.posZ));
                    if (blockData != null) {
                        System.out.println("blockData is not null");

                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), blockData.blockPos, blockData.enumFacing, getNewVector(blockData))) {
                            System.out.println("placing the Block");

                            mc.thePlayer.swingItem();
                            hypixelSprint = true;
                        }
                    }
                }
            }

            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), hypixelSprint);
            mc.thePlayer.setSprinting(hypixelSprint);
        }
    });

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(eventMotion -> {
        if (eventMotion.isPre()) {
            switch (sprint.value) {
                case "none" -> {
                    mc.gameSettings.keyBindSprint.pressed = false;
                    mc.thePlayer.setSprinting(false);
                }
                case "Vanilla" -> {
                    mc.gameSettings.keyBindSprint.pressed = true;
                    mc.thePlayer.setSprinting(true);
                }
                case "HypixelNonSprint" -> {
                    mc.thePlayer.setSprinting(false);
                    mc.thePlayer.motionX *= 0.97;
                    mc.thePlayer.motionZ *= 0.97;
                    if (mc.gameSettings.keyBindSprint.isKeyDown()) {
                        mc.thePlayer.motionX *= 0.733;
                        mc.thePlayer.motionZ *= 0.733;
                    }
                }
            }

            switch (rotations.value) {
                case "Hypixel" -> {
                    targetRotations = new float[]{(MoveUtil.getDirection() - 180), mc.gameSettings.keyBindJump.isKeyDown() ? 90 : 78};

                    eventMotion.yaw = targetRotations[0];
                    eventMotion.pitch = targetRotations[1];

                    mc.thePlayer.rotationYawHead = targetRotations[0];
                    mc.thePlayer.renderYawOffset = targetRotations[0];
                    mc.thePlayer.rotationPitchHead = targetRotations[1];
                }
                case "Disabled" -> {
                    eventMotion.yaw = mc.thePlayer.rotationYaw;
                    eventMotion.pitch = mc.thePlayer.rotationPitch;

                    mc.thePlayer.rotationYawHead = mc.thePlayer.rotationYaw;
                    mc.thePlayer.renderYawOffset = mc.thePlayer.rotationYaw;
                    mc.thePlayer.rotationPitchHead = mc.thePlayer.rotationPitch;
                }
                case "Vulcan" -> {
                    targetRotations = new float[]{(MoveUtil.getDirection() - 180), mc.gameSettings.keyBindJump.isKeyDown() ? 90 : 90};

                    eventMotion.yaw = targetRotations[0];
                    eventMotion.pitch = targetRotations[1];

                    mc.thePlayer.rotationYawHead = targetRotations[0];
                    mc.thePlayer.renderYawOffset = targetRotations[0];
                    mc.thePlayer.rotationPitchHead = targetRotations[1];
                }
            }

            switch (tower.value) {
                case "Hypixel" -> {
                    if (!mc.gameSettings.keyBindJump.isKeyDown() && MoveUtil.isMoving()) {
                        angle = mc.thePlayer.rotationYaw;
                        ticks = 100;
                        return;
                    }

                    tickCounter++;
                    ticks++;

                    if (tickCounter >= 23) {
                        tickCounter = 1;
                        angle = mc.thePlayer.rotationYaw;
                        ticks = 100;
                    }

                    if (mc.thePlayer.onGround) {
                        ticks = 0;
                    }

                    float step = ticks == 1 ? 90 : 0;

                    if (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - angle) < step) {
                        angle = mc.thePlayer.rotationYaw;
                    } else if (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - angle) < 0) {
                        angle -= step;
                    } else if (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - angle) > 0) {
                        angle += step;
                    }

                    mc.thePlayer.rotationYaw = angle;

                    if (tickCounter < 20) {
                        MoveUtil.strafe(.26);
                        if (mc.gameSettings.keyBindJump.isKeyDown()) {
                            switch (ticks) {
                                case 0:
                                    if (mc.thePlayer.posY % 1 == 0) {
                                        eventMotion.onGround = true;
                                    }

                                    mc.thePlayer.motionY = 0.42f;
                                    break;

                                case 1:
                                    mc.thePlayer.motionY = 0.33;
                                    break;

                                case 2:
                                    mc.thePlayer.motionY = 1 - mc.thePlayer.posY % 1;
                                    break;
                            }
                        }
                    }

                    if (ticks == 2) ticks = -1;
                }
            }

            if (isOnHypixel()) {
                if (mc.thePlayer.onGround) {
                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        mc.thePlayer.motionX *= 1.01 - Math.random() / 100f;
                        mc.thePlayer.motionZ *= 1.01 - Math.random() / 100f;
                    } else {
                        mc.thePlayer.setSprinting(sprint.is("Hypixel"));
                        mc.gameSettings.keyBindSprint.pressed = sprint.is("Hypixel");
                    }
                } else {
                    mc.thePlayer.setSprinting(sprint.is("Hypixel"));
                    mc.gameSettings.keyBindSprint.pressed = sprint.is("Hypixel");
                }

                if (!mc.thePlayer.isSprinting()) this.stopped = true;
            }

            if (sprint.is("Hypixel")) {
                if (mc.thePlayer.onGround) {
                    if (shouldJump) {
                        mc.thePlayer.jump();
                        shouldJump = false;
                    }
                }
            }
        }
        switch (tower.value) {
            case "Verus" -> {
                if (mc.thePlayer.onGround) {
                    airTicks = 0;
                    groundTicks++;
                } else {
                    airTicks++;
                }
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (mc.thePlayer.ticksExisted % 3 == 0) {
                        mc.thePlayer.motionY = .6f;
                    }
                }
            }
            case "Vulcan" -> {
                if (mc.thePlayer.onGround) {
                    airTicks = 0;
                    groundTicks++;
                } else {
                    airTicks++;
                }
                if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.onGround) {
                    MoveUtil.setSpeed(0.215);
                mc.thePlayer.motionY = 0.42f;
                }
                if(airTicks == 5){
                    mc.thePlayer.motionY = -2f;
                }
            }
        }
    });

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(eventPacket -> {
        final Packet<?> packet = eventPacket.packet;

        Packet<?> p = eventPacket.packet;

        if (eventPacket.isOutbound()) {
            if (p instanceof C08PacketPlayerBlockPlacement wrapper) {
                if (wrapper.getPlacedBlockDirection() != 255) {
                    if (mc.thePlayer.isSprinting()) {
                        this.blocks++;
                    } else {
                        this.blocks -= 2;
                    }
                }
            }
        }

        if (packet instanceof C09PacketHeldItemChange) {
            serverSlot = ((C09PacketHeldItemChange) packet).getSlotId();
        } else if (packet instanceof C03PacketPlayer c03PacketPlayer) {
            if (noGround) {
                c03PacketPlayer.setOnGround(false);
                noGround = false;
            }
        }
    });

    private static final class BlockData {
        private final BlockPos blockPos;
        private final EnumFacing enumFacing;

        public BlockData(BlockPos blockPos, EnumFacing enumFacing) {
            this.blockPos = blockPos;
            this.enumFacing = enumFacing;
        }

        public BlockPos getBlockPos() {
            return blockPos;
        }

        public EnumFacing getEnumFacing() {
            return enumFacing;
        }
    }

    public static BlockData getBlockData(BlockPos pos) {
        if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
            for (int x = 0; x < 4; x++) {
                for (int z = 0; z < 4; z++) {
                    for (int i = 1; i > -3; i -= 2) {
                        final BlockPos blockPos = pos.add(x * i, 0, z * i);
                        if (mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockAir) {
                            for (EnumFacing direction : EnumFacing.values()) {
                                final BlockPos block = blockPos.offset(direction);
                                final Material material = mc.theWorld.getBlockState(block).getBlock().getMaterial();
                                if (material.isSolid() && !material.isLiquid()) {
                                    return new Scaffold.BlockData(block, direction.getOpposite());
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Vec3 getNewVector(BlockData lastblockdata) {
        if (lastblockdata == null) {
            return null;
        }
        BlockPos pos = lastblockdata.blockPos;
        EnumFacing facing = lastblockdata.enumFacing;
        Vec3 vec3 = new Vec3(pos);

        double amount1 = 0.45 + Math.random() * 0.1;
        double amount2 = 0.45 + Math.random() * 0.1;

        if (facing == EnumFacing.UP) {
            vec3 = vec3.addVector(amount1, 1, amount2);
        } else if (facing == EnumFacing.DOWN) {
            vec3 = vec3.addVector(amount1, 0, amount2);
        } else if (facing == EnumFacing.EAST) {
            vec3 = vec3.addVector(1, amount1, amount2);
        } else if (facing == EnumFacing.WEST) {
            vec3 = vec3.addVector(0, amount1, amount2);
        } else if (facing == EnumFacing.NORTH) {
            vec3 = vec3.addVector(amount1, amount2, 0);
        } else if (facing == EnumFacing.SOUTH) {
            vec3 = vec3.addVector(amount1, amount2, 1);
        }

        return vec3;
    }

    private Vec3 getHitVector() {
        Vec3 hitVec = new Vec3(blockData.getBlockPos().getX() + Math.random(), blockData.getBlockPos().getY() + Math.random(), blockData.getBlockPos().getZ() + Math.random());

        if (targetRotations == null) return hitVec;

        return hitVec;
    }

    public boolean isOnHypixel() {
        return !mc.isSingleplayer() && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
    }

}
