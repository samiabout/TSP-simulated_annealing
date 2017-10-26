import java.util.ArrayList;
import java.util.List;

/**
 * Created by sami- on 23/09/2017.
 *
 * This class describes one node of the graph
 * by its name and its position (x,y)
 */
public class Node {
    private String name;
    private int x;
    private int y;


    public Node(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Node(Node nodebis) {//create a clone of the node
        this.name = nodebis.name;
        this.x = nodebis.getX();
        this.y = nodebis.getY();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
