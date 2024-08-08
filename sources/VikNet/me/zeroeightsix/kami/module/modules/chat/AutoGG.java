package me.zeroeightsix.kami.module.modules.chat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ChatTextUtils;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created 15 November 2019 by hub
 * Updated 24 November 2019 by hub
 * should work with crystals now - cryrobyte
 */
@Module.Info(name = "AutoGG", category = Module.Category.CHAT, description = "Announce killed Players")
public class AutoGG extends Module {

    private ConcurrentHashMap<String, Integer> targetedPlayers = null;

    private Setting<Boolean> toxicMode = register(Settings.b("ToxicMode", false));
    private Setting<Boolean> clientName = register(Settings.b("ClientName", true));
    private Setting<Integer> timeoutTicks = register(Settings.i("TimeoutTicks", 20));

    @Override
    public void onEnable() {
        targetedPlayers = new ConcurrentHashMap<>();
    }

    @Override
    public void onDisable() {
        targetedPlayers = null;
    }

    @Override
    public void onUpdate() {

        if (isDisabled() || mc.player == null) {
            return;
        }

        if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap<>();
        }

        for (Entity entity : mc.world.getLoadedEntityList()) {

            // skip non player entities
            if (!EntityUtil.isPlayer(entity)) {
                continue;
            }
            EntityPlayer player = (EntityPlayer) entity;

            // skip if player is alive
            if (player.getHealth() > 0) {
                continue;
            }

            String name = player.getName();
            if (shouldAnnounce(name)) {
                doAnnounce(name);
                break;
            }

        }

        targetedPlayers.forEach((name, timeout) -> {
            if (timeout <= 0) {
                targetedPlayers.remove(name);
            } else {
                targetedPlayers.put(name, timeout - 1);
            }
        });

    }

    @EventHandler
    public Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {

        if (mc.player == null) {
            return;
        }

        if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap<>();
        }

        // return if packet is not of type CPacketUseEntity
        if (!(event.getPacket() instanceof CPacketUseEntity)) {
            return;
        }
        CPacketUseEntity cPacketUseEntity = ((CPacketUseEntity) event.getPacket());

        // return if action is not of type CPacketUseEntity.Action.ATTACK
        if (!(cPacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK))) {
            return;
        }

        // return if targeted Entity is not a player
        Entity targetEntity = cPacketUseEntity.getEntityFromWorld(mc.world);
        if (!EntityUtil.isPlayer(targetEntity)) {
            return;
        }

        addTargetedPlayer(targetEntity.getName());

    });

    @EventHandler
    public Listener<LivingDeathEvent> livingDeathEventListener = new Listener<>(event -> {

        if (mc.player == null) {
            return;
        }

        if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap<>();
        }
        EntityLivingBase entity = event.getEntityLiving();
        if (entity != null) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;
                if (player.getHealth() <= 0.0F) {
                    String name = player.getName();
                    if (this.shouldAnnounce(name)) {
                        this.doAnnounce(name);
                    }

                }
            }
        }

        // skip non player entities
        if (!EntityUtil.isPlayer(entity)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;

        // skip if player is alive
        if (player.getHealth() > 0) {
            return;
        }

        String name = player.getName();
        if (shouldAnnounce(name)) {
            doAnnounce(name);
        }

    });

    private boolean shouldAnnounce(String name) {
        return targetedPlayers.containsKey(name);
    }

    private void doAnnounce(String name) {

        targetedPlayers.remove(name);

        StringBuilder message = new StringBuilder();

        if (toxicMode.getValue()) {
            message.append("NEXT!");
        } else {
            message.append("GG, Tack VikNet :^)");
        }

        String messageSanitized = message.toString().replaceAll(ChatTextUtils.SECTIONSIGN, "");

        if (messageSanitized.length() > 255) {
            messageSanitized = messageSanitized.substring(0, 255);
        }

        mc.player.connection.sendPacket(new CPacketChatMessage(messageSanitized));

    }

    public void addTargetedPlayer(String name) {

        // skip if self is the target
        if (Objects.equals(name, mc.player.getName())) {
            return;
        }

        if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap<>();
        }

        targetedPlayers.put(name, timeoutTicks.getValue());

    }

}
