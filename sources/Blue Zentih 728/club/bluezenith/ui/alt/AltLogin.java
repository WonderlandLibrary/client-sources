package club.bluezenith.ui.alt;

import club.bluezenith.BlueZenith;
import club.bluezenith.util.MinecraftInstance;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import net.minecraft.util.Session;

import java.net.Proxy;
import java.util.function.Consumer;

public final class AltLogin {

    private static final YggdrasilAuthenticationService authService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");

    public static void login(String email, @Nullable String password, @Nullable Consumer<Session> onSuccess, @Nullable Consumer<Throwable> onFailure) {
        boolean cracked = password == null;

        if (!cracked) {
            BlueZenith.scheduledExecutorService.execute(() -> {
                try {
                    final Session session = createSession(email, password);
                    MinecraftInstance.mc.session = session;
                    if (onSuccess != null) onSuccess.accept(session);
                } catch (Exception exception) {
                    if(onFailure != null) {
                        onFailure.accept(exception);
                    }
                }
            });
        } else {
            final Session s = new Session(email, "", "", "mojang");
            MinecraftInstance.mc.session = s;
            onSuccess.accept(s);
        }
    }

    public static Session createSession(String email, @NotNull String password) throws AuthenticationException {
        final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication) authService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(password);
        authentication.logIn();
        return new Session(
                authentication.getSelectedProfile().getName(),
                authentication.getSelectedProfile().getId().toString(),
                authentication.getAuthenticatedToken(),
                "mojang"
        );
    }
}