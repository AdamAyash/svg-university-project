package main.commandlineinterface.commandresult;

//енумерация описваща резулатата от различните команди
public enum CommandResult {

    //-----Constants-----
    COMMAND_FAILED("The execution of the command failed"),
    ERROR_LOGGER_FAILED("Error logger failed"),
    EXIT_PROGRAM("Exiting the program..."),
    FILE_SUCCESSFULLY_OPENED("Successfully opened "),
    FILE_NOT_OPENED("There is currently no file opened"),
    FILE_ALREADY_OPENED("There is already a file opened, please close the existing one "),
    FILE_CLOSED_SUCCESSFULLY("Successfully closed "),
    FILE_SAVED_SUCCESSFULLY("Successfully saved the changes to "),
    ERASE_SHAPE_NOT_FOUND("There is no figure number "),
    SHAPE_SUCCESSFULLY_CREATED("Successfully created "),
    SUCCESSFULLY_TRANSLATED_ALL_FIGURES("Translated all figures "),
    SUCCESSFULLY_TRANSLATED_SHAPE("Successfully translated ");
    //-----Members-----
    private final String commandResultMessage;
    //-----Constructor-----
    private CommandResult(String commandResultMessage){
        this.commandResultMessage = commandResultMessage;
    }
    private CommandResult() {
        this.commandResultMessage = null;
    }

    //-----Methods-----
    public String getCommandResultMessage() {
        return commandResultMessage;
    }

    //-----Overrides----

}
