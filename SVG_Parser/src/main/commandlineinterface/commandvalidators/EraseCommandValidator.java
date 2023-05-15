package main.commandlineinterface.commandvalidators;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.Command;
import main.commandlineinterface.commands.EraseCommand;
import main.errorlogger.ErrorLogger;

//клас служещ за жалидация на командатата за изтриване на фигура
public class EraseCommandValidator extends BaseCommandValidator{

    //-----Constants-----

    //-----Members-----

    //-----Constructor-----

    //-----Methods-----

    //-----Overrides----
    @Override
    public CommandResult validate(Command command) {
        CommandResult cResult = super.validate(command);
        EraseCommand eraseCommand = (EraseCommand)command;

        if(cResult != CommandResult.COMMAND_SUCCESSFUL) {
            return cResult;
        }

        try{
            int eraseIndex = Integer.parseInt(eraseCommand.getUserInputCommand()[1]) - 1;
            eraseCommand.setEraseShapeIndex(eraseIndex);

        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
            ErrorLogger.logError(e.toString());
            cResult = CommandResult.COMMAND_FAILED;
        }



        return cResult;
    }
}
