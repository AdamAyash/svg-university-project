package main.commandlineinterface.inputmanager;

import java.util.Scanner;

//класът служи за четене на вход  от потребителя и разделяне на командите в масив
public class InputManager {

    //-----Constants-----
    //константа описваща по какъв символ ще се отделят параметрите на въведената команда
    private final String SPLIT_DELIMITER = " ";

    //константа от тип Scanner, служи за четене на вход от потребителя
    private final Scanner USER_INPUT_SCANNER;

    //-----Members-----
    //описва команда и нейните параметри
    private String[] command;

    //-----Constructor-----
    public InputManager() {
        USER_INPUT_SCANNER = new Scanner(System.in);
    }

    //-----Methods-----
    public String[] getCommand() {
        return command;
    }

    //чете команда от потребителя и разделя съответните параметри, като ги запазва в масив - command
    public void readUserInput() {
        command = USER_INPUT_SCANNER.nextLine().split(SPLIT_DELIMITER);
    }

    //-----Overrides-----
}
