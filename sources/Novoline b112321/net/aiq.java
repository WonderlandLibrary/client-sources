package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.google.gson.JsonObject;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.aPX;
import net.aiL;

public class aiq extends aiL {
   private final Map m = new HashMap();
   private CompoundTag b;
   private static PacketRemapper[] n;

   public aiq() {
      super("1.16", "1.16.2", true);
   }

   public void a(JsonObject var1, JsonObject var2, JsonObject var3) {
      PacketRemapper[] var4 = b();

      try {
         this.b = aPX.a(MappingDataLoader.getResource("dimension-registry-1.16.2.nbt"));
      } catch (IOException var10) {
         Via.d().getLogger().severe("Error loading dimension registry:");
         var10.printStackTrace();
      }

      ListTag var5 = (ListTag)((CompoundTag)this.b.get("minecraft:dimension_type")).get("value");
      Iterator var6 = var5.iterator();
      if(var6.hasNext()) {
         Tag var7 = (Tag)var6.next();
         CompoundTag var8 = (CompoundTag)var7;
         CompoundTag var9 = new CompoundTag("", ((CompoundTag)var8.get("element")).getValue());
         this.m.put(((StringTag)var8.get("name")).getValue(), var9);
      }

   }

   public Map a() {
      return this.m;
   }

   public CompoundTag c() {
      return this.b;
   }

   public static void b(PacketRemapper[] var0) {
      n = var0;
   }

   public static PacketRemapper[] b() {
      return n;
   }

   static {
      b(new PacketRemapper[4]);
   }
}
