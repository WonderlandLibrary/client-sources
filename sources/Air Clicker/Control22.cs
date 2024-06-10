using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200003A RID: 58
internal class Control22 : ContainerControl
{
	// Token: 0x1700010F RID: 271
	// (get) Token: 0x0600033E RID: 830 RVA: 0x0000FF8C File Offset: 0x0000E18C
	// (set) Token: 0x0600033F RID: 831 RVA: 0x0000FFA4 File Offset: 0x0000E1A4
	[Category("Colors")]
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
		}
	}

	// Token: 0x17000110 RID: 272
	// (get) Token: 0x06000340 RID: 832 RVA: 0x0000FFB0 File Offset: 0x0000E1B0
	// (set) Token: 0x06000341 RID: 833 RVA: 0x0000FFC8 File Offset: 0x0000E1C8
	[Category("Colors")]
	public Color Color_1
	{
		get
		{
			return this.color_1;
		}
		set
		{
			this.color_1 = value;
		}
	}

	// Token: 0x17000111 RID: 273
	// (get) Token: 0x06000342 RID: 834 RVA: 0x0000FFD4 File Offset: 0x0000E1D4
	// (set) Token: 0x06000343 RID: 835 RVA: 0x0000FFEC File Offset: 0x0000E1EC
	[Category("Colors")]
	public Color Color_2
	{
		get
		{
			return this.color_2;
		}
		set
		{
			this.color_2 = value;
		}
	}

	// Token: 0x17000112 RID: 274
	// (get) Token: 0x06000344 RID: 836 RVA: 0x0000FFF8 File Offset: 0x0000E1F8
	// (set) Token: 0x06000345 RID: 837 RVA: 0x0001000C File Offset: 0x0000E20C
	[Category("Colors")]
	public Color Color_3
	{
		get
		{
			return Class16.color_0;
		}
		set
		{
			Class16.color_0 = value;
		}
	}

	// Token: 0x17000113 RID: 275
	// (get) Token: 0x06000346 RID: 838 RVA: 0x00010014 File Offset: 0x0000E214
	// (set) Token: 0x06000347 RID: 839 RVA: 0x0001001C File Offset: 0x0000E21C
	[Category("Options")]
	public bool Boolean_0
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
		}
	}

	// Token: 0x06000348 RID: 840 RVA: 0x00010028 File Offset: 0x0000E228
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		if (e.Button == MouseButtons.Left & new Rectangle(0, 0, base.Width, Conversions.ToInteger(this.object_0)).Contains(e.Location))
		{
			this.bool_0 = true;
			this.point_0 = e.Location;
		}
	}

	// Token: 0x06000349 RID: 841 RVA: 0x00010088 File Offset: 0x0000E288
	private void Control22_MouseDoubleClick(object sender, MouseEventArgs e)
	{
		if (this.Boolean_0 && (e.Button == MouseButtons.Left & new Rectangle(0, 0, base.Width, Conversions.ToInteger(this.object_0)).Contains(e.Location)))
		{
			if (base.FindForm().WindowState != FormWindowState.Normal)
			{
				if (base.FindForm().WindowState == FormWindowState.Maximized)
				{
					base.FindForm().WindowState = FormWindowState.Normal;
					base.FindForm().Refresh();
				}
			}
			else
			{
				base.FindForm().WindowState = FormWindowState.Maximized;
				base.FindForm().Refresh();
			}
		}
	}

	// Token: 0x0600034A RID: 842 RVA: 0x00010128 File Offset: 0x0000E328
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.bool_0 = false;
	}

	// Token: 0x0600034B RID: 843 RVA: 0x00010138 File Offset: 0x0000E338
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		if (this.bool_0)
		{
			base.Parent.Location = Control.MousePosition - (Size)this.point_0;
		}
	}

	// Token: 0x0600034C RID: 844 RVA: 0x0001016C File Offset: 0x0000E36C
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		base.ParentForm.FormBorderStyle = FormBorderStyle.None;
		base.ParentForm.AllowTransparency = false;
		base.ParentForm.TransparencyKey = Color.Fuchsia;
		base.ParentForm.FindForm().StartPosition = FormStartPosition.CenterScreen;
		this.Dock = DockStyle.Fill;
		base.Invalidate();
	}

	// Token: 0x0600034D RID: 845 RVA: 0x000101C8 File Offset: 0x0000E3C8
	public Control22()
	{
		base.MouseDoubleClick += this.Control22_MouseDoubleClick;
		this.bool_0 = false;
		this.bool_1 = false;
		this.point_0 = new Point(0, 0);
		this.object_0 = 50;
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Color.FromArgb(60, 70, 73);
		this.color_2 = Color.FromArgb(53, 58, 60);
		this.color_3 = Color.FromArgb(234, 234, 234);
		this.color_4 = Color.FromArgb(171, 171, 172);
		this.color_5 = Color.FromArgb(196, 199, 200);
		this.color_6 = Color.FromArgb(45, 47, 49);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.White;
		this.Font = new Font("Segoe UI", 12f);
	}

	// Token: 0x0600034E RID: 846 RVA: 0x000102DC File Offset: 0x0000E4DC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		this.int_0 = base.Width;
		this.int_1 = base.Height;
		Rectangle rect = new Rectangle(0, 0, this.int_0, this.int_1);
		Rectangle rect2 = new Rectangle(0, 0, this.int_0, 50);
		Graphics graphics_ = Class16.graphics_0;
		graphics_.SmoothingMode = SmoothingMode.HighQuality;
		graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics_.Clear(this.BackColor);
		graphics_.FillRectangle(new SolidBrush(this.color_1), rect);
		graphics_.FillRectangle(new SolidBrush(this.color_0), rect2);
		graphics_.FillRectangle(new SolidBrush(Color.FromArgb(243, 243, 243)), new Rectangle(8, 16, 4, 18));
		graphics_.FillRectangle(new SolidBrush(Class16.color_0), 16, 16, 4, 18);
		graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_3), new Rectangle(26, 15, this.int_0, this.int_1), Class16.stringFormat_0);
		graphics_.DrawRectangle(new Pen(this.color_2), rect);
		base.OnPaint(e);
		Class16.graphics_0.Dispose();
		e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
		Class16.bitmap_0.Dispose();
	}

	// Token: 0x040001B9 RID: 441
	private int int_0;

	// Token: 0x040001BA RID: 442
	private int int_1;

	// Token: 0x040001BB RID: 443
	private bool bool_0;

	// Token: 0x040001BC RID: 444
	private bool bool_1;

	// Token: 0x040001BD RID: 445
	private Point point_0;

	// Token: 0x040001BE RID: 446
	private object object_0;

	// Token: 0x040001BF RID: 447
	private Color color_0;

	// Token: 0x040001C0 RID: 448
	private Color color_1;

	// Token: 0x040001C1 RID: 449
	private Color color_2;

	// Token: 0x040001C2 RID: 450
	private Color color_3;

	// Token: 0x040001C3 RID: 451
	private Color color_4;

	// Token: 0x040001C4 RID: 452
	private Color color_5;

	// Token: 0x040001C5 RID: 453
	public Color color_6;
}
