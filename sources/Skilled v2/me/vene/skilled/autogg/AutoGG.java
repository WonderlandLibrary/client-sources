 package me.vene.skilled.autogg;
 
 import java.io.IOException;
 import java.net.URL;
 import net.minecraft.client.Minecraft;
 import net.minecraft.util.EnumChatFormatting;
 import net.minecraftforge.client.event.ClientChatReceivedEvent;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import org.apache.commons.io.IOUtils;

 public class AutoGG {
   public static String unformattedMessage;
   private String[] triggers;
   private Minecraft mc = Minecraft.getMinecraft();
   
   public AutoGG() throws IOException {
     String rawTriggers = IOUtils.toString(new URL("https://gist.githubusercontent.com/minemanpi/72c38b0023f5062a5f3eba02a5132603/raw/triggers.txt"));
     this.triggers = rawTriggers.split("\n");
   }

   @SubscribeEvent
   public void onChat(final ClientChatReceivedEvent event) throws InterruptedException {
       AutoGG.unformattedMessage = event.message.getUnformattedText();
       AutoGG.unformattedMessage = EnumChatFormatting.getTextWithoutFormattingCodes(AutoGG.unformattedMessage);
       for (int i = 0; i < this.triggers.length; ++i) {
           if (AutoGG.unformattedMessage.contains(this.triggers[i])) {
               this.mc.thePlayer.sendChatMessage("/achat gg");
               break;
           }
       }
   }
}
