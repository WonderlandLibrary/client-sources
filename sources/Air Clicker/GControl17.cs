using System;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x02000072 RID: 114
public class GControl17 : Control
{
	// Token: 0x17000187 RID: 391
	// (get) Token: 0x0600053B RID: 1339 RVA: 0x0001A9B8 File Offset: 0x00018BB8
	// (set) Token: 0x0600053C RID: 1340 RVA: 0x0001A9C0 File Offset: 0x00018BC0
	private virtual TextBox TextBox_0
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
			EventHandler value2 = new EventHandler(this.method_3);
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

	// Token: 0x17000188 RID: 392
	// (get) Token: 0x0600053D RID: 1341 RVA: 0x0001AA04 File Offset: 0x00018C04
	// (set) Token: 0x0600053E RID: 1342 RVA: 0x0001AA0C File Offset: 0x00018C0C
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.TextBox_0.UseSystemPasswordChar = this.Boolean_0;
			this.bool_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000189 RID: 393
	// (get) Token: 0x0600053F RID: 1343 RVA: 0x0001AA2C File Offset: 0x00018C2C
	// (set) Token: 0x06000540 RID: 1344 RVA: 0x0001AA44 File Offset: 0x00018C44
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

	// Token: 0x1700018A RID: 394
	// (get) Token: 0x06000541 RID: 1345 RVA: 0x0001AA64 File Offset: 0x00018C64
	// (set) Token: 0x06000542 RID: 1346 RVA: 0x0001AA7C File Offset: 0x00018C7C
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

