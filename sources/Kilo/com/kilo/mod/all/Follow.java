package com.kilo.mod.all;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.toolbar.dropdown.TextBoxRel;
import com.kilo.mod.util.FollowHandler;
import com.kilo.util.Util;

public class Follow extends Module {

	private net.minecraft.pathfinding.PathFinder pathFinder;
	public List<Float[]> points = new CopyOnWriteArrayList<Float[]>();

	private float[] oldRotation = new float[] {0, 0};
	private float[] angleToTarget = new float[2];
	
	private EntityLivingBase target;
	
	public Follow(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Enter name...", "Player to follow", Interactable.TYPE.TEXTBOX, TextBoxRel.FOLLOW, null, false);
	}
	
	public void onEnable() {
		pathFinder = new net.minecraft.pathfinding.PathFinder(new WalkNodeProcessor());
		super.onEnable();
	}
	
	public void onDisable() {
		if (mc.gameSettings.keyBindForward.getKeyCode() < 0) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Mouse.isButtonDown(mc.gameSettings.keyBindForward.getKeyCode()+100));
		} else {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
		}
		super.onDisable();
	}
	
	public void update() {
		if (!active) { return; }
		target = null;
		for(Object o : mc.theWorld.loadedEntityList) {
			Entity e = (Entity)o;
			
			if (!(e instanceof EntityLivingBase)) {
				continue;
			}

			if (e.getCommandSenderName().equalsIgnoreCase(FollowHandler.follow) && mc.thePlayer.getDistanceToEntity(e) > 3) {
				target = (EntityLivingBase) e;
				break;
			}
		}
	}
	
	public void onPlayerMovePreUpdate() {
		if (target != null) {
			if (target.deathTime > 0 || target.isDead || !target.isEntityAlive()) {
				target = null;
			}
		}
		
		if (target != null && pathFinder != null) {
			points.clear();
			PathEntity e = pathFinder.createEntityPathTo(mc.theWorld, mc.thePlayer, target, 50);
			if (e != null) {
				try {
					int offset = (e.getCurrentPathLength()%2 == 0)?1:2;
					for(int i = offset; i < e.getCurrentPathLength(); i++){
						PathPoint pp = e.getPathPointFromIndex(i);
						float x = pp.xCoord+0.5f;
						float y = pp.yCoord+0.5f;
						float z = pp.zCoord+0.5f;
						points.add(new Float[] {x, y, z});
					}
				} catch (Exception b){ 
					b.printStackTrace();
				}
				
				float var3 = mc.thePlayer.onGround?mc.thePlayer.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(mc.thePlayer.posZ))).getBlock().slipperiness * 0.91F:0.91f;
				
				if (points.size() > 0) {
					angleToTarget = Util.getRotationToPos(points.get(0)[0], mc.thePlayer.posY+mc.thePlayer.getEyeHeight(), points.get(0)[2]);
				}
				
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
				
				if (mc.thePlayer.isCollidedHorizontally && !ModuleManager.get("step").active){
					if (mc.thePlayer.onGround){
						mc.thePlayer.jump();
					}
				}
			} else {
				if (mc.gameSettings.keyBindForward.getKeyCode() < 0) {
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Mouse.isButtonDown(mc.gameSettings.keyBindForward.getKeyCode()+100));
				} else {
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
				}
			}
		} else {
			if (mc.gameSettings.keyBindForward.getKeyCode() < 0) {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Mouse.isButtonDown(mc.gameSettings.keyBindForward.getKeyCode()+100));
			} else {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
			}
			points.clear();
		}
	}
	
	public void onPlayerPreMotion() {
		if (target == null) { return; }
		
		oldRotation[0] = mc.thePlayer.rotationYaw;
		oldRotation[1] = mc.thePlayer.rotationPitch;
		
		mc.thePlayer.rotationYaw = angleToTarget[0];
		mc.thePlayer.rotationPitch = angleToTarget[1];
	}
	
	public void onPlayerPostMotion() {
		if (target == null) { return; }
		
		mc.thePlayer.rotationYaw = oldRotation[0];
		mc.thePlayer.rotationPitch = oldRotation[1];
	}
}
