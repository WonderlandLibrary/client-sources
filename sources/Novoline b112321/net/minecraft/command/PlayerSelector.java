package net.minecraft.command;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.a24;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector$1;
import net.minecraft.command.PlayerSelector$10;
import net.minecraft.command.PlayerSelector$11;
import net.minecraft.command.PlayerSelector$12;
import net.minecraft.command.PlayerSelector$2;
import net.minecraft.command.PlayerSelector$3;
import net.minecraft.command.PlayerSelector$4;
import net.minecraft.command.PlayerSelector$5;
import net.minecraft.command.PlayerSelector$7;
import net.minecraft.command.PlayerSelector$8;
import net.minecraft.command.PlayerSelector$9;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings$GameType;

public class PlayerSelector {
   private static final Pattern tokenPattern = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
   private static final Pattern intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
   private static final Pattern keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
   private static final Set WORLD_BINDING_ARGS = Sets.newHashSet(new String[]{"x", "y", "z", "dx", "dy", "dz", "rm", "r"});

   public static EntityPlayerMP matchOnePlayer(ICommandSender var0, String var1) {
      return (EntityPlayerMP)matchOneEntity(var0, var1, EntityPlayerMP.class);
   }

   public static Entity matchOneEntity(ICommandSender var0, String var1, Class var2) {
      List var3 = matchEntities(var0, var1, var2);
      return var3.size() == 1?(Entity)var3.get(0):null;
   }

   public static IChatComponent matchEntitiesToChatComponent(ICommandSender var0, String var1) {
      List var2 = matchEntities(var0, var1, Entity.class);
      if(var2.isEmpty()) {
         return null;
      } else {
         ArrayList var3 = Lists.newArrayList();

         for(Entity var5 : var2) {
            var3.add(var5.getDisplayName());
         }

         return CommandBase.join(var3);
      }
   }

   public static List matchEntities(ICommandSender var0, String var1, Class var2) {
      Matcher var3 = tokenPattern.matcher(var1);
      if(var3.matches() && var0.canCommandSenderUseCommand(1, "@")) {
         Map var4 = getArgumentMap(var3.group(2));
         if(!isEntityTypeValid(var0, var4)) {
            return Collections.emptyList();
         } else {
            String var5 = var3.group(1);
            BlockPos var6 = func_179664_b(var4, var0.getPosition());
            List var7 = getWorlds(var0, var4);
            ArrayList var8 = Lists.newArrayList();

            for(World var10 : var7) {
               ArrayList var11 = Lists.newArrayList();
               var11.addAll(func_179663_a(var4, var5));
               var11.addAll(func_179648_b(var4));
               var11.addAll(func_179649_c(var4));
               var11.addAll(func_179659_d(var4));
               var11.addAll(func_179657_e(var4));
               var11.addAll(func_179647_f(var4));
               var11.addAll(func_180698_a(var4, var6));
               var11.addAll(func_179662_g(var4));
               var8.addAll(filterResults(var4, var2, var11, var5, var10, var6));
            }

            return func_179658_a(var8, var4, var0, var2, var5, var6);
         }
      } else {
         return Collections.emptyList();
      }
   }

   private static List getWorlds(ICommandSender var0, Map var1) {
      ArrayList var2 = Lists.newArrayList();
      if(func_179665_h(var1)) {
         var2.add(var0.getEntityWorld());
      } else {
         Collections.addAll(var2, MinecraftServer.getServer().worldServers);
      }

      return var2;
   }

   private static boolean isEntityTypeValid(ICommandSender var0, Map var1) {
      String var2 = func_179651_b(var1, "type");
      var2 = var2.startsWith("!")?var2.substring(1):var2;
      if(!EntityList.isStringValidEntityName(var2)) {
         ChatComponentTranslation var3 = new ChatComponentTranslation("commands.generic.entity.invalidType", new Object[]{var2});
         var3.getChatStyle().setColor(EnumChatFormatting.RED);
         var0.addChatMessage(var3);
         return false;
      } else {
         return true;
      }
   }

   private static List func_179663_a(Map var0, String var1) {
      ArrayList var2 = Lists.newArrayList();
      String var3 = func_179651_b(var0, "type");
      boolean var4 = var3.startsWith("!");
      var3 = var3.substring(1);
      boolean var5 = !var1.equals("e");
      boolean var6 = var1.equals("r");
      if(!var1.equals("e")) {
         var2.add(new PlayerSelector$1());
      } else {
         var2.add(new PlayerSelector$2(var3, var4));
      }

      return var2;
   }

