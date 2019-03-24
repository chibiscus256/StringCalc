import java.util.*;

public class FSCalc {

    public static String calc(String a, String b, String operator) {

        switch (operator) {
            case "+":
                return Double.valueOf(a) + Double.valueOf(b) + "";
            case "~":
                return Double.valueOf(a) - Double.valueOf(b) + "";
            case "*":
                return Double.valueOf(a) * Double.valueOf(b) + "";
            case "/":
                return Double.valueOf(a) / Double.valueOf(b) + "";
        }
        return null;
    }

    //чтобы отличать  знак "-" от оператора "-", заменяем оператор на тильду.

    private static String minusDiminish(String s) {

        String result = "" + s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if ((s.charAt(i) == '-') && ("+-*/(~".indexOf(s.charAt(i - 1)) == -1))
                /* если предыдущий символ не является оператором, меняем его на тильду.. */
                result += '~';
            else result += s.charAt(i);
        }
        return result;
    }

    public static String[] removeConsists(String[] inputArray, String toRemove) { //удаление из массива

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(inputArray));
        while (arrayList.contains(toRemove)) {
            arrayList.remove(toRemove);
        }
        String[] newArray = new String[arrayList.size()];
        arrayList.toArray(newArray);
        return newArray;
    }

    private static int symbolCount(StringBuilder inStr, char symbol) {
        int count = 0;
        for (int i = 0; i < inStr.length(); i++) {
            if (inStr.charAt(i) == symbol) count++;
        }
        return count;
    }

    // уравнивание количества скобок, недостающие открывающие скобки помещаются в начало строки, закрывающие в конец. repeat() не хочет работать
    private static StringBuilder bracketsEqualize(StringBuilder inString) {
        int difference = symbolCount(inString, '(') - symbolCount(inString, ')');

        if (difference > 0) {
            for (int i = 0; i < difference; i++) {
                inString = inString.insert(inString.length(), ")");
            }
        } else if (difference < 0) {
            for (int i = 0; i < difference; i++)
                inString = inString.insert(0, "(");
        }
        return inString;
    }

    private static String cogit(String expression) {

        String num[];
        int index = -1;
        Character priorityOperator = '/';  // default
        String operators;
        while (!((operators = expression.replaceAll("[^*+/~]", "")).isEmpty()))
        // пока имеются операторы
        {

            if ((index = operators.indexOf('/')) == -1) {        // выбираем приоритетный оператор
                priorityOperator = '*';
                if ((index = operators.indexOf('*')) == -1) {
                    priorityOperator = operators.charAt(0);
                    index = operators.indexOf(priorityOperator);
                }
            }
            num = expression.split("[^0-9\\-.]"); // разделение выражения по всем знакам, кроме цифр и точки
            num = removeConsists(num, "");

            // заменяем строковое выражение на строковый результат.

            try {
                expression = expression.replaceFirst(num[index] + "\\" + priorityOperator + num[index + 1], calc(num[index], num[index +1], "" + priorityOperator));
            }catch (Exception e) {
                return "wrong input!";
            }
        }
        return expression;
    }

    public static String bracketsOut(StringBuilder s, int forInd) { //раскрытие скобок

        while ((s.indexOf("(") != -1) || (s.indexOf(")") != -1)) {

            if ((s.indexOf(")") > forInd) && (s.charAt(forInd) == '(') && (s.substring(forInd + 1, s.indexOf(")")).indexOf("(") == -1)) { /* проверка на отсутствие скобок в выражении между самыми левыми разными скобками: */

                //вытаскиваем выражение во вложенных скобках:

                String newBracket = s.substring(forInd + 1, s.indexOf(")"));
                s = s.replace(forInd, s.indexOf(")") + 1, bracketsOut(new StringBuilder(newBracket), forInd));
            }
            forInd++;
            if (forInd>s.indexOf(")")) forInd = 0;
        }
        return cogit(s.toString());
    }

    public static String performComputation(String expr) {

        if (!expr.replaceAll("[0-9\\+/().*-]", "").isEmpty()) return "UNSUPPORTED INPUT"; ///проверка на недопустимые символы ввода, в т.ч пробелы

        StringBuilder a = new StringBuilder(minusDiminish(expr));
        String output = bracketsOut(bracketsEqualize(a), a.indexOf("("));
        return output;

        }
    }
