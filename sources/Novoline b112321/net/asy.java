package net;

import cc.novoline.events.events.EventState;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEE;
import net.aEu;
import net.aG1;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.asx;
import net.au7;
import net.axu;
import net.d3;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Mouse;

public final class asy extends as0 {
   private static float D;
   private static float I;
   private static Entity K;
   private static List C = new ObjectArrayList(0);
   private final d3 B = new d3();
   private final Comparator M = Comparator.comparingDouble(asy::lambda$new$0);
   private int H;
   @VN("reach")
   private final aEE J = (aEE)((aEE)axu.a(Double.valueOf(3.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(6.0D));
   @VN("aim-delay")
   private final aEE G = (aEE)((aEE)axu.a(Double.valueOf(0.0D)).d(Double.valueOf(0.0D))).c(Double.valueOf(1000.0D));
   @VN("height")
   private final aEE F = (aEE)((aEE)axu.a(Double.valueOf(3.5D)).d(Double.valueOf(-8.0D))).c(Double.valueOf(8.0D));
   @VN("horizontal-left")
   private final aEE x = (aEE)((aEE)axu.a(Double.valueOf(1.0D)).d(Double.valueOf(0.0D))).c(Double.valueOf(5.0D));
   @VN("horizontal-right")
   private final aEE z = (aEE)((aEE)axu.a(Double.valueOf(1.0D)).d(Double.valueOf(0.0D))).c(Double.valueOf(5.0D));
   @VN("vertical-up")
   private final aEE N = (aEE)((aEE)axu.a(Double.valueOf(1.0D)).d(Double.valueOf(0.0D))).c(Double.valueOf(5.0D));
   @VN("vertical-down")
   private final aEE L = (aEE)((aEE)axu.a(Double.valueOf(1.0D)).d(Double.valueOf(0.0D))).c(Double.valueOf(5.0D));
   @VN("only-axe-sword")
   private final aEu E = axu.g();
   @VN("ray-trace")
   private final aEu y = axu.g();
   @VN("click-aim")
   private final aEu A = axu.g();

   public asy(UW var1) {
      super(var1, "AimAssist", "Aim Assist", 0, a2V.COMBAT, "Helps you to aim");
      ae9.a(new adZ("REACH", "Reach", a6d.SLIDER, this, this.J, 0.1D));
      ae9.a(new adZ("AIM_DELAY", "Aim Delay", a6d.SLIDER, this, this.G, 50.0D));
      ae9.a(new adZ("HEIGHT", "Height", a6d.SLIDER, this, this.F, 1.0D));
      ae9.a(new adZ("H_LEFT", "Horizontal Left", a6d.SLIDER, this, this.x, 0.1D));
      ae9.a(new adZ("H_RIGHT", "Horizontal Right", a6d.SLIDER, this, this.z, 0.1D));
      ae9.a(new adZ("V_UP", "Vertical Up", a6d.SLIDER, this, this.N, 0.1D));
      ae9.a(new adZ("V_DOWN", "Vertical Down", a6d.SLIDER, this, this.L, 0.1D));
      ae9.a(new adZ("ONLY_AXESWORD", "Only Sword/Axe", a6d.CHECKBOX, this, this.E));
      ae9.a(new adZ("RAYTRACE", "Raytrace", a6d.CHECKBOX, this, this.y));
      ae9.a(new adZ("CLICKAIM", "Click Aim", a6d.CHECKBOX, this, this.A));
   }

   private List c() {
      return (List)this.w.theWorld.getLoadedEntityList().stream().filter(this::lambda$loadTargets$1).sorted(Comparator.comparingDouble(this::lambda$loadTargets$2).reversed()).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   private boolean a(Entity var1) {
      int[] var2 = asx.Q();
      return var1 != this.w.thePlayer && var1.isEntityAlive() && !this.j.k().b(var1.getName(), au7.FRIEND) && var1 instanceof EntityPlayer;
   }

   @agu
   private void a(aG1 var1) {
      int[] var2 = asx.Q();
      if(var1.h().equals(EventState.PRE)) {
         C = this.c();
         C.sort(this.M);
         if(K instanceof EntityPlayer || K instanceof EntityMob || K instanceof EntityAnimal) {
            K = null;
         }

         if(this.w.thePlayer.ticksExisted % 50 == 0 && C.size() > 1) {
            ++this.H;
         }

         if(((Boolean)this.A.a()).booleanValue() && !Mouse.isButtonDown(0)) {
            return;
         }

         if(!C.isEmpty()) {
            if(this.H >= C.size()) {
               this.H = 0;
            }

            K = (Entity)C.get(this.H);
            double[] var3 = b(K);
            if(this.B.a(((Double)this.G.a()).doubleValue())) {
               I = (float)(var3[1] + ((Double)this.F.a()).doubleValue());
               D = (float)var3[0];
               this.B.b();
            }

            boolean var4 = !((Boolean)this.E.a()).booleanValue();
            if(((Boolean)this.y.a()).booleanValue()) {
               if(!this.w.thePlayer.canEntityBeSeen(K) || !var4 && !(this.w.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(this.w.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                  return;
               }

               this.b();
            }

            if(var4) {
               this.b();
            }

            if(this.w.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword || this.w.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe) {
               this.b();
            }
         }
      }

   }

   private void b() {
      asx.Q();
      EntityPlayerSP var2 = this.w.thePlayer;
      float var3 = var2.rotationYaw;
      float var4 = var2.rotationPitch;
      if(var3 < D) {
         var2.rotationYaw = (float)((double)var2.rotationYaw + ((Double)this.z.a()).doubleValue());
      }

      if(var2.rotationYaw > D) {
         var2.rotationYaw = (float)((double)var2.rotationYaw - ((Double)this.x.a()).doubleValue());
      }

      if(var4 < I) {
         var2.rotationPitch = (float)((double)var2.rotationPitch + ((Double)this.L.a()).doubleValue());
      }

      if(var2.rotationPitch > I) {
         var2.rotationPitch = (float)((double)var2.rotationPitch - ((Double)this.N.a()).doubleValue());
      }

   }

   public static double[] b(Entity var0) {
      return null;
   }

   private double lambda$loadTargets$2(Object var1) {
      return (double)((Entity)var1).getDistanceToEntity(this.w.thePlayer);
   }

   private boolean lambda$loadTargets$1(Entity var1) {
      int[] var2 = asx.Q();
      return (double)this.w.thePlayer.getDistanceToEntity(var1) <= ((Double)this.J.a()).doubleValue() && this.a(var1);
   }

   private static double lambda$new$0(Entity var0) {
      return b(var0)[0];
   }
}
