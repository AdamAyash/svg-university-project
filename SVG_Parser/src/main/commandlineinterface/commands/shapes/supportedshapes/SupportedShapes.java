package main.commandlineinterface.commands.shapes.supportedshapes;

//енумерация описващи поддържаните команди от приложението
public enum SupportedShapes {
    //-----Constants-----
    RECTANGLE("rectangle", "rect", 4),
    CIRCLE("circle","circle", 3),
    LINE("line", "line" ,4);
    //-----Members-----
    private final String supportedShape;

    private final String svgTag;
    private final int parametersCount;
    //-----Constructor-----
    private SupportedShapes(String supportedShape, String svgTag,  int parametersCount){
        this.supportedShape = supportedShape;
        this.parametersCount = parametersCount;
        this.svgTag = svgTag;
    }

    //-----Methods-----
    public String getSupportedShape() {
        return supportedShape;
    }

    public int getParametersCount() {
        return parametersCount;
    }

    public String getSvgTag() {
        return svgTag;
    }

    //-----Overrides----
}
