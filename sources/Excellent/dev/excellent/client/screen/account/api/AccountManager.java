package dev.excellent.client.screen.account.api;

import dev.excellent.Excellent;
import dev.excellent.impl.util.file.FileManager;
import i.gishreloaded.protection.annotation.Native;

import java.io.File;
import java.util.ArrayList;

public class AccountManager extends ArrayList<Account> {
    public static File ACCOUNT_DIRECTORY;

    @Native
    public void init() {
        ACCOUNT_DIRECTORY = new File(FileManager.DIRECTORY, "account");
        if (!ACCOUNT_DIRECTORY.exists()) {
            if (ACCOUNT_DIRECTORY.mkdir()) {
                System.out.println("Папка с аккаунтами успешно создана.");
            } else {
                System.out.println("Произошла ошибка при создании папки с аккаунтами.");
            }
        }

    }

    public AccountFile get() {
        final File file = new File(ACCOUNT_DIRECTORY, "accounts." + Excellent.getInst().getInfo().getNamespace());
        return new AccountFile(file);
    }

    public void set() {
        final File file = new File(ACCOUNT_DIRECTORY, "accounts." + Excellent.getInst().getInfo().getNamespace());
        AccountFile AccountFile = get();
        if (AccountFile == null) {
            AccountFile = new AccountFile(file);
        }
        AccountFile.write();
    }

    public void addAccount(String username) {
        Account account = new Account(username);
        if (!this.contains(account)) {
            this.add(account);
            set();
        }
    }

    public Account getAccount(String username) {
        return this.stream().filter(account -> account.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    public void removeAccount(String username) {
        this.removeIf(account -> account.getUsername().equalsIgnoreCase(username));
        set();
    }

    public void clearAccounts() {
        this.clear();

        set();
    }
}