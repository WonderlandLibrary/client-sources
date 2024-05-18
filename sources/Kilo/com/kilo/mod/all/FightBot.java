package com.kilo.mod.all;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.kilo.manager.HackFriendManager;
import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.ModuleSubOption;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.CombatUtil;
import com.kilo.render.Render;
import com.kilo.util.Util;

public class FightBot extends Module {

	private net.minecraft.pathfinding.PathFinder pathFinder;
	public List<Float[]> points = new CopyOnWriteArrayList<Float[]>();

	private float[] oldRotation = new float[] {0, 0};
	private float[] oldRotationTarget = new float[] {0, 0};
	private float[] angleToTarget = new float[2];
	private com.kilo.util.Timer timer = new com.kilo.util.Timer();
	
	private EntityLivingBase target;
	
	public FightBot(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Radius", "Fightbot target radius", Interactable.TYPE.SLIDER, 50, new float[] {1, 50}, true);
		addOption("Range", "Attack range", Interactable.TYPE.SLIDER, 4, new float[] {1, 6}, true);
		addOption("FOV", "Attack FOV", Interactable.TYPE.SLIDER, 180, new float[] {20, 180}, false);
		addOption("Speed", "Attack speed", Interactable.TYPE.SLIDER, 1, new float[] {1, 10}, true);
		addOption("Exist Time", "Time entity has to exist before targetting", Interactable.TYPE.SLIDER, 0.5f, new float[] {0, 10}, true);

		addOption("Animals", "Attack animals", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Monsters", "Attack monsters", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Players", "Attack other players", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Others", "Attack all other entities", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Invisibles", "Ignore invisible entities", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Walls", "Hit through walls", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Block", "Block your sword near entities", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Silent", "Remove kill aura aimbot", Interactable.TYPE.CHECKBOX, true, null, false);
		
		List<ModuleSubOption> target = new ArrayList<ModuleSubOption>();
		target.add(new ModuleSubOption("Range", "Maximum target range", Interactable.TYPE.SLIDER, 20, new float[] {1, 50}, true));
		
		addOption("Target", "Target only one player|Will switch targets when current is out of range", Interactable.TYPE.CHECKBOX, true, null, false, target);
		addOption("ESP", "Show targetted entity", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getColorOptions());
		addOption("Path", "Show current movement path", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getColorOptions());
		addOption("Teams", "Ignore all team members for targetting", Interactable.TYPE.CHECKBOX, true, null, false);
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
		target = null;
		super.onDisable();
	}
	
	public void onPlayerPreUpdate() {
		oldRotation[0] = mc.thePlayer.rotationYaw;
		oldRotation[1] = mc.thePlayer.rotationPitch;
		
		EntityLivingBase entity = getEntityToAttack();

		if (entity == null) {
			return;
		}
		
		float[] rotations = CombatUtil.getRotationToEntity(entity);
		
		mc.thePlayer.rotationYaw = rotations[0];
		mc.thePlayer.rotationPitch = rotations[1];
	}
	
	public void onPlayerMovePreUpdate() {
		if (target == null || mc.thePlayer.getDistanceToEntity(target) > Util.makeFloat(getSubOptionValue("target", "range"))) {
			target = getEntityToTarget();
		} else {
			if (target.deathTime > 0 || CombatUtil.isDisabledEntity((EntityLivingBase)target, Util.makeFloat(getSubOptionValue("target", "range"))) || (!(target.ticksExisted > Util.makeFloat(getOptionValue("exist time"))*20)) || target.isDead || !target.isEntityAlive()) {
				target = null;
			}
		}
		
		if (target != null && pathFinder != null) {
			points.clear();
			PathEntity e = null;
			try {
				e = pathFinder.createEntityPathTo(mc.theWorld, mc.thePlayer, target, 50);
			} catch (Exception ex) {}
			
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
		
		oldRotationTarget[0] = mc.thePlayer.rotationYaw;
		oldRotationTarget[1] = mc.thePlayer.rotationPitch;
		
		mc.thePlayer.rotationYaw = angleToTarget[0];
		mc.thePlayer.rotationPitch = angleToTarget[1];
	}
	
	public void onPlayerPostMotion() {
		if (target == null) { return; }
		
		mc.thePlayer.rotationYaw = oldRotationTarget[0];
		mc.thePlayer.rotationPitch = oldRotationTarget[1];
	}
	
	public void onPlayerUpdate() {
		EntityLivingBase entity = getEntityToAttack();
		
		if (entity != null && timer.isTime((10-Util.makeFloat(getOptionValue("speed")))/10f)) {
			mc.thePlayer.swingItem();
			mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
			mc.playerController.syncCurrentPlayItem();
		}
	}
	
	public void onPlayerPostUpdate() {
		if (Util.makeBoolean(getOptionValue("block"))) {
			if (getEntityToAttack() != null) { 
				if (mc.thePlayer.getCurrentEquippedItem() != null) {
					if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
						mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
					}
				}
			}
		}
		
		if (!Util.makeBoolean(getOptionValue("silent"))) {
			return;
		}

		mc.thePlayer.rotationYaw = oldRotation[0];
		mc.thePlayer.rotationPitch = oldRotation[1];
	}

	public void render3D() {
		if (!Util.makeBoolean(getOptionValue("esp"))) { return; }

		if (Util.makeBoolean(getOptionValue("path"))) {
			int r = Util.makeInteger(getSubOptionValue("path", "red"));
			int g = Util.makeInteger(getSubOptionValue("path", "green"));
			int b = Util.makeInteger(getSubOptionValue("path", "blue"));
			int a = Util.makeInteger(getSubOptionValue("path", "opacity"));
			
			for(Float[] p : points) {
				float s = 0.4f;
				Render.bbox(new Vec3(p[0]+s, p[1]+s, p[2]+s), new Vec3(p[0]-s, p[1]-s, p[2]-s), new Color(r, g, b, (a/2)+128).getRGB(), new Color(r, g, b, a/2).getRGB());
			}
		}

		int r = Util.makeInteger(getSubOptionValue("esp", "red"));
		int g = Util.makeInteger(getSubOptionValue("esp", "green"));
		int b = Util.makeInteger(getSubOptionValue("esp", "blue"));
		int a = Util.makeInteger(getSubOptionValue("esp", "opacity"));
		
		EntityLivingBase entity = getEntityToAttack();
		if (entity != null) {
			Render.boxFilled(entity, new Color(r, g, b, a/2).getRGB(), new Color(r, g, b, (a/2)+128).getRGB());
		}
		
		if (target != null) {
			Render.boxFilled(target, new Color(r, g, b, a/2).getRGB(), new Color(r, g, b, (a/2)+128).getRGB());
		}
	}
	
	private EntityLivingBase getEntityToAttack() {
		float fov = Util.makeFloat(getOptionValue("fov"));
		float range = Util.makeFloat(getOptionValue("range"));
		float speed = Util.makeFloat(getOptionValue("speed"));
		float exist = Util.makeFloat(getOptionValue("exist time"));
		
		boolean animals = Util.makeBoolean(getOptionValue("animals"));
		boolean monsters = Util.makeBoolean(getOptionValue("monsters"));
		boolean players = Util.makeBoolean(getOptionValue("players"));
		boolean others = Util.makeBoolean(getOptionValue("others"));
		boolean invis = Util.makeBoolean(getOptionValue("invisibles"));
		boolean wall = Util.makeBoolean(getOptionValue("walls"));
		boolean teams = Util.makeBoolean(getOptionValue("teams"));
		
		return CombatUtil.getEntityNearestForAttack(range, fov, exist, animals, monsters, players, others, invis, wall, teams);
	}
	
	private EntityLivingBase getEntityToTarget() {
		float rad = Util.makeFloat(getOptionValue("radius"));
		float fov = Util.makeFloat(getOptionValue("fov"));
		float speed = Util.makeFloat(getOptionValue("speed"));
		float exist = Util.makeFloat(getOptionValue("exist time"));
		
		boolean animals = Util.makeBoolean(getOptionValue("animals"));
		boolean monsters = Util.makeBoolean(getOptionValue("monsters"));
		boolean players = Util.makeBoolean(getOptionValue("players"));
		boolean target = Util.makeBoolean(getOptionValue("target"));
		boolean wall = Util.makeBoolean(getOptionValue("walls"));
		boolean teams = Util.makeBoolean(getOptionValue("teams"));
		
		return CombatUtil.getEntityNearest(rad, fov, exist, target, animals, monsters, players, wall, teams);
	}
}
