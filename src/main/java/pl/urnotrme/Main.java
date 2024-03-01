package pl.urnotrme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Basia", "Michal", "Tomek",
                "Agnieszka", "Ola", "Kasia");
        Greeter greeter = new Greeter();
        greeter.greet("Kamila");

        List<String> ladies = new ArrayList<String>();
        for (String name : names) {
            if (name.endsWith("a")) {
                ladies.add(name);
            }
        }
        for (String ladyName: ladies) {
            greeter.greet(ladyName);
        }

        names.stream()
                .filter(name -> name.endsWith("a")) // python way lambda name: name[-1] == "a"
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .forEach(greeter::greet);
    }
}
