package wtf.diablo.client.scripting.api;

import wtf.diablo.client.event.api.AbstractEvent;
import wtf.diablo.client.module.api.data.AbstractModule;

public interface Script {

    void begin();

    void stop();

    void addFunctions();

    AbstractModule getAbstractModule();

    void registerEvent(final String event, final AbstractEvent abstractEvent);

    AbstractModule setAbstractModule(final AbstractModule abstractModule);

}
