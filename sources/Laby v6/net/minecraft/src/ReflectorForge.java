package net.minecraft.src;

import java.util.Map;
import net.minecraft.util.ResourceLocation;

public class ReflectorForge
{
    public static void FMLClientHandler_trackBrokenTexture(ResourceLocation p_FMLClientHandler_trackBrokenTexture_0_, String p_FMLClientHandler_trackBrokenTexture_1_)
    {
        if (!Reflector.FMLClientHandler_trackBrokenTexture.exists())
        {
            Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
            Reflector.call(object, Reflector.FMLClientHandler_trackBrokenTexture, new Object[] {p_FMLClientHandler_trackBrokenTexture_0_, p_FMLClientHandler_trackBrokenTexture_1_});
        }
    }

    public static void FMLClientHandler_trackMissingTexture(ResourceLocation p_FMLClientHandler_trackMissingTexture_0_)
    {
        if (!Reflector.FMLClientHandler_trackMissingTexture.exists())
        {
            Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
            Reflector.call(object, Reflector.FMLClientHandler_trackMissingTexture, new Object[] {p_FMLClientHandler_trackMissingTexture_0_});
        }
    }

    public static void putLaunchBlackboard(String p_putLaunchBlackboard_0_, Object p_putLaunchBlackboard_1_)
    {
        Map map = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);

        if (map != null)
        {
            map.put(p_putLaunchBlackboard_0_, p_putLaunchBlackboard_1_);
        }
    }
}
