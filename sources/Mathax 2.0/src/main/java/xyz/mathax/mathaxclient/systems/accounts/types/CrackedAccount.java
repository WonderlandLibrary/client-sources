package xyz.mathax.mathaxclient.systems.accounts.types;

import xyz.mathax.mathaxclient.systems.accounts.Account;
import xyz.mathax.mathaxclient.systems.accounts.AccountType;
import net.minecraft.client.util.Session;

import java.util.Optional;

public class CrackedAccount extends Account<CrackedAccount> {
    public CrackedAccount(String name) {
        super(AccountType.Cracked, name);
    }

    @Override
    public boolean fetchInfo() {
        cache.username = name;
        return true;
    }

    @Override
    public boolean login() {
        super.login();

        cache.loadHead();
        setSession(new Session(name, "", "", Optional.empty(), Optional.empty(), Session.AccountType.MOJANG));

        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CrackedAccount)) {
            return false;
        }

        return ((CrackedAccount) object).getUsername().equals(this.getUsername());
    }
}
