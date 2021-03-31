package methods;

import org.junit.Before;
import org.junit.Test;
import table.DecisionTable;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MaximinTest {

    DecisionTable decisionTable;
    Maximin maximin;

    @Before
    public void setUp() throws Exception {
        decisionTable = DecisionTable.fromFile(new File("input.csv"));
        maximin = new Maximin();
    }

    @Test
    public void testSolve() {
        List<Double> list = maximin.solve(decisionTable);
        System.out.println(list);
        System.out.println("Максимальный элемент -> " + maximin.findMax(list));
        assertNotNull(list);
        assertEquals(8, list.size());
        assertEquals(0.25d, maximin.findMax(list), 0.001);
    }

    @Test
    public void testFindAlt() {
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        List<Double> convolution = maximin.solve(decisionTable);
        List<Double> alt = maximin.findAlt(transposedMatrix, convolution);
        System.out.println("Матрица решений:");
        transposedMatrix.forEach(System.out::println);

        System.out.println("Оптимальная альтернатива:");
        System.out.println(alt);

        assertEquals(Arrays.asList(-3.0, 4.0, 2.0, 5.0), alt);
    }
}