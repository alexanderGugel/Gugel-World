import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TREE {

    private int age;
    private int x, y;

    public TREE(int xStart, int yStart) {
        age = 0;
        x = xStart;
        y = yStart;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public int getAge() {
        return age;
    }

    public void olden() {
        age = age + 1;
    }
    

}
