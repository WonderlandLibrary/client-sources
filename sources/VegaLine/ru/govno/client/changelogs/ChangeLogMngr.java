/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.changelogs;

import java.util.ArrayList;
import ru.govno.client.changelogs.ChangeLog;
import ru.govno.client.changelogs.ChangelogType;

public class ChangeLogMngr {
    public static ArrayList<ChangeLog> changeLogs = new ArrayList();

    public void setChangeLogs() {
        changeLogs.add(new ChangeLog("Aura [Mode 1.8.9 ,Checkbox RotationLook]", ChangelogType.PROTOTYPE));
        changeLogs.add(new ChangeLog("ClickGui [Scroll ,Effects ,More images]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("NameTags [Added outline & friend color]", ChangelogType.IMPROVED));
        changeLogs.add(new ChangeLog("ModuleSound [More sounds for client]", ChangelogType.NEW));
        changeLogs.add(new ChangeLog("FastBreak [New checkbox ''haste'']", ChangelogType.IMPROVED));
        changeLogs.add(new ChangeLog("Aura [AttackMode: Packet , Attack]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Speed [Instant (Mode for matrix)]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Shaders [More minecraft shaders]", ChangelogType.NEW));
        changeLogs.add(new ChangeLog("Cooldown/HurtTime indicator", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Breadcrumbs [New settings]", ChangelogType.IMPROVED));
        changeLogs.add(new ChangeLog("JumpCircle [More settings]", ChangelogType.NEW));
        changeLogs.add(new ChangeLog("Breadcrumbs [New settings]", ChangelogType.IMPROVED));
        changeLogs.add(new ChangeLog("SuperBow [More settings]", ChangelogType.IMPROVED));
        changeLogs.add(new ChangeLog("HUD [New fonts settings]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("NoSlowDown [Nonstope))]", ChangelogType.IMPROVED));
        changeLogs.add(new ChangeLog("AntiCrystal [NoFlags]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("ClickGui [New fonts]", ChangelogType.IMPROVED));
        changeLogs.add(new ChangeLog("Strafe [Best strafe]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("Jesus [NcpStaic]", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("TPExploit", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("ArmorHUD", ChangelogType.ADD));
        changeLogs.add(new ChangeLog("KickMe", ChangelogType.NEW));
    }

    public ArrayList<ChangeLog> getChangeLogs() {
        return changeLogs;
    }
}

