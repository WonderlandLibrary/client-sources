package gq.vapu.czfclient.API.eventapi.events;

public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean var1);
}
