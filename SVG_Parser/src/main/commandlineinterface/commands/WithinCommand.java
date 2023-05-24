package main.commandlineinterface.commands;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commandvalidators.BaseCommandValidator;
import main.commandlineinterface.commandvalidators.CreateCommandValidator;
import main.svgparser.CommandProcessor;


//команда проверяваща, дали дадена фигура не се съдържа напълно в друга фигура
public class WithinCommand extends CreateCommand {

    //-----Constants-----

    //-----Members-----

    //-----Constructor-----
    public WithinCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount, true);
    }

    //-----Methods-----

    //-----Overrides----

    @Override
    public CommandResult executeCommand( CommandProcessor commandProcessor) {
        BaseCommandValidator createCommandValidator = new CreateCommandValidator();
        CommandResult cResult = createCommandValidator.validate(this);

        if(cResult != CommandResult.COMMAND_SUCCESSFUL)
            return cResult;

        return commandProcessor.checkIfShapeIsWithinAnotherShape(this);
    }
}
