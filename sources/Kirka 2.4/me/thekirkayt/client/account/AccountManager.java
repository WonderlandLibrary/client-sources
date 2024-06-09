/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.account;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.thekirkayt.client.account.Alt;
import me.thekirkayt.utils.FileUtils;

public class AccountManager {
    private static final File ACCOUNT_DIR = FileUtils.getConfigFile("Accounts");
    public static List<Alt> accountList = new ArrayList<Alt>();
    public static final Alt nullAlt = new Alt("null", "null", "null");

    public static void init() {
        AccountManager.load();
        AccountManager.save();
    }

    public static void load() {
        List<String> fileContent = FileUtils.read(ACCOUNT_DIR);
        accountList.clear();
        for (String line : fileContent) {
            try {
                String[] split = line.split(":");
                String email = split[0].replace("\u02a1", "");
                if (split.length == 1) {
                    accountList.add(new Alt(email, "", ""));
                    continue;
                }
                String pass = split[1].replace("\u02a1", "");
                String name = "";
                if (split.length > 2) {
                    name = split[2].replace("\u02a1", "");
                    accountList.add(new Alt(email, name, pass));
                    continue;
                }
                accountList.add(new Alt(email, "", pass));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        FileUtils.write(ACCOUNT_DIR, fileContent, true);
        for (Alt alt : accountList) {
            String name;
            String email = alt.getEmail() == "" ? "\u02a1" : alt.getEmail();
            String pass = alt.getPassword().length() < 1 ? "\u02a1" : alt.getPassword();
            String string = name = alt.getUsername() == "" ? "\u02a1" : alt.getUsername();
            if (name.length() > 0) {
                fileContent.add(String.format("%s:%s:%s", email, pass, name));
                continue;
            }
            fileContent.add(String.format("%s:%s", email, pass));
        }
        FileUtils.write(ACCOUNT_DIR, fileContent, true);
    }

    public static void addAlt(Alt alt) {
        accountList.add(alt);
    }

    public static void addAlt(int pos, Alt alt) {
        accountList.add(pos, alt);
    }

    public static Alt getAlt(String email) {
        for (Alt account : accountList) {
            if (!account.getEmail().equalsIgnoreCase(email)) continue;
            return account;
        }
        return nullAlt;
    }

    public static void removeAlt(Alt alt) {
        accountList.remove(alt);
    }
}

