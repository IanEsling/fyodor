package uk.org.fyodor;

import org.junit.After;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTest {

    private static boolean print = System.getenv("DEV") != null;

    private final List<Object> printMe = new ArrayList<>();

    @After
    public void printEverything() {
        if (print) {
            for (Object o : printMe) {
                System.out.println(o);
            }
        }
    }

    protected void print(Object o) {
        if (print) {
            printMe.add(o);
        }
    }
}
