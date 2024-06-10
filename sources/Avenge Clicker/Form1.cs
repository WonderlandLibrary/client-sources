using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Bypass.My;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace Bypass
{
	// Token: 0x02000008 RID: 8
	[DesignerGenerated]
	public partial class Form1 : Form
	{
		// Token: 0x06000025 RID: 37 RVA: 0x00002770 File Offset: 0x00000B70
		public Form1()
		{
			this.NewPoint = default(Point);
			this.InitializeComponent();
		}

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x06000028 RID: 40 RVA: 0x0000306C File Offset: 0x0000146C
		// (set) Token: 0x06000029 RID: 41 RVA: 0x00003080 File Offset: 0x00001480
		internal virtual Panel Panel1
		{
			get
			{
				return this._Panel1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				MouseEventHandler value2 = new MouseEventHandler(this.Panel1_MouseMove);
				MouseEventHandler value3 = new MouseEventHandler(this.Panel1_MouseDown);
				if (this._Panel1 != null)
				{
					this._Panel1.MouseMove -= value2;
					this._Panel1.MouseDown -= value3;
				}
				this._Panel1 = value;
				if (this._Panel1 != null)
				{
					this._Panel1.MouseMove += value2;
					this._Panel1.MouseDown += value3;
				}
			}
		}

		// Token: 0x1700000D RID: 13
		// (get) Token: 0x0600002A RID: 42 RVA: 0x000030F0 File Offset: 0x000014F0
		// (set) Token: 0x0600002B RID: 43 RVA: 0x00003104 File Offset: 0x00001504
		internal virtual PictureBox PictureBox1
		{
			get
			{
				return this._PictureBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.PictureBox1_Click_1);
				if (this._PictureBox1 != null)
				{
					this._PictureBox1.Click -= value2;
				}
				this._PictureBox1 = value;
				if (this._PictureBox1 != null)
				{
					this._PictureBox1.Click += value2;
				}
			}
		}

		// Token: 0x1700000E RID: 14
		// (get) Token: 0x0600002C RID: 44 RVA: 0x00003150 File Offset: 0x00001550
		// (set) Token: 0x0600002D RID: 45 RVA: 0x00003164 File Offset: 0x00001564
		internal virtual PictureBox PictureBox2
		{
			get
			{
				return this._PictureBox2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.PictureBox2_Click);
				if (this._PictureBox2 != null)
				{
					this._PictureBox2.Click -= value2;
				}
				this._PictureBox2 = value;
				if (this._PictureBox2 != null)
				{
					this._PictureBox2.Click += value2;
				}
			}
		}

		// Token: 0x1700000F RID: 15
		// (get) Token: 0x0600002E RID: 46 RVA: 0x000031B0 File Offset: 0x000015B0
		// (set) Token: 0x0600002F RID: 47 RVA: 0x000031C4 File Offset: 0x000015C4
		internal virtual PictureBox PictureBox3
		{
			get
			{
				return this._PictureBox3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.PictureBox3_Click);
				if (this._PictureBox3 != null)
				{
					this._PictureBox3.Click -= value2;
				}
				this._PictureBox3 = value;
				if (this._PictureBox3 != null)
				{
					this._PictureBox3.Click += value2;
				}
			}
		}

		// Token: 0x17000010 RID: 16
		// (get) Token: 0x06000030 RID: 48 RVA: 0x00003210 File Offset: 0x00001610
		// (set) Token: 0x06000031 RID: 49 RVA: 0x00003224 File Offset: 0x00001624
		internal virtual Label Label5
		{
			get
			{
				return this._Label5;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label5 = value;
			}
		}

		// Token: 0x17000011 RID: 17
		// (get) Token: 0x06000032 RID: 50 RVA: 0x00003230 File Offset: 0x00001630
		// (set) Token: 0x06000033 RID: 51 RVA: 0x00003244 File Offset: 0x00001644
		internal virtual TrackBar TrackBar1
		{
			get
			{
				return this._TrackBar1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TrackBar1_Scroll);
				if (this._TrackBar1 != null)
				{
					this._TrackBar1.Scroll -= value2;
				}
				this._TrackBar1 = value;
				if (this._TrackBar1 != null)
				{
					this._TrackBar1.Scroll += value2;
				}
			}
		}

		// Token: 0x17000012 RID: 18
		// (get) Token: 0x06000034 RID: 52 RVA: 0x00003290 File Offset: 0x00001690
		// (set) Token: 0x06000035 RID: 53 RVA: 0x000032A4 File Offset: 0x000016A4
		internal virtual TrackBar TrackBar2
		{
			get
			{
				return this._TrackBar2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TrackBar2_Scroll);
				if (this._TrackBar2 != null)
				{
					this._TrackBar2.Scroll -= value2;
				}
				this._TrackBar2 = value;
				if (this._TrackBar2 != null)
				{
					this._TrackBar2.Scroll += value2;
				}
			}
		}

		// Token: 0x17000013 RID: 19
		// (get) Token: 0x06000036 RID: 54 RVA: 0x000032F0 File Offset: 0x000016F0
		// (set) Token: 0x06000037 RID: 55 RVA: 0x00003304 File Offset: 0x00001704
		internal virtual Button Button1
		{
			get
			{
				return this._Button1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button1_Click);
				if (this._Button1 != null)
				{
					this._Button1.Click -= value2;
				}
				this._Button1 = value;
				if (this._Button1 != null)
				{
					this._Button1.Click += value2;
				}
			}
		}

		// Token: 0x17000014 RID: 20
		// (get) Token: 0x06000038 RID: 56 RVA: 0x00003350 File Offset: 0x00001750
		// (set) Token: 0x06000039 RID: 57 RVA: 0x00003364 File Offset: 0x00001764
		internal virtual Label Label1
		{
			get
			{
				return this._Label1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label1 = value;
			}
		}

		// Token: 0x17000015 RID: 21
		// (get) Token: 0x0600003A RID: 58 RVA: 0x00003370 File Offset: 0x00001770
		// (set) Token: 0x0600003B RID: 59 RVA: 0x00003384 File Offset: 0x00001784
		internal virtual Label Label2
		{
			get
			{
				return this._Label2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label2 = value;
			}
		}

		// Token: 0x17000016 RID: 22
		// (get) Token: 0x0600003C RID: 60 RVA: 0x00003390 File Offset: 0x00001790
		// (set) Token: 0x0600003D RID: 61 RVA: 0x000033A4 File Offset: 0x000017A4
		internal virtual Label Label3
		{
			get
			{
				return this._Label3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label3 = value;
			}
		}

		// Token: 0x17000017 RID: 23
		// (get) Token: 0x0600003E RID: 62 RVA: 0x000033B0 File Offset: 0x000017B0
		// (set) Token: 0x0600003F RID: 63 RVA: 0x000033C4 File Offset: 0x000017C4
		internal virtual Label Label4
		{
			get
			{
				return this._Label4;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label4 = value;
			}
		}

		// Token: 0x17000018 RID: 24
		// (get) Token: 0x06000040 RID: 64 RVA: 0x000033D0 File Offset: 0x000017D0
		// (set) Token: 0x06000041 RID: 65 RVA: 0x000033E4 File Offset: 0x000017E4
		internal virtual Timer Timer1
		{
			get
			{
				return this._Timer1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer1_Tick);
				if (this._Timer1 != null)
				{
					this._Timer1.Tick -= value2;
				}
				this._Timer1 = value;
				if (this._Timer1 != null)
				{
					this._Timer1.Tick += value2;
				}
			}
		}

		// Token: 0x06000042 RID: 66
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
		private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x06000043 RID: 67 RVA: 0x00003430 File Offset: 0x00001830
		private void PictureBox1_Click(object sender, EventArgs e)
		{
			this.Close();
		}

		// Token: 0x06000044 RID: 68 RVA: 0x00003438 File Offset: 0x00001838
		private void Panel1_MouseDown(object sender, MouseEventArgs e)
		{
			checked
			{
				this.X = Control.MousePosition.X - this.Location.X;
				this.Y = Control.MousePosition.Y - this.Location.Y;
			}
		}

		// Token: 0x06000045 RID: 69 RVA: 0x0000348C File Offset: 0x0000188C
		private void Panel1_MouseMove(object sender, MouseEventArgs e)
		{
			checked
			{
				if (e.Button == MouseButtons.Left)
				{
					this.NewPoint = Control.MousePosition;
					this.NewPoint.Y = this.NewPoint.Y - this.Y;
					this.NewPoint.X = this.NewPoint.X - this.X;
					this.Location = this.NewPoint;
				}
			}
		}

		// Token: 0x06000046 RID: 70 RVA: 0x000034F8 File Offset: 0x000018F8
		private void Label5_MouseDown(object sender, MouseEventArgs e)
		{
			checked
			{
				this.X = Control.MousePosition.X - this.Location.X;
				this.Y = Control.MousePosition.Y - this.Location.Y;
			}
		}

		// Token: 0x06000047 RID: 71 RVA: 0x0000354C File Offset: 0x0000194C
		private void Label5_MouseMove(object sender, MouseEventArgs e)
		{
			checked
			{
				if (e.Button == MouseButtons.Left)
				{
					this.NewPoint = Control.MousePosition;
					this.NewPoint.Y = this.NewPoint.Y - this.Y;
					this.NewPoint.X = this.NewPoint.X - this.X;
					this.Location = this.NewPoint;
				}
			}
		}

		// Token: 0x06000048 RID: 72 RVA: 0x000035B8 File Offset: 0x000019B8
		private void PictureBox1_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000049 RID: 73 RVA: 0x000035BC File Offset: 0x000019BC
		private void PictureBox2_Click(object sender, EventArgs e)
		{
			this.Close();
		}

		// Token: 0x0600004A RID: 74 RVA: 0x000035C4 File Offset: 0x000019C4
		private void PictureBox3_Click(object sender, EventArgs e)
		{
			MyProject.Forms.Form3.Show();
			this.Close();
		}

		// Token: 0x0600004B RID: 75 RVA: 0x000035DC File Offset: 0x000019DC
		private void TrackBar1_Scroll(object sender, EventArgs e)
		{
			this.Label3.Text = Conversions.ToString(this.TrackBar1.Value);
		}

		// Token: 0x0600004C RID: 76 RVA: 0x000035FC File Offset: 0x000019FC
		private void TrackBar2_Scroll(object sender, EventArgs e)
		{
			this.Label4.Text = Conversions.ToString(this.TrackBar2.Value);
		}

		// Token: 0x0600004D RID: 77 RVA: 0x0000361C File Offset: 0x00001A1C
		private void Timer1_Tick(object sender, EventArgs e)
		{
			VBMath.Randomize();
			Random random = new Random();
			checked
			{
				int maxValue = (int)Math.Round(1000.0 / (double)this.TrackBar1.Value);
				int minValue = (int)Math.Round(1000.0 / (double)this.TrackBar2.Value);
				this.Timer1.Interval = random.Next(minValue, maxValue);
				if (Control.MouseButtons == MouseButtons.Left)
				{
					Form1.apimouse_event(4, 0, 0, 0, 0);
					Form1.apimouse_event(2, 0, 0, 0, 0);
				}
			}
		}

		// Token: 0x0600004E RID: 78 RVA: 0x000036A4 File Offset: 0x00001AA4
		private void Button1_Click(object sender, EventArgs e)
		{
			checked
			{
				this.toggle++;
				if (this.toggle == 1)
				{
					this.Timer1.Start();
					this.Button1.Text = "Off";
				}
				else
				{
					this.Timer1.Stop();
					this.toggle = 0;
					this.Button1.Text = "On";
				}
			}
		}

		// Token: 0x0400000E RID: 14
		[AccessedThroughProperty("Panel1")]
		private Panel _Panel1;

		// Token: 0x0400000F RID: 15
		[AccessedThroughProperty("PictureBox1")]
		private PictureBox _PictureBox1;

		// Token: 0x04000010 RID: 16
		[AccessedThroughProperty("PictureBox2")]
		private PictureBox _PictureBox2;

		// Token: 0x04000011 RID: 17
		[AccessedThroughProperty("PictureBox3")]
		private PictureBox _PictureBox3;

		// Token: 0x04000012 RID: 18
		[AccessedThroughProperty("Label5")]
		private Label _Label5;

		// Token: 0x04000013 RID: 19
		[AccessedThroughProperty("TrackBar1")]
		private TrackBar _TrackBar1;

		// Token: 0x04000014 RID: 20
		[AccessedThroughProperty("TrackBar2")]
		private TrackBar _TrackBar2;

		// Token: 0x04000015 RID: 21
		[AccessedThroughProperty("Button1")]
		private Button _Button1;

		// Token: 0x04000016 RID: 22
		[AccessedThroughProperty("Label1")]
		private Label _Label1;

		// Token: 0x04000017 RID: 23
		[AccessedThroughProperty("Label2")]
		private Label _Label2;

		// Token: 0x04000018 RID: 24
		[AccessedThroughProperty("Label3")]
		private Label _Label3;

		// Token: 0x04000019 RID: 25
		[AccessedThroughProperty("Label4")]
		private Label _Label4;

		// Token: 0x0400001A RID: 26
		[AccessedThroughProperty("Timer1")]
		private Timer _Timer1;

		// Token: 0x0400001B RID: 27
		public const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x0400001C RID: 28
		public const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x0400001D RID: 29
		public const int MOUSEEVENTF_MIDDLEDOWN = 32;

		// Token: 0x0400001E RID: 30
		public const int MOUSEEVENTF_MDDLEUP = 64;

		// Token: 0x0400001F RID: 31
		public const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x04000020 RID: 32
		public const int MOUSEEVENTF_RIGHTUP = 16;

		// Token: 0x04000021 RID: 33
		public const int MOUSEEVENTF_MOVE = 1;

		// Token: 0x04000022 RID: 34
		private int toggle;

		// Token: 0x04000023 RID: 35
		private Point NewPoint;

		// Token: 0x04000024 RID: 36
		private int X;

		// Token: 0x04000025 RID: 37
		private int Y;
	}
}
