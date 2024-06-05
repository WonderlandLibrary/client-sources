package Focus.Beta.IMPL.Module.impl.misc;

import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPacketReceive;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.world.biome.BiomeGenMesa;

public class KillSluts extends Module{


	private String[] Messages = new String[]{
			"Dont Download FDP, get Focus Client  ; ",
			"Config issue [Focus] ",
			"Get Focus get good  [Focus] ",
			"How Did you even press the install button with that aim [Focus] ",
			"To get good you need to have Focus " ,
			"What should i choose Focus or Focus",
			"NoHaxJustFocus",
			"Stop it, ge some help! Get Focus",
			"#SwitchToFocus ",
			"FDP Sucks, just get Focus ",
			"Your client sucks, just get Focus",
			"Why use skidma in 2021 when you could be using Focus?",
			"You need Focus Client lol ",
			"Im not cheating, im just using Focus ",
			"Image not using Focus",
			"Oh that probably hurt u might need Focus to fix that "
			
			

	};
	public KillSluts(){
		super("KillSluts", new String[0], Type.MISC, "Insults your opponents");
	}
	
    @EventHandler
    public void onPacketReceive(EventPacketReceive event) {   
		if(event.getPacket() instanceof S02PacketChat){
				S02PacketChat packet = (S02PacketChat)event.getPacket();
				String message = packet.getChatComponent().getUnformattedText();
				if(!message.isEmpty()) {
					for(Object entity : mc.theWorld.loadedEntityList) {
						if (entity instanceof EntityPlayer) {
							EntityPlayer p = (EntityPlayer) entity;
							if (message.contains(p.getName()) && message.contains(mc.thePlayer.getName())
									&& (message.contains("killed") || message.contains("slain") || message.contains("knocked") || message.contains("thrown") || message.contains("foi morto por"))
									&& !p.getName().equalsIgnoreCase(mc.thePlayer.getName())) {

								EntityPlayer e = (EntityPlayer) entity;
								mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(e.getName() + ", " + randomPhrase() + " [" + RandomUtils.nextLong(4444L, 10000000L) + "]"));
							}
							
					}
				}
			}
		}
		
   	}
		    private String randomPhrase() {
		  		Random random = new Random();
		  		return Messages[random.nextInt(Messages.length)];
		  	}

}
