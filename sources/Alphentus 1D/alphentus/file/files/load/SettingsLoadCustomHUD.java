package alphentus.file.files.load;

import alphentus.file.files.Component;
import alphentus.gui.customhud.settings.settings.Value;
import alphentus.gui.customhud.settings.settings.ValueTab;
import alphentus.settings.Setting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class SettingsLoadCustomHUD extends Component {

    final File path = new File(getFileDirectory(), "settingscustomhud.txt");

    @Override
    public void load() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String currentLine = null;

            while ((currentLine = bufferedReader.readLine()) != null) {
                    for (Value setting : getInit().valueManager.getValues()) {
                    if (setting.getValueTab().name().equals(currentLine.split(":")[0])) {
                        if (setting.getValueName().equals(currentLine.split(":")[1])) {
                            if (setting.getValueIdentifier().equals("CheckBox")) {
                                Boolean toggle = Boolean.parseBoolean(currentLine.split(":")[2]);
                                setting.setState(toggle);
                            } else if (setting.getValueIdentifier().equals("ComboBox")) {
                                setting.setCurrentMode(currentLine.split(":")[2]);
                            } else if (setting.getValueIdentifier().equals("Slider")) {
                                Float current = Float.parseFloat(currentLine.split(":")[2]);
                                setting.setCurrentValue(current);
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
