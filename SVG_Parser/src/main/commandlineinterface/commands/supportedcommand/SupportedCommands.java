package main.commandlineinterface.commands.supportedcommand;


//енумерация с всики поддървани комнаид от приложението
public enum SupportedCommands {
    //-----Constants-----
    EXIT("exit", 0),
    OPEN("open", 1),
    CLOSE("close", 0),
    SAVE("save", 0),
    PRINT("print", 0),
    ERASE("erase", 1),
    TRANSLATE("translate", 3),
    WITHIN("within", 0),
    CREATE("create", 0),
    SAVE_AS("saveas", 1),
    HELP("help", 0);

    //описва ключовата дума на командата
    private final String COMMAND;
    private final int COMMAND_PARAMETER_COUNT;

    //-----Members-----

    //-----Constructor-----
    private SupportedCommands(String command, int commandParameterCount) {
        this.COMMAND = command;
        this.COMMAND_PARAMETER_COUNT = commandParameterCount;
    }

    //-----Methods-----
    public String getCommand() {
        return COMMAND;
    }

    public int getCommandParameterCount() {
        return COMMAND_PARAMETER_COUNT;
    }
}
