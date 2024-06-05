package alphentus.gui.altmanager;

import alphentus.init.Init;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import java.net.Proxy;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class Rescan {

    public ArrayList<Alt> falseAccounts = new ArrayList<>();

    public void scan() {
        falseAccounts.clear();
        for (Alt alt : Init.getInstance().altManager.altArrayList) {
            try {
                YggdrasilUserAuthentication yua = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
                yua.setUsername(alt.getAccountEmail());
                yua.setPassword(alt.getAccountPassword());
                yua.logIn();
            } catch (Exception e) {
                alt.setAccountUsername("null");
                this.falseAccounts.add(alt);
                e.printStackTrace();
            }
        }
        System.out.println("All Accounts are scanned");
        Init.getInstance().altManager.deleteScannedAccounts = true;
    }

    public void deleteFaleAccounts() {
        for (Alt alt : this.falseAccounts) {
            Init.getInstance().altManager.altArrayList.remove(alt);
        }
        this.falseAccounts.clear();
        System.out.println("All Accounts are deleted");
        Init.getInstance().altManager.deleteScannedAccounts = false;
    }

}
