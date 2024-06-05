package net;

import net.Eb;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;

public class yF implements Eb {
   private ModelPlayer b;
   private Entity a;

   public yF(Entity var1, ModelPlayer var2) {
      this.b = var2;
      this.a = var1;
   }

   public Entity b() {
      return this.a;
   }

   public ModelPlayer a() {
      return this.b;
   }
}
