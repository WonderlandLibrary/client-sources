package me.superskidder.lune.values.type;

/**
 * @description: 新的Mode 适配JavaScript
 * @author: QianXia
 * @create: 2020/12/05 19:18
 **/
public class NewMode {
    private String[] modes;
    private String value;
    public String name;

    public NewMode(String name, String[] modes, String value) {
        this.name = name;
        this.modes = modes;
        this.setValue(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public String[] getModes() {
        return this.modes;
    }

    public String getModeAsString() {
        return this.getValue();
    }

    public void setMode(String mode) {
        String[] arrV = this.modes;
        int n = arrV.length;
        int n2 = 0;
        while (n2 < n) {
            String e = arrV[n2];
            if (e.equalsIgnoreCase(mode)) {
                this.setValue(e);
            }
            ++n2;
        }
    }

    public boolean isValid(String name) {
        String[] arrV = this.modes;
        int n = arrV.length;
        int n2 = 0;
        while (n2 < n) {
            String e = arrV[n2];
            if (e.equalsIgnoreCase(name)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public boolean isCurrentMode(String str) {
        return this.value.equals(str);
    }
}
