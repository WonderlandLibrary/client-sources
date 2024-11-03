package de.florianmichael.vialoadingbase.util;

import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.scheduler.Task;
import com.viaversion.viaversion.api.scheduler.TaskStatus;

public class VLBTask implements PlatformTask<Task> {
   private final Task object;

   public VLBTask(Task object) {
      this.object = object;
   }

   public Task getObject() {
      return this.object;
   }

   @Override
   public void cancel() {
      this.object.cancel();
   }

   public TaskStatus getStatus() {
      return this.getObject().status();
   }
}
