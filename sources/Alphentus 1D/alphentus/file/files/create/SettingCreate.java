package alphentus.file.files.create;

import alphentus.file.files.Component;
import alphentus.mod.Mod;
import alphentus.settings.Setting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class SettingCreate extends Component {

    final File path = new File(getFileDirectory(), "settings.txt");

    @Override
    public void create () {
        try {

            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Mod mod : getInit().modManager.getModArrayList()) {
                for (Setting setting : getInit().settingManager.getSettingArrayList()) {
                    if (setting.getMod().equals(mod)) {
                        if (setting.getSettingIdentifier().equals("CheckBox")) {
                            bw.write(mod.getModuleName() + ":" + setting.getName() + ":" + setting.isState() + System.getProperty("line.separator"));
                        } else if (setting.getSettingIdentifier().equals("Slider")) {
                            bw.write(mod.getModuleName() + ":" + setting.getName() + ":" + setting.getCurrent() + System.getProperty("line.separator"));
                        } else if (setting.getSettingIdentifier().equals("ComboBox")) {
                            bw.write(mod.getModuleName() + ":" + setting.getName() + ":" + setting.getSelectedCombo() + System.getProperty("line.separator"));
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
