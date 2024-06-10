package me.dinozoid.strife.target;

import me.dinozoid.strife.font.CustomFont;
import me.dinozoid.strife.target.implementations.Friend;

import java.util.ArrayList;
import java.util.List;

public class TargetRepository {

    private final List<Target> TARGETS = new ArrayList<>();

    public void add(Target target) {
        TARGETS.add(target);
    }

    public void remove(Target target) {
        TARGETS.remove(target);
    }

    public void remove(String name) {
        TARGETS.remove(targetBy(name));
    }

    public Target targetBy(String name) {
        return TARGETS.stream().filter(target -> target.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean contains(String name) {
        return targetBy(name) != null;
    }

    public boolean isFriend(Target target) {
        return target instanceof Friend;
    }

    public boolean isFriend(String name) {
        return contains(name) && targetBy(name) instanceof Friend;
    }

    public List<Target> targets() {
        return TARGETS;
    }
}
