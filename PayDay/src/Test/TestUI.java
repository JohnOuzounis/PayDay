package Test;


import javax.swing.JFrame;


import Model.*;
import Controller.*;
import View.*;

import org.junit.Test;

public class TestUI 
{

    @Test
	public void testPlayerPanel()
	{
		JFrame frame = new JFrame();
        PlayerPanel panel = new PlayerPanel(new Player(), new Controller(null));

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		while (frame.isActive());
	}

    @Test
	public void testOptionPanel()
	{
		OptionDialog o = new OptionDialog("title", "text", "option_approve"," option_cancel", "unicef.png");
        System.out.println(o.GetOption());

        o = new OptionDialog("title", "text", "option_approve"," option_cancel", "unicef.png");
        System.out.println(o.GetOption());

        o = new OptionDialog("title", "text", "option_approve"," option_cancel", "unicef.png");
        System.out.println(o.GetOption());
        
        System.exit(0);
	}

    @Test
    public void testInfoBoxPanel()
    {
        JFrame frame = new JFrame();
        InfoBoxPanel panel = new InfoBoxPanel(new Controller(null));

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		while (frame.isActive());
    }

    @Test
    public void testBoardPanel()
    {
        JFrame frame = new JFrame();
        BoardPanel panel = new BoardPanel(new Controller(null));

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		while (frame.isActive());
    }

    @Test
    public void testGamePanel()
    {
        JFrame frame = new JFrame();
        GamePanel panel = new GamePanel(new Controller(null));

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		while (frame.isActive());
    }
    
}
