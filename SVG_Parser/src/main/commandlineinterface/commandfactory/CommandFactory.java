package main.commandlineinterface.commandfactory;
import main.commandlineinterface.commands.*;
import main.commandlineinterface.commands.base.Command;
import main.commandlineinterface.commands.EraseCommand;
import main.commandlineinterface.commands.supportedcommand.SupportedCommands;
import main.exceptions.CommandNotImplementedException;

//клас служещ за създаването на различните поддържани команди
public class CommandFactory {
    //-----Constants-----
    private final String COMMAND_NOT_SUPPORTED_MESSAGE = " is not recognized as internal or external command.";

    //-----Members-----

    //-----Constructor-----

    //-----Methods-----

    public Command createCommand(String[] command) throws CommandNotImplementedException, ArrayIndexOutOfBoundsException{
        String currentCommandKeyword = command[0];

        if(currentCommandKeyword.equals(SupportedCommands.EXIT.getCommand()))
            return new ExitCommand(command, SupportedCommands.EXIT.getCommandParameterCount());

        if(currentCommandKeyword.equals(SupportedCommands.OPEN.getCommand()))
            return new OpenCommand(command, SupportedCommands.OPEN.getCommandParameterCount());

        if(currentCommandKeyword.equals(SupportedCommands.SAVE.getCommand()))
            return new SaveCommand(command, SupportedCommands.SAVE.getCommandParameterCount());

        if(currentCommandKeyword.equals(SupportedCommands.CREATE.getCommand()))
            return new CreateCommand(command, SupportedCommands.CREATE.getCommandParameterCount(), false);

        if(currentCommandKeyword.equals(SupportedCommands.CLOSE.getCommand()))
            return new CloseCommand(command, SupportedCommands.CLOSE.getCommandParameterCount());

        if(currentCommandKeyword.equals(SupportedCommands.PRINT.getCommand()))
            return new PrintCommand(command, SupportedCommands.PRINT.getCommandParameterCount());

        if(currentCommandKeyword.equals(SupportedCommands.ERASE.getCommand()))
            return new EraseCommand(command, SupportedCommands.ERASE.getCommandParameterCount());

        if(currentCommandKeyword.equals(SupportedCommands.TRANSLATE.getCommand()))
                return new TranslateCommand(command, SupportedCommands.TRANSLATE.getCommandParameterCount());

        if(currentCommandKeyword.equals(SupportedCommands.SAVE_AS.getCommand()))
            return new SaveAsCommand(command, SupportedCommands.SAVE_AS.getCommandParameterCount());

        if(currentCommandKeyword.equals(SupportedCommands.WITHIN.getCommand()))
            return new WithinCommand(command, SupportedCommands.WITHIN.getCommandParameterCount());

        if(currentCommandKeyword.equals(SupportedCommands.HELP.getCommand()))
            return new HelpCommand(command, SupportedCommands.HELP.getCommandParameterCount());


        throw new CommandNotImplementedException("\"" + currentCommandKeyword + "\"" + COMMAND_NOT_SUPPORTED_MESSAGE);
    }


    //-----Overrides----
}
