package me.dinozoid.strife.account;

import com.thealtening.auth.service.AlteningServiceType;
import me.dinozoid.strife.font.CustomFont;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    private final List<Account> ACCOUNTS = new ArrayList<>();

    private Account currentAccount;

    public void init() {
        ACCOUNTS.clear();
    }


    public void addAccount(AlteningServiceType type, String email, String password) {
        Account account = new Account(type, email, password);
        ACCOUNTS.add(account);
    }

    public void addAccount(String email, String password) {
        addAccount(AlteningServiceType.MOJANG, email, password);
    }

    public boolean removeAccount(String email, String password) {
        return removeAccount(accountBy(email, password));
    }

    public boolean removeAccount(Account account) {
        return ACCOUNTS.remove(account);
    }

    public Account accountBy(String email, String password) {
        return ACCOUNTS.stream().filter(account -> account.email().equals(email)).filter(account -> account.password().equals(email)).findFirst().orElse(null);
    }

    public Account accountBy(String username) {
        return ACCOUNTS.stream().filter(account -> account.info().username().equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    public List<Account> accounts() {
        return ACCOUNTS;
    }
    public Account currentAccount() {
        return currentAccount;
    }
    public void currentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }
}
