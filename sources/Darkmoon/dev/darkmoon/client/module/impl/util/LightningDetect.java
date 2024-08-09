package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.command.impl.WayCommand;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.utility.misc.ChatUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.util.math.BlockPos;

@ModuleAnnotation(name = "LightningDetect", category = Category.UTIL)
public class LightningDetect extends Module {
    public static ModeSetting lightMode = new ModeSetting("Detect-Mode", "New", "New", "Old");
    public static BooleanSetting autoGps = new BooleanSetting("Auto-GPS", true);
    @EventTarget
    public static void onServerPacket(EventReceivePacket event) {
        if (lightMode.is("New")) {
        if (event.getPacket() instanceof SPacketSpawnGlobalEntity) {
            SPacketSpawnGlobalEntity packet = (SPacketSpawnGlobalEntity) event.getPacket();
            if (packet.getType() == 1 && packet.getEntityId() > 0) {
                int boltX = (int) packet.getX();
                int boltY = (int) packet.getY();
                int boltZ = (int) packet.getZ();
                ChatUtility.addChatMessage("Lightning spawned at: [" + boltX + ", " + boltY + ", " + boltZ + "]");
                if (autoGps.get()) {
                    WayCommand.x = boltX;
                    WayCommand.z = boltZ;
                    WayCommand.gps = true;
                }
            }
        }
        }
    }
}
