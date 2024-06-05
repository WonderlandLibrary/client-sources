// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.BlockLiquid;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import gg.childtrafficking.smokex.gui.element.VAlignment;
import gg.childtrafficking.smokex.gui.element.HAlignment;
import gg.childtrafficking.smokex.gui.element.Element;
import net.minecraft.client.entity.EntityPlayerSP;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import org.apache.commons.lang3.RandomUtils;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockAir;
import gg.childtrafficking.smokex.module.ModuleManager;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.BlockPos;
import gg.childtrafficking.smokex.utils.player.MovementUtils;
import gg.childtrafficking.smokex.gui.animation.Animation;
import gg.childtrafficking.smokex.gui.animation.animations.SmoothMoveAnimation;
import gg.childtrafficking.smokex.gui.animation.Easing;
import gg.childtrafficking.smokex.gui.element.elements.TextElement;
import java.util.ArrayList;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import gg.childtrafficking.smokex.event.events.network.EventSendPacket;
import net.minecraft.block.Block;
import gg.childtrafficking.smokex.event.events.render.EventRender2D;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.network.Packet;
import java.util.List;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Scaffold", renderName = "Scaffold", aliases = { "BlockFly" }, category = ModuleCategory.PLAYER)
public final class ScaffoldModule extends Module
{
    private final NumberProperty<Long> placeDelayProperty;
    private final BooleanProperty swingProperty;
    private final BooleanProperty keepYProperty;
    private final BooleanProperty timerBoostProperty;
    private final NumberProperty<Float> timerSpeedProperty;
    private final BooleanProperty safeWalkProperty;
    private final BooleanProperty towerProperty;
    public final BooleanProperty sprintProperty;
    public final BooleanProperty downwardsProperty;
    private float cachedTimer;
    private int facing;
    public double ogY;
    private double height;
    private int currentHeldItem;
    public final TimerUtil timer;
    private final TimerUtil itemTimer;
    private float[] rotations;
    private float yaw;
    private float pitch;
    public boolean placing;
    public boolean downwards;
    private int stage;
    private final List<Packet<?>> packets;
    private final EventListener<EventRender2D> render2DEventCallback;
    private static List<Block> invalidBlocks;
    private final EventListener<EventSendPacket> sendPacketEventListener;
    private final EventListener<EventMove> moveEventListener;
    private final EventListener<EventUpdate> updatePlayerEventEventCallback;
    
