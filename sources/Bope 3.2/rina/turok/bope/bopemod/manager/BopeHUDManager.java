package rina.turok.bope.bopemod.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import rina.turok.bope.bopemod.guiscreen.hud.BopeArmorPreview;
//import rina.turok.bope.bopemod.guiscreen.hud.BopeArrayList;
import rina.turok.bope.bopemod.guiscreen.hud.BopeCoordinates;
import rina.turok.bope.bopemod.guiscreen.hud.BopeCrystalCount;
import rina.turok.bope.bopemod.guiscreen.hud.BopeEXPCount;
import rina.turok.bope.bopemod.guiscreen.hud.BopeGappleCount;
import rina.turok.bope.bopemod.guiscreen.hud.BopeInventoryPreview;
import rina.turok.bope.bopemod.guiscreen.hud.BopeNotifyClient;
import rina.turok.bope.bopemod.guiscreen.hud.BopeServerInfo;
import rina.turok.bope.bopemod.guiscreen.hud.BopeSurroundPreview;
import rina.turok.bope.bopemod.guiscreen.hud.BopeTotemCount;
import rina.turok.bope.bopemod.guiscreen.hud.BopeUser;
import rina.turok.bope.bopemod.guiscreen.hud.BopeWatermark;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;

public class BopeHUDManager {
   private String tag;
   public static ArrayList array_hud = new ArrayList();
   public BopeNotifyClient notify_client;

   public BopeHUDManager(String tag) {
      this.tag = tag;
      this.notify_client = new BopeNotifyClient();
      this.add_component_pinnable(this.notify_client);
      this.add_component_pinnable(new BopeInventoryPreview());
      this.add_component_pinnable(new BopeSurroundPreview());
      this.add_component_pinnable(new BopeArmorPreview());
      this.add_component_pinnable(new BopeCrystalCount());
      this.add_component_pinnable(new BopeCoordinates());
      this.add_component_pinnable(new BopeGappleCount());
      this.add_component_pinnable(new BopeTotemCount());
      this.add_component_pinnable(new BopeServerInfo());
      this.add_component_pinnable(new BopeWatermark());
      //this.add_component_pinnable(new BopeArrayList());
      this.add_component_pinnable(new BopeEXPCount());
      this.add_component_pinnable(new BopeUser());
      array_hud.sort(Comparator.comparing(BopePinnable::get_title));
   }

   public void add_component_pinnable(BopePinnable module) {
      array_hud.add(module);
   }

   public ArrayList get_array_huds() {
      return array_hud;
   }

   public void render() {
      Iterator var1 = this.get_array_huds().iterator();

      while(var1.hasNext()) {
         BopePinnable pinnables = (BopePinnable)var1.next();
         if (pinnables.is_active()) {
            pinnables.render();
            pinnables.update();
            pinnables.fix_screen();
         }
      }

   }

   public BopePinnable get_pinnable_with_tag(String tag) {
      BopePinnable pinnable_requested = null;
      Iterator var3 = this.get_array_huds().iterator();

      while(var3.hasNext()) {
         BopePinnable pinnables = (BopePinnable)var3.next();
         if (pinnables.get_tag().equalsIgnoreCase(tag)) {
            pinnable_requested = pinnables;
         }
      }

      return pinnable_requested;
   }

   public String get_tag() {
      return this.tag;
   }
}

