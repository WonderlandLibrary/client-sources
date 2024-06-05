package net.minecraft.util;

import java.util.List;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentScore;
import net.minecraft.util.ChatComponentSelector;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class ChatComponentProcessor {
   public static IChatComponent processComponent(ICommandSender var0, IChatComponent var1, Entity var2) throws CommandException {
      IChatComponent var3 = null;
      if(var1 instanceof ChatComponentScore) {
         ChatComponentScore var4 = (ChatComponentScore)var1;
         String var5 = var4.getName();
         if(PlayerSelector.hasArguments(var5)) {
            List var6 = PlayerSelector.matchEntities(var0, var5, Entity.class);
            if(var6.size() != 1) {
               throw new EntityNotFoundException();
            }

            var5 = ((Entity)var6.get(0)).getName();
         }

         var3 = var5.equals("*")?new ChatComponentScore(var2.getName(), var4.getObjective()):new ChatComponentScore(var5, var4.getObjective());
         ((ChatComponentScore)var3).setValue(var4.getUnformattedTextForChat());
      } else if(var1 instanceof ChatComponentSelector) {
         String var9 = ((ChatComponentSelector)var1).getSelector();
         var3 = PlayerSelector.matchEntitiesToChatComponent(var0, var9);
         var3 = new ChatComponentText("");
      } else if(var1 instanceof ChatComponentText) {
         var3 = new ChatComponentText(((ChatComponentText)var1).getChatComponentText_TextValue());
      } else {
         if(!(var1 instanceof ChatComponentTranslation)) {
            return var1;
         }

         Object[] var10 = ((ChatComponentTranslation)var1).getFormatArgs();

         for(int var12 = 0; var12 < var10.length; ++var12) {
            Object var14 = var10[var12];
            if(var14 instanceof IChatComponent) {
               var10[var12] = processComponent(var0, (IChatComponent)var14, var2);
            }
         }

         var3 = new ChatComponentTranslation(((ChatComponentTranslation)var1).getKey(), var10);
      }

      ChatStyle var11 = var1.getChatStyle();
      ((IChatComponent)var3).setChatStyle(var11.createShallowCopy());

      for(IChatComponent var15 : var1.getSiblings()) {
         ((IChatComponent)var3).appendSibling(processComponent(var0, var15, var2));
      }

      return (IChatComponent)var3;
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
