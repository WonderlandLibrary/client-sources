/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.vialoadingbase;

import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import dev.intave.NewPhisicsFixes;
import dev.intave.vialoadingbase.model.ComparableProtocolVersion;
import dev.intave.vialoadingbase.model.Platform;
import dev.intave.vialoadingbase.platform.ViaBackwardsPlatformImpl;
import dev.intave.vialoadingbase.platform.ViaRewindPlatformImpl;
import dev.intave.vialoadingbase.platform.ViaVersionPlatformImpl;
import dev.intave.vialoadingbase.platform.viaversion.VLBViaCommandHandler;
import dev.intave.vialoadingbase.platform.viaversion.VLBViaInjector;
import dev.intave.vialoadingbase.platform.viaversion.VLBViaProviders;
import dev.intave.vialoadingbase.util.JLoggerToLog4j;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

public class ViaLoadingBase {
    public static final String VERSION = "${vialoadingbase_version}";
    public static final Logger LOGGER = new JLoggerToLog4j(LogManager.getLogger("ViaLoadingBase"));
    public static final Platform PSEUDO_VIA_VERSION = new Platform("ViaVersion", () -> true, () -> {}, protocolVersions -> protocolVersions.addAll(ViaVersionPlatformImpl.createVersionList()));
    public static final Platform PLATFORM_VIA_BACKWARDS = new Platform("ViaBackwards", () -> ViaLoadingBase.inClassPath("com.viaversion.viabackwards.api.ViaBackwardsPlatform"), () -> new ViaBackwardsPlatformImpl(Via.getManager().getPlatform().getDataFolder()));
    public static final Platform PLATFORM_VIA_REWIND = new Platform("ViaRewind", () -> ViaLoadingBase.inClassPath("de.gerrygames.viarewind.api.ViaRewindPlatform"), () -> new ViaRewindPlatformImpl(Via.getManager().getPlatform().getDataFolder()));
    public static final Map<ProtocolVersion, ComparableProtocolVersion> PROTOCOLS = new LinkedHashMap<ProtocolVersion, ComparableProtocolVersion>();
    private static ViaLoadingBase instance;
    private final LinkedList<Platform> platforms;
    private final File runDirectory;
    private final int nativeVersion;
    private final BooleanSupplier forceNativeVersionCondition;
    private final Supplier<JsonObject> dumpSupplier;
    private final Consumer<ViaProviders> providers;
    private final Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer;
    private final Consumer<ComparableProtocolVersion> onProtocolReload;
    private ComparableProtocolVersion nativeProtocolVersion;
    private ComparableProtocolVersion targetProtocolVersion;

    public ViaLoadingBase(LinkedList<Platform> platforms, File runDirectory, int nativeVersion, BooleanSupplier forceNativeVersionCondition, Supplier<JsonObject> dumpSupplier, Consumer<ViaProviders> providers, Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer, Consumer<ComparableProtocolVersion> onProtocolReload) {
        this.platforms = platforms;
        this.runDirectory = new File(runDirectory, "ViaLoadingBase");
        this.nativeVersion = nativeVersion;
        this.forceNativeVersionCondition = forceNativeVersionCondition;
        this.dumpSupplier = dumpSupplier;
        this.providers = providers;
        this.managerBuilderConsumer = managerBuilderConsumer;
        this.onProtocolReload = onProtocolReload;
        instance = this;
        this.initPlatform();
    }

    public ComparableProtocolVersion getTargetVersion() {
        if (this.forceNativeVersionCondition != null && this.forceNativeVersionCondition.getAsBoolean()) {
            return this.nativeProtocolVersion;
        }
        return this.targetProtocolVersion;
    }

    public void reload(ProtocolVersion protocolVersion) {
        this.reload(ViaLoadingBase.fromProtocolVersion(protocolVersion));
        NewPhisicsFixes.updateNewVerionStatus(protocolVersion);
    }

    public void reload(ComparableProtocolVersion protocolVersion) {
        this.targetProtocolVersion = protocolVersion;
        if (this.onProtocolReload != null) {
            this.onProtocolReload.accept(this.targetProtocolVersion);
        }
    }

