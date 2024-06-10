using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace WindowsApplication1
{
	// Token: 0x02000013 RID: 19
	internal abstract class ThemeContainer151 : ContainerControl
	{
		// Token: 0x060000B8 RID: 184 RVA: 0x0000CB88 File Offset: 0x0000AF88
		public ThemeContainer151()
		{
			this.Messages = new Message[9];
			this._Movable = true;
			this._Sizable = true;
			this._MoveHeight = 24;
			this.Items = new Dictionary<string, Color>();
			this.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this._ImageSize = Size.Empty;
			this.MeasureBitmap = new Bitmap(1, 1);
			this.MeasureGraphics = Graphics.FromImage(this.MeasureBitmap);
			this.Font = new Font("Verdana", 8f);
			this.InvalidateCustimization();
		}

		// Token: 0x060000B9 RID: 185 RVA: 0x0000CC1C File Offset: 0x0000B01C
		protected override void SetBoundsCore(int x, int y, int width, int height, BoundsSpecified specified)
		{
			if (this._LockWidth != 0)
			{
				width = this._LockWidth;
			}
			if (this._LockHeight != 0)
			{
				height = this._LockHeight;
			}
			base.SetBoundsCore(x, y, width, height, specified);
		}

		// Token: 0x060000BA RID: 186 RVA: 0x0000CC50 File Offset: 0x0000B050
		protected sealed override void OnSizeChanged(EventArgs e)
		{
			base.OnSizeChanged(e);
			if (this._Movable && !this._ControlMode)
			{
				this.Header = checked(new Rectangle(7, 7, this.Width - 14, this._MoveHeight - 7));
			}
			this.Invalidate();
		}

		// Token: 0x060000BB RID: 187 RVA: 0x0000CC90 File Offset: 0x0000B090
		protected sealed override void OnPaint(PaintEventArgs e)
		{
			if (this.Width != 0)
			{
				if (this.Height == 0)
				{
					return;
				}
				this.G = e.Graphics;
				this.PaintHook();
			}
		}

		// Token: 0x060000BC RID: 188 RVA: 0x0000CCB8 File Offset: 0x0000B0B8
		protected sealed override void OnHandleCreated(EventArgs e)
		{
			this.InitializeMessages();
			this.InvalidateCustimization();
			this.ColorHook();
			this._IsParentForm = (this.Parent is Form);
			if (!this._ControlMode)
			{
				this.Dock = DockStyle.Fill;
			}
			if (this._LockWidth != 0)
			{
				this.Width = this._LockWidth;
			}
			if (this._LockHeight != 0)
			{
				this.Height = this._LockHeight;
			}
			Color right;
			if (!(this.BackColorWait == right))
			{
				this.BackColor = this.BackColorWait;
			}
			if (this._IsParentForm && !this._ControlMode)
			{
				this.ParentForm.FormBorderStyle = this._BorderStyle;
				this.ParentForm.TransparencyKey = this._TransparencyKey;
			}
			this.OnCreation();
			base.OnHandleCreated(e);
		}

		// Token: 0x060000BD RID: 189 RVA: 0x0000CD80 File Offset: 0x0000B180
		protected virtual void OnCreation()
		{
		}

		// Token: 0x060000BE RID: 190 RVA: 0x0000CD84 File Offset: 0x0000B184
		private void SetState(MouseState current)
		{
			this.State = current;
			this.Invalidate();
		}

		// Token: 0x060000BF RID: 191 RVA: 0x0000CD94 File Offset: 0x0000B194
		protected override void OnMouseMove(MouseEventArgs e)
		{
			if (this._Sizable && !this._ControlMode)
			{
				this.InvalidateMouse();
			}
			base.OnMouseMove(e);
		}

		// Token: 0x060000C0 RID: 192 RVA: 0x0000CDB4 File Offset: 0x0000B1B4
		protected override void OnEnabledChanged(EventArgs e)
		{
			if (this.Enabled)
			{
				this.SetState(MouseState.None);
			}
			else
			{
				this.SetState(MouseState.Block);
			}
			base.OnEnabledChanged(e);
		}

		// Token: 0x060000C1 RID: 193 RVA: 0x0000CDD8 File Offset: 0x0000B1D8
		protected override void OnMouseEnter(EventArgs e)
		{
			this.SetState(MouseState.Over);
			base.OnMouseEnter(e);
		}

		// Token: 0x060000C2 RID: 194 RVA: 0x0000CDE8 File Offset: 0x0000B1E8
		protected override void OnMouseUp(MouseEventArgs e)
		{
			this.SetState(MouseState.Over);
			base.OnMouseUp(e);
		}

		// Token: 0x060000C3 RID: 195 RVA: 0x0000CDF8 File Offset: 0x0000B1F8
		protected override void OnMouseLeave(EventArgs e)
		{
			this.SetState(MouseState.None);
			if (this._Sizable && !this._ControlMode && this.GetChildAtPoint(this.PointToClient(Control.MousePosition)) != null)
			{
				this.Cursor = Cursors.Default;
				this.Previous = 0;
			}
			base.OnMouseLeave(e);
		}

		// Token: 0x060000C4 RID: 196 RVA: 0x0000CE48 File Offset: 0x0000B248
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (e.Button != MouseButtons.Left)
			{
				return;
			}
			this.SetState(MouseState.Down);
			if (this._IsParentForm && this.ParentForm.WindowState == FormWindowState.Maximized)
			{
				return;
			}
			if (this._ControlMode)
			{
				return;
			}
			if (this._Movable && this.Header.Contains(e.Location))
			{
				this.Capture = false;
				this.DefWndProc(ref this.Messages[0]);
			}
			else if (this._Sizable && this.Previous != 0)
			{
				this.Capture = false;
				this.DefWndProc(ref this.Messages[this.Previous]);
			}
		}

		// Token: 0x060000C5 RID: 197 RVA: 0x0000CEF8 File Offset: 0x0000B2F8
		private int GetIndex()
		{
			this.GetIndexPoint = this.PointToClient(Control.MousePosition);
			this.B1 = (this.GetIndexPoint.X < 7);
			checked
			{
				this.B2 = (this.GetIndexPoint.X > this.Width - 7);
				this.B3 = (this.GetIndexPoint.Y < 7);
				this.B4 = (this.GetIndexPoint.Y > this.Height - 7);
				if (this.B1 && this.B3)
				{
					return 4;
				}
				if (this.B1 && this.B4)
				{
					return 7;
				}
				if (this.B2 && this.B3)
				{
					return 5;
				}
				if (this.B2 && this.B4)
				{
					return 8;
				}
				if (this.B1)
				{
					return 1;
				}
				if (this.B2)
				{
					return 2;
				}
				if (this.B3)
				{
					return 3;
				}
				if (this.B4)
				{
					return 6;
				}
				return 0;
			}
		}

		// Token: 0x060000C6 RID: 198 RVA: 0x0000CFE8 File Offset: 0x0000B3E8
		private void InvalidateMouse()
		{
			this.Current = this.GetIndex();
			if (this.Current == this.Previous)
			{
				return;
			}
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

		// Token: 0x060000C7 RID: 199 RVA: 0x0000D08C File Offset: 0x0000B48C
		private void InitializeMessages()
		{
			Message[] messages = this.Messages;
			int num = 0;
			IntPtr handle = this.Parent.Handle;
			int msg = 161;
			IntPtr wparam = new IntPtr(2);
			messages[num] = Message.Create(handle, msg, wparam, IntPtr.Zero);
			int num2 = 1;
			checked
			{
				do
				{
					Message[] messages2 = this.Messages;
					int num3 = num2;
					IntPtr handle2 = this.Parent.Handle;
					int msg2 = 161;
					wparam = new IntPtr(num2 + 9);
					messages2[num3] = Message.Create(handle2, msg2, wparam, IntPtr.Zero);
					num2++;
				}
				while (num2 <= 8);
			}
		}

		// Token: 0x1700003F RID: 63
		// (get) Token: 0x060000C8 RID: 200 RVA: 0x0000D110 File Offset: 0x0000B510
		// (set) Token: 0x060000C9 RID: 201 RVA: 0x0000D124 File Offset: 0x0000B524
		public override Color BackColor
		{
			get
			{
				return base.BackColor;
			}
			set
			{
				if (this.IsHandleCreated)
				{
					if (!this._ControlMode)
					{
						this.Parent.BackColor = value;
					}
					base.BackColor = value;
				}
				else
				{
					this.BackColorWait = value;
				}
			}
		}

		// Token: 0x17000040 RID: 64
		// (get) Token: 0x060000CA RID: 202 RVA: 0x0000D160 File Offset: 0x0000B560
		// (set) Token: 0x060000CB RID: 203 RVA: 0x0000D174 File Offset: 0x0000B574
		[EditorBrowsable(EditorBrowsableState.Never)]
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
		[Browsable(false)]
		public override Color ForeColor
		{
			get
			{
				return Color.Empty;
			}
			set
			{
			}
		}

		// Token: 0x17000041 RID: 65
		// (get) Token: 0x060000CC RID: 204 RVA: 0x0000D178 File Offset: 0x0000B578
		// (set) Token: 0x060000CD RID: 205 RVA: 0x0000D188 File Offset: 0x0000B588
		[EditorBrowsable(EditorBrowsableState.Never)]
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
		[Browsable(false)]
		public override Image BackgroundImage
		{
			get
			{
				return null;
			}
			set
			{
			}
		}

		// Token: 0x17000042 RID: 66
		// (get) Token: 0x060000CE RID: 206 RVA: 0x0000D18C File Offset: 0x0000B58C
		// (set) Token: 0x060000CF RID: 207 RVA: 0x0000D19C File Offset: 0x0000B59C
		[Browsable(false)]
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
		[EditorBrowsable(EditorBrowsableState.Never)]
		public override ImageLayout BackgroundImageLayout
		{
			get
			{
				return ImageLayout.None;
			}
			set
			{
			}
		}

		// Token: 0x17000043 RID: 67
		// (get) Token: 0x060000D0 RID: 208 RVA: 0x0000D1A0 File Offset: 0x0000B5A0
		// (set) Token: 0x060000D1 RID: 209 RVA: 0x0000D1B4 File Offset: 0x0000B5B4
		public override string Text
		{
			get
			{
				return base.Text;
			}
			set
			{
				base.Text = value;
				this.Invalidate();
			}
		}

		// Token: 0x17000044 RID: 68
		// (get) Token: 0x060000D2 RID: 210 RVA: 0x0000D1C4 File Offset: 0x0000B5C4
		// (set) Token: 0x060000D3 RID: 211 RVA: 0x0000D1D8 File Offset: 0x0000B5D8
		public override Font Font
		{
			get
			{
				return base.Font;
			}
			set
			{
				base.Font = value;
				this.Invalidate();
			}
		}

		// Token: 0x17000045 RID: 69
		// (get) Token: 0x060000D4 RID: 212 RVA: 0x0000D1E8 File Offset: 0x0000B5E8
		// (set) Token: 0x060000D5 RID: 213 RVA: 0x0000D1FC File Offset: 0x0000B5FC
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

		// Token: 0x17000046 RID: 70
		// (get) Token: 0x060000D6 RID: 214 RVA: 0x0000D208 File Offset: 0x0000B608
		// (set) Token: 0x060000D7 RID: 215 RVA: 0x0000D21C File Offset: 0x0000B61C
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

		// Token: 0x17000047 RID: 71
		// (get) Token: 0x060000D8 RID: 216 RVA: 0x0000D228 File Offset: 0x0000B628
		// (set) Token: 0x060000D9 RID: 217 RVA: 0x0000D23C File Offset: 0x0000B63C
		protected int MoveHeight
		{
			get
			{
				return this._MoveHeight;
			}
			set
			{
				if (value < 8)
				{
					return;
				}
				this.Header = checked(new Rectangle(7, 7, this.Width - 14, value - 7));
				this._MoveHeight = value;
				this.Invalidate();
			}
		}

		// Token: 0x17000048 RID: 72
		// (get) Token: 0x060000DA RID: 218 RVA: 0x0000D26C File Offset: 0x0000B66C
		// (set) Token: 0x060000DB RID: 219 RVA: 0x0000D280 File Offset: 0x0000B680
		protected bool ControlMode
		{
			get
			{
				return this._ControlMode;
			}
			set
			{
				this._ControlMode = value;
			}
		}

		// Token: 0x17000049 RID: 73
		// (get) Token: 0x060000DC RID: 220 RVA: 0x0000D28C File Offset: 0x0000B68C
		// (set) Token: 0x060000DD RID: 221 RVA: 0x0000D2BC File Offset: 0x0000B6BC
		public Color TransparencyKey
		{
			get
			{
				if (this._IsParentForm && !this._ControlMode)
				{
					return this.ParentForm.TransparencyKey;
				}
				return this._TransparencyKey;
			}
			set
			{
				if (this._IsParentForm && !this._ControlMode)
				{
					this.ParentForm.TransparencyKey = value;
				}
				this._TransparencyKey = value;
			}
		}

		// Token: 0x1700004A RID: 74
		// (get) Token: 0x060000DE RID: 222 RVA: 0x0000D2EC File Offset: 0x0000B6EC
		// (set) Token: 0x060000DF RID: 223 RVA: 0x0000D31C File Offset: 0x0000B71C
		public FormBorderStyle BorderStyle
		{
			get
			{
				if (this._IsParentForm && !this._ControlMode)
				{
					return this.ParentForm.FormBorderStyle;
				}
				return this._BorderStyle;
			}
			set
			{
				if (this._IsParentForm && !this._ControlMode)
				{
					this.ParentForm.FormBorderStyle = value;
				}
				this._BorderStyle = value;
			}
		}

		// Token: 0x1700004B RID: 75
		// (get) Token: 0x060000E0 RID: 224 RVA: 0x0000D344 File Offset: 0x0000B744
		// (set) Token: 0x060000E1 RID: 225 RVA: 0x0000D358 File Offset: 0x0000B758
		public bool NoRounding
		{
			get
			{
				return this._NoRounding;
			}
			set
			{
				this._NoRounding = value;
				this.Invalidate();
			}
		}

		// Token: 0x1700004C RID: 76
		// (get) Token: 0x060000E2 RID: 226 RVA: 0x0000D368 File Offset: 0x0000B768
		// (set) Token: 0x060000E3 RID: 227 RVA: 0x0000D37C File Offset: 0x0000B77C
		public Image Image
		{
			get
			{
				return this._Image;
			}
			set
			{
				if (value == null)
				{
					this._ImageSize = Size.Empty;
				}
				else
				{
					this._ImageSize = value.Size;
				}
				this._Image = value;
				this.Invalidate();
			}
		}

		// Token: 0x1700004D RID: 77
		// (get) Token: 0x060000E4 RID: 228 RVA: 0x0000D3A8 File Offset: 0x0000B7A8
		protected Size ImageSize
		{
			get
			{
				return this._ImageSize;
			}
		}

		// Token: 0x1700004E RID: 78
		// (get) Token: 0x060000E5 RID: 229 RVA: 0x0000D3BC File Offset: 0x0000B7BC
		protected bool IsParentForm
		{
			get
			{
				return this._IsParentForm;
			}
		}

		// Token: 0x1700004F RID: 79
		// (get) Token: 0x060000E6 RID: 230 RVA: 0x0000D3D0 File Offset: 0x0000B7D0
		// (set) Token: 0x060000E7 RID: 231 RVA: 0x0000D3E4 File Offset: 0x0000B7E4
		protected int LockWidth
		{
			get
			{
				return this._LockWidth;
			}
			set
			{
				this._LockWidth = value;
				if (this.LockWidth != 0 && this.IsHandleCreated)
				{
					this.Width = this.LockWidth;
				}
			}
		}

		// Token: 0x17000050 RID: 80
		// (get) Token: 0x060000E8 RID: 232 RVA: 0x0000D40C File Offset: 0x0000B80C
		// (set) Token: 0x060000E9 RID: 233 RVA: 0x0000D420 File Offset: 0x0000B820
		protected int LockHeight
		{
			get
			{
				return this._LockHeight;
			}
			set
			{
				this._LockHeight = value;
				if (this.LockHeight != 0 && this.IsHandleCreated)
				{
					this.Height = this.LockHeight;
				}
			}
		}

		// Token: 0x17000051 RID: 81
		// (get) Token: 0x060000EA RID: 234 RVA: 0x0000D448 File Offset: 0x0000B848
		// (set) Token: 0x060000EB RID: 235 RVA: 0x0000D4A4 File Offset: 0x0000B8A4
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
		public Bloom[] Colors
		{
			get
			{
				List<Bloom> list = new List<Bloom>();
				Dictionary<string, Color>.Enumerator enumerator = this.Items.GetEnumerator();
				while (enumerator.MoveNext())
				{
					List<Bloom> list2 = list;
					KeyValuePair<string, Color> keyValuePair = enumerator.Current;
					string key = keyValuePair.Key;
					KeyValuePair<string, Color> keyValuePair2 = enumerator.Current;
					list2.Add(new Bloom(key, keyValuePair2.Value));
				}
				return list.ToArray();
			}
			set
			{
				checked
				{
					for (int i = 0; i < value.Length; i++)
					{
						Bloom bloom = value[i];
						if (this.Items.ContainsKey(bloom.Name))
						{
							this.Items[bloom.Name] = bloom.Value;
						}
					}
					this.InvalidateCustimization();
					this.ColorHook();
					this.Invalidate();
				}
			}
		}

		// Token: 0x17000052 RID: 82
		// (get) Token: 0x060000EC RID: 236 RVA: 0x0000D504 File Offset: 0x0000B904
		// (set) Token: 0x060000ED RID: 237 RVA: 0x0000D518 File Offset: 0x0000B918
		public string Customization
		{
			get
			{
				return this._Customization;
			}
			set
			{
				if (Operators.CompareString(value, this._Customization, false) == 0)
				{
					return;
				}
				Bloom[] colors = this.Colors;
				checked
				{
					try
					{
						byte[] value2 = Convert.FromBase64String(value);
						int num = 0;
						int num2 = colors.Length - 1;
						for (int i = num; i <= num2; i++)
						{
							colors[i].Value = Color.FromArgb(BitConverter.ToInt32(value2, i * 4));
						}
					}
					catch (Exception ex)
					{
						return;
					}
					this._Customization = value;
					this.Colors = colors;
					this.ColorHook();
					this.Invalidate();
				}
			}
		}

		// Token: 0x060000EE RID: 238 RVA: 0x0000D5A4 File Offset: 0x0000B9A4
		protected Color GetColor(string name)
		{
			return this.Items[name];
		}

		// Token: 0x060000EF RID: 239 RVA: 0x0000D5C0 File Offset: 0x0000B9C0
		protected void SetColor(string name, Color color)
		{
			if (this.Items.ContainsKey(name))
			{
				this.Items[name] = color;
			}
			else
			{
				this.Items.Add(name, color);
			}
		}

		// Token: 0x060000F0 RID: 240 RVA: 0x0000D5F8 File Offset: 0x0000B9F8
		protected void SetColor(string name, byte r, byte g, byte b)
		{
			this.SetColor(name, Color.FromArgb((int)r, (int)g, (int)b));
		}

		// Token: 0x060000F1 RID: 241 RVA: 0x0000D60C File Offset: 0x0000BA0C
		protected void SetColor(string name, byte a, byte r, byte g, byte b)
		{
			this.SetColor(name, Color.FromArgb((int)a, (int)r, (int)g, (int)b));
		}

		// Token: 0x060000F2 RID: 242 RVA: 0x0000D620 File Offset: 0x0000BA20
		protected void SetColor(string name, byte a, Color color)
		{
			this.SetColor(name, Color.FromArgb((int)a, color));
		}

		// Token: 0x060000F3 RID: 243 RVA: 0x0000D630 File Offset: 0x0000BA30
		private void InvalidateCustimization()
		{
			MemoryStream memoryStream = new MemoryStream(checked(this.Items.Count * 4));
			foreach (Bloom bloom in this.Colors)
			{
				memoryStream.Write(BitConverter.GetBytes(bloom.Value.ToArgb()), 0, 4);
			}
			memoryStream.Close();
			this._Customization = Convert.ToBase64String(memoryStream.ToArray());
		}

		// Token: 0x060000F4 RID: 244
		protected abstract void ColorHook();

		// Token: 0x060000F5 RID: 245
		protected abstract void PaintHook();

		// Token: 0x060000F6 RID: 246 RVA: 0x0000D69C File Offset: 0x0000BA9C
		protected Point Center(Rectangle r1, Size s1)
		{
			this.CenterReturn = checked(new Point(r1.Width / 2 - s1.Width / 2 + r1.X, r1.Height / 2 - s1.Height / 2 + r1.Y));
			return this.CenterReturn;
		}

		// Token: 0x060000F7 RID: 247 RVA: 0x0000D6F0 File Offset: 0x0000BAF0
		protected Point Center(Rectangle r1, Rectangle r2)
		{
			return this.Center(r1, r2.Size);
		}

		// Token: 0x060000F8 RID: 248 RVA: 0x0000D70C File Offset: 0x0000BB0C
		protected Point Center(int w1, int h1, int w2, int h2)
		{
			this.CenterReturn = checked(new Point(w1 / 2 - w2 / 2, h1 / 2 - h2 / 2));
			return this.CenterReturn;
		}

		// Token: 0x060000F9 RID: 249 RVA: 0x0000D73C File Offset: 0x0000BB3C
		protected Point Center(Size s1, Size s2)
		{
			return this.Center(s1.Width, s1.Height, s2.Width, s2.Height);
		}

		// Token: 0x060000FA RID: 250 RVA: 0x0000D76C File Offset: 0x0000BB6C
		protected Point Center(Rectangle r1)
		{
			return this.Center(this.ClientRectangle.Width, this.ClientRectangle.Height, r1.Width, r1.Height);
		}

		// Token: 0x060000FB RID: 251 RVA: 0x0000D7AC File Offset: 0x0000BBAC
		protected Point Center(Size s1)
		{
			return this.Center(this.Width, this.Height, s1.Width, s1.Height);
		}

		// Token: 0x060000FC RID: 252 RVA: 0x0000D7DC File Offset: 0x0000BBDC
		protected Point Center(int w1, int h1)
		{
			return this.Center(this.Width, this.Height, w1, h1);
		}

		// Token: 0x060000FD RID: 253 RVA: 0x0000D800 File Offset: 0x0000BC00
		protected Size Measure(string text)
		{
			return this.MeasureGraphics.MeasureString(text, this.Font, this.Width).ToSize();
		}

		// Token: 0x060000FE RID: 254 RVA: 0x0000D830 File Offset: 0x0000BC30
		protected Size Measure()
		{
			return this.MeasureGraphics.MeasureString(this.Text, this.Font).ToSize();
		}

		// Token: 0x060000FF RID: 255 RVA: 0x0000D85C File Offset: 0x0000BC5C
		protected void DrawCorners(Color c1)
		{
			this.DrawCorners(c1, 0, 0, this.Width, this.Height);
		}

		// Token: 0x06000100 RID: 256 RVA: 0x0000D874 File Offset: 0x0000BC74
		protected void DrawCorners(Color c1, Rectangle r1)
		{
			this.DrawCorners(c1, r1.X, r1.Y, r1.Width, r1.Height);
		}

		// Token: 0x06000101 RID: 257 RVA: 0x0000D89C File Offset: 0x0000BC9C
		protected void DrawCorners(Color c1, int x, int y, int width, int height)
		{
			if (this._NoRounding)
			{
				return;
			}
			this.DrawCornersBrush = new SolidBrush(c1);
			this.G.FillRectangle(this.DrawCornersBrush, x, y, 1, 1);
			checked
			{
				this.G.FillRectangle(this.DrawCornersBrush, x + (width - 1), y, 1, 1);
				this.G.FillRectangle(this.DrawCornersBrush, x, y + (height - 1), 1, 1);
				this.G.FillRectangle(this.DrawCornersBrush, x + (width - 1), y + (height - 1), 1, 1);
			}
		}

		// Token: 0x06000102 RID: 258 RVA: 0x0000D928 File Offset: 0x0000BD28
		protected void DrawBorders(Pen p1, int x, int y, int width, int height, int offset)
		{
			checked
			{
				this.DrawBorders(p1, x + offset, y + offset, width - offset * 2, height - offset * 2);
			}
		}

		// Token: 0x06000103 RID: 259 RVA: 0x0000D948 File Offset: 0x0000BD48
		protected void DrawBorders(Pen p1, int offset)
		{
			this.DrawBorders(p1, 0, 0, this.Width, this.Height, offset);
		}

		// Token: 0x06000104 RID: 260 RVA: 0x0000D960 File Offset: 0x0000BD60
		protected void DrawBorders(Pen p1, Rectangle r, int offset)
		{
			this.DrawBorders(p1, r.X, r.Y, r.Width, r.Height, offset);
		}

		// Token: 0x06000105 RID: 261 RVA: 0x0000D988 File Offset: 0x0000BD88
		protected void DrawBorders(Pen p1, int x, int y, int width, int height)
		{
			checked
			{
				this.G.DrawRectangle(p1, x, y, width - 1, height - 1);
			}
		}

		// Token: 0x06000106 RID: 262 RVA: 0x0000D9A0 File Offset: 0x0000BDA0
		protected void DrawBorders(Pen p1)
		{
			this.DrawBorders(p1, 0, 0, this.Width, this.Height);
		}

		// Token: 0x06000107 RID: 263 RVA: 0x0000D9B8 File Offset: 0x0000BDB8
		protected void DrawBorders(Pen p1, Rectangle r)
		{
			this.DrawBorders(p1, r.X, r.Y, r.Width, r.Height);
		}

		// Token: 0x06000108 RID: 264 RVA: 0x0000D9E0 File Offset: 0x0000BDE0
		protected void DrawText(Brush b1, HorizontalAlignment a, int x, int y)
		{
			this.DrawText(b1, this.Text, a, x, y);
		}

		// Token: 0x06000109 RID: 265 RVA: 0x0000D9F4 File Offset: 0x0000BDF4
		protected void DrawText(Brush b1, Point p1)
		{
			this.DrawText(b1, this.Text, p1.X, p1.Y);
		}

		// Token: 0x0600010A RID: 266 RVA: 0x0000DA14 File Offset: 0x0000BE14
		protected void DrawText(Brush b1, int x, int y)
		{
			this.DrawText(b1, this.Text, x, y);
		}

		// Token: 0x0600010B RID: 267 RVA: 0x0000DA28 File Offset: 0x0000BE28
		protected void DrawText(Brush b1, string text, HorizontalAlignment a, int x, int y)
		{
			if (text.Length == 0)
			{
				return;
			}
			this.DrawTextSize = this.Measure(text);
			checked
			{
				this.DrawTextPoint = new Point(this.Width / 2 - this.DrawTextSize.Width / 2, this.MoveHeight / 2 - this.DrawTextSize.Height / 2);
				switch (a)
				{
				case HorizontalAlignment.Left:
					this.DrawText(b1, text, x, this.DrawTextPoint.Y + y);
					break;
				case HorizontalAlignment.Right:
					this.DrawText(b1, text, this.Width - this.DrawTextSize.Width - x, this.DrawTextPoint.Y + y);
					break;
				case HorizontalAlignment.Center:
					this.DrawText(b1, text, this.DrawTextPoint.X + x, this.DrawTextPoint.Y + y);
					break;
				}
			}
		}

		// Token: 0x0600010C RID: 268 RVA: 0x0000DB04 File Offset: 0x0000BF04
		protected void DrawText(Brush b1, string text, Point p1)
		{
			this.DrawText(b1, text, p1.X, p1.Y);
		}

		// Token: 0x0600010D RID: 269 RVA: 0x0000DB1C File Offset: 0x0000BF1C
		protected void DrawText(Brush b1, string text, int x, int y)
		{
			if (text.Length == 0)
			{
				return;
			}
			this.G.DrawString(text, this.Font, b1, (float)x, (float)y);
		}

		// Token: 0x0600010E RID: 270 RVA: 0x0000DB40 File Offset: 0x0000BF40
		protected void DrawImage(HorizontalAlignment a, int x, int y)
		{
			this.DrawImage(this._Image, a, x, y);
		}

		// Token: 0x0600010F RID: 271 RVA: 0x0000DB54 File Offset: 0x0000BF54
		protected void DrawImage(Point p1)
		{
			this.DrawImage(this._Image, p1.X, p1.Y);
		}

		// Token: 0x06000110 RID: 272 RVA: 0x0000DB70 File Offset: 0x0000BF70
		protected void DrawImage(int x, int y)
		{
			this.DrawImage(this._Image, x, y);
		}

		// Token: 0x06000111 RID: 273 RVA: 0x0000DB80 File Offset: 0x0000BF80
		protected void DrawImage(Image image, HorizontalAlignment a, int x, int y)
		{
			if (image == null)
			{
				return;
			}
			checked
			{
				this.DrawImagePoint = new Point(this.Width / 2 - image.Width / 2, this.MoveHeight / 2 - image.Height / 2);
				switch (a)
				{
				case HorizontalAlignment.Left:
					this.DrawImage(image, x, this.DrawImagePoint.Y + y);
					break;
				case HorizontalAlignment.Right:
					this.DrawImage(image, this.Width - image.Width - x, this.DrawImagePoint.Y + y);
					break;
				case HorizontalAlignment.Center:
					this.DrawImage(image, this.DrawImagePoint.X + x, this.DrawImagePoint.Y + y);
					break;
				}
			}
		}

		// Token: 0x06000112 RID: 274 RVA: 0x0000DC34 File Offset: 0x0000C034
		protected void DrawImage(Image image, Point p1)
		{
			this.DrawImage(image, p1.X, p1.Y);
		}

		// Token: 0x06000113 RID: 275 RVA: 0x0000DC4C File Offset: 0x0000C04C
		protected void DrawImage(Image image, int x, int y)
		{
			if (image == null)
			{
				return;
			}
			this.G.DrawImage(image, x, y, image.Width, image.Height);
		}

		// Token: 0x06000114 RID: 276 RVA: 0x0000DC6C File Offset: 0x0000C06C
		protected void DrawGradient(ColorBlend blend, int x, int y, int width, int height)
		{
			this.DrawGradient(blend, x, y, width, height, 90f);
		}

		// Token: 0x06000115 RID: 277 RVA: 0x0000DC80 File Offset: 0x0000C080
		protected void DrawGradient(Color c1, Color c2, int x, int y, int width, int height)
		{
			this.DrawGradient(c1, c2, x, y, width, height, 90f);
		}

		// Token: 0x06000116 RID: 278 RVA: 0x0000DC98 File Offset: 0x0000C098
		protected void DrawGradient(ColorBlend blend, int x, int y, int width, int height, float angle)
		{
			this.DrawGradientRectangle = new Rectangle(x, y, width, height);
			this.DrawGradient(blend, this.DrawGradientRectangle, angle);
		}

		// Token: 0x06000117 RID: 279 RVA: 0x0000DCBC File Offset: 0x0000C0BC
		protected void DrawGradient(Color c1, Color c2, int x, int y, int width, int height, float angle)
		{
			this.DrawGradientRectangle = new Rectangle(x, y, width, height);
			this.DrawGradient(c1, c2, this.DrawGradientRectangle, angle);
		}

		// Token: 0x06000118 RID: 280 RVA: 0x0000DCE0 File Offset: 0x0000C0E0
		protected void DrawGradient(ColorBlend blend, Rectangle r, float angle)
		{
			this.DrawGradientBrush = new LinearGradientBrush(r, Color.Empty, Color.Empty, angle);
			this.DrawGradientBrush.InterpolationColors = blend;
			this.G.FillRectangle(this.DrawGradientBrush, r);
		}

		// Token: 0x06000119 RID: 281 RVA: 0x0000DD18 File Offset: 0x0000C118
		protected void DrawGradient(Color c1, Color c2, Rectangle r, float angle)
		{
			this.DrawGradientBrush = new LinearGradientBrush(r, c1, c2, angle);
			this.G.FillRectangle(this.DrawGradientBrush, r);
		}

		// Token: 0x0400005A RID: 90
		protected Graphics G;

		// Token: 0x0400005B RID: 91
		private Rectangle Header;

		// Token: 0x0400005C RID: 92
		protected MouseState State;

		// Token: 0x0400005D RID: 93
		private Point GetIndexPoint;

		// Token: 0x0400005E RID: 94
		private bool B1;

		// Token: 0x0400005F RID: 95
		private bool B2;

		// Token: 0x04000060 RID: 96
		private bool B3;

		// Token: 0x04000061 RID: 97
		private bool B4;

		// Token: 0x04000062 RID: 98
		private int Current;

		// Token: 0x04000063 RID: 99
		private int Previous;

		// Token: 0x04000064 RID: 100
		private Message[] Messages;

		// Token: 0x04000065 RID: 101
		private Color BackColorWait;

		// Token: 0x04000066 RID: 102
		private bool _Movable;

		// Token: 0x04000067 RID: 103
		private bool _Sizable;

		// Token: 0x04000068 RID: 104
		private int _MoveHeight;

		// Token: 0x04000069 RID: 105
		private bool _ControlMode;

		// Token: 0x0400006A RID: 106
		private Color _TransparencyKey;

		// Token: 0x0400006B RID: 107
		private FormBorderStyle _BorderStyle;

		// Token: 0x0400006C RID: 108
		private bool _NoRounding;

		// Token: 0x0400006D RID: 109
		private Image _Image;

		// Token: 0x0400006E RID: 110
		private Size _ImageSize;

		// Token: 0x0400006F RID: 111
		private bool _IsParentForm;

		// Token: 0x04000070 RID: 112
		private int _LockWidth;

		// Token: 0x04000071 RID: 113
		private int _LockHeight;

		// Token: 0x04000072 RID: 114
		private Dictionary<string, Color> Items;

		// Token: 0x04000073 RID: 115
		private string _Customization;

		// Token: 0x04000074 RID: 116
		private Point CenterReturn;

		// Token: 0x04000075 RID: 117
		private Bitmap MeasureBitmap;

		// Token: 0x04000076 RID: 118
		private Graphics MeasureGraphics;

		// Token: 0x04000077 RID: 119
		private SolidBrush DrawCornersBrush;

		// Token: 0x04000078 RID: 120
		private Point DrawTextPoint;

		// Token: 0x04000079 RID: 121
		private Size DrawTextSize;

		// Token: 0x0400007A RID: 122
		private Point DrawImagePoint;

		// Token: 0x0400007B RID: 123
		private LinearGradientBrush DrawGradientBrush;

		// Token: 0x0400007C RID: 124
		private Rectangle DrawGradientRectangle;
	}
}
