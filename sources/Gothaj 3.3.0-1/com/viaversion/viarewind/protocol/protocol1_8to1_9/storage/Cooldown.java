package com.viaversion.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.cooldown.CooldownVisualization;
import com.viaversion.viarewind.utils.Tickable;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.util.Pair;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

public class Cooldown extends StoredObject implements Tickable {
   private double attackSpeed = 4.0;
   private long lastHit = 0L;
   private CooldownVisualization.Factory visualizationFactory = CooldownVisualization.Factory.fromConfiguration();
   private CooldownVisualization current;

   public Cooldown(UserConnection user) {
      super(user);
   }

   @Override
   public void tick() {
      if (!this.hasCooldown()) {
         this.endCurrentVisualization();
      } else {
         BlockPlaceDestroyTracker tracker = this.getUser().get(BlockPlaceDestroyTracker.class);
         if (tracker.isMining()) {
            this.lastHit = 0L;
            this.endCurrentVisualization();
         } else {
            if (this.current == null) {
               this.current = this.visualizationFactory.create(this.getUser());
            }

            try {
               this.current.show(this.getCooldown());
            } catch (Exception var3) {
               ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Unable to show cooldown visualization", (Throwable)var3);
            }
         }
      }
   }

   private void endCurrentVisualization() {
      if (this.current != null) {
         try {
            this.current.hide();
         } catch (Exception var2) {
            ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Unable to hide cooldown visualization", (Throwable)var2);
         }

         this.current = null;
      }
   }

   public boolean hasCooldown() {
      long time = System.currentTimeMillis() - this.lastHit;
      double cooldown = this.restrain((double)time * this.attackSpeed / 1000.0, 0.0, 1.5);
      return cooldown > 0.1 && cooldown < 1.1;
   }

   public double getCooldown() {
      long time = System.currentTimeMillis() - this.lastHit;
      return this.restrain((double)time * this.attackSpeed / 1000.0, 0.0, 1.0);
   }

   private double restrain(double x, double a, double b) {
      return x < a ? a : Math.min(x, b);
   }

   public double getAttackSpeed() {
      return this.attackSpeed;
   }

   public void setAttackSpeed(double attackSpeed) {
      this.attackSpeed = attackSpeed;
   }

   public void setAttackSpeed(double base, ArrayList<Pair<Byte, Double>> modifiers) {
      this.attackSpeed = base;

      for (int j = 0; j < modifiers.size(); j++) {
         if (modifiers.get(j).key() == 0) {
            this.attackSpeed = this.attackSpeed + modifiers.get(j).value();
            modifiers.remove(j--);
         }
      }

      for (int jx = 0; jx < modifiers.size(); jx++) {
         if (modifiers.get(jx).key() == 1) {
            this.attackSpeed = this.attackSpeed + base * modifiers.get(jx).value();
            modifiers.remove(jx--);
         }
      }

      for (int jxx = 0; jxx < modifiers.size(); jxx++) {
         if (modifiers.get(jxx).key() == 2) {
            this.attackSpeed = this.attackSpeed * (1.0 + modifiers.get(jxx).value());
            modifiers.remove(jxx--);
         }
      }
   }

   public void hit() {
      this.lastHit = System.currentTimeMillis();
   }

   public void setLastHit(long lastHit) {
      this.lastHit = lastHit;
   }

   public void setVisualizationFactory(CooldownVisualization.Factory visualizationFactory) {
      this.visualizationFactory = Objects.requireNonNull(visualizationFactory, "visualizationFactory");
   }
}
