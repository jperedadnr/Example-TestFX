# Example of using TestFX
This example project shows the use of [TestFX](https://github.com/TestFX/TestFX). 
It is based on the example provided by the TestFX community documentation. It takes the 
example further by separating out the Scene information into a separate class 
and adds some further tests.

# Structure of the Application 
The application code is found in [src/main/java](src/main/java). It contains a
single class, ExampleGridScene. This is a subclass of the JavaFX Scene class. The 
ExampleGridScene adds a GridPane as the root element. Two buttons are then 
added into the root element. 

Each button has an ID. This is a string label. It is used by the tests as
a way to find the relevant button and interact with it during the testing stage. 

The first button simple changes its label once it has been pressed. The 
label is changed to the same value whenever it is pressed. 

The second button has label with the format `<numberOfClicks> clicks`. 
It starts with the value `0 clicks`. Each time it is pressed, the value 
will change to represent the click count. For example, after two clicks, 
the value will be `2 clicks`. 

# Structure of the Test 
The test class is found in [src/test/java](src/test/java). 
The class is setup to use JUnit 5 and the Hamcrest matchers. Each test is 
marked with the `@Test` annotation. 

There is a method at the top of the class that is used to start the 
Graphical User Interface (GUI) panel for each test. It has the `@Start` annotation, which comes
from the TestFX package for JUnit5 integration. The `@Start` method is 
passed a JavaFX Stage. The GUI panel is to be placed into that Stage 
and it is then shown. 

The tests are using [Hamcrest](http://hamcrest.org) matchers, which provide alternative ways
to write assertions; Hamcrest refers to these as flexible expressions of intent, rather than 
assertions. However, they amount to same thing. If a Hamcrest expression does 
not evaluate to true, the test will be recorded as failed. 

An example Hamcrest matcher is `verifyThat()`.

The tests can make use of a FXRobot class, which comes from the TestFX classes. This enables the
code to interact with the UI, e.g. clicking on a specific button. 

In order to identify controls to interact with, a CSS ID is used. For example, 
look at the following line of code. 

```
verifyThat("#first-button", hasText("click me to change my name!"));
``` 

The first parameter is a Node Query, i.e. a way to find an element in the GUI.
There are different ways to perform this query, but this code is using 
CSS IDs. That is, it must start with a # character and is followed by a 
string of characters. In this code, this matches the names set using 
`setId()` in the ExampleGridScene class. 

# Building and running the project
The focus of this project is to run the tests. The Maven `pom.xml` is 
setup with the necessary dependencies to allow that to happen. There is 
no `main()` method, so this program cannot currently run other than 
through the tests. 

To run the program from the command line, use `mvn test`.

The project can also be imported into environments such as Eclipse and IntelliJ IDEA.

# Further information about TestFX
The TestFX community has a [Wiki](https://github.com/TestFX/TestFX/wiki) and 
also discusses issues on Gitter. See the TestFX pages for further 
information.  

