package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private int bookID;
    private String title;
    private String author;
    private String genre;
    private String branchName;
    private String availability;
    private int branchId;


}
