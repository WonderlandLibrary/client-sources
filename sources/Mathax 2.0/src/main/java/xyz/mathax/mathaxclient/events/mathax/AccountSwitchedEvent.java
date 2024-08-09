package xyz.mathax.mathaxclient.events.mathax;

public class AccountSwitchedEvent {
    private static final AccountSwitchedEvent INSTANCE = new AccountSwitchedEvent();

    public static AccountSwitchedEvent get() {
        return INSTANCE;
    }
}
