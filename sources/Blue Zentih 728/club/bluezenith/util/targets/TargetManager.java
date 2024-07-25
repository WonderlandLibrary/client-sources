package club.bluezenith.util.targets;

import club.bluezenith.BlueZenith;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class TargetManager {

    final List<String> targetList = Lists.newArrayList();

    public boolean isTarget(String name) {
        return targetList.stream().anyMatch(target -> target.equalsIgnoreCase(name));
    }

    public void addTarget(String name) {
        BlueZenith.getBlueZenith().getFriendManager().removeFriend(name);
        targetList.add(name);
    }

    public void removeTarget(String name) {
        targetList.removeIf(target -> target.equalsIgnoreCase(name));
    }

    public void clearList() {
        targetList.clear();
    }

    public List<String> getTargetsList() {
        return new ArrayList<>(targetList);
    }
}
