package project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentFinder {
	
		private File fileDirectory;
		private String content;
		
		/**
		 *  Constructor reads text from a given file 
		 *  and puts it to string content 
		 */
		public CommentFinder(String fname){
			
			FileReader fr = null;
			StringBuffer sb = null;
			
			try{
				fr = new FileReader(fname);
				this.fileDirectory = new File(fname);
			} catch(FileNotFoundException ex1){
				ex1.printStackTrace();
			}
			
			sb = new StringBuffer();
			
			try(BufferedReader br = new BufferedReader(fr)){
				String line = null;
				while((line =  br.readLine()) != null){
					sb.append(line + "\n");
				}
				this.content = sb.toString();
			}
			catch(IOException ex2){
				ex2.printStackTrace();
			}
		}
		
		
		
		/**
		 *   Saves all changes to the file from string content
		 */
		public void save() {
			
			PrintWriter saver = null;
			
			try{
				saver = new PrintWriter(fileDirectory);
				saver.write(content);
				
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				saver.close();
				System.out.println("Changes saved");
			}
		}
		
		/** NEW METHOD
		 *  Finds and erase nested comments 
		 */
		public void eraseNestedComments() {
			
			Pattern p = Pattern.compile("//.*|\"(?:\\\\[^\"]|\\\\\"|.)*?\"|(?s)/\\*.*?\\*/");
			Matcher m = p.matcher(content);
			StringBuffer sbu = new StringBuffer();
			String commentSign, restOfMatch;
			
			while(m.find()) {
				
				commentSign = m.group().substring(0,2);
				restOfMatch = m.group().substring(2);
				
				if(commentSign.equals("//")){
					if(m.group().equals("//")){
						m.appendReplacement(sbu, m.group().replaceAll("//", ""));
					} else {
					m.appendReplacement(sbu, commentSign 
							+ restOfMatch.replaceAll("\\*?/?\\*?", ""));
					}
				} else if (commentSign.equals("/*")) {
					m.appendReplacement(sbu, commentSign 
							+ restOfMatch.replaceAll("//", ""));
				}
			}
			m.appendTail(sbu);
			content = sbu.toString();
			
//			Uncomment to see changed file in console
//			System.out.println(content);
		}
		
		/** Deprecated
		 * 	Method uses eraseOnelineComments() and eraseSingleInMultiComments() methods
		 * 	to change string content
		 */
		public void findAndErase() {
			
			eraseSingleInMultiComments();
			eraseOnelineComments();
			
//			Uncomment to see changes of a file in console
			System.out.println(content);
		}
		
		/** Deprecated
		 *  Find and replace additional oneline comments in oneline comments
		 */
		private void eraseOnelineComments() {
			
			Pattern p = Pattern.compile("(//)(.*)");
			Matcher m = p.matcher(content);
			StringBuffer sbu = new StringBuffer();
			StringBuffer temp ;
			
			while(m.find()){
				temp = new StringBuffer();
				temp.append(m.group(1));
				temp.append(m.group(2).replaceAll("[^*]/[^*]", ""));
				m.appendReplacement(sbu, temp.toString());
			}
				m.appendTail(sbu);
				content = sbu.toString();
		}
		
		/** Deprecated
		 *  Find and replace additional oneline comments in multiline comments
		 */
		private void eraseSingleInMultiComments() {
			
			Pattern p = Pattern.compile("(?s)/\\*.*?\\*/(//)?");
			Matcher m = p.matcher(content);
			StringBuffer sbu = new StringBuffer();
			
				while(m.find()){
					m.appendReplacement(sbu, m.group().replaceAll("//",""));
				}	
				m.appendTail(sbu);
				content = sbu.toString();
		}
}
