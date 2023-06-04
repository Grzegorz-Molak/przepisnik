package pw.paint.DTOs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import pw.paint.model.Ingredient;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDto {

    private ObjectId id;
    private String name;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private Boolean status;
    private List<String> tags;
    private Integer likes;
    private Integer timeMinutes;
    private String author;
}
