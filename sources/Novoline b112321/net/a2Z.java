package net;

import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import net.a2M;
import net.aAP;
import net.aRE;
import net.axM;
import net.bgR;
import net.cI;

public class a2Z extends a2M {
   private static int j = 2147473647;
   private final UUID d;
   private String h;
   private float l;
   private boolean i = false;
   private bgR a;
   private final int e;
   private double c;
   private double f;
   private double g;
   private static String k;

   public a2Z(bgR var1, UUID var2, String var3, float var4) {
      d();
      this.e = j++;
      this.a = var1;
      this.d = var2;
      this.h = var3;
      this.l = var4;
      if(PacketRemapper.b() == null) {
         b("IkiEf");
      }

   }

   public String b() {
      return this.h;
   }

   public a2M a(String var1) {
      d();
      this.h = var1;
      if(this.i) {
         this.c();
      }

      return this;
   }

   public float c() {
      return this.l;
   }

   public a2M a(float var1) {
      d();
      this.l = var1;
      if(this.l <= 0.0F) {
         this.l = 1.0E-4F;
      }

      if(this.i) {
         this.c();
      }

      return this;
   }

   public axM f() {
      return null;
   }

   public a2M a(axM var1) {
      throw new UnsupportedOperationException(this.getClass().getName() + " does not support color");
   }

   public aAP g() {
      return null;
   }

   public a2M a(aAP var1) {
      throw new UnsupportedOperationException(this.getClass().getName() + " does not support styles");
   }

   public a2M b(UUID var1) {
      throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
   }

   public a2M a(bgR var1) {
      throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
   }

   public a2M a(UUID var1) {
      throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
   }

   public a2M b(bgR var1) {
      throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
   }

   public a2M c(BossFlag var1) {
      throw new UnsupportedOperationException(this.getClass().getName() + " does not support flags");
   }

   public a2M b(BossFlag var1) {
      throw new UnsupportedOperationException(this.getClass().getName() + " does not support flags");
   }

   public boolean a(BossFlag var1) {
      return false;
   }

   public Set d() {
      return Collections.singleton(((cI)this.a.b(cI.class)).a());
   }

   public Set j() {
      throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
   }

   public a2M i() {
      String var1 = d();
      if(!this.i) {
         this.i = true;
         this.e();
      }

      return this;
   }

   public a2M h() {
      String var1 = d();
      if(this.i) {
         this.i = false;
         this.b();
      }

      return this;
   }

   public boolean k() {
      return this.i;
   }

   public UUID a() {
      return this.d;
   }

   public void a(double var1, double var3, double var5) {
      this.c = var1;
      this.f = var3;
      this.g = var5;
      this.a();
   }

   private void e() {
      PacketWrapperImpl var1 = new PacketWrapperImpl(15, (ByteBuf)null, this.a);
      var1.a(Type.VAR_INT, Integer.valueOf(this.e));
      var1.a(Type.M, Short.valueOf((short)64));
      var1.a(Type.I, Integer.valueOf((int)(this.c * 32.0D)));
      var1.a(Type.I, Integer.valueOf((int)(this.f * 32.0D)));
      var1.a(Type.I, Integer.valueOf((int)(this.g * 32.0D)));
      var1.a(Type.k, Byte.valueOf((byte)0));
      var1.a(Type.k, Byte.valueOf((byte)0));
      var1.a(Type.k, Byte.valueOf((byte)0));
      var1.a(Type.SHORT, Short.valueOf((short)0));
      var1.a(Type.SHORT, Short.valueOf((short)0));
      var1.a(Type.SHORT, Short.valueOf((short)0));
      ArrayList var2 = new ArrayList();
      var2.add(new Metadata(0, MetaType1_8.Byte, Byte.valueOf((byte)32)));
      var2.add(new Metadata(2, MetaType1_8.String, this.h));
      var2.add(new Metadata(3, MetaType1_8.Byte, Byte.valueOf((byte)1)));
      var2.add(new Metadata(6, MetaType1_8.Float, Float.valueOf(this.l * 300.0F)));
      var1.a(Types1_8.METADATA_LIST, var2);
      PacketUtil.b(var1, aRE.class, true, true);
   }

   private void a() {
      PacketWrapperImpl var1 = new PacketWrapperImpl(24, (ByteBuf)null, this.a);
      var1.a(Type.VAR_INT, Integer.valueOf(this.e));
      var1.a(Type.I, Integer.valueOf((int)(this.c * 32.0D)));
      var1.a(Type.I, Integer.valueOf((int)(this.f * 32.0D)));
      var1.a(Type.I, Integer.valueOf((int)(this.g * 32.0D)));
      var1.a(Type.k, Byte.valueOf((byte)0));
      var1.a(Type.k, Byte.valueOf((byte)0));
      var1.a(Type.c, Boolean.valueOf(false));
      PacketUtil.b(var1, aRE.class, true, true);
   }

   private void c() {
      PacketWrapperImpl var1 = new PacketWrapperImpl(28, (ByteBuf)null, this.a);
      var1.a(Type.VAR_INT, Integer.valueOf(this.e));
      ArrayList var2 = new ArrayList();
      var2.add(new Metadata(2, MetaType1_8.String, this.h));
      var2.add(new Metadata(6, MetaType1_8.Float, Float.valueOf(this.l * 300.0F)));
      var1.a(Types1_8.METADATA_LIST, var2);
      PacketUtil.b(var1, aRE.class, true, true);
   }

   private void b() {
      PacketWrapperImpl var1 = new PacketWrapperImpl(19, (ByteBuf)null, this.a);
      var1.a(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{this.e});
      PacketUtil.b(var1, aRE.class, true, true);
   }

   public void a(double var1, double var3, double var5, float var7, float var8) {
      double var10 = Math.toRadians((double)var7);
      d();
      double var12 = Math.toRadians((double)var8);
      var1 = var1 - Math.cos(var12) * Math.sin(var10) * 48.0D;
      var3 = var3 - Math.sin(var12) * 48.0D;
      var5 = var5 + Math.cos(var12) * Math.cos(var10) * 48.0D;
      this.a(var1, var3, var5);
   }

   static {
      b((String)null);
   }

   public static void b(String var0) {
      k = var0;
   }

   public static String d() {
      return k;
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
