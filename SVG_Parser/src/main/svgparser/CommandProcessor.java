package main.svgparser;
import main.commandlineinterface.PrintWriter.PrintWriter;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.*;
import main.commandlineinterface.commands.base.Command;
import main.commandlineinterface.commands.shapes.*;
import main.commandlineinterface.commands.shapes.supportedshapes.SupportedShapes;
import main.commandlineinterface.commands.EraseCommand;
import main.errorlogger.ErrorLogger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//класът служи за обработка на генерираните команди
public class CommandProcessor {

    //-----Constants-----

    //оказва името на главния таг в svg файла
    private final String ROOT_ELEMENT = "svg";

    //-----Members-----
    //оказва дали има отворен файл в момента
    private boolean isFileOpened;
    private List<Element> shapesList;

    //представкява буфер където ще се зареждат данните от svg/xml файловете
    private Document currentDocument;

    //предтавлява файл или директория
    private  File currentFile;

    //-----Constructor-----
   public CommandProcessor(){
       shapesList = new ArrayList<>();
       this.isFileOpened = false;
   }

    //-----Methods-----

    //създава или отваря вече съществуващ файл
    public CommandResult openFile(final Command currentCommand){
        CommandResult cResult = CommandResult.FILE_SUCCESSFULLY_OPENED;
       if(!isFileOpened) {
           OpenCommand openCommand = (OpenCommand) currentCommand;

           currentFile = new File(((OpenCommand) currentCommand).getFilePath());

           DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder documentBuilder;
           try {
               documentBuilder = documentBuilderFactory.newDocumentBuilder();
               if (currentFile.exists()) {
                   currentDocument = documentBuilder.parse(openCommand.getFilePath());
                   if(!loadAllShapes())
                       cResult = CommandResult.COMMAND_FAILED;
               } else {
                   currentDocument = documentBuilder.newDocument();
                   Element svgRootElement = currentDocument.createElement(ROOT_ELEMENT);
                   currentDocument.appendChild(svgRootElement);
               }
           } catch (ParserConfigurationException | IOException | SAXException |
                    IllegalArgumentException | NullPointerException e) {

               ErrorLogger.logError(e.toString());
               return cResult;
           }
           isFileOpened = true;
       }else {
           cResult = CommandResult.FILE_ALREADY_OPENED;
       }
        return cResult;
    }

