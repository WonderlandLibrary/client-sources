package me.sleepyfish.smok.gui.guis;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.gui.comp.IComp;
import me.sleepyfish.smok.utils.misc.MouseUtils;
import me.sleepyfish.smok.utils.entities.SoundUtils;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.rats.impl.visual.*;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.render.GlUtils;
import me.sleepyfish.smok.utils.render.RenderUtils;
import me.sleepyfish.smok.gui.comp.impl.SliderComp;
import me.sleepyfish.smok.gui.comp.impl.CategoryComp;
import me.sleepyfish.smok.utils.render.animation.normal.Animation;
import me.sleepyfish.smok.utils.render.animation.normal.Direction;
import me.sleepyfish.smok.utils.render.animation.simple.AnimationUtils;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import java.util.Objects;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

// Class from SMok Client by SleepyFish
public class ClickGui extends GuiScreen {

    private final ArrayList<CategoryComp> categoryComps = new ArrayList<>();
    private ScheduledFuture<?> future;
    private boolean textHudMoving = false;
    private boolean friendsGuiMoving = false;
    private boolean cosmeticsGuiMoving = false;
    private boolean targetHudMoving = false;
    public boolean close;
    public Animation introAnimation;
    private int oldPosX;
    private int oldPosY;

    public boolean renderingBlur = false;
    public boolean allowRenderBlur = true;
    public boolean renderFavorites = false;

    public ClickGui() {
        int widthOff = 50;

        for (Rat.Category modCategory : Rat.Category.values()) {
            CategoryComp current = new CategoryComp(modCategory);

            if (current.category != Rat.Category.Favorites) {
                if (current.category == Rat.Category.Category) {
                    current.setX(8);
                    current.setY(22);
                } else {
                    current.setX(widthOff);
                    current.setY(20);
                }
                current.setOpened(true);
                this.categoryComps.add(current);
                widthOff += 95;
            } else {
                current.setX(8);
                current.setY(136);
                current.setOpened(true);
                this.categoryComps.add(current);
            }
        }
    }

    public void initGui() {
        if (Gui.saveMousePos.isEnabled()) {
            Mouse.setCursorPosition(this.oldPosX, this.oldPosY);
        }

        ClientUtils.updateClientVersion();

        this.introAnimation = AnimationUtils.getAnimation();
        this.close = false;

        this.blur(true);
    }

    public void drawScreen(int x, int y, float p) {
        GlUtils.startScale(mc.displayWidth / 4F, mc.displayHeight / 4F, (float) this.introAnimation.getValue());

        MouseUtils.mouseX = x;
        MouseUtils.mouseY = y;
        Gui.setColorMode();

        if (Smok.inst.ratManager.getRatByClass(Text_Gui.class).isEnabled() && this.textHudMoving) {
            Text_Gui.movementOnTop(MouseUtils.mouseX, MouseUtils.mouseY);
        }

        if (Smok.inst.ratManager.getRatByClass(Target_Hud.class).isEnabled() && this.targetHudMoving) {
            Target_Hud.movementOnTop(MouseUtils.mouseX, MouseUtils.mouseY);
        }

        if (Smok.inst.ratManager.getRatByClass(Friends_Gui.class).isEnabled() && this.friendsGuiMoving) {
            Friends_Gui.movementOnTop(MouseUtils.mouseX, MouseUtils.mouseY);
        }

        if (Smok.inst.ratManager.getRatByClass(Cosmetics_Gui.class).isEnabled() && this.cosmeticsGuiMoving) {
            Cosmetics_Gui.movementOnTop(MouseUtils.mouseX, MouseUtils.mouseY);
        }

        if (Smok.inst.ratManager.getRatByClass(Friends_Gui.class).isEnabled()) {
            Friends_Gui.render();
        }

        if (Smok.inst.ratManager.getRatByClass(Cosmetics_Gui.class).isEnabled()) {
            Cosmetics_Gui.render();
        }

        if (this.close) {
            this.textHudMoving = false;
            this.friendsGuiMoving = false;
            this.cosmeticsGuiMoving = false;
            this.targetHudMoving = false;
            this.introAnimation.setDirection(Direction.BACKWARDS);

            for (IComp comp : Objects.requireNonNull(this.categoryComps.iterator().hasNext() ? this.categoryComps.iterator().next().getRats() : null)) {
                if (comp instanceof SliderComp) ((SliderComp) comp).sliding = false;
            }

            if (this.introAnimation.isDone(Direction.BACKWARDS)) {
                mc.displayGuiScreen(null);
                this.blur(false);
            }
        }

        RenderUtils.drawAuthor(this.width, this.height);
        RenderUtils.drawVersion(this.width, this.height);

        // render categories
        for (CategoryComp cat : this.categoryComps) {
            cat.drawCategory();
            cat.up(MouseUtils.mouseX, MouseUtils.mouseY);

            for (IComp c : cat.getRats()) {
                c.update(MouseUtils.mouseX, MouseUtils.mouseY);
            }
        }

        GlUtils.stopScale();
    }

