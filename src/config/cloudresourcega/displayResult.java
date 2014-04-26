/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.cloudresourcega;

import java.util.ArrayList;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.core.Variable;
import jmetal.util.JMException;
/**
 *
 * @author machine
 */
public class displayResult {
    
    public static void showPopulation(SolutionSet pop) throws JMException{
        System.out.println("The Population Results::");
        System.out.println("Population Size::"+pop.size());
        ArrayList<Double> instanceSol;
        //= new ArrayList<Double>();
        
        for(int i = 0; i < pop.size(); i++){
            Solution s = pop.get(i);
            Variable[] var = s.getDecisionVariables();
            instanceSol = new ArrayList<Double>();
            for(int j = 0; j < var.length; j++){
                instanceSol.add(var[j].getValue());
            }
            System.out.println(instanceSol.get(0)+":"+
                    instanceSol.get(1)+":"+
                    instanceSol.get(2)+":"+
                    instanceSol.get(3)+":"+
                    instanceSol.get(4));
            //System.out.println(var[0]+":"+var[1]+":"+var[2]+":"+var[3]+":"+var[4]);
            //System.out.println("Chromosome Length"+var.length);
            
        }
    }
    
}
