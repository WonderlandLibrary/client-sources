package mathax.client.systems.profiles;

import mathax.client.MatHax;
import mathax.client.events.game.GameJoinedEvent;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.System;
import mathax.client.systems.Systems;
import mathax.client.utils.Utils;
import mathax.client.utils.misc.NbtUtils;
import mathax.client.eventbus.EventHandler;
import net.minecraft.nbt.NbtCompound;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Profiles extends System<Profiles> implements Iterable<Profile> {

    public static final File FOLDER = new File(MatHax.VERSION_FOLDER, "Profiles");
    private List<Profile> profiles = new ArrayList<>();

    public Profiles() {
        super("Profiles");
    }

    public static Profiles get() {
        return Systems.get(Profiles.class);
    }

    public void add(Profile profile) {
        if (!profiles.contains(profile)) profiles.add(profile);
        profile.save();
        save();
    }

    public void remove(Profile profile) {
        if (profiles.remove(profile)) profile.delete();
        save();
    }

    public Profile get(String name) {
        for (Profile profile : this) {
            if (profile.name.equalsIgnoreCase(name)) return profile;
        }

        return null;
    }

    public List<Profile> getAll() {
        return profiles;
    }

    @Override
    public File getFile() {
        return new File(FOLDER, "Profiles.nbt");
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.put("profiles", NbtUtils.listToTag(profiles));
        return tag;
    }

    @Override
    public Profiles fromTag(NbtCompound tag) {
        profiles = NbtUtils.listFromTag(tag.getList("profiles", 10), tag1 -> new Profile().fromTag((NbtCompound) tag1));
        return this;
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent event) {
        for (Profile profile : this) {
            if (profile.loadOnJoinIps.contains(Utils.getWorldName())) profile.load();
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        for (Profile profile : this) {
            if (profile.saveOnLeave && profile.loadOnJoinIps.contains(Utils.getWorldName())) profile.save();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        for (Profile profile : this) {
            if (profile.saveOnInterval && profile.loadOnJoinIps.contains(Utils.getWorldName())) {
                profile.timer--;

                if (profile.timer <= 0) {
                    profile.timer = profile.saveInterval * 20;
                    profile.save();
                }
            }
        }
    }

    @Override
    public Iterator<Profile> iterator() {
        return profiles.iterator();
    }
}
