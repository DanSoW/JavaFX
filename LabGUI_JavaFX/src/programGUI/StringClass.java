package programGUI;

public class StringClass {

    private String text;

    public StringClass(){ //Конструктор по умолчанию
        text = "";
    }

    public StringClass(String _text){ //Конструктор с параметром
        _text = _text.trim();
        text = _text;
    }

    public StringClass(StringClass obj){ //Конструктор копирования
        text = obj.text;
    }

    public void setText(String _text){ //Метод для задания данных
        _text = _text.trim();
        text = _text;
    }

    public String getText(){ //Метод для взятия данных
        return text;
    }

    public boolean substringInString(String substring) throws Exception{
        /*
        a. Определите, является ли одна строка подстрокой другой.
	    */

        substring = substring.trim();

        boolean flag = false;

        for(int i = 0; (i < text.length()) && (!flag); i++)
            if(text.charAt(i) == substring.charAt(0)){
                int count = 0;
                for(int k = 0; k < substring.length(); k++)
                    if(substring.charAt(k) == text.charAt(k + i))
                        count++;
                if(count == substring.length())
                    flag = true;
            }

        return flag;
    }

    public int lengthDefineWord(int number) throws Exception{
        /*
        b. Найти длину указанного слова в предложении.
        */

        int counter = 1;
        int lengthWord = 0;

        for(int i = 0; i < text.length(); i++){
            if((i >= 1) && ((text.charAt(i-1) == ' ') || (text.charAt(i-1) == ','))
                    && ((text.charAt(i) != ' ') && (text.charAt(i) != ',')))
                counter++;

            if((counter == number) && ((text.charAt(i) != ' ') && (text.charAt(i) != ',')))
                lengthWord++;
        }

        return lengthWord;
    }

        public String centerWords () throws Exception{
        //c. Выведите из строки, содержащей слова, разделенные пробелами и запятыми, центральное слово
        // (если в предложении два централь-ных слова, выведите оба).

        String results = "";
        int countWords = 1;

        for(int i = 1; i < text.length(); i++)
            if(((text.charAt(i - 1) == ' ') || (text.charAt(i-1) == ','))
                    && ((text.charAt(i) != ' ') && (text.charAt(i) != ',')))
                countWords++;

        int number = (int)(countWords / 2), counter = 1;

        if((countWords % 2) != 0)
            number++;

        for(int i = 0; i < text.length(); i++){
            if((i >= 1) && ((text.charAt(i-1) == ' ') || (text.charAt(i-1) == ','))
                    && ((text.charAt(i) != ' ') && (text.charAt(i) != ',')))
                counter++;
            if((counter == number) && ((text.charAt(i) != ' ') && (text.charAt(i) != ',')))
                results += text.charAt(i);
        }

        if((countWords % 2) == 0){
            results += ' ';
            counter = 1;
            number++;
            for(int i = 0; i < text.length(); i++){
                if((i >= 1) && ((text.charAt(i-1) == ' ') || (text.charAt(i-1) == ','))
                        && ((text.charAt(i) != ' ') && (text.charAt(i) != ',')))
                    counter++;
                if((counter == number) && ((text.charAt(i) != ' ') && (text.charAt(i) != ',')))
                    results += text.charAt(i);
            }
        }

        return results;
    }
}
