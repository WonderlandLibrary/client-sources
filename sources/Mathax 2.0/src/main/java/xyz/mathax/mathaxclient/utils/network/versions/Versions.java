package xyz.mathax.mathaxclient.utils.network.versions;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.init.PreInit;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import net.minecraft.SharedConstants;
import org.json.JSONObject;
import xyz.mathax.mathaxclient.utils.network.api.Api;

public class Versions {
    private static Version version, latestVersion;

    private static boolean updateAvailable = false;

    @PreInit
    public static void init() {
        setVersion();
        checkForUpdate();
    }

    public static Version get() {
        if (version == null) {
            setVersion();
        }

        return version;
    }

    public static Version getLatest() {
        return latestVersion;
    }

    public static String getStylized(boolean latest) {
        if (version == null) {
            setVersion();
        }

        return latest ? "v" + latestVersion : "v" + version;
    }

    public static String getStylized(Version version) {
        return "v" + version;
    }

    public static void setVersion() {
        version = new Version(MatHax.META.getVersion().getFriendlyString());
    }
    
    public static String getStylized() {
        return getStylized(false);
    }

    public static boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public static void checkForUpdate() {
        latestVersion = null;
        updateAvailable = false;

        JSONObject json = Api.getVersions();
        if (json == null) {
            return;
        }

        if (json.has("versions") && JSONUtils.isValidJSONArray(json, "versions"))  {
            for (Object object : json.getJSONArray("versions")) {
                if (!(object instanceof JSONObject versionJson)) {
                    continue;
                }

                if (versionJson.has("minecraft-version") && versionJson.getString("minecraft-version").equals(getMinecraft()) && versionJson.has("latest-version")) {
                    latestVersion = new Version(versionJson.getString("latest-version"));
                }
            }
        }

        if (latestVersion != null) {
            updateAvailable = latestVersion.isHigherThan(version);
        }
    }

    public static String getMinecraft(boolean replaceDots){
        String version = SharedConstants.getGameVersion().getName();
        return replaceDots ? version.replace(".", "-") : version;
    }

    public static String getMinecraft(){
        return getMinecraft(false);
    }
}
