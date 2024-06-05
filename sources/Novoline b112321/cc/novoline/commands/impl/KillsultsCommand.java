package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.modules.misc.Killsults;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.messages.TextMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.Uj;
import net.a_E;
import net.minecraft.util.EnumChatFormatting;

public class KillsultsCommand extends NovoCommand {
   public KillsultsCommand(Novoline var1) {
      super(var1, "killsults", (Iterable)Arrays.asList(new String[]{"ks", "sults"}));
   }

   public void process(String[] var1) throws IOException {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.a("Killsults help:", ".killsults", new Uj[]{MessageFactory.a("list", "shows all killsults"), MessageFactory.a("add (\"text\")", "add a killsults"), MessageFactory.a("remove (index)", "removes a killsults"), MessageFactory.a("clear", "removes all killsults")});
      } else {
         String var3 = var1[0].toLowerCase();
         Killsults var4 = (Killsults)this.novoline.getModuleManager().getModule(Killsults.class);
         List var5 = var4.getKillsults();
         byte var7 = -1;
         switch(var3.hashCode()) {
         case 96417:
            if(!var3.equals("add")) {
               break;
            }

            var7 = 0;
         case -934610812:
            if(!var3.equals("remove")) {
               break;
            }

            var7 = 1;
         case 94746189:
            if(!var3.equals("clear")) {
               break;
            }

            var7 = 2;
         case 3322014:
            if(var3.equals("list")) {
               var7 = 3;
            }
         }

         switch(var7) {
         case 0:
            int var8 = 0;
            int var10 = var1.length;
            int var11 = 0;
            if(var11 < var10) {
               String var12 = var1[var11];
               if(var12.contains("\"")) {
                  var8 = Arrays.asList(var1).indexOf(var12);
               }

               ++var11;
            }

            if(!var1[var8].endsWith("\"")) {
               this.notifyError("message must be enclosed in quotation marks");
            }

            StringBuilder var9 = new StringBuilder();
            var10 = 1;
            if(var10 <= var8) {
               var9.append(var1[var10]).append(var10 == var8?"":" ");
               ++var10;
            }

            String var18 = var9.toString().replace("\"", "");
            if(var18.isEmpty()) {
               this.notifyError("message is empty!");
            }

            if(!var5.contains(var18)) {
               var5.add(var18);
               this.notify("added: \"" + var18 + "\" to list!");
            }

            this.notifyError("list already contains that line!");
         case 1:
            if(var1.length < 2) {
               this.notifyError("please specify the index of the killsult you want to delete");
            }

            String var13 = var1[1];
            if(var13.length() == 1 && Character.isDigit(var13.charAt(0))) {
               var13 = (String)var5.get(Integer.parseInt(var1[1]));
               if(var5.contains(var13)) {
                  var5.remove(var13);
                  this.notify("removed: " + var13 + " from list!");
               }

               this.notifyError("list does not contains that line!");
            }

            this.notifyError("please specify the index of the killsult you want to delete");
         case 2:
            if(var5.isEmpty()) {
               this.notifyError("list is empty!");
            }

            var5.clear();
            this.notify("list cleared!");
         case 3:
            var4.loadSults();
            if(var5.isEmpty()) {
               this.notifyError("list is empty!");
            }

            TextMessage var15 = MessageFactory.text("List of killsults:");
            this.send(var15, true);
            Iterator var19 = var5.iterator();
            if(var19.hasNext()) {
               String var21 = (String)var19.next();
               String var22 = "> " + var5.indexOf(var21) + ": " + EnumChatFormatting.GREEN + var21;
               if(var22.contains("%s")) {
                  this.send(var22.replace("%s", EnumChatFormatting.RED + "%s" + EnumChatFormatting.GREEN));
               }

               this.send(var22);
            }
         default:
         }
      }
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
