package optifine;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;

public class PlayerControllerOF extends PlayerControllerMP
{
    private boolean acting;
    
    public PlayerControllerOF(final Minecraft mcIn, final NetHandlerPlayClient netHandler) {
        super(mcIn, netHandler);
        this.acting = false;
    }
    
    @Override
    public boolean func_180511_b(final BlockPos loc, final EnumFacing face) {
        this.acting = true;
        final boolean res = super.func_180511_b(loc, face);
        this.acting = false;
        return res;
    }
    
    @Override
    public boolean func_180512_c(final BlockPos posBlock, final EnumFacing directionFacing) {
        this.acting = true;
        final boolean res = super.func_180512_c(posBlock, directionFacing);
        this.acting = false;
        return res;
    }
    
    @Override
    public boolean sendUseItem(final EntityPlayer player, final World worldIn, final ItemStack stack) {
        this.acting = true;
        final boolean res = super.sendUseItem(player, worldIn, stack);
        this.acting = false;
        return res;
    }
    
    @Override
    public boolean func_178890_a(final EntityPlayerSP player, final WorldClient worldIn, final ItemStack stack, final BlockPos pos, final EnumFacing facing, final Vec3 vec) {
        this.acting = true;
        final boolean res = super.func_178890_a(player, worldIn, stack, pos, facing, vec);
        this.acting = false;
        return res;
    }
    
    public boolean isActing() {
        return this.acting;
    }
}
