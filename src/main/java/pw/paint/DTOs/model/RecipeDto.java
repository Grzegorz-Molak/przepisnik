package pw.paint.DTOs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDto {

    private String id;
    private String name;
    private String author;
    private Boolean status;

    private List<String> tags;
    private List<String> ingredients;
    private List<String> steps;
    private Integer timeMinutes;

}
