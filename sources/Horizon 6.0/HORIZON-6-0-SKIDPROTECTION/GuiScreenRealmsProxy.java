package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import com.google.common.collect.Lists;

public class GuiScreenRealmsProxy extends GuiScreen
{
    private RealmsScreen HorizonCode_Horizon_È;
    private static final String Â = "CL_00001847";
    
    public GuiScreenRealmsProxy(final RealmsScreen p_i1087_1_) {
        this.HorizonCode_Horizon_È = p_i1087_1_;
        super.ÇŽÉ = Collections.synchronizedList((List<Object>)Lists.newArrayList());
    }
    
    public RealmsScreen Ó() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È.init();
        super.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È(final String p_154325_1_, final int p_154325_2_, final int p_154325_3_, final int p_154325_4_) {
        super.HorizonCode_Horizon_È(this.É, p_154325_1_, p_154325_2_, p_154325_3_, p_154325_4_);
    }
    
    public void Â(final String p_154322_1_, final int p_154322_2_, final int p_154322_3_, final int p_154322_4_) {
        Gui_1808253012.Â(this.É, p_154322_1_, p_154322_2_, p_154322_3_, p_154322_4_);
    }
    
    @Override
    public void Â(final int x, final int y, final int textureX, final int textureY, final int width, final int height) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(x, y, textureX, textureY, width, height);
        super.Â(x, y, textureX, textureY, width, height);
    }
    
    @Override
    public void £á() {
        super.£á();
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return super.Ø­áŒŠá();
    }
    
    @Override
    public void Â(final int tint) {
        super.Â(tint);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.HorizonCode_Horizon_È.render(mouseX, mouseY, partialTicks);
    }
    
    public void HorizonCode_Horizon_È(final ItemStack itemIn, final int x, final int y) {
        super.HorizonCode_Horizon_È(itemIn, x, y);
    }
    
    public void HorizonCode_Horizon_È(final String tabName, final int mouseX, final int mouseY) {
        super.HorizonCode_Horizon_È(tabName, mouseX, mouseY);
    }
    
    public void HorizonCode_Horizon_È(final List textLines, final int x, final int y) {
        super.HorizonCode_Horizon_È(textLines, x, y);
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.tick();
        super.Ý();
    }
    
    public int à() {
        return this.É.HorizonCode_Horizon_È;
    }
    
    public int HorizonCode_Horizon_È(final String p_154326_1_) {
        return this.É.HorizonCode_Horizon_È(p_154326_1_);
    }
    
    public void Ý(final String p_154319_1_, final int p_154319_2_, final int p_154319_3_, final int p_154319_4_) {
        this.É.HorizonCode_Horizon_È(p_154319_1_, p_154319_2_, (float)p_154319_3_, p_154319_4_);
    }
    
    public List HorizonCode_Horizon_È(final String p_154323_1_, final int p_154323_2_) {
        return this.É.Ý(p_154323_1_, p_154323_2_);
    }
    
    public final void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        this.HorizonCode_Horizon_È.buttonClicked(((GuiButtonRealmsProxy)button).Ó());
    }
    
    public void Ø() {
        super.ÇŽÉ.clear();
    }
    
    public void HorizonCode_Horizon_È(final RealmsButton p_154327_1_) {
        super.ÇŽÉ.add(p_154327_1_.HorizonCode_Horizon_È());
    }
    
    public List áŒŠÆ() {
        final ArrayList var1 = Lists.newArrayListWithExpectedSize(super.ÇŽÉ.size());
        for (final GuiButton var3 : super.ÇŽÉ) {
            var1.add(((GuiButtonRealmsProxy)var3).Ó());
        }
        return var1;
    }
    
    public void Â(final RealmsButton p_154328_1_) {
        super.ÇŽÉ.remove(p_154328_1_);
    }
    
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.HorizonCode_Horizon_È.mouseClicked(mouseX, mouseY, mouseButton);
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void n_() throws IOException {
        this.HorizonCode_Horizon_È.mouseEvent();
        super.n_();
    }
    
    @Override
    public void ˆÏ­() throws IOException {
        this.HorizonCode_Horizon_È.áŒŠÆ();
        super.ˆÏ­();
    }
    
    public void Â(final int mouseX, final int mouseY, final int state) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(mouseX, mouseY, state);
    }
    
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    public void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        this.HorizonCode_Horizon_È.keyPressed(typedChar, keyCode);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean result, final int id) {
        this.HorizonCode_Horizon_È.confirmResult(result, id);
    }
    
    @Override
    public void q_() {
        this.HorizonCode_Horizon_È.removed();
        super.q_();
    }
}
