package cc.novoline.modules.move;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.SlowdownEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.move.Scaffold;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class NoSlow extends AbstractModule {
   @Property("vanilla")
   private final BooleanProperty vanilla = PropertyFactory.booleanFalse();

   public NoSlow(ModuleManager var1) {
      super(var1, "NoSlow", "No Slow", EnumModuleType.MOVEMENT, "No slow down when using items");
      Manager.put(new Setting("NS_VANILLA", "Vanilla", SettingType.CHECKBOX, this, this.vanilla));
   }

   @EventTarget
   public void onTick(TickUpdateEvent var1) {
      this.setSuffix(((Boolean)this.vanilla.get()).booleanValue()?"Vanilla":"Packet");
   }

   @EventTarget
   public void onBlock(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(!((Boolean)this.vanilla.get()).booleanValue() && this.mc.player.isMoving() && this.mc.player.getHeldItem() != null && this.mc.player.getHeldItem().getItem() instanceof ItemSword && this.mc.gameSettings.keyBindUseItem.isKeyDown() && !((KillAura)this.getModule(KillAura.class)).shouldBlock()) {
         if(var1.getState().equals(EventState.PRE)) {
            this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
         }

         this.sendPacket(new C08PacketPlayerBlockPlacement(this.mc.player.getHeldItem()));
      }

   }

   @EventTarget
   public void onSlowDown(SlowdownEvent var1) {
      var1.setCancelled(true);
   }

   public void onEnable() {
      this.setSuffix(((Boolean)this.vanilla.get()).booleanValue()?"Vanilla":"NCP");
   }

   public BooleanProperty a() {
      return this.vanilla;
   }
}
