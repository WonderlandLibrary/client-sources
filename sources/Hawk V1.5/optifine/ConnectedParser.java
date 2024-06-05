package optifine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.BiomeGenBase;

public class ConnectedParser {
   private static final MatchBlock[] NO_MATCH_BLOCKS = new MatchBlock[0];
   private String context = null;

   public MatchBlock[] parseMatchBlocks(String var1) {
      if (var1 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();
         String[] var3 = Config.tokenize(var1, " ");

         for(int var4 = 0; var4 < var3.length; ++var4) {
            String var5 = var3[var4];
            MatchBlock[] var6 = this.parseMatchBlock(var5);
            if (var6 == null) {
               return NO_MATCH_BLOCKS;
            }

            var2.addAll(Arrays.asList(var6));
         }

         MatchBlock[] var7 = (MatchBlock[])var2.toArray(new MatchBlock[var2.size()]);
         return var7;
      }
   }

   public String parseName(String var1) {
      String var2 = var1;
      int var3 = var1.lastIndexOf(47);
      if (var3 >= 0) {
         var2 = var1.substring(var3 + 1);
      }

      int var4 = var2.lastIndexOf(46);
      if (var4 >= 0) {
         var2 = var2.substring(0, var4);
      }

      return var2;
   }

   public EnumFacing parseFace(String var1) {
      var1 = var1.toLowerCase();
      if (!var1.equals("bottom") && !var1.equals("down")) {
         if (!var1.equals("top") && !var1.equals("up")) {
            if (var1.equals("north")) {
               return EnumFacing.NORTH;
            } else if (var1.equals("south")) {
               return EnumFacing.SOUTH;
            } else if (var1.equals("east")) {
               return EnumFacing.EAST;
            } else if (var1.equals("west")) {
               return EnumFacing.WEST;
            } else {
               Config.warn(String.valueOf((new StringBuilder("Unknown face: ")).append(var1)));
               return null;
            }
         } else {
            return EnumFacing.UP;
         }
      } else {
         return EnumFacing.DOWN;
      }
   }

   public boolean isFullBlockName(String[] var1) {
      if (var1.length < 2) {
         return false;
      } else {
         String var2 = var1[1];
         return var2.length() < 1 ? false : (this.startsWithDigit(var2) ? false : !var2.contains("="));
      }
   }

