public class Movie {	
   
    private int movieid;
    private String title;
  
    public Movie( int movieid, String title ) 
	  { 
	  this.movieid = movieid; 
	  this.title = title; 
	
	  } 	
  @Override
	  public String toString() 	  {	
	     	return title; 	
	  } 

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }   
}
