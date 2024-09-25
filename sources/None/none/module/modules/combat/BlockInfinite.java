package none.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3d;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event3D;
import none.event.events.EventMove;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.utils.MoveUtils;
import none.utils.RenderingUtil;
import none.utils.TimeHelper;
import none.utils.Utils;
import none.utils.render.Colors;

public class BlockInfinite extends Module{

	public BlockInfinite() {
		super("BlockInfinite", "BlockInfinite", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	TimeHelper timer = new TimeHelper();
	private List<Vector3d> path = new ArrayList<>();
	private boolean doTeleport = false;
	private List<Packet> packets = new ArrayList<>();
	private List<Packet> packets2 = new ArrayList<>();
	private boolean freeze = false;
	private int freezeTimer = 0;
	private BlockPos endpos = null;
	private Entity target;
	
	@Override
	protected void onEnable() {
		super.onEnable();
		timer.setLastMS();
		path.clear();
		if (mc.thePlayer == null) return;
		packets.clear();
		freezeTimer = 0;
		doTeleport = false;
		freeze = false;
		endpos = null;
		target = null;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		timer.setLastMS();
		path.clear();
	}

	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, Event3D.class, EventPacket.class, EventTick.class, EventMove.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + ":" + packets.size() + ":" + packets2.size());
		
		if ((!mc.thePlayer.isEntityAlive() || (mc.currentScreen != null && mc.currentScreen instanceof GuiGameOver))) {
			packets.clear();
			packets2.clear();
			target = null;
			endpos = null;
			freezeTimer = 0;
			freeze = false;
			return;
		}
		if(mc.thePlayer.ticksExisted <= 1){
			packets.clear();
			packets2.clear();
			target = null;
			endpos = null;
			freezeTimer = 0;
			freeze = false;
			return;
		}
		
		if (event instanceof EventTick) {
			if (!freeze && Mouse.isButtonDown(1)) {
				if (!packets.isEmpty()) {
                	packets.forEach(packet -> mc.getConnection().getNetworkManager().sendPacketNoEvent(packet));
                	packets.clear();
                }
				packets2.clear();
				target = null;
				endpos = null;
				freeze = false;
				freezeTimer = 0;
				evc(ChatFormatting.DARK_RED + "Cancelled");
			}
			
			if (target != null && !freeze) {
				endpos = target.getPosition();
				int range = 3;
				if (Math.floor(mc.thePlayer.posX)  <= Math.floor(endpos.getX() + range) && Math.floor(mc.thePlayer.posX)  >= Math.floor(endpos.getX() - range) && Math.floor(mc.thePlayer.posZ) <= Math.floor(endpos.getZ() + range) && Math.floor(mc.thePlayer.posZ) >= Math.floor(endpos.getZ() - range)) {
					mc.thePlayer.setPositionAndUpdate(endpos.getX(), endpos.getY(), endpos.getZ());
					mc.thePlayer.connection.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(endpos.getX(), endpos.getY() - 11, endpos.getZ(), false));
					freezeTimer = 0;
                    freeze = true;
                    target = null;
                    endpos = null;
                    if (!packets.isEmpty()) {
//                    	mc.thePlayer.swingItem();
                    	packets.forEach(packet -> mc.getConnection().getNetworkManager().sendPacketNoEvent(packet));
                    	packets.clear();
                    }
                    
                    if (!packets2.isEmpty()) {
                    	packets2.forEach(packet -> mc.getConnection().getNetworkManager().sendPacketNoEvent(packet));
                    	packets2.clear();
                    }
				}
				else {
					final float vanillaSpeed = 1F;
					mc.thePlayer.capabilities.isFlying = false;
					mc.thePlayer.motionY = 0;
					mc.thePlayer.motionX = 0;
					mc.thePlayer.motionZ = 0;
					if(mc.gameSettings.keyBindJump.isKeyDown())
						mc.thePlayer.motionY += vanillaSpeed;
					if(mc.gameSettings.keyBindSneak.isKeyDown())
						mc.thePlayer.motionY -= vanillaSpeed;
					MoveUtils.setMotion(vanillaSpeed);
				}
			}else
			if (target == null && endpos != null && !freeze) {
				int range = 3;
				if (Math.floor(mc.thePlayer.posX)  <= Math.floor(endpos.getX() + range) && Math.floor(mc.thePlayer.posX)  >= Math.floor(endpos.getX() - range) && Math.floor(mc.thePlayer.posZ) <= Math.floor(endpos.getZ() + range) && Math.floor(mc.thePlayer.posZ) >= Math.floor(endpos.getZ() - range)) {
					mc.thePlayer.setPositionAndUpdate(endpos.getX(), endpos.getY(), endpos.getZ());
					mc.thePlayer.connection.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(endpos.getX(), endpos.getY() - 11, endpos.getZ(), false));
					freezeTimer = 0;
                    freeze = true;
                    endpos = null;
                    
                    if (!packets.isEmpty()) {
                    	packets.forEach(packet -> mc.getConnection().getNetworkManager().sendPacketNoEvent(packet));
                    	packets.clear();
                    }
                    
                    if (!packets2.isEmpty()) {
                    	packets2.forEach(packet -> mc.getConnection().getNetworkManager().sendPacketNoEvent(packet));
                    	packets2.clear();
                    }
				}
				else {
					final float vanillaSpeed = 1F;
					mc.thePlayer.capabilities.isFlying = false;
					mc.thePlayer.motionY = 0;
					mc.thePlayer.motionX = 0;
					mc.thePlayer.motionZ = 0;
					if(mc.gameSettings.keyBindJump.isKeyDown())
						mc.thePlayer.motionY += vanillaSpeed;
					if(mc.gameSettings.keyBindSneak.isKeyDown())
						mc.thePlayer.motionY -= vanillaSpeed;
					MoveUtils.setMotion(vanillaSpeed);
				}
			}
			freezeTimer++;
			if(freeze && freezeTimer >= 40) {
				freezeTimer = 0;
				freeze = false;
			}			
			return;
		}
		
		if (event instanceof EventMove) {
			EventMove em = (EventMove) event;
//			if ((endpos != null || target != null) && !freeze) {
//				double yaw = Utils.getDirection();
//				BlockPos pos = endpos;
//				double dist = mc.thePlayer.getDistance(pos.getX(), mc.thePlayer.posY, pos.getZ());
////				em.setX(-Math.sin(yaw) * (dist/ 10));
////				em.setZ(Math.cos(yaw) * (dist / 10));
//				em.setX(-Math.sin(yaw) * 0.5);
//				em.setZ(Math.cos(yaw) * 0.5);
//				em.setY(0);
//			}else
			if (freeze) {
				em.setX(0);
				em.setY(0);
				em.setZ(0);
				em.setCancelled(true);
			}
		}
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre()) {
				if (target != null && !freeze) {
					endpos = target.getPosition();
					int range = 3;
					if (Math.floor(mc.thePlayer.posX)  <= Math.floor(endpos.getX() + range) && Math.floor(mc.thePlayer.posX)  >= Math.floor(endpos.getX() - range) && Math.floor(mc.thePlayer.posZ) <= Math.floor(endpos.getZ() + range) && Math.floor(mc.thePlayer.posZ) >= Math.floor(endpos.getZ() - range)) {
						float[] rot = Utils.getNeededRotationsForEntity(target);
//						e.setYaw(rot[0]);
//						e.setPitch(rot[1]);
					}
				}
//				if (endpos != null) {
//					int range = 1;
//					if (Math.floor(mc.thePlayer.posX)  <= Math.floor(endpos.getX() + range) && Math.floor(mc.thePlayer.posX)  >= Math.floor(endpos.getX() - range) && Math.floor(mc.thePlayer.posZ) <= Math.floor(endpos.getZ() + range) && Math.floor(mc.thePlayer.posZ) >= Math.floor(endpos.getZ() - range)) {
//						mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY - 11, mc.thePlayer.posZ);
//						freezeTimer = 0;
//	                    freeze = true;
//	                    endpos = null;
//	                    if (!packets.isEmpty()) {
//	                    	packets.forEach(packet -> mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet));
//	                    }
//	                    
//	                    if (!packets2.isEmpty()) {
//	                    	packets2.forEach(packet -> mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet));
//	                    }
//					}
//				}
			}
		}
		
		if (event instanceof EventPacket) {
			EventPacket ep  =(EventPacket)event;
    		Packet p = ep.getPacket();
    		
    		if(ep.isPre()){
    			if (p instanceof C03PacketPlayer) {
    				if (endpos != null && !freeze) {
    					ep.setCancelled(true);
    					
    					if(!(p instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(p instanceof C03PacketPlayer.C06PacketPlayerPosLook))
    		                return;
    					
    					packets.add(p);
    				}
    			}
//    			if(p instanceof C08PacketPlayerBlockPlacement){
//    				C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement)p;
//    				BlockPos pos = packet.getPosition();
//    				int dir = packet.getPlacedBlockDirection();
//    				if (dir == 255) return; 
//    				ItemStack stack = packet.getStack();
//    				double dist = mc.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ());
//    				if(dist > 0 && pos.getY() != -1 && (stack != null || mc.theWorld.getBlockState(pos).getBlock() instanceof BlockContainer)){
////    					ep.setCancelled(true);
//       		         	Vec3N topFrom = new Vec3N(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
//                        Vec3N to = new Vec3N(pos.getX(), pos.getY(), pos.getZ());
//                        path = PathUtils.findPath(to.getX(), to.getY(), to.getZ(), Math.sqrt(dist));
////                        for (Vector3d pathElm : path) {
////                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), mc.theWorld.getBlockState(new BlockPos(pathElm.getX(), pathElm.getY(), pathElm.getZ()).add(0, -1, 0)).getBlock() instanceof BlockAir));
////                        }
//                        timer.setLastMS();
////                        mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(p);
////                        Collections.reverse(path);
////                        for (Vector3d pathElm : path) {
////                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
////                        }
//    				}
//    			}
    			if (p instanceof C02PacketUseEntity) {
    				C02PacketUseEntity packet = (C02PacketUseEntity) p;
    				net.minecraft.network.play.client.C02PacketUseEntity.Action act = packet.getAction();
    				Entity entity = packet.getEntityFromWorld(mc.theWorld);
    				double dist = mc.thePlayer.getDistance(entity.posX, entity.posY, entity.posZ);
    				if (dist > 0 && act == net.minecraft.network.play.client.C02PacketUseEntity.Action.ATTACK) {
    					ep.setCancelled(true);
    					BlockPos to = new BlockPos(entity.posX, entity.posY, entity.posZ);
//                        if(mc.thePlayer.onGround)
//            				mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + 0.2D, mc.thePlayer.posZ);
//                        endpos = to;
    					target = entity;
//                        path = PathUtils.findPath(to.getX(), to.getY() + 0.2, to.getZ(), 0.1);
//                        path = PlayerUtil.noneTeleport(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), to);
//                        mc.thePlayer.setPositionAndUpdate(to.getX(), to.getY() + 0.2 - 11, to.getZ());
//                        for (Vector3d pathElm : path) {
//                        	mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), !(mc.theWorld.getBlockState(new BlockPos(pathElm.getX(), pathElm.getY(), pathElm.getZ())).getBlock() instanceof BlockAir)));
//                        }
//                        freezeTimer = 0;
//                        freeze = true;
                        
//                        timer.setLastMS();
//                        mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(p);
        				
    					packets2.add(p);
    					
//                        Collections.reverse(path);
//                        for (Vector3d pathElm : path) {
//                            mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
//                        }
	                    evc("Selected Target = " + entity.getName());
	                    evc("Fly to Target");
    				}
    			}
    			
    			if(p instanceof C07PacketPlayerDigging){
        			C07PacketPlayerDigging packet = (C07PacketPlayerDigging)p;
        			Action act = packet.getStatus();
        			BlockPos pos = packet.getPosition();
        			EnumFacing face = packet.getFacing();
    				double dist = mc.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ());
        			if(dist > 0 && act == Action.START_DESTROY_BLOCK){
        				ep.setCancelled(true);
	    					BlockPos to = pos;
//	                      if(mc.thePlayer.onGround)
//	          				mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + 0.2D, mc.thePlayer.posZ);
	                      
//	                      path = PathUtils.findPath(to.getX(), to.getY(), to.getZ(), Math.sqrt(dist));
	                      endpos = new BlockPos(to.getX(), to.getY(), to.getZ());
	//                      path = PlayerUtil.noneTeleport(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), to);
//	                      for (Vector3d pathElm : path) {
//	                    	  mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), !(mc.theWorld.getBlockState(new BlockPos(pathElm.getX(), pathElm.getY(), pathElm.getZ())).getBlock() instanceof BlockAir)));
//	                      }
//	                      boolean start = false;
//	                      for (int i = 0; i < 1000; i++) {
//	                    	  if (Math.floor(mc.thePlayer.posX) == to.getX() && Math.floor(mc.thePlayer.posZ) == to.getZ()) {
//	                    		  start = true;
//	                    		  break;
//	                    	  }else {
//	                    		  double yaw = Utils.getDirection();
//	                    		  mc.thePlayer.motionX = -Math.sin(yaw) * Math.sqrt(dist);
//	                    		  mc.thePlayer.motionZ = Math.cos(yaw) * Math.sqrt(dist);
//	                    	  }
//	                      }
//	                      if (start) {
//	                      mc.thePlayer.setPositionAndUpdate(to.getX(), to.getY() + 0.2 - 11, to.getZ());
//	                      freezeTimer = 0;
//	                      freeze = true;
//	                      timer.setLastMS();
	                      C07PacketPlayerDigging end = new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, to, face);
//	                      mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(p);
//	                      mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(end);
//	                      }
	                      target = null;
	                      packets2.add(p);
	                      packets2.add(end);
//	                      Collections.reverse(path);
//	                      for (Vector3d pathElm : path) {
//	                    	  mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
//	                      }
	                      evc("Selected Block Pos:X = " + endpos.getX() + ", Y = " + endpos.getY() + ", Z = " + endpos.getZ());
	                      evc("Fly to position[" + "X=" + endpos.getX() + ",Y=" + endpos.getY() + ",Z=" + endpos.getZ() + "]");
        			}else if(act == Action.ABORT_DESTROY_BLOCK){
        				ep.setCancelled(true);
        			}
        		}
    		}
		}
		
		if (event instanceof Event3D) {
			if (!path.isEmpty() && !timer.hasTimeReached(1000)) {
	   			 for(Vector3d vec : path){
	   				 drawPath(vec);
	   			 }
            }
		}
	}
	
	public void drawPath(Vector3d vec) {
        double x = vec.getX() - RenderManager.renderPosX;
        double y = vec.getY() - RenderManager.renderPosY;
        double z = vec.getZ() - RenderManager.renderPosZ;
        double width = 0.3;
        double height = mc.thePlayer.getEyeHeight();
        RenderingUtil.pre3D();
        GL11.glLoadIdentity();
        mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
        int colors[] = {Colors.getColor(Color.black), Colors.getColor(Color.white)};
        for (int i = 0; i < 2; i++) {
            RenderingUtil.glColor(colors[i]);
            GL11.glLineWidth(3 - i * 2);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x - width, y, z - width);
            GL11.glVertex3d(x - width, y, z - width);
            GL11.glVertex3d(x - width, y + height, z - width);
            GL11.glVertex3d(x + width, y + height, z - width);
            GL11.glVertex3d(x + width, y, z - width);
            GL11.glVertex3d(x - width, y, z - width);
            GL11.glVertex3d(x - width, y, z + width);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x + width, y, z + width);
            GL11.glVertex3d(x + width, y + height, z + width);
            GL11.glVertex3d(x - width, y + height, z + width);
            GL11.glVertex3d(x - width, y, z + width);
            GL11.glVertex3d(x + width, y, z + width);
            GL11.glVertex3d(x + width, y, z - width);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x + width, y + height, z + width);
            GL11.glVertex3d(x + width, y + height, z - width);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x - width, y + height, z + width);
            GL11.glVertex3d(x - width, y + height, z - width);
            GL11.glEnd();
        }

        RenderingUtil.post3D();
    }
}
