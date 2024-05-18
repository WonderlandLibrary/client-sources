package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockWorkbench extends Block
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        entityPlayer.displayGui(new InterfaceCraftingTable(world, blockPos));
        entityPlayer.triggerAchievement(StatList.field_181742_Z);
        return " ".length() != 0;
    }
    
    protected BlockWorkbench() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    public static class InterfaceCraftingTable implements IInteractionObject
    {
        private static final String[] I;
        private final World world;
        private final BlockPos position;
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("f<%\t\n", "HRDdo");
            InterfaceCraftingTable.I[" ".length()] = I("\u0004\b\u0006\u0011\u0010\u001b\u0000\u000e\u0000I\n\u0013\t\u0012\u0007\u0000\u000f\u000f+\u0007\b\u0003\u0004\u0011", "iahts");
        }
        
        @Override
        public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
            return new ContainerWorkbench(inventoryPlayer, this.world, this.position);
        }
        
        @Override
        public String getName() {
            return null;
        }
        
        @Override
        public boolean hasCustomName() {
            return "".length() != 0;
        }
        
        static {
            I();
        }
        
        @Override
        public String getGuiID() {
            return InterfaceCraftingTable.I[" ".length()];
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public InterfaceCraftingTable(final World world, final BlockPos position) {
            this.world = world;
            this.position = position;
        }
        
        @Override
        public IChatComponent getDisplayName() {
            return new ChatComponentTranslation(String.valueOf(Blocks.crafting_table.getUnlocalizedName()) + InterfaceCraftingTable.I["".length()], new Object["".length()]);
        }
    }
}
