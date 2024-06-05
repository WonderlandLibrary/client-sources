package club.mega.module.impl.player;

import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.util.*;
import net.minecraft.network.play.client.C03PacketPlayer;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "Test", description = "Test", category = Category.PLAYER)
public class Test extends Module {

    private final TimeUtil timeUtil = new TimeUtil();

    @Handler
    public final void tick(final EventTick event) {
        ChatUtil.sendMessage(MC.thePlayer.rotationPitch);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, MC.thePlayer.posY + 3, MC.thePlayer.posZ, MC.thePlayer.onGround));
        MC.thePlayer.setPosition(MC.thePlayer.posX, MC.thePlayer.posY + 3, MC.thePlayer.posZ);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
