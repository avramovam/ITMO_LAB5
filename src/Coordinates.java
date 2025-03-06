public class Coordinates {
    private float x; //Максимальное значение поля: 847
    private Integer y; //Значение поля должно быть больше -177, Поле не может быть null

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    public Float getX() {
        return x;
    }

    public void setX(float x) {
        if (x > 847) {
            throw new IllegalArgumentException("X координата не может быть больше 847");
        }
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        if (y <= -177) {
            throw new IllegalArgumentException("Y координата не может быть меньше -177");
        }
        this.y = y;
    }
}