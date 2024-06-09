package lunadevs.luna.module.combat;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
/**
 * Thx iYoshiMC
 */
public class InfiniteAura extends Module {

	public InfiniteAura() {
		super("InfiniteReach", Keyboard.KEY_NONE, Category.COMBAT, true);
	}
	private int ticks = 0;
	private int tpdelay;
	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		 ticks++;
		 tpdelay++;
         if(ticks >= 20 - speed()){
             ticks = 0;
             for(Iterator<Entity> entities = mc.theWorld.loadedEntityList.iterator(); entities.hasNext();) {
                 Object theObject = entities.next();
                 if(theObject instanceof EntityLivingBase) {
                     EntityLivingBase entity = (EntityLivingBase) theObject;
                    
                     if(entity instanceof EntityPlayerSP) continue;
                    
                     if(mc.thePlayer.getDistanceToEntity(entity) <= 40F) {
                        
                         if(entity.isInvisible()){
                             break;
                         }
                         if(entity.isEntityAlive()) {
                        	 if(tpdelay >= 2){
                            	 zCore.p().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX+0.1, entity.posY, entity.posZ+0.1, true));
                            	 zCore.p().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, false));
                             }
                             if(mc.thePlayer.getHeldItem() != null){
                                     mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                                 }
                             if(mc.thePlayer.isBlocking()){
                                 mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
                             }
                             
                             
                            
                             mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                             mc.thePlayer.swingItem();
                             break;
                         }
                         super.onUpdate();
                     }
                 }
             }
         }
     }
 private int speed() {
     return 18;
 }


	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public String getValue() {
		return "Beta";
	}

}
