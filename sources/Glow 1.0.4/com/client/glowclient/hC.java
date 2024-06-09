package com.client.glowclient;

import net.minecraft.tileentity.*;
import net.minecraft.entity.*;

public class HC extends Exception
{
    public HC(final TileEntity tileEntity, final Throwable t) {
        super(String.valueOf(tileEntity), t);
    }
    
    public HC(final Entity entity, final Throwable t) {
        super(String.valueOf(entity), t);
    }
    
    public HC(final String s, final Throwable t) {
        super(s, t);
    }
}
