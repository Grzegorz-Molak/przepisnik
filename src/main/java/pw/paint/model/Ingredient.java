package pw.paint.model;

public class Ingredient {
    int id;
    String name;

    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        char a = '"';
        String str = Character.toString(a);
        return "{" + str + "id" + str +  " : " + id + ", " + str + "name" + str + " : " + str + name + str + "}";

    }
}

