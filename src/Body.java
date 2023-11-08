import java.math.BigDecimal;

public class Body {
    BigDecimal posx;
    BigDecimal posy;
    BigDecimal velx;
    BigDecimal vely;
    BigDecimal accx;
    BigDecimal accy;
    BigDecimal mass;
    BigDecimal radius;
    int id;
    public Body(BigDecimal posx, BigDecimal posy, BigDecimal velx,BigDecimal vely, BigDecimal mass, int id) {
        this.posx = posx;
        this.posy = posy;
        this.velx = velx;
        this.vely = vely;
        this.mass = mass;
        accx = new BigDecimal("0.0");
        accy = new BigDecimal("0.0");
        radius=new BigDecimal("0.05");
        this.id = id;
    }

    @Override
    public String toString() {
        return "" +
                "" + posx.doubleValue() +
                "  " + posy.doubleValue() +
                "  " + velx.doubleValue() +
                "  " + vely.doubleValue() +
                "  " + mass.doubleValue();
    }
}
