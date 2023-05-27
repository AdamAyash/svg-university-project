package main.commandlineinterface.commandvalidators;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.OpenCommand;
import main.commandlineinterface.commands.base.Command;

//клас за валидация на командатат за отваряне на файл, проверява дали разширението на файла е .svg или .xml
public class OpenCommandValidator extends BaseCommandValidator{

    //-----Constants-----

    //служи за разделяна на името на файла и разширението
    private final String DOT_DELIMITER = "\\.";

    private final String SVG_EXTENSION = "svg";

    private final String XML_EXTENSION = "xml";

    //указва на кой индекс е разширението на файла в масива
    private final int FILE_EXTENSION_INDEX = 1;


    //-----Members-----

    //-----Constructor-----

    //-----Methods-----

    //-----Overrides----

    @Override
    public boolean validate(Command command) {
        if(!super.validate(command))
            return false;

        OpenCommand openCommand = (OpenCommand)command;

        String fileExtension  = openCommand.getUserInputCommand()[FILE_EXTENSION_INDEX].split("[.]")[FILE_EXTENSION_INDEX];

     if(!(fileExtension.equals(SVG_EXTENSION) || fileExtension.equals(XML_EXTENSION)))
         return false;

        return true;

    }
}
