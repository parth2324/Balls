package Project.BallDesign;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
class Ball
{
    int x,y,dirx,diry,spx,spy;
    Color c;
    Dimension d;
    private ArrayList<Integer> in;
    Ball(int x,int y,Color c,int dirx,int diry,int spx,int spy,Dimension d)
    {
        this.x=x;
        this.y=y;
        this.c=c;
        this.dirx=dirx;
        this.diry=diry;
        this.spx=spx;
        this.spy=spy;
        this.d=d;
    }
    void update()
    {
        x+=dirx*spx;
        y+=diry*spy;
        if(y>d.height-10 || y<0)diry*=-1;
        if(x>d.width-10 || x<0)dirx*=-1;
    }
    ArrayList<Integer> vicinity(Ball[] arr,int max,int start)
    {
        in=new ArrayList<>();
        for(int i=start+1;i<arr.length;i++){if(arr[i].x!=this.x && Math.abs(arr[i].x-this.x)<=max && Math.abs(arr[i].y-this.y)<=max)in.add(i);}
        return in;
    }
}