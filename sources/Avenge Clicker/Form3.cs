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
	// Token: 0x0200000A RID: 10
	[DesignerGenerated]
	public partial class Form3 : Form
	{
		// Token: 0x06000057 RID: 87 RVA: 0x00003A38 File Offset: 0x00001E38
		public Form3()
		{
			base.Load += this.Form3_Load;
			this.NewPoint = default(Point);
			this.InitializeComponent();
		}

		// Token: 0x1700001B RID: 27
		// (get) Token: 0x0600005A RID: 90 RVA: 0x00004444 File Offset: 0x00002844
		// (set) Token: 0x0600005B RID: 91 RVA: 0x00004458 File Offset: 0x00002858
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

		// Token: 0x1700001C RID: 28
		// (get) Token: 0x0600005C RID: 92 RVA: 0x000044C8 File Offset: 0x000028C8
		// (set) Token: 0x0600005D RID: 93 RVA: 0x000044DC File Offset: 0x000028DC
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

		// Token: 0x1700001D RID: 29
		// (get) Token: 0x0600005E RID: 94 RVA: 0x00004528 File Offset: 0x00002928
		// (set) Token: 0x0600005F RID: 95 RVA: 0x0000453C File Offset: 0x0000293C
		internal virtual PictureBox PictureBox1
		{
			get
			{
				return this._PictureBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._PictureBox1 = value;
			}
		}

		// Token: 0x1700001E RID: 30
		// (get) Token: 0x06000060 RID: 96 RVA: 0x00004548 File Offset: 0x00002948
		// (set) Token: 0x06000061 RID: 97 RVA: 0x0000455C File Offset: 0x0000295C
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

		// Token: 0x1700001F RID: 31
		// (get) Token: 0x06000062 RID: 98 RVA: 0x000045A8 File Offset: 0x000029A8
		// (set) Token: 0x06000063 RID: 99 RVA: 0x000045BC File Offset: 0x000029BC
		internal virtual PictureBox PictureBox4
		{
			get
			{
				return this._PictureBox4;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.PictureBox4_Click);
				if (this._PictureBox4 != null)
				{
					this._PictureBox4.Click -= value2;
				}
				this._PictureBox4 = value;
				if (this._PictureBox4 != null)
				{
					this._PictureBox4.Click += value2;
				}
			}
		}

		// Token: 0x17000020 RID: 32
		// (get) Token: 0x06000064 RID: 100 RVA: 0x00004608 File Offset: 0x00002A08
		// (set) Token: 0x06000065 RID: 101 RVA: 0x0000461C File Offset: 0x00002A1C
		internal virtual Label Label1
		{
			get
			{
				return this._Label1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Label1_Click);
				if (this._Label1 != null)
				{
					this._Label1.Click -= value2;
				}
				this._Label1 = value;
				if (this._Label1 != null)
				{
					this._Label1.Click += value2;
				}
			}
		}

		// Token: 0x17000021 RID: 33
		// (get) Token: 0x06000066 RID: 102 RVA: 0x00004668 File Offset: 0x00002A68
		// (set) Token: 0x06000067 RID: 103 RVA: 0x0000467C File Offset: 0x00002A7C
		internal virtual Label Label2
		{
			get
			{
				return this._Label2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Label2_Click);
				if (this._Label2 != null)
				{
					this._Label2.Click -= value2;
				}
				this._Label2 = value;
				if (this._Label2 != null)
				{
					this._Label2.Click += value2;
				}
			}
		}

		// Token: 0x17000022 RID: 34
		// (get) Token: 0x06000068 RID: 104 RVA: 0x000046C8 File Offset: 0x00002AC8
		// (set) Token: 0x06000069 RID: 105 RVA: 0x000046DC File Offset: 0x00002ADC
		internal virtual PictureBox PictureBox5
		{
			get
			{
				return this._PictureBox5;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.PictureBox5_Click);
				if (this._PictureBox5 != null)
				{
					this._PictureBox5.Click -= value2;
				}
				this._PictureBox5 = value;
				if (this._PictureBox5 != null)
				{
					this._PictureBox5.Click += value2;
				}
			}
		}

		// Token: 0x17000023 RID: 35
		// (get) Token: 0x0600006A RID: 106 RVA: 0x00004728 File Offset: 0x00002B28
		// (set) Token: 0x0600006B RID: 107 RVA: 0x0000473C File Offset: 0x00002B3C
		internal virtual Label Label3
		{
			get
			{
				return this._Label3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Label3_Click);
				if (this._Label3 != null)
				{
					this._Label3.Click -= value2;
				}
				this._Label3 = value;
				if (this._Label3 != null)
				{
					this._Label3.Click += value2;
				}
			}
		}

		// Token: 0x17000024 RID: 36
		// (get) Token: 0x0600006C RID: 108 RVA: 0x00004788 File Offset: 0x00002B88
		// (set) Token: 0x0600006D RID: 109 RVA: 0x0000479C File Offset: 0x00002B9C
		internal virtual PictureBox PictureBox6
		{
			get
			{
				return this._PictureBox6;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._PictureBox6 = value;
			}
		}

		// Token: 0x17000025 RID: 37
		// (get) Token: 0x0600006E RID: 110 RVA: 0x000047A8 File Offset: 0x00002BA8
		// (set) Token: 0x0600006F RID: 111 RVA: 0x000047BC File Offset: 0x00002BBC
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

		// Token: 0x17000026 RID: 38
		// (get) Token: 0x06000070 RID: 112 RVA: 0x000047C8 File Offset: 0x00002BC8
		// (set) Token: 0x06000071 RID: 113 RVA: 0x000047DC File Offset: 0x00002BDC
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

		// Token: 0x17000027 RID: 39
		// (get) Token: 0x06000072 RID: 114 RVA: 0x000047E8 File Offset: 0x00002BE8
		// (set) Token: 0x06000073 RID: 115 RVA: 0x000047FC File Offset: 0x00002BFC
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

		// Token: 0x06000074 RID: 116 RVA: 0x00004848 File Offset: 0x00002C48
		private void PictureBox2_Click(object sender, EventArgs e)
		{
			this.Close();
		}

		// Token: 0x06000075 RID: 117 RVA: 0x00004850 File Offset: 0x00002C50
		private void PictureBox4_Click(object sender, EventArgs e)
		{
			MyProject.Forms.Form1.Show();
			this.Close();
		}

		// Token: 0x06000076 RID: 118 RVA: 0x00004868 File Offset: 0x00002C68
		private void Label2_Click(object sender, EventArgs e)
		{
			MyProject.Forms.Form1.Show();
			this.Close();
		}

		// Token: 0x06000077 RID: 119 RVA: 0x00004880 File Offset: 0x00002C80
		private void PictureBox5_Click(object sender, EventArgs e)
		{
			ColorDialog colorDialog = new ColorDialog();
			if (colorDialog.ShowDialog() == DialogResult.OK)
			{
				this.BackColor = colorDialog.Color;
			}
		}

		// Token: 0x06000078 RID: 120 RVA: 0x000048A8 File Offset: 0x00002CA8
		private void PictureBox3_Click(object sender, EventArgs e)
		{
			MyProject.Forms.Form4.Show();
			this.Close();
		}

		// Token: 0x06000079 RID: 121 RVA: 0x000048C0 File Offset: 0x00002CC0
		private void Label1_Click(object sender, EventArgs e)
		{
			MyProject.Forms.Form4.Show();
			this.Close();
		}

		// Token: 0x0600007A RID: 122 RVA: 0x000048D8 File Offset: 0x00002CD8
		private void Label3_Click(object sender, EventArgs e)
		{
			ColorDialog colorDialog = new ColorDialog();
			if (colorDialog.ShowDialog() == DialogResult.OK)
			{
				this.BackColor = colorDialog.Color;
			}
		}

		// Token: 0x0600007B RID: 123 RVA: 0x00004900 File Offset: 0x00002D00
		private void Form3_Load(object sender, EventArgs e)
		{
			this.Timer1.Start();
		}

		// Token: 0x0600007C RID: 124 RVA: 0x00004910 File Offset: 0x00002D10
		private void Timer1_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			this.Label5.ForeColor = Color.FromArgb(random.Next(0, 255), random.Next(0, 255), random.Next(0, 255));
		}

		// Token: 0x0600007D RID: 125 RVA: 0x00004958 File Offset: 0x00002D58
		private void Panel1_MouseDown(object sender, MouseEventArgs e)
		{
			checked
			{
				this.X = Control.MousePosition.X - this.Location.X;
				this.Y = Control.MousePosition.Y - this.Location.Y;
			}
		}

		// Token: 0x0600007E RID: 126 RVA: 0x000049AC File Offset: 0x00002DAC
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

		// Token: 0x0400002A RID: 42
		[AccessedThroughProperty("Panel1")]
		private Panel _Panel1;

		// Token: 0x0400002B RID: 43
		[AccessedThroughProperty("PictureBox2")]
		private PictureBox _PictureBox2;

		// Token: 0x0400002C RID: 44
		[AccessedThroughProperty("PictureBox1")]
		private PictureBox _PictureBox1;

		// Token: 0x0400002D RID: 45
		[AccessedThroughProperty("PictureBox3")]
		private PictureBox _PictureBox3;

		// Token: 0x0400002E RID: 46
		[AccessedThroughProperty("PictureBox4")]
		private PictureBox _PictureBox4;

		// Token: 0x0400002F RID: 47
		[AccessedThroughProperty("Label1")]
		private Label _Label1;

		// Token: 0x04000030 RID: 48
		[AccessedThroughProperty("Label2")]
		private Label _Label2;

		// Token: 0x04000031 RID: 49
		[AccessedThroughProperty("PictureBox5")]
		private PictureBox _PictureBox5;

		// Token: 0x04000032 RID: 50
		[AccessedThroughProperty("Label3")]
		private Label _Label3;

		// Token: 0x04000033 RID: 51
		[AccessedThroughProperty("PictureBox6")]
		private PictureBox _PictureBox6;

		// Token: 0x04000034 RID: 52
		[AccessedThroughProperty("Label4")]
		private Label _Label4;

		// Token: 0x04000035 RID: 53
		[AccessedThroughProperty("Label5")]
		private Label _Label5;

		// Token: 0x04000036 RID: 54
		[AccessedThroughProperty("Timer1")]
		private Timer _Timer1;

		// Token: 0x04000037 RID: 55
		private Point NewPoint;

		// Token: 0x04000038 RID: 56
		private int X;

		// Token: 0x04000039 RID: 57
		private int Y;
	}
}
