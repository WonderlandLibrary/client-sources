package alphentus.gui.customhud.settings.settings;

import alphentus.init.Init;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class SetValues {

    public Value visibleWatermark = new Value("Enabled", true, ValueTab.WATERMARK);
    public Value visibleTabGui = new Value("Enabled", true, ValueTab.TABGUI);
    public Value visibleArrayList = new Value("Enabled", true, ValueTab.ARRAYLIST);
    public Value visibleKeyStrokes = new Value("Enabled", true, ValueTab.KEYSTROKES);
    public Value visibleHotBar = new Value("Enabled", true, ValueTab.HOTBAR);
    public Value visibleEffects = new Value("Enabled", true, ValueTab.EFFECTS);

    // Arraylist Values
    String[] fontsList = {"Vanilla", "Thruster", "BebasNeue", "Tahoma", "Verdana", "Comfortaa", "Jello", "Arial", "Arial Light"};
    public Value chooseFont = new Value("ArrayList Font", fontsList, "Vanilla", ValueTab.ARRAYLIST);

    String[] colorList = {"Client", "White", "Custom", "Rainbow"};
    public Value chooseColor = new Value("Color", colorList, "Client", ValueTab.ARRAYLIST);

    String[] rectList = {"None", "Right", "Left"};
    public Value chooseRect = new Value("Rect Position", rectList, "None", ValueTab.ARRAYLIST);

    String[] animationList = {"None", "Slide", "Plop"};
    public Value chooseAnimation = new Value("Animation", animationList, "None", ValueTab.ARRAYLIST);

    public Value listHeight = new Value("ArrayList Height", 0, 2, 0, true, ValueTab.ARRAYLIST);
    public Value fontSize = new Value("Font Size", 15, 25, 20, true, ValueTab.ARRAYLIST);
    public Value fontShadow = new Value("Font Shadow", true, ValueTab.ARRAYLIST);
    public Value background = new Value("Background", true, ValueTab.ARRAYLIST);
    public Value rainbowSpeed = new Value("Rainbow Speed", 1000, 10000, 5000, true, ValueTab.ARRAYLIST);
    public Value rainbowOffset = new Value("Rainbow Offset", 1, 15, 1, true, ValueTab.ARRAYLIST);
    public Value textHeight = new Value("Text Height", -2, 5, 0, false, ValueTab.ARRAYLIST);
    public Value textX = new Value("Text X", -1, 3, 0, false, ValueTab.ARRAYLIST);
    public Value textOffset = new Value("Text Offset", 0, 5, 1, true, ValueTab.ARRAYLIST);
    public Value backgroundColor = new Value("Background Color", 0, 255, 0, true, ValueTab.ARRAYLIST);
    public Value backgroundAlpha = new Value("Background Alpha", 0, 255, 255, true, ValueTab.ARRAYLIST);
    public Value rectThickness = new Value("Rect Thickness", 1, 4, 2, true, ValueTab.ARRAYLIST);
    public Value outline = new Value("Outline", true, ValueTab.ARRAYLIST);
    public Value pulsate = new Value("Pulsate", false, ValueTab.ARRAYLIST);
    public Value colorRed = new Value("Color Red", 0, 255, 255, true, ValueTab.ARRAYLIST);
    public Value colorGreen = new Value("Color Green", 0, 255, 255, true, ValueTab.ARRAYLIST);
    public Value colorBlue = new Value("Color Blue", 0, 255, 255, true, ValueTab.ARRAYLIST);

    public SetValues() {
        Init.getInstance().valueManager.addValue(visibleWatermark);
        Init.getInstance().valueManager.addValue(visibleTabGui);
        Init.getInstance().valueManager.addValue(visibleArrayList);
        Init.getInstance().valueManager.addValue(visibleKeyStrokes);
        Init.getInstance().valueManager.addValue(visibleHotBar);
        Init.getInstance().valueManager.addValue(visibleEffects);

        Init.getInstance().valueManager.addValue(listHeight);
        Init.getInstance().valueManager.addValue(chooseFont);
        Init.getInstance().valueManager.addValue(chooseColor);
        Init.getInstance().valueManager.addValue(pulsate);
        Init.getInstance().valueManager.addValue(chooseRect);
        Init.getInstance().valueManager.addValue(chooseAnimation);
        Init.getInstance().valueManager.addValue(fontSize);
        Init.getInstance().valueManager.addValue(fontShadow);
        Init.getInstance().valueManager.addValue(background);
        Init.getInstance().valueManager.addValue(rainbowSpeed);
        Init.getInstance().valueManager.addValue(rainbowOffset);
        Init.getInstance().valueManager.addValue(textHeight);
        Init.getInstance().valueManager.addValue(textX);
        Init.getInstance().valueManager.addValue(textOffset);
        Init.getInstance().valueManager.addValue(backgroundColor);
        Init.getInstance().valueManager.addValue(backgroundAlpha);
        Init.getInstance().valueManager.addValue(rectThickness);
        Init.getInstance().valueManager.addValue(outline);
        Init.getInstance().valueManager.addValue(colorRed);
        Init.getInstance().valueManager.addValue(colorGreen);
        Init.getInstance().valueManager.addValue(colorBlue);
    }

}