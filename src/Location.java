public class Location {
    private Integer x; //Поле не может быть null
    private Long y; //Поле не может быть null
    private String name; //Поле может быть null

    public Location(Integer x, Long y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        if (x == null) {
            throw new IllegalArgumentException("X cannot be null");
        }
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        if (y == null) {
            throw new IllegalArgumentException("Y cannot be null");
        }
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }
}
