package programGUI;

import java.util.Scanner;

public class MenuClass {
    public static boolean isNumber(String text){
        if(text.length() == 0)
            return false;
        int countMinus = 0;
        boolean flag = true;
        for(int i = 0; (i < text.length()) && (flag); i++){
            CharSequence s = Character.toString(text.charAt(i));

            if((!("0123456789".contains((CharSequence)Character.toString(text.charAt(i)))))
            && (text.charAt(i) != '-'))
                flag = false;
            else if(text.charAt(i) == '-')
                countMinus++;
        }

        if(!flag)
            return false;

        flag = (countMinus <= 1);
        return flag;
    }

    public static int getCorrectValue(int min, int max){
        Scanner sc = new Scanner(System.in);
        String number = "";

        System.out.print("Введите число: ");
        number = sc.nextLine();
        while((!isNumber(number)) || (!(((Integer.valueOf(number)) >= min) && ((Integer.valueOf(number)) <= max)))){
            System.out.print("Введите корректное целое число из диапазона [ " + min + " ; " + max + " ]: ");
            number = sc.nextLine();
        }

        int value = Integer.valueOf(number);
        return value;
    }

    public void startMenu() throws Exception {
        do{
            outputMenu();
            System.out.print("\nПродолжить работу?(0 - нет, 1 - да)\n");
        }while(getCorrectValue(0, 1) == 1);
    }

