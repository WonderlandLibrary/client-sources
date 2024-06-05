package alphentus.file.files.load;

import alphentus.event.Type;
import alphentus.file.files.Component;
import alphentus.gui.altmanager.Alt;
import alphentus.gui.altmanager.AltType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class AltsLoad extends Component {

    final File path = new File(getFileDirectory(), "alts.txt");

    @Override
    public void load () {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String currentLine = null;

            while ((currentLine = bufferedReader.readLine()) != null) {
               try {
                   if (currentLine.split(":")[2].length() > 0) {
                       getInit().altManager.altArrayList.add(new Alt(currentLine.split(":")[2], currentLine.split(":")[0], currentLine.split(":")[1], currentLine.split(":")[0].contains("@alt") ? AltType.THEALTENING : AltType.MOJANG));
                   }
               }catch (Exception e2){
                   getInit().altManager.altArrayList.add(new Alt(currentLine.split(":")[0], currentLine.split(":")[1], currentLine.split(":")[0].contains("@alt") ? AltType.THEALTENING : AltType.MOJANG));
               }
            }
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.load();
    }
}
