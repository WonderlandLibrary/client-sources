package org.lwjgl.opengl;











class StateStack
{
  private int[] state_stack;
  









  private int stack_pos;
  









  public int getState()
  {
    return state_stack[stack_pos];
  }
  
  public void pushState(int new_state) {
    int pos = ++stack_pos;
    if (pos == state_stack.length) {
      growState();
    }
    state_stack[pos] = new_state;
  }
  
  public int popState() {
    return state_stack[(stack_pos--)];
  }
  
  public void growState() {
    int[] new_state_stack = new int[state_stack.length + 1];
    System.arraycopy(state_stack, 0, new_state_stack, 0, state_stack.length);
    state_stack = new_state_stack;
  }
  
  StateStack(int initial_value) {
    state_stack = new int[1];
    stack_pos = 0;
    state_stack[stack_pos] = initial_value;
  }
}
