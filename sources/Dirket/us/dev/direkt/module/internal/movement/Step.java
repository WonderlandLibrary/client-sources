package us.dev.direkt.module.internal.movement;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.internal.movement.speed.Speed;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "Step", category = ModCategory.MOVEMENT)
public class Step extends ToggleableModule {

    public static boolean cancelSomePackets;
    private static int groundTicks;
    private int stepTicks;

    public static void doStep(Entity e, double par3) {
        if (!Wrapper.getMinecraft().isSingleplayer() && Direkt.getInstance().getModuleManager().getModule(Step.class).isRunning() && Wrapper.getPlayer().isCollidedVertically && par3 > -0.5 && (par3 == 0 || par3 == -0.3125 || par3 == -0.1875 || par3 == -0.0625 || par3 == -0.125 || par3 == -0.25 || par3 == -0.375) && Wrapper.getWorld() != null) {
            //New NCP Step
            Step.cancelSomePackets = true;
            Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + (.41999998688698 * (par3 + Wrapper.getPlayer().stepHeight)), Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
            Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + (.7531999805212 * (par3 + Wrapper.getPlayer().stepHeight)), Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
            Step.groundTicks = 0;
        }
    }

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>((event) -> {
        if (!Direkt.getInstance().getModuleManager().getModule(Speed.class).isSpeeding())
            if (Wrapper.getPlayer() != null && Wrapper.getPlayer().onGround || (Wrapper.getPlayer().isCollidedVertically))
                Wrapper.getPlayer().stepHeight = 1F;
            else
                Wrapper.getPlayer().stepHeight = 0.5F;

        if (cancelSomePackets)
            this.stepTicks++;
        if (this.stepTicks >= 3) {
            if (Direkt.getInstance().getModuleManager().getModule(Timer.class).isRunning())
                Wrapper.getMinecraft().getTimer().timerSpeed = 0.45F;
            else
                Wrapper.getMinecraft().getTimer().timerSpeed = 0.35F;
            cancelSomePackets = false;
            this.stepTicks = 0;
        } else if (!Direkt.getInstance().getModuleManager().getModule(Timer.class).isRunning() && !Direkt.getInstance().getModuleManager().getModule(Speed.class).isSpeeding())
            Wrapper.getMinecraft().getTimer().timerSpeed = 1.0F;
    }, new PacketFilter<>(CPacketPlayer.class));

    @Override
    public void onEnable() {
        if (Wrapper.getPlayer() != null)
            Wrapper.getPlayer().stepHeight = 1F;
    }

    @Override
    public void onDisable() {
        Wrapper.getMinecraft().getTimer().timerSpeed = 1F;
        Wrapper.getPlayer().stepHeight = 0.6F;
    }
}
