package club.bluezenith.core.data.preferences;

/**
 * The class that holds all preferences (that the user can change in preferences.json, todo make a gui?
 * To add a preference (an option in the client that can be adjusted by the user),
 * just add a public static <b>primitive</b> (int, boolean, String, long, etc..) and annotate it with @Preference(serialized-name).
 * The serialization/deserialization of these values is performed on startup/shutdown.
 * @see PreferencesSerializer
 */
public class Preferences {
    @Preference("enable-event-priority")
    public static boolean useSortingInEventManager = true;

    @Preference("use-background-shader")
    public static boolean useBackgroundShader = true;

    @Preference("use-blur-in-menus")
    public static boolean useBlurInMenus = true;

    @Preference("alt-manager-doubleclick-maxdelay")
    public static int altManagerDoubleClickDelay = 250;

    @Preference("dont-edit-alt-manager-sorting-index")
    public static int altManagerSortingIndex = 0;

    @Preference("alt-manager-favorites-only")
    public static boolean altManagerFavoritesOnly = false;

    @Preference("alt-manager-unbanned-only")
    public static boolean altManagerUnbannedOnly = false;

    @Preference("alt-manager-premium-only")
    public static boolean altManagerPremiumOnly = false;
    @Preference("mark-ip-as-banned-even-without-playtime")
    public static boolean banIpsWithoutPlaytime = false;
}
