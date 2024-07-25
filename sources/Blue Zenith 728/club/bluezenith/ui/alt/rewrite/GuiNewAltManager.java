package club.bluezenith.ui.alt.rewrite;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.alt.AccountLogin;
import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.core.data.alt.info.AccountType;
import club.bluezenith.core.data.alt.info.HypixelInfo;
import club.bluezenith.core.data.preferences.Preferences;
import club.bluezenith.ui.alt.rewrite.actions.GuiAccountInfo;
import club.bluezenith.ui.alt.rewrite.actions.GuiAddAccount;
import club.bluezenith.ui.alt.rewrite.iptracker.IpTracker;
import club.bluezenith.ui.button.BorderlessCheckbox;
import club.bluezenith.ui.button.BorderlessCombobox;
import club.bluezenith.ui.guis.GuiConfirmDialog;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.client.TriConsumer;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.math.Range;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Comparator;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.core.data.preferences.Preferences.*;
import static club.bluezenith.ui.alt.AltLogin.createSession;
import static club.bluezenith.util.client.ClientUtils.emailPattern;
import static club.bluezenith.util.font.FontUtil.*;
import static club.bluezenith.util.math.MathUtil.getRandomInt;
import static java.awt.Toolkit.getDefaultToolkit;
import static java.awt.datatransfer.DataFlavor.stringFlavor;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.lwjgl.input.Keyboard.*;

public class GuiNewAltManager extends GuiScreen {

    private final MillisTimer sortTimer = new MillisTimer();
    public int selectedAccounts, previousAltCount;
    private final AccountList altList = new AccountList(); //the alt list (renderer, click handler, etc...)
    private boolean hasLoadedAccounts;
    private BorderlessCombobox sortingMode;

    public final IpTracker ipTracker = new IpTracker();

