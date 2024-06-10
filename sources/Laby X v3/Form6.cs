using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using MonoFlat;

namespace LabyxV3
{
	// Token: 0x02000006 RID: 6
	public partial class Form6 : Form
	{
		// Token: 0x06000032 RID: 50
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
		private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x06000033 RID: 51 RVA: 0x0000400C File Offset: 0x0000220C
		public Form6()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000034 RID: 52 RVA: 0x00004024 File Offset: 0x00002224
		private void Button2_Click(object sender, EventArgs e)
		{
			this.timer4.Stop();
			this.timer3.Stop();
			this.timer2.Stop();
			this.timer1.Stop();
			base.Hide();
			this.Button1.Text = "J-Aim Off";
		}

		// Token: 0x06000035 RID: 53 RVA: 0x0000407A File Offset: 0x0000227A
		private void button3_Click(object sender, EventArgs e)
		{
			base.Hide();
		}

		// Token: 0x06000036 RID: 54 RVA: 0x00004084 File Offset: 0x00002284
		private void Button1_Click(object sender, EventArgs e)
		{
			checked
			{
				this.toggle++;
				bool flag = this.toggle == 1;
				if (flag)
				{
					this.timer4.Start();
					this.timer3.Start();
					this.timer2.Start();
					this.timer1.Start();
					this.Button1.Text = "B-Hit On";
				}
				else
				{
					this.timer4.Start();
					this.timer3.Stop();
					this.timer2.Stop();
					this.timer1.Stop();
					this.toggle = 0;
					this.Button1.Text = "B-Hit Off";
				}
				this.toggle_flag_active_disable_ToggledChanged();
			}
		}

		// Token: 0x06000037 RID: 55 RVA: 0x00004144 File Offset: 0x00002344
		private void toggle_flag_active_disable_ToggledChanged()
		{
			bool flag = this.status == 0;
			if (flag)
			{
				this.status = 1;
			}
			else
			{
				bool flag2 = this.status == 1;
				if (flag2)
				{
					this.status = 0;
				}
			}
		}

		// Token: 0x06000038 RID: 56 RVA: 0x00004180 File Offset: 0x00002380
		private void timer1_Tick_1(object sender, EventArgs e)
		{
			bool flag = this.status == 1;
			if (flag)
			{
				int interval = checked(new Random().Next((int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value), (int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value)));
				this.timer1.Interval = interval;
				bool flag2 = Control.MouseButtons == MouseButtons.Left;
				if (flag2)
				{
					Form6.apimouse_event(1, 10, 0, 0, 0);
				}
			}
		}

		// Token: 0x06000039 RID: 57 RVA: 0x0000420C File Offset: 0x0000240C
		private void timer2_Tick_1(object sender, EventArgs e)
		{
			bool flag = this.status == 1;
			if (flag)
			{
				int interval = checked(new Random().Next((int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value), (int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value)));
				this.timer1.Interval = interval;
				bool flag2 = Control.MouseButtons == MouseButtons.Left;
				if (flag2)
				{
					Form6.apimouse_event(1, -9, 0, 0, 0);
				}
			}
		}

		// Token: 0x0600003A RID: 58 RVA: 0x00004298 File Offset: 0x00002498
		private void timer3_Tick_1(object sender, EventArgs e)
		{
			bool flag = this.status == 1;
			if (flag)
			{
				int interval = checked(new Random().Next((int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value), (int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value)));
				this.timer1.Interval = interval;
				bool flag2 = Control.MouseButtons == MouseButtons.Left;
				if (flag2)
				{
					Form6.apimouse_event(1, 0, 5, 0, 0);
				}
			}
		}

		// Token: 0x0600003B RID: 59 RVA: 0x00004324 File Offset: 0x00002524
		private void timer4_Tick_1(object sender, EventArgs e)
		{
			bool flag = this.status == 1;
			if (flag)
			{
				int interval = checked(new Random().Next((int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value), (int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value)));
				this.timer1.Interval = interval;
				bool flag2 = Control.MouseButtons == MouseButtons.Left;
				if (flag2)
				{
					Form6.apimouse_event(1, 0, -5, 0, 0);
				}
			}
		}

		// Token: 0x0600003C RID: 60 RVA: 0x000043B0 File Offset: 0x000025B0
		private void monoFlat_TrackBar1_ValueChanged()
		{
			this.Label3.Text = this.monoFlat_TrackBar1.Value.ToString();
		}

		// Token: 0x04000031 RID: 49
		private int toggle;

		// Token: 0x04000032 RID: 50
		private int status;
	}
}
