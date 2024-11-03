package xyz.cucumber.base.module.feat.other;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.cmds.FriendsCommand;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.OTHER,
   description = "Disallows to attack friends",
   name = "Friends",
   priority = ArrayPriority.LOW
)
public class FriendsModule extends Mod {
   public boolean pressed;
   public BooleanSettings midClickAdd = new BooleanSettings("Mid click add", false);
   public NumberSettings range = new NumberSettings("Range", () -> this.midClickAdd.isEnabled(), 5.0, 3.0, 50.0, 0.25);

   public FriendsModule() {
      this.addSettings(new ModuleSettings[]{this.midClickAdd, this.range});
   }

   @Override
   public void onEnable() {
      this.pressed = false;
   }

   @EventListener
   public void onTick(EventTick e) {
      if (this.midClickAdd.isEnabled()) {
         if (this.mc.gameSettings.keyBindPickBlock.isKeyDown()) {
            this.pressed = true;
         } else if (this.pressed) {
            this.pressed = false;
            Entity entity = RotationUtils.rayTrace(this.range.getValue(), new float[]{this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch});
            if (entity != null && entity instanceof EntityPlayer && entity.getName() != null && entity.getName() != "") {
               if (FriendsCommand.friends.contains(entity.getName())) {
                  FriendsCommand.friends.remove(entity.getName());
                  Client.INSTANCE.getCommandManager().sendChatMessage("Friend " + entity.getName() + " was removed");
               } else {
                  FriendsCommand.friends.add(entity.getName());
                  Client.INSTANCE.getCommandManager().sendChatMessage("Friend " + entity.getName() + " was added");
               }
            }
         }
      }
   }
}
