package in.momin5.cookieclient.api.module;

public enum Category {
    COMBAT("Combat"),
    MISC("Misc"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    RENDER("Render"),
    HUD("HUD");

    public String name;
    public int moduleIndex;

    Category(String name){
        this.name = name;
    }
}
