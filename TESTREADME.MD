# Testing Instructions

## Building the Program Under Test (PUT)

Follow these instructions to build PUT:

1. **Download**: Download the source code of the PUT from the project's repository or from submitted zip file on Canvas. 

2. **Extract**: Extract the contents of the downloaded zip archive to a directory on your local machine.

3. **Navigate**: Navigate to the root directory of the extracted source code.

4. **Build**: Build the program using the provided build tools or commands.
   ```sh
   # Example build command for Maven projects
   mvn clean install

 ## Building and Running Unit Tests

Follow these instructions to build and run the unit tests for the PUT:

1. **Ensure Build**: Ensure that the PUT is built successfully as per the instructions provided above.

2. **Navigate**: Navigate to the directory containing the unit tests for the PUT.

3. **Run Tests**: Run the unit tests using the JUnit testing framework integrated with Maven.
   ```sh
   # Command to execute JUnit tests with Maven
   mvn test

