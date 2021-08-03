package com.mycompany.transportation_problem;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVarType;
import ilog.concert.IloObjective;
import ilog.concert.IloObjectiveSense;
import ilog.cplex.IloCplex;

public class TP_Model {

    protected IloCplex model;

    protected int n;
    protected int m;
    protected double[][] costs;
    protected double[] supply;
    protected double[] demands;
    protected IloIntVar[][] x;
    protected boolean Feasibility;

    TP_Model(int m, int n, double[][] costs, double[] supply, double[] demands) throws IloException {
        this.n = n;
        this.m = m;
        this.costs = costs;
        this.supply = supply;
        this.demands = demands;
        this.model = new IloCplex();
        this.x = new IloIntVar[m][n];
        this.Feasibility = Feasibility_Check();
    }

    private boolean Feasibility_Check() {
        double total_demand = 0;
        for (int j = 0; j < n; j++) {
            total_demand += demands[j];

        }
        double total_supply = 0;
        for (int i = 0; i < m; ++i) {
            total_supply += supply[i];
        }

        if (total_demand <= total_supply) {

            return true;

        }

        return false;

    }

    protected void addVariables() throws IloException {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                x[i][j] = (IloIntVar) model.numVar(0, Integer.MAX_VALUE, IloNumVarType.Int, "x[" + i + "][" + j + "]");
            }
        }

    }

    //The following code creates the constraints for the problem.
    protected void addConstraints() throws IloException {
// Constrain (2)
        for (int i = 0; i < m; i++) {
            IloLinearNumExpr expr_2 = model.linearNumExpr();
            for (int j = 0; j < n; j++) {
                expr_2.addTerm(x[i][j], 1);
            }
            model.addLe(expr_2, supply[i]);
        }

        // Constrain (3)
        for (int j = 0; j < n; j++) {
            IloLinearNumExpr expr_3 = model.linearNumExpr();
            for (int i = 0; i < m; i++) {
                expr_3.addTerm(x[i][j], 1);
            }
            model.addEq(expr_3, demands[j]);
        }
    }
    //The following code creates the objective function for the problem.

    protected void addObjective() throws IloException {
        IloLinearNumExpr objective = model.linearNumExpr();

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; j++) {
                objective.addTerm(x[i][j], costs[i][j]);

            }
        }

        IloObjective Obj = model.addObjective(IloObjectiveSense.Minimize, objective);
    }

    public void solve() throws IloException {
        addVariables();
        addObjective();
        addConstraints();
        model.exportModel("Transportation_Problem.lp");

        for (int i = 0; i < m; ++i) {
            int pos_i = i + 1;
            System.out.println("Supplier: " + pos_i + " makes " + supply[i] + " units of product");
        }
        for (int j = 0; j < n; j++) {
            int pos_j = j + 1;
            System.out.println("Demand Center: " + pos_j + " requires " + demands[j] + " units of product");

        }

        model.solve();

        if (Feasibility == true) {
            double total_demand = 0;
            for (int j = 0; j < n; j++) {
                total_demand += demands[j];

            }
            double total_supply = 0;
            for (int i = 0; i < m; ++i) {
                total_supply += supply[i];
            }

            System.out.println("Feasibility condition is satisfied");
            if (total_demand == total_supply) {
                System.out.println("Total demand is equal to total supply");
                System.out.println("Total demand is: " + total_demand);
                System.out.println("Total supply is: " + total_supply);

            } else {
                System.out.println("Total demand is less than total supply");
                System.out.println("Total demand is: " + total_demand);
                System.out.println("Total supply is: " + total_supply);
            }

            if (model.getStatus() == IloCplex.Status.Feasible
                    | model.getStatus() == IloCplex.Status.Optimal) {
                System.out.println("Solution status = " + model.getStatus());

                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println("Total Cost = " + model.getObjValue());
                System.out.println();
                for (int i = 0; i < m; ++i) {
                    int pos_i = i + 1;
                    System.out.println("From Supply Center " + pos_i);
                    for (int j = 0; j < n; ++j) {
                        if (model.getValue(x[i][j]) != 0) {
                            int pos_j = j + 1;
                            System.out.println("---->to demand center " + pos_j + " flows " + model.getValue(x[i][j]) + " and related cost is = " + model.getValue(x[i][j])*costs[i][j]);
                        }
                    }
                      System.out.println();
                }
            } else {
                System.out.println("The problem status is:" + model.getStatus());
            }

        } else {
            System.out.println("Feasibility condition is not satisfied");
            double total_demand = 0;
            for (int j = 0; j < n; j++) {
                total_demand += demands[j];

            }
            double total_supply = 0;
            for (int i = 0; i < m; ++i) {
                total_supply += supply[i];
            }

            System.out.println("Total demand is: " + total_demand);
            System.out.println("Total supply is: " + total_supply);;
        }

    }

}
