import java.util.List;
import java.util.LinkedList;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import java.awt.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import java.io.IOException;

public class Menu extends Scene {


    // a menu item: label and associated Scene to jump to
    private static class Item
    {
        public String label;
        public Scene  scene;

        public Item(String label, Scene s)
        {
            this.label = label;
            this.scene = s;
        }

    }

    public static final int DO_EXIT=0;

    private static class Special extends Item
    {
        public int tag;

        public Special(String label, int tag)
        {
            super(label, null);
            this.tag = tag;
        }
    }


    // these menu items
    private List<Item> items;

    // currently selected items
    private int currItem;


    public Menu()
    {
        items = new LinkedList<>();


    }

    // reset menu
    public void clear()
    {
        items.clear();
    }

    public void addItem(String label, Scene s)
    {
        items.add(new Item(label,s));
    }
    
    public void addSpecial(String label, int tag)
    {
        items.add(new Special(label, tag));
    }


    public Scene nextScene()
    {
        return items.get(currItem).scene;
    }


    public boolean drawFrame(float delta)
    {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // process keyboard input

        while (Keyboard.next())
        {
            if (Keyboard.getEventKeyState()) // key was pressed
            {
                switch (Keyboard.getEventKey())
                {
                 case Keyboard.KEY_DOWN:
                     currItem = (currItem + 1) % items.size();


                     break;

                 case Keyboard.KEY_UP:

                     currItem--;

                     if (currItem < 0)
                     {
                         currItem += items.size(); // go to end
                     }


                     break;

                 case Keyboard.KEY_RETURN:

                     // TODO: play sound
                     Item item = items.get(currItem);

                     if (item instanceof Special)
                     {
                         switch (((Special)item).tag)
                         {
                          case DO_EXIT: exit();
                              break;
                         }

                     }

                     return false;

                }
            }
        }



        // draw menu, highlighting currItem

        float spacing = Display.getHeight()/(items.size() + 4);
        float offset = 2*spacing;

        TrueTypeFont menuFont = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), true);

        for (int i=0; i<items.size(); i++)
        {
            if (i == currItem)
            {
                menuFont.drawString(Display.getWidth()/2, offset, items.get(i).label, Color.yellow);
            }
            else
            {
                menuFont.drawString(Display.getWidth()/2, offset, items.get(i).label);
            }

            offset += spacing;
        }
        // font binds a texture, so let's turn it off..
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);


        return true;
    }


}
