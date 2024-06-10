package xyz.gucciclient.utils;

import java.util.*;
import net.minecraft.client.entity.*;

public class RotationUtils
{
    public static void jitter(final Random rand) {
        if (rand.nextBoolean()) {
            if (rand.nextBoolean()) {
                final EntityPlayerSP player5;
                final EntityPlayerSP player = player5 = Wrapper.getPlayer();
                player5.rotationPitch -= (float)(rand.nextFloat() * 0.5);
            }
            else {
                final EntityPlayerSP player6;
                final EntityPlayerSP player2 = player6 = Wrapper.getPlayer();
                player6.rotationPitch += (float)(rand.nextFloat() * 0.5);
            }
        }
        else if (rand.nextBoolean()) {
            final EntityPlayerSP player7;
            final EntityPlayerSP player3 = player7 = Wrapper.getPlayer();
            player7.rotationYaw -= (float)(rand.nextFloat() * 0.5);
        }
        else {
            final EntityPlayerSP player8;
            final EntityPlayerSP player4 = player8 = Wrapper.getPlayer();
            player8.rotationYaw += (float)(rand.nextFloat() * 0.5);
        }
    }
}
