package club.bluezenith.ui.alt.rewrite;

import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.core.data.alt.info.AccountType;
import club.bluezenith.core.data.alt.info.HypixelInfo;
import club.bluezenith.core.data.preferences.Preferences;
import club.bluezenith.util.render.scrollable.ScrollableElement;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.Objects;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.font.FontUtil.*;
import static club.bluezenith.util.math.MathUtil.convertMSToTimeString;
import static club.bluezenith.util.math.MathUtil.convertMillisToAltPlaytime;
import static club.bluezenith.util.render.RenderUtil.animate;
import static club.bluezenith.util.render.RenderUtil.rect;
import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

public class AccountElement implements ScrollableElement {
    private static final int PRIMARY_COLOR = new Color(16, 16, 32, 150).getRGB(),
                             STAR_COLOR = new Color(245, 214, 61).getRGB();
    boolean isSelected, isCurrentAccount, isDrawingContextMenu;
    long lastClickTime;
    float x, y, width, height, distanceBetweenNext, hoverProgress, loginIndicatorProgress, invalidIndicatorProgress;

    AccountInfo associatedAccount;

    public AccountElement(AccountInfo account) {
        this.associatedAccount = account;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks, boolean isHoveredOver) {
        rect(x, y, x + (width = 320), y + (height = 25) - distanceBetweenNext, PRIMARY_COLOR);

        rect(x, y, x + width, y + height - distanceBetweenNext, new Color(1, 1, 1, animateHoverProgress(isHoveredOver)));
        rect(x, y, x + width, y + height - distanceBetweenNext, new Color(0.292156863F, 0.945098039F, 0.292156863F, animateLoginProgress()).getRGB());
        rect(x, y, x + width, y + height - distanceBetweenNext, new Color(246 / 255F, 66 / 255F, 66 / 255F, animateInvalidProgress()));

        float longestStringLeftWidth = x + 2, nameStringWidth;
        rubikMedium37.drawString(getAccount().getEffectiveUsername(), longestStringLeftWidth, y + 2, -1, true);
        nameStringWidth = longestStringLeftWidth += rubikMedium37.getStringWidthF(getAccount().getEffectiveUsername());

        if(getAccount().isOffline()) { //cracked account
            float thisStringWidth = x + 2;

            rubikR30.drawString(
                    getAccount().getAccountType().equals(AccountType.MICROSOFT) ? "Microsoft account" : "Offline account",
                    x + 2,
                    y + 5F + rubikMedium37.FONT_HEIGHT,
                    -1,
                    true);

            thisStringWidth += rubikR30.getStringWidthF("Offline account");

            if(thisStringWidth > longestStringLeftWidth) longestStringLeftWidth = thisStringWidth;
        } else {
            float thisStringWidth = x + 2;
            final String creds = getAccount().getEmail();

            rubikR30.drawString(creds, x + 2, y + 5.5F + rubikMedium37.FONT_HEIGHT, -1, true);

            thisStringWidth += rubikR30.getStringWidthF(creds);
            if(thisStringWidth > longestStringLeftWidth) longestStringLeftWidth = thisStringWidth;
        }

        final boolean isFavorite = getAccount().isFavorite;
        if(isFavorite)
            starIcon.drawString("s", nameStringWidth + 2.5F, y + 3.5F, STAR_COLOR);

        final FontRenderer statsFont = rubikR27;

        float longestStringRight = 0;
        if(getAccount().getAccountType() != AccountType.OFFLINE) {
            float currentY = y + statsFont.FONT_HEIGHT - 3;

            final HypixelInfo info = getAccount().createInfo();

            final String playtime = format("PT: %s  GP: %s  W: %s  WLR: %s", info.getPlaytimeString(), info.getGamesPlayed(), info.getGamesWon(), info.getWinLoseRate());
            statsFont.drawString(playtime, longestStringRight = (x + width - statsFont.getStringWidthF(playtime) - 3), currentY, -1, true);
            currentY += statsFont.FONT_HEIGHT + 4;


            long timeTilExpires = getAccount().getTimeUntilExpires();
            String expiresIn = "";

            if(timeTilExpires != 0) {
                if(timeTilExpires < 0)
                    expiresIn = "Expired  ";
                else expiresIn = "Exp. in: " + convertMillisToAltPlaytime(timeTilExpires);
            }

            final String kills = format("%s  K: %s  D: %s  KDR: %s", expiresIn, info.getKills(), info.getDeaths(), info.getKillDeathRate());
            final float thisStringStart = x + width - statsFont.getStringWidthF(kills) - 3; //work with start positions because of different alignment
            statsFont.drawString(kills, thisStringStart, currentY, -1, true);

            if(thisStringStart < longestStringRight) //the string with least X is the one that's closer to center
                longestStringRight = thisStringStart;
        }

        if(getAccount().getHypixelInfo() != null && getAccount().getHypixelInfo().isBanned()) {
            final String unbanInfo =
                    getAccount().getHypixelInfo().getTimeLeftUntilUnban() == Long.MIN_VALUE ?
                            "§7Permanently §fbanned"
            : "Banned for §7" + convertMSToTimeString(getAccount().getHypixelInfo().getTimeLeftUntilUnban(), true);

            final float centeredX = (longestStringRight - (longestStringRight - longestStringLeftWidth)/2F) - statsFont.getStringWidthF(unbanInfo)/2F;
            statsFont.drawString(unbanInfo, centeredX + 0.01F, y + (height / 2F) - statsFont.FONT_HEIGHT/2f - 1.5F, -1, true);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        switch (mouseButton) {
            case 0:
                if (currentTimeMillis() - lastClickTime < Preferences.altManagerDoubleClickDelay) {
                    isSelected = true;
                    lastClickTime = 0;
                } else {
                    isSelected = false;
                    lastClickTime = currentTimeMillis();
                }
                break;
            case 2:
                getAccount().isFavorite = !getAccount().isFavorite;
                isSelected = false;
                break;
            case 1:
              //  mc.displayGuiScreen(new GuiAccountInfo(getBlueZenith().getNewAltManagerGUI(), true, this));
                isDrawingContextMenu = true;
                break;
        }
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setDistanceBetweenNext(float distanceBetweenNext) {
        this.distanceBetweenNext = distanceBetweenNext;
    }

    @Override
    public float getDrawnElementHeight() {
        return height + distanceBetweenNext;
    }

    @Override
    public float getDrawnElementWidth() {
        return width;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public boolean isDrawingContextMenu() {
        return isDrawingContextMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountElement that = (AccountElement) o;
        return Objects.equals(associatedAccount, that.associatedAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedAccount);
    }

    private float animateLoginProgress() {
        this.isCurrentAccount = getAccount().equals(getBlueZenith().getAccountRepository().getCurrentAccount());
        return loginIndicatorProgress = animate(isCurrentAccount ? 0.588235294F : 0, loginIndicatorProgress, 0.1F);
    }

    private float animateHoverProgress(boolean isHovered) {
        //true if this account is logged in & indicator alpha anim is done (don't fade out too early for smoother transition)
        final boolean hasLoginIndicator = this.isCurrentAccount && abs(loginIndicatorProgress - 0.588235294F) < 0.03;
        return hoverProgress = animate(isHovered || hasLoginIndicator ? 0 : 0.25F, hoverProgress, 0.08F);
    }

    private float animateInvalidProgress() {
        final boolean invalid = getAccount().getTimeUntilExpires() < 0 || getAccount().isInvalidCredentials();
        return invalidIndicatorProgress = animate(invalid ? 0.55F : 0F, invalidIndicatorProgress, 0.08F);
    }

    public AccountInfo getAccount() {
        return associatedAccount;
    }
}
