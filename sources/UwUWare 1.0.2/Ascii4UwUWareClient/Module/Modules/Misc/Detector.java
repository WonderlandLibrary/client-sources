package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Combat.AntiBot;
import Ascii4UwUWareClient.UI.Notification.Notification;
import Ascii4UwUWareClient.UI.Notification.NotificationManager;
import Ascii4UwUWareClient.UI.Notification.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Iterator;

public class Detector extends Module {
    EntityLivingBase player;

    public Detector() {
        super("Detector", new String[]{}, ModuleType.Misc);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        for (Iterator<Entity> people = Minecraft.theWorld.loadedEntityList.iterator(); people.hasNext(); ) {
            Object gottem = people.next();
            if (gottem instanceof EntityPlayer) {
                EntityPlayer aPlayer = (EntityPlayer) gottem;
                if (aPlayer != Minecraft.thePlayer) {
                    this.player = aPlayer;
                    if (this.player.ticksExisted > 50) {
                        if (this.player.rotationPitch < -90 || this.player.rotationPitch > 90)
                            NotificationManager.show(new Notification(NotificationType.WARNING, "Cheater Detection:", player.getName() + " is rotated in an invalid direction!", 7));
                        if (!this.player.onGround && !AntiBot.isBot(player) && !AntiBot.isHypixelSpinBot((EntityPlayer) player) && Minecraft.thePlayer.ticksExisted % 1000 == 0){
                            NotificationManager.show(new Notification(NotificationType.WARNING, "Cheater Detection:", player.getName() + " is Flying / not on ground For too long!", 7));
                        }
                    }
                }
            }
        }
    }

}