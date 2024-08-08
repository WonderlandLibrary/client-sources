package me.zeroeightsix.kami.module.modules.misc;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.combat.VikNetAura;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

/**
 * Created by 086 on 9/04/2018.
 * Updated 24 November 2019 by hub
 */
@Module.Info(name = "AutoLog", description = "Automatically log when in danger or on low health", category = Module.Category.MISC)
public class AutoLog extends Module {

    private Setting<Integer> health = register(Settings.integerBuilder("Health").withRange(0, 36).withValue(6).build());
    private boolean shouldLog = false;
    long lastLog = System.currentTimeMillis();

    @EventHandler
    private Listener<LivingDamageEvent> livingDamageEventListener = new Listener<>(event -> {
        if (mc.player == null) {
            return;
        }
        if (event.getEntity() == mc.player) {
            if (mc.player.getHealth() - event.getAmount() < health.getValue()) {
                log();
            }
        }
    });

    @EventHandler
    private Listener<EntityJoinWorldEvent> entityJoinWorldEventListener = new Listener<>(event -> {
        if (mc.player == null) {
            return;
        }
        if (event.getEntity() instanceof EntityEnderCrystal) {
            Vec3d crystalPos = event.getEntity().getPositionVector();
            if (mc.player.getHealth() - VikNetAura.calculateDamage(crystalPos.x, crystalPos.y, crystalPos.z, mc.player) < health.getValue()) {
                log();
            }
        }
    });

    @Override
    public void onUpdate() {
        if (shouldLog) {
            shouldLog = false;
            if (System.currentTimeMillis() - lastLog < 2000) {
                return;
            }
            Minecraft.getMinecraft().getConnection().handleDisconnect(new SPacketDisconnect(new TextComponentString("AutoLogged")));
        }
    }

    private void log() {
        ModuleManager.getModuleByName("AutoReconnect").disable();
        shouldLog = true;
        lastLog = System.currentTimeMillis();
    }

}
