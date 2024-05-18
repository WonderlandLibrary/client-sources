package de.labystudio.utils;

import net.minecraft.entity.Entity;

public class OldSneaking
{
    private static long sneak = 0L;
    private static boolean is = false;
    private static int value = 0;

    public static float getCustomEyeHeight(Entity entity)
    {
        if (is != entity.isSneaking() || sneak <= 0L)
        {
            sneak = System.currentTimeMillis();
        }

        is = entity.isSneaking();
        float f = 1.62F;

        if (entity.isSneaking())
        {
            int i = (int)(sneak + 8L - System.currentTimeMillis());

            if (i > -50)
            {
                f = f + (float)((double)i * 0.0017D);

                if (f < 0.0F || f > 10.0F)
                {
                    f = 1.54F;
                }
            }
            else
            {
                f = (float)((double)f - 0.08D);
            }
        }
        else
        {
            int j = (int)(sneak + 8L - System.currentTimeMillis());

            if (j > -50)
            {
                f = f - (float)((double)j * 0.0017D);
                f = (float)((double)f - 0.08D);

                if (f < 0.0F)
                {
                    f = 1.62F;
                }
            }
            else
            {
                f = f - 0.0F;
            }
        }

        return f;
    }
}
