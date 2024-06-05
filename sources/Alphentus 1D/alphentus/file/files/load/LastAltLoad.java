package alphentus.file.files.load;

import alphentus.file.files.Component;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Proxy;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class LastAltLoad extends Component {

    final File path = new File(getFileDirectory(), "lastalt.txt");

    public void loadCustom () {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String currentLine = null;

            while ((currentLine = bufferedReader.readLine()) != null) {
                try {
                        String em = currentLine.split(":")[0];
                        String pa = currentLine.split(":")[1];

                        YggdrasilUserAuthentication yua = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(
                                Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
                        yua.setUsername(em);
                        yua.setPassword(pa);


                        yua.logIn();

                        Minecraft.getMinecraft().session = new Session(yua.getSelectedProfile().getName(),
                                yua.getSelectedProfile().getId().toString(), yua.getAuthenticatedToken(), "mojang");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
