import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class World {
    Body bodies[];
    BigDecimal G;
    BigDecimal dt;

    public World(BigDecimal g, int n, BigDecimal dt) {
        G = g;
        bodies=new Body[n];
        //com();
        this.dt=dt;
    }

    private BigDecimal force(BigDecimal m1, BigDecimal m2, BigDecimal r)
    {//r is r2
        r=r.multiply(r);
        if(r.doubleValue()<1)
        {
            return new BigDecimal(0);
        }
        return (G.multiply(m1).multiply(m2)).divide(r,25, RoundingMode.FLOOR); // net force
    }
    private void forcexy()
    {
        for (Body x:bodies) {
            x.accx= new BigDecimal("0.0");
            x.accy=new BigDecimal("0.0");
        }
        for (int i = 0; i < bodies.length; i++) {
            Body a = bodies[i];
            for (int j = i + 1; j < bodies.length; j++) {
                Body b = bodies[j];
                if(a==b)
                {
                    continue;
                }
                BigDecimal m1,m2;
                BigDecimal x;
                BigDecimal y;
                m1=a.mass;
                m2=b.mass;
                x=b.posx.subtract(a.posx);//a at origin and b at some point
                y=b.posy.subtract(a.posy);
                BigDecimal fxy[] = new BigDecimal[2];
                BigDecimal r=((x.multiply(x)).add(y.multiply(y)));
                r=r.sqrt(new MathContext(10));
                double theta;
                /*if(bodies[i].radius.add(bodies[j].radius).compareTo(r)>=0)
                {
                    System.out.println("Collision");
                    bodycollision(i,j);
                }*/
                if (x.doubleValue()==0.0)       // when a body is directly on y axis wrt to target body
                {
                    if ((Math.signum(y.doubleValue())==-1))
                    {
                        fxy[1]= force(m1, m2, r).multiply(BigDecimal.valueOf(-1));
                        a.accy=a.accy.add(fxy[1].divide(a.mass,25, RoundingMode.FLOOR));
                        b.accy=b.accy.subtract(fxy[1].divide(b.mass,25, RoundingMode.FLOOR));
                        break;
                    }
                    else {
                        fxy[1]= force(m1, m2, r);
                        a.accy=a.accy.add(fxy[1].divide(a.mass,25, RoundingMode.FLOOR));
                        b.accy=b.accy.subtract(fxy[1].divide(b.mass,25, RoundingMode.FLOOR));
                        break;
                    }
                }
                else if (y.doubleValue()==0.0)           //when body is on x axis wrt target body
                {
                    if((Math.signum(x.doubleValue())==-1)) {
                        fxy[0] = force(m1, m2 ,r).multiply(BigDecimal.valueOf(-1));
                        a.accx=a.accx.add(fxy[0].divide(a.mass, 25, RoundingMode.FLOOR));
                        b.accx=b.accx.subtract(fxy[0].divide(b.mass,25, RoundingMode.FLOOR));
                        break;
                    }
                    else {
                        theta=0.0;
                        fxy[0] = force(m1, m2 ,r);
                        a.accx=a.accx.add(fxy[0].divide(a.mass, 25, RoundingMode.FLOOR));
                        b.accx=b.accx.subtract(fxy[0].divide(b.mass,25, RoundingMode.FLOOR));
                        break;
                    }
                }
                else
                {
                    theta = y.divide(x,25, RoundingMode.FLOOR).doubleValue();
                    theta = Math.atan(theta);
                }
                if ((Math.signum(x.doubleValue())==-1) && (Math.signum(y.doubleValue())==1))       // theta is adjusted according to quadrant with target body at origin
                {
                    theta = theta+Math.PI;
                }
                else if ((Math.signum(x.doubleValue())==-1) && (Math.signum(y.doubleValue())==-1))
                {
                    theta = theta+Math.PI;

                } else if ((Math.signum(x.doubleValue())==1)&&(Math.signum(y.doubleValue())==-1))
                {
                    theta = theta+(2*(Math.PI));
                }

                fxy[0] = force(m1, m2 ,r).multiply(BigDecimal.valueOf(Math.cos(theta)));
                fxy[1]= force(m1, m2, r).multiply(BigDecimal.valueOf(Math.sin(theta)));
                a.accx=a.accx.add(fxy[0].divide(a.mass, 25, RoundingMode.FLOOR));
                a.accy=a.accy.add(fxy[1].divide(a.mass,25, RoundingMode.FLOOR));
                b.accx=b.accx.subtract(fxy[0].divide(b.mass,25, RoundingMode.FLOOR));
                b.accy=b.accy.subtract(fxy[1].divide(b.mass,25, RoundingMode.FLOOR));
            }

        }
    }
    void updatedisplacement()
    {
        for (Body body: bodies)
        {
            body.posx=body.posx.add((body.velx.multiply(dt)).add(body.accx.multiply(dt.multiply(dt).multiply(BigDecimal.valueOf(0.5)))));
            body.posy=body.posy.add((body.vely.multiply(dt)).add(body.accy.multiply(dt.multiply(dt).multiply(BigDecimal.valueOf(0.5)))));
        }

    }
    void updatevelocity()
    {
        for (Body body:bodies)
        {
            body.velx=body.velx.add(body.accx.multiply(dt));
            body.vely=body.vely.add(body.accy.multiply(dt));
        }
    }
    void update()
    {
        forcexy();
        for (Body x:bodies) {
            System.out.println(x);
        }
        updatedisplacement();
        updatevelocity();
        //com();
    }
    /*void wallcollision()
    {
        for (Body b:bodies)
        {
            if(b.posx.compareTo()<0)
            {

            }

        }

    }
    */

    /*void bodycollision(int i, int j)
    {

    }*/
}
