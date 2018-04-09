/* Blizzard Internship
   File Name:          TrainDepot.java
   Programmer:         Jared Wines
   Date Last Modified: 1/29/14 @ 9:08 PM

   Problem Statement: This program is a game that asks the user to cut a wire by clicking on a button to diffuse a bomb. The user will
   only get 4 tries with one hint related to Blizzard. If the user cuts the right wire within 4 turns than the user wins,
    but if the user never cuts the right wire the train will explode.
   
   
   
   Overall Plan (step-by-step, how you want the code to make it happen):
   1. make the gui variables like JPanel, Jbutton, etc.
   2. Make public wirecut to hold the gui settings.
   3. Use the Jbutton to use as wires for the game.
   3. Make the actionlisteners for the Jbutton wire colors.
   5. Make void Story for story line.
   6. Make a void alive to display a message when player wins and if they want to play again.
   7. Make a death method to display a message when a player loses and if they want to play again.
   8. Make a random wire color gen to randomly pick a wire color.
   9. make a random hint gen to randomly pick a hint that relates to the correct wire.
   10. Make a CutWire method that lowers your life each turn and checks if your a winner or loser and if the wire color is correct or wrong.
   11. Use GridLayout to set the game's layout for the gui.

*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TrainDepot extends JFrame {
    //gui private variables
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JTextArea textArea;
    private JTextArea textRiddle;
    private JButton yellow;
    private JButton blue;
    private JButton red;
    private JButton green;
    private JButton brown;
    private Random generator;
    //number of defeat lifes
    int life = 5;
    int cart = 1;
    //sets deactivationColor to the random color
    String deactivationColor = WireColorGen();
    //sets the hint to the random hint
    String hint = "HINT: " + HintHolderGen();
    Font myFont = new Font("Serif", Font.BOLD, 15);

    // main method
    public static void main(String[] args) {
        TrainDepot train = new TrainDepot();
    }

    public TrainDepot() {
        // Set the title bar text, window size and action for close button
        super("Train Depot");
        setSize(652, 555);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        yellow = new JButton();
        yellow.setToolTipText("Click to cut the Yellow Wire.");
        yellow.setBackground(Color.YELLOW);
        yellow.setPreferredSize(new Dimension(35, 487));
        blue = new JButton();
        blue.setBackground(Color.BLUE);
        blue.setToolTipText("Click to cut the Blue Wire.");
        blue.setPreferredSize(new Dimension(35, 487));
        red = new JButton();
        red.setBackground(Color.RED);
        red.setToolTipText("Click to cut the Red Wire.");
        red.setPreferredSize(new Dimension(35, 487));
        green = new JButton();
        green.setBackground(Color.GREEN);
        green.setToolTipText("Click to cut the Green Wire.");
        green.setPreferredSize(new Dimension(35, 487));
        brown = new JButton();
        brown.setToolTipText("Click to cut the Brown Wire.");
        brown.setBackground(new Color(121, 73, 0));
        brown.setPreferredSize(new Dimension(35, 487));

        yellow.addActionListener(new YellowListener());
        blue.addActionListener(new BlueListener());
        red.addActionListener(new RedListener());
        green.addActionListener(new GreenListener());
        brown.addActionListener(new BrownListener());

        // Create a panel and add the components to it.
        panel = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();

        panel.setLayout(null);
        panel.setBackground(Color.LIGHT_GRAY);
        panel2.setBounds(270, 165, 360, 97);
        panel2.setBackground(Color.CYAN);
        panel3.setBounds(270, 270, 360, 240);
        panel3.setBackground(Color.CYAN);
        panel4.setBounds(10, 5, 250, 505);
        panel4.setBackground(Color.BLACK);
        panel5.setBounds(270, 5, 360, 150);
        panel5.setBackground(Color.DARK_GRAY);
        // text that holds the hint
        textRiddle = new JTextArea(hint);
        textRiddle.setFont(myFont);
        textRiddle.setLineWrap(true);
        textRiddle.setWrapStyleWord(true);
        textRiddle.setOpaque(false);
        textRiddle.setEditable(false);
        textRiddle.setWrapStyleWord(true);
        textRiddle.setBounds(270, 155, 355, 90);
		
        //text that holds the lifes and story.
        textArea = new JTextArea();
        textArea.setFont(myFont);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(270, 260, 355, 245);
        
        Story();
		JOptionPane.showConfirmDialog(null, "Prologue: You and your trusty defective iPhone, that seems to have a mind of its own, " +
			"are on a train that is about to explode.\n" +
            "Your only hope is diffusing the bomb on each of the train's carts " +
            "by cutting the correct wire.\nLuckily, your smart phone was able to hack the bomb for hints related to blizzard on what wire to cut.\n" +
            "Be careful, each time you change carts you get less chances!!!", "Prologue", JOptionPane.PLAIN_MESSAGE);

        //adding and setting components to panel
        add(panel);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(panel5);
        panel2.add(textRiddle);
        panel3.add(textArea);
        panel4.add(yellow);
        panel4.add(blue);
        panel4.add(red);
        panel4.add(green);
        panel4.add(brown);
        panel5.add(new TrainDepotImage());

        panel2.setBorder(BorderFactory.createRaisedBevelBorder());
        panel3.setBorder(BorderFactory.createRaisedBevelBorder());
        panel4.setBorder(BorderFactory.createRaisedBevelBorder());
        panel5.setBorder(BorderFactory.createRaisedBevelBorder());

        //make the gui visible
        setVisible(true);
        setLocationRelativeTo(null);
        validate();
    }

    //ActionListener for the yellow(wire) button
    private class YellowListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            yellow.setVisible(false);
            CutWire("yellow");
        }
    }

    //ActionListener for the blue(wire) button
    private class BlueListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            blue.setVisible(false);
            CutWire("blue");
        }
    }

    //ActionListener for the red(wire) button
    private class RedListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            red.setVisible(false);
            CutWire("red");
        }
    }

    //ActionListener for the green(wire) button
    private class GreenListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            green.setVisible(false);
            CutWire("green");
        }
    }

    //ActionListener for the brown(wire) button
    private class BrownListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            brown.setVisible(false);
            CutWire("brown");
        }
    }

    //runs the Story, death, alive method
    public void CutWire(String Color) {
        if (!(deactivationColor.equalsIgnoreCase(Color))) {
            life--;
            Death(life);
            Story();
        } else
            Alive();
        validate();
        repaint();
    }

    //Alive method that ask the user if they want to play again after they win.
    public void Alive() {
        if (cart == 5) {
            textArea.setText("You did it! I won't be turned into scrap!!!");
            int selectedOption = JOptionPane.showConfirmDialog(null,
                    "WIN\n We are Alive!!! And so is my hard ware!!! Wait... if I'm a smart phone..." +
                            " Why didn't you use me to google the answers to the hints???\n" +
                            "Do you want to go on another train!!!",
                    "Play Again?",
                    JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                TrainDepot train = new TrainDepot();
            }
            if (selectedOption == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            textArea.setText("You defuse a bomb! On to the next cart!!!!");
            JOptionPane.showConfirmDialog(null,
                    "DEFUSED\n We did it!!! The Bomb is done!!!",
                    "Next Cart",
                    JOptionPane.PLAIN_MESSAGE);
            resetNewCart();
        }
    }


    //Alive method that ask the user if they want to play again after they lose.

    public void Death(int Life) {
        if (Life == 0) {
            textArea.setText("BootLoader Mode...");
            int selectedOption = JOptionPane.showConfirmDialog(null,
                    "LOSE\n We are dead!!! Do you want to go on another train in the after life!!!",
                    "Play Again?",
                    JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                life = 5;
                cart = 0;
                Story();
                resetNewCart();
            }
            if (selectedOption == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }

    public void cartBomb() {
        if (cart == 1) {
            life = 5;
        } else if (cart == 2) {
            life = 4;
        } else if (cart == 3) {
            life = 3;
        } else if (cart == 4) {
            life = 2;
        } else if (cart == 5) {
            life = 1;
        }
    }

    //Story method that holds all the story text and choices what text to use.
    public void Story() {
        //cart 1
        if (life == 5 && cart == 1) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Ahhhhhh 5 wires.. 5 chances???? Really????");
        } else if (life == 4 && cart == 1) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Ok hahaha Don't worry.");
        } else if (life == 3 && cart == 1) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: You like to push random buttons don't you haha.");
        } else if (life == 2 && cart == 1) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: This bomb wasn't thought out, was it");
        } else if (life == 1 && cart == 1) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Can't mess this up");
        }
        //cart 2
        else if (life == 4 && cart == 2) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: On to the second cart! Good thing there are no armed guards here" +
                    "\n or we would be in trouble!! I can hack but I can't shoot!");
        } else if (life == 3 && cart == 2) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Only three more chances!!! Make them count!!!");
        } else if (life == 2 && cart == 2) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Ok.... Take your time, no pressure. There are no armed guards and no time limit.\n" +
                    " Just you and a bunch of innocent people on a train and your wonderful smart phone ABOUT TO GET BLOWN UP.");

        } else if (life == 1 && cart == 2) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Ok ok ok ok ok ok... We could die!!!!!!! Cut the right one!!!" +
                    " think of your smart phone!!!!");
        }
        //cart 3
        else if (life == 3 && cart == 3) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Yes!!!!!!!!! The people on the train are cheering you on\n" +
                    " as you enter the third cart!!!");
        } else if (life == 2 && cart == 3) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Ok... focus... focus... focus... now cut!!!)\n");
        } else if (life == 1 && cart == 3) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Don't cut that wire!!!! Wait..... no, cut it!\n" +
                    " Ahhhhhha cut all of them!!!");
        }
        //cart 4

        else if (life == 2 && cart == 4) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: CART NUMBER 4! We can survive this !!!! We got this!!!\n");
        } else if (life == 1 && cart == 4) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH!!!!! We are going to die!\n" +
                    " We don't got this!!!");
        }
        //cart 5
        else if (life == 1 && cart == 5) {
            textArea.setText(("Lifes: " + life + "\n") + ("Cart: " + cart + " of 5\n") + "Siri: Everyone might live!!!! I knew we could do it! You can do this.\n" +
                    " All of the passengers are cheering you on, saying you can do this!!");
        }

    }

    //WireColorGen method random selects a color to be used as a wire color.
    public String WireColorGen() {
        String wireColor = "";
        generator = new Random();
        String wireColorArr[] = {"yellow", "blue", "red", "green", "brown"};
        int select = generator.nextInt(wireColorArr.length);
        wireColor = wireColorArr[select];

        return wireColor;
    }

    //Randomly selects the hint to be used that corresponds with the color of wire that was selected
    public String HintHolderGen() {
        String riddle = "";
        generator = new Random();

        String[] yellowHint = {
                "What color is Grand Widow Faerlina's armor in WOW?",
                "What color is Kaz'rogal in WOW: Burning Crusade?,",
                "What color are Heirloom items?",
                "What color are legendary items? (Pick the closest color)",
                "What color is the Mekgineer's Chopper mount in WOW?",
                "What color is the icon for Engineering in WOW?",
                "What color is the icon for Paladins in WOW?",
                "what color is the default Blood Elf's hair in WOW?",
                "What color is the default Human's hair in WOW?",
                "What color is the Golden King mount in WOW?"
        };
        String[] blueHint = {
                "What color is Blizzard's logo?",
                "What color are the Lich King's flames in WOW?",
                "What color are rare items in WOW?",
                "What color is Sapphiron in WOW?",
                "What color is Instructor Razuvious's armor in WOW?",
                "What color is the Alliance's logo in WOW?",
                "What color is the Silvermoon Hawkstrider mount in WOW: Wrath of the Lich King?",
                "What color is the icon for alchemy in WOW?",
                "What color is Draenei icon in WOW?",
                "What color is the Spectral Wolf mount in WOW?"
        };
        String[] redHint = {
                "What color is the main boss in Diablo II Chapter V?",
                "What color is Magmadar in WOW?",
                "What color is Death Wing in WOW?",
                "What color is Ragnaros in WOW?",
                "What color is Kil'jaeden in WOW: Burning Crusade?",
                "What color is the hard copy of Cataclysm in WOW?",
                "What color is the Horde's logo?",
                "What color is the icon for First Aid in WOW?",
                "What color is the Crimson Primal Direhorn mount in WOW?",
                "What color is the Turbostrider mount in WOW?"};
        String[] greenHint = {
                "What color is the icon for Rogues in WOW?",
                "What color is the hard copy of Burning Crusade?",
                "What color is the hard copy Mists of Pandaria in WOW?",
                "What color is Thrall, Son of Durotan in WOW: Wrath of the Lich King?",
                "What color are Goblins in WOW?",
                "What color is High Priest Venoxis in WOW?",
                "What color is Illidan Stormrage's dual swords in WOW: Burning Crusade?",
                "What color are uncommon items in WOW?",
                "What color is Valithria Dreamwalker in WOW: Wrath of the Lich King?",
                "What color is the shell of the Sea Turtle mount?"};
        String[] brownHint = {
                "What color is the Brewfest Ram mount in WOW?",
                "What color is the Mountain Horse mount in WOW?",
                "What color is the icon for Leatherworking in WOW?",
                "What color is the mount Kor'kron Annihilator in WOW: Cataclysm?",
                "What color is the hard copy going to be in Warlords of Draenor in WOW?",
                "What color is the Traveler's Tundra Mammoth mount in WOW?",
                "What color are druids in tree form in WOW?",
                "What color is Kologarn in WOW: Wrath of Lich King?",
                "What color is Loatheb from WOW",
                "What color is High Priest Thekal from WOW"};

        if ("yellow" == deactivationColor) {
            int select = generator.nextInt(yellowHint.length);
            riddle = yellowHint[select];

        } else if ("blue" == deactivationColor) {
            int select = generator.nextInt(blueHint.length);
            riddle = blueHint[select];

        } else if ("red" == deactivationColor) {
            int select = generator.nextInt(redHint.length);
            riddle = redHint[select];

        } else if ("green" == deactivationColor) {
            int select = generator.nextInt(greenHint.length);
            riddle = greenHint[select];

        } else if ("brown" == deactivationColor) {
            int select = generator.nextInt(brownHint.length);
            riddle = brownHint[select];

        }
        return riddle;
    }

    public void resetNewCart() {
        //sets deactivationColor to the random color
        deactivationColor = WireColorGen();
        //sets the hint to the random hint
        textRiddle.setText("HINT: " + HintHolderGen());
        cart++;
        cartBomb();
        Story();


        yellow.setVisible(true);
        blue.setVisible(true);
        red.setVisible(true);
        green.setVisible(true);
        brown.setVisible(true);
        validate();
        repaint();
    }
}   //end of Java.WireCut