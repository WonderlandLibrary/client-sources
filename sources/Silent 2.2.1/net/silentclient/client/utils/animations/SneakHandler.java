package net.silentclient.client.utils.animations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.mods.render.AnimationsMod;

public class SneakHandler {
    private static final float START_HEIGHT = 1.62f;
    private static final float END_HEIGHT = 1.54f;

    private static final SneakHandler INSTANCE = new SneakHandler();

    private float eyeHeight;
    private float lastEyeHeight;

    public static SneakHandler getInstance() {
        return INSTANCE;
    }

    public float getEyeHeight(float partialTicks) {
        if (!AnimationsMod.getSettingBoolean("Smooth Sneaking")) {
            return eyeHeight;
        }

        return lastEyeHeight + (eyeHeight - lastEyeHeight) * partialTicks;
    }

    @EventTarget
    public void onTick(ClientTickEvent event) {
        lastEyeHeight = eyeHeight;

        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            eyeHeight = START_HEIGHT;
            return;
        }

        if (player.isSneaking()) {
            eyeHeight = END_HEIGHT;
        } else if (!AnimationsMod.getSettingBoolean("Smooth Sneaking")) {
            eyeHeight = START_HEIGHT;
        } else if (eyeHeight < START_HEIGHT) {
            float delta = START_HEIGHT - eyeHeight;
            delta *= 0.4;
            eyeHeight = START_HEIGHT - delta;
        }
    }
}
