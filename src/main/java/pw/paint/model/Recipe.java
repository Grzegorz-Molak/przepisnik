package pw.paint.model;

import java.util.List;
import java.util.Map;

public class Recipe {
    private int id;
    private String name;
    private Map<Ingredient, String> ingredients;
    private List<String> steps;
    private boolean status;
    private List<Tag> tags;
    private int author_id;
    private boolean isLiked;

    private int timeMinutes;
    //photo

    public  Recipe(){};

    public Recipe(int id, String name, Map<Ingredient, String> ingredients, List<String> steps, boolean status, List<Tag> tags, int author_id, boolean isLiked, int timeMinutes) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.status = status;
        this.tags = tags;
        this.author_id = author_id;
        this.isLiked = isLiked;
        this.timeMinutes = timeMinutes;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
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

    public Map<Ingredient, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, String> ingredients) {
        this.ingredients = ingredients;
    }

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
