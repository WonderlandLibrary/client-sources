package me.vaziak.sensation.client.impl.movement;

import java.util.Arrays;
import java.util.List;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.*;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.utils.math.AngleUtility;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import me.vaziak.sensation.utils.math.Vector;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.Vec3;


public class Scaffold extends Module {

    BooleanProperty tower            = new BooleanProperty("Tower", "Quickly places blocks under you when you are holding your jump key.", null, true);
    BooleanProperty forceitem        = new BooleanProperty("Force Item","Forces you to hold the scaffold item", null, true);
    BooleanProperty reverse 		 = new BooleanProperty("Reverse Rotations","Allows for sprint scaffolding, fucks with some scaffold checks", null, true);
    BooleanProperty delayedplacement = new BooleanProperty("Delay","Delay placing blocks", null, true);
    DoubleProperty prop_delay        = new DoubleProperty("Place Delay", "Delay between blockplaces", ()-> delayedplacement.getValue(), 60, 2, 200, 1, null);

    long lastPlace;
    TimerUtil timer;
    EnumFacing lastFace;

    int heldItem, delay, places;
    private boolean sneakthing;
    public float yaw, pitch;
    private static List<Block> retardBlock =
            Arrays.asList(
                    //Containers and shet
                    Blocks.enchanting_table,Blocks.furnace, Blocks.carpet, Blocks.crafting_table,Blocks.trapped_chest, Blocks.chest,
                    //Water/Liquids
                    Blocks.air, Blocks.water, Blocks.lava, Blocks.flowing_water, Blocks.flowing_lava,
                    //Snow layers or interactable blocks like buttons
                    Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox,  Blocks.stone_button, Blocks.wooden_button, Blocks.lever,
                    //pressureplates and noteblocks
                    Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate,

                    Blocks.wooden_pressure_plate,  Blocks.heavy_weighted_pressure_plate

                    //Tbh I should probably add flowers and mushrooms to this ngl
            );
    private final AngleUtility angleUtility = new AngleUtility(10, 40, 10, 40);
    private int facing;
    private double height;
    private boolean decrease;
    private BlockData lastBDat;
    private double yheight;
	private boolean towering;
	private int counter;
	private TimerUtil delayTimer;
    public Scaffold() {
        super("Scaffold", Category.MOVEMENT);
        registerValue(tower, reverse, delayedplacement, prop_delay, forceitem);
        timer = new TimerUtil();
        delayTimer = new TimerUtil();
    }

    //We do not need to override the cheat class ondisabele/onenable for scaffold, made that mistake too many times
    public void onDisable() {
    	if (mc.thePlayer == null || mc.theWorld == null) return;
        sneakthing = false;
        C0BPacketEntityAction p1 = new C0BPacketEntityAction(mc.thePlayer, Action.STOP_SNEAKING);
        mc.thePlayer.sendQueue.addToSendQueue(p1);
        if(facing != mc.thePlayer.inventory.currentItem && facing != -1){
            C09PacketHeldItemChange p = new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem);
            mc.thePlayer.sendQueue.addToSendQueueNoEvent(p);
            facing = -1;
        }
        mc.timer.timerSpeed = 1.0f;
        mc.gameSettings.keyBindSneak.pressed = false;
        mc.thePlayer.movementInput.sneak = false;

