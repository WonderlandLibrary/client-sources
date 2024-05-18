package net.minecraft.entity.ai;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.block.*;
import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIEatGrass extends EntityAIBase
{
    private static final Predicate<IBlockState> field_179505_b;
    private EntityLiving grassEaterEntity;
    int eatingGrassTimer;
    private static final String[] I;
    private World entityWorld;
    
    public EntityAIEatGrass(final EntityLiving grassEaterEntity) {
        this.grassEaterEntity = grassEaterEntity;
        this.entityWorld = grassEaterEntity.worldObj;
        this.setMutexBits(0x25 ^ 0x22);
    }
    
    @Override
    public void resetTask() {
        this.eatingGrassTimer = "".length();
    }
    
    static {
        I();
        field_179505_b = (Predicate)BlockStateHelper.forBlock(Blocks.tallgrass).where(BlockTallGrass.TYPE, (com.google.common.base.Predicate<? extends BlockTallGrass.EnumType>)Predicates.equalTo((Object)BlockTallGrass.EnumType.GRASS));
    }
    
    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.eatingGrassTimer > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void startExecuting() {
        this.eatingGrassTimer = (0x23 ^ 0xB);
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte)(0x62 ^ 0x68));
        this.grassEaterEntity.getNavigator().clearPathEntity();
    }
    
    @Override
    public void updateTask() {
        this.eatingGrassTimer = Math.max("".length(), this.eatingGrassTimer - " ".length());
        if (this.eatingGrassTimer == (0x9F ^ 0x9B)) {
            final BlockPos blockPos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
            if (EntityAIEatGrass.field_179505_b.apply((Object)this.entityWorld.getBlockState(blockPos))) {
                if (this.entityWorld.getGameRules().getBoolean(EntityAIEatGrass.I["".length()])) {
                    this.entityWorld.destroyBlock(blockPos, "".length() != 0);
                }
                this.grassEaterEntity.eatGrassBonus();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                final BlockPos down = blockPos.down();
                if (this.entityWorld.getBlockState(down).getBlock() == Blocks.grass) {
                    if (this.entityWorld.getGameRules().getBoolean(EntityAIEatGrass.I[" ".length()])) {
                        this.entityWorld.playAuxSFX(1022 + 1632 - 2036 + 1383, down, Block.getIdFromBlock(Blocks.grass));
                        this.entityWorld.setBlockState(down, Blocks.dirt.getDefaultState(), "  ".length());
                    }
                    this.grassEaterEntity.eatGrassBonus();
                }
            }
        }
    }
    
    @Override
    public boolean shouldExecute() {
        final Random rng = this.grassEaterEntity.getRNG();
        int n;
        if (this.grassEaterEntity.isChild()) {
            n = (0xF0 ^ 0xC2);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            n = 953 + 607 - 774 + 214;
        }
        if (rng.nextInt(n) != 0) {
            return "".length() != 0;
        }
        final BlockPos blockPos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
        int n2;
        if (EntityAIEatGrass.field_179505_b.apply((Object)this.entityWorld.getBlockState(blockPos))) {
            n2 = " ".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else if (this.entityWorld.getBlockState(blockPos.down()).getBlock() == Blocks.grass) {
            n2 = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\b\")$3\f(-\n/\u0002", "eMKcA");
        EntityAIEatGrass.I[" ".length()] = I("4 *?+0*.\u00117>", "YOHxY");
    }
}