    public ScaffoldModule() {
        this.placeDelayProperty = new NumberProperty<Long>("Place Delay", 50L, 0L, 500L, 25L);
        this.swingProperty = new BooleanProperty("Swing", true);
        this.keepYProperty = new BooleanProperty("Keep-Y", false);
        this.timerBoostProperty = new BooleanProperty("Timer Boost", true);
        this.timerSpeedProperty = new NumberProperty<Float>("Timer Speed", 1.0f, 1.0f, 2.2f, 0.1f);
        this.safeWalkProperty = new BooleanProperty("Safe Walk", true);
        this.towerProperty = new BooleanProperty("Tower", false);
        this.sprintProperty = new BooleanProperty("Sprint", false);
        this.downwardsProperty = new BooleanProperty("Downwards", true);
        this.facing = 2;
        this.timer = new TimerUtil();
        this.itemTimer = new TimerUtil();
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.stage = 0;
        this.packets = new ArrayList<Packet<?>>();
        this.render2DEventCallback = (event -> {
            final TextElement blocksCount = (TextElement)this.getElement("blocks");
            blocksCount.setText(Integer.toString(this.getBlockCount()) + " blocks");
            if (!blocksCount.isAtPosition(0.0, 0.0)) {
                if (blocksCount.getAnimation() != null) {
                    if (blocksCount.getAnimation().x != 0.0f || blocksCount.getAnimation().y != 0.0f) {
                        blocksCount.setAnimation(new SmoothMoveAnimation(0.0f, 0.0f, 450L, Easing.EASE_OUT));
                    }
                }
                else {
                    blocksCount.setAnimation(new SmoothMoveAnimation(0.0f, 0.0f, 450L, Easing.EASE_OUT));
                }
            }
            return;
        });
        this.sendPacketEventListener = (event -> {});
        this.moveEventListener = (event -> MovementUtils.setSpeed(event, MovementUtils.getBaseMoveSpeed()));
        this.updatePlayerEventEventCallback = (event -> {
            this.setSuffix("Watchdog");
            event.setYaw(this.yaw);
            event.setPitch(this.pitch);
            final BlockPos underPos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
            new Vec3(0.0, (this.mc.gameSettings.keyBindSneak.isKeyDown() && !this.mc.gameSettings.keyBindJump.isKeyDown()) ? -1.0 : 0.0, 0.0);
            final Vec3 offset3;
            final BlockData blockData = this.find(offset3);
            if (event.isPre()) {
                if (this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.jump();
                }
                if (this.pitch > 90.0f) {
                    this.pitch = 90.0f;
                }
            }
            else if (this.getBlockSlot() == -1 && this.itemTimer.hasElapsed(150.0)) {
                this.getBlocksFromInventory();
                this.itemTimer.reset();
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.mc.thePlayer.setSneaking(false);
                this.downwards = true;
            }
            else {
                this.downwards = false;
            }
            if (this.mc.theWorld.getBlockState(underPos).getBlock().getMaterial().isReplaceable() && blockData != null) {
                this.placing = true;
                if (this.getBlockSlot() != -1) {
                    if (event.isPre()) {
                        final BlockPos sideBlock = blockData.position;
                        this.yaw = MathHelper.wrapAngleTo180_float(this.getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), blockData.face)[0]);
                        this.pitch = MathHelper.wrapAngleTo180_float(this.getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), blockData.face)[1] + 6.0f);
                        if (this.pitch > 90.0f) {
                            this.pitch = 90.0f;
                        }
                        event.setYaw(this.yaw);
                        event.setPitch(this.pitch);
                        if (this.timer.hasElapsed(this.placeDelayProperty.getValue())) {
                            this.stage = 0;
                            if (this.sprintProperty.getValue() && this.mc.thePlayer.onGround) {
                                MovementUtils.ncpBoost();
                            }
                            if (this.swingProperty.getValue()) {
                                this.mc.thePlayer.swingItem();
                            }
                            else {
                                this.mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
                            }
                        }
                    }
                    else {
                        this.mc.thePlayer.inventory.currentItem = this.getBlockSlot();
                        final double hitvecx = blockData.position.getX() + this.height + blockData.face.getFrontOffsetX() / this.facing;
                        final double hitvecy = blockData.position.getY() + this.height + blockData.face.getFrontOffsetY() / this.facing;
                        final double hitvecz = blockData.position.getZ() + this.height + blockData.face.getFrontOffsetZ() / this.facing;
                        final Vec3 vec = new Vec3(hitvecx, hitvecy, hitvecz);
                        if (this.timer.hasElapsed(this.placeDelayProperty.getValue())) {
                            this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), blockData.position, blockData.face, vec);
                            this.timer.reset();
                        }
                    }
                }
            }
            else {
                ModuleManager.getInstance(AutoSprintModule.class).setOk(!this.downwards);
            }
            if (event.isPre()) {
                if (this.mc.gameSettings.keyBindJump.isKeyDown() && (!MovementUtils.isMoving() || this.towerProperty.getValue())) {
                    if (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer).add(0, 2, 0)).getBlock() instanceof BlockAir) {
                        this.mc.thePlayer.cameraPitch = 0.0f;
                        final double[] jumpY = { 0.41999998688698, 0.7531999805212 };
                        final double divideY = event.getPosY() % 1.0;
                        final double roundY = MathHelper.floor_double(this.mc.thePlayer.posY);
                        if (divideY > 0.419 && divideY < 0.753) {
                            event.setPosY(roundY + jumpY[0]);
                        }
                        else if (divideY > 0.753) {
                            event.setPosY(roundY + jumpY[1]);
                        }
                        else {
                            event.setPosY(roundY);
                            event.setOnGround(true);
                        }
                        if (!MovementUtils.isMoving()) {
                            RandomUtils.nextDouble(0.06, 0.0625);
                        }
                    }
                }
            }
            else if (PlayerUtils.isOnGround(0.15) && this.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                thePlayer.motionX *= 0.8;
                final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                thePlayer2.motionZ *= 0.8;
                this.mc.thePlayer.motionY = 0.41999976;
            }
        });
    }
    
    @Override
    public void init() {
        this.addElement(new TextElement("blocks", 25.0f, 25.0f, "", -698558, true)).setHAlignment(HAlignment.CENTER).setVAlignment(VAlignment.CENTER);
    }
    
    @Override
    public void onDisable() {
        if (this.timerBoostProperty.getValue()) {
            this.mc.timer.timerSpeed = this.cachedTimer;
        }
        this.mc.thePlayer.inventory.currentItem = this.currentHeldItem;
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        if (this.keepYProperty.getValue()) {
            this.ogY = this.mc.thePlayer.posY;
        }
        this.packets.clear();
        this.placing = false;
        this.currentHeldItem = this.mc.thePlayer.inventory.currentItem;
        this.getElement("blocks").setPosition(500.0f, 0.0f);
        this.cachedTimer = this.mc.timer.timerSpeed;
        if (this.timerBoostProperty.getValue()) {
            this.mc.timer.timerSpeed = this.timerSpeedProperty.getValue();
        }
        super.onEnable();
    }
    
    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock) {
                    if (!contains(((ItemBlock)item).getBlock())) {
                        blockCount += is.stackSize;
                    }
                }
            }
        }
        return blockCount;
    }
    
    public static boolean contains(final Block block) {
        return ScaffoldModule.invalidBlocks.contains(block);
    }
    
    private void getBlocksFromInventory() {
        if (this.mc.currentScreen instanceof GuiChest) {
            return;
        }
        for (int index = 9; index < 36; ++index) {
            final ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null) {
                if (isValid(stack.getItem())) {
                    this.mc.playerController.windowClick(0, index, 6, 2, this.mc.thePlayer);
                    break;
                }
            }
        }
    }
    
    private int getBlockSlot() {
        for (int i = 36; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (isValid(stack.getItem())) {
                    return i - 36;
                }
            }
        }
        return -1;
    }
    
    public static boolean isValid(final Item item) {
        if (!(item instanceof ItemBlock)) {
            return false;
        }
        final ItemBlock iBlock = (ItemBlock)item;
        final Block block = iBlock.getBlock();
        return !ScaffoldModule.invalidBlocks.contains(block);
    }
    
    public BlockData find(final Vec3 offset3) {
        final double x = this.mc.thePlayer.posX;
        final double y = this.keepYProperty.getValue() ? this.ogY : this.mc.thePlayer.posY;
        final double z = this.mc.thePlayer.posZ;
        final EnumFacing[] invert = { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
        final BlockPos position = new BlockPos(new Vec3(x, y, z).add(offset3)).offset(EnumFacing.DOWN);
        for (final EnumFacing facing : EnumFacing.values()) {
            final BlockPos offset4 = position.offset(facing);
            if (!(this.mc.theWorld.getBlockState(offset4).getBlock() instanceof BlockAir) && !this.rayTrace(this.mc.thePlayer.getLook(0.0f), this.getPositionByFace(offset4, invert[facing.ordinal()]))) {
                return new BlockData(offset4, invert[facing.ordinal()]);
            }
        }
        final BlockPos[] array;
        final BlockPos[] offsets = array = new BlockPos[] { new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(0, 0, 2), new BlockPos(0, 0, -2), new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0) };
        for (int length2 = array.length, j = 0; j < length2; ++j) {
            final BlockPos offset4 = array[j];
            final BlockPos offsetPos = position.add(offset4.getX(), 0, offset4.getZ());
            if (this.mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                for (final EnumFacing facing2 : EnumFacing.values()) {
                    final BlockPos offset5 = offsetPos.offset(facing2);
                    if (!(this.mc.theWorld.getBlockState(offset5).getBlock() instanceof BlockAir) && !this.rayTrace(this.mc.thePlayer.getLook(0.01f), this.getPositionByFace(offset4, invert[facing2.ordinal()]))) {
                        return new BlockData(offset5, invert[facing2.ordinal()]);
                    }
                }
            }
        }
        return null;
    }
    
    private float[] getBlockRotations(final int x, final int y, final int z, final EnumFacing facing) {
        final Entity temp = new EntitySnowball(this.mc.theWorld);
        temp.posX = x + 0.5;
        final Entity entity = temp;
        final double n = y;
        final double height = 0.5;
        this.height = height;
        entity.posY = n + height;
        temp.posZ = z + 0.5;
        return this.mc.thePlayer.canEntityBeSeen(temp) ? this.getAngles(temp) : this.getRotationToBlock(new BlockPos(x, y, z), facing);
    }
    
    private float[] getAngles(final Entity e) {
        return new float[] { this.getYawChangeToEntity(e) + this.mc.thePlayer.rotationYaw, this.getPitchChangeToEntity(e) + this.mc.thePlayer.rotationPitch };
    }
    
    private float getYawChangeToEntity(final Entity entity) {
        final double deltaX = entity.posX - this.mc.thePlayer.posX;
        final double deltaZ = entity.posZ - this.mc.thePlayer.posZ;
        final double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + v;
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + v;
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(this.mc.thePlayer.rotationYaw - (float)yawToEntity));
    }
    
    private float getPitchChangeToEntity(final Entity entity) {
        final double deltaX = entity.posX - this.mc.thePlayer.posX;
        final double deltaZ = entity.posZ - this.mc.thePlayer.posZ;
        final double deltaY = entity.posY - 1.6 + entity.getEyeHeight() - 0.4 - this.mc.thePlayer.posY;
        final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }
    
    public float[] getRotationToBlock(final BlockPos pos, final EnumFacing face) {
        final double random = 0.5;
        final int ranface = 3;
        final double n = pos.getX();
        final double height = random;
        this.height = height;
        final double xDiff = n + height - this.mc.thePlayer.posX + face.getDirectionVec().getX() / 3;
        final double n2 = pos.getZ();
        final double height2 = random;
        this.height = height2;
        final double zDiff = n2 + height2 - this.mc.thePlayer.posZ + face.getDirectionVec().getZ() / 3;
        final double yDiff = pos.getY() - this.mc.thePlayer.posY - 1.0;
        final double distance = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(-Math.toDegrees(Math.atan2(xDiff, zDiff)));
        final float pitch = (float)(-Math.toDegrees(Math.atan(yDiff / distance)));
        return new float[] { (Math.abs(yaw - this.mc.thePlayer.rotationYaw) < 0.1) ? this.mc.thePlayer.rotationYaw : yaw, (Math.abs(pitch - this.mc.thePlayer.rotationPitch) < 0.1) ? this.mc.thePlayer.rotationPitch : pitch };
    }
    
    public Vec3 getPositionByFace(final BlockPos position, final EnumFacing facing) {
        final Vec3 offset = new Vec3(facing.getDirectionVec().getX() / 2.0, facing.getDirectionVec().getY() / 2.0, facing.getDirectionVec().getZ() / 2.0);
        final Vec3 point = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
        return point.add(offset);
    }
    
    private boolean rayTrace(final Vec3 origin, final Vec3 position) {
        final Vec3 difference = position.subtract(origin);
        final int steps = 10;
        final double x = difference.xCoord / steps;
        final double y = difference.yCoord / steps;
        final double z = difference.zCoord / steps;
        Vec3 point = origin;
        for (int i = 0; i < steps; ++i) {
            final BlockPos blockPosition = new BlockPos(point = point.addVector(x, y, z));
            final IBlockState blockState = this.mc.theWorld.getBlockState(blockPosition);
            if (!(blockState.getBlock() instanceof BlockLiquid)) {
                if (!(blockState.getBlock() instanceof BlockAir)) {
                    AxisAlignedBB boundingBox = blockState.getBlock().getCollisionBoundingBox(this.mc.theWorld, blockPosition, blockState);
                    if (boundingBox == null) {
                        boundingBox = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                    }
                    if (boundingBox.offset(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()).isVecInside(point)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    static {
        ScaffoldModule.invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, Blocks.chest, Blocks.dispenser, Blocks.air, Blocks.water, Blocks.lava, Blocks.flowing_water, Blocks.flowing_lava, Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.yellow_flower, Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars);
    }
    
    private static class BlockData
    {
        public BlockPos position;
        public EnumFacing face;
        
        private BlockData(final BlockPos position, final EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }
    
    public enum ScaffoldMode
    {
        WATCHDOG;
    }
}
