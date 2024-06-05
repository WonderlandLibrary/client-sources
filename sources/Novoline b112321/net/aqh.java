package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.aqu;
import net.ayd;
import net.dr;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.Nullable;
import viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import viaversion.viabackwards.api.data.VBMappingDataLoader;
import viaversion.viabackwards.api.rewriters.ItemRewriterBase;
import viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter$Pos;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.BlockColors;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

public abstract class aqh extends ItemRewriterBase {
   private static final Map i = new HashMap();
   protected final Int2ObjectMap g;
   private static int[] h;

   protected aqh(ayd var1, String var2) {
      super(var1, false);
      this.g = (Int2ObjectMap)i.get(var2);
   }

   @Nullable
   public Item a(Item var1) {
      int var2 = aqu.d();
      if(var1 == null) {
         return null;
      } else {
         MappedLegacyBlockItem var3 = (MappedLegacyBlockItem)this.g.get(var1.getIdentifier());
         return super.a(var1);
      }
   }

   public int a(int var1) {
      aqu.d();
      int var3 = var1 >> 4;
      int var4 = var1 & 15;
      dr var5 = this.a(var3, var4);
      return var5 == null?var1:var5.d() << 4 | var5.c() & 15;
   }

   @Nullable
   public dr a(int var1, int var2) {
      aqu.d();
      MappedLegacyBlockItem var4 = (MappedLegacyBlockItem)this.g.get(var1);
      if(var4 != null && var4.isBlock()) {
         dr var5 = var4.g();
         return var5.c() == -1?var5.a(var2):var5;
      } else {
         return null;
      }
   }

   protected void a(Chunk var1) {
      aqu.d();
      HashMap var3 = new HashMap();

      for(CompoundTag var5 : var1.getBlockEntities()) {
         Tag var6;
         Tag var7;
         if((var6 = var5.get("x")) != null && (var7 = var5.get("y")) != null) {
            Tag var8;
            if((var8 = var5.get("z")) == null) {
               ;
            }

            LegacyBlockItemRewriter$Pos var9 = new LegacyBlockItemRewriter$Pos(((Integer)var6.getValue()).intValue() & 15, ((Integer)var7.getValue()).intValue(), ((Integer)var8.getValue()).intValue() & 15);
            var3.put(var9, var5);
            ChunkSection var10 = var1.getSections()[var9.getY() >> 4];
            if(var10 != null) {
               int var11 = var10.getFlatBlock(var9.getX(), var9.getY() & 15, var9.getZ());
               int var12 = var11 >> 4;
               MappedLegacyBlockItem var13 = (MappedLegacyBlockItem)this.g.get(var12);
               if(var13 != null && var13.hasBlockEntityHandler()) {
                  var13.getBlockEntityHandler().handleOrNewCompoundTag(var11, var5);
               }
               break;
            }
         }
      }

      int var14 = 0;
      if(var14 < var1.getSections().length) {
         ChunkSection var16 = var1.getSections()[var14];
         ++var14;
      }

   }

   protected CompoundTag a(String var1) {
      CompoundTag var2 = new CompoundTag("");
      var2.put(new CompoundTag("display"));
      var1 = ChatColor.RESET + var1;
      ((CompoundTag)var2.get("display")).put(new StringTag("Name", this.jsonNameFormat?ChatRewriter.legacyTextToJson(var1).toString():var1));
      return var2;
   }

   static {
      b((int[])null);
      JsonObject var7 = VBMappingDataLoader.loadFromDataDir("legacy-mappings.json");

      for(Entry var9 : var7.entrySet()) {
         Int2ObjectOpenHashMap var10 = new Int2ObjectOpenHashMap(8);
         i.put(var9.getKey(), var10);

         for(Entry var12 : ((JsonElement)var9.getValue()).getAsJsonObject().entrySet()) {
            JsonObject var13 = ((JsonElement)var12.getValue()).getAsJsonObject();
            int var14 = var13.getAsJsonPrimitive("id").getAsInt();
            JsonPrimitive var15 = var13.getAsJsonPrimitive("data");
            short var16 = var15.getAsShort();
            String var17 = var13.getAsJsonPrimitive("name").getAsString();
            JsonPrimitive var18 = var13.getAsJsonPrimitive("block");
            boolean var19 = var18.getAsBoolean();
            if(((String)var12.getKey()).indexOf(45) != -1) {
               String[] var20 = ((String)var12.getKey()).split("-", 2);
               int var21 = Integer.parseInt(var20[0]);
               int var22 = Integer.parseInt(var20[1]);
               if(var17.contains("%color%")) {
                  for(int var25 = var21; var25 <= var22; ++var25) {
                     var10.put(var25, new MappedLegacyBlockItem(var14, var16, var17.replace("%color%", BlockColors.get(var25 - var21)), var19));
                  }
               } else {
                  MappedLegacyBlockItem var23 = new MappedLegacyBlockItem(var14, var16, var17, var19);

                  for(int var24 = var21; var24 <= var22; ++var24) {
                     var10.put(var24, var23);
                  }
               }
            } else {
               var10.put(Integer.parseInt((String)var12.getKey()), new MappedLegacyBlockItem(var14, var16, var17, var19));
            }
         }
      }

   }

   public static void b(int[] var0) {
      h = var0;
   }

   public static int[] a() {
      return h;
   }
}
