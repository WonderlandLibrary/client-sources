package alphentus.file.files.create;

import alphentus.file.files.Component;
import alphentus.gui.customhud.settings.settings.Value;
import alphentus.gui.customhud.settings.settings.ValueTab;
import alphentus.mod.Mod;
import alphentus.settings.Setting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class SettingsCustomHUDCreate extends Component {

    final File path = new File(getFileDirectory(), "settingscustomhud.txt");

    public SettingsCustomHUDCreate() {
    }

    @Override
    public void create() {
        try {

            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);


            for (ValueTab valueTab : ValueTab.values()) {
                for (Value setting : getInit().valueManager.getValues()) {
                    if (valueTab.equals(setting.getValueTab())) {
                        if (setting.getValueIdentifier().equals("CheckBox")) {
                            bw.write(valueTab.name() + ":" + setting.getValueName() + ":" + setting.isState() + System.getProperty("line.separator"));
                        } else if (setting.getValueIdentifier().equals("Slider")) {
                            bw.write(valueTab.name() + ":" + setting.getValueName() + ":" + setting.getCurrentValue() + System.getProperty("line.separator"));
                        } else if (setting.getValueIdentifier().equals("ComboBox")) {
                            bw.write(valueTab.name() + ":" + setting.getValueName() + ":" + setting.getCurrentMode() + System.getProperty("line.separator"));
                        }
                    }
                }
            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.create();
    }
}