    public void mouseClicked(int x, int y, int b) {
        if (Smok.inst.ratManager.getRatByClass(Friends_Gui.class).isEnabled()) {
            Friends_Gui.clickMouse(x, y, b);
        }

        if (Smok.inst.ratManager.getRatByClass(Cosmetics_Gui.class).isEnabled()) {
            Cosmetics_Gui.clickMouse(x, y, b);
        }

        Iterator<CategoryComp> iter = this.categoryComps.iterator();

        if (Smok.inst.ratManager.getRatByClass(Text_Gui.class).isEnabled() && MouseUtils.isInside(x, y, (Text_Gui.guiX - 10), (Text_Gui.guiY - 10), 95.0, 25.0) && b == 0) {
            this.textHudMoving = true;
            Text_Gui.guiX2 = x - Text_Gui.guiX;
            Text_Gui.guiY2 = y - Text_Gui.guiY;
        }

        if (Smok.inst.ratManager.getRatByClass(Target_Hud.class).isEnabled() && MouseUtils.isInside(x, y, Target_Hud.guiX, Target_Hud.guiY, 140.0, 48.0) && b == 0) {
            this.targetHudMoving = true;
            Target_Hud.guiX2 = x - Target_Hud.guiX;
            Target_Hud.guiY2 = y - Target_Hud.guiY;
        }

        if (Smok.inst.ratManager.getRatByClass(Friends_Gui.class).isEnabled() && MouseUtils.isInside(x, y, (Friends_Gui.guiX - 10), (Friends_Gui.guiY - 10), 95.0, 25.0) && b == 0) {
            this.friendsGuiMoving = true;
            Friends_Gui.guiX2 = x - Friends_Gui.guiX;
            Friends_Gui.guiY2 = y - Friends_Gui.guiY;
        }

        if (Smok.inst.ratManager.getRatByClass(Cosmetics_Gui.class).isEnabled() && MouseUtils.isInside(x, y, (Cosmetics_Gui.guiX - 10), (Cosmetics_Gui.guiY - 10), 95.0, 25.0) && b == 0) {
            this.cosmeticsGuiMoving = true;
            Cosmetics_Gui.guiX2 = x - Cosmetics_Gui.guiX;
            Cosmetics_Gui.guiY2 = y - Cosmetics_Gui.guiY;
        }

        if (MouseUtils.isInside(x, y, (this.width - 62), (this.height - 33), 63.0, 27.0) && b == 0) {
            ClientUtils.openLink("https://github.com/SleepyFishYT/SMok-Client/releases/latest");
        }

        if (MouseUtils.isInside(x, y, ((float) this.width / 2.0F - ((float) FontUtils.r20.getStringWidth("SleepyFish") / 2.0F + 2.0F)), ((float) this.height - 68.0F), ((float) FontUtils.r20.getStringWidth("SleepyFish") + 4.0F), 20.0) && b == 0) {
            ClientUtils.openLink("https://discord.gg/7JXXvkufJK");
        }

        while (true) {
            CategoryComp cat;
            do {
                do {
                    if (!iter.hasNext())
                        return;

                    cat = iter.next();

                    if (cat.category == Rat.Category.Category) {
                        if (cat.isOpened() && b == 0) {
                            if (this.isHoveringOverFavorite(MouseUtils.mouseX, MouseUtils.mouseY, cat)) {
                                if (Smok.inst.guiManager.getClickGui().renderFavorites) {
                                    if (Gui.clientSounds.isEnabled()) {
                                        SoundUtils.playDisableSound();
                                    }

                                    Smok.inst.guiManager.getClickGui().renderFavorites = false;
                                } else {
                                    if (Gui.clientSounds.isEnabled()) {
                                        SoundUtils.playEnableSound();
                                    }

                                    Smok.inst.guiManager.getClickGui().renderFavorites = true;
                                }
                            }
                        }
                    }

                    if (cat.insideArea(x, y) && !cat.isHoveringOverCategoryCollapseIcon(x, y) && !cat.mousePressed(x, y) && b == 0) {
                        cat.mousePressed(true);
                        cat.xx = x - cat.getX();
                        cat.yy = y - cat.getY();
                    }

                    if (cat.insideArea(x, y) && b == 1)
                        cat.setOpened(!cat.isOpened());

                } while (!cat.isOpened());
            } while (cat.getRats().isEmpty());

            for (IComp c : cat.getRats()) {
                c.mouseDown(x, y, b);
            }
        }
    }

