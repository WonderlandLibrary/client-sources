package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public class ModelArmorStandArmor extends ModelBiped
{
    public ModelArmorStandArmor(final float n) {
        this(n, 0x17 ^ 0x57, 0x38 ^ 0x18);
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
            if (1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelArmorStandArmor() {
        this(0.0f);
    }
    
    protected ModelArmorStandArmor(final float n, final int n2, final int n3) {
        super(n, 0.0f, n2, n3);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        if (entity instanceof EntityArmorStand) {
            final EntityArmorStand entityArmorStand = (EntityArmorStand)entity;
            this.bipedHead.rotateAngleX = 0.017453292f * entityArmorStand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = 0.017453292f * entityArmorStand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = 0.017453292f * entityArmorStand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0f, 1.0f, 0.0f);
            this.bipedBody.rotateAngleX = 0.017453292f * entityArmorStand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = 0.017453292f * entityArmorStand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = 0.017453292f * entityArmorStand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = 0.017453292f * entityArmorStand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = 0.017453292f * entityArmorStand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = 0.017453292f * entityArmorStand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = 0.017453292f * entityArmorStand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = 0.017453292f * entityArmorStand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = 0.017453292f * entityArmorStand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = 0.017453292f * entityArmorStand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = 0.017453292f * entityArmorStand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = 0.017453292f * entityArmorStand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9f, 11.0f, 0.0f);
            this.bipedRightLeg.rotateAngleX = 0.017453292f * entityArmorStand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = 0.017453292f * entityArmorStand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = 0.017453292f * entityArmorStand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9f, 11.0f, 0.0f);
            ModelBase.copyModelAngles(this.bipedHead, this.bipedHeadwear);
        }
    }
}
