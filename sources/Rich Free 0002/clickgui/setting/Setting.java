package clickgui.setting;

import java.util.ArrayList;
import java.util.function.Supplier;

import me.rich.module.Feature;

//Deine Imports
/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class Setting {
	
	private String name;
	private Feature parent;
	private String mode;
	
	private String sval;
	private ArrayList<String> options;
	
	private boolean bval;
	
	private double dval;
	private double min;
	private double max;
	private boolean onlyint = false;
	private Supplier<Boolean> visibility;
	
	public Setting(String name, Feature parent, String sval, ArrayList<String> options) {
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        this.mode = "Combo";
        this.visibility = () -> true;
    }

    public Setting(String name, Feature parent, boolean bval) {
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.mode = "Check";
        this.visibility = () -> true;
    }

    public Setting(String name, Feature parent, double dval, double min, double max, boolean onlyint) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.mode = "Slider";
        this.visibility = () -> true;
    }

    public Setting(String name, Feature parent, double dval, double min, double max) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.mode = "HueSlider";
        this.visibility = () -> true;
    }

    public Setting(String name, Feature parent, double dval, double min, double max, int fix) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.mode = "BrightNessSlider";
        this.visibility = () -> true;
    }

    public Setting(String name, Feature parent, double dval, double min, double max, String fix) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.mode = "SaturationSlider";
        this.visibility = () -> true;
    }
	
	public String getName(){
		return name;
	}
	
	public Feature getParentMod(){
		return parent;
	}
	
	public String getValString(){
		return this.sval;
	}
	
	public void setValString(String in){
		this.sval = in;
	}
	
    public int getValInt() {
        if (this.onlyint) {
            this.dval = (int) dval;
        }
        return (int) this.dval;
    }
	
    public void setValFloat(float in) {
        this.dval = in;
    }
    
    public boolean isVisible() {
        return visibility.get();
    }
	
	public ArrayList<String> getOptions(){
		return this.options;
	}
	
	public boolean getValBoolean(){
		return this.bval;
	}
	
	public void setValBoolean(boolean in){
		this.bval = in;
	}
	
	public double getValDouble(){
		if(this.onlyint){
			this.dval = (int)dval;
		}
		return this.dval;
	}
	
    public float getValFloat() {
        if (this.onlyint) {
            this.dval = (int)this.dval;
        }
        return (float)this.dval;
    }

	public void setValDouble(double in){
		this.dval = in;
	}
	
	public boolean isHueSlider() {
        return this.mode.equalsIgnoreCase("HueSlider");
    }

    public boolean isBrightSlider() {
        return this.mode.equalsIgnoreCase("BrightNessSlider");
    }

    public boolean isSaturationSlider() {
        return this.mode.equalsIgnoreCase("SaturationSlider");
    }

    public boolean isCheck() {
        return this.mode.equalsIgnoreCase("Check");
    }

    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("Slider");
    }
    
	public double getMin(){
		return this.min;
	}
	
	public double getMax(){
		return this.max;
	}
	
	public boolean isCombo(){
		return this.mode.equalsIgnoreCase("Combo") ? true : false;
	}
	
	public boolean onlyInt(){
		return this.onlyint;
	}
}
