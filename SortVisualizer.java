// package javaapplication1;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class sortingArea extends JPanel {
    //    private static final long serialVersionUID = 1L;
    private final int WIDTH = 1000, HEIGHT = WIDTH * 9 / 16;
    private  int SIZE = 70;
    private  float BAR_WIDTH = (float)WIDTH / SIZE;
    private float[] bar_height = new float[SIZE];
    private SwingWorker<Void,Void> shuffler, sorter;
    private int current_index, traversing_index;
     int min_ss_idx=SIZE;
     int comp_bs_idx=SIZE;


    sortingArea() throws InterruptedException {
        setBackground(Color.WHITE);
        setBounds(0,HEIGHT/8,WIDTH,HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        initBarHeight();
        initShuffler();
//        insrtSorter();

    }
    public void sor (int x) throws InterruptedException {
//        setBackground(Color.WHITE);
//        setBounds(0,HEIGHT/8,WIDTH,HEIGHT);
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        initBarHeight();
//        initShuffler();
        switch (x) {
            case 1 -> insrtSorter();
            case 2 -> selectSorter();
            case 3 -> bubbleSorter();
            default -> {
            }
        }

        initShuffler();




//      initBarHeight();
//        insrtSorter();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        Rectangle2D.Float bar;
        g2d.setColor(new Color(150,120,225));
        for(int i = 0; i < SIZE; i++) {
            bar = new Rectangle2D.Float(i * BAR_WIDTH, HEIGHT-bar_height[i], BAR_WIDTH, bar_height[i]);
            g2d.fill(bar);
        }        

        if(min_ss_idx<SIZE) 
       {
           g.setColor(Color.RED);
         g.drawLine((int)(min_ss_idx * BAR_WIDTH),(int)HEIGHT-(int)bar_height[min_ss_idx],(int)WIDTH,(int)HEIGHT-(int)bar_height[min_ss_idx]);
         
         g2d.setColor(new Color(50,125,25));
        bar = new Rectangle2D.Float(min_ss_idx * BAR_WIDTH,
                HEIGHT-bar_height[min_ss_idx],
                BAR_WIDTH,
                bar_height[min_ss_idx]);
        g2d.fill(bar);
         
       }
        
        g2d.setColor(Color.GREEN);
        bar = new Rectangle2D.Float(traversing_index * BAR_WIDTH,
                HEIGHT-bar_height[traversing_index],
                BAR_WIDTH,
                bar_height[traversing_index]);
        g2d.fill(bar);        
        
        g2d.setColor(Color.LIGHT_GRAY);
        bar = new Rectangle2D.Float(current_index * BAR_WIDTH,
                HEIGHT-bar_height[current_index],
                BAR_WIDTH,
                bar_height[current_index]);
        g2d.fill(bar);
        
       
    }

    private void insrtSorter() throws InterruptedException {
        sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
//                min_ss_idx=SIZE;
                for(current_index = 1; current_index < SIZE; current_index++) 
                {
                    traversing_index = current_index;
                    while(traversing_index > 0 && bar_height[traversing_index] < bar_height[traversing_index - 1]) 
                    {

                        swap(traversing_index, traversing_index - 1);
                        traversing_index--;
                        if(SIZE>=70)
                            Thread.sleep(3);
                        else if(SIZE<50)
                            Thread.sleep(10);
                        else
                            Thread.sleep(7);

                        repaint();
                    }
                }
                current_index = 0;
                traversing_index = 0;

                return null;
            }
        };
    }
    
     private void selectSorter()
    {
        sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                for(current_index = 0; current_index < SIZE; current_index++) {
                    min_ss_idx=current_index;
                    for(traversing_index=current_index+1;traversing_index<SIZE;traversing_index++)
                    {
                        if(bar_height[traversing_index]<bar_height[min_ss_idx])
                            min_ss_idx=traversing_index;
                        if(SIZE>=70)
                            Thread.sleep(3);
                        else if(SIZE<50)
                            Thread.sleep(10);
                        else
                            Thread.sleep(7);
                        repaint();
                    }
                    swap(current_index,min_ss_idx);
                    repaint();
                }
                current_index = 0;
                traversing_index = 0;
                min_ss_idx=SIZE;

                return null;
            }
        };

    }

    private void bubbleSorter()
    {
        sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                current_index=SIZE-1;
                for(int i=0; i < SIZE; i++) 
                {
                    current_index=SIZE-1-i;

                    for(traversing_index=0;traversing_index<SIZE-1-i;traversing_index++)
                    {
                        comp_bs_idx=traversing_index+1;
                        if(bar_height[traversing_index]>bar_height[comp_bs_idx])
                            swap(traversing_index,comp_bs_idx);
                        repaint();
                        if(SIZE>=70)
                            Thread.sleep(3);
                        else if(SIZE<50)
                            Thread.sleep(10);
                        else
                            Thread.sleep(7);
                        repaint();
                    }
                    
                }
                current_index = 0;
                traversing_index = 0;
                comp_bs_idx=SIZE;

                return null;
            }
        };
    }
     
     private void initShuffler() throws InterruptedException {
        shuffler = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                int middle = SIZE / 2;
                for(int i = 0, j = middle; i < middle; i++, j++) {
                    int random_index = new Random().nextInt(SIZE);
                    swap(i, random_index);

                    random_index = new Random().nextInt(SIZE);
                    swap(j, random_index);

                    Thread.sleep(0);
                    repaint();
                }

                return null;
            }

            @Override
            public void done() {
                super.done();
                sorter.execute();
            }
        };
        shuffler.execute();
    }

    public void initBarHeight() {
        float interval = (float)HEIGHT / SIZE;
        for(int i = 0; i < SIZE; i++)
        {
            int random_height = new Random().nextInt(HEIGHT-200);

            bar_height[i] = 25+random_height;

        }
    }
    public void newBarHeight() throws InterruptedException {
        float interval = (float)HEIGHT / SIZE;
        for(int i = 0; i < SIZE; i++)
        {
            int random_height = new Random().nextInt(HEIGHT-200);

            bar_height[i] = 25+random_height;
            repaint();

        }
        initShuffler();
    }

    private void swap(int indexA, int indexB) {
        float temp = bar_height[indexA];
        bar_height[indexA] = bar_height[indexB];
        bar_height[indexB] = temp;
    }
   
    public void stoper()
    {
        sorter.cancel(true);
        repaint();
    }

    public void valueInit()
    {
        min_ss_idx=SIZE;
        traversing_index=0;
    }
    public void setSize(int x)
    {
        SIZE=x;    
        BAR_WIDTH = (float)WIDTH / SIZE;
        comp_bs_idx=SIZE;min_ss_idx=SIZE;bar_height = new float[SIZE];
    }
    


}