    @Override
    public void initGui() {
        this.parentScreen = BlueZenith.getBlueZenith().getMainMenu();

        if(!hasLoadedAccounts) {
            hasLoadedAccounts = true;
            altList.setContents(null); //the list is pulled from the accountRepository
        }

        altList.onGuiInit(); //reset the scroll

        final float middle = width/2F;
        final float halfWidthOfAltElement = 320/2F;
        final int listBottom = 30;

        //configure the alt list position etc
        altList.setPosition(middle - halfWidthOfAltElement, 25);
        altList.setBounds(middle + halfWidthOfAltElement, height - listBottom);
        altList.setDistanceBetweenElements(2);
        altList.setItemVisibilityPredicate((item) ->
                (!altManagerFavoritesOnly || item.getAccount().isFavorite)
                        && (!altManagerUnbannedOnly || (!item.getAccount().isBanned() || item.getAccount().getAccountType() == AccountType.OFFLINE))
                        && (!altManagerPremiumOnly || item.getAccount().getAccountType() != AccountType.OFFLINE));
        previousAltCount = altList.contents.size();

        int lastX;
        final int buttonsAmount = 6; //used for centering buttons
        final int margin = 5; //distance between buttons at the bottom of the list
        final int buttonWidth = 60;

        int checkboxY = 25;
        this.buttonList.add(sortingMode = new BorderlessCombobox(-4, (int) (middle + halfWidthOfAltElement + margin), checkboxY, "Sorting",
                stream(SortingMode.values()).map(SortingMode::getName).toArray(String[]::new)) //generate modes from the SortingMode enum
                .setCurrentIndex(Preferences.altManagerSortingIndex)
                .setValueChangeListener((str) -> this.altList.sortContents(SortingMode.values()[sortingMode.getCurrentIndex()].getAccountElementComparator().reversed())));
        checkboxY -= 2;
        this.buttonList.add(new BorderlessCheckbox(-3, (int) (middle + halfWidthOfAltElement + margin), checkboxY + 15, "Only favorite")
                .onClick((toggled) -> altManagerFavoritesOnly = toggled).setFont(rubikR30).setToggled(altManagerFavoritesOnly)); //setToggled to keep filter states after you close the gui

        this.buttonList.add(new BorderlessCheckbox(-2, (int) (middle + halfWidthOfAltElement + margin), checkboxY + 30, "Only unbanned")
                .onClick((toggled) -> altManagerUnbannedOnly = toggled).setFont(rubikR30).setToggled(altManagerUnbannedOnly));

        this.buttonList.add(new BorderlessCheckbox(-1, (int) (middle + halfWidthOfAltElement + margin), checkboxY + 45, "Only premium")
                .onClick((toggled) -> altManagerPremiumOnly = toggled).setFont(rubikR30).setToggled(altManagerPremiumOnly));

        this.buttonList.add(new GuiButton(0, lastX = (int)(middle - ((margin + buttonWidth) * buttonsAmount)/2F), height - listBottom + 5, buttonWidth, 20, "Login")
                .onClick(() -> {
                    final AccountElement accountElement = altList.singleSelectedAccount;
                    if(accountElement != null)
                        loginWithAccount(accountElement);
                }));

        lastX += margin + buttonWidth;

        this.buttonList.add(new GuiButton(1, lastX, height - listBottom + 5, buttonWidth, 20, "Add")
                .onClick(() -> mc.displayGuiScreen(new GuiAddAccount(this, true, false, (acc) ->
                    getBlueZenith().getAccountRepository().addAccount(acc)
                ).setTitle("Add account"))));
        lastX += margin + buttonWidth;

        this.buttonList.add(new GuiButton(2, lastX, height - listBottom + 5, buttonWidth, 20, "Remove")
                .onClick(() -> {
                    if(isKeyDown(KEY_LSHIFT)) {
                        mc.displayGuiScreen(new GuiConfirmDialog(this, true,
                                (accepted -> { if(accepted) removeAccounts(false); }))
                                .setTitle("Do you want to clear the entire account list?")
                                .setDescription("This action is irreversible!")
                                .setConfirmText("Delete")
                                .setDeclineText("Cancel"));
                    } else {
                        removeAccounts(true);
                    }
                }));
        lastX += margin + buttonWidth;

        this.buttonList.add(new GuiButton(3, lastX, height - listBottom + 5, buttonWidth, 20, "Direct")
                .onClick(() -> mc.displayGuiScreen(new GuiAddAccount(this, true, true, (acc) -> loginWithAccount(acc.createElement().getRenderedElement())).setTitle("Direct login"))));
        lastX += margin + buttonWidth;

        this.buttonList.add(new GuiButton(4, lastX, height - listBottom + 5, buttonWidth, 20, "Cracked")
                .onClick(() -> {
                    final Session crackedSession = new Session(randomAlphanumeric(getRandomInt(3, 17)), "", "", "mojang");
                    getBlueZenith().getAccountRepository().setOngoingLogin(new AccountInfo(""), supplyAsync(() -> new AccountLogin(crackedSession, new AccountInfo(crackedSession.getUsername()))));
                }));

        lastX += margin + buttonWidth;
        this.buttonList.add(new GuiButton(6, lastX, height - listBottom + 5, buttonWidth, 20, "Info")
                .onClick(() -> {
                    if(this.altList.singleSelectedAccount != null)
                    mc.displayGuiScreen(new GuiAccountInfo(this, true, this.altList.singleSelectedAccount));
                    else getBlueZenith().getNotificationPublisher().postError("Account Manager", "You have to select an account whose info to view.", 2500);
                }));
        lastX += margin + buttonWidth;
     /*   this.buttonList.add(new GuiButton(6, lastX, height - listBottom + 5, buttonWidth, 20, "test") //todo test
                .onClick(() -> mc.displayGuiScreen(new testscreen())));*/

        final SortingMode[] values = SortingMode.values();
        final Range<Integer> modeRange = Range.of(0, values.length - 1);

        this.altList.sortContents(SortingMode.values()[modeRange.clamp(Preferences.altManagerSortingIndex)].getAccountElementComparator().reversed());
        super.initGui();
    }

