package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.TranslateCommandValidator;
import main.svgparser.CommandProcessor;

public class TranslateCommand extends BaseCommand {

    //-----Constants-----

    //-----Members-----
    private boolean translateAllShapes;

    private int verticalParameterValue;

    private int horizontalParameterValue;

    private int indexOfShapeTobeErased;

    //-----Constructor-----
    public TranslateCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
        translateAllShapes = false;
    }
    //-----Methods-----
    public boolean isTranslateAllShapes() {
        return translateAllShapes;
    }

    public int getVerticalParameterValue() {
        return verticalParameterValue;
    }

    public void setVerticalParameterValue(int verticalParameterValue) {
        this.verticalParameterValue = verticalParameterValue;
    }

    public void setTranslateAllShapes(boolean translateAllShapes) {
        this.translateAllShapes = translateAllShapes;
    }

    public int getHorizontalParameterValue() {
        return horizontalParameterValue;
    }

    public int getIndexOfShapeTobeErased() {
        return indexOfShapeTobeErased;
    }

    public void setHorizontalParameterValue(int horizontalParameterValue) {
        this.horizontalParameterValue = horizontalParameterValue;
    }

    public void setIndexOfShapeTobeErased(int indexOfShapeTobeErased) {
        this.indexOfShapeTobeErased = indexOfShapeTobeErased;
    }

    //-----Overrides----
    @Override
    public CommandResult executeCommand(CommandProcessor commandProcessor) {
        TranslateCommandValidator translateCommandValidator = new TranslateCommandValidator();

        CommandResult cResult = translateCommandValidator.validate(this);

        if(cResult != CommandResult.COMMAND_SUCCESSFUL)
            return cResult;

        return commandProcessor.translate(this);
    }


}
