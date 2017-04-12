package project1;


public class Main {

	  public static void main(String[] args) {
		  
		// directory to a java file with nested comments (can be changed if needed)
	    String fname  = "src/project1/FILENAME.java";
	    
	    CommentFinder finder = new CommentFinder(fname);
	    
	    finder.findAndErase();
//	    finder.eraseNestedComments();
	    
//	    uncomment if want to save changes to file
//	    finder.save();
	  }
	}

