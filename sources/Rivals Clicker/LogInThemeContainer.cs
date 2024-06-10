using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000011 RID: 17
	public class LogInThemeContainer : ContainerControl
	{
		// Token: 0x17000061 RID: 97
		// (get) Token: 0x06000109 RID: 265 RVA: 0x00009878 File Offset: 0x00007A78
		// (set) Token: 0x0600010A RID: 266 RVA: 0x00009890 File Offset: 0x00007A90
		protected int LockWidth
		{
			get
			{
				return this._LockWidth;
			}
			set
			{
				this._LockWidth = value;
				bool flag = this.LockWidth != 0 && base.IsHandleCreated;
				if (flag)
				{
					base.Width = this.LockWidth;
				}
			}
		}

		// Token: 0x17000062 RID: 98
		// (get) Token: 0x0600010B RID: 267 RVA: 0x000098C8 File Offset: 0x00007AC8
		// (set) Token: 0x0600010C RID: 268 RVA: 0x000098E0 File Offset: 0x00007AE0
		protected int LockHeight
		{
			get
			{
				return this._LockHeight;
			}
			set
			{
				this._LockHeight = value;
				bool flag = this.LockHeight != 0 && base.IsHandleCreated;
				if (flag)
				{
					base.Height = this.LockHeight;
				}
			}
		}

		// Token: 0x0600010D RID: 269 RVA: 0x00009918 File Offset: 0x00007B18
		protected sealed override void OnSizeChanged(EventArgs e)
		{
			bool flag = this._Movable && !this._ControlMode;
			if (flag)
			{
				this.Frame = new Rectangle(7, 7, checked(base.Width - 14), 35);
			}
			base.Invalidate();
			base.OnSizeChanged(e);
		}

		// Token: 0x0600010E RID: 270 RVA: 0x00009968 File Offset: 0x00007B68
		protected override void SetBoundsCore(int x, int y, int width, int height, BoundsSpecified specified)
		{
			bool flag = this._LockWidth != 0;
			if (flag)
			{
				width = this._LockWidth;
			}
			bool flag2 = this._LockHeight != 0;
			if (flag2)
			{
				height = this._LockHeight;
			}
			base.SetBoundsCore(x, y, width, height, specified);
		}

		// Token: 0x0600010F RID: 271 RVA: 0x000028D1 File Offset: 0x00000AD1
		private void SetState(DrawHelpers.MouseState current)
		{
			this.State = current;
			base.Invalidate();
		}

		// Token: 0x06000110 RID: 272 RVA: 0x000099B0 File Offset: 0x00007BB0
		protected override void OnMouseMove(MouseEventArgs e)
		{
			bool flag = !this._IsParentForm || base.ParentForm.WindowState != FormWindowState.Maximized;
			if (flag)
			{
				bool flag2 = this._Sizable && !this._ControlMode;
				if (flag2)
				{
					this.InvalidateMouse();
				}
			}
			base.OnMouseMove(e);
			this.SetState(DrawHelpers.MouseState.Over);
		}

		// Token: 0x06000111 RID: 273 RVA: 0x00009A10 File Offset: 0x00007C10
		protected override void OnEnabledChanged(EventArgs e)
		{
			bool enabled = base.Enabled;
			if (enabled)
			{
				this.SetState(DrawHelpers.MouseState.None);
			}
			else
			{
				this.SetState(DrawHelpers.MouseState.Block);
			}
			base.OnEnabledChanged(e);
		}

		// Token: 0x06000112 RID: 274 RVA: 0x000028E2 File Offset: 0x00000AE2
		protected override void OnMouseEnter(EventArgs e)
		{
			this.SetState(DrawHelpers.MouseState.Over);
			base.OnMouseEnter(e);
		}

		// Token: 0x06000113 RID: 275 RVA: 0x000028F5 File Offset: 0x00000AF5
		protected override void OnMouseUp(MouseEventArgs e)
		{
			this.SetState(DrawHelpers.MouseState.Over);
			base.OnMouseUp(e);
		}

		// Token: 0x06000114 RID: 276 RVA: 0x00009A44 File Offset: 0x00007C44
		protected override void OnMouseLeave(EventArgs e)
		{
			this.SetState(DrawHelpers.MouseState.None);
			bool flag = base.GetChildAtPoint(base.PointToClient(Control.MousePosition)) != null;
			if (flag)
			{
				bool flag2 = this._Sizable && !this._ControlMode;
				if (flag2)
				{
					this.Cursor = Cursors.Default;
					this.Previous = 0;
				}
			}
			base.OnMouseLeave(e);
		}

		// Token: 0x06000115 RID: 277 RVA: 0x00009AAC File Offset: 0x00007CAC
		protected override void OnMouseDown(MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.SetState(DrawHelpers.MouseState.Down);
			}
			bool flag2 = (!this._IsParentForm || base.ParentForm.WindowState != FormWindowState.Maximized) && !this._ControlMode;
			if (flag2)
			{
				bool flag3 = this._Movable && this.Frame.Contains(e.Location);
				if (flag3)
				{
					base.Capture = false;
					this.WM_LMBUTTONDOWN = true;
					this.DefWndProc(ref this.Messages[0]);
				}
				else
				{
					bool flag4 = this._Sizable && this.Previous != 0;
					if (flag4)
					{
						base.Capture = false;
						this.WM_LMBUTTONDOWN = true;
						this.DefWndProc(ref this.Messages[this.Previous]);
					}
				}
			}
			this.GetMouseLocation = base.PointToClient(Control.MousePosition);
			checked
			{
				bool flag5 = this.GetMouseLocation.X > base.Width - 39 && this.GetMouseLocation.X < base.Width - 16 && this.GetMouseLocation.Y < 22;
				if (flag5)
				{
					bool allowClose = this._AllowClose;
					if (allowClose)
					{
						bool flag6 = this._CloseChoice == LogInThemeContainer.__CloseChoice.Application;
						if (flag6)
						{
							Environment.Exit(0);
						}
						else
						{
							base.ParentForm.Close();
						}
					}
				}
				else
				{
					bool flag7 = this.GetMouseLocation.X > base.Width - 64 && this.GetMouseLocation.X < base.Width - 41 && this.GetMouseLocation.Y < 22;
					if (flag7)
					{
						bool allowMaximize = this._AllowMaximize;
						if (allowMaximize)
						{
							FormWindowState windowState = base.FindForm().WindowState;
							if (windowState != FormWindowState.Normal)
							{
								if (windowState == FormWindowState.Maximized)
								{
									base.FindForm().WindowState = FormWindowState.Normal;
								}
							}
							else
							{
								this.OldSize = base.Size;
								base.FindForm().WindowState = FormWindowState.Maximized;
							}
						}
					}
					else
					{
						bool flag8 = this.GetMouseLocation.X > base.Width - 89 && this.GetMouseLocation.X < base.Width - 66 && this.GetMouseLocation.Y < 22;
						if (flag8)
						{
							bool allowMinimize = this._AllowMinimize;
							if (allowMinimize)
							{
								FormWindowState windowState2 = base.FindForm().WindowState;
								if (windowState2 != FormWindowState.Normal)
								{
									if (windowState2 == FormWindowState.Maximized)
									{
										base.FindForm().WindowState = FormWindowState.Minimized;
									}
								}
								else
								{
									this.OldSize = base.Size;
									base.FindForm().WindowState = FormWindowState.Minimized;
								}
							}
						}
					}
				}
				base.OnMouseDown(e);
			}
		}

		// Token: 0x06000116 RID: 278 RVA: 0x00009D58 File Offset: 0x00007F58
		private void InitializeMessages()
		{
			this.Messages[0] = Message.Create(base.Parent.Handle, 161, new IntPtr(2), IntPtr.Zero);
			int I = 1;
			checked
			{
				do
				{
					this.Messages[I] = Message.Create(base.Parent.Handle, 161, new IntPtr(I + 9), IntPtr.Zero);
					I++;
				}
				while (I <= 8);
			}
		}

		// Token: 0x06000117 RID: 279 RVA: 0x00009DCC File Offset: 0x00007FCC
		private int GetMouseIndex()
		{
			this.GetIndexPoint = base.PointToClient(Control.MousePosition);
			this.B1 = (this.GetIndexPoint.X < 6);
			checked
			{
				this.B2 = (this.GetIndexPoint.X > base.Width - 6);
				this.B3 = (this.GetIndexPoint.Y < 6);
				this.B4 = (this.GetIndexPoint.Y > base.Height - 6);
				bool flag = this.B1 && this.B3;
				int GetMouseIndex;
				if (flag)
				{
					GetMouseIndex = 4;
				}
				else
				{
					bool flag2 = this.B1 && this.B4;
					if (flag2)
					{
						GetMouseIndex = 7;
					}
					else
					{
						bool flag3 = this.B2 && this.B3;
						if (flag3)
						{
							GetMouseIndex = 5;
						}
						else
						{
							bool flag4 = this.B2 && this.B4;
							if (flag4)
							{
								GetMouseIndex = 8;
							}
							else
							{
								bool b = this.B1;
								if (b)
								{
									GetMouseIndex = 1;
								}
								else
								{
									bool b2 = this.B2;
									if (b2)
									{
										GetMouseIndex = 2;
									}
									else
									{
										bool b3 = this.B3;
										if (b3)
										{
											GetMouseIndex = 3;
										}
										else
										{
											bool b4 = this.B4;
											if (b4)
											{
												GetMouseIndex = 6;
											}
											else
											{
												GetMouseIndex = 0;
											}
										}
									}
								}
							}
						}
					}
				}
				return GetMouseIndex;
			}
		}

		// Token: 0x06000118 RID: 280 RVA: 0x00009EF8 File Offset: 0x000080F8
		private void InvalidateMouse()
		{
			this.Current = this.GetMouseIndex();
			bool flag = this.Current == this.Previous;
			if (!flag)
			{
				this.Previous = this.Current;
				switch (this.Previous)
				{
				case 0:
					this.Cursor = Cursors.Default;
					break;
				case 1:
				case 2:
					this.Cursor = Cursors.SizeWE;
					break;
				case 3:
				case 6:
					this.Cursor = Cursors.SizeNS;
					break;
				case 4:
				case 8:
					this.Cursor = Cursors.SizeNWSE;
					break;
				case 5:
				case 7:
					this.Cursor = Cursors.SizeNESW;
					break;
				}
			}
		}

		// Token: 0x06000119 RID: 281 RVA: 0x00009FB8 File Offset: 0x000081B8
		protected override void WndProc(ref Message m)
		{
			base.WndProc(ref m);
			bool flag = this.WM_LMBUTTONDOWN && m.Msg == 513;
			if (flag)
			{
				this.WM_LMBUTTONDOWN = false;
				this.SetState(DrawHelpers.MouseState.Over);
				bool flag2 = !this._SmartBounds;
				if (!flag2)
				{
					bool isParentMdi = this.IsParentMdi;
					if (isParentMdi)
					{
						this.CorrectBounds(new Rectangle(Point.Empty, base.Parent.Parent.Size));
					}
					else
					{
						try
						{
							this.CorrectBounds(Screen.FromControl(base.Parent).WorkingArea);
						}
						catch (Exception ex)
						{
						}
					}
				}
			}
		}

		// Token: 0x0600011A RID: 282 RVA: 0x0000A074 File Offset: 0x00008274
		private void CorrectBounds(Rectangle bounds)
		{
			bool flag = base.Parent.Width > bounds.Width;
			if (flag)
			{
				base.Parent.Width = bounds.Width;
			}
			bool flag2 = base.Parent.Height > bounds.Height;
			if (flag2)
			{
				base.Parent.Height = bounds.Height;
			}
			int X = base.Parent.Location.X;
			int Y = base.Parent.Location.Y;
			bool flag3 = X < bounds.X;
			if (flag3)
			{
				X = bounds.X;
			}
			bool flag4 = Y < bounds.Y;
			if (flag4)
			{
				Y = bounds.Y;
			}
			checked
			{
				int Width = bounds.X + bounds.Width;
				int Height = bounds.Y + bounds.Height;
				bool flag5 = X + base.Parent.Width > Width;
				if (flag5)
				{
					X = Width - base.Parent.Width;
				}
				bool flag6 = Y + base.Parent.Height > Height;
				if (flag6)
				{
					Y = Height - base.Parent.Height;
				}
				bool flag7 = base.FindForm().WindowState == FormWindowState.Maximized | base.FindForm().WindowState == FormWindowState.Minimized;
				if (flag7)
				{
					base.Parent.Size = this.OldSize;
				}
			}
		}

		// Token: 0x0600011B RID: 283 RVA: 0x0000A1D8 File Offset: 0x000083D8
		protected sealed override void OnHandleCreated(EventArgs e)
		{
			bool flag = this._LockWidth != 0;
			if (flag)
			{
				base.Width = this._LockWidth;
			}
			bool flag2 = this._LockHeight != 0;
			if (flag2)
			{
				base.Height = this._LockHeight;
			}
			bool flag3 = !this._ControlMode;
			if (flag3)
			{
				base.Dock = DockStyle.Fill;
			}
		}

		// Token: 0x0600011C RID: 284 RVA: 0x0000A230 File Offset: 0x00008430
		protected sealed override void OnParentChanged(EventArgs e)
		{
			base.OnParentChanged(e);
			bool flag = base.Parent == null;
			if (!flag)
			{
				this._IsParentForm = (base.Parent is Form);
				bool flag2 = !this._ControlMode;
				if (flag2)
				{
					this.InitializeMessages();
					base.Parent.BackColor = this.BackColor;
				}
			}
		}

		// Token: 0x17000063 RID: 99
		// (get) Token: 0x0600011D RID: 285 RVA: 0x0000A294 File Offset: 0x00008494
		// (set) Token: 0x0600011E RID: 286 RVA: 0x00002908 File Offset: 0x00000B08
		public LogInThemeContainer.__CloseChoice CloseChoice
		{
			get
			{
				return this._CloseChoice;
			}
			set
			{
				this._CloseChoice = value;
			}
		}

		// Token: 0x17000064 RID: 100
		// (get) Token: 0x0600011F RID: 287 RVA: 0x0000A2AC File Offset: 0x000084AC
		// (set) Token: 0x06000120 RID: 288 RVA: 0x00002912 File Offset: 0x00000B12
		public bool Movable
		{
			get
			{
				return this._Movable;
			}
			set
			{
				this._Movable = value;
			}
		}

		// Token: 0x17000065 RID: 101
		// (get) Token: 0x06000121 RID: 289 RVA: 0x0000A2C4 File Offset: 0x000084C4
		// (set) Token: 0x06000122 RID: 290 RVA: 0x0000291C File Offset: 0x00000B1C
		public bool Sizable
		{
			get
			{
				return this._Sizable;
			}
			set
			{
				this._Sizable = value;
			}
		}

		// Token: 0x17000066 RID: 102
		// (get) Token: 0x06000123 RID: 291 RVA: 0x0000A2DC File Offset: 0x000084DC
		// (set) Token: 0x06000124 RID: 292 RVA: 0x00002926 File Offset: 0x00000B26
		protected bool ControlMode
		{
			get
			{
				return this._ControlMode;
			}
			set
			{
				this._ControlMode = value;
				base.Invalidate();
			}
		}

		// Token: 0x17000067 RID: 103
		// (get) Token: 0x06000125 RID: 293 RVA: 0x0000A2F4 File Offset: 0x000084F4
		// (set) Token: 0x06000126 RID: 294 RVA: 0x00002937 File Offset: 0x00000B37
		public bool SmartBounds
		{
			get
			{
				return this._SmartBounds;
			}
			set
			{
				this._SmartBounds = value;
			}
		}

		// Token: 0x17000068 RID: 104
		// (get) Token: 0x06000127 RID: 295 RVA: 0x0000A30C File Offset: 0x0000850C
		protected bool IsParentForm
		{
			get
			{
				return this._IsParentForm;
			}
		}

		// Token: 0x17000069 RID: 105
		// (get) Token: 0x06000128 RID: 296 RVA: 0x0000A324 File Offset: 0x00008524
		protected bool IsParentMdi
		{
			get
			{
				bool flag = base.Parent == null;
				return !flag && base.Parent.Parent != null;
			}
		}

		// Token: 0x1700006A RID: 106
		// (get) Token: 0x06000129 RID: 297 RVA: 0x0000A358 File Offset: 0x00008558
		// (set) Token: 0x0600012A RID: 298 RVA: 0x00002941 File Offset: 0x00000B41
		[Category("Control")]
		public int FontSize
		{
			get
			{
				return this._FontSize;
			}
			set
			{
				this._FontSize = value;
			}
		}

		// Token: 0x1700006B RID: 107
		// (get) Token: 0x0600012B RID: 299 RVA: 0x0000A370 File Offset: 0x00008570
		// (set) Token: 0x0600012C RID: 300 RVA: 0x0000294B File Offset: 0x00000B4B
		[Category("Control")]
		public bool AllowMinimize
		{
			get
			{
				return this._AllowMinimize;
			}
			set
			{
				this._AllowMinimize = value;
			}
		}

		// Token: 0x1700006C RID: 108
		// (get) Token: 0x0600012D RID: 301 RVA: 0x0000A388 File Offset: 0x00008588
		// (set) Token: 0x0600012E RID: 302 RVA: 0x00002955 File Offset: 0x00000B55
		[Category("Control")]
		public bool AllowMaximize
		{
			get
			{
				return this._AllowMaximize;
			}
			set
			{
				this._AllowMaximize = value;
			}
		}

		// Token: 0x1700006D RID: 109
		// (get) Token: 0x0600012F RID: 303 RVA: 0x0000A3A0 File Offset: 0x000085A0
		// (set) Token: 0x06000130 RID: 304 RVA: 0x0000295F File Offset: 0x00000B5F
		[Category("Control")]
		public bool ShowIcon
		{
			get
			{
				return this._ShowIcon;
			}
			set
			{
				this._ShowIcon = value;
				base.Invalidate();
			}
		}

		// Token: 0x1700006E RID: 110
		// (get) Token: 0x06000131 RID: 305 RVA: 0x0000A3B8 File Offset: 0x000085B8
		// (set) Token: 0x06000132 RID: 306 RVA: 0x00002970 File Offset: 0x00000B70
		[Category("Control")]
		public bool AllowClose
		{
			get
			{
				return this._AllowClose;
			}
			set
			{
				this._AllowClose = value;
			}
		}

		// Token: 0x1700006F RID: 111
		// (get) Token: 0x06000133 RID: 307 RVA: 0x0000A3D0 File Offset: 0x000085D0
		// (set) Token: 0x06000134 RID: 308 RVA: 0x0000297A File Offset: 0x00000B7A
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

		// Token: 0x17000070 RID: 112
		// (get) Token: 0x06000135 RID: 309 RVA: 0x0000A3E8 File Offset: 0x000085E8
		// (set) Token: 0x06000136 RID: 310 RVA: 0x00002984 File Offset: 0x00000B84
		[Category("Colours")]
		public Color HoverColour
		{
			get
			{
				return this._HoverColour;
			}
			set
			{
				this._HoverColour = value;
			}
		}

		// Token: 0x17000071 RID: 113
		// (get) Token: 0x06000137 RID: 311 RVA: 0x0000A400 File Offset: 0x00008600
		// (set) Token: 0x06000138 RID: 312 RVA: 0x0000298E File Offset: 0x00000B8E
		[Category("Colours")]
		public Color BaseColour
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

		// Token: 0x17000072 RID: 114
		// (get) Token: 0x06000139 RID: 313 RVA: 0x0000A418 File Offset: 0x00008618
		// (set) Token: 0x0600013A RID: 314 RVA: 0x00002998 File Offset: 0x00000B98
		[Category("Colours")]
		public Color ContainerColour
		{
			get
			{
				return this._ContainerColour;
			}
			set
			{
				this._ContainerColour = value;
			}
		}

		// Token: 0x17000073 RID: 115
		// (get) Token: 0x0600013B RID: 315 RVA: 0x0000A430 File Offset: 0x00008630
		// (set) Token: 0x0600013C RID: 316 RVA: 0x000029A2 File Offset: 0x00000BA2
		[Category("Colours")]
		public Color FontColour
		{
			get
			{
				return this._FontColour;
			}
			set
			{
				this._FontColour = value;
			}
		}

		// Token: 0x0600013D RID: 317 RVA: 0x0000A448 File Offset: 0x00008648
		public LogInThemeContainer()
		{
			this._CloseChoice = LogInThemeContainer.__CloseChoice.Form;
			this._FontSize = 12;
			this._Font = new Font("Segoe UI", (float)this._FontSize);
			this.CaptureMovement = false;
			this.MouseP = new Point(0, 0);
			this._FontColour = Color.FromArgb(255, 255, 255);
			this._BaseColour = Color.FromArgb(35, 35, 35);
			this._ContainerColour = Color.FromArgb(54, 54, 54);
			this._BorderColour = Color.FromArgb(60, 60, 60);
			this._HoverColour = Color.FromArgb(42, 42, 42);
			this.State = DrawHelpers.MouseState.None;
			this.Messages = new Message[9];
			this._Movable = true;
			this._Sizable = true;
			this._SmartBounds = true;
			this._AllowMinimize = true;
			this._AllowMaximize = true;
			this._ShowIcon = true;
			this._AllowClose = true;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = this._BaseColour;
			this.Dock = DockStyle.Fill;
		}

		// Token: 0x0600013E RID: 318 RVA: 0x0000A564 File Offset: 0x00008764
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			base.ParentForm.FormBorderStyle = FormBorderStyle.None;
			base.ParentForm.AllowTransparency = false;
			base.ParentForm.TransparencyKey = Color.Fuchsia;
			base.ParentForm.FindForm().StartPosition = FormStartPosition.CenterScreen;
			this.Dock = DockStyle.Fill;
			base.Invalidate();
		}

		// Token: 0x0600013F RID: 319 RVA: 0x0000A5C8 File Offset: 0x000087C8
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			Graphics graphics = G;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.FillRectangle(new SolidBrush(this._BaseColour), new Rectangle(0, 0, base.Width, base.Height));
			checked
			{
				graphics.FillRectangle(new SolidBrush(this._ContainerColour), new Rectangle(2, 35, base.Width - 4, base.Height - 37));
				graphics.DrawRectangle(new Pen(this._BorderColour), new Rectangle(0, 0, base.Width, base.Height));
				Point[] ControlBoxPoints = new Point[]
				{
					new Point(base.Width - 90, 0),
					new Point(base.Width - 90, 22),
					new Point(base.Width - 15, 22),
					new Point(base.Width - 15, 0)
				};
				graphics.DrawLines(new Pen(this._BorderColour), ControlBoxPoints);
				graphics.DrawLine(new Pen(this._BorderColour), base.Width - 65, 0, base.Width - 65, 22);
				this.GetMouseLocation = base.PointToClient(Control.MousePosition);
				DrawHelpers.MouseState state = this.State;
				if (state == DrawHelpers.MouseState.Over)
				{
					bool flag = this.GetMouseLocation.X > base.Width - 39 && this.GetMouseLocation.X < base.Width - 16 && this.GetMouseLocation.Y < 22;
					if (flag)
					{
						graphics.FillRectangle(new SolidBrush(this._HoverColour), new Rectangle(base.Width - 39, 0, 23, 22));
					}
					else
					{
						bool flag2 = this.GetMouseLocation.X > base.Width - 64 && this.GetMouseLocation.X < base.Width - 41 && this.GetMouseLocation.Y < 22;
						if (flag2)
						{
							graphics.FillRectangle(new SolidBrush(this._HoverColour), new Rectangle(base.Width - 64, 0, 23, 22));
						}
						else
						{
							bool flag3 = this.GetMouseLocation.X > base.Width - 89 && this.GetMouseLocation.X < base.Width - 66 && this.GetMouseLocation.Y < 22;
							if (flag3)
							{
								graphics.FillRectangle(new SolidBrush(this._HoverColour), new Rectangle(base.Width - 89, 0, 23, 22));
							}
						}
					}
				}
				graphics.DrawLine(new Pen(this._BorderColour), base.Width - 40, 0, base.Width - 40, 22);
				graphics.DrawLine(new Pen(this._FontColour, 2f), base.Width - 33, 6, base.Width - 22, 16);
				graphics.DrawLine(new Pen(this._FontColour, 2f), base.Width - 33, 16, base.Width - 22, 6);
				graphics.DrawLine(new Pen(this._FontColour), base.Width - 83, 16, base.Width - 72, 16);
				graphics.DrawLine(new Pen(this._FontColour), base.Width - 58, 16, base.Width - 47, 16);
				graphics.DrawLine(new Pen(this._FontColour), base.Width - 58, 16, base.Width - 58, 6);
				graphics.DrawLine(new Pen(this._FontColour), base.Width - 47, 16, base.Width - 47, 6);
				graphics.DrawLine(new Pen(this._FontColour), base.Width - 58, 6, base.Width - 47, 6);
				graphics.DrawLine(new Pen(this._FontColour), base.Width - 58, 7, base.Width - 47, 7);
				bool showIcon = this._ShowIcon;
				if (showIcon)
				{
					graphics.DrawIcon(base.FindForm().Icon, new Rectangle(6, 6, 22, 22));
					graphics.DrawString(this.Text, this._Font, new SolidBrush(this._FontColour), new RectangleF(31f, 0f, (float)(base.Width - 110), 35f), new StringFormat
					{
						LineAlignment = StringAlignment.Center,
						Alignment = StringAlignment.Near
					});
				}
				else
				{
					graphics.DrawString(this.Text, this._Font, new SolidBrush(this._FontColour), new RectangleF(4f, 0f, (float)(base.Width - 110), 35f), new StringFormat
					{
						LineAlignment = StringAlignment.Center,
						Alignment = StringAlignment.Near
					});
				}
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x04000060 RID: 96
		private LogInThemeContainer.__CloseChoice _CloseChoice;

		// Token: 0x04000061 RID: 97
		private int _FontSize;

		// Token: 0x04000062 RID: 98
		private readonly Font _Font;

		// Token: 0x04000063 RID: 99
		private int MouseXLoc;

		// Token: 0x04000064 RID: 100
		private int MouseYLoc;

		// Token: 0x04000065 RID: 101
		private bool CaptureMovement;

		// Token: 0x04000066 RID: 102
		private const int MoveHeight = 35;

		// Token: 0x04000067 RID: 103
		private Point MouseP;

		// Token: 0x04000068 RID: 104
		private Color _FontColour;

		// Token: 0x04000069 RID: 105
		private Color _BaseColour;

		// Token: 0x0400006A RID: 106
		private Color _ContainerColour;

		// Token: 0x0400006B RID: 107
		private Color _BorderColour;

		// Token: 0x0400006C RID: 108
		private Color _HoverColour;

		// Token: 0x0400006D RID: 109
		private int _LockWidth;

		// Token: 0x0400006E RID: 110
		private int _LockHeight;

		// Token: 0x0400006F RID: 111
		private Rectangle Frame;

		// Token: 0x04000070 RID: 112
		private DrawHelpers.MouseState State;

		// Token: 0x04000071 RID: 113
		private Point GetMouseLocation;

		// Token: 0x04000072 RID: 114
		private Size OldSize;

		// Token: 0x04000073 RID: 115
		private Message[] Messages;

		// Token: 0x04000074 RID: 116
		private Point GetIndexPoint;

		// Token: 0x04000075 RID: 117
		private bool B1;

		// Token: 0x04000076 RID: 118
		private bool B2;

		// Token: 0x04000077 RID: 119
		private bool B3;

		// Token: 0x04000078 RID: 120
		private bool B4;

		// Token: 0x04000079 RID: 121
		private int Current;

		// Token: 0x0400007A RID: 122
		private int Previous;

		// Token: 0x0400007B RID: 123
		private bool WM_LMBUTTONDOWN;

		// Token: 0x0400007C RID: 124
		private bool _Movable;

		// Token: 0x0400007D RID: 125
		private bool _Sizable;

		// Token: 0x0400007E RID: 126
		private bool _ControlMode;

		// Token: 0x0400007F RID: 127
		private bool _SmartBounds;

		// Token: 0x04000080 RID: 128
		private bool _IsParentForm;

		// Token: 0x04000081 RID: 129
		private bool _AllowMinimize;

		// Token: 0x04000082 RID: 130
		private bool _AllowMaximize;

		// Token: 0x04000083 RID: 131
		private bool _ShowIcon;

		// Token: 0x04000084 RID: 132
		private bool _AllowClose;

		// Token: 0x02000012 RID: 18
		public enum __CloseChoice
		{
			// Token: 0x04000086 RID: 134
			Form,
			// Token: 0x04000087 RID: 135
			Application
		}
	}
}
