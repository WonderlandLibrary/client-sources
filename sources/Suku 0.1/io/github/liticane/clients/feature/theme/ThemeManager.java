package io.github.liticane.clients.feature.theme;

public class ThemeManager {
    private Theme theme = Theme.COSMIC;

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
