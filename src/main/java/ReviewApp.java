import javax.swing.*;
import java.awt.*;

public class ReviewApp extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private ReviewP reviewPanel;
    private ReviewT tablePanel;

    public ReviewApp(){
        setTitle("Review Page");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        tablePanel = new ReviewT(this);
        reviewPanel = new ReviewP(this);

        cardPanel.add(reviewPanel,"Review");
        cardPanel.add(tablePanel, "Table");

        add(cardPanel);
        setVisible(true);
    }
    public void showCard(String name){
        cardLayout.show(cardPanel,name);
    }
    public ReviewT getTablePanel(){
        return tablePanel;
    }
}

