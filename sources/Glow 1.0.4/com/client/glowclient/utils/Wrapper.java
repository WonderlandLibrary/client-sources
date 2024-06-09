package com.client.glowclient.utils;

import net.minecraft.client.*;
import org.apache.logging.log4j.*;
import net.minecraftforge.fml.client.*;

public interface Wrapper
{
    public static final Logger logger = LogManager.getLogger("GlowClient");
    public static final Minecraft mc = FMLClientHandler.instance().getClient();
}
