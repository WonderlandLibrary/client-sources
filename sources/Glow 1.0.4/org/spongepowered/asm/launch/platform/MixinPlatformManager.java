package org.spongepowered.asm.launch.platform;

import java.util.*;
import org.spongepowered.asm.service.*;
import java.io.*;
import java.net.*;
import org.spongepowered.asm.mixin.*;
import org.apache.logging.log4j.*;

public class MixinPlatformManager
{
    private static final String DEFAULT_MAIN_CLASS = "net.minecraft.client.main.Main";
    private static final String MIXIN_TWEAKER_CLASS = "org.spongepowered.asm.launch.MixinTweaker";
    private static final Logger logger;
    private final Map<URI, MixinContainer> containers;
    private MixinContainer primaryContainer;
    private boolean prepared;
    private boolean injected;
    
    public MixinPlatformManager() {
        super();
        this.containers = new LinkedHashMap<URI, MixinContainer>();
        this.prepared = false;
    }
    
    public void init() {
        MixinPlatformManager.logger.debug("Initialising Mixin Platform Manager");
        try {
            final URI uri = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            if (uri != null) {
                MixinPlatformManager.logger.debug("Mixin platform: primary container is {}", new Object[] { uri });
                this.primaryContainer = this.addContainer(uri);
            }
        }
        catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        this.scanClasspath();
    }
    
    public Collection<String> getPhaseProviderClasses() {
        final Collection<String> phaseProviders = this.primaryContainer.getPhaseProviders();
        if (phaseProviders != null) {
            return (Collection<String>)Collections.unmodifiableCollection((Collection<?>)phaseProviders);
        }
        return (Collection<String>)Collections.emptyList();
    }
    
    public final MixinContainer addContainer(final URI uri) {
        final MixinContainer mixinContainer = this.containers.get(uri);
        if (mixinContainer != null) {
            return mixinContainer;
        }
        MixinPlatformManager.logger.debug("Adding mixin platform agents for container {}", new Object[] { uri });
        final MixinContainer mixinContainer2 = new MixinContainer(this, uri);
        this.containers.put(uri, mixinContainer2);
        if (this.prepared) {
            mixinContainer2.prepare();
        }
        return mixinContainer2;
    }
    
    public final void prepare(final List<String> list) {
        this.prepared = true;
        final Iterator<MixinContainer> iterator = this.containers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().prepare();
        }
        if (list != null) {
            this.parseArgs(list);
        }
        else {
            final String property = System.getProperty("sun.java.command");
            if (property != null) {
                this.parseArgs(Arrays.asList(property.split(" ")));
            }
        }
    }
    
    private void parseArgs(final List<String> list) {
        boolean equals = false;
        for (final String s : list) {
            if (equals) {
                this.addConfig(s);
            }
            equals = "--mixin".equals(s);
        }
    }
    
    public final void inject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        if (this.primaryContainer != null) {
            this.primaryContainer.initPrimaryContainer();
        }
        this.scanClasspath();
        MixinPlatformManager.logger.debug("inject() running with {} agents", new Object[] { this.containers.size() });
        for (final MixinContainer mixinContainer : this.containers.values()) {
            try {
                mixinContainer.inject();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void scanClasspath() {
        for (final URL url : MixinService.getService().getClassProvider().getClassPath()) {
            try {
                final URI uri = url.toURI();
                if (!this.containers.containsKey(uri)) {
                    MixinPlatformManager.logger.debug("Scanning {} for mixin tweaker", new Object[] { uri });
                    if ("file".equals(uri.getScheme()) && new File(uri).exists()) {
                        if ("org.spongepowered.asm.launch.MixinTweaker".equals(MainAttributes.of(uri).get("TweakClass"))) {
                            MixinPlatformManager.logger.debug("{} contains a mixin tweaker, adding agents", new Object[] { uri });
                            this.addContainer(uri);
                        }
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public String getLaunchTarget() {
        final Iterator<MixinContainer> iterator = this.containers.values().iterator();
        while (iterator.hasNext()) {
            final String launchTarget = iterator.next().getLaunchTarget();
            if (launchTarget != null) {
                return launchTarget;
            }
        }
        return "net.minecraft.client.main.Main";
    }
    
    final void setCompatibilityLevel(final String s) {
        try {
            final MixinEnvironment.CompatibilityLevel value = MixinEnvironment.CompatibilityLevel.valueOf(s.toUpperCase());
            MixinPlatformManager.logger.debug("Setting mixin compatibility level: {}", new Object[] { value });
            MixinEnvironment.setCompatibilityLevel(value);
        }
        catch (IllegalArgumentException ex) {
            MixinPlatformManager.logger.warn("Invalid compatibility level specified: {}", new Object[] { s });
        }
    }
    
    final void addConfig(String substring) {
        if (substring.endsWith(".json")) {
            MixinPlatformManager.logger.debug("Registering mixin config: {}", new Object[] { substring });
            Mixins.addConfiguration(substring);
        }
        else if (substring.contains(".json@")) {
            final int index = substring.indexOf(".json@");
            final String substring2 = substring.substring(index + 6);
            substring = substring.substring(0, index + 5);
            final MixinEnvironment.Phase forName = MixinEnvironment.Phase.forName(substring2);
            if (forName != null) {
                MixinPlatformManager.logger.warn("Setting config phase via manifest is deprecated: {}. Specify target in config instead", new Object[] { substring });
                MixinPlatformManager.logger.debug("Registering mixin config: {}", new Object[] { substring });
                MixinEnvironment.getEnvironment(forName).addConfiguration(substring);
            }
        }
    }
    
    final void addTokenProvider(final String s) {
        if (s.contains("@")) {
            final String[] split = s.split("@", 2);
            final MixinEnvironment.Phase forName = MixinEnvironment.Phase.forName(split[1]);
            if (forName != null) {
                MixinPlatformManager.logger.debug("Registering token provider class: {}", new Object[] { split[0] });
                MixinEnvironment.getEnvironment(forName).registerTokenProviderClass(split[0]);
            }
            return;
        }
        MixinEnvironment.getDefaultEnvironment().registerTokenProviderClass(s);
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
