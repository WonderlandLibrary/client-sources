package net.minecraft.client.particle;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class EntityParticleEmitter extends EntityFX
{
    private int lifetime;
    private EnumParticleTypes particleTypes;
    private Entity attachedEntity;
    private int age;
    
    public EntityParticleEmitter(final World world, final Entity attachedEntity, final EnumParticleTypes particleTypes) {
        super(world, attachedEntity.posX, attachedEntity.getEntityBoundingBox().minY + attachedEntity.height / 2.0f, attachedEntity.posZ, attachedEntity.motionX, attachedEntity.motionY, attachedEntity.motionZ);
        this.attachedEntity = attachedEntity;
        this.lifetime = "   ".length();
        this.particleTypes = particleTypes;
        this.onUpdate();
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
    }
    
    @Override
    public void onUpdate() {
        int i = "".length();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (i < (0x6C ^ 0x7C)) {
            final double n = this.rand.nextFloat() * 2.0f - 1.0f;
            final double n2 = this.rand.nextFloat() * 2.0f - 1.0f;
            final double n3 = this.rand.nextFloat() * 2.0f - 1.0f;
            if (n * n + n2 * n2 + n3 * n3 <= 1.0) {
                this.worldObj.spawnParticle(this.particleTypes, "".length() != 0, this.attachedEntity.posX + n * this.attachedEntity.width / 4.0, this.attachedEntity.getEntityBoundingBox().minY + this.attachedEntity.height / 2.0f + n2 * this.attachedEntity.height / 4.0, this.attachedEntity.posZ + n3 * this.attachedEntity.width / 4.0, n, n2 + 0.2, n3, new int["".length()]);
            }
            ++i;
        }
        this.age += " ".length();
        if (this.age >= this.lifetime) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return "   ".length();
    }
}
