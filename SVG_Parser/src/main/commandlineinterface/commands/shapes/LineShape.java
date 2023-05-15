package main.commandlineinterface.commands.shapes;

//класът представлява линия и нейните параметри
public class LineShape extends BasicShape{
    //-----Constants-----

    //-----Members-----

    //представлява x1
    private int firstXCoordinate;

    //представлява x2
    private int secondXCoordinate;

    //представлява y1
    private int firstYCoordinate;

    //представлява y2
    private int secondYCoordinate;

    //-----Constructor-----
    public LineShape(int firstXCoordinate, int secondXCoordinate, int firstYCoordinate, int secondYCoordinate) {
        this.firstXCoordinate = firstXCoordinate;
        this.secondXCoordinate = secondXCoordinate;
        this.firstYCoordinate = firstYCoordinate;
        this.secondYCoordinate = secondYCoordinate;
    }

    //-----Methods-----

    public int getFirstXCoordinate() {
        return firstXCoordinate;
    }

    public int getSecondXCoordinate() {
        return secondXCoordinate;
    }

    public int getFirstYCoordinate() {
        return firstYCoordinate;
    }

    public int getSecondYCoordinate() {
        return secondYCoordinate;
    }


    //-----Overrides----
}
