package net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.VV;
import net.aR5;
import net.acE;
import net.acS;
import net.aqu;
import net.ayd;
import org.jetbrains.annotations.Nullable;
import viaversion.viabackwards.api.entities.storage.EntityData;
import viaversion.viabackwards.api.entities.storage.EntityObjectData;
import viaversion.viabackwards.api.entities.storage.MetaStorage;
import viaversion.viabackwards.api.rewriters.EntityRewriterBase;
import viaversion.viabackwards.api.rewriters.LegacyEntityRewriter$2;
import viaversion.viabackwards.api.rewriters.LegacyEntityRewriter$3;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.entities.ObjectType;
import viaversion.viaversion.api.minecraft.metadata.MetaType;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

public abstract class aqr extends EntityRewriterBase {
   private final Map l;
   private static acE[] k;

   protected aqr(ayd var1) {
      this(var1, aR5.String, aR5.Boolean);
   }

   protected aqr(ayd var1, MetaType var2, MetaType var3) {
      super(var1, var2, 2, var3, 3);
      this.l = new HashMap();
   }

   protected EntityObjectData a(ObjectType var1, ObjectType var2, int var3) {
      EntityObjectData var4 = new EntityObjectData(var1.getId(), true, var2.getId(), var3);
      this.l.put(var1, var4);
      return var4;
   }

   @Nullable
   protected EntityData a(ObjectType var1) {
      return (EntityData)this.l.get(var1);
   }

   protected void b(ClientboundPacketType var1) {
      this.c.a(var1, new acS(this));
   }

   protected void b(ClientboundPacketType var1, EntityType var2) {
      this.c.a(var1, new LegacyEntityRewriter$2(this, var2));
   }

   protected void a(ClientboundPacketType var1, Type var2, Type var3) {
      this.c().a(var1, new LegacyEntityRewriter$3(this, var2, var3));
   }

   protected void a(ClientboundPacketType var1, Type var2) {
      this.a(var1, (Type)null, var2);
   }

   protected PacketHandler a(Type var1) {
      return this::lambda$getMobSpawnRewriter$0;
   }

   protected PacketHandler c() {
      return this::lambda$getObjectTrackerHandler$1;
   }

   protected PacketHandler a(Type var1, EntityType var2) {
      return this::lambda$getTrackerAndMetaHandler$2;
   }

   protected PacketHandler a(Function var1) {
      return this::lambda$getObjectRewriter$3;
   }

   protected EntityType getObjectTypeFromId(int var1) {
      return this.getTypeFromId(var1);
   }

   private void lambda$getObjectRewriter$3(Function var1, PacketWrapper var2) throws Exception {
      aqu.e();
      ObjectType var4 = (ObjectType)var1.apply(var2.get(Type.BYTE, 0));
      VV.d().getLogger().warning("Could not find Entity Type" + var2.get(Type.BYTE, 0));
   }

   private void lambda$getTrackerAndMetaHandler$2(EntityType var1, Type var2, PacketWrapper var3) throws Exception {
      this.addTrackedEntity(var3, ((Integer)var3.get(Type.VAR_INT, 0)).intValue(), var1);
      List var4 = this.handleMeta(var3.user(), ((Integer)var3.get(Type.VAR_INT, 0)).intValue(), new MetaStorage((List)var3.get(var2, 0))).getMetaDataList();
      var3.set(var2, 0, var4);
   }

   private void lambda$getObjectTrackerHandler$1(PacketWrapper var1) throws Exception {
      this.addTrackedEntity(var1, ((Integer)var1.get(Type.VAR_INT, 0)).intValue(), this.getObjectTypeFromId(((Byte)var1.get(Type.BYTE, 0)).byteValue()));
   }

   private void lambda$getMobSpawnRewriter$0(Type var1, PacketWrapper var2) throws Exception {
      aqu.e();
      int var4 = ((Integer)var2.get(Type.VAR_INT, 0)).intValue();
      EntityType var5 = this.getEntityType(var2.user(), var4);
      MetaStorage var6 = new MetaStorage((List)var2.get(var1, 0));
      this.handleMeta(var2.user(), var4, var6);
      EntityData var7 = this.getEntityData(var5);
      var2.set(Type.VAR_INT, 1, Integer.valueOf(var7.getReplacementId()));
      if(var7.hasBaseMeta()) {
         var7.getDefaultMeta().createMeta(var6);
      }

      var2.set(var1, 0, var6.getMetaDataList());
   }

   public static void b(acE[] var0) {
      k = var0;
   }

   public static acE[] b() {
      return k;
   }

   private static Exception b(Exception var0) {
      return var0;
   }

   static {
      if(b() != null) {
         b(new acE[4]);
      }

   }
}
