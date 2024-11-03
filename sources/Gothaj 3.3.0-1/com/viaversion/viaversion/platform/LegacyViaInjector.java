package com.viaversion.viaversion.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.ReflectionUtil;
import com.viaversion.viaversion.util.SynchronizedListWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyViaInjector implements ViaInjector {
   protected final List<ChannelFuture> injectedFutures = new ArrayList<>();
   protected final List<Pair<Field, Object>> injectedLists = new ArrayList<>();

   @Override
   public void inject() throws ReflectiveOperationException {
      Object connection = this.getServerConnection();
      if (connection == null) {
         throw new RuntimeException("Failed to find the core component 'ServerConnection'");
      } else {
         for (Field field : connection.getClass().getDeclaredFields()) {
            if (List.class.isAssignableFrom(field.getType()) && field.getGenericType().getTypeName().contains(ChannelFuture.class.getName())) {
               field.setAccessible(true);
               List<ChannelFuture> list = (List<ChannelFuture>)field.get(connection);
               List<ChannelFuture> wrappedList = new SynchronizedListWrapper<>(list, o -> {
                  try {
                     this.injectChannelFuture(o);
                  } catch (ReflectiveOperationException var3) {
                     throw new RuntimeException(var3);
                  }
               });
               synchronized (list) {
                  for (ChannelFuture future : list) {
                     this.injectChannelFuture(future);
                  }

                  field.set(connection, wrappedList);
               }

               this.injectedLists.add(new Pair<>(field, connection));
            }
         }
      }
   }

   private void injectChannelFuture(ChannelFuture future) throws ReflectiveOperationException {
      List<String> names = future.channel().pipeline().names();
      ChannelHandler bootstrapAcceptor = null;

      for (String name : names) {
         ChannelHandler handler = future.channel().pipeline().get(name);

         try {
            ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class);
            bootstrapAcceptor = handler;
            break;
         } catch (ReflectiveOperationException var9) {
         }
      }

      if (bootstrapAcceptor == null) {
         bootstrapAcceptor = future.channel().pipeline().first();
      }

      try {
         ChannelInitializer<Channel> oldInitializer = ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
         ReflectionUtil.set(bootstrapAcceptor, "childHandler", this.createChannelInitializer(oldInitializer));
         this.injectedFutures.add(future);
      } catch (NoSuchFieldException var8) {
         this.blame(bootstrapAcceptor);
      }
   }

   @Override
   public void uninject() throws ReflectiveOperationException {
      for (ChannelFuture future : this.injectedFutures) {
         ChannelPipeline pipeline = future.channel().pipeline();
         ChannelHandler bootstrapAcceptor = pipeline.first();
         if (bootstrapAcceptor == null) {
            Via.getPlatform().getLogger().info("Empty pipeline, nothing to uninject");
         } else {
            Iterator originalList = pipeline.names().iterator();

            while (true) {
               if (originalList.hasNext()) {
                  String name = (String)originalList.next();
                  ChannelHandler handler = pipeline.get(name);
                  if (handler == null) {
                     Via.getPlatform().getLogger().warning("Could not get handler " + name);
                     continue;
                  }

                  try {
                     if (!(ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class) instanceof WrappedChannelInitializer)) {
                        continue;
                     }

                     bootstrapAcceptor = handler;
                  } catch (ReflectiveOperationException var13) {
                     continue;
                  }
               }

               try {
                  ChannelInitializer<Channel> initializer = ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
                  if (initializer instanceof WrappedChannelInitializer) {
                     ReflectionUtil.set(bootstrapAcceptor, "childHandler", ((WrappedChannelInitializer)initializer).original());
                  }
               } catch (Exception var12) {
                  Via.getPlatform()
                     .getLogger()
                     .log(Level.SEVERE, "Failed to remove injection handler, reload won't work with connections, please reboot!", (Throwable)var12);
               }
               break;
            }
         }
      }

      this.injectedFutures.clear();

      for (Pair<Field, Object> pair : this.injectedLists) {
         try {
            Field field = pair.key();
            Object o = field.get(pair.value());
            if (o instanceof SynchronizedListWrapper) {
               List<ChannelFuture> originalList = ((SynchronizedListWrapper)o).originalList();
               synchronized (originalList) {
                  field.set(pair.value(), originalList);
               }
            }
         } catch (ReflectiveOperationException var11) {
            Via.getPlatform().getLogger().severe("Failed to remove injection, reload won't work with connections, please reboot!");
         }
      }

      this.injectedLists.clear();
   }

   @Override
   public boolean lateProtocolVersionSetting() {
      return true;
   }

   @Override
   public JsonObject getDump() {
      JsonObject data = new JsonObject();
      JsonArray injectedChannelInitializers = new JsonArray();
      data.add("injectedChannelInitializers", injectedChannelInitializers);

      for (ChannelFuture future : this.injectedFutures) {
         JsonObject futureInfo = new JsonObject();
         injectedChannelInitializers.add(futureInfo);
         futureInfo.addProperty("futureClass", future.getClass().getName());
         futureInfo.addProperty("channelClass", future.channel().getClass().getName());
         JsonArray pipeline = new JsonArray();
         futureInfo.add("pipeline", pipeline);

         for (String pipeName : future.channel().pipeline().names()) {
            JsonObject handlerInfo = new JsonObject();
            pipeline.add(handlerInfo);
            handlerInfo.addProperty("name", pipeName);
            ChannelHandler channelHandler = future.channel().pipeline().get(pipeName);
            if (channelHandler == null) {
               handlerInfo.addProperty("status", "INVALID");
            } else {
               handlerInfo.addProperty("class", channelHandler.getClass().getName());

               try {
                  Object child = ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
                  handlerInfo.addProperty("childClass", child.getClass().getName());
                  if (child instanceof WrappedChannelInitializer) {
                     handlerInfo.addProperty("oldInit", ((WrappedChannelInitializer)child).original().getClass().getName());
                  }
               } catch (ReflectiveOperationException var12) {
               }
            }
         }
      }

      JsonObject wrappedLists = new JsonObject();
      JsonObject currentLists = new JsonObject();

      try {
         for (Pair<Field, Object> pair : this.injectedLists) {
            Field field = pair.key();
            Object list = field.get(pair.value());
            currentLists.addProperty(field.getName(), list.getClass().getName());
            if (list instanceof SynchronizedListWrapper) {
               wrappedLists.addProperty(field.getName(), ((SynchronizedListWrapper)list).originalList().getClass().getName());
            }
         }

         data.add("wrappedLists", wrappedLists);
         data.add("currentLists", currentLists);
      } catch (ReflectiveOperationException var13) {
      }

      return data;
   }

   @Nullable
   protected abstract Object getServerConnection() throws ReflectiveOperationException;

   protected abstract WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> var1);

   protected abstract void blame(ChannelHandler var1) throws ReflectiveOperationException;
}
