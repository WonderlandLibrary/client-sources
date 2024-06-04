package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PlayerControllerOF extends PlayerControllerMP
{
  private boolean acting = false;
  
  public PlayerControllerOF(Minecraft mcIn, NetHandlerPlayClient netHandler)
  {
    super(mcIn, netHandler);
  }
  
  public boolean func_180511_b(BlockPos loc, EnumFacing face)
  {
    acting = true;
    boolean res = super.func_180511_b(loc, face);
    acting = false;
    return res;
  }
  
  public boolean func_180512_c(BlockPos posBlock, EnumFacing directionFacing)
  {
    acting = true;
    boolean res = super.func_180512_c(posBlock, directionFacing);
    acting = false;
    return res;
  }
  



  public boolean sendUseItem(EntityPlayer player, World worldIn, ItemStack stack)
  {
    acting = true;
    boolean res = super.sendUseItem(player, worldIn, stack);
    acting = false;
    return res;
  }
  
  public boolean func_178890_a(EntityPlayerSP player, WorldClient worldIn, ItemStack stack, BlockPos pos, EnumFacing facing, Vec3 vec)
  {
    acting = true;
    boolean res = super.func_178890_a(player, worldIn, stack, pos, facing, vec);
    acting = false;
    return res;
  }
  
  public boolean isActing()
  {
    return acting;
  }
}
