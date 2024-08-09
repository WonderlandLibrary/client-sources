package dev.luvbeeq.via;

import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import dev.luvbeeq.via.model.ComparableProtocolVersion;
import dev.luvbeeq.via.model.Platform;
import dev.luvbeeq.via.platform.ViaBackwardsPlatformImpl;
import dev.luvbeeq.via.platform.ViaVersionPlatformImpl;
import dev.luvbeeq.via.platform.viaversion.ViaInjector;
import dev.luvbeeq.via.platform.viaversion.ViaProviders;
import dev.luvbeeq.via.util.JLoggerToLog4j;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class ViaLoadingBase {
    public final static String VERSION = "${vialoadingbase_version}";
    public final static Logger LOGGER = new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
    public final static Platform PSEUDO_VIA_VERSION = new Platform("ViaVersion", () -> true, () -> {
    }, protocolVersions -> protocolVersions.addAll(ViaVersionPlatformImpl.createVersionList()));
    public final static Platform PLATFORM_VIA_BACKWARDS = new Platform("ViaBackwards", () -> inClassPath("com.viaversion.viabackwards.api.ViaBackwardsPlatform"), () -> new ViaBackwardsPlatformImpl(Via.getManager().getPlatform().getDataFolder()));
    public final static Map<ProtocolVersion, ComparableProtocolVersion> PROTOCOLS = new LinkedHashMap<>();

    private static ViaLoadingBase instance;

    private final LinkedList<Platform> platforms;
    private final File runDirectory;
    private final int nativeVersion;
    private final BooleanSupplier forceNativeVersionCondition;
    private final Supplier<JsonObject> dumpSupplier;
    private final Consumer<com.viaversion.viaversion.api.platform.providers.ViaProviders> providers;
    private final Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer;
    private final Consumer<ComparableProtocolVersion> onProtocolReload;

    private ComparableProtocolVersion nativeProtocolVersion;
    private ComparableProtocolVersion targetProtocolVersion;

    public ViaLoadingBase(LinkedList<Platform> platforms, File runDirectory, int nativeVersion, BooleanSupplier forceNativeVersionCondition, Supplier<JsonObject> dumpSupplier, Consumer<com.viaversion.viaversion.api.platform.providers.ViaProviders> providers, Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer, Consumer<ComparableProtocolVersion> onProtocolReload) {
        this.platforms = platforms;

        this.runDirectory = new File(runDirectory, "viaversion");
        this.nativeVersion = nativeVersion;
        this.forceNativeVersionCondition = forceNativeVersionCondition;
        this.dumpSupplier = dumpSupplier;
        this.providers = providers;
        this.managerBuilderConsumer = managerBuilderConsumer;
        this.onProtocolReload = onProtocolReload;

        instance = this;
        initPlatform();
    }

    public ComparableProtocolVersion getTargetVersion() {
        if (forceNativeVersionCondition != null && forceNativeVersionCondition.getAsBoolean())
            return nativeProtocolVersion;

        return targetProtocolVersion;
    }

    public void reload(final ProtocolVersion protocolVersion) {
        reload(fromProtocolVersion(protocolVersion));
    }

    public void reload(final ComparableProtocolVersion protocolVersion) {
        this.targetProtocolVersion = protocolVersion;

        if (this.onProtocolReload != null) this.onProtocolReload.accept(targetProtocolVersion);
    }

    public void initPlatform() {
        for (Platform platform : platforms) platform.createProtocolPath();
        for (ProtocolVersion preProtocol : Platform.TEMP_INPUT_PROTOCOLS)
            PROTOCOLS.put(preProtocol, new ComparableProtocolVersion(preProtocol.getVersion(), preProtocol.getName(), Platform.TEMP_INPUT_PROTOCOLS.indexOf(preProtocol)));

        this.nativeProtocolVersion = fromProtocolVersion(ProtocolVersion.getProtocol(this.nativeVersion));
        this.targetProtocolVersion = this.nativeProtocolVersion;

        final ViaVersionPlatformImpl viaVersionPlatform = new ViaVersionPlatformImpl(ViaLoadingBase.LOGGER);
        final ViaManagerImpl.ViaManagerBuilder builder = ViaManagerImpl.builder().
                platform(viaVersionPlatform).
                loader(new ViaProviders()).
                injector(new ViaInjector());

        if (this.managerBuilderConsumer != null) this.managerBuilderConsumer.accept(builder);

        Via.init(builder.build());

        final ViaManagerImpl manager = (ViaManagerImpl) Via.getManager();
        manager.addEnableListener(() -> {
            for (Platform platform : this.platforms) platform.build(ViaLoadingBase.LOGGER);
        });

        manager.init();
        manager.onServerLoaded();
        manager.getProtocolManager().setMaxProtocolPathSize(Integer.MAX_VALUE);
        manager.getProtocolManager().setMaxPathDeltaIncrease(-1);
        ((ProtocolManagerImpl) manager.getProtocolManager()).refreshVersions();

    }

    public static ViaLoadingBase getInstance() {
        return instance;
    }

    public List<Platform> getSubPlatforms() {
        return platforms;
    }

    public File getRunDirectory() {
        return runDirectory;
    }

    public int getNativeVersion() {
        return nativeVersion;
    }

    public Supplier<JsonObject> getDumpSupplier() {
        return dumpSupplier;
    }

    public Consumer<com.viaversion.viaversion.api.platform.providers.ViaProviders> getProviders() {
        return providers;
    }

    public static boolean inClassPath(final String name) {
        try {
            Class.forName(name);
            return true;
        } catch(Exception ignored) {
            return false;
        }
    }

    public static ComparableProtocolVersion fromProtocolVersion(final ProtocolVersion protocolVersion) {
        return PROTOCOLS.get(protocolVersion);
    }

    public static ComparableProtocolVersion fromProtocolId(final int protocolId) {
        return PROTOCOLS.values().stream().filter(protocol -> protocol.getVersion() == protocolId).findFirst().orElse(null);
    }

    public static List<ProtocolVersion> getProtocols() {
        return new LinkedList<>(PROTOCOLS.keySet());
    }

    public static class ViaLoadingBaseBuilder {
        private final LinkedList<Platform> platforms = new LinkedList<>();

        private File runDirectory;
        private Integer nativeVersion;
        private BooleanSupplier forceNativeVersionCondition;
        private Supplier<JsonObject> dumpSupplier;
        private Consumer<com.viaversion.viaversion.api.platform.providers.ViaProviders> providers;
        private Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer;
        private Consumer<ComparableProtocolVersion> onProtocolReload;

        public ViaLoadingBaseBuilder() {
            platforms.add(PSEUDO_VIA_VERSION);
            platforms.add(PLATFORM_VIA_BACKWARDS);
        }

        public static ViaLoadingBaseBuilder create() {
            return new ViaLoadingBaseBuilder();
        }

        public ViaLoadingBaseBuilder platform(final Platform platform) {
            this.platforms.add(platform);
            return this;
        }

        public ViaLoadingBaseBuilder platform(final Platform platform, final int position) {
            this.platforms.add(position, platform);
            return this;
        }

        public ViaLoadingBaseBuilder runDirectory(final File runDirectory) {
            this.runDirectory = runDirectory;
            return this;
        }

        public ViaLoadingBaseBuilder nativeVersion(final int nativeVersion) {
            this.nativeVersion = nativeVersion;
            return this;
        }

        public ViaLoadingBaseBuilder forceNativeVersionCondition(final BooleanSupplier forceNativeVersionCondition) {
            this.forceNativeVersionCondition = forceNativeVersionCondition;
            return this;
        }

        public ViaLoadingBaseBuilder dumpSupplier(final Supplier<JsonObject> dumpSupplier) {
            this.dumpSupplier = dumpSupplier;
            return this;
        }

        public ViaLoadingBaseBuilder providers(final Consumer<com.viaversion.viaversion.api.platform.providers.ViaProviders> providers) {
            this.providers = providers;
            return this;
        }

        public ViaLoadingBaseBuilder managerBuilderConsumer(final Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer) {
            this.managerBuilderConsumer = managerBuilderConsumer;
            return this;
        }

        public ViaLoadingBaseBuilder onProtocolReload(final Consumer<ComparableProtocolVersion> onProtocolReload) {
            this.onProtocolReload = onProtocolReload;
            return this;
        }

        public void build() {
            if (ViaLoadingBase.getInstance() != null) {
                return;
            }
            if (runDirectory == null || nativeVersion == null) {
                return;
            }
            new ViaLoadingBase(platforms, runDirectory, nativeVersion, forceNativeVersionCondition, dumpSupplier, providers, managerBuilderConsumer, onProtocolReload);
        }
    }
}
