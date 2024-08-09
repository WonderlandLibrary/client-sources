package xyz.mathax.mathaxclient.systems.accounts.types;

import xyz.mathax.mathaxclient.systems.accounts.Account;
import xyz.mathax.mathaxclient.systems.accounts.AccountType;
import xyz.mathax.mathaxclient.systems.accounts.MicrosoftLogin;
import net.minecraft.client.util.Session;

import java.util.Optional;

public class MicrosoftAccount extends Account<MicrosoftAccount> {
    public MicrosoftAccount(String refreshToken) {
        super(AccountType.Microsoft, refreshToken);
    }

    @Override
    public boolean fetchInfo() {
        return auth() != null;
    }

    @Override
    public boolean login() {
        super.login();

        String token = auth();
        if (token == null) {
            return false;
        }

        cache.loadHead();
        setSession(new Session(cache.username, cache.uuid, token, Optional.empty(), Optional.empty(), Session.AccountType.MSA));

        return true;
    }

    private String auth() {
        MicrosoftLogin.LoginData data = MicrosoftLogin.login(name);
        if (!data.isGood()) {
            return null;
        }

        name = data.newRefreshToken;
        cache.username = data.username;
        cache.uuid = data.uuid;

        return data.mcToken;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MicrosoftAccount)) {
            return false;
        }

        return ((MicrosoftAccount) object).name.equals(this.name);
    }
}
