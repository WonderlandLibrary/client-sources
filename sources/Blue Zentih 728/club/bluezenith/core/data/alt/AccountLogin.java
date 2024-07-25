package club.bluezenith.core.data.alt;

import club.bluezenith.core.data.alt.info.AccountInfo;
import net.minecraft.util.Session;

public class AccountLogin {
    private final Session session;
    private final AccountInfo account;

    public AccountLogin(Session session, AccountInfo account) {
        this.session = session;
        this.account = account;
    }

    public Session getSession() {
        return session;
    }

    public AccountInfo getAccount() {
        return account;
    }

}
