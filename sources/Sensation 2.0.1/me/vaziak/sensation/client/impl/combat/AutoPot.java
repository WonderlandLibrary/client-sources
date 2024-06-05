package me.vaziak.sensation.client.impl.combat;

import java.awt.Event;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

/**
 * Made by verble
 * -------------------------------
 * Donated to Niada ~ Nov 20th 2018
 *
 * Code modified to bypass some other shitty server in like idk March or April 2019
 **/
public class AutoPot extends Module {
    TimerUtil stopwatch;

	private BooleanProperty speedPots = new BooleanProperty("Speed Pots", "Slpash Speeds", false);
	private BooleanProperty noJumpPots = new BooleanProperty("Ignore Jump Pots", "Slpash Speeds without jumps", () -> speedPots.getValue(),  false);
	private BooleanProperty healthPots = new BooleanProperty("Health Pots", "Slpash Speeds", false);
    public AutoPot() {
        super("AutoPot", Category.COMBAT);
        registerValue(speedPots, healthPots, noJumpPots);
        stopwatch = new TimerUtil();
    }
    private float[] getBlockRotations(int x, int y, int z, EnumFacing facing)
    {
        Entity temp = new EntitySnowball(mc.theWorld);
        temp.posX = (x + 0.5);
        temp.posY = (y + (0.5));
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

    private float getPitchChangeToEntity(Entity entity) {
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - 0.4 - mc.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }

    public float[] getRotationToBlock(BlockPos pos, EnumFacing face) {
    	double random = MathUtils.getRandomInRange(.45, .55);
    	int ranface = MathUtils.getRandomInRange(2, 4);
        double xDiff = pos.getX() + (.5) - mc.thePlayer.posX + face.getDirectionVec().getX() / (2);
        double zDiff = pos.getZ() + (.5) - mc.thePlayer.posZ + face.getDirectionVec().getZ() / (2);
        double yDiff = pos.getY() - mc.thePlayer.posY - 1;
        double distance = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) -Math.toDegrees(Math.atan2(xDiff, zDiff));
        float pitch = (float) -Math.toDegrees(Math.atan(yDiff / distance));

        return new float[] {Math.abs(yaw - mc.thePlayer.rotationYaw) < .1 ? mc.thePlayer.rotationYaw : yaw, Math.abs(pitch - mc.thePlayer.rotationPitch) < .1 ? mc.thePlayer.rotationPitch : pitch};
    }
    
