package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.ShroomClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class C {

    public static final Reflections reflections = new Reflections(
        ShroomClient.class.getPackageName()
    );
    public static final Logger logger = LoggerFactory.getLogger(
        "shroomclientnextgen"
    );

    public static final MinecraftClient mc = MinecraftClient.getInstance();

    public static ClientPlayerEntity p() {
        return mc.player;
    }

    public static ClientWorld w() {
        return mc.world;
    }

    public static boolean isInGame() {
        return p() != null && w() != null;
    }
}
