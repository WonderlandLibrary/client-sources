package me.darkmagician6.morbid.util.file;

import me.darkmagician6.morbid.util.*;
import java.util.*;
import java.io.*;
import me.darkmagician6.morbid.*;
import net.minecraft.client.*;

public class PreviousAccount
{
    public static String username;
    public static String password;
    public static ArrayList account;
    private static File previousAccount;
    
    public static void saveAccount(final String user, final String pass) {
        if (!PreviousAccount.previousAccount.exists()) {
            try {
                PreviousAccount.previousAccount.createNewFile();
                saveAccount(user, pass);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            PreviousAccount.account.clear();
            PreviousAccount.account.add(user);
            PreviousAccount.account.add(pass);
            final BufferedWriter writer = new BufferedWriter(new FileWriter(PreviousAccount.previousAccount));
            for (final String s : PreviousAccount.account) {
                writer.write(s + "\r\n");
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        MorbidHelper.gc();
    }
    
    public static void loadAccount() {
        if (PreviousAccount.previousAccount.exists()) {
            try {
                final BufferedReader reader = new BufferedReader(new FileReader(PreviousAccount.previousAccount));
                String s = "";
                while ((s = reader.readLine()) != null) {
                    PreviousAccount.account.add(s);
                }
                reader.close();
                PreviousAccount.username = PreviousAccount.account.get(0);
                PreviousAccount.password = PreviousAccount.account.get(1);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        MorbidHelper.gc();
    }
    
    static {
        PreviousAccount.account = new ArrayList();
        MorbidWrapper.mcObj();
        PreviousAccount.previousAccount = new File(Minecraft.b(), "/Morbid/previousAlt.txt");
    }
}
