// 
// Decompiled by Procyon v0.5.36
// 

package skid;

public class AquaSkid
{
    public static void checkForUpdates() {
        System.out.println("Checking for updates...");
        if (Updater.isUpdateAvailable()) {
            System.out.println("Update found!");
            Updater.update();
        }
    }
}
