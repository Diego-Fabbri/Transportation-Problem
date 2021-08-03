
package com.mycompany.transportation_problem;

import Utility.Data;
import ilog.concert.IloException;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Main {
    public static void main(String[] args) throws IloException, FileNotFoundException{
     System.setOut(new PrintStream("Transportation_Problem.log"));
    double[][] costs = Data.Costs();
   int m = Data.numSuppliers(costs);
   int n = Data.numDemandCenters(costs);
   double [] supply= Data.supply();
   
   double [] demands = Data.Demands();
   TP_Model model = new TP_Model(m,n,costs,supply,demands);
   model.solve();
    
    
    
    
    
    
    
    
    
    
    }
}
