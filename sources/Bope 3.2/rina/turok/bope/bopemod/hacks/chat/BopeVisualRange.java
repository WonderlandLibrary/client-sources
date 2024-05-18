package rina.turok.bope.bopemod.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeVisualRange extends BopeModule {
   private List people;
   private List peoplenew;

   public BopeVisualRange() {
      super(BopeCategory.BOPE_CHAT, false);
      this.name = "Visual Range";
      this.tag = "VisualRange";
      this.description = "Add who joins to visual range.";
      this.release("B.O.P.E - module - B.O.P.E");
   }

   public void enable() {
      this.people = new ArrayList();
   }

   public void update() {
      if (!(this.mc.world == null | this.mc.player == null)) {
         this.peoplenew = new ArrayList();
         List playerEntities = this.mc.world.playerEntities;
         Iterator var2 = playerEntities.iterator();

         while(var2.hasNext()) {
            Entity e = (Entity)var2.next();
            if (!e.getName().equals(this.mc.player.getName())) {
               this.peoplenew.add(e.getName());
            }
         }

         if (this.peoplenew.size() > 0) {
            var2 = this.peoplenew.iterator();

            while(var2.hasNext()) {
               String name = (String)var2.next();
               if (!this.people.contains(name)) {
                  BopeMessage.send_client_message(ChatFormatting.WHITE + name + ChatFormatting.BLUE);
                  this.people.add(name);
               }
            }
         }

      }
   }
}
