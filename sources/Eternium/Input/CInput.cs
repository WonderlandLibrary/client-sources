using System;
using System.Threading;
using Eternium_mcpe_client.DllImports;
using Eternium_mcpe_client.Hotkeys;

namespace Eternium_mcpe_client.Input
{
	// Token: 0x02000010 RID: 16
	public static class CInput
	{
		// Token: 0x06000052 RID: 82 RVA: 0x00005BF4 File Offset: 0x00003DF4
		private static void ThreadLoop()
		{
			for (;;)
			{
				CInput.SavedState_VK_Control = CInput.State_VK_Control;
				CInput.SavedState_VK_RightShift = CInput.State_VK_RightShift;
				CInput.State_VK_Control = Hotkeys.GetKeyStateDown(DllImports.VirtualKeys.Control);
				CInput.State_VK_RightShift = Hotkeys.GetKeyStateDown(DllImports.VirtualKeys.RightShift);
				Thread.Sleep(25);
			}
		}

		// Token: 0x06000053 RID: 83 RVA: 0x00005C40 File Offset: 0x00003E40
		public static void StartThread()
		{
			CInput.thread = new Thread(new ThreadStart(CInput.ThreadLoop));
			CInput.thread.Priority = ThreadPriority.AboveNormal;
			CInput.thread.Start();
		}

		// Token: 0x06000054 RID: 84 RVA: 0x00005C70 File Offset: 0x00003E70
		public static bool GetKeyStateControlPressed()
		{
			bool result = false;
			bool flag = !CInput.State_VK_Control && CInput.SavedState_VK_Control;
			if (flag)
			{
				result = true;
			}
			return result;
		}

		// Token: 0x06000055 RID: 85 RVA: 0x00005C9C File Offset: 0x00003E9C
		public static bool GetKeyStateRightShiftPressed()
		{
			bool result = false;
			bool flag = !CInput.State_VK_RightShift && CInput.SavedState_VK_RightShift;
			if (flag)
			{
				result = true;
			}
			return result;
		}

		// Token: 0x04000043 RID: 67
		private static Thread thread;

		// Token: 0x04000044 RID: 68
		private static bool State_VK_Control = false;

		// Token: 0x04000045 RID: 69
		private static bool State_VK_RightShift = false;

		// Token: 0x04000046 RID: 70
		private static bool SavedState_VK_Control = false;

		// Token: 0x04000047 RID: 71
		private static bool SavedState_VK_RightShift = false;
	}
}
