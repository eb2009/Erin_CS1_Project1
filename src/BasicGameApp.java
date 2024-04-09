//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.management.timer.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener, MouseListener{

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image astroPic;
    public Image barcaPic;

    public Image bgPic;

    /**Step 0 declare image **/
    public Image catPic;
    public Image yumsiesPic;
    public Image bg2Pic;
    public Image p2wPic;
    public Image p1wPic;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Astronaut astro;
    private Astronaut[] barca;

    private Astronaut yumsies;
    private Astronaut bg;
    private Astronaut bg2;
    private Astronaut p2w;
    private Astronaut p1w;
    /** step 1 declare object**/
    private Hero cat;
    private Font myFont = new Font("Serif", Font.BOLD, 200);



    public boolean p2wins=false;
    public boolean p1wins= false;
    public boolean showbarca = true;
    public boolean showyumsies = false;
    public boolean showastro=true;
    public boolean showcat= true;
    public boolean level2 = false;
    public boolean showbg = true;
    public boolean showbg2= false;



    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() {

        setUpGraphics();
        canvas.addKeyListener(this);



        //variable and objects
        //create (construct) the objects needed for the game and load up
        astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png"); //load the picture
        astro = new Astronaut(500, 600, 0,0);
        /** Step 2 construct object**/
        cat = new Hero(500,600);
        /**step 3 add image to object**/
        catPic = Toolkit.getDefaultToolkit().getImage("lechat.png" );
        cat.printInfo();

        barcaPic = Toolkit.getDefaultToolkit().getImage("barca.png");
        barca = new Astronaut[11];
        for (int i= 0; i< 11; i = i+1){
            barca[i] = new Astronaut((int)(Math.random()*1000), (int)(Math.random()*700),2,2);
        }

        //all the backgrounds
        //level1 bg
        bgPic = Toolkit.getDefaultToolkit().getImage("cherry.jpg"); //load the picture

        //cat wins background
        p2wPic = Toolkit.getDefaultToolkit().getImage("p2wins.png"); //load the picture
       //astro wins background
        p1wPic = Toolkit.getDefaultToolkit().getImage("p1wins.png"); //load the picture

        //level 2 wins background
        bg2Pic = Toolkit.getDefaultToolkit().getImage("lz3WCsw-2642780641.jpg");




    }// BasicGameApp()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(10);


        }

    }

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);



        if (showbg==true && !p2wins){
            //make bkground
            g.drawImage(bgPic, 0, 0, WIDTH, HEIGHT, null);

        }
        if (p2wins==true){
            p2w.height=700;
            p2w.width=1000;
            g.drawImage(p2wPic, 0, 0, WIDTH, HEIGHT, null);

        }
        if (p1wins==true){
            g.drawImage(p1wPic, 0, 0, WIDTH, HEIGHT, null);

        }
        if (showbg2 == true){
            //make bkground for lvl 2
            g.drawImage(bg2Pic, 0, 0, WIDTH, HEIGHT, null);
        }
        for (int i= 0; i< 11; i = i+1){
            if(barca[i].isAlive == true) {
                g.drawImage(barcaPic, barca[i].xpos, barca[i].ypos, barca[i].width, barca[i].height, null);
            }

        }
        if (showastro==true){
            g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
        }
        //cat
        if (showcat==true){
            g.drawImage(catPic, cat.xpos, cat.ypos, cat.width, cat.height, null);


        }

        g.setColor(Color.magenta);
        g.fillRect(10,5, 150,15);
        g.setColor(Color.CYAN);
        g.fillRect(895,5, 101,15);
        g.setColor(Color.BLACK);
        g.drawString("P1 Astronaut Score: " + astro.score, 15,15);
        g.drawString(" P2 Cat Score: " + cat.score, 900,15);






        g.dispose();

        bufferStrategy.show();
    }



    public void moveThings() {
        //calls the move( ) code in the objects
        astro.playermove();
        cat.playermove();
        showbg = true;

        showbarca= true;
        for (int i= 0; i< 11; i = i+1) {

            barca[i].wrap();

            if (barca[i].rec.intersects(astro.rec) && barca[i].isAlive && astro.isAlive) {
                astro.score = astro.score + 1;
                barca[i].isAlive = false;
                showbarca = false;


            }
            if (barca[i].rec.intersects(cat.rec) && barca[i].isAlive && cat.isAlive) {

                cat.score = cat.score + 1;
                barca[i].isAlive = false;
                showbarca = false;


            }
            if (cat.score + astro.score == 11) {
                Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
                g.setColor(Color.magenta);
                g.fillRect(10, 5, 150, 15);
                g.setColor(Color.CYAN);
                g.fillRect(895, 5, 101, 15);
                g.setColor(Color.BLACK);
                g.setFont(myFont);
                g.drawString("P1 Astronaut Score: " + astro.score, 350, 350);
                g.drawString(" P2 Cat Score: " + cat.score, 650, 350);


            }
            if (cat.score + astro.score == 11 && cat.score > astro.score) {
                showbarca = false;
                p2wins = true;
                // timer();
                level2 = true;


            }
            if (astro.score + cat.score == 11 && cat.score < astro.score) {

                showbarca = false;

                //timer();
                level2 = true;

            }


        }
        if (level2 == true){
            showbg = false;
            p1wins = false;
            p2wins = false;
            showbg2 = true;
            astro.playermove();
            cat.playermove();
            showbarca= true;


            for (int i= 0; i< 9; i = i+1) {
                cat.score=0;
                astro.score=0;
                barca[i].isAlive=true;
                if (barca[i].rec.intersects(astro.rec) && barca[i].isAlive && astro.isAlive) {
                    astro.score = astro.score + 1;
                    barca[i].isAlive = false;
                    showbarca = false;


                }
                if (barca[i].rec.intersects(cat.rec) && barca[i].isAlive && cat.isAlive) {

                    cat.score = cat.score + 1;
                    barca[i].isAlive = false;
                    showbarca = false;


                }
                if (barca[i].isAlive == false){
                    showbarca = false;
                }
                if (barca[i].isAlive==true) {
                    barca[i].wrap();
                    barca[6].isAlive=true;
                    barca[7].isAlive=true;
                    barca[8].isAlive=true;
                    barca[9].isAlive=true;
                    barca[1].isAlive=false;
                    barca[2].isAlive=false;
                    barca[3].isAlive=false;
                    barca[4].isAlive=false;
                    barca[5].isAlive=false;
                    timer();
                    barca[1].isAlive=true;
                    barca[2].isAlive=true;
                    barca[3].isAlive=true;
                    barca[4].isAlive=true;
                    barca[5].isAlive=true;
                    barca[6].isAlive=false;
                    barca[7].isAlive=false;
                    barca[8].isAlive=false;
                    barca[9].isAlive=false;
                    timer();
                    barca[i].isAlive=true;
                    barca[i].wrap();

                }
                if (cat.score + astro.score == 9) {
                    showyumsies = true;
                    Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
                    g.setColor(Color.magenta);
                    g.fillRect(10, 5, 150, 15);
                    g.setColor(Color.CYAN);
                    g.fillRect(895, 5, 101, 15);
                    g.setColor(Color.BLACK);
                    g.setFont(myFont);
                    g.drawString("P1 Astronaut Score: " + astro.score, 350, 350);
                    g.drawString(" P2 Cat Score: " + cat.score, 650, 350);


                }
                if (cat.score + astro.score == 9 && cat.score > astro.score) {
                    p2wins = true;



                }
                if (astro.score + cat.score == 9 && cat.score < astro.score) {
                    p1wins = true;


                }


            }
        }
    }

