package pw.stamina.causam.scan.result;

import pw.stamina.causam.subscribe.*;
import java.util.*;

enum EmptyScanResult implements ScanResult
{
    INSTANCE;
    
    @Override
    public Set<Subscription> getSubscriptions() {
        return (Set<Subscription>)Collections.emptySet();
    }
}
