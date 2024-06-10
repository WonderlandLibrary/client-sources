using System;
using System.Reflection;
using System.Runtime.InteropServices;

namespace Action_Installer
{
	// Token: 0x02000003 RID: 3
	public class mH
	{
		// Token: 0x0200000A RID: 10
		public class mH
		{
			// Token: 0x06000044 RID: 68
			[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "SetWindowsHookExA", ExactSpelling = true, SetLastError = true)]
			private static extern int SetWindowsHookEx(int idHook, Action_Installer.mH.mH.MouseProcDelegate lpfn, int hmod, int dwThreadId);

			// Token: 0x06000045 RID: 69
			[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
			private static extern int CallNextHookEx(int hHook, int nCode, int wParam, Action_Installer.mH.mH.MSLLHOOKSTRUCT lParam);

			// Token: 0x06000046 RID: 70
			[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
			private static extern int UnhookWindowsHookEx(int hHook);

			// Token: 0x14000001 RID: 1
			// (add) Token: 0x06000047 RID: 71 RVA: 0x00003B08 File Offset: 0x00001D08
			// (remove) Token: 0x06000048 RID: 72 RVA: 0x00003B40 File Offset: 0x00001D40
			public event Action_Installer.mH.mH.mldEventHandler mld;

			// Token: 0x14000002 RID: 2
			// (add) Token: 0x06000049 RID: 73 RVA: 0x00003B78 File Offset: 0x00001D78
			// (remove) Token: 0x0600004A RID: 74 RVA: 0x00003BB0 File Offset: 0x00001DB0
			public event Action_Installer.mH.mH.mluEventHandler mlu;

			// Token: 0x0600004B RID: 75 RVA: 0x00003BE8 File Offset: 0x00001DE8
			public mH()
			{
				this.MouseHookDelegate = new Action_Installer.mH.mH.MouseProcDelegate(this.MouseProc);
				this.MouseHook = Action_Installer.mH.mH.SetWindowsHookEx(14, this.MouseHookDelegate, Marshal.GetHINSTANCE(Assembly.GetExecutingAssembly().GetModules()[0]).ToInt32(), 0);
			}

			// Token: 0x0600004C RID: 76 RVA: 0x00003C3C File Offset: 0x00001E3C
			private int MouseProc(int nCode, int wParam, ref Action_Installer.mH.mH.MSLLHOOKSTRUCT lParam)
			{
				bool flag = nCode == 0;
				if (flag)
				{
					if (wParam != 513)
					{
						if (wParam == 514)
						{
							Action_Installer.mH.mH.mluEventHandler mluEventHandler = this.mluEvent;
							if (mluEventHandler != null)
							{
								mluEventHandler();
							}
						}
					}
					else
					{
						Action_Installer.mH.mH.mldEventHandler mldEventHandler = this.mldEvent;
						if (mldEventHandler != null)
						{
							mldEventHandler();
						}
					}
				}
				return 0;
			}

			// Token: 0x0600004D RID: 77 RVA: 0x00003C9C File Offset: 0x00001E9C
			protected override void Finalize()
			{
				Action_Installer.mH.mH.UnhookWindowsHookEx(this.MouseHook);
				base.Finalize();
			}

			// Token: 0x04000026 RID: 38
			private const int HC_ACTION = 0;

			// Token: 0x04000027 RID: 39
			private const int WH_MOUSE_LL = 14;

			// Token: 0x04000028 RID: 40
			private const int WM_LBUTTONDOWN = 513;

			// Token: 0x04000029 RID: 41
			private const int WM_LBUTTONUP = 514;

			// Token: 0x0400002A RID: 42
			private int MouseHook;

			// Token: 0x0400002B RID: 43
			private Action_Installer.mH.mH.MouseProcDelegate MouseHookDelegate;

			// Token: 0x0200000E RID: 14
			// (Invoke) Token: 0x06000063 RID: 99
			private delegate int MouseProcDelegate(int nCode, int wParam, ref Action_Installer.mH.mH.MSLLHOOKSTRUCT lParam);

			// Token: 0x0200000F RID: 15
			private struct MSLLHOOKSTRUCT
			{
				// Token: 0x04000031 RID: 49
				public int mouseData;

				// Token: 0x04000032 RID: 50
				public int flags;

				// Token: 0x04000033 RID: 51
				public int time;

				// Token: 0x04000034 RID: 52
				public int dwExtraInfo;
			}

			// Token: 0x02000010 RID: 16
			// (Invoke) Token: 0x06000067 RID: 103
			public delegate void mldEventHandler();

			// Token: 0x02000011 RID: 17
			// (Invoke) Token: 0x0600006B RID: 107
			public delegate void mluEventHandler();
		}
	}
}
