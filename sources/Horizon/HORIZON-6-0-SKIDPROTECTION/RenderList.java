package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;

public class RenderList extends ChunkRenderContainer
{
    private static final String Ý = "CL_00000957";
    
    @Override
    public void HorizonCode_Horizon_È(final EnumWorldBlockLayer p_178001_1_) {
        if (this.Â) {
            for (final RenderChunk var3 : this.HorizonCode_Horizon_È) {
                final ListedRenderChunk var4 = (ListedRenderChunk)var3;
                GlStateManager.Çªà¢();
                this.HorizonCode_Horizon_È(var3);
                GL11.glCallList(var4.HorizonCode_Horizon_È(p_178001_1_, var4.à()));
                GlStateManager.Ê();
            }
            GlStateManager.ÇŽÉ();
            this.HorizonCode_Horizon_È.clear();
        }
    }
}
