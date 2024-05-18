package mods.betterhat.main;

import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.events.RenderModelBipedEvent;
import net.minecraft.client.renderer.GlStateManager;

public class BetterHatListener implements Listener
{
    private void initModel(ModelRendererUV model, float size)
    {
        ModelBoxUV modelboxuv = null;
        float f = 1.135F;

        for (int i = -4; i < 4; ++i)
        {
            for (int j = -4; j < 4; ++j)
            {
                modelboxuv = ModelBoxUV.addBox(model, (float)i * 1.135F, -8.5225F, (float)j * 1.135F, 1, 1, 1, size + 0.07F);
                modelboxuv.setAllUV(44 + i, 3 - j);
                modelboxuv = ModelBoxUV.addBox(model, (float)i * 1.135F, -0.5575F, (float)j * 1.135F, 1, 1, 1, size + 0.07F);
                modelboxuv.setAllUV(52 + i, 3 - j);
            }
        }

        for (int k = -4; k < 4; ++k)
        {
            for (int i1 = -8; i1 < 0; ++i1)
            {
                modelboxuv = ModelBoxUV.addBox(model, (float)k * 1.135F, ((float)i1 + 0.5F) * 1.135F, -4.55F, 1, 1, 1, size + 0.07F);
                modelboxuv.setAllUV(44 + k, 16 + i1);
                modelboxuv = ModelBoxUV.addBox(model, (float)k * 1.135F, ((float)i1 + 0.5F) * 1.135F, 3.415F, 1, 1, 1, size + 0.07F);
                modelboxuv.setAllUV(60 + k, 16 + i1);
            }
        }

        for (int l = -3; l < 4; ++l)
        {
            for (int j1 = -8; j1 < 0; ++j1)
            {
                modelboxuv = ModelBoxUV.addBox(model, -4.55F, ((float)j1 + 0.5F) * 1.135F, (float)l * 1.135F, 1, 1, 1, size + 0.07F);
                modelboxuv.setAllUV(36 - l - 1, 16 + j1);
                modelboxuv = ModelBoxUV.addBox(model, 3.415F, ((float)j1 + 0.5F) * 1.135F, (float)l * 1.135F, 1, 1, 1, size + 0.07F);
                modelboxuv.setAllUV(52 + l, 16 + j1);
            }
        }

        modelboxuv.initQuads();
    }

    @EventHandler
    public void onRenderBipedHeadwear(RenderModelBipedEvent event)
    {
        event.setCancelled(true);

        if (!(event.getBipedHeadwear() instanceof ModelRendererUV))
        {
            ModelRendererUV modelrendereruv = new ModelRendererUV(event.getModelBiped(), 32, 0);
            this.initModel(modelrendereruv, 0.001F);
            modelrendereruv.setRotationPoint(0.0F, 0.0F + event.getVar5(), 0.0F);
            event.setBipedHeadwear(modelrendereruv);
        }
        else
        {
            GlStateManager.resetColor();
            GlStateManager.enableDepth();
            ((ModelRendererUV)event.getBipedHeadwear()).isHidden = false;
            ((ModelRendererUV)event.getBipedHeadwear()).applyRotation(event.getBipedHead());
            ((ModelRendererUV)event.getBipedHeadwear()).renderBetterHat(event.getScale());
            ((ModelRendererUV)event.getBipedHeadwear()).isHidden = true;
        }
    }
}
