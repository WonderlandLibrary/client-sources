package optfine;

import net.minecraft.client.model.*;

public class PlayerItemRenderer
{
    private ModelRenderer modelRenderer;
    private int attachTo;
    private float scaleFactor;
    
    public void render(final ModelBiped modelBiped, final float n) {
        final ModelRenderer attachModel = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
        if (attachModel != null) {
            attachModel.postRender(n);
        }
        this.modelRenderer.render(n * this.scaleFactor);
    }
    
    public PlayerItemRenderer(final int attachTo, final float scaleFactor, final ModelRenderer modelRenderer) {
        this.attachTo = "".length();
        this.scaleFactor = 0.0f;
        this.modelRenderer = null;
        this.attachTo = attachTo;
        this.scaleFactor = scaleFactor;
        this.modelRenderer = modelRenderer;
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
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelRenderer getModelRenderer() {
        return this.modelRenderer;
    }
}
