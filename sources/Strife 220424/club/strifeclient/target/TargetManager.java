package club.strifeclient.target;

import club.strifeclient.target.impl.Enemy;
import club.strifeclient.target.impl.Friend;

import java.util.*;
import java.util.stream.Collectors;

public final class TargetManager {
    private final Set<Target> targetSet;

    public TargetManager() {
        targetSet = new HashSet<>();
    }

    public boolean addFriend(String... args) {
        return targetSet.addAll(Arrays.stream(args).map(Friend::new).collect(Collectors.toList()));
    }

    public boolean addEnemy(String... args) {
        return targetSet.addAll(Arrays.stream(args).map(Enemy::new).collect(Collectors.toList()));
    }

    public boolean isFriend(String targetName) {
        return targetSet.stream().anyMatch(target -> target.getTargetName().equals(targetName) && target instanceof Friend);
    }

    public boolean isEnemy(String targetName) {
        return targetSet.stream().anyMatch(target -> target.getTargetName().equals(targetName) && target instanceof Enemy);
    }

    public void remove(String... args) {
        final List<Target> realTarget = targetSet.stream().filter(target ->
                Arrays.asList(args).contains(target.getTargetName())).collect(Collectors.toList());
        realTarget.forEach(targetSet::remove);
    }

    public List<Target> getAllFriends() {
        return targetSet.stream().filter(target -> target instanceof Friend).collect(Collectors.toList());
    }

    public List<Target> getAllEnemies() {
        return targetSet.stream().filter(target -> target instanceof Enemy).collect(Collectors.toList());
    }
}
