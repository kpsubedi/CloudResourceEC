/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.cloudresourcega;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.core.Variable;

/**
 *
 * @author machine
 */
public class displayResult {
    
    public static void showPopulation(SolutionSet pop){
        System.out.println("The Population Results::");
        System.out.println("Population Size::"+pop.size());
        
        for(int i = 0; i < pop.size(); i++){
            Solution s = pop.get(i);
            Variable[] var = s.getDecisionVariables();
            System.out.println(var[0]+":"+var[1]+":"+var[2]+":"+var[3]+":"+var[4]);
        }
    }
    
}
