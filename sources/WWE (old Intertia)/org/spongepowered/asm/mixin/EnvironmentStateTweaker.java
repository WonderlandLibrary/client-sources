package org.spongepowered.asm.mixin;

import java.util.*;
import java.io.*;
import net.minecraft.launchwrapper.*;
import org.spongepowered.asm.launch.*;

public class EnvironmentStateTweaker implements ITweaker
{
    public EnvironmentStateTweaker() {
        super();
    }
    
    public void acceptOptions(final List<String> list, final File file, final File file2, final String s) {
    }
    
    public void injectIntoClassLoader(final LaunchClassLoader launchClassLoader) {
        MixinBootstrap.getPlatform().inject();
    }
    
    public String getLaunchTarget() {
        return "";
    }
    
    public String[] getLaunchArguments() {
        MixinEnvironment.gotoPhase(MixinEnvironment.Phase.DEFAULT);
        return new String[0];
    }
}
