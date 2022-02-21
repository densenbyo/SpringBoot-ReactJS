package cz.cvut.fel.ear.lingo;

import cz.cvut.fel.ear.lingo.model.*;
import org.mockito.Mockito;

import java.util.Random;

public class Generator {
    private static final Random RAND = new Random();

    public static int randomInt(int limit) {
        return RAND.nextInt(limit);
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }

    public static Long randomLong() {
        return RAND.nextLong();
    }

    public static User generateUser() {
        User result = new User(
                "username" + RAND.nextInt(100),
                "mail" + RAND.nextInt(100),
                "password" + RAND.nextInt(100)
        );
        result.setActive(RAND.nextBoolean());
        result.setUsername("username" + RAND.nextInt(100));

        return result;
    }

    public static Tag generateTag() {
        return new Tag("name" + RAND.nextInt());
    }

    public static Repo generateRepo() {
        Repo result = new Repo();
        int limit = RAND.nextInt(100);

        for(int i = 0; i < limit+1; i++) {
            Flashcard temp = Mockito.mock(Flashcard.class);
            Mockito.when(temp.getId()).thenReturn(RAND.nextLong());
            result.addFlashcard(temp);
        }

        return result;
    }

    public static FlashcardDeck generateFlashcardDeck() {
        return new FlashcardDeck(
                "name" + RAND.nextInt(),
                "description" + RAND.nextInt(),
                generateUser()
        );
    }

    public static Flashcard generateFlashcard(User user) {
        return new Flashcard(
                "name" + RAND.nextInt(100),
                "translation" + RAND.nextInt(100),
                generateUser()
        );
    }
}
