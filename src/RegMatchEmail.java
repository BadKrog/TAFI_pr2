import javax.xml.stream.events.StartDocument;
import java.util.*;

/**
 * Правила создания email.
 * User-name:
 *      Допускаются использовать только латинские буквы, цифры, знак подчеркивания, точку и дефис.
 *      Имя должно начинаться с латинской буквы или цифры.
 *      Имя должно заканчиваться латинской буквой или цифрой.
 *      Имя не должно содержать идущие друг за другом знак подчеркивая, точку и дефис.
 * Server:
 *      Допускаются использовать только латинские буквы, цифры и дефис.
 *      Имя должно начинаться и заканчиваться латинской буквой или цифрой.
 *      Имя может содержать точки, разделяя доменные имена, которые по отдельности должны подчиняться
 *      2-м первым правилам.
 */

public class RegMatchEmail {

    public static final int START = 0;
    public static final int SYMBOL1 = 1;
    public static final int SPEC_SIMBOL = 2;
    public static final int DOG = 3;
    public static final int SYMBOL2 =4;
    public static final int DASH = 5;
    public static final int DOT = 6;


    public static void main(String[] args) {
        // Добавляем в symbol латинские буквы и цифры
        List<Character> symbols = new ArrayList<>();
        char start = '0';
        while(start<='z'){
            if ((start>='0'&&start<='9')||
                (start>='A'&&start<='Z')||
                (start>='a'&&start<='z')){

                symbols.add(start);
            }
            start++;
        }

        // Создаем список со специальными символами
        List<Character> specSymbols = Arrays.asList('.', '-', '_');

        // описываем состояния автомата
        Set<Integer> endStates = Collections.singleton(SYMBOL2);
        StateMachine sm = new StateMachine(START, endStates);
        sm.add(START, symbols, SYMBOL1);
        sm.add(SYMBOL1, symbols, SYMBOL1);
        sm.add(SYMBOL1, specSymbols, SPEC_SIMBOL);
        sm.add(SPEC_SIMBOL, symbols, SYMBOL1);
        sm.add(SYMBOL1, '@', DOG);
        sm.add(DOG, symbols, SYMBOL2);
        sm.add(SYMBOL2, symbols, SYMBOL2);
        sm.add(SYMBOL2, '-', DASH);
        sm.add(DASH, symbols, SYMBOL2);
        sm.add(SYMBOL2, '.', DOT);
        sm.add(DOT, symbols, SYMBOL2);

        // Поиск всех вхождений в строке:
        String[] str = {"Oleg_Kulikov.@mail.ru",
                        "_Pavel1999@mail.ru",
                        "Georgiy..Galina-Morozova@gmail.com",
                        "Mariy*&43%@mail.ru",
                        "Hello@-vin.ru",
                        "Zarina@ma-il.td.less",
                        "anton.zubreev@mail.ru"};
        for(int i=0; i<str.length; i++){
            sm.findAll(str[i]);
        }
    }
}
