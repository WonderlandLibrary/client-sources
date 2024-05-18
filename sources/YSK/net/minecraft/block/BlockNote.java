package net.minecraft.block;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import com.google.common.collect.*;

public class BlockNote extends BlockContainer
{
    private static final String[] I;
    private static final List<String> INSTRUMENTS;
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, BlockNote.I[0x81 ^ 0x84] + this.getInstrument(n), 3.0f, (float)Math.pow(2.0, (n2 - (0x2E ^ 0x22)) / 12.0));
        world.spawnParticle(EnumParticleTypes.NOTE, blockPos.getX() + 0.5, blockPos.getY() + 1.2, blockPos.getZ() + 0.5, n2 / 24.0, 0.0, 0.0, new int["".length()]);
        return " ".length() != 0;
    }
    
    public BlockNote() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    private String getInstrument(int length) {
        if (length < 0 || length >= BlockNote.INSTRUMENTS.size()) {
            length = "".length();
        }
        return BlockNote.INSTRUMENTS.get(length);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityNote) {
            final TileEntityNote tileEntityNote = (TileEntityNote)tileEntity;
            tileEntityNote.changePitch();
            tileEntityNote.triggerNote(world, blockPos);
            entityPlayer.triggerAchievement(StatList.field_181735_S);
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x62 ^ 0x64])["".length()] = I("\u0000\u0019\u000b\b", "hxyxS");
        BlockNote.I[" ".length()] = I("\u0010/", "rKftU");
        BlockNote.I["  ".length()] = I("!\u0014-0\n", "RzLBo");
        BlockNote.I["   ".length()] = I("-(.", "EIZOI");
        BlockNote.I[0x4A ^ 0x4E] = I("\u0013'\u0018\u0003+\u00052\n\u0013!", "qFkpJ");
        BlockNote.I[0xBA ^ 0xBF] = I("\u001f\u0004\u001c7Z", "qkhRt");
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final boolean blockPowered = world.isBlockPowered(blockPos);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityNote) {
            final TileEntityNote tileEntityNote = (TileEntityNote)tileEntity;
            if (tileEntityNote.previousRedstoneState != blockPowered) {
                if (blockPowered) {
                    tileEntityNote.triggerNote(world, blockPos);
                }
                tileEntityNote.previousRedstoneState = blockPowered;
            }
        }
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityNote) {
                ((TileEntityNote)tileEntity).triggerNote(world, blockPos);
                entityPlayer.triggerAchievement(StatList.field_181734_R);
            }
        }
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityNote();
    }
    
    static {
        I();
        final String[] array = new String[0x67 ^ 0x62];
        array["".length()] = BlockNote.I["".length()];
        array[" ".length()] = BlockNote.I[" ".length()];
        array["  ".length()] = BlockNote.I["  ".length()];
        array["   ".length()] = BlockNote.I["   ".length()];
        array[0x62 ^ 0x66] = BlockNote.I[0x71 ^ 0x75];
        INSTRUMENTS = Lists.newArrayList((Object[])array);
    }
}
