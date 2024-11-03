package com.viaversion.viaversion.api.scheduler;

public interface Task {
   TaskStatus status();

   void cancel();
}
