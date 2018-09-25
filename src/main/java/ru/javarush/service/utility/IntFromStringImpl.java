package ru.javarush.service.utility;

import org.springframework.stereotype.Component;

@Component
public class IntFromStringImpl implements IntFromString {

    public int recognize(String str) {
        if ((str != null) && (!str.equals(""))) {
            try {
                return Integer.parseInt(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
