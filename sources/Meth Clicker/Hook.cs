using System;
using System.Drawing;
using System.Reflection;
using System.Runtime.InteropServices;

namespace Meth
{
	// Token: 0x02000009 RID: 9
	public class Hook
	{
		// Token: 0x0600001E RID: 30
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "SetWindowsHookExA", ExactSpelling = true, SetLastError = true)]
		private static extern int SetWindowsHookEx(int idHook, Hook.MouseProcDelegate lpfn, int hmod, int dwThreadId);

		// Token: 0x0600001F RID: 31
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern int CallNextHookEx(int hHook, int nCode, int wParam, Hook.MSLLHOOKSTRUCT lParam);

		// Token: 0x06000020 RID: 32
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern int UnhookWindowsHookEx(int hHook);

		// Token: 0x14000001 RID: 1
		// (add) Token: 0x06000021 RID: 33 RVA: 0x00002568 File Offset: 0x00000768
		// (remove) Token: 0x06000022 RID: 34 RVA: 0x000025A0 File Offset: 0x000007A0
		public event Hook.Mouse_MoveEventHandler Mouse_Move;

		// Token: 0x14000002 RID: 2
		// (add) Token: 0x06000023 RID: 35 RVA: 0x000025D8 File Offset: 0x000007D8
		// (remove) Token: 0x06000024 RID: 36 RVA: 0x00002610 File Offset: 0x00000810
		public event Hook.Mouse_Left_DownEventHandler Mouse_Left_Down;

		// Token: 0x14000003 RID: 3
		// (add) Token: 0x06000025 RID: 37 RVA: 0x00002648 File Offset: 0x00000848
		// (remove) Token: 0x06000026 RID: 38 RVA: 0x00002680 File Offset: 0x00000880
		public event Hook.Mouse_Left_UpEventHandler Mouse_Left_Up;

		// Token: 0x14000004 RID: 4
		// (add) Token: 0x06000027 RID: 39 RVA: 0x000026B8 File Offset: 0x000008B8
		// (remove) Token: 0x06000028 RID: 40 RVA: 0x000026F0 File Offset: 0x000008F0
		public event Hook.Mouse_Left_DoubleClickEventHandler Mouse_Left_DoubleClick;

		// Token: 0x14000005 RID: 5
		// (add) Token: 0x06000029 RID: 41 RVA: 0x00002728 File Offset: 0x00000928
		// (remove) Token: 0x0600002A RID: 42 RVA: 0x00002760 File Offset: 0x00000960
		public event Hook.Mouse_Right_DownEventHandler Mouse_Right_Down;

		// Token: 0x14000006 RID: 6
		// (add) Token: 0x0600002B RID: 43 RVA: 0x00002798 File Offset: 0x00000998
		// (remove) Token: 0x0600002C RID: 44 RVA: 0x000027D0 File Offset: 0x000009D0
		public event Hook.Mouse_Right_UpEventHandler Mouse_Right_Up;

		// Token: 0x14000007 RID: 7
		// (add) Token: 0x0600002D RID: 45 RVA: 0x00002808 File Offset: 0x00000A08
		// (remove) Token: 0x0600002E RID: 46 RVA: 0x00002840 File Offset: 0x00000A40
		public event Hook.Mouse_Right_DoubleClickEventHandler Mouse_Right_DoubleClick;

		// Token: 0x14000008 RID: 8
		// (add) Token: 0x0600002F RID: 47 RVA: 0x00002878 File Offset: 0x00000A78
		// (remove) Token: 0x06000030 RID: 48 RVA: 0x000028B0 File Offset: 0x00000AB0
		public event Hook.Mouse_Middle_DownEventHandler Mouse_Middle_Down;

		// Token: 0x14000009 RID: 9
		// (add) Token: 0x06000031 RID: 49 RVA: 0x000028E8 File Offset: 0x00000AE8
		// (remove) Token: 0x06000032 RID: 50 RVA: 0x00002920 File Offset: 0x00000B20
		public event Hook.Mouse_Middle_UpEventHandler Mouse_Middle_Up;

		// Token: 0x1400000A RID: 10
		// (add) Token: 0x06000033 RID: 51 RVA: 0x00002958 File Offset: 0x00000B58
		// (remove) Token: 0x06000034 RID: 52 RVA: 0x00002990 File Offset: 0x00000B90
		public event Hook.Mouse_Middle_DoubleClickEventHandler Mouse_Middle_DoubleClick;

		// Token: 0x1400000B RID: 11
		// (add) Token: 0x06000035 RID: 53 RVA: 0x000029C8 File Offset: 0x00000BC8
		// (remove) Token: 0x06000036 RID: 54 RVA: 0x00002A00 File Offset: 0x00000C00
		public event Hook.Mouse_WheelEventHandler Mouse_Wheel;

		// Token: 0x06000038 RID: 56 RVA: 0x00002A40 File Offset: 0x00000C40
		private int MouseProc(int nCode, int wParam, ref Hook.MSLLHOOKSTRUCT lParam)
		{
			if (nCode == 0)
			{
				switch (wParam)
				{
				case 512:
				{
					Hook.Mouse_MoveEventHandler mouse_MoveEvent = this.Mouse_MoveEvent;
					if (mouse_MoveEvent != null)
					{
						mouse_MoveEvent(lParam.pt);
					}
					break;
				}
				case 513:
				{
					Hook.Mouse_Left_DownEventHandler mouse_Left_DownEvent = this.Mouse_Left_DownEvent;
					if (mouse_Left_DownEvent != null)
					{
						mouse_Left_DownEvent(lParam.pt);
					}
					break;
				}
				case 514:
				{
					Hook.Mouse_Left_UpEventHandler mouse_Left_UpEvent = this.Mouse_Left_UpEvent;
					if (mouse_Left_UpEvent != null)
					{
						mouse_Left_UpEvent(lParam.pt);
					}
					break;
				}
				case 515:
				{
					Hook.Mouse_Left_DoubleClickEventHandler mouse_Left_DoubleClickEvent = this.Mouse_Left_DoubleClickEvent;
					if (mouse_Left_DoubleClickEvent != null)
					{
						mouse_Left_DoubleClickEvent(lParam.pt);
					}
					break;
				}
				case 516:
				{
					Hook.Mouse_Right_DownEventHandler mouse_Right_DownEvent = this.Mouse_Right_DownEvent;
					if (mouse_Right_DownEvent != null)
					{
						mouse_Right_DownEvent(lParam.pt);
					}
					break;
				}
				case 517:
				{
					Hook.Mouse_Right_UpEventHandler mouse_Right_UpEvent = this.Mouse_Right_UpEvent;
					if (mouse_Right_UpEvent != null)
					{
						mouse_Right_UpEvent(lParam.pt);
					}
					break;
				}
				case 518:
				{
					Hook.Mouse_Right_DoubleClickEventHandler mouse_Right_DoubleClickEvent = this.Mouse_Right_DoubleClickEvent;
					if (mouse_Right_DoubleClickEvent != null)
					{
						mouse_Right_DoubleClickEvent(lParam.pt);
					}
					break;
				}
				case 519:
				{
					Hook.Mouse_Middle_DownEventHandler mouse_Middle_DownEvent = this.Mouse_Middle_DownEvent;
					if (mouse_Middle_DownEvent != null)
					{
						mouse_Middle_DownEvent(lParam.pt);
					}
					break;
				}
				case 520:
				{
					Hook.Mouse_Middle_UpEventHandler mouse_Middle_UpEvent = this.Mouse_Middle_UpEvent;
					if (mouse_Middle_UpEvent != null)
					{
						mouse_Middle_UpEvent(lParam.pt);
					}
					break;
				}
				case 521:
				{
					Hook.Mouse_Middle_DoubleClickEventHandler mouse_Middle_DoubleClickEvent = this.Mouse_Middle_DoubleClickEvent;
					if (mouse_Middle_DoubleClickEvent != null)
					{
						mouse_Middle_DoubleClickEvent(lParam.pt);
					}
					break;
				}
				case 522:
				{
					Hook.Wheel_Direction direction;
					if (lParam.mouseData < 0)
					{
						direction = Hook.Wheel_Direction.WheelDown;
					}
					else
					{
						direction = Hook.Wheel_Direction.WheelUp;
					}
					Hook.Mouse_WheelEventHandler mouse_WheelEvent = this.Mouse_WheelEvent;
					if (mouse_WheelEvent != null)
					{
						mouse_WheelEvent(lParam.pt, direction);
					}
					break;
				}
				}
			}
			return Hook.CallNextHookEx(this.MouseHook, nCode, wParam, lParam);
		}

		// Token: 0x06000039 RID: 57 RVA: 0x00002BFD File Offset: 0x00000DFD
		protected override void Finalize()
		{
			Hook.UnhookWindowsHookEx(this.MouseHook);
			base.Finalize();
		}

		// Token: 0x0600003A RID: 58 RVA: 0x00002C14 File Offset: 0x00000E14
		public void HookMouse()
		{
			this.MouseHookDelegate = new Hook.MouseProcDelegate(this.MouseProc);
			this.MouseHook = Hook.SetWindowsHookEx(14, this.MouseHookDelegate, Marshal.GetHINSTANCE(Assembly.GetExecutingAssembly().GetModules()[0]).ToInt32(), 0);
		}

		// Token: 0x0600003B RID: 59 RVA: 0x00002C60 File Offset: 0x00000E60
		public void UnhookMouse()
		{
			Hook.UnhookWindowsHookEx(this.MouseHook);
		}

		// Token: 0x04000013 RID: 19
		private const int HC_ACTION = 0;

		// Token: 0x04000014 RID: 20
		private const int WH_MOUSE_LL = 14;

		// Token: 0x04000015 RID: 21
		private const int WM_MOUSEMOVE = 512;

		// Token: 0x04000016 RID: 22
		private const int WM_LBUTTONDOWN = 513;

		// Token: 0x04000017 RID: 23
		private const int WM_LBUTTONUP = 514;

		// Token: 0x04000018 RID: 24
		private const int WM_LBUTTONDBLCLK = 515;

		// Token: 0x04000019 RID: 25
		private const int WM_RBUTTONDOWN = 516;

		// Token: 0x0400001A RID: 26
		private const int WM_RBUTTONUP = 517;

		// Token: 0x0400001B RID: 27
		private const int WM_RBUTTONDBLCLK = 518;

		// Token: 0x0400001C RID: 28
		private const int WM_MBUTTONDOWN = 519;

		// Token: 0x0400001D RID: 29
		private const int WM_MBUTTONUP = 520;

		// Token: 0x0400001E RID: 30
		private const int WM_MBUTTONDBLCLK = 521;

		// Token: 0x0400001F RID: 31
		private const int WM_MOUSEWHEEL = 522;

		// Token: 0x04000020 RID: 32
		private int MouseHook;

		// Token: 0x04000021 RID: 33
		private Hook.MouseProcDelegate MouseHookDelegate;

		// Token: 0x02000033 RID: 51
		// (Invoke) Token: 0x0600033A RID: 826
		private delegate int MouseProcDelegate(int nCode, int wParam, ref Hook.MSLLHOOKSTRUCT lParam);

		// Token: 0x02000034 RID: 52
		private struct MSLLHOOKSTRUCT
		{
			// Token: 0x0400019F RID: 415
			public Point pt;

			// Token: 0x040001A0 RID: 416
			public int mouseData;

			// Token: 0x040001A1 RID: 417
			public int flags;

			// Token: 0x040001A2 RID: 418
			public int time;

			// Token: 0x040001A3 RID: 419
			public int dwExtraInfo;
		}

		// Token: 0x02000035 RID: 53
		public enum Wheel_Direction
		{
			// Token: 0x040001A5 RID: 421
			WheelUp,
			// Token: 0x040001A6 RID: 422
			WheelDown
		}

		// Token: 0x02000036 RID: 54
		// (Invoke) Token: 0x0600033E RID: 830
		public delegate void Mouse_MoveEventHandler(Point ptLocat);

		// Token: 0x02000037 RID: 55
		// (Invoke) Token: 0x06000342 RID: 834
		public delegate void Mouse_Left_DownEventHandler(Point ptLocat);

		// Token: 0x02000038 RID: 56
		// (Invoke) Token: 0x06000346 RID: 838
		public delegate void Mouse_Left_UpEventHandler(Point ptLocat);

		// Token: 0x02000039 RID: 57
		// (Invoke) Token: 0x0600034A RID: 842
		public delegate void Mouse_Left_DoubleClickEventHandler(Point ptLocat);

		// Token: 0x0200003A RID: 58
		// (Invoke) Token: 0x0600034E RID: 846
		public delegate void Mouse_Right_DownEventHandler(Point ptLocat);

		// Token: 0x0200003B RID: 59
		// (Invoke) Token: 0x06000352 RID: 850
		public delegate void Mouse_Right_UpEventHandler(Point ptLocat);

		// Token: 0x0200003C RID: 60
		// (Invoke) Token: 0x06000356 RID: 854
		public delegate void Mouse_Right_DoubleClickEventHandler(Point ptLocat);

		// Token: 0x0200003D RID: 61
		// (Invoke) Token: 0x0600035A RID: 858
		public delegate void Mouse_Middle_DownEventHandler(Point ptLocat);

		// Token: 0x0200003E RID: 62
		// (Invoke) Token: 0x0600035E RID: 862
		public delegate void Mouse_Middle_UpEventHandler(Point ptLocat);

		// Token: 0x0200003F RID: 63
		// (Invoke) Token: 0x06000362 RID: 866
		public delegate void Mouse_Middle_DoubleClickEventHandler(Point ptLocat);

		// Token: 0x02000040 RID: 64
		// (Invoke) Token: 0x06000366 RID: 870
		public delegate void Mouse_WheelEventHandler(Point ptLocat, Hook.Wheel_Direction Direction);
	}
}
