package main.commandlineinterface.commands.shapes;

public class ShapeUtilities {
    //-----Constants-----

    //-----Members-----

    //-----Constructor-----

    //-----Methods-----
  public static  boolean isRectangleWithinRectangle(double firstRectangleXCoordinate,double firstRectangleYCoordinate, double firstRectangleWidth, double firstRectangleHeight,
                                                    double secondRectangleXCoordinate,double secondRectangleYCoordinate, double secondRectangleWidth, double secondRectangleHeight) {

    return firstRectangleXCoordinate >= secondRectangleXCoordinate && (firstRectangleXCoordinate + firstRectangleWidth) <= secondRectangleXCoordinate + secondRectangleWidth
            && firstRectangleYCoordinate >= secondRectangleYCoordinate && (firstRectangleYCoordinate + firstRectangleHeight) <= firstRectangleYCoordinate + secondRectangleHeight;
  }

 public static boolean isLineWithinRectangle(double rectangleX1Coordinate, double rectangleY1Coordinate, double rectangleWidth,
                                             double rectangleHeight, double lineX1Coordinate, double lineX2Coordinate, double lineY1Coordinate,double lineY2Coordinate){
    return lineX1Coordinate >= rectangleX1Coordinate && lineX1Coordinate <= rectangleX1Coordinate + rectangleWidth &&
            lineX2Coordinate >= rectangleX1Coordinate && lineX2Coordinate <= rectangleX1Coordinate + rectangleWidth &&
            lineY1Coordinate >= rectangleY1Coordinate && lineY1Coordinate <= rectangleY1Coordinate + rectangleHeight &&
            lineY2Coordinate >= rectangleY1Coordinate && lineY2Coordinate <= rectangleY1Coordinate + rectangleHeight;
 }

    public static boolean isRectangleWithinCircle(double circleCenterX, double circleCenterY, double circleRadius,
                                              double rectangleX, double rectangleY, double rectangleWidth, double rectangleHeight) {
        double rectangleX2 = rectangleX + rectangleWidth;
        double rectangleY2 = rectangleY + rectangleHeight;

        double rectTopLeftDistance = distance(circleCenterX, circleCenterY, rectangleX, rectangleY);
        double rectTopRightDistance = distance(circleCenterX, circleCenterY, rectangleX2, rectangleY);
        double rectBottomLeftDistance = distance(circleCenterX, circleCenterY, rectangleX, rectangleY2);
        double rectBottomRightDistance = distance(circleCenterX, circleCenterY, rectangleX2, rectangleY2);

        return rectTopLeftDistance <= circleRadius && rectTopRightDistance <= circleRadius &&
                rectBottomLeftDistance <= circleRadius && rectBottomRightDistance <= circleRadius;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    //-----Overrides----
}
