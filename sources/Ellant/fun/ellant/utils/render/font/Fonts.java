package fun.ellant.utils.render.font;

public class Fonts {

    public static Font montserrat, consolas, icons, damage, sfui, sfbold, sfMedium, sfbold2, icon, test, miscelasibility;

    public static void register() {
        montserrat = new Font("Montserrat-Regular.ttf.png", "Montserrat-Regular.ttf.json");
        icons = new Font("icons.ttf.png", "icons.ttf.json");
        consolas = new Font("consolas.ttf.png", "consolas.ttf.json");
        damage = new Font("damage.ttf.png", "damage.ttf.json");
        sfui = new Font("sf_semibold.ttf.png", "sf_semibold.ttf.json");
        sfbold = new Font("sf_bold.ttf.png", "sf_bold.ttf.json");
        sfbold2 = new Font("sfbold2.png", "sfbold2.json");
        sfMedium = new Font("sf_medium.ttf.png", "sf_medium.ttf.json");
        icon = new Font("icon.ttf.png", "icons.ttf.json");
        test = new Font("Test.png", "Test.ttf.json");
        miscelasibility = new Font("Miscelasibility.ttf.png", "Miscelasibility.ttf.json");
    }

}
