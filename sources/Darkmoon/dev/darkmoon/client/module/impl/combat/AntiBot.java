package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ModuleAnnotation(name = "AntiBot", category = Category.COMBAT)
public class AntiBot extends Module {
    public static List<Entity> bots = new ArrayList<>();

    @EventTarget
    public void onUpdate(EventUpdate event) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (!player.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(StandardCharsets.UTF_8))) && player instanceof EntityOtherPlayerMP) {
                if (!bots.contains(player)) {
                    bots.add(player);
                }
            }
            if (!player.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(StandardCharsets.UTF_8))) && player.isInvisible() && player instanceof EntityOtherPlayerMP) {
                mc.world.removeEntity(player);
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        bots.clear();
    }
}
