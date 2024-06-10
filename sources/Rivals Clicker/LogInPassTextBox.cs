using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000014 RID: 20
	[DefaultEvent("TextChanged")]
	public class LogInPassTextBox : Control
	{
		// Token: 0x1700007F RID: 127
		// (get) Token: 0x0600015F RID: 351 RVA: 0x00002A49 File Offset: 0x00000C49
		// (set) Token: 0x06000160 RID: 352 RVA: 0x00002A53 File Offset: 0x00000C53
		private virtual TextBox TB { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000080 RID: 128
		// (get) Token: 0x06000161 RID: 353 RVA: 0x0000B1E8 File Offset: 0x000093E8
		// (set) Token: 0x06000162 RID: 354 RVA: 0x0000B200 File Offset: 0x00009400
		[Category("Options")]
		public HorizontalAlignment TextAlign
		{
			get
			{
				return this._TextAlign;
			}
			set
			{
				this._TextAlign = value;
				bool flag = this.TB != null;
				if (flag)
				{
					this.TB.TextAlign = value;
				}
			}
		}

		// Token: 0x17000081 RID: 129
		// (get) Token: 0x06000163 RID: 355 RVA: 0x0000B234 File Offset: 0x00009434
		// (set) Token: 0x06000164 RID: 356 RVA: 0x0000B24C File Offset: 0x0000944C
		[Category("Options")]
		public int MaxLength
		{
			get
			{
				return this._MaxLength;
			}
			set
			{
				this._MaxLength = value;
				bool flag = this.TB != null;
				if (flag)
				{
					this.TB.MaxLength = value;
				}
			}
		}

		// Token: 0x17000082 RID: 130
		// (get) Token: 0x06000165 RID: 357 RVA: 0x0000B280 File Offset: 0x00009480
		// (set) Token: 0x06000166 RID: 358 RVA: 0x0000B298 File Offset: 0x00009498
		[Category("Options")]
		public bool ReadOnly
		{
			get
			{
				return this._ReadOnly;
			}
			set
			{
				this._ReadOnly = value;
				bool flag = this.TB != null;
				if (flag)
				{
					this.TB.ReadOnly = value;
				}
			}
		}

		// Token: 0x17000083 RID: 131
		// (get) Token: 0x06000167 RID: 359 RVA: 0x0000B2CC File Offset: 0x000094CC
		// (set) Token: 0x06000168 RID: 360 RVA: 0x0000B2E4 File Offset: 0x000094E4
		[Category("Options")]
		public bool UseSystemPasswordChar
		{
			get
			{
				return this._UseSystemPasswordChar;
			}
			set
			{
				this._UseSystemPasswordChar = value;
				bool flag = this.TB != null;
				if (flag)
				{
					this.TB.UseSystemPasswordChar = value;
				}
			}
		}

		// Token: 0x17000084 RID: 132
		// (get) Token: 0x06000169 RID: 361 RVA: 0x0000B318 File Offset: 0x00009518
		// (set) Token: 0x0600016A RID: 362 RVA: 0x0000B330 File Offset: 0x00009530
		[Category("Options")]
		public bool Multiline
		{
			get
			{
				return this._Multiline;
			}
			set
			{
				this._Multiline = value;
				bool flag = this.TB != null;
				checked
				{
					if (flag)
					{
						this.TB.Multiline = value;
						if (value)
						{
							this.TB.Height = base.Height - 11;
						}
						else
						{
							base.Height = this.TB.Height + 11;
						}
					}
				}
			}
		}

		// Token: 0x17000085 RID: 133
		// (get) Token: 0x0600016B RID: 363 RVA: 0x0000AC68 File Offset: 0x00008E68
		// (set) Token: 0x0600016C RID: 364 RVA: 0x0000B398 File Offset: 0x00009598
		[Category("Options")]
		public override string Text
		{
			get
			{
				return base.Text;
			}
			set
			{
				base.Text = value;
				bool flag = this.TB != null;
				if (flag)
				{
					this.TB.Text = value;
				}
			}
		}

		// Token: 0x17000086 RID: 134
		// (get) Token: 0x0600016D RID: 365 RVA: 0x0000ACB4 File Offset: 0x00008EB4
		// (set) Token: 0x0600016E RID: 366 RVA: 0x0000B3CC File Offset: 0x000095CC
		[Category("Options")]
		public override Font Font
		{
			get
			{
				return base.Font;
			}
			set
			{
				base.Font = value;
				bool flag = this.TB != null;
				checked
				{
					if (flag)
					{
						this.TB.Font = value;
						this.TB.Location = new Point(3, 5);
						this.TB.Width = base.Width - 35;
						bool flag2 = !this._Multiline;
						if (flag2)
						{
							base.Height = this.TB.Height + 11;
						}
					}
				}
			}
		}

		// Token: 0x0600016F RID: 367 RVA: 0x0000B44C File Offset: 0x0000964C
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			bool flag = !base.Controls.Contains(this.TB);
			if (flag)
			{
				base.Controls.Add(this.TB);
			}
		}

		// Token: 0x06000170 RID: 368 RVA: 0x00002A5C File Offset: 0x00000C5C
		private void OnBaseTextChanged(object s, EventArgs e)
		{
			this.Text = this.TB.Text;
		}

		// Token: 0x06000171 RID: 369 RVA: 0x0000B490 File Offset: 0x00009690
		private void OnBaseKeyDown(object s, KeyEventArgs e)
		{
			bool flag = e.Control && e.KeyCode == Keys.A;
			if (flag)
			{
				this.TB.SelectAll();
				e.SuppressKeyPress = true;
			}
			bool flag2 = e.Control && e.KeyCode == Keys.C;
			if (flag2)
			{
				this.TB.Copy();
				e.SuppressKeyPress = true;
			}
		}

		// Token: 0x06000172 RID: 370 RVA: 0x0000B4FC File Offset: 0x000096FC
		protected override void OnResize(EventArgs e)
		{
			this.TB.Location = new Point(5, 5);
			checked
			{
				this.TB.Width = base.Width - 35;
				bool multiline = this._Multiline;
				if (multiline)
				{
					this.TB.Height = base.Height - 11;
				}
				else
				{
					base.Height = this.TB.Height + 11;
				}
				base.OnResize(e);
			}
		}

		// Token: 0x17000087 RID: 135
		// (get) Token: 0x06000173 RID: 371 RVA: 0x0000B574 File Offset: 0x00009774
		// (set) Token: 0x06000174 RID: 372 RVA: 0x00002A71 File Offset: 0x00000C71
		[Category("Colours")]
		public Color BackgroundColour
		{
			get
			{
				return this._BaseColour;
			}
			set
			{
				this._BaseColour = value;
			}
		}

		// Token: 0x17000088 RID: 136
		// (get) Token: 0x06000175 RID: 373 RVA: 0x0000B58C File Offset: 0x0000978C
		// (set) Token: 0x06000176 RID: 374 RVA: 0x00002A7B File Offset: 0x00000C7B
		[Category("Colours")]
		public Color TextColour
		{
			get
			{
				return this._TextColour;
			}
			set
			{
				this._TextColour = value;
			}
		}

		// Token: 0x17000089 RID: 137
		// (get) Token: 0x06000177 RID: 375 RVA: 0x0000B5A4 File Offset: 0x000097A4
		// (set) Token: 0x06000178 RID: 376 RVA: 0x00002A85 File Offset: 0x00000C85
		[Category("Colours")]
		public Color BorderColour
		{
			get
			{
				return this._BorderColour;
			}
			set
			{
				this._BorderColour = value;
			}
		}

		// Token: 0x06000179 RID: 377 RVA: 0x00002A8F File Offset: 0x00000C8F
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = DrawHelpers.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600017A RID: 378 RVA: 0x00002AA8 File Offset: 0x00000CA8
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = DrawHelpers.MouseState.Over;
			this.TB.Focus();
			base.Invalidate();
		}

		// Token: 0x0600017B RID: 379 RVA: 0x00002ACD File Offset: 0x00000CCD
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = DrawHelpers.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x0600017C RID: 380 RVA: 0x0000B5BC File Offset: 0x000097BC
		public LogInPassTextBox()
		{
			this.State = DrawHelpers.MouseState.None;
			this._BaseColour = Color.FromArgb(255, 255, 255);
			this._TextColour = Color.FromArgb(50, 50, 50);
			this._BorderColour = Color.FromArgb(180, 187, 205);
			this._TextAlign = HorizontalAlignment.Left;
			this._MaxLength = 32767;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			this.TB = new TextBox();
			this.TB.Height = 190;
			this.TB.Font = new Font("Segoe UI", 10f);
			this.TB.Text = this.Text;
			this.TB.BackColor = Color.FromArgb(42, 42, 42);
			this.TB.ForeColor = Color.FromArgb(255, 255, 255);
			this.TB.MaxLength = this._MaxLength;
			this.TB.Multiline = false;
			this.TB.ReadOnly = this._ReadOnly;
			this.TB.UseSystemPasswordChar = this._UseSystemPasswordChar;
			this.TB.BorderStyle = BorderStyle.None;
			this.TB.Location = new Point(5, 5);
			this.TB.Width = checked(base.Width - 35);
			this.TB.TextChanged += this.OnBaseTextChanged;
			this.TB.KeyDown += this.OnBaseKeyDown;
		}

		// Token: 0x0600017D RID: 381 RVA: 0x0000B77C File Offset: 0x0000997C
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			Rectangle Base = new Rectangle(0, 0, base.Width, base.Height);
			Graphics graphics = G;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this.BackColor);
			this.TB.BackColor = Color.FromArgb(42, 42, 42);
			this.TB.ForeColor = Color.FromArgb(255, 255, 255);
			GraphicsPath GP = DrawHelpers.RoundRectangle(Base, 6);
			graphics.FillPath(new SolidBrush(Color.FromArgb(42, 42, 42)), GP);
			graphics.DrawPath(new Pen(new SolidBrush(Color.FromArgb(35, 35, 35)), 2f), GP);
			GP.Dispose();
			checked
			{
				graphics.FillPie(new SolidBrush(base.FindForm().BackColor), new Rectangle(base.Width - 25, base.Height - 60, base.Height + 25, base.Height + 25), 90f, 90f);
				graphics.DrawPie(new Pen(Color.FromArgb(35, 35, 35), 2f), new Rectangle(base.Width - 25, base.Height - 60, base.Height + 25, base.Height + 25), 90f, 90f);
				graphics.FillEllipse(new SolidBrush(this._TextColour), new Rectangle(10, 5, 10, 7));
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x04000092 RID: 146
		private DrawHelpers.MouseState State;

		// Token: 0x04000094 RID: 148
		private Color _BaseColour;

		// Token: 0x04000095 RID: 149
		private Color _TextColour;

		// Token: 0x04000096 RID: 150
		private Color _BorderColour;

		// Token: 0x04000097 RID: 151
		private HorizontalAlignment _TextAlign;

		// Token: 0x04000098 RID: 152
		private int _MaxLength;

		// Token: 0x04000099 RID: 153
		private bool _ReadOnly;

		// Token: 0x0400009A RID: 154
		private bool _UseSystemPasswordChar;

		// Token: 0x0400009B RID: 155
		private bool _Multiline;
	}
}
