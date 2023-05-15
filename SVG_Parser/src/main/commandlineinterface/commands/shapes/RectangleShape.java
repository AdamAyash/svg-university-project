package main.commandlineinterface.commands.shapes;

public class RectangleShape extends BasicShape{
    //-----Constants-----

    //-----Members-----
    private int xCoordinate;
    private int yCoordinate;
    private int width;
    private int height;

    //-----Constructor-----
    public RectangleShape(int xCoordinate, int yCoordinate, int width, int height) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.width = width;
        this.height = height;
    }
    //-----Methods-----
    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //-----Overrides----

}