    public void mouseReleased(int x, int y, int button) {
        if (button == 0) {
            if (Smok.inst.ratManager.getRatByClass(Text_Gui.class).isEnabled() && this.textHudMoving) {
                this.textHudMoving = false;
            }

            if (Smok.inst.ratManager.getRatByClass(Target_Hud.class).isEnabled() && this.targetHudMoving) {
                this.targetHudMoving = false;
            }

            if (Smok.inst.ratManager.getRatByClass(Friends_Gui.class).isEnabled() && this.friendsGuiMoving) {
                this.friendsGuiMoving = false;
            }

            if (Smok.inst.ratManager.getRatByClass(Cosmetics_Gui.class).isEnabled() && this.cosmeticsGuiMoving) {
                this.cosmeticsGuiMoving = false;
            }

            for (CategoryComp cat : this.categoryComps) {
                cat.mousePressed(false);
            }

            Iterator<CategoryComp> iter = categoryComps.iterator();

            CategoryComp cat;
            while (iter.hasNext()) {
                cat = iter.next();
                cat.mousePressed(false);
            }

            iter = categoryComps.iterator();
            while (true) {
                do {
                    do {
                        if (!iter.hasNext())
                            return;

                        cat = iter.next();
                    } while (!cat.isOpened());
                } while (cat.getRats().isEmpty());

                for (IComp c : cat.getRats())
                    c.mouseReleased(x, y, button);
            }
        }
    }

    public void keyTyped(char t, int k) {
        Iterator<CategoryComp> iter = this.categoryComps.iterator();
        if (k == 1) {
            if (Gui.saveMousePos.isEnabled()) {
                this.oldPosX = MouseUtils.mouseX;
                this.oldPosY = MouseUtils.mouseY;
            }

            this.close = true;
        } else {
            while (iter.hasNext()) {
                CategoryComp cat = iter.next();
                if (cat.isOpened() && !cat.getRats().isEmpty()) {
                    for (IComp c : cat.getRats())
                        c.keyTyped(t, k);
                }
            }
        }
    }

    public void handleMouseInput() {
        super.handleMouseInput();

        float sc = 0 + Mouse.getEventDWheel() * 0.2F;
        for (CategoryComp category : getCategories())
            category.scroll(sc);
    }

    public void onGuiClosed() {
        if (this.future != null) {
            this.future.cancel(true);
            this.future = null;
        }

        for (CategoryComp cat : this.categoryComps) {
            cat.mousePressed(false);
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public boolean isTargetHudMoving() {
        return this.targetHudMoving;
    }

    public boolean isTextHudMoving() {
        return this.textHudMoving;
    }

    public boolean isFriendsGuiMoving() {
        return this.friendsGuiMoving;
    }

    public boolean isCosmeticsGuiMoving() {
        return this.cosmeticsGuiMoving;
    }

    public ArrayList<CategoryComp> getCategories() {
        return this.categoryComps;
    }

    private boolean isHoveringOverFavorite(int x, int y, CategoryComp cat) {
        return MouseUtils.isInside(x, y, cat.getX() + 4, cat.getY() + cat.getHeight() + cat.getCategoryHeight() + 4, 10, 10);
    }

    public void blur(boolean mode) {
        this.allowRenderBlur = true;
        this.renderingBlur = mode;

        if (!mode) {
            mc.entityRenderer.stopUseShader();
        }
    }

}