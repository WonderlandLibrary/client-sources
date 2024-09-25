package none.module.modules.world;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event3D;
import none.event.events.EventMove;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.MoveUtils;
import none.utils.PlayerUtil;
import none.utils.RenderingUtil;
import none.valuesystem.ModeValue;

public class Teleport extends Module{
	
	public Teleport() {
		super("Teleport", "Teleport", Category.WORLD, Keyboard.KEY_T);
	}
	public static final String[] mode = {"Basic", "AAC350"};
	public static ModeValue modes = new ModeValue("Mode", "Basic", mode);
	
	private boolean canTP, zitter = false, doTeleport = false, freeze = false, hadGround = false, disableLogger = false;
	private int delay, flyTimer = 0, freezeTimer = 0;
    public BlockPos endPos = null;
    public double[] pos = new double[] {0,0,0};
    
    private List<Packet> packets = new ArrayList<>();
    
	public MovingObjectPosition getBlinkBlock() {
        Vec3 var4 = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
        Vec3 var5 = mc.thePlayer.getLook(mc.timer.renderPartialTicks);
        Vec3 var6 = var4.addVector(var5.xCoord * 70, var5.yCoord * 70, var5.zCoord * 70);
        return mc.theWorld.rayTraceBlocks(var4, var6, false, false, true);
    }
	
	@Override
	protected void onEnable() {
		super.onEnable();
		endPos = null;
		packets.clear();
		delay = 5;
		if (mc.thePlayer == null) return;
		final double[] pos = {mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ};
		this.pos = pos;
		if (modes.getSelected().equalsIgnoreCase("AAC350")) {
			evc(ChatFormatting.GREEN + "Teleport Made by Liquidbounce Client.");
		}
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		delay = -1;
		endPos = null;
		mc.timer.timerSpeed = 1F;
		hadGround = false;
        freeze = false;
        zitter = false;
        disableLogger = false;
        flyTimer = 0;
	}

	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, EventPacket.class, Event3D.class, EventMove.class, EventTick.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if ((!mc.thePlayer.isEntityAlive() || (mc.currentScreen != null && mc.currentScreen instanceof GuiGameOver)) && !modes.getSelected().equalsIgnoreCase("Test")) {
			Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), " disabled by death.", 3));
			evc(getName() + " disabled by death.");
			this.toggle();
			return;
		}
		if(mc.thePlayer.ticksExisted <= 1 && !modes.getSelected().equalsIgnoreCase("Test")){
			Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), " disabled by respawn.", 3));
			evc(getName() + " disabled by respawn.");
			this.toggle();
			return;
		}
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + modes.getSelected() + ":" + packets.size());
		
		if (event instanceof EventMove) {
			EventMove move = (EventMove) event;
			if (modes.getSelected().equalsIgnoreCase("AAC350") && freeze) {
				move.setX(0);
				move.setY(0);
				move.setZ(0);
				move.setCancelled(true);
			}
		}
		
		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet packet = ep.getPacket();
			if (disableLogger) return;
			
			if (packet instanceof C03PacketPlayer) {
				final C03PacketPlayer packetPlayer = (C03PacketPlayer) packet;
				if (modes.getSelected().equalsIgnoreCase("AAC350")) {
		            if(flyTimer <= 40)
		                return;
		
		            ep.setCancelled(true);
		
		            if(!(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook))
		                return;
		
		            packets.add(packet);
				}
	        }
