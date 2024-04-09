import java.awt.*;

public class Hero {
    //variable declaration
    public int xpos; //the x position
    public int ypos;// the y position
    public int dx;//the speed of the hero in the x direction
    public int dy;//the speed of the hero in the y direction
    public int width;
    public int height;
    public int score;
    public Rectangle rec;
    public boolean isAlive;// a boolean to denote if the hero is alive or not
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    //constructor section
    public Hero(int pXpos,int pYpos){
        xpos=pXpos;
        ypos=pYpos;
        dx=1;
        dy=1;
        width=100;
        height=100;
        score = 0;
        isAlive=true;

    }

    //print into section
    public void printInfo() {
        System.out.println("x-position: " + xpos + ".");
        System.out.println(" y-position: " + ypos + ".");
        System.out.println("speed in x direction: " + dx + ".");
        System.out.println("speed in y direction: " + dy + ".");
        System.out.println("the width of the hero is " + width + ".");
        System.out.println("the height of the hero is " + height + ".");
        System.out.println("the hero's aliveness is " + isAlive + ".");
    }
    public void move1() {

        if (ypos > 700 - height) {//south wall
            dy = -dy;


        }
        if (xpos > 1000 - width) {//east wall
            dx = -dx;

        }
        if (ypos < 0) {//north wall
            dy = -dy;


        }
        if (xpos < 0) {//west wall
            dx = -dx;

        }
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos, ypos, width, height);

    }
    public void playermove(){

        if (left==true && xpos >= 10){
            dx= -5;

        } else if (right==true && xpos <= 950) {
            dx=5;

        }else {
            dx=0;
        }
        if (up==true && ypos >= 10 ){
            dy= -5;
            System.out.println("we're moving up");
        } else if (down==true && ypos <= 600) {
            dy=5;

        }else {
            dy=0;
        }
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos, ypos, width, height);
    }
}
