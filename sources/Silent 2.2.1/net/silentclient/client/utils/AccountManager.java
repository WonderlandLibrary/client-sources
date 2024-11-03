package net.silentclient.client.utils;

import com.google.common.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.silentclient.client.Client;
import net.silentclient.client.gui.silentmainmenu.MainMenuConcept;
import net.silentclient.client.mixin.ducks.MinecraftExt;
import net.silentclient.client.utils.types.PlayerResponse;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AccountManager {
    private ArrayList<AccountType> accounts;
    private AccountType selected;

    public void init() {
        Client.logger.info("[ACCOUNT-MANAGER]: Getting accounts");
        updateAccounts();

        Client.logger.info("[ACCOUNT-MANAGER]: Getting selected account");
        String accountText = Requests.httpGet(Client.getInstance().getApiUrl() + "/selected");
        if(accountText != null) {
            try {
                AccountType account = Client.getInstance().getGson().fromJson(accountText, AccountType.class);
                Client.logger.info("[ACCOUNT-MANAGER]: Selected Account Username: " + account.username);
                setSelected(account, 0, true);
            } catch (Exception err) {
                Client.logger.catching(err);
            }
        }
    }

    public void updateAccounts() {
        String accountsText = Requests.httpGet(Client.getInstance().getApiUrl() + "/accounts");
        if(accountsText != null) {
            try {
                Type listType = new TypeToken<ArrayList<AccountType>>(){}.getType();

                accounts = Client.getInstance().getGson().fromJson(accountsText, listType);
            } catch (Exception err) {
                Client.logger.catching(err);
            }
        }
    }

    public void setSelected(AccountType account, int index, boolean force) {
        if(!force) {
            Requests.httpPost(Client.getInstance().getApiUrl() + "/set_selected", new JSONObject().put("selected", index).toString());
        }
        selected = account;
        if(!force) {
            Session session = new Session(account.username, nameToUuid(account.username), account.mc_access_token != null ? account.mc_access_token : "0", account.mc_access_token != null ? "msa" : "legacy");
            ((MinecraftExt) Minecraft.getMinecraft()).setSession(session);
            Client.getInstance().getUserData().setAccessToken(selected.access_token);
            Players.unregister();
            Players.register();
        }
        PlayerResponse acc = Client.getInstance().updateAccount();
        if(acc != null) {
            Client.getInstance().setAccount(acc.getAccount());
        }

        MainMenuConcept.imageLocation = null;
        MainMenuConcept.image = null;
        MainMenuConcept.initSkin = false;
    }

    public static String nameToUuid(String username) {
        try {
            String content = Requests.httpGet(Client.getInstance().getApiUrl() + "/uuid?username="+username);

            return Client.getInstance().getGson().fromJson(content, UuidResponse.class).uuid;
        } catch (Exception err) {
            Client.logger.catching(err);
            return "0";
        }
    }

    public ArrayList<AccountType> getAccounts() {
        return accounts;
    }

    public class AccountType {
        public String username;
        public String access_token;
        public String mc_access_token;
        public String mc_refresh_token;
    }

    public class UuidResponse {
        public String uuid;
    }
}
