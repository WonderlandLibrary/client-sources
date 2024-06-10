using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using MonoFlat;

namespace LabyxV3
{
	// Token: 0x02000007 RID: 7
	public partial class Form5 : Form
	{
		// Token: 0x0600003F RID: 63
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
		private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x06000040 RID: 64 RVA: 0x00004BFF File Offset: 0x00002DFF
		public Form5()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000041 RID: 65 RVA: 0x00004C17 File Offset: 0x00002E17
		private void Form3k_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x06000042 RID: 66 RVA: 0x00004C1C File Offset: 0x00002E1C
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

		// Token: 0x06000043 RID: 67 RVA: 0x00004C57 File Offset: 0x00002E57
		private void Button2_Click_1(object sender, EventArgs e)
		{
			this.timer1.Stop();
			base.Hide();
			this.Button1.Text = "S-Tap Off";
		}

		// Token: 0x06000044 RID: 68 RVA: 0x00004C80 File Offset: 0x00002E80
		private void Button1_Click_1(object sender, EventArgs e)
		{
			checked
			{
				this.toggle++;
				bool flag = this.toggle != 1;
				if (flag)
				{
					this.timer1.Stop();
					this.toggle = 0;
					this.Button1.Text = "S-Tap Off";
				}
				else
				{
					this.timer1.Start();
					this.Button1.Text = "S-Tap On";
				}
				this.toggle_flag_active_disable_ToggledChanged();
			}
		}

		// Token: 0x06000045 RID: 69 RVA: 0x00004CFC File Offset: 0x00002EFC
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
					SendKeys.Send("{S}");
				}
			}
		}

		// Token: 0x06000046 RID: 70 RVA: 0x00004D88 File Offset: 0x00002F88
		private void monoFlat_TrackBar1_ValueChanged_1()
		{
			this.Label3.Text = this.monoFlat_TrackBar1.Value.ToString();
		}

		// Token: 0x06000047 RID: 71 RVA: 0x00004DB5 File Offset: 0x00002FB5
		private void button3_Click(object sender, EventArgs e)
		{
			base.Hide();
		}

		// Token: 0x06000048 RID: 72 RVA: 0x00004DBF File Offset: 0x00002FBF
		private void Label3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x04000041 RID: 65
		private readonly object VBMath;

		// Token: 0x04000042 RID: 66
		private int toggle;

		// Token: 0x04000043 RID: 67
		private int status;
	}
}
