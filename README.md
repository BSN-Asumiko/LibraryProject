# Library

## Project description

This project is a command-line based library management system that allows users to interact with a PostgreSQL database. Users can view, add, edit, and delete books, as well as perform searches by author, genre, and title. The project includes unit tests to ensure that the functions operate correctly, and Maven is used for managing these tests.

## Features

View Books: List all books available in the library.
Add Book: Add a new book to the library.
Edit Book: Edit details of an existing book.
Delete Book: Remove a book from the library.
Search Books: Search for books by author, genre, or title.

## Used technologies

- Java V. 17
- Junit V. 4.11
- Maven V. 3.9.8
- PostgreSQL V. 42.7.3
- PgAdmin 4
- Mockito V. 5.12
- Mockito Core V. 5.12

## Setup Instructions

1. Access the online repository page

2. Copy the repository URL that appears when accessing the Code button

3. Enter VS Code

In the VS Code Terminal (Ctrl + ño Terminal > New Terminal) we write the following:

$ git clone (copied link)

cd ../path/to/the/file

4. Create a PostgreSQL database.

5. Update the src/main/resources/application.properties file with your database credentials.
 
6. Configuring the JAVA_HOME environment variable is an essential step for Java development and ensuring that your system and various tools (like Maven) can locate your Java installation.

Below are the steps to configure JAVA_HOME for different operating systems:

* Windows
  
Determine the Java installation path:

Locate the directory where Java is installed. Typically, it is something like C:\Program Files\Java\jdk-11.x.x.
Set JAVA_HOME:

Open the Start menu and search for "Environment Variables".
Click "Edit the system environment variables".
In the System Properties window, click the "Environment Variables" button.
In the Environment Variables window, click "New" under System variables.
Enter JAVA_HOME as the variable name and the path to your JDK as the variable value (e.g., C:\Program Files\Java\jdk-11.x.x).
Click "OK" to save the new variable.
Update the PATH variable:

In the Environment Variables window, find the Path variable under System variables, select it, and click "Edit".
Click "New" and add %JAVA_HOME%\bin.
Click "OK" to save your changes.
Verify the configuration:

Open a new Command Prompt and run:
sh

echo %JAVA_HOME%
You should see the path to your Java installation.
Also, run:
sh

java -version
This should display the installed Java version.
* macOS
  
Determine the Java installation path:

Run the following command in Terminal to find your Java installation path:
sh

/usr/libexec/java_home
This will output something like /Library/Java/JavaVirtualMachines/jdk-11.x.x.jdk/Contents/Home.
Set JAVA_HOME:

Open Terminal and edit your shell profile. For bash, you would typically edit ~/.bash_profile or ~/.bashrc. For zsh (default on macOS Catalina and later), edit ~/.zshrc:
sh

nano ~/.zshrc
Add the following line to set the JAVA_HOME variable (replace the path with your actual Java installation path):
sh

export JAVA_HOME=$(/usr/libexec/java_home)
Save the file and reload your profile:
sh

source ~/.zshrc
Verify the configuration:

Run:
sh

echo $JAVA_HOME
You should see the path to your Java installation.
Also, run:
sh

java -version
This should display the installed Java version.

* Linux (Ubuntu/Debian)
  
Determine the Java installation path:

Typically, Java is installed in /usr/lib/jvm. You can list the contents of this directory to find the correct path:
sh

ls /usr/lib/jvm
You might see something like java-11-openjdk-amd64.
Set JAVA_HOME:

Open your profile file in a text editor. This could be ~/.bashrc, ~/.profile, or ~/.bash_profile:
sh

nano ~/.bashrc
Add the following lines to set the JAVA_HOME variable (replace the path with your actual Java installation path):
sh

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
Save the file and reload your profile:
sh

source ~/.bashrc
Verify the configuration:

Run:
sh

echo $JAVA_HOME
You should see the path to your Java installation.
Also, run:
sh

java -version
This should display the installed Java version.

## How to view data by console

We click on the 'Run Java' play button in the App.java document

## How to run the tests

Having the 'Extension Pack for Java' extension in VSC, and Maven installed,  click the laboratory symbol and the play button

## Integrants

- https://github.com/flaviferri
- https://github.com/Adrianaortiz00
- https://github.com/BSN-Asumiko
- https://github.com/Lauraa23
- https://github.com/indiakka
