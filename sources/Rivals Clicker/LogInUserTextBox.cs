using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000013 RID: 19
	[DefaultEvent("TextChanged")]
	public class LogInUserTextBox : Control
	{
		// Token: 0x17000074 RID: 116
		// (get) Token: 0x06000140 RID: 320 RVA: 0x000029AC File Offset: 0x00000BAC
		// (set) Token: 0x06000141 RID: 321 RVA: 0x000029B6 File Offset: 0x00000BB6
		private virtual TextBox TB { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000075 RID: 117
		// (get) Token: 0x06000142 RID: 322 RVA: 0x0000AAB8 File Offset: 0x00008CB8
		// (set) Token: 0x06000143 RID: 323 RVA: 0x0000AAD0 File Offset: 0x00008CD0
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

		// Token: 0x17000076 RID: 118
		// (get) Token: 0x06000144 RID: 324 RVA: 0x0000AB04 File Offset: 0x00008D04
		// (set) Token: 0x06000145 RID: 325 RVA: 0x0000AB1C File Offset: 0x00008D1C
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

		// Token: 0x17000077 RID: 119
		// (get) Token: 0x06000146 RID: 326 RVA: 0x0000AB50 File Offset: 0x00008D50
		// (set) Token: 0x06000147 RID: 327 RVA: 0x0000AB68 File Offset: 0x00008D68
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

		// Token: 0x17000078 RID: 120
		// (get) Token: 0x06000148 RID: 328 RVA: 0x0000AB9C File Offset: 0x00008D9C
		// (set) Token: 0x06000149 RID: 329 RVA: 0x0000ABB4 File Offset: 0x00008DB4
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

		// Token: 0x17000079 RID: 121
		// (get) Token: 0x0600014A RID: 330 RVA: 0x0000ABE8 File Offset: 0x00008DE8
		// (set) Token: 0x0600014B RID: 331 RVA: 0x0000AC00 File Offset: 0x00008E00
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

		// Token: 0x1700007A RID: 122
		// (get) Token: 0x0600014C RID: 332 RVA: 0x0000AC68 File Offset: 0x00008E68
		// (set) Token: 0x0600014D RID: 333 RVA: 0x0000AC80 File Offset: 0x00008E80
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

		// Token: 0x1700007B RID: 123
		// (get) Token: 0x0600014E RID: 334 RVA: 0x0000ACB4 File Offset: 0x00008EB4
		// (set) Token: 0x0600014F RID: 335 RVA: 0x0000ACCC File Offset: 0x00008ECC
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

		// Token: 0x06000150 RID: 336 RVA: 0x0000AD4C File Offset: 0x00008F4C
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			bool flag = !base.Controls.Contains(this.TB);
			if (flag)
			{
				base.Controls.Add(this.TB);
			}
		}

		// Token: 0x06000151 RID: 337 RVA: 0x000029BF File Offset: 0x00000BBF
		private void OnBaseTextChanged(object s, EventArgs e)
		{
			this.Text = this.TB.Text;
		}

		// Token: 0x06000152 RID: 338 RVA: 0x0000AD90 File Offset: 0x00008F90
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

		// Token: 0x06000153 RID: 339 RVA: 0x0000ADFC File Offset: 0x00008FFC
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

		// Token: 0x1700007C RID: 124
		// (get) Token: 0x06000154 RID: 340 RVA: 0x0000AE74 File Offset: 0x00009074
		// (set) Token: 0x06000155 RID: 341 RVA: 0x000029D4 File Offset: 0x00000BD4
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

		// Token: 0x1700007D RID: 125
		// (get) Token: 0x06000156 RID: 342 RVA: 0x0000AE8C File Offset: 0x0000908C
		// (set) Token: 0x06000157 RID: 343 RVA: 0x000029DE File Offset: 0x00000BDE
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

		// Token: 0x1700007E RID: 126
		// (get) Token: 0x06000158 RID: 344 RVA: 0x0000AEA4 File Offset: 0x000090A4
		// (set) Token: 0x06000159 RID: 345 RVA: 0x000029E8 File Offset: 0x00000BE8
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

		// Token: 0x0600015A RID: 346 RVA: 0x000029F2 File Offset: 0x00000BF2
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = DrawHelpers.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600015B RID: 347 RVA: 0x00002A0B File Offset: 0x00000C0B
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = DrawHelpers.MouseState.Over;
			this.TB.Focus();
			base.Invalidate();
		}

		// Token: 0x0600015C RID: 348 RVA: 0x00002A30 File Offset: 0x00000C30
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = DrawHelpers.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x0600015D RID: 349 RVA: 0x0000AEBC File Offset: 0x000090BC
		public LogInUserTextBox()
		{
			this.State = DrawHelpers.MouseState.None;
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._BorderColour = Color.FromArgb(35, 35, 35);
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

		// Token: 0x0600015E RID: 350 RVA: 0x0000B070 File Offset: 0x00009270
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
				graphics.FillPie(new SolidBrush(base.FindForm().BackColor), new Rectangle(base.Width - 25, base.Height - 23, base.Height + 25, base.Height + 25), 180f, 90f);
				graphics.DrawPie(new Pen(Color.FromArgb(35, 35, 35), 2f), new Rectangle(base.Width - 25, base.Height - 23, base.Height + 25, base.Height + 25), 180f, 90f);
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x04000088 RID: 136
		private DrawHelpers.MouseState State;

		// Token: 0x0400008A RID: 138
		private Color _BaseColour;

		// Token: 0x0400008B RID: 139
		private Color _TextColour;

		// Token: 0x0400008C RID: 140
		private Color _BorderColour;

		// Token: 0x0400008D RID: 141
		private HorizontalAlignment _TextAlign;

		// Token: 0x0400008E RID: 142
		private int _MaxLength;

		// Token: 0x0400008F RID: 143
		private bool _ReadOnly;

		// Token: 0x04000090 RID: 144
		private bool _UseSystemPasswordChar;

		// Token: 0x04000091 RID: 145
		private bool _Multiline;
	}
}
