package org.lwjgl.opencl;

import org.lwjgl.PointerWrapperAbstract;



































abstract class CLProgramCallback
  extends PointerWrapperAbstract
{
  private CLContext context;
  
  protected CLProgramCallback()
  {
    super(CallbackUtil.getProgramCallback());
  }
  




  final void setContext(CLContext context)
  {
    this.context = context;
  }
  




  private void handleMessage(long program_address)
  {
    handleMessage(context.getCLProgram(program_address));
  }
  
  protected abstract void handleMessage(CLProgram paramCLProgram);
}
