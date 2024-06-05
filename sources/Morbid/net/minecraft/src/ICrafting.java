package net.minecraft.src;

import java.util.*;

public interface ICrafting
{
    void sendContainerAndContentsToPlayer(final Container p0, final List p1);
    
    void sendSlotContents(final Container p0, final int p1, final ItemStack p2);
    
    void sendProgressBarUpdate(final Container p0, final int p1, final int p2);
}
