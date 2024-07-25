package club.bluezenith.ui.guis.mainmenu.changelog.fetch;

import java.util.List;

public interface ChangelogProvider {
    void fetch();
    List<ChangelogEntry> getChangelogEntries();
    boolean hasFetchedChangelog();
}
