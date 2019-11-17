package Project.BallDesign;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
//import Ball;
class Run
{
    private static Dimension d;
    private static int max,sleep=5,c,r=255,g=255,b=0;
    private static ArrayList<Integer> e,ex;
    private static Ball[] balls;
    private static int[] x,y;
    private static boolean polygon=false,heavypoly=false;
    public static void main(String[] args)
    {
        JFrame window=new JFrame("GUI Project");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setExtendedState(Frame.MAXIMIZED_BOTH);
        d=Toolkit.getDefaultToolkit().getScreenSize();
        window.setBounds(0,0,d.width,d.height);
        window.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyChar()=='-' && e.isControlDown() && max>0)max--;
                else if(e.getKeyChar()=='=' && e.isControlDown())max++;
                else if(e.getKeyChar()=='=' && sleep>0)sleep--;
                else if(e.getKeyChar()=='-')sleep++;
                if(e.getKeyChar()=='e' && r>0)r-=15;
                if(e.getKeyChar()=='r' && r<255)r+=15;
                if(e.getKeyChar()=='v' && b>0)b-=15;
                if(e.getKeyChar()=='b' && b<255)b+=15;
                if(e.getKeyChar()=='f' && g>0)g-=15;
                if(e.getKeyChar()=='g' && g<255)g+=15;
                if(e.getKeyChar()=='p')polygon=!polygon;
                if(e.getKeyChar()=='h')heavypoly=!heavypoly;
            }
        });
        JPanel display=new JPanel()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public void paint(Graphics gg)
            {
                super.paint(gg);
                gg.setColor(Color.BLACK);
                gg.fillRect(0,0,d.width+7,d.height);
                if(balls!=null)
                {
                    for(int i=0;i<balls.length;i++)
                    {
                        gg.setColor(balls[i].c);
                        gg.fillOval(balls[i].x,balls[i].y,10,10);
                        balls[i].update();
                        balls[i].c=new Color((int)(balls[i].x*((double)r/d.width)),(int)(balls[i].y*((double)g/d.height)),b);
                        e=balls[i].vicinity(balls,max,i);
                        c=0;
                        while(c<e.size())
                        {
                            gg.setColor(balls[e.get(c)].c);
                            gg.drawLine(balls[e.get(c)].x+5,balls[e.get(c)].y+5,balls[i].x+5,balls[i].y+5);
                            c++;
                        }
                        if(e.size()>1 && polygon)
                        {
                            x=new int[e.size()+1];
                            y=new int[e.size()+1];
                            c=0;
                            while(c<e.size())
                            {
                                x[c]=balls[e.get(c)].x+5;
                                y[c]=balls[e.get(c)].y+5;
                                c++;
                            }
                            x[e.size()]=balls[i].x+5;
                            y[e.size()]=balls[i].y+5;
                            gg.setColor(new Color(balls[i].c.getRed(),balls[i].c.getGreen(),balls[i].c.getBlue(),130));
                            gg.fillPolygon(x,y,e.size()+1);
                        }
                        if(heavypoly)
                        {
                            ex=new ArrayList<>();
                            e=new ArrayList<>();
                            ex.add(balls[i].x+5);
                            e.add(balls[i].y+5);
                            for(int j=0;j<balls.length;j++)
                            {
                                if(Math.abs(balls[j].x-balls[i].x)<=max && Math.abs(balls[j].y-balls[i].y)<=max && i!=j)
                                {
                                    ex.add(balls[j].x+5);
                                    e.add(balls[j].y+5);
                                }
                            }
                            if(ex.size()>2)
                            {
                                gg.setColor(new Color(balls[i].c.getRed(),balls[i].c.getGreen(),balls[i].c.getBlue(),130));
                                x=new int[ex.size()];
                                y=new int[e.size()];
                                for(int ye=0;ye<ex.size();ye++)
                                {
                                    x[ye]=ex.get(ye);
                                    y[ye]=e.get(ye);
                                }
                                gg.fillPolygon(x,y,ex.size());
                            }
                        }
                        /*for(int j=0;j<balls.length;j++)
                        {
                            if(Math.abs(balls[j].x-balls[i].x)<=max && Math.abs(balls[j].y-balls[i].y)<=max && i!=j)
                            {
                                g.setColor(balls[j].c);
                                g.drawLine(balls[j].x+5,balls[j].y+5,balls[i].x+5,balls[i].y+5);
                            }
                        }*/
                        // bad way above ! O(2n^2)
                    }
                }
            }
        };
        display.setBounds(window.getBounds());
        display.setDoubleBuffered(true);
        window.add(display);
        window.setVisible(true);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                max=Integer.parseInt(JOptionPane.showInputDialog(window,"Enter the minimum length for connection between balls :","Question",JOptionPane.QUESTION_MESSAGE));
                balls=new Ball[Integer.parseInt(JOptionPane.showInputDialog(window,"Enter the number of balls :","Question",JOptionPane.QUESTION_MESSAGE))];
                for(int i=0;i<balls.length;i++){balls[i]=new Ball(gen(d.width),gen(d.height),new Color(gen(gen(0,128),gen(128,255)),gen(gen(0,128),gen(128,255)),gen(gen(0,128),gen(128,255))),gen(1,-1),gen(1,-1),gen(5),gen(5),d);}
                while(true){display.repaint();try{Thread.sleep(sleep);}catch(Exception e){System.err.println("Interrupted");}}
            }
        }).start();
    }
    private static Random rr=new Random();
    private static int gen(int tru,int fal){return rr.nextInt(10)>=5?tru:fal;}
    private static int gen(int bound){return rr.nextInt(bound);}
}