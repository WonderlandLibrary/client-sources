using Microsoft.VisualBasic;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	public class DataSession
	{
		public DateTime StartingTime;

		public List<OneAction> EventList;

		public DateTime LastEvent;

		private DateTime teapTime;

		private OneAction tempAction;

		private bool EnableEvent;

		private Control InvokeBy;

		public DataSession(bool _EnableEvent = false, ref Control _InvokeBy = null)
		{
			this.EnableEvent = _EnableEvent;
			this.InvokeBy = _InvokeBy;
			this.StartingTime = DateAndTime.Now;
			this.LastEvent = DateAndTime.Now;
			this.EventList = new List<OneAction>();
		}

		public void AddKeyboradEvent(Keys Downkey)
		{
			this.teapTime = DateAndTime.Now;
			TimeSpan lastEvent = this.teapTime - this.LastEvent;
			this.tempAction = new KeyAction(Downkey, checked((int)Math.Round(lastEvent.TotalMilliseconds)));
			this.EventList.Add(this.tempAction);
			this.LastEvent = this.teapTime;
			if (this.EnableEvent)
			{
				this.DoEvent(ref this.tempAction);
			}
		}

		public void AddMouseEvent(FullHook.MouseState ActionState, Point p)
		{
			this.teapTime = DateAndTime.Now;
			TimeSpan lastEvent = this.teapTime - this.LastEvent;
			this.tempAction = new MouseAction((MouseAction.MouseAction)ActionState, p, checked((int)Math.Round(lastEvent.TotalMilliseconds)), MouseHook.Wheel_Direction.WheelUp);
			this.EventList.Add(this.tempAction);
			this.LastEvent = this.teapTime;
			if (this.EnableEvent)
			{
				this.DoEvent(ref this.tempAction);
			}
		}

		public void AddScrolEvent(MouseHook.Wheel_Direction dir, Point p)
		{
			this.teapTime = DateAndTime.Now;
			TimeSpan lastEvent = this.teapTime - this.LastEvent;
			this.tempAction = new MouseAction(MouseAction.MouseAction.S, p, checked((int)Math.Round(lastEvent.TotalMilliseconds)), dir);
			this.EventList.Add(this.tempAction);
			this.LastEvent = this.teapTime;
			if (this.EnableEvent)
			{
				this.DoEvent(ref this.tempAction);
			}
		}

		public void AddWinEvent(FullHook.WinState State, string WinName, string ProName)
		{
			this.teapTime = DateAndTime.Now;
			TimeSpan lastEvent = this.teapTime - this.LastEvent;
			this.tempAction = new WinAction(State, WinName, ProName, checked((int)Math.Round(lastEvent.TotalMilliseconds)));
			this.EventList.Add(this.tempAction);
			this.LastEvent = this.teapTime;
			if (this.EnableEvent)
			{
				this.DoEvent(ref this.tempAction);
			}
		}

		private void DoEvent(ref OneAction _Action)
		{
			if (!this.InvokeBy.InvokeRequired)
			{
				DataSession.ActionAddedEventHandler actionAddedEventHandler = this.ActionAdded;
				if (actionAddedEventHandler != null)
				{
					actionAddedEventHandler(ref _Action);
				}
				return;
			}
			this.InvokeBy.Invoke(new DataSession.NoneInput(this.DoEvent), new object[] { _Action });
		}

		public event DataSession.ActionAddedEventHandler ActionAdded;

		public delegate void ActionAddedEventHandler(ref OneAction _Action);

		public enum ActionType
		{
			Keyboard,
			Win,
			Mouse,
			TimeSet
		}

		private delegate void NoneInput(ref OneAction _Action);
	}
}