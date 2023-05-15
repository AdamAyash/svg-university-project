package main.commandlineinterface.commandvalidators;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.Command;

//оказва основното поведение за валидирането на съответните му имплементации
public interface CommandValidator {
    CommandResult validate(Command command);
}
