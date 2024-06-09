package org.spongepowered.asm.launch;

import java.util.*;
import java.io.*;
import net.minecraft.launchwrapper.*;

public class MixinTweaker implements ITweaker
{
    public MixinTweaker() {
        super();
        MixinBootstrap.start();
    }
    
    public final void acceptOptions(final List<String> list, final File file, final File file2, final String s) {
        MixinBootstrap.doInit(list);
    }
    
    public final void injectIntoClassLoader(final LaunchClassLoader launchClassLoader) {
        MixinBootstrap.inject();
    }
    
    public String getLaunchTarget() {
        return MixinBootstrap.getPlatform().getLaunchTarget();
    }
    
    public String[] getLaunchArguments() {
        return new String[0];
    }
}
