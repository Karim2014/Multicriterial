package table;

public class Criterion implements Comparable<Criterion> {

    public enum ASPIRATION {
        MAX {
            @Override
            public double normalize(double X, double max, double min) {
                return (X - min) / (max - min);
            }

            @Override
            public double normalizeZero(double X, double max, double min) {
                return X / max;
            }
        },
        MIN {
            @Override
            public double normalize(double X, double max, double min) {
                return (max - X) / (max - min);
            }

            @Override
            public double normalizeZero(double X, double max, double min) {
                return min / X;
            }
        };

        public abstract double normalize(double X, double max, double min);
        public abstract double normalizeZero(double X, double max, double min);
    }

    private int name;
    private ASPIRATION aspiration;

    public Criterion(int name, ASPIRATION aspiration) {
        this.name = name;
        this.aspiration = aspiration;
    }

    public ASPIRATION getAspiration() {
        return aspiration;
    }

    public int getName() {
        return name;
    }

    @Override
    public String toString() {
        return "C" + name + "->" + aspiration;
    }

    @Override
    public int compareTo(Criterion o) {
        return Integer.compare(name, o.getName());
    }
}