    public CommandResult checkIfShapeIsWithinAnotherShape(final Command command){
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;
        if(!isFileOpened)
            return cResult = CommandResult.FILE_NOT_OPENED;

        WithinCommand withinCommand = (WithinCommand)command;

        BasicShape shape = withinCommand.getShape();

        for(int i = 0; i < shapesList.size(); i++) {
                Element currentShape = (Element) shapesList.get(i);

                if(currentShape.getTagName().equals(SupportedShapes.RECTANGLE.getSvgTag())) {
                        int x = Integer.parseInt(currentShape.getAttribute("x"));
                        int y = Integer.parseInt(currentShape.getAttribute("y"));
                        int width = Integer.parseInt(currentShape.getAttribute("width"));
                        int height = Integer.parseInt(currentShape.getAttribute("height"));

                        if (ShapeUtilities.isRectangleWithinRectangle(x, y, width, height, ((RectangleShape)shape).getXCoordinate(), ((RectangleShape)shape).getYCoordinate(), ((RectangleShape)shape).getWidth(), ((RectangleShape)shape).getHeight()) && shape instanceof RectangleShape) {
                            print(i);
                        }

                        if (ShapeUtilities.isRectangleWithinCircle(((CircleShape)shape).getCenterXCoordinate(), ((CircleShape)shape).getCenterYCoordinate(), ((CircleShape)shape).getRadius(), x, y, width, height) && shape instanceof CircleShape) {
                            print(i);
                        }
                }
                   if(currentShape.getTagName().equals(SupportedShapes.CIRCLE.getSvgTag())) {
                       int cx = Integer.parseInt(currentShape.getAttribute("cx"));
                       int cy = Integer.parseInt(currentShape.getAttribute("cy"));
                       int radius = Integer.parseInt(currentShape.getAttribute("r"));

                       int closestX = Math.max((((RectangleShape) shape).getXCoordinate()), Math.min(cx, ((RectangleShape) shape).getXCoordinate() + ((RectangleShape) shape).getWidth()));
                       int closestY = Math.max(((RectangleShape) shape).getYCoordinate(), Math.min(cy, ((RectangleShape) shape).getYCoordinate() + ((RectangleShape) shape).getHeight()));


                       int distance = (int)Math.sqrt(Math.pow(cx - closestX, 2) + Math.pow(cy - closestY, 2));

                       if (distance <= radius)
                           print(i);
                   }

                   if(currentShape.getTagName().equals(SupportedShapes.LINE.getSvgTag())){

                       int firstXCoordinate = Integer.parseInt(currentShape.getAttribute("x1"));
                       int secondXCoordinate = Integer.parseInt(currentShape.getAttribute("x2"));
                       int  firstYCoordinate = Integer.parseInt(currentShape.getAttribute("y1"));
                       int secondYCoordinate = Integer.parseInt(currentShape.getAttribute("y2"));
                       if (ShapeUtilities.isLineWithinRectangle(((RectangleShape)shape).getXCoordinate(), ((RectangleShape)shape).getYCoordinate(), ((RectangleShape)shape).getWidth(), ((RectangleShape)shape).getHeight(), firstXCoordinate, secondXCoordinate, firstYCoordinate, secondYCoordinate)) {
                           print(i);
                       }
            }
                }
        return cResult;
    }

    private void checkShapeTypeAndTranslate(Element currentShape, TranslateCommand translateCommand){
        boolean result = true;
        switch (currentShape.getTagName()){
            case "rect":
                currentShape.setAttribute("y", String.valueOf(translateCommand.getVerticalParameterValue() + Integer.parseInt(currentShape.getAttribute("y"))));
                currentShape.setAttribute("x", String.valueOf(translateCommand.getHorizontalParameterValue() + Integer.parseInt(currentShape.getAttribute("x"))));
                break;
            case "circle":
                currentShape.setAttribute("cy", String.valueOf(translateCommand.getVerticalParameterValue() + Integer.parseInt(currentShape.getAttribute("cy"))));
                currentShape.setAttribute("cx", String.valueOf(translateCommand.getHorizontalParameterValue() + Integer.parseInt(currentShape.getAttribute("cx"))));
                break;
            case "line":
                currentShape.setAttribute("x1", String.valueOf(translateCommand.getVerticalParameterValue() + Integer.parseInt(currentShape.getAttribute("x1"))));
                currentShape.setAttribute("x2", String.valueOf(translateCommand.getHorizontalParameterValue() + Integer.parseInt(currentShape.getAttribute("x2"))));
                currentShape.setAttribute("y1", String.valueOf(translateCommand.getVerticalParameterValue() + Integer.parseInt(currentShape.getAttribute("y1"))));
                currentShape.setAttribute("y2", String.valueOf(translateCommand.getHorizontalParameterValue() + Integer.parseInt(currentShape.getAttribute("y2"))));
                break;
        }
    }

    public CommandResult translate(final Command command){
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;
        if(!isFileOpened)
            return cResult = CommandResult.FILE_NOT_OPENED;

        try{
            TranslateCommand translateCommand = (TranslateCommand)command;

            Element rootElement = getRootElement();
            if(rootElement == null)
                return CommandResult.COMMAND_FAILED;

            Element currentShape;
            NodeList shapes = rootElement.getChildNodes();
            if(translateCommand.isTranslateAllShapes()){
                for(int i = 0; i < this.shapesList.size(); i++){
                     currentShape = this.shapesList.get(i);
                    checkShapeTypeAndTranslate(currentShape, translateCommand);
                }
            }else {
                currentShape = this.shapesList.get(translateCommand.getIndexOfShapeTobeErased() - 1);
                checkShapeTypeAndTranslate(currentShape, translateCommand);
            }
        }
        catch (IndexOutOfBoundsException | NullPointerException e){
            cResult = CommandResult.COMMAND_FAILED;
            ErrorLogger.logError(e.toString());
        }

        return cResult;
    }

