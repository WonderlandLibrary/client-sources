using System;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;

namespace FastPot
{
	// Token: 0x02000005 RID: 5
	internal class ThrowPot
	{
		// Token: 0x0600001C RID: 28
		[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(uint dwFlags, uint dx, uint dy, uint cButtons, uint dwExtraInfo);

		// Token: 0x17000001 RID: 1
		// (get) Token: 0x0600001D RID: 29 RVA: 0x000032C7 File Offset: 0x000014C7
		// (set) Token: 0x0600001E RID: 30 RVA: 0x000032CF File Offset: 0x000014CF
		public KeyboardHook.VKeys KeyBind { get; set; } = KeyboardHook.VKeys.KEY_Z;

		// Token: 0x17000002 RID: 2
		// (get) Token: 0x0600001F RID: 31 RVA: 0x000032D8 File Offset: 0x000014D8
		// (set) Token: 0x06000020 RID: 32 RVA: 0x000032E0 File Offset: 0x000014E0
		public KeyboardHook.VKeys ThrowPotKey { get; set; } = KeyboardHook.VKeys.KEY_Q;

		// Token: 0x17000003 RID: 3
		// (get) Token: 0x06000021 RID: 33 RVA: 0x000032E9 File Offset: 0x000014E9
		// (set) Token: 0x06000022 RID: 34 RVA: 0x000032F1 File Offset: 0x000014F1
		public KeyboardHook.VKeys InventoryKey { get; set; } = KeyboardHook.VKeys.KEY_E;

		// Token: 0x17000004 RID: 4
		// (get) Token: 0x06000023 RID: 35 RVA: 0x000032FA File Offset: 0x000014FA
		// (set) Token: 0x06000024 RID: 36 RVA: 0x00003302 File Offset: 0x00001502
		public KeyboardHook.VKeys FirstPot { get; set; } = KeyboardHook.VKeys.KEY_3;

		// Token: 0x17000005 RID: 5
		// (get) Token: 0x06000025 RID: 37 RVA: 0x0000330B File Offset: 0x0000150B
		// (set) Token: 0x06000026 RID: 38 RVA: 0x00003313 File Offset: 0x00001513
		public KeyboardHook.VKeys LastPot { get; set; } = KeyboardHook.VKeys.KEY_8;

		// Token: 0x17000006 RID: 6
		// (get) Token: 0x06000027 RID: 39 RVA: 0x0000331C File Offset: 0x0000151C
		// (set) Token: 0x06000028 RID: 40 RVA: 0x00003324 File Offset: 0x00001524
		public KeyboardHook.VKeys Sword { get; set; } = KeyboardHook.VKeys.KEY_1;

		// Token: 0x17000007 RID: 7
		// (get) Token: 0x06000029 RID: 41 RVA: 0x0000332D File Offset: 0x0000152D
		// (set) Token: 0x0600002A RID: 42 RVA: 0x00003335 File Offset: 0x00001535
		public int CurrentPotSlot { get; set; } = 3;

		// Token: 0x17000008 RID: 8
		// (get) Token: 0x0600002B RID: 43 RVA: 0x0000333E File Offset: 0x0000153E
		// (set) Token: 0x0600002C RID: 44 RVA: 0x00003346 File Offset: 0x00001546
		public int SwordSlot { get; set; } = 1;

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x0600002D RID: 45 RVA: 0x0000334F File Offset: 0x0000154F
		// (set) Token: 0x0600002E RID: 46 RVA: 0x00003357 File Offset: 0x00001557
		public bool IsToggled { get; set; }

		// Token: 0x1700000A RID: 10
		// (get) Token: 0x0600002F RID: 47 RVA: 0x00003360 File Offset: 0x00001560
		// (set) Token: 0x06000030 RID: 48 RVA: 0x00003368 File Offset: 0x00001568
		public bool IsReadyToPot { get; set; }

		// Token: 0x06000031 RID: 49 RVA: 0x00003374 File Offset: 0x00001574
		public ThrowPot()
		{
			this.sendKeys = new Thread(new ThreadStart(this.ThrowPotion));
			this.keyboardHook.KeyUp += this.KeyboardHook_KeyUp;
			this.keyboardHook.Install();
		}

		// Token: 0x06000032 RID: 50 RVA: 0x00003428 File Offset: 0x00001628
		private void KeyboardHook_KeyUp(KeyboardHook.VKeys key)
		{
			bool flag = key == this.KeyBind || key == KeyboardHook.VKeys.SHIFT;
			if (flag)
			{
				this.IsToggled = !this.IsToggled;
				MainForm.MainFr.label1.Text = (this.IsToggled ? "TOGGLED: YES" : "TOGGLED: NO");
			}
			bool flag2 = key == this.ThrowPotKey && !this.IsReadyToPot && this.IsToggled;
			if (flag2)
			{
				bool flag3 = this.CurrentPotSlot > this.LastPot - KeyboardHook.VKeys.KEY_0;
				if (flag3)
				{
					this.CurrentPotSlot = this.FirstPot - KeyboardHook.VKeys.KEY_0;
				}
				bool flag4 = !this.sendKeys.IsAlive;
				if (flag4)
				{
					this.sendKeys = new Thread(new ThreadStart(this.ThrowPotion));
					this.sendKeys.Start();
				}
				else
				{
					this.sendKeys.Abort();
				}
			}
			bool flag5 = key == this.InventoryKey;
			if (flag5)
			{
				this.CurrentPotSlot = this.FirstPot - KeyboardHook.VKeys.KEY_0;
			}
		}

		// Token: 0x06000033 RID: 51 RVA: 0x0000352C File Offset: 0x0000172C
		private void ThrowPotion()
		{
			this.IsReadyToPot = true;
			SendKeys.SendWait("{" + this.CurrentPotSlot.ToString() + "}");
			Thread.Sleep(int.Parse(MainForm.MainFr.textBox1.Text));
			ThrowPot.mouse_event(this.MOUSEEVENTF_RIGHTDOWN, 0U, 0U, this.MOUSEEVENTF_RIGHTDOWN, 0U);
			Thread.Sleep(this.rnd.Next(1, 20));
			ThrowPot.mouse_event(this.MOUSEEVENTF_RIGHTUP, 0U, 0U, this.MOUSEEVENTF_RIGHTUP, 0U);
			Thread.Sleep(50);
			this.SwordSlot = this.Sword - KeyboardHook.VKeys.KEY_0;
			SendKeys.SendWait("{" + this.SwordSlot.ToString() + "}");
			this.IsReadyToPot = false;
			int currentPotSlot = this.CurrentPotSlot;
			this.CurrentPotSlot = currentPotSlot + 1;
		}

		// Token: 0x04000020 RID: 32
		public uint MOUSEEVENTF_RIGHTUP = 16U;

		// Token: 0x04000021 RID: 33
		public uint MOUSEEVENTF_RIGHTDOWN = 8U;

		// Token: 0x04000022 RID: 34
		private KeyboardHook keyboardHook = new KeyboardHook();

		// Token: 0x04000023 RID: 35
		private Random rnd = new Random();

		// Token: 0x04000024 RID: 36
		private Thread sendKeys;
	}
}
