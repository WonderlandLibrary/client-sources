package net.java.games.input;

import java.io.IOException;


























abstract class LinuxDeviceTask
{
  public static final int INPROGRESS = 1;
  public static final int COMPLETED = 2;
  public static final int FAILED = 3;
  private Object result;
  private IOException exception;
  private int state = 1;
  
  LinuxDeviceTask() {}
  
  public final void doExecute() { try { result = execute();
      state = 2;
    } catch (IOException e) {
      exception = e;
      state = 3;
    }
  }
  
  public final IOException getException() {
    return exception;
  }
  
  public final Object getResult() {
    return result;
  }
  
  public final int getState() {
    return state;
  }
  
  protected abstract Object execute()
    throws IOException;
}
