import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {


    private static final Map<String, Integer> romanNumerals = new HashMap<>();
    private static boolean isRomanNumerals = false;



    public static void main(String[] args) {
        creatMapOfRomanianNumerals();//
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите арифметическое выражение: ");
            String userInput = scanner.nextLine();
            if (!isUserInputValid(userInput)) {
                throw new IllegalArgumentException("Ввод некорректный");
            }
            String result = calc(userInput);

            System.out.println("Результат: " + outputResult(result));
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    public static int convertRomanToArabic(String string) {
        return romanNumerals.get(string);
    }

    private static String intToRoman(String inputString) {
       int value = Integer.parseInt(inputString);
       if (value <= 0) {
            throw new IllegalArgumentException("Результат с римскими числами должен быть положительным");
        }
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (value >= values[i]) {
                result.append(numerals[i]);
                value -= values[i];
            }
        }
        return result.toString();
    }

    private static String calc(String userInput) {

        String[] tokens = userInput.split(" ");
        int operand1;
        int operand2;
        String operator = tokens[1];
        if (isRomanNumerals) {
            operand1 = convertRomanToArabic(tokens[0]);
            operand2 = convertRomanToArabic(tokens[2]);
        } else {
            operand1 = Integer.parseInt(tokens[0]);
            operand2 = Integer.parseInt(tokens[2]);
        }
        switch (operator) {
            case "+":
                return String.valueOf(operand1 + operand2);

            case "-":
                return String.valueOf(operand1 - operand2);

            case "*":
                return String.valueOf(operand1 * operand2);

            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Деление на ноль.");
                }
                return String.valueOf(operand1 / operand2);

            default:
                throw new IllegalArgumentException("Недопустимая операция. Допустимые операции: +, -, *, /");
        }

    }

    private static boolean isUserInputValid(String string) {
        try {

            String[] tokens = string.split(" ");
            // проверяем прафильность формата ввода 3 элемента должно быть
            if (!(tokens.length == 3)) {
                throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) записанные через пробел");
            }
            String firstValue = tokens[0];
            String secondValue = tokens[2];


            // проверяем являются ли значения 1 и 2 римскими цифрами от 1 до 10

            if (romanNumerals.containsKey(firstValue) && romanNumerals.containsKey(secondValue)) {
                int intValue1 = romanNumerals.get(firstValue);
                int intValue2 = romanNumerals.get(secondValue);
                if (intValue1 <= 10 && intValue2 <= 10) {
                    return isRomanNumerals = true;
                }else {
                    throw new IllegalArgumentException("операнды не входят в диапазон 1- 10");
                }
            }

            // проверяем являются ли символы 1 и 2 арабскими цифрами от 1 до 10
            int operand1 = Integer.parseInt(firstValue);
            int operand2 = Integer.parseInt(firstValue);

            if (!(operand1 >= 1 && operand1 <= 10 && operand2 >= 1 && operand2 <= 10)) {
                throw new IllegalArgumentException("операнды не входят в диапазон 1- 10");
            }
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию " + e.getMessage());
        }
    }

    private static void creatMapOfRomanianNumerals() {
        romanNumerals.put("I", 1);
        romanNumerals.put("II", 2);
        romanNumerals.put("III", 3);
        romanNumerals.put("IV", 4);
        romanNumerals.put("V", 5);
        romanNumerals.put("VI", 6);
        romanNumerals.put("VII", 7);
        romanNumerals.put("VIII", 8);
        romanNumerals.put("IX", 9);
        romanNumerals.put("X", 10);
    }

    private static String outputResult(String result){
        if (!isRomanNumerals){
            return result;
        }
        return intToRoman(result);
    }
}