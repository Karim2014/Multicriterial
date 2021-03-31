package methods;

import org.junit.Before;
import org.junit.Test;
import table.DecisionTable;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MultiplicativeConvolutionTest {

    DecisionTable decisionTable;
    MultiplicativeConvolution multiplicativeConvolution;

    @Before
    public void setUp() throws Exception {
        decisionTable = DecisionTable.fromFile(new File("input.csv"));
        multiplicativeConvolution = new MultiplicativeConvolution(Arrays.asList(0.3f, 0.1f, 0.2f, 0.4f));
    }

    @Test
    public void testDoConvolution() {
        List<Double> convolution = multiplicativeConvolution.solve(decisionTable);
        System.out.println(convolution);
        System.out.println("Максимальный элемент -> " + multiplicativeConvolution.findMax(convolution));
        assertNotNull(convolution);
        assertEquals(8, convolution.size());
        assertEquals(0.720d, multiplicativeConvolution.findMax(convolution), 0.001);
    }

    @Test
    public void testFindAlt() {
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        List<Double> convolution = multiplicativeConvolution.solve(decisionTable);
        List<Double> alt = multiplicativeConvolution.findAlt(transposedMatrix, convolution);
        assertEquals(Arrays.asList(4.0, 8.0, 1.0, 6.0), alt);

        System.out.println("Матрица решений:");
        System.out.println(transposedMatrix);
        System.out.println("Оптимальная альтернатива:");
        System.out.println(alt);
    }
}