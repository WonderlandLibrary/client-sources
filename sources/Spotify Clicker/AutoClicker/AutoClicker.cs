using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Threading;

namespace AutoClicker
{
	// Token: 0x02000009 RID: 9
	internal class AutoClicker
	{
		// Token: 0x0600004E RID: 78 RVA: 0x000032FB File Offset: 0x000014FB
		public AutoClicker()
		{
			this.rnd = new Random();
		}

		// Token: 0x14000003 RID: 3
		// (add) Token: 0x0600004F RID: 79 RVA: 0x00003310 File Offset: 0x00001510
		// (remove) Token: 0x06000050 RID: 80 RVA: 0x00003348 File Offset: 0x00001548
		public event EventHandler<AutoClicker.NextClickEventArgs> NextClick;

		// Token: 0x06000051 RID: 81 RVA: 0x00003380 File Offset: 0x00001580
		private void Click()
		{
			this.SyncSettings();
			int num = this.count;
			while (this.countType == AutoClicker.CountType.UntilStopped || num > 0)
			{
				if (!this.IsAlive)
				{
					return;
				}
				this.SyncSettings();
				List<Win32.INPUT> list = new List<Win32.INPUT>();
				if (this.locationType == AutoClicker.LocationType.Fixed)
				{
					Win32.INPUT item = new Win32.INPUT
					{
						type = Win32.SendInputEventType.InputMouse,
						mi = new Win32.MOUSEINPUT
						{
							dx = Win32.CalculateAbsoluteCoordinateX(this.x),
							dy = Win32.CalculateAbsoluteCoordinateX(this.y),
							dwFlags = (Win32.MouseEventFlags)32769
						}
					};
					list.Add(item);
				}
				else if (this.locationType == AutoClicker.LocationType.Random)
				{
					Win32.INPUT item2 = new Win32.INPUT
					{
						type = Win32.SendInputEventType.InputMouse,
						mi = new Win32.MOUSEINPUT
						{
							dx = this.rnd.Next(65536),
							dy = this.rnd.Next(65536),
							dwFlags = (Win32.MouseEventFlags)32769
						}
					};
					list.Add(item2);
				}
				else if (this.locationType == AutoClicker.LocationType.RandomRange)
				{
					Win32.INPUT item3 = new Win32.INPUT
					{
						type = Win32.SendInputEventType.InputMouse,
						mi = new Win32.MOUSEINPUT
						{
							dx = Win32.CalculateAbsoluteCoordinateX(this.rnd.Next(this.x, this.x + this.width)),
							dy = Win32.CalculateAbsoluteCoordinateY(this.rnd.Next(this.y, this.y + this.height)),
							dwFlags = (Win32.MouseEventFlags)32769
						}
					};
					list.Add(item3);
				}
				for (int i = 0; i < (this.doubleClick ? 2 : 1); i++)
				{
					if (i == 1)
					{
						Thread.Sleep(50);
					}
					if (this.buttonType == AutoClicker.ButtonType.Left)
					{
						Win32.INPUT item4 = new Win32.INPUT
						{
							type = Win32.SendInputEventType.InputMouse,
							mi = new Win32.MOUSEINPUT
							{
								dwFlags = Win32.MouseEventFlags.LeftDown
							}
						};
						list.Add(item4);
						Win32.INPUT item5 = new Win32.INPUT
						{
							type = Win32.SendInputEventType.InputMouse,
							mi = new Win32.MOUSEINPUT
							{
								dwFlags = Win32.MouseEventFlags.LeftUp
							}
						};
						list.Add(item5);
					}
					if (this.buttonType == AutoClicker.ButtonType.Middle)
					{
						Win32.INPUT item6 = new Win32.INPUT
						{
							type = Win32.SendInputEventType.InputMouse,
							mi = new Win32.MOUSEINPUT
							{
								dwFlags = Win32.MouseEventFlags.MiddleDown
							}
						};
						list.Add(item6);
						Win32.INPUT item7 = new Win32.INPUT
						{
							type = Win32.SendInputEventType.InputMouse,
							mi = new Win32.MOUSEINPUT
							{
								dwFlags = Win32.MouseEventFlags.MiddleUp
							}
						};
						list.Add(item7);
					}
					if (this.buttonType == AutoClicker.ButtonType.Right)
					{
						Win32.INPUT item8 = new Win32.INPUT
						{
							type = Win32.SendInputEventType.InputMouse,
							mi = new Win32.MOUSEINPUT
							{
								dwFlags = Win32.MouseEventFlags.RightDown
							}
						};
						list.Add(item8);
						Win32.INPUT item9 = new Win32.INPUT
						{
							type = Win32.SendInputEventType.InputMouse,
							mi = new Win32.MOUSEINPUT
							{
								dwFlags = Win32.MouseEventFlags.RightUp
							}
						};
						list.Add(item9);
					}
				}
				Win32.SendInput((uint)list.Count, list.ToArray(), Marshal.SizeOf(default(Win32.INPUT)));
				int num2 = this.rnd.Next(MainForm.mincps, MainForm.maxcps);
				int num3 = 1000 / num2 - this.rnd.Next(5, 10);
				MainForm.updateCPS(num2);
				EventHandler<AutoClicker.NextClickEventArgs> nextClick = this.NextClick;
				if (nextClick != null)
				{
					nextClick(this, new AutoClicker.NextClickEventArgs
					{
						NextClick = num3
					});
				}
				Thread.Sleep(num3);
				num--;
			}
			EventHandler<EventArgs> finished = this.Finished;
			if (finished == null)
			{
				return;
			}
			finished(this, null);
		}

