package alphentus.settings;


import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 30/07/2020.
 */
public class SettingManager {

    private ArrayList<Setting> settingArrayList;

    public SettingManager(){
        settingArrayList = new ArrayList<>();
    }

    public final void addSetting(Setting setting){
        this.settingArrayList.add(setting);
    }

    public final ArrayList<Setting> getSettingArrayList () {
        return settingArrayList;
    }
}
