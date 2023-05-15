package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commands.shapes.BasicShape;
import main.commandlineinterface.commandvalidators.BaseCommandValidator;
import main.commandlineinterface.commandvalidators.CreateCommandValidator;

public class CreateCommand extends BaseCommand {

    //-----Constants-----

    //-----Members-----

    //референция към обекта на фигурата
    private BasicShape shape;

    //цветът
    private String color;

    //-----Constructor-----
    public CreateCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }

    //-----Methods-----
    public BasicShape getShape() {
        return shape;
    }

    public void setShape(BasicShape shape) {
        this.shape = shape;
    }

    //-----Overrides----
    @Override
    public CommandResult executeCommand() {
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;
        BaseCommandValidator createCommandValidator = new CreateCommandValidator();

        cResult = createCommandValidator.validate(this);

        return cResult;
    }
}
