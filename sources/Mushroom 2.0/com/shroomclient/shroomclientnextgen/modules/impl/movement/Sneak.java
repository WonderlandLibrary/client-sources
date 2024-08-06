package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

@RegisterModule(
    name = "Sneak",
    uniqueId = "sneak",
    description = "Allows You To Sneak At Full Speed",
    category = ModuleCategory.Movement
)
public class Sneak extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Vanilla;

    Boolean OnGround = false;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        switch (mode) {
            case NCP -> {
                if (e.isSneaking()) {
                    sentSneak = false;
                    PacketUtil.sendPacket(
                        new ClientCommandC2SPacket(
                            C.p(),
                            ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY
                        ),
                        false
                    );
                }
            }
        }
    }

    boolean sentSneak = false;

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Post e) {
        switch (mode) {
            case NCP -> {
                if (e.isSneaking()) {
                    sentSneak = true;
                    PacketUtil.sendPacket(
                        new ClientCommandC2SPacket(
                            C.p(),
                            ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY
                        ),
                        false
                    );
                }
            }
        }
    }

    public static boolean shouldSneakNormalSpeed() {
        return ModuleManager.isEnabled(Sneak.class);
    }

    public enum Mode {
        Vanilla,
        NCP,
    }
}
