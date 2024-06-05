package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import net.Uv;
import net.aED;
import net.azl;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModel$Builder;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor$ArmorMaterial;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import optifine.Blender;
import optifine.Config;
import optifine.MatchBlock;
import optifine.NbtTagValue;
import optifine.RangeInt;
import optifine.RangeListInt;
import optifine.StrUtils;
import optifine.TextureUtils;
import org.lwjgl.opengl.GL11;

public class CustomItemProperties {
   public String name = null;
   public String basePath = null;
   public int type = 1;
   public int[] items = null;
   public String texture = null;
   public Map mapTextures = null;
   public RangeListInt damage = null;
   public boolean damagePercent;
   public int damageMask;
   public RangeListInt stackSize;
   public RangeListInt enchantmentIds;
   public RangeListInt enchantmentLevels;
   public NbtTagValue[] nbtTagValues;
   public int blend;
   public float speed;
   public float rotation;
   public int layer;
   public float duration;
   public int weight;
   public ResourceLocation textureLocation;
   public Map mapTextureLocations;
   public TextureAtlasSprite sprite;
   public Map mapSprites;
   public IBakedModel model;
   public Map mapModels;
   private int textureWidth;
   private int textureHeight;
   public static final int TYPE_UNKNOWN = 0;
   public static final int TYPE_ITEM = 1;
   public static final int TYPE_ENCHANTMENT = 2;
   public static final int TYPE_ARMOR = 3;

   public CustomItemProperties(Properties var1, String var2) {
      MatchBlock.b();
      this.damagePercent = false;
      this.damageMask = 0;
      this.stackSize = null;
      this.enchantmentIds = null;
      this.enchantmentLevels = null;
      this.nbtTagValues = null;
      this.blend = 1;
      this.speed = 0.0F;
      this.rotation = 0.0F;
      this.layer = 0;
      this.duration = 1.0F;
      this.weight = 0;
      this.textureLocation = null;
      this.mapTextureLocations = null;
      this.sprite = null;
      this.mapSprites = null;
      this.model = null;
      this.mapModels = null;
      this.textureWidth = 0;
      this.textureHeight = 0;
      this.name = parseName(var2);
      this.basePath = parseBasePath(var2);
      this.type = this.parseType(var1.getProperty("type"));
      this.items = this.parseItems(var1.getProperty("items"), var1.getProperty("matchItems"));
      this.mapTextures = parseTextures(var1, this.basePath);
      this.texture = parseTexture(var1.getProperty("texture"), var1.getProperty("tile"), var1.getProperty("source"), var2, this.basePath, this.type, this.mapTextures);
      String var4 = var1.getProperty("damage");
      this.damagePercent = var4.contains("%");
      var4.replace("%", "");
      this.damage = this.parseRangeListInt(var4);
      this.damageMask = this.parseInt(var1.getProperty("damageMask"), 0);
      this.stackSize = this.parseRangeListInt(var1.getProperty("stackSize"));
      this.enchantmentIds = this.parseRangeListInt(var1.getProperty("enchantmentIDs"));
      this.enchantmentLevels = this.parseRangeListInt(var1.getProperty("enchantmentLevels"));
      this.nbtTagValues = this.parseNbtTagValues(var1);
      this.blend = Blender.parseBlend(var1.getProperty("blend"));
      this.speed = this.parseFloat(var1.getProperty("speed"), 0.0F);
      this.rotation = this.parseFloat(var1.getProperty("rotation"), 0.0F);
      this.layer = this.parseInt(var1.getProperty("layer"), 0);
      this.weight = this.parseInt(var1.getProperty("weight"), 0);
      this.duration = this.parseFloat(var1.getProperty("duration"), 1.0F);
      if(PacketRemapper.b() == null) {
         MatchBlock.b(new PacketRemapper[4]);
      }

   }

   private static String parseName(String var0) {
      MatchBlock.b();
      String var2 = var0;
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

   private int parseType(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return 1;
      } else if(var1.equals("item")) {
         return 1;
      } else if(var1.equals("enchantment")) {
         return 2;
      } else if(var1.equals("armor")) {
         return 3;
      } else {
         Config.warn("Unknown method: " + var1);
         return 0;
      }
   }