    public void initPlatform() {
        for (Platform platform : this.platforms) {
            platform.createProtocolPath();
        }
        for (ProtocolVersion preProtocol : Platform.TEMP_INPUT_PROTOCOLS) {
            PROTOCOLS.put(preProtocol, new ComparableProtocolVersion(preProtocol.getVersion(), preProtocol.getName(), Platform.TEMP_INPUT_PROTOCOLS.indexOf(preProtocol)));
        }
        this.targetProtocolVersion = this.nativeProtocolVersion = ViaLoadingBase.fromProtocolVersion(ProtocolVersion.getProtocol(this.nativeVersion));
        ViaVersionPlatformImpl viaVersionPlatform = new ViaVersionPlatformImpl(LOGGER);
        ViaManagerImpl.ViaManagerBuilder builder = ViaManagerImpl.builder().platform(viaVersionPlatform).loader(new VLBViaProviders()).injector(new VLBViaInjector()).commandHandler(new VLBViaCommandHandler());
        if (this.managerBuilderConsumer != null) {
            this.managerBuilderConsumer.accept(builder);
        }
        Via.init(builder.build());
        ViaManagerImpl manager = (ViaManagerImpl)Via.getManager();
        manager.addEnableListener(() -> {
            for (Platform platform : this.platforms) {
                platform.build(LOGGER);
            }
        });
        manager.init();
        manager.onServerLoaded();
        manager.getProtocolManager().setMaxProtocolPathSize(Integer.MAX_VALUE);
        manager.getProtocolManager().setMaxPathDeltaIncrease(-1);
        ((ProtocolManagerImpl)manager.getProtocolManager()).refreshVersions();
        LOGGER.info("ViaLoadingBase has loaded " + Platform.COUNT + "/" + this.platforms.size() + " platforms");
    }

    public static ViaLoadingBase getInstance() {
        return instance;
    }

    public List<Platform> getSubPlatforms() {
        return this.platforms;
    }

    public File getRunDirectory() {
        return this.runDirectory;
    }

    public int getNativeVersion() {
        return this.nativeVersion;
    }

    public Supplier<JsonObject> getDumpSupplier() {
        return this.dumpSupplier;
    }

    public Consumer<ViaProviders> getProviders() {
        return this.providers;
    }

    public static boolean inClassPath(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static ComparableProtocolVersion fromProtocolVersion(ProtocolVersion protocolVersion) {
        return PROTOCOLS.get(protocolVersion);
    }

    public static ComparableProtocolVersion fromProtocolId(int protocolId) {
        return PROTOCOLS.values().stream().filter(protocol -> protocol.getVersion() == protocolId).findFirst().orElse(null);
    }

    public static List<ProtocolVersion> getProtocols() {
        return new LinkedList<ProtocolVersion>(PROTOCOLS.keySet());
    }

    public static class ViaLoadingBaseBuilder {
        private final LinkedList<Platform> platforms = new LinkedList();
        private File runDirectory;
        private Integer nativeVersion;
        private BooleanSupplier forceNativeVersionCondition;
        private Supplier<JsonObject> dumpSupplier;
        private Consumer<ViaProviders> providers;
        private Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer;
        private Consumer<ComparableProtocolVersion> onProtocolReload;

        public ViaLoadingBaseBuilder() {
            this.platforms.add(PSEUDO_VIA_VERSION);
            this.platforms.add(PLATFORM_VIA_BACKWARDS);
            this.platforms.add(PLATFORM_VIA_REWIND);
        }

        public static ViaLoadingBaseBuilder create() {
            return new ViaLoadingBaseBuilder();
        }

        public ViaLoadingBaseBuilder platform(Platform platform) {
            this.platforms.add(platform);
            return this;
        }

        public ViaLoadingBaseBuilder platform(Platform platform, int position) {
            this.platforms.add(position, platform);
            return this;
        }

        public ViaLoadingBaseBuilder runDirectory(File runDirectory) {
            this.runDirectory = runDirectory;
            return this;
        }

        public ViaLoadingBaseBuilder nativeVersion(int nativeVersion) {
            this.nativeVersion = nativeVersion;
            return this;
        }

        public ViaLoadingBaseBuilder forceNativeVersionCondition(BooleanSupplier forceNativeVersionCondition) {
            this.forceNativeVersionCondition = forceNativeVersionCondition;
            return this;
        }

        public ViaLoadingBaseBuilder dumpSupplier(Supplier<JsonObject> dumpSupplier) {
            this.dumpSupplier = dumpSupplier;
            return this;
        }

        public ViaLoadingBaseBuilder providers(Consumer<ViaProviders> providers) {
            this.providers = providers;
            return this;
        }

        public ViaLoadingBaseBuilder managerBuilderConsumer(Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer) {
            this.managerBuilderConsumer = managerBuilderConsumer;
            return this;
        }

        public ViaLoadingBaseBuilder onProtocolReload(Consumer<ComparableProtocolVersion> onProtocolReload) {
            this.onProtocolReload = onProtocolReload;
            return this;
        }

        public void build() {
            if (ViaLoadingBase.getInstance() != null) {
                LOGGER.severe("ViaLoadingBase has already started the platform!");
                return;
            }
            if (this.runDirectory == null || this.nativeVersion == null) {
                LOGGER.severe("Please check your ViaLoadingBaseBuilder arguments!");
                return;
            }
            new ViaLoadingBase(this.platforms, this.runDirectory, this.nativeVersion, this.forceNativeVersionCondition, this.dumpSupplier, this.providers, this.managerBuilderConsumer, this.onProtocolReload);
        }
    }
}

