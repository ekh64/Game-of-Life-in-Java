
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Life extends JApplet{
	public void init()
	{
		setLayout(new FlowLayout());
		JPanel panel = new LifePanel();
		panel.setLayout(null);
		add(panel);
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setTitle("The Game of Life");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JApplet applet = new Life();
		applet.init();
		frame.getContentPane().add(applet);
		frame.pack();
		frame.setVisible(true);
	}
}
	

class LifePanel extends JPanel implements ActionListener {					
	int mx,my,red,green,blue;
	boolean[][] myGrid = new boolean[52][52];
	Image myImage;
	Timer myTimer;
	JButton startbutton,stopbutton,nextbutton,resetbutton,colorbutton;
	
	public int randomColor(int RGB){
		return (int)(256*Math.random()); 
	}
	
	public LifePanel(){
		setPreferredSize(new Dimension(500,543));
		red = randomColor(red);
		green = randomColor(green);
		blue = randomColor(blue);
		
		startbutton = new JButton("Start");
		startbutton.addActionListener(this);
		add(startbutton);
		startbutton.setBounds(100, 503, 150, 20);
		
		stopbutton = new JButton("Stop");
		stopbutton.addActionListener(this);
		add(stopbutton);
		stopbutton.setBounds(100, 523, 150, 20);
		
		nextbutton = new JButton("Next");
		nextbutton.addActionListener(this);
		add(nextbutton);
		nextbutton.setBounds(250, 503, 150, 20);
		
		resetbutton = new JButton("Reset");
		resetbutton.addActionListener(this);
		resetbutton.setBackground(Color.red);
		add(resetbutton);
		resetbutton.setBounds(400, 503, 70, 40);
		
		colorbutton = new JButton("Change Color");
		colorbutton.addActionListener(this);
		add(colorbutton);
		colorbutton.setBounds(250, 523, 150, 20);
		
		
		setBackground(Color.LIGHT_GRAY);
		
		myTimer = new Timer(300,this);
		
		myMouseListener mListener = new myMouseListener();
		addMouseListener(mListener);
		addMouseMotionListener( mListener );
	}
	
	public int pixeltocoord(int pixel){
		int c = pixel/10 + 1;
		
		if(c>50){
			c = 0;
		}
		return c;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		myImage = createImage(500,500);
			for(int i = 0; i<500;i+=10){
				for(int j = 0;j<500;j+=10){
					int k = i/10 + 1;
					int m = j/10 + 1;
					if(myGrid[k][m]){
							g.setColor(new Color(red,green,blue));
							g.fillRect(i, j, 10, 10);
					}
					g.setColor(Color.BLACK);
					g.drawRect(i, j, 10, 10);
				}
			}
	}
	
	public boolean[][] copy(boolean[][]original){
		boolean[][] dupe = new boolean[52][52];
		for(int i = 0; i < original.length; i++){
			for(int j = 0; j < original[0].length; j++){
				dupe[i][j] = original[i][j];
			}
		}
		return dupe;
	}
	
	public boolean[][] Gen(boolean[][] b){
		boolean[][] temp = new boolean [52][52];
		temp = copy(b);
		for(int i = 1; i<temp.length - 1;i++){
			for(int j = 1; j<temp[0].length-1;j++){
				int alive = 0;
				alive = count(b,i,j);
				if(b[i][j]){
					if(alive == 2){
						temp[i][j] = true;
					}
					else if(alive == 3){
						temp[i][j] = true;
					}
					else{
						temp[i][j] = false;
					}
				}
				else if(b[i][j] == false){
					if(alive == 3){
						temp[i][j] = true;
					}
				}
			}
		}
		return temp;
	}

	public int count(boolean[][] b, int row, int column){
		int surround = 0;
		if(b[row-1][column+1]){
			surround++;
		}
		if(b[row][column+1]){
			surround++;
		}
		if(b[row+1][column+1]){
			surround++;
		}
		if(b[row-1][column]){
			surround++;
		}
		if(b[row+1][column]){
			surround++;
		}
		if(b[row-1][column-1]){
			surround++;
		}
		if(b[row][column-1]){
			surround++;
		}
		if(b[row+1][column-1]){
			surround++;
		}
		return surround;        
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(colorbutton)){
			if(myTimer.isRunning()){
				red = randomColor(red);
				green = randomColor(green);
				blue = randomColor(blue);
				repaint();
			}
			else{
				red = randomColor(red);
				green = randomColor(green);
				blue = randomColor(blue);
				repaint();
				myTimer.stop();
			}
		}
		
		else{
			myTimer.start();
			myGrid = Gen(myGrid);
			stopbutton.setEnabled(true);
			startbutton.setEnabled(false);
			repaint();
		}
			
		if(e.getSource().equals(stopbutton)){
			myTimer.stop();
			repaint();
			stopbutton.setEnabled(false);
			startbutton.setEnabled(true);
		}
		
		if(e.getSource().equals(nextbutton)){
			myTimer.stop();
			repaint();
			stopbutton.setEnabled(false);
			startbutton.setEnabled(true);
		}
		
		if(e.getSource().equals(resetbutton)){
			myTimer.stop();
			boolean newGrid[][]= new boolean [52][52];
			myGrid = copy(newGrid);
			red = randomColor(red);
			green = randomColor(green);
			blue = randomColor(blue);
			repaint();
			stopbutton.setEnabled(true);
			startbutton.setEnabled(true);
		}					
	}
	class myMouseListener implements MouseListener, MouseMotionListener{
		   public void mouseEntered( MouseEvent e ) {
		      // called when the pointer enters the applet's rectangular area
		   }
		   public void mouseExited( MouseEvent e ) {
		      // called when the pointer leaves the applet's rectangular area
		   }
		   public void mouseClicked( MouseEvent e ) {
			   mx = pixeltocoord(e.getX());
			   my = pixeltocoord(e.getY());
			   if(myGrid[mx][my]){
				   myGrid[mx][my] = false;
			   }
			   else{
				   myGrid[mx][my] = true;
			   }
			   repaint();
			   e.consume();
		   }
		   public void mousePressed( MouseEvent e ) {  // called after a button is pressed down
		   }
		   public void mouseReleased( MouseEvent e ) {  // called after a button is released
		   }
		   public void mouseDragged( MouseEvent e ) { 
			   mx = pixeltocoord(e.getX());
			   my = pixeltocoord(e.getY());
			   if(myGrid[mx][my]){
				   myGrid[mx][my] = false;
			   }
			   else{
				   myGrid[mx][my] = true;
			   }
			   repaint();
			   e.consume();
		   }
		   public void mouseMoved( MouseEvent e ) {  
		   }
	}
}