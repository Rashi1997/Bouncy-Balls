import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CopyOnWriteArrayList;
//implements ActionListener 
public class BallWorld {
	
	private JFrame f;
	private static JPanel mainPanel;
    private DrawPanel drawPanel;
    private static java.util.concurrent.CopyOnWriteArrayList<Ball> balls;
	private static boolean flag = true;
	private static int noofballs=50;
	private static JLabel ballslabel;
	public BallWorld() {
		
		
		
		
		
//		initializing frame
		f = new JFrame();
		
//		GUI initializations
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		java.net.URL infoUrl = getClass().getResource("images/info.png");
		java.net.URL playUrl = getClass().getResource("images/play.png");
		java.net.URL pauseUrl = getClass().getResource("images/pause.png");
		java.net.URL addUrl = getClass().getResource("images/add.png");
		java.net.URL subtractUrl = getClass().getResource("images/subtract.png");
		
		ImageIcon infoicon = new ImageIcon(((new ImageIcon(infoUrl))
				.getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		ImageIcon playicon = new ImageIcon(((new ImageIcon(playUrl))
				.getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		ImageIcon pauseicon = new ImageIcon(((new ImageIcon(pauseUrl))
				.getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		ImageIcon addicon = new ImageIcon(((new ImageIcon(addUrl))
				.getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		ImageIcon subtracticon = new ImageIcon(((new ImageIcon(subtractUrl))
				.getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		JPanel midpanel = new JPanel(new BorderLayout());
		JPanel textpanel = new JPanel(new BorderLayout());
		drawPanel = new DrawPanel();
		ballslabel = new JLabel();
	    JButton subtract=new JButton(subtracticon);
		JButton add=new JButton(addicon);
		JButton info=new JButton(infoicon);
	    JButton playPause=new JButton(playicon);
	    
	    

		balls = new CopyOnWriteArrayList<>();

//	    creating instance of buttons 
	    subtract.setBorder(emptyBorder);
		add.setBorder(emptyBorder);
	    info.setBorder(emptyBorder);
	    playPause.setBorder(emptyBorder);
		
		
//		Text for number of balls
		ballslabel.setText("Balls"+noofballs);
		
//		Text field input
		JTextField txtInput = new JTextField("Enter upto 1000 balls");
		
//		Listener for text field input for initializing number of balls on screen
		txtInput.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(!txtInput.getText().isEmpty()) {
		    		boolean digit =true;
		    		for(Character c: txtInput.getText().toCharArray()) {
		    			if(!Character.isDigit(c)) {
		    				digit =false;
		    			}
		    			
		    		}
		    		if(digit ==true) { 
		    			try {
			    			if(Integer.parseInt(txtInput.getText())>1000)
			    				noofballs=Integer.parseInt(txtInput.getText());
			    			else
			    				noofballs = Integer.parseInt(txtInput.getText());
		    			}
		    			catch ( NumberFormatException exc )
		    			{ // do nothing if text field value is bad 
		    			}
		    		}
		    		else 
		    			noofballs=50;
	    		}
    			else {
    				System.out.println(txtInput.getText());
    				noofballs=50;
    			}
	    	}
	    });
		
		
		
//		adding text field to center of text panel
		textpanel.add(txtInput, BorderLayout.CENTER);
		textpanel.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
	    midpanel.add(textpanel, BorderLayout.CENTER);

	    
	    
	    midpanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    bottomPanel.add(midpanel, BorderLayout.CENTER);
	    
//	    adding listener for subtract button
	    subtract.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(balls.size()>0) {
		    		balls.remove(balls.size()-1);
		            noofballs--;
		    		ballslabel.setText("Balls   "+noofballs);
		    		textpanel.remove(txtInput);
		    		System.out.println(noofballs);
	    			mainPanel.add(drawPanel);
	    			mainPanel.revalidate();
	    			mainPanel.repaint();
	    		}
	    	}
	    });	

	    
//	    adding mouse listener to draw panel for dark mode
	    drawPanel.addMouseListener(new MouseAdapter() {
            private Color background;

            @Override
            public void mouseEntered(MouseEvent e) {
                background = drawPanel.getBackground();
                drawPanel.setBackground(Color.BLACK);
                drawPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	drawPanel.setBackground(background);
            }
        });

	    
//	    adding listener for add button
		add.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(balls.size()<1010) {
	    		Ball ball = new Ball(
	                    /* Random positions from 0 to windowWidth or windowHeight */
	                    (int) Math.floor(Math.random() * 1200),
	                    (int) Math.floor(Math.random() * 1200),
	                    /* Random size from 10 to 30 */
	                    (int) Math.floor(Math.random() * 20) + 10,
	                    /* Random RGB colors*/
	                    new Color(
	                            (int) Math.floor((Math.random() * 256)),
	                            (int) Math.floor((Math.random() * 256)),
	                            (int) Math.floor((Math.random() * 256))
	                    ),
	                    /* Random velocities from -5 to 5 */
	                    (int) Math.floor((Math.random() * 10) - 5),
	                    (int) Math.floor((Math.random() * 10) - 5)
	            );
	    		Thread thread = new Thread(ball);
	    		thread.start();
	            balls.add(ball);
	            noofballs++;
	    		ballslabel.setText("Balls   "+noofballs);
	    		textpanel.remove(txtInput);
	    		System.out.println(noofballs);
	    		textpanel.add(ballslabel, BorderLayout.LINE_START);
    			mainPanel.add(drawPanel);
    			mainPanel.revalidate();
    			mainPanel.repaint();
	    		}
	    	}
	    });	
		
		
//		adding listener for info button
	    info.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		JDialog d = new JDialog(f, "Instructions"); 
	    		String html = "<html><body width='%1s'><h1>&nbsp;Instructions</h1>"
	                    + "<p>&nbsp;Click play button to start <br>"
	    				+ "&nbsp;Click subtract button to remove a ball <br>"
	    				+ "&nbsp;Click add button to add a ball <br></p>"
	                    + "<br><br>"
	                    + "<p>&nbsp;Dark mode enabled on move over inside "
	                    + "ball panel.</p></body></html>";
	    		JLabel l = new JLabel(html);
	    		d.add(l); 
	    		  
	            // setsize of dialog 
	            d.setSize(300, 300); 
	    	    d.setLocationRelativeTo(null);
	  
	            // set visibility of dialog 
	            d.setVisible(true);
	    	}
	    });
	    
	    
