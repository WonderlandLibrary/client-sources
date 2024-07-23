package io.github.liticane.monoxide.module.impl.player;

import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.value.impl.ModeValue;

import io.github.liticane.monoxide.component.impl.BalanceComponent;
import io.github.liticane.monoxide.component.ComponentManager;
import net.minecraft.network.play.client.C03PacketPlayer;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;

import java.util.function.Supplier;

@ModuleData(name = "Regen", description = "Regenerates health faster", category = ModuleCategory.MISCELLANEOUS)
public class RegenModule extends Module {

    public ModeValue mode = new ModeValue("Mode", this, new String[]{"Normal", "Polar", "Grim"});

    private final NumberValue<Integer> health = new NumberValue<>("Health", this, 15, 1, 19, 0, new Supplier[]{() -> mode.is("Normal")}),
            packets = new NumberValue<>("Packets", this, 10, 1, 100, 0, new Supplier[]{() -> mode.is("Normal") || mode.is("Grim")});

    private BalanceComponent balancer;

    public RegenModule() {
        this.balancer = ComponentManager.getInstance().getByClass(BalanceComponent.class);
    }

    @Override
    public String getSuffix() {
        return mode.getValue();
    }

    @Listen
    public void onUpdateMotionEvent(UpdateMotionEvent event) {
        if (event.getType() == UpdateMotionEvent.Type.MID) {
            switch (mode.getValue()) {
                case "Normal":
                    if (mc.thePlayer.getHealth() < health.getValue().floatValue() && mc.thePlayer.getHealth() > 0) {
                        for (int i = 0; i < packets.getValue(); i++) {
                            this.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
                        }
                    }
                    break;
                case "Grim":
                    if (mc.thePlayer.hurtTime > 0) {
                        for (int i = 0; i < packets.getValue(); i++) {
                            sendPacketUnlogged(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.prevPosX, mc.thePlayer.prevPosY, mc.thePlayer.prevPosZ, mc.thePlayer.prevRotationYaw, mc.thePlayer.prevRotationPitch, mc.thePlayer.prevPosY % 0.015625 == 0));
                        }
                    }
                    break;
            }
        }
    }
}