public void timer(){
        boolean timerIsUp=false;
        for(int count=0;count<1000000;count++){
            if(count>999999){
                timerIsUp=true;
            }
        }
}


    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }






    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout


        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key= e.getKeyChar();
        int keyCode=e.getKeyCode();
        System.out.println("Key Pressed: " + key+ ",  Code: "+ keyCode);
        if (keyCode==87){
            astro.up = true;
        }
        if (keyCode==65){
            astro.left = true;
        }
        if (keyCode==83){
            astro.down = true;
        }
        if (keyCode==68){
            astro.right = true;
        }
        if (keyCode == 39){
            cat.right = true;
        }
        if (keyCode == 37){
            cat.left = true;
        }
        if (keyCode == 38){
            cat.up = true;
        }
        if (keyCode == 40){
            cat.down = true;
        }





    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key= e.getKeyChar();
        int keyCode=e.getKeyCode();
        if (keyCode==87){
            astro.up = false;
        }
        if (keyCode==65){
            astro.left = false;
        }
        if (keyCode==83){
            astro.down = false;
        }
        if (keyCode==68){
            astro.right = false;
        }
        if (keyCode == 39){
            cat.right = false;
        }
        if (keyCode == 37){
            cat.left = false;
        }
        if (keyCode == 38){
            cat.up = false;
        }
        if (keyCode == 40){
            cat.down = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}