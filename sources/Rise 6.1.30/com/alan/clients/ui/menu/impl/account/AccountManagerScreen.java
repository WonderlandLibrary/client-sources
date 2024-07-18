package com.alan.clients.ui.menu.impl.account;

import com.alan.clients.Client;
import com.alan.clients.ui.menu.component.button.MenuButton;
import com.alan.clients.ui.menu.component.button.impl.MenuTextButton;
import com.alan.clients.ui.menu.impl.account.display.AccountViewModel;
import com.alan.clients.ui.menu.impl.account.impl.AddAccountScreen;
import com.alan.clients.ui.menu.impl.main.MainMenu;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.account.Account;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.file.alt.AltManager;
import com.alan.clients.util.gui.ScrollUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.render.ScissorUtil;
import com.alan.clients.util.shader.RiseShaders;
import com.alan.clients.util.shader.base.ShaderRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.alan.clients.layer.Layers.*;

public class AccountManagerScreen extends GuiScreen implements Accessor {
    private static final AltManager ALT_MANAGER = Client.INSTANCE.getAltManager();

    /**
     * The runnables that are executed when the buttons are clicked.
     */
    private static final Runnable ADD_ACCOUNT_RUNNABLE = () -> mc.displayGuiScreen(new AddAccountScreen());
    //    private static final Runnable CANCEL_RUNNABLE = () -> mc.displayGuiScreen(new MainMenu());
    private static final Runnable CANCEL_RUNNABLE = () -> {
        mc.displayGuiScreen(new MainMenu());
    };

    /**
     * The runnable that is executed in the background.
     */
    private static final Runnable BACKGROUND_RUNNABLE = () -> {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        RenderUtil.rectangle(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Color.BLACK);
    };

    /**
     * The animations used in this screen.
     */
    private static final Animation SLIDE_ANIMATION = new Animation(Easing.EASE_OUT_CIRC, 350);

    /**
     * The buttons used in this screen.
     */
    private static final MenuButton[] MENU_BUTTONS = new MenuButton[2];

    /**
     * Keeps track of the account view models.
     */
    private static final List<AccountViewModel<?>> ACCOUNT_DISPLAY_LIST = new ArrayList<>();
    private static GuiScreen prevScreen;

    private boolean updateMarker;

    private static int screenWidth;
    private static int screenHeight;
    ScrollUtil scrollUtil = new ScrollUtil();
    private static int accountsInRow;

    public AccountManagerScreen(GuiScreen prevScreen) {
        AccountManagerScreen.prevScreen = prevScreen;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Renders the background.
        RiseShaders.MAIN_MENU_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, null);
        getLayer(BLUR).add(BACKGROUND_RUNNABLE);

        GL11.glPushMatrix();
        ScissorUtil.enable();

        // Run post shader things - somehow at least one runnable has to be added or second layer will break.
        getLayer(BLOOM).add(() -> GuiScreen.drawRect(0, 0, 0, 0, 0));

        // Draw the accounts.
        scrollUtil.onRender();
        scrollUtil.setMax(ACCOUNT_DISPLAY_LIST.isEmpty() ? 0 : (-((ACCOUNT_DISPLAY_LIST.size() % accountsInRow) + 1) *
                ACCOUNT_DISPLAY_LIST.get(0).getHeight() - 10));

        for (int i = 0; i < ACCOUNT_DISPLAY_LIST.size(); i++) {
            AccountViewModel<?> model = ACCOUNT_DISPLAY_LIST.get(i);
            model.setScroll(scrollUtil.getScroll());

            model.draw();
        }

        ScissorUtil.scissor(new ScaledResolution(mc), 0, 0, this.width, this.height - 48);
        ScissorUtil.disable();
        GL11.glPopMatrix();

