package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock
{
    private static final String[] I;
    private int field_179501_f;
    private final EntityVillager theVillager;
    private boolean field_179503_e;
    private boolean hasFarmItem;
    
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
            if (-1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAIHarvestFarmland(final EntityVillager theVillager, final double n) {
        super(theVillager, n, 0xE ^ 0x1E);
        this.theVillager = theVillager;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!this.theVillager.worldObj.getGameRules().getBoolean(EntityAIHarvestFarmland.I["".length()])) {
                return "".length() != 0;
            }
            this.field_179501_f = -" ".length();
            this.hasFarmItem = this.theVillager.isFarmItemInInventory();
            this.field_179503_e = this.theVillager.func_175557_cr();
        }
        return super.shouldExecute();
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        this.theVillager.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + " ".length(), this.destinationBlock.getZ() + 0.5, 10.0f, this.theVillager.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            final World worldObj = this.theVillager.worldObj;
            final BlockPos up = this.destinationBlock.up();
            final IBlockState blockState = worldObj.getBlockState(up);
            final Block block = blockState.getBlock();
            if (this.field_179501_f == 0 && block instanceof BlockCrops && blockState.getValue((IProperty<Integer>)BlockCrops.AGE) == (0x92 ^ 0x95)) {
                worldObj.destroyBlock(up, " ".length() != 0);
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
            else if (this.field_179501_f == " ".length() && block == Blocks.air) {
                final InventoryBasic villagerInventory = this.theVillager.getVillagerInventory();
                int i = "".length();
                "".length();
                if (!true) {
                    throw null;
                }
                while (i < villagerInventory.getSizeInventory()) {
                    final ItemStack stackInSlot = villagerInventory.getStackInSlot(i);
                    int n = "".length();
                    if (stackInSlot != null) {
                        if (stackInSlot.getItem() == Items.wheat_seeds) {
                            worldObj.setBlockState(up, Blocks.wheat.getDefaultState(), "   ".length());
                            n = " ".length();
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else if (stackInSlot.getItem() == Items.potato) {
                            worldObj.setBlockState(up, Blocks.potatoes.getDefaultState(), "   ".length());
                            n = " ".length();
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                        }
                        else if (stackInSlot.getItem() == Items.carrot) {
                            worldObj.setBlockState(up, Blocks.carrots.getDefaultState(), "   ".length());
                            n = " ".length();
                        }
                    }
                    if (n != 0) {
                        final ItemStack itemStack = stackInSlot;
                        itemStack.stackSize -= " ".length();
                        if (stackInSlot.stackSize > 0) {
                            break;
                        }
                        villagerInventory.setInventorySlotContents(i, null);
                        "".length();
                        if (true != true) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
            this.field_179501_f = -" ".length();
            this.runDelay = (0x65 ^ 0x6F);
        }
    }
    
    @Override
    protected boolean shouldMoveTo(final World world, BlockPos up) {
        if (world.getBlockState(up).getBlock() == Blocks.farmland) {
            up = up.up();
            final IBlockState blockState = world.getBlockState(up);
            final Block block = blockState.getBlock();
            if (block instanceof BlockCrops && blockState.getValue((IProperty<Integer>)BlockCrops.AGE) == (0x98 ^ 0x9F) && this.field_179503_e && (this.field_179501_f == 0 || this.field_179501_f < 0)) {
                this.field_179501_f = "".length();
                return " ".length() != 0;
            }
            if (block == Blocks.air && this.hasFarmItem && (this.field_179501_f == " ".length() || this.field_179501_f < 0)) {
                this.field_179501_f = " ".length();
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
    }
    
    static {
        I();
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.field_179501_f >= 0 && super.continueExecuting()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0005\r2\u0003(\u0001\u00076-4\u000f", "hbPDZ");
    }
}
