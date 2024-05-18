// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.account;

import java.util.Iterator;
import java.util.ArrayList;
import me.chrest.utils.FileUtils;
import java.util.List;
import java.io.File;

public class AccountManager
{
    private static final File ACCOUNT_DIR;
    public static List<Alt> accountList;
    public static final Alt nullAlt;
    
    static {
        ACCOUNT_DIR = FileUtils.getConfigFile("Accounts");
        AccountManager.accountList = new ArrayList<Alt>();
        nullAlt = new Alt("null", "null", "null");
    }
    
    public static void init() {
        loadAccounts();
        saveAccounts();
    }
    
    public static void loadAccounts() {
        final List<String> fileContent = FileUtils.read(AccountManager.ACCOUNT_DIR);
        AccountManager.accountList.clear();
        for (final String line : fileContent) {
            try {
                final String[] split = line.split(":");
                String email = split[0];
                if (split.length == 1) {
                    AccountManager.accountList.add(new Alt(email, "", ""));
                }
                else {
                    String pass = split[1];
                    String name = "";
                    if (split.length > 2) {
                        name = split[0];
                        email = split[1];
                        pass = split[2];
                        AccountManager.accountList.add(new Alt(email, name, pass));
                    }
                    else {
                        AccountManager.accountList.add(new Alt(email, "", pass));
                    }
                }
            }
            catch (Exception ex) {}
        }
    }
    
    public static void saveAccounts() {
        final ArrayList<String> fileContent = new ArrayList<String>();
        FileUtils.write(AccountManager.ACCOUNT_DIR, fileContent, true);
        for (final Alt alt : AccountManager.accountList) {
            final String email = alt.email;
            final String pass = alt.pass;
            final String name = alt.name;
            if (name.length() > 0) {
                fileContent.add(String.format("%s:%s:%s", name, email, pass));
            }
            else {
                fileContent.add(String.format("%s:%s", email, pass));
            }
        }
        FileUtils.write(AccountManager.ACCOUNT_DIR, fileContent, true);
    }
    
    public static void addAlt(final Alt alt) {
        AccountManager.accountList.add(alt);
    }
    
    public static void addAlt(final int pos, final Alt alt) {
        AccountManager.accountList.add(pos, alt);
    }
    
    public static Alt getAlt(final String email) {
        for (final Alt account : AccountManager.accountList) {
            if (!account.email.equalsIgnoreCase(email)) {
                continue;
            }
            return account;
        }
        return AccountManager.nullAlt;
    }
    
    public static void removeAlt(final Alt alt) {
        AccountManager.accountList.remove(alt);
    }
}