	// Token: 0x1700018B RID: 395
	// (get) Token: 0x06000543 RID: 1347 RVA: 0x0001AA8C File Offset: 0x00018C8C
	// (set) Token: 0x06000544 RID: 1348 RVA: 0x0001AA94 File Offset: 0x00018C94
	public bool Boolean_1
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
			this.TextBox_0.Multiline = value;
			this.System.Windows.Forms.Control.OnResize(EventArgs.Empty);
			base.Invalidate();
		}
	}

	// Token: 0x1700018C RID: 396
	// (get) Token: 0x06000545 RID: 1349 RVA: 0x0001AABC File Offset: 0x00018CBC
	// (set) Token: 0x06000546 RID: 1350 RVA: 0x0001AAC4 File Offset: 0x00018CC4
	public bool Boolean_2
	{
		get
		{
			return this.bool_2;
		}
		set
		{
			this.bool_2 = value;
			if (this.TextBox_0 != null)
			{
				this.TextBox_0.ReadOnly = value;
			}
		}
	}

	// Token: 0x06000547 RID: 1351 RVA: 0x0001AAE4 File Offset: 0x00018CE4
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x06000548 RID: 1352 RVA: 0x0001AAF4 File Offset: 0x00018CF4
	protected virtual void OnBackColorChanged(EventArgs e)
	{
		base.OnBackColorChanged(e);
		base.Invalidate();
	}

	// Token: 0x06000549 RID: 1353 RVA: 0x0001AB04 File Offset: 0x00018D04
	protected virtual void OnForeColorChanged(EventArgs e)
	{
		base.OnForeColorChanged(e);
		this.TextBox_0.ForeColor = this.ForeColor;
		base.Invalidate();
	}

	// Token: 0x0600054A RID: 1354 RVA: 0x0001AB24 File Offset: 0x00018D24
	protected virtual void OnFontChanged(EventArgs e)
	{
		base.OnFontChanged(e);
		this.TextBox_0.Font = this.Font;
	}

	// Token: 0x0600054B RID: 1355 RVA: 0x0001AB40 File Offset: 0x00018D40
	protected virtual void OnGotFocus(EventArgs e)
	{
		base.OnGotFocus(e);
		this.TextBox_0.Focus();
	}

	// Token: 0x0600054C RID: 1356 RVA: 0x0001AB58 File Offset: 0x00018D58
	private void method_0()
	{
		this.Text = this.TextBox_0.Text;
	}

	// Token: 0x0600054D RID: 1357 RVA: 0x0001AB6C File Offset: 0x00018D6C
	private void method_1()
	{
		this.TextBox_0.Text = this.Text;
	}

	// Token: 0x0600054E RID: 1358 RVA: 0x0001AB80 File Offset: 0x00018D80
	public void method_2()
	{
		TextBox textBox = this.TextBox_0;
		textBox.Text = string.Empty;
		textBox.BackColor = Color.FromArgb(36, 36, 36);
		textBox.ForeColor = this.ForeColor;
		textBox.TextAlign = HorizontalAlignment.Center;
		textBox.BorderStyle = BorderStyle.None;
		textBox.Location = new Point(3, 3);
		textBox.Font = new Font("Segoe UI", 11f, FontStyle.Regular);
		textBox.Size = checked(new Size(base.Width - 3, base.Height - 3));
		textBox.UseSystemPasswordChar = this.Boolean_0;
	}

	// Token: 0x0600054F RID: 1359 RVA: 0x0001AC18 File Offset: 0x00018E18
	public GControl17()
	{
		base.TextChanged += this.GControl17_TextChanged;
		this.TextBox_0 = new TextBox();
		this.bool_0 = false;
		this.int_0 = 32767;
		this.bool_1 = false;
		this.bool_2 = false;
		this.method_2();
		base.Controls.Add(this.TextBox_0);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.FromArgb(36, 36, 36);
		this.ForeColor = Color.FromArgb(245, 245, 245);
		this.Font = new Font("Segoe UI", 11f, FontStyle.Regular);
		base.Size = new Size(200, 26);
	}

	// Token: 0x06000550 RID: 1360 RVA: 0x0001ACE8 File Offset: 0x00018EE8
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rectangle_ = checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1));
		base.OnPaint(e);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		graphics.TextRenderingHint = TextRenderingHint.AntiAlias;
		TextBox textBox = this.TextBox_0;
		textBox.TextAlign = this.HorizontalAlignment_0;
		textBox.UseSystemPasswordChar = this.Boolean_0;
		graphics.FillPath(new SolidBrush(Color.FromArgb(36, 36, 36)), Class22.smethod_0(rectangle_, 1));
		graphics.DrawPath(new Pen(Brushes.Black, 2f), Class22.smethod_0(rectangle_, 1));
		e.Graphics.DrawImage(bitmap, new Point(0, 0));
		graphics.Dispose();
		bitmap.Dispose();
	}

	// Token: 0x06000551 RID: 1361 RVA: 0x0001ADBC File Offset: 0x00018FBC
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		checked
		{
			if (this.Boolean_1)
			{
				this.TextBox_0.Location = new Point(10, 10);
				this.TextBox_0.Size = new Size(base.Width - 20, base.Height - 20);
			}
			else
			{
				int height = this.TextBox_0.Height;
				this.TextBox_0.Location = new Point(10, (int)Math.Round(unchecked((double)base.Height / 2.0 - (double)height / 2.0 - 1.0)));
				this.TextBox_0.Size = new Size(base.Width - 20, height);
			}
		}
	}

	// Token: 0x06000552 RID: 1362 RVA: 0x0001AE7C File Offset: 0x0001907C
	[CompilerGenerated]
	[DebuggerHidden]
	private void method_3(object sender, EventArgs e)
	{
		this.method_0();
	}

	// Token: 0x06000553 RID: 1363 RVA: 0x0001AE84 File Offset: 0x00019084
	[CompilerGenerated]
	[DebuggerHidden]
	private void GControl17_TextChanged(object sender, EventArgs e)
	{
		this.method_1();
	}

	// Token: 0x040002B2 RID: 690
	[AccessedThroughProperty("_tb")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private TextBox textBox_0;

	// Token: 0x040002B3 RID: 691
	private bool bool_0;

	// Token: 0x040002B4 RID: 692
	private int int_0;

	// Token: 0x040002B5 RID: 693
	private HorizontalAlignment horizontalAlignment_0;

	// Token: 0x040002B6 RID: 694
	private bool bool_1;

	// Token: 0x040002B7 RID: 695
	private bool bool_2;
}
