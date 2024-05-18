package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;

public class LayerVillagerArmor extends LayerBipedArmor
{
    @Override
    protected void initArmor() {
        this.field_177189_c = (T)new ModelZombieVillager(0.5f, 0.0f, " ".length() != 0);
        this.field_177186_d = (T)new ModelZombieVillager(1.0f, 0.0f, " ".length() != 0);
    }
    
    public LayerVillagerArmor(final RendererLivingEntity<?> rendererLivingEntity) {
        super(rendererLivingEntity);
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
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
