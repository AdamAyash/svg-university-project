package main.commandlineinterface.commands.shapes;

//класът представлява линия и нейните параметри
public class LineShape extends BasicShape{
    //-----Constants-----

    //-----Members-----

    //представлява x1
    private double firstXCoordinate;

    //представлява x2
    private double secondXCoordinate;

    //представлява y1
    private double firstYCoordinate;

    //представлява y2
    private double secondYCoordinate;

    //-----Constructor-----
    public LineShape(double firstXCoordinate, double secondXCoordinate, double firstYCoordinate, double secondYCoordinate) {
        this.firstXCoordinate = firstXCoordinate;
        this.secondXCoordinate = secondXCoordinate;
        this.firstYCoordinate = firstYCoordinate;
        this.secondYCoordinate = secondYCoordinate;
    }

    //-----Methods-----

    public double getFirstXCoordinate() {
        return firstXCoordinate;
    }

    public double getSecondXCoordinate() {
        return secondXCoordinate;
    }

    public double getFirstYCoordinate() {
        return firstYCoordinate;
    }

    public double getSecondYCoordinate() {
        return secondYCoordinate;
    }


    //-----Overrides----
}
