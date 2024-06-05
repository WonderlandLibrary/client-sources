package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.module.implementations.combat.KillAuraModule;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.MovementUtil;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.MathUtil;
import me.dinozoid.strife.util.system.TimerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "Scaffold", renderName = "Scaffold", aliases = "BlockFly", description = "Place blocks under you.", category = Category.PLAYER)
public class ScaffoldModule extends Module {

    private final EnumProperty<ScaffoldMode> modeProperty = new EnumProperty("Mode", ScaffoldMode.HYPIXEL);
    private final DoubleProperty placeDelayProperty = new DoubleProperty("Place Delay", 25, 1, 1000, 10, Property.Representation.INT);
    private final Property<Boolean> downwardsProperty = new Property("Downwards", true);
    private final Property<Boolean> expandProperty = new Property("Expand", false);
    private final DoubleProperty expandLength = new DoubleProperty("Expand Length", 3, 2, 5, 1, Property.Representation.INT, expandProperty::value);
    private final Property<Boolean> noSwingProperty = new Property("NoSwing", true);
    private final Property<Boolean> keepYProperty = new Property("KeepY", false);

    private final TimerUtil placeTimer = new TimerUtil();

    private double startY;
    private BlockData prevData, data;
    private float[] rotations;

    @Override
    public void init() {
        super.init();
        addValueChangeListener(modeProperty);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        startY = mc.thePlayer.posY - 1;
    }

    @Override
    public void onDisable() {
        mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        super.onDisable();
    }

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        int slot = getSlot();
        if (slot == -1) return;
        if (event.state() == EventState.PRE) {
            switch (modeProperty.value()) {
                case HYPIXEL: {
                    data = getBlockData();
                    if (data != null) {
//                        if(mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                            mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
//                        }
                        rotations = PlayerUtil.getScaffoldRotations(data);
                        event.yaw(rotations[0]);
                        event.pitch(rotations[1]);
                    }
                }
                break;
            }
        } else {
            switch (modeProperty.value()) {
                case HYPIXEL: {
                    mc.thePlayer.setSprinting(false);
                    mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                    int currentSlot = mc.thePlayer.inventory.currentItem;
                    if (data != null && placeTimer.hasElapsed(placeDelayProperty.value().longValue())) {
//                        if(mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                            event.ground(false);
                            mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
//                        }
                        mc.thePlayer.inventory.currentItem = slot - PlayerUtil.ONLY_HOT_BAR_BEGIN;
                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), data.pos, data.face, PlayerUtil.getVectorForRotation(rotations[0], rotations[1]))) {
                            if (noSwingProperty.value())
                                mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
                            else mc.thePlayer.swingItem();
                            prevData = data;
                            data = null;
                            placeTimer.reset();
                        }
                    }
                    mc.thePlayer.inventory.currentItem = currentSlot;
                }
                break;
            }
        }
    });

    private int getSlot() {
        for (int i = PlayerUtil.ONLY_HOT_BAR_BEGIN; i < PlayerUtil.END; ++i) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (PlayerUtil.isValid(stack, false, true) && stack.getItem() instanceof ItemBlock) {
                return i;
            }
        }
        return -1;
    }

    private int blockCount() {
        int blockCount = 0;
        for (int i = PlayerUtil.ONLY_HOT_BAR_BEGIN; i < PlayerUtil.END; ++i) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (PlayerUtil.isValid(stack, false, true)) {
                blockCount += stack.stackSize;
            }
        }
        return blockCount;
    }

    public BlockData getBlockData() {
        BlockPos playersPosition = new BlockPos(mc.thePlayer.getPositionVector()).offset(EnumFacing.DOWN);
        if (keepYProperty.value()) {
            playersPosition = new BlockPos(mc.thePlayer.posX, startY, mc.thePlayer.posZ);
        }
        if (downwardsProperty.value() && Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && mc.thePlayer.onGround && !expandProperty.value()) {
            playersPosition = playersPosition.offset(EnumFacing.DOWN);
            startY -= 1;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        }
        if (expandProperty.value() && mc.thePlayer.onGround && MovementUtil.isMoving() && !mc.thePlayer.movementInput.jump && !mc.thePlayer.movementInput.sneak) {
            playersPosition = playersPosition.add(Math.round(MovementUtil.yawPos(expandLength.value())[0]), 0, Math.round(MovementUtil.yawPos(expandLength.value())[1]));
        }
        for (EnumFacing face : EnumFacing.values()) {
            BlockPos find = playersPosition.offset(face);
            if (!(find.getBlock() instanceof BlockAir)) {
                return new BlockData(find, getInverted(face));
            }
        }
        for (EnumFacing face : EnumFacing.values()) {
            BlockPos offsetPos = playersPosition.offset(face);
            for (EnumFacing face2 : EnumFacing.values()) {
                if (face2 == EnumFacing.DOWN || face2 == EnumFacing.UP) {
                    continue;
                }
                BlockPos offset = offsetPos.offset(face2);
                if (offset.getBlock().getMaterial() != Material.air) {
                    return new BlockData(offset, getInverted(face));
                }
            }
        }
        return null;
    }

    private Vec3 vec3(BlockData data) {
        double x = data.pos.getX() + 0.5D;
        double y = data.pos.getY() + 0.5D;
        double z = data.pos.getZ() + 0.5D;
        x += data.face.getFrontOffsetX() / 2D;
        y += data.face.getFrontOffsetY() / 2D;
        z += data.face.getFrontOffsetZ() / 2D;
        if (data.face != EnumFacing.UP && data.face != EnumFacing.DOWN) {
            y += MathUtil.random(0.5D, 0.49D);
        } else {
            x += MathUtil.random(0.25D, -0.25D);
            z += MathUtil.random(0.25D, -0.25D);
        }
        if (data.face == EnumFacing.WEST || data.face == EnumFacing.EAST)
            z += MathUtil.random(0.25D, -0.25D);
        if (data.face == EnumFacing.SOUTH || data.face == EnumFacing.NORTH)
            x += MathUtil.random(0.25D, -0.25D);
        return new Vec3(x, y, z);
    }

    public static EnumFacing getInverted(EnumFacing face) {
        EnumFacing invert = null;
        switch (face) {
            case NORTH:
                invert = EnumFacing.SOUTH;
                break;
            case SOUTH:
                invert = EnumFacing.NORTH;
                break;
            case UP:
                invert = EnumFacing.DOWN;
                break;
            case DOWN:
                invert = EnumFacing.UP;
                break;
            case EAST:
                invert = EnumFacing.WEST;
                break;
            case WEST:
                invert = EnumFacing.EAST;
                break;
        }
        return invert;
    }

    public static ScaffoldModule instance() {
        return StrifeClient.INSTANCE.moduleRepository().moduleBy(ScaffoldModule.class);
    }

    public final class BlockData {
        public BlockPos pos;
        public EnumFacing face;

        public BlockData(BlockPos pos, EnumFacing facing) {
            this.pos = pos;
            this.face = facing;
        }
    }

    private enum ScaffoldMode {
        HYPIXEL
    }

}
