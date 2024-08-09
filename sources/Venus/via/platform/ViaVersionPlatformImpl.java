/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import via.ViaLoadingBase;
import via.platform.viaversion.ViaAPIWrapper;
import via.platform.viaversion.ViaConfig;
import via.util.ViaTask;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ViaVersionPlatformImpl
implements ViaPlatform<UUID> {
    private final ViaAPI<UUID> api = new ViaAPIWrapper();
    private final Logger logger;
    private final ViaConfig config;

    public ViaVersionPlatformImpl(Logger logger) {
        this.logger = logger;
        this.config = new ViaConfig(new File(ViaLoadingBase.getInstance().getRunDirectory(), "viaversion.yml"));
    }

    public static List<ProtocolVersion> createVersionList() {
        List<ProtocolVersion> list = new ArrayList<ProtocolVersion>(ProtocolVersion.getProtocols()).stream().filter(ViaVersionPlatformImpl::lambda$createVersionList$0).collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        return new ViaCommandSender[0];
    }

    @Override
    public void sendMessage(UUID uUID, String string) {
        if (uUID == null) {
            this.getLogger().info(string);
        } else {
            this.getLogger().info("[" + uUID + "] " + string);
        }
    }

    @Override
    public boolean kickPlayer(UUID uUID, String string) {
        return true;
    }

    @Override
    public boolean disconnect(UserConnection userConnection, String string) {
        return ViaPlatform.super.disconnect(userConnection, string);
    }

    @Override
    public ViaTask runAsync(Runnable runnable) {
        return new ViaTask(Via.getManager().getScheduler().execute(runnable));
    }

    @Override
    public ViaTask runRepeatingAsync(Runnable runnable, long l) {
        return new ViaTask(Via.getManager().getScheduler().scheduleRepeating(runnable, 0L, l * 50L, TimeUnit.MILLISECONDS));
    }

    @Override
    public ViaTask runSync(Runnable runnable) {
        return this.runAsync(runnable);
    }

    @Override
    public ViaTask runSync(Runnable runnable, long l) {
        return new ViaTask(Via.getManager().getScheduler().schedule(runnable, l * 50L, TimeUnit.MILLISECONDS));
    }

    @Override
    public ViaTask runRepeatingSync(Runnable runnable, long l) {
        return this.runRepeatingAsync(runnable, l);
    }

    @Override
    public boolean isProxy() {
        return false;
    }

    @Override
    public void onReload() {
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public ViaVersionConfig getConf() {
        return this.config;
    }

    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.config;
    }

    @Override
    public ViaAPI<UUID> getApi() {
        return this.api;
    }

    @Override
    public File getDataFolder() {
        return ViaLoadingBase.getInstance().getRunDirectory();
    }

    @Override
    public String getPluginVersion() {
        return "4.7.0";
    }

    @Override
    public String getPlatformName() {
        return "ViaVersion by venusfr solutions";
    }

    @Override
    public String getPlatformVersion() {
        return "${vialoadingbase_version}";
    }

    @Override
    public boolean isPluginEnabled() {
        return false;
    }

    @Override
    public boolean isOldClientsAllowed() {
        return false;
    }

    @Override
    public Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
        return ViaPlatform.super.getUnsupportedSoftwareClasses();
    }

    @Override
    public boolean hasPlugin(String string) {
        return true;
    }

    @Override
    public JsonObject getDump() {
        if (ViaLoadingBase.getInstance().getDumpSupplier() == null) {
            return new JsonObject();
        }
        return ViaLoadingBase.getInstance().getDumpSupplier().get();
    }

    @Override
    public PlatformTask runRepeatingSync(Runnable runnable, long l) {
        return this.runRepeatingSync(runnable, l);
    }

    @Override
    public PlatformTask runSync(Runnable runnable, long l) {
        return this.runSync(runnable, l);
    }

    @Override
    public PlatformTask runSync(Runnable runnable) {
        return this.runSync(runnable);
    }

    @Override
    public PlatformTask runRepeatingAsync(Runnable runnable, long l) {
        return this.runRepeatingAsync(runnable, l);
    }

    @Override
    public PlatformTask runAsync(Runnable runnable) {
        return this.runAsync(runnable);
    }

    private static boolean lambda$createVersionList$0(ProtocolVersion protocolVersion) {
        return protocolVersion != ProtocolVersion.unknown && ProtocolVersion.getProtocols().indexOf(protocolVersion) >= 7;
    }
}

