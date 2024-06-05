/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.HWID;

public class Auth {
    private static String username;
    private static String hwid;
    private static String uid;
    private static String status;
    private static boolean ban;
    private static boolean sw;
    private static String banReason;
    public static int count;
    public static boolean loggedIn;

    public static boolean authManualHWID() {
        return true;
    }

    public static String getUserName() {
        return username;
    }

    public static String getHwid() {
        return hwid;
    }

    public static String getUid() {
        return uid;
    }

    public static boolean isBan() {
        return ban;
    }

    public static String getStatus() {
        return status;
    }

    public static boolean isSw() {
        return sw;
    }

    public static String getBanReason() {
        return banReason;
    }
}

