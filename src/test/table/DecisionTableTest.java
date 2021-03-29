package table;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DecisionTableTest {

    DecisionTable decisionTable;

    @Before
    public void setUp() throws Exception {
        decisionTable = DecisionTable.fromFile(new File("input.csv"));
    }

    @Test
    public void testToList() {
        List l = decisionTable.toList();
        assertNotNull(l);
        System.out.println(l);
    }

    /**
     * Загрузка значений из файла
     */
    @Test
    public void testLoadFromFile() {
        assertNotNull(decisionTable);
        assertEquals(4, decisionTable.getCriterion().size());
        System.out.println(decisionTable.getCriterion());
    }

    @Test
    public void testTransposeToList() {
        List<List<Double>> transposed = decisionTable.transposeToList();
        assertEquals(8, transposed.size());
        assertEquals(4, transposed.get(0).size());
        System.out.println(transposed);
    }

    /***
     * Тест нормализации значений
     */
    @Test
    public void testNormalize() {
        decisionTable.normalize();
        Criterion criterion = decisionTable.getCriterion()
                .keySet()
                .stream()
                .filter(criterion1 -> criterion1.getName() == 2)
                .findFirst()
                .get();
        assertEquals(Arrays.asList(0.4d, 0.8d, 0.0d, 0.2d, 0.6d, 0.8d, 1.0d, 0.4d),
               decisionTable.getCriterion().get(criterion));
        System.out.println("Нормализованные критерии");
        System.out.println(decisionTable.getCriterion());
    }

    @Test
    public void name() {
        List<Integer> a = Arrays.asList(1,2,3,4);
        a = a.stream().map(item -> item = item+1).collect(Collectors.toList());
        System.out.println(a);

        HashMap<Integer, List<Double>> map = new HashMap<>();
        map.put(1, Arrays.asList(1d,2d,3d,4d,5d));
        map.forEach((integer, doubles) -> {
            doubles = Arrays.asList(10d,11d,12d,13d,14d);
        });
    }
}