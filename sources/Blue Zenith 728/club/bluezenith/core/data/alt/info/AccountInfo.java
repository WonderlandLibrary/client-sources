package club.bluezenith.core.data.alt.info;

import club.bluezenith.ui.alt.rewrite.AccountElement;
import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.util.Session;

import java.util.Objects;

import static java.lang.Math.min;
import static java.lang.System.currentTimeMillis;

public class AccountInfo {
    private Session associatedSession;
    private AccountElement renderedElement;
    private AccountType accountType;

    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose                         //IMPORTANT: only PRIMITIVE types can be serialized in this, if you want to save a non-primitive type you'll need to add
    @SerializedName("password")     //if you want to save a non-primitive type you'll need to add your own serializer/deserializer to AccountSerializer (like i did with HypixelInfo)
    private String password;
    @Expose
    @SerializedName("ms_access_token")
    private String msAccessToken;
    @Expose
    @SerializedName("ms_refresh_token")
    private String msRefreshToken;
    @Expose
    @SerializedName("uuid")
    private String uuid;
    @Expose
    @SerializedName("isInvalidCredentials")
    private boolean isInvalidCredentials;

    private String censoredPassword;
    private boolean isDefaultSession;

    @Expose
    @SerializedName("hypixelInfo")
    private HypixelInfo hypixelInfo;

    @Expose
    @SerializedName("isFavorite")
    public boolean isFavorite;

    @Expose
    @SerializedName("timeAdded")
    public long timeAdded = currentTimeMillis();

    @Expose
    @SerializedName("expiresAt")
    private long expiresAt = -1;

    public AccountInfo(Session session) { //used only upon startup to correctly get the current account for further actions
        this.associatedSession = session;
        this.username = session.getUsername();
        this.isDefaultSession = true;
        this.accountType = AccountType.MOJANG;
    }

    public AccountInfo(String username) {
        this.username = username;
        this.accountType = AccountType.OFFLINE;
    }

    public AccountInfo(String username, String uuid, String accessToken, String refreshToken, long expiresIn) {
        this.username = username;
        this.uuid = uuid;
        this.msAccessToken = accessToken;
        this.msRefreshToken = refreshToken;
        this.accountType = AccountType.MICROSOFT;
        this.hypixelInfo = new HypixelInfo(this);
        this.expiresAt = currentTimeMillis() + ((expiresIn - 10) * 1000);
    }

    public AccountInfo(String email, String password) {
        if(email == null || password == null) throw new IllegalArgumentException("Null argument provided when creating a non-cracked alt.");
        this.email = email;
        this.password = password;
        this.hypixelInfo = new HypixelInfo(this);
        this.accountType = AccountType.MOJANG;

        this.makeCensoredPassword();
    }

    public void makeCensoredPassword() { //should be used only in constructor & deserializer
        if(this.password == null && this.uuid == null) {
            this.accountType = AccountType.OFFLINE;
            return;
        } else if(this.uuid == null) this.accountType = AccountType.MOJANG;
        else this.accountType = AccountType.MICROSOFT;
        final StringBuilder censoredBuilder = new StringBuilder();

        for (int i = 0; i < min(this.password != null ? this.password.length() : 0, 16); i++) {
            censoredBuilder.append('*');
        }

        this.censoredPassword = censoredBuilder.toString();
    }

    public String getMsAccessToken() {
        return msAccessToken;
    }

    public String getMsRefreshToken() {
        return msRefreshToken;
    }

    public String getUUID() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPassword(String newPassword) {
        if(newPassword == null) this.accountType = AccountType.OFFLINE;
        this.password = newPassword;
    }

    public void setInvalidCredentials(boolean isInvalidCredentials) {
        this.isInvalidCredentials = isInvalidCredentials;
    }

    public String getEffectiveUsername() {
        return Strings.isNullOrEmpty(getUsername()) ? "Unknown" : getUsername();
    }

    public String getPassword() {
        return password;
    }

    public String getCensoredPassword() {
        return censoredPassword;
    }

    public long getTimeUntilExpires() {
        if(accountType == AccountType.MICROSOFT && expiresAt != -1)
         return expiresAt - currentTimeMillis();
        else return 0;
    }

    public boolean isDefaultSession() {
        return isDefaultSession;
    }

    public boolean isOffline() {
        return getPassword() == null; //offline accounts do not have passwords
    }

    public boolean isBanned() { //mark as 'banned' if the account is cracked (no hypixel info) or if it's premium & banned.
        return getHypixelInfo() == null || getHypixelInfo().isBanned();
    }

    public boolean isInvalidCredentials() {
        return this.isInvalidCredentials;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public synchronized HypixelInfo getHypixelInfo() {
        return hypixelInfo;
    }

    public synchronized HypixelInfo createInfo() { //used when converting cracked to premium acc
        if(this.hypixelInfo == null)
        return this.hypixelInfo = new HypixelInfo(this);
        else return this.hypixelInfo;
    }

    public AccountInfo appendSession(Session session) {
        if(this.associatedSession == null) this.associatedSession = session;
        return this;
    }

    public AccountInfo createElement() {
        this.renderedElement = new AccountElement(this);
        return this;
    }

    public AccountElement getRenderedElement() {
        return this.renderedElement;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "associatedSession=" + associatedSession +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", censoredPassword='" + censoredPassword + '\'' +
                ", isDefaultSession=" + isDefaultSession +
                ", hypixelInfo=" + hypixelInfo +
                "}\n\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountInfo that = (AccountInfo) o;
        if(that.associatedSession != null && this.associatedSession != null)
            return Objects.equals(that.associatedSession.getUsername(), this.associatedSession.getUsername());
        if(that.password == null)
            return Objects.equals(that.username, this.username);
        return Objects.equals(that.email, this.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    public void setMicrosoftTokens(String msAccessToken, String msRefreshToken, long expiresIn) {
        this.msAccessToken = msAccessToken;
        this.msRefreshToken = msRefreshToken;
        this.expiresAt = System.currentTimeMillis() + ((expiresIn - 10) * 1000);
    }
}
