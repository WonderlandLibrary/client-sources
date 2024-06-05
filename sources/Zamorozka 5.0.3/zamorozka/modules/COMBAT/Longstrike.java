package zamorozka.modules.COMBAT;

import java.util.ArrayList;
import java.util.Random;

import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameType;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Longstrike extends Module
{
	public Longstrike(){
		super("TriggerBot", 0, Category.COMBAT);
	}
    @Override
    public void setup() {
    	ArrayList<String> options2 = new ArrayList<>();
        options2.add("Enable");
        options2.add("Disable"); 
         Zamorozka.settingsManager.rSetting(new Setting("Dist", this, 3, 0, 10, true));
         Zamorozka.settingsManager.rSetting(new Setting("OnlySword", this, false)); 
    }
	private final Random random = new Random();
	  
	  public void onUpdate()
	  {
	    if (getState()) {

		    for (Entity entities : mc.world.loadedEntityList) {
			      if ((entities != mc.player) && (entities != null) && 
			        ((entities instanceof EntityPlayer)))
			      {
			    	 float f = mc.player.getDistanceToEntity(entities);
			    	 if(f<Zamorozka.settingsManager.getSettingByName("Dist").getValDouble() && mc.player.canEntityBeSeen(entities) && mc.player.getCooledAttackStrength(0) == 1){
			    		 if(Zamorozka.settingsManager.getSettingByName("OnlySword").getValBoolean()) {	
			    		if(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
			    		 mc.playerController.attackEntity(mc.player, entities);
			    		 }else {
			    			 
			    		 }
			    		 mc.playerController.attackEntity(mc.player, entities); 
			    		 }
			    	 }
			      }
		    }
	    }
	  }
}