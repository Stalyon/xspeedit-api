package oui.sncf.xspeedit.entity;

public class CalculatorComparator {

    private String boxesOptimizedCalculator;
    private String boxesActualCalculator;
    private Integer nbBoxesOptimalCalculator;
    private Integer nbBoxesActualCalculator;
    private Integer nbBoxesSaved;

    /**
     * Constructeur complet.
     *
     * @param boxesOptimizedCalculator
     * @param boxesActualCalculator
     * @param nbBoxesOptimalCalculator
     * @param nbBoxesActualCalculator
     * @param nbBoxesSaved
     */
    public CalculatorComparator(String boxesOptimizedCalculator, String boxesActualCalculator,
                                Integer nbBoxesOptimalCalculator, Integer nbBoxesActualCalculator,
                                Integer nbBoxesSaved) {
        this.boxesOptimizedCalculator = boxesOptimizedCalculator;
        this.boxesActualCalculator = boxesActualCalculator;
        this.nbBoxesOptimalCalculator = nbBoxesOptimalCalculator;
        this.nbBoxesActualCalculator = nbBoxesActualCalculator;
        this.nbBoxesSaved = nbBoxesSaved;
    }

    public String getBoxesOptimizedCalculator() {
        return boxesOptimizedCalculator;
    }

    public void setBoxesOptimizedCalculator(String boxesOptimizedCalculator) {
        this.boxesOptimizedCalculator = boxesOptimizedCalculator;
    }

    public String getBoxesActualCalculator() {
        return boxesActualCalculator;
    }

    public void setBoxesActualCalculator(String boxesActualCalculator) {
        this.boxesActualCalculator = boxesActualCalculator;
    }

    public Integer getNbBoxesOptimalCalculator() {
        return nbBoxesOptimalCalculator;
    }

    public void setNbBoxesOptimalCalculator(Integer nbBoxesOptimalCalculator) {
        this.nbBoxesOptimalCalculator = nbBoxesOptimalCalculator;
    }

    public Integer getNbBoxesActualCalculator() {
        return nbBoxesActualCalculator;
    }

    public void setNbBoxesActualCalculator(Integer nbBoxesActualCalculator) {
        this.nbBoxesActualCalculator = nbBoxesActualCalculator;
    }

    public Integer getNbBoxesSaved() {
        return nbBoxesSaved;
    }

    public void setNbBoxesSaved(Integer nbBoxesSaved) {
        this.nbBoxesSaved = nbBoxesSaved;
    }
}
