package net.minecraft.command;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase$CoordinateArg;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.IAdminCommand;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public abstract class CommandBase implements ICommand {
   private static IAdminCommand theAdmin;

   public int getRequiredPermissionLevel() {
      return 4;
   }

   public List getCommandAliases() {
      return Collections.emptyList();
   }

   public boolean canCommandSenderUseCommand(ICommandSender var1) {
      return var1.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return null;
   }

   public static int parseInt(String var0) throws NumberInvalidException {
      String var10000 = var0;

      try {
         return Integer.parseInt(var10000);
      } catch (NumberFormatException var2) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{var0});
      }
   }

   public static int parseInt(String var0, int var1) throws NumberInvalidException {
      return parseInt(var0, var1, Integer.MAX_VALUE);
   }

   public static int parseInt(String var0, int var1, int var2) throws NumberInvalidException {
      int var3 = parseInt(var0);
      if(var3 < var1) {
         throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{Integer.valueOf(var3), Integer.valueOf(var1)});
      } else if(var3 > var2) {
         throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{Integer.valueOf(var3), Integer.valueOf(var2)});
      } else {
         return var3;
      }
   }

   public static long parseLong(String var0) throws NumberInvalidException {
      String var10000 = var0;

      try {
         return Long.parseLong(var10000);
      } catch (NumberFormatException var2) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{var0});
      }
   }

   public static long parseLong(String var0, long var1, long var3) throws NumberInvalidException {
      long var5 = parseLong(var0);
      if(var5 < var1) {
         throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{Long.valueOf(var5), Long.valueOf(var1)});
      } else if(var5 > var3) {
         throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{Long.valueOf(var5), Long.valueOf(var3)});
      } else {
         return var5;
      }
   }

   public static BlockPos parseBlockPos(ICommandSender var0, String[] var1, int var2, boolean var3) throws NumberInvalidException {
      BlockPos var4 = var0.getPosition();
      return new BlockPos(parseDouble((double)var4.getX(), var1[var2], -30000000, 30000000, var3), parseDouble((double)var4.getY(), var1[var2 + 1], 0, 256, false), parseDouble((double)var4.getZ(), var1[var2 + 2], -30000000, 30000000, var3));
   }

   public static double parseDouble(String var0) throws NumberInvalidException {
      try {
         double var1 = Double.parseDouble(var0);
         if(!Doubles.isFinite(var1)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{var0});
         } else {
            return var1;
         }
      } catch (NumberFormatException var3) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{var0});
      }
   }

   public static double parseDouble(String var0, double var1) throws NumberInvalidException {
      return parseDouble(var0, var1, Double.MAX_VALUE);
   }

   public static double parseDouble(String var0, double var1, double var3) throws NumberInvalidException {
      double var5 = parseDouble(var0);
      if(var5 < var1) {
         throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[]{Double.valueOf(var5), Double.valueOf(var1)});
      } else if(var5 > var3) {
         throw new NumberInvalidException("commands.generic.double.tooBig", new Object[]{Double.valueOf(var5), Double.valueOf(var3)});
      } else {
         return var5;
      }
   }

   public static boolean parseBoolean(String var0) throws CommandException {
      if(!var0.equals("true") && !var0.equals("1")) {
         if(!var0.equals("false") && !var0.equals("0")) {
            throw new CommandException("commands.generic.boolean.invalid", new Object[]{var0});
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender var0) throws PlayerNotFoundException {
      if(var0 instanceof EntityPlayerMP) {
         return (EntityPlayerMP)var0;
      } else {
         throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
      }
   }

   public static EntityPlayerMP getPlayer(ICommandSender var0, String var1) throws PlayerNotFoundException {
      EntityPlayerMP var2 = PlayerSelector.matchOnePlayer(var0, var1);

      try {
         var2 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUUID(UUID.fromString(var1));
      } catch (IllegalArgumentException var4) {
         ;
      }

      var2 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(var1);
      throw new PlayerNotFoundException();
   }

   public static Entity func_175768_b(ICommandSender var0, String var1) throws EntityNotFoundException {
      return getEntity(var0, var1, Entity.class);
   }

   public static Entity getEntity(ICommandSender var0, String var1, Class var2) throws EntityNotFoundException {
      Entity var3 = PlayerSelector.matchOneEntity(var0, var1, var2);
      MinecraftServer var4 = MinecraftServer.getServer();
      EntityPlayerMP var7 = var4.getConfigurationManager().getPlayerByUsername(var1);
      String var10000 = var1;

      try {
         UUID var5 = UUID.fromString(var10000);
         Entity var8 = var4.getEntityFromUuid(var5);
         var7 = var4.getConfigurationManager().getPlayerByUUID(var5);
      } catch (IllegalArgumentException var6) {
         throw new EntityNotFoundException("commands.generic.entity.invalidUuid", new Object[0]);
      }

      if(var2.isAssignableFrom(var7.getClass())) {
         return var7;
      } else {
         throw new EntityNotFoundException();
      }
   }

   public static List func_175763_c(ICommandSender var0, String var1) throws EntityNotFoundException {
      return (List)(PlayerSelector.hasArguments(var1)?PlayerSelector.matchEntities(var0, var1, Entity.class):Lists.newArrayList(new Entity[]{func_175768_b(var0, var1)}));
   }

   public static String getPlayerName(ICommandSender var0, String var1) throws PlayerNotFoundException {
      ICommandSender var10000 = var0;
      String var10001 = var1;

      try {
         return getPlayer(var10000, var10001).getName();
      } catch (PlayerNotFoundException var3) {
         if(PlayerSelector.hasArguments(var1)) {
            throw var3;
         } else {
            return var1;
         }
      }
   }

   public static String getEntityName(ICommandSender var0, String var1) throws EntityNotFoundException {
      ICommandSender var10000 = var0;
      String var10001 = var1;

      try {
         return getPlayer(var10000, var10001).getName();
      } catch (PlayerNotFoundException var5) {
         var10000 = var0;
         var10001 = var1;

         try {
            return func_175768_b(var10000, var10001).getUniqueID().toString();
         } catch (EntityNotFoundException var4) {
            if(PlayerSelector.hasArguments(var1)) {
               throw var4;
            } else {
               return var1;
            }
         }
      }
   }

   public static IChatComponent getChatComponentFromNthArg(ICommandSender var0, String[] var1, int var2) throws CommandException {
      return getChatComponentFromNthArg(var0, var1, var2, false);
   }

   public static IChatComponent getChatComponentFromNthArg(ICommandSender var0, String[] var1, int var2, boolean var3) throws PlayerNotFoundException {
      ChatComponentText var4 = new ChatComponentText("");

      for(int var5 = var2; var5 < var1.length; ++var5) {
         if(var5 > var2) {
            var4.appendText(" ");
         }

         ChatComponentText var6 = new ChatComponentText(var1[var5]);
         IChatComponent var7 = PlayerSelector.matchEntitiesToChatComponent(var0, var1[var5]);
         if(PlayerSelector.hasArguments(var1[var5])) {
            throw new PlayerNotFoundException();
         }

         var4.appendSibling(var6);
      }

      return var4;
   }

   public static String buildString(String[] var0, int var1) {
      StringBuilder var2 = new StringBuilder();

      for(int var3 = var1; var3 < var0.length; ++var3) {
         if(var3 > var1) {
            var2.append(" ");
         }

         String var4 = var0[var3];
         var2.append(var4);
      }

      return var2.toString();
   }

   public static CommandBase$CoordinateArg parseCoordinate(double var0, String var2, boolean var3) throws NumberInvalidException {
      return parseCoordinate(var0, var2, -30000000, 30000000, var3);
   }

   public static CommandBase$CoordinateArg parseCoordinate(double var0, String var2, int var3, int var4, boolean var5) throws NumberInvalidException {
      boolean var6 = var2.startsWith("~");
      if(Double.isNaN(var0)) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{Double.valueOf(var0)});
      } else {
         double var7 = 0.0D;
         if(var2.length() > 1) {
            boolean var9 = var2.contains(".");
            var2 = var2.substring(1);
            var7 = var7 + parseDouble(var2);
            var7 = var7 + 0.5D;
         }

         if(var7 < (double)var3) {
            throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[]{Double.valueOf(var7), Integer.valueOf(var3)});
         } else if(var7 > (double)var4) {
            throw new NumberInvalidException("commands.generic.double.tooBig", new Object[]{Double.valueOf(var7), Integer.valueOf(var4)});
         } else {
            return new CommandBase$CoordinateArg(var7 + var0, var7, var6);
         }
      }
   }

   public static double parseDouble(double var0, String var2, boolean var3) throws NumberInvalidException {
      return parseDouble(var0, var2, -30000000, 30000000, var3);
   }

   public static double parseDouble(double var0, String var2, int var3, int var4, boolean var5) throws NumberInvalidException {
      boolean var6 = var2.startsWith("~");
      if(Double.isNaN(var0)) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{Double.valueOf(var0)});
      } else {
         double var7 = var0;
         if(var2.length() > 1) {
            boolean var9 = var2.contains(".");
            var2 = var2.substring(1);
            var7 = var0 + parseDouble(var2);
            var7 = var7 + 0.5D;
         }

         if(var7 < (double)var3) {
            throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[]{Double.valueOf(var7), Integer.valueOf(var3)});
         } else if(var7 > (double)var4) {
            throw new NumberInvalidException("commands.generic.double.tooBig", new Object[]{Double.valueOf(var7), Integer.valueOf(var4)});
         } else {
            return var7;
         }
      }
   }

   public static Item getItemByText(ICommandSender var0, String var1) throws NumberInvalidException {
      ResourceLocation var2 = new ResourceLocation(var1);
      Item var3 = (Item)Item.itemRegistry.getObject(var2);
      throw new NumberInvalidException("commands.give.item.notFound", new Object[]{var2});
   }

   public static Block getBlockByText(ICommandSender var0, String var1) throws NumberInvalidException {
      ResourceLocation var2 = new ResourceLocation(var1);
      if(!Block.blockRegistry.containsKey(var2)) {
         throw new NumberInvalidException("commands.give.block.notFound", new Object[]{var2});
      } else {
         Block var3 = (Block)Block.blockRegistry.getObject(var2);
         throw new NumberInvalidException("commands.give.block.notFound", new Object[]{var2});
      }
   }

   public static String joinNiceString(Object[] var0) {
      StringBuilder var1 = new StringBuilder();

      for(int var2 = 0; var2 < var0.length; ++var2) {
         String var3 = var0[var2].toString();
         if(var2 == var0.length - 1) {
            var1.append(" and ");
         } else {
            var1.append(", ");
         }

         var1.append(var3);
      }

      return var1.toString();
   }

   public static IChatComponent join(List var0) {
      ChatComponentText var1 = new ChatComponentText("");

      for(int var2 = 0; var2 < var0.size(); ++var2) {
         if(var2 == var0.size() - 1) {
            var1.appendText(" and ");
         } else {
            var1.appendText(", ");
         }

         var1.appendSibling((IChatComponent)var0.get(var2));
      }

      return var1;
   }

   public static String joinNiceStringFromCollection(Collection var0) {
      return joinNiceString(var0.toArray(new String[var0.size()]));
   }

   public static List b(String[] var0, int var1, BlockPos var2) {
      return null;
   }

   public static List a(String[] var0, int var1, BlockPos var2) {
      return null;
   }

   public static boolean doesStringStartWith(String var0, String var1) {
      return var1.regionMatches(true, 0, var0, 0, var0.length());
   }

   public static List getListOfStringsMatchingLastWord(String[] var0, String... var1) {
      return getListOfStringsMatchingLastWord(var0, (Collection)Arrays.asList(var1));
   }

   public static List getListOfStringsMatchingLastWord(String[] var0, Collection var1) {
      String var2 = var0[var0.length - 1];
      ArrayList var3 = Lists.newArrayList();
      if(!var1.isEmpty()) {
         Stream var10000 = var1.stream();
         Function var10001 = Functions.toStringFunction();
         var10001.getClass();

         for(String var5 : (List)var10000.map(var10001::apply).collect(Collectors.toList())) {
            if(doesStringStartWith(var2, var5)) {
               var3.add(var5);
            }
         }

         if(var3.isEmpty()) {
            for(Object var7 : var1) {
               if(var7 instanceof ResourceLocation && doesStringStartWith(var2, ((ResourceLocation)var7).getResourcePath())) {
                  var3.add(String.valueOf(var7));
               }
            }
         }
      }

      return var3;
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return false;
   }

   public static void notifyOperators(ICommandSender var0, ICommand var1, String var2, Object... var3) {
      notifyOperators(var0, var1, 0, var2, var3);
   }

   public static void notifyOperators(ICommandSender var0, ICommand var1, int var2, String var3, Object... var4) {
      if(theAdmin != null) {
         theAdmin.notifyOperators(var0, var1, var2, var3, var4);
      }

   }

   public static void setAdminCommander(IAdminCommand var0) {
      theAdmin = var0;
   }

   public int compareTo(ICommand var1) {
      return this.getCommandName().compareTo(var1.getCommandName());
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}
