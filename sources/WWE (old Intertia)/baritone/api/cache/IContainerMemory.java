package baritone.api.cache;

import java.util.*;

public interface IContainerMemory
{
    IRememberedInventory getInventoryByPos(final et p0);
    
    Map<et, IRememberedInventory> getRememberedInventories();
}
