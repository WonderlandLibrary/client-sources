package net.minecraft.client.renderer.entity;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;

public class RenderMinecartMobSpawner extends RenderMinecart<EntityMinecartMobSpawner>
{
    @Override
    protected void func_180560_a(final EntityMinecart entityMinecart, final float n, final IBlockState blockState) {
        this.func_180560_a((EntityMinecartMobSpawner)entityMinecart, n, blockState);
    }
    
    @Override
    protected void func_180560_a(final EntityMinecartMobSpawner entityMinecartMobSpawner, final float n, final IBlockState blockState) {
        super.func_180560_a(entityMinecartMobSpawner, n, blockState);
        if (blockState.getBlock() == Blocks.mob_spawner) {
            TileEntityMobSpawnerRenderer.renderMob(entityMinecartMobSpawner.func_98039_d(), entityMinecartMobSpawner.posX, entityMinecartMobSpawner.posY, entityMinecartMobSpawner.posZ, n);
        }
    }
    
    public RenderMinecartMobSpawner(final RenderManager renderManager) {
        super(renderManager);
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
