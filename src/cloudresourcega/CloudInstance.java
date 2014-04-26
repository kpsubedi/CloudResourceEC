//  TSP.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
import config.cloudresourcega.ConfigData;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.encodings.solutionType.PermutationSolutionType;
import jmetal.encodings.variable.Permutation;
import jmetal.util.JMException;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.IntSolutionType;

/**
 * Class representing a TSP (Traveling Salesman Problem) problem.
 */
public class CloudInstance extends Problem {
    private HashMap <Integer , Double> cpu; 
    private HashMap <Integer , Double> gpu;
    private HashMap <Integer , Double> ram;
    private HashMap <Integer , Double> storage;
    private HashMap <Integer , Double> network;
    private double [] instw;
        
    
  /**
   * Creates a new TSP problem instance. It accepts data files from TSPLIB
   * @param filename The file containing the definition of the problem
   */
  public CloudInstance(String solutionType, int attr, double [] uppers, double [] inst) throws FileNotFoundException {
    instw = inst;
    numberOfVariables_  = attr;
    numberOfObjectives_ = 2;
    numberOfConstraints_= 1;
    problemName_        = "CloudInstance";
    upperLimit_ = new double [attr];
    lowerLimit_ = new double [attr];
    for (int i = 0 ; i <attr ; i++){
        lowerLimit_[i] = 1;
        upperLimit_[i] = uppers[i];
    }

    solutionType_ = new IntSolutionType(this) ;
    //Load Database 
    cpu = new HashMap<Integer, Double>();
    cpu = loadDB("input/cpu.txt");
    gpu = loadDB("input/gpu.txt");
    ram = loadDB("input/memory.txt");
    storage = loadDB("input/storage.txt");
    network = loadDB("input/network.txt");
    try {
      if (solutionType.compareTo("Int") == 0)
        solutionType_ = new IntSolutionType(this) ;
      else {
        throw new JMException("Solution type invalid") ;
      }
    } catch (JMException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
   
  
  } // 

  /**
   * Evaluates a solution
   * @param solution The solution to evaluate
   */
  public void evaluate(Solution solution) {
    double fitness1;
    double fitness2;
    // calculate the fitness and at the end 
    //System.out.println("Solution:"+solution);
    
    fitness1 = this.calculatePriceFitness(solution);
    fitness2 = 0.0;
    solution.setObjective(0, -fitness1);// if you want to maximize the fitness use solution.setObjective(0, -fitness)
    solution.setObjective(1, fitness2);
  } // evaluate

public void evaluateConstraints(Solution solution) throws JMException {
    if (solution.getDecisionVariables()[2].getValue()<solution.getDecisionVariables()[3].getValue())
    {
        solution.setOverallConstraintViolation(0);
    }else{
        solution.setOverallConstraintViolation(1);
    }
    
}

public double calculatePriceFitness(Solution solution){
    double cost = 0.0;
    //solline ++;
    Variable[] variable = solution.getDecisionVariables();
    //System.out.println(solution.numberOfVariables());
    //System.out.println("var [0] for solution :" + variable[0] +" line number "+ solline);
    //This is the input from users - GP, CO, MO, SO, GPU
    double[] weightList = new double[5]; 
    //weightList[0] = 4;
    //weightList[1] = 2;
    //weightList[2] = 3;
    //weightList[3] = 1;
    //weightList[4] = 1;
   
    weightList = instw;   
    //double[] weightList = ConfigData.getComputeInstance();
    //System.out.println();
    double[] price = {1,1,1,0,0};
    price[0] = this.cpu.get(Integer.parseInt( variable[0].toString()));
    price[1] = this.gpu.get(Integer.parseInt(variable[1].toString()));
    price[2] = this.ram.get(Integer.parseInt(variable[2].toString()));
    price[3] = this.storage.get(Integer.parseInt(variable[3].toString()));
    price[4] = this.network.get(Integer.parseInt(variable[4].toString()));
    for (int j =0; j< solution.numberOfVariables();j++){
        cost = cost + (price[j] * weightList[j]);
    } 
  return cost;
}

public static HashMap<Integer,Double> loadDB(String fileName) throws FileNotFoundException{
    HashMap<Integer, Double> hashMap = new HashMap<Integer, Double>();
    Scanner sc = new Scanner(new FileReader(fileName));
    while(sc.hasNextLine()){
        String[] value = sc.nextLine().split("\t");
        hashMap.put(Integer.parseInt(value[0]), Double.parseDouble(value[1]));
        System.out.println(value[0]+"::"+value[1]);
    }
    return hashMap;
} //loadDB
  
} // TSP
