package main.commandlineinterface.commands;
import main.commandlineinterface.CommandLineInterface;
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
    public boolean executeCommand( CommandProcessor commandProcessor, CommandLineInterface commandLineInterface) {
        BaseCommandValidator createCommandValidator = new CreateCommandValidator();
        this.setCommandLineInterface(commandLineInterface);

         if(!createCommandValidator.validate(this))
            return false;

        return commandProcessor.checkIfShapeIsWithinAnotherShape(this);
    }
}