   private static List func_179648_b(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      int var2 = parseIntWithDefault(var0, "lm", -1);
      int var3 = parseIntWithDefault(var0, "l", -1);
      if(var2 > -1 || var3 > -1) {
         var1.add(new PlayerSelector$3(var2, var3));
      }

      return var1;
   }

   private static List func_179649_c(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      int var2 = parseIntWithDefault(var0, "m", WorldSettings$GameType.NOT_SET.getID());
      if(var2 != WorldSettings$GameType.NOT_SET.getID()) {
         var1.add(new PlayerSelector$4(var2));
      }

      return var1;
   }

   private static List func_179659_d(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      String var2 = func_179651_b(var0, "team");
      boolean var3 = var2.startsWith("!");
      var2 = var2.substring(1);
      var1.add(new PlayerSelector$5(var2, var3));
      return var1;
   }

   private static List func_179657_e(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      Map var2 = func_96560_a(var0);
      if(!var2.isEmpty()) {
         var1.add(new a24(var2));
      }

      return var1;
   }

   private static List func_179647_f(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      String var2 = func_179651_b(var0, "name");
      boolean var3 = var2.startsWith("!");
      var2 = var2.substring(1);
      var1.add(new PlayerSelector$7(var2, var3));
      return var1;
   }

   private static List func_180698_a(Map var0, BlockPos var1) {
      ArrayList var2 = Lists.newArrayList();
      int var3 = parseIntWithDefault(var0, "rm", -1);
      int var4 = parseIntWithDefault(var0, "r", -1);
      int var5 = var3 * var3;
      int var6 = var4 * var4;
      var2.add(new PlayerSelector$8(var1, var3, var5, var4, var6));
      return var2;
   }

   private static List func_179662_g(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      if(var0.containsKey("rym") || var0.containsKey("ry")) {
         int var2 = func_179650_a(parseIntWithDefault(var0, "rym", 0));
         int var3 = func_179650_a(parseIntWithDefault(var0, "ry", 359));
         var1.add(new PlayerSelector$9(var2, var3));
      }

      if(var0.containsKey("rxm") || var0.containsKey("rx")) {
         int var4 = func_179650_a(parseIntWithDefault(var0, "rxm", 0));
         int var5 = func_179650_a(parseIntWithDefault(var0, "rx", 359));
         var1.add(new PlayerSelector$10(var4, var5));
      }

      return var1;
   }

   private static List filterResults(Map var0, Class var1, List var2, String var3, World var4, BlockPos var5) {
      ArrayList var6 = Lists.newArrayList();
      String var7 = func_179651_b(var0, "type");
      var7 = var7.startsWith("!")?var7.substring(1):var7;
      boolean var8 = !var3.equals("e");
      boolean var9 = var3.equals("r");
      int var10 = parseIntWithDefault(var0, "dx", 0);
      int var11 = parseIntWithDefault(var0, "dy", 0);
      int var12 = parseIntWithDefault(var0, "dz", 0);
      int var13 = parseIntWithDefault(var0, "r", -1);
      Predicate var14 = Predicates.and(var2);
      Predicate var15 = Predicates.and(EntitySelectors.selectAnything, var14);
      int var16 = var4.getPlayerEntities().size();
      int var17 = var4.getLoadedEntityList().size();
      boolean var18 = var16 < var17 / 16;
      if(!var0.containsKey("dx") && !var0.containsKey("dy") && !var0.containsKey("dz")) {
         new AxisAlignedBB((double)(var5.getX() - var13), (double)(var5.getY() - var13), (double)(var5.getZ() - var13), (double)(var5.getX() + var13 + 1), (double)(var5.getY() + var13 + 1), (double)(var5.getZ() + var13 + 1));
         var6.addAll(var4.getPlayers(var1, var15));
      } else {
         AxisAlignedBB var19 = func_179661_a(var5, var10, var11, var12);
         PlayerSelector$11 var20 = new PlayerSelector$11(var19);
         var6.addAll(var4.getPlayers(var1, Predicates.and(var15, var20)));
      }

      return var6;
   }

