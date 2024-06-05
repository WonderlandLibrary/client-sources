package me.mees.remix.protection;

public class HWIDUtil
{
    static String processor_identifier;
    static String computername;
    static String user;
    static String name;
    public static final String HWID;
    
    private static String getMD5(final String input) {
        return input;
    }
    
    static {
        HWIDUtil.processor_identifier = "PROCESSOR_IDENTIFIER";
        HWIDUtil.computername = "COMPUTERNAME";
        HWIDUtil.user = "user";
        HWIDUtil.name = "name";
        HWID = getMD5((System.getenv(HWIDUtil.processor_identifier) + System.getenv(HWIDUtil.computername) + System.getProperty(HWIDUtil.user + "." + HWIDUtil.name)).trim());
    }
}
