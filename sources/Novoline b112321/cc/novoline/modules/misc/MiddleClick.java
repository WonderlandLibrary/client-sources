package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.misc.WindowedFullscreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.lwjgl.input.Mouse;

public class MiddleClick extends AbstractModule {
   @Property("pearl")
   private final BooleanProperty pearl = PropertyFactory.booleanTrue();
   @Property("friend")
   private final BooleanProperty friend = PropertyFactory.booleanTrue();
   @Property("down-place")
   private final BooleanProperty A = PropertyFactory.booleanFalse();
   private boolean down = true;

   public MiddleClick(ModuleManager var1) {
      super(var1, EnumModuleType.MISC, "MiddleClick", "Middle Click");
      Manager.put(new Setting("MC_FRIEND", "Friend", SettingType.CHECKBOX, this, this.friend));
      Manager.put(new Setting("MC_PEARL", "Pearl", SettingType.CHECKBOX, this, this.pearl));
      Manager.put(new Setting("MC_DOWN_PLACE", "Down Place", SettingType.CHECKBOX, this, this.A));
   }

   private int pearlSlot() {
      return this.mc.player.getSlotByItem(Items.ender_pearl);
   }

   private void sendHeldItemChange(int var1) {
      String[] var2 = WindowedFullscreen.a();
      if(this.mc.player.inventory.currentItem != this.pearlSlot()) {
         this.sendPacketNoEvent(new C09PacketHeldItemChange(var1));
      }

   }

   @EventTarget
   public void onPre(MotionUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getState().equals(EventState.PRE)) {
         if(Mouse.isButtonDown(2)) {
            if(!this.down) {
               return;
            }

            label37: {
               if(this.mc.objectMouseOver.entityHit == null || !(this.mc.objectMouseOver.entityHit instanceof EntityPlayer) || !((Boolean)this.friend.get()).booleanValue()) {
                  if(this.pearlSlot() == -1 || !((Boolean)this.pearl.get()).booleanValue()) {
                     break label37;
                  }

                  this.sendHeldItemChange(this.pearlSlot());
                  this.sendPacket(new C08PacketPlayerBlockPlacement(this.mc.player.getHeldItem()));
                  this.sendHeldItemChange(this.mc.player.inventory.currentItem);
               }

               EntityPlayer var3 = (EntityPlayer)this.mc.objectMouseOver.entityHit;
               if(this.novoline.getPlayerManager().hasType(var3.getName(), PlayerManager$EnumPlayerType.FRIEND)) {
                  this.mc.player.c(".f remove " + var3.getName());
               }

               this.mc.player.c(".f add " + var3.getName());
            }

            this.down = false;
         }

         this.down = true;
      }

   }

   public boolean b() {
      String[] var1 = WindowedFullscreen.a();
      return this.isEnabled() && ((Boolean)this.A.get()).booleanValue();
   }
}
