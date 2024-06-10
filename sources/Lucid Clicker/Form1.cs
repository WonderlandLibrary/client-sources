using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;

namespace LucidPublic
{
	// Token: 0x02000002 RID: 2
	public partial class Form1 : Form
	{
		// Token: 0x06000002 RID: 2
		[DllImport("User32.dll")]
		private static extern short GetAsyncKeyState(int vKey);

		// Token: 0x06000003 RID: 3
		[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(uint dwFlags, uint dx, uint dy, uint cButtons, uint dwExtraInfo);

		// Token: 0x06000004 RID: 4 RVA: 0x00002052 File Offset: 0x00000252
		public Form1()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000005 RID: 5 RVA: 0x00002108 File Offset: 0x00000308
		private void Form1_Load(object sender, EventArgs e)
		{
			this.trackBar1.Maximum = 25;
			this.trackBar1.Minimum = 5;
			this.trackBar1.TickFrequency = 1;
			this.timer1.Start();
			this.timer1.Interval = 10;
			this.radioButton1.Select();
		}

		// Token: 0x06000006 RID: 6 RVA: 0x00002160 File Offset: 0x00000360
		private void timer1_Tick(object sender, EventArgs e)
		{
			this.label3.Text = this.trackBar1.Value.ToString();
			this.delay = 1000 / this.trackBar1.Value;
			if (this.checkBox1.Checked && this.radioButton1.Checked && Form1.GetAsyncKeyState(16) < 0)
			{
				Thread.Sleep(this.delay);
				uint x = (uint)Cursor.Position.X;
				uint y = (uint)Cursor.Position.Y;
				Form1.mouse_event(6U, x, y, 0U, 0U);
			}
			if (this.checkBox1.Checked && this.radioButton2.Checked && Form1.GetAsyncKeyState(17) < 0)
			{
				Thread.Sleep(this.delay);
				uint x2 = (uint)Cursor.Position.X;
				uint y2 = (uint)Cursor.Position.Y;
				Form1.mouse_event(6U, x2, y2, 0U, 0U);
			}
		}

		// Token: 0x06000007 RID: 7 RVA: 0x00002060 File Offset: 0x00000260
		private void button1_Click(object sender, EventArgs e)
		{
			base.Close();
		}

		// Token: 0x04000001 RID: 1
		private const uint MOUSEEVENTF_LEFTDOWN = 2U;

		// Token: 0x04000002 RID: 2
		private const uint MOUSEEVENTF_LEFTUP = 4U;

		// Token: 0x04000003 RID: 3
		private int delay;
	}
}
