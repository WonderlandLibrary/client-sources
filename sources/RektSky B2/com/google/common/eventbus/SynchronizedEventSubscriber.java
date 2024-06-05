package com.google.common.eventbus;

import java.lang.reflect.*;

final class SynchronizedEventSubscriber extends EventSubscriber
{
    public SynchronizedEventSubscriber(final Object target, final Method method) {
        super(target, method);
    }
    
    @Override
    public void handleEvent(final Object event) throws InvocationTargetException {
        synchronized (this) {
            super.handleEvent(event);
        }
    }
}
