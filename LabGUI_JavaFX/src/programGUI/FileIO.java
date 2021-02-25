package programGUI;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileIO {
    private static boolean checkFilePath(String filePath){
        boolean check = false;
        if((filePath.length() == 0)
                || (filePath.charAt(0) == ':')
                || (filePath.indexOf(':') != filePath.lastIndexOf(':'))
                || (filePath.indexOf(':') == (-1))
                || ((filePath.indexOf('\\') != (-1)) && (filePath.indexOf('/') != (-1)))
                || ((filePath.indexOf('\\') == (-1)) && (filePath.indexOf('/') == (-1)))
                || ((filePath.indexOf('\\') != (-1)) && filePath.indexOf('\\') < filePath.indexOf(':'))
                || ((filePath.indexOf('/') != (-1)) && filePath.indexOf('/') < filePath.indexOf(':'))
                || (filePath.indexOf('.') != filePath.lastIndexOf('.'))
        )
            return check;
        char[] symbols = {'|', '?', '*', '<', '>'};

        for(char symbol : symbols)
            if(filePath.indexOf(symbol) != (-1))
                return check;
        check = true;
        return check;
    }
    public static void inputDataInFile(String filePath, String data, boolean create) throws Exception{
        if((!checkFilePath(filePath)))
            throw new Exception("\nОшибка: не корректный путь к файлу. Дальнейшая работа с записью в файл не возможна." +
                    " Работа программы будет переведена в штатный режим. \n");

        if(create == true){
            String[] cellFilePath = filePath.replace('\\', '/').split("\\/");
            String directory = "";
            for(int i = 0; i < (cellFilePath.length-1); i++)
                directory += cellFilePath[i] + "\\";

            File file = new File(filePath);
            if((!file.exists()) && (!(new File(directory).mkdir())) && (!file.createNewFile()))
                throw new Exception("\nОшибка: файла не существует и файл нельзя создать. Дальнейшая работа с записью в файл не возможна." +
                        " Работа программы будет переведена в штатный режим. \n");
        }

        try(FileWriter fileWriter = new FileWriter(filePath, false)){
            fileWriter.write(data);
        }catch(Exception ex){
            throw new Exception("\nОшибка: файла не существует. Дальнейшая работа с записью в файл не возможна." +
                    " Работа программы будет переведена в штатный режим. \n");
        }
    }

    public static String outputDataFromFile(String filePath) throws Exception{
        if((!checkFilePath(filePath)))
            throw new Exception("\nОшибка: не корректный путь к файлу. Дальнейшая работа с чтением из файла не возможна." +
                    " Работа программы будет переведена в штатный режим. \n");
        String data = "";
        try(FileReader fileReader = new FileReader(filePath)){
            int c = (-1);
            while((c = fileReader.read()) != (-1))
                data += (char)c;
        }catch(Exception ex){
            throw new Exception("\nОшибка: не удалось считать данные из файл. Дальнейшая работа с чтением из файла не возможна." +
                    " Работа программы будет переведена в штатный режим. \n");
        }

        return data;
    }
}
