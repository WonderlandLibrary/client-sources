package eze.modules.player;

import eze.modules.*;
import net.minecraft.client.*;
import eze.settings.*;
import eze.events.*;
import eze.events.listeners.*;
import net.minecraft.item.*;
import eze.util.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;

public class ScaffoldHopeItWorks extends Module
{
    public BooleanSetting noSprint;
    public static BooleanSetting safewalk;
    public BooleanSetting sneakOnPlace;
    public BooleanSetting keepRotations;
    public NumberSetting timerspeed;
    public static boolean isEnabled;
    public static Minecraft mc;
    private BlockUtils2.BlockData blockData;
    private Timer timer;
    private int delay;
    public long LastBuild;
    public float yaw;
    public float pitch;
    
    static {
        ScaffoldHopeItWorks.safewalk = new BooleanSetting("Safewalk", true);
        ScaffoldHopeItWorks.isEnabled = false;
        ScaffoldHopeItWorks.mc = Minecraft.getMinecraft();
    }
    
    public ScaffoldHopeItWorks() {
        super("Scaffold", 49, Category.PLAYER);
        this.noSprint = new BooleanSetting("No sprint", false);
        this.sneakOnPlace = new BooleanSetting("Sneak on place", false);
        this.keepRotations = new BooleanSetting("Keep Rotations", false);
        this.timerspeed = new NumberSetting("Timer", 1.0, 0.1, 4.0, 0.1);
        this.timer = new Timer();
        this.addSettings(this.noSprint, ScaffoldHopeItWorks.safewalk, this.sneakOnPlace, this.keepRotations, this.timerspeed);
    }
    
    @Override
    public void onEnable() {
        ScaffoldHopeItWorks.isEnabled = true;
    }
    
    @Override
    public void onDisable() {
        ScaffoldHopeItWorks.isEnabled = false;
        ScaffoldHopeItWorks.mc.gameSettings.keyBindSneak.pressed = false;
        ScaffoldHopeItWorks.mc.timer.timerSpeed = 1.0f;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion) {
            if (this.noSprint.isEnabled()) {
                ScaffoldHopeItWorks.mc.thePlayer.setSprinting(false);
            }
            if (this.sneakOnPlace.isEnabled() && this.timer.hasTimeElapsed(50L, true)) {
                ScaffoldHopeItWorks.mc.gameSettings.keyBindSneak.pressed = false;
            }
            if (!this.sneakOnPlace.isEnabled() && ScaffoldHopeItWorks.mc.gameSettings.keyBindSneak.pressed) {
                ScaffoldHopeItWorks.mc.gameSettings.keyBindSneak.pressed = false;
            }
            final EventMotion event = (EventMotion)e;
            if (ScaffoldHopeItWorks.mc.thePlayer.getCurrentEquippedItem() == null) {
                return;
            }
            if (ScaffoldHopeItWorks.mc.thePlayer.getCurrentEquippedItem().getItem() == Item.getItemById(0)) {
                return;
            }
            if (!(ScaffoldHopeItWorks.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock)) {
                return;
            }
            if (e.isPre()) {
                ScaffoldHopeItWorks.mc.timer.timerSpeed = (float)this.timerspeed.getValue();
                if (this.sneakOnPlace.isEnabled()) {
                    ScaffoldHopeItWorks.mc.thePlayer.setSneaking(true);
                }
                if (ScaffoldHopeItWorks.mc.theWorld == null || ScaffoldHopeItWorks.mc.thePlayer == null) {
                    return;
                }
                try {
                    this.blockData = null;
                    if (ScaffoldHopeItWorks.mc.thePlayer.getHeldItem() != null && ScaffoldHopeItWorks.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
                        final BlockPos player = new BlockPos(ScaffoldHopeItWorks.mc.thePlayer.posX, ScaffoldHopeItWorks.mc.thePlayer.posY - 1.0, ScaffoldHopeItWorks.mc.thePlayer.posZ);
                        this.blockData = this.getBlockData(player);
                        final float[] values = RotationUtils.getRotationFromPosition(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ());
                        if (this.keepRotations.enabled) {
                            event.setYaw(values[0]);
                            event.setPitch(90.0f);
                        }
                    }
                }
                catch (Exception ex) {}
            }
            if (e.isPost()) {
                if (this.blockData == null) {
                    return;
                }
                if (ScaffoldHopeItWorks.mc.theWorld == null || ScaffoldHopeItWorks.mc.thePlayer == null) {
                    return;
                }
                final Random random = new Random();
                if (this.timer.hasTimeElapsed(1 + random.nextInt(9), this.expanded) && ScaffoldHopeItWorks.mc.playerController.func_178890_a(ScaffoldHopeItWorks.mc.thePlayer, ScaffoldHopeItWorks.mc.theWorld, ScaffoldHopeItWorks.mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                    if (!this.keepRotations.enabled) {
                        final BlockPos player2 = new BlockPos(ScaffoldHopeItWorks.mc.thePlayer.posX, ScaffoldHopeItWorks.mc.thePlayer.posY - 1.0, ScaffoldHopeItWorks.mc.thePlayer.posZ);
                        this.blockData = this.getBlockData(player2);
                        final float[] values2 = RotationUtils.getRotationFromPosition(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ());
                        event.setYaw(values2[0]);
                        event.setPitch(90.0f);
                    }
                    if (this.sneakOnPlace.enabled) {
                        ScaffoldHopeItWorks.mc.gameSettings.keyBindSneak.pressed = true;
                    }
                    this.LastBuild = System.currentTimeMillis();
                    ScaffoldHopeItWorks.mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                    ScaffoldHopeItWorks.mc.thePlayer.swingItem();
                }
            }
        }
    }
    
