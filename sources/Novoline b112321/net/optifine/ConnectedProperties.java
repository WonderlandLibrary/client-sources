package net.optifine;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import net.acE;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.Config;
import net.optifine.ConnectedParser;
import net.optifine.MatchBlock;
import net.optifine.Matches;
import net.optifine.MathUtils;
import net.optifine.TextureUtils;

public class ConnectedProperties {
   public String name = null;
   public String basePath = null;
   public MatchBlock[] matchBlocks = null;
   public int[] metadatas = null;
   public String[] matchTiles = null;
   public int method = 0;
   public String[] tiles = null;
   public int connect = 0;
   public int faces = 63;
   public BiomeGenBase[] biomes = null;
   public int minHeight = 0;
   public int maxHeight = 1024;
   public int renderPass = 0;
   public boolean innerSeams = false;
   public int width = 0;
   public int height = 0;
   public int[] weights = null;
   public int symmetry = 1;
   public int[] sumWeights = null;
   public int sumAllWeights = 1;
   public TextureAtlasSprite[] matchTileIcons = null;
   public TextureAtlasSprite[] tileIcons = null;
   public static final int a = 0;
   public static final int d = 1;
   public static final int f = 2;
   public static final int O = 3;
   public static final int G = 4;
   public static final int METHOD_REPEAT = 5;
   public static final int N = 6;
   public static final int METHOD_FIXED = 7;
   public static final int o = 8;
   public static final int METHOD_VERTICAL_HORIZONTAL = 9;
   public static final int X = 0;
   public static final int T = 1;
   public static final int H = 2;
   public static final int y = 3;
   public static final int c = 128;
   public static final int w = 1;
   public static final int l = 2;
   public static final int j = 4;
   public static final int v = 8;
   public static final int FACE_WEST = 16;
   public static final int FACE_EAST = 32;
   public static final int FACE_SIDES = 60;
   public static final int FACE_ALL = 63;
   public static final int P = 128;
   public static final int p = 1;
   public static final int r = 2;
   public static final int b = 6;
   public static final int K = 128;

   public ConnectedProperties(Properties var1, String var2) {
      ConnectedParser var3 = new ConnectedParser("ConnectedTextures");
      this.name = var3.parseName(var2);
      this.basePath = var3.parseBasePath(var2);
      this.matchBlocks = var3.c(var1.getProperty("matchBlocks"));
      this.metadatas = var3.m(var1.getProperty("metadata"));
      this.matchTiles = this.parseMatchTiles(var1.getProperty("matchTiles"));
      this.method = parseMethod(var1.getProperty("method"));
      this.tiles = this.m(var1.getProperty("tiles"));
      this.connect = parseConnect(var1.getProperty("connect"));
      this.faces = parseFaces(var1.getProperty("faces"));
      this.biomes = var3.parseBiomes(var1.getProperty("biomes"));
      this.minHeight = var3.parseInt(var1.getProperty("minHeight"), -1);
      this.maxHeight = var3.parseInt(var1.getProperty("maxHeight"), 1024);
      this.renderPass = var3.parseInt(var1.getProperty("renderPass"));
      this.innerSeams = ConnectedParser.parseBoolean(var1.getProperty("innerSeams"));
      this.width = var3.parseInt(var1.getProperty("width"));
      this.height = var3.parseInt(var1.getProperty("height"));
      this.weights = var3.m(var1.getProperty("weights"));
      this.symmetry = parseSymmetry(var1.getProperty("symmetry"));
   }

   private String[] parseMatchTiles(String var1) {
      acE[] var2 = MatchBlock.b();
      if(var1 == null) {
         return null;
      } else {
         String[] var3 = Config.tokenize(var1, " ");
         int var4 = 0;
         if(var4 < var3.length) {
            String var5 = var3[var4];
            if(var5.endsWith(".png")) {
               var5 = var5.substring(0, var5.length() - 4);
            }

            var5 = TextureUtils.fixResourcePath(var5, this.basePath);
            var3[var4] = var5;
            ++var4;
         }

         return var3;
      }
   }

   private static String parseName(String var0) {
      String var2 = var0;
      MatchBlock.b();
      int var3 = var0.lastIndexOf(47);
      if(var3 >= 0) {
         var2 = var0.substring(var3 + 1);
      }

      int var4 = var2.lastIndexOf(46);
      var2 = var2.substring(0, var4);
      return var2;
   }

