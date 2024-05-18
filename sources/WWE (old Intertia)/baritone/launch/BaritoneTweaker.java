package baritone.launch;

import io.github.impactdevelopment.simpletweaker.*;
import org.spongepowered.asm.launch.*;
import java.util.*;
import net.minecraft.launchwrapper.*;
import java.util.function.*;
import org.spongepowered.asm.mixin.*;

public class BaritoneTweaker extends SimpleTweaker
{
    public BaritoneTweaker() {
        super();
    }
    
    public void injectIntoClassLoader(final LaunchClassLoader launchClassLoader) {
        super.injectIntoClassLoader(launchClassLoader);
        MixinBootstrap.init();
        final List list = Launch.blackboard.get("TweakClasses");
        String obfuscationContext = "notch";
        if (list.stream().anyMatch(BaritoneTweaker::lambda$injectIntoClassLoader$0)) {
            obfuscationContext = "searge";
        }
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext(obfuscationContext);
        Mixins.addConfiguration("mixins.baritone.json");
    }
    
    private static boolean lambda$injectIntoClassLoader$0(final String s) {
        return s.contains("net.minecraftforge.fml.common.launcher");
    }
}
