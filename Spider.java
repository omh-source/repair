 

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider
{
  private static final int MAX_PAGES_TO_SEARCH = 10;
  private Set<String> pagesVisited = new HashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();
  public static int founder=0;

  public void search(String url, String searchWord)
  {
      while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
      {
          String currentUrl;
          Leg leg = new Leg();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(url);
          }
          else
          {
              currentUrl = this.nextUrl();
          }
          leg.crawl(currentUrl); 
          boolean success = leg.searchForWord(searchWord);
          if(success)
          {
             founder++;
             System.out.println("A link to a publicly available blueprint or repair schematic can be directly  found at \n"+currentUrl);
              break;
          }
          this.pagesToVisit.addAll(leg.getLinks());
      }
      System.out.println(this.pagesVisited.size() + " web page(s) were visited, exiting.");
     // if(founder<=0)
       //     System.out.println("v3: Sorry, it doesn't look like this brand supports right to repair / the device cannot be found.");
  }


 
  private String nextUrl()
  {
      String nextUrl;
      do
      {
          nextUrl = this.pagesToVisit.remove(0);
      } while(this.pagesVisited.contains(nextUrl));
      this.pagesVisited.add(nextUrl);
      return nextUrl;
  }
}
