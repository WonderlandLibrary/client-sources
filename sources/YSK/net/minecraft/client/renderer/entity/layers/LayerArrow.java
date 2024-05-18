package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class LayerArrow implements LayerRenderer<EntityLivingBase>
{
    private final RendererLivingEntity field_177168_a;
    
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final int arrowCountInEntity = entityLivingBase.getArrowCountInEntity();
        if (arrowCountInEntity > 0) {
            final EntityArrow entityArrow = new EntityArrow(entityLivingBase.worldObj, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);
            final Random random = new Random(entityLivingBase.getEntityId());
            RenderHelper.disableStandardItemLighting();
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < arrowCountInEntity) {
                GlStateManager.pushMatrix();
                final ModelRenderer randomModelBox = this.field_177168_a.getMainModel().getRandomModelBox(random);
                final ModelBox modelBox = randomModelBox.cubeList.get(random.nextInt(randomModelBox.cubeList.size()));
                randomModelBox.postRender(0.0625f);
                final float nextFloat = random.nextFloat();
                final float nextFloat2 = random.nextFloat();
                final float nextFloat3 = random.nextFloat();
                GlStateManager.translate((modelBox.posX1 + (modelBox.posX2 - modelBox.posX1) * nextFloat) / 16.0f, (modelBox.posY1 + (modelBox.posY2 - modelBox.posY1) * nextFloat2) / 16.0f, (modelBox.posZ1 + (modelBox.posZ2 - modelBox.posZ1) * nextFloat3) / 16.0f);
                final float n8 = nextFloat * 2.0f - 1.0f;
                final float n9 = nextFloat2 * 2.0f - 1.0f;
                final float n10 = nextFloat3 * 2.0f - 1.0f;
                final float n11 = n8 * -1.0f;
                final float n12 = n9 * -1.0f;
                final float n13 = n10 * -1.0f;
                final float sqrt_float = MathHelper.sqrt_float(n11 * n11 + n13 * n13);
                final EntityArrow entityArrow2 = entityArrow;
                final EntityArrow entityArrow3 = entityArrow;
                final float n14 = (float)(Math.atan2(n11, n13) * 180.0 / 3.141592653589793);
                entityArrow3.rotationYaw = n14;
                entityArrow2.prevRotationYaw = n14;
                final EntityArrow entityArrow4 = entityArrow;
                final EntityArrow entityArrow5 = entityArrow;
                final float n15 = (float)(Math.atan2(n12, sqrt_float) * 180.0 / 3.141592653589793);
                entityArrow5.rotationPitch = n15;
                entityArrow4.prevRotationPitch = n15;
                this.field_177168_a.getRenderManager().renderEntityWithPosYaw(entityArrow, 0.0, 0.0, 0.0, 0.0f, n3);
                GlStateManager.popMatrix();
                ++i;
            }
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
    
    public LayerArrow(final RendererLivingEntity field_177168_a) {
        this.field_177168_a = field_177168_a;
    }
}
