package dev.excellent.client.module.impl.combat;

import com.google.common.collect.Lists;
import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.BooleanValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.concurrent.CopyOnWriteArrayList;

@ModuleInfo(name = "Anti Bot", description = "Игнорирует ботов.", category = Category.COMBAT)
public class AntiBot extends Module {
    public static Singleton<AntiBot> singleton = Singleton.create(() -> Module.link(AntiBot.class));
    public static final CopyOnWriteArrayList<PlayerEntity> bots = Lists.newCopyOnWriteArrayList();

    public final BooleanValue remove = new BooleanValue("Удалять из мира", this, false);

    @Override
    protected void onEnable() {
        super.onEnable();
        bots.clear();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        bots.clear();
    }

    private final Listener<UpdateEvent> onUpdate = event -> {
        for (PlayerEntity entity : mc.world.getPlayers()) {
            if (!entity.getUniqueID().equals(PlayerEntity.getOfflineUUID(entity.getName().getString()))) {
                if (!bots.contains(entity)) {
                    bots.add(entity);
                }
            }
        }

        if (remove.getValue()) {
            try {
                mc.world.getPlayers().removeIf(bots::contains);
            } catch(Exception ignored) {
            }
        }
    };

    public static boolean contains(LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            return bots.contains(entity);
        }
        return false;
    }

    public static boolean isEmpty() {
        return bots.isEmpty();
    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> bots.clear();
    private final Listener<WorldLoadEvent> onWorldLoad = event -> bots.clear();

}