package wwe;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;
import java.io.*;

class xpXF extends GuiScreen
{
    private ResourceLocation SvJp;
    private DynamicTexture nTvJ;
    float prvr;
    int cRYz;
    sbfc ACoH;
    
    xpXF() {
        super();
        this.prvr = 0.0f;
        this.cRYz = 0;
        this.ACoH = new sbfc();
    }
    
    public void func_73866_w_() {
        this.cRYz = 0;
        final int n = this.height / 4 + 48;
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final int n2 = scaledResolution.getScaledWidth() / 5 + 15;
        this.buttonList.add(new Gpxt(1, 15, scaledResolution.getScaledHeight() / 2 - scaledResolution.getScaledHeight() / 4, scaledResolution.getScaledWidth() / 5, scaledResolution.getScaledHeight() / 2, "Options"));
        this.buttonList.add(new Gpxt(2, scaledResolution.getScaledWidth() / 5 + 30, scaledResolution.getScaledHeight() / 2 - scaledResolution.getScaledHeight() / 4, scaledResolution.getScaledWidth() / 5, scaledResolution.getScaledHeight() / 2, "Rage Quit"));
        this.buttonList.add(new GuiButton(21, 50, this.height - this.height / 8 - 10, 20, 20, "<"));
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.mc.getTextureManager().bindTexture((ResourceLocation)qdlI.JTTe.get(qdlI.kbXN));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), (float)scaledResolution.getScaledWidth(), (float)scaledResolution.getScaledHeight());
        bozE.xISY.drawString("WWE Mod", 15, scaledResolution.getScaledHeight() / 8 - bozE.xISY.FONT_HEIGHT / 2, -1);
        bozE.xISY.drawString("Welcome " + Wrapper.mc.getSession().getUsername(), scaledResolution.getScaledWidth() - bozE.xISY.getStringWidth("Welcome " + Wrapper.mc.getSession().getUsername()) - 2, scaledResolution.getScaledHeight() - 13, -1);
        super.drawScreen(n, n2, n3);
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings));
        }
        if (guiButton.id == 2) {
            this.mc.shutdown();
        }
        if (guiButton.id == 21) {
            this.mc.displayGuiScreen((GuiScreen)new qdlI());
        }
    }
}
