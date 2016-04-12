package embedded.mongo.data;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Test {
    @Id
    String id;

    @NonNull String val;

    LocalDateTime updatedAt = LocalDateTime.now();
}
