using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using Guna.UI.WinForms;

namespace Steam_Client_Bootstrapper
{
	// Token: 0x02000002 RID: 2
	public partial class Clicker : Form
	{
		// Token: 0x06000001 RID: 1
		[DllImport("user32", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x06000002 RID: 2 RVA: 0x00002050 File Offset: 0x00000250
		public Clicker()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000003 RID: 3 RVA: 0x00002068 File Offset: 0x00000268
		private void gunaTrackBar1_ValueChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000004 RID: 4 RVA: 0x00002068 File Offset: 0x00000268
		private void trackBar2_ValueChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000005 RID: 5 RVA: 0x000022AC File Offset: 0x000004AC
		private void timer1_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			int maxValue = (int)Math.Round(1000.0 / ((double)this.gunaTrackBar1.Value + (double)this.trackBar2.Value * 0.2));
			int minValue = (int)Math.Round(1000.0 / ((double)this.gunaTrackBar1.Value + (double)this.trackBar2.Value * 0.4));
			try
			{
				this.timer1.Interval = random.Next(minValue, maxValue);
			}
			catch
			{
			}
			bool flag = Control.MouseButtons == MouseButtons.Left;
			if (flag)
			{
				Clicker.mouse_event(4, 0, 0, 0, 0);
				Clicker.mouse_event(2, 0, 0, 0, 0);
			}
		}

		// Token: 0x06000006 RID: 6 RVA: 0x000022AC File Offset: 0x000004AC
		private void timer1_Tick_1(object sender, EventArgs e)
		{
			Random random = new Random();
			int maxValue = (int)Math.Round(1000.0 / ((double)this.gunaTrackBar1.Value + (double)this.trackBar2.Value * 0.2));
			int minValue = (int)Math.Round(1000.0 / ((double)this.gunaTrackBar1.Value + (double)this.trackBar2.Value * 0.4));
			try
			{
				this.timer1.Interval = random.Next(minValue, maxValue);
			}
			catch
			{
			}
			bool flag = Control.MouseButtons == MouseButtons.Left;
			if (flag)
			{
				Clicker.mouse_event(4, 0, 0, 0, 0);
				Clicker.mouse_event(2, 0, 0, 0, 0);
			}
		}

		// Token: 0x06000007 RID: 7 RVA: 0x0000206B File Offset: 0x0000026B
		private void bunifuThinButton23_Click(object sender, EventArgs e)
		{
			this.timer1.Start();
		}

		// Token: 0x06000008 RID: 8 RVA: 0x0000207A File Offset: 0x0000027A
		private void bunifuThinButton21_Click(object sender, EventArgs e)
		{
			this.timer1.Stop();
		}

		// Token: 0x06000009 RID: 9 RVA: 0x00002380 File Offset: 0x00000580
		private void gunaTrackBar1_Scroll(object sender, ScrollEventArgs e)
		{
			this.gunaLabel1.Text = this.gunaTrackBar1.Value.ToString();
		}

		// Token: 0x0600000A RID: 10 RVA: 0x000023B0 File Offset: 0x000005B0
		private void Clicker_Load(object sender, EventArgs e)
		{
			this.gunaTrackBar1.Maximum = 65;
			this.gunaTrackBar1.Minimum = 14;
			this.trackBar2.Maximum = 60;
			this.trackBar2.Minimum = 7;
			this.trackBar2.TickStyle = TickStyle.BottomRight;
			this.trackBar2.TickFrequency = 10;
		}

		// Token: 0x0600000B RID: 11 RVA: 0x00002068 File Offset: 0x00000268
		private void label1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x04000001 RID: 1
		private const int MOUSEEVENTF_MOVE = 1;

		// Token: 0x04000002 RID: 2
		private const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x04000003 RID: 3
		private const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x04000004 RID: 4
		private const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x04000005 RID: 5
		private const int MOUSEEVENTF_RIGHTUP = 22;
	}
}
