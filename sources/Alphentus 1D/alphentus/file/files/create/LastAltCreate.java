package alphentus.file.files.create;

import alphentus.file.files.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class LastAltCreate extends Component {

    final File path = new File(getFileDirectory(), "lastalt.txt");

    public void createCustomAlt (String email, String password) {
        try {

            FileWriter fw = new FileWriter(path);
            fw.close();

            try {
                FileWriter fw2 = new FileWriter(path);
                BufferedWriter bw = new BufferedWriter(fw2);

                bw.write(email + ":" + password);

                bw.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
