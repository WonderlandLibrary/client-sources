package net;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.Dk;
import net.aRi;
import net.axZ;
import net.t4;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
import viaversion.viarewind.replacement.EntityReplacement;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.type.Type;

public class zb implements EntityReplacement {
   private int e;
   private List i = new ArrayList();
   private double b;
   private double a;
   private double g;
   private float f;
   private float h;
   private float d;
   private UserConnection c;

   public zb(int var1, UserConnection var2) {
      this.e = var1;
      this.c = var2;
      this.spawn();
   }

   public void setLocation(double var1, double var3, double var5) {
      this.b = var1;
      this.a = var3;
      this.g = var5;
      this.c();
   }

   public void relMove(double var1, double var3, double var5) {
      this.b += var1;
      this.a += var3;
      this.g += var5;
      this.c();
   }

   public void setYawPitch(float var1, float var2) {
      boolean var3 = ArmorStandReplacement.a();
      if(this.f != var1 || this.h != var2) {
         this.f = var1;
         this.h = var2;
         this.c();
      }

   }

   public void setHeadYaw(float var1) {
      boolean var2 = ArmorStandReplacement.a();
      if(this.d != var1) {
         this.d = var1;
         this.c();
      }

   }

   public void updateMetadata(List var1) {
      ArmorStandReplacement.f();
      Iterator var3 = var1.iterator();
      if(var3.hasNext()) {
         Metadata var4 = (Metadata)var3.next();
         this.i.removeIf(zb::lambda$updateMetadata$0);
         this.i.add(var4);
      }

      this.d();
   }

   public void c() {
      PacketWrapper var2 = new PacketWrapper(24, (ByteBuf)null, this.c);
      var2.write(Type.INT, Integer.valueOf(this.e));
      ArmorStandReplacement.a();
      var2.write(Type.INT, Integer.valueOf((int)(this.b * 32.0D)));
      var2.write(Type.INT, Integer.valueOf((int)(this.a * 32.0D)));
      var2.write(Type.INT, Integer.valueOf((int)(this.g * 32.0D)));
      var2.write(Type.BYTE, Byte.valueOf((byte)((int)(this.f / 360.0F * 256.0F))));
      var2.write(Type.BYTE, Byte.valueOf((byte)((int)(this.h / 360.0F * 256.0F))));
      PacketWrapper var3 = new PacketWrapper(25, (ByteBuf)null, this.c);
      var3.write(Type.INT, Integer.valueOf(this.e));
      var3.write(Type.BYTE, Byte.valueOf((byte)((int)(this.d / 360.0F * 256.0F))));
      PacketUtil.sendPacket(var2, aRi.class, true, true);
      PacketUtil.sendPacket(var3, aRi.class, true, true);
   }

   public void d() {
      ArmorStandReplacement.f();
      PacketWrapper var2 = new PacketWrapper(28, (ByteBuf)null, this.c);
      var2.write(Type.INT, Integer.valueOf(this.e));
      ArrayList var3 = new ArrayList();
      Iterator var4 = this.i.iterator();
      if(var4.hasNext()) {
         Metadata var5 = (Metadata)var4.next();
         var3.add(new Metadata(var5.getId(), var5.getMetaType(), var5.getValue()));
      }

      Dk.a(t4.SQUID, var3);
      var2.write(axZ.j, var3);
      PacketUtil.sendPacket(var2, aRi.class);
   }

   public void spawn() {
      PacketWrapper var1 = new PacketWrapper(15, (ByteBuf)null, this.c);
      var1.write(Type.VAR_INT, Integer.valueOf(this.e));
      var1.write(Type.UNSIGNED_BYTE, Short.valueOf((short)60));
      var1.write(Type.INT, Integer.valueOf(0));
      var1.write(Type.INT, Integer.valueOf(0));
      var1.write(Type.INT, Integer.valueOf(0));
      var1.write(Type.BYTE, Byte.valueOf((byte)0));
      var1.write(Type.BYTE, Byte.valueOf((byte)0));
      var1.write(Type.BYTE, Byte.valueOf((byte)0));
      var1.write(Type.SHORT, Short.valueOf((short)0));
      var1.write(Type.SHORT, Short.valueOf((short)0));
      var1.write(Type.SHORT, Short.valueOf((short)0));
      var1.write(axZ.j, new ArrayList());
      PacketUtil.sendPacket(var1, aRi.class, true, true);
   }

   public void despawn() {
      PacketWrapper var1 = new PacketWrapper(19, (ByteBuf)null, this.c);
      var1.write(axZ.b, new int[]{this.e});
      PacketUtil.sendPacket(var1, aRi.class, true, true);
   }

   public int getEntityId() {
      return this.e;
   }

   private static boolean lambda$updateMetadata$0(Metadata var0, Metadata var1) {
      boolean var2 = ArmorStandReplacement.a();
      return var1.getId() == var0.getId();
   }
}
