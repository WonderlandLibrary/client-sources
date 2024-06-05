package alphentus.mod.mods.hud;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.gui.customhud.dragging.DraggingUtil;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.CategoryTab;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 04/08/2020.
 */
public class TabGUI extends Mod {

    public static ModCategory cat;
    private final Init init = Init.getInstance();
    private final UnicodeFontRenderer fontRenderer = init.fontManager.myinghei20;
    private final UnicodeFontRenderer fontRenderer2 = init.fontManager.thruster20;
    public Setting blur = new Setting("Blur", false, this);
    public Setting backGroundColor = new Setting("Color", 0, 255, 32, true, this);
    public Setting alphaColor = new Setting("Alpha", 25, 180, 25, true, this);
    public int x = 10, y = 20, width, height, wholeHeight;
    ResourceLocation combat = new ResourceLocation("client/Icons/Combat.png");
    ResourceLocation hud = new ResourceLocation("client/Icons/HUD.png");
    ResourceLocation movement = new ResourceLocation("client/Icons/Movement.png");
    ResourceLocation player = new ResourceLocation("client/Icons/Player.png");
    ResourceLocation visuals = new ResourceLocation("client/Icons/Visuals.png");
    ResourceLocation world = new ResourceLocation("client/Icons/World.png");
    ArrayList<CategoryTab> categoryTabs = new ArrayList<CategoryTab>();
    private DraggingUtil draggingUtil = Init.getInstance().draggingUtil;
    private Gui gui = new Gui();
    private int currentModule;
    private int currentCategory = 0;
    private boolean selected;

    public TabGUI() {
        super("TabGUI", Keyboard.KEY_NONE, false, ModCategory.HUD);
        Init.getInstance().settingManager.addSetting(alphaColor);
        Init.getInstance().settingManager.addSetting(backGroundColor);
        Init.getInstance().settingManager.addSetting(blur);
        // DAVID
        for (ModCategory modCategory : ModCategory.values()) {
            categoryTabs.add(new CategoryTab(modCategory));
        }
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.RENDER2D)
            return;

        if (Init.getInstance().modManager.getModuleByClass(HUD.class).isCustom()) {
            alphaColor.setVisible(true);
            backGroundColor.setVisible(true);
            blur.setVisible(true);
        } else {
            alphaColor.setVisible(false);
            backGroundColor.setVisible(false);
            blur.setVisible(false);
        }

