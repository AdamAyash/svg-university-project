package main.commandlineinterface.commandvalidators;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.TranslateCommand;
import main.commandlineinterface.commands.base.Command;
import main.commandlineinterface.commands.supportedcommand.SupportedCommands;
import main.errorlogger.ErrorLogger;


//класът служи за валидация на командатат за транслация
public class TranslateCommandValidator extends BaseCommandValidator{
    //-----Constants-----

    //-----Members-----

    //-----Constructor-----

    //-----Methods-----

    //валидира вертикалният аргумент
    private boolean validateVerticalParameter(TranslateCommand translateCommand) {
        String verticalParameter;
        boolean result = true;

        try {
            if (translateCommand.isTranslateAllShapes()) {
                verticalParameter = translateCommand.getUserInputCommand()[1];
            } else {
                verticalParameter = translateCommand.getUserInputCommand()[2];
            }
            String verticalKeyword = verticalParameter.replaceAll("-?\\.?\\d+", "");

            if(!verticalKeyword.equals("vertical="))
                result = false;

            String verticalParameterValue =  verticalParameter.replaceAll("[a-zA-Z]+\\=","");
            translateCommand.setVerticalParameterValue( Double.parseDouble(verticalParameterValue));
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
            result = false;
        }

        return result;
    }


    //валидира хоризонталния аргумент
    private boolean validateHorizontalParameter(TranslateCommand translateCommand) {
        String horizontalParameter;
        boolean result = true;

        try {
            if (translateCommand.isTranslateAllShapes()) {
                horizontalParameter = translateCommand.getUserInputCommand()[2];
            } else {
                horizontalParameter = translateCommand.getUserInputCommand()[3];
            }
            String verticalKeyword = horizontalParameter.replaceAll("-?\\.?\\d+", "");

            if(!verticalKeyword.equals("horizontal="))
                result = false;

            String verticalParameterValue =  horizontalParameter.replaceAll("[a-zA-Z]+\\=","");
            translateCommand.setHorizontalParameterValue(Double.parseDouble(verticalParameterValue));
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
            result = false;
        }

        return result;
    }

    //-----Overrides----

    @Override
    public boolean validate(Command command) {
        try {
            TranslateCommand translateCommand = (TranslateCommand)command;

            if(translateCommand.getUserInputCommand().length == SupportedCommands.TRANSLATE.getCommandParameterCount())
                translateCommand.setTranslateAllShapes(true);

           else if(translateCommand.getUserInputCommand().length == SupportedCommands.TRANSLATE.getCommandParameterCount() + 1)
               translateCommand.setIndexOfShapeTobeErased(Integer.parseInt(translateCommand.getUserInputCommand()[1]));

           else
                return false;

            if(!validateVerticalParameter(translateCommand) || !validateHorizontalParameter(translateCommand))
                return false;

        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
            ErrorLogger.logError(e.toString());
            return false;
        }

        return true;
    }
}
