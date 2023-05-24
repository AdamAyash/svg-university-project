package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
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
    public CommandResult executeCommand(CommandProcessor commandProcessor) {
        BaseCommandValidator createCommandValidator = new CreateCommandValidator();

        CommandResult cResult = createCommandValidator.validate(this);

        if(cResult != CommandResult.COMMAND_SUCCESSFUL)
            return cResult;

        return commandProcessor.createShape(this);
    }
}
