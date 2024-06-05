package me.rich.changelogs;

import java.util.ArrayList;

public class ChangeLogMngr {
    public static ArrayList<ChangeLog> changeLogs = new ArrayList<>();

    public void setChangeLogs() {
    	changeLogs.add(new ChangeLog("ChinaHat [Mode: StrawCircle, StrawHexagon, Circle, Hexagon, StrawPolygon, Polygon]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("WaterMark [Mode: Nursultan, FDP , Novoline]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("AntiBot [Mode: Matrix, HitBefore + CheckBox: Invisible Remove]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Jesus [Mode: Default , MatrixZoom]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Speed [Mode: Matrix , MatrixDisabler]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("NoSlowDown [Mode: Vanila , Matrix]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Animations [Mode: Rotate , Glide]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("KillAura [Mode: Packet , Client]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Velocity [Mode: Cancel , Matrix]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Cooldown/HurtTime indicator", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("BlockHitAnimations", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("WaterLeave", ChangelogType.IMPROVED));
        changeLogs.add(new ChangeLog("HealthManager", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("TriangleESP", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("SessionInfo", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("AutoPotion", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("AutoTotem", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Crosshair", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("BindList", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("HitBox", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Crosshair", ChangelogType.ADD));
    }

    public ArrayList<ChangeLog> getChangeLogs() {
        return changeLogs;
    }
}