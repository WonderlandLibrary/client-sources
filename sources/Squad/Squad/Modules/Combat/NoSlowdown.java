package Squad.Modules.Combat;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowdown extends Module{

	
	private float range;
	public NoSlowdown() {
		super("NoSlow", Keyboard.KEY_NONE, 0x88, Category.Other);
		// TODO Auto-generated constructor stub
	}
	
	public void onEnable(){
		setDisplayname("NoSlow §7AAC");
		EventManager.register(this);
	}
	
	public void onDisable(){
		EventManager.unregister(this);
	}
	
	public void setup(){
		ArrayList<String> options = new ArrayList<>();
		Squad.instance.setmgr.rSetting(new Setting("RangeNS", this, 4.0, 1.0, 6.0, false));
	}
	
	
	
	public void Check(EntityLivingBase en){
		if(en.getDistanceToEntity(mc.thePlayer) <= 5){
			
		}
		
		
	}
	@EventTarget
	   public void onUpdate(EventUpdate e){
		range = ((float) Squad.instance.setmgr.getSettingByName("RangeNS").getValDouble());
		for (Object o : mc.theWorld.loadedEntityList) {
			if (o instanceof EntityLivingBase) {
				if (o != mc.thePlayer) {
		EntityLivingBase en = (EntityLivingBase) o;
		if(en.getDistanceToEntity(mc.thePlayer) <= range){
			if ((mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword))
		    {
		      mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
		      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
		    }
				}
			}
		}
			}
	}
}
		

   


