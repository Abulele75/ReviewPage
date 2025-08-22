import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReviewT extends JPanel {

    JTable Reviewtbl;
    JButton btnAllReviews, btnPostive, btnCritical, btnMostRecent, btnHighestRating, btnBack;
    JLabel lblSortby, lblFilter, lblImg;
    JPanel pnlNorth, pnlCenter, pnlSouth;
    private Icon Msc;
    private ReviewApp parentApp;

    ArrayList<Review> rvList = new ArrayList<>();
    DefaultTableModel model;


    public ReviewT(ReviewApp app) {

        this.parentApp = app;


        btnAllReviews = new JButton("All Reviews");
        btnPostive = new JButton("Postive (4-5)");
        btnCritical = new JButton("Critical (1-3)");
        btnMostRecent = new JButton("Most Recent");
        btnHighestRating = new JButton("Highest Rating");

        btnBack = new ReviewT.RoundedButton("Back", 30);


        btnBack.setForeground(Color.BLACK);

        lblFilter = new JLabel("Filter Reviews");
        // lblFilter.setForeground(Color.YELLOW);
        lblFilter.setFont(new Font("Arial", Font.BOLD, 14));
        lblSortby = new JLabel("Sort by");
        lblSortby.setFont(new Font("Arial", Font.BOLD, 14));
        // lblSortby.setForeground(Color.YELLOW);
        // lblStudentReviews = new JLabel("Student Reviews:");
//lblStudentReviews.setForeground(Color.WHITE);
        // lblStudentReviews.setFont(new Font("Arial", Font.BOLD, 30));
        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();

        Image img = new ImageIcon(getClass().getResource("/Msc.jpeg")).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        Msc = new ImageIcon(img);
        lblImg = new JLabel(Msc);


        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{"Subject", "Tutor", "Rating", "Comment"});

        Reviewtbl = new JTable(model);
        Reviewtbl.setBackground(Color.white);
        Reviewtbl.setFont(new Font("Arial", Font.PLAIN, 14));
        Reviewtbl.setRowHeight(30);
        Reviewtbl.setGridColor(Color.GRAY); // subtle grid
        Reviewtbl.setShowGrid(true);

        // Most Recent button functionality
        btnMostRecent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0); // Clear table
                for (int i = rvList.size() - 1; i >= 0; i--) {
                    Review r = rvList.get(i);
                    model.addRow(new Object[]{r.subject, r.tutor, r.rating, r.comment});
                }
            }
        });

// Highest Rating button functionality
        btnHighestRating.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0); // Clear table
                // Copy and sort the list by rating descending
                ArrayList<Review> sortedList = new ArrayList<Review>(rvList);
                sortedList.sort(new java.util.Comparator<Review>() {
                    @Override
                    public int compare(Review r1, Review r2) {
                        return Integer.compare(r2.rating, r1.rating); // descending
                    }
                });
                for (Review r : sortedList) {
                    model.addRow(new Object[]{r.subject, r.tutor, r.rating, r.comment});
                }
            }
        });



        setGUI();

    }

    private void showAllReviews() {
        filterTable(1, 5);
    }

    public void addReview(Review r) {
        rvList.add(r);
        model.addRow(new Object[]{r.subject, r.tutor, r.rating, r.comment});
    }

    private void filterTable(int minRating, int maxRating) {
        model.setRowCount(0); // Clear table

        for (Review r : rvList) {
            if (r.rating >= minRating && r.rating <= maxRating) {
                model.addRow(new Object[]{r.subject, r.tutor, r.rating, r.comment});
            }
        }
    }




    public void setGUI() {

        btnAllReviews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterTable(1, 5); // Show all
            }
        });

        btnPostive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterTable(4, 5); // Show positive reviews only
            }
        });

        btnCritical.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterTable(1, 3); // Show critical reviews only
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                parentApp.showCard("Review");

            }


        });


        // getContentPane().setBackground(Color.decode("#090979"));
        setBackground(new Color(176, 224, 230));


        // pnlNorth.setBackground(Color.decode("#090979"));
        pnlNorth.setBackground(new Color(176, 224, 230));
        pnlNorth.setOpaque(true);
        //pnlCenter.setBackground(Color.decode("#090979"));
        pnlCenter.setBackground(new Color(176, 224, 230));
        pnlCenter.setOpaque(true);
        // pnlSouth.setBackground(Color.decode("#090979"));
        pnlSouth.setBackground(new Color(176, 224, 230));
        pnlSouth.setOpaque(true);
        pnlNorth.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        pnlCenter.setLayout(new FlowLayout());
        pnlSouth.setLayout(new FlowLayout());
        pnlSouth.setForeground(new Color(176, 224, 230));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;

// Row 0: image (spanning all columns)
        pnlNorth.add(lblImg, gbc);

// Row 1: Filter label + buttons
        gbc.gridy = 1;
        gbc.gridwidth = 1; // reset width
        pnlNorth.add(lblFilter, gbc);

        gbc.gridx = 1;
        pnlNorth.add(btnAllReviews, gbc);

        gbc.gridx = 2;
        pnlNorth.add(btnPostive, gbc);

        gbc.gridx = 3;
        pnlNorth.add(btnCritical, gbc);

// Row 2: Sort label + buttons
        gbc.gridy = 2;
        gbc.gridx = 0;
        pnlNorth.add(lblSortby, gbc);

        gbc.gridx = 1;
        pnlNorth.add(btnMostRecent, gbc);

        gbc.gridx = 2;
        pnlNorth.add(btnHighestRating, gbc);


        Reviewtbl.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(Reviewtbl);


        scroll.getViewport().setBackground(Color.white);

        pnlCenter.add(scroll);
        pnlSouth.add(btnBack);

        this.setLayout(new BorderLayout());
        showAllReviews();

        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);

      /*  this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        // setVisible(true);

    }


    public class RoundedButton extends JButton {

        private int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
        }


        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    public static class Review {
        String subject;
        String tutor;
        int rating;
        String comment;

        public Review(String subject, String tutor, int rating, String comment) {
            this.subject = subject;
            this.tutor = tutor;
            this.rating = rating;
            this.comment = comment;
        }

    }
}









