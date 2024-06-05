package alphentus.utils;

import alphentus.init.Init;
import alphentus.mod.ModCategory;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.hud.TabGUI;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.renderer.entity.Render;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 08/08/2020.
 */
public class CategoryTab {

    public float x, y, width, height;
    public ModCategory modCategory;
    public Color color;

    private final Init init = Init.getInstance();
    private UnicodeFontRenderer fontRenderer = init.fontManager.myinghei20;

    public float animation;
    private long lastMS = 1;

    public CategoryTab(ModCategory modCategory) {
        this.modCategory = modCategory;
        this.animation = 0F;
    }

    public void draw() {

        if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Moon")) {
            if (fontRenderer != init.fontManager.myinghei25)
                fontRenderer = init.fontManager.myinghei25;
        }else{
            if (fontRenderer != init.fontManager.myinghei20)
                fontRenderer = init.fontManager.myinghei20;
        }

        RenderUtils.relativeRect(x, y, x + width, y + height, getColor().getRGB());

        fontRenderer.drawStringWithShadow(modCategory.name().substring(0, 1).toUpperCase() + modCategory.name().substring(1).toLowerCase(), x + animation + 2F, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, 14737632);

        if (TabGUI.cat.equals(modCategory)) {
            if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Moon")){
                if (animation < 6)
                    animation += 0.035 * RenderUtils.deltaTime;
            }else{
                if (animation < 8)
                    animation += 0.035 * RenderUtils.deltaTime;
            }
        } else {
            if (animation > 1)
                animation -= 0.035 * RenderUtils.deltaTime;

        }

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPosition(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    String convert_upper(String g) {
        return g.substring(0, 1).toUpperCase() + g.substring(1);
    }

}
