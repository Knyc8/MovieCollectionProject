import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.println("Enter a cast search term: ");
        String castTerm = scanner.nextLine();

        //prevent case sensitivity
        castTerm = castTerm.toLowerCase();

        //arrayList to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> castResults = new ArrayList<String>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieCast = movies.get(i).getCast();
            movieCast = movieCast.toLowerCase();
            String[] castList = movieCast.split("\\|");

            for (String cast : castList)
            {
                if (cast.indexOf(castTerm) != -1) {
                    //add the Movie object to the results list
                    if (castResults.indexOf(cast) == -1) {
                        castResults.add(cast);
                    }
                }
            }
        }
        //sorts the genres into alphabetical order
        castResults.sort(String::compareTo);

        //prints out each genre
        for (int i = 0; i < castResults.size(); i++)
        {
            System.out.println(i+1 + ". " + castResults.get(i));
        }

        System.out.println("Which actor(ress) would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        while (choice > castResults.size() || choice < 1)
        {
            System.out.print("Enter number: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        String actor = castResults.get(choice - 1);

        for (Movie movie : movies)
        {
            String[] movieCast = movie.getCast().split("\\|");
            for (String name : movieCast)
            {
                if (name.equalsIgnoreCase(actor))
                {
                    results.add(movie);
                }
            }
        }

        sortResults(results);
        System.out.println(actor);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieChoice = scanner.nextInt();
        scanner.nextLine();
        while (movieChoice > results.size() || choice < 1)
        {
            System.out.print("Enter number: ");
            movieChoice = scanner.nextInt();
            scanner.nextLine();
        }

        Movie selectedMovie = results.get(movieChoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeywords = movies.get(i).getKeywords();
            movieKeywords = movieKeywords.toLowerCase();

            if (movieKeywords.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by keywords
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String keywords = results.get(i).getKeywords();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + keywords);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        while (choice > results.size() || choice < 1)
        {
            System.out.print("Enter number: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listGenres()
    {
        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> genres = new ArrayList<String>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getGenres();
            movieTitle = movieTitle.toLowerCase();
            String[] splitted = movieTitle.split("\\|");
            for (String split : splitted) {
                if (genres.indexOf(split) == -1) {
                    genres.add(split);
                }
            }
        }

        //sorts the genres into alphabetical order
        genres.sort(String::compareTo);

        //prints out each genre
        for (int i = 0; i < genres.size(); i++)
        {
            System.out.println(i+1 + ". " + genres.get(i));
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        while (choice > genres.size() || choice < 1)
        {
            System.out.print("Enter number: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        String genre = genres.get(choice - 1);

        for (Movie movie : movies)
        {
            String[] genreList = movie.getGenres().split("\\|");
            for (String genName : genreList)
            {
                if (genName.equalsIgnoreCase(genre))
                {
                    results.add(movie);
                }
            }
        }

        // sort the results by keywords
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int genChoice = scanner.nextInt();
        scanner.nextLine();
        while (genChoice > results.size() || choice < 1)
        {
            System.out.print("Enter number: ");
            genChoice = scanner.nextInt();
            scanner.nextLine();
        }

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Double> ratings = new ArrayList<Double>();
        for (Movie movie : movies)
        {
            ratings.add(movie.getUserRating());
        }
        ratings.sort(Double::compareTo);
        Collections.sort(ratings, Collections.reverseOrder());

        ArrayList<Movie> highestRated = new ArrayList<Movie>();
        for (int i = 0; i < 50; i++)
        {
            for (Movie movie : movies)
            {
                if (movie.getUserRating() == ratings.get(i))
                {
                    if (highestRated.indexOf(movie) == -1) {
                        highestRated.add(movie);
                        break;
                    }
                }
            }
        }

        int i = 0;
        for (Movie movie: highestRated)
        {
            i++;
            System.out.println(i + ". " + movie.getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        while (choice > highestRated.size() || choice < 1)
        {
            System.out.print("Enter number: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        Movie selectedMovie = highestRated.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Integer> revenue = new ArrayList<Integer>();
        for (Movie movie : movies)
        {
            revenue.add(movie.getRevenue());
        }
        revenue.sort(Integer::compareTo);
        Collections.sort(revenue, Collections.reverseOrder());

        ArrayList<Movie> highestRev = new ArrayList<Movie>();
        for (int i = 0; i < 50; i++)
        {
            for (Movie movie : movies)
            {
                if (movie.getRevenue() == revenue.get(i))
                {
                    if (highestRev.indexOf(movie) == -1) {
                        highestRev.add(movie);
                        break;
                    }
                }
            }
        }

        int i = 0;
        for (Movie movie: highestRev)
        {
            i++;
            System.out.println(i + ". " + movie.getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        while (choice > highestRev.size() || choice < 1)
        {
            System.out.print("Enter number: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        Movie selectedMovie = highestRev.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}