package zamorozka.ui;

public class ColoredString {
	ColoredChar[] chars;
	String string;
	
	public ColoredString(String str) {
		chars = ColoredChar.getFromString(str);
		string = str;
	}
	
	public void setColor(int i, int r, int g, int b) {
		chars[i].setColor(r, g, b);
	}
	
	public String getString() {
		return string;
	}
	
	public int length() {
		return chars.length;
	}
	
	public ColoredChar charAt(int i) {
		return chars[i];
	}
}
