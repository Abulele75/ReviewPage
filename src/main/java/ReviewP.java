import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReviewP extends JPanel {


    JPanel pnlSouth, pnlNorth,pnlCenter;
    JLabel lblTitle, lblSelectTutor, lblSelectSub,lblRating, lblReview,lblImg;
    JComboBox CBOsubjects, CBOtutors;
    JTextArea txtArea;
    JButton btnSubmit, btnShowReviews;
    private JLabel[] stars = new JLabel[5];
    private int currentRating = 0;
    private Icon Fullstar, Empty;
    private Icon Msc;
    private ReviewApp parentApp;
    // ReviewT tableFrame = new ReviewT();


    public ReviewP( ReviewApp app) {
        this.parentApp = app;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(176, 224, 230));


        lblTitle = new JLabel("Leave a review");
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        Image img = new ImageIcon(getClass().getResource("/Msc.jpeg")).getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
        Msc = new ImageIcon(img);
        lblImg = new JLabel(Msc);
        lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);




        String[] subjects = {"Select Subject","Mathematics", "Mathematical literacy", "Physical Science", " Life science", "Agricultural Science", "Accounting"};
        CBOsubjects = new JComboBox(subjects);


        String[] tutors = {"Select Tutor","Abulele", "Tony", "Inam", "Leeroy", "Lindi"};
        CBOtutors = new JComboBox(tutors);



        lblSelectSub = new JLabel("Select a Subject");
        lblSelectSub.setForeground(Color.BLACK);
        lblSelectTutor = new JLabel("Select a Tutor");
        lblSelectTutor.setForeground(Color.BLACK);

        txtArea = new JTextArea(2,20);

        JPanel starPanel = new JPanel(new FlowLayout());
        starPanel.setBackground(Color.decode("#090979"));
        starPanel.setPreferredSize(new Dimension(200, 40));
        Image fullImg = new ImageIcon(getClass().getResource("/Fullstar.png")).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        Fullstar = new ImageIcon(fullImg);

        Image emptyImg = new ImageIcon(getClass().getResource("/Empty.png")).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        Empty = new ImageIcon(emptyImg);


        for (int i = 0; i < stars.length; i++) {
            final int index = i;
            stars[i] = new JLabel(Empty);
            stars[i].setPreferredSize(new Dimension(40, 40)); // make stars larger
            stars[i].setBorder(BorderFactory.createLineBorder(Color.WHITE)); // for debug
            stars[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    setRating(index + 1);
                }
            });
            starPanel.add(stars[i]);



            lblRating = new JLabel("Rating");
            lblRating.setForeground(Color.BLACK);


            lblReview = new JLabel("Comment");
            lblReview.setForeground(Color.BLACK);
            //  txtArea = new JTextArea(2,20);
            // JLabel lblImg = new JLabel(Msc);







            pnlSouth = new JPanel();
            pnlNorth = new JPanel();
            pnlCenter = new JPanel();

       /* String[] subjects = {"Select Subject","Mathematics", "Mathematical literacy", "Physical Science", " Life science", "Agricultural Science", "Accounting"};
        CBOsubjects = new JComboBox(subjects);


        String[] tutors = {"Select Tutor","Abulele", "Tony", "Inam", "Leeroy", "Lindi"};
        CBOtutors = new JComboBox(tutors); */


      /*  Image img = new ImageIcon(getClass().getResource("/Msc.jpeg")).getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
        Msc = new ImageIcon(img);
         lblImg = new JLabel(Msc);*/




        }

        //btnSubmit = new JButton("Submit");
        btnSubmit = new RoundedButton("Submit", 30);
        btnSubmit.setForeground(Color.BLACK);


        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 if(CBOsubjects.getSelectedIndex()==0||CBOtutors.getSelectedIndex()==0|| txtArea.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill in the missing fields");
                    return;
}
              try( Connection con = DBConnection.derbyConnection()){
                  int StudentId = 1;
                  String subjects = CBOsubjects.getSelectedItem().toString();
                  int SubjectCode = -1;
                  
                  PreparedStatement ps1 = con.prepareStatement(
                "SELECT subject_code FROM Subject WHERE subject_name = ?");
            ps1.setString(1, subjects);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                SubjectCode = rs1.getInt("subject_code");
            }
            
                  
                  String tutors = CBOtutors.getSelectedItem().toString();
                  int TutorId = -1;
                   PreparedStatement ps2 = con.prepareStatement(
                "SELECT tutor_id FROM Tutor WHERE name = ?");
            ps2.setString(1, tutors);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                TutorId = rs2.getInt("tutor_id");
            }
                  
                  int Rating = currentRating;
                  String comments = txtArea.getText();
                  
                  PreparedStatement insert = con.prepareStatement(
                "INSERT INTO Review (student_id, subject_id, tutor_id, rating, comment) VALUES (?, ?, ?, ?, ?)");
            insert.setInt(1, StudentId);
            insert.setInt(2, SubjectCode);
            insert.setInt(3, TutorId);
            insert.setInt(4, Rating);
            insert.setString(5, comments);

            int rows = insert.executeUpdate();
               
                     
                    //To capture the review data
                              //Marking this one out because we wanna connect to both the database and the table so we create new arrays at the top
                  /*  String subject = CBOsubjects.getSelectedItem().toString();
                    String tutor = CBOtutors.getSelectedItem().toString();
                    String comment = txtArea.getText();
                    int rating = currentRating; */

                    ReviewT.Review r = new ReviewT.Review(subjects, tutors, Rating, comments);
              

                    parentApp.getTablePanel().addReview(r);



                    JOptionPane.showMessageDialog(null,"Review submitted successfully");

                    CBOsubjects.setSelectedIndex(0);
                    CBOtutors.setSelectedIndex(0);

                    txtArea.setText("");

                    setRating(0);

                    parentApp.showCard("Table");



                }catch(SQLException ex){
                        JOptionPane.showMessageDialog(null, " Error saving review: " + ex.getMessage());
                
                }

                 }

        
                 });
            

        btnShowReviews = new RoundedButton("Show Reviews", 30);
        btnShowReviews.setForeground(Color.BLACK);

        btnShowReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentApp.showCard("Table");
            }
        });


        setGUI();
    }


    class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false); // makes corners transparent
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fill background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            super.paintComponent(g2);
            g2.dispose();

        }

    }

    private void setRating(int rating){
        currentRating = rating;

        for (int i = 0; i < stars.length; i++) {
            stars[i].setIcon(i< rating? Fullstar:Empty);

        }


    }
    public void setGUI() {
        // getContentPane().setBackground(Color.decode("#090979"));
        setBackground(Color.WHITE);
        pnlNorth.setBackground(new Color(176, 224, 230));

        //pnlNorth.setBackground(Color.white);
        pnlNorth.setOpaque(true);
        // pnlCenter.setBackground(Color.decode("#090979"));
        pnlCenter.setBackground(new Color(176,224,230));
        pnlCenter.setOpaque(true);
        // pnlSouth.setBackground(Color.decode("#090979"));
        pnlSouth.setBackground(new Color(176,224,230));
        pnlSouth.setOpaque(true);
        // pnlNorth.setLayout(new GridLayout(2,1));
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
        pnlCenter.setLayout(new GridLayout(0,1));
        pnlSouth.setLayout(new FlowLayout());

        pnlNorth.add(lblImg);
        lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlNorth.add(lblTitle);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));


        pnlCenter.add(new JLabel(""));


        pnlCenter.add(lblSelectSub);
        pnlCenter.add(CBOsubjects);
        pnlCenter.add(lblSelectTutor);
        pnlCenter.add(CBOtutors);

        pnlCenter.add(lblReview);

        pnlCenter.add(new JScrollPane(txtArea));


        JPanel starPanel =  new JPanel( new FlowLayout());
        for (JLabel star : stars) {
            starPanel.add(star);
        }
        pnlCenter.add(lblRating);
        pnlCenter.add(starPanel);
        setRating(0);


        pnlSouth.add(btnSubmit);
        pnlSouth.add(btnShowReviews);

        this.setLayout(new BorderLayout());
        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);
        // setGUI();

        // ReviewT tableFrame = new ReviewT();
        //tableFrame.setVisible(true); //created to connect the comment section table with the comment submission page

               /*this.pack();
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); */
        setVisible(true);


    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReviewApp::new);
    }
}


