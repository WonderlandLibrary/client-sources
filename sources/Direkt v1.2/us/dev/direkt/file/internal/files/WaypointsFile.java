package us.dev.direkt.file.internal.files;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import us.dev.direkt.Direkt;
import us.dev.direkt.file.internal.AbstractClientFile;
import us.dev.direkt.file.internal.FileData;
import us.dev.direkt.module.internal.render.waypoints.handler.WaypointManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@FileData(fileName = "waypoints")
public class WaypointsFile extends AbstractClientFile {
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setPrettyPrinting()
            .create();

    @Override
    public void load() {
		try (Reader reader = new BufferedReader(new FileReader(this.getFile()))) {
            final Type serializedObject = new TypeToken<ArrayList<WaypointManager.WaypointData>>(){}.getType();
            final List<WaypointManager.WaypointData> serializedData = gson.fromJson(reader, serializedObject);
            if (serializedData != null){
                for (WaypointManager.WaypointData data : serializedData) {
                	Direkt.getInstance().getWaypointManager().addWaypointNoSave(data.getName(), data.getWorld(), data.getServer(), data.getLocation(), data.getDimension(), data.getColor(), data.getType());
                }
            }
		} catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        final List<WaypointManager.WaypointData> serializedData = Direkt.getInstance().getWaypointManager().getWaypoints().stream()
                .filter(waypoint -> waypoint.getType() != WaypointManager.Type.DEATH)
                .collect(Collectors.toList());
        try {
			Files.write(gson.toJson(serializedData).getBytes("UTF-8"), this.getFile());
		} catch (IOException e) {
            e.printStackTrace();
        }
    }
}
