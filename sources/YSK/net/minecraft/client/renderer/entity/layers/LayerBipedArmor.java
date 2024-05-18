package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;

public class LayerBipedArmor extends LayerArmorBase<ModelBiped>
{
    @Override
    protected void func_177179_a(final ModelBase modelBase, final int n) {
        this.func_177179_a((ModelBiped)modelBase, n);
    }
    
    protected void func_177194_a(final ModelBiped modelBiped) {
        modelBiped.setInvisible("".length() != 0);
    }
    
    @Override
    protected void initArmor() {
        this.field_177189_c = (T)new ModelBiped(0.5f);
        this.field_177186_d = (T)new ModelBiped(1.0f);
    }
    
    public LayerBipedArmor(final RendererLivingEntity<?> rendererLivingEntity) {
        super(rendererLivingEntity);
    }
    
    @Override
    protected void func_177179_a(final ModelBiped modelBiped, final int n) {
        this.func_177194_a(modelBiped);
        switch (n) {
            case 1: {
                modelBiped.bipedRightLeg.showModel = (" ".length() != 0);
                modelBiped.bipedLeftLeg.showModel = (" ".length() != 0);
                "".length();
                if (1 < -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                modelBiped.bipedBody.showModel = (" ".length() != 0);
                modelBiped.bipedRightLeg.showModel = (" ".length() != 0);
                modelBiped.bipedLeftLeg.showModel = (" ".length() != 0);
                "".length();
                if (true != true) {
                    throw null;
                }
                break;
            }
            case 3: {
                modelBiped.bipedBody.showModel = (" ".length() != 0);
                modelBiped.bipedRightArm.showModel = (" ".length() != 0);
                modelBiped.bipedLeftArm.showModel = (" ".length() != 0);
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            case 4: {
                modelBiped.bipedHead.showModel = (" ".length() != 0);
                modelBiped.bipedHeadwear.showModel = (" ".length() != 0);
                break;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
