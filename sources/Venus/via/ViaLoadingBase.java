/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via;

import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
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
import via.model.ComparableProtocolVersion;
import via.model.Platform;
import via.platform.ViaBackwardsPlatformImpl;
import via.platform.ViaVersionPlatformImpl;
import via.platform.viaversion.ViaInjector;
import via.platform.viaversion.ViaProviders;
import via.util.JLoggerToLog4j;

public class ViaLoadingBase {
    public static final String VERSION = "${vialoadingbase_version}";
    public static final Logger LOGGER = new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
    public static final Platform PSEUDO_VIA_VERSION = new Platform("ViaVersion", ViaLoadingBase::lambda$static$0, ViaLoadingBase::lambda$static$1, ViaLoadingBase::lambda$static$2);
    public static final Platform PLATFORM_VIA_BACKWARDS = new Platform("ViaBackwards", ViaLoadingBase::lambda$static$3, ViaLoadingBase::lambda$static$4);
    public static final Map<ProtocolVersion, ComparableProtocolVersion> PROTOCOLS = new LinkedHashMap<ProtocolVersion, ComparableProtocolVersion>();
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

    public ViaLoadingBase(LinkedList<Platform> linkedList, File file, int n, BooleanSupplier booleanSupplier, Supplier<JsonObject> supplier, Consumer<com.viaversion.viaversion.api.platform.providers.ViaProviders> consumer, Consumer<ViaManagerImpl.ViaManagerBuilder> consumer2, Consumer<ComparableProtocolVersion> consumer3) {
        this.platforms = linkedList;
        this.runDirectory = new File(file, "viaversion");
        this.nativeVersion = n;
        this.forceNativeVersionCondition = booleanSupplier;
        this.dumpSupplier = supplier;
        this.providers = consumer;
        this.managerBuilderConsumer = consumer2;
        this.onProtocolReload = consumer3;
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
    }

    public void reload(ComparableProtocolVersion comparableProtocolVersion) {
        this.targetProtocolVersion = comparableProtocolVersion;
        if (this.onProtocolReload != null) {
            this.onProtocolReload.accept(this.targetProtocolVersion);
        }
    }

