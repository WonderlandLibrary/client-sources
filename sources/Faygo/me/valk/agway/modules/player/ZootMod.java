package me.valk.agway.modules.player;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.entity.EventEntityCollision;
import me.valk.event.events.other.EventPacket;
import me.valk.event.events.other.EventPacket.EventPacketType;
import me.valk.event.events.player.EventMotion;
import me.valk.event.events.player.EventSetAir;
import me.valk.help.world.Location;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.module.annotations.Description;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

@Description(description = "Guardian is a zoot, anti-fire, anti-cactus, anti-down, anti-lava and more rolled into one.")
public class ZootMod extends Module {

	public ZootMod(){
		super(new ModData("Zoot", Keyboard.KEY_NONE, new Color(69,117,145)), ModType.PLAYER);
	}
	
	@EventListener
	public void onBoundingBox(EventEntityCollision event){
		if((event.getBlock() instanceof BlockCactus || event.getBlock() instanceof BlockFire || event.getBlock() instanceof BlockWeb) && event.getEntity() == p && p.posY < event.getLocation().getY() + 1 && !p.isSneaking()){
			event.setBoundingBox(new AxisAlignedBB(
					event.getLocation().getX(),
					event.getLocation().getY(),
					event.getLocation().getZ(),
					event.getLocation().getX() + 1,
                    (event.getBlock() instanceof BlockCactus || event.getBlock() instanceof BlockSoulSand)? event.getBoundingBox().maxY : event.getLocation().getY() + 1,
					event.getLocation().getZ() + 1));
		}
	}
	
	public boolean isOnCactus(Entity entity){
		if(entity == null)
			return false;
		boolean onLiquid = false;
		int y = (int) entity.boundingBox.offset(0.0D, -0.01D, 0.0D).minY;
		for(int x = MathHelper.floor_double(entity.boundingBox.minX); x < MathHelper
				.floor_double(entity.boundingBox.maxX) + 1; x++){
			for(int z = MathHelper.floor_double(entity.boundingBox.minZ); z < MathHelper
					.floor_double(entity.boundingBox.maxZ) + 1; z++){
				Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z))
						.getBlock();
				if((block != null) && (!(block instanceof BlockAir))){
					if(!(block instanceof BlockCactus))
						return false;
					onLiquid = true;
				}
			}
		}
		return onLiquid;
	}
	
	public boolean doSave(){
        return p.isInsideOfMaterial(Material.water) || p.isInsideOfMaterial(Material.lava) || p.isInsideBlock() || isOnCactus(p);

    }

    @EventListener
    public void onSetAir(EventSetAir event){
        boolean hasMoved = p.lastTickPosX != p.posX || p.lastTickPosY != p.posY || p.lastTickPosZ != p.posZ;
        if(doSave() && !hasMoved){
            event.setCancelled(true);
        }
    }

	@EventListener
	public void onPacketSend(EventPacket event){
		if(event.getPacket() instanceof C03PacketPlayer && doSave() && event.getType() == EventPacketType.SEND){
			boolean hasMoved = p.lastTickPosX != p.posX || p.lastTickPosY != p.posY || p.lastTickPosZ != p.posZ;
			
			if(!hasMoved){
				event.setCancelled(true);
			}
		}
	}
	
	@EventListener
	public void onMotion(EventMotion event){
        if(mc.thePlayer.getActivePotionEffect(Potion.confusion) != null) p.removePotionEffect(Potion.confusion.getId());
        if(mc.thePlayer.getActivePotionEffect(Potion.blindness) != null) p.removePotionEffect(Potion.blindness.getId());

		if(event.getType() == EventType.PRE){
			Location loc = event.getLocation();
			event.setLocation(loc);
			if((p.isBurning()
                    || mc.thePlayer.getActivePotionEffect(Potion.wither) != null
                    || mc.thePlayer.getActivePotionEffect(Potion.digSlowdown) != null
                    || mc.thePlayer.getActivePotionEffect(Potion.weakness) != null
                    || mc.thePlayer.getActivePotionEffect(Potion.moveSlowdown) != null
                    || mc.thePlayer.getActivePotionEffect(Potion.poison) != null) && !p.isInsideOfMaterial(Material.fire) && !p.isInsideOfMaterial(Material.lava) && p.onGround){
				for(int i = 0; i <= 200; i++) mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
			}
		}
	}

}