		// Token: 0x17000011 RID: 17
		// (get) Token: 0x06000052 RID: 82 RVA: 0x0000375B File Offset: 0x0000195B
		public bool IsAlive
		{
			get
			{
				return this.Clicker != null && this.Clicker.IsAlive;
			}
		}

		// Token: 0x06000053 RID: 83 RVA: 0x00003772 File Offset: 0x00001972
		public void Start()
		{
			this.Clicker = new Thread(new ThreadStart(this.Click));
			this.Clicker.IsBackground = true;
			this.Clicker.Start();
		}

		// Token: 0x06000054 RID: 84 RVA: 0x000037A2 File Offset: 0x000019A2
		public void Stop()
		{
			if (this.Clicker != null)
			{
				this.Clicker.Abort();
			}
		}

		// Token: 0x06000055 RID: 85 RVA: 0x000037B8 File Offset: 0x000019B8
		private void SyncSettings()
		{
			if (this.buttonUpdated)
			{
				this.buttonType = this.tmpButtonType;
				this.doubleClick = this.tmpDoubleClick;
				this.buttonUpdated = false;
			}
			if (this.locationUpdated)
			{
				this.locationType = this.tmpLocationType;
				this.x = this.tmpX;
				this.y = this.tmpY;
				this.width = this.tmpWidth;
				this.height = this.tmpHeight;
				this.locationUpdated = false;
			}
			if (this.delayUpdated)
			{
				this.delayType = this.tmpDelayType;
				this.delay = this.tmpDelay;
				this.delayRange = this.tmpDelayRange;
				this.delayUpdated = false;
			}
			if (this.countUpdated)
			{
				this.countType = this.tmpCountType;
				this.count = this.tmpCount;
				this.countUpdated = false;
			}
		}

		// Token: 0x06000056 RID: 86 RVA: 0x00003891 File Offset: 0x00001A91
		public void UpdateButton(AutoClicker.ButtonType ButtonType, bool DoubleClick)
		{
			this.tmpButtonType = ButtonType;
			this.tmpDoubleClick = DoubleClick;
			this.buttonUpdated = true;
		}

		// Token: 0x06000057 RID: 87 RVA: 0x000038A8 File Offset: 0x00001AA8
		public void UpdateLocation(AutoClicker.LocationType LocationType, int X, int Y, int Width, int Height)
		{
			this.tmpLocationType = LocationType;
			this.tmpX = X;
			this.tmpY = Y;
			this.tmpWidth = Width;
			this.tmpHeight = Height;
			this.locationUpdated = true;
		}

