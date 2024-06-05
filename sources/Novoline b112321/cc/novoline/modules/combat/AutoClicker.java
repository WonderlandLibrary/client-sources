package cc.novoline.modules.combat;

import cc.novoline.events.EventTarget;
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
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.utils.Timer;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public final class AutoClicker extends AbstractModule {
   private final Timer timer = new Timer();
   @Property("minimum-cps")
   private final IntProperty minimumCps = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(8)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(20));
   @Property("maximum-cps")
   private final IntProperty maximumCps = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(10)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(20));
   @Property("only-swords-tools")
   private final BooleanProperty onlySwordsTools = PropertyFactory.booleanTrue();

   public AutoClicker(ModuleManager var1) {
      super(var1, "AutoClicker", "Auto Clicker", 0, EnumModuleType.COMBAT);
      Manager.put(new Setting("MINRAND", "Min CPS", SettingType.SLIDER, this, this.minimumCps, 1.0D));
      Manager.put(new Setting("MAXRAND", "Max CPS", SettingType.SLIDER, this, this.maximumCps, 1.0D));
      Manager.put(new Setting("ONLY_SWORDAXE", "Only Swords/Tools", SettingType.CHECKBOX, this, this.onlySwordsTools));
   }

   @EventTarget
   public void onTick(TickUpdateEvent var1) {
      int[] var2 = KillAura.Q();
      if(this.mc.gameSettings.keyBindAttack.isKeyDown() && (!((Boolean)this.onlySwordsTools.get()).booleanValue() || this.mc.player.getHeldItem() != null && (this.mc.player.getHeldItem().getItem() instanceof ItemTool || this.mc.player.getHeldItem().getItem() instanceof ItemSword)) && this.timer.delay((double)(1000 / ThreadLocalRandom.current().nextInt(((Integer)this.minimumCps.get()).intValue(), ((Integer)this.maximumCps.get()).intValue() + 1)))) {
         this.mc.player.swingItem();
         if(this.mc.objectMouseOver.entityHit != null) {
            this.mc.at.b((EntityPlayer)this.mc.player, (Entity)this.mc.objectMouseOver.entityHit);
         }

         this.timer.reset();
      }

   }
}
