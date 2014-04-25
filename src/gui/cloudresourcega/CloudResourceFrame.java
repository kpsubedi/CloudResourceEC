/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.cloudresourcega;


import config.cloudresourcega.ConfigData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Random;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author machine
 */
public class CloudResourceFrame extends JFrame implements ActionListener, ListSelectionListener {
    
    //private static String[] items = {"N/A","Compute Type","Storage Type","General Purpose","GPU Type"};
    //private static String[] priceRange = {""};
    private static String[] columnNames = {"vCPUs","vGPUs","RAM","Storage","Network"};
    private static Object[][] data = {
    {new Integer(2), new Integer(1),
     new Integer(4), new Integer(25), new Integer(1)},
    {new Integer(4), new Integer(2),
     new Integer(6), new Integer(3), new Integer(2)},
    {new Integer(8), new Integer(4),
     new Integer(12), new Integer(50), new Integer(3)},
    {new Integer(1), new Integer(1),
     new Integer(2), new Integer(8), new Integer(1)},
    {new Integer(2), new Integer(6),
     new Integer(10), new Integer(30), new Integer(1)}
};
    
    private JPanel runAlgorithmPanel;
    private JComboBox instanceTypesComboBox;
    private JLabel instanceTypeLabel;
    private JButton searchSolutionButton;
    private JButton exitButton;
    private JPanel fitnessPanel;
    private JLabel firstFitness1Label;
    private JTextField fitnessText1;
    private JLabel secondFitnessLabel;
    private JLabel instancePrice;
    private JTextField fitnessText2;
    private JPanel solutionPanel;
    private JTable solutionTable;
    private JScrollPane scrollPane;
    private JLabel priceRangeLabel;
    
    private JTextField userInstanceChoice;
            
    public CloudResourceFrame() {
        //Create and set up the window.
        //MainFrame frame = new MainFrame();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        
        // center frame in screen
        
        setSize(screenWidth, screenHeight);
        setLocation(screenWidth/4, screenHeight/4);
        
        // set frame icon adn title
        
        setTitle("Cloud Resource Optimization Using EC");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridBagLayout());
        runAlgorithmPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        instanceTypeLabel = new JLabel("Instance Type");
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 10;
        c.ipady = 10;
        runAlgorithmPanel.add(instanceTypeLabel, c);
        
        instanceTypesComboBox = new JComboBox(ConfigData.getInstanceTypeNames().toArray());
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 10;
        c.ipady = 10;
        runAlgorithmPanel.add(instanceTypesComboBox,c);
        
        instanceTypesComboBox.addActionListener((ActionListener) this);
        
        instancePrice = new JLabel("Price Range");
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 10;
        c.ipady = 10;
        runAlgorithmPanel.add(instancePrice, c);
        
        priceRangeLabel = new JLabel();
        //priceRangeTextField.setEditable(false);
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 10;
        c.ipady = 10;
        runAlgorithmPanel.add(priceRangeLabel, c);
        
        searchSolutionButton = new JButton("Search");
        searchSolutionButton.setEnabled(Boolean.FALSE);
        searchSolutionButton.setForeground(Color.red);
        searchSolutionButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 10;
        c.ipady = 10;
        runAlgorithmPanel.add(searchSolutionButton,c);
        
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = 10;
        c.ipady = 10;
        runAlgorithmPanel.add(exitButton,c);
        //fitness score
        fitnessPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        firstFitness1Label = new JLabel("First Fitness");
        c1.gridx = 0;
        c1.gridy = 0;
        fitnessPanel.add(firstFitness1Label,c1);
        fitnessText1 = new JTextField(10);
        c1.gridx = 1;
        c1.gridy = 0;
        fitnessPanel.add(fitnessText1,c1);
        
        secondFitnessLabel = new JLabel("Second Fitness");
        c1.gridx = 0;
        c1.gridy = 1;
        fitnessPanel.add(secondFitnessLabel,c1);
        fitnessText2 = new JTextField(10);
        c1.gridx = 1;
        c1.gridy = 1;
        fitnessPanel.add(fitnessText2,c1);
        
        
        //solution display
        solutionPanel = new JPanel(new GridBagLayout());
        solutionTable = new JTable();
        solutionTable.setModel(new DefaultTableModel(data, columnNames));
        scrollPane = new JScrollPane(solutionTable);
        solutionTable.setVisible(true);
        solutionPanel.add(scrollPane);
        
