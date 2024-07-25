package club.bluezenith.ui.guis.mainmenu.changelog.render;

import club.bluezenith.ui.guis.mainmenu.changelog.fetch.ChangelogEntry;

import java.util.List;

public interface ChangelogRenderer {
    void setContents(List<ChangelogEntry> contents);
    void render(int mouseX, int mouseY, float partialTicks, boolean updateScroll);
    void mouseClicked(int mouseX, int mouseY);
    void setPosition(float x, float y);
    void setMaxHeight(float height);

    void setWidth(float screenWidth);
}
