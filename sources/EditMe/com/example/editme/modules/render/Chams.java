package com.example.editme.modules.render;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.setting.SettingsManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@Module.Info(
   name = "Chams",
   category = Module.Category.RENDER,
   description = "See entities through walls"
)
public class Chams extends Module {
   private static Setting animals = SettingsManager.b("Animals", false);
   private static Setting mobs = SettingsManager.b("Mobs", false);
   private static Setting players = SettingsManager.b("Players", true);

   public static boolean renderChams(Entity var0) {
      return var0 instanceof EntityPlayer ? (Boolean)players.getValue() : (EntityUtil.isPassive(var0) ? (Boolean)animals.getValue() : (Boolean)mobs.getValue());
   }

   public Chams() {
      this.registerAll(new Setting[]{players, animals, mobs});
   }
}
