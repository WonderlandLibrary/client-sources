package net.augustus.irc;

import net.augustus.Augustus;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.interfaces.MM;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class DiscordBot extends ListenerAdapter{

	public static String TOKEN = "";
	public static final String CHANNEL_ID = "1260522500018933761";

    private static JDA jda;
	
	public void init() {
      try {
          JDABuilder builder = JDABuilder.createDefault(TOKEN);
          builder.addEventListeners(new DiscordBot());
          jda = builder.build();
      }catch(Exception e) {
          e.printStackTrace();
      }
	}
	
	@Override
    public void onReady(ReadyEvent event) {
    }
	
	@Override
    public void onMessageReceived(MessageReceivedEvent event) {
		Message message = event.getMessage();
        String content = message.getContentRaw();
        if(event.getAuthor().getName().equalsIgnoreCase("irc")) {
        	System.out.println(content);
        	Augustus.getInstance().getIrc().messageReceived(content);
        }
    }

    public static void sendMessage(String message, String clientBrand) {
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        if (channel != null) {
            channel.sendMessage(getHeaders(clientBrand) + message).queue();
        } else {
            System.out.println("Channel not found!");
        }
    }
    
    public static String getHeaders(String clientBrand) {
    	String s = "[";
    	s = s + clientBrand + ":";
    	if(MC.mc.theWorld != null) {
	    	if(MM.mm.irc.showUsername.getBoolean())
	    		s = s + MC.mc.getSession().getUsername() + ":";
	    	if(MM.mm.irc.showCoords.getBoolean())
	    		s = s + MC.mc.thePlayer.posX + "=" + MC.mc.thePlayer.posY + "=" + MC.mc.thePlayer.posZ + ":";
	    	if(MM.mm.irc.showHealth.getBoolean())
	    		s = s + MC.mc.thePlayer.getHealth() + "/" + MC.mc.thePlayer.getMaxHealth();
    	}
    	s = s + "]";
    	return s;
    }

}
