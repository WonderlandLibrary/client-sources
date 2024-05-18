package de.labystudio.cosmetic;

import de.labystudio.labymod.LabyMod;
import net.minecraft.client.Minecraft;

public class CosmeticTick
{
    public static float LOCAL_XMAS_YAW = 0.0F;
    public static float LOCAL_XMAS_TICK_VALUE = 0.0F;
    public static float LOCAL_XMAS_FPS_VALUE = 0.0F;

    public static void tickAllCosmetics()
    {
        if (LabyMod.getInstance().isInGame())
        {
            CosmeticUser cosmeticuser = LabyMod.getInstance().getCosmeticManager().getCosmeticUser(Minecraft.getMinecraft().thePlayer);

            if (cosmeticuser != null)
            {
                if (cosmeticuser.getEnumList().contains(EnumCosmetic.XMAS))
                {
                    float f = Minecraft.getMinecraft().thePlayer.rotationYaw;

                    if (f != LOCAL_XMAS_YAW)
                    {
                        LOCAL_XMAS_TICK_VALUE += Math.abs(f - LOCAL_XMAS_YAW) / 190.0F;
                    }

                    if (LOCAL_XMAS_TICK_VALUE > 0.0F)
                    {
                        LOCAL_XMAS_TICK_VALUE -= 0.15F;
                    }

                    if (LOCAL_XMAS_TICK_VALUE > 1.0F)
                    {
                        LOCAL_XMAS_TICK_VALUE = 1.0F;
                    }

                    if (LOCAL_XMAS_TICK_VALUE < 0.0F)
                    {
                        LOCAL_XMAS_TICK_VALUE = 0.0F;
                    }

                    LOCAL_XMAS_YAW = f;
                }
            }
        }
    }
}
