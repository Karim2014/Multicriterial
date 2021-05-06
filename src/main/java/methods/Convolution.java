package methods;

import table.DecisionTable;

public abstract class Convolution extends BaseMethod {

    protected float[] alphas;

    /**
     * Конструктор. Выполняет инициализацию коэффициентов важности
     * Сумма коэффициентов должна быть 1 (100%)
     * @param alphas
     */
    public Convolution(DecisionTable table, float[] alphas) {
        super(table);
        float sum = 0;
        for (int i = 0; i < alphas.length; i++) {
            sum += alphas[i];
        }
        if (sum != 1) {
            // TODO Сделать обработку исключения
        }
        this.alphas = alphas;
    }



}
