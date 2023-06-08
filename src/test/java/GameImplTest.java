import com.assignment.nl22w.game.impl.GameImpl;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameImplTest {

    @Test
    void escapeFromWoodShortestDistanceEqualsThirteen() throws IOException {
        var game = new GameImpl();
        assertEquals(13, game.escapeFromTheWoods(new ClassPathResource("map2.txt")));
    }

    @Test
    void escapeFromWoodShortestDistanceEqualsFour() throws IOException {
        var game = new GameImpl();
        assertEquals(4, game.escapeFromTheWoods(new ClassPathResource("map1.txt")));
    }
}