    public void outputMenu() throws Exception {
        System.out.println("Выберите задание, которое необходимо решить: ");
        System.out.println("1. Определить, является ли строка подстрокой исходной строки.");
        System.out.println("2. Найти длину указанного слова в предложении.");
        System.out.println("3. Вывести центральное слово(слова) в предложении.");
        System.out.println("4. Выход из программы");

        int numberAnswer = getCorrectValue(1, 4);
        if(numberAnswer == 4)
            return;

        System.out.println("\n\nВыберите способ считывания данных: ");
        System.out.print("Требования к входным данным из файла: исходная строка и дополнытельная информация(подстрока / порядковый номер слова)\n");
        System.out.print("должны располагаться в один столбец и разделяться символом перевода коретки(\\n или Enter)\n\n");
        System.out.println("1. Считать входные данные из файла.");
        System.out.println("2. Считать входные данные из консоли.");

        int numberInput = getCorrectValue(1, 2);

        System.out.println("\n\nВыберите способ вывода данных: ");
        System.out.println("1. Вывести данные в файл");
        System.out.println("2. Вывести данные в консоль");

        int numberOutput = getCorrectValue(1, 2);

        Scanner sc = new Scanner(System.in);
        String text = "", answer = "";
        StringClass obj = new StringClass();

        switch(numberAnswer){
            case 1:{
                String substr = "";
                if(numberInput == 1){
                    System.out.print("\nВведите путь к файлу, из которого нужно считать входные данные(исходную строку и подстроку разделённые \\n (Enter)): ");
                    String filePath = sc.nextLine();
                    try{
                        String[] str = FileIO.outputDataFromFile(filePath).split("\n");
                        text = str[0];
                        substr = str[1];
                        System.out.print("\nДанные успешно считаны из файла!\n");
                    }catch(Exception ex){
                        System.out.print(ex.getMessage());
                        System.out.print("\nВвод данных через консоль...\n\n");
                        System.out.print("\nВведите исходную строку: ");
                        text = sc.nextLine();
                        System.out.print("Введите подстроку: ");
                        substr = sc.nextLine();
                    }
                }else{
                    System.out.print("\nВведите исходную строку: ");
                    text = sc.nextLine();
                    System.out.print("Введите подстроку: ");
                    substr = sc.nextLine();
                }
                obj.setText(text);

                if(obj.substringInString(substr))
                    answer = "Подстрока \"" + substr + "\" присутствует в строке " + "\"" + text + "\"";
                else
                    answer = "Подстрока \"" + substr + "\" не присутствует в строке " + "\"" + text + "\"";

                if(numberOutput == 2){
                    System.out.print(answer);
                    break;
                }

                System.out.print("\nВведите путь к файлу, в который нужно вывести результат работы программы: ");
                String filePath = sc.nextLine();
                try{
                    FileIO.inputDataInFile(filePath, answer, true);
                    System.out.print("\nДанные успешно сохранены в файл!\n");
                }catch (Exception ex){
                    System.out.print(ex.getMessage());
                    System.out.println("\nВывод результата на консоль: \n");
                    System.out.print(answer);
                }

                break;
            }
            case 2:{
                int number = 0;
                if(numberInput == 1){
                    System.out.print("\nВведите путь к файлу, из которого нужно считать входные данные(исходную строку и порядковый номер слова разделённые \\n (Enter)): ");
                    String filePath = sc.nextLine();
                    try{
                        String[] str = FileIO.outputDataFromFile(filePath).split("\n");
                        text = str[0];
                        number = Integer.valueOf(str[1]);
                        System.out.print("\nДанные успешно считаны из файла!\n");
                    }catch(NumberFormatException ex){
                        System.out.print("\nОшибка: порядковый номер слова считан не корректно. Данные в файле не соответствуют требованиям входных данных.\n" +
                                "Работа программы будет переведена в штатный режим. \n");
                        System.out.print("\nВвод данных через консоль...\n\n");
                        System.out.print("\nВведите исходную строку: ");
                        text = sc.nextLine();
                        System.out.print("Введите порядковый номер в предложении длину которого необходимо найти\n");
                        number = getCorrectValue(1, text.trim().length());
                    }catch(Exception ex){
                        System.out.print(ex.getMessage());
                        System.out.print("\nВвод данных через консоль...\n\n");
                        System.out.print("\nВведите исходную строку: ");
                        text = sc.nextLine();
                        System.out.print("Введите порядковый номер в предложении длину которого необходимо найти\n");
                        number = getCorrectValue(1, text.trim().length());
                    }
                }else{
                    System.out.print("\nВведите исходную строку: ");
                    text = sc.nextLine();
                    System.out.print("Введите порядковый номер в предложении длину которого необходимо найти\n");
                    number = getCorrectValue(1, text.trim().length());
                }
                obj.setText(text);

                int lengthWord = obj.lengthDefineWord(number);
                answer = "Слово с порядковым номером " + String.valueOf(number) + " имеет длину " + String.valueOf(lengthWord);

                if(numberOutput == 2){
                    System.out.print(answer);
                    break;
                }

                System.out.print("\nВведите путь к файлу, в который нужно вывести результат работы программы: ");
                String filePath = sc.nextLine();
                try{
                    FileIO.inputDataInFile(filePath, answer, true);
                    System.out.print("\nДанные успешно сохранены в файл!\n");
                }catch (Exception ex){
                    System.out.print(ex.getMessage());
                    System.out.println("\nВывод результата на консоль: \n");
                    System.out.print(answer);
                }

                break;
            }
            case 3:{
                int number = 0;
                if(numberInput == 1){
                    System.out.print("\nВведите путь к файлу, из которого нужно считать входные данные(исходную строку и порядковый номер слова разделённые \\n (Enter)): ");
                    String filePath = sc.nextLine();
                    try{
                        text = FileIO.outputDataFromFile(filePath);
                        System.out.print("\nДанные успешно считаны из файла!\n");
                    }catch(Exception ex){
                        System.out.print(ex.getMessage());
                        System.out.print("\nВвод данных через консоль...\n\n");
                        System.out.print("\nВведите исходную строку: ");
                        text = sc.nextLine();
                    }
                }else{
                    System.out.print("\nВведите исходную строку: ");
                    text = sc.nextLine();
                }
                obj.setText(text);

                answer = "Центральное слово/слова в предложении: " + obj.centerWords();

                if(numberOutput == 2){
                    System.out.print(answer);
                    break;
                }

                System.out.print("\nВведите путь к файлу, в который нужно вывести результат работы программы: ");
                String filePath = sc.nextLine();
                try{
                    FileIO.inputDataInFile(filePath, answer, true);
                    System.out.print("\nДанные успешно сохранены в файл!\n");
                }catch (Exception ex){
                    System.out.print(ex.getMessage());
                    System.out.println("\nВывод результата на консоль: \n");
                    System.out.print(answer);
                }

                break;
            }
        }
    }
}
