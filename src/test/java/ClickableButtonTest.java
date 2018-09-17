import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
class ClickableButtonsTest {

    @Start
    void onStart(Stage stage) {
        stage.setScene(new ExampleGridScene());
        stage.show();
    }

    @Test
    void should_contain_first_button() {
        // expect:
        verifyThat("#first-button", hasText("click me to change my name!"));
    }

    @Test
    void should_click_on_first_button(FxRobot robot) {
        // when:
        robot.clickOn("#first-button");

        // then:
        verifyThat("#first-button", hasText("clicked!"));
    }

    @Test
    void should_click_on_second_button_once(FxRobot robot) {
        // when:
        robot.clickOn("#second-button");

        // then:
        verifyThat("#second-button", hasText("1 clicks"));
        verifyThat("#first-button", hasText("click me to change my name!"));
    }

    @Test
    void should_click_on_second_button_twice(FxRobot robot) {
        // when:
        robot.clickOn("#second-button");
        robot.clickOn("#second-button");

        // then:
        verifyThat("#second-button", hasText("2 clicks"));
        verifyThat("#first-button", hasText("click me to change my name!"));
    }

}