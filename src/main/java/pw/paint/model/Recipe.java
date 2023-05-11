package pw.paint.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "recipe")
public class Recipe {
    @Id
    private Integer id;
    private String name;
//    @DBRef
//    private Map<Ingredient, String> ingredients;
    private List<String> steps;
    private boolean status;
//    @DBRef
//    private List<Tag> tags = new ArrayList<>();
    private int author_id;
    private int likes;
    private int timeMinutes;
    //photo

    public Recipe() {};

    public Recipe(Integer id, String name, Map<Ingredient, String> ingredients, List<String> steps, boolean status, List<Tag> tags, int author_id, int likes, int timeMinutes) {
        this.id = id;
        this.name = name;
//        this.ingredients = ingredients;
        this.steps = steps;
        this.status = status;
//        this.tags = tags;
        this.author_id = author_id;
        this.likes = likes;
        this.timeMinutes = timeMinutes;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
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

//    public Map<Ingredient, String> getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(Map<Ingredient, String> ingredients) {
//        this.ingredients = ingredients;
//    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    public List<Tag> getTags() {
//        return tags;
//    }
//
//    public void setTags(List<Tag> tags) {
//        this.tags = tags;
//    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
