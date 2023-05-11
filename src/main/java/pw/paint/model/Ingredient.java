package pw.paint.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ingredient")
public class Ingredient {
    @Id
    private Integer id;
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

