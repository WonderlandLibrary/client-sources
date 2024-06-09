package com.client.glowclient;

import com.google.common.base.*;
import java.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;

public class wB
{
    public static final int d = 2;
    private static final List<ModContainer> L;
    public static final String A = "http://mc.lunatri.us/json?v=%d&mc=%s&limit=5";
    public static final String B = "https://mods.io/mods?author=Lunatrius";
    private static final Joiner b;
    
    static {
        L = new ArrayList<ModContainer>();
        b = Joiner.on('\n');
    }
    
    public static List M() {
        return wB.L;
    }
    
    public static void M() {
        new Xb("LunatriusCore Version Check").start();
    }
    
    public static boolean M(final String s) {
        return ForgeModContainer.getConfig().get("version_checking", s, false).getBoolean();
    }
    
    public static void M(final ModContainer modContainer, final String s) {
        wB.L.add(modContainer);
        final ModMetadata metadata = modContainer.getMetadata();
        if (metadata.description != null) {
            final StringBuilder sb = new StringBuilder();
            final ModMetadata modMetadata = metadata;
            modMetadata.description = sb.append(modMetadata.description).append("\n---\nCompiled against Forge ").append(s).toString();
        }
    }
    
    public wB() {
        super();
    }
    
    public static Joiner M() {
        return wB.b;
    }
}
