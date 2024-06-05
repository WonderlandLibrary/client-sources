package net.minecraft.src;

public interface IAdminCommand
{
    void notifyAdmins(final ICommandSender p0, final int p1, final String p2, final Object... p3);
}
