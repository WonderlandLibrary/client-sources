package HORIZON-6-0-SKIDPROTECTION;

import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import javax.sound.sampled.Clip;

public class SoundPlayer
{
    public Clip HorizonCode_Horizon_È;
    public String Â;
    
    public SoundPlayer() {
        this.HorizonCode_Horizon_È = null;
        this.Â = "";
    }
    
    public void HorizonCode_Horizon_È(final String path) {
        try {
            final File inputStream = new File(path).getAbsoluteFile();
            final AudioInputStream aStream = AudioSystem.getAudioInputStream(inputStream);
            this.Â = inputStream.getName();
            final Clip clip = AudioSystem.getClip();
            (this.HorizonCode_Horizon_È = clip).open(aStream);
            this.HorizonCode_Horizon_È.start();
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        catch (LineUnavailableException e3) {
            e3.printStackTrace();
        }
        catch (OutOfMemoryError e4) {
            e4.printStackTrace();
            this.HorizonCode_Horizon_È = null;
        }
    }
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È.stop();
    }
    
    public void Â() {
        this.HorizonCode_Horizon_È.start();
    }
    
    public void HorizonCode_Horizon_È(final float dcb) {
        final FloatControl fct = (FloatControl)this.HorizonCode_Horizon_È.getControl(FloatControl.Type.MASTER_GAIN);
        fct.setValue(dcb);
    }
    
    public float Ý() {
        final FloatControl fct = (FloatControl)this.HorizonCode_Horizon_È.getControl(FloatControl.Type.MASTER_GAIN);
        return fct.getValue();
    }
}
