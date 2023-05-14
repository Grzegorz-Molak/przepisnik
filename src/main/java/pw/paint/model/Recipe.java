package pw.paint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recipes")
public class Recipe {

    @Id
    private ObjectId id;
    private String name;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private Boolean status;
    private List<String> tags;
    private Integer likes;
    private Integer timeMinutes;
    @DBRef
    private User author;

    //TO DO PHOTO VARIABLE

}
