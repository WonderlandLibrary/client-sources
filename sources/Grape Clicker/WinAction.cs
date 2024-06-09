using System;

namespace Xh0kO1ZCmA
{
	public class WinAction : OneAction
	{
		public FullHook.WinState WinActionType;

		public string WindowsName;

		public string ProName;

		public WinAction(FullHook.WinState _WinActionType, string _WindowsName, string _ProName, int _Delay) : base(DataSession.ActionType.Win, _Delay)
		{
			this.WindowsName = _WindowsName;
			this.ProName = _ProName;
			this.WinActionType = _WinActionType;
		}

		public override string ToString()
		{
			string str;
			switch (this.WinActionType)
			{
				case FullHook.WinState.O:
				{
					str = string.Concat("Win Open >> ", this.ProName, " -> ", this.WindowsName);
					break;
				}
				case FullHook.WinState.C:
				{
					str = string.Concat("Win Close >> ", this.ProName, " -> ", this.WindowsName);
					break;
				}
				case FullHook.WinState.M:
				{
					str = string.Concat("Win Modify >> ", this.ProName, " -> ", this.WindowsName);
					break;
				}
				default:
				{
					throw new Exception("Bad Window Action Type");
				}
			}
			return str;
		}
	}
}