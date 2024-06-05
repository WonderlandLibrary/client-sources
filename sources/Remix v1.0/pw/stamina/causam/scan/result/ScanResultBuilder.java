package pw.stamina.causam.scan.result;

import pw.stamina.causam.subscribe.*;
import java.util.*;

public final class ScanResultBuilder
{
    private Set<Subscription> subscriptions;
    
    public ScanResultBuilder() {
        this.subscriptions = null;
    }
    
    public ScanResultBuilder addSubscription(final Subscription<?> subscription) {
        Objects.requireNonNull(subscription, "subscription");
        if (!this.hasSubscriptions()) {
            this.subscriptions = new HashSet<Subscription>();
        }
        this.subscriptions.add(subscription);
        return this;
    }
    
    public ScanResult build() {
        return this.hasSubscriptions() ? new ImmutableScanResult(this.subscriptions) : EmptyScanResult.INSTANCE;
    }
    
    private boolean hasSubscriptions() {
        return this.subscriptions != null;
    }
}
