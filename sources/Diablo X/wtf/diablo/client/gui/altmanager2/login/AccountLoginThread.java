package wtf.diablo.client.gui.altmanager2.login;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationType;

public class AccountLoginThread extends Thread {

    private final Alt alt;
    private final String email, password;
    private final AccountType accountType;

    public AccountLoginThread(String email, String password, AccountType accountType) {
        this.alt = null;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public AccountLoginThread(Alt alt) {
        this.alt = alt;
        this.accountType = alt.getAccountType();
        this.email = "";
        this.password = "";
    }

    @Override
    public void run() {

        String email = this.alt == null ? this.email : this.alt.getEmail() == null ? this.alt.getUsername() : this.alt.getEmail();
        String password = this.alt == null ? this.password : this.alt.getPassword();
        switch (this.accountType) {
            /*case MOJANG:
                final YggdrasilUserAuthentication auth = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
                auth.setUsername(email);
                auth.setPassword(password);
                try {
                    auth.logIn();
                    Session loginSession = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
                    Diablo.INSTANCE.getMc().setSession(loginSession);
                    if (alt != null) {
                        alt.setDisplayName(loginSession.getUsername());
                        alt.setUUID(loginSession.getPlayerID());
                    }
                    Diablo.INSTANCE.getNotificationManager().addNotification(new Notification("Login", "Successfully logged in as " + auth.getSelectedProfile().getName() + "!", 5000, NotificationType.SUCCESS));
                } catch (InvalidCredentialsException exception) {
                    if (alt != null) {
                        Diablo.INSTANCE.getNotificationManager().addNotification(new Notification("Login Failed", "Invalid login!", 5000, NotificationType.ERROR));
                        alt.setInvalid(true);
                    }
                } catch (AuthenticationException ignored) {
                    Diablo.INSTANCE.getNotificationManager().addNotification(new Notification("Connection Failed", "Could not connect to authentication servers!", 5000, NotificationType.ERROR));
                }
                break;*/
            case CRACKED:
                Minecraft.getMinecraft().setSession(new Session(email, "", "", "mojang"));
                break;
            case MICROSOFT:
                MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                try {
                    MicrosoftAuthResult result = authenticator.loginWithCredentials(email, password);
                    //MicrosoftAuthResult result = authenticator.loginWithWebview();
                    Session loginSession = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "mojang");
                    Minecraft.getMinecraft().setSession(loginSession);
                    if (alt != null) {
                        alt.setDisplayName(loginSession.getUsername());
                        alt.setUUID(loginSession.getPlayerID());
                    }
                    Diablo.getInstance().getNotificationManager().addNotification(new Notification("Login",  "Successfully logged in as " + loginSession.getUsername() + "!", 5000, NotificationType.SUCCESS));
                } catch (MicrosoftAuthenticationException e) {
                    Diablo.getInstance().getNotificationManager().addNotification(new Notification("Login Failed", "Invalid login!", 5000, NotificationType.ERROR));
                    e.printStackTrace();
                }
                break;

        }
    }
}
