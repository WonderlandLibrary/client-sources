package wtf.diablo.client.util.mc.alt;

import org.json.JSONArray;
import org.json.JSONObject;
import wtf.diablo.client.gui.altmanager2.login.Alt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public final class AltUtil {

    private static File altFileLocation;
    private static final CopyOnWriteArrayList<Alt> altList = new CopyOnWriteArrayList<>();

    public static void setAltFileLocation(File file) {
        altFileLocation = file;
    }

    public static CopyOnWriteArrayList<Alt> getAltList() {
        return altList;
    }

    public static String getAltListString() {
        JSONArray array = new JSONArray();
        for (Alt alt : altList) {
            array.put(alt.getAltData());
        }
        return array.toString();
    }

    public static void setAltList(String jsonString) {
        altList.clear();
        JSONArray array = new JSONArray(jsonString);
        array.forEach((object) -> {
            JSONObject currentAltObject = (JSONObject) object;
            altList.add(Alt.getAltFromData(currentAltObject));
        });
    }

    public static File getAltFileLocation() {
        return altFileLocation;
    }

    public static void saveAlts() {
        try (FileWriter writer = new FileWriter(getAltFileLocation())) {
            if (!getAltFileLocation().exists())
                getAltFileLocation().createNewFile();

            writer.write(getAltListString());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public enum AccountType {
        MICROSOFT,
        CRACKED
    }

}
