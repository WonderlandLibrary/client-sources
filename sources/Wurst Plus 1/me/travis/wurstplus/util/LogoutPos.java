package me.travis.wurstplus.util;

import java.util.UUID;
import net.minecraft.util.math.Vec3d;

public class LogoutPos {
    
    final UUID id;
    final String name;
    final Vec3d maxs;
    final Vec3d mins;
    
    public LogoutPos(UUID uuid, String name, Vec3d maxs, Vec3d mins) {
      this.id = uuid;
      this.name = name;
      this.maxs = maxs;
      this.mins = mins;
    }
    
    public UUID getId() {
      return id;
    }
    
    public String getName() {
      return name;
    }
    
    public Vec3d getMaxs() {
      return maxs;
    }
    
    public Vec3d getMins() {
      return mins;
    }
    
    public Vec3d getTopVec() {
      return new Vec3d(
          (getMins().x + getMaxs().x) / 2.D, getMaxs().y, (getMins().z + getMaxs().z) / 2.D);
    }
    
    @Override
    public boolean equals(Object other) {
      return this == other
          || (other instanceof LogoutPos && getId().equals(((LogoutPos) other).getId()));
    }
    
    @Override
    public int hashCode() {
      return getId().hashCode();
    }
  }