package me.gishreload.yukon.hacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.BlockData;
import me.gishreload.yukon.utils.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBlockData;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketMultiBlockChange.BlockUpdateData;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class Scaffold extends Module{
	public Scaffold() {
		super("Scaffold", 0, Category.PLAYER);
	}
	
	@Override
	public void onDisable(){
		if(Meanings.SafeWalk2){
			Meanings.SafeWalk = false;
		}
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eScaffold \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
        }

	@Override
	public void onEnable(){
		if(Meanings.SafeWalk2){
			Meanings.SafeWalk = true;
		}
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eScaffold \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
        }
	public BlockData blockData = null;
    public BlockData blockData1 = null;
    public BlockData blockData2 = null;
    public BlockData blockData3 = null;
    public BlockData blockData4 = null;
    public BlockData blockData5 = null;
 
    private List<Block> blacklist = Arrays
            .asList(new Block[] { Blocks.AIR, Blocks.WATER, Blocks.FLOWING_WATER, Blocks.LAVA, Blocks.FLOWING_LAVA });
 
    public BlockData getBlockData(BlockPos pos) {
        if (!this.blacklist.contains(mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.blacklist.contains(mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.blacklist.contains(mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.blacklist.contains(mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.blacklist.contains(mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }
 
    public static ArrayList<Float> rotationY = new ArrayList<Float>();
    public static ArrayList<Float> rotationP = new ArrayList<Float>();
    public static ArrayList<Float> rotationY2 = new ArrayList<Float>();
    public static ArrayList<Float> rotationP2 = new ArrayList<Float>();
    public static ArrayList<Float> rotationY3 = new ArrayList<Float>();
    public static ArrayList<Float> rotationP3 = new ArrayList<Float>();
    public static ArrayList<Float> rotationY4 = new ArrayList<Float>();
    public static ArrayList<Float> rotationP4 = new ArrayList<Float>();
    public static ArrayList<Float> rotationY5 = new ArrayList<Float>();
    public static ArrayList<Float> rotationP5 = new ArrayList<Float>();
	
	@Override
	public void onUpdate() {
		if(this.isToggled()){
			if (Wrapper.getPlayer().getHeldItem(EnumHand.MAIN_HAND) == null) {
	            return;
	        }
	        if (Wrapper.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
	            BlockPos blocka = new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 1, Wrapper.getPlayer().posZ);
	            BlockPos blockna = new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 2, Wrapper.getPlayer().posZ);
	            if (Wrapper.getPlayer().onGround) {
	                if (!rotationP5.isEmpty()) {
	                    Wrapper.sendPackets(new CPacketPlayer.Rotation(rotationY5.get(0), rotationP5.get(0), true));
	                    rotationY5.clear();
	                    rotationP5.clear();
	                }
	                if (Wrapper.getPlayer().getHorizontalFacing() == EnumFacing.SOUTH) {
	                    BlockPos block = new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 1,
	                            Wrapper.getPlayer().posZ - 0.265);
	                    if (mc.theWorld.getBlockState(block).getBlock() == Blocks.AIR) {
	                        try {
	                            if (rotationY.isEmpty()) {
	                                rotationY.add((float) 0);
	                            }
	                            if (rotationP.isEmpty()) {
	                                rotationP.add((float) 0);
	                            }
	                            this.blockData1 = getBlockData(block);
	                            Wrapper.sendPackets(new CPacketPlayer.Rotation(180, 82, true));
	                            Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
	                           mc.playerController.processRightClickBlock(Wrapper.getPlayer(),
	                                    mc.theWorld, Wrapper.getPlayer().getHeldItem(EnumHand.MAIN_HAND),
	                                    this.blockData1.position,
	                                    this.blockData1.face, new Vec3d(this.blockData1.position.getX(),
	                                            this.blockData1.position.getY(), this.blockData1.position.getZ()),
	                                    EnumHand.MAIN_HAND);
	                        } catch (Exception e) {
	                        	Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a75Edictum\u00a7l\u00a75]\u00a7f Так нельзя. Только вперёд, назад, влево и вправо.");
	                        }
	                    } else {
	                        if (!rotationP2.isEmpty()) {
	                            Wrapper.sendPackets(new CPacketPlayer.Rotation(rotationY2.get(0), rotationP2.get(0), true));
	                            rotationY2.clear();
	                            rotationP2.clear();
	                        }
	                    }
	                }
	                if (Wrapper.getPlayer().getHorizontalFacing() == EnumFacing.NORTH) {
	                    BlockPos block = new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 1,
	                            Wrapper.getPlayer().posZ + 0.265);
	                    if (mc.theWorld.getBlockState(block).getBlock() == Blocks.AIR) {
	                        try {
	                            if (rotationY2.isEmpty()) {
	                                rotationY2.add((float) -180);
	                            }
	                            if (rotationP2.isEmpty()) {
	                                rotationP2.add((float) 0);
	                            }
	                            this.blockData2 = getBlockData(block);
	                            Wrapper.sendPackets(new CPacketPlayer.Rotation(0, 82, true));
	                            Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
	                            mc.playerController.processRightClickBlock(Wrapper.getPlayer(),
	                            		mc.theWorld, Wrapper.getPlayer().getHeldItem(EnumHand.MAIN_HAND),
	                                    this.blockData2.position,
	                                    this.blockData2.face, new Vec3d(this.blockData2.position.getX(),
	                                            this.blockData2.position.getY(), this.blockData2.position.getZ()),
	                                    EnumHand.MAIN_HAND);
	                        } catch (Exception e) {
	                        	Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a75Edictum\u00a7l\u00a75]\u00a7f Так нельзя. Только вперёд, назад, влево и вправо.");
	                        }
	                    } else {
	                        if (!rotationP.isEmpty()) {
	                            Wrapper.sendPackets(new CPacketPlayer.Rotation(rotationY.get(0), rotationP.get(0), true));
	                            rotationY.clear();
	                            rotationP.clear();
	                        }
	                    }
	                }
	                if (Wrapper.getPlayer().getHorizontalFacing() == EnumFacing.EAST) {
	                    BlockPos block = new BlockPos(Wrapper.getPlayer().posX - 0.265, Wrapper.getPlayer().posY - 1,
	                            Wrapper.getPlayer().posZ);
	                    if (mc.theWorld.getBlockState(block).getBlock() == Blocks.AIR) {
	                        try {
	                            if (rotationY3.isEmpty()) {
	                                rotationY3.add((float) -90);
	                            }
	                            if (rotationP3.isEmpty()) {
	                                rotationP3.add((float) 0);
	                            }
	                            this.blockData3 = getBlockData(block);
	                            Wrapper.sendPackets(new CPacketPlayer.Rotation(90, 82, true));
	                            Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
	                            mc.playerController.processRightClickBlock(Wrapper.getPlayer(),
	                            		mc.theWorld, Wrapper.getPlayer().getHeldItem(EnumHand.MAIN_HAND),
	                                    this.blockData3.position,
	                                    this.blockData3.face, new Vec3d(this.blockData3.position.getX(),
	                                            this.blockData3.position.getY(), this.blockData3.position.getZ()),
	                                    EnumHand.MAIN_HAND);
	                        } catch (Exception e) {
	                        	Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a75Edictum\u00a7l\u00a75]\u00a7f Так нельзя. Только вперёд, назад, влево и вправо.");
	                        }
	                    } else {
	                        if (!rotationP4.isEmpty()) {
	                            Wrapper.sendPackets(new CPacketPlayer.Rotation(rotationY4.get(0), rotationP4.get(0), true));
	                            rotationY4.clear();
	                            rotationP4.clear();
	                        }
	                    }
	                }
	                if (Wrapper.getPlayer().getHorizontalFacing() == EnumFacing.WEST) {
	                    BlockPos block = new BlockPos(Wrapper.getPlayer().posX + 0.265, Wrapper.getPlayer().posY - 1,
	                            Wrapper.getPlayer().posZ);
	                    if (mc.theWorld.getBlockState(block).getBlock() == Blocks.AIR) {
	                        try {
	                            if (rotationY4.isEmpty()) {
	                                rotationY4.add((float) 90);
	                            }
	                            if (rotationP4.isEmpty()) {
	                                rotationP4.add((float) 0);
	                            }
	                            this.blockData4 = getBlockData(block);
	                            Wrapper.sendPackets(new CPacketPlayer.Rotation(-90, 82, true));
	                            Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
	                            mc.playerController.processRightClickBlock(Wrapper.getPlayer(),
	                            		mc.theWorld, Wrapper.getPlayer().getHeldItem(EnumHand.MAIN_HAND),
	                                    this.blockData4.position,
	                                    this.blockData4.face, new Vec3d(this.blockData4.position.getX(),
	                                            this.blockData4.position.getY(), this.blockData4.position.getZ()),
	                                    EnumHand.MAIN_HAND);
	                        } catch (Exception e) {
	                            Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a75Edictum\u00a7l\u00a75]\u00a7f Так нельзя. Только вперёд, назад, влево и вправо.");
	                        }
	                    } else {
	                        if (!rotationP3.isEmpty()) {
	                            Wrapper.sendPackets(new CPacketPlayer.Rotation(rotationY3.get(0), rotationP3.get(0), true));
	                            rotationY3.clear();
	                            rotationP3.clear();
	                        }
	                    }
	                }
	            }
	        }
	        super.onUpdate();
	    }
	}
}



