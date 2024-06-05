package ClassSub;

import java.util.*;
import java.net.*;
import org.jetbrains.annotations.*;
import net.minecraft.util.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import net.minecraft.client.*;
import java.awt.*;
import org.lwjgl.input.*;

public class Class170
{
    private static final Random RANDOM;
    
    
    public static int random(final int n, final int n2) {
        if (n2 <= n) {
            return n;
        }
        return Class170.RANDOM.nextInt(n2 - n) + n;
    }
    
    public static Session createSession(final String username, final String password, @NotNull final Proxy proxy) throws Exception {
        final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(proxy, "").createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(username);
        yggdrasilUserAuthentication.setPassword(password);
        yggdrasilUserAuthentication.logIn();
        return new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
    }
    
    public static double getDirection() {
        final Minecraft getMinecraft = Minecraft.getMinecraft();
        float rotationYaw = getMinecraft.thePlayer.rotationYaw;
        if (getMinecraft.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (getMinecraft.thePlayer.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (getMinecraft.thePlayer.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (getMinecraft.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (getMinecraft.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return Math.toRadians(rotationYaw);
    }
    
    public static Point calculateMouseLocation() {
        final Minecraft getMinecraft = Minecraft.getMinecraft();
        int guiScale = getMinecraft.gameSettings.guiScale;
        if (guiScale == 0) {
            guiScale = 1000;
        }
        int n;
        for (n = 0; n < guiScale && getMinecraft.displayWidth / (n + 1) >= 320 && getMinecraft.displayHeight / (n + 1) >= 240; ++n) {}
        return new Point(Mouse.getX() / n, getMinecraft.displayHeight / n - Mouse.getY() / n - 1);
    }
    
    static {
        RANDOM = new Random();
    }
}
