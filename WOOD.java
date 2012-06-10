import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class WOOD extends JPanel {


    private int width, height;
    
    private JFrame appWindow;
    private TREE[][] tree;
    
    private Timer ticker;
    
    private int time;
    
    
    // Variables that need to be set for a proper simulation
    
    private int sTime = 250; // Time in milliseconds for one period/ year
    private int sTreesStart = 3; // Number if trees at the beginning
    private int sDeath = 10; // Trees die at this age
    private int sAgeN = 2;
    private int sAgeNew = 1; // sexual maturity 
    private int sNumTrees = 20;
    
//    public WOOD(int widthWood, int heightWood) {        
    public WOOD() {        
        
        height = 40;
        width = 80;
        
        
        appWindow = new JFrame();
        appWindow.setTitle("Gugel World - Wood Simulation");
        appWindow.setVisible(true);
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appWindow.setSize((width*10 + 50), (height*10 + 50));
        appWindow.setLocationRelativeTo(null);
        appWindow.setResizable(false);
        
        appWindow.add(this);
        
        tree  = new TREE[width][height];
        
        time = 0;
        
        ticker = new Timer(sTime, new ActionListener() {
                                        public void actionPerformed(ActionEvent evt) {
                                            next();
                                            time = time + 1;
                                            System.out.println("Time " + time);
                                            getAverageAge();
                                        }
                                     });
        
        createRandomStart(sTreesStart);
        startSimulation();
    }
    
    private void createRandomStart(int treesStart) {
        Random randomGenerator = new Random();
        for (int i = 0; i <  treesStart; i++) {
            int randomX = randomGenerator.nextInt(width);
            int randomY = randomGenerator.nextInt(height);
            
            plantTree(randomX, randomY);
        }
    }
    
    public void startSimulation() {
        ticker.start();
    }
    
    public void stopSimulation() {
        ticker.stop();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // setBackground(new Color(139, 69, 19));
        for (int r=0; r<tree.length; r++) {
            for (int c=0; c<tree[r].length; c++) {
                if (tree[r][c] != null) {
                    g.setColor(new Color((tree[r][c].getAge()*((int) 255/10)), 255, 0));
                    g.fillRect(r*10, c*10, 10, 10);
                } else {
                    
                }
            }
        }
    }
    
    public void next() {
        Random randomGenerator = new Random();
        for (int r=0; r<tree.length; r++) {
            for (int c=0; c<tree[r].length; c++) {
                if (tree[r][c] != null) {

                    if (tree[r][c].getAge() == sDeath) {
                        tree[r][c] = null; // Trees only get 10 years old
                        System.out.println("Tree died (too old)");
                    } else {

                        tree[r][c].olden(); // All trees get one year older
                        
                           
                        // trees die if they are too close to old, huge trees/ if they are much younger than their neighbourhood                            
                        int age = getAgeOfTree(r-1, c-1) + getAgeOfTree(r, c-1) + getAgeOfTree(r+1, c-1) + 
                            getAgeOfTree(r+1, c) + getAgeOfTree(r+1, c+1) + getAgeOfTree(r, c+1) + 
                            getAgeOfTree(r-1, c+1) + getAgeOfTree(r-1, c);
                            
                        if ((age/8)/sAgeN > tree[r][c].getAge()) {
                            tree[r][c] = null;
                            System.out.println("Tree died (too old neighbourhood)");
                        }
                            
                        
                            
                        
                        if (tree[r][c] != null) {
                            if (tree[r][c].getAge() > sAgeNew) {
                                switch(randomGenerator.nextInt(sNumTrees - tree[r][c].getAge())) {
                                    case 0: tree[r][c] = null;
                                    System.out.println("Tree died (random)");
                                    break;

                                    case 1: plantTree(r-1, c-1);
                                    break;
                                    case 2: plantTree(r, c-1);
                                    break;
                                    case 3: plantTree(r+1, c-1);
                                    break;
                                    case 4: plantTree(r+1, c);
                                    break;
                                    case 5: plantTree(r+1, c+1);
                                    break;
                                    case 6: plantTree(r, c+1);
                                    break;
                                    case 7: plantTree(r-1, c+1);
                                    break;
                                    case 8: plantTree(r-1, c);
                                    break;
                                }
                            }
                        }
                        
                        /*
                         * 123
                         * 8T4
                         * 765
                         * 
                         */
                        
                    }
                    
                } else {
                    
                }
            }
        }
        
        this.removeAll();
        this.repaint();
    }
    
    public void plantTree(int xTree, int yTree) {
        // check if coordinates are valid
        if (xTree < width && xTree >= 0 && yTree < height && yTree >= 0) {
            // check if there is already a tree at the same position
            if (tree[xTree][yTree] == null) {
                tree[xTree][yTree] = new TREE(xTree, yTree);
            }
        }
    }
    
    public double getAverageAge() {
        double averageAge;
        double age = 0;
        int count = 0;
        for (int r=0; r<tree.length; r++) {
            for (int c=0; c<tree[r].length; c++) {
                if (tree[r][c] != null) {
                    age = age + tree[r][c].getAge();
                    count = count + 1;
                }
            }
        }
        averageAge = age/count*1.0;
        System.out.println("The average age is " + averageAge);
        System.out.println("There are currently " + count + " trees");
        return averageAge;
    }
    
    
    public int getAgeOfTree(int xTree, int yTree) {
        if (xTree < width && xTree >= 0 && yTree < height && yTree >= 0) {
            if (tree[xTree][yTree] != null) {
                return tree[xTree][yTree].getAge();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
