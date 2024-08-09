package xyz.mathax.mathaxclient.systems.profiles;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.game.GameJoinedEvent;
import xyz.mathax.mathaxclient.systems.System;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Profiles extends System<Profiles> implements Iterable<Profile> {
    public static final File FOLDER = new File(MatHax.VERSION_FOLDER, "Profiles");

    private final List<Profile> profiles = new ArrayList<>();

    public Profiles() {
        super("Profiles", null);
    }

    public static Profiles get() {
        return Systems.get(Profiles.class);
    }

    public void add(Profile profile) {
        if (!profiles.contains(profile)) {
            profiles.add(profile);
        }

        profile.save();
        save();
    }

    public void remove(Profile profile) {
        if (profiles.remove(profile)) {
            profile.delete();
        }

        save();
    }

    public Profile get(String name) {
        for (Profile profile : this) {
            if (profile.nameSetting.get().equalsIgnoreCase(name)) {
                return profile;
            }
        }

        return null;
    }

    public List<Profile> getAll() {
        return profiles;
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent event) {
        for (Profile profile : this) {
            if (profile.loadOnJoinSetting.get().contains(Utils.getWorldName())) {
                profile.load();
            }
        }
    }

    public boolean isEmpty() {
        return profiles.isEmpty();
    }

    @Override
    public Iterator<Profile> iterator() {
        return profiles.iterator();
    }

    @Override
    public void save(File folder) {
        folder = new File(MatHax.VERSION_FOLDER, "Profiles");

        for (Profile profile : profiles) {
            JSONUtils.saveJSON(profile.toJson(), new File(folder, profile.nameSetting.get() + "/" + profile.nameSetting.get() + ".json"));
        }
    }

    @Override
    public void load(File folder) {
        folder = new File(MatHax.VERSION_FOLDER, "Profiles");
        File[] profileFolders = folder.listFiles();
        if (profileFolders == null || profileFolders.length < 1) {
            return;
        }

        for (File profileFolder : profileFolders) {
            JSONObject json = JSONUtils.loadJSON(new File(profileFolder, profileFolder.getName() + ".json"));
            if (json == null) {
                continue;
            }

            profiles.add(new Profile(json));
        }
    }
}