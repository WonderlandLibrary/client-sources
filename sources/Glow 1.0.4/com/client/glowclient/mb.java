package com.client.glowclient;

import java.util.*;
import net.minecraftforge.fml.client.config.*;
import net.minecraftforge.common.config.*;
import net.minecraft.client.gui.*;

@Deprecated
public abstract class MB extends GuiConfig
{
    private static List<IConfigElement> M(final Configuration configuration, final String s) {
        return (List<IConfigElement>)new ConfigElement(configuration.getCategory(s)).getChildElements();
    }
    
    public MB(final GuiScreen guiScreen, final String s, final Configuration configuration, final String s2) {
        final List<IConfigElement> m = M(configuration, s2);
        final boolean b = false;
        super(guiScreen, (List)m, s, b, b, GuiConfig.getAbridgedConfigPath(configuration.toString()));
    }
}
