package cn.Hanabi.modules.World;

import cn.Hanabi.value.*;
import net.minecraft.block.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.modules.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import io.netty.util.internal.*;
import cn.Hanabi.events.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import ClassSub.*;
import java.util.*;

public class Scaffold_ extends Mod
{
    private Class273 blockData;
    private Class205 time;
    private Class205 delay;
    private Class205 timer2;
    private Value<Boolean> tower;
    private Value<Boolean> noSwing;
    private Value<Boolean> nosprint;
    private Value<Boolean> aac;
    private double olddelay;
    private BlockPos blockpos;
    private EnumFacing facing;
    private boolean rotated;
    private boolean should;
    private static int[] Facing;
    public static List<Block> blacklistedBlocks;
    private Class205 spacetimer;
    
    
    @EventTarget
    public void onKey(final EventKey eventKey) {
        if (eventKey.getKey() == 57) {
            this.timer2.reset();
            this.spacetimer.reset();
        }
    }
    
    public Scaffold_() {
        super("Scaffold", Category.WORLD);
        this.time = new Class205();
        this.delay = new Class205();
        this.timer2 = new Class205();
        this.tower = new Value<Boolean>("Scaffold_Tower", true);
        this.noSwing = new Value<Boolean>("Scaffold_NoSwing", false);
        this.nosprint = new Value<Boolean>("Scaffold_NoSprint", false);
        this.aac = new Value<Boolean>("Scaffold_AAC", false);
        this.rotated = false;
        this.should = false;
        this.spacetimer = new Class205();
        if (Class334.password.length() < 32) {
            System.exit(0);
        }
    }
    
    private boolean couldBlockBePlaced() {
        final double posX = Scaffold_.mc.thePlayer.posX;
        final double posZ = Scaffold_.mc.thePlayer.posZ;
        final double doubleRandom = this.getDoubleRandom(0.12, 0.2);
        switch (Facing()[Scaffold_.mc.thePlayer.getHorizontalFacing().ordinal()]) {
            case 3: {
                if (Scaffold_.mc.theWorld.getBlockState(new BlockPos(Scaffold_.mc.thePlayer.posX, Scaffold_.mc.thePlayer.posY - 0.1, posZ + doubleRandom)).getBlock() != Blocks.air) {
                    break;
                }
                return true;
            }
            case 4: {
                if (Scaffold_.mc.theWorld.getBlockState(new BlockPos(Scaffold_.mc.thePlayer.posX, Scaffold_.mc.thePlayer.posY - 0.1, posZ - doubleRandom)).getBlock() != Blocks.air) {
                    break;
                }
                return true;
            }
            case 5: {
                if (Scaffold_.mc.theWorld.getBlockState(new BlockPos(posX + doubleRandom, Scaffold_.mc.thePlayer.posY - 0.1, Scaffold_.mc.thePlayer.posZ)).getBlock() != Blocks.air) {
                    break;
                }
                return true;
            }
            case 6: {
                if (Scaffold_.mc.theWorld.getBlockState(new BlockPos(posX - doubleRandom, Scaffold_.mc.thePlayer.posY - 0.1, Scaffold_.mc.thePlayer.posZ)).getBlock() != Blocks.air) {
                    break;
                }
                return true;
            }
        }
        return false;
    }
    
