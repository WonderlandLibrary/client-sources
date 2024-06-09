using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Drawing;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Threading;

namespace Xh0kO1ZCmA
{
	public class MouseHook : IDisposable
	{
		private const int HC_ACTION = 0;

		private const int WH_MOUSE_LL = 14;

		private const int WM_MOUSEMOVE = 512;

		private const int WM_LBUTTONDOWN = 513;

		private const int WM_LBUTTONUP = 514;

		private const int WM_LBUTTONDBLCLK = 515;

		private const int WM_RBUTTONDOWN = 516;

		private const int WM_RBUTTONUP = 517;

		private const int WM_RBUTTONDBLCLK = 518;

		private const int WM_MBUTTONDOWN = 519;

		private const int WM_MBUTTONUP = 520;

		private const int WM_MBUTTONDBLCLK = 521;

		private const int WM_MOUSEWHEEL = 522;

		private int MouseHook;

		private Xh0kO1ZCmA.MouseHook.MouseProcDelegate MouseHookDelegate;

		public MouseHook()
		{
			this.MouseHookDelegate = new Xh0kO1ZCmA.MouseHook.MouseProcDelegate(this.MouseProc);
			Xh0kO1ZCmA.MouseHook.MouseProcDelegate mouseHookDelegate = this.MouseHookDelegate;
			IntPtr hINSTANCE = Marshal.GetHINSTANCE(Assembly.GetExecutingAssembly().GetModules()[0]);
			this.MouseHook = Xh0kO1ZCmA.MouseHook.SetWindowsHookEx(14, mouseHookDelegate, hINSTANCE.ToInt32(), 0);
		}

		[DllImport("user32.dll", CharSet=CharSet.None, ExactSpelling=false)]
		private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode, IntPtr wParam, IntPtr lParam);

		public void Dispose()
		{
			try
			{
				Xh0kO1ZCmA.MouseHook.UnhookWindowsHookEx((IntPtr)this.MouseHook);
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				ProjectData.ClearProjectError();
			}
		}

		protected override void Finalize()
		{
			Xh0kO1ZCmA.MouseHook.UnhookWindowsHookEx((IntPtr)this.MouseHook);
			this.Finalize();
		}

		private int MouseProc(int nCode, int wParam, ref Xh0kO1ZCmA.MouseHook.MSLLHOOKSTRUCT lParam)
		{
			if (nCode == 0)
			{
				switch (wParam)
				{
					case 513:
					{
						Xh0kO1ZCmA.MouseHook.Mouse_Left_DownEventHandler mouseLeftDownEventHandler = this.Mouse_Left_Down;
						if (mouseLeftDownEventHandler == null)
						{
							break;
						}
						mouseLeftDownEventHandler(lParam.pt);
						break;
					}
					case 514:
					{
						Xh0kO1ZCmA.MouseHook.Mouse_Left_UpEventHandler mouseLeftUpEventHandler = this.Mouse_Left_Up;
						if (mouseLeftUpEventHandler == null)
						{
							break;
						}
						mouseLeftUpEventHandler(lParam.pt);
						break;
					}
					case 515:
					{
						Xh0kO1ZCmA.MouseHook.Mouse_Left_DoubleClickEventHandler mouseLeftDoubleClickEventHandler = this.Mouse_Left_DoubleClick;
						if (mouseLeftDoubleClickEventHandler == null)
						{
							break;
						}
						mouseLeftDoubleClickEventHandler(lParam.pt);
						break;
					}
				}
			}
			return (int)Xh0kO1ZCmA.MouseHook.CallNextHookEx((IntPtr)this.MouseHook, nCode, (IntPtr)wParam, (IntPtr)lParam.dwExtraInfo);
		}

		[DllImport("user32", CharSet=CharSet.Ansi, EntryPoint="SetWindowsHookExA", ExactSpelling=true, SetLastError=true)]
		private static extern int SetWindowsHookEx(int idHook, Xh0kO1ZCmA.MouseHook.MouseProcDelegate lpfn, int hmod, int dwThreadId);

		[DllImport("user32", CharSet=CharSet.Ansi, ExactSpelling=true, SetLastError=true)]
		private static extern int UnhookWindowsHookEx(IntPtr hHook);

		public event Xh0kO1ZCmA.MouseHook.Mouse_Left_DoubleClickEventHandler Mouse_Left_DoubleClick;

		public event Xh0kO1ZCmA.MouseHook.Mouse_Left_DownEventHandler Mouse_Left_Down;

		public event Xh0kO1ZCmA.MouseHook.Mouse_Left_UpEventHandler Mouse_Left_Up;

		public event Xh0kO1ZCmA.MouseHook.Mouse_Middle_DoubleClickEventHandler Mouse_Middle_DoubleClick;

		public event Xh0kO1ZCmA.MouseHook.Mouse_Middle_DownEventHandler Mouse_Middle_Down;

		public event Xh0kO1ZCmA.MouseHook.Mouse_Middle_UpEventHandler Mouse_Middle_Up;

		public event Xh0kO1ZCmA.MouseHook.Mouse_MoveEventHandler Mouse_Move;

		public event Xh0kO1ZCmA.MouseHook.Mouse_Right_DoubleClickEventHandler Mouse_Right_DoubleClick;

		public event Xh0kO1ZCmA.MouseHook.Mouse_Right_DownEventHandler Mouse_Right_Down;

		public event Xh0kO1ZCmA.MouseHook.Mouse_Right_UpEventHandler Mouse_Right_Up;

		public event Xh0kO1ZCmA.MouseHook.Mouse_WheelEventHandler Mouse_Wheel;

		public delegate void Mouse_Left_DoubleClickEventHandler(Point ptLocat);

		public delegate void Mouse_Left_DownEventHandler(Point ptLocat);

		public delegate void Mouse_Left_UpEventHandler(Point ptLocat);

		public delegate void Mouse_Middle_DoubleClickEventHandler(Point ptLocat);

		public delegate void Mouse_Middle_DownEventHandler(Point ptLocat);

		public delegate void Mouse_Middle_UpEventHandler(Point ptLocat);

		public delegate void Mouse_MoveEventHandler(Point ptLocat);

		public delegate void Mouse_Right_DoubleClickEventHandler(Point ptLocat);

		public delegate void Mouse_Right_DownEventHandler(Point ptLocat);

		public delegate void Mouse_Right_UpEventHandler(Point ptLocat);

		public delegate void Mouse_WheelEventHandler(Point ptLocat, Xh0kO1ZCmA.MouseHook.Wheel_Direction Direction);

		private delegate int MouseProcDelegate(int nCode, int wParam, ref Xh0kO1ZCmA.MouseHook.MSLLHOOKSTRUCT lParam);

		private struct MSLLHOOKSTRUCT
		{
			public Point pt;

			public int mouseData;

			public int flags;

			public int time;

			public int dwExtraInfo;
		}

		public enum Wheel_Direction
		{
			WheelUp,
			WheelDown
		}
	}
}