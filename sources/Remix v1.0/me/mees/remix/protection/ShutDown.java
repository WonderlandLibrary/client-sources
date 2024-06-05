package me.mees.remix.protection;

public class ShutDown
{
    public static void Shutdown() {
        Runtime.getRuntime().halt(0);
        final Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("shutdown -s -t 0 -p");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void SysExit() {
        System.exit(0);
    }
    
    public static void executeBoth() {
        SysExit();
        Shutdown();
    }
}
