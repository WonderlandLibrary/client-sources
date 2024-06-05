package client.metaware.impl.module.player;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.event.impl.render.Render3DEvent;
import client.metaware.impl.module.movmeent.Speed;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.system.TimerUtil;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.ScaffoldUtils;
import client.metaware.impl.utils.util.other.MathUtils;
import client.metaware.impl.utils.util.other.PlayerUtil;
import client.metaware.impl.utils.util.player.MovementUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "Scaffold", renderName = "Scaffold", aliases = "BlockFly", description = "Place blocks under you.", category = Category.PLAYER)
public class Scaffold extends Module {

    private final EnumProperty<ScaffoldMode> modeProperty = new EnumProperty<>("Mode", ScaffoldMode.HYPIXEL);
    private final DoubleProperty placeDelayProperty = new DoubleProperty("Place Delay", 25, 1, 1000, 10, Property.Representation.INT);
    private final Property<Boolean> timerSpeedProperty = new Property<>("Timer Speed", true);
    private final DoubleProperty timerValueProperty = new DoubleProperty("Timer Value", 1.65, 1.0, 2.7, 0.05, Property.Representation.DOUBLE, timerSpeedProperty::getValue);
    private final Property<Boolean> downwardsProperty = new Property<>("Downwards", true);
    private final Property<Boolean> sprintProperty = new Property<>("Sprint Bypass", false);
    private final Property<Boolean> expandProperty = new Property<>("Expand", false);
    private final DoubleProperty expandLength = new DoubleProperty("Expand Length", 3, 2, 5, 1, Property.Representation.INT, expandProperty::getValue);
    private final Property<Boolean> noSwingProperty = new Property<>("NoSwing", true);
    private final Property<Boolean> keepYProperty = new Property<>("KeepY", false);

    private static final BlockPos[] BLOCK_POSITIONS = new BlockPos[] { new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
    private static final EnumFacing[] FACINGS = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH };

    private final TimerUtil placeTimer = new TimerUtil();

    private double startY;
    private int placed, slot, newSlot, oldSlot, disableSlot;;
    private BlockData prevData, data;
    private BlockPos blockUnder;
    private float lastYaw = 0.0f, lastPitch = 0.0f;
    private float[] rotations;
    public List<Block> blockBlacklist = Arrays.asList(Blocks.air, Blocks.yellow_flower, Blocks.water, Blocks.tnt, Blocks.chest,
            Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.enchanting_table, Blocks.carpet,
            Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice,
            Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch,
            Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore,
            Blocks.iron_ore, Blocks.lapis_ore, Blocks.sand, Blocks.lit_redstone_ore, Blocks.quartz_ore,
            Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
            Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
            Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table, Blocks.beacon, Blocks.rail, Blocks.detector_rail,
            Blocks.activator_rail, Blocks.golden_rail, Blocks.ladder, Blocks.ender_chest, Blocks.end_portal_frame,
            Blocks.vine, Blocks.waterlily, Blocks.double_plant, Blocks.deadbush, Blocks.red_flower, Blocks.yellow_flower,
            Blocks.torch, Blocks.redstone_torch, Blocks.unlit_redstone_torch, Blocks.brown_mushroom, Blocks.brown_mushroom,
            Blocks.anvil, Blocks.redstone_wire, Blocks.powered_repeater, Blocks.unpowered_repeater, Blocks.powered_comparator,
            Blocks.unpowered_comparator, Blocks.noteblock, Blocks.dispenser, Blocks.dropper, Blocks.hopper, Blocks.tripwire,
            Blocks.tripwire_hook, Blocks.heavy_weighted_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.stone_pressure_plate,
            Blocks.wooden_pressure_plate, Blocks.trapdoor, Blocks.iron_trapdoor, Blocks.crafting_table);

    @Override
    public void onEnable() {
        super.onEnable();
        placed = 0;
        startY = mc.thePlayer.posY - 1;
        disableSlot = mc.thePlayer.inventory.currentItem;
        mc.thePlayer.setSprinting(false);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        mc.gameSettings.keyBindSneak.pressed = false;
        mc.thePlayer.inventory.currentItem = disableSlot;
        data = null;
        super.onDisable();
    }


