package me.travis.wurstplus.module.modules.chat;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.travis.wurstplus.event.events.PacketEvent;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import me.travis.wurstplus.module.ModuleManager;

@Module.Info(name="AutoEZ", category=Module.Category.CHAT)
public class AutoEZ extends Module {
    private ConcurrentHashMap<String, Integer> targetedPlayers = null;
    private Setting<Boolean> greenMode = this.register(Settings.b("Green Text", false));
    private Setting<Boolean> toxicMode = this.register(Settings.b("ToxicMode", false));
    private Setting<Integer> timeoutTicks = this.register(Settings.i("TimeoutTicks", 20));

    @EventHandler
    public Listener<PacketEvent.Send> sendListener = new Listener<PacketEvent.Send>(event -> {
        if (AutoEZ.mc.player == null) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }
        if (!(event.getPacket() instanceof CPacketUseEntity)) {
            return;
        }
        CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
        if (!cPacketUseEntity.getAction().equals((Object)CPacketUseEntity.Action.ATTACK)) {
            return;
        }
        Entity targetEntity = cPacketUseEntity.getEntityFromWorld((World)AutoEZ.mc.world);
        if (!EntityUtil.isPlayer(targetEntity)) {
            return;
        }
        this.addTargetedPlayer(targetEntity.getName());
    }, new Predicate[0]);

    @EventHandler
    public Listener<LivingDeathEvent> livingDeathEventListener = new Listener<LivingDeathEvent>(event -> {
        EntityLivingBase entity;
        if (AutoEZ.mc.player == null) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }
        if ((entity = event.getEntityLiving()) == null) {
            return;
        }
        if (!EntityUtil.isPlayer((Entity)entity)) {
            return;
        }
        EntityPlayer player = (EntityPlayer)entity;
        if (player.getHealth() > 0.0f) {
            return;
        }
        String name = player.getName();
        if (this.shouldAnnounce(name)) {
            this.doAnnounce(name);
        }
    }, new Predicate[0]);

    @Override
    public void onEnable() {
        this.targetedPlayers = new ConcurrentHashMap();
    }

    @Override
    public void onDisable() {
        this.targetedPlayers = null;
    }

    @Override
    public void onUpdate() {
        if (this.isDisabled() || AutoEZ.mc.player == null) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
        }
        for (final Entity entity : AutoEZ.mc.world.getLoadedEntityList()) {
            if (!EntityUtil.isPlayer(entity)) {
                continue;
            }
            final EntityPlayer player = (EntityPlayer)entity;
            if (player.getHealth() > 0.0f) {
                continue;
            }
            final String name2 = player.getName();
            if (this.shouldAnnounce(name2)) {
                this.doAnnounce(name2);
                break;
            }
        }
        this.targetedPlayers.forEach((name, timeout) -> {
            if (timeout <= 0) {
                this.targetedPlayers.remove(name);
            } else {
                this.targetedPlayers.put((String)name, timeout - 1);
            }
        });
    }

    private boolean shouldAnnounce(String name) {
        return this.targetedPlayers.containsKey(name);
    }

    private void doAnnounce(String name) {
        String messageSanitized;
        this.targetedPlayers.remove(name);
        StringBuilder message = new StringBuilder();
        if (this.greenMode.getValue()) {
            message.append("> ");
        }
        if (ModuleManager.getModuleByName("Auto32k").isEnabled()) {
            message.append("Fastest a32k in the west, brought to you by ");
        }
        else if (this.toxicMode.getValue().booleanValue()) {
            message.append("You just got fucking rekt son, all because of ");
        } else {
            message.append("You just got nae nae'd by ");
        }
        message.append("Wurst+");
        if ((messageSanitized = message.toString().replaceAll("\u00a7", "")).length() > 255) {
            messageSanitized = messageSanitized.substring(0, 255);
        }
        AutoEZ.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(messageSanitized));
    }

    public void addTargetedPlayer(String name) {
        if (Objects.equals(name, AutoEZ.mc.player.getName())) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }
        this.targetedPlayers.put(name, this.timeoutTicks.getValue());
    }
}