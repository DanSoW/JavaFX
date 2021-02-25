package programGUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.event.DocumentEvent;
import java.io.File;
import java.rmi.server.ExportException;

public class Main extends Application {

    private StringClass stringTask = null;  //экземпляр объекта, решающего задачи
    private Button _btnStart = null;        //экземпляр объекта кнопки выполнения задания
    private Button _btnSave = null;         //экземпляр объекта кнопки сохранения результата выполнения задания в файл
    private Button _btnRead = null;         //экземпляр объекта кнопки чтения результатов выполнения задания из файла
    private TextField _txtBase = null;      //текстовое поле (основной текст, относительно которого решается задача
    private TextField _txtSubstring = null; //текстовое поле для подстроки
    private TextField _txtNumberWord = null;//текстовое поле для номера слова в предложении
    private TextArea _txtResult = null;     //текстовое поле для вывода результата
    private ComboBox _cmbTask = null;       //экземпляр объекта ComboBox, для выбора решаемой задачи

    //Задания:
    public static final String task1 = "Определить, является ли строка подстрокой другой строки";
    public static final String task2 = "Найти длину указанного слова в предложении";
    public static final String task3 = "Определить центральное слово";

    //Вывод сообщения
    public static void MessageShow(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private boolean ComboBoxValidate(){
        if((_cmbTask.getValue() == null) || ((!_cmbTask.getValue().toString().equals(task1))
        && (!_cmbTask.getValue().toString().equals(task2))
        && (!_cmbTask.getValue().toString().equals(task3)))){
            return false;
        }

        return true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("viewFile.fxml")); //загрузка fxml файла с вёрсткой интерфейса
        Scene scene = new Scene(root); //установка для сцены объектов
        primaryStage.setScene(scene); //установка для основного контейнера сцены
        primaryStage.setTitle("Window"); //установка названия окна

        stringTask = new StringClass(); //определение экземпляра объекта, для решения заданий

        ComboBox cmbTask = (ComboBox) scene.lookup("#_cmbTask");
        cmbTask.getItems().addAll(task1, task2, task3); //добавления решаемых задач в ComboBox

        //нахождение элементов на вёрстке
        _btnStart = (Button) scene.lookup("#_btnStart");
        _btnSave = (Button) scene.lookup("#_btnSave");
        _btnRead = (Button) scene.lookup("#_btnRead");
        _txtSubstring = (TextField) scene.lookup("#_txtSubstring");
        _txtNumberWord = (TextField) scene.lookup("#_txtNumberWord");
        _txtBase = (TextField) scene.lookup("#_txtBase");
        _txtResult = (TextArea) scene.lookup("#_txtResult");
        _cmbTask = (ComboBox) scene.lookup("#_cmbTask");
        _txtResult.editableProperty().set(false); //установка режима "только чтение" для элемента TextArea

        _cmbTask.valueProperty().addListener(new ChangeListener<String>() { //обработка события изменения значения в ComboBox
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                _txtResult.setText("");
            }
        });

