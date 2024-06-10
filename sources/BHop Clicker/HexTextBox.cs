using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace shit_temple
{
	// Token: 0x02000015 RID: 21
	[DefaultEvent("TextChanged")]
	internal class HexTextBox : Control
	{
		// Token: 0x17000016 RID: 22
		// (get) Token: 0x06000075 RID: 117 RVA: 0x0000268C File Offset: 0x0000088C
		// (set) Token: 0x06000076 RID: 118 RVA: 0x00002694 File Offset: 0x00000894
		private virtual TextBox TB { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000017 RID: 23
		// (get) Token: 0x06000077 RID: 119 RVA: 0x0000269D File Offset: 0x0000089D
		// (set) Token: 0x06000078 RID: 120 RVA: 0x000026A5 File Offset: 0x000008A5
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
				if (this.TB != null)
				{
					this.TB.TextAlign = value;
				}
			}
		}

		// Token: 0x17000018 RID: 24
		// (get) Token: 0x06000079 RID: 121 RVA: 0x000026C2 File Offset: 0x000008C2
		// (set) Token: 0x0600007A RID: 122 RVA: 0x000026CA File Offset: 0x000008CA
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
				if (this.TB != null)
				{
					this.TB.MaxLength = value;
				}
			}
		}

		// Token: 0x17000019 RID: 25
		// (get) Token: 0x0600007B RID: 123 RVA: 0x000026E7 File Offset: 0x000008E7
		// (set) Token: 0x0600007C RID: 124 RVA: 0x000026EF File Offset: 0x000008EF
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
				if (this.TB != null)
				{
					this.TB.ReadOnly = value;
				}
			}
		}

		// Token: 0x1700001A RID: 26
		// (get) Token: 0x0600007D RID: 125 RVA: 0x0000270C File Offset: 0x0000090C
		// (set) Token: 0x0600007E RID: 126 RVA: 0x00002714 File Offset: 0x00000914
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
				if (this.TB != null)
				{
					this.TB.UseSystemPasswordChar = value;
				}
			}
		}

		// Token: 0x1700001B RID: 27
		// (get) Token: 0x0600007F RID: 127 RVA: 0x00002731 File Offset: 0x00000931
		// (set) Token: 0x06000080 RID: 128 RVA: 0x000036A4 File Offset: 0x000018A4
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
				checked
				{
					if (this.TB != null)
					{
						this.TB.Multiline = value;
						if (value)
						{
							this.TB.Height = base.Height - 11;
							return;
						}
						base.Height = this.TB.Height + 11;
					}
				}
			}
		}

		// Token: 0x1700001C RID: 28
		// (get) Token: 0x06000081 RID: 129 RVA: 0x00002739 File Offset: 0x00000939
		// (set) Token: 0x06000082 RID: 130 RVA: 0x00002741 File Offset: 0x00000941
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
				if (this.TB != null)
				{
					this.TB.Text = value;
				}
			}
		}

		// Token: 0x1700001D RID: 29
		// (get) Token: 0x06000083 RID: 131 RVA: 0x0000275E File Offset: 0x0000095E
		// (set) Token: 0x06000084 RID: 132 RVA: 0x000036F8 File Offset: 0x000018F8
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
				checked
				{
					if (this.TB != null)
					{
						this.TB.Font = value;
						this.TB.Location = new Point(3, 5);
						this.TB.Width = base.Width - 6;
						if (!this._Multiline)
						{
							base.Height = this.TB.Height + 11;
						}
					}
				}
			}
		}

		// Token: 0x06000085 RID: 133 RVA: 0x00002766 File Offset: 0x00000966
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			if (!base.Controls.Contains(this.TB))
			{
				base.Controls.Add(this.TB);
			}
		}

		// Token: 0x06000086 RID: 134 RVA: 0x00002792 File Offset: 0x00000992
		private void OnBaseTextChanged(object s, EventArgs e)
		{
			this.Text = this.TB.Text;
		}

		// Token: 0x06000087 RID: 135 RVA: 0x00003764 File Offset: 0x00001964
		private void OnBaseKeyDown(object s, KeyEventArgs e)
		{
			if (e.Control && e.KeyCode == Keys.A)
			{
				this.TB.SelectAll();
				e.SuppressKeyPress = true;
			}
			if (e.Control && e.KeyCode == Keys.C)
			{
				this.TB.Copy();
				e.SuppressKeyPress = true;
			}
		}

		// Token: 0x06000088 RID: 136 RVA: 0x000037BC File Offset: 0x000019BC
		protected override void OnResize(EventArgs e)
		{
			this.TB.Location = new Point(5, 5);
			checked
			{
				this.TB.Width = base.Width - 10;
				if (this._Multiline)
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

		// Token: 0x06000089 RID: 137 RVA: 0x000027A5 File Offset: 0x000009A5
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this._State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600008A RID: 138 RVA: 0x000027BB File Offset: 0x000009BB
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this._State = MouseState.Over;
			this.TB.Focus();
			base.Invalidate();
		}

		// Token: 0x0600008B RID: 139 RVA: 0x000027DD File Offset: 0x000009DD
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this._State = MouseState.Over;
			this.TB.Focus();
			base.Invalidate();
		}

		// Token: 0x0600008C RID: 140 RVA: 0x000027FF File Offset: 0x000009FF
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this._State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x0600008D RID: 141 RVA: 0x00003828 File Offset: 0x00001A28
		public HexTextBox()
		{
			this._State = MouseState.None;
			this._TextAlign = HorizontalAlignment.Left;
			this._MaxLength = 32767;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			this.TB = new TextBox();
			this.TB.Font = new Font("Segoe UI", 9f);
			this.TB.Text = this.Text;
			this.TB.BackColor = Color.FromArgb(47, 51, 60);
			this.TB.ForeColor = Color.FromArgb(236, 95, 75);
			this.TB.MaxLength = this._MaxLength;
			this.TB.Multiline = this._Multiline;
			this.TB.ReadOnly = this._ReadOnly;
			this.TB.UseSystemPasswordChar = this._UseSystemPasswordChar;
			this.TB.BorderStyle = BorderStyle.None;
			this.TB.Location = new Point(5, 5);
			checked
			{
				this.TB.Width = base.Width - 10;
				this.TB.Cursor = Cursors.IBeam;
				if (this._Multiline)
				{
					this.TB.Height = base.Height - 11;
				}
				else
				{
					base.Height = this.TB.Height + 11;
				}
				this.TB.TextChanged += this.OnBaseTextChanged;
				this.TB.KeyDown += this.OnBaseKeyDown;
			}
		}

		// Token: 0x0600008E RID: 142 RVA: 0x00002815 File Offset: 0x00000A15
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			e.Graphics.Clear(Color.FromArgb(47, 51, 60));
		}

		// Token: 0x0400002C RID: 44
		private MouseState _State;

		// Token: 0x0400002E RID: 46
		private HorizontalAlignment _TextAlign;

		// Token: 0x0400002F RID: 47
		private int _MaxLength;

		// Token: 0x04000030 RID: 48
		private bool _ReadOnly;

		// Token: 0x04000031 RID: 49
		private bool _UseSystemPasswordChar;

		// Token: 0x04000032 RID: 50
		private bool _Multiline;
	}
}
