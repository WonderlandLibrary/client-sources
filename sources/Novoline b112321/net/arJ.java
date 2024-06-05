package net;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.UW;
import net.VN;
import net.a0p;
import net.a2V;
import net.a6d;
import net.aE3;
import net.aE8;
import net.aEs;
import net.aEu;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.apK;
import net.arQ;
import net.as0;
import net.au7;
import net.ava;
import net.avq;
import net.axu;
import net.aye;
import net.d3;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;

public final class arJ extends as0 {
   private final List C = new CopyOnWriteArrayList();
   @VN("render-distance")
   private final aE8 E = (aE8)((aE8)axu.b(Integer.valueOf(192)).d(Integer.valueOf(4))).c(Integer.valueOf(256));
   @VN("tag-font")
   private final aEs B = axu.a("Client").a(new String[]{"Client", "Vanilla"});
   @VN("content")
   private final aE3 y = axu.a((Object)"Distance").a((Object[])(new String[]{"Distance", "Armor"}));
   @VN("additions")
   private final aE3 z = axu.a((Object)"Background").a((Object[])(new String[]{"Background", "Health"}));
   @VN("health-type")
   private final aEs A = axu.a("Bar").a(new String[]{"Bar", "Value"});
   @VN("background-alpha")
   private final aE8 x = (aE8)((aE8)axu.b(Integer.valueOf(100)).d(Integer.valueOf(50))).c(Integer.valueOf(255));
   @VN("only-targets")
   private final aEu D = axu.g();
   private d3 F = new d3();

   public arJ(UW var1) {
      super(var1, "Nametags", "Nametags", 0, a2V.VISUALS);
      ae9.a(new adZ("NAMETAGS_FONT", "Nametags Font", a6d.COMBOBOX, this, this.B));
      ae9.a(new adZ("NAMETAGS_RENDER_DIST", "Render distance", a6d.SLIDER, this, this.E, 4.0D));
      ae9.a(new adZ("NAMETAGS_CONTENT", "Content", a6d.SELECTBOX, this, this.y));
      ae9.a(new adZ("NAMETAGS_ADDITIONS", "Additions", a6d.SELECTBOX, this, this.z));
      ae9.a(new adZ("NAMETAGS_BG_ALPHA", "Background Alpha", a6d.SLIDER, this, this.x, 5.0D, this::lambda$new$0));
      ae9.a(new adZ("NAMETAGS_HEALTH", "Health Type", a6d.COMBOBOX, this, this.A, this::lambda$new$1));
      ae9.a(new adZ("ONLY_TAR", "Only targets", a6d.CHECKBOX, this, this.D));
   }

   public void c() {
      this.C.clear();
   }

   private a0p a(EntityLivingBase var1) {
      return (a0p)this.C.stream().filter(arJ::lambda$getPlayerByEntity$2).findFirst().orElse((Object)null);
   }

   @agu
   public void a(apK var1) {
      int var2 = ava.e();
      if(var1.a() != ((avq)this.b(avq.class)).b()) {
         var1.setCancelled(this.j.k().a(var1.a().getName()) == au7.TARGET || this.w.thePlayer.getDistanceToEntity(var1.a()) <= (float)((Integer)this.E.a()).intValue() && !((Boolean)this.D.a()).booleanValue() && !(var1.a() instanceof EntityArmorStand) || ((arQ)this.b(arQ.class)).y() && ((List)((arQ)this.b(arQ.class)).b().a()).contains("Name"));
      }
   }

   @agu
   public void a(aSt var1) {
      this.C.forEach(a0p::c);
   }

   @agu
   private void a(aye var1) {
      Stream var10000 = this.w.theWorld.getLoadedEntityList().stream();
      EntityPlayer.class.getClass();
      var10000 = var10000.filter(EntityPlayer.class::isInstance).filter(arJ::lambda$onRender$3).filter(Entity::isEntityAlive);
      EntityLivingBase.class.getClass();
      var10000.map(EntityLivingBase.class::cast).filter(this::lambda$onRender$4).forEach(this::lambda$onRender$5);
      this.C.forEach(this::lambda$onRender$6);
   }

   public List g() {
      return this.C;
   }

   public aE8 d() {
      return this.E;
   }

   public aE3 a() {
      return this.y;
   }

   public aE3 e() {
      return this.z;
   }

   public aEs c() {
      return this.A;
   }

   public aE8 f() {
      return this.x;
   }

   public aEu b() {
      return this.D;
   }

   private void lambda$onRender$6(aye var1, a0p var2) {
      int var3 = ava.h();
      if(!a0p.a(var2).isEntityAlive() || this.w.thePlayer.getDistanceToEntity(a0p.a(var2)) > (float)((Integer)this.E.a()).intValue() && gZ.g().k().a(a0p.a(var2).getName()) != au7.TARGET) {
         this.C.remove(var2);
      }

      if(!this.w.theWorld.getLoadedEntityList().contains(a0p.a(var2)) || ((Boolean)this.D.a()).booleanValue() && gZ.g().k().a(a0p.a(var2).getName()) != au7.TARGET || a0p.a(var2).getEntityId() == this.w.thePlayer.getEntityId() || a0p.a(var2).getDisplayName().getFormattedText().contains("NPC") || a0p.a(var2).getDisplayName().getUnformattedText().equalsIgnoreCase(a0p.a(var2).getName())) {
         this.C.remove(var2);
      }

      float var4 = (float)(a0p.a(var2).lastTickPosX + (a0p.a(var2).posX - a0p.a(var2).lastTickPosX) * (double)var1.a() - this.w.getRenderManager().h);
      float var5 = (float)(a0p.a(var2).lastTickPosY + 2.3D + (a0p.a(var2).posY + 2.3D - (a0p.a(var2).lastTickPosY + 2.3D)) * (double)var1.a() - this.w.getRenderManager().g);
      float var6 = (float)(a0p.a(var2).lastTickPosZ + (a0p.a(var2).posZ - a0p.a(var2).lastTickPosZ) * (double)var1.a() - this.w.getRenderManager().m);
      a0p.a(var2, a0p.a(var2, (double)var4, (double)var5, (double)var6));
   }

   private void lambda$onRender$5(EntityLivingBase var1) {
      this.C.add(new a0p(this, var1));
   }

   private boolean lambda$onRender$4(EntityLivingBase var1) {
      int var2 = ava.h();
      return !this.C.contains(this.a(var1));
   }

   private static boolean lambda$onRender$3(Entity var0) {
      int var1 = ava.e();
      return !var0.isInvisible();
   }

   private static boolean lambda$getPlayerByEntity$2(EntityLivingBase var0, a0p var1) {
      return a0p.a(var1).equals(var0);
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(((List)this.z.a()).contains("Health"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(((List)this.z.a()).contains("Background"));
   }

   static Minecraft d(arJ var0) {
      return var0.w;
   }

   static aEs a(arJ var0) {
      return var0.B;
   }

   static Minecraft c(arJ var0) {
      return var0.w;
   }

   static Minecraft b(arJ var0) {
      return var0.w;
   }
}
