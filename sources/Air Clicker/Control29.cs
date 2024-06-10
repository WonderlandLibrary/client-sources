using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x02000041 RID: 65
[DefaultEvent("CheckedChanged")]
internal class Control29 : Control
{
	// Token: 0x14000009 RID: 9
	// (add) Token: 0x0600039D RID: 925 RVA: 0x000117DC File Offset: 0x0000F9DC
	// (remove) Token: 0x0600039E RID: 926 RVA: 0x00011818 File Offset: 0x0000FA18
	public event Control29.Delegate10 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control29.Delegate10 @delegate = this.delegate10_0;
			Control29.Delegate10 delegate2;
			do
			{
				delegate2 = @delegate;
				Control29.Delegate10 value2 = (Control29.Delegate10)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control29.Delegate10>(ref this.delegate10_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control29.Delegate10 @delegate = this.delegate10_0;
			Control29.Delegate10 delegate2;
			do
			{
				delegate2 = @delegate;
				Control29.Delegate10 value2 = (Control29.Delegate10)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control29.Delegate10>(ref this.delegate10_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x17000128 RID: 296
	// (get) Token: 0x0600039F RID: 927 RVA: 0x00011854 File Offset: 0x0000FA54
	// (set) Token: 0x060003A0 RID: 928 RVA: 0x0001186C File Offset: 0x0000FA6C
	[Category("Options")]
	public Control29.Enum3 Enum3_0
	{
		get
		{
			return this.enum3_0;
		}
		set
		{
			this.enum3_0 = value;
		}
	}

	// Token: 0x17000129 RID: 297
	// (get) Token: 0x060003A1 RID: 929 RVA: 0x00011878 File Offset: 0x0000FA78
	// (set) Token: 0x060003A2 RID: 930 RVA: 0x00011880 File Offset: 0x0000FA80
	[Category("Options")]
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
		}
	}

	// Token: 0x060003A3 RID: 931 RVA: 0x0001188C File Offset: 0x0000FA8C
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x060003A4 RID: 932 RVA: 0x0001189C File Offset: 0x0000FA9C
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Width = 76;
		base.Height = 33;
	}

	// Token: 0x060003A5 RID: 933 RVA: 0x000118B8 File Offset: 0x0000FAB8
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x060003A6 RID: 934 RVA: 0x000118D0 File Offset: 0x0000FAD0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x060003A7 RID: 935 RVA: 0x000118E8 File Offset: 0x0000FAE8
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x060003A8 RID: 936 RVA: 0x00011900 File Offset: 0x0000FB00
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x060003A9 RID: 937 RVA: 0x00011918 File Offset: 0x0000FB18
	protected virtual void OnClick(EventArgs e)
	{
		base.OnClick(e);
		this.bool_0 = !this.bool_0;
		Control29.Delegate10 @delegate = this.delegate10_0;
		if (@delegate != null)
		{
			@delegate(this);
		}
	}

	// Token: 0x060003AA RID: 938 RVA: 0x0001194C File Offset: 0x0000FB4C
	public Control29()
	{
		this.bool_0 = false;
		this.enum2_0 = Enum2.None;
		this.color_0 = Class16.color_0;
		this.color_1 = Color.FromArgb(220, 85, 96);
		this.color_2 = Color.FromArgb(84, 85, 86);
		this.color_3 = Color.FromArgb(45, 47, 49);
		this.color_4 = Color.FromArgb(243, 243, 243);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		base.Size = new Size(44, checked(base.Height + 1));
		this.Cursor = Cursors.Hand;
		this.Font = new Font("Segoe UI", 10f);
		base.Size = new Size(76, 33);
	}

	// Token: 0x060003AB RID: 939 RVA: 0x00011A2C File Offset: 0x0000FC2C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			GraphicsPath path = new GraphicsPath();
			GraphicsPath graphicsPath = new GraphicsPath();
			Rectangle rectangle_ = new Rectangle(0, 0, this.int_0, this.int_1);
			Rectangle rectangle = new Rectangle(this.int_0 / 2, 0, 38, this.int_1);
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			switch (this.enum3_0)
			{
			case Control29.Enum3.Style1:
				path = Class16.smethod_0(rectangle_, 6);
				graphicsPath = Class16.smethod_0(rectangle, 6);
				graphics_.FillPath(new SolidBrush(this.color_2), path);
				graphics_.FillPath(new SolidBrush(this.color_3), graphicsPath);
				graphics_.DrawString("OFF", this.Font, new SolidBrush(this.color_2), new Rectangle(19, 1, this.int_0, this.int_1), Class16.stringFormat_1);
				if (this.Boolean_0)
				{
					path = Class16.smethod_0(rectangle_, 6);
					graphicsPath = Class16.smethod_0(new Rectangle(this.int_0 / 2, 0, 38, this.int_1), 6);
					graphics_.FillPath(new SolidBrush(this.color_3), path);
					graphics_.FillPath(new SolidBrush(this.color_0), graphicsPath);
					graphics_.DrawString("ON", this.Font, new SolidBrush(this.color_0), new Rectangle(8, 7, this.int_0, this.int_1), Class16.stringFormat_0);
				}
				break;
			case Control29.Enum3.Style2:
				path = Class16.smethod_0(rectangle_, 6);
				rectangle = new Rectangle(4, 4, 36, this.int_1 - 8);
				graphicsPath = Class16.smethod_0(rectangle, 4);
				graphics_.FillPath(new SolidBrush(this.color_1), path);
				graphics_.FillPath(new SolidBrush(this.color_3), graphicsPath);
				graphics_.DrawLine(new Pen(this.color_2), 18, 20, 18, 12);
				graphics_.DrawLine(new Pen(this.color_2), 22, 20, 22, 12);
				graphics_.DrawLine(new Pen(this.color_2), 26, 20, 26, 12);
				graphics_.DrawString("r", new Font("Marlett", 8f), new SolidBrush(this.color_4), new Rectangle(19, 2, base.Width, base.Height), Class16.stringFormat_1);
				if (this.Boolean_0)
				{
					path = Class16.smethod_0(rectangle_, 6);
					rectangle = new Rectangle(this.int_0 / 2 - 2, 4, 36, this.int_1 - 8);
					graphicsPath = Class16.smethod_0(rectangle, 4);
					graphics_.FillPath(new SolidBrush(this.color_0), path);
					graphics_.FillPath(new SolidBrush(this.color_3), graphicsPath);
					graphics_.DrawLine(new Pen(this.color_2), this.int_0 / 2 + 12, 20, this.int_0 / 2 + 12, 12);
					graphics_.DrawLine(new Pen(this.color_2), this.int_0 / 2 + 16, 20, this.int_0 / 2 + 16, 12);
					graphics_.DrawLine(new Pen(this.color_2), this.int_0 / 2 + 20, 20, this.int_0 / 2 + 20, 12);
					graphics_.DrawString("ü", new Font("Wingdings", 14f), new SolidBrush(this.color_4), new Rectangle(8, 7, base.Width, base.Height), Class16.stringFormat_0);
				}
				break;
			case Control29.Enum3.Style3:
				path = Class16.smethod_0(rectangle_, 16);
				rectangle = new Rectangle(this.int_0 - 28, 4, 22, this.int_1 - 8);
				graphicsPath.AddEllipse(rectangle);
				graphics_.FillPath(new SolidBrush(this.color_3), path);
				graphics_.FillPath(new SolidBrush(this.color_1), graphicsPath);
				graphics_.DrawString("OFF", this.Font, new SolidBrush(this.color_1), new Rectangle(-12, 2, this.int_0, this.int_1), Class16.stringFormat_1);
				if (this.Boolean_0)
				{
					path = Class16.smethod_0(rectangle_, 16);
					rectangle = new Rectangle(6, 4, 22, this.int_1 - 8);
					graphicsPath.Reset();
					graphicsPath.AddEllipse(rectangle);
					graphics_.FillPath(new SolidBrush(this.color_3), path);
					graphics_.FillPath(new SolidBrush(this.color_0), graphicsPath);
					graphics_.DrawString("ON", this.Font, new SolidBrush(this.color_0), new Rectangle(12, 2, this.int_0, this.int_1), Class16.stringFormat_1);
				}
				break;
			case Control29.Enum3.Style4:
				if (!this.Boolean_0)
				{
				}
				break;
			case Control29.Enum3.Style5:
				if (!this.Boolean_0)
				{
				}
				break;
			}
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x040001E7 RID: 487
	private int int_0;

	// Token: 0x040001E8 RID: 488
	private int int_1;

	// Token: 0x040001E9 RID: 489
	private Control29.Enum3 enum3_0;

	// Token: 0x040001EA RID: 490
	private bool bool_0;

	// Token: 0x040001EB RID: 491
	private Enum2 enum2_0;

	// Token: 0x040001EC RID: 492
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private Control29.Delegate10 delegate10_0;

	// Token: 0x040001ED RID: 493
	private Color color_0;

	// Token: 0x040001EE RID: 494
	private Color color_1;

	// Token: 0x040001EF RID: 495
	private Color color_2;

	// Token: 0x040001F0 RID: 496
	private Color color_3;

	// Token: 0x040001F1 RID: 497
	private Color color_4;

	// Token: 0x02000042 RID: 66
	// (Invoke) Token: 0x060003AF RID: 943
	public delegate void Delegate10(object sender);

	// Token: 0x02000043 RID: 67
	[Flags]
	public enum Enum3
	{
		// Token: 0x040001F3 RID: 499
		Style1 = 0,
		// Token: 0x040001F4 RID: 500
		Style2 = 1,
		// Token: 0x040001F5 RID: 501
		Style3 = 2,
		// Token: 0x040001F6 RID: 502
		Style4 = 3,
		// Token: 0x040001F7 RID: 503
		Style5 = 4
	}
}
