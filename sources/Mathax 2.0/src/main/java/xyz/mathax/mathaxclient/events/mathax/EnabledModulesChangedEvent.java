package xyz.mathax.mathaxclient.events.mathax;

public class EnabledModulesChangedEvent {
    private static final EnabledModulesChangedEvent INSTANCE = new EnabledModulesChangedEvent();

    public static EnabledModulesChangedEvent get() {
        return INSTANCE;
    }
}
