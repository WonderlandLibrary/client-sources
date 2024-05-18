package net.minecraft.src;

public class ModelPig extends ModelQuadruped
{
    public ModelPig() {
        this(0.0f);
    }
    
    public ModelPig(final float par1) {
        super(6, par1);
        this.head.setTextureOffset(16, 16).addBox(-2.0f, 0.0f, -9.0f, 4, 3, 1, par1);
        this.field_78145_g = 4.0f;
    }
}
