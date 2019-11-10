# JFuture 2019

## Task
Imagine that you're a developer working on Cinema Analytics System, who needs to collect data and make calculations on the following items:

1. Display the dynamics of film releases in China and the United States over the past 3 years. Please take into account 5 different film genres.

2. Make the list of top 5 Directors of the highest-rated films.

## Running instruction
In order to run the program, you need to use Maven.

1. Set current directory to project directory;

2. Build project using

    ```
    mvn install
    ```

2. Run program using 

    ```
    mvn exec:java -Dexec.mainClass=dev.jfuture.task.TaskSolver
    ```

The program will gather the data and make calculations. Note, that it may take a while.
After that files "dynamics.html" and "directors.html" will be created in the project directory. 

You can see result examples in [dynamics.html](dynamics.html) and [directors.html](directors.html)

## Algorithm description
* Task 1:

    In the first task the program parses movies from Wikipedia tables.
    Then it calculates the amount of each genre and selects five the most popular genres.
    Later the amount of movies of each genre is counted. The results are presented in the form of HTML documents.
    
    For **information gathering** see [WikipediaMovieParser](src/main/java/dev/jfuture/task/parser/wikipedia/WikipediaMovieParser.java)
    
    For **calculations** see [GenreCalculator](src/main/java/dev/jfuture/task/calculator/GenreCalculator.java) and
    [MovieService](src/main/java/dev/jfuture/task/service/MovieService.java)
    
    ---
    
* Task 2:
    In the second task the program parses movies from IMDb rating.
    Rating is calculated from parsed values.
    The list is distinct, i. e. it does not contains the same directors.
    It is performed by comparing calculated ratings of the movies
    in case the author has several movies from the movie list.
    
    For **information gathering** and **calculations** see [ImdbMovieParser](src/main/java/dev/jfuture/task/parser/imdb/ImdbMovieParser.java)
    
## Data
All data is collected from online resources during program execution. 
Data sources are indicated in the generated documents.

## Author
Mikhail Loseu