package club.bluezenith.ui.alt.rewrite;

import club.bluezenith.ui.alt.rewrite.context.ContextMenu;
import club.bluezenith.util.anim.ScrollV;
import club.bluezenith.util.render.RenderUtil;
import club.bluezenith.util.render.scrollable.ScrollableList;
import club.bluezenith.util.render.scrollable.TriPredicate;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.ui.clickgui.ClickGui.i;
import static club.bluezenith.util.MinecraftInstance.mc;
import static club.bluezenith.util.render.RenderUtil.crop;
import static net.minecraft.client.renderer.GlStateManager.translate;
import static org.lwjgl.input.Keyboard.KEY_LCONTROL;
import static org.lwjgl.input.Keyboard.isKeyDown;
import static org.lwjgl.opengl.GL11.*;

public class AccountList implements ScrollableList<AccountElement> {
    float x, y, x2, y2, distanceBetweenElements, scrollAmount;
    boolean isMouseInScissorArea, reducedHeight;
    Predicate<AccountElement> visibilityPredicate;
    AccountElement selectedAccount;

    ScrollV scroll = new ScrollV() {
        @Override
        protected void clampTarget(float target) { //override the method and set higher bounds for faster scrolling (downwards)
            this.target = MathHelper.clamp(target, minY - 50, maxY + 550);
        }
    };

    List<AccountElement> contents = new ArrayList<>();
    List<AccountElement> selectedAccounts = new ArrayList<>();
    AccountElement singleSelectedAccount;
    ContextMenu contextMenu;

    private float contextXMultiplier, contextY;

    @Override
    public void setContents(List<AccountElement> contents) {
        getBlueZenith().getAccountRepository().distinct();
                                                                                        //make renderable accounts
        getBlueZenith().getAccountRepository().getAccounts().stream().map(acc -> acc.createElement().getRenderedElement()).forEach(element -> {
            if(!this.contents.contains(element))
                this.contents.add(element);
        });
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks, boolean updateScroll) {
        if(getBlueZenith().getAccountRepository().getAccounts().size() != this.contents.size()) setContents(null); //keep em in sync

        glPushMatrix();
        isMouseInScissorArea = false;
        reducedHeight = false;
        float currentY = y;

        glEnable(GL_SCISSOR_TEST);
        crop(x, y, x2, y2);
        isMouseInScissorArea = i(mouseX, mouseY, x, y, x2, y2);
        RenderUtil.rect(x, y, x2, y2, new Color(0, 0, 0, 80));
        scrollAmount = scroll.getAmountScrolled();

        translate(0, -scrollAmount, 0); //translate (for scroll)

        for (final AccountElement content : contents) {
            if(this.visibilityPredicate != null && !visibilityPredicate.test(content)) continue;
            if(currentY - scrollAmount > y2) continue; //if entry is out of the scissor box, don't render it to save FPS
            if(currentY - scrollAmount < -1) {
                currentY += content.getDrawnElementHeight(); //have to increment the Y for skipped entries,
                continue;               //otherwise the list does not draw as it's stuck (currentY always 0 though scroll still works)
            }
            if(content.isSelected()) {
                selectedAccount = content;
            }

            content.setDistanceBetweenNext(distanceBetweenElements);
            content.setPosition(x, currentY);

            if(isMouseInScissorArea || content.equals(singleSelectedAccount) || selectedAccounts.contains(content)) {
                //whether there is a context menu that is being hovered over
                //and therefore should capture the mouse instead of the current account element
                final boolean blocked = contextMenu != null && contextMenu.isMouseOver(mouseX, mouseY);

                content.render(mouseX, mouseY, partialTicks,
                        blocked || !content.equals(singleSelectedAccount)
                                && !selectedAccounts.contains(content)
                                && !isHovering.test(mouseX, (int) (mouseY + scrollAmount), content)
                );
            }
            else content.render(mouseX, mouseY, partialTicks, true);
            currentY += content.getDrawnElementHeight();
        }

        if(currentY < y2) {
            reducedHeight = true;
        }

        glDisable(GL_SCISSOR_TEST); //we don't need the scissor box anymore

        if(contextMenu != null) {
            contextMenu.draw(mouseX, mouseY);
            if(contextMenu.hasDisappeared())
                contextMenu = null;
        }

        //if the area isnt being drawn for blooming/blurring it, & no context menu is drawn, update animations and scroll
        if((contextMenu == null || contextMenu.hasDisappeared()) && updateScroll && !reducedHeight) {
            scroll.update(1, currentY, y2 - distanceBetweenElements); //update the scroll if it's needed, isn't blocked and the mouse's hovered over the changelog area.
        }
        glPopMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(!i(mouseX, mouseY, x - 35, y, x2 + 30, y2 + 60)) { //+60 is the buttons height. just to make it not reset the selectedAccounts when you click "Remove" or something like that.
            if(mouseButton == 0) {
                singleSelectedAccount = null;
                selectedAccounts.clear();
            }
        }
        if(!isMouseInScissorArea) {
            if(contextMenu != null && !contextMenu.isMouseOver(mouseX, mouseY))
                contextMenu.disappear();
            return;
        }

        mouseY += this.scrollAmount;

        for (AccountElement content : this.contents) {
            if (this.visibilityPredicate != null && !visibilityPredicate.test(content)) continue;
                if (isHovering.test(mouseX, mouseY, content)) {
                    if (mouseButton == 1) {
                        contextMenu = new ContextMenu(content, mouseX, mouseY);

                        //these variables are used for when the window gets resized
                        //and a context menu is active
                        //in order to re-position the context menu correctly.
                        contextXMultiplier = (float) mouseX / mc.displayWidth;
                        contextY = mouseY;

                    } else if (isKeyDown(KEY_LCONTROL) && mouseButton == 0) {
                        singleSelectedAccount = null;
                        if (selectedAccounts.contains(content))
                            selectedAccounts.remove(content);
                        else selectedAccounts.add(content);
                    } else if(contextMenu == null || contextMenu.hasDisappeared()) {
                        singleSelectedAccount = content;
                        content.mouseClicked(mouseX, mouseY, mouseButton);
                        if (content.isSelected() && mouseButton == 0)
                            getBlueZenith().getNewAltManagerGUI().loginWithAccount(content);
                    }
                    break;
                } else content.isSelected = false;
            }

        if(contextMenu != null) {
            if (contextMenu.isMouseOver(mouseX, mouseY))
                contextMenu.mouseClicked(mouseX, mouseY, mouseButton);
            else contextMenu.disappear();
        }
     }

    @Override
    public void keyTyped(int code, char key) {
        if(contextMenu != null)
            contextMenu.keyTyped(code);
    }

    @Override
    public void sortContents(Comparator<AccountElement> comparator) {
        this.contents.sort(comparator);
    }

    private final TriPredicate<Integer, Integer, AccountElement> isHovering = (mouseX, mouseY, account) -> i(mouseX, mouseY, account.x, account.y, account.x +account.width, account.y + account.height - distanceBetweenElements);

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setBounds(float width, float height) {
        this.x2 = width;
        this.y2 = height;
    }

    @Override
    public void setDistanceBetweenElements(float distanceBetweenElements) {
        this.distanceBetweenElements = distanceBetweenElements;
    }

    @Override
    public void onGuiInit() {
        scroll.reset();
        if(contextMenu != null) {
            this.contextMenu = new ContextMenu(contextMenu.getAccount(), mc.displayWidth * contextXMultiplier, contextY);
        }
    }

    @Override
    public void setItemVisibilityPredicate(Predicate<AccountElement> predicate) {
        this.visibilityPredicate = predicate;
    }
}