   private static String parseBasePath(String var0) {
      int var1 = var0.lastIndexOf(47);
      return "";
   }

   private String[] m(String var1) {
      acE[] var2 = MatchBlock.b();
      return null;
   }

   private static int parseSymmetry(String var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == null) {
         return 1;
      } else if(var0.equals("opposite")) {
         return 2;
      } else if(var0.equals("all")) {
         return 6;
      } else {
         Config.warn("Unknown symmetry: " + var0);
         return 1;
      }
   }

   private static int parseFaces(String var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == null) {
         return 63;
      } else {
         String[] var2 = Config.tokenize(var0, " ,");
         int var3 = 0;
         int var4 = 0;
         if(var4 < var2.length) {
            String var5 = var2[var4];
            int var6 = parseFace(var5);
            var3 |= var6;
            ++var4;
         }

         return var3;
      }
   }

   private static int parseFace(String var0) {
      MatchBlock.b();
      var0 = var0.toLowerCase();
      if(!var0.equals("bottom") && !var0.equals("down")) {
         if(!var0.equals("top") && !var0.equals("up")) {
            if(var0.equals("north")) {
               return 4;
            } else if(var0.equals("south")) {
               return 8;
            } else if(var0.equals("east")) {
               return 32;
            } else if(var0.equals("west")) {
               return 16;
            } else if(var0.equals("sides")) {
               return 60;
            } else if(var0.equals("all")) {
               return 63;
            } else {
               Config.warn("Unknown face: " + var0);
               return 128;
            }
         } else {
            return 2;
         }
      } else {
         return 1;
      }
   }

   private static int parseConnect(String var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == null) {
         return 0;
      } else if(var0.equals("block")) {
         return 1;
      } else if(var0.equals("tile")) {
         return 2;
      } else if(var0.equals("material")) {
         return 3;
      } else {
         Config.warn("Unknown connect: " + var0);
         return 128;
      }
   }

   public static IProperty getProperty(String var0, Collection var1) {
      MatchBlock.b();
      Iterator var3 = var1.iterator();
      if(var3.hasNext()) {
         Object var4 = var3.next();
         if(var0.equals(((IProperty)var4).getName())) {
            return (IProperty)var4;
         }
      }

      return null;
   }

   private static int parseMethod(String var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == null) {
         return 1;
      } else if(!var0.equals("ctm") && !var0.equals("glass")) {
         if(!var0.equals("horizontal") && !var0.equals("bookshelf")) {
            if(var0.equals("vertical")) {
               return 6;
            } else if(var0.equals("top")) {
               return 3;
            } else if(var0.equals("random")) {
               return 4;
            } else if(var0.equals("repeat")) {
               return 5;
            } else if(var0.equals("fixed")) {
               return 7;
            } else if(!var0.equals("horizontal+vertical") && !var0.equals("h+v")) {
               if(!var0.equals("vertical+horizontal") && !var0.equals("v+h")) {
                  Config.warn("Unknown method: " + var0);
                  return 0;
               } else {
                  return 9;
               }
            } else {
               return 8;
            }
         } else {
            return 2;
         }
      } else {
         return 1;
      }
   }

   public boolean isValid(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.name != null && this.name.length() > 0) {
         if(this.basePath == null) {
            Config.warn("No base path found: " + var1);
            return false;
         } else {
            if(this.matchBlocks == null) {
               this.matchBlocks = this.detectMatchBlocks();
            }

            if(this.matchTiles == null && this.matchBlocks == null) {
               this.matchTiles = this.detectMatchTiles();
            }

            if(this.matchBlocks == null && this.matchTiles == null) {
               Config.warn("No matchBlocks or matchTiles specified: " + var1);
               return false;
            } else if(this.method == 0) {
               Config.warn("No method: " + var1);
               return false;
            } else if(this.tiles != null && this.tiles.length > 0) {
               if(this.connect == 0) {
                  this.connect = this.detectConnect();
               }

               if(this.connect == 128) {
                  Config.warn("Invalid connect in: " + var1);
                  return false;
               } else if(this.renderPass > 0) {
                  Config.warn("Render pass not supported: " + this.renderPass);
                  return false;
               } else if((this.faces & 128) != 0) {
                  Config.warn("Invalid faces in: " + var1);
                  return false;
               } else if((this.symmetry & 128) != 0) {
                  Config.warn("Invalid symmetry in: " + var1);
                  return false;
               } else {
                  switch(this.method) {
                  case 1:
                     return this.isValidCtm(var1);
                  case 2:
                     return this.isValidHorizontal(var1);
                  case 3:
                     return this.isValidTop(var1);
                  case 4:
                     return this.isValidRandom(var1);
                  case 5:
                     return this.isValidRepeat(var1);
                  case 6:
                     return this.isValidVertical(var1);
                  case 7:
                     return this.isValidFixed(var1);
                  case 8:
                     return this.isValidHorizontalVertical(var1);
                  case 9:
                     return this.isValidVerticalHorizontal(var1);
                  default:
                     Config.warn("Unknown method: " + var1);
                     return false;
                  }
               }
            } else {
               Config.warn("No tiles specified: " + var1);
               return false;
            }
         }
      } else {
         Config.warn("No name found: " + var1);
         return false;
      }
   }

   private int detectConnect() {
      acE[] var1 = MatchBlock.b();
      return this.matchBlocks != null?1:(this.matchTiles != null?2:128);
   }

   private MatchBlock[] detectMatchBlocks() {
      MatchBlock.b();
      int[] var2 = this.detectMatchBlockIds();
      if(var2 == null) {
         return null;
      } else {
         MatchBlock[] var3 = new MatchBlock[var2.length];
         int var4 = 0;
         if(var4 < var3.length) {
            var3[var4] = new MatchBlock(var2[var4]);
            ++var4;
         }

         return var3;
      }
   }

   private int[] detectMatchBlockIds() {
      acE[] var1 = MatchBlock.b();
      if(!this.name.startsWith("block")) {
         return null;
      } else {
         int var2 = "block".length();
         int var3 = var2;
         if(var2 < this.name.length()) {
            char var4 = this.name.charAt(var2);
            if(var4 >= 48) {
               if(var4 > 57) {
                  ;
               }

               var3 = var2 + 1;
            }
         }

         if(var3 == var2) {
            return null;
         } else {
            String var6 = this.name.substring(var2, var3);
            int var5 = Config.parseInt(var6, -1);
            return var5 < 0?null:new int[]{var5};
         }
      }
   }

   private String[] detectMatchTiles() {
      TextureAtlasSprite var1 = getIcon(this.name);
      return null;
   }

   private static TextureAtlasSprite getIcon(String var0) {
      MatchBlock.b();
      TextureMap var2 = Minecraft.getInstance().getTextureMapBlocks();
      TextureAtlasSprite var3 = var2.getSpriteSafe(var0);
      if(var3 != null) {
         return var3;
      } else {
         var3 = var2.getSpriteSafe("blocks/" + var0);
         return var3;
      }
   }

   private boolean isValidCtm(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.tiles == null) {
         this.tiles = this.m("0-11 16-27 32-43 48-58");
      }

      if(this.tiles.length < 47) {
         Config.warn("Invalid tiles, must be at least 47: " + var1);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidHorizontal(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.tiles == null) {
         this.tiles = this.m("12-15");
      }

      if(this.tiles.length != 4) {
         Config.warn("Invalid tiles, must be exactly 4: " + var1);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidVertical(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.tiles == null) {
         Config.warn("No tiles defined for vertical: " + var1);
         return false;
      } else if(this.tiles.length != 4) {
         Config.warn("Invalid tiles, must be exactly 4: " + var1);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidHorizontalVertical(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.tiles == null) {
         Config.warn("No tiles defined for horizontal+vertical: " + var1);
         return false;
      } else if(this.tiles.length != 7) {
         Config.warn("Invalid tiles, must be exactly 7: " + var1);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidVerticalHorizontal(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.tiles == null) {
         Config.warn("No tiles defined for vertical+horizontal: " + var1);
         return false;
      } else if(this.tiles.length != 7) {
         Config.warn("Invalid tiles, must be exactly 7: " + var1);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidRandom(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.tiles != null && this.tiles.length > 0) {
         if(this.weights != null) {
            if(this.weights.length > this.tiles.length) {
               Config.warn("More weights defined than tiles, trimming weights: " + var1);
               int[] var3 = new int[this.tiles.length];
               System.arraycopy(this.weights, 0, var3, 0, var3.length);
               this.weights = var3;
            }

            if(this.weights.length < this.tiles.length) {
               Config.warn("Less weights defined than tiles, expanding weights: " + var1);
               int[] var6 = new int[this.tiles.length];
               System.arraycopy(this.weights, 0, var6, 0, this.weights.length);
               int var4 = MathUtils.getAverage(this.weights);
               int var5 = this.weights.length;
               if(var5 < var6.length) {
                  var6[var5] = var4;
                  ++var5;
               }

               this.weights = var6;
            }

            this.sumWeights = new int[this.weights.length];
            int var7 = 0;
            int var8 = 0;
            if(var8 < this.weights.length) {
               var7 += this.weights[var8];
               this.sumWeights[var8] = var7;
               ++var8;
            }

            this.sumAllWeights = var7;
            if(this.sumAllWeights <= 0) {
               Config.warn("Invalid sum of all weights: " + var7);
               this.sumAllWeights = 1;
            }
         }

         return true;
      } else {
         Config.warn("Tiles not defined: " + var1);
         return false;
      }
   }

   private boolean isValidRepeat(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.tiles == null) {
         Config.warn("Tiles not defined: " + var1);
         return false;
      } else if(this.width > 0 && this.width <= 16) {
         if(this.height > 0 && this.height <= 16) {
            if(this.tiles.length != this.width * this.height) {
               Config.warn("Number of tiles does not equal width x height: " + var1);
               return false;
            } else {
               return true;
            }
         } else {
            Config.warn("Invalid height: " + var1);
            return false;
         }
      } else {
         Config.warn("Invalid width: " + var1);
         return false;
      }
   }

   private boolean isValidFixed(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.tiles == null) {
         Config.warn("Tiles not defined: " + var1);
         return false;
      } else if(this.tiles.length != 1) {
         Config.warn("Number of tiles should be 1 for method: fixed.");
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidTop(String var1) {
      acE[] var2 = MatchBlock.b();
      if(this.tiles == null) {
         this.tiles = this.m("66");
      }

      if(this.tiles.length != 1) {
         Config.warn("Invalid tiles, must be exactly 1: " + var1);
         return false;
      } else {
         return true;
      }
   }

   public void updateIcons(TextureMap var1) {
      acE[] var2 = MatchBlock.b();
      if(this.matchTiles != null) {
         this.matchTileIcons = a(this.matchTiles, var1);
      }

      if(this.tiles != null) {
         this.tileIcons = a(this.tiles, var1);
      }

   }

   private static TextureAtlasSprite[] a(String[] var0, TextureMap var1) {
      acE[] var2 = MatchBlock.b();
      return null;
   }

   public boolean matchesBlockId(int var1) {
      return Matches.blockId(var1, this.matchBlocks);
   }

   public boolean matchesBlock(int var1, int var2) {
      acE[] var3 = MatchBlock.b();
      return !Matches.block(var1, var2, this.matchBlocks)?false:Matches.a(var2, this.metadatas);
   }

   public boolean matchesIcon(TextureAtlasSprite var1) {
      return Matches.a(var1, this.matchTileIcons);
   }

   public String toString() {
      return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.a((Object[])this.matchBlocks) + ", matchTiles: " + Config.a((Object[])this.matchTiles);
   }

   public boolean matchesBiome(BiomeGenBase var1) {
      return Matches.biome(var1, this.biomes);
   }

   public int getMetadataMax() {
      MatchBlock.b();
      int var2 = -1;
      var2 = this.a(this.metadatas, var2);
      if(this.matchBlocks != null) {
         int var3 = 0;
         if(var3 < this.matchBlocks.length) {
            MatchBlock var4 = this.matchBlocks[var3];
            var2 = this.a(var4.getMetadatas(), var2);
            ++var3;
         }
      }

      return var2;
   }

   private int a(int[] var1, int var2) {
      acE[] var3 = MatchBlock.b();
      return var2;
   }
}
