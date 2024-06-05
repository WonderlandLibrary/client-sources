package ClassSub;

public final class Class18
{
    
    
    public static void checkGLContext() {
        try {
            Class197.get().glGetError();
        }
        catch (NullPointerException ex) {
            throw new RuntimeException("OpenGL based resources (images, fonts, sprites etc) must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
        }
    }
}
