package club.bluezenith.ui.guis.mainmenu.changelog.render;

import club.bluezenith.ui.guis.mainmenu.changelog.fetch.ChangelogEntry;
import club.bluezenith.util.anim.ScrollV;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.ui.clickgui.ClickGui.i;
import static club.bluezenith.util.font.FontUtil.vkChangelog45;
import static club.bluezenith.util.font.FontUtil.vkChangelog50;
import static club.bluezenith.util.render.RenderUtil.animate;
import static club.bluezenith.util.render.RenderUtil.crop;
import static java.lang.Math.max;
import static net.minecraft.client.renderer.GlStateManager.translate;
import static org.lwjgl.opengl.GL11.*;

public class BasicChangelogRenderer implements ChangelogRenderer {
    float x, y, x2, y2, titleX, titleY, titleX2, titleY2;
    float grayFade = 1, scissorProgress = 1;
    boolean needUpdate, hasReducedHeight, isShown;

    FontRenderer font = vkChangelog45;
    ScrollV scroll = new ScrollV() {
        @Override
        protected void clampTarget(float target) { //faster scrolling down
            this.target = MathHelper.clamp(target, minY - 50, maxY + 150);
        }
    };

    List<ChangelogEntry> contents = new ArrayList<>();

    @Override
    public void setContents(List<ChangelogEntry> _contents) {

        final float maxStringWidth = x2 - this.x;

        //I have to cast this shit bc when u call .reversed() it doesn't infer the object type...

     //   _contents.sort(comparingDouble(a -> font.getStringWidthF(((ChangelogEntry)a).getType().getCharacter() + " " + ((ChangelogEntry)a).getContents())).reversed());

        //copy the list to not modify the original one
        //that way we can fit the contents to given width again
        final List<ChangelogEntry> entries = new ArrayList<>(_contents);

        contents.clear();

        for (int i = 0; i < entries.size(); i++) {
            final ChangelogEntry entry = entries.get(i);
            final boolean needTrim = font.getStringWidthF(entry.toString()) > maxStringWidth;

            if(needTrim) {
                final String[] words = entry.getContents().split(" ");
                final StringBuilder builder = new StringBuilder();

                for (int j = 0; j < words.length; j++) {
                    final boolean hasNext = words.length > j + 1;
                    final String currentWord = words[j],
                                 nextWord = hasNext ? words[j + 1] : null,
                                 entireString = builder.toString();

                    if(j == 0) {
                        builder.append(currentWord);
                    }

                    if(nextWord != null) {
                        if(font.getStringWidthF(entry.getType().getCharacter() + " " + entireString + nextWord) >= maxStringWidth) {
                            contents.add(new ChangelogEntry(entireString, entry.getType())); //we made sure this entry is equal or close to the target width, safe to add it
                            //the rest of the original entry is going to be checked next iteration
                            entries.add(i + 1, new ChangelogEntry(entry.getContents().substring(entireString.length()), entry.getType()));
                            break;
                        } else builder.append(" ").append(nextWord);
                    }
                }
            } else contents.add(entry);
        }

        scroll.reset(); //reset the scroll in case of a change in the changelog contents?
        hasReducedHeight = false; //reset the blocking scroll state, just in case :)
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks, boolean updateScroll) {
        if(contents == null || contents.isEmpty()) {
            drawTitle(mouseX, mouseY);
            return; //don't draw anything if the list has failed to fetch or isn't fetched yet
        }
        this.needUpdate = updateScroll;
        glPushMatrix();

        final float scrolled = scroll.getAmountScrolled();
        final double yIncrement = font.FONT_HEIGHT + font.FONT_HEIGHT * 0.2;
        float currentY = y;

        drawTitle(mouseX, mouseY); //draw the title and set the title variables

        glEnable(GL_SCISSOR_TEST); //enable scissor to make the changelog not take up your entire screen
        crop(x, y + 2, x2 + 10, max(y + 2, y2 * scissorProgress)); //crop the area, multiply y2 by scissorProgress for close/open animation

        translate(0, -scrolled + 1.5F, 0); //translate (for scroll)

        float maxStringWidth = 0; //the most wide string length. used to ensure that scrolling with mouse cursor outside the changelog area is impossible.

        for (final ChangelogEntry content : contents) {
            if(currentY - scrolled > y2) continue; //if the changelog entry is out of the scissor box, don't render it to save FPS
            if(currentY - scrolled < y - yIncrement) {
                currentY += yIncrement; //have to increment the Y for skipped entries,
                continue;               //otherwise the changelog does not draw as it's stuck (currentY always 0 though scroll still works)
            }
            final String changelogEntry = content.toString(); //looks like [+] ChangelogTextGoesHere

            font.drawString(changelogEntry,
                    x + 0.001F, //0.001F because it seems to fix the font issues sometimes.
                    currentY,
                    content.getType().getEffectiveColor().getRGB());

            final float drawnStringWidth = font.getStringWidthF(changelogEntry);

            if(maxStringWidth < drawnStringWidth) maxStringWidth = drawnStringWidth;
            currentY += yIncrement;
        }

        maxStringWidth += 25; //extend the area (for the hover detection) a bit

        if(y2 > currentY) { //if the max height isn't reached, lower it, otherwise scissor animation will take too long
            hasReducedHeight = true; //block scroll because it's not necessary
            y2 = currentY + 5;
        }

        glDisable(GL_SCISSOR_TEST); //we don't need the scissor box anymore

        if(this.needUpdate) { //if the area isn't being drawn for blooming/blurring it, update animations and scroll
            if (!this.isShown) { //open-close animation
                scissorProgress = animate(0, scissorProgress, 0.06F);
            } else {
                scissorProgress = animate(1, scissorProgress, 0.12F);
            }                                                                                            //extend the area (for the hover detection) a bit
            if(!this.hasReducedHeight && this.isShown && i(mouseX, mouseY, x, y, maxStringWidth, y2 + 25))
              scroll.update(50, currentY, y2); //update the scroll if it's needed, isn't blocked and the mouse's hovered over the changelog area.
        }
        glPopMatrix();
    }


