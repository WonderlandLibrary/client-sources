package net.minecraft.command;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public class PlayerSelector {
   private static final String __OBFID = "CL_00000086";
   private static final Pattern intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
   private static final Pattern keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
   private static final Pattern tokenPattern = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
   private static final Set field_179666_d = Sets.newHashSet(new String[]{"x", "y", "z", "dx", "dy", "dz", "rm", "r"});

   private static List func_179658_a(List var0, Map var1, ICommandSender var2, Class var3, String var4, BlockPos var5) {
      int var6 = func_179653_a(var1, "c", !var4.equals("a") && !var4.equals("e") ? 1 : 0);
      if (!var4.equals("p") && !var4.equals("a") && !var4.equals("e")) {
         if (var4.equals("r")) {
            Collections.shuffle((List)var0);
         }
      } else if (var5 != null) {
         Collections.sort((List)var0, new Comparator(var5) {
            private static final String __OBFID = "CL_00002359";
            private final BlockPos val$p_179658_5_;

            {
               this.val$p_179658_5_ = var1;
            }

            public int func_179611_a(Entity var1, Entity var2) {
               return ComparisonChain.start().compare(var1.getDistanceSq(this.val$p_179658_5_), var2.getDistanceSq(this.val$p_179658_5_)).result();
            }

            public int compare(Object var1, Object var2) {
               return this.func_179611_a((Entity)var1, (Entity)var2);
            }
         });
      }

      Entity var7 = var2.getCommandSenderEntity();
      if (var7 != null && var3.isAssignableFrom(var7.getClass()) && var6 == 1 && ((List)var0).contains(var7) && !"r".equals(var4)) {
         var0 = Lists.newArrayList(new Entity[]{var7});
      }

      if (var6 != 0) {
         if (var6 < 0) {
            Collections.reverse((List)var0);
         }

         var0 = ((List)var0).subList(0, Math.min(Math.abs(var6), ((List)var0).size()));
      }

      return (List)var0;
   }

   private static AxisAlignedBB func_179661_a(BlockPos var0, int var1, int var2, int var3) {
      boolean var4 = var1 < 0;
      boolean var5 = var2 < 0;
      boolean var6 = var3 < 0;
      int var7 = var0.getX() + (var4 ? var1 : 0);
      int var8 = var0.getY() + (var5 ? var2 : 0);
      int var9 = var0.getZ() + (var6 ? var3 : 0);
      int var10 = var0.getX() + (var4 ? 0 : var1) + 1;
      int var11 = var0.getY() + (var5 ? 0 : var2) + 1;
      int var12 = var0.getZ() + (var6 ? 0 : var3) + 1;
      return new AxisAlignedBB((double)var7, (double)var8, (double)var9, (double)var10, (double)var11, (double)var12);
   }

   private static List func_179663_a(Map var0, String var1) {
      ArrayList var2 = Lists.newArrayList();
      String var3 = func_179651_b(var0, "type");
      boolean var4 = var3 != null && var3.startsWith("!");
      if (var4) {
         var3 = var3.substring(1);
      }

      boolean var5 = !var1.equals("e");
      boolean var6 = var1.equals("r") && var3 != null;
      if ((var3 == null || !var1.equals("e")) && !var6) {
         if (var5) {
            var2.add(new Predicate() {
               private static final String __OBFID = "CL_00002358";

               public boolean apply(Object var1) {
                  return this.func_179624_a((Entity)var1);
               }

               public boolean func_179624_a(Entity var1) {
                  return var1 instanceof EntityPlayer;
               }
            });
         }
      } else {
         var2.add(new Predicate(var3, var4) {
            private static final String __OBFID = "CL_00002362";
            private final boolean val$var4;
            private final String val$var3_f;

            public boolean apply(Object var1) {
               return this.func_179613_a((Entity)var1);
            }

            {
               this.val$var3_f = var1;
               this.val$var4 = var2;
            }

            public boolean func_179613_a(Entity var1) {
               return EntityList.func_180123_a(var1, this.val$var3_f) ^ this.val$var4;
            }
         });
      }

      return var2;
   }

   public static boolean hasArguments(String var0) {
      return tokenPattern.matcher(var0).matches();
   }

   private static List func_179647_f(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      String var2 = func_179651_b(var0, "name");
      boolean var3 = var2 != null && var2.startsWith("!");
      if (var3) {
         var2 = var2.substring(1);
      }

      if (var2 != null) {
         var1.add(new Predicate(var2, var3) {
            private static final String __OBFID = "CL_00002353";
            private final boolean val$var3;
            private final String val$var2_f;

            public boolean func_179600_a(Entity var1) {
               return var1.getName().equals(this.val$var2_f) ^ this.val$var3;
            }

            public boolean apply(Object var1) {
               return this.func_179600_a((Entity)var1);
            }

            {
               this.val$var2_f = var1;
               this.val$var3 = var2;
            }
         });
      }

      return var1;
   }

   private static String func_179651_b(Map var0, String var1) {
      return (String)var0.get(var1);
   }

   private static List func_179662_g(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      int var2;
      int var3;
      if (var0.containsKey("rym") || var0.containsKey("ry")) {
         var2 = func_179650_a(func_179653_a(var0, "rym", 0));
         var3 = func_179650_a(func_179653_a(var0, "ry", 359));
         var1.add(new Predicate(var2, var3) {
            private static final String __OBFID = "CL_00002351";
            private final int val$var3;
            private final int val$var2;

            public boolean apply(Object var1) {
               return this.func_179591_a((Entity)var1);
            }

            public boolean func_179591_a(Entity var1) {
               int var2 = PlayerSelector.func_179650_a((int)Math.floor((double)var1.rotationYaw));
               return this.val$var2 > this.val$var3 ? var2 >= this.val$var2 || var2 <= this.val$var3 : var2 >= this.val$var2 && var2 <= this.val$var3;
            }

            {
               this.val$var2 = var1;
               this.val$var3 = var2;
            }
         });
      }

      if (var0.containsKey("rxm") || var0.containsKey("rx")) {
         var2 = func_179650_a(func_179653_a(var0, "rxm", 0));
         var3 = func_179650_a(func_179653_a(var0, "rx", 359));
         var1.add(new Predicate(var2, var3) {
            private final int val$var2;
            private final int val$var3;
            private static final String __OBFID = "CL_00002361";

            public boolean apply(Object var1) {
               return this.func_179616_a((Entity)var1);
            }

            {
               this.val$var2 = var1;
               this.val$var3 = var2;
            }

            public boolean func_179616_a(Entity var1) {
               int var2 = PlayerSelector.func_179650_a((int)Math.floor((double)var1.rotationPitch));
               return this.val$var2 > this.val$var3 ? var2 >= this.val$var2 || var2 <= this.val$var3 : var2 >= this.val$var2 && var2 <= this.val$var3;
            }
         });
      }

      return var1;
   }

   private static boolean func_179655_b(ICommandSender var0, Map var1) {
      String var2 = func_179651_b(var1, "type");
      var2 = var2 != null && var2.startsWith("!") ? var2.substring(1) : var2;
      if (var2 != null && !EntityList.func_180125_b(var2)) {
         ChatComponentTranslation var3 = new ChatComponentTranslation("commands.generic.entity.invalidType", new Object[]{var2});
         var3.getChatStyle().setColor(EnumChatFormatting.RED);
         var0.addChatMessage(var3);
         return false;
      } else {
         return true;
      }
   }

   private static List func_179649_c(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      int var2 = func_179653_a(var0, "m", WorldSettings.GameType.NOT_SET.getID());
      if (var2 != WorldSettings.GameType.NOT_SET.getID()) {
         var1.add(new Predicate(var2) {
            private static final String __OBFID = "CL_00002356";
            private final int val$var2;

            public boolean func_179619_a(Entity var1) {
               if (!(var1 instanceof EntityPlayerMP)) {
                  return false;
               } else {
                  EntityPlayerMP var2 = (EntityPlayerMP)var1;
                  return var2.theItemInWorldManager.getGameType().getID() == this.val$var2;
               }
            }

            public boolean apply(Object var1) {
               return this.func_179619_a((Entity)var1);
            }

            {
               this.val$var2 = var1;
            }
         });
      }

      return var1;
   }

   private static int func_179653_a(Map var0, String var1, int var2) {
      return var0.containsKey(var1) ? MathHelper.parseIntWithDefault((String)var0.get(var1), var2) : var2;
   }

   public static Entity func_179652_a(ICommandSender var0, String var1, Class var2) {
      List var3 = func_179656_b(var0, var1, var2);
      return var3.size() == 1 ? (Entity)var3.get(0) : null;
   }

   private static boolean func_179665_h(Map var0) {
      Iterator var1 = field_179666_d.iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         if (var0.containsKey(var2)) {
            return true;
         }
      }

      return false;
   }

   public static List func_179656_b(ICommandSender var0, String var1, Class var2) {
      Matcher var3 = tokenPattern.matcher(var1);
      if (var3.matches() && var0.canCommandSenderUseCommand(1, "@")) {
         Map var4 = getArgumentMap(var3.group(2));
         if (!func_179655_b(var0, var4)) {
            return Collections.emptyList();
         } else {
            String var5 = var3.group(1);
            BlockPos var6 = func_179664_b(var4, var0.getPosition());
            List var7 = func_179654_a(var0, var4);
            ArrayList var8 = Lists.newArrayList();
            Iterator var9 = var7.iterator();

            while(var9.hasNext()) {
               World var10 = (World)var9.next();
               if (var10 != null) {
                  ArrayList var11 = Lists.newArrayList();
                  var11.addAll(func_179663_a(var4, var5));
                  var11.addAll(func_179648_b(var4));
                  var11.addAll(func_179649_c(var4));
                  var11.addAll(func_179659_d(var4));
                  var11.addAll(func_179657_e(var4));
                  var11.addAll(func_179647_f(var4));
                  var11.addAll(func_180698_a(var4, var6));
                  var11.addAll(func_179662_g(var4));
                  var8.addAll(func_179660_a(var4, var2, var11, var5, var10, var6));
               }
            }

            return func_179658_a(var8, var4, var0, var2, var5, var6);
         }
      } else {
         return Collections.emptyList();
      }
   }

   private static List func_179648_b(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      int var2 = func_179653_a(var0, "lm", -1);
      int var3 = func_179653_a(var0, "l", -1);
      if (var2 > -1 || var3 > -1) {
         var1.add(new Predicate(var2, var3) {
            private static final String __OBFID = "CL_00002357";
            private final int val$var3;
            private final int val$var2;

            public boolean func_179625_a(Entity var1) {
               if (!(var1 instanceof EntityPlayerMP)) {
                  return false;
               } else {
                  EntityPlayerMP var2 = (EntityPlayerMP)var1;
                  return (this.val$var2 <= -1 || var2.experienceLevel >= this.val$var2) && (this.val$var3 <= -1 || var2.experienceLevel <= this.val$var3);
               }
            }

            {
               this.val$var2 = var1;
               this.val$var3 = var2;
            }

            public boolean apply(Object var1) {
               return this.func_179625_a((Entity)var1);
            }
         });
      }

      return var1;
   }

   private static BlockPos func_179664_b(Map var0, BlockPos var1) {
      return new BlockPos(func_179653_a(var0, "x", var1.getX()), func_179653_a(var0, "y", var1.getY()), func_179653_a(var0, "z", var1.getZ()));
   }

   public static int func_179650_a(int var0) {
      var0 %= 360;
      if (var0 >= 160) {
         var0 -= 360;
      }

      if (var0 < 0) {
         var0 += 360;
      }

      return var0;
   }

   private static List func_179660_a(Map var0, Class var1, List var2, String var3, World var4, BlockPos var5) {
      ArrayList var6 = Lists.newArrayList();
      String var7 = func_179651_b(var0, "type");
      var7 = var7 != null && var7.startsWith("!") ? var7.substring(1) : var7;
      boolean var8 = !var3.equals("e");
      boolean var9 = var3.equals("r") && var7 != null;
      int var10 = func_179653_a(var0, "dx", 0);
      int var11 = func_179653_a(var0, "dy", 0);
      int var12 = func_179653_a(var0, "dz", 0);
      int var13 = func_179653_a(var0, "r", -1);
      Predicate var14 = Predicates.and(var2);
      Predicate var15 = Predicates.and(IEntitySelector.selectAnything, var14);
      if (var5 != null) {
         int var16 = var4.playerEntities.size();
         int var17 = var4.loadedEntityList.size();
         boolean var18 = var16 < var17 / 16;
         AxisAlignedBB var19;
         if (!var0.containsKey("dx") && !var0.containsKey("dy") && !var0.containsKey("dz")) {
            if (var13 >= 0) {
               var19 = new AxisAlignedBB((double)(var5.getX() - var13), (double)(var5.getY() - var13), (double)(var5.getZ() - var13), (double)(var5.getX() + var13 + 1), (double)(var5.getY() + var13 + 1), (double)(var5.getZ() + var13 + 1));
               if (var8 && var18 && !var9) {
                  var6.addAll(var4.func_175661_b(var1, var15));
               } else {
                  var6.addAll(var4.func_175647_a(var1, var19, var15));
               }
            } else if (var3.equals("a")) {
               var6.addAll(var4.func_175661_b(var1, var14));
            } else if (var3.equals("p") || var3.equals("r") && !var9) {
               var6.addAll(var4.func_175661_b(var1, var15));
            } else {
               var6.addAll(var4.func_175644_a(var1, var15));
            }
         } else {
            var19 = func_179661_a(var5, var10, var11, var12);
            if (var8 && var18 && !var9) {
               Predicate var20 = new Predicate(var19) {
                  private static final String __OBFID = "CL_00002360";
                  private final AxisAlignedBB val$var19;

                  {
                     this.val$var19 = var1;
                  }

                  public boolean apply(Object var1) {
                     return this.func_179609_a((Entity)var1);
                  }

                  public boolean func_179609_a(Entity var1) {
                     return var1.posX >= this.val$var19.minX && var1.posY >= this.val$var19.minY && var1.posZ >= this.val$var19.minZ ? var1.posX < this.val$var19.maxX && var1.posY < this.val$var19.maxY && var1.posZ < this.val$var19.maxZ : false;
                  }
               };
               var6.addAll(var4.func_175661_b(var1, Predicates.and(var15, var20)));
            } else {
               var6.addAll(var4.func_175647_a(var1, var19, var15));
            }
         }
      } else if (var3.equals("a")) {
         var6.addAll(var4.func_175661_b(var1, var14));
      } else if (!var3.equals("p") && (!var3.equals("r") || var9)) {
         var6.addAll(var4.func_175644_a(var1, var15));
      } else {
         var6.addAll(var4.func_175661_b(var1, var15));
      }

      return var6;
   }

   private static List func_179654_a(ICommandSender var0, Map var1) {
      ArrayList var2 = Lists.newArrayList();
      if (func_179665_h(var1)) {
         var2.add(var0.getEntityWorld());
      } else {
         Collections.addAll(var2, MinecraftServer.getServer().worldServers);
      }

      return var2;
   }

   public static boolean matchesMultiplePlayers(String var0) {
      Matcher var1 = tokenPattern.matcher(var0);
      if (!var1.matches()) {
         return false;
      } else {
         Map var2 = getArgumentMap(var1.group(2));
         String var3 = var1.group(1);
         int var4 = !"a".equals(var3) && !"e".equals(var3) ? 1 : 0;
         return func_179653_a(var2, "c", var4) != 1;
      }
   }

   private static List func_179657_e(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      Map var2 = func_96560_a(var0);
      if (var2 != null && var2.size() > 0) {
         var1.add(new Predicate(var2) {
            private static final String __OBFID = "CL_00002354";
            private final Map val$var2;

            {
               this.val$var2 = var1;
            }

            public boolean apply(Object var1) {
               return this.func_179603_a((Entity)var1);
            }

            public boolean func_179603_a(Entity var1) {
               Scoreboard var2 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
               Iterator var3 = this.val$var2.entrySet().iterator();

               Entry var4;
               boolean var5;
               int var6;
               do {
                  if (!var3.hasNext()) {
                     return true;
                  }

                  var4 = (Entry)var3.next();
                  String var7 = (String)var4.getKey();
                  var5 = false;
                  if (var7.endsWith("_min") && var7.length() > 4) {
                     var5 = true;
                     var7 = var7.substring(0, var7.length() - 4);
                  }

                  ScoreObjective var8 = var2.getObjective(var7);
                  if (var8 == null) {
                     return false;
                  }

                  String var9 = var1 instanceof EntityPlayerMP ? var1.getName() : var1.getUniqueID().toString();
                  if (!var2.func_178819_b(var9, var8)) {
                     return false;
                  }

                  Score var10 = var2.getValueFromObjective(var9, var8);
                  var6 = var10.getScorePoints();
                  if (var6 < (Integer)var4.getValue() && var5) {
                     return false;
                  }
               } while(var6 <= (Integer)var4.getValue() || var5);

               return false;
            }
         });
      }

      return var1;
   }

   private static List func_180698_a(Map var0, BlockPos var1) {
      ArrayList var2 = Lists.newArrayList();
      int var3 = func_179653_a(var0, "rm", -1);
      int var4 = func_179653_a(var0, "r", -1);
      if (var1 != null && (var3 >= 0 || var4 >= 0)) {
         int var5 = var3 * var3;
         int var6 = var4 * var4;
         var2.add(new Predicate(var1, var3, var5, var4, var6) {
            private final int val$var4;
            private final BlockPos val$p_180698_1_;
            private final int val$var5;
            private final int val$var6;
            private final int val$var3;
            private static final String __OBFID = "CL_00002352";

            {
               this.val$p_180698_1_ = var1;
               this.val$var3 = var2;
               this.val$var5 = var3;
               this.val$var4 = var4;
               this.val$var6 = var5;
            }

            public boolean apply(Object var1) {
               return this.func_179594_a((Entity)var1);
            }

            public boolean func_179594_a(Entity var1) {
               int var2 = (int)var1.func_174831_c(this.val$p_180698_1_);
               return (this.val$var3 < 0 || var2 >= this.val$var5) && (this.val$var4 < 0 || var2 <= this.val$var6);
            }
         });
      }

      return var2;
   }

   private static List func_179659_d(Map var0) {
      ArrayList var1 = Lists.newArrayList();
      String var2 = func_179651_b(var0, "team");
      boolean var3 = var2 != null && var2.startsWith("!");
      if (var3) {
         var2 = var2.substring(1);
      }

      if (var2 != null) {
         var1.add(new Predicate(var2, var3) {
            private final String val$var2_f;
            private static final String __OBFID = "CL_00002355";
            private final boolean val$var3;

            {
               this.val$var2_f = var1;
               this.val$var3 = var2;
            }

            public boolean apply(Object var1) {
               return this.func_179621_a((Entity)var1);
            }

            public boolean func_179621_a(Entity var1) {
               if (!(var1 instanceof EntityLivingBase)) {
                  return false;
               } else {
                  EntityLivingBase var2 = (EntityLivingBase)var1;
                  Team var3 = var2.getTeam();
                  String var4 = var3 == null ? "" : var3.getRegisteredName();
                  return var4.equals(this.val$var2_f) ^ this.val$var3;
               }
            }
         });
      }

      return var1;
   }

   private static Map getArgumentMap(String var0) {
      HashMap var1 = Maps.newHashMap();
      if (var0 == null) {
         return var1;
      } else {
         int var2 = 0;
         int var3 = -1;

         Matcher var4;
         for(var4 = intListPattern.matcher(var0); var4.find(); var3 = var4.end()) {
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

            if (var5 != null && var4.group(1).length() > 0) {
               var1.put(var5, var4.group(1));
            }
         }

         if (var3 < var0.length()) {
            var4 = keyValueListPattern.matcher(var3 == -1 ? var0 : var0.substring(var3));

            while(var4.find()) {
               var1.put(var4.group(1), var4.group(2));
            }
         }

         return var1;
      }
   }

   public static EntityPlayerMP matchOnePlayer(ICommandSender var0, String var1) {
      return (EntityPlayerMP)func_179652_a(var0, var1, EntityPlayerMP.class);
   }

   public static IChatComponent func_150869_b(ICommandSender var0, String var1) {
      List var2 = func_179656_b(var0, var1, Entity.class);
      if (var2.isEmpty()) {
         return null;
      } else {
         ArrayList var3 = Lists.newArrayList();
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            Entity var5 = (Entity)var4.next();
            var3.add(var5.getDisplayName());
         }

         return CommandBase.join(var3);
      }
   }

   public static Map func_96560_a(Map var0) {
      HashMap var1 = Maps.newHashMap();
      Iterator var2 = var0.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         if (var3.startsWith("score_") && var3.length() > "score_".length()) {
            var1.put(var3.substring("score_".length()), MathHelper.parseIntWithDefault((String)var0.get(var3), 1));
         }
      }

      return var1;
   }
}
