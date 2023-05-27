package main.commandlineinterface.commands.shapes;

//калсът описва кръг и параметрите му
public class CircleShape extends BasicShape {

    //-----Constants-----

    //-----Members-----

    //x кординатата на центъра на кръга
    private double centerXCoordinate;
    //y кординатата на центъра на кръга
    private double centerYCoordinate;
    //радиус на кръга
    private double radius;

    //-----Constructor-----

    public CircleShape(double centerXCoordinate, double centerYCoordinate, double radius) {
        this.centerXCoordinate = centerXCoordinate;
        this.centerYCoordinate = centerYCoordinate;
        this.radius = radius;
    }

    //-----Methods-----

    public double getCenterXCoordinate() {
        return centerXCoordinate;
    }

    public double getCenterYCoordinate() {
        return centerYCoordinate;
    }

    public double getRadius() {
        return radius;
    }

    //-----Overrides----
}
