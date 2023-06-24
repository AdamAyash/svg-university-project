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

    private final String RECTANGLE_X_COORDINATE = "x";
    private final String RECTANGLE_Y_COORDINATE = "y";
    private final String RECTANGLE_WIDTH = "width";
    private final String RECTANGLE_HEIGHT = "height";

    private final String CIRCLE_X_COORDINATE = "cx";
    private final String CIRCLE_Y_COORDINATE = "cy";
    private final String CIRCLE_RADIUS = "r";

    private final String LINE_FIRST_X_COORDINATE = "x1";
    private final String LINE_SECOND_X_COORDINATE = "x2";
    private final String LINE_FIRST_Y_COORDINATE = "y1";
    private final String LINE_SECOND_Y_COORDINATE = "y2";
    private final String FILL = "fill";
    private final String STROKE = "stroke";



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
    public boolean openFile(final Command currentCommand){
        OpenCommand openCommand = (OpenCommand) currentCommand;
       if(!isFileOpened) {
           currentFile = new File(((OpenCommand) currentCommand).getFilePath());
           DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder documentBuilder;
           try {
               documentBuilder = documentBuilderFactory.newDocumentBuilder();
               if (currentFile.exists()) {
                   currentDocument = documentBuilder.parse(openCommand.getFilePath());
                   if(!loadAllShapes())
                       return false;
               } else {
                   currentDocument = documentBuilder.newDocument();
                   Element svgRootElement = currentDocument.createElement(ROOT_ELEMENT);
                   currentDocument.appendChild(svgRootElement);
               }
           } catch (ParserConfigurationException | IOException | SAXException |
                    IllegalArgumentException | NullPointerException e) {

               ErrorLogger.logError(e.toString());
               return false;
           }
           isFileOpened = true;
           openCommand.getCommandLineInterface().PrintCommandResult(CommandResult.FILE_SUCCESSFULLY_OPENED, openCommand.getFilePath());
       }else {
           openCommand.getCommandLineInterface().PrintCommandResult(CommandResult.FILE_ALREADY_OPENED);
       }
        return true;
    }

    public boolean checkIfShapeIsWithinAnotherShape(final Command command){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
        WithinCommand withinCommand = (WithinCommand)command;

        BasicShape shape = withinCommand.getShape();

        for(int i = 0; i < shapesList.size(); i++) {
                Element currentShape = (Element) shapesList.get(i);

                if(currentShape.getTagName().equals(SupportedShapes.RECTANGLE.getSvgTag())) {
                        double x = Double.parseDouble(currentShape.getAttribute(RECTANGLE_X_COORDINATE));
                        double y = Double.parseDouble(currentShape.getAttribute(RECTANGLE_Y_COORDINATE));
                        double width = Double.parseDouble(currentShape.getAttribute(RECTANGLE_WIDTH));
                        double height = Double.parseDouble(currentShape.getAttribute(RECTANGLE_HEIGHT));

                            if(shape instanceof  RectangleShape) {
                                if (ShapeUtilities.isRectangleWithinRectangle(x, y, width, height, ((RectangleShape) shape).getXCoordinate(), ((RectangleShape) shape).getYCoordinate(), ((RectangleShape) shape).getWidth(), ((RectangleShape) shape).getHeight())) {
                                    print(i);
                                }
                            }

                            if(shape instanceof  CircleShape) {
                             if (ShapeUtilities.isRectangleWithinCircle(((CircleShape) shape).getCenterXCoordinate(), ((CircleShape) shape).getCenterYCoordinate(), ((CircleShape) shape).getRadius(), x, y, width, height)) {
                                 print(i);
                             }
                         }
                }
                   if(currentShape.getTagName().equals(SupportedShapes.CIRCLE.getSvgTag())) {
                       double cx = Double.parseDouble(currentShape.getAttribute(CIRCLE_X_COORDINATE));
                       double cy = Double.parseDouble(currentShape.getAttribute(CIRCLE_Y_COORDINATE));
                       double radius = Double.parseDouble(currentShape.getAttribute(CIRCLE_RADIUS));

                       if(shape instanceof RectangleShape) {
                           double closestX = Math.max((((RectangleShape) shape).getXCoordinate()), Math.min(cx, ((RectangleShape) shape).getXCoordinate() + ((RectangleShape) shape).getWidth()));
                           double closestY = Math.max(((RectangleShape) shape).getYCoordinate(), Math.min(cy, ((RectangleShape) shape).getYCoordinate() + ((RectangleShape) shape).getHeight()));

                           double distance =  Math.sqrt(Math.pow(cx - closestX, 2) + Math.pow(cy - closestY, 2));

                           if (distance <= radius)
                               print(i);
                       }

                       if(shape instanceof CircleShape){
                           double distance = Math.sqrt(Math.pow(((CircleShape) shape).getCenterXCoordinate() - cx, 2) + Math.pow(((CircleShape) shape).getCenterYCoordinate() - cy, 2));

                           if(distance + radius <= ((CircleShape) shape).getRadius())
                               print(i);
                       }
                   }

                   if(currentShape.getTagName().equals(SupportedShapes.LINE.getSvgTag())){

                       double firstXCoordinate = Double.parseDouble(currentShape.getAttribute(LINE_FIRST_X_COORDINATE));
                       double secondXCoordinate = Double.parseDouble(currentShape.getAttribute(LINE_SECOND_X_COORDINATE));
                       double  firstYCoordinate = Double.parseDouble(currentShape.getAttribute(LINE_FIRST_Y_COORDINATE));
                       double secondYCoordinate = Double.parseDouble(currentShape.getAttribute(LINE_SECOND_Y_COORDINATE));

                       if(shape instanceof RectangleShape) {
                           if (ShapeUtilities.isLineWithinRectangle(((RectangleShape) shape).getXCoordinate(), ((RectangleShape) shape).getYCoordinate(), ((RectangleShape) shape).getWidth(), ((RectangleShape) shape).getHeight(), firstXCoordinate, secondXCoordinate, firstYCoordinate, secondYCoordinate))
                               print(i);
                       }

                       if(shape instanceof CircleShape){
                           double startDistanceSquared = Math.pow(firstXCoordinate - ((CircleShape) shape).getCenterXCoordinate(), 2) + Math.pow(secondXCoordinate - ((CircleShape) shape).getCenterYCoordinate(), 2);
                           double endDistanceSquared = Math.pow(secondXCoordinate - ((CircleShape) shape).getCenterXCoordinate(), 2) + Math.pow(secondYCoordinate - ((CircleShape) shape).getCenterYCoordinate(), 2);

                           if (startDistanceSquared <= Math.pow(((CircleShape) shape).getRadius(), 2) && endDistanceSquared <= Math.pow(((CircleShape) shape).getRadius(), 2))
                               print(i);
                           }
                       }
                    }
        return true;
    }

    private void checkShapeTypeAndTranslate(Element currentShape, TranslateCommand translateCommand){
       if(currentShape.getTagName().equals(SupportedShapes.RECTANGLE.getSvgTag())) {
           currentShape.setAttribute(RECTANGLE_Y_COORDINATE, String.valueOf(translateCommand.getVerticalParameterValue() + Double.parseDouble(currentShape.getAttribute(RECTANGLE_Y_COORDINATE))));
           currentShape.setAttribute(RECTANGLE_X_COORDINATE, String.valueOf(translateCommand.getHorizontalParameterValue() + Double.parseDouble(currentShape.getAttribute(RECTANGLE_X_COORDINATE))));
       }
        if(currentShape.getTagName().equals(SupportedShapes.CIRCLE.getSvgTag())) {
            currentShape.setAttribute(CIRCLE_Y_COORDINATE, String.valueOf(translateCommand.getVerticalParameterValue() + Double.parseDouble(currentShape.getAttribute(CIRCLE_Y_COORDINATE))));
            currentShape.setAttribute(CIRCLE_X_COORDINATE, String.valueOf(translateCommand.getHorizontalParameterValue() + Double.parseDouble(currentShape.getAttribute(CIRCLE_X_COORDINATE))));
        }
        if(currentShape.getTagName().equals(SupportedShapes.LINE.getSvgTag())) {
                currentShape.setAttribute(LINE_FIRST_Y_COORDINATE, String.valueOf(translateCommand.getVerticalParameterValue() + Double.parseDouble(currentShape.getAttribute(LINE_FIRST_Y_COORDINATE))));
                currentShape.setAttribute(LINE_FIRST_X_COORDINATE, String.valueOf(translateCommand.getHorizontalParameterValue() + Double.parseDouble(currentShape.getAttribute(LINE_FIRST_X_COORDINATE))));
                currentShape.setAttribute(LINE_SECOND_Y_COORDINATE, String.valueOf(translateCommand.getVerticalParameterValue() + Double.parseDouble(currentShape.getAttribute(LINE_SECOND_Y_COORDINATE))));
                currentShape.setAttribute(LINE_SECOND_X_COORDINATE, String.valueOf(translateCommand.getHorizontalParameterValue() + Double.parseDouble(currentShape.getAttribute(LINE_SECOND_X_COORDINATE))));
        }
    }

    public boolean translate(final Command command){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }

        try{
            TranslateCommand translateCommand = (TranslateCommand)command;

            Element rootElement = getRootElement();
            if(rootElement == null)
                return false;

            Element currentShape;
            NodeList shapes = rootElement.getChildNodes();
            if(translateCommand.isTranslateAllShapes()){
                for(int i = 0; i < this.shapesList.size(); i++){
                     currentShape = this.shapesList.get(i);
                    checkShapeTypeAndTranslate(currentShape, translateCommand);
                }
                PrintWriter.print(CommandResult.SUCCESSFULLY_TRANSLATED_ALL_FIGURES.getCommandResultMessage());
            }else {
                currentShape = this.shapesList.get(translateCommand.getIndexOfShapeTobeErased() - 1);
                checkShapeTypeAndTranslate(currentShape, translateCommand);
                PrintWriter.print(CommandResult.SUCCESSFULLY_TRANSLATED_SHAPE.getCommandResultMessage() + "(" + translateCommand.getIndexOfShapeTobeErased() + ")");
            }
        }
        catch (IndexOutOfBoundsException | NullPointerException e){
            ErrorLogger.logError(e.toString());
            return false;
        }

        return true;
    }

    //методът прави проверка кава ще бъде фигурата която ще се създава и изиква съответният метод
    public boolean createShape(final Command command){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }

        CreateCommand createCommand = (CreateCommand)command;

        if(createCommand.getShape() instanceof RectangleShape)
            return createRectangle(createCommand);

        if(createCommand.getShape() instanceof CircleShape)
            return createCircle(createCommand);

        if(createCommand.getShape() instanceof LineShape)
            return createLine(createCommand);

       return false;
    }

    //методът създава правоъгълник и го добавя в отворения файл
    private boolean createRectangle(CreateCommand command){

        Element rootElement = getRootElement();
        if(rootElement == null)
            return false;

        RectangleShape rectangleShape =  (RectangleShape)command.getShape();

        Element rectangleElement = currentDocument.createElement(SupportedShapes.RECTANGLE.getSvgTag());
        rectangleElement.setAttribute(RECTANGLE_X_COORDINATE, String.valueOf(rectangleShape.getXCoordinate()));
        rectangleElement.setAttribute(RECTANGLE_Y_COORDINATE, String.valueOf(rectangleShape.getYCoordinate()));
        rectangleElement.setAttribute(RECTANGLE_WIDTH, String.valueOf(rectangleShape.getWidth()));
        rectangleElement.setAttribute(RECTANGLE_HEIGHT, String.valueOf(rectangleShape.getHeight()));
        rectangleElement.setAttribute(FILL, rectangleShape.getColor());
        rootElement.appendChild(rectangleElement);
        if(!this.shapesList.add(rectangleElement))
            return  false;

        PrintWriter.print(CommandResult.SHAPE_SUCCESSFULLY_CREATED.getCommandResultMessage() + SupportedShapes.RECTANGLE.getSupportedShape() + " (" + shapesList.size() + ")");
        return true;
    }

    private boolean createLine(final CreateCommand command){

        Element rootElement = getRootElement();
        if(rootElement == null)
            return false;

        LineShape lineShape =  (LineShape)command.getShape();

        Element circleElement = currentDocument.createElement(SupportedShapes.LINE.getSvgTag());
        circleElement.setAttribute(LINE_FIRST_X_COORDINATE, String.valueOf(lineShape.getFirstXCoordinate()));
        circleElement.setAttribute(LINE_FIRST_Y_COORDINATE, String.valueOf(lineShape.getFirstYCoordinate()));
        circleElement.setAttribute(LINE_SECOND_X_COORDINATE, String.valueOf(lineShape.getSecondXCoordinate()));
        circleElement.setAttribute(LINE_SECOND_Y_COORDINATE, String.valueOf(lineShape.getSecondYCoordinate()));
        circleElement.setAttribute(STROKE, lineShape.getColor());
        rootElement.appendChild(circleElement);
        if(!this.shapesList.add(circleElement))
            return false;
        PrintWriter.print(CommandResult.SHAPE_SUCCESSFULLY_CREATED.getCommandResultMessage() + SupportedShapes.LINE.getSupportedShape() + " (" + shapesList.size() + ")");
        return true;
    }

    private boolean createCircle(final CreateCommand command){
        Element rootElement = getRootElement();
        if(rootElement == null)
            return false;

        CircleShape circleShape =  (CircleShape)command.getShape();

        Element circleElement = currentDocument.createElement(SupportedShapes.CIRCLE.getSvgTag());
        circleElement.setAttribute(CIRCLE_X_COORDINATE, String.valueOf(circleShape.getCenterXCoordinate()));
        circleElement.setAttribute(CIRCLE_Y_COORDINATE, String.valueOf(circleShape.getCenterYCoordinate()));
        circleElement.setAttribute(CIRCLE_RADIUS, String.valueOf(circleShape.getRadius()));
        circleElement.setAttribute(FILL, circleShape.getColor());
        rootElement.appendChild(circleElement);
       if(!this.shapesList.add(circleElement))
           return false;

        PrintWriter.print(CommandResult.SHAPE_SUCCESSFULLY_CREATED.getCommandResultMessage() + SupportedShapes.CIRCLE.getSupportedShape() + " (" + shapesList.size() + ")");
        return true;
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

    public boolean eraseShape(final Command currentCommand){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
        try {
            EraseCommand eraseCommand = (EraseCommand) currentCommand;
            if(eraseCommand.getEraseShapeIndex() > shapesList.size() - 1 || eraseCommand.getEraseShapeIndex() < 0){
                PrintWriter.print(CommandResult.ERASE_SHAPE_NOT_FOUND.getCommandResultMessage() + (eraseCommand.getEraseShapeIndex() + 1));
                return false;
            }

            Element shapeToBeErased = this.shapesList.remove(eraseCommand.getEraseShapeIndex());

            shapeToBeErased.getParentNode().removeChild(shapeToBeErased);
            PrintWriter.print("Erased a " + shapeToBeErased.getNodeName() + " (" + (eraseCommand.getEraseShapeIndex() + 1) + ")");
        }
        catch (ClassCastException | NullPointerException  | IndexOutOfBoundsException e){
            ErrorLogger.logError(e.toString());
            return false;
        }

        return true;
    }


    //принтира една фигура по подаден индекс
    private void print(int index) throws ArrayIndexOutOfBoundsException , NullPointerException{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((index+1) +  ". " + this.shapesList.get(index).getTagName() + " ");
        NamedNodeMap shapeAttributes = this.shapesList.get(index).getAttributes();
        for (int j = 0; j < shapeAttributes.getLength(); j++) {
            stringBuilder.append(shapeAttributes.item(j).getNodeName() + "=" + shapeAttributes.item(j).getNodeValue() + " ");
        }
        PrintWriter.print(stringBuilder.toString());
    }

    public boolean printAllShapes(){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
       try {
           for (int i = 0; i < this.shapesList.size(); i++) {
               print(i);
           }
       }
       catch (ArrayIndexOutOfBoundsException  | NullPointerException e){
           ErrorLogger.logError(e.toString());
           return false;
       }
        return true;
    }

    //записва промените направени в будера във файла
    public boolean saveFile() {
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
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
            return false;
        }
        PrintWriter.print(CommandResult.FILE_SAVED_SUCCESSFULLY.getCommandResultMessage() + currentFile.getName());
        closeCurrentFile();

        return true;

    }

    public void help() {
       PrintWriter.print("The following commands are supported");
        PrintWriter.print("open <file> opens <file>");
        PrintWriter.print("close closes currently opened file");
        PrintWriter.print("save saves the currently open file");
        PrintWriter.print("saveas <file> saves the currently open file in <file>");
        PrintWriter.print("print print all supported shapes");
        PrintWriter.print("create creates a new shape");
        PrintWriter.print("erase <n>  Erases shape with index <n>");
        PrintWriter.print("translate [<n>] translates shape with index <n> of if <n> is not assigned, translates all shapes");
        PrintWriter.print("within <option> prints all shapes which are contained within another shape described by option");
        PrintWriter.print("help prints this information ");
        PrintWriter.print("exit exists the program");
    }

    public boolean saveFile(String filePath) {
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
        try {
            DOMSource source = new DOMSource(currentDocument);

            Result streamResult = new StreamResult(new File(filePath));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, streamResult);
        }
        catch (TransformerException | NullPointerException e){
            ErrorLogger.logError(e.toString());
            return false;
        }
        PrintWriter.print(CommandResult.FILE_SAVED_SUCCESSFULLY.getCommandResultMessage() + currentFile.getName());
        closeCurrentFile();

        return true;

    }


    //затваря текущия файл, без да запазва промените по него
    public boolean closeCurrentFile(){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
        currentDocument = null;
        isFileOpened = false;
        shapesList.removeAll(shapesList);
        PrintWriter.print(CommandResult.FILE_CLOSED_SUCCESSFULLY.getCommandResultMessage() + currentFile.getName());
        return true;
    }

    //-----Overrides----
}
