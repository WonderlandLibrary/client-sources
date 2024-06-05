package net.minecraft.src;

import net.minecraft.client.*;
import java.util.*;

public class CreativeCrafting implements ICrafting
{
    private final Minecraft mc;
    
    public CreativeCrafting(final Minecraft par1) {
        this.mc = par1;
    }
    
    @Override
    public void sendContainerAndContentsToPlayer(final Container par1Container, final List par2List) {
    }
    
    @Override
    public void sendSlotContents(final Container par1Container, final int par2, final ItemStack par3ItemStack) {
        this.mc.playerController.sendSlotPacket(par3ItemStack, par2);
    }
    
    @Override
    public void sendProgressBarUpdate(final Container par1Container, final int par2, final int par3) {
    }
}
