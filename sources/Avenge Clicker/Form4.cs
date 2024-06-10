using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Bypass.My;
using Microsoft.VisualBasic.CompilerServices;

namespace Bypass
{
	// Token: 0x0200000B RID: 11
	[DesignerGenerated]
	public partial class Form4 : Form
	{
		// Token: 0x0600007F RID: 127 RVA: 0x00004A18 File Offset: 0x00002E18
		public Form4()
		{
			this.NewPoint = default(Point);
			this.InitializeComponent();
		}

		// Token: 0x17000028 RID: 40
		// (get) Token: 0x06000082 RID: 130 RVA: 0x00004ED0 File Offset: 0x000032D0
		// (set) Token: 0x06000083 RID: 131 RVA: 0x00004EE4 File Offset: 0x000032E4
		internal virtual Panel Panel1
		{
			get
			{
				return this._Panel1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				MouseEventHandler value2 = new MouseEventHandler(this.Panel1_MouseDown);
				MouseEventHandler value3 = new MouseEventHandler(this.Panel1_MouseMove);
				if (this._Panel1 != null)
				{
					this._Panel1.MouseDown -= value2;
					this._Panel1.MouseMove -= value3;
				}
				this._Panel1 = value;
				if (this._Panel1 != null)
				{
					this._Panel1.MouseDown += value2;
					this._Panel1.MouseMove += value3;
				}
			}
		}

		// Token: 0x17000029 RID: 41
		// (get) Token: 0x06000084 RID: 132 RVA: 0x00004F54 File Offset: 0x00003354
		// (set) Token: 0x06000085 RID: 133 RVA: 0x00004F68 File Offset: 0x00003368
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

		// Token: 0x1700002A RID: 42
		// (get) Token: 0x06000086 RID: 134 RVA: 0x00004FB4 File Offset: 0x000033B4
		// (set) Token: 0x06000087 RID: 135 RVA: 0x00004FC8 File Offset: 0x000033C8
		internal virtual PictureBox PictureBox1
		{
			get
			{
				return this._PictureBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				MouseEventHandler value2 = new MouseEventHandler(this.PictureBox1_MouseMove);
				MouseEventHandler value3 = new MouseEventHandler(this.PictureBox1_MouseDown);
				if (this._PictureBox1 != null)
				{
					this._PictureBox1.MouseMove -= value2;
					this._PictureBox1.MouseDown -= value3;
				}
				this._PictureBox1 = value;
				if (this._PictureBox1 != null)
				{
					this._PictureBox1.MouseMove += value2;
					this._PictureBox1.MouseDown += value3;
				}
			}
		}

		// Token: 0x1700002B RID: 43
		// (get) Token: 0x06000088 RID: 136 RVA: 0x00005038 File Offset: 0x00003438
		// (set) Token: 0x06000089 RID: 137 RVA: 0x0000504C File Offset: 0x0000344C
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

		// Token: 0x1700002C RID: 44
		// (get) Token: 0x0600008A RID: 138 RVA: 0x00005098 File Offset: 0x00003498
		// (set) Token: 0x0600008B RID: 139 RVA: 0x000050AC File Offset: 0x000034AC
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

		// Token: 0x0600008C RID: 140 RVA: 0x000050F8 File Offset: 0x000034F8
		private void PictureBox2_Click(object sender, EventArgs e)
		{
			this.Close();
		}

		// Token: 0x0600008D RID: 141 RVA: 0x00005100 File Offset: 0x00003500
		private void Panel1_MouseDown(object sender, MouseEventArgs e)
		{
			checked
			{
				this.X = Control.MousePosition.X - this.Location.X;
				this.Y = Control.MousePosition.Y - this.Location.Y;
			}
		}

		// Token: 0x0600008E RID: 142 RVA: 0x00005154 File Offset: 0x00003554
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

		// Token: 0x0600008F RID: 143 RVA: 0x000051C0 File Offset: 0x000035C0
		private void PictureBox1_MouseDown(object sender, MouseEventArgs e)
		{
			checked
			{
				this.X = Control.MousePosition.X - this.Location.X;
				this.Y = Control.MousePosition.Y - this.Location.Y;
			}
		}

		// Token: 0x06000090 RID: 144 RVA: 0x00005214 File Offset: 0x00003614
		private void PictureBox1_MouseMove(object sender, MouseEventArgs e)
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

		// Token: 0x06000091 RID: 145 RVA: 0x00005280 File Offset: 0x00003680
		private void Button1_Click(object sender, EventArgs e)
		{
			this.Kill(1);
		}

		// Token: 0x06000092 RID: 146 RVA: 0x0000528C File Offset: 0x0000368C
		private void Kill(int Timeout)
		{
			Process.Start(new ProcessStartInfo("cmd.exe")
			{
				Arguments = string.Concat(new string[]
				{
					"/C  ping 1.1.1.1 –n 1 –w ",
					Timeout.ToString(),
					" > Nul & Del \"",
					Application.ExecutablePath,
					"\""
				}),
				CreateNoWindow = true,
				ErrorDialog = false,
				WindowStyle = ProcessWindowStyle.Hidden
			});
			Application.ExitThread();
		}

		// Token: 0x06000093 RID: 147 RVA: 0x00005304 File Offset: 0x00003704
		private void PictureBox3_Click(object sender, EventArgs e)
		{
			MyProject.Forms.Form3.Show();
			this.Close();
		}

		// Token: 0x0400003B RID: 59
		[AccessedThroughProperty("Panel1")]
		private Panel _Panel1;

		// Token: 0x0400003C RID: 60
		[AccessedThroughProperty("PictureBox2")]
		private PictureBox _PictureBox2;

		// Token: 0x0400003D RID: 61
		[AccessedThroughProperty("PictureBox1")]
		private PictureBox _PictureBox1;

		// Token: 0x0400003E RID: 62
		[AccessedThroughProperty("Button1")]
		private Button _Button1;

		// Token: 0x0400003F RID: 63
		[AccessedThroughProperty("PictureBox3")]
		private PictureBox _PictureBox3;

		// Token: 0x04000040 RID: 64
		private Point NewPoint;

		// Token: 0x04000041 RID: 65
		private int X;

		// Token: 0x04000042 RID: 66
		private int Y;
	}
}
