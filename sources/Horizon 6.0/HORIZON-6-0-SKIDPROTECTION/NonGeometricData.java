package HORIZON-6-0-SKIDPROTECTION;

import java.util.Properties;

public class NonGeometricData
{
    public static final String HorizonCode_Horizon_È = "id";
    public static final String Â = "fill";
    public static final String Ý = "stroke";
    public static final String Ø­áŒŠá = "opacity";
    public static final String Âµá€ = "stroke-width";
    public static final String Ó = "stroke-miterlimit";
    public static final String à = "stroke-dasharray";
    public static final String Ø = "stroke-dashoffset";
    public static final String áŒŠÆ = "stroke-opacity";
    public static final String áˆºÑ¢Õ = "none";
    private String ÂµÈ;
    private Properties á;
    
    public NonGeometricData(final String metaData) {
        this.ÂµÈ = "";
        this.á = new Properties();
        this.ÂµÈ = metaData;
        this.HorizonCode_Horizon_È("stroke-width", "1");
    }
    
    private String Ó(final String str) {
        if (str.equals("")) {
            return "#000000";
        }
        if (str.equals("white")) {
            return "#ffffff";
        }
        if (str.equals("black")) {
            return "#000000";
        }
        return str;
    }
    
    public void HorizonCode_Horizon_È(final String attribute, String value) {
        if (value == null) {
            value = "";
        }
        if (attribute.equals("fill")) {
            value = this.Ó(value);
        }
        if (attribute.equals("stroke-opacity") && value.equals("0")) {
            this.á.setProperty("stroke", "none");
        }
        if (attribute.equals("stroke-width")) {
            if (value.equals("")) {
                value = "1";
            }
            if (value.endsWith("px")) {
                value = value.substring(0, value.length() - 2);
            }
        }
        if (attribute.equals("stroke")) {
            if ("none".equals(this.á.getProperty("stroke"))) {
                return;
            }
            if ("".equals(this.á.getProperty("stroke"))) {
                return;
            }
            value = this.Ó(value);
        }
        this.á.setProperty(attribute, value);
    }
    
    public boolean HorizonCode_Horizon_È(final String attribute) {
        return this.Â(attribute).startsWith("#");
    }
    
    public String HorizonCode_Horizon_È() {
        return this.ÂµÈ;
    }
    
    public String Â(final String attribute) {
        return this.á.getProperty(attribute);
    }
    
    public Color Ý(final String attribute) {
        if (!this.HorizonCode_Horizon_È(attribute)) {
            throw new RuntimeException("Attribute " + attribute + " is not specified as a color:" + this.Â(attribute));
        }
        final int col = Integer.parseInt(this.Â(attribute).substring(1), 16);
        return new Color(col);
    }
    
    public String Ø­áŒŠá(final String attribute) {
        String value = this.Â(attribute);
        if (value.length() < 7) {
            return "";
        }
        value = value.substring(5, value.length() - 1);
        return value;
    }
    
    public float Âµá€(final String attribute) {
        final String value = this.Â(attribute);
        if (value == null) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(value);
        }
        catch (NumberFormatException e) {
            throw new RuntimeException("Attribute " + attribute + " is not specified as a float:" + this.Â(attribute));
        }
    }
    
    public boolean Â() {
        return this.HorizonCode_Horizon_È("fill");
    }
    
    public boolean Ý() {
        return this.HorizonCode_Horizon_È("stroke") && this.Âµá€("stroke-width") > 0.0f;
    }
}
