package pl.urnotrme.playgound;

import org.junit.jupiter.api.Test;

public class FirstTest {

    @Test
    void myFirstTest() {
        assert true == true;
    }

    @Test
    void my2ndTest() {
        String name = "kamila";
        String hello = String.format("Hello %s", name);
        assert hello.equals("Hello kamila");
    }

    @Test
    void baseTestSchema() {
        //Arrange  // Given // Input
        //Act     // When  // call/interaction
        //Assert // Then  // Output
    }


}
