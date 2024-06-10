package me.sleepyfish.smok.gui.comp.impl;

import java.awt.Color;
import java.util.ArrayList;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.gui.comp.IComp;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.impl.acategory.*;
import me.sleepyfish.smok.rats.impl.visual.Gui;
import me.sleepyfish.smok.rats.settings.*;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.FastEditUtils;
import me.sleepyfish.smok.utils.misc.MouseUtils;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.render.GlUtils;
import me.sleepyfish.smok.utils.render.RoundedUtils;
import me.sleepyfish.smok.utils.render.animation.normal.Animation;
import me.sleepyfish.smok.utils.render.animation.normal.Direction;
import me.sleepyfish.smok.utils.render.animation.simple.AnimationUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

// Class from SMok Client by SleepyFish
public class ModuleComp implements IComp {

    private final ArrayList<IComp> settings;
    private boolean isBinding = false;
    private final Animation bindAnimation;
    public Animation introAnimation;
    private CategoryComp category;
    private boolean expanded;
    private Rat rat;
    private int off;
    private int height;

    public ModuleComp(Rat mod, CategoryComp category, int off) {
        this.bindAnimation = AnimationUtils.getAnimation();
        this.bindAnimation.setDuration(200);

        this.introAnimation = AnimationUtils.getAnimation();
        this.introAnimation.setDuration(200);

        this.rat = mod;
        this.category = category;
        this.off = off;
        this.settings = new ArrayList<>();
        this.expanded = false;
        this.height = off + FastEditUtils.settingGap;

        if (!mod.getSettings().isEmpty()) {
            for (SettingHelper settings : mod.getSettings()) {
                if (settings instanceof DoubleSetting) {
                    DoubleSetting a = (DoubleSetting) settings;
                    this.getSettings().add(new SliderComp(a, this, this.height));
                } else if (settings instanceof BoolSetting) {
                    BoolSetting a = (BoolSetting) settings;
                    this.getSettings().add(new BooleanComp(a, this, this.height));
                } else if (settings instanceof ClickSetting) {
                    ClickSetting a = (ClickSetting) settings;
                    this.getSettings().add(new ClickComp(a, this, this.height));
                } else if (settings instanceof SpaceSetting) {
                    this.getSettings().add(new SpaceComp(this, this.height));
                } else if (settings instanceof ModeSetting) {
                    ModeSetting<Enum<?>> a = (ModeSetting<Enum<?>>) settings;
                    this.getSettings().add(new ModeComp(a, this, this.height));
                }

                this.height += FastEditUtils.settingGap;
            }
        }
    }

    @Override
    public void setComponentStartAt(int smok) {
        this.off = smok;
        int y = this.off + FastEditUtils.ratGap;

        for (IComp c : this.getSettings()) {
            c.setComponentStartAt(y);
            if (c instanceof SliderComp) {
                y += FastEditUtils.ratGap;
            } else if (c instanceof BooleanComp || c instanceof SpaceComp || c instanceof ModeComp || c instanceof ClickComp) {
                y += FastEditUtils.settingGap;
            }
        }
    }

