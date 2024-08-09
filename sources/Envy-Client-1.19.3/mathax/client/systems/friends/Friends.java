package mathax.client.systems.friends;

import mathax.client.utils.render.color.RainbowColors;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.systems.System;
import mathax.client.systems.Systems;
import mathax.client.systems.config.Config;
import mathax.client.utils.misc.NbtUtils;
import mathax.client.utils.misc.ChatUtils;
import mathax.client.utils.render.ToastSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static mathax.client.MatHax.mc;

public class Friends extends System<Friends> implements Iterable<Friend> {
    private List<Friend> friends = new ArrayList<>();

    public final SettingColor color = new SettingColor(0, 255, 0);
    public boolean attack = false;

    public Friends() {
        super("Friends");
    }

    public static Friends get() {
        return Systems.get(Friends.class);
    }

    @Override
    public void init() {
        RainbowColors.add(color);
    }

    public boolean add(Friend friend) {
        if (friend.name.isEmpty()) return false;
        if (friend.name.equals(mc.getSession().getUsername())) {
            if (Config.get().chatFeedback.get()) ChatUtils.error("Friends", "You can't add yourself to friends!");
            if (Config.get().toastFeedback.get()) mc.getToastManager().add(new ToastSystem(Items.EMERALD, color.getPacked(), "Friends " + Formatting.GRAY + "[" + Formatting.WHITE + friend.name + Formatting.GRAY + "]", null, Formatting.RED + "You can't add yourself!", Config.get().toastDuration.get()));
            return false;
        }

        if (!friends.contains(friend)) {
            friends.add(friend);
            save();

            return true;
        }

        return false;
    }

    public boolean remove(Friend friend) {
        if (friends.remove(friend)) {
            save();
            return true;
        }

        return false;
    }

    public Friend get(String name) {
        for (Friend friend : friends) {
            if (friend.name.equals(name)) return friend;
        }

        return null;
    }

    public Friend get(PlayerEntity player) {
        return get(player.getEntityName());
    }

    public boolean isFriend(PlayerEntity player) {
        return get(player) != null;
    }

    public boolean shouldAttack(PlayerEntity player) {
        return !isFriend(player) || attack;
    }

    public int count() {
        return friends.size();
    }

    @Override
    @NotNull
    public Iterator<Friend> iterator() {
        return friends.iterator();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        NbtList friendsTag = new NbtList();

        for (Friend friend : friends) friendsTag.add(friend.toTag());
        tag.put("friends", friendsTag);
        tag.put("color", color.toTag());
        tag.putBoolean("attack", attack);

        return tag;
    }

    @Override
    public Friends fromTag(NbtCompound tag) {
        friends = NbtUtils.listFromTag(tag.getList("friends", 10), tag1 -> new Friend((NbtCompound) tag1));
        if (tag.contains("color")) color.fromTag(tag.getCompound("color"));
        attack = tag.contains("attack") && tag.getBoolean("attack");
        return this;
    }
}
