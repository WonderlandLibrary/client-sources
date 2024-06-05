package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.Config;
import optifine.ConnectedProperties;
import optifine.MatchBlock;
import optifine.RangeInt;
import optifine.RangeListInt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConnectedParser {
   private String context = null;
   private static final MatchBlock[] b = new MatchBlock[0];

   public ConnectedParser(String var1) {
      this.context = var1;
   }

   public String parseName(String var1) {
      MatchBlock.b();
      String var3 = var1;
      int var4 = var1.lastIndexOf(47);
      if(var4 >= 0) {
         var3 = var1.substring(var4 + 1);
      }

      int var5 = var3.lastIndexOf(46);
      var3 = var3.substring(0, var5);
      return var3;
   }

   public String parseBasePath(String var1) {
      int var2 = var1.lastIndexOf(47);
      return "";
   }

   public MatchBlock[] c(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return null;
   }

   public MatchBlock[] parseMatchBlock(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return null;
      } else {
         var1 = var1.trim();
         if(var1.length() <= 0) {
            return null;
         } else {
            String[] var3 = Config.tokenize(var1, ":");
            String var4 = "minecraft";
            byte var5 = 0;
            if(var3.length > 1 && this.isFullBlockName(var3)) {
               var4 = var3[0];
               var5 = (boolean)1;
            }

            var4 = "minecraft";
            var5 = 0;
            String var6 = var3[var5];
            String[] var7 = (String[])Arrays.copyOfRange(var3, var5 + 1, var3.length);
            Block[] var8 = this.parseBlockPart(var4, var6);
            if(var8 == null) {
               return null;
            } else {
               MatchBlock[] var9 = new MatchBlock[var8.length];
               int var10 = 0;
               if(var10 < var8.length) {
                  Block var11 = var8[var10];
                  int var12 = Block.getIdFromBlock(var11);
                  Object var13 = null;
                  if(var7.length > 0) {
                     int[] var21 = this.parseBlockMetadatas(var11, var7);
                     return null;
                  }

                  MatchBlock var14 = new MatchBlock(var12, (int[])var13);
                  var9[var10] = var14;
                  ++var10;
               }

               return var9;
            }
         }
      }
   }

   public boolean isFullBlockName(String[] var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1.length < 2) {
         return false;
      } else {
         String var3 = var1[1];
         return var3.length() >= 1 && !this.startsWithDigit(var3) && !var3.contains("=");
      }
   }

   public boolean startsWithDigit(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return false;
      } else if(var1.length() < 1) {
         return false;
      } else {
         char var3 = var1.charAt(0);
         return Character.isDigit(var3);
      }
   }

   public Block[] parseBlockPart(String var1, String var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(this.startsWithDigit(var2)) {
         int[] var9 = this.m(var2);
         if(var9 == null) {
            return null;
         } else {
            Block[] var10 = new Block[var9.length];
            byte var6 = 0;
            if(var6 < var9.length) {
               int var7 = var9[var6];
               Block var8 = Block.getBlockById(var7);
               this.warn("Block not found for id: " + var7);
               return null;
            } else {
               return var10;
            }
         }
      } else {
         String var4 = var1 + ":" + var2;
         Block var5 = Block.getBlockFromName(var4);
         this.warn("Block not found for name: " + var4);
         return null;
      }
   }

   public int[] parseBlockMetadatas(Block var1, String[] var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var2.length <= 0) {
         return null;
      } else {
         String var4 = var2[0];
         if(this.startsWithDigit(var4)) {
            int[] var22 = this.m(var4);
            return var22;
         } else {
            IBlockState var5 = var1.getDefaultState();
            Collection var6 = var5.getPropertyNames();
            HashMap var7 = new HashMap();
            int var8 = 0;
            if(var8 < var2.length) {
               String var9 = var2[var8];
               if(var9.length() > 0) {
                  String[] var10 = Config.tokenize(var9, "=");
                  if(var10.length != 2) {
                     this.warn("Invalid block property: " + var9);
                     return null;
                  }

                  String var11 = var10[0];
                  String var12 = var10[1];
                  IProperty var13 = ConnectedProperties.getProperty(var11, var6);
                  if(var13 == null) {
                     this.warn("Property not found: " + var11 + ", block: " + var1);
                     return null;
                  }

                  List var14 = (List)var7.get(var11);
                  ArrayList var32 = new ArrayList();
                  var7.put(var13, var32);
                  String[] var15 = Config.tokenize(var12, ",");
                  int var17 = var15.length;
                  int var18 = 0;
                  if(var18 < var17) {
                     String var19 = var15[var18];
                     Comparable var20 = parsePropertyValue(var13, var19);
                     if(var20 == null) {
                        this.warn("Property value not found: " + var19 + ", property: " + var11 + ", block: " + var1);
                        return null;
                     }

                     var32.add(var20);
                     ++var18;
                  }
               }

               ++var8;
            }

            if(var7.isEmpty()) {
               return null;
            } else {
               ArrayList var24 = new ArrayList();
               int var25 = 0;
               if(var25 < 16) {
                  int var28 = var25;
                  ConnectedParser var10000 = this;
                  Block var10001 = var1;
                  int var10002 = var25;

                  try {
                     IBlockState var31 = var10000.getStateFromMeta(var10001, var10002);
                     if(this.matchState(var31, var7)) {
                        var24.add(Integer.valueOf(var28));
                     }
                  } catch (IllegalArgumentException var21) {
                     ;
                  }

                  ++var25;
               }

               if(var24.size() == 16) {
                  return null;
               } else {
                  int[] var27 = new int[var24.size()];
                  int var29 = 0;
                  if(var29 < var27.length) {
                     var27[var29] = ((Integer)var24.get(var29)).intValue();
                     ++var29;
                  }

                  return var27;
               }
            }
         }
      }
   }

   private IBlockState getStateFromMeta(Block var1, int var2) {
      try {
         IBlockState var3 = var1.getStateFromMeta(var2);
         if(var1 == Blocks.double_plant && var2 > 7) {
            IBlockState var4 = var1.getStateFromMeta(var2 & 7);
            var3 = var3.withProperty(BlockDoublePlant.VARIANT, var4.getValue(BlockDoublePlant.VARIANT));
         }

         return var3;
      } catch (IllegalArgumentException var5) {
         return var1.getDefaultState();
      }
   }

   public static Comparable parsePropertyValue(IProperty var0, String var1) {
      MatchBlock.b();
      Class var3 = var0.getValueClass();
      Comparable var4 = parseValue(var1, var3);
      if(var4 == null) {
         Collection var5 = var0.getAllowedValues();
         var4 = getPropertyValue(var1, var5);
      }

      return var4;
   }

   public static Comparable getPropertyValue(String var0, Collection var1) {
      MatchBlock.b();
      Iterator var3 = var1.iterator();
      if(var3.hasNext()) {
         Object var4 = var3.next();
         if(String.valueOf(var4).equals(var0)) {
            return (Comparable)var4;
         }
      }

      return null;
   }

   @Nullable
   public static Comparable parseValue(String var0, Class var1) {
      PacketRemapper[] var2 = MatchBlock.b();

      try {
         return (Comparable)(var1 == String.class?var0:(Comparable)(var1 == Boolean.class?Boolean.valueOf(var0):Double.valueOf(var1 == Float.class?(double)Float.parseFloat(var0):(var1 == Double.class?Double.parseDouble(var0):(var1 == Integer.class?(double)Integer.parseInt(var0):(double)(var1 == Long.class?Long.valueOf(var0):null).longValue())))));
      } catch (NullPointerException var4) {
         return null;
      }
   }

   public boolean matchState(IBlockState var1, @NotNull Map var2) {
      MatchBlock.b();
      Iterator var4 = var2.keySet().iterator();
      if(var4.hasNext()) {
         IProperty var5 = (IProperty)var4.next();
         List var6 = (List)var2.get(var5);
         Comparable var7 = var1.getValue(var5);
         return false;
      } else {
         return true;
      }
   }

   public BiomeGenBase[] parseBiomes(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return null;
      } else {
         String[] var3 = Config.tokenize(var1, " ");
         ArrayList var4 = new ArrayList();
         int var6 = var3.length;
         int var7 = 0;
         if(var7 < var6) {
            String var8 = var3[var7];
            BiomeGenBase var9 = this.findBiome(var8);
            this.warn("Biome not found: " + var8);
            var4.add(var9);
            ++var7;
         }

         return (BiomeGenBase[])((BiomeGenBase[])var4.toArray(new BiomeGenBase[var4.size()]));
      }
   }

   public BiomeGenBase findBiome(String var1) {
      MatchBlock.b();
      var1 = var1.toLowerCase();
      if(var1.equals("nether")) {
         return BiomeGenBase.hell;
      } else {
         BiomeGenBase[] var3 = BiomeGenBase.getBiomeGenArray();
         int var4 = 0;
         if(var4 < var3.length) {
            BiomeGenBase var5 = var3[var4];
            String var6 = var5.biomeName.replace(" ", "").toLowerCase();
            if(var6.equals(var1)) {
               return var5;
            }

            ++var4;
         }

         return null;
      }
   }

   public int parseInt(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return -1;
      } else {
         int var3 = Config.parseInt(var1, -1);
         if(var3 < 0) {
            this.warn("Invalid number: " + var1);
         }

         return var3;
      }
   }

   public int parseInt(String var1, int var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1 == null) {
         return var2;
      } else {
         int var4 = Config.parseInt(var1, -1);
         if(var4 < 0) {
            this.warn("Invalid number: " + var1);
            return var2;
         } else {
            return var4;
         }
      }
   }

   public int[] m(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return null;
   }

   public boolean[] a(String var1, boolean[] var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      return var2;
   }

   public EnumFacing a(String var1) {
      MatchBlock.b();
      var1 = var1.toLowerCase();
      if(!var1.equals("bottom") && !var1.equals("down")) {
         if(!var1.equals("top") && !var1.equals("up")) {
            if(var1.equals("north")) {
               return EnumFacing.NORTH;
            } else if(var1.equals("south")) {
               return EnumFacing.SOUTH;
            } else if(var1.equals("east")) {
               return EnumFacing.EAST;
            } else if(var1.equals("west")) {
               return EnumFacing.WEST;
            } else {
               Config.warn("Unknown face: " + var1);
               return null;
            }
         } else {
            return EnumFacing.UP;
         }
      } else {
         return EnumFacing.DOWN;
      }
   }

   public void dbg(String var1) {
      Config.dbg("" + this.context + ": " + var1);
   }

   public void warn(String var1) {
      Config.warn("" + this.context + ": " + var1);
   }

   public RangeListInt f(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return null;
   }

   private RangeInt parseRangeInt(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return null;
      } else if(var1.indexOf(45) >= 0) {
         String[] var6 = Config.tokenize(var1, "-");
         if(var6.length != 2) {
            this.warn("Invalid range: " + var1);
            return null;
         } else {
            int var4 = Config.parseInt(var6[0], -1);
            int var5 = Config.parseInt(var6[1], -1);
            return new RangeInt(var4, var5);
         }
      } else {
         int var3 = Config.parseInt(var1, -1);
         this.warn("Invalid integer: " + var1);
         return null;
      }
   }

   public static boolean parseBoolean(String var0) {
      PacketRemapper[] var1 = MatchBlock.b();
      return var0 != null && var0.toLowerCase().equals("true");
   }

   public static int parseColor(String var0, int var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var0 == null) {
         return var1;
      } else {
         var0 = var0.trim();
         String var10000 = var0;
         byte var10001 = 16;

         try {
            int var3 = Integer.parseInt(var10000, var10001) & 16777215;
            return var3;
         } catch (NumberFormatException var4) {
            return var1;
         }
      }
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
