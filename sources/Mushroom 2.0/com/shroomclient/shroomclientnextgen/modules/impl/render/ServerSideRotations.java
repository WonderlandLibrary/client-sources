package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;

@RegisterModule(
    name = "Server Side Rotations",
    uniqueId = "serverrots",
    description = "Shows Your Server Side Rotations (In F5)",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class ServerSideRotations extends Module {

    public static float serverPitch = 0;

    public static float getServerPitch() {
        if (ModuleManager.isEnabled(ServerSideRotations.class)) {
            return serverPitch;
        }

        return C.p().getPitch();
    }

    @SubscribeEvent
    public void onMotion(MotionEvent.Post e) {
        if (C.p() != null) {
            C.p().setHeadYaw(e.getYaw());
            C.p().setBodyYaw(e.getYaw());

            serverPitch = e.getPitch();
        }
    }

    @Override
    protected void onEnable() {
        if (ModuleManager.isEnabled(AntiAim.class)) ModuleManager.setEnabled(
            AntiAim.class,
            false,
            false
        );
    }

    @Override
    protected void onDisable() {}
}
