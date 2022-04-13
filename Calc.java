import java.util.Arrays;
import java.util.Scanner;
public class Calc {
    public static String[] RomeNumbers = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
            "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
            "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
            "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L",
            "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
            "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
            "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
            "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
            "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};

    public static void main(String[] args) {



        Scanner in = new Scanner(System.in);
        System.out.print("Введите выражение:");
        String str = in.nextLine();

        String ANSWER = "";
        try {
            ANSWER = Control(str);
            System.out.println("ОТВЕТ:    "+ANSWER);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static String Control(String str) throws WrongExpression, DifferentSystems, TooBig, RomeLessThanZero{
        //********* проверяется число операторов *************
        String operator = new String();
        int counter = 0;

        for(char i:str.toCharArray()){
            if(i == '+' || i == '-'|| i == '*'|| i == '/' ){
                counter++;
                operator = "\\"+i;
            }
        }

        if(counter != 1){
            throw new DifferentSystems("Ошибка!Выражение записано неверно, должно быть 2 операнда и 1 оператор(+,-,/,*)");
        }
        //***************************************************

        //********** определяется система счета ***********
        String count_system = new String();
        count_system = CheckSystem(str, operator);
        String ans = new String();
        //***************************************

        //*********** вычисления ****************
        if(count_system.equals("arab")){
            ans = CountArab(str, operator);
        }else if(count_system.equals("rome")){
            ans = CountRome(str, operator);
        }else{throw new WrongExpression("Ошибка! Используются одновременно разные системы счисления или выражение записано неверно");}
        return ans;
        //******************************************
    }
    //CheckSystem определяет систему счета или, если запись ошибочна, возвращает строку "error"
    public static String CheckSystem(String str, String operator){
        String word_1 = str.split(operator)[0].trim();
        String word_2 = str.split(operator)[1].trim();

        if(Arrays.asList(RomeNumbers).contains(word_1)){
            word_1 = "rome";}else{
            try{Integer.parseInt(word_1);
                word_1 = "arab";
            }catch(NumberFormatException n){
                word_1 = "error_1";
            }
        }

        if(Arrays.asList(RomeNumbers).contains(word_2)){
            word_2 = "rome";}
        else{
        try{Integer.parseInt(word_2);
                word_2 = "arab";
            }catch(NumberFormatException n){
                word_2 = "error_2";
            }
        }
        if(word_1.equals(word_2)){
            return word_1;
        }else{return "error";}

    }

    // CountArab - выполняет операции с числами в Арабской системе счета, если введены слишком большие числа бросает исключение TooBig
    public static String CountArab(String str, String operator ) throws  TooBig{
        int num1 = Integer.parseInt(str.split(operator)[0].trim());
        int num2 = Integer.parseInt(str.split(operator)[1].trim());
        int ans = 0;
        if(num1 <= 10 && num1 > 0 && num2 > 0 && num2 <=10){
            switch(operator){
                case ("\\+"):
                    ans = num1 + num2;
                    break;
                case ("\\-"):
                    ans = num1 - num2;
                    break;
                case ("\\*"):
                    ans = num1*num2;
                    break;
                case ("\\/"):
                    ans = num1/num2;
                    break;
            }
        }else{throw new TooBig("Ошибка! Числа должны быть в интервале(0, 11)");}
        return Integer.toString(ans);
    }
    //CountRome - выполняет операции с числами в Римской системе счета, если введены слишком большие числа бросает TooBig, если результат операции <= 0
    //бросает RomeLessThanZero
    public static String CountRome(String str, String operator) throws TooBig, RomeLessThanZero{
        String[] allowed = Arrays.copyOfRange(RomeNumbers, 0, 10);
        String num1 = str.split(operator)[0].trim();
        String num2 = str.split(operator)[1].trim();
        int ans = 0;
        if(Arrays.asList(allowed).contains(num1) && Arrays.asList(allowed).contains(num2)){
            int n1 = Arrays.asList(allowed).indexOf(num1)+1;
            int n2 = Arrays.asList(allowed).indexOf(num2)+1;
            switch(operator){
                case ("\\+"):
                    ans = n1 + n2;
                    break;
                case ("\\-"):
                    ans = n1 - n2;
                    break;
                case ("\\*"):
                    ans = n1*n2;
                    break;
                case ("\\/"):
                    ans = n1/n2;
                    break;
            }
            if(ans < 0){throw new RomeLessThanZero("Ошибка! Результат вычислений меньше или равен 0. У римлян нет таких чисел");}
        }else{throw new TooBig("Ошибка! Числа должны быть в интервале(0, 11)");}
        return RomeNumbers[ans-1];
    }
}
class DifferentSystems extends Exception{
    DifferentSystems(String str){
        super(str);
    }

}
class WrongExpression extends Exception{
    WrongExpression(String str){
        super(str);
    }
}

class TooBig extends Exception{
    TooBig(String str){
        super(str);
    }
}
class RomeLessThanZero extends Exception{
    RomeLessThanZero(String str){
        super(str);
    }
}


