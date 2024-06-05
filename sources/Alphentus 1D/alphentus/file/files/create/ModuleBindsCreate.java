package alphentus.file.files.create;

import alphentus.file.files.Component;
import alphentus.mod.Mod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author avox | lmao
 * @since on 08/08/2020.
 */
public class ModuleBindsCreate extends Component {

    final File path = new File(getFileDirectory(), "binds.txt");

    @Override
    public void create () {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Mod mod : getInit().modManager.getModArrayList()) {
                bw.write(mod.getModuleName() + ":" + mod.getKeybind() + System.getProperty("line.separator"));
            }

            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.create();
    }

}
