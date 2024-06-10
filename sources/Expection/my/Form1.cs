using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace AnyDesk.my
{
	// Token: 0x02000007 RID: 7
	public partial class Form1 : Form
	{
		// Token: 0x0600000C RID: 12
		[DllImport("user32", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern int GetWindowText(IntPtr hWnd, StringBuilder lpString, int cch);

		// Token: 0x0600000D RID: 13
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x0600000E RID: 14
		[DllImport("user32.dll", CharSet = CharSet.Auto)]
		public static extern IntPtr SendMessage(IntPtr hWnd, uint msg, int wParam, int lParam);

		// Token: 0x0600000F RID: 15
		[DllImport("user32.dll", SetLastError = true)]
		private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

		// Token: 0x06000010 RID: 16
		[DllImport("user32.dll", SetLastError = true)]
		private static extern uint GetWindowThreadProcessId(IntPtr hWnd, out uint processId);

		// Token: 0x06000011 RID: 17
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(Keys vKey);

		// Token: 0x06000012 RID: 18 RVA: 0x000023F0 File Offset: 0x000005F0
		public Form1()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000013 RID: 19 RVA: 0x0000246C File Offset: 0x0000066C
		private void button1_Click(object sender, EventArgs e)
		{
			if (this.reach)
			{
				this.reach = false;
				this.button1.BackColor = Color.FromArgb(45, 45, 45);
			}
			else
			{
				this.reach = true;
				this.button1.BackColor = Color.FromArgb(55, 90, 225);
			}
			this.button1.Refresh();
		}

		// Token: 0x06000014 RID: 20 RVA: 0x00002059 File Offset: 0x00000259
		private void TimerReach_Tick(object sender, EventArgs e)
		{
			Form1.GetWindowThreadProcessId(Form1.FindWindow("LWJGL", null), out this.pid);
			new Thread(new ThreadStart(this.ReachModule)).Start();
		}

		// Token: 0x06000015 RID: 21 RVA: 0x000024CC File Offset: 0x000006CC
		public void ReachModule()
		{
			bool flag = this.reach;
			if (flag)
			{
				double w = this.newr;
				this.Fusked();
				this.kick();
				this.lmao();
				this.kek(w);
				this.Syn(w);
				this.oldr = w;
			}
			else
			{
				this.Syn(3.0);
			}
		}

		// Token: 0x06000016 RID: 22 RVA: 0x0000252C File Offset: 0x0000072C
		public void lmao()
		{
			this.MC = Process.GetProcessById((int)this.pid);
			if (this.MC != null)
			{
				this.SReach.FimScan = 268435456UL;
				this.Reach = this.SReach.ScanArray(this.MC, "00 00 00 00 00 00 08 40 ?? ??");
				for (int i = 0; i < this.Reach.Count<IntPtr>(); i++)
				{
					if (!this.R.Contains(this.Reach[i]))
					{
						foreach (IntPtr intPtr in this.RB)
						{
							if (intPtr.ToString("X").Substring(0, 5) == this.Reach[i].ToString("X").Substring(0, 5))
							{
								this.R.Add(this.Reach[i]);
							}
						}
					}
					Thread.Sleep(20);
				}
				return;
			}
			MessageBox.Show("Não achou o jogo da cabeça quadrada", "http://expection.dx.am");
			Application.Exit();
		}

		// Token: 0x06000017 RID: 23 RVA: 0x00002660 File Offset: 0x00000860
		public void kek(double w)
		{
			Memory.OpenProcess((int)this.pid, Enums.ProcessAccessFlags.All);
			for (int i = 0; i < this.R.Count<IntPtr>(); i++)
			{
				double num = Memory.Read<double>((int)this.R[i]);
				bool flag = num != 3.0 && num != this.oldr && num != w;
				if (flag)
				{
					this.R.RemoveAt(i);
				}
			}
		}

		// Token: 0x06000018 RID: 24 RVA: 0x000026E4 File Offset: 0x000008E4
		public void Fusked()
		{
			this.MC = Process.GetProcessById((int)this.pid);
			if (this.MC != null)
			{
				this.SReachb.FimScan = 268435456UL;
				this.Reachb = this.SReachb.ScanArray(this.MC, "00 00 00 00 00 00 12 40 ?? ??");
				for (int i = 0; i < this.Reachb.Count<IntPtr>(); i++)
				{
					if (!this.RB.Contains(this.Reachb[i]) && !this.R.Contains(this.Reachb[i]))
					{
						this.RB.Add(this.Reachb[i]);
					}
					Thread.Sleep(20);
				}
				return;
			}
			MessageBox.Show("Não achou o jogo da cabeça quadrada", "http://expection.dx.am");
			Application.Exit();
		}

		// Token: 0x06000019 RID: 25 RVA: 0x000027B4 File Offset: 0x000009B4
		public void kick()
		{
			Memory.OpenProcess((int)this.pid, Enums.ProcessAccessFlags.All);
			for (int i = 0; i < this.RB.Count<IntPtr>(); i++)
			{
				bool flag = Memory.Read<double>((int)this.RB[i]) != 4.5;
				if (flag)
				{
					this.RB.RemoveAt(i);
				}
				Thread.Sleep(20);
			}
		}

		// Token: 0x0600001A RID: 26 RVA: 0x00002830 File Offset: 0x00000A30
		public void Syn(double w)
		{
			Memory.OpenProcess((int)this.pid, Enums.ProcessAccessFlags.All);
			for (int i = 0; i < this.R.Count<IntPtr>(); i++)
			{
				Memory.Write<double>((int)this.R[i], w);
			}
		}

		// Token: 0x0600001B RID: 27 RVA: 0x00002884 File Offset: 0x00000A84
		private void trackBar1_Scroll(object sender, EventArgs e)
		{
			double num = (300.0 + (double)this.trackBar1.Value) / 100.0;
			this.newr = num;
			this.flatLabel6.Text = "Reach: " + num.ToString();
		}

		// Token: 0x0600001C RID: 28 RVA: 0x000028D8 File Offset: 0x00000AD8
		private void button2_Click(object sender, EventArgs e)
		{
			if (this.velocity)
			{
				this.velocity = false;
				this.button2.BackColor = Color.FromArgb(45, 45, 45);
			}
			else
			{
				this.velocity = true;
				this.button2.BackColor = Color.FromArgb(55, 90, 225);
			}
			this.button2.Refresh();
		}

		// Token: 0x0600001D RID: 29 RVA: 0x00002938 File Offset: 0x00000B38
		private void trackBar2_Scroll(object sender, EventArgs e)
		{
			double num = Math.Round(100.0 / (double)this.trackBar2.Value);
			double num2 = Math.Round(8000.0 * num);
			this.newv = num2;
			this.label1.Text = "KB: " + this.trackBar2.Value.ToString();
		}

		// Token: 0x0600001E RID: 30 RVA: 0x000029A4 File Offset: 0x00000BA4
		public void sex()
		{
			this.MC = Process.GetProcessById((int)this.pid);
			if (this.MC != null)
			{
				this.Svelo.InicioScan = 318767104UL;
				this.Svelo.FimScan = 1610612736UL;
				this.Velocity = this.Svelo.ScanArray(this.MC, "00 00 00 00 00 40 BF 40 00 00 00 00 00 00 ?? ??");
				for (int i = 0; i < this.Velocity.Count<IntPtr>(); i++)
				{
					if (!this.V.Contains(this.Velocity[i]))
					{
						this.V.Add(this.Velocity[i]);
					}
				}
				return;
			}
			MessageBox.Show("Não achou o jogo da cabeça quadrada", "http://expection.dx.am");
			Application.Exit();
		}

		// Token: 0x0600001F RID: 31 RVA: 0x00002A68 File Offset: 0x00000C68
		public void boobs(double w)
		{
			Memory.OpenProcess((int)this.pid, Enums.ProcessAccessFlags.All);
			for (int i = 0; i < this.V.Count<IntPtr>(); i++)
			{
				double num = Memory.Read<double>((int)this.V[i]);
				bool flag = num != 8000.0 && num != this.oldv && num != w;
				if (flag)
				{
					this.V.RemoveAt(i);
				}
			}
		}

		// Token: 0x06000020 RID: 32 RVA: 0x00002AEC File Offset: 0x00000CEC
		public void Manthe(double w)
		{
			Memory.OpenProcess((int)this.pid, Enums.ProcessAccessFlags.All);
			for (int i = 0; i < this.V.Count<IntPtr>(); i++)
			{
				Memory.Write<double>((int)this.V[i], w);
			}
		}

		// Token: 0x06000021 RID: 33 RVA: 0x00002B40 File Offset: 0x00000D40
		public void CHEATBREAKER()
		{
			bool flag = this.velocity;
			if (flag)
			{
				double w = this.newv;
				this.sex();
				this.boobs(w);
				this.Manthe(w);
				this.oldv = w;
			}
			else
			{
				this.Manthe(8000.0);
			}
		}

		// Token: 0x06000022 RID: 34 RVA: 0x0000208A File Offset: 0x0000028A
		private void label1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0400003C RID: 60
		private uint pid;

		// Token: 0x0400003D RID: 61
		private Process MC;

		// Token: 0x0400003E RID: 62
		private bool reach;

		// Token: 0x0400003F RID: 63
		private DotNetScanMemory_SmoLL SReachb = new DotNetScanMemory_SmoLL();

		// Token: 0x04000040 RID: 64
		private DotNetScanMemory_SmoLL SReach = new DotNetScanMemory_SmoLL();

		// Token: 0x04000041 RID: 65
		private IntPtr[] Reach;

		// Token: 0x04000042 RID: 66
		private List<IntPtr> R = new List<IntPtr>();

		// Token: 0x04000043 RID: 67
		private IntPtr[] Reachb;

		// Token: 0x04000044 RID: 68
		private List<IntPtr> RB = new List<IntPtr>();

		// Token: 0x04000045 RID: 69
		private double newr;

		// Token: 0x04000046 RID: 70
		private double oldr = 3.0;

		// Token: 0x04000047 RID: 71
		private bool velocity;

		// Token: 0x04000048 RID: 72
		private IntPtr[] Velocity;

		// Token: 0x04000049 RID: 73
		private List<IntPtr> V = new List<IntPtr>();

		// Token: 0x0400004A RID: 74
		private DotNetScanMemory_SmoLL Svelo = new DotNetScanMemory_SmoLL();

		// Token: 0x0400004B RID: 75
		private double newv;

		// Token: 0x0400004C RID: 76
		private double oldv = 8000.0;

		// Token: 0x0400004D RID: 77
		private bool wave;
	}
}
