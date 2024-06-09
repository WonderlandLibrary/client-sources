using System;
using System.Drawing;

namespace Xh0kO1ZCmA
{
	public class MouseAction : OneAction
	{
		public Xh0kO1ZCmA.MouseAction.MouseAction MainType;

		public Point p;

		public Xh0kO1ZCmA.MouseAction.ScrolDir ScrolType;

		public MouseAction(Xh0kO1ZCmA.MouseAction.MouseAction _MainType, Point _p, int _Delay, MouseHook.Wheel_Direction dir = 0) : base(DataSession.ActionType.Mouse, _Delay)
		{
			this.MainType = _MainType;
			this.p = _p;
			this.ScrolType = (Xh0kO1ZCmA.MouseAction.ScrolDir)dir;
		}

		public override string ToString()
		{
			string str;
			switch (this.MainType)
			{
				case Xh0kO1ZCmA.MouseAction.MouseAction.L:
				{
					str = string.Concat("Mouse Left >> ", this.p.ToString());
					break;
				}
				case Xh0kO1ZCmA.MouseAction.MouseAction.DL:
				{
					str = string.Concat("Mouse Double Left >> ", this.p.ToString());
					break;
				}
				case Xh0kO1ZCmA.MouseAction.MouseAction.R:
				{
					str = string.Concat("Mouse Right >> ", this.p.ToString());
					break;
				}
				case Xh0kO1ZCmA.MouseAction.MouseAction.DR:
				{
					str = string.Concat("Mouse Double Right >> ", this.p.ToString());
					break;
				}
				case Xh0kO1ZCmA.MouseAction.MouseAction.M:
				{
					str = string.Concat("Mouse Middle >> ", this.p.ToString());
					break;
				}
				case Xh0kO1ZCmA.MouseAction.MouseAction.DM:
				{
					str = string.Concat("Mouse Double Middle >> ", this.p.ToString());
					break;
				}
				case Xh0kO1ZCmA.MouseAction.MouseAction.S:
				{
					Xh0kO1ZCmA.MouseAction.ScrolDir scrolType = this.ScrolType;
					if (scrolType == Xh0kO1ZCmA.MouseAction.ScrolDir.up)
					{
						str = string.Concat("Scrol Up >> ", this.p.ToString());
						break;
					}
					else
					{
						if (scrolType != Xh0kO1ZCmA.MouseAction.ScrolDir.down)
						{
							throw new Exception("Bad Mouse Type");
						}
						str = string.Concat("Scrol Down >> ", this.p.ToString());
						break;
					}
				}
				default:
				{
					throw new Exception("Bad Mouse Type");
				}
			}
			return str;
		}

		public enum MouseAction
		{
			L,
			DL,
			R,
			DR,
			M,
			DM,
			S
		}

		public enum ScrolDir
		{
			up,
			down
		}
	}
}