    @EventHandler
    private final Listener<Render3DEvent> render3DListener = event -> {
        double x = RenderUtil.interpolateScale(mc.thePlayer.posX, mc.thePlayer.lastTickPosX, event.getPartialTicks()) - mc.getRenderManager().viewerPosX;
        double y = RenderUtil.interpolateScale(mc.thePlayer.posY, mc.thePlayer.lastTickPosY, event.getPartialTicks()) - mc.getRenderManager().viewerPosY;
        double z = RenderUtil.interpolateScale(mc.thePlayer.posZ, mc.thePlayer.lastTickPosZ, event.getPartialTicks()) - mc.getRenderManager().viewerPosZ;
        glPushMatrix();
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_LINE_STIPPLE);
        glEnable(GL_LINE_SMOOTH);
        glDepthMask(false);
        glLineWidth(5);
        int vertices = 12;
        float radius = 0.4f;
        float yOffset = 0.3f;
        glBegin(GL_LINE_LOOP);
        Color color = new Color(0, 255, 0);
        if (getBlockCount() > 128) {
            color = new Color(0, 255, 0);
        }
        if (getBlockCount() < 128) {
            color = new Color(255, 255, 0);
        }
        if (getBlockCount() < 64) {
            color = new Color(255, 0, 0);
        }
        for (int i = 0; i < vertices; i++) {
            double sin = radius * Math.sin((2 * Math.PI) * i / vertices);
            double cos = radius * Math.cos((2 * Math.PI) * i / vertices);
            RenderUtil.color(color);
            glVertex3d(x + sin / 2, y + yOffset, z + cos / 2);
            glVertex3d(x + sin / 3, y + yOffset, z + cos / 3);
            glVertex3d(x + sin, y + yOffset, z + cos);
            i++;
        }
        glEnd();
        glBegin(GL_LINE_LOOP);
        radius = 0.135f;
        for (int i = 0; i < vertices; i++) {
            double sin = radius * Math.sin((2 * Math.PI) * i / vertices);
            double cos = radius * Math.cos((2 * Math.PI) * i / vertices);
            RenderUtil.color(color);
            glVertex3d(x + sin, y + yOffset, z + cos);
            i++;
        }
        glEnd();
        glDepthMask(true);
        glDisable(GL_LINE_SMOOTH);
        glDisable(GL_LINE_STIPPLE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glPopMatrix();
    };

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = event -> {
//        if (slot == -1) {
////            toggle();
//            return;
//        }

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && !mc.thePlayer.movementInput.jump && !Metaware.INSTANCE.getModuleManager().getModuleByClass(Speed.class).isToggled()) {
            mc.thePlayer.motionX *= 0.818f;
            mc.thePlayer.motionZ *= 0.818f;
        }
        int tempSlot = getSlot();
        slot = -1;
        if (tempSlot != -1) {
            newSlot = getSlot();
            oldSlot = mc.thePlayer.inventory.currentItem;
            mc.thePlayer.inventory.currentItem = newSlot;
        }
        if(!mc.thePlayer.onGround)
            mc.timer.timerSpeed = 1.0f;
        if (event.isPre()) {
            switch (modeProperty.getValue()) {
                case HYPIXEL: {
//                    if(placed == 3 && MovementUtil.isMovingOnGround()){
//                        mc.thePlayer.motionY = 0.12;
//                        placed = 0;
//                    }

                    if (mc.thePlayer.movementInput.jump) placed = 0;
//                    if(placed == 3){
//                       mc.gameSettings.keyBindSneak.pressed = true;
//                    }else if(placed > 3){
//                        mc.gameSettings.keyBindSneak.pressed = false;
//                       placed = 0;
//                    }
//                    PlayerUtil.sendMessage("Placed: " + placed);
                    if (sprintProperty.getValue())
                        mc.getNetHandler().addToSendQueueNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    if (!mc.gameSettings.keyBindJump.pressed)
                        mc.timer.timerSpeed = timerSpeedProperty.getValue() ? (float) (timerValueProperty.getValue() + (Math.random() / 500L)) : 1.0f;
//                   if(data != null){
//                       float[] rotations = getScaffoldRotations(data.pos, data.face);
//                       float Sensitivity = getSensitivityMultiplier();
//                       float yaw = (float) (rotations[0] + ThreadLocalRandom.current().nextDouble(-0.25F, 0.25F));
//                       float pitch = (float) (rotations[1] - ThreadLocalRandom.current().nextDouble(-0.25F, 0.25F));
//                       float yawSENS = (Math.round(yaw / Sensitivity) * Sensitivity);
//                       float pitchSENS = (Math.round(pitch / Sensitivity) * Sensitivity);
//                       event.setYaw(yawSENS);
//                       event.setPitch(pitchSENS);
//                       lastYaw = event.getYaw();
//                       lastPitch = event.getPitch();
//                   }
//                    if (mc.gameSettings.keyBindJump.isKeyDown() && MovementUtils.isOnGround(0.3)) {
//                        double n = event.getPosY() % 1.0;
//                        double n2 = down(event.getPosY());
//                        List<Object> list = Arrays.asList(new Double[]{(double) (0.419998F - 1.67E-18F), 0.7531999805212});
//                        if (n > 0.419 && n < 0.753) {
//                            event.setPosY(n2 + (Double) list.get(0));
//                        } else if (n > 0.753) {
//                            event.setPosY(n2 + (Double) list.get(1));
//                        } else {
//                            mc.thePlayer.motionY = 0.419998F - 1.67E-18F;
//                            mc.thePlayer.motionY *= 0.98;
//                            event.setPosY(n2);
//                        }
//                        if (!mc.thePlayer.isMoving()) {
//                          // if(timerSpeedProperty.getValue()) mc.timer.timerSpeed = (float) (2.7 + (Math.random() / 50L));
//                            mc.thePlayer.motionX = 0;
//                            mc.thePlayer.motionZ = 0;
//                            event.setPosX(event.getPosX() + (mc.thePlayer.ticksExisted % 2 == 0 ? ThreadLocalRandom.current().nextDouble(0.08, 0.0834) : -ThreadLocalRandom.current().nextDouble(0.08, 0.0834)));
//                            event.setPosZ(event.getPosZ() + (mc.thePlayer.ticksExisted % 2 != 0 ? ThreadLocalRandom.current().nextDouble(0.08, 0.0834) : -ThreadLocalRandom.current().nextDouble(0.08, 0.0834)));
//                        }
//                        if(!mc.thePlayer.isMoving()){
//                            if (!mc.thePlayer.isMoving()) {
//                                mc.thePlayer.motionZ *= 0.0;
//                                mc.thePlayer.motionX *= 0.0;
//                                if (mc.thePlayer.ticksExisted % 8 == 0)
//                                    mc.thePlayer.motionY = 0.42F - 1.6643E-12F;
//                            }
//                        }\

                        if (mc.gameSettings.keyBindJump.pressed && !mc.thePlayer.isPotionActive(Potion.jump)) {
                            MovementUtils.setSpeed(event, 0.103252);
                            if (!mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
                                mc.thePlayer.motionZ *= 0.0;
                                mc.thePlayer.motionX *= 0.0;
//                            if (isOnGround(0.35)) {
                                mc.thePlayer.motionY += 0.449;
                                mc.timer.timerSpeed = 1.2f;
//                            } else {
//                                mc.thePlayer.motionY -= 0.105F + (Math.random() / 500);
//                            }
                            } else {
                                if (mc.thePlayer.onGround && MovementUtils.isOnGround(0.325)) {
                                    mc.thePlayer.motionY = 0.42F;
                                } else if (mc.thePlayer.motionY < 0.17D && mc.thePlayer.motionY > 0.16D) {
                                    mc.thePlayer.motionY = -0.180012412f;
                                }
                            }
                        }


//                    }

                    blockUnder = getBlockUnder();
                    data = getBlockData();
                    if (data != null) {
                        if (data.hitVec != null) {
                            rotations = getBlockRotations(data.pos, data.face);
                        } else {
                            data = null;
                        }
                    }
                    if (rotations != null) {
                        float sensitivity = MovementUtils.getSensitivityMultiplier();
                        float yaw = (float) (rotations[0] + ThreadLocalRandom.current().nextDouble(-0.15F, 0.15F));
                        float pitch = (float) (rotations[1] - ThreadLocalRandom.current().nextDouble(-0.15F, 0.15F));
                        float yawSENS = (Math.round(yaw / sensitivity) * sensitivity);
                        float pitchSENS = (Math.round(pitch / sensitivity) * sensitivity);
                        event.setYaw(MovementUtils.getMovementDirection() - 180);
                        event.setPitch(pitchSENS);
                    }
                }
                break;
            }
        } else {
            switch (modeProperty.getValue()) {
                case HYPIXEL: {
//                    mc.thePlayer.setSprinting(false);
//                    int currentSlot = mc.thePlayer.inventory.currentItem;
//                    if (data != null && placeTimer.delay(placeDelayProperty.getValue().longValue()) && rotations != null) {
//                        mc.thePlayer.inventory.currentItem = slot - PlayerUtil.ONLY_HOT_BAR_BEGIN;
//                        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), data.pos, data.face,
//                                new Vec3(getVec3d(data.pos, data.face).getX(), getVec3d(data.pos, data.face).getY(), getVec3d(data.pos, data.face).getZ()));
//                            placed++;
//                            if (sprintProperty.getValue())
//                                mc.getNetHandler().addToSendQueueNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
//                            if (noSwingProperty.getValue())
//                                mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
//                            else mc.thePlayer.swingItem();
//                            prevData = data;
//                            placeTimer.reset();
//                    }
//                    mc.thePlayer.inventory.currentItem = currentSlot;
//                }
                    mc.thePlayer.setSprinting(false);
                    int currentSlot = mc.thePlayer.inventory.currentItem;
                    if (data != null && validateReplaceable(data)) {
                        if (!mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                            event.setOnGround(false);
                        }
                        mc.thePlayer.inventory.currentItem = newSlot;
                        if (mc.playerController.onPlayerRightClick3d(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(),
                                data.pos, data.face, getVec3d(data.pos, data.face))) {
                            if (sprintProperty.getValue())
                                mc.getNetHandler().addToSendQueueNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                            if (noSwingProperty.getValue())
                                mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
                            else mc.thePlayer.swingItem();
                            mc.thePlayer.inventory.currentItem = newSlot;
                            MovementUtils.setSpeed(event, 0.04);
                        }
//                        placeTimer.reset();
                    }
                }
                break;
            }
        }
    };

    private Vec3 isLookingAtBlock(final BlockData data, final float yaw, final float pitch) {
        final Vec3 src = mc.thePlayer.getPositionEyes(1.0F);
        final Vec3 rotationVec = Entity.getVectorForRotation(pitch, yaw);
        final float reach = mc.playerController.getBlockReachDistance();
        final Vec3 dest = src.addVector(rotationVec.xCoord * reach, rotationVec.yCoord * reach, rotationVec.zCoord * reach);
        final MovingObjectPosition rayTraceResult = mc.theWorld.rayTraceBlocks(src, dest, false, false, true);
        if (rayTraceResult == null) return null;
        if (rayTraceResult.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) return null;
        final BlockPos dstPos = data.pos;
        final BlockPos rayDstPos = rayTraceResult.getBlockPos();
        if (rayDstPos.getX() != dstPos.getX() ||
                rayDstPos.getY() != dstPos.getY() ||
                rayDstPos.getZ() != dstPos.getZ()) return null;
        if (rayTraceResult.sideHit != data.face) return null;
        return rayTraceResult.hitVec;
    }

    private static BlockPos getBlockUnder() {
        final EntityPlayerSP player = mc.thePlayer;
        return new BlockPos(player.posX, player.posY - 1.0, player.posZ);
    }

    public static int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && ScaffoldUtils.canIItemBePlaced(item)) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }


    private int down(double n) {
        int n2 = (int)n;
        try {
            if (n < (double)n2) {
                return n2 - 1;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return n2;
    }

    private int getSlot() {
//        for (int i = PlayerUtil.ONLY_HOT_BAR_BEGIN; i < PlayerUtil.END; ++i) {
//            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
//            if (PlayerUtil.isValid(stack, false, true) && stack.getItem() instanceof ItemBlock) {
//                return i;
//            }
//        }
//        return -1;
        for (int i = 36; i < 45; ++i) {
            ItemStack item;
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()
                    || !isValid(item = mc.thePlayer.inventoryContainer.getSlot(i).getStack()))
                continue;
            return i - 36;
        }
        return -1;
    }

    public boolean isEmpty(ItemStack stack) {
        return stack == null;
    }

    public boolean isValid(ItemStack item) {
        if (isEmpty(item)) {
            return false;
        }
        if (item.getUnlocalizedName().equalsIgnoreCase("tile.chest")) {
            return false;
        }
        if (!(item.getItem() instanceof ItemBlock)) {
            return false;
        }
        return !blockBlacklist.contains(((ItemBlock) item.getItem()).getBlock());
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

    private float[] getBlockRotations(final BlockPos blockPos, final EnumFacing enumFacing) {
        final Vec3 positionEyes = mc.thePlayer.getPositionEyes(2.0f);
        final Vec3 add = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5).add(new Vec3(enumFacing.getDirectionVec()).scale(0.49f));
        final double n = add.xCoord - positionEyes.xCoord;
        final double n2 = add.yCoord - positionEyes.yCoord;
        final double n3 = add.zCoord - positionEyes.zCoord;
        return new float[]{(float) (Math.atan2(n3, n) * 180.0 / Math.PI - 90.0), -(float) (Math.atan2(n2, (float) Math.hypot(n, n3)) * 180.0 / Math.PI)};
    }

    private static boolean validateBlockRange(final BlockData data) {
        final Vec3 pos = data.hitVec;
        if (pos == null) {
            return false;
        }
        final EntityPlayerSP player = mc.thePlayer;
        final double x = pos.xCoord - player.posX;
        final double y = pos.yCoord - (player.posY + player.getEyeHeight());
        final double z = pos.zCoord - player.posZ;
        return StrictMath.sqrt(x * x + y * y + z * z) <= 4.0;
    }

    private static boolean validateReplaceable(final BlockData data) {
        final BlockPos pos = data.pos.offset(data.face);
        final World world = mc.theWorld;
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    public Vec3d getVec3d(BlockPos pos, EnumFacing face) {
        double x = pos.getX() + 0.412041452 + MathUtils.randomDoubleValue();
        double y = pos.getY() + 0.412041452 + MathUtils.randomDoubleValue();
        double z = pos.getZ() + 0.412041452 + MathUtils.randomDoubleValue();
        x += (double) face.getFrontOffsetX() / 2;
        z += (double) face.getFrontOffsetZ() / 2;
        y += (double) face.getFrontOffsetY() / 2;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += MathUtils.randomNumber(0.412041452 + MathUtils.randomDoubleValue(), -0.412041452);
            z += MathUtils.randomNumber(0.412041452 + MathUtils.randomDoubleValue(),
                    -0.402645276 + MathUtils.randomDoubleValue());
        } else {
            y += MathUtils.randomNumber(0.412041452 + MathUtils.randomDoubleValue(),
                    -0.412041452 + MathUtils.randomDoubleValue());
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += MathUtils.randomNumber(0.412041452 + MathUtils.randomDoubleValue(),
                    -0.412041452 + MathUtils.randomDoubleValue());
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += MathUtils.randomNumber(0.412041452 + MathUtils.randomDoubleValue(),
                    -0.412041452 + MathUtils.randomDoubleValue());
        }
        return new Vec3d(x, y, z);
    }

    public BlockData getBlockData() {
        BlockPos playersPosition = new BlockPos(mc.thePlayer.getPositionVector()).offset(EnumFacing.DOWN);
            if (keepYProperty.getValue()) {
                playersPosition = new BlockPos(mc.thePlayer.posX, startY, mc.thePlayer.posZ);
            }
            if (downwardsProperty.getValue() && Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && mc.thePlayer.onGround && !expandProperty.getValue()) {
                playersPosition = playersPosition.offset(EnumFacing.DOWN);
                startY -= 1;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
            }
            if (expandProperty.getValue() && mc.thePlayer.onGround && mc.thePlayer.isMoving() && !mc.thePlayer.movementInput.jump && !mc.thePlayer.movementInput.sneak) {
                playersPosition = playersPosition.add(Math.round(MovementUtils.yawPos(expandLength.getValue())[0]), 0, Math.round(MovementUtils.yawPos(expandLength.getValue())[1]));
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

    public final class BlockData {
        public BlockPos pos;
        public Vec3 hitVec;
        public EnumFacing face;

        public BlockData(BlockPos pos, EnumFacing facing) {
            this.pos = pos;
            this.face = facing;
            this.hitVec = getHitVec();
        }

        private Vec3 getHitVec() {
            final Vec3i directionVec = this.face.getDirectionVec();
            final Minecraft mc = Minecraft.getMinecraft();

            double x;
            double z;

            switch (this.face.getAxis()) {
                case Z:
                    final double absX = Math.abs(mc.thePlayer.posX);
                    double xOffset = absX - (int) absX;

                    if (mc.thePlayer.posX < 0) {
                        xOffset = 1.0F - xOffset;
                    }

                    x = directionVec.getX() * xOffset;
                    z = directionVec.getZ() * xOffset;
                    break;
                case X:
                    final double absZ = Math.abs(mc.thePlayer.posZ);
                    double zOffset = absZ - (int) absZ;

                    if (mc.thePlayer.posZ < 0) {
                        zOffset = 1.0F - zOffset;
                    }

                    x = directionVec.getX() * zOffset;
                    z = directionVec.getZ() * zOffset;
                    break;
                default:
                    x = 0.25;
                    z = 0.25;
                    break;
            }

            if (this.face.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) {
                x = -x;
                z = -z;
            }

            final Vec3 hitVec = new Vec3(this.pos).addVector(x + z, directionVec.getY() * 0.5, x + z);

            final Vec3 src = mc.thePlayer.getPositionEyes(1.0F);
            final MovingObjectPosition obj = mc.theWorld.rayTraceBlocks(src,
                    hitVec,
                    false,
                    false,
                    true);

            if (obj == null || obj.hitVec == null || obj.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
                return null;

            switch (this.face.getAxis()) {
                case Z:
                    obj.hitVec = new Vec3(obj.hitVec.xCoord, obj.hitVec.yCoord, Math.round(obj.hitVec.zCoord));
                    break;
                case X:
                    obj.hitVec = new Vec3(Math.round(obj.hitVec.xCoord), obj.hitVec.yCoord, obj.hitVec.zCoord);
                    break;
            }

            if (this.face != EnumFacing.DOWN && this.face != EnumFacing.UP) {
                final IBlockState blockState = mc.theWorld.getBlockState(obj.getBlockPos());
                final Block blockAtPos = blockState.getBlock();

                double blockFaceOffset;

                blockFaceOffset = RandomUtils.nextDouble(0.1, 0.3);

                if (blockAtPos instanceof BlockSlab && !((BlockSlab) blockAtPos).isDouble()) {
                    final BlockSlab.EnumBlockHalf half = blockState.getValue(BlockSlab.HALF);

                    if (half != BlockSlab.EnumBlockHalf.TOP) {
                        blockFaceOffset += 0.5;
                    }
                }

                obj.hitVec = obj.hitVec.addVector(0.0D, -blockFaceOffset, 0.0D);
            }

            return obj.hitVec;
        }
    }

    private float[] getScaffoldRotations(final BlockPos blockPos, final EnumFacing enumFacing) {
        if(blockPos != null || enumFacing != null) {
            final Vec3d positionEyes = mc.thePlayer.getPositionEyes3d(2.0f);
            final Vec3d add = new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.49f));
            final double n = add.xCoord - positionEyes.xCoord;
            final double n2 = add.yCoord - positionEyes.yCoord;
            final double n3 = add.zCoord - positionEyes.zCoord;
            return new float[]{(float) (Math.atan2(n3, n) * 180.0 / Math.PI - 90.0), -(float) (Math.atan2(n2, (float) Math.hypot(n, n3)) * 180.0 / Math.PI)};
        }
        return null;
    }

    private float getSensitivityMultiplier() {
        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return (f * f * f * 8.0F) * 0.15F;
    }

    private enum ScaffoldMode {
        HYPIXEL
    }

}