package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.util.Iterator;

public class VboRenderList extends ChunkRenderContainer
{
    private static final String Ý = "CL_00002533";
    
    @Override
    public void HorizonCode_Horizon_È(final EnumWorldBlockLayer p_178001_1_) {
        if (this.Â) {
            for (final RenderChunk var3 : this.HorizonCode_Horizon_È) {
                final VertexBuffer var4 = var3.Â(p_178001_1_.ordinal());
                GlStateManager.Çªà¢();
                this.HorizonCode_Horizon_È(var3);
                var3.Ó();
                var4.HorizonCode_Horizon_È();
                this.HorizonCode_Horizon_È();
                var4.HorizonCode_Horizon_È(7);
                GlStateManager.Ê();
            }
            OpenGlHelper.à(OpenGlHelper.Ç, 0);
            GlStateManager.ÇŽÉ();
            this.HorizonCode_Horizon_È.clear();
        }
    }
    
    private void HorizonCode_Horizon_È() {
        GL11.glVertexPointer(3, 5126, 28, 0L);
        GL11.glColorPointer(4, 5121, 28, 12L);
        GL11.glTexCoordPointer(2, 5126, 28, 16L);
        OpenGlHelper.á(OpenGlHelper.µà);
        GL11.glTexCoordPointer(2, 5122, 28, 24L);
        OpenGlHelper.á(OpenGlHelper.£à);
    }
}
