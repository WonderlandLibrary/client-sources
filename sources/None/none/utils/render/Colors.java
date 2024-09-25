package none.utils.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import none.utils.Utils;

public class Colors {
    public static int getColor(Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(int brightness) {
        return getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static int getColor(int red, int green, int blue) {
        return getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }
    
    public static int getColor(Color Color, int alpha) {
    	int red = Color.getRed();
    	int green = Color.getGreen();
    	int blue = Color.getBlue();
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }
    
    public static List<Color> getColorsList() {
    	List<Color> list = new ArrayList<>();
    	list.add(Color.BLACK);
    	list.add(Color.BLUE);
    	list.add(Color.CYAN);
    	list.add(Color.DARK_GRAY);
    	list.add(Color.GRAY);
    	list.add(Color.GREEN);
    	list.add(Color.LIGHT_GRAY);
    	list.add(Color.MAGENTA);
    	list.add(Color.ORANGE);
    	list.add(Color.PINK);
    	list.add(Color.RED);
    	list.add(Color.WHITE);
    	list.add(Color.YELLOW);
    	return list;
    }
    
    public static Color getRandomColor() {
    	Color color = null;
    	int random = Utils.random(0, getColorsList().size());
    	color = getColorsList().get(random);
    	return color;
    }
}
