/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.tabs;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.AbstractTab;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;
import digital.rbq.utils.font.FontRendererHook;
import digital.rbq.utils.font.FontUtils;

public class ModCategoryTab
extends AbstractTab<ModuleCategory> {
    private static final FontRenderer ICONS = new FontRendererHook(FontUtils.getFontFromTTF(new ResourceLocation("autumn/undefeated.ttf"), 14.0f, 0), true, true);
    private float hoverPosition;

    public ModCategoryTab(TabHandler handler, ModuleCategory stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        if (this.handler.canDescendTree()) {
            this.handler.descendTabTree();
        }
    }

    @Override
    public void renderTabFront() {
        FontRenderer fr = this.mc.fontRenderer;
        if (this.handler.isInDirectTree(this)) {
            if (this.hoverPosition < 7.0f) {
                this.hoverPosition = Math.min(7.0f, this.hoverPosition + 0.5f);
            }
        } else if (this.handler.getCurrentTab() != this && this.hoverPosition != 0.0f) {
            this.hoverPosition = Math.max(0.0f, this.hoverPosition - 0.5f);
        }
        String character = "";
        switch ((ModuleCategory)((Object)this.stateObject)) {
            case COMBAT: {
                character = "s";
                break;
            }
            case PLAYER: {
                character = "q";
                break;
            }
            case MOVEMENT: {
                character = "j";
                break;
            }
            case VISUALS: {
                character = "t";
                break;
            }
            case WORLD: {
                character = "v";
                break;
            }
            case EXPLOIT: {
                character = "m";
            }
        }
        ICONS.drawStringWithShadow(character, this.getTabWidth() - 8, (float)this.getPosY() + 5.0f, -1);
        fr.drawStringWithShadow(((ModuleCategory)((Object)this.stateObject)).toString(), (float)(this.getPosX() + 3) + this.hoverPosition, (float)this.getPosY() + 2.5f, 0xFFFFFF);
    }

    @Override
    public int getTabWidth() {
        return 65;
    }
}

