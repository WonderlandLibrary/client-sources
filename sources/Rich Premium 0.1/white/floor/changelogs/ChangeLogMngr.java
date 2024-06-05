package white.floor.changelogs;

import java.util.ArrayList;

public class ChangeLogMngr {
    public static ArrayList<ChangeLog> changeLogs = new ArrayList<>();

    public void setChangeLogs() {
    	changeLogs.add(new ChangeLog("test", ChangelogType.ADD));
    }

    public ArrayList<ChangeLog> getChangeLogs() {
        return changeLogs;
    }
}