package frontEnd;

import javax.swing.JFrame;

public class MainWindow extends JFrame{
    
    public MainWindow()
    {
        this.setTitle("Graph Visualizer");
        this.setSize(500, 500);

        VisualizerPanel visualizerPanel = new VisualizerPanel();

        this.add(visualizerPanel);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    

}
