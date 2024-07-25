package club.bluezenith.module.modules.fun;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.NoObf;
import club.bluezenith.util.render.RenderUtil;
import club.bluezenith.events.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class hampter extends Module {
    public hampter() {
        super("hampter", ModuleCategory.FUN, "hi");
    }
    @NoObf
    @Listener
    public void vbucksfreedownload2021(Render2DEvent e){
        GlStateManager.pushMatrix();
        RenderUtil.drawImage(new ResourceLocation("club/bluezenith/fun/hampter.jpg"), 0, e.resolution.getScaledHeight() - 120, 120, 120, 1);
        GlStateManager.resetColor();
        GlStateManager.popMatrix();
    }
}
