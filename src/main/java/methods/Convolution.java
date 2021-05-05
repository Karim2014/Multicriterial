package methods;

public abstract class Convolution extends BaseMethod {

    protected float[] alphas;

    /**
     * Конструктор. Выполняет инициализацию коэффициентов важности
     * Сумма коэффициентов должна быть 1 (100%)
     * @param alphas
     */
    public Convolution(float[] alphas) {
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