    @EventTarget
    public void onPre(final EventPreMotion eventPreMotion) {
        if (Scaffold_.mc.thePlayer != null) {
            this.blockData = this.getBlockData(new BlockPos((Vec3i)new BlockPos(Scaffold_.mc.thePlayer.posX, Scaffold_.mc.thePlayer.posY - 0.5, Scaffold_.mc.thePlayer.posZ)));
            final int blockItem = this.getBlockItem();
            if (this.nosprint.getValueState() || this.aac.getValueState()) {
                Scaffold_.mc.thePlayer.setSprinting(false);
            }
            final Item getItem = Scaffold_.mc.thePlayer.inventory.getStackInSlot(blockItem).getItem();
            if (this.blockData != null && blockItem != -1 && getItem != null && getItem instanceof ItemBlock) {
                final Vec3 blockSide = this.getBlockSide(this.blockData.pos, this.blockData.face);
                final float[] array = Class339.getRotationsNeededBlock(blockSide.xCoord, blockSide.yCoord - 0.24, blockSide.zCoord);
                if (!this.aac.getValueState()) {
                    eventPreMotion.yaw = array[0] / 1.101228f;
                    eventPreMotion.pitch = array[1] / 1.102311f;
                }
                if (this.aac.getValueState()) {
                    if (((IKeyBinding)Scaffold_.mc.gameSettings.keyBindForward).getPress()) {
                        eventPreMotion.yaw += 180.0f;
                        if (((IKeyBinding)Scaffold_.mc.gameSettings.keyBindLeft).getPress()) {
                            eventPreMotion.yaw -= 45.0f;
                        }
                        else if (((IKeyBinding)Scaffold_.mc.gameSettings.keyBindRight).getPress()) {
                            eventPreMotion.yaw += 45.0f;
                        }
                    }
                    else if (((IKeyBinding)Scaffold_.mc.gameSettings.keyBindBack).getPress()) {
                        eventPreMotion.yaw = Scaffold_.mc.thePlayer.rotationYaw;
                        if (((IKeyBinding)Scaffold_.mc.gameSettings.keyBindLeft).getPress()) {
                            eventPreMotion.yaw += 45.0f;
                        }
                        else if (((IKeyBinding)Scaffold_.mc.gameSettings.keyBindRight).getPress()) {
                            eventPreMotion.yaw -= 45.0f;
                        }
                    }
                    else if (((IKeyBinding)Scaffold_.mc.gameSettings.keyBindLeft).getPress()) {
                        eventPreMotion.yaw += 90.0f;
                    }
                    else if (((IKeyBinding)Scaffold_.mc.gameSettings.keyBindRight).getPress()) {
                        eventPreMotion.yaw -= 90.0f;
                    }
                    eventPreMotion.yaw += new Random().nextInt(5);
                    eventPreMotion.pitch = 82 + new Random().nextInt(3);
                    if (Class200.MovementInput() && !this.nosprint.getValueState()) {
                        Class200.setSpeed(0.11);
                    }
                }
            }
        }
        Scaffold_.mc.thePlayer.rotationYawHead = eventPreMotion.getYaw();
        Scaffold_.mc.thePlayer.renderYawOffset = eventPreMotion.getYaw();
    }
    
    public Vec3 scale(final Vec3 vec3, final double n) {
        return new Vec3(vec3.xCoord * n, vec3.yCoord * n, vec3.zCoord * n);
    }
    