    public BlockUtils2.BlockData getBlockData(final BlockPos pos) {
        return (ScaffoldHopeItWorks.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) ? new BlockUtils2.BlockData(pos.add(0, -1, 0), EnumFacing.UP) : ((ScaffoldHopeItWorks.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) ? new BlockUtils2.BlockData(pos.add(-1, 0, 0), EnumFacing.EAST) : ((ScaffoldHopeItWorks.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) ? new BlockUtils2.BlockData(pos.add(1, 0, 0), EnumFacing.WEST) : ((ScaffoldHopeItWorks.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) ? new BlockUtils2.BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH) : ((ScaffoldHopeItWorks.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) ? new BlockUtils2.BlockData(pos.add(0, 0, 1), EnumFacing.NORTH) : null))));
    }
    
    public static float[] getFacingRotations(final int x, final int y, final int z, final EnumFacing facing, final boolean real) {
        Minecraft.getMinecraft();
        final EntitySnowball temp = new EntitySnowball(ScaffoldHopeItWorks.mc.theWorld);
        if (real) {
            final String str;
            switch (str = facing.getName()) {
                case "up": {
                    temp.posX = x + 0.5;
                    temp.posY = y + 0.5;
                    temp.posZ = z + 0.5;
                    break;
                }
                case "east": {
                    temp.posX = x + 1;
                    temp.posY = y + 0.5;
                    temp.posZ = z + 0.5;
                    break;
                }
                case "west": {
                    temp.posX = x;
                    temp.posY = y + 0.5;
                    temp.posZ = z + 0.5;
                    break;
                }
                case "north": {
                    temp.posX = x + 0.5;
                    temp.posY = y + 0.5;
                    temp.posZ = z;
                    break;
                }
                case "south": {
                    temp.posX = x + 0.5;
                    temp.posY = y + 0.5;
                    temp.posZ = z + 1;
                    break;
                }
                default:
                    break;
            }
            return RotationUtils.getRotationFromPosition(temp.posX, temp.posY, temp.posZ);
        }
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        final EntitySnowball entitySnowball = temp;
        entitySnowball.posX += facing.getDirectionVec().getX() * 0.25;
        final EntitySnowball entitySnowball2 = temp;
        entitySnowball2.posY += facing.getDirectionVec().getY() * 0.25;
        final EntitySnowball entitySnowball3 = temp;
        entitySnowball3.posZ += facing.getDirectionVec().getZ() * 0.25;
        return RotationUtils.getRotationFromPosition(temp.posX, temp.posY, temp.posZ);
    }
}
