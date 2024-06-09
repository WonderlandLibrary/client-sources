using Microsoft.VisualBasic.Devices;
using System;
using System.Windows.Forms;
using Xh0kO1ZCmA.My;

namespace Xh0kO1ZCmA
{
	public class KeyAction : OneAction
	{
		public Keys MainKey;

		public string StartingValue;

		public KeyAction(Keys _MainKey, int _Delay) : base(DataSession.ActionType.Keyboard, _Delay)
		{
			this.MainKey = _MainKey;
			if (MyProject.Computer.Keyboard.CapsLock)
			{
				this.StartingValue = this.MainKey.ToString().ToUpper();
				return;
			}
			this.StartingValue = this.MainKey.ToString().ToLower();
		}

		public override string ToString()
		{
			return string.Concat("Key Down >> ", this.MainKey.ToString());
		}
	}
}