                                                     //not really blurable because blur looks terrible w it, but i cba to remove that...
    private final TriConsumer<Integer, Integer, Float> blurable = (mouseX, mouseY, partialTicks) -> {
        final TFontRenderer titleFont = vkDemibold;
        final String text = "Accounts";

        final float halfWidth = width/2F;
        final float halfListWidth = 160; //half of the alt list width
        float titlePos = halfWidth - halfListWidth; //these two are used to properly center the middle text ("x accounts | x unbanned/selected");
        float currentSessionPos;

        titleFont.drawString(text, titlePos, 0, -1, true);
        titlePos += titleFont.getStringWidthF(text);

        final String sessionInfo = "Logged in as " + GREEN + mc.session.getUsername();

        currentSessionPos = (halfWidth + halfListWidth - rubikR30.getStringWidthF(sessionInfo));

        final boolean hasSelectedAccounts = (selectedAccounts = altList.selectedAccounts.size()) > 0;

        final String accountStats = format("%s accounts | %s %s",
                altList.contents.size(),
                (hasSelectedAccounts ? selectedAccounts : altList.contents.stream().filter(acc -> !acc.getAccount().isBanned()).count()),
                (hasSelectedAccounts ? "selected" : "unbanned"));

                                                                        //0.01F is the bypass value. Default width (using larger font size) of the account stats string width.
        float statsWidth = rubikR30.getStringWidthF(accountStats)/2F + 0.01F;

        final boolean shouldUseSmallFont = currentSessionPos - statsWidth < titlePos + 65; //true when the "Accounts  x accounts | x unbanned  Logged in as x" strings overlap

        final float listEndPos = halfWidth + halfListWidth; //the X pos where the alt list ends (is cropped)
        final float stringsY = 10; //shortcut for easier modifying of the Y position
        if(shouldUseSmallFont) { //draw the latter strings with a smaller font to prevent overlap
            rubikR27.drawString(sessionInfo, currentSessionPos = listEndPos - rubikR27.getStringWidthF(sessionInfo), stringsY, -1, true);
            statsWidth = rubikR27.getStringWidthF(accountStats)/2f + 0.01f; //the previous width value is incorrect as it was computed with a larget font size. Redefine it.

                                              //center the account stats between the strings correctly (instead of centering it against X axis)
            rubikR27.drawString(accountStats, ((currentSessionPos - (currentSessionPos - titlePos) / 2F) - statsWidth), stringsY, -1, true);
        } else {
            rubikR30.drawString(sessionInfo, currentSessionPos = listEndPos - rubikR30.getStringWidthF(sessionInfo), stringsY, -1, true);

                                              //center the account stats between the strings correctly (instead of centering it against X axis)
            rubikR30.drawString(accountStats, ((currentSessionPos - (currentSessionPos - titlePos)/2F) - statsWidth), stringsY, -1, true);
        }
        altList.render(mouseX, mouseY, partialTicks, mc.currentScreen == this); //render the alt list itself.
    };

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground(mc.currentScreen == this);
        super.drawScreen(mouseX, mouseY, partialTicks);
        blurable.accept(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        if((previousAltCount != getBlueZenith().getAccountRepository().getAccounts().size() //re-sort list if alt has been added or removed
                || sortTimer.hasTimeReached(100))) { //or if .1s has passed

            this.altList.sortContents(SortingMode.values()[sortingMode.getCurrentIndex()].getAccountElementComparator().reversed());
            previousAltCount = this.altList.contents.size();
            sortTimer.reset();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(mouseButton == 3) mc.displayGuiScreen(null);
        Preferences.altManagerSortingIndex = sortingMode.getCurrentIndex(); // save index to a variable to not reset sorting upon switching screens
        this.altList.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button instanceof BorderlessCheckbox || button instanceof BorderlessCombobox) {
            altList.onGuiInit();
        }
        button.runClickCallback();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        altList.keyTyped(keyCode, typedChar);

        if(keyCode == KEY_DELETE)
            removeAccounts(true);

        if(keyCode == KEY_LCONTROL || keyCode == KEY_V) {
            if(isKeyDown(KEY_LCONTROL) && isKeyDown(KEY_V)) {
                try {
                   final String clipboardContent = getDefaultToolkit().getSystemClipboard().getData(stringFlavor).toString();
                   if(!clipboardContent.contains(":") || !clipboardContent.contains("@") || !clipboardContent.contains(".")) return;

                   if(clipboardContent.contains("\n")) {
                       handleAltList(clipboardContent);
                   } else {
                       handleSingleAlt(clipboardContent);
                   }

                } catch (UnsupportedFlavorException ignored) {}
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void onGuiClosed() {
        Preferences.altManagerSortingIndex = sortingMode.getCurrentIndex();
    }

    public void loginWithAccount(AccountElement accountElement) {
        if(accountElement.getAccount().equals(getBlueZenith().getAccountRepository().getCurrentAccount())) {
            getBlueZenith().getNotificationPublisher().postError("Account Manager", "Already logged into this account!", 2500);
            return;
        }
        getBlueZenith().getAccountRepository().setOngoingLogin(accountElement.getAccount(), supplyAsync(() -> {
            try {
                final AccountInfo account = accountElement.getAccount();

                if(account.isOffline() && account.getAccountType() == AccountType.OFFLINE) { //assume a cracked account
                    final Session crackedSession = new Session(account.getEffectiveUsername(), "", "", "mojang");
                    return new AccountLogin(crackedSession, account);
                }
                else if(account.getAccountType() == AccountType.MICROSOFT) {
                    final Session microsoftSession = new Session(account.getEffectiveUsername(), account.getUUID(), account.getMsAccessToken(),  "mojang");
                    return new AccountLogin(microsoftSession, account);
                }
                else {  //not cracked
                    final Session session = createSession(account.getEmail(), account.getPassword());
                    return new AccountLogin(session, account);
                }
            } catch (Exception exception) {
                if(!(exception instanceof InvalidCredentialsException))
                   exception.printStackTrace();
                throw new RuntimeException(exception);
            }
        }, BlueZenith.scheduledExecutorService));
    }

    private void handleAltList(String content) {
        for (String s : content.split("\n")) {
            handleSingleAlt(s);
        }
    }

    private void handleSingleAlt(String content) {
        final String[] combo = content.split(":");
        if(combo.length != 2) return; //need email:pass combo

        final String email = combo[0]; //email goes first
        if(!emailPattern.matcher(email).find()) return; //not a valid email
        final String password = combo[1]; //password goes second

        final AccountInfo newAccount = new AccountInfo(email, password);
        if(getBlueZenith().getAccountRepository().getAccounts().contains(newAccount)) {
            getBlueZenith().getNotificationPublisher().postError("Account Manager", "This account is already added!", 2500);
            return;
        }
        getBlueZenith().getAccountRepository().addAccount(newAccount);
    }

    private void removeAccounts(boolean onlySelected) { //if false, remove ALL accounts
        if(onlySelected) {
            removeSelectedAccounts();
        } else {
            final AccountElement[] toRemove = altList.contents.toArray(new AccountElement[0]);
            for (AccountElement element : toRemove) {
                removeAccount(element.getAccount());
            }
        }
        altList.onGuiInit();
        altList.singleSelectedAccount = null;
    }

    public void removeAccount(AccountInfo toRemove) {
        altList.contents.removeIf(element -> element.getAccount() == toRemove);
        getBlueZenith().getAccountRepository().removeAccount(toRemove);
    }

    private void removeSelectedAccounts() {
        selectedAccounts = altList.selectedAccounts.size();
        if (selectedAccounts > 0) {
            for (AccountElement acc : altList.selectedAccounts) {
                altList.contents.remove(acc);
                getBlueZenith().getAccountRepository().removeAccount(acc.getAccount());
                selectedAccounts--;
            }
            altList.selectedAccounts.clear();
        } else if (altList.singleSelectedAccount != null) {
            altList.contents.remove(altList.singleSelectedAccount);
            getBlueZenith().getAccountRepository().removeAccount(altList.singleSelectedAccount.getAccount());
        }
    }

    private enum SortingMode {
        TIME_ADDED("Time added (new to old)", (acc1, acc2) -> Long.compare(acc1.getAccount().timeAdded, acc2.getAccount().timeAdded)),
        TIME_ADDED_REVERSED("Time added (old to new)", TIME_ADDED.getAccountElementComparator().reversed()),
        UNBANNED_FIRST("Unbanned first", (acc1, acc2) -> {
            if(acc1.getAccount().isBanned() && acc2.getAccount().isBanned() && !acc1.getAccount().isOffline() && !acc2.getAccount().isOffline()) {
                return Long.compare(-acc1.getAccount().getHypixelInfo().getTimeLeftUntilUnban(), -acc2.getAccount().getHypixelInfo().getTimeLeftUntilUnban());
            } else return Boolean.compare(!acc1.getAccount().isBanned(), !acc2.getAccount().isBanned());
        }),
        PREMIUM_FIRST("Premium first", (acc1, acc2) ->
                Boolean.compare(acc1.getAccount().getAccountType() != AccountType.OFFLINE, acc2.getAccount().getAccountType() != AccountType.OFFLINE)
        ),
        HIGHEST_PLAYTIME("Highest playtime", (acc1, acc2) -> {
            final HypixelInfo first = acc1.getAccount().getHypixelInfo(),
                              second = acc2.getAccount().getHypixelInfo();

            return Long.compare(first == null ? 0 : first.getPlaytime(), second == null ? 0 : second.getPlaytime());
        }),
        FAVORITES_FIRST("Favorites first", (acc1, acc2) -> {
            if(acc1.getAccount().isFavorite || acc2.getAccount().isFavorite) {
                return Boolean.compare(acc1.getAccount().isFavorite, acc2.getAccount().isFavorite);
            } else {
                return TIME_ADDED.getAccountElementComparator().compare(acc1, acc2);
            }
        });

        private final String name;
        private final Comparator<AccountElement> accountElementComparator;

        SortingMode(String name, Comparator<AccountElement> accountElementComparator) {
            this.name = name;
            this.accountElementComparator = accountElementComparator;
        }

        public Comparator<AccountElement> getAccountElementComparator() {
            return this.accountElementComparator;
        }

        public String getName() {
            return this.name;
        }
    }
}
