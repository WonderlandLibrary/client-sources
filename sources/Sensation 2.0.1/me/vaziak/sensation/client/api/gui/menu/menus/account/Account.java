package me.vaziak.sensation.client.api.gui.menu.menus.account;

import me.vaziak.sensation.Sensation;
import net.minecraft.client.main.altening.AltService;

public class Account {

    private String username;
    private String password;
    private String[] loggedBans;
    private AltService.EnumAltService service;

    public Account(String username, String password, AltService.EnumAltService service) {
        this.username = username;
        this.password = password;
        this.service = service;
        if (!Sensation.instance.usedAccounts.contains(username + ":" + password)) {
        	Sensation.instance.usedAccounts.add(username + ":" + password);
        }
    }

    public void attemptLogin() throws IllegalArgumentException {
        Sensation.instance.accountLoginService.attemptLogin(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public AltService.EnumAltService getService() {
        return service;
    }

    public void setService(AltService.EnumAltService service) {
        this.service = service;
    }
}