    //методът прави проверка кава ще бъде фигурата която ще се създава и изиква съответният метод
    public CommandResult createShape(final Command command){
       CommandResult cResult = CommandResult.COMMAND_FAILED;
        if(!isFileOpened)
            return cResult = CommandResult.FILE_NOT_OPENED;
        CreateCommand createCommand = (CreateCommand)command;

        if(createCommand.getShape() instanceof RectangleShape)
            cResult = createRectangle(createCommand);

        if(createCommand.getShape() instanceof CircleShape)
            cResult = createCircle(createCommand);

        if(createCommand.getShape() instanceof LineShape)
            cResult = createLine(createCommand);

       return cResult;
    }

    //методът създава правоъгълник и го добавя в отворения файл
    private CommandResult createRectangle(CreateCommand command){
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;

        Element rootElement = getRootElement();
        if(rootElement == null)
            cResult = CommandResult.COMMAND_FAILED;

        RectangleShape rectangleShape =  (RectangleShape)command.getShape();

        Element rectangleElement = currentDocument.createElement("rect");
        rectangleElement.setAttribute("x", String.valueOf(rectangleShape.getXCoordinate()));
        rectangleElement.setAttribute("y", String.valueOf(rectangleShape.getYCoordinate()));
        rectangleElement.setAttribute("width", String.valueOf(rectangleShape.getWidth()));
        rectangleElement.setAttribute("height", String.valueOf(rectangleShape.getHeight()));
        rectangleElement.setAttribute("fill", rectangleShape.getColor());
        rootElement.appendChild(rectangleElement);
        if(!this.shapesList.add(rectangleElement))
            cResult = CommandResult.COMMAND_FAILED;

        return cResult;
    }

    private CommandResult createLine(final CreateCommand command){
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;

        Element rootElement = getRootElement();
        if(rootElement == null)
            cResult = CommandResult.COMMAND_FAILED;

        LineShape lineShape =  (LineShape)command.getShape();

        Element circleElement = currentDocument.createElement("line");
        circleElement.setAttribute("x1", String.valueOf(lineShape.getFirstXCoordinate()));
        circleElement.setAttribute("y1", String.valueOf(lineShape.getFirstYCoordinate()));
        circleElement.setAttribute("x2", String.valueOf(lineShape.getSecondXCoordinate()));
        circleElement.setAttribute("y2", String.valueOf(lineShape.getSecondYCoordinate()));
        circleElement.setAttribute("stroke", lineShape.getColor());
        rootElement.appendChild(circleElement);
        if(!this.shapesList.add(circleElement))
            cResult = CommandResult.COMMAND_FAILED;

        return cResult;
    }

    private CommandResult createCircle(final CreateCommand command){
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;

        Element rootElement = getRootElement();
        if(rootElement == null)
            cResult = CommandResult.COMMAND_FAILED;

        CircleShape circleShape =  (CircleShape)command.getShape();

        Element circleElement = currentDocument.createElement("circle");
        circleElement.setAttribute("cx", String.valueOf(circleShape.getCenterXCoordinate()));
        circleElement.setAttribute("cy", String.valueOf(circleShape.getCenterYCoordinate()));
        circleElement.setAttribute("r", String.valueOf(circleShape.getRadius()));
        circleElement.setAttribute("fill", circleShape.getColor());
        rootElement.appendChild(circleElement);
       if(!this.shapesList.add(circleElement))
           cResult = CommandResult.COMMAND_FAILED;

        return cResult;
    }

