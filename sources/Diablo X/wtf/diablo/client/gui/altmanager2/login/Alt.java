package wtf.diablo.client.gui.altmanager2.login;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Alt {

    // Data Stored as <Server IP, Unban UNIX Timestamp>
    private final HashMap<String, Long> banData = new HashMap<>();
    private String email;
    private String password;
    private String displayName;
    private String uuid = "";
    private boolean invalid;
    private AccountType accountType;
    private boolean pinned = false;

    public Alt(String email, String password, AccountType accountType) {
        this.email = email;
        this.displayName = email;
        this.password = password;
        if (this.password.equals("")) {
            this.accountType = AccountType.CRACKED;
        } else {
            this.accountType = accountType;
        }
    }

    public static Alt getAltFromData(JSONObject altData) {
        String username = altData.getString("username");
        String email = new String(Base64.getDecoder().decode(altData.getString("email")), StandardCharsets.UTF_8);
        String password = new String(Base64.getDecoder().decode(altData.getString("password")), StandardCharsets.UTF_8);
        String uuid = altData.getString("uuid");
        AccountType altType = altData.getEnum(AccountType.class, "type");
        boolean pinned = altData.getBoolean("pinned");
        Alt alt = new Alt(email, password, altType);
        alt.setPinned(pinned);
        alt.setDisplayName(username);
        alt.setUUID(uuid);
        Map<String, Object> bans = altData.getJSONObject("bans").toMap();
        bans.forEach((key, value) -> alt.updateBan(key, (Long) value - System.currentTimeMillis()));

        return alt;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.displayName == null ? this.email : this.displayName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        if (password.isEmpty()) {
            this.accountType = AccountType.CRACKED;
            this.displayName = null;
            this.password = null;
        } else {
            this.password = password;
        }
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isBanned() {
        return !this.banData.isEmpty();
    }


    public void updateBan(String server, long banLength) {
        if (banLength <= 0) {
            this.banData.remove(server);
            return;
        }
        this.banData.put(server, System.currentTimeMillis() + banLength);
    }

    public HashMap<String, Long> getBanData() {
        return banData;
    }

    public void login() {
        new AccountLoginThread(this).start();
    }

    public boolean isInvalid() {
        return this.invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public boolean isPinned() {
        return this.pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public JSONObject getAltData() {
        JSONObject json = new JSONObject();
        json.put("username", this.displayName);
        json.put("email", Base64.getEncoder().encodeToString(this.email.getBytes(StandardCharsets.UTF_8)));
        json.put("password", Base64.getEncoder().encodeToString(this.password.getBytes(StandardCharsets.UTF_8)));
        json.put("type", this.accountType);
        json.put("pinned", this.pinned);
        json.put("bans", this.banData);
        json.put("uuid", this.uuid);
        return json;
    }

    public String getUUID() {
        return this.uuid;
    }

    void setUUID(String uuid) {
        this.uuid = uuid;
    }
}
