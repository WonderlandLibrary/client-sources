package net.shoreline.client.api.manager.client;

import net.shoreline.client.api.account.Account;
import net.shoreline.client.api.account.microsoft.MicrosoftAuthenticator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Account
 */
public class AccountManager
{
    //
    private final List<Account> accounts = new ArrayList<>();
    // The Microsoft authenticator
    public static final MicrosoftAuthenticator MICROSOFT_AUTH =
            new MicrosoftAuthenticator();

    /**
     *
     *
     * @param account
     */
    public void register(Account account)
    {
        accounts.add(account);
    }

    /**
     *
     *
     * @param accounts
     *
     * @see #register(Account)
     */
    public void register(Account... accounts)
    {
        for (Account account : accounts)
        {
            register(account);
        }
    }

    /**
     *
     *
     * @return
     */
    public List<Account> getAccounts()
    {
        return accounts;
    }
}
