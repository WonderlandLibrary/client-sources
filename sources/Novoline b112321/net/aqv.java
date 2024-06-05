package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.viaversion.viabackwards.api.exceptions.RemovedValueException;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.Set;
import net.EN;
import net.Gh;
import net.UN;
import net.Zh;
import net.aAA;
import net.aMz;
import net.aSG;
import net.anp;
import net.anw;
import net.aqF;
import net.aqG;
import net.awD;
import net.axs;
import net.n3;
import net.y7;
import net.yb;

public class aqv extends aqF {
   private final Set l = Sets.newHashSet(new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"});

   public aqv(Protocol1_16_1To1_16_2 var1) {
      super(var1);
   }

   protected void registerPackets() {
      aqG.c();
      this.b(UN.SPAWN_ENTITY, awD.FALLING_BLOCK);
      this.b(UN.SPAWN_MOB);
      this.a(UN.SPAWN_EXPERIENCE_ORB, awD.EXPERIENCE_ORB);
      this.a(UN.SPAWN_PAINTING, awD.PAINTING);
      this.a(UN.SPAWN_PLAYER, awD.PLAYER);
      this.a((y7)UN.DESTROY_ENTITIES);
      this.a(UN.ENTITY_METADATA, aSG.c);
      ((Protocol1_16_1To1_16_2)this.c).a(UN.JOIN_GAME, new anp(this));
      ((Protocol1_16_1To1_16_2)this.c).a(UN.RESPAWN, new anw(this));
      if(PacketRemapper.b() == null) {
         aqG.b(false);
      }

   }

   private String a(CompoundTag var1) {
      StringTag var2 = (StringTag)var1.get("effects");
      return this.l.contains(var2.getValue())?var2.getValue():"minecraft:overworld";
   }

   protected void registerRewrites() {
      this.c().a(this::lambda$registerRewrites$0);
      this.a(awD.values(), axs.class);
      this.a(awD.PIGLIN_BRUTE, awD.PIGLIN).jsonName("Piglin Brute");
      this.c().a(awD.ABSTRACT_PIGLIN, true).a(aqv::lambda$registerRewrites$1);
   }

   protected ConnectionManager b(int var1) {
      return aAA.a(var1);
   }

   private static Metadata lambda$registerRewrites$1(yb var0) throws RemovedValueException {
      boolean var1 = aqG.d();
      if(var0.a() == 15) {
         var0.i().setId(16);
      }

      if(var0.a() == 16) {
         var0.i().setId(15);
      }

      return var0.i();
   }

   private Metadata lambda$registerRewrites$0(yb var1) throws RemovedValueException {
      aqG.d();
      Metadata var3 = var1.i();
      MetaType var4 = var3.metaType();
      if(var4 == n3.Slot) {
         var3.a(((Protocol1_16_1To1_16_2)this.c).a().a((aMz)var3.getValue()));
      }

      if(var4 == n3.BlockID) {
         var3.a(Integer.valueOf(((Protocol1_16_1To1_16_2)this.c).getMappingData().c(((Integer)var3.getValue()).intValue())));
      }

      if(var4 == n3.OptChat) {
         JsonElement var5 = (JsonElement)var3.value();
         ((Protocol1_16_1To1_16_2)this.c).b().a(var5);
      }

      if(var4 == n3.PARTICLE) {
         this.a((Gh)((Gh)var3.getValue()));
      }

      return var3;
   }

   static EN a(aqv var0, ConnectionManager var1, Type var2) {
      return var0.a(var1, var2);
   }

   static String a(aqv var0, CompoundTag var1) {
      return var0.a(var1);
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }
}
