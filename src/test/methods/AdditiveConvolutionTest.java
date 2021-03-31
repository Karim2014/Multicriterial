package methods;

import org.junit.Before;
import org.junit.Test;
import table.DecisionTable;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AdditiveConvolutionTest {

    private DecisionTable decisionTable;
    private AdditiveConvolution additiveConvolution;

    @Before
    public void setUp() throws Exception {
        decisionTable = DecisionTable.fromFile(new File("input.csv"));
        additiveConvolution = new AdditiveConvolution(Arrays.asList(0.4f, 0.1f, 0.2f, 0.3f));
    }

    @Test
    public void testDoConvolution() {
        List<Double> convolution = additiveConvolution.solve(decisionTable);
        assertNotNull(convolution);
        assertEquals(8, convolution.size());
        assertEquals(0.771d, additiveConvolution.findMax(convolution), 0.001);
        System.out.println(convolution);
        System.out.println("Максимальный элемент -> " + additiveConvolution.findMax(convolution));
    }

    @Test
    public void testFindAlt() {
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        List<Double> convolution = additiveConvolution.solve(decisionTable);
        List<Double> alt = additiveConvolution.findAlt(transposedMatrix, convolution);
        assertEquals(Arrays.asList(4.0, 8.0, 1.0, 6.0), alt);

        System.out.println("Матрица решений:");
        System.out.println(transposedMatrix);
        System.out.println("Оптимальная альтернатива:");
        System.out.println(alt);
    }
}