package net;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.legacy.bossbar.CommonBoss$UpdateAction;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.a2M;
import net.aAP;
import net.aRY;
import net.axM;
import net.bgR;

public abstract class a2H extends a2M {
   private final UUID a;
   private final Set e;
   private final Set j;
   private String g;
   private float d;
   private axM i;
   private aAP h;
   private boolean f;
   private static PacketRemapper[] c;

   public a2H(String var1, float var2, axM var3, aAP var4) {
      d();
      super();
      Preconditions.checkNotNull(var1, "Title cannot be null");
      Preconditions.checkArgument(var2 >= 0.0F && var2 <= 1.0F, "Health must be between 0 and 1");
      this.a = UUID.randomUUID();
      this.g = var1;
      this.d = var2;
      this.i = var3 == null?axM.PURPLE:var3;
      this.h = var4 == null?aAP.SOLID:var4;
      this.e = Collections.newSetFromMap(new WeakHashMap());
      this.j = new HashSet();
      this.f = true;
   }

   public a2M a(String var1) {
      Preconditions.checkNotNull(var1);
      this.g = var1;
      this.a(CommonBoss$UpdateAction.UPDATE_TITLE);
      return this;
   }

   public a2M a(float var1) {
      PacketRemapper[] var2 = d();
      Preconditions.checkArgument(var1 >= 0.0F && var1 <= 1.0F, "Health must be between 0 and 1");
      this.d = var1;
      this.a(CommonBoss$UpdateAction.UPDATE_HEALTH);
      if(PacketRemapper.b() == null) {
         a(new PacketRemapper[5]);
      }

      return this;
   }

   public axM f() {
      return this.i;
   }

   public a2M a(axM var1) {
      Preconditions.checkNotNull(var1);
      this.i = var1;
      this.a(CommonBoss$UpdateAction.UPDATE_STYLE);
      return this;
   }

   public a2M a(aAP var1) {
      Preconditions.checkNotNull(var1);
      this.h = var1;
      this.a(CommonBoss$UpdateAction.UPDATE_STYLE);
      return this;
   }

   public a2M b(UUID var1) {
      return this.a(Via.b().a(var1));
   }

   public a2M a(bgR var1) {
      PacketRemapper[] var2 = d();
      if(this.e.add(var1) && this.f) {
         this.a(var1, this.a(CommonBoss$UpdateAction.ADD, var1));
      }

      return this;
   }

   public a2M a(UUID var1) {
      return this.b(Via.b().a(var1));
   }

   public a2M b(bgR var1) {
      PacketRemapper[] var2 = d();
      if(this.e.remove(var1)) {
         this.a(var1, this.a(CommonBoss$UpdateAction.REMOVE, var1));
      }

      return this;
   }

   public a2M c(BossFlag var1) {
      d();
      Preconditions.checkNotNull(var1);
      if(!this.a(var1)) {
         this.j.add(var1);
      }

      this.a(CommonBoss$UpdateAction.UPDATE_FLAGS);
      return this;
   }

   public a2M b(BossFlag var1) {
      d();
      Preconditions.checkNotNull(var1);
      if(this.a(var1)) {
         this.j.remove(var1);
      }

      this.a(CommonBoss$UpdateAction.UPDATE_FLAGS);
      return this;
   }

   public boolean a(BossFlag var1) {
      Preconditions.checkNotNull(var1);
      return this.j.contains(var1);
   }

   public Set d() {
      return (Set)this.e.stream().map(a2H::lambda$getPlayers$0).filter(Objects::nonNull).collect(Collectors.toSet());
   }

   public Set j() {
      return Collections.unmodifiableSet(this.e);
   }

   public a2M i() {
      this.a(true);
      return this;
   }

   public a2M h() {
      this.a(false);
      return this;
   }

   public boolean k() {
      return this.f;
   }

   private void a(boolean var1) {
      PacketRemapper[] var2 = d();
      if(this.f != var1) {
         this.f = var1;
         this.a(CommonBoss$UpdateAction.ADD);
      }

   }

   public UUID a() {
      return this.a;
   }

   public UUID c() {
      return this.a;
   }

   public String b() {
      return this.g;
   }

   public float c() {
      return this.d;
   }

   public aAP g() {
      return this.h;
   }

   public Set a() {
      return this.j;
   }

   private void a(CommonBoss$UpdateAction var1) {
      d();
      Iterator var3 = (new ArrayList(this.e)).iterator();
      if(var3.hasNext()) {
         bgR var4 = (bgR)var3.next();
         PacketWrapperImpl var5 = this.a(var1, var4);
         this.a(var4, var5);
      }

   }

   private void a(bgR var1, PacketWrapperImpl var2) {
      PacketRemapper[] var3 = d();
      if(var1.c() != null && var1.c().b().b(aRY.class)) {
         PacketWrapperImpl var10000 = var2;
         Class var10001 = aRY.class;

         try {
            var10000.a(var10001);
         } catch (Exception var5) {
            var5.printStackTrace();
         }

      } else {
         this.e.remove(var1);
      }
   }

   private PacketWrapperImpl a(CommonBoss$UpdateAction param1, bgR param2) {
      // $FF: Couldn't be decompiled
   }

   private int b() {
      d();
      int var2 = 0;
      Iterator var3 = this.j.iterator();
      if(var3.hasNext()) {
         BossFlag var4 = (BossFlag)var3.next();
         var2 |= var4.getId();
      }

      return var2;
   }

   private static UUID lambda$getPlayers$0(bgR var0) {
      return Via.b().b(var0);
   }

   public static void a(PacketRemapper[] var0) {
      c = var0;
   }

   public static PacketRemapper[] d() {
      return c;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      a(new PacketRemapper[2]);
   }
}