        // Renders the buttons.
        for (MenuButton button : MENU_BUTTONS) {
            button.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void updateScreen() {
        if (updateMarker) {
            updateMarker = false;

            // Handle the account removal.
            List<AccountViewModel<?>> removables = new ArrayList<>();
            for (AccountViewModel<?> model : ACCOUNT_DISPLAY_LIST) {
                if (model.isRemovable()) {
                    ALT_MANAGER.getAccounts().remove(model.getAccount());
                    removables.add(model);
                }
            }

            ACCOUNT_DISPLAY_LIST.removeAll(removables);
            ALT_MANAGER.update();
            reorderViewModels();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        // Handle the button click actions.
        for (MenuButton button : MENU_BUTTONS) {
            if (MouseUtil.isHovered(button.getX(), button.getY(), button.getWidth(), button.getHeight(), mouseX, mouseY)) {
                button.runAction();
                return;
            }
        }

        // Handle the account click actions.
        for (AccountViewModel<?> model : ACCOUNT_DISPLAY_LIST) {
            if (model.mouseClicked(mouseX, mouseY, mouseButton)) {
                updateMarker = true;
                return;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {
        super.initGui();
        screenWidth = width;
        screenHeight = height;

        int buttons = MENU_BUTTONS.length;
        int buttonWidth = 100;
        int buttonHeight = 24;
        int buttonPadding = 5;

        // Calculate the button x position in case new buttons are added.
        int buttonX = width / 2 - ((buttons & 1) == 0 ?
                (buttonWidth + buttonPadding) * (int) (buttons / 2) - buttonPadding / 2 :
                (buttonWidth + buttonPadding) * (int) (buttons / 2) + buttonWidth / 2);

        // Initialize the buttons.
        MENU_BUTTONS[0] = new MenuTextButton(0, 0, 0, 0, ADD_ACCOUNT_RUNNABLE, "Add Account");
        MENU_BUTTONS[1] = new MenuTextButton(0, 0, 0, 0, CANCEL_RUNNABLE, "Cancel");

        // Set the button positions.
        for (MenuButton button : MENU_BUTTONS) {
            button.setX(buttonX);
            button.setY(height - buttonHeight - buttonPadding * 2);
            button.setWidth(buttonWidth);
            button.setHeight(buttonHeight);
            buttonX += buttonWidth + buttonPadding;
        }

        if (prevScreen instanceof AccountManagerScreen) {
            reorderViewModels();
            return;
        }

        ACCOUNT_DISPLAY_LIST.clear();
        ALT_MANAGER.load();
        List<Account> accounts = ALT_MANAGER.getAccounts();
        for (Account account : accounts) {
            addDisplay(account);
        }

        // Set the account positions.
        getLayer(REGULAR).add(BACKGROUND_RUNNABLE);
    }

    public static void addAccount(Account account) {
        addDisplay(account);
        ALT_MANAGER.getAccounts().add(account);
        ALT_MANAGER.update();
    }

    private static void addDisplay(Account account) {
        int accountWidth = 172;
        int accountHeight = 40;
        int accountPadding = 5;
        accountsInRow = Math.max(1, Math.min(3, (screenWidth - accountWidth / 3) / (accountWidth + accountPadding)));

        // Calculate the account x and y position.
        int accountY = 16 + (accountHeight + accountPadding) * (ACCOUNT_DISPLAY_LIST.size() / accountsInRow);
        int accountX = screenWidth / 2 - ((accountsInRow & 1) == 0 ?
                (accountWidth + accountPadding) * (int) (accountsInRow / 2) - accountPadding / 2 :
                (accountWidth + accountPadding) * (int) (accountsInRow / 2) + accountWidth / 2);

        accountX += (accountWidth + accountPadding) * (ACCOUNT_DISPLAY_LIST.size() % accountsInRow);

        // Add the account view model.
        AccountViewModel<?> viewModel = new AccountViewModel<>(account, accountX, accountY, accountWidth, accountHeight);
        viewModel.setScreenHeight(screenHeight);
        ACCOUNT_DISPLAY_LIST.add(viewModel);
    }

    private void reorderViewModels() {
        List<AccountViewModel<?>> accountViewModels = new ArrayList<>(ACCOUNT_DISPLAY_LIST);
        ACCOUNT_DISPLAY_LIST.clear();
        for (AccountViewModel<?> model : accountViewModels) {
            addDisplay(model.getAccount());
        }
    }

    @Override
    public void onResize(Minecraft mcIn, int p_175273_2_, int p_175273_3_) {
        prevScreen = this;
        super.onResize(mcIn, p_175273_2_, p_175273_3_);
    }

    @Override
    public void onGuiClosed() {
        ACCOUNT_DISPLAY_LIST.clear();
        Arrays.fill(MENU_BUTTONS, null);
    }
}