   public BiomeGenBase findBiome(String var1) {
      var1 = var1.toLowerCase();
      if (var1.equals("nether")) {
         return BiomeGenBase.hell;
      } else {
         BiomeGenBase[] var2 = BiomeGenBase.getBiomeGenArray();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            BiomeGenBase var4 = var2[var3];
            if (var4 != null) {
               String var5 = var4.biomeName.replace(" ", "").toLowerCase();
               if (var5.equals(var1)) {
                  return var4;
               }
            }
         }

         return null;
      }
   }

   private RangeInt parseRangeInt(String var1) {
      if (var1 == null) {
         return null;
      } else if (var1.indexOf(45) >= 0) {
         String[] var5 = Config.tokenize(var1, "-");
         if (var5.length != 2) {
            this.warn(String.valueOf((new StringBuilder("Invalid range: ")).append(var1)));
            return null;
         } else {
            int var3 = Config.parseInt(var5[0], -1);
            int var4 = Config.parseInt(var5[1], -1);
            if (var3 >= 0 && var4 >= 0) {
               return new RangeInt(var3, var4);
            } else {
               this.warn(String.valueOf((new StringBuilder("Invalid range: ")).append(var1)));
               return null;
            }
         }
      } else {
         int var2 = Config.parseInt(var1, -1);
         if (var2 < 0) {
            this.warn(String.valueOf((new StringBuilder("Invalid integer: ")).append(var1)));
            return null;
         } else {
            return new RangeInt(var2, var2);
         }
      }
   }

   public int[] parseBlockMetadatas(Block var1, String[] var2) {
      if (var2.length <= 0) {
         return null;
      } else {
         String var3 = var2[0];
         if (this.startsWithDigit(var3)) {
            int[] var19 = this.parseIntList(var3);
            return var19;
         } else {
            IBlockState var4 = var1.getDefaultState();
            Collection var5 = var4.getPropertyNames();
            HashMap var6 = new HashMap();

            for(int var7 = 0; var7 < var2.length; ++var7) {
               String var8 = var2[var7];
               if (var8.length() > 0) {
                  String[] var9 = Config.tokenize(var8, "=");
                  if (var9.length != 2) {
                     this.warn(String.valueOf((new StringBuilder("Invalid block property: ")).append(var8)));
                     return null;
                  }

                  String var10 = var9[0];
                  String var11 = var9[1];
                  IProperty var12 = ConnectedProperties.getProperty(var10, var5);
                  if (var12 == null) {
                     this.warn(String.valueOf((new StringBuilder("Property not found: ")).append(var10).append(", block: ").append(var1)));
                     return null;
                  }

                  Object var13 = (List)var6.get(var10);
                  if (var13 == null) {
                     var13 = new ArrayList();
                     var6.put(var12, var13);
                  }

                  String[] var14 = Config.tokenize(var11, ",");

                  for(int var15 = 0; var15 < var14.length; ++var15) {
                     String var16 = var14[var15];
                     Comparable var17 = parsePropertyValue(var12, var16);
                     if (var17 == null) {
                        this.warn(String.valueOf((new StringBuilder("Property value not found: ")).append(var16).append(", property: ").append(var10).append(", block: ").append(var1)));
                        return null;
                     }

                     ((List)var13).add(var17);
                  }
               }
            }

            if (var6.isEmpty()) {
               return null;
            } else {
               ArrayList var20 = new ArrayList();

               int var21;
               for(int var22 = 0; var22 < 16; ++var22) {
                  var21 = var22;

                  try {
                     IBlockState var24 = this.getStateFromMeta(var1, var21);
                     if (this.matchState(var24, var6)) {
                        var20.add(var21);
                     }
                  } catch (IllegalArgumentException var18) {
                  }
               }

               if (var20.size() == 16) {
                  return null;
               } else {
                  int[] var23 = new int[var20.size()];

                  for(var21 = 0; var21 < var23.length; ++var21) {
                     var23[var21] = (Integer)var20.get(var21);
                  }

                  return var23;
               }
            }
         }
      }
   }

   public int parseInt(String var1) {
      if (var1 == null) {
         return -1;
      } else {
         int var2 = Config.parseInt(var1, -1);
         if (var2 < 0) {
            this.warn(String.valueOf((new StringBuilder("Invalid number: ")).append(var1)));
         }

         return var2;
      }
   }

   public Block[] parseBlockPart(String var1, String var2) {
      if (!this.startsWithDigit(var2)) {
         String var8 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(":").append(var2));
         Block var9 = Block.getBlockFromName(var8);
         if (var9 == null) {
            this.warn(String.valueOf((new StringBuilder("Block not found for name: ")).append(var8)));
            return null;
         } else {
            Block[] var10 = new Block[]{var9};
            return var10;
         }
      } else {
         int[] var3 = this.parseIntList(var2);
         if (var3 == null) {
            return null;
         } else {
            Block[] var4 = new Block[var3.length];

            for(int var5 = 0; var5 < var3.length; ++var5) {
               int var6 = var3[var5];
               Block var7 = Block.getBlockById(var6);
               if (var7 == null) {
                  this.warn(String.valueOf((new StringBuilder("Block not found for id: ")).append(var6)));
                  return null;
               }

               var4[var5] = var7;
            }

            return var4;
         }
      }
   }

   public static Comparable getPropertyValue(String var0, Collection var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Comparable var3 = (Comparable)var2.next();
         if (String.valueOf(var3).equals(var0)) {
            return var3;
         }
      }

      return null;
   }

   public static int parseColor(String var0, int var1) {
      if (var0 == null) {
         return var1;
      } else {
         var0 = var0.trim();

         try {
            int var2 = Integer.parseInt(var0, 16) & 16777215;
            return var2;
         } catch (NumberFormatException var3) {
            return var1;
         }
      }
   }

   public void dbg(String var1) {
      Config.dbg(String.valueOf((new StringBuilder()).append(this.context).append(": ").append(var1)));
   }

   public void warn(String var1) {
      Config.warn(String.valueOf((new StringBuilder()).append(this.context).append(": ").append(var1)));
   }

   public static Comparable parsePropertyValue(IProperty var0, String var1) {
      Class var2 = var0.getValueClass();
      Comparable var3 = parseValue(var1, var2);
      if (var3 == null) {
         Collection var4 = var0.getAllowedValues();
         var3 = getPropertyValue(var1, var4);
      }

      return var3;
   }

   public boolean startsWithDigit(String var1) {
      if (var1 == null) {
         return false;
      } else if (var1.length() < 1) {
         return false;
      } else {
         char var2 = var1.charAt(0);
         return Character.isDigit(var2);
      }
   }

   public ConnectedParser(String var1) {
      this.context = var1;
   }

   public MatchBlock[] parseMatchBlock(String var1) {
      if (var1 == null) {
         return null;
      } else {
         var1 = var1.trim();
         if (var1.length() <= 0) {
            return null;
         } else {
            String[] var2 = Config.tokenize(var1, ":");
            String var3 = "minecraft";
            boolean var4 = false;
            byte var5;
            if (var2.length > 1 && this.isFullBlockName(var2)) {
               var3 = var2[0];
               var5 = 1;
            } else {
               var3 = "minecraft";
               var5 = 0;
            }

            String var6 = var2[var5];
            String[] var7 = (String[])Arrays.copyOfRange(var2, var5 + 1, var2.length);
            Block[] var8 = this.parseBlockPart(var3, var6);
            if (var8 == null) {
               return null;
            } else {
               MatchBlock[] var9 = new MatchBlock[var8.length];

               for(int var10 = 0; var10 < var8.length; ++var10) {
                  Block var11 = var8[var10];
                  int var12 = Block.getIdFromBlock(var11);
                  int[] var13 = null;
                  if (var7.length > 0) {
                     var13 = this.parseBlockMetadatas(var11, var7);
                     if (var13 == null) {
                        return null;
                     }
                  }

                  MatchBlock var14 = new MatchBlock(var12, var13);
                  var9[var10] = var14;
               }

               return var9;
            }
         }
      }
   }

   private IBlockState getStateFromMeta(Block var1, int var2) {
      try {
         IBlockState var3 = var1.getStateFromMeta(var2);
         if (var1 == Blocks.double_plant && var2 > 7) {
            IBlockState var4 = var1.getStateFromMeta(var2 & 7);
            var3 = var3.withProperty(BlockDoublePlant.VARIANT_PROP, var4.getValue(BlockDoublePlant.VARIANT_PROP));
         }

         return var3;
      } catch (IllegalArgumentException var5) {
         return var1.getDefaultState();
      }
   }

   public static Comparable parseValue(String var0, Class var1) {
      return (Comparable)(var1 == String.class ? var0 : (var1 == Boolean.class ? Boolean.valueOf(var0) : var1 == Float.class ? (double)Float.valueOf(var0) : (var1 == Double.class ? Double.valueOf(var0) : (double)(var1 == Integer.class ? (long)Integer.valueOf(var0) : var1 == Long.class ? Long.valueOf(var0) : null))));
   }

   public RangeListInt parseRangeListInt(String var1) {
      if (var1 == null) {
         return null;
      } else {
         RangeListInt var2 = new RangeListInt();
         String[] var3 = Config.tokenize(var1, " ,");

         for(int var4 = 0; var4 < var3.length; ++var4) {
            String var5 = var3[var4];
            RangeInt var6 = this.parseRangeInt(var5);
            if (var6 == null) {
               return null;
            }

            var2.addRange(var6);
         }

         return var2;
      }
   }

   public boolean[] parseFaces(String var1, boolean[] var2) {
      if (var1 == null) {
         return var2;
      } else {
         EnumSet var3 = EnumSet.allOf(EnumFacing.class);
         String[] var4 = Config.tokenize(var1, " ,");

         for(int var5 = 0; var5 < var4.length; ++var5) {
            String var6 = var4[var5];
            if (var6.equals("sides")) {
               var3.add(EnumFacing.NORTH);
               var3.add(EnumFacing.SOUTH);
               var3.add(EnumFacing.WEST);
               var3.add(EnumFacing.EAST);
            } else if (var6.equals("all")) {
               var3.addAll(Arrays.asList(EnumFacing.VALUES));
            } else {
               EnumFacing var7 = this.parseFace(var6);
               if (var7 != null) {
                  var3.add(var7);
               }
            }
         }

         boolean[] var8 = new boolean[EnumFacing.VALUES.length];

         for(int var9 = 0; var9 < var8.length; ++var9) {
            var8[var9] = var3.contains(EnumFacing.VALUES[var9]);
         }

         return var8;
      }
   }

   public boolean matchState(IBlockState var1, Map<IProperty, List<Comparable>> var2) {
      Set var3 = var2.keySet();
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         IProperty var7 = (IProperty)var4.next();
         List var5 = (List)var2.get(var7);
         Comparable var6 = var1.getValue(var7);
         if (var6 == null) {
            return false;
         }

         if (!var5.contains(var6)) {
            return false;
         }
      }

      return true;
   }

   public String parseBasePath(String var1) {
      int var2 = var1.lastIndexOf(47);
      return var2 < 0 ? "" : var1.substring(0, var2);
   }

   public BiomeGenBase[] parseBiomes(String var1) {
      if (var1 == null) {
         return null;
      } else {
         String[] var2 = Config.tokenize(var1, " ");
         ArrayList var3 = new ArrayList();

         for(int var4 = 0; var4 < var2.length; ++var4) {
            String var5 = var2[var4];
            BiomeGenBase var6 = this.findBiome(var5);
            if (var6 == null) {
               this.warn(String.valueOf((new StringBuilder("Biome not found: ")).append(var5)));
            } else {
               var3.add(var6);
            }
         }

         BiomeGenBase[] var7 = (BiomeGenBase[])var3.toArray(new BiomeGenBase[var3.size()]);
         return var7;
      }
   }

   public int parseInt(String var1, int var2) {
      if (var1 == null) {
         return var2;
      } else {
         int var3 = Config.parseInt(var1, -1);
         if (var3 < 0) {
            this.warn(String.valueOf((new StringBuilder("Invalid number: ")).append(var1)));
            return var2;
         } else {
            return var3;
         }
      }
   }

   public int[] parseIntList(String var1) {
      if (var1 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();
         String[] var3 = Config.tokenize(var1, " ,");

         for(int var4 = 0; var4 < var3.length; ++var4) {
            String var5 = var3[var4];
            if (var5.contains("-")) {
               String[] var12 = Config.tokenize(var5, "-");
               if (var12.length != 2) {
                  this.warn(String.valueOf((new StringBuilder("Invalid interval: ")).append(var5).append(", when parsing: ").append(var1)));
               } else {
                  int var7 = Config.parseInt(var12[0], -1);
                  int var8 = Config.parseInt(var12[1], -1);
                  if (var7 >= 0 && var8 >= 0 && var7 <= var8) {
                     for(int var9 = var7; var9 <= var8; ++var9) {
                        var2.add(var9);
                     }
                  } else {
                     this.warn(String.valueOf((new StringBuilder("Invalid interval: ")).append(var5).append(", when parsing: ").append(var1)));
                  }
               }
            } else {
               int var6 = Config.parseInt(var5, -1);
               if (var6 < 0) {
                  this.warn(String.valueOf((new StringBuilder("Invalid number: ")).append(var5).append(", when parsing: ").append(var1)));
               } else {
                  var2.add(var6);
               }
            }
         }

         int[] var10 = new int[var2.size()];

         for(int var11 = 0; var11 < var10.length; ++var11) {
            var10[var11] = (Integer)var2.get(var11);
         }

         return var10;
      }
   }

   public static boolean parseBoolean(String var0) {
      return var0 == null ? false : var0.toLowerCase().equals("true");
   }
}
