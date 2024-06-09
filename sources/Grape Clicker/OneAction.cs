using System;

namespace Xh0kO1ZCmA
{
	public abstract class OneAction
	{
		public DataSession.ActionType ActionState;

		public long Delay;

		public OneAction(DataSession.ActionType _ActionState, int _Delay)
		{
			this.ActionState = _ActionState;
			this.Delay = (long)_Delay;
		}

		public override abstract string ToString();
	}
}