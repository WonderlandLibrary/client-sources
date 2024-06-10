using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200000B RID: 11
[DesignerGenerated]
public partial class arraylist : Form
{
	// Token: 0x060000D4 RID: 212 RVA: 0x00004B04 File Offset: 0x00002D04
	public arraylist()
	{
		base.Load += this.arraylist_Load;
		this.InitializeComponent();
	}

	// Token: 0x060000D5 RID: 213 RVA: 0x00004B24 File Offset: 0x00002D24
	[DebuggerNonUserCode]
	protected virtual void Dispose(bool disposing)
	{
		try
		{
			if (disposing && this.icontainer_0 != null)
			{
				this.icontainer_0.Dispose();
			}
		}
		finally
		{
			base.Dispose(disposing);
		}
	}

	// Token: 0x1700009A RID: 154
	// (get) Token: 0x060000D7 RID: 215 RVA: 0x00004F04 File Offset: 0x00003104
	// (set) Token: 0x060000D8 RID: 216 RVA: 0x00004F0C File Offset: 0x0000310C
	internal virtual PictureBox PictureBox1
	{
		[CompilerGenerated]
		get
		{
			return this._PictureBox1;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			MouseEventHandler value2 = new MouseEventHandler(this.method_0);
			PictureBox pictureBox = this._PictureBox1;
			if (pictureBox != null)
			{
				pictureBox.MouseMove -= value2;
			}
			this._PictureBox1 = value;
			pictureBox = this._PictureBox1;
			if (pictureBox != null)
			{
				pictureBox.MouseMove += value2;
			}
		}
	}

	// Token: 0x1700009B RID: 155
	// (get) Token: 0x060000D9 RID: 217 RVA: 0x00004F50 File Offset: 0x00003150
	// (set) Token: 0x060000DA RID: 218 RVA: 0x00004F58 File Offset: 0x00003158
	internal virtual PictureBox PictureBox8
	{
		[CompilerGenerated]
		get
		{
			return this._PictureBox8;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			MouseEventHandler value2 = new MouseEventHandler(this.method_1);
			PictureBox pictureBox = this._PictureBox8;
			if (pictureBox != null)
			{
				pictureBox.MouseMove -= value2;
			}
			this._PictureBox8 = value;
			pictureBox = this._PictureBox8;
			if (pictureBox != null)
			{
				pictureBox.MouseMove += value2;
			}
		}
	}

	// Token: 0x1700009C RID: 156
	// (get) Token: 0x060000DB RID: 219 RVA: 0x00004F9C File Offset: 0x0000319C
	// (set) Token: 0x060000DC RID: 220 RVA: 0x00004FA4 File Offset: 0x000031A4
	internal virtual Control28 FlatButton1
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton1;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_2);
			Control28 flatButton = this._FlatButton1;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton1 = value;
			flatButton = this._FlatButton1;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700009D RID: 157
	// (get) Token: 0x060000DD RID: 221 RVA: 0x00004FE8 File Offset: 0x000031E8
	// (set) Token: 0x060000DE RID: 222 RVA: 0x00004FF0 File Offset: 0x000031F0
	internal virtual Control38 FlatListBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x060000DF RID: 223 RVA: 0x00004FFC File Offset: 0x000031FC
	private void arraylist_Load(object sender, EventArgs e)
	{
	}

	// Token: 0x060000E0 RID: 224 RVA: 0x00005000 File Offset: 0x00003200
	private void method_0(object sender, MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			base.Location += checked(new Size(e.X - this.point_0.X, e.Y - this.point_0.Y));
		}
		else
		{
			this.point_0 = e.Location;
		}
	}

	// Token: 0x060000E1 RID: 225 RVA: 0x00005064 File Offset: 0x00003264
	private void method_1(object sender, MouseEventArgs e)
	{
		if (e.Button != MouseButtons.Left)
		{
			this.point_1 = e.Location;
		}
		else
		{
			base.Location += checked(new Size(e.X - this.point_1.X, e.Y - this.point_1.Y));
		}
	}

	// Token: 0x060000E2 RID: 226 RVA: 0x000050C8 File Offset: 0x000032C8
	private void method_2(object sender, EventArgs e)
	{
		Class1.Class2_0.Skeet_0.FlatCheckBox4.Boolean_0 = false;
		base.Close();
	}

	// Token: 0x04000013 RID: 19
	private IContainer icontainer_0;

	// Token: 0x04000018 RID: 24
	private Point point_0;

	// Token: 0x04000019 RID: 25
	private Point point_1;
}
