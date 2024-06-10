package me.sleepyfish.smok.gui.comp.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.gui.comp.IComp;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.impl.acategory.*;
import me.sleepyfish.smok.rats.impl.visual.Gui;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.FastEditUtils;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.render.RenderUtils;
import me.sleepyfish.smok.utils.render.RoundedUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;

// Class from SMok Client by SleepyFish
public class CategoryComp {

    public ArrayList<IComp> ratInCategory = new ArrayList<>();
    private boolean categoryOpened;
    public boolean mouseRelocate;
    private final double marginX;
    public Rat.Category category;
    private int categoryHeight;
    private final int height;
    private int catBGOff = 2;
    private int offset;
    private int width;
    public int xx;
    public int yy;
    private int y;
    private int x;

    public CategoryComp(Rat.Category smok) {
        this.category = smok;
        this.width = 92;
        this.height = 10;
        this.x = 5;
        this.y = 5;
        this.xx = 0;
        this.categoryOpened = false;
        this.mouseRelocate = false;
        int tY = this.getHeight() + 3;
        this.marginX = 55.0;

        for (Rat rat : Smok.inst.ratManager.getRatsInCategory(this.category)) {
            this.ratInCategory.add(new ModuleComp(rat, this, tY));
            tY += FastEditUtils.ratGap;
        }
    }

