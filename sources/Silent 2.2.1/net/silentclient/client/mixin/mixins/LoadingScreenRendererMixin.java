package net.silentclient.client.mixin.mixins;

import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mixin.accessors.MinecraftAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;

@Mixin(LoadingScreenRenderer.class)
public class LoadingScreenRendererMixin {
    @Shadow private boolean loadingSuccess;

    @Shadow private long systemTime;

    @Shadow private Minecraft mc;

    @Shadow private String currentlyDisplayedText;

    /**
     * @author kirillsaint
     * @reason Custom Loading Screen
     */
    @Overwrite
    public void setLoadingProgress(int progress)
    {
        if (progress < 0 && mc.theWorld != null) {
            return;
        }
        if (!((MinecraftAccessor) this.mc).isRunning())
        {
            if (!this.loadingSuccess)
            {
                throw new MinecraftError();
            }
        }
        else
        {
            long i = Minecraft.getSystemTime();

            if (i - this.systemTime >= 100L)
            {
                this.systemTime = i;
                ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                scaledResolution.getScaleFactor();

                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                GlStateManager.ortho(0.0D, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0F, 0.0F, -200.0F);


                Gui.drawRect(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(19, 19, 19).getRGB());
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                RenderUtil.drawCentredImage(new ResourceLocation("silentclient/logos/logo.png"), scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 3, 300, 58);
                Client.getInstance().getSilentFontRenderer().drawCenteredString((currentlyDisplayedText != "" ? currentlyDisplayedText : "Loading") + "...", scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() - 100, 14, SilentFontRenderer.FontType.TITLE);

                this.mc.updateDisplay();

                try
                {
                    Thread.yield();
                }
                catch (Exception var15)
                {

                }
            }
        }
    }
}
