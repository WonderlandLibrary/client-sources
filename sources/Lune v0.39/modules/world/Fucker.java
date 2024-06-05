package me.superskidder.lune.modules.world;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.combat.KillAura;
import me.superskidder.lune.events.EventPostUpdate;
import me.superskidder.lune.events.EventPreUpdate;
import me.superskidder.lune.guis.notification.TimeHelper;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Num;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.*;

public class Fucker extends Mod {
    private final Num<Double> range = new Num<>("Range", 3.0, 1.0, 6.0);
    private final Num<Double> delay = new Num<>("Delay", 100.0, 100.0, 1000.0);
    private final Bool<Boolean> swing = new Bool<>("NoSwing", false);
    private final Bool<Boolean> noHit = new Bool<>("PauseFuckWhenHit", false);
    private BlockPos fucking;
    private boolean ready = false;
    private final TimeHelper timer = new TimeHelper();

    public Fucker() {
        super("BedFucker", ModCategory.World,"Bed Fucker");
        this.addValues(this.range, this.delay, this.swing, this.noHit);
    }

    @Override
    public void onEnabled() {
        timer.reset();
    }

    @Override
    public void onDisable() {
        this.ready = false;
        this.fucking = null;
    }


    @EventTarget
    private void onPreUpdate(EventPreUpdate e) {
        if (noHit.getValue() && Lune.moduleManager.getModByClass(KillAura.class).getState() && KillAura.target != null) {
            return;
        }
        int reach = this.range.getValue().intValue();
        for(int y = reach; y >= -reach; --y) {
            for (int x = -reach; x <= reach; ++x) {
                for (int z = -reach; z <= reach; ++z) {
                    boolean confirm = x != 0 || z != 0;
                    if (mc.thePlayer.isSneaking()) {
                        confirm = !confirm;
                    }
                    if (confirm) {
                        BlockPos pos = new BlockPos(mc.thePlayer.posX + (double) x, mc.thePlayer.posY + (double) y, mc.thePlayer.posZ + (double) z);
                        if (this.blockChecks(mc.theWorld.getBlockState(pos).getBlock()) && mc.thePlayer.getDistance(mc.thePlayer.posX + (double) x, mc.thePlayer.posY + (double) y, mc.thePlayer.posZ + (double) z) < (double) mc.playerController.getBlockReachDistance() - 0.5D) {
                            float[] rotations = this.getBlockRotations(mc.thePlayer.posX + (double) x, mc.thePlayer.posY + (double) y, mc.thePlayer.posZ + (double) z);
                            e.setYaw(rotations[0]);
                            e.setPitch(rotations[1]);
                            this.fucking = pos;
                            ready = true;
                            return;
                        }else {
                        	fucking = null;
                        	ready = false;
                        }
                    }
                }
            }
        }
    }

    @EventTarget
    public void onPostUpdate(EventPostUpdate e2) {
        if (noHit.getValue() && Lune.moduleManager.getModByClass(KillAura.class).getState() && KillAura.target != null) {
            return;
        }
        
        
        if (this.timer.hasReached(delay.getValue())) {
        	if(ready) {
        		System.out.println("Start Break!");
        		mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK,fucking,EnumFacing.DOWN));
        	}
        	timer.reset();
        }
        if(ready) {
        	mc.thePlayer.swingItem();
        }

    }

    private boolean blockChecks(Block block) {
        return block == Blocks.bed;
    }

    private float[] getBlockRotations(double x, double y, double z) {
        double var4 = x - mc.thePlayer.posX + 0.5D;
        double var5 = z - mc.thePlayer.posZ + 0.5D;
        double var6 = y - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() - 1.0D);
        double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        float var8 = (float)(Math.atan2(var5, var4) * 180.0D / 3.141592653589793D) - 90.0F;
        return new float[]{var8, (float)(-(Math.atan2(var6, var7) * 180.0D / 3.141592653589793D))};
    }

    private EnumFacing getFacingDirection(BlockPos pos) {
        EnumFacing direction = null;
        if (!mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isFullCube()) {
            direction = EnumFacing.UP;
        } else if (!mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isFullCube()) {
            direction = EnumFacing.DOWN;
        } else if (!mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isFullCube()) {
            direction = EnumFacing.EAST;
        } else if (!mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isFullCube()) {
            direction = EnumFacing.WEST;
        } else if (!mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isFullCube()) {
            direction = EnumFacing.SOUTH;
        } else if (!mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isFullCube()) {
            direction = EnumFacing.NORTH;
        }
        MovingObjectPosition rayResult = mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), new Vec3((double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D));
        return rayResult != null && rayResult.getBlockPos() == pos ? rayResult.sideHit : direction;
    }
}