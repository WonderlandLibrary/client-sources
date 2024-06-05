package koks.hud.tabgui;

import koks.Koks;
import koks.modules.Module;
import koks.utilities.CustomFont;
import koks.utilities.DeltaTime;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 08:35
 */
public class CategoryTab {

    public Module.Category category;
    public boolean expanded, longestWidth;
    private int x, y, width, height;
    public final RenderUtils renderUtils = new RenderUtils();

    public int currentModule = 0, currentCategory = 0;
    private final CustomFont fr = new CustomFont("fonts/Helvetica45Light_0.ttf", 20);

    public float animationX = 3;

    public List<ModuleTab> moduleTabs = new ArrayList<>();

    public CategoryTab(Module.Category category) {
        this.category = category;
        Koks.getKoks().moduleManager.getModules().stream().filter(module -> module.getModuleCategory().equals(this.category)).forEach(module -> moduleTabs.add(new ModuleTab(module)));
    }

    public void drawScreen(int categoryCurrent, boolean shadow, boolean clientColor, boolean centeredString, CustomFont tabGuiLengthFont) {
        int[] yHeight = {0};
        int[] yHeight2 = {0};
        int[] longestWidth = {width};

        if ((categoryCurrent == category.ordinal())) {
            if (animationX < 9)
                animationX += 0.025 * DeltaTime.getDeltaTime();
        } else {
            if (animationX > 3)
                animationX -= 0.025 * DeltaTime.getDeltaTime();
        }

        switch (Koks.getKoks().getThemeCategory()) {
            case NONE:
                this.longestWidth = false;
                this.moduleTabs.forEach(moduleTab -> {
                    if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(moduleTab.getModule().getModuleName()) > longestWidth[0])
                        longestWidth[0] = Minecraft.getMinecraft().fontRendererObj.getStringWidth(moduleTab.getModule().getModuleName()) + (centeredString ? 0 : 3);
                });
                this.currentCategory = categoryCurrent;
                renderUtils.drawRect(7, x, y, x + width, y + height, currentCategory == category.ordinal() ? clientColor ? Koks.getKoks().client_color : new Color(0, 0, 0, 175) : new Color(0, 0, 0, 125));
                fr.drawStringWithShadow(category.name().substring(0, 1).toUpperCase() + category.name().substring(1).toLowerCase(), centeredString ? x + width / 2 - fr.getStringWidth(category.name().substring(0, 1).toUpperCase() + category.name().substring(1).toLowerCase()) / 2 : x + animationX, y + height / 2 - fr.FONT_HEIGHT / 2, -1);
                if (expanded) {
                    /*
                     *  SHADOWS
                     */
                    if (shadow) {
                        this.moduleTabs.forEach(moduleTab -> {
                            yHeight2[0] += height;
                        });
                        GL11.glPushMatrix();
                        GlStateManager.disableAlpha();
                        GlStateManager.enableBlend();
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glColor4f(1, 1, 1, 1);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/top.png"), x + width + 3, y - 3, longestWidth[0], 3, false);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/bottom.png"), x + width + 2, y + yHeight2[0], longestWidth[0] + 1, 6, false);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/left.png"), x + width, y - 1, 3, yHeight2[0] + 1, false);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/right.png"), x + width + longestWidth[0] + 3, y - 1, 3, yHeight2[0] + 2, false);
                        GL11.glDisable(GL11.GL_BLEND);
                        GlStateManager.enableAlpha();
                        GlStateManager.disableBlend();
                        GL11.glPopMatrix();
                    }
                    /*
                     * SHADOWS END
                     */
                    this.moduleTabs.forEach(moduleTab -> {
                        moduleTab.setInformation(x + width + 3, y + yHeight[0], longestWidth[0], height);
                        moduleTab.drawScreen(currentModule, clientColor, centeredString, tabGuiLengthFont, this);
                        yHeight[0] += height;
                    });
                }
                break;
            default:
                Koks.getKoks().themeManager.getThemeList().forEach(theme -> {
                    if (theme.getThemeCategory().equals(Koks.getKoks().getThemeCategory())) {
                        if (theme.drawTabGUI()) {

                            if (theme.isLongestWidthStringModule()) {
                                this.moduleTabs.forEach(moduleTab -> {
                                    if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(moduleTab.getModule().getModuleName()) > longestWidth[0])
                                        longestWidth[0] = Minecraft.getMinecraft().fontRendererObj.getStringWidth(moduleTab.getModule().getModuleName()) + (centeredString ? 0 : 3);
                                });
                            } else {
                                longestWidth[0] = width;
                            }

                            this.currentCategory = categoryCurrent;
                            theme.categoryTabGUI(this, x, y, width, height);

                            if (expanded) {

                                if (shadow) {
                                    this.moduleTabs.forEach(moduleTab -> {
                                        yHeight2[0] += height;
                                    });
                                    GL11.glPushMatrix();
                                    GlStateManager.disableAlpha();
                                    GlStateManager.enableBlend();
                                    GL11.glEnable(GL11.GL_BLEND);
                                    GL11.glColor4f(1, 1, 1, 1);
                                    renderUtils.drawImage(new ResourceLocation("client/shadows/top.png"), x + width + 3, y - 3, longestWidth[0], 3, false);
                                    renderUtils.drawImage(new ResourceLocation("client/shadows/bottom.png"), x + width + 2, y + yHeight2[0], longestWidth[0] + 1, 6, false);
                                    renderUtils.drawImage(new ResourceLocation("client/shadows/left.png"), x + width, y - 1, 3, yHeight2[0] + 1, false);
                                    renderUtils.drawImage(new ResourceLocation("client/shadows/right.png"), x + width + longestWidth[0] + 3, y - 1, 3, yHeight2[0] + 2, false);
                                    GL11.glDisable(GL11.GL_BLEND);
                                    GlStateManager.enableAlpha();
                                    GlStateManager.disableBlend();
                                    GL11.glPopMatrix();
                                }

                                this.moduleTabs.forEach(moduleTab -> {
                                    moduleTab.setInformation(x + width + 3, y + yHeight[0], longestWidth[0], height);
                                    moduleTab.drawScreen(currentModule, clientColor, centeredString, tabGuiLengthFont, this);
                                    yHeight[0] += height;
                                });
                            }

                        }
                    }
                });
                break;
        }
    }

    public void drawScreen(int categoryCurrent, boolean shadow, boolean clientColor, boolean centeredString) {
        int[] yHeight = {0};
        int[] yHeight2 = {0};
        int[] longestWidth = {width};

        if ((categoryCurrent == category.ordinal())) {
            if (animationX < 9)
                animationX += 0.025 * DeltaTime.getDeltaTime();
            if(animationX > 9)
                animationX = 9;
        } else {
            if (animationX > 3)
                animationX -= 0.025 * DeltaTime.getDeltaTime();
            if(animationX < 3)
                animationX = 3;
        }

        switch (Koks.getKoks().getThemeCategory()) {
            case NONE:
                this.longestWidth = false;
                this.moduleTabs.forEach(moduleTab -> {
                    if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(moduleTab.getModule().getModuleName()) > longestWidth[0])
                        longestWidth[0] = Minecraft.getMinecraft().fontRendererObj.getStringWidth(moduleTab.getModule().getModuleName()) + (centeredString ? 0 : 3);
                });
                this.currentCategory = categoryCurrent;
                renderUtils.drawRect(7, x, y, x + width, y + height, currentCategory == category.ordinal() ? clientColor ? Koks.getKoks().client_color : new Color(0, 0, 0, 175) : new Color(0, 0, 0, 125));
                fr.drawStringWithShadow(category.name().substring(0, 1).toUpperCase() + category.name().substring(1).toLowerCase(), centeredString ? x + width / 2 - fr.getStringWidth(category.name().substring(0, 1).toUpperCase() + category.name().substring(1).toLowerCase()) / 2 : x + animationX, y + height / 2 - fr.FONT_HEIGHT / 2, -1);
                if (expanded) {
                    /*
                     *  SHADOWS
                     */
                    if (shadow) {
                        this.moduleTabs.forEach(moduleTab -> {
                            yHeight2[0] += height;
                        });
                        GL11.glPushMatrix();
                        GlStateManager.disableAlpha();
                        GlStateManager.enableBlend();
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glColor4f(1, 1, 1, 1);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/top.png"), x + width + 3, y - 3, longestWidth[0], 3, false);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/bottom.png"), x + width + 2, y + yHeight2[0], longestWidth[0] + 1, 6, false);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/left.png"), x + width, y - 1, 3, yHeight2[0] + 1, false);
                        renderUtils.drawImage(new ResourceLocation("client/shadows/right.png"), x + width + longestWidth[0] + 3, y - 1, 3, yHeight2[0] + 2, false);
                        GL11.glDisable(GL11.GL_BLEND);
                        GlStateManager.enableAlpha();
                        GlStateManager.disableBlend();
                        GL11.glPopMatrix();
                    }
                    /*
                     * SHADOWS END
                     */
                    this.moduleTabs.forEach(moduleTab -> {
                        moduleTab.setInformation(x + width + 3, y + yHeight[0], longestWidth[0], height);
                        moduleTab.drawScreen(currentModule, clientColor, centeredString, this);
                        yHeight[0] += height;
                    });
                }
                break;
        }
    }

    public void keyPress(int key) {
        if (expanded) {
            if (key == Keyboard.KEY_RIGHT) {
                for (int i = 0; i < moduleTabs.size(); i++) {
                    ModuleTab moduleTab = moduleTabs.get(i);
                    if (currentModule == i)
                        moduleTab.getModule().toggle();
                }
            } else if (key == Keyboard.KEY_LEFT) {
                expanded = false;
            } else if (key == Keyboard.KEY_UP) {
                currentModule--;
                if (currentModule < 0)
                    currentModule = moduleTabs.size() - 1;
            } else if (key == Keyboard.KEY_DOWN) {
                currentModule++;
                if (currentModule > moduleTabs.size() - 1)
                    currentModule = 0;
            }
        } else {
            if (key == Keyboard.KEY_RIGHT && currentCategory == category.ordinal()) {
                expanded = true;
            }
        }
    }

    public void setInformation(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isCurrentCategory() {
        return currentCategory == category.ordinal();
    }

    public boolean isExpanded() {
        return expanded;
    }
}
