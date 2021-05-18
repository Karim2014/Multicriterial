package methods;

import org.junit.Before;
import org.junit.Test;
import table.DecisionTable;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MainCriterionTest {

    private DecisionTable decisionTable;
    private MainCriterion mainCriterion;

    @Before
    public void setUp() throws Exception {
        decisionTable = DecisionTable.fromFile(new File("input.csv"));
        mainCriterion = new MainCriterion(decisionTable, 3);
    }

    @Test
    public void testFindOpt() {
        System.out.println("Оптимальные значения:");
        System.out.println(mainCriterion.findOpt());
        assertEquals(Arrays.asList(7d,1d,1d,7d), mainCriterion.findOpt());
    }

    @Test
    public void findOptReverse() {
        System.out.println("Обратные оптимальные значения:");
        System.out.println(mainCriterion.findOptReverse());
        assertEquals(Arrays.asList(-7d,8d,6d,0d), mainCriterion.findOptReverse());
    }

    @Test
    public void testSolve() {
        mainCriterion.setLimits(new double[] {0d, 8d, 3d, 0d});
        System.out.println("Оптимальная альтернатива");
        System.out.println(mainCriterion.solve());
        assertEquals(Arrays.asList(4d, 8d, 1d, 6d), mainCriterion.solve());
    }
}