package me.enrythebest.reborn.cracked.util.file;

import me.enrythebest.reborn.cracked.*;
import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.util.*;
import java.util.*;
import java.io.*;

public class PreviousAccount
{
    public static String username;
    public static String password;
    public static ArrayList account;
    private static File previousAccount;
    
    static {
        PreviousAccount.account = new ArrayList();
        MorbidWrapper.mcObj();
        PreviousAccount.previousAccount = new File(Minecraft.getMinecraftDir(), "/Morbid/previousAlt.txt");
    }
    
    public static void saveAccount(final String var0, final String var1) {
        if (!PreviousAccount.previousAccount.exists()) {
            try {
                PreviousAccount.previousAccount.createNewFile();
                saveAccount(var0, var1);
            }
            catch (Exception var2) {
                var2.printStackTrace();
            }
        }
        try {
            PreviousAccount.account.clear();
            PreviousAccount.account.add(var0);
            PreviousAccount.account.add(var1);
            final BufferedWriter var3 = new BufferedWriter(new FileWriter(PreviousAccount.previousAccount));
            for (final String var5 : PreviousAccount.account) {
                var3.write(String.valueOf(var5) + "\r\n");
            }
            var3.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
        MorbidHelper.gc();
    }
    
    public static void loadAccount() {
        if (PreviousAccount.previousAccount.exists()) {
            try {
                final BufferedReader var0 = new BufferedReader(new FileReader(PreviousAccount.previousAccount));
                String var2 = "";
                while ((var2 = var0.readLine()) != null) {
                    PreviousAccount.account.add(var2);
                }
                var0.close();
                PreviousAccount.username = PreviousAccount.account.get(0);
                PreviousAccount.password = PreviousAccount.account.get(1);
            }
            catch (Exception var3) {
                var3.printStackTrace();
            }
        }
        MorbidHelper.gc();
    }
}
