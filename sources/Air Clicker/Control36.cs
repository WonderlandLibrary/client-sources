using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000050 RID: 80
internal class Control36 : Control
{
	// Token: 0x06000437 RID: 1079 RVA: 0x000148D0 File Offset: 0x00012AD0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x06000438 RID: 1080 RVA: 0x000148E8 File Offset: 0x00012AE8
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x06000439 RID: 1081 RVA: 0x00014900 File Offset: 0x00012B00
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x0600043A RID: 1082 RVA: 0x00014918 File Offset: 0x00012B18
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x0600043B RID: 1083 RVA: 0x00014930 File Offset: 0x00012B30
	private bool[] method_0()
	{
		bool[] array = new bool[4];
		try
		{
			foreach (object obj in base.Parent.Controls)
			{
				Control control = (Control)obj;
				if (control is Control36 && !(control == this | !this.Rectangle_0.IntersectsWith(this.Rectangle_0)))
				{
					double num = checked(Math.Atan2((double)(base.Left - control.Left), (double)(base.Top - control.Top))) * 2.0 / 3.1415926535897931;
					checked
					{
						if ((double)((long)Math.Round(num) / 1L) == num)
						{
							array[(int)Math.Round(unchecked(num + 1.0))] = true;
						}
					}
				}
			}
		}
		finally
		{
			IEnumerator enumerator;
			if (enumerator is IDisposable)
			{
				(enumerator as IDisposable).Dispose();
			}
		}
		return array;
	}

	// Token: 0x17000146 RID: 326
	// (get) Token: 0x0600043C RID: 1084 RVA: 0x00014A30 File Offset: 0x00012C30
	private Rectangle Rectangle_0
	{
		get
		{
			Rectangle result = new Rectangle(base.Left, base.Top, base.Width, base.Height);
			return result;
		}
	}

	// Token: 0x17000147 RID: 327
	// (get) Token: 0x0600043D RID: 1085 RVA: 0x00014A60 File Offset: 0x00012C60
	// (set) Token: 0x0600043E RID: 1086 RVA: 0x00014A78 File Offset: 0x00012C78
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

	// Token: 0x17000148 RID: 328
	// (get) Token: 0x0600043F RID: 1087 RVA: 0x00014A84 File Offset: 0x00012C84
	// (set) Token: 0x06000440 RID: 1088 RVA: 0x00014A9C File Offset: 0x00012C9C
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

	// Token: 0x17000149 RID: 329
	// (get) Token: 0x06000441 RID: 1089 RVA: 0x00014AA8 File Offset: 0x00012CA8
	// (set) Token: 0x06000442 RID: 1090 RVA: 0x00014AB0 File Offset: 0x00012CB0
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

	// Token: 0x06000443 RID: 1091 RVA: 0x00014ABC File Offset: 0x00012CBC
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
	}

	// Token: 0x06000444 RID: 1092 RVA: 0x00014AC8 File Offset: 0x00012CC8
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
	}

	// Token: 0x06000445 RID: 1093 RVA: 0x00014AD0 File Offset: 0x00012CD0
	public Control36()
	{
		this.enum2_0 = Enum2.None;
		this.bool_0 = false;
		this.color_0 = Class16.color_0;
		this.color_1 = Color.FromArgb(243, 243, 243);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.Size = new Size(106, 32);
		this.BackColor = Color.Transparent;
		this.Font = new Font("Segoe UI", 12f);
		this.Cursor = Cursors.Hand;
	}

	// Token: 0x06000446 RID: 1094 RVA: 0x00014B64 File Offset: 0x00012D64
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		this.int_0 = base.Width;
		this.int_1 = base.Height;
		GraphicsPath path = new GraphicsPath();
		bool[] array = this.method_0();
		GraphicsPath graphicsPath = Class16.smethod_1(0f, 0f, (float)this.int_0, (float)this.int_1, 0.3f, !(array[2] | array[1]), !(array[1] | array[0]), !(array[3] | array[0]), !(array[3] | array[2]));
		Rectangle rectangle = new Rectangle(0, 0, this.int_0, this.int_1);
		Graphics graphics_ = Class16.graphics_0;
		graphics_.SmoothingMode = SmoothingMode.HighQuality;
		graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics_.Clear(this.BackColor);
		switch (this.enum2_0)
		{
		case Enum2.None:
			if (!this.Boolean_0)
			{
				graphics_.FillRectangle(new SolidBrush(this.color_0), rectangle);
				graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
			}
			else
			{
				path = graphicsPath;
				graphics_.FillPath(new SolidBrush(this.color_0), path);
				graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
			}
			break;
		case Enum2.Over:
			if (!this.Boolean_0)
			{
				graphics_.FillRectangle(new SolidBrush(this.color_0), rectangle);
				graphics_.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.White)), rectangle);
				graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
			}
			else
			{
				path = graphicsPath;
				graphics_.FillPath(new SolidBrush(this.color_0), path);
				graphics_.FillPath(new SolidBrush(Color.FromArgb(20, Color.White)), path);
				graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
			}
			break;
		case Enum2.Down:
			if (!this.Boolean_0)
			{
				graphics_.FillRectangle(new SolidBrush(this.color_0), rectangle);
				graphics_.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.Black)), rectangle);
				graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
			}
			else
			{
				path = graphicsPath;
				graphics_.FillPath(new SolidBrush(this.color_0), path);
				graphics_.FillPath(new SolidBrush(Color.FromArgb(20, Color.Black)), path);
				graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
			}
			break;
		}
		base.OnPaint(e);
		Class16.graphics_0.Dispose();
		e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
		Class16.bitmap_0.Dispose();
	}

	// Token: 0x04000242 RID: 578
	private int int_0;

	// Token: 0x04000243 RID: 579
	private int int_1;

	// Token: 0x04000244 RID: 580
	private Enum2 enum2_0;

	// Token: 0x04000245 RID: 581
	private bool bool_0;

	// Token: 0x04000246 RID: 582
	private Color color_0;

	// Token: 0x04000247 RID: 583
	private Color color_1;
}
