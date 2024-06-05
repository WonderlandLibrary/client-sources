package cc.novoline.modules.misc;

import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.modules.move.FastSneak;
import cc.novoline.modules.player.ChestStealer;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Keyboard;

public final class GuiMove extends AbstractModule {
   @Property("sneak")
   private final BooleanProperty sneak = PropertyFactory.createBoolean(Boolean.valueOf(false));

   public GuiMove(ModuleManager var1) {
      super(var1, "GuiMove", "Gui Move", EnumModuleType.MISC, "Allows you to walk with an opened GUI");
      Manager.put(new Setting("GM_SNEAK", "Sneak", SettingType.CHECKBOX, this, this.sneak));
   }

   public void updatePlayerMoveState() {
      WindowedFullscreen.a();
      MovementInput var2 = this.mc.player.movementInput();
      if(this.isEnabled(ChestStealer.class) && ((Boolean)((ChestStealer)this.getModule(ChestStealer.class)).a().get()).booleanValue() && this.mc.currentScreen instanceof GuiChest) {
         var2.setMoveStrafe(0.0F);
         var2.setMoveForward(0.0F);
      } else {
         var2.setMoveStrafe(0.0F);
         var2.setMoveForward(0.0F);
         if(Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode())) {
            var2.setMoveForward(var2.getMoveForward() + 1.0F);
         }

         if(Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode())) {
            var2.setMoveForward(var2.getMoveForward() - 1.0F);
         }

         if(Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode())) {
            var2.setMoveStrafe(var2.getMoveStrafe() + 1.0F);
         }

         if(Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode())) {
            var2.setMoveStrafe(var2.getMoveStrafe() - 1.0F);
         }

         var2.a(Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode()));
         var2.setSneak(((Boolean)this.sneak.get()).booleanValue() && Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode()) || this.mc.gameSettings.keyBindSneak.isKeyDown());
         if(var2.sneak() && !this.isEnabled(FastSneak.class)) {
            var2.setMoveStrafe(var2.getMoveStrafe() * 0.3F);
            var2.setMoveForward(var2.getMoveForward() * 0.3F);
         }

      }
   }
}
