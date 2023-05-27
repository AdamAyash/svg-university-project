package main.commandlineinterface.commands;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commands.shapes.BasicShape;
import main.commandlineinterface.commandvalidators.BaseCommandValidator;
import main.commandlineinterface.commandvalidators.CreateCommandValidator;
import main.svgparser.CommandProcessor;

public class CreateCommand extends BaseCommand {

    //-----Constants-----

    //-----Members-----

    //референция към обекта на фигурата
    private BasicShape shape;

    //оказва дали фигурата е цветна или не
    private boolean isColorless;

    //-----Constructor-----
    public CreateCommand(String[] userInputCommand, int commandParameterCount, boolean isColorless) {
        super(userInputCommand, commandParameterCount);
        this.isColorless = isColorless;
    }

    //-----Methods-----
    public BasicShape getShape() {
        return shape;
    }

    public void setShape(BasicShape shape) {
        this.shape = shape;
    }

    public boolean isColorless() {
        return isColorless;
    }

    //-----Overrides----
    @Override
    public boolean executeCommand(CommandProcessor commandProcessor, CommandLineInterface commandLineInterface) {
        BaseCommandValidator createCommandValidator = new CreateCommandValidator();
        this.setCommandLineInterface(commandLineInterface);

        if(!createCommandValidator.validate(this))
            return false;

        return commandProcessor.createShape(this);
    }
}
