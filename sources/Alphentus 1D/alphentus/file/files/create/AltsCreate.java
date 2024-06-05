package alphentus.file.files.create;

import alphentus.file.files.Component;
import alphentus.gui.altmanager.Alt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class AltsCreate extends Component {

    final File path = new File(getFileDirectory(), "alts.txt");

    @Override
    public void create () {

        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Alt alt : getInit().altManager.altArrayList) {
                bw.write(alt.getAccountEmail() + ":" + alt.getAccountPassword() + ":" + alt.getAccountUsername() + System.getProperty("line.separator"));
            }

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.create();
    }
}
