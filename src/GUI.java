import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JFrame implements ActionListener, KeyListener {
    // Instance variables
    private ArrayList<Flashcard> cards;
    private int index = 0;
    private int guiIndex = 0; // 0 makes flashcard GUI visible //1 makes add new card GUI visible
    private int initialTime = (int)System.currentTimeMillis();
    private ArrayList<String> breakMessages;
    //misc

    // GUI variables for index cards
    private JFrame cardJFrame;
    private JTextArea cardLabel;
    private JButton flipButton;
    private JButton backButton;
    private JButton nextButton;
    private JButton addButton;
    private JButton deleteButton;
    // GUI variables for add card button
    private JFrame addJFrame;
    private JTextArea addCardLabel;
    private JButton addCardButton;
    private JButton flipAddCardButton;
    private JButton deleteAddCardButton;
    //GUI variables for taking a break
    private JFrame breakJFrame;
    private JTextArea breakLabel;
    private JButton breakCloseButton;
    private JButton breakDeclineButton;

    public GUI() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        // Set-up for flash card GUI
        cardJFrame = new JFrame("Flashcards");
        cardJFrame.setLayout(new FlowLayout());
        cardJFrame.setSize(725, 475);
        cardJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLabel = new JTextArea("Hello World Swing");
        cardLabel.setWrapStyleWord(true);
        cardLabel.setBounds(50, 50, 560, 375);
        cardLabel.setPreferredSize(new Dimension(560, 375));
        cardLabel.setLineWrap(true);
        cardLabel.setText("APUSH Flashcards");
        cardLabel.setFont(cardLabel.getFont().deriveFont(23f));
        cardLabel.setBackground(new Color(250, 250, 204));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        cardLabel.setBorder(border);
        cardLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cardJFrame.add(cardLabel);
        cardJFrame.setVisible(true);

        flipButton = new JButton("Flip");
        backButton = new JButton("Back");
        nextButton = new JButton("Next");
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");

        cardJFrame.add(addButton);
        cardJFrame.add(deleteButton);
        cardJFrame.add(nextButton);
        cardJFrame.add(flipButton);
        cardJFrame.add(backButton);

        // Set-up for add new card GUI
        addJFrame = new JFrame("Add New Flashcard");
        addJFrame.setLayout(new FlowLayout());
        addJFrame.setSize(725, 475);
        addJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addJFrame.setVisible(false);

        addCardLabel = new JTextArea("");
        addCardLabel.setLineWrap(true);
        addCardLabel.setWrapStyleWord(true);
        Border addCardBorder = BorderFactory.createLineBorder(Color.BLACK);
        addCardLabel.setBorder(border);
        addCardLabel.setBounds(50, 50, 560, 375);
        addCardLabel.setPreferredSize(new Dimension(560, 375));
        addCardLabel.setLineWrap(true);
        addCardLabel.setLineWrap(true);
        addCardLabel.setText("");
        addCardLabel.setFont(cardLabel.getFont().deriveFont(23f));
        addCardLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        addJFrame.add(addCardLabel);

        addCardButton = new JButton("Add this card");
        flipAddCardButton = new JButton("Flip new card");
        addJFrame.add(addCardButton);
        addJFrame.add(flipAddCardButton);

        //Set-up for break window
        breakJFrame = new JFrame("Take a break");
        breakJFrame.setLayout(new FlowLayout());
        breakJFrame.setSize(425, 275);
        breakJFrame.setLocation(150, 100);
        breakJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        breakJFrame.setVisible(false);

        breakLabel = new JTextArea("");
        breakLabel.setLineWrap(true);
        breakLabel.setWrapStyleWord(true);
        Border breakBorder = BorderFactory.createLineBorder(Color.BLACK);
        breakLabel.setBorder(border);
        breakLabel.setBounds(25, 25, 260, 175);
        breakLabel.setPreferredSize(new Dimension(260, 175));
        breakLabel.setLineWrap(true);
        breakLabel.setLineWrap(true);
        breakLabel.setText("");
        breakLabel.setFont(cardLabel.getFont().deriveFont(23f));
        breakLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        breakCloseButton = new JButton("Okay");
        breakDeclineButton = new JButton("No");
        breakJFrame.add(breakCloseButton);
        breakJFrame.add(breakLabel);
        // breakJFrame.add(breakDeclineButton);

        cards = new ArrayList<Flashcard>();
        if (cards.size() < 1) {
            setUpCards();
        }

        //break messages
        breakMessages = new ArrayList<String>();
        breakMessages.add("Remember to drink water!");
        breakMessages.add("Take a short break!");
        breakMessages.add("test");

        setUpButtonActionListeners();
    }

    public void setUpButtonActionListeners() {
        // Flash card GUI
        flipButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("flip pressed");
                cards.get(index).flip();
            }
        });
        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index == cards.size() - 1) {
                    index = 0;
                } else {
                    cards.get(index).setFrontUp(true);
                    index++;
                }
                System.out.println("next pressed");
                // cardLabel.setLocation(cardLabel.getX()+1, cardLabel.getY()+1);
            }
        });

        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back pressed");
                if (index == 0) {
                    index = cards.size() - 1;
                } else {
                    index--;
                    cards.get(index).getFront();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("delete pressed");
                cards.remove(index);
                if (index == 0) {
                    index = cards.size() - 1;
                } else {
                    index--;
                }
            }
        });

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add pressed");
                guiIndex = 1;

                Flashcard newCard = new Flashcard("", "");
                cards.add(index, newCard);
                String currentSide = addCardLabel.getText();
                if (cards.get(index).frontUp()) {
                    // if (cards.get(index).getFront().equals("")) {
                    //   addCardLabel.setText("Edit front of card");
                    // } else {
                    //   addCardLabel.setText(cards.get(index).getFront());
                    // }
                    addCardLabel.setText("Edit this side of card");
                    cards.get(index).setFront(currentSide);
                } else {
                    if (cards.get(index).getBack().equals("")) {
                        //   System.out.print("equals ");
                        //   addCardLabel.setText("Edit back of card");
                        // } else {
                        //   System.out.print("else");
                        //   addCardLabel.setText(cards.get(index).getBack());
                        addCardLabel.setText("Edit this side of card");
                    }
                    currentSide = addCardLabel.getText();

                    cards.get(index).setBack(currentSide);
                }
                // cards.get(index).flip();
            }
        });

        // Add new flashcard GUI
        addCardButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add card pressed");
                String currentSide = addCardLabel.getText();
                if (cards.get(index).frontUp()) {
                    cards.get(index).setFront(currentSide);
                } else {
                    cards.get(index).setBack(currentSide);
                }
                guiIndex = 0;
                cards.get(index).flip();
            }
        });

        flipAddCardButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add flip pressed");
                String currentSide = addCardLabel.getText();
                if (cards.get(index).frontUp()) {
                    // if (cards.get(index).getFront().equals("")) {
                    //   addCardLabel.setText("Edit front of card");
                    // } else {
                    //   addCardLabel.setText(cards.get(index).getFront());
                    // }
                    addCardLabel.setText("Edit this side of card");
                    cards.get(index).setFront(currentSide);
                } else {
                    if (cards.get(index).getBack().equals("")) {
                        //   System.out.print("equals ");
                        //   addCardLabel.setText("Edit back of card");
                        // } else {
                        //   System.out.print("else");
                        //   addCardLabel.setText(cards.get(index).getBack());
                        addCardLabel.setText("Edit this side of card");
                    }
                    currentSide = addCardLabel.getText();
                    cards.get(index).setBack(currentSide);
                }
                cards.get(index).flip();
            }
        });
        breakCloseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                guiIndex = 0;
            }
        });
        // breakDeclineButton.addActionListener(new ActionListener() {

        //   @Override
        //   public void actionPerformed(ActionEvent e) {
        //     breakJframe.setVisible(true);
        //   }
        // });
    }

    public void update() {
        //timer for making break window show up
        int intervalInSeconds = 15;
        if((((initialTime - (int)System.currentTimeMillis()) / 1000) % intervalInSeconds == 0) && guiIndex == 0) {
            guiIndex = 2;
            int randomIndex = (int)random(0, breakMessages.size()-1);
            breakLabel.setText(breakMessages.get(randomIndex));
            String text = breakLabel.getText();
            text += "\nCurrently shows up really frequently to showcase the function";
            breakLabel.setText(text);
        }
        // guiIndex 0 makes flashcard GUI visible //1 makes add new card GUI visible
        if (guiIndex == 0) {
            cardJFrame.setVisible(true);
            addJFrame.setVisible(false);
            breakJFrame.setVisible(false);
            if (cards.get(index).frontUp()) {
                cardLabel.setText(cards.get(index).getFront());
            } else {
                cardLabel.setText(cards.get(index).getBack());
            }
        } else if (guiIndex == 1) {
            cardJFrame.setVisible(false);
            addJFrame.setVisible(true);
        } else if(guiIndex == 2){
            breakJFrame.setVisible(true);
        }
        // jFrame.setLocation(jFrame.getX()+1, jFrame.getY()+1);

    }

    public void setUpCards() {
        cards = new ArrayList<Flashcard>();
        String text = ""
                + "Conquistadors_"
                + "conquistador, (Spanish: conqueror) plural conquistadores or conquistadors, any of the leaders in the Spanish conquest of America, especially of Mexico and Peru, in the 16th century._"
                + "Jamestown_"
                + "1st permanent British colony in the New World. Founded by Virginia Company and received charter from King James I._"
                + "Sugar Act_"
                + "Sugar Act of 1764. First law passed by Parliament that raised tax revenues in the colonies for the crown. It increased duty on foreign sugar imported from the West Indies._"
                + "Intolerable Acts_"
                + "Acts passed in retaliation to the Boston Tea Party; the British government closed port of Boston until tea was paid for; revised the charter if Massachusetts (which drastically reduced their powers of self-government), forced colonists of Massachusetts to house British soldiers and allowed British officers to be tried in England for crimes of violence._"
                + "Salem Witch Trials_"
                + "1629 outbreak of witchcraft accusations in a Massachusetts Bay puritan village marked by an atmosphere of fear, hysteria and stress. Spectral evidence was used frequently_"
                + "Nativism_"
                + "Nativism is the political policy of promoting or protecting the interests of native/indigenous or established inhabitants over those of immigrants, including the support of immigration-restriction measures._"
                + "Triangular trade_"
                + "Trading System between Europe, Africa, and the colonies; European purchased slaves in Africa and sold them to colonies, new materials from colonies went to Europe while European finished products were sold in the colonies._"
                + "First Great Awakening_"
                + "Religious revival in the colonies in 1730s and 1740s; George Whitefield and Jonathan Edwards preached a message of atonement for sins by admitting them to God. The movement attempted to combat the growing secularism and rationalism of mid-eighteenth century America._"
                + "Popular Sovereignty_"
                + "Popular sovereignty is the principle that the people living in a territory should be allowed to decide for themselves whether to allow or prohibit slavery._"
                + "Common sense_"
                + "A pamphlet written by Thomas Paine in 1776 that criticized monarchies and convinced many American colonists of the need to break away from Britain._"
                + "American Revolution (1775-1783)_"
                + "A period when 13 colonies gained independence from England. Based on disapproval by colonists of several taxes and other unpopular laws. Protests lead to fighting in 1775, and after two main British armies were captured in 1777 and 1781 and an alliance of the colonists with the French, the Treaty of Paris was signed._"
                + "laissez-faire_"
                + "economic system based on private ownership of business and transactions between private parties were free from government interference. It allowed businesses to grow and increased the gap between the rich and the poor._"
                + "Louisiana Purchase_" + "Territory in western United States purchased from France in 1803 for $15 million._"
                + "Truman Doctrine_"
                + "President Truman's policy of providing economic and military aid to any country threatened by communism or totalitarian ideology._"
                + "U.S Industrial Revolution_"
                + "Transformation of manufacturing; power-driven machines took place of hand-operated tools especially after 1815._";
        int index = text.indexOf("_");
        while (index != -1) {
            String front = text.substring(0, index);
            text = text.substring(index + 1);
            index = text.indexOf("_");
            String back = text.substring(0, index);
            text = text.substring(index + 1);
            index = text.indexOf("_");
            Flashcard card = new Flashcard(front, back);

            cards.add(card);
        }
    }
    public static double random(int max, int min){
        return Math.random()*(max-min)+min;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // KeyListener interface requires this method be added, even if unimplemented
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // KeyListener interface requires this method be added, even if unimplemented
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // KeyListener interface requires this method be added, even if unimplemented
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // KeyListener interface requires this method be added, even if unimplemented
    }
}