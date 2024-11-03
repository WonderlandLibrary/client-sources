package net.silentclient.client.mixin;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.silentclient.client.Client;
import net.silentclient.client.mixin.transformer.NVGClassTransformer;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SilentClientTweaker implements ITweaker {
    private List<String> launchArgs = new ArrayList<>();
    public static boolean hasOptifine = false;

    @Override
    public final void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile)
    {
        Client.logger.info("Silent Client Tweak Loader: Options");
        try {
            Class.forName("optifine.OptiFineTweaker");
            hasOptifine = true;
        }
        catch(ClassNotFoundException e) {
        }
        Client.logger.info("Optifine loaded: " + hasOptifine);

        if(!hasOptifine) {
            this.launchArgs.addAll(args);

            if (!args.contains("--version") && profile != null)
            {
                launchArgs.add("--version");
                launchArgs.add(profile);
            }

            if (assetsDir != null) {
                launchArgs.add("--assetsDir");
                launchArgs.add(assetsDir.getAbsolutePath());
            }

            if (gameDir != null) {
                launchArgs.add("--gameDir");
                launchArgs.add(gameDir.getAbsolutePath());
            }
        } else {
            this.launchArgs = new ArrayList();
        }
    }

    @Override
    public final void injectIntoClassLoader(LaunchClassLoader classLoader)
    {
        Client.logger.info("Silent Client Tweak Loader: Injecting into class loader");
        MixinBootstrap.init();
        MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();
        Mixins.addConfiguration("mixins.SilentClient.json");
        if (environment.getObfuscationContext() == null)
        {
            environment.setObfuscationContext("notch");
        }
        environment.setSide(MixinEnvironment.Side.CLIENT);
        classLoader.registerTransformer("net.silentclient.client.mixin.SilentClientTransformer");
        classLoader.registerTransformer(NVGClassTransformer.class.getName());
        unlockLwjgl();
    }

    private void unlockLwjgl() {
        try {
            Field transformerExceptions = LaunchClassLoader.class.getDeclaredField("classLoaderExceptions");
            transformerExceptions.setAccessible(true);
            Object o = transformerExceptions.get(Launch.classLoader);
            ((Set<String>) o).remove("org.lwjgl.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLaunchTarget()
    {
        Client.logger.info("Silent Client Tweak Loader: Launch target");
        return MixinBootstrap.getPlatform().getLaunchTarget();
    }

    @Override
    public String[] getLaunchArguments()
    {
        Client.logger.info("Silent Client Tweak Loader: Launch Args");
        return launchArgs.toArray(new String[0]);
    }
}
