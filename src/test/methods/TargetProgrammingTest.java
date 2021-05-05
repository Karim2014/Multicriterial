package methods;

import org.junit.Before;
import org.junit.Test;
import table.DecisionTable;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TargetProgrammingTest {

    DecisionTable decisionTable;
    TargetProgramming targetProgramming;

    @Before
    public void setUp() throws Exception {
        decisionTable = DecisionTable.fromFile(new File("input.csv"));
        targetProgramming = new TargetProgramming();
    }

    @Test
    public void testSolve() {
        List<Double> list = targetProgramming.solve(decisionTable);
        list.forEach(System.out::println);
        System.out.println("Минимальный элемент -> " + targetProgramming.findMin(list));

        assertNotNull(list);
        assertEquals(8, list.size());
        assertEquals(0.906, targetProgramming.findMin(list), 0.001d);
        List<Double> expectedList = Arrays.asList(1.258, 1.298, 1.261, 1.313, 1.156, 1.149, 0.906, 1.123);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedList.get(i), list.get(i), 0.001d);
        }
    }

    @Test
    public void testFindAlt() {
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        List<Double> convolution = targetProgramming.solve(decisionTable);
        List<Double> alt = targetProgramming.findAlt(transposedMatrix, convolution);
        assertEquals(Arrays.asList(4.0, 8.0, 1.0, 6.0), alt);

        System.out.println("Матрица решений:");
        transposedMatrix.forEach(System.out::println);
        System.out.println("Оптимальная альтернатива:");
        System.out.println(alt);
    }
}