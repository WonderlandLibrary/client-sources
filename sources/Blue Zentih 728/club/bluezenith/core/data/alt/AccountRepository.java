package club.bluezenith.core.data.alt;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.core.data.preferences.DataHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.MinecraftInstance.mc;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

public class AccountRepository implements DataHandler {

    private AccountInfo currentAccount;
    private volatile CompletableFuture<AccountLogin> ongoingLogin; //prevent multiple logins at the same time to avoid IP bans?
    private final AccountSerializer accountSerializer;
    protected List<AccountInfo> accounts;

    private long loginStart;

    public AccountRepository() {
        accounts = new ArrayList<>();
        accountSerializer = new AccountSerializer();
    }

    public boolean isLoggingIn() {
        return ongoingLogin != null && !ongoingLogin.isDone();
    }

    public void setOngoingLogin(AccountInfo info, CompletableFuture<AccountLogin> loginTask) {
        final boolean hadOngoingLogin = this.ongoingLogin != null;

        if(currentTimeMillis() - loginStart > 10_000L && loginStart != 0 || !hadOngoingLogin) {
            this.ongoingLogin = loginTask;
            setTimeout(loginTask);
            loginTask.whenComplete(((session, throwable) -> {
                if(throwable != null) { //something went wrong while executing the callback, but it didn't time out
                    if(throwable.getCause() == null)
                        getBlueZenith().getNotificationPublisher().postError(
                                "Account Manager",
                                "Something went wrong while logging into the account.\nCause: " + throwable.getClass().getName(),
                                5000
                        );
                    else {
                        info.setInvalidCredentials(true);
                        getBlueZenith().getNotificationPublisher().postError(
                                "Account Manager",
                                "Couldn't log into the account", 5000);
                    }
                } else {
                    this.currentAccount = session.getAccount();
                    this.currentAccount.setUsername(session.getSession().getUsername());
                    mc.session = session.getSession();
                    session.getSession().getSessionID();
                    info.setInvalidCredentials(false);
                    getBlueZenith().getNotificationPublisher().postSuccess(
                            "Account Manager",
                            "Logged in as " + session.getSession().getUsername(),
                            2500);
                }
                this.ongoingLogin = null;
            }));
            loginStart = currentTimeMillis();
        } else {
            loginTask.cancel(true);
            getBlueZenith().getNotificationPublisher().postError(
                    "Account Manager",
                    "Already logging in!",
                    2500
            );
        }
    }

    public void addAccount(AccountInfo account) {
        this.accounts.add(account);
    }

    public boolean isAccountAdded(AccountInfo account) {
        return this.accounts.contains(account);
    }

    public boolean removeAccount(AccountInfo account) {
        return this.accounts.remove(account);
    }

    public AccountInfo getCurrentAccount() {
        return this.currentAccount;
    }

    public List<AccountInfo> getAccounts() {
        return this.accounts;
    }

    private void setTimeout(CompletableFuture<?> future) {
        BlueZenith.scheduledExecutorService.schedule(() -> {
         try {
            future.get(10, TimeUnit.SECONDS);
        } catch (Exception exception) {
             if(exception.getCause() != null && exception.getCause().getMessage().contains("InvalidCredentialsException")) return; //error thrown on invalid creds, ignore it
            if(exception instanceof TimeoutException) { //the future has timed out (10s), login failed.
                this.ongoingLogin = null;
                getBlueZenith().getNotificationPublisher().postError(
                        "Account Manager",
                        "Couldn't login to the account because of a timeout.",
                        3000
                );
            } else {
                getBlueZenith().getNotificationPublisher().postError(
                        "Account Manager",
                        "An error has occurred. See logs for more info.",
                        4000
                );
                exception.printStackTrace();
            }
        }
        }, 10, TimeUnit.SECONDS);
    }

    @Override
    public void serialize() {
        accountSerializer.serialize(this);
    }

    @Override
    public void deserialize() {
        accountSerializer.deserialize(this);
        this.currentAccount = new AccountInfo(mc.session);
        distinct();
    }

    public void distinct() {
        this.accounts = this.accounts.stream().distinct().collect(toList());
    }
}
