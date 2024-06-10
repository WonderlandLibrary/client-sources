using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000BA RID: 186
[DefaultEvent("ToggledChanged")]
internal class Control45 : Control
{
	// Token: 0x1400001E RID: 30
	// (add) Token: 0x06000891 RID: 2193 RVA: 0x00028980 File Offset: 0x00026B80
	// (remove) Token: 0x06000892 RID: 2194 RVA: 0x000289B8 File Offset: 0x00026BB8
	public event Control45.Delegate15 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control45.Delegate15 @delegate = this.delegate15_0;
			Control45.Delegate15 delegate2;
			do
			{
				delegate2 = @delegate;
				Control45.Delegate15 value2 = (Control45.Delegate15)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control45.Delegate15>(ref this.delegate15_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control45.Delegate15 @delegate = this.delegate15_0;
			Control45.Delegate15 delegate2;
			do
			{
				delegate2 = @delegate;
				Control45.Delegate15 value2 = (Control45.Delegate15)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control45.Delegate15>(ref this.delegate15_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x17000272 RID: 626
	// (get) Token: 0x06000893 RID: 2195 RVA: 0x000289F4 File Offset: 0x00026BF4
	// (set) Token: 0x06000894 RID: 2196 RVA: 0x000289FC File Offset: 0x00026BFC
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			base.Invalidate();
			Control45.Delegate15 @delegate = this.delegate15_0;
			if (@delegate != null)
			{
				@delegate();
			}
		}
	}

	// Token: 0x17000273 RID: 627
	// (get) Token: 0x06000895 RID: 2197 RVA: 0x00028A28 File Offset: 0x00026C28
	// (set) Token: 0x06000896 RID: 2198 RVA: 0x00028A40 File Offset: 0x00026C40
	public Control45.Enum12 Enum12_0
	{
		get
		{
			return this.enum12_0;
		}
		set
		{
			this.enum12_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000897 RID: 2199 RVA: 0x00028A50 File Offset: 0x00026C50
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Size = new Size(76, 33);
	}

	// Token: 0x06000898 RID: 2200 RVA: 0x00028A68 File Offset: 0x00026C68
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.Boolean_0 = !this.Boolean_0;
		base.Focus();
	}

	// Token: 0x06000899 RID: 2201 RVA: 0x00028A88 File Offset: 0x00026C88
	public Control45()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.DoubleBuffer, true);
	}

	// Token: 0x0600089A RID: 2202 RVA: 0x00028A9C File Offset: 0x00026C9C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Graphics graphics = e.Graphics;
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.Clear(base.Parent.BackColor);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			GraphicsPath path = new GraphicsPath();
			GraphicsPath path2 = new GraphicsPath();
			Rectangle rectangle = new Rectangle(0, 0, this.int_0, this.int_1);
			Rectangle rectangle2 = new Rectangle(this.int_0 / 2, 0, 38, this.int_1);
			Graphics graphics2 = graphics;
			graphics2.SmoothingMode = SmoothingMode.HighQuality;
			graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics2.Clear(this.BackColor);
			path = Class24.smethod_0(rectangle, 4);
			rectangle2 = new Rectangle(4, 4, 36, this.int_1 - 8);
			path2 = Class24.smethod_0(rectangle2, 4);
			graphics2.FillPath(new SolidBrush(Color.FromArgb(66, 76, 85)), path);
			graphics2.FillPath(new SolidBrush(Color.FromArgb(32, 41, 50)), path2);
			if (this.bool_0)
			{
				path = Class24.smethod_0(rectangle, 4);
				rectangle2 = new Rectangle(this.int_0 / 2 - 2, 4, 36, this.int_1 - 8);
				path2 = Class24.smethod_0(rectangle2, 4);
				graphics2.FillPath(new SolidBrush(Color.FromArgb(181, 41, 42)), path);
				graphics2.FillPath(new SolidBrush(Color.FromArgb(32, 41, 50)), path2);
			}
			switch (this.enum12_0)
			{
			case Control45.Enum12.CheckMark:
				if (!this.Boolean_0)
				{
					graphics.DrawString("r", new Font("Marlett", 14f, FontStyle.Regular), Brushes.DimGray, (float)(this.rectangle_0.X + 59), (float)(this.rectangle_0.Y + 18), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				else
				{
					graphics.DrawString("ü", new Font("Wingdings", 18f, FontStyle.Regular), Brushes.WhiteSmoke, (float)(this.rectangle_0.X + 18), (float)(this.rectangle_0.Y + 19), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				break;
			case Control45.Enum12.OnOff:
				if (!this.Boolean_0)
				{
					graphics.DrawString("OFF", new Font("Segoe UI", 12f, FontStyle.Regular), Brushes.DimGray, (float)(this.rectangle_0.X + 57), (float)(this.rectangle_0.Y + 16), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				else
				{
					graphics.DrawString("ON", new Font("Segoe UI", 12f, FontStyle.Regular), Brushes.WhiteSmoke, (float)(this.rectangle_0.X + 18), (float)(this.rectangle_0.Y + 16), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				break;
			case Control45.Enum12.YesNo:
				if (!this.Boolean_0)
				{
					graphics.DrawString("NO", new Font("Segoe UI", 12f, FontStyle.Regular), Brushes.DimGray, (float)(this.rectangle_0.X + 56), (float)(this.rectangle_0.Y + 16), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				else
				{
					graphics.DrawString("YES", new Font("Segoe UI", 12f, FontStyle.Regular), Brushes.WhiteSmoke, (float)(this.rectangle_0.X + 19), (float)(this.rectangle_0.Y + 16), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				break;
			case Control45.Enum12.IO:
				if (!this.Boolean_0)
				{
					graphics.DrawString("O", new Font("Segoe UI", 12f, FontStyle.Regular), Brushes.DimGray, (float)(this.rectangle_0.X + 57), (float)(this.rectangle_0.Y + 16), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				else
				{
					graphics.DrawString("I", new Font("Segoe UI", 12f, FontStyle.Regular), Brushes.WhiteSmoke, (float)(this.rectangle_0.X + 18), (float)(this.rectangle_0.Y + 16), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				break;
			}
		}
	}

	// Token: 0x0400041A RID: 1050
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Control45.Delegate15 delegate15_0;

	// Token: 0x0400041B RID: 1051
	private bool bool_0;

	// Token: 0x0400041C RID: 1052
	private Control45.Enum12 enum12_0;

	// Token: 0x0400041D RID: 1053
	private Rectangle rectangle_0;

	// Token: 0x0400041E RID: 1054
	private int int_0;

	// Token: 0x0400041F RID: 1055
	private int int_1;

	// Token: 0x020000BB RID: 187
	public enum Enum12
	{
		// Token: 0x04000421 RID: 1057
		CheckMark,
		// Token: 0x04000422 RID: 1058
		OnOff,
		// Token: 0x04000423 RID: 1059
		YesNo,
		// Token: 0x04000424 RID: 1060
		IO
	}

	// Token: 0x020000BC RID: 188
	// (Invoke) Token: 0x0600089E RID: 2206
	public delegate void Delegate15();
}
