using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000018 RID: 24
	public class LogInNormalTextBox : Control
	{
		// Token: 0x17000096 RID: 150
		// (get) Token: 0x060001AC RID: 428 RVA: 0x00002C6E File Offset: 0x00000E6E
		// (set) Token: 0x060001AD RID: 429 RVA: 0x00002C78 File Offset: 0x00000E78
		private virtual TextBox TB { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000097 RID: 151
		// (get) Token: 0x060001AE RID: 430 RVA: 0x0000C168 File Offset: 0x0000A368
		// (set) Token: 0x060001AF RID: 431 RVA: 0x0000C180 File Offset: 0x0000A380
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

		// Token: 0x17000098 RID: 152
		// (get) Token: 0x060001B0 RID: 432 RVA: 0x0000C1B4 File Offset: 0x0000A3B4
		// (set) Token: 0x060001B1 RID: 433 RVA: 0x0000C1CC File Offset: 0x0000A3CC
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

		// Token: 0x17000099 RID: 153
		// (get) Token: 0x060001B2 RID: 434 RVA: 0x0000C200 File Offset: 0x0000A400
		// (set) Token: 0x060001B3 RID: 435 RVA: 0x0000C218 File Offset: 0x0000A418
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

		// Token: 0x1700009A RID: 154
		// (get) Token: 0x060001B4 RID: 436 RVA: 0x0000C24C File Offset: 0x0000A44C
		// (set) Token: 0x060001B5 RID: 437 RVA: 0x0000C264 File Offset: 0x0000A464
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

		// Token: 0x1700009B RID: 155
		// (get) Token: 0x060001B6 RID: 438 RVA: 0x0000C298 File Offset: 0x0000A498
		// (set) Token: 0x060001B7 RID: 439 RVA: 0x0000C2B0 File Offset: 0x0000A4B0
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

		// Token: 0x1700009C RID: 156
		// (get) Token: 0x060001B8 RID: 440 RVA: 0x0000AC68 File Offset: 0x00008E68
		// (set) Token: 0x060001B9 RID: 441 RVA: 0x0000C318 File Offset: 0x0000A518
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

		// Token: 0x1700009D RID: 157
		// (get) Token: 0x060001BA RID: 442 RVA: 0x0000ACB4 File Offset: 0x00008EB4
		// (set) Token: 0x060001BB RID: 443 RVA: 0x0000C34C File Offset: 0x0000A54C
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
						this.TB.Width = base.Width - 6;
						bool flag2 = !this._Multiline;
						if (flag2)
						{
							base.Height = this.TB.Height + 11;
						}
					}
				}
			}
		}

		// Token: 0x060001BC RID: 444 RVA: 0x0000C3CC File Offset: 0x0000A5CC
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			bool flag = !base.Controls.Contains(this.TB);
			if (flag)
			{
				base.Controls.Add(this.TB);
			}
		}

		// Token: 0x060001BD RID: 445 RVA: 0x00002C81 File Offset: 0x00000E81
		private void OnBaseTextChanged(object s, EventArgs e)
		{
			this.Text = this.TB.Text;
		}

		// Token: 0x060001BE RID: 446 RVA: 0x0000C410 File Offset: 0x0000A610
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

		// Token: 0x060001BF RID: 447 RVA: 0x0000C47C File Offset: 0x0000A67C
		protected override void OnResize(EventArgs e)
		{
			this.TB.Location = new Point(5, 5);
			checked
			{
				this.TB.Width = base.Width - 10;
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

		// Token: 0x1700009E RID: 158
		// (get) Token: 0x060001C0 RID: 448 RVA: 0x0000C4F4 File Offset: 0x0000A6F4
		// (set) Token: 0x060001C1 RID: 449 RVA: 0x00002C96 File Offset: 0x00000E96
		public LogInNormalTextBox.Styles Style
		{
			get
			{
				return this._Style;
			}
			set
			{
				this._Style = value;
			}
		}

		// Token: 0x060001C2 RID: 450 RVA: 0x00002CA0 File Offset: 0x00000EA0
		public void SelectAll()
		{
			this.TB.Focus();
			this.TB.SelectAll();
		}

		// Token: 0x1700009F RID: 159
		// (get) Token: 0x060001C3 RID: 451 RVA: 0x0000C50C File Offset: 0x0000A70C
		// (set) Token: 0x060001C4 RID: 452 RVA: 0x00002CBB File Offset: 0x00000EBB
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

		// Token: 0x170000A0 RID: 160
		// (get) Token: 0x060001C5 RID: 453 RVA: 0x0000C524 File Offset: 0x0000A724
		// (set) Token: 0x060001C6 RID: 454 RVA: 0x00002CC5 File Offset: 0x00000EC5
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

		// Token: 0x170000A1 RID: 161
		// (get) Token: 0x060001C7 RID: 455 RVA: 0x0000C53C File Offset: 0x0000A73C
		// (set) Token: 0x060001C8 RID: 456 RVA: 0x00002CCF File Offset: 0x00000ECF
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

		// Token: 0x060001C9 RID: 457 RVA: 0x00002CD9 File Offset: 0x00000ED9
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = DrawHelpers.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060001CA RID: 458 RVA: 0x00002CF2 File Offset: 0x00000EF2
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = DrawHelpers.MouseState.Over;
			this.TB.Focus();
			base.Invalidate();
		}

		// Token: 0x060001CB RID: 459 RVA: 0x00002D17 File Offset: 0x00000F17
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = DrawHelpers.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060001CC RID: 460 RVA: 0x0000C554 File Offset: 0x0000A754
		public LogInNormalTextBox()
		{
			this.State = DrawHelpers.MouseState.None;
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._Style = LogInNormalTextBox.Styles.NotRounded;
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

		// Token: 0x060001CD RID: 461 RVA: 0x0000C710 File Offset: 0x0000A910
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Rectangle Base = new Rectangle(0, 0, base.Width, base.Height);
			Graphics graphics = g;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this.BackColor);
			this.TB.BackColor = Color.FromArgb(42, 42, 42);
			this.TB.ForeColor = Color.FromArgb(255, 255, 255);
			LogInNormalTextBox.Styles style = this._Style;
			if (style != LogInNormalTextBox.Styles.Rounded)
			{
				if (style == LogInNormalTextBox.Styles.NotRounded)
				{
					graphics.FillRectangle(new SolidBrush(Color.FromArgb(42, 42, 42)), checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1)));
					graphics.DrawRectangle(new Pen(new SolidBrush(Color.FromArgb(35, 35, 35)), 2f), new Rectangle(0, 0, base.Width, base.Height));
				}
			}
			else
			{
				GraphicsPath GP = DrawHelpers.RoundRectangle(Base, 6);
				graphics.FillPath(new SolidBrush(Color.FromArgb(42, 42, 42)), GP);
				graphics.DrawPath(new Pen(new SolidBrush(Color.FromArgb(35, 35, 35)), 2f), GP);
				GP.Dispose();
			}
			graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		}

		// Token: 0x040000AB RID: 171
		private DrawHelpers.MouseState State;

		// Token: 0x040000AD RID: 173
		private Color _BaseColour;

		// Token: 0x040000AE RID: 174
		private Color _TextColour;

		// Token: 0x040000AF RID: 175
		private Color _BorderColour;

		// Token: 0x040000B0 RID: 176
		private LogInNormalTextBox.Styles _Style;

		// Token: 0x040000B1 RID: 177
		private HorizontalAlignment _TextAlign;

		// Token: 0x040000B2 RID: 178
		private int _MaxLength;

		// Token: 0x040000B3 RID: 179
		private bool _ReadOnly;

		// Token: 0x040000B4 RID: 180
		private bool _UseSystemPasswordChar;

		// Token: 0x040000B5 RID: 181
		private bool _Multiline;

		// Token: 0x02000019 RID: 25
		public enum Styles
		{
			// Token: 0x040000B7 RID: 183
			Rounded,
			// Token: 0x040000B8 RID: 184
			NotRounded
		}
	}
}
