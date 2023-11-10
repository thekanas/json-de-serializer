package by.stolybko;

import java.util.ArrayList;
import java.util.List;

public class Temp {
    public static void main(String[] args) throws ClassNotFoundException {
        List<String> list = new ArrayList<>();
        Class<?> listClass = list.getClass();
        String typeName = listClass.getName();
        System.out.println(typeName);
        //Class<?> elementClass = Class.forName(typeName.substring(typeName.indexOf("<") + 1, typeName.indexOf(">")));

    }
}
