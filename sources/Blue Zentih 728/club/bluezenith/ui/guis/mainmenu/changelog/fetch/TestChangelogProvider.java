package club.bluezenith.ui.guis.mainmenu.changelog.fetch;

import club.bluezenith.util.math.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class TestChangelogProvider implements ChangelogProvider {

    List<ChangelogEntry> changes = new ArrayList<>();

    @Override
    public void fetch() {
        if(hasFetchedChangelog()) return;

        for (ChangelogEntry.EntryType value : ChangelogEntry.EntryType.values()) {
            for (int i = 0; i < MathUtil.getRandomInt(15, 20); i++) {
                changes.add(new ChangelogEntry("coming sometime later", value));

                if(i % 3 == 0)
                    changes.add(new ChangelogEntry("i have slayed i have slayed i have slayed i have slayed i have slayed i have slayed i have slayed i have slayed i have slayed", value));
            }
        }

        changes.add(new ChangelogEntry("" +
                "I get no bitches. Let's figure out why that happened. " +
                "Well, first of all, it has been scientifically proven you pull absolutely zero bitches", ChangelogEntry.EntryType.ADDITION));
    }

    @Override
    public List<ChangelogEntry> getChangelogEntries() {
        return changes;
    }

    @Override
    public boolean hasFetchedChangelog() {
        return !changes.isEmpty();
    }
}
