package cc.slack.ui.altmanager.gui;

import cc.slack.ui.alt.GuiAltLogin;
import cc.slack.ui.altmanager.AccountManager;
import cc.slack.ui.altmanager.auth.Account;
import cc.slack.ui.altmanager.auth.MicrosoftAuth;
import cc.slack.ui.altmanager.auth.SessionManager;
import cc.slack.ui.altmanager.utils.Notification;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class GuiAccountManager extends GuiScreen {
  private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  private final GuiScreen previousScreen;

  private GuiButton loginButton = null;
  private GuiButton deleteButton = null;
  private GuiButton cancelButton = null;
  private GuiAccountList guiAccountList = null;
  private Notification notification = null;
  private int selectedAccount = -1;
  private ExecutorService executor = null;
  private CompletableFuture<Void> task = null;

  public GuiAccountManager(GuiScreen previousScreen) {
    this.previousScreen = previousScreen;
  }

  public GuiAccountManager(GuiScreen previousScreen, Notification notification) {
    this.previousScreen = previousScreen;
    this.notification = notification;
  }

  @Override
  public void initGui() {
    AccountManager.load();
    Keyboard.enableRepeatEvents(true);

    buttonList.clear();
    buttonList.add(loginButton = new GuiButton(0, width / 2 - 150 - 4, height - 52, 150, 20, "Login"));
    buttonList.add(new GuiButton(1, width / 2 + 4, height - 52, 150, 20, "Add (Microsoft)"));
    buttonList.add(new GuiButton(4, width / 2 + 160, height - 52, 150, 20, "Add (Cracked)"));
    buttonList.add(new GuiButton(5, width / 2 + 160, height - 28, 150, 20, "Add (Session)"));
    buttonList.add(deleteButton = new GuiButton(2, width / 2 - 150 - 4, height - 28, 150, 20, "Delete"));
    buttonList.add(cancelButton = new GuiButton(3, width / 2 + 4, height - 28, 150, 20, "Cancel"));

    guiAccountList = new GuiAccountList(mc);
    guiAccountList.registerScrollButtons(11, 12);

    updateScreen();
  }

  @Override
  public void onGuiClosed() {
    Keyboard.enableRepeatEvents(false);

    if (task != null && !task.isDone()) {
      task.cancel(true);
      executor.shutdownNow();
    }
  }

  @Override
  public void updateScreen() {
    if (loginButton != null && deleteButton != null) {
      loginButton.enabled = deleteButton.enabled = selectedAccount >= 0;
      if (task != null && !task.isDone()) {
        loginButton.enabled = false;
      }
    }
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
    if (guiAccountList != null) {
      guiAccountList.drawScreen(mouseX, mouseY, renderPartialTicks);
    }
    super.drawScreen(mouseX, mouseY, renderPartialTicks);

    drawCenteredString(
      fontRendererObj,
      String.format(
        "§rAccount Manager §8(§7%s§8)§r", AccountManager.size()
      ),
      width / 2, 20, -1
    );

    String text = String.format(
      "§7Username: §3%s§r", SessionManager.getSession().getUsername()
    );
    mc.currentScreen.drawString(this.fontRendererObj, text, 3, 3, -1);

    if (notification != null && !notification.isExpired()) {
      String notificationText = notification.getMessage();
      Gui.drawRect(
        mc.currentScreen.width / 2 - this.fontRendererObj.getStringWidth(notificationText) / 2 - 3, 4,
        mc.currentScreen.width / 2 + this.fontRendererObj.getStringWidth(notificationText) / 2 + 3, 4 + 3 + this.fontRendererObj.FONT_HEIGHT + 2,
        0x64000000
      );
      mc.currentScreen.drawCenteredString(
              this.fontRendererObj, notification.getMessage(),
        mc.currentScreen.width / 2, 4 + 3, -1
      );
    }
  }

  @Override
  public void handleMouseInput() throws IOException {
    if (guiAccountList != null) {
      guiAccountList.handleMouseInput();
    }
    super.handleMouseInput();
  }

  @Override
  protected void keyTyped(char typedChar, int keyCode) {
    switch (keyCode) {
      case Keyboard.KEY_UP: {
        if (selectedAccount > 0) {
          --selectedAccount;
          if (isCtrlKeyDown()) {
            AccountManager.swap(selectedAccount, selectedAccount + 1);
            AccountManager.save();
          }
        }
      }
      break;
      case Keyboard.KEY_DOWN: {
        if (selectedAccount < AccountManager.size() - 1) {
          ++selectedAccount;
          if (isCtrlKeyDown()) {
            AccountManager.swap(selectedAccount, selectedAccount - 1);
            AccountManager.save();
          }
        }
      }
      break;
      case Keyboard.KEY_RETURN: {
        actionPerformed(loginButton);
      }
      break;
      case Keyboard.KEY_DELETE: {
        actionPerformed(deleteButton);
      }
      break;
      case Keyboard.KEY_ESCAPE: {
        actionPerformed(cancelButton);
      }
      break;
    }

    if (isKeyComboCtrlC(keyCode) && selectedAccount >= 0) {
      setClipboardString(AccountManager.get(selectedAccount).getUsername());
    }
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    if (button == null) {
      return;
    }

    if (button.enabled) {
      switch (button.id) {
        case 0: { // Login
          if (task == null || task.isDone()) {
            if (executor == null) {
              executor = Executors.newSingleThreadExecutor();
            }
            Account account = AccountManager.get(selectedAccount);
            String username = StringUtils.isBlank(account.getUsername()) ? "???" : account.getUsername();
            AtomicReference<String> refreshToken = new AtomicReference<>("");
            AtomicReference<String> accessToken = new AtomicReference<>("");
            notification = new Notification(String.format(
              "§7Fetching your Minecraft profile... (%s)§r", username
            ), -1L);
            task = MicrosoftAuth.login(account.getAccessToken(), executor)
              .handle((session, error) -> {
                if (session != null) {
                  account.setUsername(session.getUsername());
                  AccountManager.save();
                  SessionManager.setSession(session);
                  notification = new Notification(String.format(
                    "§aSuccessful login! (%s)§r", account.getUsername()
                  ), 5000L);
                  return true;
                }
                return false;
              })
              .thenComposeAsync(completed -> {
                if (completed) {
                  throw new NoSuchElementException();
                }
                notification = new Notification(String.format(
                  "§7Refreshing Microsoft access tokens... (%s)§r", username
                ), -1L);
                return MicrosoftAuth.refreshMSAccessTokens(account.getRefreshToken(), executor);
              })
              .thenComposeAsync(msAccessTokens -> {
                notification = new Notification(String.format(
                  "§7Acquiring Xbox access token... (%s)§r", username
                ), -1L);
                refreshToken.set(msAccessTokens.get("refresh_token"));
                return MicrosoftAuth.acquireXboxAccessToken(msAccessTokens.get("access_token"), executor);
              })
              .thenComposeAsync(xboxAccessToken -> {
                notification = new Notification(String.format(
                  "§7Acquiring Xbox XSTS token... (%s)§r", username
                ), -1L);
                return MicrosoftAuth.acquireXboxXstsToken(xboxAccessToken, executor);
              })
              .thenComposeAsync(xboxXstsData -> {
                notification = new Notification(String.format(
                  "§7Acquiring Minecraft access token... (%s)§r", username
                ), -1L);
                return MicrosoftAuth.acquireMCAccessToken(
                  xboxXstsData.get("Token"), xboxXstsData.get("uhs"), executor
                );
              })
              .thenComposeAsync(mcToken -> {
                notification = new Notification(String.format(
                  "§7Fetching your Minecraft profile... (%s)§r", username
                ), -1L);
                accessToken.set(mcToken);
                return MicrosoftAuth.login(mcToken, executor);
              })
              .thenAccept(session -> {
                account.setRefreshToken(refreshToken.get());
                account.setAccessToken(accessToken.get());
                account.setUsername(session.getUsername());
                account.setTimestamp(System.currentTimeMillis());
                AccountManager.save();
                SessionManager.setSession(session);
                notification = new Notification(String.format(
                  "§aSuccessful login! (%s)§r", account.getUsername()
                ), 5000L);
              })
              .exceptionally(error -> {
                if (!(error.getCause() instanceof NoSuchElementException)) {
                  notification = new Notification(String.format(
                    "§c%s (%s)&r", error.getMessage(), username
                  ), 5000L);
                }
                return null;
              });
          }
        }
        break;
        case 1: { // Add
          mc.displayGuiScreen(new GuiMicrosoftAuth(previousScreen));
        }
        break;
        case 2: { // Delete
          AccountManager.remove(selectedAccount--);
          AccountManager.save();
          updateScreen();
        }
        break;
        case 3: { // Cancel
          mc.displayGuiScreen(previousScreen);
        }
        break;
        case 4: {
          mc.displayGuiScreen(new GuiAltLogin(this));
        }
        break;
        case 5: {
          mc.displayGuiScreen(new GuiSessionLogin(this));
        }
        break;
        default: {
          guiAccountList.actionPerformed(button);
        }
      }
    }
  }

  class GuiAccountList extends GuiSlot {
    public GuiAccountList(Minecraft mc) {
      super(
        mc, GuiAccountManager.this.width, GuiAccountManager.this.height,
        32, GuiAccountManager.this.height - 64, 27
      );
    }

    @Override
    protected int getSize() {
      return AccountManager.size();
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
      GuiAccountManager.this.selectedAccount = slotIndex;
      GuiAccountManager.this.updateScreen();
      if (isDoubleClick) {
        GuiAccountManager.this.actionPerformed(loginButton);
      }
    }

    @Override
    protected boolean isSelected(int slotIndex) {
      return slotIndex == GuiAccountManager.this.selectedAccount;
    }

    @Override
    protected int getContentHeight() {
      return AccountManager.size() * 27;
    }

    @Override
    protected void drawBackground() {
      GuiAccountManager.this.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int entryID, int x, int y, int k, int mouseXIn, int mouseYIn) {
      Account account = AccountManager.get(entryID);

      String username = StringUtils.isBlank(account.getUsername()) ? "???" : account.getUsername();
      String text = String.format(
        "%s%s§r",
        SessionManager.getSession().getUsername().equals(username) ? "§a§l" : "§f",
        username
      );
      GuiAccountManager.this.drawString(
        GuiAccountManager.this.fontRendererObj, text,
        x + 2, y + 2, -1
      );

      String time = String.format(
        "§8§o%s§r", sdf.format(new Date(account.getTimestamp()))
      );
      GuiAccountManager.this.drawString(
        GuiAccountManager.this.fontRendererObj, time,
        x + 2, y + 13, -1
      );
    }
  }
}
