package xyz.cucumber.base.module.feat.other;

import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.OTHER,
   description = "Attacks only allowed players",
   name = "Reverse Friends",
   priority = ArrayPriority.LOW
)
public class ReverseFriendsModule extends Mod {
   public static CopyOnWriteArrayList<String> allowed = new CopyOnWriteArrayList<>();
   public boolean pressed;
   public NumberSettings range = new NumberSettings("Range", 5.0, 3.0, 50.0, 0.25);

   public ReverseFriendsModule() {
      this.addSettings(new ModuleSettings[]{this.range});
   }

   @Override
   public void onEnable() {
      this.pressed = false;
   }

   @EventListener
   public void onTick(EventTick e) {
      if (this.mc.gameSettings.keyBindPickBlock.isKeyDown()) {
         this.pressed = true;
      } else if (this.pressed) {
         this.pressed = false;
         Entity entity = RotationUtils.rayTrace(this.range.getValue(), new float[]{this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch});
         if (entity != null && entity instanceof EntityPlayer && entity.getName() != null && entity.getName() != "") {
            if (allowed.contains(entity.getName())) {
               allowed.remove(entity.getName());
               Client.INSTANCE.getCommandManager().sendChatMessage("Target " + entity.getName() + " was removed");
            } else {
               allowed.add(entity.getName());
               Client.INSTANCE.getCommandManager().sendChatMessage("Target " + entity.getName() + " was added");
            }
         }
      }
   }
}
