import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Leg extends Spider
{
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;
    public static String query="dynamic fullflame";
    public static String brand_original="megacorp";
    public static String adjquery=query.replace(" ","+");

    public static String[] brands={"Acer", "Alcatel", "Allview", "Amazon", "Apple", "Asus", "Blackberry", "Ericsson", "Fitbit", "Google", "HTC ", "Huawei ", "Kyocera", "Lenovo", "LG ", "Meizu", "Microsoft", "Motorola ", "Nokia ", "OnePlus", "Oppo", "Pantech", "Sagem", "Samsung ", "Siemens", "Sony_Sony-Ericsson ", "Various_phones", "Wiko", "Xiaomi", "ZTE"};
    public static int[] brandkeys={641, 316, 763, 759, 555, 317, 689, 322, 762, 748, 642, 696, 697, 741, 327, 747, 749, 333, 335, 778, 750, 337, 339, 340, 343, 344, 643, 742, 745, 746};
    
    
   
    public boolean crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
     
        if(!connection.response().contentType().contains("text/html")) {
                System.out.println("Something's wrong. This webpage isn't a parse-able text or HTML.");
                return false;
        }
            Elements linksOnPage = htmlDocument.select("a[href]");
            for(int i=0;i<linksOnPage.size();i++){
            if((url.contains("blueprints/") || url.contains("vectordrawing/")) && (url.contains(query) || url.contains(brand_original))) {
            System.out.println("I also noticed a URL of "+linksOnPage.get(i));
            founder++;
        }
        }
            
        for(Element link : linksOnPage)
            {
                this.links.add(link.absUrl("href"));
            }
            return true;
        }
        catch(IOException e) {
         return false;
        }
    }

    public boolean searchForWord(String word)
    {
        if(this.htmlDocument == null) return false;    
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(word.toLowerCase());
    }

    public List<String> getLinks()
    {
        return this.links;
    }
    
    public static void main()
    {
        
        Leg spider = new Leg();
        Scanner w=new Scanner(System.in);
        System.out.println("This program is a very basic right-to-repair checker.");
        System.out.println("If the entered device has a publicly accessble blueprint or repair schematic, this program will tell you that it exists.");
        System.out.println("If it exists, the given brand supports right to repair. Otherwise, it doesn't.");
        System.out.println("Enter the brand of your device");
        String brand=w.nextLine();
        brand=brand.trim();
        brand_original=brand;
        System.out.println("Now, enter what device you're looking to search for. Be as exact as possible.");
        int counter=-1;
        String dev=w.nextLine();
        query=dev;
        for(int i=0;i<brands.length;i++){
        if(brand.equalsIgnoreCase(brands[i])) {
            counter = i; break;
        }
    }
    System.out.println("counter = "+counter);
    System.out.println("Searching...");
    
    if(counter ==-1)
    System.out.println("Sorry, it doesn't look like this brand supports right to repair / the brand cannot be found.");
    else {
        spider.search("https://www.the-blueprints.com/vectordrawings/brand/"+brands[counter].toLowerCase()+"/"+brandkeys[counter]+"/", query);
        if (founder<=0)
            System.out.println("v2: Sorry, it doesn't look like this brand supports right to repair / the device cannot be found.");

    }
    }
}