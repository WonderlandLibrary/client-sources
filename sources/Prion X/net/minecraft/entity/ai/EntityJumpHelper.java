package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityJumpHelper
{
  private EntityLiving entity;
  protected boolean isJumping;
  private static final String __OBFID = "CL_00001571";
  
  public EntityJumpHelper(EntityLiving p_i1612_1_)
  {
    entity = p_i1612_1_;
  }
  
  public void setJumping()
  {
    isJumping = true;
  }
  



  public void doJump()
  {
    entity.setJumping(isJumping);
    isJumping = false;
  }
}
