using System;
using System.ComponentModel;

namespace SnagFree.TrayApp.Core
{
	// Token: 0x02000002 RID: 2
	internal class GlobalKeyboardHookEventArgs : HandledEventArgs
	{
		// Token: 0x17000001 RID: 1
		// (get) Token: 0x06000001 RID: 1 RVA: 0x00002050 File Offset: 0x00000250
		// (set) Token: 0x06000002 RID: 2 RVA: 0x00002058 File Offset: 0x00000258
		public GlobalKeyboardHook.KeyboardState KeyboardState { get; private set; }

		// Token: 0x17000002 RID: 2
		// (get) Token: 0x06000003 RID: 3 RVA: 0x00002061 File Offset: 0x00000261
		// (set) Token: 0x06000004 RID: 4 RVA: 0x00002069 File Offset: 0x00000269
		public GlobalKeyboardHook.LowLevelKeyboardInputEvent KeyboardData { get; private set; }

		// Token: 0x06000005 RID: 5 RVA: 0x00002072 File Offset: 0x00000272
		public GlobalKeyboardHookEventArgs(GlobalKeyboardHook.LowLevelKeyboardInputEvent keyboardData, GlobalKeyboardHook.KeyboardState keyboardState)
		{
			this.KeyboardData = keyboardData;
			this.KeyboardState = keyboardState;
		}
	}
}
