package fr.dog.component.impl.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.dog.component.Component;
import fr.dog.ui.altmanager.Alt;
import fr.dog.util.system.FileUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class AltsComponent extends Component {

    @Getter
    private static ArrayList<Alt> altList = new ArrayList<>();

    public static void addAlt(Session session){
        altList.add(new Alt(session));
    }
    public static void removeAlt(Alt alt){
        altList.remove(alt);
    }
    public static void read(){
        altList.clear();
        new File(mc.mcDataDir, "/dog").mkdir();
        final File d = new File(mc.mcDataDir, "/dog/alts.json");
        if(!d.exists()){
            return;
        }
        JsonObject config = FileUtil.readJsonFromFile(d.getAbsolutePath());
        for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
            String username = entry.getKey();
            String otherData = entry.getValue().getAsString();
            Session session = new Session(username, otherData.split(":")[0], otherData.split(":")[1], "legacy");
            Alt alt = new Alt(session);
            altList.add(alt);
        }
    }

    public static void write(){
        JsonObject jsonObject = new JsonObject();
        for(Alt a : altList){
            jsonObject.addProperty(a.getSession().getUsername(), a.getSession().getPlayerID() + ":" + a.getSession().getToken());
        }
        FileUtil.writeJsonToFile(jsonObject, new File(mc.mcDataDir, "/dog/alts.json").getAbsolutePath());
    }


}
