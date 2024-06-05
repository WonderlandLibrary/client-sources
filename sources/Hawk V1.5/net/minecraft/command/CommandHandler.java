package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandHandler implements ICommandManager {
   private static final Logger logger = LogManager.getLogger();
   private final Map commandMap = Maps.newHashMap();
   private static final String __OBFID = "CL_00001765";
   private final Set commandSet = Sets.newHashSet();

   public int executeCommand(ICommandSender var1, String var2) {
      var2 = var2.trim();
      if (var2.startsWith("/")) {
         var2 = var2.substring(1);
      }

      String[] var3 = var2.split(" ");
      String var4 = var3[0];
      var3 = dropFirstString(var3);
      ICommand var5 = (ICommand)this.commandMap.get(var4);
      int var6 = this.getUsernameIndex(var5, var3);
      int var7 = 0;
      ChatComponentTranslation var8;
      if (var5 == null) {
         var8 = new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
         var8.getChatStyle().setColor(EnumChatFormatting.RED);
         var1.addChatMessage(var8);
      } else if (var5.canCommandSenderUseCommand(var1)) {
         if (var6 > -1) {
            List var9 = PlayerSelector.func_179656_b(var1, var3[var6], Entity.class);
            String var10 = var3[var6];
            var1.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, var9.size());
            Iterator var11 = var9.iterator();

            while(var11.hasNext()) {
               Entity var12 = (Entity)var11.next();
               var3[var6] = var12.getUniqueID().toString();
               if (this.func_175786_a(var1, var3, var5, var2)) {
                  ++var7;
               }
            }

            var3[var6] = var10;
         } else {
            var1.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
            if (this.func_175786_a(var1, var3, var5, var2)) {
               ++var7;
            }
         }
      } else {
         var8 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
         var8.getChatStyle().setColor(EnumChatFormatting.RED);
         var1.addChatMessage(var8);
      }

      var1.func_174794_a(CommandResultStats.Type.SUCCESS_COUNT, var7);
      return var7;
   }

   public List getPossibleCommands(ICommandSender var1) {
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = this.commandSet.iterator();

      while(var3.hasNext()) {
         ICommand var4 = (ICommand)var3.next();
         if (var4.canCommandSenderUseCommand(var1)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   public List getTabCompletionOptions(ICommandSender var1, String var2, BlockPos var3) {
      String[] var4 = var2.split(" ", -1);
      String var5 = var4[0];
      if (var4.length == 1) {
         ArrayList var9 = Lists.newArrayList();
         Iterator var7 = this.commandMap.entrySet().iterator();

         while(var7.hasNext()) {
            Entry var8 = (Entry)var7.next();
            if (CommandBase.doesStringStartWith(var5, (String)var8.getKey()) && ((ICommand)var8.getValue()).canCommandSenderUseCommand(var1)) {
               var9.add(var8.getKey());
            }
         }

         return var9;
      } else {
         if (var4.length > 1) {
            ICommand var6 = (ICommand)this.commandMap.get(var5);
            if (var6 != null && var6.canCommandSenderUseCommand(var1)) {
               return var6.addTabCompletionOptions(var1, dropFirstString(var4), var3);
            }
         }

         return null;
      }
   }

   private int getUsernameIndex(ICommand var1, String[] var2) {
      if (var1 == null) {
         return -1;
      } else {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            if (var1.isUsernameIndex(var2, var3) && PlayerSelector.matchesMultiplePlayers(var2[var3])) {
               return var3;
            }
         }

         return -1;
      }
   }

   public ICommand registerCommand(ICommand var1) {
      this.commandMap.put(var1.getCommandName(), var1);
      this.commandSet.add(var1);
      Iterator var2 = var1.getCommandAliases().iterator();

      while(true) {
         String var3;
         ICommand var4;
         do {
            if (!var2.hasNext()) {
               return var1;
            }

            var3 = (String)var2.next();
            var4 = (ICommand)this.commandMap.get(var3);
         } while(var4 != null && var4.getCommandName().equals(var3));

         this.commandMap.put(var3, var1);
      }
   }

   protected boolean func_175786_a(ICommandSender var1, String[] var2, ICommand var3, String var4) {
      ChatComponentTranslation var5;
      try {
         var3.processCommand(var1, var2);
         return true;
      } catch (WrongUsageException var7) {
         var5 = new ChatComponentTranslation("commands.generic.usage", new Object[]{new ChatComponentTranslation(var7.getMessage(), var7.getErrorOjbects())});
         var5.getChatStyle().setColor(EnumChatFormatting.RED);
         var1.addChatMessage(var5);
      } catch (CommandException var8) {
         var5 = new ChatComponentTranslation(var8.getMessage(), var8.getErrorOjbects());
         var5.getChatStyle().setColor(EnumChatFormatting.RED);
         var1.addChatMessage(var5);
      } catch (Throwable var9) {
         var5 = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
         var5.getChatStyle().setColor(EnumChatFormatting.RED);
         var1.addChatMessage(var5);
         logger.error(String.valueOf((new StringBuilder("Couldn't process command: '")).append(var4).append("'")), var9);
      }

      return false;
   }

   public Map getCommands() {
      return this.commandMap;
   }

   private static String[] dropFirstString(String[] var0) {
      String[] var1 = new String[var0.length - 1];
      System.arraycopy(var0, 1, var1, 0, var0.length - 1);
      return var1;
   }
}