//			else if (ep.isIncoming() && packet instanceof S08PacketPlayerPosLook) {
//				if(flyTimer <= 40)
//	                return;
//	        	ep.setCancelled(true);
//	        }
		}
		
		if (event instanceof EventTick) {
			if (modes.getSelected().equalsIgnoreCase("AAC350")) {
				freezeTimer++;
				if(freeze && freezeTimer >=40) {
					freezeTimer = 0;
					freeze = false;
					setState(false);
				}
				
				if(flyTimer <= 40) {
					flyTimer++;
					if(mc.thePlayer.onGround) {
						mc.thePlayer.jump();
					}else{
						MoveUtils.forward(zitter ? -0.21D : 0.21D);
						zitter = !zitter;
					}
					hadGround = false;
					return;
				}
				
				if(mc.thePlayer.onGround)
					hadGround = true;
				
				if(!hadGround)
					return;
				
				if(mc.thePlayer.onGround)
					mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + 0.2D, mc.thePlayer.posZ);
				
				final float vanillaSpeed = 2F;
				mc.thePlayer.capabilities.isFlying = false;
				mc.thePlayer.motionY = 0;
				mc.thePlayer.motionX = 0;
				mc.thePlayer.motionZ = 0;
				if(mc.gameSettings.keyBindJump.isKeyDown())
					mc.thePlayer.motionY += vanillaSpeed;
				if(mc.gameSettings.keyBindSneak.isKeyDown())
					mc.thePlayer.motionY -= vanillaSpeed;
				MoveUtils.setMotion(vanillaSpeed);
				
				if(Mouse.isButtonDown(1) && !doTeleport) {
					mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY - 11, mc.thePlayer.posZ);
					
					disableLogger = true;
					packets.forEach(packet -> mc.getConnection().getNetworkManager().sendPacketNoEvent(packet));
					
					freezeTimer = 0;
					freeze = true;
				}
				
				doTeleport = Mouse.isButtonDown(1);
				return;
			}
		}
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate) event;
			
			if (em.isPre()) {
				if (modes.getSelected().equalsIgnoreCase("AAC350")) {
					
				}
				if(modes.getSelected().equalsIgnoreCase("Basic")){
	                if (canTP && Mouse.isButtonDown(1) && !mc.thePlayer.isSneaking() && delay <= 0 && mc.inGameHasFocus && getBlinkBlock().entityHit == null && !(getBlock(getBlinkBlock().getBlockPos()) instanceof BlockChest)) {
	                	endPos = getBlinkBlock().getBlockPos().add(0,1,0);
	                    delay = 5;
	                }
	                if (delay == 0) {
	                	event.setCancelled(true);
	                	final double[] startPos = {mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ};
	                	PlayerUtil.teleport(startPos, endPos);                
	                	event.setCancelled(false);
	                }
            	}
			}
		}
		
		if (event instanceof Event3D) {
			Event3D er = (Event3D) event;
            try {
                final int x = getBlinkBlock().getBlockPos().getX();
                final int y = getBlinkBlock().getBlockPos().getY();
                final int z = getBlinkBlock().getBlockPos().getZ();
                final Block block1 = getBlock(x, y, z);
                final Block block2 = getBlock(x, y + 1, z);
                final Block block3 = getBlock(x, y + 2, z);
                final boolean blockBelow = !(block1 instanceof BlockSign) && block1.getMaterial().isSolid();
                final boolean blockLevel = !(block2 instanceof BlockSign) && block1.getMaterial().isSolid();
                final boolean blockAbove = !(block3 instanceof BlockSign) && block1.getMaterial().isSolid();
                if (getBlock(getBlinkBlock().getBlockPos()).getMaterial() != Material.AIR && blockBelow && blockLevel && blockAbove && !(getBlock(getBlinkBlock().getBlockPos()) instanceof BlockChest)) {
                    canTP = true;
                    GL11.glPushMatrix();
                    RenderingUtil.pre3D();
                    mc.entityRenderer.setupCameraTransform(er.getPartialTicks(), 2);
                   
                    GL11.glColor4d(0, 0.6, 0, 0.25);
                    RenderingUtil.drawBoundingBox(new AxisAlignedBB(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ, x - RenderManager.renderPosX + 1.0, y + getBlock(getBlinkBlock().getBlockPos()).getBlockBoundsMaxY() - RenderManager.renderPosY, z - RenderManager.renderPosZ + 1.0));
                    GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                    RenderingUtil.post3D();
                    GL11.glPopMatrix();
                } else {
                    canTP = false;
                }
            } catch (Exception e) {

            }
		}
	}
	
	public static Block getBlock(final int x, final int y, final int z) {
		Minecraft mc = Minecraft.getMinecraft();
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(final BlockPos pos) {
    	Minecraft mc = Minecraft.getMinecraft();
        return mc.theWorld.getBlockState(pos).getBlock();
    }
    
    public void HClip(double range, float yaw) {
    	float var1 = yaw * 0.017453292F;
        mc.thePlayer.setPosition(mc.thePlayer.posX -  MathHelper.sin(var1)*range,
        		mc.thePlayer.posY ,
        		mc.thePlayer.posZ + MathHelper.cos(var1)*range);
    }
}