   private int[] parseItems(String var1, String var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1 == null) {
         var1 = var2;
      }

      if(var1 == null) {
         return null;
      } else {
         var1 = var1.trim();
         TreeSet var4 = new TreeSet();
         String[] var5 = Config.tokenize(var1, " ");
         int var6 = 0;
         if(var6 < var5.length) {
            String var7 = var5[var6];
            int var8 = Config.parseInt(var7, -1);
            if(var8 >= 0) {
               var4.add(new Integer(var8));
            }

            if(var7.contains("-")) {
               String[] var9 = Config.tokenize(var7, "-");
               if(var9.length == 2) {
                  int var10 = Config.parseInt(var9[0], -1);
                  int var11 = Config.parseInt(var9[1], -1);
                  if(var11 >= 0) {
                     int var12 = Math.min(var10, var11);
                     int var13 = Math.max(var10, var11);
                     if(var12 > var13) {
                        ;
                     }

                     var4.add(new Integer(var12));
                     int var14 = var12 + 1;
                  }
               }
            }

            Item var21 = Item.getByNameOrId(var7);
            if(var21 == null) {
               Config.warn("Item not found: " + var7);
            }

            int var22 = Item.b(var21);
            if(var22 < 0) {
               Config.warn("Item not found: " + var7);
            }

            var4.add(new Integer(var22));
            ++var6;
         }

         Integer[] var17 = (Integer[])((Integer[])((Integer[])var4.toArray(new Integer[var4.size()])));
         int[] var18 = new int[var17.length];
         int var19 = 0;
         if(var19 < var18.length) {
            var18[var19] = var17[var19].intValue();
            ++var19;
         }

         return var18;
      }
   }

   private static String parseTexture(String var0, String var1, String var2, String var3, String var4, int var5, Map var6) {
      PacketRemapper[] var7 = MatchBlock.b();
      if(var0 == null) {
         var0 = var1;
      }

      if(var0 == null) {
         var0 = var2;
      }

      if(var0 != null) {
         String var15 = ".png";
         if(var0.endsWith(var15)) {
            var0 = var0.substring(0, var0.length() - var15.length());
         }

         var0 = fixTextureName(var0, var4);
         return var0;
      } else if(var5 == 3) {
         return null;
      } else {
         if(var6 != null) {
            String var8 = (String)var6.get("texture.bow_standby");
            if(var8 != null) {
               return var8;
            }
         }

         String var12 = var3;
         int var9 = var3.lastIndexOf(47);
         if(var9 >= 0) {
            var12 = var3.substring(var9 + 1);
         }

         int var10 = var12.lastIndexOf(46);
         var12 = var12.substring(0, var10);
         var12 = fixTextureName(var12, var4);
         return var12;
      }
   }

   private static Map parseTextures(Properties var0, String var1) {
      String var3 = "texture.";
      MatchBlock.b();
      Map var4 = getMatchingProperties(var0, var3);
      if(var4.size() <= 0) {
         return null;
      } else {
         Set var5 = var4.keySet();
         LinkedHashMap var6 = new LinkedHashMap();
         Iterator var7 = var5.iterator();
         if(var7.hasNext()) {
            Object var8 = var7.next();
            String var9 = (String)var4.get(var8);
            var9 = fixTextureName(var9, var1);
            var6.put(var8, var9);
         }

         return var6;
      }
   }

   private static String fixTextureName(String var0, String var1) {
      MatchBlock.b();
      var0 = TextureUtils.fixResourcePath(var0, var1);
      if(!var0.startsWith(var1) && !var0.startsWith("textures/") && !var0.startsWith("mcpatcher/")) {
         var0 = var1 + "/" + var0;
      }

      if(var0.endsWith(".png")) {
         var0 = var0.substring(0, var0.length() - 4);
      }

      String var3 = "textures/blocks/";
      if(var0.startsWith(var3)) {
         var0 = var0.substring(var3.length());
      }

      if(var0.startsWith("/")) {
         var0 = var0.substring(1);
      }

      return var0;
   }

   private int parseInt(String var1, int var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1 == null) {
         return var2;
      } else {
         var1 = var1.trim();
         int var4 = Config.parseInt(var1, Integer.MIN_VALUE);
         if(var4 == Integer.MIN_VALUE) {
            Config.warn("Invalid integer: " + var1);
            return var2;
         } else {
            return var4;
         }
      }
   }

   private float parseFloat(String var1, float var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1 == null) {
         return var2;
      } else {
         var1 = var1.trim();
         float var4 = Config.parseFloat(var1, Float.MIN_VALUE);
         if(var4 == Float.MIN_VALUE) {
            Config.warn("Invalid float: " + var1);
            return var2;
         } else {
            return var4;
         }
      }
   }

   private RangeListInt parseRangeListInt(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return null;
      } else {
         String[] var3 = Config.tokenize(var1, " ");
         RangeListInt var4 = new RangeListInt();
         byte var5 = 0;
         if(var5 < var3.length) {
            String var6 = var3[var5];
            RangeInt var7 = this.parseRangeInt(var6);
            Config.warn("Invalid range list: " + var1);
            return null;
         } else {
            return var4;
         }
      }
   }

   private RangeInt parseRangeInt(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return null;
      } else {
         var1 = var1.trim();
         int var3 = var1.length() - var1.replace("-", "").length();
         if(var3 > 1) {
            Config.warn("Invalid range: " + var1);
            return null;
         } else {
            String[] var4 = Config.tokenize(var1, "- ");
            int[] var5 = new int[var4.length];
            int var6 = 0;
            if(var6 < var4.length) {
               String var7 = var4[var6];
               int var8 = Config.parseInt(var7, -1);
               if(var8 < 0) {
                  Config.warn("Invalid range: " + var1);
                  return null;
               }

               var5[var6] = var8;
               ++var6;
            }

            if(var5.length == 1) {
               var6 = var5[0];
               return var1.startsWith("-")?new RangeInt(0, var6):(var1.endsWith("-")?new RangeInt(var6, 255):new RangeInt(var6, var6));
            } else if(var5.length == 2) {
               var6 = Math.min(var5[0], var5[1]);
               int var13 = Math.max(var5[0], var5[1]);
               return new RangeInt(var6, var13);
            } else {
               Config.warn("Invalid range: " + var1);
               return null;
            }
         }
      }
   }

   private NbtTagValue[] parseNbtTagValues(Properties var1) {
      MatchBlock.b();
      String var3 = "nbt.";
      Map var4 = getMatchingProperties(var1, var3);
      if(var4.size() <= 0) {
         return null;
      } else {
         ArrayList var5 = new ArrayList();
         Iterator var6 = var4.keySet().iterator();
         if(var6.hasNext()) {
            Object var7 = var6.next();
            String var8 = (String)var4.get(var7);
            String var9 = ((String)var7).substring(var3.length());
            NbtTagValue var10 = new NbtTagValue(var9, var8);
            var5.add(var10);
         }

         NbtTagValue[] var11 = (NbtTagValue[])((NbtTagValue[])((NbtTagValue[])var5.toArray(new NbtTagValue[var5.size()])));
         return var11;
      }
   }

   private static Map getMatchingProperties(Properties var0, String var1) {
      LinkedHashMap var3 = new LinkedHashMap();
      MatchBlock.b();
      Iterator var4 = var0.keySet().iterator();
      if(var4.hasNext()) {
         Object var5 = var4.next();
         String var6 = var0.getProperty((String)var5);
         if(((String)var5).startsWith(var1)) {
            var3.put(var5, var6);
         }
      }

      return var3;
   }

   public boolean isValid(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.name != null && this.name.length() > 0) {
         if(this.basePath == null) {
            Config.warn("No base path found: " + var1);
            return false;
         } else if(this.type == 0) {
            Config.warn("No type defined: " + var1);
            return false;
         } else if((this.type == 1 || this.type == 3) && this.items == null) {
            Config.warn("No items defined: " + var1);
            return false;
         } else if(this.texture == null && this.mapTextures == null) {
            Config.warn("No texture specified: " + var1);
            return false;
         } else if(this.type == 2 && this.enchantmentIds == null) {
            Config.warn("No enchantmentIDs specified: " + var1);
            return false;
         } else {
            return true;
         }
      } else {
         Config.warn("No name found: " + var1);
         return false;
      }
   }

   public void updateIcons(TextureMap var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.texture != null) {
         this.textureLocation = this.b(this.texture);
         if(this.type == 1) {
            ResourceLocation var3 = this.getSpriteLocation(this.textureLocation);
            this.sprite = var1.b(var3);
         }
      }

      if(this.mapTextures != null) {
         this.mapTextureLocations = new HashMap();
         this.mapSprites = new HashMap();
         Iterator var9 = this.mapTextures.keySet().iterator();
         if(var9.hasNext()) {
            String var4 = (String)var9.next();
            String var5 = (String)this.mapTextures.get(var4);
            ResourceLocation var6 = this.b(var5);
            this.mapTextureLocations.put(var4, var6);
            if(this.type == 1) {
               ResourceLocation var7 = this.getSpriteLocation(var6);
               TextureAtlasSprite var8 = var1.b(var7);
               this.mapSprites.put(var4, var8);
            }
         }
      }

   }

   private ResourceLocation b(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return null;
   }

   private ResourceLocation getSpriteLocation(ResourceLocation var1) {
      String var2 = var1.getResourcePath();
      var2 = StrUtils.removePrefix(var2, "textures/");
      var2 = StrUtils.removeSuffix(var2, ".png");
      ResourceLocation var3 = new ResourceLocation(var1.getResourceDomain(), var2);
      return var3;
   }

   public void updateModel(TextureMap var1, ItemModelGenerator var2) {
      String[] var4 = this.getModelTextures();
      MatchBlock.b();
      boolean var5 = this.isUseTint();
      this.model = makeBakedModel(var1, var2, var4, var5);
      if(this.type == 1 && this.mapTextures != null) {
         Iterator var6 = this.mapTextures.keySet().iterator();
         if(var6.hasNext()) {
            String var7 = (String)var6.next();
            String var8 = (String)this.mapTextures.get(var7);
            String var9 = StrUtils.removePrefix(var7, "texture.");
            if(var9.startsWith("bow") || var9.startsWith("fishing_rod")) {
               String[] var10 = new String[]{var8};
               IBakedModel var11 = makeBakedModel(var1, var2, var10, var5);
               if(this.mapModels == null) {
                  this.mapModels = new HashMap();
               }

               this.mapModels.put(var9, var11);
            }
         }
      }

   }

   private boolean isUseTint() {
      return true;
   }

   private static IBakedModel makeBakedModel(TextureMap var0, ItemModelGenerator var1, String[] var2, boolean var3) {
      ModelBlock var4 = makeModelBlock(var2);
      ModelBlock var5 = var1.makeItemModel(var0, var4);
      IBakedModel var6 = bakeModel(var0, var5, var3);
      return var6;
   }

   private String[] getModelTextures() {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.type == 1 && this.items.length == 1) {
         Item var2 = Item.getItemById(this.items[0]);
         if(var2 == Items.potionitem && this.damage != null && this.damage.getCountRanges() > 0) {
            RangeInt var9 = this.damage.getRange(0);
            int var10 = var9.getMin();
            boolean var11 = (var10 & 16384) != 0;
            String var12 = this.getMapTexture(this.mapTextures, "texture.potion_overlay", "items/potion_overlay");
            String var13 = null;
            var13 = this.getMapTexture(this.mapTextures, "texture.potion_bottle_splash", "items/potion_bottle_splash");
            return new String[]{var12, var13};
         }

         if(var2 instanceof ItemArmor) {
            ItemArmor var3 = (ItemArmor)var2;
            if(var3.getArmorMaterial() == ItemArmor$ArmorMaterial.LEATHER) {
               String var4 = "leather";
               String var5 = "helmet";
               if(var3.armorType == 0) {
                  var5 = "helmet";
               }

               if(var3.armorType == 1) {
                  var5 = "chestplate";
               }

               if(var3.armorType == 2) {
                  var5 = "leggings";
               }

               if(var3.armorType == 3) {
                  var5 = "boots";
               }

               String var6 = var4 + "_" + var5;
               String var7 = this.getMapTexture(this.mapTextures, "texture." + var6, "items/" + var6);
               String var8 = this.getMapTexture(this.mapTextures, "texture." + var6 + "_overlay", "items/" + var6 + "_overlay");
               return new String[]{var7, var8};
            }
         }
      }

      return new String[]{this.texture};
   }

   private String getMapTexture(Map var1, String var2, String var3) {
      PacketRemapper[] var4 = MatchBlock.b();
      if(var1 == null) {
         return var3;
      } else {
         String var5 = (String)var1.get(var2);
         return var5 == null?var3:var5;
      }
   }

   private static ModelBlock makeModelBlock(String[] var0) {
      MatchBlock.b();
      StringBuffer var2 = new StringBuffer();
      var2.append("{\"parent\": \"builtin/generated\",\"textures\": {");
      int var3 = 0;
      if(var3 < var0.length) {
         String var4 = var0[var3];
         var2.append(", ");
         var2.append("\"layer" + var3 + "\": \"" + var4 + "\"");
         ++var3;
      }

      var2.append("}}");
      String var6 = var2.toString();
      ModelBlock var7 = ModelBlock.deserialize(var6);
      return var7;
   }

   private static IBakedModel bakeModel(TextureMap var0, ModelBlock var1, boolean var2) {
      MatchBlock.b();
      ModelRotation var4 = ModelRotation.X0_Y0;
      boolean var5 = false;
      TextureAtlasSprite var6 = var0.getSpriteSafe(var1.resolveTextureName("particle"));
      SimpleBakedModel$Builder var7 = (new SimpleBakedModel$Builder(var1)).setTexture(var6);
      Iterator var8 = var1.getElements().iterator();
      if(var8.hasNext()) {
         BlockPart var9 = (BlockPart)var8.next();
         Iterator var10 = var9.mapFaces.keySet().iterator();
         if(var10.hasNext()) {
            EnumFacing var11 = (EnumFacing)var10.next();
            BlockPartFace var12 = (BlockPartFace)var9.mapFaces.get(var11);
            if(!var2) {
               var12 = new BlockPartFace(var12.cullFace, -1, var12.texture, var12.blockFaceUV);
            }

            TextureAtlasSprite var13 = var0.getSpriteSafe(var1.resolveTextureName(var12.texture));
            BakedQuad var14 = makeBakedQuad(var9, var12, var13, var11, var4, var5);
            if(var12.cullFace == null) {
               var7.addGeneralQuad(var14);
            }

            var7.addFaceQuad(var4.rotateFace(var12.cullFace), var14);
         }
      }

      return var7.makeBakedModel();
   }

   private static BakedQuad makeBakedQuad(BlockPart var0, BlockPartFace var1, TextureAtlasSprite var2, EnumFacing var3, ModelRotation var4, boolean var5) {
      azl var6 = new azl();
      return aED.a(var6, var0.positionFrom, var0.positionTo, var1, var2, var3, var4, var0.partRotation, var5, var0.shade);
   }

   public String toString() {
      return "" + this.basePath + "/" + this.name + ", type: " + this.type + ", items: [" + Config.a(this.items) + "], textture: " + this.texture;
   }

   public float b(Uv var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.textureWidth <= 0) {
         if(this.textureLocation != null) {
            ITextureObject var3 = var1.b(this.textureLocation);
            int var4 = var3.getGlTextureId();
            int var5 = GlStateManager.getBoundTexture();
            GlStateManager.bindTexture(var4);
            this.textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
            GlStateManager.bindTexture(var5);
         }

         if(this.textureWidth <= 0) {
            this.textureWidth = 16;
         }
      }

      return (float)this.textureWidth;
   }

   public float a(Uv var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.textureHeight <= 0) {
         if(this.textureLocation != null) {
            ITextureObject var3 = var1.b(this.textureLocation);
            int var4 = var3.getGlTextureId();
            int var5 = GlStateManager.getBoundTexture();
            GlStateManager.bindTexture(var4);
            this.textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
            GlStateManager.bindTexture(var5);
         }

         if(this.textureHeight <= 0) {
            this.textureHeight = 16;
         }
      }

      return (float)this.textureHeight;
   }

   public IBakedModel getModel(ModelResourceLocation var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.mapTextures != null) {
         String var3 = var1.getResourcePath();
         if(this.mapModels != null) {
            IBakedModel var4 = (IBakedModel)this.mapModels.get(var3);
            if(var4 != null) {
               return var4;
            }
         }
      }

      return this.model;
   }
}