        _btnStart.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!ComboBoxValidate()){
                    MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: не выбрана задача для решения");
                    return;
                }

                if(_txtBase.getText().length() == 0){
                    MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: не введены данные об основной строке");
                    return;
                }

                stringTask.setText(_txtBase.getText());
                if(_cmbTask.getValue().toString().equals(task1)){
                    if(_txtSubstring.getText().length() == 0){
                        MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: не введены данные о подстроке");
                        return;
                    }

                    try{
                        _txtResult.setText("Подстрока " + _txtSubstring.getText() +
                                ((stringTask.substringInString(_txtSubstring.getText()))? " является" : " не является")
                                + " подстрокой строки " + _txtBase.getText());
                    }catch (Exception e){
                        e.printStackTrace();
                        MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: данная подстрока задана не корректно");
                    }
                }else if(_cmbTask.getValue().toString().equals(task2)){
                    if(_txtNumberWord.getText().length() == 0){
                        MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: не введены данные о номере слова в основной строке");
                        return;
                    }

                    try{
                        _txtResult.setText("Слово под номером " + _txtNumberWord.getText() +
                                " имеет длину " + stringTask.lengthDefineWord(Integer.valueOf(_txtNumberWord.getText())));
                    }catch (Exception e){
                        e.printStackTrace();
                        MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: данные номера слова в основной строке не корректны");
                    }
                }else if(_cmbTask.getValue().toString().equals(task3)){
                    try{
                        _txtResult.setText("Центральным словом(-ами) текста " + _txtBase.getText().toString()
                                + " является(-ются) " + stringTask.centerWords().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                        MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: основная строка не корректна, не возможно определить центральное слово");
                    }
                }
            }
        });

        _btnRead.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!ComboBoxValidate()){
                    MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: нет данных для чтения");
                    return;
                }

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Чтение данных");
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                try{
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        String dataCmb = _cmbTask.getValue().toString();
                        String data = FileIO.outputDataFromFile(file.getAbsolutePath());
                        if(data.length() == 0){
                            MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: данные отсутствуют в файле");
                        }
                        String[] strings = data.split("\n");
                        int number = Integer.valueOf(strings[0]);
                        if(((dataCmb.equals(task1) || dataCmb.equals(task2)) && (strings.length != 3))
                        || ((number > 3) || (number < 1))){
                            MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: в файле содержатся не корректные данные");
                            return;
                        }
                        stringTask.setText(strings[1]);

                        if((number == 1) && (dataCmb.equals(task1))){
                            _txtBase.setText(strings[1]);
                            _txtSubstring.setText(strings[2]);
                            _txtResult.setText("Подстрока " + _txtSubstring.getText() +
                                    ((stringTask.substringInString(_txtSubstring.getText()))? " является" : " не является")
                                    + " подстрокой строки " + _txtBase.getText());
                        }else if((number == 2) && (dataCmb.equals(task2))){
                            _txtBase.setText(strings[1]);
                            _txtNumberWord.setText(Integer.valueOf(strings[2]).toString());
                            _txtResult.setText("Слово под номером " + _txtNumberWord.getText() +
                                    " имеет длину " + stringTask.lengthDefineWord(Integer.valueOf(_txtNumberWord.getText())));
                        }else if((number == 3) && (dataCmb.equals(task3))){
                            if(strings.length != 2){
                                MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: в файле содержатся не корректные данные");
                                return;
                            }
                            _txtBase.setText(strings[1]);
                            _txtResult.setText("Центральным словом(-ами) текста " + _txtBase.getText().toString()
                                    + " является(-ются) " + stringTask.centerWords().toString());
                        }
                        else{
                            MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: в файле содержится данные о результатах другой задачи");
                            stringTask.setText("");
                            return;
                        }

                        MessageShow(Alert.AlertType.INFORMATION, "Информация", "Данные считаны!");
                    }else{
                        MessageShow(Alert.AlertType.INFORMATION, "Информация", "Данные не считаны!");
                    }
                }catch (Exception e){
                    MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: невозможно открыть данный файл");
                    e.printStackTrace();
                }
            }
        });

        _btnSave.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String dataCmb;
                if((_txtResult.getText().length() == 0) || (!ComboBoxValidate())
                        || ((dataCmb = _cmbTask.getValue().toString()).length() == 0)
                        || (_txtBase.getText().length() == 0)
                        || (dataCmb.equals(task1) && _txtSubstring.getText().length() == 0)
                        || (dataCmb.equals(task2) && _txtNumberWord.getText().length() == 0)){
                    MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: нет данных для записи");
                    return;
                }

                FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
                fileChooser.setTitle("Сохранение данных");//Заголовок диалога
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                try{
                    File file = fileChooser.showSaveDialog(primaryStage);
                    if (file != null) {
                        String data = "";
                        if(dataCmb.equals(task1)){
                            data += "1\n" + _txtBase.getText() +
                                    "\n" + _txtSubstring.getText();
                        }
                        else if(dataCmb.equals(task2)){
                            data += "2\n" + _txtBase.getText() +
                                    "\n" + _txtNumberWord.getText();
                        }
                        else if(dataCmb.equals(task3)){
                            data += "3\n" + _txtBase.getText();
                        }

                        FileIO.inputDataInFile(file.getAbsolutePath(), data, false);
                        MessageShow(Alert.AlertType.INFORMATION, "Информация", "Данные сохранены!");
                    }else{
                        MessageShow(Alert.AlertType.INFORMATION, "Информация", "Данные не сохранены!");
                    }
                }catch (Exception e){
                    MessageShow(Alert.AlertType.ERROR, "Ошибка!", "Ошибка: невозможно открыть данный файл");
                    e.printStackTrace();
                }
            }
        });
        primaryStage.show(); //вывод основного окна приложения на экран
    }


    public static void main(String[] args) {
        launch(args);
    }
}
