using System;
using System.Runtime.InteropServices;
using System.Threading;

namespace ac.ac
{
	// Token: 0x02000009 RID: 9
	internal class Worker
	{
		// Token: 0x06000041 RID: 65
		[DllImport("user32.dll")]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x06000042 RID: 66 RVA: 0x00003076 File Offset: 0x00001276
		public static void PerformLeftClick(int xpos, int ypos)
		{
			Worker.mouse_event(2, xpos, ypos, 0, 0);
		}

		// Token: 0x06000043 RID: 67 RVA: 0x00003082 File Offset: 0x00001282
		public static void PerformRightClick(int xpos, int ypos)
		{
			Worker.mouse_event(8, xpos, ypos, 0, 0);
		}

		// Token: 0x06000044 RID: 68 RVA: 0x0000308E File Offset: 0x0000128E
		private void mouseMove(MouseHook.MSLLHOOKSTRUCT mouseStruct)
		{
			this.mouseX = mouseStruct.pt.x;
			this.mouseY = mouseStruct.pt.y;
		}

		// Token: 0x06000045 RID: 69 RVA: 0x000030B4 File Offset: 0x000012B4
		public Worker(Autoclicker a)
		{
			this.ac = a;
			this.mouseHook.MouseMove += this.mouseMove;
			this.mouseHook.LeftButtonDown += this.leftMouseDown;
			this.mouseHook.LeftButtonUp += this.leftMouseUp;
			this.mouseHook.RightButtonDown += this.rightMouseDown;
			this.mouseHook.RightButtonUp += this.rightMouseUp;
			this.mouseHook.Install();
			this.keyHook.KeyUp += this.keyUp;
			this.keyHook.Install();
		}

		// Token: 0x06000046 RID: 70 RVA: 0x00003190 File Offset: 0x00001390
		private void keyUp(KeyboardHook.VKeys key)
		{
			if (key == KeyboardHook.VKeys.KEY_F)
			{
				this.ac.enabled = !this.ac.enabled;
				if (this.ac.enabled)
				{
					this.ac.en.Text = "ON";
					return;
				}
				this.ac.en.Text = "OFF";
			}
		}

		// Token: 0x06000047 RID: 71 RVA: 0x000031FC File Offset: 0x000013FC
		private void rightMouseDown(MouseHook.MSLLHOOKSTRUCT mouseStruct)
		{
			this.rightDown = true;
		}

		// Token: 0x06000048 RID: 72 RVA: 0x00003205 File Offset: 0x00001405
		private void rightMouseUp(MouseHook.MSLLHOOKSTRUCT mouseStruct)
		{
			this.rightDown = false;
		}

		// Token: 0x06000049 RID: 73 RVA: 0x0000320E File Offset: 0x0000140E
		private void leftMouseUp(MouseHook.MSLLHOOKSTRUCT mouseStruct)
		{
			this.leftDown = false;
		}

		// Token: 0x0600004A RID: 74 RVA: 0x00003217 File Offset: 0x00001417
		private void leftMouseDown(MouseHook.MSLLHOOKSTRUCT mouseStruct)
		{
			this.leftDown = true;
		}

		// Token: 0x0600004B RID: 75 RVA: 0x00003220 File Offset: 0x00001420
		public void Click()
		{
			Random random = new Random();
			while (this.running)
			{
				if (this.leftDown && this.ac.enabled)
				{
					if (this.first)
					{
						Thread.Sleep(200);
						this.first = false;
					}
					else
					{
						Worker.PerformLeftClick(this.mouseX, this.mouseY);
						int num = random.Next(0, this.ac.random);
						Thread.Sleep(1000 / this.ac.cps + num);
					}
				}
				else
				{
					this.first = true;
				}
			}
		}

		// Token: 0x0600004C RID: 76 RVA: 0x000032BD File Offset: 0x000014BD
		public void stop()
		{
			this.running = false;
		}

		// Token: 0x04000027 RID: 39
		private volatile bool running = true;

		// Token: 0x04000028 RID: 40
		private Autoclicker ac;

		// Token: 0x04000029 RID: 41
		private MouseHook mouseHook = new MouseHook();

		// Token: 0x0400002A RID: 42
		private KeyboardHook keyHook = new KeyboardHook();

		// Token: 0x0400002B RID: 43
		public const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x0400002C RID: 44
		public const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x0400002D RID: 45
		public const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x0400002E RID: 46
		public const int MOUSEEVENTF_RIGHTUP = 16;

		// Token: 0x0400002F RID: 47
		private bool leftDown;

		// Token: 0x04000030 RID: 48
		private bool rightDown;

		// Token: 0x04000031 RID: 49
		private int mouseX;

		// Token: 0x04000032 RID: 50
		private int mouseY;

		// Token: 0x04000033 RID: 51
		private bool first;
	}
}
