package me.superskidder.lune.modules.movement;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventPacketSend;
import me.superskidder.lune.events.EventPostUpdate;
import me.superskidder.lune.events.EventPreUpdate;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.font.CFontRenderer;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.utils.player.RotationUtils;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Scaffold extends Mod {
    ItemStack is;
    private BlockData blockData;
    private ArrayList<BlockData> blockDataList = new ArrayList<>();

    private timeHelper timer2 = new timeHelper();
    public Bool<Boolean> tower = new Bool("Tower", true);
    public Bool<Boolean> moveTower = new Bool<>("MoveTower", false);
    public Bool<Boolean> sprint = new Bool<>("Sprint", true);
    private Num<Number> delay = new Num<>("Delay", 0.0, 0.0, 1000.0);
    private Num<Number> expand = new Num<>("Expand", 4.0, 1.0, 10.0);

    public Mode<?> option = new Mode<>("Mode", Smode.values(), Smode.Hypixel);
    private List<Block> blacklisted = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava,
            Blocks.flowing_lava, Blocks.enchanting_table, Blocks.ender_chest, Blocks.yellow_flower, Blocks.carpet,
            Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.crafting_table, Blocks.snow_layer,
            Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch,
            Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore,
            Blocks.lit_redstone_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
            Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
            Blocks.wooden_button, Blocks.cactus, Blocks.lever, Blocks.activator_rail, Blocks.rail, Blocks.detector_rail,
            Blocks.golden_rail, Blocks.furnace, Blocks.ladder, Blocks.oak_fence, Blocks.redstone_torch,
            Blocks.iron_trapdoor, Blocks.trapdoor, Blocks.tripwire_hook, Blocks.hopper, Blocks.acacia_fence_gate,
            Blocks.birch_fence_gate, Blocks.dark_oak_fence_gate, Blocks.jungle_fence_gate, Blocks.spruce_fence_gate,
            Blocks.oak_fence_gate, Blocks.dispenser, Blocks.sapling, Blocks.tallgrass, Blocks.deadbush, Blocks.web,
            Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.nether_brick_fence, Blocks.vine,
            Blocks.double_plant, Blocks.flower_pot, Blocks.beacon, Blocks.pumpkin, Blocks.lit_pumpkin);
    public static List<Block> blacklistedBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water,
            Blocks.lava, Blocks.flowing_lava, Blocks.ender_chest, Blocks.enchanting_table, Blocks.stone_button,
            Blocks.wooden_button, Blocks.crafting_table, Blocks.beacon);
    private int width = 0;
    private ItemStack currentlyHolding;
    private TimerUtil towerTimer = new TimerUtil();

    public Scaffold() {
        super("Scaffold", ModCategory.Movement, "Auto build a bridge under your feet");
        this.addValues(option, delay, tower, sprint, moveTower);
    }

    @EventTarget
    public void onPacket(EventPacketSend event) {
        Packet packet = event.getPacket();
        if (packet instanceof C0BPacketEntityAction && sprint.getValue()) {
            if (((C0BPacketEntityAction) packet).getAction() == C0BPacketEntityAction.Action.START_SPRINTING) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onPre(EventPreUpdate event) {

        this.setDisplayName(this.option.getValue().toString());
        if (blockData != null) {

            if (this.option.getValue() == Smode.AAC || this.option.getValue() == Smode.Hypixel) {
                event.setYaw(mc.thePlayer.rotationYaw + 180);
                event.setPitch(80);
            } else {
                float[] weishenme = RotationUtils.grabBlockRotations(this.blockData.getPosition());
                event.setYaw(weishenme[0]);
                event.setPitch(weishenme[1]);
            }

        }
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY - 1.0;
        double z = mc.thePlayer.posZ;
        BlockPos underPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        Block underBlock = mc.theWorld.getBlockState(underPos).getBlock();
        BlockPos blockBelow = new BlockPos(x, y, z);
        if (mc.thePlayer != null) {
            blockDataList = new ArrayList<>();
            if (option.getValue() == Smode.Expand) {
                for (int i = 0; i < expand.value.intValue(); i++) {
                    if(!blockDataList.contains(getBlockData(new BlockPos(x + i, y, z), blacklistedBlocks))) {
                        blockDataList.add(getBlockData(new BlockPos(x + i, y, z), blacklistedBlocks));
                    }
                }
                System.out.println(blockDataList.size());
                blockDataList.clear();
            }

            this.blockData = this.getBlockData(blockBelow, blacklistedBlocks);
            if (this.blockData == null) {
                this.blockData = this.getBlockData(blockBelow.offset(EnumFacing.DOWN), blacklistedBlocks);
            }
            if (this.mc.theWorld.getBlockState(blockBelow = new BlockPos(x, y, z)).getBlock() == Blocks.air) {
                if (this.blockData != null) {
                    if (this.option.getValue() == Smode.NCP) {
                        float[] weishenme = RotationUtils.grabBlockRotations(this.blockData.getPosition());
                        event.setYaw(weishenme[0]);
                        event.setPitch(weishenme[1]);
                    }
                }
                if ((this.tower.getValue().booleanValue() || moveTower.getValue().booleanValue())
                        && this.mc.gameSettings.keyBindJump.pressed) {
                    if (getBlockCount() <= 0)
                        return;
                    if (this.option.getValue() == Smode.Hypixel || this.option.getValue() == Smode.NCP) {
                        if ((!this.isMoving2() || moveTower.getValue().booleanValue())
                                && this.mc.gameSettings.keyBindJump.pressed) {
                            if (!moveTower.getValue().booleanValue()) {
                                mc.thePlayer.motionX = 0.0;
                                mc.thePlayer.motionZ = 0.0;
                            }
                            mc.thePlayer.jumpMovementFactor = 0.0f;
                            blockBelow = new BlockPos(x, y, z);
                            if (this.mc.theWorld.getBlockState(blockBelow).getBlock() == Blocks.air
                                    && this.blockData != null) {
                                if (towerTimer.delay(50)) {
                                    mc.thePlayer.motionY = 0.41993956416514;
                                    if (!moveTower.getValue().booleanValue()) {
                                        mc.thePlayer.motionX *= 0.75;
                                        mc.thePlayer.motionZ *= 0.75;
                                    }
                                    towerTimer.reset();
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public boolean isOnGround(double height) {
        if (!this.mc.theWorld
                .getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0))
                .isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isMoving2() {
        return mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f;
    }

    @EventTarget
    public void onSafe(EventPostUpdate event) {
        mc.thePlayer.setSprinting((boolean) sprint.getValue());
        int i;
        for (i = 36; i < 45; ++i) {

            Item item;
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()
                    || !((item = (is = mc.thePlayer.inventoryContainer.getSlot(i).getStack())
                    .getItem()) instanceof ItemBlock)
                    || this.blacklisted.contains(((ItemBlock) item).getBlock())
                    || ((ItemBlock) item).getBlock().getLocalizedName().toLowerCase().contains("chest")
                    || this.blockData == null) {
                continue;
            }
            int currentItem = mc.thePlayer.inventory.currentItem;
            IBlockState underBlock = mc.theWorld
                    .getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ));
            boolean flag = underBlock.getBlock().equals(Blocks.air) || !mc.thePlayer.onGround;
            if (this.option.getValue() == Smode.Expand) {
                for (BlockData bd : blockDataList) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i - 36));
                    mc.thePlayer.inventory.currentItem = i - 36;
                    this.currentlyHolding = this.mc.thePlayer.inventory.getStackInSlot(i - 36);
                    Minecraft.playerController.updateController();
                    Minecraft.playerController.onPlayerRightClick(mc.thePlayer, this.mc.theWorld,
                            mc.thePlayer.getHeldItem(), bd.position, bd.face,
                            new Vec3(bd.access$2(bd)).addVector(0.5, 0.5, 0.5)
                                    .add(new Vec3(bd.access$3(bd).getDirectionVec()).scale(0.5)));
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    mc.thePlayer.inventory.currentItem = currentItem;
                    mc.playerController.updateController();
                }
                blockDataList.clear();
            }
            if (flag) {
                if (option.getValue() != Smode.Expand) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i - 36));
                    mc.thePlayer.inventory.currentItem = i - 36;
                    this.currentlyHolding = this.mc.thePlayer.inventory.getStackInSlot(i - 36);
                    Minecraft.playerController.updateController();
                    Minecraft.playerController.onPlayerRightClick(mc.thePlayer, this.mc.theWorld,
                            mc.thePlayer.getHeldItem(), BlockData.position, BlockData.face,
                            new Vec3(BlockData.access$2(this.blockData)).addVector(0.5, 0.5, 0.5)
                                    .add(new Vec3(BlockData.access$3(this.blockData).getDirectionVec()).scale(0.5)));
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    mc.thePlayer.inventory.currentItem = currentItem;
                    mc.playerController.updateController();
                }
            }
            return;
        }
        if (this.invCheck()) {
            for (i = 9; i < 36; ++i) {
                Item item;
                if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()
                        || !((item = mc.thePlayer.inventoryContainer.getSlot(i).getStack()
                        .getItem()) instanceof ItemBlock)
                        || this.blacklisted.contains(((ItemBlock) item).getBlock())
                        || ((ItemBlock) item).getBlock().getLocalizedName().toLowerCase().contains("chest")) {
                    continue;
                }
                this.swap(i, 7);
                break;
            }
        }

    }

    public static double randomNumber(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }

    public static float randomFloat(long seed) {
        seed = System.currentTimeMillis() + seed;
        return 0.3f + (float) new Random(seed).nextInt(70000000) / 1.0E8f + 1.458745E-8f;
    }

    protected void swap(int slot, int hotbarNum) {
        Minecraft.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2,
                mc.thePlayer);
    }

    private boolean invCheck() {
        for (int i = 36; i < 45; ++i) {
            Item item;
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()
                    || !((item = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()) instanceof ItemBlock)
                    || this.blacklisted.contains(((ItemBlock) item).getBlock()))
                continue;
            return false;
        }
        return true;
    }

    private double getDoubleRandom(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    private boolean canPlace(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos,
                             EnumFacing side, Vec3 vec3) {
        if (heldStack.getItem() instanceof ItemBlock) {
            return ((ItemBlock) heldStack.getItem()).canPlaceBlockOnSide(worldIn, hitPos, side, player, heldStack);
        }
        return false;
    }

    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
                continue;
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (!(is.getItem() instanceof ItemBlock) || this.blacklisted.contains(((ItemBlock) item).getBlock()))
                continue;
            blockCount += is.stackSize;
        }
        return blockCount;
    }

    private BlockData getBlockData(BlockPos pos, List list) {
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {

            return new BlockData(pos.add(-1, 0, 0), Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && mc.thePlayer.onGround
                    && mc.thePlayer.fallDistance == 0.0f
                    && this.mc.theWorld
                    .getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ))
                    .getBlock() == Blocks.air ? EnumFacing.DOWN : EnumFacing.EAST,
                    this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(1, 0, 0), Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && mc.thePlayer.onGround
                    && mc.thePlayer.fallDistance == 0.0f
                    && this.mc.theWorld
                    .getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ))
                    .getBlock() == Blocks.air ? EnumFacing.DOWN : EnumFacing.WEST,
                    this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
            return new BlockData(pos.add(0, 0, -1), Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && mc.thePlayer.onGround
                    && mc.thePlayer.fallDistance == 0.0f
                    && this.mc.theWorld
                    .getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ))
                    .getBlock() == Blocks.air ? EnumFacing.DOWN : EnumFacing.SOUTH,
                    this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
            return new BlockData(pos.add(0, 0, 1), Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && mc.thePlayer.onGround
                    && mc.thePlayer.fallDistance == 0.0f
                    && this.mc.theWorld
                    .getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ))
                    .getBlock() == Blocks.air ? EnumFacing.DOWN : EnumFacing.NORTH,
                    this.blockData);
        }
        BlockPos add = pos.add(-1, 0, 0);
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock())) {
            return new BlockData(add.add(1, 0, 0), EnumFacing.WEST, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock())) {
            return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock())) {
            return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH, this.blockData);
        }
        BlockPos add2 = pos.add(1, 0, 0);
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock())) {
            return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock())) {
            return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock())) {
            return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH, this.blockData);
        }
        BlockPos add3 = pos.add(0, 0, -1);
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock())) {
            return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock())) {
            return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock())) {
            return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH, this.blockData);
        }
        BlockPos add4 = pos.add(0, 0, 1);
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock())) {
            return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock())) {
            return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH, this.blockData);
        }
        if (!blacklistedBlocks.contains(this.mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock())) {
            return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH, this.blockData);
        }
        return null;
    }

    public boolean isAirBlock(Block block) {
        return block.getMaterial().isReplaceable()
                && (!(block instanceof BlockSnow) || block.getBlockBoundsMaxY() <= 0.125);
    }

    public Vec3 getBlockSide(BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.NORTH) {
            return new Vec3(pos.getX(), pos.getY(), (double) pos.getZ() - 0.5);
        }
        if (face == EnumFacing.EAST) {
            return new Vec3((double) pos.getX() + 0.5, pos.getY(), pos.getZ());
        }
        if (face == EnumFacing.SOUTH) {
            return new Vec3(pos.getX(), pos.getY(), (double) pos.getZ() + 0.5);
        }
        if (face == EnumFacing.WEST) {
            return new Vec3((double) pos.getX() - 0.5, pos.getY(), pos.getZ());
        }
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void onEnabled() {
        super.onEnabled();
        this.timer2.reset();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        Timer.timerSpeed = 1.0f;
    }

    public class timeHelper {
        private long prevMS = 0L;

        public boolean delay(float milliSec) {
            return (float) (this.getTime() - this.prevMS) >= milliSec;
        }

        public void reset() {
            this.prevMS = this.getTime();
        }

        public long getTime() {
            return System.nanoTime() / 1000000L;
        }

        public long getDifference() {
            return this.getTime() - this.prevMS;
        }

        public void setDifference(long difference) {
            this.prevMS = this.getTime() - difference;
        }
    }

    private static class BlockData {
        public Object pos;
        public static BlockPos position;
        public static EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face, BlockData blockData) {
            BlockData.position = position;
            BlockData.face = face;
        }

        private BlockPos getPosition() {
            return position;
        }

        private EnumFacing getFacing() {
            return face;
        }

        static BlockPos access$0(BlockData var0) {
            return var0.getPosition();
        }

        static EnumFacing access$1(BlockData var0) {
            return var0.getFacing();
        }

        static BlockPos access$2(BlockData var0) {
            return position;
        }

        static EnumFacing access$3(BlockData var0) {
            return face;
        }
    }

    @EventTarget
    private void render(EventRender2D e) {
        CFontRenderer font = FontLoaders.F18;
        ScaledResolution res = new ScaledResolution(mc);
        int color = new Color(0, 255, 55).getRGB();
        if (getBlockCount() < 16) {
            color = new Color(255, 50, 0).getRGB();
        } else if (getBlockCount() < 32) {
            color = new Color(255, 155, 0).getRGB();
        }
        width = font.getStringWidth(this.getBlockCount() + "") / 2;
        font.drawStringWithShadow(this.getBlockCount() + "", res.getScaledWidth() / 2 - this.width,
                res.getScaledHeight() / 2 - 15, color);

    }

    public static enum Smode {
        Hypixel, AAC, NCP, Expand
    }

}
