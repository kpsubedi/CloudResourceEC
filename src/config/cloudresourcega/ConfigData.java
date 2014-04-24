/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.cloudresourcega;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author machine
 */
public class ConfigData {
    
    private static final List<String> instanceTypeNames = Arrays.asList(new String[]{"Select","Compute Type","GPU Type","Storage Type","General Purpose","Micro Type"});
    private static final List<Double> INSTANCE_MIN_COSTS = Arrays.asList(new Double[]{400.0, 300.0, 200.0, 100.0, 0.0});
    private static final List<Double> INSTANCE_MAX_COSTS = Arrays.asList(new Double[]{1400.0, 1300.0, 1200.0, 1100.0, 0.0});
      
    private static double[] computeWeight;
    private static double[] gpuWeight;
    private static double[] storageWeight;
    private static double[] generalPurposeWeight;
    private static double[] microWeight;
    
    public static List<String> getInstanceTypeNames(){
        return instanceTypeNames;
    }
    
    public static int getInstancesCount(){
        return instanceTypeNames.size();
    }
    
    public static double getInstanceMinCost(int instanceType){
        return INSTANCE_MIN_COSTS.get(instanceType);
    }
    
    public static double getInstanceMaxCost(int instanceType){
        return INSTANCE_MAX_COSTS.get(instanceType);
    }
    
    public static void setComputeInstance(double[] computeInstance){
        computeWeight = new double[5];
        computeWeight = computeInstance;
    }
    
    public static double[] getComputeInstance(){
        return computeWeight;
    }

    /**
     * @return the gpuWeight
     */
    public static double[] getGpuWeight() {
        return gpuWeight;
    }

    /**
     * @param aGpuWeight the gpuWeight to set
     */
    public static void setGpuWeight(double[] aGpuWeight) {
        gpuWeight = aGpuWeight;
    }

    /**
     * @return the generalPurposeWeight
     */
    public static double[] getGeneralPurposeWeight() {
        return generalPurposeWeight;
    }

    /**
     * @param aGeneralPurposeWeight the generalPurposeWeight to set
     */
    public static void setGeneralPurposeWeight(double[] aGeneralPurposeWeight) {
        generalPurposeWeight = aGeneralPurposeWeight;
    }

    /**
     * @return the microWeight
     */
    public static double[] getMicroWeight() {
        return microWeight;
    }

    /**
     * @param aMicroWeight the microWeight to set
     */
    public static void setMicroWeight(double[] aMicroWeight) {
        microWeight = aMicroWeight;
    }

    /**
     * @return the storageWeight
     */
    public static double[] getStorageWeight() {
        return storageWeight;
    }

    /**
     * @param aStorageWeight the storageWeight to set
     */
    public static void setStorageWeight(double[] aStorageWeight) {
        storageWeight = aStorageWeight;
    }
    
}
