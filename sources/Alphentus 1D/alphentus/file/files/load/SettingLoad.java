package alphentus.file.files.load;

import alphentus.file.files.Component;
import alphentus.mod.Mod;
import alphentus.settings.Setting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class SettingLoad extends Component {

    final File path = new File(getFileDirectory(), "settings.txt");


    @Override
    public void load() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String currentLine = null;

            while ((currentLine = bufferedReader.readLine()) != null) {
                for (Setting setting : getInit().settingManager.getSettingArrayList()) {
                    if (setting.getMod().getModuleName().equals(currentLine.split(":")[0])) {
                        if (setting.getName().equals(currentLine.split(":")[1])) {

                            if (setting.getSettingIdentifier().equals("CheckBox")) {
                                Boolean toggle = Boolean.parseBoolean(currentLine.split(":")[2]);
                                setting.setState(toggle);
                            } else if (setting.getSettingIdentifier().equals("Slider")) {
                                Float current = Float.parseFloat(currentLine.split(":")[2]);
                                setting.setCurrent(current);
                            } else if (setting.getSettingIdentifier().equals("ComboBox")) {
                                if (setting.getCombos().length > 1) {
                                    setting.setSelectedCombo(currentLine.split(":")[2]);
                                }
                            }

                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.load();
    }
}