        if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Alphentus")) {
/*            y = 72;
            x = 6;*/
            width = 78;
            height = fontRenderer2.FONT_HEIGHT + 5;
            wholeHeight = height * ModCategory.values().length;
        } else {
/*            y = 20;
            x = 2;*/
            width = 75;
            height = fontRenderer.FONT_HEIGHT + 2;
            wholeHeight = height * ModCategory.values().length;
        }

        systemMessage("" + x);

        if (!getState())
            return;

        float yHeightCategory = 0;
        float yHeightBlur = 0;

        float yHeightModBlur = 0;
        float yHeightMod = 0;

        final int[] color = {(int) backGroundColor.getCurrent()};

        Color bgColor75 = new Color(color[0], color[0], color[0], (int) alphaColor.getCurrent() + 50);
        Color bgColor25 = new Color(color[0], color[0], color[0], (int) alphaColor.getCurrent());

        for (ModCategory ignored : ModCategory.values()) {
            yHeightBlur += height;
        }

        if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Custom") || Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Sigma")) {
            if (blur.isState()) {
                init.tabGUIBlur.blur(x, y, width, yHeightBlur + 1.9999, 30);
                init.blurUtil.blur(x, y, width, yHeightBlur + 2, 30);
            }

            for (CategoryTab categoryTab : this.categoryTabs) {
                if (currentCategory == categoryTab.modCategory.ordinal())
                    cat = categoryTab.modCategory;

                categoryTab.setPosition(x, y + yHeightCategory, width, height);
                categoryTab.setColor(cat.equals(categoryTab.modCategory) ? bgColor75 : bgColor25);
                categoryTab.draw();
                yHeightCategory += height;
            }

            if (!selected)
                return;

            for (Mod mod : init.modManager.getModArrayList()) {
                if (mod.getModCategory().equals(cat)) {
                    yHeightModBlur += height;
                }
            }
            if (blur.isState()) {
                init.tabGUIBlur.blur(x + width + 5, y + currentCategory * height, width, yHeightModBlur + 2, 30);
                init.blurUtil.blur(x + width + 5, y + currentCategory * height, width, yHeightModBlur + 2, 30);
            }

            java.util.ArrayList<Mod> mods = new java.util.ArrayList();
            for (Mod mod : init.modManager.getModArrayList()) {
                if (mod.getModCategory().equals(cat)) {
                    mods.add(mod);
                }
            }

            for (Mod mod : mods) {
                if (mod.getModCategory().equals(cat)) {
                    RenderUtils.relativeRect(x + width + 5, y + currentCategory * height + yHeightMod, x + width + 5 + width, y + currentCategory * height + height + yHeightMod, mods.get(currentModule) == mod ? bgColor75.getRGB() : mod.getState() ? new Color(init.CLIENT_COLOR.getRed(), init.CLIENT_COLOR.getGreen(), init.CLIENT_COLOR.getBlue(), 75).getRGB() : mods.get(currentModule) == mod ? bgColor75.getRGB() : bgColor25.getRGB());
                    fontRenderer.drawStringWithShadow(mod.getModuleName(), mod.getState() ? x + width + 9 : x + width + 7, y + currentCategory * height + height / 2 - fontRenderer.FONT_HEIGHT / 2 + yHeightMod, 14737632);
                    yHeightMod += height;
                }
            }
        } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Alphentus")) {
            for (ModCategory modCategory : ModCategory.values()) {

                if (currentCategory == modCategory.ordinal())
                    cat = modCategory;

                RenderUtils.relativeRect(x, y + yHeightCategory, x + width, y + height + yHeightCategory, 0xFF202020);
                Init.getInstance().fontManager.stem20.drawStringWithShadow(cat == modCategory ? ">" : "", x + 15, y + height / 2 - fontRenderer2.FONT_HEIGHT / 2 + yHeightCategory + 1, 0xFFFFFFFF);
                fontRenderer2.drawStringWithShadow(modCategory.name(), cat == modCategory ? x + 23 : x + 15, y + height / 2 - fontRenderer2.FONT_HEIGHT / 2 + yHeightCategory - 1, 0xFFFFFFFF);

                RenderUtils.drawImage(new ResourceLocation("client/Icons/" + modCategory.name().toLowerCase() + ".png"), (int) x + 1, (int) y + (int) yHeightCategory + 2, 12, 12);


                yHeightCategory += height;
            }

            if (!selected)
                return;

            for (Mod mod : init.modManager.getModArrayList()) {
                if (mod.getModCategory().equals(cat)) {
                    yHeightModBlur += height;
                }
            }

            java.util.ArrayList<Mod> mods = new java.util.ArrayList();
            for (Mod mod : init.modManager.getModArrayList()) {
                if (mod.getModCategory().equals(cat)) {
                    mods.add(mod);
                }
            }

            for (Mod mod : mods) {
                if (mod.getModCategory().equals(cat)) {
                    RenderUtils.relativeRect(x + width + 5, y + currentCategory * height + yHeightMod, x + width + 5 + width, y + currentCategory * height + height + yHeightMod, 0xFF202020);
                    Init.getInstance().fontManager.stem20.drawStringWithShadow(mods.get(currentModule) == mod ? ">" : "", x + width + 7, y + currentCategory * height + height / 2 - fontRenderer2.FONT_HEIGHT / 2 + yHeightMod + 2, mod.getState() ? 0xFF909090 : 0xFFFFFFFF);
                    fontRenderer2.drawStringWithShadow(mod.getModuleName(), mods.get(currentModule) == mod ? x + width + 15 : x + width + 7, y + currentCategory * height + height / 2 - fontRenderer2.FONT_HEIGHT / 2 + yHeightMod, mod.getState() ? 0xFF909090 : 0xFFFFFFFF);
                    yHeightMod += height;
                }
            }
        } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Moon")) {

            this.height = 16;
            init.blurUtil.blur(x, y, width, yHeightBlur + 2, 30);

            for (CategoryTab categoryTab : this.categoryTabs) {
                if (currentCategory == categoryTab.modCategory.ordinal())
                    cat = categoryTab.modCategory;
                categoryTab.setPosition(x, y + yHeightCategory, width, height);
                categoryTab.setColor(cat.equals(categoryTab.modCategory) ? new Color(0,0,0,150) : new Color(0,0,0,100));
                categoryTab.draw();
                yHeightCategory += height;
            }

            if (!selected)
                return;

            for (Mod mod : init.modManager.getModArrayList()) {
                if (mod.getModCategory().equals(cat)) {
                    yHeightModBlur += height;
                }
            }

            init.blurUtil.blur(x + width + 5, y + currentCategory * height, width, yHeightModBlur + 2, 30);

            java.util.ArrayList<Mod> mods = new java.util.ArrayList();
            for (Mod mod : init.modManager.getModArrayList()) {
                if (mod.getModCategory().equals(cat)) {
                    mods.add(mod);
                }
            }

            for (Mod mod : mods) {
                if (mod.getModCategory().equals(cat)) {
                    RenderUtils.relativeRect(x + width + 5, y + currentCategory * height + yHeightMod, x + width + 5 + width, y + currentCategory * height + height + yHeightMod, mods.get(currentModule) == mod ? new Color(0,0,0,150).getRGB() : mod.getState() ? new Color(init.CLIENT_COLOR.getRed(), init.CLIENT_COLOR.getGreen(), init.CLIENT_COLOR.getBlue(), 75).getRGB() : mods.get(currentModule) == mod ? new Color(0,0,0,150).getRGB() : new Color(0,0,0,100).getRGB());
                    fontRenderer.drawStringWithShadow(mod.getModuleName(), mod.getState() ? x + width + 9 : x + width + 7, y + currentCategory * height + height / 2 - fontRenderer.FONT_HEIGHT / 2 + yHeightMod, 14737632);
                    yHeightMod += height;
                }
            }
        }

    }

    public final void draw() {


    }

    public final void keyTyped(int keyCode) {
        if (keyCode == Keyboard.KEY_DOWN && !selected && currentCategory < ModCategory.values().length - 1) {
            currentCategory++;
            currentModule = 0;
        }

        if (keyCode == Keyboard.KEY_UP && !selected && currentCategory > 0) {
            currentCategory--;
            currentModule = 0;
        }

        if (keyCode == Keyboard.KEY_RIGHT)
            selected = true;

        if (keyCode == Keyboard.KEY_LEFT)
            selected = false;

        java.util.ArrayList<Mod> mods = new ArrayList();

        for (Mod mod : Init.getInstance().modManager.getModArrayList()) {
            if (mod.getModCategory().equals(cat)) {
                mods.add(mod);
            }
        }

        if (keyCode == Keyboard.KEY_DOWN && selected && currentModule < mods.size() - 1) {
            currentModule++;
        }

        if (keyCode == Keyboard.KEY_UP && selected && currentModule > 0) {
            currentModule--;
        }

        if (keyCode == Keyboard.KEY_RETURN && selected) {
            mods.get(currentModule).setState(!mods.get(currentModule).getState());
        }

    }

    public void updatePosition(int mouseX, int mouseY, int dragX, int dragY) {
        x = dragX + mouseX;
        y = dragY + mouseY;
    }

}