   private static List func_179658_a(List var0, Map var1, ICommandSender var2, Class var3, String var4, BlockPos var5) {
      int var6 = parseIntWithDefault(var1, "c", !var4.equals("a") && !var4.equals("e")?1:0);
      if(!var4.equals("p") && !var4.equals("a") && !var4.equals("e")) {
         if(var4.equals("r")) {
            Collections.shuffle(var0);
         }
      } else {
         Collections.sort(var0, new PlayerSelector$12(var5));
      }

      Entity var7 = var2.getCommandSenderEntity();
      if(var3.isAssignableFrom(var7.getClass()) && var6 == 1 && var0.contains(var7) && !"r".equals(var4)) {
         var0 = Lists.newArrayList(new Entity[]{var7});
      }

      Collections.reverse(var0);
      var0 = var0.subList(0, Math.min(Math.abs(var6), var0.size()));
      return var0;
   }

   private static AxisAlignedBB func_179661_a(BlockPos var0, int var1, int var2, int var3) {
      boolean var4 = true;
      boolean var5 = true;
      boolean var6 = true;
      int var7 = var0.getX() + var1;
      int var8 = var0.getY() + var2;
      int var9 = var0.getZ() + var3;
      int var10 = var0.getX() + 0 + 1;
      int var11 = var0.getY() + 0 + 1;
      int var12 = var0.getZ() + 0 + 1;
      return new AxisAlignedBB((double)var7, (double)var8, (double)var9, (double)var10, (double)var11, (double)var12);
   }

   public static int func_179650_a(int var0) {
      var0 = var0 % 360;
      if(var0 >= 160) {
         var0 -= 360;
      }

      var0 = var0 + 360;
      return var0;
   }

   private static BlockPos func_179664_b(Map var0, BlockPos var1) {
      return new BlockPos(parseIntWithDefault(var0, "x", var1.getX()), parseIntWithDefault(var0, "y", var1.getY()), parseIntWithDefault(var0, "z", var1.getZ()));
   }

   private static boolean func_179665_h(Map var0) {
      for(String var2 : WORLD_BINDING_ARGS) {
         if(var0.containsKey(var2)) {
            return true;
         }
      }

      return false;
   }

   private static int parseIntWithDefault(Map var0, String var1, int var2) {
      return var0.containsKey(var1)?MathHelper.parseIntWithDefault((String)var0.get(var1), var2):var2;
   }

   private static String func_179651_b(Map var0, String var1) {
      return (String)var0.get(var1);
   }

   public static Map func_96560_a(Map var0) {
      HashMap var1 = Maps.newHashMap();

      for(String var3 : var0.keySet()) {
         if(var3.startsWith("score_") && var3.length() > "score_".length()) {
            var1.put(var3.substring("score_".length()), Integer.valueOf(MathHelper.parseIntWithDefault((String)var0.get(var3), 1)));
         }
      }

      return var1;
   }

   public static boolean matchesMultiplePlayers(String var0) {
      Matcher var1 = tokenPattern.matcher(var0);
      if(!var1.matches()) {
         return false;
      } else {
         Map var2 = getArgumentMap(var1.group(2));
         String var3 = var1.group(1);
         int var4 = !"a".equals(var3) && !"e".equals(var3)?1:0;
         return parseIntWithDefault(var2, "c", var4) != 1;
      }
   }

   public static boolean hasArguments(String var0) {
      return tokenPattern.matcher(var0).matches();
   }

   private static Map getArgumentMap(String var0) {
      HashMap var1 = Maps.newHashMap();
      int var2 = 0;
      int var3 = -1;

      for(Matcher var4 = intListPattern.matcher(var0); var4.find(); var3 = var4.end()) {
         String var5 = null;
         switch(var2++) {
         case 0:
            var5 = "x";
            break;
         case 1:
            var5 = "y";
            break;
         case 2:
            var5 = "z";
            break;
         case 3:
            var5 = "r";
         }

         if(!var4.group(1).isEmpty()) {
            var1.put(var5, var4.group(1));
         }
      }

      if(var3 < var0.length()) {
         Matcher var6 = keyValueListPattern.matcher(var3 == -1?var0:var0.substring(var3));

         while(var6.find()) {
            var1.put(var6.group(1), var6.group(2));
         }
      }

      return var1;
   }
}