    @EventTarget
    public void onPost(final EventPostMotion eventPostMotion) {
        if (Scaffold_.mc.thePlayer != null && this.blockData != null) {
            final int blockItem = this.getBlockItem();
            final Random random = new Random();
            final Item getItem = Scaffold_.mc.thePlayer.inventory.getStackInSlot(blockItem).getItem();
            final boolean b = Scaffold_.mc.thePlayer.inventory.currentItem != blockItem;
            if (b) {
                Scaffold_.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(blockItem));
            }
            if (blockItem != -1 && getItem != null && getItem instanceof ItemBlock) {
                Vec3 vec3;
                if (this.aac.getValueState()) {
                    vec3 = this.scale(new Vec3((Vec3i)this.blockData.pos).addVector((double)(random.nextFloat() / 3.0f), (double)(random.nextFloat() / 3.0f), (double)(random.nextFloat() / 3.0f)).add(new Vec3(this.blockData.face.getDirectionVec())), random.nextFloat() / 3.0f);
                }
                else {
                    vec3 = this.scale(new Vec3((Vec3i)this.blockData.pos).addVector(0.5, 0.5, 0.5).add(new Vec3(this.blockData.face.getDirectionVec())), 0.5);
                }
                if (Scaffold_.mc.playerController.onPlayerRightClick(Scaffold_.mc.thePlayer, Scaffold_.mc.theWorld, Scaffold_.mc.thePlayer.inventory.getStackInSlot(blockItem), this.blockData.pos, this.blockData.face, vec3) && (this.time.isDelayComplete(random.nextInt(300)) || !this.aac.getValueState())) {
                    this.delay.reset();
                    this.blockData = null;
                    this.time.reset();
                    if (this.tower.getValueState() && !((IKeyBinding)Scaffold_.mc.gameSettings.keyBindRight).getPress() && !((IKeyBinding)Scaffold_.mc.gameSettings.keyBindLeft).getPress() && !((IKeyBinding)Scaffold_.mc.gameSettings.keyBindForward).getPress() && ((IKeyBinding)Scaffold_.mc.gameSettings.keyBindJump).getPress()) {
                        final EntityPlayerSP thePlayer = Scaffold_.mc.thePlayer;
                        final EntityPlayerSP thePlayer2 = Scaffold_.mc.thePlayer;
                        final double n = 0.0;
                        thePlayer2.motionZ = n;
                        thePlayer.motionX = n;
                        Scaffold_.mc.thePlayer.motionY = 0.4199323;
                        if (this.timer2.isDelayComplete(1500L)) {
                            Scaffold_.mc.thePlayer.motionY = -0.27994532;
                            this.timer2.reset();
                        }
                    }
                    if (this.noSwing.getValueState()) {
                        Scaffold_.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
                    }
                    else {
                        Scaffold_.mc.thePlayer.swingItem();
                    }
                }
            }
            if (b) {
                this.sendCurrentItem();
            }
        }
        ((IMinecraft)Scaffold_.mc).setRightClickDelayTimer(6);
    }
    
    public float[] getIntaveRots(final BlockPos blockPos, final EnumFacing enumFacing) {
        double n = blockPos.getX() + 0.5;
        double n2 = blockPos.getY() + 0.5;
        double n3 = blockPos.getZ() + 0.5;
        if (enumFacing != null) {
            if (EnumFacing.UP != null) {
                n2 += 0.5;
            }
            else if (EnumFacing.DOWN != null) {
                n2 -= 0.5;
            }
            else if (EnumFacing.WEST != null) {
                n += 0.5;
            }
            else if (EnumFacing.EAST != null) {
                n -= 0.5;
            }
            else if (EnumFacing.NORTH != null) {
                n3 += 0.5;
            }
            else if (EnumFacing.SOUTH != null) {
                n3 -= 0.5;
            }
        }
        final double n4 = n - Scaffold_.mc.thePlayer.posX;
        final double n5 = n2 - Scaffold_.mc.thePlayer.posY + Scaffold_.mc.thePlayer.getEyeHeight();
        final double n6 = n3 - Scaffold_.mc.thePlayer.posZ;
        return new float[] { MathHelper.wrapAngleTo180_float((float)(Math.atan2(n6, n4) * 180.0 / 3.141592653589793) - 90.0f), MathHelper.wrapAngleTo180_float((float)(-Math.atan2(n5, Math.sqrt(n4 * n4 + n6 * n6)) * 180.0 / 3.141592653589793)) };
    }
    
    private double getDoubleRandom(final double n, final double n2) {
        return ThreadLocalRandom.current().nextDouble(n, n2);
    }
    
    @EventTarget
    public void onSafe(final EventSafeWalk eventSafeWalk) {
        eventSafeWalk.setSafe(true);
    }
    
    private boolean canPlace(final EntityPlayerSP entityPlayerSP, final WorldClient worldClient, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final Vec3 vec3) {
        return itemStack.getItem() instanceof ItemBlock && ((ItemBlock)itemStack.getItem()).canPlaceBlockOnSide((World)worldClient, blockPos, enumFacing, (EntityPlayer)entityPlayerSP, itemStack);
    }
    
    private void setBlockAndFacing(final BlockPos blockPos) {
        if (Scaffold_.mc.theWorld.getBlockState(blockPos.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.blockpos = blockPos.add(0, -1, 0);
            this.facing = EnumFacing.UP;
        }
        else if (Scaffold_.mc.theWorld.getBlockState(blockPos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.blockpos = blockPos.add(-1, 0, 0);
            this.facing = EnumFacing.EAST;
        }
        else if (Scaffold_.mc.theWorld.getBlockState(blockPos.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.blockpos = blockPos.add(1, 0, 0);
            this.facing = EnumFacing.WEST;
        }
        else if (Scaffold_.mc.theWorld.getBlockState(blockPos.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.blockpos = blockPos.add(0, 0, -1);
            this.facing = EnumFacing.SOUTH;
        }
        else if (Scaffold_.mc.theWorld.getBlockState(blockPos.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.blockpos = blockPos.add(0, 0, 1);
            this.facing = EnumFacing.NORTH;
        }
        else {
            this.facing = null;
        }
    }
    
    private void sendCurrentItem() {
        Scaffold_.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(Scaffold_.mc.thePlayer.inventory.currentItem));
    }
    
    private int getBlockItem() {
        int n = -1;
        for (int i = 8; i >= 0; --i) {
            if (Scaffold_.mc.thePlayer.inventory.getStackInSlot(i) != null && Scaffold_.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && !Scaffold_.blacklistedBlocks.contains(Block.getBlockFromItem(Scaffold_.mc.thePlayer.inventory.getStackInSlot(i).getItem()))) {
                n = i;
            }
        }
        return n;
    }
    
    private Class273 getBlockData(final BlockPos blockPos) {
        if (this.getBlockData(blockPos, 1) != null) {
            return this.getBlockData(blockPos, 1);
        }
        if (this.getBlockData(blockPos.down(), 1) != null) {
            return this.getBlockData(blockPos.down(), 1);
        }
        return null;
    }
    
    public Class273 getBlockData(final BlockPos blockPos, final int n) {
        return (Class273)((Scaffold_.mc.theWorld.getBlockState(blockPos.add(0, 0, n)).getBlock() != Blocks.air) ? new Class273(blockPos.add(0, 0, n), EnumFacing.NORTH) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(0, 0, -n)).getBlock() != Blocks.air) ? new Class273(blockPos.add(0, 0, -n), EnumFacing.SOUTH) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(n, 0, 0)).getBlock() != Blocks.air) ? new Class273(blockPos.add(n, 0, 0), EnumFacing.WEST) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(-n, 0, 0)).getBlock() != Blocks.air) ? new Class273(blockPos.add(-n, 0, 0), EnumFacing.EAST) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(0, -n, 0)).getBlock() != Blocks.air) ? new Class273(blockPos.add(0, -n, 0), EnumFacing.UP) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(1, 0, n)).getBlock() != Blocks.air) ? new Class273(blockPos.add(1, 0, n), EnumFacing.NORTH) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(-1, 0, -n)).getBlock() != Blocks.air) ? new Class273(blockPos.add(-1, 0, -n), EnumFacing.SOUTH) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(n, 0, 1)).getBlock() != Blocks.air) ? new Class273(blockPos.add(n, 0, 1), EnumFacing.WEST) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(-n, 0, -1)).getBlock() != Blocks.air) ? new Class273(blockPos.add(-n, 0, -1), EnumFacing.EAST) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(-1, 0, n)).getBlock() != Blocks.air) ? new Class273(blockPos.add(-1, 0, n), EnumFacing.NORTH) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(1, 0, -n)).getBlock() != Blocks.air) ? new Class273(blockPos.add(1, 0, -n), EnumFacing.SOUTH) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(n, 0, -1)).getBlock() != Blocks.air) ? new Class273(blockPos.add(n, 0, -1), EnumFacing.WEST) : ((Scaffold_.mc.theWorld.getBlockState(blockPos.add(-n, 0, 1)).getBlock() != Blocks.air) ? new Class273(blockPos.add(-n, 0, 1), EnumFacing.EAST) : null)))))))))))));
    }
    
    public Vec3 getBlockSide(final BlockPos blockPos, final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.NORTH) ? new Vec3((double)blockPos.getX(), (double)blockPos.getY(), blockPos.getZ() - 0.5) : ((enumFacing == EnumFacing.EAST) ? new Vec3(blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ()) : ((enumFacing == EnumFacing.SOUTH) ? new Vec3((double)blockPos.getX(), (double)blockPos.getY(), blockPos.getZ() + 0.5) : ((enumFacing == EnumFacing.WEST) ? new Vec3(blockPos.getX() - 0.5, (double)blockPos.getY(), (double)blockPos.getZ()) : new Vec3((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()))));
    }
    
    public void onEnable() {
        this.timer2.reset();
        super.onEnable();
    }
    
    public void onDisable() {
        super.onDisable();
        this.timer2.reset();
        this.sendCurrentItem();
        ((IKeyBinding)Scaffold_.mc.gameSettings.keyBindSneak).setPress(false);
        Class211.getTimer().timerSpeed = 1.0f;
    }
    
    static int[] Facing() {
        final int[] facing = Scaffold_.Facing;
        if (Scaffold_.Facing != null) {
            return facing;
        }
        final int[] facing2 = new int[EnumFacing.values().length];
        try {
            facing2[EnumFacing.DOWN.ordinal()] = 1;
            facing2[EnumFacing.EAST.ordinal()] = 6;
            facing2[EnumFacing.NORTH.ordinal()] = 3;
            facing2[EnumFacing.SOUTH.ordinal()] = 4;
            facing2[EnumFacing.UP.ordinal()] = 2;
            facing2[EnumFacing.WEST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        return Scaffold_.Facing = facing2;
    }
    
    static {
        Scaffold_.blacklistedBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.ender_chest);
    }
    
    public class Class273
    {
        public BlockPos pos;
        public EnumFacing face;
        final Scaffold_ this$0;
        
        
        public Class273(final Scaffold_ this$0, final BlockPos pos, final EnumFacing face) {
            ((Class273)this).this$0 = this$0;
            ((Class273)this).pos = pos;
            ((Class273)this).face = face;
        }
    }
}
