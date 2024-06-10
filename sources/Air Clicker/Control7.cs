using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x0200001A RID: 26
[DefaultEvent("TextChanged")]
internal class Control7 : Control
{
	// Token: 0x170000D5 RID: 213
	// (get) Token: 0x06000234 RID: 564 RVA: 0x00009844 File Offset: 0x00007A44
	// (set) Token: 0x06000235 RID: 565 RVA: 0x0000985C File Offset: 0x00007A5C
	public HorizontalAlignment HorizontalAlignment_0
	{
		get
		{
			return this.horizontalAlignment_0;
		}
		set
		{
			this.horizontalAlignment_0 = value;
			if (this.textBox_0 != null)
			{
				this.textBox_0.TextAlign = value;
			}
		}
	}

	// Token: 0x170000D6 RID: 214
	// (get) Token: 0x06000236 RID: 566 RVA: 0x0000987C File Offset: 0x00007A7C
	// (set) Token: 0x06000237 RID: 567 RVA: 0x00009894 File Offset: 0x00007A94
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			this.int_0 = value;
			if (this.textBox_0 != null)
			{
				this.textBox_0.MaxLength = value;
			}
		}
	}

	// Token: 0x170000D7 RID: 215
	// (get) Token: 0x06000238 RID: 568 RVA: 0x000098B4 File Offset: 0x00007AB4
	// (set) Token: 0x06000239 RID: 569 RVA: 0x000098BC File Offset: 0x00007ABC
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			if (this.textBox_0 != null)
			{
				this.textBox_0.ReadOnly = value;
			}
		}
	}

	// Token: 0x170000D8 RID: 216
	// (get) Token: 0x0600023A RID: 570 RVA: 0x000098DC File Offset: 0x00007ADC
	// (set) Token: 0x0600023B RID: 571 RVA: 0x000098E4 File Offset: 0x00007AE4
	public bool Boolean_1
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
			if (this.textBox_0 != null)
			{
				this.textBox_0.UseSystemPasswordChar = value;
			}
		}
	}

	// Token: 0x170000D9 RID: 217
	// (get) Token: 0x0600023C RID: 572 RVA: 0x00009904 File Offset: 0x00007B04
	// (set) Token: 0x0600023D RID: 573 RVA: 0x0000990C File Offset: 0x00007B0C
	public bool Boolean_2
	{
		get
		{
			return this.bool_2;
		}
		set
		{
			this.bool_2 = value;
			checked
			{
				if (this.textBox_0 != null)
				{
					this.textBox_0.Multiline = value;
					if (value)
					{
						this.textBox_0.Height = base.Height - 11;
					}
					else
					{
						base.Height = this.textBox_0.Height + 11;
					}
				}
			}
		}
	}

	// Token: 0x170000DA RID: 218
	// (get) Token: 0x0600023E RID: 574 RVA: 0x00009964 File Offset: 0x00007B64
	// (set) Token: 0x0600023F RID: 575 RVA: 0x0000997C File Offset: 0x00007B7C
	public virtual string Text
	{
		get
		{
			return this.method_0();
		}
		set
		{
			this.method_1(value);
			if (this.textBox_0 != null)
			{
				this.textBox_0.Text = value;
			}
		}
	}

	// Token: 0x170000DB RID: 219
	// (get) Token: 0x06000240 RID: 576 RVA: 0x0000999C File Offset: 0x00007B9C
	// (set) Token: 0x06000241 RID: 577 RVA: 0x000099B4 File Offset: 0x00007BB4
	public virtual Font Font
	{
		get
		{
			return this.method_2();
		}
		set
		{
			this.method_3(value);
			checked
			{
				if (this.textBox_0 != null)
				{
					this.textBox_0.Font = value;
					this.textBox_0.Location = new Point(5, 5);
					this.textBox_0.Width = base.Width - 8;
					if (!this.bool_2)
					{
						base.Height = this.textBox_0.Height + 11;
					}
				}
			}
		}
	}

	// Token: 0x06000242 RID: 578 RVA: 0x00009A24 File Offset: 0x00007C24
	protected virtual void OnHandleCreated(EventArgs e)
	{
		if (!base.Controls.Contains(this.textBox_0))
		{
			base.Controls.Add(this.textBox_0);
		}
		base.OnHandleCreated(e);
	}

	// Token: 0x06000243 RID: 579 RVA: 0x00009A54 File Offset: 0x00007C54
	public Control7()
	{
		this.horizontalAlignment_0 = HorizontalAlignment.Left;
		this.int_0 = 32767;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, true);
		this.Cursor = Cursors.IBeam;
		this.textBox_0 = new TextBox();
		this.textBox_0.Font = this.System.Windows.Forms.Control.Font;
		this.textBox_0.Text = this.System.Windows.Forms.Control.Text;
		this.textBox_0.MaxLength = this.int_0;
		this.textBox_0.Multiline = this.bool_2;
		this.textBox_0.ReadOnly = this.bool_0;
		this.textBox_0.UseSystemPasswordChar = this.bool_1;
		this.textBox_0.ForeColor = Color.FromArgb(235, 235, 235);
		this.textBox_0.BackColor = Color.FromArgb(50, 50, 50);
		this.textBox_0.BorderStyle = BorderStyle.None;
		this.textBox_0.Location = new Point(5, 5);
		checked
		{
			this.textBox_0.Width = base.Width - 14;
			if (!this.bool_2)
			{
				base.Height = this.textBox_0.Height + 11;
			}
			else
			{
				this.textBox_0.Height = base.Height - 11;
			}
			this.textBox_0.TextChanged += this.textBox_0_TextChanged;
			this.textBox_0.KeyDown += this.textBox_0_KeyDown;
			this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
			this.pen_1 = new Pen(Color.FromArgb(55, 55, 55));
		}
	}

	// Token: 0x06000244 RID: 580 RVA: 0x00009C00 File Offset: 0x00007E00
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			this.graphicsPath_0 = Class9.smethod_2(0, 0, base.Width - 1, base.Height - 1, 7);
			this.graphicsPath_1 = Class9.smethod_2(1, 1, base.Width - 3, base.Height - 3, 7);
			this.pathGradientBrush_0 = new PathGradientBrush(this.graphicsPath_0);
			this.pathGradientBrush_0.CenterColor = Color.FromArgb(50, 50, 50);
			this.pathGradientBrush_0.SurroundColors = new Color[]
			{
				Color.FromArgb(45, 45, 45)
			};
			this.pathGradientBrush_0.FocusScales = new PointF(0.9f, 0.5f);
			Class9.graphics_0.FillPath(this.pathGradientBrush_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_1);
		}
	}

	// Token: 0x06000245 RID: 581 RVA: 0x00009D18 File Offset: 0x00007F18
	private void textBox_0_TextChanged(object sender, EventArgs e)
	{
		this.System.Windows.Forms.Control.Text = this.textBox_0.Text;
	}

	// Token: 0x06000246 RID: 582 RVA: 0x00009D2C File Offset: 0x00007F2C
	private void textBox_0_KeyDown(object sender, KeyEventArgs e)
	{
		if (e.Control && e.KeyCode == Keys.A)
		{
			this.textBox_0.SelectAll();
			e.SuppressKeyPress = true;
		}
	}

	// Token: 0x06000247 RID: 583 RVA: 0x00009D58 File Offset: 0x00007F58
	protected virtual void OnResize(EventArgs e)
	{
		this.textBox_0.Location = new Point(5, 5);
		checked
		{
			this.textBox_0.Width = base.Width - 10;
			this.textBox_0.Height = base.Height - 11;
			base.OnResize(e);
		}
	}

	// Token: 0x06000248 RID: 584 RVA: 0x00009DA8 File Offset: 0x00007FA8
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		this.textBox_0.Focus();
		base.OnMouseDown(e);
	}

	// Token: 0x06000249 RID: 585 RVA: 0x00009DC0 File Offset: 0x00007FC0
	protected virtual void OnEnter(EventArgs e)
	{
		this.textBox_0.Focus();
		base.Invalidate();
		base.OnEnter(e);
	}

	// Token: 0x0600024A RID: 586 RVA: 0x00009DDC File Offset: 0x00007FDC
	protected virtual void OnLeave(EventArgs e)
	{
		base.Invalidate();
		base.OnLeave(e);
	}

	// Token: 0x0600024B RID: 587 RVA: 0x00009DEC File Offset: 0x00007FEC
	string method_0()
	{
		return base.Text;
	}

	// Token: 0x0600024C RID: 588 RVA: 0x00009DF4 File Offset: 0x00007FF4
	void method_1(string string_0)
	{
		base.Text = string_0;
	}

	// Token: 0x0600024D RID: 589 RVA: 0x00009E00 File Offset: 0x00008000
	Font method_2()
	{
		return base.Font;
	}

	// Token: 0x0600024E RID: 590 RVA: 0x00009E08 File Offset: 0x00008008
	void method_3(Font font_0)
	{
		base.Font = font_0;
	}

	// Token: 0x040000B1 RID: 177
	private HorizontalAlignment horizontalAlignment_0;

	// Token: 0x040000B2 RID: 178
	private int int_0;

	// Token: 0x040000B3 RID: 179
	private bool bool_0;

	// Token: 0x040000B4 RID: 180
	private bool bool_1;

	// Token: 0x040000B5 RID: 181
	private bool bool_2;

	// Token: 0x040000B6 RID: 182
	private TextBox textBox_0;

	// Token: 0x040000B7 RID: 183
	private GraphicsPath graphicsPath_0;

	// Token: 0x040000B8 RID: 184
	private GraphicsPath graphicsPath_1;

	// Token: 0x040000B9 RID: 185
	private Pen pen_0;

	// Token: 0x040000BA RID: 186
	private Pen pen_1;

	// Token: 0x040000BB RID: 187
	private PathGradientBrush pathGradientBrush_0;
}
