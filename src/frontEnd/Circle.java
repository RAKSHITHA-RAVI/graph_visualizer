package frontEnd;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;

public class Circle {

    public Circle(Graphics2D g2d, int x, int y, Integer nodeId, boolean[] isVisited)
    {
        if(isVisited[nodeId])
            g2d.setPaint(new Color(0x66f1e1));
        else
            g2d.setPaint(new Color(0xf7c0c6));

        g2d.fillOval(x, y, 50, 50);

        g2d.setPaint(Color.black);
        g2d.setFont(new Font(null, Font.BOLD, 20));
        g2d.drawString(nodeId.toString(), x+20, y+30);
    }

    public Circle(Graphics2D g2d, int x, int y, Integer nodeId, boolean isSelected)
    {        
        g2d.setPaint(new Color(0x0d879f));
        g2d.fillOval(x, y, 50, 50);
        g2d.setPaint(Color.black);
        g2d.setFont(new Font(null, Font.BOLD, 20));
        g2d.drawString(nodeId.toString(), x+20, y+30);
    }
    
}
