package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;

public class RenderTransformer {
    public static float checkPerspective() {
        return Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1;
    }
}
