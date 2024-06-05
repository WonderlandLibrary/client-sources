package lol.base.addons;

public enum CategoryAddon {

    COMBAT("Combat", true, false), // For combat modules, such as KillAura, Velocity and MoreKnockback
    MOVEMENT("Movement", true, false), // For movement modules, such as Fly, Speed and Sprint
    PLAYER("Player", true, false), // For player modules, such as NoFall, InventoryManager and AutoTool
    RENDER("Render", true, false), // For visual modules, such as JumpCircle, ESP and Nametags
    WORLD("World", true, false), // For world modules, such as ChestStealer, ChestAura and Timer
    HUD("HUD", true, false); // For "hud" modules, such as TargetHUD, ClickGUI, Watermark and ModuleList

    public final String categoryName;
    public boolean expanded, hidden;

    CategoryAddon(String displayName, boolean expanded, boolean hidden) {
        this.categoryName = displayName;
        this.expanded = expanded;
        this.hidden = hidden;
    }

}
