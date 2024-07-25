package club.bluezenith.module;

public enum ModuleCategory {
    COMBAT("Combat"),
    PLAYER("Player"),
    MOVEMENT("Movement"),
    RENDER("Render", "Visuals"),
    EXPLOIT("Exploit", "Exploits"),
    MISC("Misc"),
    FUN("Fun");

    public String displayName, novoGuiName;
    public boolean showContent;

    ModuleCategory(String displayName, String novoGuiName) {
        this.displayName = displayName;
        this.novoGuiName = novoGuiName;
        this.showContent = false;
    }

    ModuleCategory(String displayName) {
        this(displayName, displayName);
    }
}
