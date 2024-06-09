package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;

public class NoItemRender extends Module {
   public NoItemRender(ModuleData data) {
      super(data);
   }

   public void onEvent(Event event) {
      Iterator it = mc.theWorld.loadedEntityList.iterator();

      while(it.hasNext()) {
         Entity ent = (Entity)it.next();
         if (ent instanceof EntityItem) {
            mc.theWorld.removeEntity(ent);
         }
      }

   }
}
