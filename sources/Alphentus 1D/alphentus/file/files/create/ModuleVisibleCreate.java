package alphentus.file.files.create;

import alphentus.file.files.Component;
import alphentus.mod.Mod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class ModuleVisibleCreate extends Component {

    final File path = new File(getFileDirectory(), "visibles.txt");

    @Override
    public void create() {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Mod mod : getInit().modManager.getModArrayList()) {
                bw.write(mod.getModuleName() + ":" + mod.isVisible() + System.getProperty("line.separator"));
            }

            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.create();
    }

}
