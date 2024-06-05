package cc.novoline.utils.tasks;

import cc.novoline.events.EventManager;
import cc.novoline.utils.tasks.FutureTask;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class TaskManager {
   private final List futureTasks = new CopyOnWriteArrayList();

   public void queue(FutureTask var1) {
      this.futureTasks.add(var1);
      EventManager.register(this);
   }

   public List getFutureTasks() {
      return this.futureTasks;
   }
}
