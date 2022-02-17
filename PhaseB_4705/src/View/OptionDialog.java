package View;

import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Instances of this class create a new window that displays some info an image and 
 * where the user can choose between one or two options
 */
public class OptionDialog 
{
    private int option = 0;

    /**
     * Opens a new window and displays some information,
     * if option_cancel is null only one option is displayed
     * <p>Precondition: <p>a) option_approve should not be null</p> 
     *                  <p>b) imageName should not be null</p>
     * </p>
     * @param title
     * @param text
     * @param option_approve
     * @param option_cancel
     * @param imageName
     */
    public OptionDialog(String title, String text, String option_approve, String option_cancel, String imageName)
    {
        if (imageName == null || option_approve == null)
            throw new NullPointerException("image name or approve option are null");
            
        JFrame frame = new JFrame();

        LinkedList<Object> options = new LinkedList<Object>();
        options.add(option_approve);
        if (option_cancel != null)
            options.add(option_cancel);
            
        int choise = JOptionPane.showOptionDialog(frame, text, title, JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE,
                                                 new ImageIcon(this.getClass().getClassLoader().getResource("resources/images/" + imageName)),
                                                 (Object[])options.toArray(), option_approve);
        if (choise == JOptionPane.OK_OPTION)
            option = 1;
        else if (choise == JOptionPane.CLOSED_OPTION)
            option = -1;
    }

    /**
     * @return <p>Returns 1 for approve option, 
     *            0 for cancel option and -1 for no option</p>
     */
    public int GetOption()
    {
        return option;
    }
}
