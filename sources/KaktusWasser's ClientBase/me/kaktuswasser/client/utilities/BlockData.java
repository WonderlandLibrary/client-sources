// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;

public class BlockData
{
    public BlockPos position;
    public EnumFacing face;
    
    public BlockData(final BlockPos position, final EnumFacing face) {
        this.position = position;
        this.face = face;
    }
}
