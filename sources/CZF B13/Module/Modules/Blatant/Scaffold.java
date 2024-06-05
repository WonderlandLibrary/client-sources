package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.API.Events.World.EventPostUpdate;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.UI.Font.CFontRenderer;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.InventoryUtils;
import gq.vapu.czfclient.Util.Math.RotationUtil;
import gq.vapu.czfclient.Util.Render.Colors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Scaffold extends Module {

    public static List invalid = Arrays.asList(
            Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava,
            Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars,
            Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore,
            Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt,
            Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore,
            Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate,
            Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily,
            Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace,
            Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence);
    public static BlockCache blockCache;
    public static float pitch;
    public static int fucku = 0;
    public String ppp;
    float yaw;
    private final Mode<Enum> mode = new Mode("Mode", "Mode", ScMode.values(), ScMode.Normal);
    private final Option tower = new Option("Tower", "Tower", false);
    private final Option timer = new Option("Timer", "Timer", false);
    private final Option silent = new Option("Silent", "Silent", false);
    private int currentItem;
    private int width = 0;

    public Scaffold() {
        super("Scaffold", new String[]{"Scaffold"}, ModuleType.Blatant);
        this.addValues(this.tower, this.silent, this.mode, timer);
        this.currentItem = 0;
        this.setColor((new Color(244, 119, 194)).getRGB());
    }

    public static int grabBlockSlot() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
                return i;
            }
        }

        return -1;
    }

    public static int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && isValid(item)) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }

    private static boolean isValid(Item item) {
        if (!(item instanceof ItemBlock)) {
            return false;
        }
        ItemBlock iBlock = (ItemBlock) item;
        Block block = iBlock.getBlock();
        return !invalid.contains(block);
    }

    public void onEnable() {
        this.currentItem = mc.thePlayer.inventory.currentItem;
    }

    public void onDisable() {
        Timer.timerSpeed = 1.0F;
        mc.thePlayer.inventory.currentItem = this.currentItem;
        fucku = 0;
        super.onDisable();
    }

    @EventHandler
    private void onUpdate(EventMove e) {
        if (mc.gameSettings.keyBindSprint.isKeyDown()) {
            mc.thePlayer.setMoveSpeed(e, 0.15);
            if (mc.thePlayer.isSprinting()) {
                mc.thePlayer.setSprinting(false);
            }
            if (!mc.thePlayer.onGround) {
                mc.thePlayer.setMoveSpeed(e, 0.0);
            }
        }
    }

    @EventHandler
    private void onUpdate(EventPreUpdate event) {
        this.setSuffix(this.mode.getValue());
        if (grabBlockSlot() == -1) {
            this.setEnabled(false);
        }
        if (grabBlockSlot() != -1 && fucku == 1) {
            mc.thePlayer.renderYawOffset = yaw;
            mc.thePlayer.rotationYawHead = yaw;
            event.setYaw(yaw);
            pitch = 84;
            event.setPitch(pitch);
            //mc.thePlayer.setSprinting(false);
            // mc.thePlayer.prevRenderArmPitch=pitch;
        }
        if (mc.gameSettings.keyBindSprint.isKeyDown() && mc.thePlayer.onGround) {
            return;
        }
        if (grabBlockSlot() != -1) {
            blockCache = grab();
            if (blockCache != null) {
                fucku = 1;
                float[] rotations = RotationUtil.用搅拌机把你妈的阴扩一下(BlockCache.access$0(blockCache));
                Entity entity = mc.getRenderViewEntity();
                EnumFacing enumfacing = entity.getHorizontalFacing();
                switch (GuiOverlayDebug.GuiOverlayDebug$1.field_178907_a[enumfacing.ordinal()]) {
                    case 1://North
                        yaw = 360;
                        break;

                    case 2://South
                        yaw = 190;
                        break;

                    case 3://West
                        yaw = 280;
                        break;

                    case 4://East
                        yaw = 90;
                }
                //yaw = rotations[0];
                pitch = 84;
                //pitch = RotationUtil.操过你妈的狗累计可绕地球36圈(this.grabPosition(BlockCache.access$0(this.blockCache), BlockCache.access$1(this.blockCache)))[1] - 1.0F;
                // System.out.println(pitch);
                mc.thePlayer.swingItem();
                CFontRenderer font = FontLoaders.GoogleSans18;
                //mc.timer.timerSpeed = 1.2F;
                //font.drawString(this.getBlockCount() + " remaining blocks", Display.getWidth() / 2, Display.getHeight() - font.getHeight(), new Color(106, 106, 106).getRGB());
            }
        }
    }

    @EventHandler
    public void renderTabGui(final EventRender2D e) {
        int blocks = InventoryUtils.findAutoBlockBlock();
        if (blocks == -1) {
            return;
        }
        ItemStack stack = mc.thePlayer.inventory.getStackInSlot(blocks - 36);
        CFontRenderer font = FontLoaders.GoogleSans18;
        ScaledResolution res = new ScaledResolution(mc);
        int color = Colors.getColor(255, 0, 0);
        if (getBlockCount() > 64 && 256 > getBlockCount()) {
            color = Colors.getColor(255, 255, 0);
        } else if (getBlockCount() > 256) {
            color = Colors.getColor(0, 255, 0);
        }
        if (getBlockCount() >= 100 && getBlockCount() < 1000) {
            this.width = 7;
        }
        if (getBlockCount() >= 10 && getBlockCount() < 100) {
            this.width = 5;
        }
        if (getBlockCount() >= 0 && getBlockCount() < 10) {
            this.width = 3;
        }
        font.drawString("" + getBlockCount(), (res.getScaledWidth() / 2 - font.getStringWidth("" + getBlockCount()) / 2), (res.getScaledHeight() / 2 - font.getHeight() / 2) + 20, color);
        if (getBlockCount() != 0) {
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(stack, res.getScaledWidth() / 2 - 30, (res.getScaledHeight() / 2) + 12);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }

    @EventHandler
    private void onPostUpdate(EventPostUpdate event) {
        if (blockCache != null) {
            if (((Boolean) this.timer.getValue()).booleanValue()) {
                Timer.timerSpeed = 1.13F;
            } else if (((Boolean) this.tower.getValue()).booleanValue()) {
                BlockPos underPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
                Block underBlock = mc.theWorld.getBlockState(underPos).getBlock();
                if (mc.thePlayer.isMoving() && !mc.gameSettings.keyBindJump.pressed) {
                    if (isOnGround(0.76) && !isOnGround(0.75) && mc.thePlayer.motionY > 0.23
                            && mc.thePlayer.motionY < 0.25) {
                        mc.thePlayer.motionY = (Math.round(mc.thePlayer.posY) - mc.thePlayer.posY);
                    }
                    if (isOnGround(0.0001)) {
                        mc.thePlayer.motionX *= 0.9;
                        mc.thePlayer.motionZ *= 0.9;
                    } else if (mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 0.0001
                            && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 0.0001) {
                        mc.thePlayer.motionY = 0;
                    }
                } else if (!mc.thePlayer.isMoving() && mc.gameSettings.keyBindJump.pressed) {
                    mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionZ = 0;
                    mc.thePlayer.jumpMovementFactor = 0;
                    if (isAirBlock(underBlock)) {
                        mc.thePlayer.motionY = 0.4196;
                        mc.thePlayer.motionX *= 0.75;
                        mc.thePlayer.motionZ *= 0.75;
                    }
                }
            }

            int currentSlot = mc.thePlayer.inventory.currentItem;
            int slot = grabBlockSlot();
            mc.thePlayer.inventory.currentItem = slot;
            Minecraft.playerController.updateController();

            if (this.placeBlock(BlockCache.access$2(blockCache), BlockCache.access$3(blockCache))) {
                if (((Boolean) this.silent.getValue()).booleanValue()) {
                    mc.thePlayer.inventory.currentItem = currentSlot;
                    Minecraft.playerController.updateController();
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(currentSlot));
                }
                blockCache = null;
            }

        }
    }

    public boolean isOnGround(double d) {
        // TODO 自动生成的方法存根
        return false;
    }

    public boolean isAirBlock(Block block) {
        if (block.getMaterial().isReplaceable()) {
            return !(block instanceof BlockSnow) || !(block.getBlockBoundsMaxY() > 0.125);
        }

        return false;
    }

    private boolean placeBlock(BlockPos pos, EnumFacing facing) {
        new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight(),
                mc.thePlayer.posZ);
        if (Minecraft.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
                mc.thePlayer.getHeldItem(), pos, facing,
                (new Vec3(BlockCache.access$2(blockCache))).addVector(0.5D, 0.5D, 0.5D)
                        .add((new Vec3(BlockCache.access$3(blockCache).getDirectionVec())).scale(0.5D)))) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            return true;
        } else {
            return false;
        }
    }

    private Vec3 grabPosition(BlockPos position, EnumFacing facing) {
        Vec3 offset = new Vec3((double) facing.getDirectionVec().getX() / 2.0D,
                (double) facing.getDirectionVec().getY() / 2.0D, (double) facing.getDirectionVec().getZ() / 2.0D);
        Vec3 point = new Vec3((double) position.getX() + 0.5D, (double) position.getY() + 0.5D,
                (double) position.getZ() + 0.5D);
        return point.add(offset);
    }

    private BlockCache grab() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY - 0.8;
        double z = mc.thePlayer.posZ;
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        x += (forward * 0.4 * Math.cos(Math.toRadians(yaw + 90.0f))
                + strafe * 0.4 * Math.sin(Math.toRadians(yaw + 90.0f)));
        z += (forward * 0.4 * Math.sin(Math.toRadians(yaw + 90.0f))
                - strafe * 0.4 * Math.cos(Math.toRadians(yaw + 90.0f)));
        BlockPos blockBelow = new BlockPos(x, y, z);
        BlockPos blockBelow1 = new BlockPos(x, y, z);

        EnumFacing[] invert = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH,
                EnumFacing.EAST, EnumFacing.WEST};
        BlockPos position = new BlockPos(mc.thePlayer.getPositionVector()).down();
        if (!(mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
            return null;
        }
        EnumFacing[] var6 = EnumFacing.values();
        int var5 = var6.length;
        int offset = 0;
        while (offset < var5) {
            EnumFacing offsets = var6[offset];
            BlockPos offset1 = position.offset(offsets);
            mc.theWorld.getBlockState(offset1);
            if (!(mc.theWorld.getBlockState(offset1).getBlock() instanceof BlockAir)) {
                return new BlockCache(this, offset1, invert[offsets.getIndex()], null);
            }
            ++offset;
        }
        BlockPos[] var16;
        BlockPos[] var19 = var16 = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1),
                new BlockPos(0, 0, -1), new BlockPos(1, 0, 0)};
        int var18 = var16.length;
        var5 = 0;
        while (var5 < var18) {
            BlockPos var17 = var19[var5];
            BlockPos offsetPos = position.add(var17.getX(), 0, var17.getZ());
            mc.theWorld.getBlockState(offsetPos);
            if (mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                EnumFacing[] var13 = EnumFacing.values();
                int var12 = var13.length;
                int var11 = 0;
                while (var11 < var12) {
                    EnumFacing facing2 = var13[var11];
                    BlockPos offset2 = offsetPos.offset(facing2);
                    mc.theWorld.getBlockState(offset2);
                    if (!(mc.theWorld.getBlockState(offset2).getBlock() instanceof BlockAir)) {
                        return new BlockCache(null, offset2, invert[facing2.ordinal()], null);
                    }
                    ++var11;
                }

            }
            ++var5;
        }
        return null;
    }

    enum ScMode {
        Normal
    }

    static class BlockCache {
        final Scaffold this$0;
        private final BlockPos position;
        private final EnumFacing facing;

        private BlockCache(Scaffold var1, BlockPos position, EnumFacing facing) {
            this.this$0 = var1;
            this.position = position;
            this.facing = facing;
        }

        BlockCache(Scaffold var1, BlockPos var2, EnumFacing var3, BlockCache var4) {
            this(var1, var2, var3);
        }

        static BlockPos access$0(BlockCache var0) {
            return var0.getPosition();
        }

        static EnumFacing access$1(BlockCache var0) {
            return var0.getFacing();
        }

        static BlockPos access$2(BlockCache var0) {
            return var0.position;
        }

        static EnumFacing access$3(BlockCache var0) {
            return var0.facing;
        }

        private BlockPos getPosition() {
            return this.position;
        }

        private EnumFacing getFacing() {
            return this.facing;
        }
    }
}
