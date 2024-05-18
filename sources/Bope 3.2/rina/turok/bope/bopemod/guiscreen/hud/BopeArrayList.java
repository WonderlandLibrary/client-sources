/*package rina.turok.bope.bopemod.guiscreen.hud;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;

public class BopeArrayList extends BopePinnable {
   public BopeArrayList() {
      super("Array List", "ArrayList", 1.0F, 0, 0);
   }

   public void render() {
      if (this.is_on_gui()) {
         this.background();
      }

      int position_update_y = 2;
      Comparator comparator = (first, second) -> {
         String first_name = first.get_tag() + (first.detail_option() == null ? "" : Bope.g + " [" + Bope.r + first.detail_option() + Bope.g + "]" + Bope.r);
         String second_name = second.get_tag() + (second.detail_option() == null ? "" : Bope.g + " [" + Bope.r + second.detail_option() + Bope.g + "]" + Bope.r);
         float diff = (float)(this.get(second_name, "width") - this.get(first_name, "width"));
         if (this.get_dock_y()) {
            return diff != 0.0F ? (int)diff : second_name.compareTo(first_name);
         } else {
            return (int)diff;
         }
      };
      List pretty_modules = (List)Bope.get_module_manager().get_array_modules().stream().filter(module -> {
         return module.is_active();
      }).filter(module -> {
         return module.to_show();
      }).sorted(comparator).collect(Collectors.toList());
      Iterator var4 = pretty_modules.iterator();

      while(var4.hasNext()) {
         BopeModule modules = (BopeModule)var4.next();
         if (!modules.get_category().get_tag().equals("BopeGUI")) {
            String module_name = modules.get_tag() + (modules.detail_option() == null ? "" : Bope.g + " [" + Bope.r + modules.detail_option() + Bope.g + "]" + Bope.r);
            this.create_line(module_name, 1, position_update_y);
            position_update_y += this.get(module_name, "height") + 2;
            if (this.get(module_name, "width") >= this.get_width()) {
               this.set_width(this.get(module_name, "width") + 2);
            }

            this.set_height(position_update_y);
         }
      }

   }
} */
