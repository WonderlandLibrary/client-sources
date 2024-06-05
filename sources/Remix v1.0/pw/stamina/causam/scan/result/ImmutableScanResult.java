package pw.stamina.causam.scan.result;

import pw.stamina.causam.subscribe.*;
import java.util.*;

final class ImmutableScanResult implements ScanResult
{
    private final Set<Subscription> subscriptions;
    
    ImmutableScanResult(final Set<Subscription> subscriptions) {
        this.subscriptions = (Set<Subscription>)Collections.unmodifiableSet((Set<? extends Subscription>)subscriptions);
    }
    
    @Override
    public Set<Subscription> getSubscriptions() {
        return this.subscriptions;
    }
}
