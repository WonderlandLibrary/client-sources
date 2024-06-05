package net;

import cc.novoline.events.events.UpdateModelEvent;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;

public class bgP {
   public static ModelPlayer a(UpdateModelEvent var0) {
      return var0.getModelPlayer();
   }

   public static Entity b(UpdateModelEvent var0) {
      return var0.getEntity();
   }
}