        userInstanceChoice = new JTextField(30);
        userInstanceChoice.setEditable(Boolean.FALSE);
        
        
        GridBagConstraints cc = new GridBagConstraints();
        //cc.anchor = GridBagConstraints.FIRST_LINE_START;
        //cc.fill = GridBagConstraints.VERTICAL;
        //cc.insets = new Insets(0, 0, 0, 0);
        cc.gridx = 0;
        cc.gridy = 0;
        cc.ipadx = 20;
        cc.ipady = 20;
        this.add(runAlgorithmPanel,cc);
        //Display the window.
       
        cc.gridx = 0;
        cc.gridy = 1;
        this.add(fitnessPanel,cc);
        
        cc.gridx = 1;
        cc.gridy = 0;
        this.add(userInstanceChoice,cc);
        cc.gridx = 1;
        cc.gridy = 1;
        //cc.gridwidth = 2;
        //cc.gridheight = 2;
        this.add(solutionPanel,cc);
        
       
        
        this.pack();
        this.setVisible(true);
    }
    
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == exitButton){
            if (JOptionPane.showConfirmDialog(this, "Are you sure?") == JOptionPane.YES_OPTION){
                System.exit(0);
            }           
        }
        if(e.getSource() == instanceTypesComboBox){
           if (instanceTypesComboBox.getSelectedIndex() == 6){
                priceRangeLabel.setText("Free");
                //(vCPU,vGPU,RAM,Storage,Network) Micro Instances
                double microCPU = 1;
                double microGPU = 0;
                double microMemory = 1;
                double microStorage = 40;
                double microNA = 1;
                System.out.println("The vCPU:"+microCPU + "The vGPU:"+microGPU + "RAM:"+microMemory + "Storage:" + microStorage 
                        + "Network:" + microNA);
                double[] microInstance = {microCPU,microGPU,microMemory,microStorage,microNA};
                ConfigData.setMicroWeight(microInstance);
                userInstanceChoice.setText("vCPU:"+microCPU+"  vGPU:"+microGPU+"   RAM:"+microMemory+"   Storage:"+microStorage
                        +"   vNIC:"+microNA);
                
            }
            if(instanceTypesComboBox.getSelectedIndex() == 5){
                int index5 = instanceTypesComboBox.getSelectedIndex();
                priceRangeLabel.setText(Double.toString(ConfigData.getInstanceMinCost(index5 - 1)) + "-"+
                        Double.toString(ConfigData.getInstanceMaxCost(index5 -1)));
                //(vCPU,vGPU,RAM,Storage,Network) General Purpose
                Random rand = new Random();
                double generalCPU = rand.nextInt(7) + 1;
                double generalGPU = 0;
                double generalMemory = rand.nextInt(16) + 4;
                double generalStorage = rand.nextInt(32) + 4;
                double generalNA = 1;
                System.out.println("The vCPU:"+generalCPU + "The vGPU:"+generalGPU + "RAM:"+generalMemory + "Storage:" + generalStorage 
                        + "Network:" + generalNA);
                double[] storageInstance = {generalCPU,generalGPU,generalMemory,generalStorage,generalNA};
                ConfigData.setGeneralPurposeWeight(storageInstance);
                userInstanceChoice.setText("vCPU:"+generalCPU+"  vGPU:"+generalGPU+"   RAM:"+generalMemory+"   Storage:"+generalStorage
                        +"   vNIC:"+generalNA);
            }
            if(instanceTypesComboBox.getSelectedIndex() == 4){
                int index4 = instanceTypesComboBox.getSelectedIndex();
                priceRangeLabel.setText(Double.toString(ConfigData.getInstanceMinCost(index4 - 1)) + "-"+
                        Double.toString(ConfigData.getInstanceMaxCost(index4 -1)));
                //(vCPU,vGPU,RAM,Storage,Network) Storage
                Random rand = new Random();
                double storageCPU = rand.nextInt(28) + 4;
                double storageGPU = 0;
                double storageMemory = rand.nextInt(17) + 15;
                double storageStorage = rand.nextInt(24) + 8;
                double storageNA = 1;
                System.out.println("The vCPU:"+storageCPU + "The vGPU:"+storageGPU + "RAM:"+storageMemory + "Storage:" + storageStorage 
                        + "Network:" + storageNA);
                double[] storageInstance = {storageCPU,storageGPU,storageMemory,storageStorage,storageNA};
                ConfigData.setStorageWeight(storageInstance);
                userInstanceChoice.setText("vCPU:"+storageCPU+"  vGPU:"+storageGPU+"   RAM:"+storageMemory+"   Storage:"+storageStorage
                        +"   vNIC:"+storageNA);
            }
            if(instanceTypesComboBox.getSelectedIndex() == 3){
                int index3 = instanceTypesComboBox.getSelectedIndex();
                priceRangeLabel.setText(Double.toString(ConfigData.getInstanceMinCost(index3 - 1)) + "-"+
                        Double.toString(ConfigData.getInstanceMaxCost(index3 - 1)));
                //create GUP weight (vCPU,vGPU,RAM,Storage,Network)
                Random rand = new Random();
                double gpuCPU = rand.nextInt(3) + 1;
                double gpuGPU = rand.nextInt(8) + 1;
                double gpuMemory = rand.nextInt(12) + 4;
                double gpuStorage = rand.nextInt(24) + 8;
                double gpuNA = 1;
                System.out.println("The vCPU:"+gpuCPU + "The vGPU:"+gpuGPU + "RAM:"+gpuMemory + "Storage:" + gpuStorage 
                        + "Network:" + gpuNA);
                double[] gpuInstance = {gpuCPU,gpuGPU,gpuMemory,gpuStorage,gpuNA};
                ConfigData.setGpuWeight(gpuInstance);
                userInstanceChoice.setText("vCPU:"+gpuCPU+"  vGPU:"+gpuGPU+"   RAM:"+gpuMemory+"   Storage:"+gpuStorage
                        +"   vNIC:"+gpuNA);
            }
            if(instanceTypesComboBox.getSelectedIndex() == 2){
                int index2 = instanceTypesComboBox.getSelectedIndex();
                priceRangeLabel.setText(Double.toString(ConfigData.getInstanceMinCost(index2 - 1)) + "-"+
                        Double.toString(ConfigData.getInstanceMaxCost(index2 - 1)));
                Random rand = new Random();
                double computeCPU = rand.nextInt(7) + 1;
                double computeGPU = rand.nextInt(4) + 1;
                double computeMemory = rand.nextInt(12) + 4;
                double computeStorage = rand.nextInt(24) + 8;
                double computeNA = 1;
                System.out.println("The vCPU:"+computeCPU + "The vGPU:"+computeGPU + "RAM:"+computeMemory + "Storage:" + computeStorage 
                        + "Network:" + computeNA);
                double[] computeInstance = {computeCPU,computeGPU,computeMemory,computeStorage,computeNA};
                ConfigData.setComputeInstance(computeInstance);
                userInstanceChoice.setText("vCPU:"+computeCPU+"  vGPU:"+computeGPU+"   RAM:"+computeMemory+"   Storage:"+computeStorage
                        +"   vNIC:"+computeNA);
                //create compute weight list (vCPU,vGPU,RAM,Storage,Network)
            }
            if(instanceTypesComboBox.getSelectedIndex() == 1){
               priceRangeLabel.setText(Double.toString(ConfigData.getInstanceMinCost(instanceTypesComboBox.getSelectedIndex() - 1)) + "-"+
                        Double.toString(ConfigData.getInstanceMaxCost(instanceTypesComboBox.getSelectedIndex() - 1))); 
               //memory optimized 2-32 15-244 32-640
               Random rand = new Random();
               double memoryCPU = rand.nextInt(14) + 2;
               double memoryGPU = 0;
               double memoryMemory = rand.nextInt(24) + 12;
               double memoryStorage = rand.nextInt(32) + 16;
               double memoryNA = 1;
               System.out.println("The vCPU:"+memoryCPU + "The vGPU:"+memoryGPU + "RAM:"+memoryMemory + "Storage:" + memoryStorage 
                        + "Network:" + memoryNA);
               double[] memoryInstance = {memoryCPU,memoryGPU,memoryMemory,memoryStorage,memoryNA};
                ConfigData.setMemoryWeight(memoryInstance);
                userInstanceChoice.setText("vCPU:"+memoryCPU+"  vGPU:"+memoryGPU+"   RAM:"+memoryMemory+"   Storage:"+memoryStorage
                        +"   vNIC:"+memoryNA);
               
            }
            if(instanceTypesComboBox.getSelectedIndex() == 0){
                priceRangeLabel.setText("");
            }
            
            if(instanceTypesComboBox.getSelectedIndex() > 0){
                searchSolutionButton.setEnabled(Boolean.TRUE);
            }else {
                searchSolutionButton.setEnabled(Boolean.FALSE);
            }
            
        }
        if(e.getSource() == searchSolutionButton){
            if(instanceTypesComboBox.getSelectedIndex() == 6){
                JOptionPane.showMessageDialog(null, instanceTypesComboBox.getSelectedIndex());
                //find all possible solutions and display in table
                Object[][] data6 = {
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)}
                };
                
                //scrollPane.removeAll();
                //scrollPane.add(new JTable(data1, columnNames));
                DefaultTableModel model = (DefaultTableModel)solutionTable.getModel();
                if (model.getRowCount() > 0){
                    for(int i = model.getRowCount() -1; i>-1; i--){
                        model.removeRow(i);
                    }
                }
                //model.addRow(new Object[]{new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)});
                for(int j = 0; j < data6.length; j++){
                    model.addRow(data6[j]);
                }
                
            }
            if(instanceTypesComboBox.getSelectedIndex() == 5){
                //find all possible solutions and display in table
                JOptionPane.showMessageDialog(null, instanceTypesComboBox.getSelectedIndex());
                Object[][] data5 = {
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)}
                };
                
                //scrollPane.removeAll();
                //scrollPane.add(new JTable(data1, columnNames));
                DefaultTableModel model = (DefaultTableModel)solutionTable.getModel();
                if (model.getRowCount() > 0){
                    for(int i = model.getRowCount() -1; i>-1; i--){
                        model.removeRow(i);
                    }
                }
                //model.addRow(new Object[]{new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)});
                for(int j = 0; j < data5.length; j++){
                    model.addRow(data5[j]);
                }
                
            }
            if(instanceTypesComboBox.getSelectedIndex() == 4){
                //find all possible solutions and display in table
               JOptionPane.showMessageDialog(null, instanceTypesComboBox.getSelectedIndex()); 
                Object[][] data4 = {
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)}
                };
                
                //scrollPane.removeAll();
                //scrollPane.add(new JTable(data1, columnNames));
                DefaultTableModel model = (DefaultTableModel)solutionTable.getModel();
                if (model.getRowCount() > 0){
                    for(int i = model.getRowCount() -1; i>-1; i--){
                        model.removeRow(i);
                    }
                }
                //model.addRow(new Object[]{new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)});
                for(int j = 0; j < data4.length; j++){
                    model.addRow(data4[j]);
                }
            }
            if(instanceTypesComboBox.getSelectedIndex() == 3){
                //find all possible solutions and display in table
                JOptionPane.showMessageDialog(null, instanceTypesComboBox.getSelectedIndex());
                Object[][] data3 = {
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)}
                };
                
                //scrollPane.removeAll();
                //scrollPane.add(new JTable(data1, columnNames));
                DefaultTableModel model = (DefaultTableModel)solutionTable.getModel();
                if (model.getRowCount() > 0){
                    for(int i = model.getRowCount() -1; i>-1; i--){
                        model.removeRow(i);
                    }
                }
                //model.addRow(new Object[]{new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)});
                for(int j = 0; j < data3.length; j++){
                    model.addRow(data3[j]);
                }
            }
            if(instanceTypesComboBox.getSelectedIndex() == 2){
                 //find all possible solutions and display in table
                 JOptionPane.showMessageDialog(null, instanceTypesComboBox.getSelectedIndex());
                 Object[][] data2 = {
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)},
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)}
                };
                
                //scrollPane.removeAll();
                //scrollPane.add(new JTable(data1, columnNames));
                DefaultTableModel model = (DefaultTableModel)solutionTable.getModel();
                if (model.getRowCount() > 0){
                    for(int i = model.getRowCount() -1; i>-1; i--){
                        model.removeRow(i);
                    }
                }
                //model.addRow(new Object[]{new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)});
                for(int j = 0; j < data2.length; j++){
                    model.addRow(data2[j]);
                }
                //find all possible solutions and display in table
                for(double dd: ConfigData.getComputeInstance()){
                System.out.println(":::"+dd);
            }
            //JOptionPane.showMessageDialog(null, "Find GA Solution");
           
            }
            if(instanceTypesComboBox.getSelectedIndex() == 1){
                JOptionPane.showMessageDialog(null, instanceTypesComboBox.getSelectedIndex());
                 Object[][] data1 = {
                    {new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)}
                };
                
                //scrollPane.removeAll();
                //scrollPane.add(new JTable(data1, columnNames));
                DefaultTableModel model = (DefaultTableModel)solutionTable.getModel();
                if (model.getRowCount() > 0){
                    for(int i = model.getRowCount() -1; i>-1; i--){
                        model.removeRow(i);
                    }
                }
                //model.addRow(new Object[]{new Integer(2), new Integer(1),new Integer(4), new Integer(25), new Integer(1)});
                for(int j = 0; j < data1.length; j++){
                    model.addRow(data1[j]);
                }
            }
          //create instance type randomlly from range of each alliels
        }
    }
    
    
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CloudResourceFrame();
           }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
