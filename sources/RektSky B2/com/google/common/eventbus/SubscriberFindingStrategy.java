package com.google.common.eventbus;

import com.google.common.collect.*;

interface SubscriberFindingStrategy
{
    Multimap<Class<?>, EventSubscriber> findAllSubscribers(final Object p0);
}
