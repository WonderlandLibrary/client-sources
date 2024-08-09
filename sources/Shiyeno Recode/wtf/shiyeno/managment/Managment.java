package wtf.shiyeno.managment;

import wtf.shiyeno.command.CommandManager;
import wtf.shiyeno.command.macro.MacroManager;
import wtf.shiyeno.config.ConfigManager;
import wtf.shiyeno.config.LastAccountConfig;
import wtf.shiyeno.friend.FriendManager;
import wtf.shiyeno.modules.FunctionManager;
import wtf.shiyeno.notification.NotificationManager;
import wtf.shiyeno.proxy.ProxyConnection;
import wtf.shiyeno.scripts.ScriptManager;
import wtf.shiyeno.ui.alt.AltConfig;
import wtf.shiyeno.ui.alt.AltManager;
import wtf.shiyeno.ui.clickgui.Window;
import wtf.shiyeno.ui.midnight.StyleManager;
import wtf.shiyeno.util.UserProfile;

public class Managment {

    public static FunctionManager FUNCTION_MANAGER;
    public static CommandManager COMMAND_MANAGER;
    public static FriendManager FRIEND_MANAGER;
    public static MacroManager MACRO_MANAGER;
    public static LastAccountConfig LAST_ACCOUNT_CONFIG;
    public static ScriptManager SCRIPT_MANAGER;

    public static StaffManager STAFF_MANAGER;
    public static Window CLICK_GUI;
    public static ConfigManager CONFIG_MANAGER;
    public static StyleManager STYLE_MANAGER;
    public static UserProfile USER_PROFILE;
    public static NotificationManager NOTIFICATION_MANAGER;
    public static AltManager ALT;
    public static AltConfig ALT_CONFIG;

    public static ProxyConnection PROXY_CONN;
}