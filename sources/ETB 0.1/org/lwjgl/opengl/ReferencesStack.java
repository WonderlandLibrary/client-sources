package org.lwjgl.opengl;












class ReferencesStack
{
  private References[] references_stack;
  









  private int stack_pos;
  










  public References getReferences()
  {
    return references_stack[stack_pos];
  }
  
  public void pushState() {
    int pos = ++stack_pos;
    if (pos == references_stack.length) {
      growStack();
    }
    references_stack[pos].copy(references_stack[(pos - 1)], -1);
  }
  
  public References popState(int mask) {
    References result = references_stack[(stack_pos--)];
    
    references_stack[stack_pos].copy(result, mask ^ 0xFFFFFFFF);
    result.clear();
    
    return result;
  }
  
  private void growStack() {
    References[] new_references_stack = new References[references_stack.length + 1];
    System.arraycopy(references_stack, 0, new_references_stack, 0, references_stack.length);
    references_stack = new_references_stack;
    references_stack[(references_stack.length - 1)] = new References(GLContext.getCapabilities());
  }
  
  ReferencesStack() {
    ContextCapabilities caps = GLContext.getCapabilities();
    references_stack = new References[1];
    stack_pos = 0;
    for (int i = 0; i < references_stack.length; i++) {
      references_stack[i] = new References(caps);
    }
  }
}
