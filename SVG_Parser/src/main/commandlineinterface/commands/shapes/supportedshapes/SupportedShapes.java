package main.commandlineinterface.commands.shapes.supportedshapes;

//енумерация описващи поддържаните команди от приложението
public enum SupportedShapes {
    //-----Constants-----
    RECTANGLE("rectangle", 4),
    CIRCLE("circle", 3),
    LINE("line", 4);
    //-----Members-----
    private final String supportedShape;
    private final int parametersCount;
    //-----Constructor-----
    private SupportedShapes(String supportedShape, int parametersCount){
        this.supportedShape = supportedShape;
        this.parametersCount = parametersCount;
    }

    //-----Methods-----
    public String getSupportedShape() {
        return supportedShape;
    }

    public int getParametersCount() {
        return parametersCount;
    }

    //-----Overrides----
}
