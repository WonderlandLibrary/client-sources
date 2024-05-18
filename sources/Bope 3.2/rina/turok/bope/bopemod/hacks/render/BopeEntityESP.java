package rina.turok.bope.bopemod.hacks.render;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeEntityESP extends BopeModule {
   BopeSetting hostile = this.create("Hostile", "EntityESPHostile", true);
   BopeSetting pigs_ani = this.create("Animals & Pigies", "EntityESPAnimals", true);
   BopeSetting crystal = this.create("Crystals", "EntityESPCrystal", true);
   BopeSetting item = this.create("Items", "EntityESPItems", true);
   BopeSetting render_1 = this.create("Render Entity", "EntityESPRenderEntity", "Chams", this.combobox(new String[]{"Chams", "Outline", "Disabled"}));
   BopeSetting disp = this.create("Distance Render", "EntityESPDistanceRender", 6, 0, 10);
   BopeSetting range = this.create("Range", "EntityESPRange", 200, 0, 200);
   public static float distance_player = 0.0F;

   public BopeEntityESP() {
      super(BopeCategory.BOPE_RENDER);
      this.name = "Entity ESP";
      this.tag = "EntityESP";
      this.description = "Entity ESP - Extra Sensory Perception.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void render(BopeEventRender event) {
      Iterator var2 = this.mc.world.loadedEntityList.iterator();

      while(true) {
         while(true) {
            Entity entity;
            do {
               do {
                  do {
                     if (!var2.hasNext()) {
                        return;
                     }

                     entity = (Entity)var2.next();
                  } while(entity == this.mc.player);
               } while(this.mc.player.getDistance(entity) >= (float)this.range.get_value(1));
            } while(distance_player <= (float)this.disp.get_value(1));

            if (entity instanceof EntityItem && this.item.get_value(true)) {
               entity.setGlowing(true);
            } else if (entity instanceof EntityItem && !this.item.get_value(true)) {
               entity.setGlowing(false);
            }
         }
      }
   }

   public void disable() {
      Iterator var1 = this.mc.world.loadedEntityList.iterator();

      while(var1.hasNext()) {
         Entity entity = (Entity)var1.next();
         if (entity instanceof EntityItem && this.item.get_value(true)) {
            entity.setGlowing(false);
         }
      }

   }
}
