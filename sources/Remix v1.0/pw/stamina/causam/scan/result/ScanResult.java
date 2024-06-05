package pw.stamina.causam.scan.result;

import java.util.*;
import pw.stamina.causam.subscribe.*;

public interface ScanResult
{
    Set<Subscription> getSubscriptions();
}