		// Token: 0x06000058 RID: 88 RVA: 0x000038D6 File Offset: 0x00001AD6
		public void UpdateDelay(AutoClicker.DelayType DelayType, int Delay, int DelayRange)
		{
			this.tmpDelayType = DelayType;
			this.tmpDelay = Delay;
			this.tmpDelayRange = DelayRange;
			this.delayUpdated = true;
		}

		// Token: 0x06000059 RID: 89 RVA: 0x000038F4 File Offset: 0x00001AF4
		public void UpdateCount(AutoClicker.CountType CountType, int Count)
		{
			this.tmpCountType = CountType;
			this.tmpCount = Count;
			this.countUpdated = true;
		}

		// Token: 0x04000032 RID: 50
		private AutoClicker.ButtonType buttonType;

		// Token: 0x04000033 RID: 51
		private bool doubleClick;

		// Token: 0x04000034 RID: 52
		private AutoClicker.LocationType locationType;

		// Token: 0x04000035 RID: 53
		private int x;

		// Token: 0x04000036 RID: 54
		private int y;

		// Token: 0x04000037 RID: 55
		private int width;

		// Token: 0x04000038 RID: 56
		private int height;

		// Token: 0x04000039 RID: 57
		private AutoClicker.DelayType delayType;

		// Token: 0x0400003A RID: 58
		private int delay;

		// Token: 0x0400003B RID: 59
		private int delayRange;

		// Token: 0x0400003C RID: 60
		private AutoClicker.CountType countType;

		// Token: 0x0400003D RID: 61
		private int count;

		// Token: 0x0400003E RID: 62
		private bool buttonUpdated;

		// Token: 0x0400003F RID: 63
		private AutoClicker.ButtonType tmpButtonType;

		// Token: 0x04000040 RID: 64
		private bool tmpDoubleClick;

		// Token: 0x04000041 RID: 65
		private bool locationUpdated;

		// Token: 0x04000042 RID: 66
		private AutoClicker.LocationType tmpLocationType;

		// Token: 0x04000043 RID: 67
		private int tmpX;

		// Token: 0x04000044 RID: 68
		private int tmpY;

		// Token: 0x04000045 RID: 69
		private int tmpWidth;

		// Token: 0x04000046 RID: 70
		private int tmpHeight;

		// Token: 0x04000047 RID: 71
		private bool delayUpdated;

		// Token: 0x04000048 RID: 72
		private AutoClicker.DelayType tmpDelayType;

		// Token: 0x04000049 RID: 73
		private int tmpDelay;

		// Token: 0x0400004A RID: 74
		private int tmpDelayRange;

		// Token: 0x0400004B RID: 75
		private bool countUpdated;

		// Token: 0x0400004C RID: 76
		private AutoClicker.CountType tmpCountType;

		// Token: 0x0400004D RID: 77
		private int tmpCount;

		// Token: 0x0400004E RID: 78
		private Thread Clicker;

		// Token: 0x0400004F RID: 79
		private Random rnd;

		// Token: 0x04000051 RID: 81
		public EventHandler<EventArgs> Finished;

		// Token: 0x02000016 RID: 22
		public enum ButtonType
		{
			// Token: 0x040000DA RID: 218
			Left,
			// Token: 0x040000DB RID: 219
			Middle,
			// Token: 0x040000DC RID: 220
			Right
		}

		// Token: 0x02000017 RID: 23
		public enum LocationType
		{
			// Token: 0x040000DE RID: 222
			Cursor,
			// Token: 0x040000DF RID: 223
			Fixed,
			// Token: 0x040000E0 RID: 224
			Random,
			// Token: 0x040000E1 RID: 225
			RandomRange
		}

		// Token: 0x02000018 RID: 24
		public enum DelayType
		{
			// Token: 0x040000E3 RID: 227
			Fixed,
			// Token: 0x040000E4 RID: 228
			Range
		}

		// Token: 0x02000019 RID: 25
		public enum CountType
		{
			// Token: 0x040000E6 RID: 230
			Fixed,
			// Token: 0x040000E7 RID: 231
			UntilStopped
		}

		// Token: 0x0200001A RID: 26
		public class NextClickEventArgs : EventArgs
		{
			// Token: 0x040000E8 RID: 232
			public int NextClick;
		}
	}
}
