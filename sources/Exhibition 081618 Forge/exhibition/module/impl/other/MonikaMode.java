package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventMouse;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.ChatUtil;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;

import java.util.Random;

import org.lwjgl.input.Mouse;

public class MonikaMode extends Module {
   public MonikaMode(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
	   EventMotionUpdate em = (EventMotionUpdate)event;
       for(final NetworkPlayerInfo playerInfo : mc.getNetHandler().getPlayerInfoMap()) {

       }
   }
   
   public String getRandomMonika() {
	   String[] strings = {"Monika","Monika","M0nika","Mon1ka","Monik4","M0n1ka","M0nik4"};
	return strings[new Random().nextInt(strings.length)];
   }
}