class navPanel extends JPanel implements ActionListener
{
    private final int WIDTH = 1000, HEIGHT = WIDTH / 16;
    JButton sortButton1=new JButton();
    JButton sortButton2=new JButton();
    JButton sortButton3=new JButton();
    
    private sortingArea sortaa;


    navPanel(sortingArea sora ) throws InterruptedException {
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBounds(10,0,WIDTH,HEIGHT);
        setLayout(new GridLayout(1,4,15,15));
        this.sortaa=sora;

        sortButton1.setText("Insertion Sort");
        sortButton1.addActionListener(this);
        sortButton2.setText("Selection Sort");
        sortButton2.addActionListener(this);
        sortButton3.setText("Bubble Sort");
        sortButton3.addActionListener(this);
        
        add(sortButton1);
        add(sortButton2);
        add(sortButton3);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sortButton1) {
            try {
                sortaa.sor(1);
                buttonDisabler();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource()==sortButton2) {
            try {
                sortaa.sor(2);
                buttonDisabler();


            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource()==sortButton3) {
            try {
                sortaa.sor(3);
                buttonDisabler();

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public void buttonDisabler()
    {
        sortButton1.setEnabled(false);
        sortButton2.setEnabled(false);
        sortButton3.setEnabled(false);
        
    }
    public void buttonEnabler()
    {
        sortButton1.setEnabled(true);
        sortButton2.setEnabled(true);
        sortButton3.setEnabled(true);
        
        
    }
}

class bottomPanel extends JPanel implements ActionListener
{
    private final int WIDTH = 1000, HEIGHT = WIDTH / 16;
    JButton newArr=new JButton("Create new array");
    JSlider sizeSlider=new JSlider(30,100,70);
    JLabel l=new JLabel();
    private sortingArea sortObj;
    private navPanel navObj;
//    private ChangeListener l;
    
    
    bottomPanel(sortingArea sortObj,navPanel navObj)
    {
        setBackground(Color.WHITE);
        setBounds(0,0,WIDTH,HEIGHT);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        newArr.setBounds(0, 0, WIDTH/2-100, HEIGHT);
        newArr.setPreferredSize(new Dimension(WIDTH/2-100, HEIGHT*2/3));
//        setLayout(new GridLayout(1,2,15,15));
        this.sortObj=sortObj;
        this.navObj=navObj;
        l.setText("Array Size is " + sizeSlider.getValue());
        sizeSlider.setBounds(WIDTH/2+25, HEIGHT/3, WIDTH/3,HEIGHT/3 );
        sizeSlider.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/2));
        sizeSlider.setPaintTrack(true);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.addChangeListener(new ChangeListener()
        {public void stateChanged(ChangeEvent e)
            {
                sortObj.setSize(sizeSlider.getValue());
                l.setForeground(Color.RED);
                l.setText("Array Size is " + sizeSlider.getValue()+". Click new Array button");
                navObj.buttonDisabler();
            }
        });
        newArr.addActionListener(this);
        add(newArr);
        add(sizeSlider);
        add(l);
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==newArr)
            try 
            {
                navObj.buttonEnabler();

                l.setForeground(Color.BLACK);
                l.setText("Array Size is " + sizeSlider.getValue());
                sortObj.newBarHeight();
                sortObj.stoper();
                sortObj.valueInit();
            }
            catch (InterruptedException ex) {
            Logger.getLogger(bottomPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}



public class SortVisualizer {
    public static void main(String args[]) throws InterruptedException {

        JFrame frame = new JFrame("Sorting Algorithm visualizer");
        frame.setLayout(null);
//            frame.setSize(1000,10000/16);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sortingArea sor=new sortingArea();
        navPanel nav=new navPanel(sor);

        bottomPanel bo=new bottomPanel(sor,nav);
        

        frame.setContentPane(sor);
        frame.add(nav);
        frame.add(bo);

        frame.validate();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}

    

