package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class CommandClone extends CommandBase
{
  private static final String __OBFID = "CL_00002348";
  
  public CommandClone() {}
  
  public String getCommandName()
  {
    return "clone";
  }
  



  public int getRequiredPermissionLevel()
  {
    return 2;
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "commands.clone.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args) throws CommandException
  {
    if (args.length < 9)
    {
      throw new WrongUsageException("commands.clone.usage", new Object[0]);
    }
    

    sender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
    BlockPos var3 = func_175757_a(sender, args, 0, false);
    BlockPos var4 = func_175757_a(sender, args, 3, false);
    BlockPos var5 = func_175757_a(sender, args, 6, false);
    StructureBoundingBox var6 = new StructureBoundingBox(var3, var4);
    StructureBoundingBox var7 = new StructureBoundingBox(var5, var5.add(var6.func_175896_b()));
    int var8 = var6.getXSize() * var6.getYSize() * var6.getZSize();
    
    if (var8 > 32768)
    {
      throw new CommandException("commands.clone.tooManyBlocks", new Object[] { Integer.valueOf(var8), Integer.valueOf(32768) });
    }
    

    boolean var9 = false;
    Block var10 = null;
    int var11 = -1;
    
    if (((args.length < 11) || ((!args[10].equals("force")) && (!args[10].equals("move")))) && (var6.intersectsWith(var7)))
    {
      throw new CommandException("commands.clone.noOverlap", new Object[0]);
    }
    

    if ((args.length >= 11) && (args[10].equals("move")))
    {
      var9 = true;
    }
    
    if ((minY >= 0) && (maxY < 256) && (minY >= 0) && (maxY < 256))
    {
      World var12 = sender.getEntityWorld();
      
      if ((var12.isAreaLoaded(var6)) && (var12.isAreaLoaded(var7)))
      {
        boolean var13 = false;
        
        if (args.length >= 10)
        {
          if (args[9].equals("masked"))
          {
            var13 = true;
          }
          else if (args[9].equals("filtered"))
          {
            if (args.length < 12)
            {
              throw new WrongUsageException("commands.clone.usage", new Object[0]);
            }
            
            var10 = getBlockByText(sender, args[11]);
            
            if (args.length >= 13)
            {
              var11 = parseInt(args[12], 0, 15);
            }
          }
        }
        
        ArrayList var14 = Lists.newArrayList();
        ArrayList var15 = Lists.newArrayList();
        ArrayList var16 = Lists.newArrayList();
        LinkedList var17 = Lists.newLinkedList();
        BlockPos var18 = new BlockPos(minX - minX, minY - minY, minZ - minZ);
        
        for (int var19 = minZ; var19 <= maxZ; var19++)
        {
          for (int var20 = minY; var20 <= maxY; var20++)
          {
            for (int var21 = minX; var21 <= maxX; var21++)
            {
              BlockPos var22 = new BlockPos(var21, var20, var19);
              BlockPos var23 = var22.add(var18);
              IBlockState var24 = var12.getBlockState(var22);
              
              if (((!var13) || (var24.getBlock() != net.minecraft.init.Blocks.air)) && ((var10 == null) || ((var24.getBlock() == var10) && ((var11 < 0) || (var24.getBlock().getMetaFromState(var24) == var11)))))
              {
                TileEntity var25 = var12.getTileEntity(var22);
                
                if (var25 != null)
                {
                  NBTTagCompound var26 = new NBTTagCompound();
                  var25.writeToNBT(var26);
                  var15.add(new StaticCloneData(var23, var24, var26));
                  var17.addLast(var22);
                }
                else if ((!var24.getBlock().isFullBlock()) && (!var24.getBlock().isFullCube()))
                {
                  var16.add(new StaticCloneData(var23, var24, null));
                  var17.addFirst(var22);
                }
                else
                {
                  var14.add(new StaticCloneData(var23, var24, null));
                  var17.addLast(var22);
                }
              }
            }
          }
        }
        
        if (var9)
        {
          BlockPos var29;
          

          for (Iterator var27 = var17.iterator(); var27.hasNext(); var12.setBlockState(var29, net.minecraft.init.Blocks.barrier.getDefaultState(), 2))
          {
            var29 = (BlockPos)var27.next();
            TileEntity var31 = var12.getTileEntity(var29);
            
            if ((var31 instanceof IInventory))
            {
              ((IInventory)var31).clearInventory();
            }
          }
          
          var27 = var17.iterator();
          
          while (var27.hasNext())
          {
            BlockPos var29 = (BlockPos)var27.next();
            var12.setBlockState(var29, net.minecraft.init.Blocks.air.getDefaultState(), 3);
          }
        }
        
        ArrayList var28 = Lists.newArrayList();
        var28.addAll(var14);
        var28.addAll(var15);
        var28.addAll(var16);
        java.util.List var30 = Lists.reverse(var28);
        

        StaticCloneData var34;
        
        for (Iterator var32 = var30.iterator(); var32.hasNext(); var12.setBlockState(field_179537_a, net.minecraft.init.Blocks.barrier.getDefaultState(), 2))
        {
          var34 = (StaticCloneData)var32.next();
          TileEntity var36 = var12.getTileEntity(field_179537_a);
          
          if ((var36 instanceof IInventory))
          {
            ((IInventory)var36).clearInventory();
          }
        }
        
        var8 = 0;
        var32 = var28.iterator();
        
        while (var32.hasNext())
        {
          StaticCloneData var34 = (StaticCloneData)var32.next();
          
          if (var12.setBlockState(field_179537_a, field_179535_b, 2))
          {
            var8++;
          }
        }
        StaticCloneData var34;
        for (var32 = var15.iterator(); var32.hasNext(); var12.setBlockState(field_179537_a, field_179535_b, 2))
        {
          var34 = (StaticCloneData)var32.next();
          TileEntity var36 = var12.getTileEntity(field_179537_a);
          
          if ((field_179536_c != null) && (var36 != null))
          {
            field_179536_c.setInteger("x", field_179537_a.getX());
            field_179536_c.setInteger("y", field_179537_a.getY());
            field_179536_c.setInteger("z", field_179537_a.getZ());
            var36.readFromNBT(field_179536_c);
            var36.markDirty();
          }
        }
        
        var32 = var30.iterator();
        
        while (var32.hasNext())
        {
          StaticCloneData var34 = (StaticCloneData)var32.next();
          var12.func_175722_b(field_179537_a, field_179535_b.getBlock());
        }
        
        java.util.List var33 = var12.func_175712_a(var6, false);
        
        if (var33 != null)
        {
          Iterator var35 = var33.iterator();
          
          while (var35.hasNext())
          {
            NextTickListEntry var37 = (NextTickListEntry)var35.next();
            
            if (var6.func_175898_b(field_180282_a))
            {
              BlockPos var38 = field_180282_a.add(var18);
              var12.func_180497_b(var38, var37.func_151351_a(), (int)(scheduledTime - var12.getWorldInfo().getWorldTotalTime()), priority);
            }
          }
        }
        
        if (var8 <= 0)
        {
          throw new CommandException("commands.clone.failed", new Object[0]);
        }
        

        sender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, var8);
        notifyOperators(sender, this, "commands.clone.success", new Object[] { Integer.valueOf(var8) });

      }
      else
      {
        throw new CommandException("commands.clone.outOfWorld", new Object[0]);
      }
    }
    else
    {
      throw new CommandException("commands.clone.outOfWorld", new Object[0]);
    }
  }
  



  public java.util.List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
  {
    return (args.length == 12) && ("filtered".equals(args[9])) ? func_175762_a(args, Block.blockRegistry.getKeys()) : args.length == 11 ? getListOfStringsMatchingLastWord(args, new String[] { "normal", "force", "move" }) : args.length == 10 ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "masked", "filtered" }) : (args.length > 6) && (args.length <= 9) ? func_175771_a(args, 6, pos) : (args.length > 3) && (args.length <= 6) ? func_175771_a(args, 3, pos) : (args.length > 0) && (args.length <= 3) ? func_175771_a(args, 0, pos) : null;
  }
  
  static class StaticCloneData
  {
    public final BlockPos field_179537_a;
    public final IBlockState field_179535_b;
    public final NBTTagCompound field_179536_c;
    private static final String __OBFID = "CL_00002347";
    
    public StaticCloneData(BlockPos p_i46037_1_, IBlockState p_i46037_2_, NBTTagCompound p_i46037_3_)
    {
      field_179537_a = p_i46037_1_;
      field_179535_b = p_i46037_2_;
      field_179536_c = p_i46037_3_;
    }
  }
}
