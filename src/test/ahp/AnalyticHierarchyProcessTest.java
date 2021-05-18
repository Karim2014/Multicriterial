package ahp;

import org.junit.Test;

import java.util.Scanner;

public class AnalyticHierarchyProcessTest {

    AnalyticHierarchyProcess ahp = new AnalyticHierarchyProcess();
    Scanner scanner = new Scanner(System.in);

    @Test
    public void start() {
        ahp.start(scanner);

    }
}