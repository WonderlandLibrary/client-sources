package club.strifeclient.util.networking;

import club.strifeclient.util.callback.VariableCallback;
import club.strifeclient.util.misc.MinecraftUtil;
import club.strifeclient.util.misc.TaskUtil;
import me.ratsiel.auth.model.mojang.MinecraftAuthenticator;
import me.ratsiel.auth.model.mojang.MinecraftToken;
import me.ratsiel.auth.model.mojang.profile.MinecraftProfile;
import net.minecraft.util.Session;

import java.util.ArrayList;
import java.util.List;

public final class AccountUtil extends MinecraftUtil {

    private static final List<VariableCallback<Boolean>> loginCallbacks = new ArrayList<>();

    public static void addLoginCallback(VariableCallback<Boolean> callback) {
        if (!loginCallbacks.contains(callback))
            loginCallbacks.add(callback);
    }
    private static void runCallbacks(boolean login) {
        loginCallbacks.forEach(callback -> callback.callback(login));
    }

    public static void login(final String combo) throws Error {
        String[] args = combo.split(":");
        if (args.length > 1)
            login(args[0], args[1]);
        else throw new Error("Missing email/password");
    }

    public static void login(final String email, final String password) {
        TaskUtil.getExecutorService().execute(() -> {
            if (password == null || password.isEmpty() || password.trim().isEmpty()) {
                System.out.println(email + " " + password);
                mc.session = new Session(email, "", "", "legacy");
                runCallbacks(true);
                return;
            }
            try {
                final MinecraftAuthenticator minecraftAuthenticator = new MinecraftAuthenticator();
                final MinecraftToken minecraftToken = minecraftAuthenticator.loginWithXbox(email, password);
                final MinecraftProfile minecraftProfile = minecraftAuthenticator.checkOwnership(minecraftToken);
                mc.session = new Session(minecraftProfile.getUsername(), minecraftProfile.getUuid().toString(), minecraftToken.getAccessToken(), "mojang");
                runCallbacks(true);
            } catch (Exception e) {
                e.printStackTrace();
                runCallbacks(false);
            }
        });
    }
}