//		adding listener for play pause button
	    playPause.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		playPause.setIcon( flag ? pauseicon :playicon  );

	    		/* Give Swing 10 milliseconds to add noofballs to screen! */
    			try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	    		if(flag) {
	    			balls = new CopyOnWriteArrayList<>();

	    			for (int i = 0; i < noofballs; i++) {
		    	            Ball ball = new Ball(
		    	                    /* Random positions from 0 to windowWidth or windowHeight */
		    	                    (int) Math.floor(Math.random() * f.getWidth()),
		    	                    (int) Math.floor(Math.random() * f.getHeight()),
		    	                    /* Random size from 10 to 30 */
		    	                    (int) Math.floor(Math.random() * 20) + 10,
		    	                    /* Random RGB colors*/
		    	                    new Color(
		    	                            (int) Math.floor((Math.random() * 256)),
		    	                            (int) Math.floor((Math.random() * 256)),
		    	                            (int) Math.floor((Math.random() * 256))
		    	                    ),
		    	                    /* Random velocities from -5 to 5 */
		    	                    (int) Math.floor((Math.random() * 10) - 5),
		    	                    (int) Math.floor((Math.random() * 10) - 5)
		    	            );
		    	    		Thread thread = new Thread(ball);
		    	    		thread.start();
		    	            balls.add(ball);
	    	        }

	    		    midpanel.add(add, BorderLayout.LINE_START);
	    		    midpanel.add(subtract, BorderLayout.LINE_END);
	    		    txtInput.setEditable(false);
		    		textpanel.remove(txtInput);
		    		textpanel.revalidate();
		    		textpanel.repaint();
		    		textpanel.add(ballslabel, BorderLayout.LINE_START);
	    			ballslabel.setText("Balls   "+noofballs);
	    			mainPanel.add(drawPanel);
	    			mainPanel.revalidate();
	    			mainPanel.repaint();
	    			
	    		}
	    		else {
	    			noofballs=50;
	    		    txtInput.setEditable(true);
	    			balls = new CopyOnWriteArrayList<>();
	    			midpanel.remove(add);
	    			midpanel.remove(subtract);
	    			textpanel.remove(ballslabel);
	    			txtInput.setText("Enter upto 1000 balls");
		    		textpanel.add(txtInput);
	    			mainPanel.remove(drawPanel);
	    			mainPanel.revalidate();
	    			mainPanel.repaint();
	    		}
	    		
	    		flag = !flag;
	    	}
	    });

        



		bottomPanel.add(info, BorderLayout.LINE_END);
		bottomPanel.add(playPause, BorderLayout.LINE_START);
		
		
	    mainPanel = new JPanel(new BorderLayout());
	    mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

	    mainPanel.add(bottomPanel, BorderLayout.PAGE_START);
        
	    f.getContentPane().add(mainPanel);
		f.setSize(400,500);//400 width and 500 height 
	    f.setLocationRelativeTo(null);
	    f.setVisible(true);
	    
	}
	
	static void updateeverthing() {
		while (true) {
	
            for (Ball b: balls) {
                b.run();
            }

            /* Give Swing 10 milliseconds to see the update! */
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mainPanel.repaint();
        }
	}
	
	
	public static void main(String[] args) {
		new BallWorld();
		updateeverthing();
	}
	
	
	
	
	
     class Ball implements Runnable{
        private int posX, posY, size;
        private Color color;

        private int vx = 5;
        private int vy = 5;

        public Ball(int posX, int posY, int size, Color color, int vx, int vy) {
            this.posX = posX;
            this.posY = posY;
            this.size = size;
            this.color = color;
            this.vx = vx;
            this.vy = vy;
        }

         public void run() {

            if (posX > mainPanel.getWidth()-20-2*size || posX < 0) {
                vx *= -1;
            }

            if (posY > mainPanel.getHeight()-50-2*size-20 || posY < 0) {
                vy *= -1;
            }

            if (posX > mainPanel.getWidth()-20-2*size) {
                posX = mainPanel.getWidth()-20-2*size;
            }

            if (posX < 0) {
                posX = 0;
            }

            if (posY > mainPanel.getHeight()-50-2*size-20) {
                posY = mainPanel.getHeight()-50-2*size-20;
            }

            if (posY < 0) {
                posY = 0;
            }

            this.posX += vx;
            this.posY += vy;

        }

        void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(posX, posY, size, size);
        }

    }
    
    
    class DrawPanel extends JPanel {
        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            for (Ball b: balls) {
                b.draw(graphics);
            }

        }
    }
    

}