    public void initPlatform() {
        for (Platform object2 : this.platforms) {
            object2.createProtocolPath();
        }
        for (ProtocolVersion protocolVersion : Platform.TEMP_INPUT_PROTOCOLS) {
            PROTOCOLS.put(protocolVersion, new ComparableProtocolVersion(protocolVersion.getVersion(), protocolVersion.getName(), Platform.TEMP_INPUT_PROTOCOLS.indexOf(protocolVersion)));
        }
        this.targetProtocolVersion = this.nativeProtocolVersion = ViaLoadingBase.fromProtocolVersion(ProtocolVersion.getProtocol(this.nativeVersion));
        ViaVersionPlatformImpl viaVersionPlatformImpl = new ViaVersionPlatformImpl(LOGGER);
        ViaManagerImpl.ViaManagerBuilder viaManagerBuilder = ViaManagerImpl.builder().platform(viaVersionPlatformImpl).loader(new ViaProviders()).injector(new ViaInjector());
        if (this.managerBuilderConsumer != null) {
            this.managerBuilderConsumer.accept(viaManagerBuilder);
        }
        Via.init(viaManagerBuilder.build());
        ViaManagerImpl viaManagerImpl = (ViaManagerImpl)Via.getManager();
        viaManagerImpl.addEnableListener(this::lambda$initPlatform$5);
        viaManagerImpl.init();
        viaManagerImpl.onServerLoaded();
        viaManagerImpl.getProtocolManager().setMaxProtocolPathSize(Integer.MAX_VALUE);
        viaManagerImpl.getProtocolManager().setMaxPathDeltaIncrease(-1);
        ((ProtocolManagerImpl)viaManagerImpl.getProtocolManager()).refreshVersions();
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

    public Consumer<com.viaversion.viaversion.api.platform.providers.ViaProviders> getProviders() {
        return this.providers;
    }

    public static boolean inClassPath(String string) {
        try {
            Class.forName(string);
            return true;
        } catch (Exception exception) {
            return true;
        }
    }

    public static ComparableProtocolVersion fromProtocolVersion(ProtocolVersion protocolVersion) {
        return PROTOCOLS.get(protocolVersion);
    }

    public static ComparableProtocolVersion fromProtocolId(int n) {
        return PROTOCOLS.values().stream().filter(arg_0 -> ViaLoadingBase.lambda$fromProtocolId$6(n, arg_0)).findFirst().orElse(null);
    }

    public static List<ProtocolVersion> getProtocols() {
        return new LinkedList<ProtocolVersion>(PROTOCOLS.keySet());
    }

    private static boolean lambda$fromProtocolId$6(int n, ComparableProtocolVersion comparableProtocolVersion) {
        return comparableProtocolVersion.getVersion() == n;
    }

    private void lambda$initPlatform$5() {
        for (Platform platform : this.platforms) {
            platform.build(LOGGER);
        }
    }

    private static void lambda$static$4() {
        new ViaBackwardsPlatformImpl(Via.getManager().getPlatform().getDataFolder());
    }

    private static boolean lambda$static$3() {
        return ViaLoadingBase.inClassPath("com.viaversion.viabackwards.api.ViaBackwardsPlatform");
    }

    private static void lambda$static$2(List list) {
        list.addAll(ViaVersionPlatformImpl.createVersionList());
    }

    private static void lambda$static$1() {
    }

    private static boolean lambda$static$0() {
        return false;
    }

    public static class ViaLoadingBaseBuilder {
        private final LinkedList<Platform> platforms = new LinkedList();
        private File runDirectory;
        private Integer nativeVersion;
        private BooleanSupplier forceNativeVersionCondition;
        private Supplier<JsonObject> dumpSupplier;
        private Consumer<com.viaversion.viaversion.api.platform.providers.ViaProviders> providers;
        private Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer;
        private Consumer<ComparableProtocolVersion> onProtocolReload;

        public ViaLoadingBaseBuilder() {
            this.platforms.add(PSEUDO_VIA_VERSION);
            this.platforms.add(PLATFORM_VIA_BACKWARDS);
        }

        public static ViaLoadingBaseBuilder create() {
            return new ViaLoadingBaseBuilder();
        }

        public ViaLoadingBaseBuilder platform(Platform platform) {
            this.platforms.add(platform);
            return this;
        }

        public ViaLoadingBaseBuilder platform(Platform platform, int n) {
            this.platforms.add(n, platform);
            return this;
        }

        public ViaLoadingBaseBuilder runDirectory(File file) {
            this.runDirectory = file;
            return this;
        }

        public ViaLoadingBaseBuilder nativeVersion(int n) {
            this.nativeVersion = n;
            return this;
        }

        public ViaLoadingBaseBuilder forceNativeVersionCondition(BooleanSupplier booleanSupplier) {
            this.forceNativeVersionCondition = booleanSupplier;
            return this;
        }

        public ViaLoadingBaseBuilder dumpSupplier(Supplier<JsonObject> supplier) {
            this.dumpSupplier = supplier;
            return this;
        }

        public ViaLoadingBaseBuilder providers(Consumer<com.viaversion.viaversion.api.platform.providers.ViaProviders> consumer) {
            this.providers = consumer;
            return this;
        }

        public ViaLoadingBaseBuilder managerBuilderConsumer(Consumer<ViaManagerImpl.ViaManagerBuilder> consumer) {
            this.managerBuilderConsumer = consumer;
            return this;
        }

        public ViaLoadingBaseBuilder onProtocolReload(Consumer<ComparableProtocolVersion> consumer) {
            this.onProtocolReload = consumer;
            return this;
        }

        public void build() {
            if (ViaLoadingBase.getInstance() != null) {
                return;
            }
            if (this.runDirectory == null || this.nativeVersion == null) {
                return;
            }
            new ViaLoadingBase(this.platforms, this.runDirectory, this.nativeVersion, this.forceNativeVersionCondition, this.dumpSupplier, this.providers, this.managerBuilderConsumer, this.onProtocolReload);
        }
    }
}

