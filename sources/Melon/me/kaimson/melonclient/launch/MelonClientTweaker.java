package me.kaimson.melonclient.launch;

import com.google.common.collect.*;
import java.io.*;
import java.util.*;
import net.minecraft.launchwrapper.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import org.apache.logging.log4j.*;

public class MelonClientTweaker implements ITweaker
{
    private final List<String> launchArguments;
    public static final Logger logger;
    
    public MelonClientTweaker() {
        this.launchArguments = (List<String>)Lists.newArrayList();
    }
    
    public void acceptOptions(final List<String> args, final File gameDir, final File assetsDir, final String profile) {
        this.launchArguments.addAll(args);
        if (!args.contains("--version") && profile != null) {
            this.launchArguments.add("--version");
            this.launchArguments.add(profile);
        }
        if (!args.contains("--assetDir") && assetsDir != null) {
            this.launchArguments.add("--assetDir");
            this.launchArguments.add(assetsDir.getAbsolutePath());
        }
        if (!args.contains("--gameDir") && gameDir != null) {
            this.launchArguments.add("--gameDir");
            this.launchArguments.add(gameDir.getAbsolutePath());
        }
    }
    
    public void injectIntoClassLoader(final LaunchClassLoader classLoader) {
        MixinBootstrap.init();
        final MixinEnvironment env = MixinEnvironment.getDefaultEnvironment();
        Mixins.addConfiguration("mixins.melonclient.json");
        if (env.getObfuscationContext() == null) {
            env.setObfuscationContext("notch");
        }
        env.setSide(MixinEnvironment.Side.CLIENT);
        MelonClientTweaker.logger.info("Registering transformers");
        classLoader.registerTransformer("me.kaimson.melonclient.asm.CameraTransformer");
    }
    
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }
    
    public String[] getLaunchArguments() {
        return this.launchArguments.toArray(new String[0]);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
