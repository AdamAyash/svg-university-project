package main.commandlineinterface.commandvalidators;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.CreateCommand;
import main.commandlineinterface.commands.base.Command;
import main.commandlineinterface.commands.shapes.BasicShape;
import main.commandlineinterface.commands.shapes.CircleShape;
import main.commandlineinterface.commands.shapes.LineShape;
import main.commandlineinterface.commands.shapes.RectangleShape;
import main.commandlineinterface.commands.shapes.supportedcolors.SupportedColors;
import main.commandlineinterface.commands.shapes.supportedshapes.SupportedShapes;
import main.errorlogger.ErrorLogger;
import main.exceptions.ShapeNotSupportedException;

//клас за валидация на команди за създаване на фигури
public class CreateCommandValidator extends BaseCommandValidator{

    //-----Constants-----

    //константа оказваща броя на ненувните параметри при валидацията на фигурите
    private final int UNNECESSARY_ARGUMENTS_COUNT = 3;


    private final String SHAPE_NOT_SUPPORTED_MESSAGE = "The shape you entered is not supported";

    private final int SHAPE_INDEX = 1;

    //-----Members-----

    //-----Constructor-----

    public CreateCommandValidator() {
    }


    //-----Methods-----

    //проверява дали фигурата описана в командата се поддържа
    private SupportedShapes isShapeSupported(CreateCommand command){
        SupportedShapes currentShape = null;

        for (SupportedShapes supportedShape:
                SupportedShapes.values()) {
            if(supportedShape.getSupportedShape().equals(command.getUserInputCommand()[SHAPE_INDEX])){
                  currentShape = supportedShape;
                  break;
            }
        }

        return currentShape;
    }

    //проверява дали въведеният цвят се поддържа
    private SupportedColors isColorSupported(CreateCommand command){
        SupportedColors currentColor = null;

        for (SupportedColors color:
                SupportedColors.values()){
            if(color.getColor().equals(command.getUserInputCommand()[command.getUserInputCommand().length - 1])){
                currentColor = color;
                break;
            }
        }
        return currentColor;
    }

    //проверява дали параметрите подадени в командата като вход съвпадат с тези от енумерацията на съответната фигура
    private boolean checkShapeParameterCount(CreateCommand command, SupportedShapes currentShape){

        if((command.getUserInputCommand().length - UNNECESSARY_ARGUMENTS_COUNT + 1) == currentShape.getParametersCount() && command.isColorless())
            return true;

        if((command.getUserInputCommand().length - UNNECESSARY_ARGUMENTS_COUNT) == currentShape.getParametersCount())
            return true;

        return false;
    }

    //задава параметрите подадени като вход в съответноте променвили
    private BasicShape checkAndAssignParameters(CreateCommand command, SupportedShapes currentShape) throws ArrayIndexOutOfBoundsException, NullPointerException,
    NumberFormatException, ShapeNotSupportedException{
        switch (currentShape){
            case RECTANGLE:
                double xCoordinate = Double.parseDouble(command.getUserInputCommand()[2]);
                double yCoordinate = Double.parseDouble(command.getUserInputCommand()[3]);
                double width = Double.parseDouble(command.getUserInputCommand()[4]);
                double height = Double.parseDouble(command.getUserInputCommand()[5]);
                return new RectangleShape(xCoordinate, yCoordinate, width, height);
            case CIRCLE:
                double centerXCoordinate = Double.parseDouble(command.getUserInputCommand()[2]);
                double centerYCoordinate = Double.parseDouble(command.getUserInputCommand()[3]);
                double radius = Double.parseDouble(command.getUserInputCommand()[4]);

                if(radius <= 0)
                    throw new NumberFormatException();

                return new CircleShape(centerXCoordinate, centerYCoordinate, radius);
            case LINE:
                double firstXCoordinate = Double.parseDouble(command.getUserInputCommand()[2]);
                double secondXCoordinate = Double.parseDouble(command.getUserInputCommand()[3]);
                double firstYCoordinate = Double.parseDouble(command.getUserInputCommand()[4]);
                double secondYCoordinate = Double.parseDouble(command.getUserInputCommand()[5]);
                return new LineShape(firstXCoordinate, secondXCoordinate, firstYCoordinate, secondYCoordinate);

        }
        throw new ShapeNotSupportedException(SHAPE_NOT_SUPPORTED_MESSAGE);
    }

    //-----Overrides----
    @Override
    public boolean validate(Command command) throws ArrayIndexOutOfBoundsException, NullPointerException,
            NumberFormatException{
        try {
            CreateCommand createCommand = (CreateCommand) command;

            SupportedShapes shape = isShapeSupported(createCommand);
            SupportedColors color = isColorSupported(createCommand);

                if (checkShapeParameterCount(createCommand, shape) && shape != null && (color != null || createCommand.isColorless())) {
                    createCommand.setShape(checkAndAssignParameters(createCommand, shape));

                    if(!createCommand.isColorless())
                    createCommand.getShape().setColor(color.getColor(), createCommand.isColorless());
                }
        }
        catch (ArrayIndexOutOfBoundsException| NullPointerException|
                NumberFormatException |  ShapeNotSupportedException e){
            ErrorLogger.logError(e.toString());
            return  false;
        }

        return true;

    }
}
