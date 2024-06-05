package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.player.Freecam;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import java.util.function.Supplier;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;

public class AutoHead extends AbstractModule {
   private Timer timer = new Timer();
   @Property("health")
   private final DoubleProperty health = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(14.0D)).minimum(Double.valueOf(1.0D))).maximum(Double.valueOf(20.0D));
   @Property("force-absorption")
   private final BooleanProperty force_absorption = PropertyFactory.booleanFalse();
   @Property("check-regen")
   private final BooleanProperty check_regen = PropertyFactory.booleanFalse();

   public AutoHead(ModuleManager var1) {
      super(var1, EnumModuleType.COMBAT, "AutoHead", "Auto Head");
      Manager.put(new Setting("AH_FORCE_ABS", "Force absorption", SettingType.CHECKBOX, this, this.force_absorption));
      Manager.put(new Setting("AH_CHECK_REGEN", "Check Regen", SettingType.CHECKBOX, this, this.check_regen, this::lambda$new$0));
      Manager.put(new Setting("AH_HEALTH", "Health", SettingType.SLIDER, this, this.health, 1.0D, this::lambda$new$1));
   }

   public int headSlot() {
      return this.mc.player.getSlotByItem(Items.skull);
   }

   @EventTarget
   public void onMotion(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState().equals(EventState.PRE) && !ServerUtils.serverIs(Servers.LOBBY) && !ServerUtils.serverIs(Servers.PRE) && this.shouldUse(this.headSlot())) {
         ItemStack var3 = this.mc.player.inventory.getStackInSlot(this.headSlot());
         if(var3 != null && var3.getItem() instanceof ItemSkull && this.timer.delay(500.0D)) {
            this.sendPacketNoEvent(new C09PacketHeldItemChange(this.headSlot()));
            this.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.player.getHeldItem()));
            this.sendPacketNoEvent(new C09PacketHeldItemChange(this.mc.player.inventory.currentItem));
            this.timer.reset();
         }
      }

   }

   private boolean shouldUse(int var1) {
      boolean var10000;
      label0: {
         int[] var2 = Freecam.a();
         if(var1 != -1) {
            if(((Boolean)this.force_absorption.get()).booleanValue()) {
               if(this.mc.player.getAbsorptionAmount() <= 0.0F) {
                  break label0;
               }
            } else if((double)this.mc.player.getHealth() < ((Double)this.health.get()).doubleValue() && (!((Boolean)this.check_regen.get()).booleanValue() || !this.mc.player.isPotionActive(Potion.regeneration))) {
               break label0;
            }
         }

         var10000 = false;
         return var10000;
      }

      var10000 = true;
      return var10000;
   }

   private Boolean lambda$new$1() {
      int[] var1 = Freecam.a();
      return Boolean.valueOf(!((Boolean)this.force_absorption.get()).booleanValue());
   }

   private Boolean lambda$new$0() {
      int[] var1 = Freecam.a();
      return Boolean.valueOf(!((Boolean)this.force_absorption.get()).booleanValue());
   }
}