    private void drawTitle(int mouseX, int mouseY) {
        final FontRenderer titleFont = vkChangelog50; //the title font

        final boolean failed = contents == null || contents.isEmpty();

        String title = failed ? "Couldn't retrieve changelog"
                : "Changelog", buildNumber = isShown ? "build " + getBlueZenith().version() + "" : "click to open..."; //the title strings...

        titleX = x; //these variables are used to determine whether the title is clicked on in this.mouseClicked
        titleY = y - titleFont.FONT_HEIGHT;
        titleX2 = titleFont.getStringWidthF(title) + 1;
        titleY2 = y - vkChangelog45.FONT_HEIGHT;

        if(this.needUpdate) { //if the area isn't being drawn for blooming/blurring it, update animations
            if (i(mouseX, mouseY, titleX, titleY, titleX2 + vkChangelog45.getStringWidthF(buildNumber) + 1, titleY2 + vkChangelog45.FONT_HEIGHT)) {
                grayFade = animate(0.6F, grayFade, 0.08F);
            } else {
                grayFade = animate(1, grayFade, 0.08F);
            }
        }

        titleFont.drawString(title, titleX, titleY, new Color(grayFade, grayFade, grayFade).getRGB());

        if(!failed)
          vkChangelog45.drawString(buildNumber, titleX2, titleY2 - 1, new Color(grayFade, grayFade, grayFade).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        final String buildNumber = isShown ? "(build " + getBlueZenith().version() + ")" : "click to open..."; //the title strings...
        if(i(mouseX, mouseY, titleX, titleY, titleX2 + vkChangelog45.getStringWidthF(buildNumber) + 1, titleY2 + vkChangelog45.FONT_HEIGHT)) {
            //open/close the changelog if the mouse clicked on the title
            isShown = !isShown;
        }
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
     //   this.x2 = 1080; //currently unused, so it's just set to a huge value
        this.y = y;
    }

    @Override
    public void setMaxHeight(float height) {
        this.y2 = height;
    }

    @Override
    public void setWidth(float width) {
        this.x2 = x + width;
    }
}
