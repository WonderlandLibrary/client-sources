package alphentus.gui.clickguisigma.settings;


import alphentus.gui.clickguisigma.ModPanels;
import alphentus.init.Init;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.visuals.ClickGUI;
import alphentus.settings.Setting;
import alphentus.utils.RenderUtils;
import alphentus.utils.Translate;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class ElementCheckBox extends Element {

    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei19;
    private final UnicodeFontRenderer fontRenderer2 = Init.getInstance().fontManager.myinghei22;
    private HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    private Translate translate;
    private float float2;


    public ElementCheckBox(ModPanels modPanel, Setting setting) {
        this.modPanel = modPanel;
        this.setting = setting;
        this.translate = new Translate(0, 0);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        RenderUtils.drawFilledCircle((int)x + (int)width + 6, (int)y + (int)height  / 2 + 1, 6, hud.guiColor3);

        fontRenderer2.drawStringWithShadow(setting.getName(), x, y + height / 2 - fontRenderer2.FONT_HEIGHT / 2, hud.textColor.getRGB(), false);

        if (setting.isState()) {
            translate.interpolate(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight() + 5, 6);
            if (float2 < 0.5F)
                float2 += 0.002 * RenderUtils.deltaTime;
        } else {
            translate.interpolate(0, 0, 6);
            float2 = 0;
        }


        GL11.glPushMatrix();
        GL11.glTranslatef((x + width + 6), y + height / 2, 0);
        GL11.glScaled(0.5 + translate.getX() / scaledResolution.getScaledWidth() - float2, 0.5 + translate.getY() / scaledResolution.getScaledHeight() - float2, 0);
        GL11.glTranslatef(-(x + width + 6), -(y + height / 2), 0);

        if (translate.getX() > 250)
            RenderUtils.drawFilledCircle((int)x + (int)width + 6, (int)y + (int)height  / 2 + 1, 6, Init.getInstance().CLIENT_COLOR);
        if (translate.getX() > 250)
            RenderUtils.drawImage(new ResourceLocation("client/Icons/haken.png"), (int) (x + width + 1F), (int)y + (int)height / 2 - 3, 10, 10);


        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0)
            this.setting.setState(!this.setting.isState());
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isHovering(int mouseX, int mouseY) {
            return mouseX > x + width && mouseX < x + width + 12 && mouseY > y && mouseY < y + height;
    }

}
