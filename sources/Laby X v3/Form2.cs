using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using MonoFlat;

namespace LabyxV3
{
	// Token: 0x02000005 RID: 5
	public partial class Form2 : Form
	{
		// Token: 0x06000027 RID: 39
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
		private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x06000028 RID: 40 RVA: 0x00003682 File Offset: 0x00001882
		public Form2()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000029 RID: 41 RVA: 0x0000369A File Offset: 0x0000189A
		private void Form2_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x0600002A RID: 42 RVA: 0x0000369D File Offset: 0x0000189D
		private void Button2_Click(object sender, EventArgs e)
		{
			this.timer1.Stop();
			this.Button1.Text = "B-Hit Off";
			base.Hide();
		}

		// Token: 0x0600002B RID: 43 RVA: 0x000036C4 File Offset: 0x000018C4
		private void Button1_Click(object sender, EventArgs e)
		{
			checked
			{
				this.toggle++;
				bool flag = this.toggle != 1;
				if (flag)
				{
					this.timer1.Stop();
					this.toggle = 0;
					this.Button1.Text = "B-Hit Off";
				}
				else
				{
					this.timer1.Start();
					this.Button1.Text = "B-Hit On";
				}
				this.toggle_flag_active_disable_ToggledChanged();
			}
		}

		// Token: 0x0600002C RID: 44 RVA: 0x00003740 File Offset: 0x00001940
		private void timer1_Tick(object sender, EventArgs e)
		{
			bool flag = this.status == 1;
			if (flag)
			{
				int interval = checked(new Random().Next((int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value), (int)Math.Round(1000.0 / (double)this.monoFlat_TrackBar1.Value)));
				this.timer1.Interval = interval;
				bool flag2 = Control.MouseButtons == MouseButtons.Left;
				if (flag2)
				{
					Form2.apimouse_event(8, 0, 0, 0, 0);
					Form2.apimouse_event(16, 0, 0, 0, 0);
				}
			}
		}

		// Token: 0x0600002D RID: 45 RVA: 0x000037D8 File Offset: 0x000019D8
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

		// Token: 0x0600002E RID: 46 RVA: 0x00003814 File Offset: 0x00001A14
		private void monoFlat_TrackBar1_ValueChanged()
		{
			this.Label3.Text = this.monoFlat_TrackBar1.Value.ToString();
		}

		// Token: 0x0600002F RID: 47 RVA: 0x00003841 File Offset: 0x00001A41
		private void button3_Click(object sender, EventArgs e)
		{
			base.Hide();
		}

		// Token: 0x04000023 RID: 35
		private readonly object VBMath;

		// Token: 0x04000024 RID: 36
		private int toggle;

		// Token: 0x04000025 RID: 37
		private int status;
	}
}
