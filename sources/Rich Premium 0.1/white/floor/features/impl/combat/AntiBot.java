package white.floor.features.impl.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import clickgui.setting.Setting;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.util.text.TextFormatting;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventAttack;
import white.floor.event.event.EventPreMotionUpdate;
import white.floor.event.event.EventReceivePacket;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.friend.FriendManager;
import white.floor.helpers.movement.MovementHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class AntiBot extends Feature {
    public static ArrayList<Entity> entete = new ArrayList();
    private UUID detectedEntity;

    public AntiBot() {
        super("AntiBot", "Delete gays.", 0, Category.COMBAT);
    }

    @Override
    public void setup() {
        Main.instance.settingsManager.rSetting(new Setting("Invisibles remove", this, false));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.isBot();
    }

    public void isBot() {
        this.setModuleName("AntiBot");

        if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(AntiBot.class), "Invisibles remove").getValBoolean()) {
            for (Object entity : mc.world.loadedEntityList)
                if (((Entity) entity).isInvisible() && entity != mc.player)
                    mc.world.removeEntity((Entity) entity);
        }
    }

    @EventTarget
    public void onMouse(EventAttack event) {
        if (isToggled()) {
                EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;
                String name = entityPlayer.getName();

                if (entityPlayer != null) {
                    if (FriendManager.getFriends().isFriend(entityPlayer.getName()))
                        return;

                    if (entete.contains(entityPlayer)) {
                        NotificationPublisher.queue(name  , "already in a-b.",  NotificationType.WARNING);
                    } else {
                        NotificationPublisher.queue(name , "added in a-b.",  NotificationType.SUCCESS);
                    }
                }
            }
        }

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (mc.player != null) {
                return;
            }
            if (!Main.instance.settingsManager.getSettingByName("Invisible Remove").getValBoolean()
                    || entity instanceof EntityPlayer && entity.isInvisible())
                continue;
            mc.world.removeEntity(entity);
            NotificationPublisher.queue(TextFormatting.GRAY + name, "Removed invisible " + entity.getName(),  NotificationType.INFO);
        }
    }

    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}
