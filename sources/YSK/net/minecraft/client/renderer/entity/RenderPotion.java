package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class RenderPotion extends RenderSnowball<EntityPotion>
{
    @Override
    public ItemStack func_177082_d(final Entity entity) {
        return this.func_177082_d((EntityPotion)entity);
    }
    
    @Override
    public ItemStack func_177082_d(final EntityPotion entityPotion) {
        return new ItemStack(this.field_177084_a, " ".length(), entityPotion.getPotionDamage());
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RenderPotion(final RenderManager renderManager, final RenderItem renderItem) {
        super(renderManager, Items.potionitem, renderItem);
    }
}
