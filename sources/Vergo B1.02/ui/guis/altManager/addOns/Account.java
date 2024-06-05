package xyz.vergoclient.ui.guis.altManager.addOns;

import com.google.gson.JsonObject;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.UserType;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.net.Proxy;
import java.util.UUID;

public final class Account {

    private String username;

    private final String email;
    private final String password;

    private AccountType type;

    private long unbanTime;
    private boolean working;

    Account(final String username, final String email,
            final String password, final AccountType type,
            final long unbanTime, final boolean working) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
        this.working = working;
        this.unbanTime = unbanTime;
    }

    public void createLoginThread() {
        final Thread altLoginThread = new Thread(() -> {
            // TODO :: Notifications

            if (this.getType() != AccountType.OFFLINE) {
                final UserAuthentication authentication = new YggdrasilAuthenticationService(
                        Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);

                authentication.setUsername(this.getEmail());
                authentication.setPassword(this.getPassword());

                try {
                    authentication.logIn();
                } catch (final AuthenticationException e) {

                    if (this.getUsername().length() == 0) {
                        this.setUsername("invalid_account");
                    }

                    this.setWorking(false);
                    return;
                }


                final String username = authentication.getSelectedProfile().getName();
                this.setUsername(username);
                this.setWorking(true);

                Minecraft.getMinecraft().setSession(new Session(username,
                        UUIDTypeAdapter.fromUUID(authentication.getSelectedProfile().getId()),
                        authentication.getAuthenticatedToken(),
                        authentication.getUserType().getName()));
            } else {
                this.setUsername(this.getEmail());

                final GameProfile profile = new GameProfile(UUID.randomUUID(), this.getUsername());

                Minecraft.getMinecraft().setSession(new Session(profile.getName(),
                        UUIDTypeAdapter.fromUUID(profile.getId()),
                        "", UserType.MOJANG.getName()));
            }
        });

        altLoginThread.start();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AccountType getType() {
        return type;
    }

    public long getUnbanDate() {
        return unbanTime;
    }

    public void setUnbanDate(final long unbanDate) {
        this.unbanTime = unbanDate;
    }

    public boolean isBanned() {
        return this.unbanTime == -1337 || System.currentTimeMillis() < this.unbanTime;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public JsonObject save() {
        final JsonObject object = new JsonObject();

        object.addProperty("email", this.email);
        object.addProperty("password", this.password);

        if (this.username != null)
            object.addProperty("username", this.username);

        object.addProperty("valid", this.working);
        object.addProperty("banned_until", this.unbanTime);

        return object;
    }

    public static Account from(JsonObject object) {
        final AccountBuilder builder = new AccountBuilder();

        if (object.has("email"))
            builder.email(object.get("email").getAsString());

        if (object.has("password"))
            builder.password(object.get("password").getAsString());

        if (object.has("username"))
            builder.username(object.get("username").getAsString());

        if (object.has("valid"))
            builder.working(object.get("valid").getAsBoolean());

        if (object.has("banned_until"))
            builder.unbanTime(object.get("banned_until").getAsLong());

        return builder.build();
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public static class AccountBuilder {
        private String username = "", email, password = "";
        private AccountType type = AccountType.MOJANG;
        private long unbanTime;
        private boolean working;

        private AccountBuilder() {
        }

        public AccountBuilder username(final String username) {
            this.username = username;

            return this;
        }

        public AccountBuilder email(final String email) {
            this.email = email;

            return this;
        }

        public AccountBuilder password(final String password) {
            this.password = password;

            return this;
        }

        public AccountBuilder unbanTime(final long unbanTime) {
            this.unbanTime = unbanTime;

            return this;
        }

        public AccountBuilder working(final boolean working) {
            this.working = working;

            return this;
        }

        public Account build() {
            if (this.password.length() == 0) {
                this.type = AccountType.OFFLINE;
            }

            return new Account(username, email, password, type, unbanTime, working);
        }
    }
}