    public void drawCategory() {
        this.width = 92;

        if (!Smok.inst.ratManager.getRatByClass(Blatant.class).isEnabled()) {
            if (this.category == Rat.Category.Blatant)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Legit.class).isEnabled()) {
            if (this.category == Rat.Category.Legit)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Other.class).isEnabled()) {
            if (this.category == Rat.Category.Other)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Useless.class).isEnabled()) {
            if (this.category == Rat.Category.Useless)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Visuals.class).isEnabled()) {
            if (this.category == Rat.Category.Visuals)
                return;
        }

        if (Smok.inst.guiManager != null)
            if (!Smok.inst.guiManager.getClickGui().renderFavorites) {
                if (this.category == Rat.Category.Favorites)
                    return;
            }

        if (ClientUtils.isSmokTheme()) {
            if (!this.categoryOpened) {
                if (Gui.renderShadows.isEnabled()) {
                    RenderUtils.drawShadow(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.catBGOff, 1.0F);
                }

                RoundedUtils.drawRoundOutline(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.catBGOff, 1.0F, 2.62F, ColorUtils.getBackgroundColor(4), ColorUtils.getBackgroundColor(4).darker());
            } else {
                this.categoryHeight = 0;

                for (IComp comp : this.ratInCategory) {
                    this.categoryHeight += comp.getHeight();
                }

                if (this.category == Rat.Category.Category) {
                    if (Gui.renderShadows.isEnabled()) {
                        RenderUtils.drawShadow(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.getCategoryHeight() + 15, 1.0F);
                    }

                    RoundedUtils.drawRoundOutline(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.getCategoryHeight() + 15, 1.0F, 2.62F, ColorUtils.getBackgroundColor(4), ColorUtils.getBackgroundColor(4).darker());

                    RenderUtils.drawImageWC("gui/favorites.png", this.getX() + 4, this.getY() + this.getHeight() + this.getCategoryHeight() + 5, 10, 10, Smok.inst.guiManager.getClickGui().renderFavorites ? new Color(0xFFC76319) : ColorUtils.getIconColor());
                } else {
                    if (Gui.renderShadows.isEnabled()) {
                        RenderUtils.drawShadow(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.getCategoryHeight(), 1.0F);
                    }

                    RoundedUtils.drawRoundOutline(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.getCategoryHeight(), 1.0F, 2.62F, ColorUtils.getBackgroundColor(4), ColorUtils.getBackgroundColor(4).darker());
                }
            }

            if (this.category == Rat.Category.Category) {
                RenderUtils.drawImage("modules/text_gui_smok.png", this.getX() + 2, this.getY() + 2, 40, 8);
            }

            if (this.category == Rat.Category.Favorites) {
                FontUtils.i20.drawString("<", this.getX() + 2, this.getY() + 4, ColorUtils.getFontColor(1));
            }

            if (this.category == Rat.Category.Other) {
                FontUtils.i20.drawString("r", this.getX() + 2, this.getY() + 4, ColorUtils.getFontColor(1));
            }

            if (this.category == Rat.Category.Visuals) {
                FontUtils.i20.drawString("i", this.getX() + 2, this.getY() + 4, ColorUtils.getFontColor(1));
            }

            if (this.category == Rat.Category.Legit) {
                FontUtils.i20.drawString(";", this.getX() + 2, this.getY() + 4, ColorUtils.getFontColor(1));
            }

            if (this.category == Rat.Category.Blatant) {
                FontUtils.i20.drawString("A", this.getX() + 2, this.getY() + 3, ColorUtils.getFontColor(1));
            }

            if (this.category == Rat.Category.Useless) {
                FontUtils.i20.drawString("6", this.getX() + 2, this.getY() + 4, ColorUtils.getFontColor(1));
            }

            FontUtils.r20.drawStringWithClientColor(this.category.getName(), (this.getX() + this.catBGOff + 14), (this.getY() + this.catBGOff + 1), true);

            FontUtils.i28.drawString(this.categoryOpened ? "J" : "K",((int) ((double) this.getX() + this.marginX + 14.0)), ((float) this.getY() + 1.3F), ColorUtils.getFontColor(1));
            if (this.categoryOpened && !this.ratInCategory.isEmpty()) {
                for (IComp c : this.ratInCategory) {
                    c.draw();
                }
            }
        } else {
            if (!this.categoryOpened) {
                if (Gui.renderShadows.isEnabled()) {
                    RenderUtils.drawShadow(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.catBGOff, 1.0F);
                }

                RoundedUtils.drawRoundOutline(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.catBGOff, 1.0F, 2.62F, ColorUtils.getBackgroundColor(4).darker().darker(), ColorUtils.getBackgroundColor(4).darker().darker());
            } else {
                this.categoryHeight = 0;

                for (IComp comp : this.ratInCategory) {
                    this.categoryHeight += comp.getHeight();
                }

                if (this.category == Rat.Category.Category) {
                    if (Gui.renderShadows.isEnabled()) {
                        RenderUtils.drawShadow(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.getCategoryHeight() + 15, 1.0F);
                    }

                    RoundedUtils.drawRoundOutline(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.getCategoryHeight() + 15, 1.0F, 2.62F, ColorUtils.getBackgroundColor(4).darker().darker(), ColorUtils.getBackgroundColor(4).darker());

                    RenderUtils.drawImageWC("gui/favorites.png", this.getX() + 4, this.getY() + this.getHeight() + this.getCategoryHeight() + 5, 10, 10, ColorUtils.getIconColor());
                } else {
                    if (Gui.renderShadows.isEnabled()) {
                        RenderUtils.drawShadow(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.getCategoryHeight(), 1.0F);
                    }

                    RoundedUtils.drawRoundOutline(this.getX(), this.getY(), this.getWidth(), this.getHeight() + this.getCategoryHeight(), 1.0F, 2.62F, ColorUtils.getBackgroundColor(4).darker().darker(), ColorUtils.getBackgroundColor(4).darker());
                }
            }

            if (this.category == Rat.Category.Category) {
                RenderUtils.drawImage("modules/text_gui_vape.png", this.getX() + 2, this.getY() + 2, 40, 8);
            }

            if (this.category == Rat.Category.Other) {
                FontUtils.i20.drawString("r", this.getX() + 2, this.getY() + 4, ColorUtils.getFontColor(1));
            }

            if (this.category == Rat.Category.Visuals) {
                FontUtils.i20.drawString("i", this.getX() + 2, this.getY() + 4, ColorUtils.getFontColor(1));
            }

            if (this.category == Rat.Category.Legit) {
                FontUtils.i20.drawString(";", this.getX() + 2, this.getY() + 4, ColorUtils.getFontColor(1));
            }

            if (this.category == Rat.Category.Blatant) {
                FontUtils.i20.drawString("A", this.getX() + 2, this.getY() + 3, ColorUtils.getFontColor(1));
            }

            if (this.category == Rat.Category.Useless) {
                FontUtils.i20.drawString("6", this.getX() + 2, this.getY() + 4, ColorUtils.getFontColor(1));
            }

            FontUtils.r20.drawString(this.category.getName(), (this.getX() + this.catBGOff + 14), (this.getY() + this.catBGOff + 1), Color.white);

            FontUtils.i28.drawString(this.categoryOpened ? "K" : "J", ((int) ((double) this.getX() + this.marginX + 14.0)), ((float) this.getY() + 1.3F), ColorUtils.getFontColor(1));

            if (this.categoryOpened && !this.ratInCategory.isEmpty()) {
                for (IComp c : this.ratInCategory) {
                    c.draw();
                }
            }
        }
    }

    public void render() {
        int off = this.getHeight() + 3;

        for (IComp comp : this.ratInCategory) {
            comp.setComponentStartAt(off);
            off += comp.getHeight();
        }
    }

    public int getWidth() {
        return this.width - 10;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y + this.getOffset();
    }

    public String getName() {
        return String.valueOf(this.ratInCategory);
    }

    public void scroll(float smok) {
        if (ClientUtils.inClickGui()) {
            this.offset += smok;
        }
    }

    public int getCategoryHeight() {
        return this.categoryHeight;
    }

    public void mousePressed(boolean d) {
        if (ClientUtils.inClickGui()) {
            this.mouseRelocate = d;
        }
    }

    public boolean isOpened() {
        return this.categoryOpened;
    }

    private int getOffset() {
        return this.offset;
    }

    public int getHeight() {
        return this.height;
    }

    public ArrayList<IComp> getRats() {
        return this.ratInCategory;
    }

    public void up(int x, int y) {
        if (this.mouseRelocate && ClientUtils.inClickGui()) {
            this.setX(x - this.xx);
            this.setY(y - this.yy);
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y - this.getOffset();
    }

    public void setOpened(boolean smok) {
        if (!Smok.inst.ratManager.getRatByClass(Blatant.class).isEnabled()) {
            if (this.category == Rat.Category.Blatant)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Legit.class).isEnabled()) {
            if (this.category == Rat.Category.Legit)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Other.class).isEnabled()) {
            if (this.category == Rat.Category.Other)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Useless.class).isEnabled()) {
            if (this.category == Rat.Category.Useless)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Visuals.class).isEnabled()) {
            if (this.category == Rat.Category.Visuals)
                return;
        }

        if (Smok.inst.guiManager != null)
            if (!Smok.inst.guiManager.getClickGui().renderFavorites) {
                if (this.category == Rat.Category.Favorites)
                    return;
            }

        if (Smok.inst.mc.getSession().getUsername().toLowerCase().startsWith("smellon")) {
            this.categoryOpened = smok;
        } else {
            if (category != Rat.Category.Favorites)
                this.categoryOpened = smok;
        }
    }

    public boolean isHoveringOverCategoryCollapseIcon(int x, int y) {
        if (!Smok.inst.ratManager.getRatByClass(Blatant.class).isEnabled()) {
            if (this.category == Rat.Category.Blatant)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Legit.class).isEnabled()) {
            if (this.category == Rat.Category.Legit)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Other.class).isEnabled()) {
            if (this.category == Rat.Category.Other)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Useless.class).isEnabled()) {
            if (this.category == Rat.Category.Useless)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Visuals.class).isEnabled()) {
            if (this.category == Rat.Category.Visuals)
                return false;
        }

        if (Smok.inst.guiManager != null)
            if (!Smok.inst.guiManager.getClickGui().renderFavorites) {
                if (this.category == Rat.Category.Favorites)
                    return false;
            }

        if (!ClientUtils.inClickGui()) {
            return false;
        } else {
            return x >= this.getX() + this.width - 13 && x <= this.getX() + this.getWidth() && (float) y >= (float) this.getY() + 2.0F && y <= this.getY() + this.getHeight() + 1;
        }
    }

    public boolean mousePressed(int x, int y) {
        if (!Smok.inst.ratManager.getRatByClass(Blatant.class).isEnabled()) {
            if (this.category == Rat.Category.Blatant)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Legit.class).isEnabled()) {
            if (this.category == Rat.Category.Legit)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Other.class).isEnabled()) {
            if (this.category == Rat.Category.Other)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Useless.class).isEnabled()) {
            if (this.category == Rat.Category.Useless)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Visuals.class).isEnabled()) {
            if (this.category == Rat.Category.Visuals)
                return false;
        }

        if (Smok.inst.guiManager != null) {
            if (!Smok.inst.guiManager.getClickGui().renderFavorites) {
                if (this.category == Rat.Category.Favorites)
                    return false;
            }
        }

        if (!ClientUtils.inClickGui()) {
            return false;
        } else {
            return x >= this.getX() + 77 && x <= this.getX() + this.getWidth() - 6 && (float) y >= (float) this.getY() + 2.0F && y <= this.getY() + this.getHeight() + 1;
        }
    }

    public boolean insideArea(int x, int y) {
        if (!Smok.inst.ratManager.getRatByClass(Blatant.class).isEnabled()) {
            if (this.category == Rat.Category.Blatant)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Legit.class).isEnabled()) {
            if (this.category == Rat.Category.Legit)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Other.class).isEnabled()) {
            if (this.category == Rat.Category.Other)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Useless.class).isEnabled()) {
            if (this.category == Rat.Category.Useless)
                return false;
        }

        if (!Smok.inst.ratManager.getRatByClass(Visuals.class).isEnabled()) {
            if (this.category == Rat.Category.Visuals)
                return false;
        }

        if (Smok.inst.guiManager != null)
            if (!Smok.inst.guiManager.getClickGui().renderFavorites) {
                if (this.category == Rat.Category.Favorites)
                    return false;
            }

        if (!ClientUtils.inClickGui()) {
            return false;
        } else {
            return x >= this.getX() && x <= this.getX() + this.getWidth() && y >= this.getY() && y <= this.getY() + this.getHeight();
        }
    }

}