using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000C1 RID: 193
[DefaultEvent("TextChanged")]
internal class Control48 : Control
{
	// Token: 0x17000276 RID: 630
	// (get) Token: 0x060008BB RID: 2235 RVA: 0x00029524 File Offset: 0x00027724
	// (set) Token: 0x060008BC RID: 2236 RVA: 0x0002952C File Offset: 0x0002772C
	public virtual TextBox TextBox_0
	{
		[CompilerGenerated]
		get
		{
			return this.textBox_0;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_6);
			TextBox textBox = this.textBox_0;
			if (textBox != null)
			{
				textBox.TextChanged -= value2;
			}
			this.textBox_0 = value;
			textBox = this.textBox_0;
			if (textBox != null)
			{
				textBox.TextChanged += value2;
			}
		}
	}

	// Token: 0x17000277 RID: 631
	// (get) Token: 0x060008BD RID: 2237 RVA: 0x00029570 File Offset: 0x00027770
	// (set) Token: 0x060008BE RID: 2238 RVA: 0x00029588 File Offset: 0x00027788
	public HorizontalAlignment HorizontalAlignment_0
	{
		get
		{
			return this.horizontalAlignment_0;
		}
		set
		{
			this.horizontalAlignment_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000278 RID: 632
	// (get) Token: 0x060008BF RID: 2239 RVA: 0x00029598 File Offset: 0x00027798
	// (set) Token: 0x060008C0 RID: 2240 RVA: 0x000295B0 File Offset: 0x000277B0
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			this.int_0 = value;
			this.TextBox_0.MaxLength = this.Int32_0;
			base.Invalidate();
		}
	}

	// Token: 0x17000279 RID: 633
	// (get) Token: 0x060008C1 RID: 2241 RVA: 0x000295D0 File Offset: 0x000277D0
	// (set) Token: 0x060008C2 RID: 2242 RVA: 0x000295D8 File Offset: 0x000277D8
	public bool Boolean_0
	{
		get
		{
			return this.bool_2;
		}
		set
		{
			this.TextBox_0.UseSystemPasswordChar = this.Boolean_0;
			this.bool_2 = value;
			base.Invalidate();
		}
	}

	// Token: 0x1700027A RID: 634
	// (get) Token: 0x060008C3 RID: 2243 RVA: 0x000295F8 File Offset: 0x000277F8
	// (set) Token: 0x060008C4 RID: 2244 RVA: 0x00029600 File Offset: 0x00027800
	public bool Boolean_1
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			if (this.TextBox_0 != null)
			{
				this.TextBox_0.ReadOnly = value;
			}
		}
	}

	// Token: 0x1700027B RID: 635
	// (get) Token: 0x060008C5 RID: 2245 RVA: 0x00029620 File Offset: 0x00027820
	// (set) Token: 0x060008C6 RID: 2246 RVA: 0x00029628 File Offset: 0x00027828
	public bool Boolean_2
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
			checked
			{
				if (this.TextBox_0 != null)
				{
					this.TextBox_0.Multiline = value;
					if (!value)
					{
						base.Height = this.TextBox_0.Height + 23;
					}
					else
					{
						this.TextBox_0.Height = base.Height - 23;
					}
				}
			}
		}
	}

	// Token: 0x1700027C RID: 636
	// (get) Token: 0x060008C7 RID: 2247 RVA: 0x00029680 File Offset: 0x00027880
	// (set) Token: 0x060008C8 RID: 2248 RVA: 0x00029698 File Offset: 0x00027898
	public Image Image_0
	{
		get
		{
			return this.image_0;
		}
		set
		{
			if (value != null)
			{
				this.size_0 = value.Size;
			}
			else
			{
				this.size_0 = Size.Empty;
			}
			this.image_0 = value;
			if (this.Image_0 == null)
			{
				this.TextBox_0.Location = new Point(8, 10);
			}
			else
			{
				this.TextBox_0.Location = new Point(35, 11);
			}
			base.Invalidate();
		}
	}

	// Token: 0x1700027D RID: 637
	// (get) Token: 0x060008C9 RID: 2249 RVA: 0x00029708 File Offset: 0x00027908
	protected Size Size_0
	{
		get
		{
			return this.size_0;
		}
	}

	// Token: 0x060008CA RID: 2250 RVA: 0x00029720 File Offset: 0x00027920
	private void method_0(object sender, EventArgs e)
	{
		this.pen_0 = new Pen(Color.FromArgb(181, 41, 42));
		this.Refresh();
	}

	// Token: 0x060008CB RID: 2251 RVA: 0x00029744 File Offset: 0x00027944
	private void method_1(object sender, EventArgs e)
	{
		this.pen_0 = new Pen(Color.FromArgb(32, 41, 50));
		this.Refresh();
	}

	// Token: 0x060008CC RID: 2252 RVA: 0x00029764 File Offset: 0x00027964
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x060008CD RID: 2253 RVA: 0x00029774 File Offset: 0x00027974
	protected virtual void OnForeColorChanged(EventArgs e)
	{
		base.OnForeColorChanged(e);
		this.TextBox_0.ForeColor = this.ForeColor;
		base.Invalidate();
	}

	// Token: 0x060008CE RID: 2254 RVA: 0x00029794 File Offset: 0x00027994
	protected virtual void OnFontChanged(EventArgs e)
	{
		base.OnFontChanged(e);
		this.TextBox_0.Font = this.Font;
	}

	// Token: 0x060008CF RID: 2255 RVA: 0x000297B0 File Offset: 0x000279B0
	protected virtual void OnPaintBackground(PaintEventArgs pevent)
	{
		base.OnPaintBackground(pevent);
	}

	// Token: 0x060008D0 RID: 2256 RVA: 0x000297BC File Offset: 0x000279BC
	private void method_2(object sender, KeyEventArgs e)
	{
		if (e.Control && e.KeyCode == Keys.A)
		{
			this.TextBox_0.SelectAll();
			e.SuppressKeyPress = true;
		}
		if (e.Control && e.KeyCode == Keys.C)
		{
			this.TextBox_0.Copy();
			e.SuppressKeyPress = true;
		}
	}

	// Token: 0x060008D1 RID: 2257 RVA: 0x0002981C File Offset: 0x00027A1C
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		checked
		{
			if (!this.bool_1)
			{
				base.Height = this.TextBox_0.Height + 23;
			}
			else
			{
				this.TextBox_0.Height = base.Height - 23;
			}
			this.graphicsPath_0 = new GraphicsPath();
			GraphicsPath graphicsPath = this.graphicsPath_0;
			graphicsPath.AddArc(0, 0, 10, 10, 180f, 90f);
			graphicsPath.AddArc(base.Width - 11, 0, 10, 10, -90f, 90f);
			graphicsPath.AddArc(base.Width - 11, base.Height - 11, 10, 10, 0f, 90f);
			graphicsPath.AddArc(0, base.Height - 11, 10, 10, 90f, 90f);
			graphicsPath.CloseAllFigures();
		}
	}

	// Token: 0x060008D2 RID: 2258 RVA: 0x000298F4 File Offset: 0x00027AF4
	protected virtual void OnGotFocus(EventArgs e)
	{
		base.OnGotFocus(e);
		this.TextBox_0.Focus();
	}

	// Token: 0x060008D3 RID: 2259 RVA: 0x0002990C File Offset: 0x00027B0C
	public void method_3()
	{
		this.Text = this.TextBox_0.Text;
	}

	// Token: 0x060008D4 RID: 2260 RVA: 0x00029920 File Offset: 0x00027B20
	public void method_4()
	{
		this.TextBox_0.Text = this.Text;
	}

	// Token: 0x060008D5 RID: 2261 RVA: 0x00029934 File Offset: 0x00027B34
	public void method_5()
	{
		TextBox textBox = this.TextBox_0;
		textBox.Location = new Point(8, 10);
		textBox.Text = string.Empty;
		textBox.BorderStyle = BorderStyle.None;
		textBox.TextAlign = HorizontalAlignment.Left;
		textBox.Font = new Font("Tahoma", 11f);
		textBox.UseSystemPasswordChar = this.Boolean_0;
		textBox.Multiline = false;
		textBox.BackColor = Color.FromArgb(66, 76, 85);
		textBox.ScrollBars = ScrollBars.None;
		this.TextBox_0.KeyDown += this.method_2;
		this.TextBox_0.Enter += this.method_0;
		this.TextBox_0.Leave += this.method_1;
	}

	// Token: 0x060008D6 RID: 2262 RVA: 0x000299F8 File Offset: 0x00027BF8
	public Control48()
	{
		base.TextChanged += this.Control48_TextChanged;
		this.TextBox_0 = new TextBox();
		this.int_0 = 32767;
		this.bool_2 = false;
		base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
		base.SetStyle(ControlStyles.UserPaint, true);
		this.method_5();
		base.Controls.Add(this.TextBox_0);
		this.pen_0 = new Pen(Color.FromArgb(32, 41, 50));
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(66, 76, 85));
		this.BackColor = Color.Transparent;
		this.ForeColor = Color.FromArgb(176, 183, 191);
		this.Text = null;
		this.Font = new Font("Tahoma", 11f);
		base.Size = new Size(135, 43);
		this.DoubleBuffered = true;
	}

	// Token: 0x060008D7 RID: 2263 RVA: 0x00029AEC File Offset: 0x00027CEC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		graphics.SmoothingMode = SmoothingMode.AntiAlias;
		TextBox textBox = this.TextBox_0;
		checked
		{
			if (this.Image_0 != null)
			{
				textBox.Width = base.Width - 45;
			}
			else
			{
				textBox.Width = base.Width - 18;
			}
			textBox.TextAlign = this.HorizontalAlignment_0;
			textBox.UseSystemPasswordChar = this.Boolean_0;
			graphics.Clear(Color.Transparent);
			graphics.FillPath(this.solidBrush_0, this.graphicsPath_0);
			graphics.DrawPath(this.pen_0, this.graphicsPath_0);
			if (this.Image_0 != null)
			{
				graphics.DrawImage(this.image_0, 5, 8, 24, 24);
			}
			NewLateBinding.LateCall(e.Graphics, null, "DrawImage", new object[]
			{
				bitmap.Clone(),
				0,
				0
			}, null, null, null, true);
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x060008D8 RID: 2264 RVA: 0x00029BFC File Offset: 0x00027DFC
	[DebuggerHidden]
	[CompilerGenerated]
	private void method_6(object sender, EventArgs e)
	{
		this.method_3();
	}

	// Token: 0x060008D9 RID: 2265 RVA: 0x00029C04 File Offset: 0x00027E04
	[CompilerGenerated]
	[DebuggerHidden]
	private void Control48_TextChanged(object sender, EventArgs e)
	{
		this.method_4();
	}

	// Token: 0x0400042C RID: 1068
	[AccessedThroughProperty("MonoFlatTB")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private TextBox textBox_0;

	// Token: 0x0400042D RID: 1069
	private int int_0;

	// Token: 0x0400042E RID: 1070
	private bool bool_0;

	// Token: 0x0400042F RID: 1071
	private bool bool_1;

	// Token: 0x04000430 RID: 1072
	private Image image_0;

	// Token: 0x04000431 RID: 1073
	private Size size_0;

	// Token: 0x04000432 RID: 1074
	private HorizontalAlignment horizontalAlignment_0;

	// Token: 0x04000433 RID: 1075
	private bool bool_2;

	// Token: 0x04000434 RID: 1076
	private Pen pen_0;

	// Token: 0x04000435 RID: 1077
	private SolidBrush solidBrush_0;

	// Token: 0x04000436 RID: 1078
	private GraphicsPath graphicsPath_0;
}
