package in.momin5.cookieclient.api.event.util;

import in.momin5.cookieclient.api.event.Event;

public interface MultiPhase <T extends Event>{
    Phase getPhase();

    T nextPhase();
}
