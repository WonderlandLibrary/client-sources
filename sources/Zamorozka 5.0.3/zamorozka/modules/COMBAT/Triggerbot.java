package zamorozka.modules.COMBAT;

import java.util.List;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Triggerbot extends Module
{
	  
	  public Triggerbot()
	  {
	    super("TriggerBot", 0, Category.COMBAT);
	  }
	    @Override
	    public void setup() {
	        Zamorozka.settingsManager.rSetting(new Setting("TrigDistance", this, 4, 0, 6, true));
	    }
	  int delay;
	  public void onUpdate()
	  {
	    if (getState()) {
			  double mode = Zamorozka.settingsManager.getSettingByName("TrigDistance").getValDouble();
		        this.setDisplayName("TriggerBot §f§ " + (int)mode);
	      killaura();
	    }
	    if(mc.world==null){
	    	isEnabled=false;
	    }
	  }

	  private void killaura()
	  {

			if(mc.player.getCooledAttackStrength(0) >= 1)
			{
				for(Object o: mc.world.loadedEntityList)
				{
					List list = mc.world.playerEntities;
				    for (int k = 0; k < list.size(); k++) {
					      if (((EntityPlayer)list.get(k)).getName() != mc.player.getName())
					      {
					        EntityPlayer entityplayer = (EntityPlayer)list.get(1);
					        Entity e = (Entity) o;
								boolean checks = !(e instanceof EntityPlayerSP) && (e instanceof EntityPlayer) && ((EntityLivingBase)e).getHealth() > 0;
								float f3 = mc.player.getDistanceToEntity(e);
								if (e instanceof EntityLivingBase) {
							        if ((f3 < Zamorozka.settingsManager.getSettingByName("TrigDistance").getValDouble())&& mc.player.getCooledAttackStrength(0) == 1)
							        {
										if(checks) 
											{
											 
											if(Minecraft.getMinecraft().objectMouseOver.entityHit instanceof EntityPlayer){
										    			mc.playerController.attackEntity(mc.player, e);
														mc.player.swingArm(EnumHand.MAIN_HAND);			
										    		}
											}
											}
							        }
								}
					      }
				    }
				}
			}
	  }