package net.java.games.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


































final class LinuxDeviceThread
  extends Thread
{
  private final List tasks = new ArrayList();
  
  public LinuxDeviceThread() {
    setDaemon(true);
    start();
  }
  
  public final synchronized void run() {
    for (;;) {
      if (!tasks.isEmpty()) {
        LinuxDeviceTask task = (LinuxDeviceTask)tasks.remove(0);
        task.doExecute();
        synchronized (task) {
          task.notify();
        }
      } else {
        try {
          wait();
        }
        catch (InterruptedException e) {}
      }
    }
  }
  
  public final Object execute(LinuxDeviceTask task) throws IOException
  {
    synchronized (this) {
      tasks.add(task);
      notify();
    }
    synchronized (task) {
      while (task.getState() == 1) {
        try {
          task.wait();
        }
        catch (InterruptedException e) {}
      }
    }
    
    switch (task.getState()) {
    case 2: 
      return task.getResult();
    case 3: 
      throw task.getException();
    }
    throw new RuntimeException("Invalid task state: " + task.getState());
  }
}
