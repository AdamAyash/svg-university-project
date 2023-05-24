package main.commandlineinterface.commands.shapes;

//калсът описва кръг и параметрите му
public class CircleShape extends BasicShape {

    //-----Constants-----

    //-----Members-----

    //x кординатата на центъра на кръга
    private int centerXCoordinate;
    //y кординатата на центъра на кръга
    private int centerYCoordinate;
    //радиус на кръга
    private int radius;

    //-----Constructor-----

    public CircleShape(int centerXCoordinate, int centerYCoordinate, int radius) {
        this.centerXCoordinate = centerXCoordinate;
        this.centerYCoordinate = centerYCoordinate;
        this.radius = radius;
    }

    //-----Methods-----

    public int getCenterXCoordinate() {
        return centerXCoordinate;
    }

    public int getCenterYCoordinate() {
        return centerYCoordinate;
    }

    public int getRadius() {
        return radius;
    }

    //-----Overrides----
}
