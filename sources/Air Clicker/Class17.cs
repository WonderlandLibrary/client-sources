using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x0200004F RID: 79
internal class Class17 : ComboBox
{
	// Token: 0x06000427 RID: 1063 RVA: 0x0001437C File Offset: 0x0001257C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x06000428 RID: 1064 RVA: 0x00014394 File Offset: 0x00012594
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x06000429 RID: 1065 RVA: 0x000143AC File Offset: 0x000125AC
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x0600042A RID: 1066 RVA: 0x000143C4 File Offset: 0x000125C4
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x0600042B RID: 1067 RVA: 0x000143DC File Offset: 0x000125DC
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_3 = e.Location.X;
		this.int_4 = e.Location.Y;
		base.Invalidate();
		if (e.X < checked(base.Width - 41))
		{
			this.Cursor = Cursors.IBeam;
		}
		else
		{
			this.Cursor = Cursors.Hand;
		}
	}

	// Token: 0x0600042C RID: 1068 RVA: 0x0001444C File Offset: 0x0001264C
	protected virtual void OnDrawItem(DrawItemEventArgs e)
	{
		base.OnDrawItem(e);
		base.Invalidate();
		if ((e.State & DrawItemState.Selected) == DrawItemState.Selected)
		{
			base.Invalidate();
		}
	}

	// Token: 0x0600042D RID: 1069 RVA: 0x00014470 File Offset: 0x00012670
	protected virtual void OnClick(EventArgs e)
	{
		base.OnClick(e);
		base.Invalidate();
	}

	// Token: 0x17000144 RID: 324
	// (get) Token: 0x0600042E RID: 1070 RVA: 0x00014480 File Offset: 0x00012680
	// (set) Token: 0x0600042F RID: 1071 RVA: 0x00014498 File Offset: 0x00012698
	[Category("Colors")]
	public Color Color_0
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

	// Token: 0x17000145 RID: 325
	// (get) Token: 0x06000430 RID: 1072 RVA: 0x000144A4 File Offset: 0x000126A4
	// (set) Token: 0x06000431 RID: 1073 RVA: 0x000144BC File Offset: 0x000126BC
	private int Int32_0
	{
		get
		{
			return this.int_2;
		}
		set
		{
			this.int_2 = value;
			try
			{
				this.method_0(value);
			}
			catch (Exception ex)
			{
			}
			base.Invalidate();
		}
	}

	// Token: 0x06000432 RID: 1074 RVA: 0x000144FC File Offset: 0x000126FC
	public void Class17_DrawItem(object sender, DrawItemEventArgs e)
	{
		if (e.Index >= 0)
		{
			e.DrawBackground();
			e.DrawFocusRectangle();
			e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
			e.Graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			if ((e.State & DrawItemState.Selected) == DrawItemState.Selected)
			{
				e.Graphics.FillRectangle(new SolidBrush(this.color_2), e.Bounds);
			}
			else
			{
				e.Graphics.FillRectangle(new SolidBrush(this.color_0), e.Bounds);
			}
			e.Graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), new Font("Segoe UI", 8f), Brushes.White, checked(new Rectangle(e.Bounds.X + 2, e.Bounds.Y + 2, e.Bounds.Width, e.Bounds.Height)));
			e.Graphics.Dispose();
		}
	}

	// Token: 0x06000433 RID: 1075 RVA: 0x00014628 File Offset: 0x00012828
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 18;
	}

	// Token: 0x06000434 RID: 1076 RVA: 0x0001463C File Offset: 0x0001283C
	public Class17()
	{
		base.DrawItem += this.Class17_DrawItem;
		this.int_2 = 0;
		this.enum2_0 = Enum2.None;
		this.color_0 = Color.FromArgb(25, 27, 29);
		this.color_1 = Color.FromArgb(45, 47, 49);
		this.color_2 = Color.FromArgb(35, 168, 109);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.DrawMode = DrawMode.OwnerDrawFixed;
		this.BackColor = Color.FromArgb(45, 45, 48);
		this.ForeColor = Color.White;
		base.DropDownStyle = ComboBoxStyle.DropDownList;
		this.Cursor = Cursors.Hand;
		this.Int32_0 = 0;
		base.ItemHeight = 18;
		this.Font = new Font("Segoe UI", 8f, FontStyle.Regular);
	}

	// Token: 0x06000435 RID: 1077 RVA: 0x00014714 File Offset: 0x00012914
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		this.int_0 = base.Width;
		this.int_1 = base.Height;
		Rectangle rect = new Rectangle(0, 0, this.int_0, this.int_1);
		checked
		{
			Rectangle rect2 = new Rectangle(this.int_0 - 40, 0, this.int_0, this.int_1);
			GraphicsPath graphicsPath = new GraphicsPath();
			new GraphicsPath();
			Graphics graphics_ = Class16.graphics_0;
			graphics_.Clear(Color.FromArgb(45, 45, 48));
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.FillRectangle(new SolidBrush(this.color_1), rect);
			graphicsPath.Reset();
			graphicsPath.AddRectangle(rect2);
			graphics_.SetClip(graphicsPath);
			graphics_.FillRectangle(new SolidBrush(this.color_0), rect2);
			graphics_.ResetClip();
			graphics_.DrawLine(Pens.White, this.int_0 - 10, 6, this.int_0 - 30, 6);
			graphics_.DrawLine(Pens.White, this.int_0 - 10, 12, this.int_0 - 30, 12);
			graphics_.DrawLine(Pens.White, this.int_0 - 10, 18, this.int_0 - 30, 18);
			graphics_.DrawString(this.Text, this.Font, Brushes.White, new Point(4, 6), Class16.stringFormat_0);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x06000436 RID: 1078 RVA: 0x000148C4 File Offset: 0x00012AC4
	void method_0(int int_5)
	{
		base.SelectedIndex = int_5;
	}

	// Token: 0x04000239 RID: 569
	private int int_0;

	// Token: 0x0400023A RID: 570
	private int int_1;

	// Token: 0x0400023B RID: 571
	private int int_2;

	// Token: 0x0400023C RID: 572
	private int int_3;

	// Token: 0x0400023D RID: 573
	private int int_4;

	// Token: 0x0400023E RID: 574
	private Enum2 enum2_0;

	// Token: 0x0400023F RID: 575
	private Color color_0;

	// Token: 0x04000240 RID: 576
	private Color color_1;

	// Token: 0x04000241 RID: 577
	private Color color_2;
}
