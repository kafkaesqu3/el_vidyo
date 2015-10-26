import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class MovementTest extends Scene {

    private World w;


    public MovementTest()
    {
        w = new World(10, 5);
    }

    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        w.setDrawEdges(true);
        w.draw();
        return true;
    }


}
