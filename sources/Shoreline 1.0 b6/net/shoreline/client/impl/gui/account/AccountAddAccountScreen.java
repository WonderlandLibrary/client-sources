package net.shoreline.client.impl.gui.account;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;
import net.shoreline.client.Shoreline;
import net.shoreline.client.api.account.msa.exception.MSAAuthException;
import net.shoreline.client.api.account.type.MinecraftAccount;
import net.shoreline.client.api.account.type.impl.CrackedAccount;
import net.shoreline.client.api.account.type.impl.MicrosoftAccount;
import net.shoreline.client.impl.manager.client.AccountManager;
import net.shoreline.client.init.Managers;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

/**
 * @author xgraza
 * @since 03/28/24
 */
public final class AccountAddAccountScreen extends Screen
{
    private final Screen parent;
    private TextFieldWidget email, password;

    public AccountAddAccountScreen(final Screen parent)
    {
        super(Text.of("Add or Create an Alt Account"));
        this.parent = parent;
    }

    @Override
    protected void init()
    {
        clearChildren();
        addDrawableChild(email = new TextFieldWidget(client.textRenderer, width / 2 - 75, height / 2 - 30, 150, 20, Text.of("")));
        email.setPlaceholder(Text.of("Email or Username..."));
        addDrawableChild(password = new TextFieldWidget(client.textRenderer, width / 2 - 75, height / 2 - 5, 150, 20, Text.of("")));
        password.setPlaceholder(Text.of("Password (Optional)"));

        addDrawableChild(ButtonWidget.builder(Text.of("Add"), (action) ->
        {
            final String accountEmail = email.getText();
            if (accountEmail.length() >= 3)
            {
                final String accountPassword = password.getText();
                MinecraftAccount account;
                if (!accountPassword.isEmpty())
                {
                    account = new MicrosoftAccount(accountEmail, accountPassword);
                }
                else
                {
                    account = new CrackedAccount(accountEmail);
                }

                // Add account to alt manager & display the parent screen
                Managers.ACCOUNT.register(account);
                client.setScreen(parent);
            }
        }).dimensions(width / 2 - 72, height / 2 + 20, 145, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Browser..."), (action) ->
        {
            try
            {
                AccountManager.MSA_AUTHENTICATOR.loginWithBrowser((token) ->
                        Shoreline.EXECUTOR.execute(() ->
                        {
                            final MicrosoftAccount account = new MicrosoftAccount(token);
                            final Session session = account.login();
                            if (session != null)
                            {
                                Managers.ACCOUNT.setSession(session);
                                Managers.ACCOUNT.register(account);
                                client.setScreen(parent);
                            }
                            else
                            {
                                AccountManager.MSA_AUTHENTICATOR.setLoginStage("Could not login to account");
                            }
                        }));
            }
            catch (IOException | URISyntaxException | MSAAuthException e)
            {
                e.printStackTrace();
            }
        }).dimensions(width / 2 - 72, height / 2 + 20 + 22, 145, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Go Back"), (action) -> client.setScreen(parent))
                .dimensions(width / 2 - 72, height / 2 + 20 + (22 * 2), 145, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        super.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(client.textRenderer, "*",
                email.getX() - 10,
                email.getY() + (email.getHeight() / 2) - (client.textRenderer.fontHeight / 2),
                (email.getText().length() >= 3 ? Color.green : Color.red).getRGB());
        context.drawCenteredTextWithShadow(client.textRenderer,
                "Add an Account", width / 2, height / 2 - 120, -1);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == GLFW_KEY_ESCAPE)
        {
            client.setScreen(parent);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
