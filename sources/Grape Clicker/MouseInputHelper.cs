using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	public sealed class MouseInputHelper
	{
		private MouseInputHelper()
		{
		}

		public static void SendMouseClick(MouseButtons Button, Point Location, bool MouseDown)
		{
			IntPtr intPtr = MouseInputHelper.NativeMethods.WindowFromPoint(new MouseInputHelper.NativeMethods.NATIVEPOINT(Location.X, Location.Y));
			MouseInputHelper.NativeMethods.MouseButtonMessages mouseButtonMessage = MouseInputHelper.NativeMethods.MouseButtonMessages.None;
			MouseButtons button = Button;
			if (button <= MouseButtons.Right)
			{
				if (button == MouseButtons.Left)
				{
					mouseButtonMessage = MouseInputHelper.NativeMethods.MouseButtonMessages.WM_LBUTTONDOWN;
				}
				else
				{
					if (button != MouseButtons.Right)
					{
						throw new InvalidOperationException(string.Concat("Invalid mouse button ", Button.ToString()));
					}
					mouseButtonMessage = MouseInputHelper.NativeMethods.MouseButtonMessages.WM_RBUTTONDOWN;
				}
			}
			else if (button == MouseButtons.Middle)
			{
				mouseButtonMessage = MouseInputHelper.NativeMethods.MouseButtonMessages.WM_MBUTTONDOWN;
			}
			else
			{
				if (button != MouseButtons.XButton1 && button != MouseButtons.XButton2)
				{
					throw new InvalidOperationException(string.Concat("Invalid mouse button ", Button.ToString()));
				}
				mouseButtonMessage = MouseInputHelper.NativeMethods.MouseButtonMessages.WM_XBUTTONDOWN;
			}
			MouseInputHelper.NativeMethods.NATIVEPOINT nATIVEPOINT = new MouseInputHelper.NativeMethods.NATIVEPOINT(Location.X, Location.Y);
			if (!MouseInputHelper.NativeMethods.ScreenToClient(intPtr, ref nATIVEPOINT))
			{
				throw new Exception(string.Concat("Unable to convert screen coordinates to client coordinates! Win32Err: ", Conversions.ToString(Marshal.GetLastWin32Error())));
			}
			IntPtr zero = IntPtr.Zero;
			IntPtr intPtr1 = MouseInputHelper.NativeMethods.CreateLWParam(nATIVEPOINT.X, nATIVEPOINT.Y);
			if (Button == MouseButtons.XButton1 || Button == MouseButtons.XButton2)
			{
				zero = MouseInputHelper.NativeMethods.CreateLWParam(0, checked((int)Math.Round((double)Button / 8388608)));
			}
			MouseInputHelper.NativeMethods.PostMessage(intPtr, checked((uint)(checked((int)mouseButtonMessage + (int)((MouseDown ? MouseInputHelper.NativeMethods.MouseButtonMessages.None : MouseInputHelper.NativeMethods.MouseButtonMessages.XBUTTON1))))), zero, intPtr1);
		}

		private sealed class NativeMethods
		{
			private NativeMethods()
			{
			}

			public static IntPtr CreateLWParam(int LoWord, int HiWord)
			{
				return new IntPtr(HiWord << 16 | LoWord & 65535);
			}

			[DllImport("user32.dll", CharSet=CharSet.Auto, ExactSpelling=false, SetLastError=true)]
			public static extern IntPtr PostMessage(IntPtr hWnd, uint Msg, IntPtr wParam, IntPtr lParam);

			[DllImport("user32.dll", CharSet=CharSet.None, ExactSpelling=false, SetLastError=true)]
			public static extern bool ScreenToClient(IntPtr hWnd, ref MouseInputHelper.NativeMethods.NATIVEPOINT lpPoint);

			[DllImport("user32.dll", CharSet=CharSet.None, ExactSpelling=false, SetLastError=true)]
			public static extern IntPtr WindowFromPoint(MouseInputHelper.NativeMethods.NATIVEPOINT p);

			public enum MouseButtonMessages
			{
				None = 0,
				XBUTTON1 = 1,
				XBUTTON2 = 2,
				WM_LBUTTONDOWN = 513,
				WM_LBUTTONUP = 514,
				WM_RBUTTONDOWN = 516,
				WM_RBUTTONUP = 517,
				WM_MBUTTONDOWN = 519,
				WM_MBUTTONUP = 520,
				WM_XBUTTONDOWN = 523,
				WM_XBUTTONUP = 524
			}

			public struct NATIVEPOINT
			{
				public int X;

				public int Y;

				public NATIVEPOINT(int X, int Y)
				{
					this = new MouseInputHelper.NativeMethods.NATIVEPOINT()
					{
						X = X,
						Y = Y
					};
				}
			}
		}
	}
}