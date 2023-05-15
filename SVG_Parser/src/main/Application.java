package main;

import main.commandlineinterface.commands.supportedcommand.SupportedCommands;
import main.svgparser.SVGParser;

public class Application {

    public static void main(String[] args) {
       SVGParser svgParser = new SVGParser();
        svgParser.start();
    }
}
