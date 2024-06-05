package me.vaziak.sensation.utils.anthony.item;

public enum EnchantableItem {
    HELMET("helmet"),
    CHESTPLATE("chestplate"),
    LEGGINGS("leggings"),
    BOOTS("boots"),
    SWORD("sword"),
    AXE("axe"),
    PICKAXE("pickaxe"),
    SHOVEL("shovel"),
    HOE("hoe"),
    BOW("boe"),
    FISHING_ROD("fishing");

    private String unlocalizedName;

    EnchantableItem(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }
}
