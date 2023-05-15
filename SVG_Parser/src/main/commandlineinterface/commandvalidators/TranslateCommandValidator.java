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
            String verticalKeyword = verticalParameter.replaceAll("\\d+", "");

            if(!verticalKeyword.equals("vertical="))
                result = false;

            String verticalParameterValue =  verticalParameter.replaceAll("[a-zA-Z]+\\=","");
            translateCommand.setVerticalParameterValue( Integer.parseInt(verticalParameterValue));
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
            String verticalKeyword = horizontalParameter.replaceAll("\\d+", "");

            if(!verticalKeyword.equals("horizontal="))
                result = false;

            String verticalParameterValue =  horizontalParameter.replaceAll("[a-zA-Z]+\\=","");
            translateCommand.setHorizontalParameterValue( Integer.parseInt(verticalParameterValue));
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
            result = false;
        }

        return result;
    }

    //-----Overrides----

    @Override
    public CommandResult validate(Command command) {
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;
        try {
            TranslateCommand translateCommand = (TranslateCommand)command;

            if(translateCommand.getUserInputCommand().length == SupportedCommands.TRANSLATE.getCommandParameterCount())
                translateCommand.setTranslateAllShapes(true);

           else if(translateCommand.getUserInputCommand().length == SupportedCommands.TRANSLATE.getCommandParameterCount() + 1)
               translateCommand.setIndexOfShapeTobeErased(Integer.parseInt(translateCommand.getUserInputCommand()[1]));

           else
                cResult = CommandResult.COMMAND_FAILED;

            if(!validateVerticalParameter(translateCommand) || !validateHorizontalParameter(translateCommand))
                cResult = CommandResult.COMMAND_FAILED;

        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
            cResult = CommandResult.COMMAND_FAILED;
            ErrorLogger.logError(e.toString());
        }

        return cResult;
    }
}
