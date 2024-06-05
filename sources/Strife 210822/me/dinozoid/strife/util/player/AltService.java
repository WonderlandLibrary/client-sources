package me.dinozoid.strife.util.player;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.api.TheAltening;
import com.thealtening.api.retriever.AsynchronousDataRetriever;
import com.thealtening.auth.service.AlteningServiceType;
import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.util.MinecraftUtil;
import net.minecraft.util.Session;

import java.net.Proxy;
import java.util.concurrent.CompletableFuture;

public class AltService extends MinecraftUtil {

	private static String loginStatus = "Log in";

	public static CompletableFuture<Boolean> login(String combo) {
		CompletableFuture<Boolean> toComplete = new CompletableFuture<>();
		String[] split = combo.split(":");
		return login(split[0], split.length == 2 ? split[1] : "");
	}

	public static CompletableFuture<Boolean> login(String email, String pass) {
		return login(AlteningServiceType.MOJANG, email, pass);
	}

	public static CompletableFuture<Boolean> login(final AlteningServiceType type, final String email, final String password) {
		CompletableFuture<Boolean> toComplete = new CompletableFuture<>();
		if(email == null) toComplete.complete(false);
		StrifeClient.INSTANCE.executorService().execute(() -> {
			loginStatus = "Logging in...";
			if(password == null || password.isEmpty() || password.trim().isEmpty()) {
				System.out.println(email + " " + password);
				mc.session = new Session(email, "", "", "legacy");
				loginStatus = "Logged in [Offline] (" + email + ")";
				toComplete.complete(true);
				return;
			}
			mc.altening().updateService(type);
			YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
			auth.setUsername(email);
			auth.setPassword(password);
			try {
				auth.logIn();
				GameProfile authedProfile = auth.getSelectedProfile();
				mc.session = new Session(authedProfile.getName(), authedProfile.getId().toString(), auth.getAuthenticatedToken(), auth.getUserType().getName());
				loginStatus = "Logged in (" + mc.getSession().getUsername() + ")";
				toComplete.complete(true);
			} catch (AuthenticationException e) {
				loginStatus = "Invalid Credentials.";
				toComplete.complete(false);
			}
		});
        return toComplete;
	}

    public static String[] generate(String token) {
	    String[] creds = new String[2];
        AsynchronousDataRetriever asynchronousDataRetriever = TheAltening.newAsyncRetriever(token);
        asynchronousDataRetriever.getAccountDataAsync().handle((input, exception) -> {
			creds[0] = input.getToken();
			creds[1] = input.getPassword();
			exception.printStackTrace();
			return null;
		});
        System.out.println(creds[0]);
        return creds;
    }

	public static String loginStatus() {
		return loginStatus;
	}

	public static void loginStatus(String loginStatus) {
		AltService.loginStatus = loginStatus;
	}

}