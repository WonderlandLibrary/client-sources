package me.vaziak.sensation.client.impl.misc;

import java.util.ArrayList;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.impl.combat.KillAura;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ChestAura extends Module {

    private boolean openInventory;
    private ArrayList<BlockPos> blackListedLocation;
    private TimerUtil resetPositions;
	private int position;
    public ChestAura() {
        super("Chest Aura", Category.MISC);
        resetPositions = new TimerUtil();
        blackListedLocation = new ArrayList();
    }
    
    public void onEnable() {
    	resetPositions.reset();
    }
    
    public void onDisable() {
    	resetPositions.reset();
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent event) {
    	if (resetPositions.hasPassed(30000)) {
    		blackListedLocation.clear();
    		resetPositions.reset();
    	}
        BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + position, mc.thePlayer.posZ);

        BlockData blockData = retriveBlockData(1, true, true, playerPos);
        if (position >= 2) {
        	position = 0;
        } else {
        	position ++;
        }
        boolean steal = ((KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura")).targetIndex == -1 || !Sensation.instance.cheatManager.isModuleEnabled("Kill Aura");
        if (blockData == null || openInventory || !steal)
            return;
        BlockPos sideBlock = blockData.position;
        Entity temp = new EntitySnowball(mc.theWorld);
        temp.posX = (sideBlock.getX() + .5);
        temp.posY = (sideBlock.getY() + .5);
        temp.posZ = (sideBlock.getZ() + .5);
        if (mc.theWorld.getBlockState(blockData.position).getBlock() == Blocks.chest && !blackListedLocation.contains(blockData.position)) {
        	if (event.isPre()) {
        		setYaw(event, getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), blockData.face)[0]);
        		event.setPitch(getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), blockData.face)[1] + 12);
        	} else {
                double hitvecx = (blockData.position.getX() + .5) + MathUtils.getRandomInRange(-.03,.263) + (blockData.face.getFrontOffsetX() / 2);
                double hitvecy = (blockData.position.getY() + .5) + MathUtils.getRandomInRange(-.03,.263) + (blockData.face.getFrontOffsetY() / 2);
                double hitvecz = (blockData.position.getZ() + .5) + MathUtils.getRandomInRange(-.03,.263) + (blockData.face.getFrontOffsetZ() / 2);
                Vec3 vec = new Vec3(hitvecx , hitvecy , hitvecz );

        		blackListedLocation.add(blockData.position);
        		mc.playerController.onPlayerRightClick(mc.thePlayer,mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), blockData.position, blockData.face, vec);
        		mc.thePlayer.swingItem(); 
        		openInventory = true;
        	}
        }
    }
    
    public void setYaw(PlayerUpdateEvent e, float yaw) {
    	boolean notarget = ((KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura")).targetIndex == -1 || !Sensation.instance.cheatManager.isModuleEnabled("Kill Aura");
    	if (notarget) e.setYaw(yaw);
    }
    
    private float[] getBlockRotations(int x, int y, int z, EnumFacing facing)
    {
        Entity temp = new EntitySnowball(mc.theWorld);
        temp.posX = (x + 0.5);
        temp.posY = (y + 0.5);
        temp.posZ = (z + 0.5);
        return mc.thePlayer.canEntityBeSeen(temp) ? getAngles(temp) : getRotationToBlock(new BlockPos(x,y,z), facing);
    }

    private float[] getAngles(Entity e) {
        return new float[] { getYawChangeToEntity(e) + mc.thePlayer.rotationYaw, getPitchChangeToEntity(e) + mc.thePlayer.rotationPitch };
    }

    private float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity;
        if ((deltaZ < 0) && (deltaX < 0)) {
            yawToEntity = 90 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            if ((deltaZ < 0) && (deltaX > 0.0D)) {
                yawToEntity = -90 + Math.toDegrees(Math.atan(deltaZ / deltaX));
            } else {
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
            }
        }
        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float)yawToEntity));
    }
    
    public float[] getRotationToBlock(BlockPos pos, EnumFacing face) {

        /*
         * Legendas code
         *  +
         *  my own code
         * */
        double xDiff = pos.getX() + (.5) - mc.thePlayer.posX + face.getDirectionVec().getX() / (2);
        double zDiff = pos.getZ() + (.5) - mc.thePlayer.posZ + face.getDirectionVec().getZ() / (2);
        double yDiff = pos.getY() - mc.thePlayer.posY - 1;
        double distance = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) -Math.toDegrees(Math.atan2(xDiff, zDiff));
        float pitch = (float) -Math.toDegrees(Math.atan(yDiff / distance));

        return new float[] {Math.abs(yaw - mc.thePlayer.rotationYaw) < .1 ? mc.thePlayer.rotationYaw : yaw, Math.abs(pitch - mc.thePlayer.rotationPitch) < .1 ? mc.thePlayer.rotationPitch : pitch};
    }

    private float getPitchChangeToEntity(Entity entity) {
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - 0.4 - mc.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }

    @Collect
    public void onSendPacket(SendPacketEvent event) {
        if (event.getPacket() instanceof C0DPacketCloseWindow) {
            openInventory = false;
        }
    }

    private class BlockData {
        // Taken from ye old MCP - go search the shit
        public BlockPos position;
        public EnumFacing face;

        private BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

    /*
     * Block Data getter with all your cardinal directions + upwards facing,
     *
     * This can be used to make things like:
     *  - Scaffolds
     *  - ChestAuras
     *  - AutoBuilders
     *  - AutoPots (I know some people used blockdata to get a spot to look at lol)
     *  - Bed Breakers/Nukers
     *  - Decimators/Nukers
     *  - Anything that requires you to get a SHITLOAD of surrounding blocks
     *
     *
     *  IF ANYONE MANAGES TO CONVERT THIS TO LESS THAN 400 LINES USING A FORLOOP OR A LAMBDA CONGRATS
     *
     *  PLEASE FUCKING DM ME LOL
     */
    private BlockData retriveBlockData(int i, boolean placeable, boolean godown, BlockPos pos) {
        EnumFacing up =  EnumFacing.UP;
        EnumFacing east = EnumFacing.EAST;
        EnumFacing west = EnumFacing.WEST;
        EnumFacing north = EnumFacing.NORTH;
        EnumFacing south = EnumFacing.SOUTH;

        if (isValidPosition(true,pos.add(0, -i, 0))) {
            return new BlockData(pos.add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, -i, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, 0, i))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, 0, -i))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(-i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, -i, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, 0, i))) {
            return new BlockData(pos.add(i, 0, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, 0, -i))) {
            return new BlockData(pos.add(i, 0, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(-i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, -i, 0))) {
            return new BlockData(pos.add(0, 0, i).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, i).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, i).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(-i, 0, 0))) {
            return new BlockData(pos.add(0, 0, i).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(i, 0, 0))) {
            return new BlockData(pos.add(0, 0, i).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, -i, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, -i).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, -i).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(-i, 0, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(i, 0, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, -i, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, 0, i))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, 0, -i))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(-i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, -i, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, 0, i))) {
            return new BlockData(pos.add(i, 0, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, 0, -i))) {
            return new BlockData(pos.add(i, 0, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(-i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, -i, 0))) {
            return new BlockData(pos.add(0, 0, i).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, i).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, i).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(-i, 0, 0))) {
            return new BlockData(pos.add(0, 0, i).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(i, 0, 0))) {
            return new BlockData(pos.add(0, 0, i).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, -i, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, -i).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, -i).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(-i, 0, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(i, 0, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(0, -i, 0))) {
            return new BlockData(pos.add(0, -i, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(0, 0, i))) {
            return new BlockData(pos.add(0, -i, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(0, 0, -i))) {
            return new BlockData(pos.add(0, -i, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(-i, 0, 0))) {
            return new BlockData(pos.add(0, -i, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(i, 0, 0))) {
            return new BlockData(pos.add(0, -i, 0).add(i, 0, 0), west);
        }
        return null;
    }

    public static boolean isValidPosition(boolean placeable, BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        return (block == Blocks.chest);
    }
}
