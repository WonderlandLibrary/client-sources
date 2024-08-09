package xyz.mathax.mathaxclient.settings;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.network.PacketUtils;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.network.Packet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PacketListSetting extends Setting<Set<Class<? extends Packet<?>>>> {
    public final Predicate<Class<? extends Packet<?>>> filter;

    private static List<String> suggestions;

    public PacketListSetting(String name, String description, Set<Class<? extends Packet<?>>> defaultValue, Consumer<Set<Class<? extends Packet<?>>>> onChanged, Consumer<Setting<Set<Class<? extends Packet<?>>>>> onModuleEnabled, Predicate<Class<? extends Packet<?>>> filter, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);

        this.filter = filter;
    }

    @Override
    public void resetImpl() {
        value = new ObjectOpenHashSet<>(defaultValue);
    }

    @Override
    protected Set<Class<? extends Packet<?>>> parseImpl(String string) {
        String[] values = string.split(",");
        Set<Class<? extends Packet<?>>> packets = new ObjectOpenHashSet<>(values.length);

        try {
            for (String value : values) {
                Class<? extends Packet<?>> packet = PacketUtils.getPacket(value.trim());
                if (packet != null && (filter == null || filter.test(packet))) {
                    packets.add(packet);
                }
            }
        } catch (Exception ignored) {}

        return packets;
    }

    @Override
    protected boolean isValueValid(Set<Class<? extends Packet<?>>> value) {
        return true;
    }

    @Override
    public List<String> getSuggestions() {
        if (suggestions == null) {
            suggestions = new ArrayList<>(PacketUtils.getC2SPackets().size() + PacketUtils.getS2CPackets().size());

            for (Class<? extends Packet<?>> packet : PacketUtils.getC2SPackets()) {
                suggestions.add(PacketUtils.getName(packet));
            }

            for (Class<? extends Packet<?>> packet : PacketUtils.getS2CPackets()) {
                suggestions.add(PacketUtils.getName(packet));
            }
        }

        return suggestions;
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        for (Class<? extends Packet<?>> packet : get()) {
            json.append("value", PacketUtils.getName(packet));
        }

        return json;
    }

    @Override
    public Set<Class<? extends Packet<?>>> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof String name) {
                    Class<? extends Packet<?>> packet = PacketUtils.getPacket(name);
                    if (packet != null && (filter == null || filter.test(packet))) {
                        get().add(packet);
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, Set<Class<? extends Packet<?>>>, PacketListSetting> {
        private Predicate<Class<? extends Packet<?>>> filter;

        public Builder() {
            super(new ObjectOpenHashSet<>(0));
        }

        public Builder filter(Predicate<Class<? extends Packet<?>>> filter) {
            this.filter = filter;
            return this;
        }

        @Override
        public PacketListSetting build() {
            return new PacketListSetting(name, description, defaultValue, onChanged, onModuleEnabled, filter, visible);
        }
    }
}
