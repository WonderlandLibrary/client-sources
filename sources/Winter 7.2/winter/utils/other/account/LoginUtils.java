/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other.account;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.UserType;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginUtils {
    private static final Logger logger = LogManager.getLogger();
    private static String displayText;

    public static String getMessage() {
        return displayText;
    }

    public static String login(String email, String password) {
        YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(password);
        try {
            authentication.logIn();
            Minecraft.getMinecraft().session = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), UserType.MOJANG.getName());
            displayText = "";
        }
        catch (AuthenticationUnavailableException e2) {
            displayText = "Cannot contact authentication server!";
        }
        catch (AuthenticationException e2) {
            displayText = e2.getMessage().contains("Invalid username or password.") || e2.getMessage().toLowerCase().contains("account migrated") ? "Wrong password!" : "Cannot contact authentication server!";
            logger.error(e2.getMessage());
        }
        catch (NullPointerException e3) {
            displayText = "Wrong password!";
        }
        return displayText;
    }

    public static void changeCrackedName(String newName) {
        Minecraft.getMinecraft().session = new Session(newName, "", "", UserType.MOJANG.getName());
    }
}