    public BlockData find(Vec3 offset3, int expand) {

        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ; 
        
        EnumFacing[] invert = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
        BlockPos position = new BlockPos(new Vec3(x,y,z).add(offset3)).offset(EnumFacing.DOWN);
        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos offset = position.offset(facing);
            if (mc.theWorld.getBlockState(offset).getBlock() instanceof BlockAir || !rayTrace(mc.thePlayer.getLook(0.0f), getPositionByFace(offset, invert[facing.ordinal()])))
                continue;
            return new BlockData(offset, invert[facing.ordinal()]);
        }
        BlockPos[] offsets = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(0, 0, 2), new BlockPos(0, 0, -2), new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0)};
        for (BlockPos offset : offsets) {
            BlockPos offsetPos = position.add(offset.getX(), 0, offset.getZ());
            if (!(mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir)) continue;
            for (EnumFacing facing : EnumFacing.values()) {
                BlockPos offset2 = offsetPos.offset(facing);
                if (mc.theWorld.getBlockState(offset2).getBlock() instanceof BlockAir || !rayTrace(mc.thePlayer.getLook(0.01f), getPositionByFace(offset, invert[facing.ordinal()])))
                    continue;
                return new BlockData(offset2, invert[facing.ordinal()]);
            }
        }
        return null;
    }
    
    public Vec3 getPositionByFace(BlockPos position, EnumFacing facing) {
        Vec3 offset = new Vec3((double) facing.getDirectionVec().getX() / 2.0, (double) facing.getDirectionVec().getY() / 2.0, (double) facing.getDirectionVec().getZ() / 2.0);
        Vec3 point = new Vec3((double) position.getX() + 0.5, (double) position.getY() + 0.5, (double) position.getZ() + 0.5);
        return point.add(offset);
    }
    
    public boolean rayTrace(Vec3 origin, Vec3 position) {
        Vec3 difference = position.subtract(origin);
        int steps = 10;
        double x = difference.xCoord / (double) steps;
        double y = difference.yCoord / (double) steps;
        double z = difference.zCoord / (double) steps;
        Vec3 point = origin;
        for (int i = 0; i < steps; ++i) {
            BlockPos blockPosition = new BlockPos(point = point.addVector(x, y, z));
            IBlockState blockState = mc.theWorld.getBlockState(blockPosition);
            if (blockState.getBlock() instanceof BlockLiquid || blockState.getBlock() instanceof BlockAir) continue;
            AxisAlignedBB boundingBox = blockState.getBlock().getCollisionBoundingBox(mc.theWorld, blockPosition, blockState);
            if (boundingBox == null) {
                boundingBox = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            }
            if (!(boundingBox = boundingBox.offset(blockPosition)).isVecInside(point)) continue;
            return false;
        }
        return true;
    }
    
    private class BlockData {
        //Taken from ye old MCP - go search the shit
        public BlockPos position;
        public EnumFacing face;

        private BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) { 

        if (mc.thePlayer != null && speedPots.getValue() && !(updateSpeedCounter() == 0)) {
            if (!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                if (stopwatch.hasPassed(1000)) { 
                    if (doesHotbarHaveSpeedPots()) {
                    	for (int index = 36; index < 45; index++) {
                            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                            if (stack == null) {
                                continue;
                            }

                            if (isSpeedPot(stack)) {
                                final int oldslot = mc.thePlayer.inventory.currentItem;
                                BlockData getBlockData = find(new Vec3(0,0, 0), 0);
                                if (getBlockData != null) { 
                                	playerUpdateEvent.setPitch(getBlockRotations(getBlockData.position.getX(), getBlockData.position.getY(), getBlockData.position.getZ(), getBlockData.face)[1] + 12);
                                    
                                	if (playerUpdateEvent.isPre()) {
                                		playerUpdateEvent.setPitch(getBlockRotations(getBlockData.position.getX(), getBlockData.position.getY(), getBlockData.position.getZ(), getBlockData.face)[1] + 12);
                                	} else {
                                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index - 36));
                                		double hitvecx = (getBlockData.position.getX() + .5) + MathUtils.getRandomInRange(-.08,.29) + (getBlockData.face.getFrontOffsetX() / 2);
                            			double hitvecy = (getBlockData.position.getY() + .5) + MathUtils.getRandomInRange(-.08,.29) + (getBlockData.face.getFrontOffsetY() / 2);
                            			double hitvecz = (getBlockData.position.getZ() + .5) + MathUtils.getRandomInRange(-.08,.29) + (getBlockData.face.getFrontOffsetZ() / 2);
                            			Vec3 vec = new Vec3(hitvecx , hitvecy , hitvecz );
                            			if (!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
	                            			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(playerUpdateEvent.getYaw(), getBlockRotations(getBlockData.position.getX(), getBlockData.position.getY(), getBlockData.position.getZ(), EnumFacing.UP)[1], playerUpdateEvent.isGrounded()));
	                            			mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,mc.thePlayer.inventory.getCurrentItem(), getBlockData.position,EnumFacing.UP,vec);
	                            			mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldslot));
                            			}
                            			stopwatch.reset();
                                	}
                                } 
                                break;
                            }
                        } 
                    } else {
                        getSpeedPotsFromInventory();
                        stopwatch.reset();
                    }
                }
            }
        } 
        if (mc.thePlayer != null && playerUpdateEvent.isPre() && healthPots.getValue() && !(updateCounter() == 0)) {
            if (mc.thePlayer.getHealth() <= 10) {
                if (stopwatch.hasPassed(MathUtils.getRandomInRange(200, 350))) { 
                    if (doesHotbarHavePots()) {
                    	playerUpdateEvent.setPitch(getRotationToBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ), EnumFacing.DOWN)[1]);
                         
                        for (int index = 36; index < 45; index++) {
                            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                            if (stack == null) {
                                continue;
                            }

                            if (isStackSplashHealthPot(stack)) {
                                final int oldslot = mc.thePlayer.inventory.currentItem;
                                BlockData getBlockData = find(new Vec3(0,0, 0), 0);
                                if (getBlockData != null) { 
                                	playerUpdateEvent.setPitch(getBlockRotations(getBlockData.position.getX(), getBlockData.position.getY(), getBlockData.position.getZ(), getBlockData.face)[1] + 12);
                                    
                                	if (playerUpdateEvent.isPre()) {
                                		playerUpdateEvent.setPitch(getBlockRotations(getBlockData.position.getX(), getBlockData.position.getY(), getBlockData.position.getZ(), getBlockData.face)[1] + 12);
                                	} else {
                                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index - 36));
                                		double hitvecx = (getBlockData.position.getX() + .5) + MathUtils.getRandomInRange(-.08,.29) + (getBlockData.face.getFrontOffsetX() / 2);
                            			double hitvecy = (getBlockData.position.getY() + .5) + MathUtils.getRandomInRange(-.08,.29) + (getBlockData.face.getFrontOffsetY() / 2);
                            			double hitvecz = (getBlockData.position.getZ() + .5) + MathUtils.getRandomInRange(-.08,.29) + (getBlockData.face.getFrontOffsetZ() / 2);
                            			Vec3 vec = new Vec3(hitvecx , hitvecy , hitvecz );
                            			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(playerUpdateEvent.getYaw(), getBlockRotations(getBlockData.position.getX(), getBlockData.position.getY(), getBlockData.position.getZ(), EnumFacing.UP)[1], playerUpdateEvent.isGrounded()));
	                            		
                            			mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,mc.thePlayer.inventory.getCurrentItem(), getBlockData.position,EnumFacing.UP,vec);
                              			stopwatch.reset();
                                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldslot));
                                	}
                                }  
                                break;
                            }
                        }
                    } else {
                        getPotsFromInventory();
                    }
                    stopwatch.reset();
                }
            }
        }
    }

    private void getSpeedPotsFromInventory() {
        if (mc.currentScreen instanceof GuiChest) {
            return;
        }
        mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));

        for (int index = 9; index < 36; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                continue;
            }
            if (isSpeedPot(stack)) {
                mc.playerController.windowClick(0, index, 0, 1, mc.thePlayer);
                break;
            }
        }
        mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
		
	}

	private boolean isSpeedPot(ItemStack stack) {
        if (stack == null || stack.getDisplayName().toLowerCase().contains("frog") && noJumpPots.getValue()) {
            return false;
        }
        if (stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem(); 
            
            if (ItemPotion.isSplash(stack.getItemDamage())) { 
 
                for (final Object o : potion.getEffects(stack)) {
                    PotionEffect effect = (PotionEffect) o; 
                    if (effect.getPotionID() == Potion.moveSpeed.id) {
                    	return true;
                    }
                }
            }
        }
        return false; 
	}

	private boolean doesHotbarHaveSpeedPots() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                continue;
            }
            if (isSpeedPot(stack)) {
                return true;
            }
        }
        return false; 
	}
	
	private int updateSpeedCounter() {
        int counter = 0;
        for (int index = 9; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                continue;
            }
            if (isSpeedPot(stack)) {
                counter += stack.stackSize;
            }
        }
        return counter;
    }

	private int updateCounter() {
        int counter = 0;
        for (int index = 9; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                continue;
            }
            if (isStackSplashHealthPot(stack)) {
                counter += stack.stackSize;
            }
        }
        return counter;
    }

    private boolean doesHotbarHavePots() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                continue;
            }
            if (isStackSplashHealthPot(stack)) {
                return true;
            }
        }
        return false;
    }

    private void getPotsFromInventory() {
        if (mc.currentScreen instanceof GuiChest) {
            return;
        }
        mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));

        for (int index = 9; index < 36; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                continue;
            }
            if (isStackSplashHealthPot(stack)) {
                mc.playerController.windowClick(0, index, 0, 1, mc.thePlayer);
                break;
            }
        }
        mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
    }

    private boolean isStackSplashHealthPot(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect) o;
                    if (effect.getPotionID() == Potion.healthBoost.id || effect.getPotionID() == Potion.heal.id) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}