    //връща обкет от тип елемент, който реално е svg тагът на него добавяме самите фигури, като child nodes
    private Element getRootElement() throws NullPointerException{
        Element rootElement = null;
        NodeList rootNodeList = currentDocument.getElementsByTagName(ROOT_ELEMENT);
        Node rootNode = rootNodeList.item(0);
        if(rootNode.getNodeType() == Node.ELEMENT_NODE){
            rootElement = (Element)rootNode;
        }

        return rootElement;
    }
    private boolean isShapeSupported(String shapeTagName) {
        boolean result = false;
        for (SupportedShapes supportedShape :
                SupportedShapes.values()) {
            if (supportedShape.getSvgTag().equals(shapeTagName)) {
                result = true;
            }
        }
        return result;
    }

    private boolean loadAllShapes(){
       boolean result = true;
       try {
           Element root = getRootElement();
           NodeList shapeList = root.getChildNodes();
           for (int i = 0; i < shapeList.getLength(); i++) {
               Node currentShape = shapeList.item(i);
               if (currentShape.getNodeType() == Node.ELEMENT_NODE) {
                   Element shapeElement = (Element) currentShape;
                   if (isShapeSupported(shapeElement.getTagName())) {
                       this.shapesList.add(shapeElement);
                   }
               }
           }
       }
       catch (ClassCastException | ArrayIndexOutOfBoundsException | NullPointerException e){
           ErrorLogger.logError(e.toString());
           result = false;
       }

       return result;
    }

    public CommandResult eraseShape(final Command currentCommand){

        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;
        if(!isFileOpened)
            return cResult = CommandResult.FILE_NOT_OPENED;
        try {
            EraseCommand eraseCommand = (EraseCommand) currentCommand;
            Element shapeToBeErased = this.shapesList.remove(eraseCommand.getEraseShapeIndex());

            shapeToBeErased.getParentNode().removeChild(shapeToBeErased);
        }
        catch (ClassCastException | NullPointerException  | IndexOutOfBoundsException e){
            ErrorLogger.logError(e.toString());
            cResult = CommandResult.COMMAND_FAILED;
        }

        return cResult;
    }

    //принтира една фигура по подаден индекс
    private void print(int index) throws ArrayIndexOutOfBoundsException , NullPointerException{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((index+1) +  ". " + this.shapesList.get(index).getTagName() + " ");
        NamedNodeMap shapeAttributes = this.shapesList.get(index).getAttributes();
        for (int j = 0; j < shapeAttributes.getLength(); j++) {
            stringBuilder.append(shapeAttributes.item(j).getNodeValue().toString() + " ");
        }
        PrintWriter.print(stringBuilder.toString());
    }

    public CommandResult printAllShapes(){
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;
        if(!isFileOpened)
            return cResult = CommandResult.FILE_NOT_OPENED;
       try {
           for (int i = 0; i < this.shapesList.size(); i++) {
               print(i);
           }
       }
       catch (ArrayIndexOutOfBoundsException  | NullPointerException e){
           cResult = CommandResult.COMMAND_FAILED;
           ErrorLogger.logError(e.toString());
       }
        return cResult;
    }

    //записва промените направени в будера във файла
    public  CommandResult saveFile() {
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;
        if(!isFileOpened)
            return cResult = CommandResult.FILE_NOT_OPENED;

        try {
            DOMSource source = new DOMSource(currentDocument);

            Result streamResult = new StreamResult(currentFile);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, streamResult);
        }
        catch (TransformerException | NullPointerException e){
            ErrorLogger.logError(e.toString());
            cResult =CommandResult.COMMAND_FAILED;
        }
        closeCurrentFile();
        return cResult;

    }

    //затваря текущия файл, без да запазва промените по него
    public CommandResult closeCurrentFile(){
        if(!isFileOpened)
            return CommandResult.FILE_NOT_OPENED;
        currentDocument = null;
        isFileOpened = false;
        shapesList.removeAll(shapesList);

        return CommandResult.FILE_CLOSED_SUCCESSFULLY;
    }

    //-----Overrides----
}
