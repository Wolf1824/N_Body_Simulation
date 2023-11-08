import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
public class NBody implements Runnable {
    World w1;
    BigDecimal unisize;
    BigDecimal dt;
    BigDecimal T;
    int number;
    BigDecimal tt;
    String [] images;
    public static void main(String [] args)
    {
        NBody nBody= new NBody();
        nBody.create();
        nBody.music();
        nBody.render();
        nBody.output();
    }
    void music()
    {
        Thread t= new Thread(this);
        t.start();
    }
    void create()
    {
        System.out.println("enter time and dt");
        T = new BigDecimal(StdIn.readLine());
        dt= new BigDecimal(StdIn.readLine());
        tt=new BigDecimal("0.0");
        Scanner sc; //file input
        FileReader obj;
        try {
            obj = new FileReader(".idea\\resources\\input.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        sc = new Scanner(obj);
        if (sc.hasNextLine()) {
            String s = sc.nextLine();
            s=s.replaceAll("\\s","");
            number = Integer.parseInt(s);
        }
        if (sc.hasNextLine()) {
            String s = sc.nextLine();
            s=s.replaceAll("\\s","");
            unisize=new BigDecimal(s);
        }
        w1=new World(new BigDecimal("6.67e-11"),number,dt);
        int m=0;
        images=new String[number];
        while (m<number)
        {
            String [] arr=new String[]{
                    sc.next(),
                    sc.next(),
                    sc.next(),
                    sc.next(),
                    sc.next()
            };
            images[m]=sc.next();
            w1.bodies[m]=new Body(new BigDecimal(arr[0]),new BigDecimal(arr[1]),new BigDecimal(arr[2]),new BigDecimal(arr[3]),new BigDecimal(arr[4]),m);
            m++;
        }
        sc.close();
        for(int q=0;q< images.length;q++)
        {
            images[q]=".idea\\resources\\"+images[q];
        }
    }
    void render() {
        int j=0;
        //w1.com();
        StdDraw.setPenColor(Color.GRAY);
        do {
            if(tt.compareTo(T)>=0)
            {
                return;
            }
            if(j%8==0)
                StdDraw.clear();
            ++j;
            for (int i = 0; i < w1.bodies.length; i++) {
                StdDraw.picture(scale((w1.bodies[i].posx)).doubleValue(), scale(w1.bodies[i].posy).doubleValue(), images[i], 0.05, 0.05);
            }
            w1.update();
            tt=tt.add(dt);
        } while (j<50000);
    }
    void output()
    {
        for (Body x: w1.bodies) {
            System.out.println(x);
        }
        FileWriter obj;
        try {
            obj = new FileWriter(".idea\\resources\\output.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(obj);
        try {
            for (Body b:w1.bodies)
            {
                pw.println(b.toString());
            }
            obj.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully wrote to the file.");

    }
    BigDecimal scale(BigDecimal x)
    {
        x=x.divide(unisize.multiply(new BigDecimal("2.0")),25, RoundingMode.FLOOR).add(new BigDecimal("0.5"));
        return x;
    }

    @Override
    public void run() {
        while(true) {
            StdAudio.play(".idea\\resources\\2001.mid");
        }
    }
}
