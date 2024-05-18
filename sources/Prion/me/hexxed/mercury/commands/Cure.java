package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;








public class Cure
  extends Command
{
  public Cure()
  {
    super("cure", "cure");
  }
  
  public void execute(String[] args)
  {
    if (getMinecraftthePlayer.isPotionActive(Potion.blindness))
      getMinecraftthePlayer.removePotionEffect(blindnessid);
    if (getMinecraftthePlayer.isPotionActive(Potion.confusion))
      getMinecraftthePlayer.removePotionEffect(confusionid);
    if (getMinecraftthePlayer.isPotionActive(Potion.digSlowdown))
      getMinecraftthePlayer.removePotionEffect(digSlowdownid);
    if (((getMinecraftthePlayer.isPotionActive(Potion.poison)) || (getMinecraftthePlayer.isPotionActive(Potion.wither)) || (getMinecraftthePlayer.isPotionActive(Potion.moveSlowdown)) || (getMinecraftthePlayer.isPotionActive(Potion.digSlowdown)) || (getMinecraftthePlayer.isPotionActive(Potion.harm)) || (getMinecraftthePlayer.isPotionActive(Potion.hunger)) || (getMinecraftthePlayer.isPotionActive(Potion.weakness))) && (shouldCure()))
    {
      for (int i = 0; i < 2100; i++) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer());
      }
    }
  }
  
  private boolean shouldCure() {
    return ((getMinecraftthePlayer.onGround) || (getMinecraftthePlayer.capabilities.isCreativeMode)) && (getMinecraftthePlayer.getFoodStats().getFoodLevel() > 18) && (!getMinecraftthePlayer.isEating());
  }
}
