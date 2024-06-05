package xyz.vergoclient.ui.guis.altManager.addOns;

import java.util.Collection;

public interface AccountManager {

    void save();

    void load();

    void addAccount(final Account account);

    void deleteAccount(final Account account);

    Collection<Account> getAccounts();

    Collection<Account> getWorkingAccounts();

    Collection<Account> getUnbannedAccounts();

}
