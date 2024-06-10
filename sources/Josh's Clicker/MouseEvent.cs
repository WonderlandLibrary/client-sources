using System;
using System.Runtime.InteropServices;
using System.Threading;

namespace dotClick
{
	// Token: 0x0200000D RID: 13
	internal class MouseEvent
	{
		// Token: 0x06000057 RID: 87
		[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(uint dwFlags, uint dx, uint dy, uint cButtons, uint dwExtraInfo);

		// Token: 0x06000058 RID: 88 RVA: 0x00002278 File Offset: 0x00000478
		public void runLeftDown()
		{
			new Thread(delegate()
			{
				MouseEvent.mouse_event(2U, 0U, 0U, 0U, 0U);
			}).Start();
		}

		// Token: 0x06000059 RID: 89 RVA: 0x000022A3 File Offset: 0x000004A3
		public void runLeftUp()
		{
			new Thread(delegate()
			{
				MouseEvent.mouse_event(4U, 0U, 0U, 0U, 0U);
			}).Start();
		}

		// Token: 0x0600005A RID: 90 RVA: 0x000022CE File Offset: 0x000004CE
		public void runRightDown()
		{
			new Thread(delegate()
			{
				MouseEvent.mouse_event(8U, 0U, 0U, 0U, 0U);
			}).Start();
		}

		// Token: 0x0600005B RID: 91 RVA: 0x000022F9 File Offset: 0x000004F9
		public void runRightUp()
		{
			new Thread(delegate()
			{
				MouseEvent.mouse_event(16U, 0U, 0U, 0U, 0U);
			}).Start();
		}

		// Token: 0x0400004E RID: 78
		private const uint MOUSEEVENTF_LEFTDOWN = 2U;

		// Token: 0x0400004F RID: 79
		private const uint MOUSEEVENTF_LEFTUP = 4U;

		// Token: 0x04000050 RID: 80
		private const uint MOUSEEVENTF_RIGHTDOWN = 8U;

		// Token: 0x04000051 RID: 81
		private const uint MOUSEEVENTF_RIGHTUP = 16U;
	}
}
