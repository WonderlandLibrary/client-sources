package rip.helium.cheat.impl.combat;

import java.util.ArrayList;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.entity.player.EntityPlayer;
import rip.helium.ClientBase;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.notification.mgmt.*;
import rip.helium.utils.property.impl.StringsProperty;

public class AntiBot extends Cheat {
	
	
	public static ArrayList<EntityPlayer> bots = new ArrayList<>();
    public static StringsProperty prop_mode = new StringsProperty("Mode", "Changes the mode.", null, false, true, new String[]{"Watchdog" }, new Boolean[]{true});

    public AntiBot(){
        super("AntiBot", "Stops killaura from targetting bots", CheatCategory.COMBAT);
        registerProperties(prop_mode);
        
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent e) {
    	//ClientBase.chat("hi");
        setMode(prop_mode.getSelectedStrings().get(0));
        if (mc.getCurrentServerData() != null && mc.theWorld != null && mc.getCurrentServerData().serverIP.contains("hypixel")) {
            for (Object entities : mc.theWorld.loadedEntityList) {
                if (entities instanceof EntityPlayer) {
                    EntityPlayer entity = (EntityPlayer) entities;
                    if (entity != mc.thePlayer) {
                    	
                        if (mc.thePlayer.getDistanceToEntity(entity) < 10) {
                            if (!entity.getDisplayName().getFormattedText().startsWith("§") || entity.isInvisible() || entity.getDisplayName().getFormattedText().toLowerCase().contains("npc")) {
                                bots.add(entity);
                                //ClientBase.chat(entity.getName() + " has big gay");
                            }
                        }
                    }
                    if (bots.contains(entity) && !entity.isInvisible()) {
                        bots.remove(entity);
                        
                    }
                }
            }
        }
        else if (mc.getCurrentServerData().serverIP.contains("mineplex")) {
    	    for (final EntityPlayer object2 : this.mc.theWorld.playerEntities) {
    		    EntityPlayer entity = (EntityPlayer) object2;
                if (!(object2.getHealth() == Double.NaN)) {
            	    bots.add(object2);
                }
    	    }
        }
    }
}