        boolean onCubecraft = onServer("cubecraft");
        if (forceitem.getValue() || onCubecraft) {
            mc.thePlayer.inventory.currentItem = heldItem;
        }
    }


    public void onEnable() { 
    	if (mc.thePlayer == null || mc.theWorld == null) return;
        if (mc.thePlayer != null) {
            boolean onCubecraft = onServer("cubecraft");
            if (forceitem.getValue() || onCubecraft) {
                heldItem = mc.thePlayer.inventory.currentItem;
            }
            lastPlace = 0;
            sneakthing = false;
            C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.STOP_SNEAKING);
            mc.thePlayer.sendQueue.addToSendQueue(p);
        }

        boolean onCubecraft = onServer("cubecraft");
        if(onCubecraft) {
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Scaffold likely bans on cubecraft - advised to not use"));
        }
    }

    @Collect
    public void onPlayerJump(PlayerJumpEvent playerJumpEvent) {
    	if (mc.thePlayer == null || mc.theWorld == null) return;
        boolean onCubecraft = onServer("cubecraft");
        boolean onHypixel = onServer("hypixel");
        boolean onMCC = onServer("mc-central");
        if (mc.thePlayer.posY >= 254) {
            playerJumpEvent.setCancelled(true);
        }
/*        if (onServer("hypixel") && counter > 3) {
        	playerJumpEvent.setCancelled(true); 
        }*/
    } 
    @Collect
    public void onMotionUpdate(PlayerMoveEvent e) {
 
    }
    
    @Collect
    public void onSafeWalk(EventSafeWalk e) {
    	e.setCancelled(true); /*|| (Sensation.instance.cheatManager.isModuleEnabled("Scaffold") && !Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) && BlockUtils.getBlockAtPos(BlockUtils.getBlockPosUnderPlayer()) instanceof BlockAir*/;;
    }
    
    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent event) {
    	if (mc.thePlayer == null || mc.theWorld == null) return;
        boolean iris = true;
        boolean onCubecraft = iris || onServer("cubecraft");
        boolean onHypixel = mc.isSingleplayer() || onServer("hypixel");
        boolean onHive = onServer("hive"); 
		boolean onMCC = onServer("mc-central");
		if (mc.thePlayer.movementInput.jump && tower.getValue()) {
			if (!mc.thePlayer.isMoving()) {
 
			} else {
				double baseSpeed = 0.2;
				if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
					int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
					baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
				}
				mc.thePlayer.setSpeed(baseSpeed);
                if (mc.thePlayer.onGround) mc.thePlayer.motionY = 0.42f;
                else if (mc.thePlayer.motionY < 0.1D) mc.thePlayer.motionY = -0.3f;
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.42f;
                } else if (mc.thePlayer.motionY < 0.1D) mc.thePlayer.motionY = -0.3f;
    			towering = true;
			}
		} else {
			counter = 0;
		}
        BlockPos belowP =  new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);//will work on downwards scaffold later!
        BlockData getBlockData = find(new Vec3(0,mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown() ? -1 : 0,0), 0);
        if (getBlockData != null && ((mc.thePlayer.isMoving()) || mc.theWorld.getBlockState(belowP).getBlock().getMaterial().isReplaceable())) {
        	if (event.isPre()) {
        		BlockPos sideBlock = getBlockData.position;
        		setYaw(event,yaw =getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), getBlockData.face)[0] - (reverse.getValue() ? 180 : 0));
        		event.setPitch(pitch =getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), getBlockData.face)[1] + 12);
        	} else {
        		int slot = mc.thePlayer.inventory.currentItem;
        		
        		delay = delayedplacement.getValue() ? prop_delay.getValue().intValue() : 0;
        		if (delay > prop_delay.getValue().intValue() || delay < 75) {
        			delay = sneakthing && onMCC ? 75 : delayedplacement.getValue() ? 75 : 0;
        		}
        		boolean canPlace = onCubecraft ? Sensation.instance.cheatManager.isModuleEnabled("Speed") || !mc.thePlayer.isMoving() || Math.abs(event.getYaw() - event.getLastYaw()) < 1 && (System.currentTimeMillis() - lastPlace >= delay) : (System.currentTimeMillis() - lastPlace >= delay) || mc.thePlayer.swingProgressInt == 0;
        		if (getBlockSlot() != -99 && canPlace && (timer.hasPassed(prop_delay.getValue().floatValue() * 2) || !delayedplacement.getValue())){
        			mc.thePlayer.inventory.currentItem = getBlockSlot();
        			double random = MathUtils.getRandomInRange(-.14,.31);
        			double hitvecx = (getBlockData.position.getX() + height) + random + (getBlockData.face.getFrontOffsetX() / 2);
        			double hitvecy = (getBlockData.position.getY() + height) + random + (getBlockData.face.getFrontOffsetY() / 2);
        			double hitvecz = (getBlockData.position.getZ() + height) + random + (getBlockData.face.getFrontOffsetZ() / 2);
        			Vec3 vec = new Vec3(hitvecx , hitvecy , hitvecz );
        		
        			mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,mc.thePlayer.inventory.getCurrentItem(), getBlockData.position, getBlockData.face,vec);
        					lastFace = getBlockData.face;
        			delay+= 15;
        			if (!onServer("cubecraft")) {
            			mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
        			}
        			if (mc.thePlayer.movementInput.jump && tower.getValue()) {
        				if (!mc.thePlayer.isMoving()) {
        					if (mc.thePlayer.isPotionActive(Potion.jump)) {
        						if (mc.thePlayer.onGround) {
        							mc.thePlayer.jump();
        						}
        					} else {

        	        			if (onServer("cubecraft") && mc.thePlayer.onGround && mc.thePlayer.ticksExisted % 3 == 0) {
        	        				mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + .25, mc.thePlayer.posZ);
        	        			}
        						mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 8 == 0 ? -.42f : .42f;
        					}
        				}
        			}
        			if (onHypixel) {
    					MovementInputFromOptions mov = new MovementInputFromOptions(mc.gameSettings);
    					mov.value = 1;
        				if (mc.gameSettings.keyBindSneak.isPressed()	) { 
        					if (!sneakthing) {
            					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.STOP_SNEAKING);
            					mc.thePlayer.sendQueue.addToSendQueue(p);
            					sneakthing = true;
        					}
        				} else {
        					sneakthing = false;
        				}
        			}
        			if (!forceitem.getValue()) {
        				mc.thePlayer.inventory.currentItem = slot;
        			}
        			lastBDat = getBlockData;
        			lastBDat.face = lastFace;
        			yaw = getRotationToBlock(lastBDat.position, lastFace)[0];
        			lastPlace = System.currentTimeMillis();
        			timer.reset();
        		} if (lastBDat != null){
        			BlockPos sideBlock = lastBDat.position;
        			if (mc.thePlayer.ticksExisted % 2 != 0 || !iris) {
        				setYaw(event, yaw = getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), lastBDat.face)[0] - (reverse.getValue() ? 180 : 0));
        				event.setPitch(getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), lastBDat.face)[1] + 13);
        			}  else {
        				setYaw(event, yaw);
        				event.setPitch(pitch);
        			}
        		}
        	}
        }  else if (lastBDat != null){
        	BlockPos sideBlock = lastBDat.position;
        	if (mc.thePlayer.ticksExisted % 2 != 0 || !iris) {
        		setYaw(event,yaw = getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), lastBDat.face)[0] - (reverse.getValue() ? 180 : 0));
        		event.setPitch(getBlockRotations(sideBlock.getX(), sideBlock.getY(), sideBlock.getZ(), lastBDat.face)[1] + 13);
        	}  else {
        		setYaw(event,yaw);
        		event.setPitch(pitch);
        	}     
        }  else {
        	setYaw(event, yaw);
        	event.setPitch(pitch);
        } 
    }
    
    public boolean placeBlock(BlockPos blockPos, EnumFacing facing, int slot, boolean swing) {
        if (delayTimer.hasPassed(prop_delay.getValue().floatValue())) {
            delayTimer.reset();
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slot);
            Vector.Vec3f hitVec = (new Vector.Vec3f(blockPos)).add(0.5f, 0.5f, 0.5f).add((new Vector.Vec3f(facing.getDirectionVec())).scale(0.5F));
            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, stack, blockPos, facing, new Vec3(hitVec.x, hitVec.y, hitVec.z))) {
                if (swing)
                    mc.thePlayer.swingItem();
                else
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                return true;
            }
        }
        return false;
    }
    
    public void setYaw(PlayerUpdateEvent e, float yaw) { 
    	if (onServer("hypixel")) return;
    		e.setYaw(yaw);
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
    
    @Collect
    public void onpacketsend(SendPacketEvent e) {

        boolean onMCC = onServer("mc-central");
        if (e.getPacket() instanceof C08PacketPlayerBlockPlacement && onMCC) {
            C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement)e.getPacket();
            if (Math.abs(packet.getPlacedBlockDirection() - facing) != 0 && !sneakthing) {
                this.sneakthing = true;
                mc.thePlayer.setSneaking(mc.thePlayer.movementInput.sneak = true);
                mc.thePlayer.setSprinting(false);
            } else {
            }
            facing = packet.getPlacedBlockDirection();
        }
        boolean onCubecraft = onServer("cubecraft");
        if (onCubecraft && e.getPacket() instanceof C0APacketAnimation) {
            e.setCancelled(true);
        }
        
    }

    private float[] getBlockRotations(int x, int y, int z, EnumFacing facing)
    {
        Entity temp = new EntitySnowball(mc.theWorld);
        temp.posX = (x + 0.5);
        temp.posY = (y + (height = MathUtils.getRandomInRange(.4999, .5111)));
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
        double xDiff = pos.getX() + (height = random) - mc.thePlayer.posX + face.getDirectionVec().getX() / (facing = ranface);
        double zDiff = pos.getZ() + (height = random) - mc.thePlayer.posZ + face.getDirectionVec().getZ() / (facing = ranface);
        double yDiff = pos.getY() - mc.thePlayer.posY - 1;
        double distance = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) -Math.toDegrees(Math.atan2(xDiff, zDiff));
        float pitch = (float) -Math.toDegrees(Math.atan(yDiff / distance));

        return new float[] {Math.abs(yaw - mc.thePlayer.rotationYaw) < .1 ? mc.thePlayer.rotationYaw : yaw, Math.abs(pitch - mc.thePlayer.rotationPitch) < .1 ? mc.thePlayer.rotationPitch : pitch};
    }

    IBlockState blockState(BlockPos pos) {
        //MCP
        return mc.theWorld.getBlockState(pos);
    }

    Block getBlock(BlockPos pos) {
        //MCP
        return blockState(pos).getBlock();
    }

    Material getMaterial(BlockPos pos) {
        //MCP
        return getBlock(pos).getMaterial();
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

    int getBlockSlot() {
        for (int i = 36; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock
                    && !retardBlock.contains(((ItemBlock) mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).getBlock()) ) {
                return i - 36;
            }
        }
        return -99;
    }
}