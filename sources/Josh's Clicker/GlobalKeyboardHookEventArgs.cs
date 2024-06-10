using System;
using System.ComponentModel;

namespace dotClick
{
	// Token: 0x02000003 RID: 3
	internal class GlobalKeyboardHookEventArgs : HandledEventArgs
	{
		// Token: 0x17000001 RID: 1
		// (get) Token: 0x06000004 RID: 4 RVA: 0x00002061 File Offset: 0x00000261
		// (set) Token: 0x06000005 RID: 5 RVA: 0x00002069 File Offset: 0x00000269
		public GlobalKeyboardHook.KeyboardState KeyboardState { get; private set; }

		// Token: 0x17000002 RID: 2
		// (get) Token: 0x06000006 RID: 6 RVA: 0x00002072 File Offset: 0x00000272
		// (set) Token: 0x06000007 RID: 7 RVA: 0x0000207A File Offset: 0x0000027A
		public GlobalKeyboardHook.LowLevelKeyboardInputEvent KeyboardData { get; private set; }

		// Token: 0x06000008 RID: 8 RVA: 0x00002083 File Offset: 0x00000283
		public GlobalKeyboardHookEventArgs(GlobalKeyboardHook.LowLevelKeyboardInputEvent keyboardData, GlobalKeyboardHook.KeyboardState keyboardState)
		{
			this.KeyboardData = keyboardData;
			this.KeyboardState = keyboardState;
		}
	}
}
