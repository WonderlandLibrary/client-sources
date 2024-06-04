package net.java.games.input;

import java.io.IOException;



























abstract class LinuxForceFeedbackEffect
  implements Rumbler
{
  private final LinuxEventDevice device;
  private final int ff_id;
  private final WriteTask write_task = new WriteTask(null);
  private final UploadTask upload_task = new UploadTask(null);
  
  public LinuxForceFeedbackEffect(LinuxEventDevice device) throws IOException {
    this.device = device;
    ff_id = upload_task.doUpload(-1, 0.0F);
  }
  
  protected abstract int upload(int paramInt, float paramFloat) throws IOException;
  
  protected final LinuxEventDevice getDevice() {
    return device;
  }
  
  public final synchronized void rumble(float intensity) {
    try {
      if (intensity > 0.0F) {
        upload_task.doUpload(ff_id, intensity);
        write_task.write(1);
      } else {
        write_task.write(0);
      }
    } catch (IOException e) {
      LinuxEnvironmentPlugin.logln("Failed to rumble: " + e);
    }
  }
  








  public final String getAxisName()
  {
    return null;
  }
  

  public final Component.Identifier getAxisIdentifier() { return null; }
  
  private final class UploadTask extends LinuxDeviceTask {
    UploadTask(LinuxForceFeedbackEffect.1 x1) { this(); }
    
    private int id;
    private float intensity;
    public final int doUpload(int id, float intensity) throws IOException {
      this.id = id;
      this.intensity = intensity;
      LinuxEnvironmentPlugin.execute(this);
      return this.id;
    }
    
    protected final Object execute() throws IOException {
      id = upload(id, intensity);
      return null; }
    
    private UploadTask() {} }
  
  private final class WriteTask extends LinuxDeviceTask { WriteTask(LinuxForceFeedbackEffect.1 x1) { this(); }
    
    private int value;
    public final void write(int value) throws IOException {
      this.value = value;
      LinuxEnvironmentPlugin.execute(this);
    }
    
    protected final Object execute() throws IOException {
      device.writeEvent(21, ff_id, value);
      return null;
    }
    
    private WriteTask() {}
  }
}
