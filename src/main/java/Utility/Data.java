package Utility;

public class Data {

    public static int numSuppliers(double[][] costs) {
        int numSuppliers = costs.length;
        return numSuppliers;
    }

    public static int numDemandCenters(double[][] costs) {
        int numDemandCenters = costs[0].length;
        return numDemandCenters;
    }

    public static double[] supply() {
        double[] supply = {150, 20, 130};
        return supply;
    }

    public static double[] Demands() {
        double[] Demands = {135, 75, 45, 45};
        return Demands;
    }

    public static double[][] Costs() {
        double[][] costs = {
            {5, 2, 3, 9},
            {7, 1, 12, 4},
            {8, 15, 19, 2},};
        return costs;
    }

}
