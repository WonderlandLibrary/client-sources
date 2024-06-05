package alphentus.file.files.load;

import alphentus.file.files.Component;
import alphentus.mod.Mod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class ModuleVisibleLoad extends Component {

    final File path = new File(getFileDirectory(), "visibles.txt");

    @Override
    public void load() {
        try {
            if (!path.exists())
                return;

            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String currentLine = null;

            while ((currentLine = bufferedReader.readLine()) != null) {
                for (Mod mod : getInit().modManager.getModArrayList()) {
                    if (mod.getModuleName().equals(currentLine.split(":")[0])) {
                        mod.setVisible(Boolean.parseBoolean(currentLine.split(":")[1]));
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
