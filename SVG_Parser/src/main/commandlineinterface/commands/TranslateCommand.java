package main.commandlineinterface.commands;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.TranslateCommandValidator;
import main.svgparser.CommandProcessor;

public class TranslateCommand extends BaseCommand {

    //-----Constants-----

    //-----Members-----
    private boolean translateAllShapes;

    private double verticalParameterValue;

    private double horizontalParameterValue;

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

    public double getVerticalParameterValue() {
        return verticalParameterValue;
    }

    public void setVerticalParameterValue(double verticalParameterValue) {
        this.verticalParameterValue = verticalParameterValue;
    }

    public void setTranslateAllShapes(boolean translateAllShapes) {
        this.translateAllShapes = translateAllShapes;
    }

    public double getHorizontalParameterValue() {
        return horizontalParameterValue;
    }

    public int getIndexOfShapeTobeErased() {
        return indexOfShapeTobeErased;
    }

    public void setHorizontalParameterValue(double horizontalParameterValue) {
        this.horizontalParameterValue = horizontalParameterValue;
    }

    public void setIndexOfShapeTobeErased(int indexOfShapeTobeErased) {
        this.indexOfShapeTobeErased = indexOfShapeTobeErased;
    }

    //-----Overrides----
    @Override
    public boolean executeCommand(CommandProcessor commandProcessor, CommandLineInterface commandLineInterface) {
        TranslateCommandValidator translateCommandValidator = new TranslateCommandValidator();
        this.setCommandLineInterface(commandLineInterface);

        if(!translateCommandValidator.validate(this))
                return false;

        return commandProcessor.translate(this);
    }


}
