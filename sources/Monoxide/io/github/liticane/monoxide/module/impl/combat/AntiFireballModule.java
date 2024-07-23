package io.github.liticane.monoxide.module.impl.combat;

import io.github.liticane.monoxide.listener.event.minecraft.game.PostTickEvent;
import io.github.liticane.monoxide.listener.event.minecraft.input.ClickingEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.util.math.time.TimeHelper;
import io.github.liticane.monoxide.value.impl.NumberValue;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ModuleData(name = "AntiFireball", description = "Automatically deflects fireballs", category = ModuleCategory.COMBAT)
public class AntiFireballModule extends Module {

    private final ModeValue swingMode = new ModeValue("Swing Mode", this, new String[]{"Normal", "Packet", "None"});
    private final NumberValue<Float> range = new NumberValue<>("Range", this, 6.0F, 3.0F, 6.0F, 1);

    private final TimeHelper timeHelper = new TimeHelper();
    private List<EntityFireball> ballsack = new ArrayList<>();

    @Listen
    public void onTick(PostTickEvent event) {

        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        ballsack = mc.theWorld.loadedEntityList

                .stream()

                .filter(entity -> entity instanceof EntityFireball)

                .map(entity -> (EntityFireball) entity)

                .filter(ballsack -> mc.thePlayer.getDistanceToEntity(ballsack) < range.getValue())

                .collect(Collectors.toList());
    }

    @Listen
    public void onClicking(ClickingEvent event) {
        for (EntityFireball ball : ballsack)
            if (timeHelper.hasReached(300)) {
                switch (swingMode.getValue()) {
                    case "Packet":
                        sendPacket(new C0APacketAnimation());
                        break;
                    case "Normal":
                        mc.thePlayer.swingItem();
                        break;
                }

                sendPacket(new C02PacketUseEntity(ball, C02PacketUseEntity.Action.ATTACK));
                timeHelper.reset();
            }
    }

}