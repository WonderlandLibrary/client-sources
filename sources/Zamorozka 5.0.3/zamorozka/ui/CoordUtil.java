package zamorozka.ui;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CoordUtil {
    public static final String coordsLogFilename = "KAMIBlueCoords.json";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Coordinate getCurrentCoord() {
        Minecraft mc = Minecraft.getMinecraft();
        return new Coordinate((int) Minecraft.player.posX, (int) Minecraft.player.posY, (int) Minecraft.player.posZ);
    }

    public static Coordinate writePlayerCoords(String locationName) {
        Coordinate coords = getCurrentCoord();
        writeCoords(coords, locationName, coordsLogFilename);
        return coords;
    }

    public static Coordinate writeCustomCoords(Coordinate xyz, String locationName) {
        writeCoords(xyz, locationName, coordsLogFilename);
        return xyz;
    }

    public static void writeCoords(Coordinate xyz, String locationName, String filename) {
        try {
            ArrayList<CoordinateInfo> coords = readCoords(filename);
            coords.add(formatter(xyz, locationName));
            FileWriter writer = new FileWriter(filename);
            gson.toJson(coords, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean removeCoord(String id, String filename) {
        boolean removed = false;
        try {
            ArrayList<CoordinateInfo> coords = readCoords(filename);
            for (CoordinateInfo coord : coords) {
                if (coord.getID().equals(id)) {
                    coords.remove(coord);
                    removed = true;
                    break;
                }
            }
            FileWriter writer = new FileWriter(filename);
            gson.toJson(coords, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return removed;
    }

    public static Coordinate getCoord(String id, String filename) {
        ArrayList<CoordinateInfo> coords = readCoords(filename);
        for (CoordinateInfo coord : coords) {
            if (coord.getID().equals(id)) {
                return coord.xyz;
            }
        }
        return null;
    }

    private static CoordinateInfo formatter(Coordinate xyz, String locationName) {
        String time = sdf.format(new Date());
        return new CoordinateInfo(xyz, locationName, time);
    }

    public static ArrayList<CoordinateInfo> readCoords(String filename) {
        try {
            ArrayList<CoordinateInfo> coords;
            coords = gson.fromJson(new FileReader(filename), new TypeToken<ArrayList<CoordinateInfo>>() {
            }.getType());
            if (coords != null) {
                return coords;
            } else {
                return new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            try {
                File file = new File(filename);
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return new ArrayList<>();
        }
    }
}
