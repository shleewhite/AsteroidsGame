import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class AsteroidsGame extends PApplet {

SpaceShip spaceship;
Stars sky[];
public void setup() 
{
  size(400, 400);
  background(0);
  spaceship = new SpaceShip();
  sky = new Stars[200];
  for (int i = 0; i < sky.length; i ++)
  {
    sky[i] = new Stars();
  }
}
public void draw() 
{
  background(0);
  for (int i = 0; i < sky.length; i++)
  {
    sky[i].show();
  }
  spaceship.move();
  spaceship.show();
}
public void keyPressed()
  {
    if (keyCode == UP){spaceship.accelerate(0.3f);}
    else if (keyCode == DOWN){spaceship.accelerate(-0.3f);}
    else if (keyCode == LEFT){spaceship.rotate(10);}
    else if (keyCode == RIGHT){spaceship.rotate(-10);}
  }
class SpaceShip extends Floater  
{   

  SpaceShip()
  {
    corners = 8;
    xCorners = new int[corners];
    yCorners = new int[corners];
      xCorners[0] = 10;
      yCorners[0] = 0;
      xCorners[1] = -1;
      yCorners[1] = -6;
      xCorners[2] = -1;
      yCorners[2] = -3;
      xCorners[3] = -10;
      yCorners[3] = -3;
      xCorners[4] = -3;
      yCorners[4] = 0;
      xCorners[5] = -10;
      yCorners[5] = 3;
      xCorners[6] = -1;
      yCorners[6] = 3;
      xCorners[7] = -1;
      yCorners[7] = 6;
    myColor = 0xff196CE8;
    myCenterX = 200;
    myCenterY = 200;
    myPointDirection = 0;
    myDirectionX = 0;
    myDirectionY = 0;
  }
  public void setX(int x){myCenterX = x;}
  public int getX(){return (int)myCenterX;}   
  public void setY(int y){myCenterY = y;}   
  public int getY(){return (int)myCenterY;}   
  public void setDirectionX(double x){myDirectionX = x;}   
  public double getDirectionX(){return myDirectionX;}
  public void setDirectionY(double y){myDirectionY = y;}   
  public double getDirectionY(){return myDirectionY;}   
  public void setPointDirection(int degrees){myPointDirection = degrees;}   
  public double getPointDirection(){return myPointDirection;} 
  public void show ()
  {             
    fill(myColor);
    strokeWeight(2);  
    stroke(255);
    double dRadians = myPointDirection*(Math.PI/180);                 
    int xRotatedTranslated, yRotatedTranslated;    
    beginShape();         
    for(int nI = 0; nI < corners; nI++)    
    {     
      xRotatedTranslated = (int)((xCorners[nI]* Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians))+myCenterX);     
      yRotatedTranslated = (int)((xCorners[nI]* Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians))+myCenterY);      
      vertex(xRotatedTranslated,yRotatedTranslated);    
    }   
    endShape(CLOSE);  
  }   
}

class Stars
{
  private int myColor, myX, myY;
  Stars()
  {
    myColor = 255;
    myX = (int)(Math.random()*400);
    myY = (int)(Math.random()*400);
  }
  public void show()
  {
    noFill();
    strokeWeight(0.5f);
    stroke(myColor);
    ellipse((int)myX, (int)myY, 4, 4);
    
  }
}
abstract class Floater
{   
  protected int corners; 
  protected int[] xCorners;   
  protected int[] yCorners;   
  protected int myColor;   
  protected double myCenterX, myCenterY; 
  protected double myDirectionX, myDirectionY;  
  protected double myPointDirection; 
  abstract public void setX(int x);
  abstract public int getX();  
  abstract public void setY(int y);  
  abstract public int getY();  
  abstract public void setDirectionX(double x); 
  abstract public double getDirectionX();
  abstract public void setDirectionY(double y);
  abstract public double getDirectionY();
  abstract public void setPointDirection(int degrees);
  abstract public double getPointDirection();

  //Accelerates the floater in the direction it is pointing (myPointDirection)   
  public void accelerate (double dAmount)   
  {          
    //convert the current direction the floater is pointing to radians    
    double dRadians =myPointDirection*(Math.PI/180);     
    //change coordinates of direction of travel    
    myDirectionX += ((dAmount) * Math.cos(dRadians));    
    myDirectionY += ((dAmount) * Math.sin(dRadians));       
  }   
  public void rotate (int nDegreesOfRotation)   
  {     
    //rotates the floater by a given number of degrees    
    myPointDirection+=nDegreesOfRotation;   
  }   
  public void move ()   //move the floater in the current direction of travel
  {      
    //change the x and y coordinates by myDirectionX and myDirectionY       
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;     

    //wrap around screen    
    if(myCenterX >width)
    {     
      myCenterX = 0;    
    }    
    else if (myCenterX<0)
    {     
      myCenterX = width;    
    }    
    if(myCenterY >height)
    {    
      myCenterY = 0;    
    }   
    else if (myCenterY < 0)
    {     
      myCenterY = height;    
    }   
  }   
  public void show ()  //Draws the floater at the current position  
  {             
    fill(myColor);   
    stroke(myColor);    
    //convert degrees to radians for sin and cos         
    double dRadians = myPointDirection*(Math.PI/180);                 
    int xRotatedTranslated, yRotatedTranslated;    
    beginShape();         
    for(int nI = 0; nI < corners; nI++)    
    {     
      //rotate and translate the coordinates of the floater using current direction 
      xRotatedTranslated = (int)((xCorners[nI]* Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians))+myCenterX);     
      yRotatedTranslated = (int)((xCorners[nI]* Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians))+myCenterY);      
      vertex(xRotatedTranslated,yRotatedTranslated);    
    }   
    endShape(CLOSE);  
  }   
} 

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}