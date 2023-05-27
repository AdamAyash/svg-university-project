package main.commandlineinterface.commands.shapes;

public class RectangleShape extends BasicShape{
    //-----Constants-----

    //-----Members-----
    private double xCoordinate;
    private double yCoordinate;
    private double width;
    private double height;

    //-----Constructor-----
    public RectangleShape(double xCoordinate, double yCoordinate, double width, double height) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.width = width;
        this.height = height;
    }
    //-----Methods-----
    public double getXCoordinate() {
        return xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    //-----Overrides----

}
