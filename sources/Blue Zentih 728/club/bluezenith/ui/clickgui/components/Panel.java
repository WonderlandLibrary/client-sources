package club.bluezenith.ui.clickgui.components;

import club.bluezenith.BlueZenith;
import club.bluezenith.util.MinecraftInstance;
import club.bluezenith.module.modules.render.ClickGUI;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class Panel extends MinecraftInstance {
    protected FontRenderer f = mc.fontRendererObj;
    public float x, y, prevX, prevY, width, height, mHeight, originalMheight, openAnimationProgress;
    public boolean showContent;
    public String id;
    protected final ClickGUI click = (ClickGUI) BlueZenith.getBlueZenith().getModuleManager().getModule(ClickGUI.class);
    public Panel(float x, float y, String id){
        this.x = x;
        this.y = y;
        showContent = true;
        this.id = id;
    }
    public Panel calculateSize(){
        width = 120;
        return this;
    }
    public void updateHeight(float increment) {
        mHeight = originalMheight + increment;
    }

    public void drawPanel(int mouseX, int mouseY, float partialTicks, boolean handleClicks) {

    }

    public void keyTyped(char charTyped, int keyCode){}

    public boolean i(int mouseX, int mouseY, float x, float y, float x2, float y2){
        return mouseX >= x && mouseY >= y && mouseX <= x2 && mouseY <= y2;
    }
    public final void toggleSound(){
        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

}
