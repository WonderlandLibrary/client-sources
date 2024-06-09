package host.kix.uzi.ui.alt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;

import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.util.Session;

public class AltManager {
    private ArrayList<Alt> alts = new ArrayList<Alt>();
    private Alt lastAlt;

    public ArrayList<Alt> getAlts() {
        return alts;
    }

    public void filterEmails() {
        ArrayList<Alt> tempList = new ArrayList<Alt>();
        for (Alt alt : alts) {
            if (alt.getDisplay().contains("@")) {
                tempList.add(new Alt(resolveName(alt), alt.getUsername(), alt.getPassword()));
            } else {
                tempList.add(alt);
            }
        }
        this.alts = tempList;
    }

    public String resolveName(Alt alt) {
        Session session = createSession(alt.getUsername(), alt.getPassword());
        if (session != null) {
            return session.getUsername();
        }
        return "";
    }

    private final Session createSession(String username, String password) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
                .createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                    auth.getAuthenticatedToken(), "mojang");
        } catch (final Exception e) {
            return null;
        }
    }

    public void setLastAlt(Alt lastAlt) {
        this.lastAlt = lastAlt;
    }

    public Alt getLastAlt() {
        return lastAlt;
    }
}