    @Override
    public void draw() {
        int offset = 2;
        Color oldColor = this.rat.isEnabled() ? ColorUtils.getFontColor(2) : ColorUtils.getFontColor(2).darker().darker();

        if (!Smok.inst.ratManager.getRatByClass(Blatant.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Blatant)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Legit.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Legit)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Other.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Other)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Useless.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Useless)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Visuals.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Visuals)
                return;
        }

        if (!Smok.inst.guiManager.getClickGui().renderFavorites) {
            if (this.rat.getCategory() == Rat.Category.Favorites)
                return;
        }

        if (this.rat.isEnabled()) {
            this.introAnimation.setDirection(Direction.FORWARDS);
        } else {
            this.introAnimation.setDirection(Direction.BACKWARDS);
        }

        GlUtils.startScale(getCategory().getX() + 2, this.getY() + 8, (float) this.introAnimation.getValue());

        //if (this.rat.isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Category) {
                FontUtils.i202.drawString("V", ((int) ((float) this.category.getX() + 71.0F)), (this.category.getY() + this.off + 5), ColorUtils.getFontColor(2));
            } else {
                if (ClientUtils.isSmokTheme()) {
                    RoundedUtils.drawRound(this.category.getX(), this.category.getY() + this.off + 1, 82.0F, 12.0F, 2.0F, ColorUtils.getClientColor(this.rat.hashCode() * 100).darker().darker());
                } else {
                    RoundedUtils.drawRound(this.category.getX() - 1, this.category.getY() + this.off + 1, 84.0F, 12.0F, 0.0F, Smok.inst.colManager.getColorModeByID(19).getColor2().darker());
                }
            }
        //} else {
        if (!this.rat.isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Category) {
                FontUtils.i202.drawString("V", ((int) ((float) this.category.getX() + 69.0F)), (this.category.getY() + this.off + 5), ColorUtils.getIconColor());
            }

            if (!ClientUtils.isSmokTheme()) {
                if (this.rat.getCategory() != Rat.Category.Category) {
                    if (this.isHoveringOverModule(MouseUtils.mouseX, MouseUtils.mouseY))
                        RoundedUtils.drawRound(this.category.getX() - 1, this.category.getY() + this.off + 1, 84.0F, 12.0F, 0.0F, ColorUtils.getBackgroundColor(4).brighter());
                }
            }
        }

        GlUtils.stopScale();

        if (this.rat.isFavorite()) {
            FontUtils.r16.drawString("F: " + this.rat.getName(), ((float) this.category.getX() + 4.0F), (this.category.getY() + this.off + 5), oldColor);
        } else {
            FontUtils.r16.drawString(this.rat.getName(), ((float) this.category.getX() + 4.0F), (this.category.getY() + this.off + 5), oldColor);
        }

        if (this.rat.getCategory() != Rat.Category.Category) {
            if (!this.getSettings().isEmpty()) {
                FontUtils.i20.drawString("k", ((int) ((float) this.category.getX() + 71.0F)), (this.category.getY() + this.off + 5), ColorUtils.getIconColor());
            }

            Color color = ColorUtils.getFontColor(2).darker().darker().darker().darker().darker();

            GlUtils.startScale((float) (this.category.getX() + 60), (float) (this.category.getY() + this.off + offset), (float) this.bindAnimation.getValue());

            if (this.isHoveringOverModule(MouseUtils.mouseX, MouseUtils.mouseY) || this.isExpanded()) {
                this.bindAnimation.setDirection(Direction.FORWARDS);
                RoundedUtils.drawRound((float) (this.category.getX() + 60), (float) (this.category.getY() + this.off + offset), 10.0F, 10.0F, 2.0F, color);
            } else {
                this.bindAnimation.setDirection(Direction.BACKWARDS);
            }

            if (this.isHoverOverKeybind(MouseUtils.mouseX, MouseUtils.mouseY)) {
                this.bindAnimation.setDirection(Direction.FORWARDS);
                RoundedUtils.drawRound((float) (this.category.getX() + 60), (float) (this.category.getY() + this.off + offset), 10.0F, 10.0F, 2.0F, color);

                if (this.rat.getBind() == 0) {
                    FontUtils.i18.drawString("n", (this.category.getX() + 61), (this.category.getY() + this.off + offset + 3), ColorUtils.getFontColor(2));
                }
            }

            GlUtils.stopScale();

            if (this.rat.getBind() != 0) {
                RoundedUtils.drawRound((float) (this.category.getX() + 60), (float) (this.category.getY() + this.off + offset), 10.0F, 10.0F, 2.0F, color);

                if (!this.isBinding) {
                    if (Keyboard.getKeyName(this.rat.getBind()) != null) {
                        FontUtils.r16.drawString(Keyboard.getKeyName(this.rat.getBind()).replace("NONE", " "), ((int) ((float) this.category.getX() + 63.0F)), (this.category.getY() + this.off + offset + 3), ColorUtils.getFontColor(2));
                    } else {
                        FontUtils.i18.drawString("n", (this.category.getX() + 61), (this.category.getY() + this.off + offset + 3), ColorUtils.getFontColor(2));
                    }
                }
            }

            if (this.isBinding) {
                RoundedUtils.drawRound((float) (this.category.getX() + 60), (float) (this.category.getY() + this.off + offset), 10.0F, 10.0F, 2.0F, color);
                FontUtils.i18.drawString("n", (this.category.getX() + 61), (this.category.getY() + this.off + offset + 3), ColorUtils.getFontColor(2));
            }
        }

        if (this.isExpanded() && !this.getSettings().isEmpty()) {
            for (IComp c : this.getSettings()) {
                c.draw();
            }
        }
    }

    @Override
    public int getHeight() {
        int y = FastEditUtils.ratGap;
        if (!this.isExpanded()) {
            return FastEditUtils.ratGap;
        } else {
            for (IComp smok : this.getSettings()) {
                if (smok instanceof SliderComp) {
                    y += FastEditUtils.ratGap;
                } else {
                    y += FastEditUtils.settingGap;
                }
            }

            return y;
        }
    }

    @Override
    public void update(int mouseX, int mouseY) {
        if (ClientUtils.inClickGui()) {
            if (!this.getSettings().isEmpty()) {
                for (IComp c : this.getSettings()) {
                    c.update(mouseX, mouseY);
                }
            }

            if (this.rat.getCategory() != Rat.Category.Category) {
                if (!Smok.inst.ratManager.getRatByClass(Blatant.class).isEnabled()) {
                    if (this.rat.getCategory() == Rat.Category.Blatant)
                        return;
                }

                if (!Smok.inst.ratManager.getRatByClass(Legit.class).isEnabled()) {
                    if (this.rat.getCategory() == Rat.Category.Legit)
                        return;
                }

                if (!Smok.inst.ratManager.getRatByClass(Other.class).isEnabled()) {
                    if (this.rat.getCategory() == Rat.Category.Other)
                        return;
                }

                if (!Smok.inst.ratManager.getRatByClass(Useless.class).isEnabled()) {
                    if (this.rat.getCategory() == Rat.Category.Useless)
                        return;
                }

                if (!Smok.inst.ratManager.getRatByClass(Visuals.class).isEnabled()) {
                    if (this.rat.getCategory() == Rat.Category.Visuals)
                        return;
                }

                if (!Smok.inst.guiManager.getClickGui().renderFavorites) {
                    if (this.rat.getCategory() == Rat.Category.Favorites)
                        return;
                }

                if (this.isHoveringOverModule(mouseX, mouseY) && this.category.isOpened() && !this.isExpanded()) {
                    if (!Smok.inst.ratManager.getRatByClass(Blatant.class).isEnabled()) {
                        if (this.rat.getCategory() == Rat.Category.Blatant)
                            return;
                    }

                    if (!Smok.inst.ratManager.getRatByClass(Legit.class).isEnabled()) {
                        if (this.rat.getCategory() == Rat.Category.Legit)
                            return;
                    }

                    if (!Smok.inst.ratManager.getRatByClass(Other.class).isEnabled()) {
                        if (this.rat.getCategory() == Rat.Category.Other)
                            return;
                    }

                    if (!Smok.inst.ratManager.getRatByClass(Useless.class).isEnabled()) {
                        if (this.rat.getCategory() == Rat.Category.Useless)
                            return;
                    }

                    if (!Smok.inst.ratManager.getRatByClass(Visuals.class).isEnabled()) {
                        if (this.rat.getCategory() == Rat.Category.Visuals)
                            return;
                    }

                    if (!Smok.inst.guiManager.getClickGui().renderFavorites) {
                        if (this.rat.getCategory() == Rat.Category.Favorites)
                            return;
                    }

                    if (Gui.toolTips.isEnabled()) {
                        RoundedUtils.drawRoundOutline(mouseX + 5, mouseY + 13, (float) FontUtils.r16.getStringWidth(this.rat.getDescription()), (int) (FontUtils.r16.getHeight() * 1.2F - 2.0), 1.0F, 2.2F, ColorUtils.getBackgroundColor(4), ColorUtils.getBackgroundColor(4).brighter());
                        FontUtils.r16.drawString(this.rat.getDescription(), mouseX + 5, mouseY + 13, ClientUtils.isSmokTheme() ? ColorUtils.getClientColor(90000) : Color.white);
                    }
                }
            }

            MouseUtils.mouseX = mouseX;
            MouseUtils.mouseY = mouseY;
        }
    }

    @Override
    public void mouseDown(int x, int y, int b) {
        if (!Smok.inst.ratManager.getRatByClass(Blatant.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Blatant)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Legit.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Legit)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Other.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Other)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Useless.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Useless)
                return;
        }

        if (!Smok.inst.ratManager.getRatByClass(Visuals.class).isEnabled()) {
            if (this.rat.getCategory() == Rat.Category.Visuals)
                return;
        }

        if (!Smok.inst.guiManager.getClickGui().renderFavorites) {
            if (this.rat.getCategory() == Rat.Category.Favorites)
                return;
        }

        if (!this.rat.isHidden()) {

            // Left click
            if (b == 0) {

                // Toggle
                if (this.isHoveringOverModule(x, y)) {

                    // Favorites
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {

                        if (!this.rat.isFavorite()) {
                            Smok.inst.ratManager.getFavorites().add(this.rat);

                            this.rat.setFavorite(true);
                        } else {
                            Smok.inst.ratManager.getFavorites().remove(this.rat);

                            this.rat.setFavorite(false);
                        }

                        return;
                    } else {
                        this.rat.toggle();
                    }
                }

                // Binding
                if (this.isHoverOverKeybind(x, y)) {
                    this.isBinding = true;
                }
            }

            // Right click
            if (b == 1) {

                // Settings
                if (this.isHoveringOverModule(x, y) && !this.getSettings().isEmpty()) {
                    this.expanded = !this.expanded;
                    this.category.render();
                }

                // Remove bind
                if (this.isBinding) {
                    this.rat.setBind(0);
                    this.isBinding = false;
                }
            }
        }

        for (IComp comp : this.getSettings()) {
            if (comp.getY() > this.getY())
                comp.mouseDown(x, y, b);
        }
    }

    @Override
    public void mouseReleased(int x, int y, int b) {
        for (IComp comp : this.getSettings()) {
            comp.mouseReleased(x, y, b);
        }
    }

    @Override
    public void keyTyped(char chara, int key) {
        for (IComp comp : this.getSettings()) {
            comp.keyTyped(chara, key);
        }

        if (this.isBinding) {
            if (key != 11) {
                this.rat.setBind(key);
            } else {
                if (Mouse.getEventButton() != 0) {
                    this.rat.setBind(Mouse.getEventButton());
                } else {
                    this.rat.setBind(0);
                }
            }

            this.isBinding = false;
        }
    }

    boolean isHoverOverKeybind(int x, int y) {
        return MouseUtils.isInside(x, y, this.category.getX() + 56, this.category.getY() + this.off, 15.0, 15.0);
    }

    public boolean isHoveringOverModule(int x, int y) {
        return x > this.category.getX() && x < this.category.getX() + this.category.getWidth() - 24 && y > this.category.getY() + this.off && y < this.category.getY() + FastEditUtils.ratGap + this.off;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public ArrayList<IComp> getSettings() {
        return this.settings;
    }

    public CategoryComp getCategory() {
        return this.category;
    }

    public void setCategory(CategoryComp category) {
        this.category = category;
    }

    public Rat getRat() {
        return this.rat;
    }

    public void setRat(Rat rat) {
        this.rat = rat;
    }

    public int getOff() {
        return this.off;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getY() {
        return this.category.getY() + this.off;
    }

}